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
import com.autosite.db.BlogComment;
import com.jtrend.service.DomainStore;

import com.autosite.db.BlogCommentDAO;

public class BlogCommentDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_BlogPostIdToMap;

	protected Map m_SiteIdBlogPostIdToMap;






    private static Logger m_logger = Logger.getLogger(BlogCommentDS.class);
    private static BlogCommentDS m_BlogCommentDS = new BlogCommentDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static BlogCommentDS getInstance() {
        return m_BlogCommentDS;
    }

    public static synchronized BlogCommentDS getInstance(long id) {
        BlogCommentDS ret = (BlogCommentDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new BlogCommentDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_BlogCommentDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((BlogCommentDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("BlogComment loaded from DB. num=" + m_idToMap.size());
        
    }

    protected BlogCommentDS() {
        m_dao = new BlogCommentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogPostIdToMap = new ConcurrentHashMap();
		m_SiteIdBlogPostIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected BlogCommentDS(long id) {
        m_dao = new BlogCommentDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogPostIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public BlogComment getById(Long id) {
        return (BlogComment) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        BlogComment o = (BlogComment)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("BlogComment removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("BlogComment added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateBlogPostIdMap(obj, del);
		updateSiteIdBlogPostIdMap(obj, del);
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
        BlogComment o = (BlogComment)obj;

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
            if (m_debug) m_logger.debug("BlogComment removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("BlogComment added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - BlogPostId
    public List getByBlogPostId(long BlogPostId) {
        
        Long _BlogPostId  = new Long(BlogPostId);
        if (m_BlogPostIdToMap.containsKey(_BlogPostId)) {
            return new ArrayList( ((Map)m_BlogPostIdToMap.get(_BlogPostId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateBlogPostIdMap(Object obj, boolean del) {
        BlogComment o = (BlogComment)obj;

		if ( o.getBlogPostId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from BlogPostIdToMap
            Map map  = (Map) m_BlogPostIdToMap.get(new Long(o.getBlogPostId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("BlogComment removed from BlogPostIdToMap" + o.getId() + " from " + o.getBlogPostId());
        }
        else {
            
            // add to BlogPostIdToMap
            Map map  = (Map) m_BlogPostIdToMap.get(new Long(o.getBlogPostId()));
            if ( map == null ) {
                map = new TreeMap();
                m_BlogPostIdToMap.put(new Long(o.getBlogPostId()), map);
                if (m_debug) m_logger.debug("created new   BlogPostIdToMap for " + o.getBlogPostId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("BlogComment added to BlogPostIdToMap " + o.getId() + " to " + o.getBlogPostId());
        }
    }


    




        
    public List getBySiteIdBlogPostId(long SiteId, long BlogPostId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdBlogPostIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdBlogPostIdToMap.get(keySiteId);

     	    Long keyBlogPostId = new Long(BlogPostId);
            
            if ( mapSiteId.containsKey(keyBlogPostId)){
                return new ArrayList( ((Map)mapSiteId.get(keyBlogPostId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdBlogPostId(long SiteId, long BlogPostId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdBlogPostIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdBlogPostIdToMap.get(keySiteId);

     	    Long keyBlogPostId = new Long(BlogPostId);
            
            if ( mapSiteId.containsKey(keyBlogPostId)){
                return (Map)mapSiteId.get(keyBlogPostId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdBlogPostIdMap(Object obj, boolean del) {
        BlogComment o = (BlogComment)obj;

   	    Long keyBlogPostId = new Long(o.getBlogPostId());

        if (del) {
            // delete from SiteIdBlogPostIdToMap
            Map mapSiteId  = (Map) m_SiteIdBlogPostIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapBlogPostId = (Map) mapSiteId.get(keyBlogPostId);
                if (mapBlogPostId != null){
                    mapBlogPostId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed BlogComment from m_SiteIdBlogPostIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getBlogPostId());
        }
        else {
            
            // add to SiteIdBlogPostIdToMap
            Map mapSiteId  = (Map) m_SiteIdBlogPostIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdBlogPostIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapBlogPostId = (Map) mapSiteId.get(keyBlogPostId);
            
            if ( mapBlogPostId == null) {
                mapBlogPostId = new TreeMap();
                mapSiteId.put(keyBlogPostId, mapBlogPostId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapBlogPostId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdBlogPostIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        










    public static void main(String[] args) throws Exception {

        BlogCommentDS ds = new BlogCommentDS();
        BlogComment obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static BlogComment createDefault(){

        BlogComment ret = new BlogComment();        
//      ret.setBlogMainId("");           
//      ret.setBlogPostId("");           
//      ret.setComment("");           
//      ret.setRating("");           
//      ret.setIpaddress("");           
//      ret.setName("");           
//      ret.setPassword("");           
//      ret.setWebsite("");           
//      ret.setEmail("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static BlogComment copy(BlogComment org){

    	BlogComment ret = new BlogComment();

		ret.setBlogMainId(org.getBlogMainId());
		ret.setBlogPostId(org.getBlogPostId());
		ret.setComment(org.getComment());
		ret.setRating(org.getRating());
		ret.setIpaddress(org.getIpaddress());
		ret.setName(org.getName());
		ret.setPassword(org.getPassword());
		ret.setWebsite(org.getWebsite());
		ret.setEmail(org.getEmail());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(BlogComment blogComment, Logger logger){
		logger.debug("BlogComment [" + blogComment.getId() + "]" + objectToString(blogComment));		
    }

	public static String objectToString(BlogComment blogComment){
		StringBuffer buf = new StringBuffer();
        buf.append("BlogComment=");
		buf.append("SiteId=").append(blogComment.getSiteId()).append(", ");
		buf.append("BlogMainId=").append(blogComment.getBlogMainId()).append(", ");
		buf.append("BlogPostId=").append(blogComment.getBlogPostId()).append(", ");
		return buf.toString();    
    }
}
