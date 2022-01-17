package com.hauschildt.car.controller;

import com.hauschildt.DataException;
import com.hauschildt.car.model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOCSV implements CarDAO {
    private static final String FILE_NAME = "cars.csv";
    private static List<Car> cars;

    @Override
    public void readInData() throws DataException {
        try(BufferedReader in = new BufferedReader(new FileReader(FILE_NAME)))
        {
            cars = new ArrayList<>();
            int lineCount =0;
            String line;
            String [] fields;
            String licensePlate;
            String make;
            String model;
            int modelYear;

            line = in.readLine();
            while(in.ready()) {
                line = in.readLine();
                lineCount++;
                fields = line.split(",");
                licensePlate = fields[0];
                make = fields[1];
                model = fields[2];
                try {
                    modelYear = Integer.parseInt(fields[3]);
                }catch (NumberFormatException e)
                {
                    throw new DataException(e.getMessage() + " in " + FILE_NAME + " on line " + lineCount);
                }
                cars.add(new Car(licensePlate, make, model, modelYear));
            }

        }catch(IOException e){
            throw new DataException(e.getMessage());
        }

    }



    private void saveToFile() throws DataException {
        try(PrintWriter writer = new PrintWriter(new File(FILE_NAME))) {
            writer.println("licensePlate,make,model,modelYear");
            String line = "";
            for(Car car: cars) {
                line = car.getLicensePlate() + "," + car.getMake() + ","
                        + car.getModel() + "," + car.getModelYear();
                writer.println(line);
            }
        } catch(FileNotFoundException e) {
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public void verifyCarList() throws DataException {
        if(cars == null) {
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
