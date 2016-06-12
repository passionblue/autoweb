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
import com.autosite.db.SiteRegPaymentInfo;
import com.jtrend.service.DomainStore;


public class SiteRegPaymentInfoDS extends AbstractDS implements DomainStore {




    private static Logger m_logger = Logger.getLogger(SiteRegPaymentInfoDS.class);
    private static SiteRegPaymentInfoDS m_SiteRegPaymentInfoDS = new SiteRegPaymentInfoDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SiteRegPaymentInfoDS getInstance() {
        return m_SiteRegPaymentInfoDS;
    }

    public static synchronized SiteRegPaymentInfoDS getInstance(long id) {
        SiteRegPaymentInfoDS ret = (SiteRegPaymentInfoDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SiteRegPaymentInfoDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SiteRegPaymentInfoDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;


    protected SiteRegPaymentInfoDS() {
        m_idToMap = new ConcurrentHashMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SiteRegPaymentInfoDS(long id) {
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SiteRegPaymentInfo getById(Long id) {
        return (SiteRegPaymentInfo) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SiteRegPaymentInfo o = (SiteRegPaymentInfo)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SiteRegPaymentInfo removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SiteRegPaymentInfo added to DS " + o.getId());
        }




    }



    







    public boolean persistEnable(){
        return false;
    }


    public static void main(String[] args) throws Exception {

        SiteRegPaymentInfoDS ds = new SiteRegPaymentInfoDS();
        SiteRegPaymentInfo obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SiteRegPaymentInfo createDefault(){

        SiteRegPaymentInfo ret = new SiteRegPaymentInfo();        
//      ret.setTargetDomain("");           
//      ret.setPaymentType("");           
//      ret.setCardType("");           
//      ret.setPaymentNum("");           
//      ret.setExpireMonth("");           
//      ret.setExpireYear("");           
//      ret.setCcv("");           
        return ret;
    }

    public static SiteRegPaymentInfo copy(SiteRegPaymentInfo org){

    	SiteRegPaymentInfo ret = new SiteRegPaymentInfo();

		ret.setTargetDomain(org.getTargetDomain());
		ret.setPaymentType(org.getPaymentType());
		ret.setCardType(org.getCardType());
		ret.setPaymentNum(org.getPaymentNum());
		ret.setExpireMonth(org.getExpireMonth());
		ret.setExpireYear(org.getExpireYear());
		ret.setCcv(org.getCcv());

        return ret;
    }

	public static void objectToLog(SiteRegPaymentInfo siteRegPaymentInfo, Logger logger){
		logger.debug("SiteRegPaymentInfo [" + siteRegPaymentInfo.getId() + "]" + objectToString(siteRegPaymentInfo));		
    }

	public static String objectToString(SiteRegPaymentInfo siteRegPaymentInfo){
		StringBuffer buf = new StringBuffer();
        buf.append("SiteRegPaymentInfo=");
		return buf.toString();    
    }
}
