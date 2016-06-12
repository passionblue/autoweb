package com.autosite.util.web;

public class DropMenuItem {

    private String m_menuName;
    private String m_display;
    private String m_value;
    private String m_dataType;
    private String m_compareValue; // For default item when DB does not contain value in edit page
    
    public DropMenuItem(String menuName, String display, String dataType, String value, String compareValue){
        m_menuName = menuName;
        m_display = display;
        m_value = value;
        m_dataType = dataType;
        m_compareValue = compareValue;
    }

    public String getDisplay() {
        return m_display;
    }

    public void setDisplay(String display) {
        m_display = display;
    }

    public String getValue() {
        return m_value;
    }

    public void setValue(String value) {
        m_value = value;
    }

    public String getDataType() {
        return m_dataType;
    }

    public void setDataType(String dataType) {
        m_dataType = dataType;
    }
    
    public String getMenuName(){
        return m_menuName;
        
    }
    
    
    public String getCompareValue() {
        return m_compareValue;
    }

    public void setCompareValue(String compareValue) {
        m_compareValue = compareValue;
    }

    public void setMenuName(String menuName) {
        m_menuName = menuName;
    }

    public String toString(){
        return m_menuName + ":" + m_display + "-" + m_value + "(" + m_compareValue + ")";
    }
    
    
}
