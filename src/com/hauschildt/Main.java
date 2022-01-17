package com.hauschildt;

import com.hauschildt.car.controller.CarDAO;
import com.hauschildt.car.controller.CarDAOFactory;
import com.hauschildt.car.view.*;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Language language = new Language();
        ResourceBundle messages = language.getMessages();
        CarDAO dao = CarDAOFactory.getCarDAO();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (true) {
            String menuTitle = "Main Menu";
            String prompt = "Select an option";
            String[] menuOptions = {
                    messages.getString("add-car"),
                    "Find a car",
                    "Show all cars",
                    "Update a car",
                    "Delete a car",
                    "Change language"
            };
            choice = UIUtility.showMenuOptions(menuTitle, prompt, menuOptions, scanner, messages);
            if (choice == 0)
                continue;
            if (choice == menuOptions.length + 1)
                break;
            UIUtility.showSectionTitle(menuOptions[Integer.valueOf(choice) - 1]);
            switch (choice) {
                case 1:
                    new AddCar().handleTask(dao, scanner, messages);
                    break;
                case 2:
                    new FindCar().handleTask(dao, scanner, messages);
                    break;
                case 3:
                    new ShowAllCars().handleTask(dao, scanner, messages);
                    break;
                case 4:
                    new UpdateCar().handleTask(dao, scanner, messages);
                    break;
                case 5:
                    new DeleteCar().handleTask(dao, scanner, messages);
                    break;
                case 6:
                    language.changeLanguage(scanner, messages);
                    messages = language.getMessages();
                    break;
            }
            UIUtility.pressEnterToContinue(scanner, messages);
        }
        System.out.println("\nProgram complete. Goodbye.\n");
        scanner.close();
    }
}
