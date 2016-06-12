package com.jtrend.util;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimeNow extends Timestamp{

    protected int m_year;
    protected int m_month;
    protected int m_day;
    protected int m_dayOfYear;
    
    public TimeNow(){
        super(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        m_year = cal.get(Calendar.YEAR);
        m_month = cal.get(Calendar.MONTH);
        m_day = cal.get(Calendar.DAY_OF_MONTH);
        m_dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
    }

    public int getYear() {
        return m_year;
    }

    public int getMonth() {
        return m_month;
    }

    public int getDay() {
        return m_day;
    }

    public int getDayOfYear() {
        return m_dayOfYear;
    }

    
}
