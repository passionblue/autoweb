package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.SiteRegStart;
import com.jtrend.service.DomainStore;


public class SiteRegStartDS extends AbstractDS implements DomainStore {

    protected Map m_TargetDomainToOneMap;



    private static Logger m_logger = Logger.getLogger(SiteRegStartDS.class);
    private static SiteRegStartDS m_SiteRegStartDS = new SiteRegStartDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SiteRegStartDS getInstance() {
        return m_SiteRegStartDS;
    }

    public static synchronized SiteRegStartDS getInstance(long id) {
        SiteRegStartDS ret = (SiteRegStartDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SiteRegStartDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SiteRegStartDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;


    protected SiteRegStartDS() {
        m_idToMap = new ConcurrentHashMap();

        m_TargetDomainToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SiteRegStartDS(long id) {
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_TargetDomainToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SiteRegStart getById(Long id) {
        return (SiteRegStart) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SiteRegStart o = (SiteRegStart)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SiteRegStart removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SiteRegStart added to DS " + o.getId());
        }

        updateTargetDomainOneMap(obj, del);



    }



    

    public SiteRegStart getObjectByTargetDomain(String keyTargetDomain) {
        return (SiteRegStart)m_TargetDomainToOneMap.get(keyTargetDomain);
    }

    private void updateTargetDomainOneMap(Object obj, boolean del) {
        SiteRegStart o = (SiteRegStart)obj;
        String _TargetDomain =  o.getTargetDomain();

        if (del) {
            // delete from TargetDomainToMap

            if (m_TargetDomainToOneMap.containsKey(_TargetDomain)){
                m_TargetDomainToOneMap.remove(_TargetDomain);
                 if (m_debug) m_logger.debug("SiteRegStart removed from TargetDomainToMap" + o.getId() + " for [" + _TargetDomain+ "]");
            } else {
                if (m_debug) m_logger.debug("SiteRegStart not removed from TargetDomainToMap" + o.getId() + " for [" + _TargetDomain+ "]");
            } 
        }
        else {
            
            if (m_TargetDomainToOneMap.containsKey(_TargetDomain)){
                if (m_debug) m_logger.debug("SiteRegStart repalced TargetDomainToMap" + o.getId() + " for [" + _TargetDomain+ "]");
            } else {
                if (m_debug) m_logger.debug("SiteRegStart added to TargetDomainToMap" + o.getId() + " for [" + _TargetDomain+ "]");
            } 
            m_TargetDomainToOneMap.put(_TargetDomain, o);
        }
    }







    public boolean persistEnable(){
        return false;
    }


    public static void main(String[] args) throws Exception {

        SiteRegStartDS ds = new SiteRegStartDS();
        SiteRegStart obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SiteRegStart createDefault(){

        SiteRegStart ret = new SiteRegStart();        
//      ret.setTargetDomain("");           
        return ret;
    }

    public static SiteRegStart copy(SiteRegStart org){

    	SiteRegStart ret = new SiteRegStart();

		ret.setTargetDomain(org.getTargetDomain());

        return ret;
    }

	public static void objectToLog(SiteRegStart siteRegStart, Logger logger){
		logger.debug("SiteRegStart [" + siteRegStart.getId() + "]" + objectToString(siteRegStart));		
    }

	public static String objectToString(SiteRegStart siteRegStart){
		StringBuffer buf = new StringBuffer();
        buf.append("SiteRegStart=");
		return buf.toString();    
    }
}
