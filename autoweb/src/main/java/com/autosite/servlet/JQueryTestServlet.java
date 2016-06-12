package com.autosite.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jtrend.util.WaitUtil;
import com.jtrend.util.WebParamUtil;

public class JQueryTestServlet extends AbstractAutositeServlet {
 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        run(request, response);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        run(request, response);
    }

    public void run(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        printHeaders(request);
        m_logger.debug("#JQueryTestServlet");
        
        long pause = WebParamUtil.getLongValue(request.getParameter("pause"));
        
        
        if (!isMissing(request.getParameter("data"))){
            response.setContentType("text");
            PrintWriter out = response.getWriter();
            out.println("It is not clear that Mr. Reid has the 60 votes he would need just to brin");
            WaitUtil.waitFor(pause);
            out.println("It is not clear that Mr. Reid has the 60 votes he would ");
            m_logger.debug("data returned................. ");
            return;
        } else if (!isMissing(request.getParameter("html"))){
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();

            WaitUtil.waitFor(pause);
            out.println("<table class=\"mytable\" > <tr> <td>tehis sij </td> <td>This is test</td></tr> </table>");
            m_logger.debug("data returned................. ");
            return;
        } else if (!isMissing(request.getParameter("random"))){
            response.setContentType("text");
            PrintWriter out = response.getWriter();
            long rand = System.nanoTime();
            out.println("" + rand);
            WaitUtil.waitFor(pause);
            m_logger.debug("random returned................. ");
            return;
        }

        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        
        out.println("<result>ttttt</result>") ;
    }
    
    private static Logger m_logger = Logger.getLogger(JQueryTestServlet.class);
}
