package com.autosite.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class SeoxCalendarMap {

    protected Map m_map = new ConcurrentHashMap();
    
    public SeoxCalendarMap(){
    }


    public void put(int year, int month, int day, Object obj){
        String key = makeKey(year, month, day);
        m_map.put(key, obj);
        
    }
    public void put(String year, String month, String day, Object obj){
        String key = makeKey(year, month, day);
        m_map.put(key, obj);
    }

    public Object get(int year, int month, int day){
        String key = makeKey(year, month, day);
        return m_map.get(key);
    }
            
    public Object get(String year, String month, String day){
        String key = makeKey(year, month, day);
        return m_map.get(key);
    }
    
    public String makeKey(int year, int month, int day){
        return year+"-"+month+"-" + day;
    }
    
    public String makeKey(String year, String month, String day){
        
        int y;
        int m;
        int d;
        try {
            y = Integer.parseInt(year);
            m = Integer.parseInt(month);
            d = Integer.parseInt(day);
            return makeKey(y, m, d);
        }
        catch (NumberFormatException e) {
            m_logger.error(e.getMessage(),e);
        }
        
        return "XXXXXX";
    }
    private static Logger m_logger = Logger.getLogger(SeoxCalendarMap.class);
}
