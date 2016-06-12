package com.autosite.util.chur;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ChurUtil {
    
    public static String getWeekString(){

        Calendar cal = GregorianCalendar.getInstance();
        int thisYear = cal.get(Calendar.YEAR);
                
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int dayOffset = Calendar.SUNDAY - dayOfWeek;
        cal.add(Calendar.DATE, dayOffset );
        
        int afterOffsetYear = cal.get(Calendar.YEAR);
        
        if ( afterOffsetYear < thisYear) {
            cal.add(Calendar.DATE, 7);
        }
        
        DateFormat formatter = new SimpleDateFormat("MM/dd");
        
        return formatter.format(cal.getTime());
    }
    public static int getCurrentYear() {

        Calendar cal = GregorianCalendar.getInstance();
        return cal.get(Calendar.YEAR);
    }
    
    public static List getWeeksForYear(int year){
        
        
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(year, 0, 1);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int dayOffset = 8-dayOfWeek;
        if (dayOffset == 7)dayOffset=0;
        cal.add(Calendar.DATE, dayOffset );
        DateFormat formatter = new SimpleDateFormat("MM/dd");
        List ret = new ArrayList();
        
        while(true) {
            ret.add(formatter.format(cal.getTime()));
            cal.add(Calendar.DATE, 7 );
            int y = cal.get(Calendar.YEAR);
            if ( y != year) break;
        }
        
        return ret;
        
    }
    
}
