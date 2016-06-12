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
import com.autosite.db.MenuItem;
import com.jtrend.service.DomainStore;

import com.autosite.db.MenuItemDAO;

public class MenuItemDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;



    protected Map m_SiteIdToPanelIdToMap;
    protected Map m_SiteIdToParentIdToMap;




    private static Logger m_logger = Logger.getLogger(MenuItemDS.class);
    private static MenuItemDS m_MenuItemDS = new MenuItemDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static MenuItemDS getInstance() {
        return m_MenuItemDS;
    }

    public static synchronized MenuItemDS getInstance(long id) {
        MenuItemDS ret = (MenuItemDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new MenuItemDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_MenuItemDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((MenuItemDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("MenuItem loaded from DB. num=" + m_idToMap.size());
        
    }

    protected MenuItemDS() {
        m_dao = new MenuItemDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToPanelIdToMap = new ConcurrentHashMap();
        m_SiteIdToParentIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected MenuItemDS(long id) {
        m_dao = new MenuItemDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToPanelIdToMap = new ConcurrentHashMap();
        m_SiteIdToParentIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public MenuItem getById(Long id) {
        return (MenuItem) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        MenuItem o = (MenuItem)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("MenuItem removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("MenuItem added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToPanelIdMap(obj, del);
        updateSiteIdToParentIdMap(obj, del);
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
        MenuItem o = (MenuItem)obj;

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
            if (m_debug) m_logger.debug("MenuItem removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("MenuItem added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    






    public List getBySiteIdToPanelIdList (long SiteId, long PanelId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToPanelIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToPanelIdToMap.get(keySiteId);

     	    Long keyPanelId = PanelId;
            
            if ( mapSiteId.containsKey(keyPanelId)){
                return new ArrayList( ((Map)mapSiteId.get(keyPanelId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdPanelId(long SiteId, long PanelId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToPanelIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToPanelIdToMap.get(keySiteId);

     	    Long keyPanelId = new Long(PanelId);
            
            if ( mapSiteId.containsKey(keyPanelId)){
                return (Map)mapSiteId.get(keyPanelId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToPanelIdMap(Object obj, boolean del) {
        MenuItem o = (MenuItem)obj;

   	    Long keyPanelId = new Long(o.getPanelId());

        if (del) {
            // delete from SiteIdPanelIdToMap
            Map mapSiteId  = (Map) m_SiteIdToPanelIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapPanelId = (Map) mapSiteId.get(keyPanelId);
                if (mapPanelId != null){
                    mapPanelId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed MenuItem from m_SiteIdToPanelIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPanelId());
        }
        else {
            
            // add to SiteIdPanelIdToMap
            Map mapSiteId  = (Map) m_SiteIdToPanelIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToPanelIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapPanelId = (Map) mapSiteId.get(keyPanelId);
            
            if ( mapPanelId == null) {
                mapPanelId = new TreeMap();
                mapSiteId.put(keyPanelId, mapPanelId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapPanelId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdPanelIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
    public List getBySiteIdToParentIdList (long SiteId, long ParentId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToParentIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToParentIdToMap.get(keySiteId);

     	    Long keyParentId = ParentId;
            
            if ( mapSiteId.containsKey(keyParentId)){
                return new ArrayList( ((Map)mapSiteId.get(keyParentId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdParentId(long SiteId, long ParentId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToParentIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToParentIdToMap.get(keySiteId);

     	    Long keyParentId = new Long(ParentId);
            
            if ( mapSiteId.containsKey(keyParentId)){
                return (Map)mapSiteId.get(keyParentId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToParentIdMap(Object obj, boolean del) {
        MenuItem o = (MenuItem)obj;

   	    Long keyParentId = new Long(o.getParentId());

        if (del) {
            // delete from SiteIdParentIdToMap
            Map mapSiteId  = (Map) m_SiteIdToParentIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapParentId = (Map) mapSiteId.get(keyParentId);
                if (mapParentId != null){
                    mapParentId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed MenuItem from m_SiteIdToParentIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getParentId());
        }
        else {
            
            // add to SiteIdParentIdToMap
            Map mapSiteId  = (Map) m_SiteIdToParentIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToParentIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapParentId = (Map) mapSiteId.get(keyParentId);
            
            if ( mapParentId == null) {
                mapParentId = new TreeMap();
                mapSiteId.put(keyParentId, mapParentId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapParentId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdParentIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    








    public static void main(String[] args) throws Exception {

        MenuItemDS ds = new MenuItemDS();
        MenuItem obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static MenuItem createDefault(){

        MenuItem ret = new MenuItem();        
//      ret.setPanelId("");           
//      ret.setParentId("");           
//      ret.setTitle("");           
//      ret.setData("");           
//      ret.setTargetType("");           
//      ret.setOrderIdx("");           
//      ret.setPageId("");           
//      ret.setContentId("");           
//      ret.setHide("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static MenuItem copy(MenuItem org){

    	MenuItem ret = new MenuItem();

		ret.setPanelId(org.getPanelId());
		ret.setParentId(org.getParentId());
		ret.setTitle(org.getTitle());
		ret.setData(org.getData());
		ret.setTargetType(org.getTargetType());
		ret.setOrderIdx(org.getOrderIdx());
		ret.setPageId(org.getPageId());
		ret.setContentId(org.getContentId());
		ret.setHide(org.getHide());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(MenuItem menuItem, Logger logger){
		logger.debug("MenuItem [" + menuItem.getId() + "]" + objectToString(menuItem));		
    }

	public static String objectToString(MenuItem menuItem){
		StringBuffer buf = new StringBuffer();
        buf.append("MenuItem=");
		buf.append("Id=").append(menuItem.getId()).append(", ");
		buf.append("SiteId=").append(menuItem.getSiteId()).append(", ");
		return buf.toString();    
    }
}
