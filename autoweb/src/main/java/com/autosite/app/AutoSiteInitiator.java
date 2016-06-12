package com.autosite.app;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.autosite.concur2.AutositeEventHandlerManager;
import com.autosite.ds.ContentDS;
import com.autosite.ds.LinkStyleConfigDS;
import com.autosite.ds.PageDS;
import com.autosite.ds.PanelDS;
import com.autosite.ds.PanelLinkStyleDS;
import com.autosite.ds.PanelStyleDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;
import com.autosite.ds.SitePostDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.lab.mongo.CustomerRepository;
import com.autosite.repository.mongo.AutositeMongoRepositoryFactory;
import com.autosite.session.devicesynch.AutositeLedgerSynchTrackingManager;
import com.autosite.util.DefaultActionMapper;
import com.autosite.util.DynMenuManager;
import com.autosite.util.LinkToUtil;
import com.autosite.util.access.AccessConfigManager3;
import com.autosite.util.chur.ChurReportEngine;
import com.autosite.util.poll.PollResult;
import com.jtrend.struts.core.DefaultPageForwardManager;
import com.jtrend.struts.core.model.ApplicationInitiator;

public class AutoSiteInitiator implements ApplicationInitiator {

    private boolean m_runOnce;

    
    public synchronized void init() {
        
//            RepositoryFactory factory = RepositoryFactory.getInstance();
//            MongoRepository r = factory.getRepository();
        
            if ( m_runOnce ) return;
            m_runOnce = true;
            
            m_logger.debug("AutoSiteInitiator.initApp()");

            //SurveyGenViewManager.getInstance().initPageView();
            //ContentSilo.getInstance();
            try {
                
                
                m_logger.debug("###############################################################################################");
                m_logger.debug("Classpath: '" + System.getProperty( "java.class.path" ) + "'" );
                m_logger.debug("Ext dirs: '" + System.getProperty( "java.ext.dirs" ) + "'" );
                m_logger.debug("Library path: '" + System.getProperty( "java.library.path" ) + "'" );
                m_logger.debug("Path separator: '" + System.getProperty( "path.separator" ) + "'" )     ;   
                
                m_logger.debug("###############################################################################################");                
                
                Enumeration ee = System.getProperties().propertyNames();;
                while(ee.hasMoreElements()){
                    String k = (String)ee.nextElement();
                    m_logger.debug("SYSTEM PROP: " + k + ":----" + System.getProperty(k));
                }
                
                for (Iterator iterator = System.getenv().keySet().iterator(); iterator.hasNext();) {  
                    String k = (String) iterator.next();
                    m_logger.debug("SYSTEM ENV: " + k + ":----" + System.getenv(k));
                }
                
                SiteDS.getInstance();
                SiteConfigDS.getInstance();
                PageDS.getInstance();
                PanelDS.getInstance();
                ContentDS.getInstance();
                StyleConfigDS.getInstance();
                LinkStyleConfigDS.getInstance();
                SitePostDS.getInstance();
                PanelStyleDS.getInstance();
                PanelLinkStyleDS.getInstance();
                AutositeLedgerSynchTrackingManager.getInstance();
                
                m_logger.info("=========================================================================================================== DynMenuManager");
                DynMenuManager.getInstance();
                
                m_logger.info("=========================================================================================================== LinkToUtil");
                LinkToUtil.findLinkTo("");
                m_logger.info("===========================================================================================================");
                ChurReportEngine.getInstance();
                
                m_logger.info("===========================================================================================================");
                PollResult.loadAll();
                m_logger.info("=========================================================================================================== DefaultPageForwardManager");
                DefaultPageForwardManager.getInstance();
                AccessConfigManager3.registerInstanceForCustomTitle("ChurApp", "conf/churapp_access_config.properties" );
                
                // get action and register from action.properties. 
                // originally, was trying to call it from proxy but that is so hard, now just do it to register views. 
                DefaultActionMapper.getInstance();
                AutositeEventHandlerManager.getInstance().getDropHandler();

            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
            }
    }
    
    private static Logger m_logger = Logger.getLogger(AutoSiteInitiator.class);
}
