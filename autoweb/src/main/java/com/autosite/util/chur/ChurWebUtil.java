package com.autosite.util.chur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtrend.util.WebParamUtil;

public class ChurWebUtil {
    
    private static Logger m_logger = LoggerFactory.getLogger(ChurWebUtil.class);
    
    public static int getSelectedYear(HttpServletRequest request){
        HttpSession session = request.getSession();
        int year = WebParamUtil.getIntValue((String)session.getAttribute("churapp.year"), 0);
        if (year == 0 ) {
            int curYear =  WebParamUtil.getIntValue((String) request.getParameter("year"), ChurUtil.getCurrentYear());
            session.setAttribute("churapp.year", ""+ curYear);
            m_logger.debug("Set year to " + curYear);
            return curYear;
        }
        else
            return year;
    }

    public static String getSelectedWeek(HttpServletRequest request){
        HttpSession session = request.getSession();
        String week = WebParamUtil.getStringValue((String)session.getAttribute("churapp.week"));
        if (week == null ) 
            return WebParamUtil.getStringValue((String) request.getParameter("week"), ChurUtil.getWeekString());
        else
            return week;
    }
    
}
