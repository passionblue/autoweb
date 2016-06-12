/*
 * Created on Nov 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.struts.core.viewproc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jtrend.session.PageView;

public interface ViewProc {
    
    public String getName();
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception;
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception;

}
