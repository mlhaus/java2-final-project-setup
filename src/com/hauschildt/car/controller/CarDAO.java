package com.hauschildt.car.controller;

import com.hauschildt.DataException;
import com.hauschildt.car.model.Car;

import java.util.List;

public interface CarDAO {
    // Read in data from the data source
    void readInData() throws DataException;

    // Verify that the data has been read in and assigned to an ArrayList
    void verifyCarList() throws DataException;

    // Creates a new car record based on the values in the supplied Car object
    void createCarRecord(Car car) throws DataException;

    // Returns the Car record associated with the licensePlate or null if there is no such car.
    Car getCarByLicensePlate(String licensePlate) throws DataException;

    // Returns a list of all the current Car records.
    List<Car> getAllCars() throws DataException;

    // Looks for the original Car and updates it to match the updated Car.
    // If the original Car cannot be found, the method will throw an Exception.
    void updateCar(Car original, Car updated) throws DataException;

    // Permanently deletes the supplied Car record
    void deleteCar(Car car) throws DataException;

    // Permanently deletes the Car record with the supplied license plate value.
    void deleteCar(String licensePlate) throws DataException;
}
