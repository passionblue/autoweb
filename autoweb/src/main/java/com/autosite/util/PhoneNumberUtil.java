package com.autosite.util;

public class PhoneNumberUtil {
    
    static public boolean isValidFormat(String phoneNumber) {
        String formatted = convertToNumberOnly(phoneNumber);
        
        if ( formatted == null) 
            return false;

        if (formatted.length() == 10 )
            return true;
        
        if ( formatted.length() == 11 && formatted.charAt(0) == '1')
            return true;
        
        return false;
    }
    
    static public String convertToStandard(String phoneNumber) {
        if ( phoneNumber == null) return null;
        
        String formatted = phoneNumber.replaceAll("[\\+\\(\\, replacement)\\-= ]|[a-zA-Z]", "");
        
        if ( formatted.length() != 10 && formatted.length() != 11)
            return phoneNumber;
        
        if ( formatted.length() == 11) {
            formatted = formatted.substring(1);
        }
                    
        formatted = formatted.substring(0, 3) + "-" +formatted.substring(3, 6) + "-" + formatted.substring(6, 10);
        
        return formatted;
    }
    
    static public String convertToNumberOnly(String phoneNumber) {
        
        if ( phoneNumber == null) return null;
        
        String formatted = phoneNumber.replaceAll("[\\+\\(\\, replacement)\\-= ]|[a-zA-Z]", "");
        
        if ( formatted.length() != 10 && formatted.length() != 11)
            return phoneNumber;
        
        if ( formatted.length() == 11) {
            formatted = formatted.substring(1);
        }
        
        return formatted;
    }
    
    
    public static void main(String[] args) {
        
        System.out.println(convertToNumberOnly("1-234-234-3434 "));
        System.out.println(convertToNumberOnly("1-234-234-34a34 "));
        System.out.println(convertToNumberOnly("1-234-234-3434b "));
        System.out.println(convertToNumberOnly("1-234-234-3434() "));
        
        System.out.println(convertToStandard("1-234-234-3434 "));
        System.out.println(convertToStandard("1-234-234-34a34 "));
        System.out.println(convertToStandard("1-234-234-3434b "));
        System.out.println(convertToStandard("1-234-234-3434() "));
        
    }
}
