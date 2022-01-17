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

    public Car getCarByLicensePlate(String licensePlate) throws DataException {
        Car car = null;
        // Use this code to look up the car from the ArrayList after it has been populated
        verifyCarList();
        for (Car car1 : cars) {
            if(car1.getLicensePlate().equals(licensePlate)){
                car = car1;
                break;
            }
        }
        // Use this code if you want to directly look the car up from a database query
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_get_Car_by_License_Plate(?);");
            callableStatement.setString(1, licensePlate);

            ResultSet resultSet = callableStatement.executeQuery();
            String make;
            String model;
            int modelYear;
            if(resultSet.next()){
                make = resultSet.getString("make");
                model = resultSet.getString("model");
                modelYear = resultSet.getInt("modelYear");
                car = new Car(licensePlate, make, model, modelYear);
            }
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new DataException(ex);
        }
        return car;
    }

    @Override
    public List<Car> getAllCars() throws DataException {
        verifyCarList();
        return cars;
    }

    public void updateCar(Car original, Car updated) throws DataException {
        // Verify that the original car is in the ArrayList before updating it
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
        // Update the car in the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_update_Car(?,?,?,?,?);");
            callableStatement.setString(1, original.getLicensePlate());
            callableStatement.setString(2, updated.getLicensePlate());
            callableStatement.setString(3, updated.getMake());
            callableStatement.setString(4, updated.getModel());
            callableStatement.setInt(5, updated.getModelYear());

            callableStatement.execute();
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new DataException(ex);
        }
    }

    @Override
    public void deleteCar(Car car) throws DataException {
        deleteCar(car.getLicensePlate());
    }

    @Override
    public void deleteCar(String licensePlate) throws DataException {
        // Verify that the car is in the ArrayList before removing it
        verifyCarList();
        Car foundCar = null;
        for (Car car : cars) {
            if(car.getLicensePlate().equals(licensePlate)){
                foundCar = car;
                break;
            }
        }
        if(null == foundCar){
            throw new DataException("Record not found for delete.");
        }
        String licensePlateToDelete = foundCar.getLicensePlate();
        cars.remove(foundCar);
        // Delete the car from the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_delete_from_Car(?);");
            callableStatement.setString(1, licensePlateToDelete);
            callableStatement.execute();
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new DataException(ex);
        }
    }

}
