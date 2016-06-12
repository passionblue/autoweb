package com.autosite.util;

import java.util.Calendar;

import org.apache.log4j.Logger;

public class JtCalendar {
    
    private static Logger m_logger = Logger.getLogger(JtCalendar.class);
    private static JtCalendar m_instance = new JtCalendar();

    
    public static JtCalendar getInstance() {
        return m_instance;
    }

    private JtCalendar(){
    
    }


    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal);
    }

}
