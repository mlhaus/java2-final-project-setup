package com.hauschildt;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Language language = new Language();
        ResourceBundle messages = language.getMessages();
        CarDAO dao = CarDAOFactory.getCarDAO();
        Scanner scanner = new Scanner(System.in);
        String menuTitle = "Main Menu";
        String prompt = "Select an option";
        String[] menuOptions = {
                "Add a Car",
                "Find a car",
                "Show all cars",
                "Update a car",
                "Delete a car",
                "Change language",
                "Change data source"
        };
        int choice = 0;
        while (true) {
            choice = UIUtility.showMenuOptions(menuTitle, prompt, menuOptions, scanner, messages);
            if (choice == 0)
                continue;
            if (choice == menuOptions.length + 1)
                break;
            UIUtility.showSectionTitle(menuOptions[Integer.valueOf(choice) - 1]);
            switch (choice) {
                case 1:
                    
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
            }
            UIUtility.pressEnterToContinue(scanner, messages);
        }
        System.out.println("\nProgram complete. Goodbye.\n");
        scanner.close();
    }
}
