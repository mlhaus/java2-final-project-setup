package com.hauschildt.car.controller;

import com.hauschildt.DataException;
import com.hauschildt.car.model.Car;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarDAOXML implements CarDAO {
    private static final String FILE_NAME = "cars.xml";
    private static List<Car> cars;

    @Override
    public void readInData() throws DataException {
        try (InputStream inputStream = new FileInputStream(FILE_NAME)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            NodeList carNodeList = document.getElementsByTagName("car");
            cars = new ArrayList<>();
            for(int i = 0; i < carNodeList.getLength(); i++) {
                Node currentCarNode = carNodeList.item(i);
                cars.add(buildCarFromNode(currentCarNode));
            }
        } catch (Exception ex) {
            throw new DataException(ex);
        }
    }

    private static Car buildCarFromNode(Node carNode) {
        Car car = new Car();
        NamedNodeMap carAttributeMap = carNode.getAttributes();
        Attr attr = (Attr)carAttributeMap.getNamedItem("license-plate");
        car.setLicensePlate(attr.getValue());
        NodeList carDataNodeList = carNode.getChildNodes();
        for(int i = 0; i < carDataNodeList.getLength(); i++) {
            Node dataNode = carDataNodeList.item(i);
            if(dataNode instanceof Element) {
                Element dataElement = (Element)dataNode;
                switch(dataElement.getTagName()) {
                    case "make":
                        String make = dataElement.getTextContent();
                        car.setMake(make);
                        break;
                    case "model":
                        String model = dataElement.getTextContent();
                        car.setModel(model);
                        break;
                    case "modelYear":
                        int modelYear = Integer.parseInt(dataElement.getTextContent());
                        car.setModelYear(modelYear);
                        break;
                    default:

                        break;
                }
            }
        }
        return car;
    }

    private void saveToFile() throws DataException {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME)){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElement("cars");
            document.appendChild(rootElement);
            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            for(Car currentCar: cars) {
                DocumentFragment carFragment = buildCarFragment(document, currentCar);
                rootElement.appendChild(carFragment);
            }
            transformer.transform(source, new StreamResult(fos));
        } catch (Exception e) {
            throw new DataException(e);
        }
    }

    private static DocumentFragment buildCarFragment(Document document, Car car) {
        DocumentFragment carFragment = document.createDocumentFragment();
        Element carElement = document.createElement("car");

        carElement.setAttribute("license-plate", car.getLicensePlate());

        Element makeElement = document.createElement("make");
        makeElement.setTextContent(car.getMake());
        carElement.appendChild(makeElement);

        Element modelElement = document.createElement("model");
        modelElement.setTextContent(car.getModel());
        carElement.appendChild(modelElement);

        Element modelYearElement = document.createElement("modelYear");
        modelYearElement.setTextContent(Integer.toString(car.getModelYear()));
        carElement.appendChild(modelYearElement);

        carFragment.appendChild(carElement);
        return carFragment;
    }

    @Override
    public void verifyCarList() throws DataException {
        if(null == cars){
            readInData();
        }
    }

    @Override
    public void createCarRecord(Car car) throws DataException {
        verifyCarList();
        Car checkCar = getCarByLicensePlate(car.getLicensePlate());
        if(null != checkCar){
            throw new DataException("License Plates must be unique.");
        }
        cars.add(car);
        saveToFile();
    }

    @Override
    public Car getCarByLicensePlate(String licensePlate) throws DataException {
        verifyCarList();
        Car car = null;
        for (Car car1 : cars) {
            if(car1.getLicensePlate().equals(licensePlate)){
                car = car1;
                break;
            }
        }
        return car;
    }

    @Override
    public List<Car> getAllCars() throws DataException {
        verifyCarList();
        return cars;
    }

    @Override
    public void updateCar(Car original, Car updated) throws DataException {
        verifyCarList();
        Car foundCar = null;
        for (Car car : cars) {
            if(car.equals(original)){
                foundCar = car;
                break;
            }
        }
        if(null == foundCar){
            throw new DataException("Original record not found for update.");
        }
        foundCar.setMake(updated.getMake());
        foundCar.setModel(updated.getModel());
        foundCar.setModelYear(updated.getModelYear());
        saveToFile();
    }

    @Override
    public void deleteCar(Car car) throws DataException {
        deleteCar(car.getLicensePlate());
    }

    @Override
    public void deleteCar(String licensePlate) throws DataException {
        verifyCarList();
        Car foundCar = null;
        for (Car car : cars) {
            if(car.getLicensePlate().equals(licensePlate)){
                foundCar = car;
                break;
            }
        }
        if(null == foundCar){
            throw new DataException("Record record not found for delete.");
        }
        cars.remove(foundCar);
        saveToFile();
    }

}
