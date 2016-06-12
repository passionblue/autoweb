package com.autosite.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class OneWayServlet extends AbstractAutositeServlet {
 
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        printHeaders(request);
        
        m_logger.debug("#####################################");
    }

    private static Logger m_logger = Logger.getLogger(OneWayServlet.class);
}
