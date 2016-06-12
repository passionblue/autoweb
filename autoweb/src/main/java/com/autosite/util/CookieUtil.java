package com.autosite.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    
    public static String getRpcId(HttpServletRequest request){
        return getCookieValue(request.getCookies(), "RPCI", null);
    }
    
    public static String getCookieValue(HttpServletRequest request, String name, String value) {
        return getCookieValue(request.getCookies(), name, value);
    }
    
    public static String getCookieValue(Cookie[] cookies, String name, String value)
    {
        if (cookies == null || name == null)
            return value;
        for (int i = 0; i < cookies.length; i++)
        {
            Cookie cookie = cookies[i];
            if (cookie.getName().equals(name))
            {
                return cookie.getValue();
            }
        }

        return value;
    }
}
