package com.hauschildt.car.view;

import com.hauschildt.car.controller.CarDAO;

import java.util.ResourceBundle;
import java.util.Scanner;

public interface CarDataHandler {
    void handleTask(CarDAO dao, Scanner in, ResourceBundle messages);
}
