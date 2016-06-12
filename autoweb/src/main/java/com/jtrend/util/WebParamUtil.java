package com.jtrend.util;

import java.sql.Timestamp;
import java.util.Date;

public class WebParamUtil {

    public static int getIntegerValue(String webParam) {
        return getIntValue(webParam, 0);
    }
    
    public static int getIntValue(String webParam) {
        return getIntValue(webParam, 0);
    }
    
    public static int getIntValue(boolean webParam) {
        return webParam? 1:0;
    }
    
    
    public static int getIntValue(String webParam, int defaultValue) {
        if (webParam == null) return defaultValue;
        
        int retValue = defaultValue;
        
        try {
            retValue = Integer.valueOf(webParam);
        }
        catch (NumberFormatException e) {
        }
        return retValue;
    }
    public static long getLongValue(String webParam) {
        return getLongValue(webParam, 0);
    }    
    public static long getLongValue(String webParam, long defaultValue) {
        if (webParam == null) return defaultValue;
        
        long retValue = defaultValue;
        
        try {
            retValue = Long.valueOf(webParam);
        }
        catch (NumberFormatException e) {
        }
        return retValue;
    }
    public static double getDoubleValue(String webParam) {
        return getDoubleValue(webParam, 0.0);
    }
    public static double getDoubleValue(String webParam, double defaultValue) {
        if (webParam == null) return defaultValue;
        
        double retValue = defaultValue;
        
        try {
            retValue = Double.valueOf(webParam);
        }
        catch (NumberFormatException e) {
        }
        return retValue;
    }
    
    
    public static String getStringValue(Object val) {
        if ( val == null) return null;
        return val.toString().trim();
    }

    public static String getStringValue(Object val, String defaultStr) {
        if ( val == null) return defaultStr;
        return val.toString().trim();
    }

    public static String getStringValue(int val) {
        return String.valueOf(val);
    }

    public static String getStringValue(long val) {
        return String.valueOf(val);
    }
    
    public static Timestamp getDateValue(Date val) {
        return new Timestamp(val.getTime());
    }
    public static Timestamp getDateValue(Date val, Timestamp defaultDate) {
        if ( val == null) return defaultDate;
        
        return new Timestamp(val.getTime());
    }
    
    public static Timestamp getDateValue(String val) {
        return new Timestamp(System.currentTimeMillis());
    }
    public static Timestamp getTimestampValue(Date val) {
        return new Timestamp(val.getTime());
    }
    
    public static Timestamp getTimestampValue(String val) {
        if ( val == null || val.trim().isEmpty()) return null;
        return new Timestamp(System.currentTimeMillis());
    }

    public static int getCheckboxValue(String val) {
        if (val != null&& ( val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("on"))){
            return 1;
        }
        return 0;
    }
    
    public static boolean getBooleanValue(int val){
        if (val == 1) return true;
        return false;
    }

    public static boolean getBooleanValue(String val){
        if (val != null && (val.equalsIgnoreCase("true") || val.equals("1") || val.equalsIgnoreCase("yes")  || val.equalsIgnoreCase("on") ))
            return true;
        return false;
    }

    
    
    
    public static  boolean isMissing(String val) {
        if ( val == null || val.trim().length() == 0) return true;
        return false;
     }

    public static  boolean isThere(String val) {
         if ( val == null || val.trim().length() == 0) return false;
         return true;
      }
     
    public static  boolean equalsTo(String val, int val2) {
         return equalsTo(val, ""+val2, false);
     }    
    public static  boolean equalsTo(String val, int val2, int val3) {
         if ( !equalsTo(val, val2) ) return false;
         if ( !equalsTo(val, val3) ) return false;
         return true;
     }    
     
    public static  boolean equalsTo(String val, String val2) {
         return equalsTo(val, val2, false);
     }

    public static  boolean equalsTo(String val, String val2, String val3, boolean ignoreCase) {
         if ( !equalsTo(val, val2, ignoreCase) ) return false;
         if ( !equalsTo(val, val3, ignoreCase) ) return false;
         return true;
     }
     
     
    public static  boolean equalsTo(String val, String val2, boolean ignoreCase) {
         
         if ( val == null || val2 == null) 
             return false;
         
         if (ignoreCase) {
             if ( val != null && val.trim().equalsIgnoreCase(val2)) {
                 return true;
             }
         } else {
             if ( val != null && val.trim().equals(val2)) {
                 return true;
             }
         }        
         return false;
     }

    
    
    
}
