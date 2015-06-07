package com.aprilapps.exporter;

import com.aprilapps.CSV;
import javafx.util.Pair;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jacek Kwiecień on 05.06.15.
 */
public abstract class LineParser {

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
            String fileName = CSV.FILE_NAME_DATE_FORMAT.format(new Date()) + ".csv";
            File csvFile = new File(CSV.HOME, fileName);
            csvFile.createNewFile();

            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(csvFile), Charset.forName("UTF-8").newEncoder());

            CSVPrinter printer = new CSVPrinter(output, CSV.FORMAT);
            printer.printRecord(CSV.HEADER);

            for (Pair pair : pairs) {
                printer.printRecord(pair.getKey(), pair.getValue(), "");
            }

            output.flush();
            output.close();
            printer.close();

            System.out.println("SUCCESS: Created CSV file under the path: " + csvFile.getPath());
        } catch (Exception e) {
            System.out.println("ERROR: Problem when writing CSV: " + e.getMessage());
        }
    }

}
