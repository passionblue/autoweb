package com.jtrend.struts.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public interface JTrendActionInterface {
    void initApp();

    ActionForward ex    (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response);
    Map           exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response);
    
    
}
