/*
 * Created on Nov 8, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools;

public class CalendarUtil {

    public static int getWeekNum(){
        int d = getDayNum();
        return (d-2)/7;
    }
    public static int getDayNum(){
        long l = System.currentTimeMillis()/86400000;
        return (int)l;
    }
    
    public static int getCurTimeNum(boolean weekly) {
        if (weekly) return getWeekNum();
        return getDayNum();
    }
    
    
}
