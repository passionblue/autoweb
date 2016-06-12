/*
 * Created on Nov 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.work;

import org.apache.log4j.Logger;

import com.jtrend.struts.core.DefaultViewManager;
import com.seox.util.SeoxLogger;

public class AppInitiator {

    private static Logger m_logger = Logger.getLogger(AppInitiator.class);
    private static boolean m_init = false;
    
    public synchronized static void init() throws Exception {
        if (m_init) return;
        m_logger.info("## Initialize sysmte DS ##");
        UserDS.getInstance().loadFromDB(); 
        KeywordDS.getInstance().loadFromDB(); 
        CategoryDS.getInstance().loadFromDB();
        NoteDS.getInstance().loadFromDB();
        //todo
        DefaultViewManager.getInstance().initPageView();
        SeoxLogger.setLogDirectory("c:/seox/logs");
        m_init = true;
    }
}
