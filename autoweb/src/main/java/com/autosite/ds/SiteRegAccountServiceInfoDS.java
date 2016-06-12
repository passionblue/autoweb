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
import com.autosite.db.SiteRegAccountServiceInfo;
import com.jtrend.service.DomainStore;


public class SiteRegAccountServiceInfoDS extends AbstractDS implements DomainStore {




    private static Logger m_logger = Logger.getLogger(SiteRegAccountServiceInfoDS.class);
    private static SiteRegAccountServiceInfoDS m_SiteRegAccountServiceInfoDS = new SiteRegAccountServiceInfoDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SiteRegAccountServiceInfoDS getInstance() {
        return m_SiteRegAccountServiceInfoDS;
    }

    public static synchronized SiteRegAccountServiceInfoDS getInstance(long id) {
        SiteRegAccountServiceInfoDS ret = (SiteRegAccountServiceInfoDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SiteRegAccountServiceInfoDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SiteRegAccountServiceInfoDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;


    protected SiteRegAccountServiceInfoDS() {
        m_idToMap = new ConcurrentHashMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SiteRegAccountServiceInfoDS(long id) {
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SiteRegAccountServiceInfo getById(Long id) {
        return (SiteRegAccountServiceInfo) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SiteRegAccountServiceInfo o = (SiteRegAccountServiceInfo)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SiteRegAccountServiceInfo removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SiteRegAccountServiceInfo added to DS " + o.getId());
        }




    }



    







    public boolean persistEnable(){
        return false;
    }


    public static void main(String[] args) throws Exception {

        SiteRegAccountServiceInfoDS ds = new SiteRegAccountServiceInfoDS();
        SiteRegAccountServiceInfo obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SiteRegAccountServiceInfo createDefault(){

        SiteRegAccountServiceInfo ret = new SiteRegAccountServiceInfo();        
//      ret.setTargetDomain("");           
//      ret.setEmail("");           
//      ret.setEmailRetype("");           
//      ret.setCompany("");           
//      ret.setFirstName("");           
//      ret.setLastName("");           
//      ret.setPhone("");           
        return ret;
    }

    public static SiteRegAccountServiceInfo copy(SiteRegAccountServiceInfo org){

    	SiteRegAccountServiceInfo ret = new SiteRegAccountServiceInfo();

		ret.setTargetDomain(org.getTargetDomain());
		ret.setEmail(org.getEmail());
		ret.setEmailRetype(org.getEmailRetype());
		ret.setCompany(org.getCompany());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setPhone(org.getPhone());

        return ret;
    }

	public static void objectToLog(SiteRegAccountServiceInfo siteRegAccountServiceInfo, Logger logger){
		logger.debug("SiteRegAccountServiceInfo [" + siteRegAccountServiceInfo.getId() + "]" + objectToString(siteRegAccountServiceInfo));		
    }

	public static String objectToString(SiteRegAccountServiceInfo siteRegAccountServiceInfo){
		StringBuffer buf = new StringBuffer();
        buf.append("SiteRegAccountServiceInfo=");
		return buf.toString();    
    }
}
