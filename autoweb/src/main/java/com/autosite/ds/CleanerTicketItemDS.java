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


import com.autosite.db.CleanerTicketItemDAO;
import com.autosite.db.CleanerTicketItemExtentDAO;
import com.autosite.db.CleanerTicketItem;
import com.surveygen.db.BaseMemoryDAO;

public class CleanerTicketItemDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(CleanerTicketItemDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static CleanerTicketItemDS m_CleanerTicketItemDS = new CleanerTicketItemDSExtent();



    public static CleanerTicketItemDS getInstance() {
        return m_CleanerTicketItemDS;
    }

    public static synchronized CleanerTicketItemDS getInstance(long id) {
        CleanerTicketItemDS ret = (CleanerTicketItemDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new CleanerTicketItemDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_CleanerTicketItemDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((CleanerTicketItemDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("CleanerTicketItem loaded from DB. num=" + m_idToMap.size());
        
    }


    protected CleanerTicketItemDS() {
        m_dao = new CleanerTicketItemExtentDAO();
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

    protected CleanerTicketItemDS(long id) {
        m_dao = new CleanerTicketItemDAO();
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

    public CleanerTicketItem getById(Long id) {
        return (CleanerTicketItem) m_idToMap.get(id);
    }

    public CleanerTicketItem getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                CleanerTicketItem loaded = (CleanerTicketItem) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return CleanerTicketItem.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        CleanerTicketItem o = (CleanerTicketItem)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("CleanerTicketItem removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("CleanerTicketItem added to DS " + o.getId());
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
        CleanerTicketItem o = (CleanerTicketItem)obj;

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
            if (m_debug) m_logger.debug("CleanerTicketItem removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("CleanerTicketItem added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











	//

    public static void main(String[] args) throws Exception {

        CleanerTicketItemDS ds = new CleanerTicketItemDS();
        CleanerTicketItem obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static CleanerTicketItem createDefault(){

        CleanerTicketItem _CleanerTicketItem = new CleanerTicketItem();        
	    _CleanerTicketItem = new CleanerTicketItem();// CleanerTicketItemDS.getInstance().getDeafult();
        return _CleanerTicketItem;
    }

    public static CleanerTicketItem copy(CleanerTicketItem org){

    	CleanerTicketItem ret = new CleanerTicketItem();

		ret.setTicketId(org.getTicketId());
		ret.setParentTicketId(org.getParentTicketId());
		ret.setProductId(org.getProductId());
		ret.setSubtotalAmount(org.getSubtotalAmount());
		ret.setTotalAmount(org.getTotalAmount());
		ret.setDiscountId(org.getDiscountId());
		ret.setTotalDiscountAmount(org.getTotalDiscountAmount());
		ret.setSpecialDiscountAmount(org.getSpecialDiscountAmount());
		ret.setNote(org.getNote());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(CleanerTicketItem cleanerTicketItem, Logger logger){
		logger.debug("CleanerTicketItem [" + cleanerTicketItem.getId() + "]" + objectToString(cleanerTicketItem));		
    }

	public static String objectToString(CleanerTicketItem cleanerTicketItem){
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerTicketItem=");
		buf.append("Id=").append(cleanerTicketItem.getId()).append(", ");
		buf.append("SiteId=").append(cleanerTicketItem.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		CleanerTicketItem cleanerTicketItem = (CleanerTicketItem)object;
		StringBuilder buf = new StringBuilder();
        buf.append("CleanerTicketItem=");
		buf.append("Id=").append(cleanerTicketItem.getId()).append(", ");
		buf.append("SiteId=").append(cleanerTicketItem.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
