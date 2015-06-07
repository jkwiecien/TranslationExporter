package com.aprilapps.importer;

import com.aprilapps.Tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jacek Kwiecień on 07.06.15.
 */
public class AndroidImporter extends Importer {

    public static final String TAG_PATTERN = "\\{[dsf]\\}";

    @Override
    public String getResourcesFileName() {
        return "strings.xml";
    }

    @Override
    public StringBuilder getHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!-- Created with TranslationExported from https://github.com/jkwiecien/TranslationExporter -->");
        sb.append('\n');
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append('\n');
        sb.append("<resources>");
        sb.append('\n');
        return sb;
    }

    @Override
    public StringBuilder getFooter() {
        StringBuilder sb = new StringBuilder();
        sb.append("</resources>");
        sb.append('\n');
        return sb;
    }

    @Override
    public String getLine(String key, String translation) {

        String value = translation.replace("'", "\\'");

        Matcher tagsMatcher = Pattern.compile(TAG_PATTERN).matcher(value);

        int i = 0;
        while (tagsMatcher.find()) {
            String tag = tagsMatcher.group(0);

            switch (tag) {
                case Tags.FLOAT_VARIABLE:
                    value = value.replaceFirst(TAG_PATTERN, "\\%" + (i + 1) + "\\$f");
                    break;
                case Tags.INTEGER_VARIABLE:
                    value = value.replaceFirst(TAG_PATTERN, "\\%" + (i + 1) + "\\$d");
                    break;
                case Tags.STRING_VARIABLE:
                    value = value.replaceFirst(TAG_PATTERN, "\\%" + (i + 1) + "\\$s");
                    break;
            }

            i++;
        }

        value = value.replace("{l}", "\n");

        System.out.println(value);

        return String.format("<string name=\"%s\">%s</string>", key, value);
    }
}
