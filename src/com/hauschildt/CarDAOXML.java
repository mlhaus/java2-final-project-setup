package com.hauschildt;

import java.util.List;

public class CarDAOXML implements CarDAO {
    private static final String FILE_NAME = "cars.xml";
    private static List<Car> cars;

    @Override
    public void readInData() throws DataException {

    }

    private void saveToFile() throws DataException {

    }

    @Override
    public void verifyCarList() throws DataException {

    }

    @Override
    public void createCarRecord(Car car) throws DataException {

    }

    @Override
    public Car getCarByLicensePlate(String licensePlate) throws DataException {
        Car car = null;

        return car;
    }

    @Override
    public List<Car> getAllCars() throws DataException {
        List<Car> cars = null;

        return cars;
    }

    @Override
    public void updateCar(Car original, Car updated) throws DataException {

    }

    @Override
    public void deleteCar(Car car) throws DataException {

    }

    @Override
    public void deleteCar(String licensePlate) throws DataException {

    }

}
