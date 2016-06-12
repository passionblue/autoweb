package com.autosite.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    protected static DateFormat m_dateFormatter;
    protected static DateFormat m_dateTimeFormatter;

    static{
        m_dateFormatter = new SimpleDateFormat("MM/dd/yyyy"); 
        m_dateTimeFormatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
    }
    
    public static DateFormat getDateFormatter(){
        return new SimpleDateFormat("MM/dd/yyyy");
    }
    
    public static DateFormat getDateTimeFormatter(){
        return new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
    }
    
    public static synchronized String dateFormat(Date date){
        if (date == null) 
            return m_dateFormatter.format(new Date());
        return m_dateFormatter.format(date);
    }

    public static synchronized String dateTimeFormat(Date date){
        if (date == null) 
            return m_dateTimeFormatter.format(new Date());
        return m_dateTimeFormatter.format(date);
    }
}
