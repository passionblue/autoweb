package com.jtrend.struts.core.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionInsert {
    public void executeBegining(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public void executePostExecute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public void executePreExecute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public void executeEnding(HttpServletRequest request, HttpServletResponse response) throws Exception;
    public boolean abort(HttpServletRequest request, HttpServletResponse response);
}
