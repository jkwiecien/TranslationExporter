package com.aprilapps.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Jacek Kwiecień on 07.06.15.
 */
public abstract class Exporter {

    protected File resourcesFile;
    protected Scanner scanner = new Scanner(System.in);

    public void start() {
        askForResourcesFile();
        try {
            readResourcesFile();
        } catch (Exception e) {
            System.out.println("ERROR: Problem when reading resources csvFile: " + e.getMessage());
        }
    }

    public void askForResourcesFile() {
        try {
            System.out.println(getAskForResourcesFileText());
            String path = scanner.nextLine();
            resourcesFile = new File(path);
            if (!resourcesFile.exists()) throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found.");
            askForResourcesFile();
        }
    }

    public abstract void readResourcesFile();

    public abstract String getAskForResourcesFileText();

}
