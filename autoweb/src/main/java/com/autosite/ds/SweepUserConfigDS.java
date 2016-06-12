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
import com.autosite.db.SweepUserConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.SweepUserConfigDAO;

public class SweepUserConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToOneMap;






    private static Logger m_logger = Logger.getLogger(SweepUserConfigDS.class);
    private static SweepUserConfigDS m_SweepUserConfigDS = new SweepUserConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SweepUserConfigDS getInstance() {
        return m_SweepUserConfigDS;
    }

    public static synchronized SweepUserConfigDS getInstance(long id) {
        SweepUserConfigDS ret = (SweepUserConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SweepUserConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SweepUserConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SweepUserConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SweepUserConfig loaded from DB. num=" + m_idToMap.size());
        
    }

    protected SweepUserConfigDS() {
        m_dao = new SweepUserConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SweepUserConfigDS(long id) {
        m_dao = new SweepUserConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SweepUserConfig getById(Long id) {
        return (SweepUserConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SweepUserConfig o = (SweepUserConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SweepUserConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SweepUserConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdOneMap(obj, del);
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
        SweepUserConfig o = (SweepUserConfig)obj;

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
            if (m_debug) m_logger.debug("SweepUserConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SweepUserConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneIdKey - UserId

    public SweepUserConfig getObjectByUserId(long keyUserId) {
        return (SweepUserConfig)m_UserIdToOneMap.get(new Long(keyUserId));
    }

    private void updateUserIdOneMap(Object obj, boolean del) {
        SweepUserConfig o = (SweepUserConfig)obj;
        Long _UserId = new Long(o.getUserId());

        if (del) {
            // delete from UserIdToOneMap

            if (m_UserIdToOneMap.containsKey(_UserId)){
                m_UserIdToOneMap.remove(_UserId);
                if (m_debug) m_logger.debug("SweepUserConfig removed from UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } else {
                if (m_debug) m_logger.debug("SweepUserConfig not removed from UserIdToMap" + o.getId() + " for [" + _UserId+ "]. Does not exist");
            } 
        }
        else {
            if (m_UserIdToOneMap.containsKey(_UserId)){
                if (m_debug) m_logger.debug("SweepUserConfig repalced UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } else {
                if (m_debug) m_logger.debug("SweepUserConfig added to UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } 
            m_UserIdToOneMap.put(_UserId, o);
        }
    }










    public static void main(String[] args) throws Exception {

        SweepUserConfigDS ds = new SweepUserConfigDS();
        SweepUserConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SweepUserConfig createDefault(){

        SweepUserConfig ret = new SweepUserConfig();        
//      ret.setUserId("");           
//      ret.setMaxSweepAllowed("");           
        return ret;
    }

    public static SweepUserConfig copy(SweepUserConfig org){

    	SweepUserConfig ret = new SweepUserConfig();

		ret.setUserId(org.getUserId());
		ret.setMaxSweepAllowed(org.getMaxSweepAllowed());

        return ret;
    }

	public static void objectToLog(SweepUserConfig sweepUserConfig, Logger logger){
		logger.debug("SweepUserConfig [" + sweepUserConfig.getId() + "]" + objectToString(sweepUserConfig));		
    }

	public static String objectToString(SweepUserConfig sweepUserConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("SweepUserConfig=");
		buf.append("Id=").append(sweepUserConfig.getId()).append(", ");
		buf.append("SiteId=").append(sweepUserConfig.getSiteId()).append(", ");
		buf.append("UserId=").append(sweepUserConfig.getUserId()).append(", ");
		return buf.toString();    
    }
}
