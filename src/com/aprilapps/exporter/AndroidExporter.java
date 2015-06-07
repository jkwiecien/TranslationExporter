package com.aprilapps.exporter;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Jacek Kwiecień on 05.06.15.
 */
public class AndroidExporter extends Exporter {

    @Override
    public void readResourcesFile() {
        try (Scanner scanner = new Scanner(resourcesFile)) {

            AndroidLineParser lineParser = new AndroidLineParser();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineParser.parseLine(line);
            }
            scanner.close();
            lineParser.onParsingFinished();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAskForResourcesFileText() {
        return "Enter the path to you resources XML resourcesFile.";
    }

}
