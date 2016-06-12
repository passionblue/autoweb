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
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.ChurExpenseItemDAO;
import com.autosite.db.ChurExpenseItem;
import com.surveygen.db.BaseMemoryDAO;

public class ChurExpenseItemDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(ChurExpenseItemDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurExpenseItemDS m_ChurExpenseItemDS = new ChurExpenseItemDSExtent();



    public static ChurExpenseItemDS getInstance() {
        return m_ChurExpenseItemDS;
    }

    public static synchronized ChurExpenseItemDS getInstance(long id) {
        ChurExpenseItemDS ret = (ChurExpenseItemDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurExpenseItemDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurExpenseItemDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurExpenseItemDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurExpenseItem loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurExpenseItemDS() {
        m_dao = new ChurExpenseItemDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ChurExpenseItemDS(long id) {
        m_dao = new ChurExpenseItemDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ChurExpenseItem getById(Long id) {
        return (ChurExpenseItem) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurExpenseItem o = (ChurExpenseItem)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurExpenseItem removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurExpenseItem added to DS " + o.getId());
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
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        ChurExpenseItem o = (ChurExpenseItem)obj;

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
            if (m_debug) m_logger.debug("ChurExpenseItem removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurExpenseItem added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    












    public static void main(String[] args) throws Exception {

        ChurExpenseItemDS ds = new ChurExpenseItemDS();
        ChurExpenseItem obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurExpenseItem createDefault(){

        ChurExpenseItem _ChurExpenseItem = new ChurExpenseItem();        
	    _ChurExpenseItem = new ChurExpenseItem();// ChurExpenseItemDS.getInstance().getDeafult();
        return _ChurExpenseItem;
    }

    public static ChurExpenseItem copy(ChurExpenseItem org){

    	ChurExpenseItem ret = new ChurExpenseItem();

		ret.setCategoryId(org.getCategoryId());
		ret.setExpenseItem(org.getExpenseItem());
		ret.setDisplay(org.getDisplay());

        return ret;
    }

	public static void objectToLog(ChurExpenseItem churExpenseItem, Logger logger){
		logger.debug("ChurExpenseItem [" + churExpenseItem.getId() + "]" + objectToString(churExpenseItem));		
    }


	public static String objectToString(ChurExpenseItem churExpenseItem){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurExpenseItem=");
		buf.append("Id=").append(churExpenseItem.getId()).append(", ");
		buf.append("SiteId=").append(churExpenseItem.getSiteId()).append(", ");
		return buf.toString();    
    }





	// Empty methods for 

}
