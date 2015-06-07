package com.aprilapps.exporter;

import javafx.util.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jacek Kwiecień on 05.06.15.
 */
public abstract class LineParser {

    public static SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss");
    private static final char DELIMITER = '\t';
    private static final Object[] HEADER = {"Resource name", "Text to translate", "Translation"};

    public class Line {
        public String resourceName;
        public String resourceValue;
    }


    protected List<Pair> pairs;

    protected LineParser() {
        this.pairs = new ArrayList<>();
    }

    public abstract void parseLine(String line);

    public void onParsingFinished() {
        try {
            File directory = new File(System.getProperty("user.home"));

            String fileName = fileNameDateFormat.format(new Date()) + ".csv";
            File csvFile = new File(directory, fileName);

            csvFile.createNewFile();

            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter(DELIMITER);

            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(csvFile), Charset.forName("UTF-8").newEncoder());

            CSVPrinter printer = new CSVPrinter(output, csvFileFormat);
            printer.printRecord(HEADER);

            for (Pair pair : pairs) {
                printer.printRecord(pair.getKey(), pair.getValue(), "");
            }

            output.flush();
            output.close();
            printer.close();

            System.out.println("SUCCESS: Utworzono plik CSV w lokalizacji: " + csvFile.getPath());
        } catch (Exception e) {
            System.out.println("ERROR: Problem when writing CSV: " + e.getMessage());
        }
    }

}
