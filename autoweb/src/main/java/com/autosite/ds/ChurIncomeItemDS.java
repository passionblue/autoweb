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


import com.autosite.db.ChurIncomeItemDAO;
import com.autosite.db.ChurIncomeItem;
import com.surveygen.db.BaseMemoryDAO;

public class ChurIncomeItemDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;





    protected Map m_SiteIdToIncomeItemToOneMap;

    private static Logger m_logger = Logger.getLogger(ChurIncomeItemDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static ChurIncomeItemDS m_ChurIncomeItemDS = new ChurIncomeItemDSExtent();



    public static ChurIncomeItemDS getInstance() {
        return m_ChurIncomeItemDS;
    }

    public static synchronized ChurIncomeItemDS getInstance(long id) {
        ChurIncomeItemDS ret = (ChurIncomeItemDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ChurIncomeItemDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ChurIncomeItemDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ChurIncomeItemDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ChurIncomeItem loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ChurIncomeItemDS() {
        m_dao = new ChurIncomeItemDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToIncomeItemToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ChurIncomeItemDS(long id) {
        m_dao = new ChurIncomeItemDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToIncomeItemToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ChurIncomeItem getById(Long id) {
        return (ChurIncomeItem) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ChurIncomeItem o = (ChurIncomeItem)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ChurIncomeItem removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ChurIncomeItem added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToIncomeItemOneMap(obj, del);
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
        ChurIncomeItem o = (ChurIncomeItem)obj;

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
            if (m_debug) m_logger.debug("ChurIncomeItem removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ChurIncomeItem added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    









	// listOneToOneStringToSiteIdKey - IncomeItem

    public  ChurIncomeItem getBySiteIdToIncomeItem(long siteId, String IncomeItem) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToIncomeItemToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToIncomeItemToOneMap.get(keySiteIdTo);

     	    String keyIncomeItem = IncomeItem;
            
            if ( keyIncomeItem!= null && mapSiteIdTo.containsKey(keyIncomeItem)){
                return ( ChurIncomeItem)mapSiteIdTo.get(keyIncomeItem);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderIncomeItem(){
        List ret = new ArrayList();
    
        List contentsByIncomeItem = new ArrayList( m_SiteIdToIncomeItemToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByIncomeItem.iterator(); iterator.hasNext();) {
            ChurIncomeItem obj = (ChurIncomeItem) iterator.next();
            if ( obj.getIncomeItem() == null ) continue;
            map.put(obj.getIncomeItem(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToIncomeItemOneMap(Object obj, boolean del) {
        ChurIncomeItem o = (ChurIncomeItem)obj;

   	    String keyIncomeItem = o.getIncomeItem();

		if ( keyIncomeItem == null || keyIncomeItem.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToIncomeItemOneMap for the ChurIncomeItem object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PollIdIncomeItemToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToIncomeItemToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyIncomeItem)){
                    mapSiteIdTo.remove(keyIncomeItem);
                }
            }
            m_logger.debug("removed ChurIncomeItem from m_SiteIdToIncomeItemToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getIncomeItem());
        }
        else {
            
            // add to SiteIdToIncomeItemToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToIncomeItemToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToIncomeItemToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			ChurIncomeItem replaced = (ChurIncomeItem) mapSiteIdTo.put(keyIncomeItem,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToIncomeItemOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("ChurIncomeItem added to SiteIdToIncomeItemToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    



    public static void main(String[] args) throws Exception {

        ChurIncomeItemDS ds = new ChurIncomeItemDS();
        ChurIncomeItem obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ChurIncomeItem createDefault(){

        ChurIncomeItem _ChurIncomeItem = new ChurIncomeItem();        
	    _ChurIncomeItem = new ChurIncomeItem();// ChurIncomeItemDS.getInstance().getDeafult();
        return _ChurIncomeItem;
    }

    public static ChurIncomeItem copy(ChurIncomeItem org){

    	ChurIncomeItem ret = new ChurIncomeItem();

		ret.setCategoryId(org.getCategoryId());
		ret.setIncomeItem(org.getIncomeItem());
		ret.setDisplay(org.getDisplay());

        return ret;
    }

	public static void objectToLog(ChurIncomeItem churIncomeItem, Logger logger){
		logger.debug("ChurIncomeItem [" + churIncomeItem.getId() + "]" + objectToString(churIncomeItem));		
    }


	public static String objectToString(ChurIncomeItem churIncomeItem){
		StringBuilder buf = new StringBuilder();
        buf.append("ChurIncomeItem=");
		buf.append("Id=").append(churIncomeItem.getId()).append(", ");
		buf.append("SiteId=").append(churIncomeItem.getSiteId()).append(", ");
		return buf.toString();    
    }





	// Empty methods for 

}
