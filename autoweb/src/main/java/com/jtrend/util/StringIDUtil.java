package com.jtrend.util;

public class StringIDUtil {
    
    public static String getSystemTimeStringId(){
        long time = System.currentTimeMillis();
        return Long.toString(time, 36).toUpperCase();
    }
}
