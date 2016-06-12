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


import com.autosite.db.CleanerPickupDeliveryRecurDAO;
import com.autosite.db.CleanerPickupDeliveryRecur;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerPickupDeliveryRecurDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerPickupDeliveryRecurDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerPickupDeliveryRecurDS m_CleanerPickupDeliveryRecurDS = new CleanerPickupDeliveryRecurDSExtent();



    public static CleanerPickupDeliveryRecurDS getInstance() {
        return m_CleanerPickupDeliveryRecurDS;
    }

    public static synchronized CleanerPickupDeliveryRecurDS getInstance(long id) {
        CleanerPickupDeliveryRecurDS ret = (CleanerPickupDeliveryRecurDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerPickupDeliveryRecurDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerPickupDeliveryRecurDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerPickupDeliveryRecurDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerPickupDeliveryRecur loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerPickupDeliveryRecurDS() {
        m_dao = new CleanerPickupDeliveryRecurDAO();
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

    protected CleanerPickupDeliveryRecurDS(long id) {
        m_dao = new CleanerPickupDeliveryRecurDAO();
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

    public CleanerPickupDeliveryRecur getById(Long id) {
        return (CleanerPickupDeliveryRecur) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerPickupDeliveryRecur o = (CleanerPickupDeliveryRecur)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerPickupDeliveryRecur removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerPickupDeliveryRecur added to DS " + o.getId());
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
        CleanerPickupDeliveryRecur o = (CleanerPickupDeliveryRecur)obj;

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
            if (m_debug) m_logger.debug("CleanerPickupDeliveryRecur removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerPickupDeliveryRecur added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerPickupDeliveryRecurDS ds = new CleanerPickupDeliveryRecurDS();
        CleanerPickupDeliveryRecur obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerPickupDeliveryRecur createDefault(){

        CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = new CleanerPickupDeliveryRecur();        
	    _CleanerPickupDeliveryRecur = new CleanerPickupDeliveryRecur();// CleanerPickupDeliveryRecurDS.getInstance().getDeafult();
        return _CleanerPickupDeliveryRecur;
    }

    public static CleanerPickupDeliveryRecur copy(CleanerPickupDeliveryRecur org){

    	CleanerPickupDeliveryRecur ret = new CleanerPickupDeliveryRecur();

		ret.setCustomerId(org.getCustomerId());
		ret.setWeekday(org.getWeekday());
		ret.setTimeHhdd(org.getTimeHhdd());

        return ret;
    }

	public static void objectToLog(CleanerPickupDeliveryRecur cleanerPickupDeliveryRecur, Logger logger){
		logger.debug("CleanerPickupDeliveryRecur [" + cleanerPickupDeliveryRecur.getId() + "]" + objectToString(cleanerPickupDeliveryRecur));		
    }

	public static String objectToString(CleanerPickupDeliveryRecur cleanerPickupDeliveryRecur){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerPickupDeliveryRecur=");
		buf.append("Id=").append(cleanerPickupDeliveryRecur.getId()).append(", ");
		buf.append("SiteId=").append(cleanerPickupDeliveryRecur.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerPickupDeliveryRecur cleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerPickupDeliveryRecur=");
		buf.append("Id=").append(cleanerPickupDeliveryRecur.getId()).append(", ");
		buf.append("SiteId=").append(cleanerPickupDeliveryRecur.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
