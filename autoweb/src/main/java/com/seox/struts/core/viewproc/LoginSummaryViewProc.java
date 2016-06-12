/*
 * Created on Nov 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.struts.core.viewproc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
import com.jtrend.struts.core.viewproc.ViewProcFactory;

public class LoginSummaryViewProc implements ViewProc {

    public String getName() {
        return "LoginSummaryViewProc";
    }

    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception {
        
        ViewProcFactory.getInstance().getViewProc("keywords").process(request, session, false);
        ViewProcFactory.getInstance().getViewProc("domain_manage").process(request, session, false);
        
    }
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
}
