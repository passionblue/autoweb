/*
 * Created on Nov 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.struts.core.viewproc;

import java.util.HashMap;
import java.util.Map;


public class ViewProcFactory {

    private Map m_procs = new HashMap();
    private static ViewProcFactory m_instance = new ViewProcFactory();
    
    public static ViewProcFactory getInstance() {
        return m_instance;
    }

    public ViewProc getViewProc(String pageAlias) {
        
        return (ViewProc) m_procs.get(pageAlias);
    }
    
    public void registerViewProc (String pageAlias, ViewProc proc) {
        m_procs.put(pageAlias, proc);
    }
    
    
    
    
}
