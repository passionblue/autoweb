package com.jtrend.stats;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;

import com.jtrend.util.RequestUtil;

public class StatUtil {

    public static StatData populate(HttpServletRequest request, HttpServletResponse response, String paramStr, Action action) {
        StatData    statData              = new StatData();
        HttpSession session               = request.getSession();
        
      //Initialize Stat Object
        statData.setRequestUrl(request.getRequestURL().toString());
        statData.setRemoteAddress(request.getRemoteAddr());
        statData.setRequestUri(request.getRequestURI());
        statData.setQueryString(request.getQueryString());
        statData.setServerName(request.getServerName());
        statData.setServletPath(request.getServletPath());
        statData.setSessionId(session.getId());
        statData.setSessionId(session.getId());
        statData.setRemoteHost(request.getRemoteHost());

        statData.setActionName(action.getClass().getName());
        statData.setParamString(paramStr);
        statData.setMethod(request.getMethod());
        
        String referer = request.getHeader("refer");
        String userAgent = request.getHeader("user-agent");
        String headerHost = request.getHeader("host");
        
        statData.setReferer(referer);
        statData.setHeaderHost(headerHost);
        statData.setParamKeys(RequestUtil.getParameterKeysString(request));
        statData.setUserAgent(userAgent);
        
        return statData;
    }
}
