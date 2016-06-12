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


import com.autosite.db.CleanerLocationDAO;
import com.autosite.db.CleanerLocationExtentDAO;
import com.autosite.db.CleanerLocation;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerLocationDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerLocationDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerLocationDS m_CleanerLocationDS = new CleanerLocationDSExtent();



    public static CleanerLocationDS getInstance() {
        return m_CleanerLocationDS;
    }

    public static synchronized CleanerLocationDS getInstance(long id) {
        CleanerLocationDS ret = (CleanerLocationDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerLocationDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerLocationDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerLocationDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerLocation loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerLocationDS() {
        m_dao = new CleanerLocationExtentDAO();
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

    protected CleanerLocationDS(long id) {
        m_dao = new CleanerLocationDAO();
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

    public CleanerLocation getById(Long id) {
        return (CleanerLocation) m_idToMap.get(id);
    }

    public CleanerLocation getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerLocation loaded = (CleanerLocation) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerLocation.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerLocation o = (CleanerLocation)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerLocation removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerLocation added to DS " + o.getId());
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
        CleanerLocation o = (CleanerLocation)obj;

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
            if (m_debug) m_logger.debug("CleanerLocation removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerLocation added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerLocationDS ds = new CleanerLocationDS();
        CleanerLocation obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerLocation createDefault(){

        CleanerLocation _CleanerLocation = new CleanerLocation();        
	    _CleanerLocation = new CleanerLocation();// CleanerLocationDS.getInstance().getDeafult();
        return _CleanerLocation;
    }

    public static CleanerLocation copy(CleanerLocation org){

    	CleanerLocation ret = new CleanerLocation();

		ret.setAddress(org.getAddress());
		ret.setCityZip(org.getCityZip());
		ret.setPhone(org.getPhone());
		ret.setManagerId(org.getManagerId());

        return ret;
    }

	public static void objectToLog(CleanerLocation cleanerLocation, Logger logger){
		logger.debug("CleanerLocation [" + cleanerLocation.getId() + "]" + objectToString(cleanerLocation));		
    }

	public static String objectToString(CleanerLocation cleanerLocation){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerLocation=");
		buf.append("Id=").append(cleanerLocation.getId()).append(", ");
		buf.append("SiteId=").append(cleanerLocation.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerLocation cleanerLocation = (CleanerLocation)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerLocation=");
		buf.append("Id=").append(cleanerLocation.getId()).append(", ");
		buf.append("SiteId=").append(cleanerLocation.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
