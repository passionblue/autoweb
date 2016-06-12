package com.jtrend.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JtStringUtil {

    // convert delimieterd to set
    public static Set convertToSet(String str){
        return convertToSet(str, ",");
    }
    public static Set convertToSet(String str, String delimiter){
        
        Set ret = new HashSet();
        
        if (str == null) return ret;
        
        if (delimiter == null || delimiter.isEmpty()){
            delimiter = ",";
        }
        
        
        String parts[] = str.split(",");
        
        for(int i = 0; i < parts.length; i++){
            if (parts[i] != null && !parts[i].trim().equals(""))
                ret.add(parts[i]);
        }
        
        return ret;
    }
    
    public static String valueOf(String val) {
        if (val == null) return "";
        return val.trim();
    }

    public static String valueOf(int val) {
        return String.valueOf(val);
    }
   
    public static String valueOf(double val) {
        return String.valueOf(val);
    }

    public static String valueOf(Date val) {
        return String.valueOf(val); //TODO have to format somehow
    }

    public static String valueOf(Date val, String format) {
        if ( val == null) return "";
        return new SimpleDateFormat(format).format(val);
    }

    public static String valueOf(Object val) {
        if ( val == null) return "";
        return val.toString();
    }
    
}
