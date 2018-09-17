package com.raye7task.utility;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtilities {
    //2018-09-13T21:25:30Z
    public static final String dateFullFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String timeHoursSeconds = "HH:mm:ss";
    public static final String dateFormat = "dd/MM/yyyy";

    public static long getLongDate(String stringDate, String inputFormat) {
        Date date = convertToDate(stringDate, inputFormat);
        return date.getTime();
    }

    public static String getFormattedDay(String stringDate, String inputFormat, String outputFormat) {
        Date date = convertToDate(stringDate, inputFormat);
        String formattedDay = getFormattedDay(date, outputFormat);
        return formattedDay;
    }

    public  static String getFormattedDay(Date date, String outputFormat) {
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
        String formattedDay = outputDateFormat.format(date);
        return formattedDay;
    }

    public static Date convertToDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        Date startDate = null;
        try {
            startDate = df.parse(date);
        } catch (ParseException e) {
            Log.d("convertToDate: ", e.toString());
        }
        return startDate;
    }
}
