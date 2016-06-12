package com.jtrend.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatUtil {
    
    private static String formatFormat = "yyyy-MM-dd HH:mm:ss"; 
    
    public static String getFormat(){
        return formatFormat;
    }

    public static void setFormat(String format){
        formatFormat = format;
    }
    
    public static String convertFromTime(Date date){
        DateFormat dateFormatter = new SimpleDateFormat(formatFormat); 

        if ( date == null)
            return "";
        
        return dateFormatter.format(date);
    }

    public static Date convertToTime(String dateTimeString ){

        DateFormat dateFormatter = new SimpleDateFormat(formatFormat); 

        if ( dateTimeString == null)
            return null;
        
        try {
            return dateFormatter.parse(dateTimeString);
        }
        catch (ParseException e) {
            return new TimeNow();
        }
    }    
}
