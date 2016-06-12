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
import com.autosite.db.PanelMenuOrder;
import com.jtrend.service.DomainStore;

import com.autosite.db.PanelMenuOrderDAO;

public class PanelMenuOrderDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;




    protected Map m_SiteIdToPanelIdToOneMap;



    private static Logger m_logger = Logger.getLogger(PanelMenuOrderDS.class);
    private static PanelMenuOrderDS m_PanelMenuOrderDS = new PanelMenuOrderDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PanelMenuOrderDS getInstance() {
        return m_PanelMenuOrderDS;
    }

    public static synchronized PanelMenuOrderDS getInstance(long id) {
        PanelMenuOrderDS ret = (PanelMenuOrderDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PanelMenuOrderDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PanelMenuOrderDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PanelMenuOrderDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PanelMenuOrder loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PanelMenuOrderDS() {
        m_dao = new PanelMenuOrderDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToPanelIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PanelMenuOrderDS(long id) {
        m_dao = new PanelMenuOrderDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToPanelIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PanelMenuOrder getById(Long id) {
        return (PanelMenuOrder) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PanelMenuOrder o = (PanelMenuOrder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PanelMenuOrder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PanelMenuOrder added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToPanelIdOneMap(obj, del);
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
        PanelMenuOrder o = (PanelMenuOrder)obj;

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
            if (m_debug) m_logger.debug("PanelMenuOrder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PanelMenuOrder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    









	// listOneToOneIdToSiteIdKey - PanelId

    public  PanelMenuOrder getBySiteIdToPanelId(long siteId, long PanelId) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToPanelIdToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToPanelIdToOneMap.get(keySiteIdTo);

     	    Long keyPanelId = new Long(PanelId);
            
            if ( mapSiteIdTo.containsKey(keyPanelId)){
                return ( PanelMenuOrder)mapSiteIdTo.get(keyPanelId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderPanelId(){
        List ret = new ArrayList();
    
        List contentsByPanelId = new ArrayList( m_SiteIdToPanelIdToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsByPanelId.iterator(); iterator.hasNext();) {
            PanelMenuOrder obj = (PanelMenuOrder) iterator.next();
            map.put(new Long(obj.getPanelId()), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToPanelIdOneMap(Object obj, boolean del) {
        PanelMenuOrder o = (PanelMenuOrder)obj;

   	    Long keyPanelId = new Long(o.getPanelId());

        if (del) {
            // delete from PollIdPanelIdToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToPanelIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keyPanelId)){
                    mapSiteIdTo.remove(keyPanelId);
                }
            }
            m_logger.debug("removed PanelMenuOrder from m_SiteIdToPanelIdToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPanelId());
        }
        else {
            
            // add to SiteIdToPanelIdToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToPanelIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToPanelIdToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new  mapSiteIdTo for " + o.getSiteId());
            }
            
			PanelMenuOrder replaced = (PanelMenuOrder) mapSiteIdTo.put(keyPanelId,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToPanelId existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("PanelMenuOrder added to SiteIdToPanelIdToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    





    public static void main(String[] args) throws Exception {

        PanelMenuOrderDS ds = new PanelMenuOrderDS();
        PanelMenuOrder obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PanelMenuOrder createDefault(){

        PanelMenuOrder ret = new PanelMenuOrder();        
//      ret.setPanelId("");           
//      ret.setOrderedIds("");           
//      ret.setReverse("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static PanelMenuOrder copy(PanelMenuOrder org){

    	PanelMenuOrder ret = new PanelMenuOrder();

		ret.setPanelId(org.getPanelId());
		ret.setOrderedIds(org.getOrderedIds());
		ret.setReverse(org.getReverse());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(PanelMenuOrder panelMenuOrder, Logger logger){
		logger.debug("PanelMenuOrder [" + panelMenuOrder.getId() + "]" + objectToString(panelMenuOrder));		
    }

	public static String objectToString(PanelMenuOrder panelMenuOrder){
		StringBuffer buf = new StringBuffer();
        buf.append("PanelMenuOrder=");
		buf.append("Id=").append(panelMenuOrder.getId()).append(", ");
		buf.append("SiteId=").append(panelMenuOrder.getSiteId()).append(", ");
		return buf.toString();    
    }
}
