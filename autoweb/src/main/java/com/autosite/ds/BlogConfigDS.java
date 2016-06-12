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
import com.autosite.db.BlogConfig;
import com.jtrend.service.DomainStore;

import com.autosite.db.BlogConfigDAO;

public class BlogConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_BlogMainIdToOneMap;



    private static Logger m_logger = Logger.getLogger(BlogConfigDS.class);
    private static BlogConfigDS m_BlogConfigDS = new BlogConfigDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static BlogConfigDS getInstance() {
        return m_BlogConfigDS;
    }

    public static synchronized BlogConfigDS getInstance(long id) {
        BlogConfigDS ret = (BlogConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new BlogConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_BlogConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((BlogConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected BlogConfigDS() {
        m_dao = new BlogConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogMainIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected BlogConfigDS(long id) {
        m_dao = new BlogConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogMainIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public BlogConfig getById(Long id) {
        return (BlogConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        BlogConfig o = (BlogConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("BlogConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("BlogConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateBlogMainIdOneMap(obj, del);



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
        BlogConfig o = (BlogConfig)obj;

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
            if (m_debug) m_logger.debug("BlogConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("BlogConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public BlogConfig getObjectByBlogMainId(long keyBlogMainId) {
        return (BlogConfig)m_BlogMainIdToOneMap.get(new Long(keyBlogMainId));
    }

    private void updateBlogMainIdOneMap(Object obj, boolean del) {
        BlogConfig o = (BlogConfig)obj;
        Long _BlogMainId = new Long(o.getBlogMainId());

        if (del) {
            // delete from BlogMainIdToOneMap

            if (m_BlogMainIdToOneMap.containsKey(_BlogMainId)){
                m_BlogMainIdToOneMap.remove(_BlogMainId);
                if (m_debug) m_logger.debug("BlogConfig removed from BlogMainIdToMap" + o.getId() + " for [" + _BlogMainId+ "]");
            } else {
                if (m_debug) m_logger.debug("BlogConfig not removed from BlogMainIdToMap" + o.getId() + " for [" + _BlogMainId+ "]. Does not exist");
            } 
        }
        else {
            if (m_BlogMainIdToOneMap.containsKey(_BlogMainId)){
                if (m_debug) m_logger.debug("BlogConfig repalced BlogMainIdToMap" + o.getId() + " for [" + _BlogMainId+ "]");
            } else {
                if (m_debug) m_logger.debug("BlogConfig added to BlogMainIdToMap" + o.getId() + " for [" + _BlogMainId+ "]");
            } 
            m_BlogMainIdToOneMap.put(_BlogMainId, o);
        }
    }








    public static void main(String[] args) throws Exception {

        BlogConfigDS ds = new BlogConfigDS();
        BlogConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static BlogConfig createDefault(){

        BlogConfig ret = new BlogConfig();        
//      ret.setBlogMainId("");           
        return ret;
    }

    public static BlogConfig copy(BlogConfig org){

    	BlogConfig ret = new BlogConfig();

		ret.setBlogMainId(org.getBlogMainId());

        return ret;
    }

	public static void objectToLog(BlogConfig blogConfig, Logger logger){
		logger.debug("BlogConfig [" + blogConfig.getId() + "]" + objectToString(blogConfig));		
    }

	public static String objectToString(BlogConfig blogConfig){
		StringBuffer buf = new StringBuffer();
        buf.append("BlogConfig=");
		buf.append("Id=").append(blogConfig.getId()).append(", ");
		buf.append("SiteId=").append(blogConfig.getSiteId()).append(", ");
		return buf.toString();    
    }
}
