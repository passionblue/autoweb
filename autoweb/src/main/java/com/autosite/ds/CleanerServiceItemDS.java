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


import com.autosite.db.CleanerServiceItemDAO;
import com.autosite.db.CleanerServiceItemExtentDAO;
import com.autosite.db.CleanerServiceItem;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerServiceItemDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerServiceItemDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerServiceItemDS m_CleanerServiceItemDS = new CleanerServiceItemDSExtent();



    public static CleanerServiceItemDS getInstance() {
        return m_CleanerServiceItemDS;
    }

    public static synchronized CleanerServiceItemDS getInstance(long id) {
        CleanerServiceItemDS ret = (CleanerServiceItemDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerServiceItemDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerServiceItemDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerServiceItemDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerServiceItem loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerServiceItemDS() {
        m_dao = new CleanerServiceItemExtentDAO();
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

    protected CleanerServiceItemDS(long id) {
        m_dao = new CleanerServiceItemDAO();
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

    public CleanerServiceItem getById(Long id) {
        return (CleanerServiceItem) m_idToMap.get(id);
    }

    public CleanerServiceItem getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerServiceItem loaded = (CleanerServiceItem) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerServiceItem.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerServiceItem o = (CleanerServiceItem)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerServiceItem removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerServiceItem added to DS " + o.getId());
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
        CleanerServiceItem o = (CleanerServiceItem)obj;

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
            if (m_debug) m_logger.debug("CleanerServiceItem removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerServiceItem added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerServiceItemDS ds = new CleanerServiceItemDS();
        CleanerServiceItem obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerServiceItem createDefault(){

        CleanerServiceItem _CleanerServiceItem = new CleanerServiceItem();        
	    _CleanerServiceItem = new CleanerServiceItem();// CleanerServiceItemDS.getInstance().getDeafult();
        return _CleanerServiceItem;
    }

    public static CleanerServiceItem copy(CleanerServiceItem org){

    	CleanerServiceItem ret = new CleanerServiceItem();

		ret.setServiceId(org.getServiceId());
		ret.setServiceItemId(org.getServiceItemId());
		ret.setItemType(org.getItemType());
		ret.setTitle(org.getTitle());
		ret.setImagePath(org.getImagePath());
		ret.setImagePathLocal(org.getImagePathLocal());
		ret.setBasePrice(org.getBasePrice());
		ret.setNote(org.getNote());

        return ret;
    }

	public static void objectToLog(CleanerServiceItem cleanerServiceItem, Logger logger){
		logger.debug("CleanerServiceItem [" + cleanerServiceItem.getId() + "]" + objectToString(cleanerServiceItem));		
    }

	public static String objectToString(CleanerServiceItem cleanerServiceItem){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerServiceItem=");
		buf.append("Id=").append(cleanerServiceItem.getId()).append(", ");
		buf.append("SiteId=").append(cleanerServiceItem.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerServiceItem cleanerServiceItem = (CleanerServiceItem)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerServiceItem=");
		buf.append("Id=").append(cleanerServiceItem.getId()).append(", ");
		buf.append("SiteId=").append(cleanerServiceItem.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
