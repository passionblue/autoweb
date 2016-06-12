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
import com.autosite.db.SiteSuggest;
import com.jtrend.service.DomainStore;

import com.autosite.db.SiteSuggestDAO;

public class SiteSuggestDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_CategoryToMap;

	protected Map m_SiteIdResolvedToMap;






    private static Logger m_logger = Logger.getLogger(SiteSuggestDS.class);
    private static SiteSuggestDS m_SiteSuggestDS = new SiteSuggestDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SiteSuggestDS getInstance() {
        return m_SiteSuggestDS;
    }

    public static synchronized SiteSuggestDS getInstance(long id) {
        SiteSuggestDS ret = (SiteSuggestDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SiteSuggestDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SiteSuggestDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SiteSuggestDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SiteSuggest loaded from DB. num=" + m_idToMap.size());
        
    }

    protected SiteSuggestDS() {
        m_dao = new SiteSuggestDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_CategoryToMap = new ConcurrentHashMap();
		m_SiteIdResolvedToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SiteSuggestDS(long id) {
        m_dao = new SiteSuggestDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_CategoryToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SiteSuggest getById(Long id) {
        return (SiteSuggest) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SiteSuggest o = (SiteSuggest)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SiteSuggest removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SiteSuggest added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateCategoryMap(obj, del);
		updateSiteIdResolvedMap(obj, del);
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
        SiteSuggest o = (SiteSuggest)obj;

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
            if (m_debug) m_logger.debug("SiteSuggest removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SiteSuggest added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


	// ListStringKeys - Category

    public List getByCategory(String keyCategory) {
        
        if (m_CategoryToMap.containsKey(keyCategory)) {
            return new ArrayList( ((Map)m_CategoryToMap.get(keyCategory)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateCategoryMap(Object obj, boolean del) {
        SiteSuggest o = (SiteSuggest)obj;

        if (del) {

            // delete from CategoryToMap
            Map map  = (Map) m_CategoryToMap.get(o.getCategory());
            if ( map != null ) {
                map.remove(new Long(o.getId()));
                if (m_debug) m_logger.debug("SiteSuggest removed from CategoryToMap" + o.getId() + " from [" + o.getCategory() + "]");
            } else {
                if (m_debug) m_logger.debug("SiteSuggest NOT removed from CategoryToMap because no map found for [" + o.getCategory() + "]");
            }
        }
        else {
            
            // add to CategoryToMap
            Map map  = (Map) m_CategoryToMap.get(o.getCategory());
            if ( map == null ) {
                map = new TreeMap();
                m_CategoryToMap.put(o.getCategory(), map);
                if (m_debug) m_logger.debug("created new   CategoryToMap for [" + o.getCategory() + "]");
            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("SiteSuggest added to CategoryToMap " + o.getId() + " to [" + o.getCategory() + "]");
        }
    }

    




        
    public List getBySiteIdResolved(long SiteId, int Resolved) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdResolvedToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdResolvedToMap.get(keySiteId);

     	    Integer keyResolved = new Integer(Resolved);
            
            if ( mapSiteId.containsKey(keyResolved)){
                return new ArrayList( ((Map)mapSiteId.get(keyResolved)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdResolved(long SiteId, int Resolved) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdResolvedToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdResolvedToMap.get(keySiteId);

     	    Integer keyResolved = new Integer(Resolved);
            
            if ( mapSiteId.containsKey(keyResolved)){
                return (Map)mapSiteId.get(keyResolved);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdResolvedMap(Object obj, boolean del) {
        SiteSuggest o = (SiteSuggest)obj;

   	    Integer keyResolved = new Integer(o.getResolved());

        if (del) {
            // delete from SiteIdResolvedToMap
            Map mapSiteId  = (Map) m_SiteIdResolvedToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapResolved = (Map) mapSiteId.get(keyResolved);
                if (mapResolved != null){
                    mapResolved.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed SiteSuggest from m_SiteIdResolvedToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getResolved());
        }
        else {
            
            // add to SiteIdResolvedToMap
            Map mapSiteId  = (Map) m_SiteIdResolvedToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdResolvedToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapResolved = (Map) mapSiteId.get(keyResolved);
            
            if ( mapResolved == null) {
                mapResolved = new TreeMap();
                mapSiteId.put(keyResolved, mapResolved);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapResolved.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdResolvedToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        










    public static void main(String[] args) throws Exception {

        SiteSuggestDS ds = new SiteSuggestDS();
        SiteSuggest obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SiteSuggest createDefault(){

        SiteSuggest ret = new SiteSuggest();        
//      ret.setCategory("");           
//      ret.setSuggest("");           
//      ret.setResolved("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static SiteSuggest copy(SiteSuggest org){

    	SiteSuggest ret = new SiteSuggest();

		ret.setCategory(org.getCategory());
		ret.setSuggest(org.getSuggest());
		ret.setResolved(org.getResolved());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(SiteSuggest siteSuggest, Logger logger){
		logger.debug("SiteSuggest [" + siteSuggest.getId() + "]" + objectToString(siteSuggest));		
    }

	public static String objectToString(SiteSuggest siteSuggest){
		StringBuffer buf = new StringBuffer();
        buf.append("SiteSuggest=");
		buf.append("Id=").append(siteSuggest.getId()).append(", ");
		buf.append("SiteId=").append(siteSuggest.getSiteId()).append(", ");
		buf.append("Category=").append(siteSuggest.getCategory()).append(", ");
		buf.append("Resolved=").append(siteSuggest.getResolved()).append(", ");
		return buf.toString();    
    }
}
