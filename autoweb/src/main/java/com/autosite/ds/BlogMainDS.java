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
import com.autosite.db.BlogMain;
import com.jtrend.service.DomainStore;

import com.autosite.db.BlogMainDAO;

public class BlogMainDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;


	protected Map m_SiteIdBlogNameToOneMap;

    private static Logger m_logger = Logger.getLogger(BlogMainDS.class);
    private static BlogMainDS m_BlogMainDS = new BlogMainDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static BlogMainDS getInstance() {
        return m_BlogMainDS;
    }

    public static synchronized BlogMainDS getInstance(long id) {
        BlogMainDS ret = (BlogMainDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new BlogMainDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_BlogMainDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((BlogMainDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected BlogMainDS() {
        m_dao = new BlogMainDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdBlogNameToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected BlogMainDS(long id) {
        m_dao = new BlogMainDAO();
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

    public BlogMain getById(Long id) {
        return (BlogMain) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        BlogMain o = (BlogMain)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("BlogMain removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("BlogMain added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);


		updateSiteIdBlogNameOneMap(obj, del);

    }


    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        BlogMain o = (BlogMain)obj;

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
            if (m_debug) m_logger.debug("BlogMain removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("BlogMain added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    





        
    public  BlogMain getBySiteIdBlogName(long SiteId, String BlogName) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdBlogNameToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdBlogNameToOneMap.get(keySiteId);

     	    String keyBlogName =  BlogName;
            if (  keyBlogName == null) return null;
            
            if ( mapSiteId.containsKey(keyBlogName)){
                return ( BlogMain)mapSiteId.get(keyBlogName);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdBlogNameOneMap(Object obj, boolean del) {
        BlogMain o = (BlogMain)obj;

     	    String keyBlogName =  o.getBlogName();
            if ( keyBlogName == null) return;

        if (del) {
            // delete from SiteIdBlogNameToOneMap
            Map mapSiteId  = (Map) m_SiteIdBlogNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyBlogName)){
                    mapSiteId.remove(keyBlogName);
                }
            }
            m_logger.debug("removed from m_SiteIdBlogNameToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getBlogName());
        }
        else {
            
            // add to SiteIdBlogNameToOneMap
            Map mapSiteId  = (Map) m_SiteIdBlogNameToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdBlogNameToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            
            
			BlogMain replaced = (BlogMain) mapSiteId.put(keyBlogName,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdBlogNameToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        BlogMainDS ds = new BlogMainDS();
        BlogMain obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static BlogMain createDefault(){

        BlogMain ret = new BlogMain();        
//      ret.setUserId("");           
//      ret.setBlogName("");           
//      ret.setBlogTitle("");           
//      ret.setTimeCreated("");           
//      ret.setMainDesignSelect("");           
//      ret.setUseCustomDesign("");           
//      ret.setCustomDesignFile("");           
        return ret;
    }

    public static BlogMain copy(BlogMain org){

    	BlogMain ret = new BlogMain();

		ret.setUserId(org.getUserId());
		ret.setBlogName(org.getBlogName());
		ret.setBlogTitle(org.getBlogTitle());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setMainDesignSelect(org.getMainDesignSelect());
		ret.setUseCustomDesign(org.getUseCustomDesign());
		ret.setCustomDesignFile(org.getCustomDesignFile());

        return ret;
    }

	public static void objectToLog(BlogMain blogMain, Logger logger){
		logger.debug("BlogMain [" + blogMain.getId() + "]" + objectToString(blogMain));		
    }

	public static String objectToString(BlogMain blogMain){
		StringBuffer buf = new StringBuffer();
        buf.append("BlogMain=");
		buf.append("Id=").append(blogMain.getId()).append(", ");
		buf.append("SiteId=").append(blogMain.getSiteId()).append(", ");
		buf.append("BlogName=").append(blogMain.getBlogName()).append(", ");
		return buf.toString();    
    }
}
