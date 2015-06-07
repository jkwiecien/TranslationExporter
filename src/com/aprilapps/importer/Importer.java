package com.aprilapps.importer;

import com.aprilapps.CSV;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Jacek Kwiecień on 07.06.15.
 */
public abstract class Importer {

    protected File csvFile;
    protected File translatedResFile;
    protected Scanner scanner = new Scanner(System.in);

    public void start() {
        askForCSVFile();
        try {
            writeTranslatedResourcesFile();
        } catch (Exception e) {
            System.out.println("ERROR: Problem when reading resources csvFile: " + e.getMessage());
        }
    }

    public void askForCSVFile() {
        try {
            System.out.println("Enter the path to translated CSV file");
            String path = scanner.nextLine();
            csvFile = new File(path);
            if (!csvFile.exists()) throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found.");
            askForCSVFile();
        }
    }

    private boolean shouldAbortInsteadOverride() {
        if (translatedResFile.exists()) {
            System.out.println("File named " + getResourcesFileName() + " already exists.");
            System.out.println("1 - Overwrite it");
            System.out.println("0 - ABORT");

            int choice = scanner.nextInt();

            if (choice == 1) {
                translatedResFile.delete();
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void writeTranslatedResourcesFile() throws IOException {
        translatedResFile = new File(CSV.HOME, getResourcesFileName());

        if (shouldAbortInsteadOverride()) return;

        FileOutputStream fos = new FileOutputStream(translatedResFile);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


        Reader in = new FileReader(csvFile);

        bw.write(getHeader().toString());

        for (CSVRecord record : CSV.FORMAT.parse(in)) {

            if (record.get(1).equals(CSV.HEADER_TEXT_TO_TRANSLATE) && record.get(2).equals(CSV.HEADER_TRANSLATION)) {
                continue;
            }

            String key = record.get(0);
            String translation = record.get(2);

            bw.write(getLine(key, translation));
            bw.newLine();
        }

        bw.write(getFooter().toString());

        bw.close();

        System.out.println("SUCCESS: Created translated file under the path: " + translatedResFile.getPath());
    }

    public abstract String getResourcesFileName();

    public abstract StringBuilder getHeader();

    public abstract StringBuilder getFooter();

    public abstract String getLine(String key, String translation);
}
