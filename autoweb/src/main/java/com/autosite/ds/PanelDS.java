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
import com.autosite.db.Panel;
import com.jtrend.service.DomainStore;

import com.autosite.db.PanelDAO;

public class PanelDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToMap;
    protected Map m_FeedIdToMap;

	protected Map m_SiteIdColumnNumToMap;





    private static Logger m_logger = Logger.getLogger(PanelDS.class);
    private static PanelDS m_PanelDS = new PanelDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PanelDS getInstance() {
        return m_PanelDS;
    }

    public static synchronized PanelDS getInstance(long id) {
        PanelDS ret = (PanelDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PanelDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PanelDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PanelDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("Panel loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PanelDS() {
        m_dao = new PanelDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        m_FeedIdToMap = new ConcurrentHashMap();
		m_SiteIdColumnNumToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PanelDS(long id) {
        m_dao = new PanelDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        m_FeedIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public Panel getById(Long id) {
        return (Panel) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        Panel o = (Panel)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("Panel removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("Panel added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdMap(obj, del);
        updateFeedIdMap(obj, del);
		updateSiteIdColumnNumMap(obj, del);
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
        Panel o = (Panel)obj;

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
            if (m_debug) m_logger.debug("Panel removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("Panel added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - PageId
    public List getByPageId(long PageId) {
        
        Long _PageId  = new Long(PageId);
        if (m_PageIdToMap.containsKey(_PageId)) {
            return new ArrayList( ((Map)m_PageIdToMap.get(_PageId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePageIdMap(Object obj, boolean del) {
        Panel o = (Panel)obj;

		if ( o.getPageId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from PageIdToMap
            Map map  = (Map) m_PageIdToMap.get(new Long(o.getPageId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("Panel removed from PageIdToMap" + o.getId() + " from " + o.getPageId());
        }
        else {
            
            // add to PageIdToMap
            Map map  = (Map) m_PageIdToMap.get(new Long(o.getPageId()));
            if ( map == null ) {
                map = new TreeMap();
                m_PageIdToMap.put(new Long(o.getPageId()), map);
                if (m_debug) m_logger.debug("created new   PageIdToMap for " + o.getPageId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("Panel added to PageIdToMap " + o.getId() + " to " + o.getPageId());
        }
    }
	// ListKeys - FeedId
    public List getByFeedId(long FeedId) {
        
        Long _FeedId  = new Long(FeedId);
        if (m_FeedIdToMap.containsKey(_FeedId)) {
            return new ArrayList( ((Map)m_FeedIdToMap.get(_FeedId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateFeedIdMap(Object obj, boolean del) {
        Panel o = (Panel)obj;

		if ( o.getFeedId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from FeedIdToMap
            Map map  = (Map) m_FeedIdToMap.get(new Long(o.getFeedId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("Panel removed from FeedIdToMap" + o.getId() + " from " + o.getFeedId());
        }
        else {
            
            // add to FeedIdToMap
            Map map  = (Map) m_FeedIdToMap.get(new Long(o.getFeedId()));
            if ( map == null ) {
                map = new TreeMap();
                m_FeedIdToMap.put(new Long(o.getFeedId()), map);
                if (m_debug) m_logger.debug("created new   FeedIdToMap for " + o.getFeedId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("Panel added to FeedIdToMap " + o.getId() + " to " + o.getFeedId());
        }
    }


    




        
    public List getBySiteIdColumnNum(long SiteId, int ColumnNum) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdColumnNumToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdColumnNumToMap.get(keySiteId);

     	    Integer keyColumnNum = new Integer(ColumnNum);
            
            if ( mapSiteId.containsKey(keyColumnNum)){
                return new ArrayList( ((Map)mapSiteId.get(keyColumnNum)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdColumnNum(long SiteId, int ColumnNum) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdColumnNumToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdColumnNumToMap.get(keySiteId);

     	    Integer keyColumnNum = new Integer(ColumnNum);
            
            if ( mapSiteId.containsKey(keyColumnNum)){
                return (Map)mapSiteId.get(keyColumnNum);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdColumnNumMap(Object obj, boolean del) {
        Panel o = (Panel)obj;

   	    Integer keyColumnNum = new Integer(o.getColumnNum());

        if (del) {
            // delete from SiteIdColumnNumToMap
            Map mapSiteId  = (Map) m_SiteIdColumnNumToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapColumnNum = (Map) mapSiteId.get(keyColumnNum);
                if (mapColumnNum != null){
                    mapColumnNum.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed Panel from m_SiteIdColumnNumToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getColumnNum());
        }
        else {
            
            // add to SiteIdColumnNumToMap
            Map mapSiteId  = (Map) m_SiteIdColumnNumToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdColumnNumToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapColumnNum = (Map) mapSiteId.get(keyColumnNum);
            
            if ( mapColumnNum == null) {
                mapColumnNum = new TreeMap();
                mapSiteId.put(keyColumnNum, mapColumnNum);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapColumnNum.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdColumnNumToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        







    public static void main(String[] args) throws Exception {

        PanelDS ds = new PanelDS();
        Panel obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static Panel createDefault(){

        Panel ret = new Panel();        
//      ret.setPageId("");           
//      ret.setPageOnly("");           
//      ret.setPageOnlyGroup("");           
//      ret.setColumnNum("");           
//      ret.setContentOnly("");           
//      ret.setPanelType("");           
//      ret.setPanelSubType("");           
//      ret.setPanelTitle("");           
//      ret.setPanelHeight("");           
//      ret.setHide("");           
//      ret.setTimeCreated("");           
//      ret.setTopSpace("");           
//      ret.setBottomSpace("");           
//      ret.setLeftSpace("");           
//      ret.setRightSpace("");           
//      ret.setStyleString("");           
//      ret.setTitleStyleString("");           
//      ret.setStyleString2("");           
//      ret.setAlign("");           
//      ret.setColumnCount("");           
//      ret.setPageDisplaySummary("");           
//      ret.setShowInPrint("");           
//      ret.setShowOnlyPrint("");           
//      ret.setFeedId("");           
        return ret;
    }

    public static Panel copy(Panel org){

    	Panel ret = new Panel();

		ret.setPageId(org.getPageId());
		ret.setPageOnly(org.getPageOnly());
		ret.setPageOnlyGroup(org.getPageOnlyGroup());
		ret.setColumnNum(org.getColumnNum());
		ret.setContentOnly(org.getContentOnly());
		ret.setPanelType(org.getPanelType());
		ret.setPanelSubType(org.getPanelSubType());
		ret.setPanelTitle(org.getPanelTitle());
		ret.setPanelHeight(org.getPanelHeight());
		ret.setHide(org.getHide());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTopSpace(org.getTopSpace());
		ret.setBottomSpace(org.getBottomSpace());
		ret.setLeftSpace(org.getLeftSpace());
		ret.setRightSpace(org.getRightSpace());
		ret.setStyleString(org.getStyleString());
		ret.setTitleStyleString(org.getTitleStyleString());
		ret.setStyleString2(org.getStyleString2());
		ret.setAlign(org.getAlign());
		ret.setColumnCount(org.getColumnCount());
		ret.setPageDisplaySummary(org.getPageDisplaySummary());
		ret.setShowInPrint(org.getShowInPrint());
		ret.setShowOnlyPrint(org.getShowOnlyPrint());
		ret.setFeedId(org.getFeedId());

        return ret;
    }

	public static void objectToLog(Panel panel, Logger logger){
		logger.debug("Panel [" + panel.getId() + "]" + objectToString(panel));		
    }

	public static String objectToString(Panel panel){
		StringBuffer buf = new StringBuffer();
        buf.append("Panel=");
		buf.append("Id=").append(panel.getId()).append(", ");
		buf.append("PageId=").append(panel.getPageId()).append(", ");
		buf.append("ColumnNum=").append(panel.getColumnNum()).append(", ");
		buf.append("PanelType=").append(panel.getPanelType()).append(", ");
		buf.append("Hide=").append(panel.getHide()).append(", ");
		return buf.toString();    
    }
}
