package com.aprilapps.exporter;

import javafx.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jacek Kwiecień on 05.06.15.
 */
public class AndroidLineParser extends LineParser {

    private static final String NAME_REGEX = "(?<=string name=\")(.*)(?=\">)";
    private static final String VALUE_REGEX = "(?<=>)(.*)(?=</)";
    private static final String INJECT_STRING_REGEX = "%\\d+\\$[s]";
    private static final String INJECT_INTEGER_REGEX = "%\\d+\\$[d]";
    private static final String INJECT_FLOAT_REGEX = "%\\d+\\$[f]";

    @Override
    public void parseLine(String line) {
        Pattern namePattern = Pattern.compile(NAME_REGEX);
        Matcher nameMatcher = namePattern.matcher(line);

        Pattern valuePattern = Pattern.compile(VALUE_REGEX);
        Matcher valueMatcher = valuePattern.matcher(line);

        while (nameMatcher.find() && valueMatcher.find()) {
            String name = nameMatcher.group(0);
            String value = valueMatcher.group(0);

            value = value.trim();
            value = value.replaceAll(INJECT_STRING_REGEX, "{s}");
            value = value.replaceAll(INJECT_INTEGER_REGEX, "{d}");
            value = value.replaceAll(INJECT_FLOAT_REGEX, "{f}");
            value = value.replace("\\n", "{l}");
            value = value.replace("\\'", "'");

            Pair<String, String> pair = new Pair<>(name, value);

            pairs.add(pair);
        }
    }
}
