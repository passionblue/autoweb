package com.jtrend.struts.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jtrend.struts.core.model.ActionInsert;

public class DefaultActionInsert implements ActionInsert{

    public void executeBegining(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#DefaultActionInsert.executeBegining()");
    }

    public void executePostExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#DefaultActionInsert.executePostExecute()");
    }

    public void executePreExecute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#DefaultActionInsert.executePreExecute()");
    }

    public void executeEnding(HttpServletRequest request, HttpServletResponse response) throws Exception {
        m_logger.debug("#DefaultActionInsert.executeEnding()");
    }

    public boolean abort(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return false;
    }

    private static Logger m_logger = Logger.getLogger(DefaultActionInsert.class);

}
