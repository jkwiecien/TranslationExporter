package com.aprilapps;


import com.aprilapps.exporter.AndroidExporter;

import java.util.Scanner;

/**
 * Created by jacekkwiecien on 05.06.15.
 */
public class App {

    private Scanner scanner = new Scanner(System.in);
    private OperationType operation;

    public void start() {
        askForOperation();

        switch (operation) {
            case ANDROID_XML_TO_CSV:
                new AndroidExporter().start();
                break;
        }
    }

    private void showMainMenu() {
        System.out.println("Chose an operation:");
        System.out.println("1 - XML to CSV");
        System.out.println("2 - CSV to XML");
    }

    private void askForOperation() {
        try {
            showMainMenu();
            int pickedPosition = scanner.nextInt() - 1;
            operation = OperationType.values()[pickedPosition];
        } catch (Exception e) {
            System.out.println("\nYou need to pick a number corresponding to the operation you want to perform.\n");
            scanner.nextLine();
            askForOperation();
        }
    }

}
