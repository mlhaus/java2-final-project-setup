package com.hauschildt.car.view;

import com.hauschildt.DataException;
import com.hauschildt.UIUtility;
import com.hauschildt.car.controller.CarDAO;
import com.hauschildt.car.model.Car;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class DeleteCar implements CarDataHandler {
    @Override
    public void handleTask(CarDAO dao, Scanner in, ResourceBundle messages) {
        try {
            List<Car> cars = dao.getAllCars();
            if(cars != null) {
                String[] menuOptions = new String[cars.size()];
                for (int i = 0; i < menuOptions.length; i++) {
                    menuOptions[i] = (i + 1) + ") " + cars.get(i).toString();
                }
                int choice = UIUtility.showMenuOptions("Select a Car to Delete",
                        "Your Choice:", menuOptions, in, messages);
                try {
                    int actualChoice = choice - 1;
                    Car car = cars.get(actualChoice);
                    dao.deleteCar(car);
                    System.out.println("\nDelete a Car complete.");
                } catch (IndexOutOfBoundsException | DataException e) {
                    UIUtility.showErrorMessage("No valid Car selected.", in, messages);
                }
            } else {
                System.out.println("There are no cars available.");
            }
        } catch (DataException e) {
            UIUtility.showErrorMessage(e.getMessage(), in, messages);
        }
    }
}
