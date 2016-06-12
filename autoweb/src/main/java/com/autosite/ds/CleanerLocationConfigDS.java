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
import com.jtrend.util.AggregatedIdMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.CleanerLocationConfigDAO;
import com.autosite.db.CleanerLocationConfigExtentDAO;
import com.autosite.db.CleanerLocationConfig;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerLocationConfigDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerLocationConfigDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerLocationConfigDS m_CleanerLocationConfigDS = new CleanerLocationConfigDSExtent();



    public static CleanerLocationConfigDS getInstance() {
        return m_CleanerLocationConfigDS;
    }

    public static synchronized CleanerLocationConfigDS getInstance(long id) {
        CleanerLocationConfigDS ret = (CleanerLocationConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerLocationConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerLocationConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerLocationConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerLocationConfig loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerLocationConfigDS() {
        m_dao = new CleanerLocationConfigExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected CleanerLocationConfigDS(long id) {
        m_dao = new CleanerLocationConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public CleanerLocationConfig getById(Long id) {
        return (CleanerLocationConfig) m_idToMap.get(id);
    }

    public CleanerLocationConfig getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerLocationConfig loaded = (CleanerLocationConfig) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerLocationConfig.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerLocationConfig o = (CleanerLocationConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerLocationConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerLocationConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            if ( m_aggregateAllSites) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        CleanerLocationConfig o = (CleanerLocationConfig)obj;

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
            if (m_debug) m_logger.debug("CleanerLocationConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerLocationConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerLocationConfigDS ds = new CleanerLocationConfigDS();
        CleanerLocationConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerLocationConfig createDefault(){

        CleanerLocationConfig _CleanerLocationConfig = new CleanerLocationConfig();        
	    _CleanerLocationConfig = new CleanerLocationConfig();// CleanerLocationConfigDS.getInstance().getDeafult();
        return _CleanerLocationConfig;
    }

    public static CleanerLocationConfig copy(CleanerLocationConfig org){

    	CleanerLocationConfig ret = new CleanerLocationConfig();

		ret.setLocationId(org.getLocationId());
		ret.setOpenHourWeekday(org.getOpenHourWeekday());
		ret.setCloseHourWeekday(org.getCloseHourWeekday());
		ret.setOpenHourSat(org.getOpenHourSat());
		ret.setCloseHourSat(org.getCloseHourSat());
		ret.setOpenHourSun(org.getOpenHourSun());
		ret.setCloseHourSun(org.getCloseHourSun());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(CleanerLocationConfig cleanerLocationConfig, Logger logger){
		logger.debug("CleanerLocationConfig [" + cleanerLocationConfig.getId() + "]" + objectToString(cleanerLocationConfig));		
    }

	public static String objectToString(CleanerLocationConfig cleanerLocationConfig){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerLocationConfig=");
		buf.append("Id=").append(cleanerLocationConfig.getId()).append(", ");
		buf.append("SiteId=").append(cleanerLocationConfig.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerLocationConfig cleanerLocationConfig = (CleanerLocationConfig)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerLocationConfig=");
		buf.append("Id=").append(cleanerLocationConfig.getId()).append(", ");
		buf.append("SiteId=").append(cleanerLocationConfig.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
