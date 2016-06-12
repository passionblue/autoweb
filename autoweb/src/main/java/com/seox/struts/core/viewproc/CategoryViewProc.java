/*
 * Created on Nov 25, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.struts.core.viewproc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;

public class CategoryViewProc implements ViewProc {

    
    public CategoryViewProc() {
        
    }
  
    public String getName() {
        return "CategoryViewProc";
    }
    
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) {
    }
    
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
    
}
