package com.hauschildt.car.view;

import com.hauschildt.DataException;
import com.hauschildt.Helpers;
import com.hauschildt.UIUtility;
import com.hauschildt.car.controller.CarDAO;
import com.hauschildt.car.model.Car;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UpdateCar implements CarDataHandler {
    @Override
    public void handleTask(CarDAO dao, Scanner in, ResourceBundle messages) {
        try {
            List<Car> cars = dao.getAllCars();
            if(cars != null) {
                String[] menuOptions = new String[cars.size()];
                for (int i = 0; i < menuOptions.length; i++) {
                    menuOptions[i] = (i + 1) + ") " + cars.get(i).toString();
                }
                int choice = UIUtility.showMenuOptions("Select a Car to Update",
                        "Your Choice:", menuOptions, in, messages);
                try {
                    int actualChoice = choice - 1;
                    Car car = cars.get(actualChoice);
                    updateCar(dao, car, in, messages);
                    System.out.println("Car updated successfully.");
                } catch (IndexOutOfBoundsException x) {
                    UIUtility.showErrorMessage("No valid Car selected.", in, messages);
                }
                System.out.println("\nUpdate a Car complete.");
            } else {
                System.out.println("There are no cars available.");
            }
        } catch (DataException e) {
            UIUtility.showErrorMessage(e.getMessage(), in, messages);
        }
    }

    private void updateCar(CarDAO dao, Car car, Scanner in, ResourceBundle messages) throws DataException {
        String current = " (Press Enter to keep the current value):";
        Car updated = new Car(car);
        System.out.println("Updating Car: " + car);
        System.out.println("License Plate: " + car.getLicensePlate());
        System.out.println("Current Make: " + car.getMake());
        String updatedMake = Helpers.getUserString("Enter the new make" + current, in);
        if (!updatedMake.isBlank()) {
            updated.setMake(updatedMake);
        }
        System.out.println("Current Model: " + car.getModel());
        String updatedModel = Helpers.getUserString("Enter the new model" + current, in);
        if (!updatedModel.isBlank()) {
            updated.setModel(updatedModel);
        }
        System.out.println("Current Model Year: " + car.getModelYear());
        int updatedModelYear = Helpers.getUserIntInRange("Enter the new model year" + current, Car.MINIMUM_MODEL_YEAR,
                Car.MAXIMUM_MODEL_YEAR, in, messages
        );
        updated.setModelYear(updatedModelYear);
        dao.updateCar(car, updated);
    }
}
