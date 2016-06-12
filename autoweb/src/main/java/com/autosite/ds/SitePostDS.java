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
import com.autosite.db.SitePost;
import com.jtrend.service.DomainStore;

import com.autosite.db.SitePostDAO;

public class SitePostDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PageIdToMap;
    protected Map m_PanelIdToMap;
    protected Map m_ContentIdToMap;

    protected Map m_SiteIdPanelIdToMap;





    private static Logger m_logger = Logger.getLogger(SitePostDS.class);
    private static SitePostDS m_SitePostDS = new SitePostDS();
    public static boolean m_debug = AutositeGlobals.m_debug;

    public static SitePostDS getInstance() {
        return m_SitePostDS;
    }

    public static synchronized SitePostDS getInstance(long id) {
        SitePostDS ret = (SitePostDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SitePostDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SitePostDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SitePostDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SitePost loaded from DB. num=" + m_idToMap.size());
        
    }

    protected SitePostDS() {
        m_dao = new SitePostDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
        m_ContentIdToMap = new ConcurrentHashMap();
        m_SiteIdPanelIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SitePostDS(long id) {
        m_dao = new SitePostDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PageIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
        m_ContentIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SitePost getById(Long id) {
        return (SitePost) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SitePost o = (SitePost)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SitePost removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SitePost added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePageIdMap(obj, del);
        updatePanelIdMap(obj, del);
        updateContentIdMap(obj, del);
        updateSiteIdPanelIdMap(obj, del);
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
        SitePost o = (SitePost)obj;

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
            if (m_debug) m_logger.debug("SitePost removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SitePost added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        SitePost o = (SitePost)obj;

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
            if (m_debug) m_logger.debug("SitePost removed from PageIdToMap" + o.getId() + " from " + o.getPageId());
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
            if (m_debug) m_logger.debug("SitePost added to PageIdToMap " + o.getId() + " to " + o.getPageId());
        }
    }
    // ListKeys - PanelId
    public List getByPanelId(long PanelId) {
        
        Long _PanelId  = new Long(PanelId);
        if (m_PanelIdToMap.containsKey(_PanelId)) {
            return new ArrayList( ((Map)m_PanelIdToMap.get(_PanelId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePanelIdMap(Object obj, boolean del) {
        SitePost o = (SitePost)obj;

        if ( o.getPanelId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
            return;
        }

        if (del) {

            // delete from PanelIdToMap
            Map map  = (Map) m_PanelIdToMap.get(new Long(o.getPanelId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("SitePost removed from PanelIdToMap" + o.getId() + " from " + o.getPanelId());
        }
        else {
            
            // add to PanelIdToMap
            Map map  = (Map) m_PanelIdToMap.get(new Long(o.getPanelId()));
            if ( map == null ) {
                map = new TreeMap();
                m_PanelIdToMap.put(new Long(o.getPanelId()), map);
                if (m_debug) m_logger.debug("created new   PanelIdToMap for " + o.getPanelId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("SitePost added to PanelIdToMap " + o.getId() + " to " + o.getPanelId());
        }
    }
    // ListKeys - ContentId
    public List getByContentId(long ContentId) {
        
        Long _ContentId  = new Long(ContentId);
        if (m_ContentIdToMap.containsKey(_ContentId)) {
            return new ArrayList( ((Map)m_ContentIdToMap.get(_ContentId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateContentIdMap(Object obj, boolean del) {
        SitePost o = (SitePost)obj;

        if ( o.getContentId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
            return;
        }

        if (del) {

            // delete from ContentIdToMap
            Map map  = (Map) m_ContentIdToMap.get(new Long(o.getContentId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("SitePost removed from ContentIdToMap" + o.getId() + " from " + o.getContentId());
        }
        else {
            
            // add to ContentIdToMap
            Map map  = (Map) m_ContentIdToMap.get(new Long(o.getContentId()));
            if ( map == null ) {
                map = new TreeMap();
                m_ContentIdToMap.put(new Long(o.getContentId()), map);
                if (m_debug) m_logger.debug("created new   ContentIdToMap for " + o.getContentId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("SitePost added to ContentIdToMap " + o.getId() + " to " + o.getContentId());
        }
    }


    




        
    public List getBySiteIdPanelId(long SiteId, long PanelId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdPanelIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdPanelIdToMap.get(keySiteId);

            Long keyPanelId = new Long(PanelId);
            
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

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdPanelIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdPanelIdToMap.get(keySiteId);

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



    private void updateSiteIdPanelIdMap(Object obj, boolean del) {
        SitePost o = (SitePost)obj;

        Long keyPanelId = new Long(o.getPanelId());

        if (del) {
            // delete from SiteIdPanelIdToMap
            Map mapSiteId  = (Map) m_SiteIdPanelIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapPanelId = (Map) mapSiteId.get(keyPanelId);
                if (mapPanelId != null){
                    mapPanelId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed SitePost from m_SiteIdPanelIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPanelId());
        }
        else {
            
            // add to SiteIdPanelIdToMap
            Map mapSiteId  = (Map) m_SiteIdPanelIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdPanelIdToMap.put(new Long(o.getSiteId()), mapSiteId);
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
        







    public static void main(String[] args) throws Exception {

        SitePostDS ds = new SitePostDS();
        SitePost obj = ds.getById((long)1);
        System.out.println(obj);

    }

    //
    public static SitePost createDefault(){

        SitePost ret = new SitePost();        
//      ret.setPageId("");           
//      ret.setContentId("");           
//      ret.setPanelId("");           
//      ret.setPostScope("");           
//      ret.setPositionCode("");           
//      ret.setPostType("");           
//      ret.setPostData("");           
//      ret.setPostDataExtra("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
//      ret.setHeight("");           
//      ret.setWidth("");           
//      ret.setStyleString("");           
//      ret.setOption1("");           
//      ret.setOption2("");           
//      ret.setOption3("");           
//      ret.setOption4("");           
//      ret.setOption5("");           
//      ret.setHide("");           
        return ret;
    }

    public static SitePost copy(SitePost org){

        SitePost ret = new SitePost();

        ret.setPageId(org.getPageId());
        ret.setContentId(org.getContentId());
        ret.setPanelId(org.getPanelId());
        ret.setPostScope(org.getPostScope());
        ret.setPositionCode(org.getPositionCode());
        ret.setPostType(org.getPostType());
        ret.setPostData(org.getPostData());
        ret.setPostDataExtra(org.getPostDataExtra());
        ret.setTimeCreated(org.getTimeCreated());
        ret.setTimeUpdated(org.getTimeUpdated());
        ret.setHeight(org.getHeight());
        ret.setWidth(org.getWidth());
        ret.setStyleString(org.getStyleString());
        ret.setOption1(org.getOption1());
        ret.setOption2(org.getOption2());
        ret.setOption3(org.getOption3());
        ret.setOption4(org.getOption4());
        ret.setOption5(org.getOption5());
        ret.setHide(org.getHide());

        return ret;
    }

    public static void objectToLog(SitePost sitePost, Logger logger){
        logger.debug("SitePost [" + sitePost.getId() + "]" + objectToString(sitePost));     
    }

    public static String objectToString(SitePost sitePost){
        StringBuffer buf = new StringBuffer();
        buf.append("SitePost=");
        buf.append("Id=").append(sitePost.getId()).append(", ");
        buf.append("SiteId=").append(sitePost.getSiteId()).append(", ");
        buf.append("PanelId=").append(sitePost.getPanelId()).append(", ");
        return buf.toString();    
    }
}
