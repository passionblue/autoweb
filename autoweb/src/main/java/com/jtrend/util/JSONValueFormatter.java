package com.jtrend.util;

import java.util.Date;

public class JSONValueFormatter {

    
    public static String toStringValue(String value) {
        return value;
    }
    
    public static String toStringValue(long value) {
        return ""+value;
    }

    public static String toStringValue(int value) {
        return ""+value;
    }

    public static String toStringValue(double value) {
        return ""+value;
    }

    public static String toStringValue(Date value) {
        return TimeFormatUtil.convertFromTime(value);
    }

    
    public static String toJsonTypeValue(String value) {
        return value;
    }
    
    public static Long toJsonTypeValue(long value) {
        return new Long(value);
    }

    public static Integer toJsonTypeValue(int value) {
        return new Integer(value);
    }

    public static Double toJsonTypeValue(double value) {
        return new Double(value);
    }
    
    public static String toJsonTypeValue(Date value) {
        return TimeFormatUtil.convertFromTime(value);
    }

    
    
    
}
