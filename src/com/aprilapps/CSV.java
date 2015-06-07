package com.aprilapps;

import org.apache.commons.csv.CSVFormat;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by Jacek Kwiecień on 07.06.15.
 */
public class CSV {

    public static final File HOME = new File(System.getProperty("user.home"));

    public static final char DELIMITER = ',';
    public static final CSVFormat FORMAT = CSVFormat.DEFAULT.withDelimiter(DELIMITER);

    public static final String HEADER_RES_NAME = "Resource name";
    public static final String HEADER_TEXT_TO_TRANSLATE = "Text to translate";
    public static final String HEADER_TRANSLATION = "Translation";

    public static final Object[] HEADER = {HEADER_RES_NAME, HEADER_TEXT_TO_TRANSLATE, HEADER_TRANSLATION};
    public static SimpleDateFormat FILE_NAME_DATE_FORMAT = new SimpleDateFormat("dd-M-yyyy_hh-mm-ss");
}
