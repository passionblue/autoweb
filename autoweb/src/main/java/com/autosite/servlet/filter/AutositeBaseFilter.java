package com.autosite.servlet.filter;

import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AutositeBaseFilter implements Filter {

    private static Logger m_logger = Logger.getLogger(AutositeBaseFilter.class);
    
    public void init(FilterConfig config) throws ServletException {
        m_logger.info("Filter " + getClass().getName() + " initiated");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        
        // Get the IP address of client machine.
        String ipAddress = request.getRemoteAddr();

        // Log the IP address and current timestamp.
        System.out.println("IP " + ipAddress + ", Time " + new Date().toString());

        // Pass request back down the filter chain
        
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
