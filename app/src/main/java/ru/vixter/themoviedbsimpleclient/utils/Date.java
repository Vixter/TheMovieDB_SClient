package ru.vixter.themoviedbsimpleclient.utils;


import java.text.SimpleDateFormat;

/**
 * Created by winfe on 31.12.2015.
 */
public class Date {

    public final static String YEAR_MONTH_DAY = "yyyy'-'MM'-'dd";

    public static String DateToUTCString(java.util.Date date, String dataformat) {
        // TODO: 15.01.16 read about ThreadLocal
        final SimpleDateFormat fmt = new SimpleDateFormat(dataformat);
        return fmt.format(date);
    }

}