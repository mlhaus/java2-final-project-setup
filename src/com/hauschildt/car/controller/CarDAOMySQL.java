package com.hauschildt.car.controller;

import com.hauschildt.DataException;
import com.hauschildt.car.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOMySQL implements CarDAO {
    private static List<Car> cars;

    private Connection buildConnection() throws SQLException {
        String databaseUrl = "localhost";
        String databasePort = "3306";
        String databaseName = "cars";
        String userName ="root";
        String password = "password";

        String connectionString = "jdbc:mysql://" + databaseUrl + ":"
                + databasePort + "/" + databaseName + "?"
                + "user=" + userName + "&"
                + "password=" + password + "&"
                + "useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(connectionString);
    }

    @Override
    public void readInData() throws DataException {
        try (Connection connection = buildConnection()) {
            if (connection.isValid(2)) {
                cars = new ArrayList<>();
                CallableStatement callableStatement = connection.prepareCall("CALL sp_get_all_Cars();");
                ResultSet resultSet = callableStatement.executeQuery();
                String licensePlate;
                String make;
                String model;
                int modelYear;
                while(resultSet.next()){
                    licensePlate =resultSet.getString("licensePlate");
                    make = resultSet.getString("make");
                    model = resultSet.getString("model");
                    modelYear = resultSet.getInt("modelYear");
                    cars.add(new Car(licensePlate, make, model, modelYear));
                }
                resultSet.close();
                callableStatement.close();
            }
        } catch(Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            if (e instanceof SQLException) {
                SQLException sqlException = (SQLException)e;
                System.out.println("Error Code: " + sqlException.getErrorCode());
                System.out.println("SQL State: " + sqlException.getSQLState());
            }
        }
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
        cars.add(car);
        try (Connection connection = buildConnection()) {
            CallableStatement callableStatement = connection.prepareCall("CALL sp_add_Car(?,?,?,?);");
            callableStatement.setString(1, car.getLicensePlate());
            callableStatement.setString(2, car.getMake());
            callableStatement.setString(3, car.getModel());
            callableStatement.setInt(4, car.getModelYear());
            callableStatement.execute();
        } catch(SQLException ex){
            throw new DataException(ex);
        }
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
