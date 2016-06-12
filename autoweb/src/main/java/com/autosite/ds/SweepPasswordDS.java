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
import com.autosite.db.SweepPassword;
import com.jtrend.service.DomainStore;


public class SweepPasswordDS extends AbstractDS implements DomainStore {







    private static Logger m_logger = Logger.getLogger(SweepPasswordDS.class);
    private static SweepPasswordDS m_SweepPasswordDS = new SweepPasswordDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SweepPasswordDS getInstance() {
        return m_SweepPasswordDS;
    }

    public static synchronized SweepPasswordDS getInstance(long id) {
        SweepPasswordDS ret = (SweepPasswordDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SweepPasswordDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SweepPasswordDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;


    protected SweepPasswordDS() {
        m_idToMap = new ConcurrentHashMap();

 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SweepPasswordDS(long id) {
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SweepPassword getById(Long id) {
        return (SweepPassword) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SweepPassword o = (SweepPassword)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SweepPassword removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SweepPassword added to DS " + o.getId());
        }

    }
	
    //    //    //


    











    public static void main(String[] args) throws Exception {

        SweepPasswordDS ds = new SweepPasswordDS();
        SweepPassword obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SweepPassword createDefault(){

        SweepPassword ret = new SweepPassword();        
//      ret.setSendPasswordEmail("");           
//      ret.setOldPassword("");           
//      ret.setNewPassword("");           
//      ret.setPasswordRetype("");           
        return ret;
    }

    public static SweepPassword copy(SweepPassword org){

    	SweepPassword ret = new SweepPassword();

		ret.setSendPasswordEmail(org.getSendPasswordEmail());
		ret.setOldPassword(org.getOldPassword());
		ret.setNewPassword(org.getNewPassword());
		ret.setPasswordRetype(org.getPasswordRetype());

        return ret;
    }

	public static void objectToLog(SweepPassword sweepPassword, Logger logger){
		logger.debug("SweepPassword [" + sweepPassword.getId() + "]" + objectToString(sweepPassword));		
    }

	public static String objectToString(SweepPassword sweepPassword){
		StringBuffer buf = new StringBuffer();
        buf.append("SweepPassword=");
		return buf.toString();    
    }
}
