package ru.vixter.themoviedbsimpleclient.utils;


import java.text.SimpleDateFormat;

/**
 * Created by winfe on 31.12.2015.
 */
public class Date {

    public final static String YEAR_MONTH_DAY = "yyyy'-'MM'-'dd";

    private static final ThreadLocal<SimpleDateFormat> simpleDateFormater = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat(Date.YEAR_MONTH_DAY);
        }
    };

    public String formatData(Date date)
    {
        return simpleDateFormater.get().format(date);
    }

}