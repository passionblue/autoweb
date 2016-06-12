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


import com.autosite.db.CleanerUserDAO;
import com.autosite.db.CleanerUserExtentDAO;
import com.autosite.db.CleanerUser;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerUserDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerUserDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerUserDS m_CleanerUserDS = new CleanerUserDSExtent();



    public static CleanerUserDS getInstance() {
        return m_CleanerUserDS;
    }

    public static synchronized CleanerUserDS getInstance(long id) {
        CleanerUserDS ret = (CleanerUserDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerUserDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerUserDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerUserDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerUser loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerUserDS() {
        m_dao = new CleanerUserExtentDAO();
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

    protected CleanerUserDS(long id) {
        m_dao = new CleanerUserDAO();
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

    public CleanerUser getById(Long id) {
        return (CleanerUser) m_idToMap.get(id);
    }

    public CleanerUser getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerUser loaded = (CleanerUser) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerUser.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerUser o = (CleanerUser)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerUser removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerUser added to DS " + o.getId());
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
        CleanerUser o = (CleanerUser)obj;

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
            if (m_debug) m_logger.debug("CleanerUser removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerUser added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerUserDS ds = new CleanerUserDS();
        CleanerUser obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerUser createDefault(){

        CleanerUser _CleanerUser = new CleanerUser();        
	    _CleanerUser = new CleanerUser();// CleanerUserDS.getInstance().getDeafult();
        return _CleanerUser;
    }

    public static CleanerUser copy(CleanerUser org){

    	CleanerUser ret = new CleanerUser();

		ret.setAutositeUserId(org.getAutositeUserId());
		ret.setInactivated(org.getInactivated());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(CleanerUser cleanerUser, Logger logger){
		logger.debug("CleanerUser [" + cleanerUser.getId() + "]" + objectToString(cleanerUser));		
    }

	public static String objectToString(CleanerUser cleanerUser){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerUser=");
		buf.append("Id=").append(cleanerUser.getId()).append(", ");
		buf.append("SiteId=").append(cleanerUser.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerUser cleanerUser = (CleanerUser)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerUser=");
		buf.append("Id=").append(cleanerUser.getId()).append(", ");
		buf.append("SiteId=").append(cleanerUser.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
