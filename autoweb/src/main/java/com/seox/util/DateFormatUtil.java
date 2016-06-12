/*
 * Created on Nov 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatUtil {

    private static SimpleDateFormat m_dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat m_dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    public static String getDateStr(Date date) {
        return m_dateFormat.format(date);
    }

    public static String getDateTimeStr(Date date) {
        m_dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
        return m_dateTimeFormat.format(date);
    }
    
}
