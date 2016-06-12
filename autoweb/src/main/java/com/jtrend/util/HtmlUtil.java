package com.jtrend.util;

public class HtmlUtil {

    // To decide which dropdown menu should be selected.
    /* USAGE                        
    <select name="position_code">
    <option value="0" <%=HtmlUtil.getOptionSelect("0", type)%>>Choose position for the post</option>
    <option value="1" <%=HtmlUtil.getOptionSelect("1", type)%>>Header Right</option>
    <option value="2" <%=HtmlUtil.getOptionSelect("2", type)%>>Right Side</option>
    <option value="3" <%=HtmlUtil.getOptionSelect("3", type)%>>Left Side</option>
    <option value="4" <%=HtmlUtil.getOptionSelect("4", type)%>>Content Top</option>
    <option value="5" <%=HtmlUtil.getOptionSelect("5", type)%>>Content Bottom</option>
    <option value="6" <%=HtmlUtil.getOptionSelect("6", type)%>>Right Side for all</option>
    <option value="7" <%=HtmlUtil.getOptionSelect("7", type)%>>Left Side for all</option>
    </select> 
    */
    public static String getOptionSelect(String menuNum, int typeNum) {
        
        String ret = "";
        
        if (menuNum != null) {
            
            if (menuNum.equals(String.valueOf(typeNum))) {
                return " selected ";
            }
        }
        return ret;
    }

    public static String getOptionSelect(String menuNum, long num) {
        
        String ret = "";
        
        if (menuNum != null) {
            
            if (menuNum.equals(String.valueOf(num))) {
                return " selected ";
            }
        }
        return ret;
    }
    
    public static String getOptionSelect(long menuNum, long num) {
        
        String ret = "";
        if (menuNum == num) {
            return " selected ";
        }
        return ret;
    }

    
    
    
    public static String getOptionSelect(String option, String value) {
        
        String ret = "";
        
        if ( option == null || option.equals("")) {
            if ( value == null || value.equals(""))
                    return " selected ";
        }
        
        if (value != null) {
            
            if (value.equalsIgnoreCase(option)) {
                return " selected ";
            }
        }
        return ret;
    }
    
    public static String getCheckedBoxValue(String value){
        if (value == null ) return "";
        if (value.equalsIgnoreCase("on") ||value.equalsIgnoreCase("yes") ||value.equalsIgnoreCase("1") ||value.trim().equalsIgnoreCase("true") ){
            return "checked=\"on\"";
        }
        return "";
    }

    public static String getCheckedBoxValue(int value){
        if (value == 1) 
            return "checked=\"on\"";
        return "";
    }

    public static String getCheckedBoxValue(boolean value){
        if (value) 
            return "checked=\"on\"";
        return "";
    }
    
}
