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
import com.autosite.db.SiteFeatureConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.SiteFeatureConfigDAO;

public class SiteFeatureConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_SiteIdToOneMap;







    private static Logger m_logger = Logger.getLogger(SiteFeatureConfigDS.class);
    private static SiteFeatureConfigDS m_SiteFeatureConfigDS = new SiteFeatureConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SiteFeatureConfigDS getInstance() {
        return m_SiteFeatureConfigDS;
    }

    public static synchronized SiteFeatureConfigDS getInstance(long id) {
        SiteFeatureConfigDS ret = (SiteFeatureConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SiteFeatureConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SiteFeatureConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SiteFeatureConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SiteFeatureConfig loaded from DB. num=" + m_idToMap.size());
        
    }

    protected SiteFeatureConfigDS() {
        m_dao = new SiteFeatureConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SiteFeatureConfigDS(long id) {
        m_dao = new SiteFeatureConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SiteFeatureConfig getById(Long id) {
        return (SiteFeatureConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SiteFeatureConfig o = (SiteFeatureConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SiteFeatureConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SiteFeatureConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdOneMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        SiteFeatureConfig o = (SiteFeatureConfig)obj;

		if ( o.getSiteId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("SiteFeatureConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
        }
        else {
            
            // add to SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map == null ) {
                map = new TreeMap();
                m_SiteIdToMap.put(new Long(o.getSiteId()), map);
                if (m_debug) m_logger.debug("created new   SiteIdToMap for " + o.getSiteId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("SiteFeatureConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneIdKey - SiteId

    public SiteFeatureConfig getObjectBySiteId(long keySiteId) {
        return (SiteFeatureConfig)m_SiteIdToOneMap.get(new Long(keySiteId));
    }

    private void updateSiteIdOneMap(Object obj, boolean del) {
        SiteFeatureConfig o = (SiteFeatureConfig)obj;
        Long _SiteId = new Long(o.getSiteId());

        if (del) {
            // delete from SiteIdToOneMap

            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                m_SiteIdToOneMap.remove(_SiteId);
                if (m_debug) m_logger.debug("SiteFeatureConfig removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("SiteFeatureConfig not removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]. Does not exist");
            } 
        }
        else {
            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                if (m_debug) m_logger.debug("SiteFeatureConfig repalced SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("SiteFeatureConfig added to SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } 
            m_SiteIdToOneMap.put(_SiteId, o);
        }
    }












    public static void main(String[] args) throws Exception {

        SiteFeatureConfigDS ds = new SiteFeatureConfigDS();
        SiteFeatureConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SiteFeatureConfig createDefault(){

        SiteFeatureConfig _SiteFeatureConfig = new SiteFeatureConfig();        
	    _SiteFeatureConfig = new SiteFeatureConfig();// SiteFeatureConfigDS.getInstance().getDeafult();
        return _SiteFeatureConfig;
    }

    public static SiteFeatureConfig copy(SiteFeatureConfig org){

    	SiteFeatureConfig ret = new SiteFeatureConfig();

		ret.setUserRegisterEnabled(org.getUserRegisterEnabled());
		ret.setEcEnabled(org.getEcEnabled());
		ret.setEmailSubsEnabled(org.getEmailSubsEnabled());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(SiteFeatureConfig siteFeatureConfig, Logger logger){
		logger.debug("SiteFeatureConfig [" + siteFeatureConfig.getId() + "]" + objectToString(siteFeatureConfig));		
    }

	public static String objectToString(SiteFeatureConfig siteFeatureConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("SiteFeatureConfig=");
		buf.append("Id=").append(siteFeatureConfig.getId()).append(", ");
		buf.append("SiteId=").append(siteFeatureConfig.getSiteId()).append(", ");
		buf.append("UserRegisterEnabled=").append(siteFeatureConfig.getUserRegisterEnabled()).append(", ");
		buf.append("EcEnabled=").append(siteFeatureConfig.getEcEnabled()).append(", ");
		buf.append("EmailSubsEnabled=").append(siteFeatureConfig.getEmailSubsEnabled()).append(", ");
		return buf.toString();    
    }
}
