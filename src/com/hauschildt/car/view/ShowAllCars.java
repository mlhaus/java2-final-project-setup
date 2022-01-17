package com.hauschildt.car.view;

import com.hauschildt.DataException;
import com.hauschildt.UIUtility;
import com.hauschildt.car.controller.CarDAO;
import com.hauschildt.car.model.Car;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ShowAllCars implements CarDataHandler {
    @Override
    public void handleTask(CarDAO dao, Scanner in, ResourceBundle messages) {
        try {
            List<Car> cars = dao.getAllCars();
            if(cars != null) {
                System.out.println("License Plate\tMake\tModel\tModelYear");
                for (Car car : cars) {
                    System.out.println(car.getLicensePlate() + "\t\t" + car.getMake() + "\t"
                            + car.getModel() + "\n" + car.getModelYear());
                }
                System.out.println("\nShow All Cars complete.");
            } else {
                System.out.println("There are no cars available.");
            }
        } catch (DataException e) {
            UIUtility.showErrorMessage(e.getMessage(), in, messages);
        }
    }
}
