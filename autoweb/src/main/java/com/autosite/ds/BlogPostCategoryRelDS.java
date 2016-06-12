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
import com.autosite.db.BlogPostCategoryRel;
import com.jtrend.service.DomainStore;

import com.autosite.db.BlogPostCategoryRelDAO;

public class BlogPostCategoryRelDS extends AbstractDS implements DomainStore {

    protected Map m_BlogPostIdToMap;


	protected Map m_BlogPostIdBlogCategoryIdToOneMap;

    private static Logger m_logger = Logger.getLogger(BlogPostCategoryRelDS.class);
    private static BlogPostCategoryRelDS m_BlogPostCategoryRelDS = new BlogPostCategoryRelDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static BlogPostCategoryRelDS getInstance() {
        return m_BlogPostCategoryRelDS;
    }

    public static synchronized BlogPostCategoryRelDS getInstance(long id) {
        BlogPostCategoryRelDS ret = (BlogPostCategoryRelDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new BlogPostCategoryRelDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_BlogPostCategoryRelDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((BlogPostCategoryRelDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected BlogPostCategoryRelDS() {
        m_dao = new BlogPostCategoryRelDAO();
        m_idToMap = new ConcurrentHashMap();

        m_BlogPostIdToMap = new ConcurrentHashMap();
		m_BlogPostIdBlogCategoryIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected BlogPostCategoryRelDS(long id) {
        m_dao = new BlogPostCategoryRelDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_BlogPostIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public BlogPostCategoryRel getById(Long id) {
        return (BlogPostCategoryRel) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        BlogPostCategoryRel o = (BlogPostCategoryRel)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("BlogPostCategoryRel removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("BlogPostCategoryRel added to DS " + o.getId());
        }

        updateBlogPostIdMap(obj, del);


		updateBlogPostIdBlogCategoryIdOneMap(obj, del);

    }


    public List getByBlogPostId(long BlogPostId) {
        
        Long _BlogPostId  = new Long(BlogPostId);
        if (m_BlogPostIdToMap.containsKey(_BlogPostId)) {
            return new ArrayList( ((Map)m_BlogPostIdToMap.get(_BlogPostId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateBlogPostIdMap(Object obj, boolean del) {
        BlogPostCategoryRel o = (BlogPostCategoryRel)obj;

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
            if (m_debug) m_logger.debug("BlogPostCategoryRel removed from BlogPostIdToMap" + o.getId() + " from " + o.getBlogPostId());
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
            if (m_debug) m_logger.debug("BlogPostCategoryRel added to BlogPostIdToMap " + o.getId() + " to " + o.getBlogPostId());
        }
    }


    





        
    public  BlogPostCategoryRel getByBlogPostIdBlogCategoryId(long BlogPostId, long BlogCategoryId) {

        Long keyBlogPostId  = new Long(BlogPostId);
        if (m_BlogPostIdBlogCategoryIdToOneMap.containsKey(keyBlogPostId)) {
            
            Map mapBlogPostId = (Map)m_BlogPostIdBlogCategoryIdToOneMap.get(keyBlogPostId);

     	    Long keyBlogCategoryId = new Long(BlogCategoryId);
            
            if ( mapBlogPostId.containsKey(keyBlogCategoryId)){
                return ( BlogPostCategoryRel)mapBlogPostId.get(keyBlogCategoryId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateBlogPostIdBlogCategoryIdOneMap(Object obj, boolean del) {
        BlogPostCategoryRel o = (BlogPostCategoryRel)obj;

     	    Long keyBlogCategoryId = new Long(o.getBlogCategoryId());

        if (del) {
            // delete from BlogPostIdBlogCategoryIdToOneMap
            Map mapBlogPostId  = (Map) m_BlogPostIdBlogCategoryIdToOneMap.get(new Long(o.getBlogPostId()));
            if ( mapBlogPostId != null ) {
                if (mapBlogPostId.containsKey(keyBlogCategoryId)){
                    mapBlogPostId.remove(keyBlogCategoryId);
                }
            }
            m_logger.debug("removed from m_BlogPostIdBlogCategoryIdToOneMap" + o.getId() + " from " + o.getBlogPostId() + " # " + o.getBlogCategoryId());
        }
        else {
            
            // add to BlogPostIdBlogCategoryIdToOneMap
            Map mapBlogPostId  = (Map) m_BlogPostIdBlogCategoryIdToOneMap.get(new Long(o.getBlogPostId()));
            if ( mapBlogPostId == null ) {
                mapBlogPostId = new TreeMap();
                m_BlogPostIdBlogCategoryIdToOneMap.put(new Long(o.getBlogPostId()), mapBlogPostId);
                m_logger.debug("created new   mapBlogPostId for " + o.getBlogPostId());
            }
            
            
			BlogPostCategoryRel replaced = (BlogPostCategoryRel) mapBlogPostId.put(keyBlogCategoryId,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to BlogPostIdBlogCategoryIdToOneMap " + o.getId() + " to " + o.getBlogPostId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        BlogPostCategoryRelDS ds = new BlogPostCategoryRelDS();
        BlogPostCategoryRel obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static BlogPostCategoryRel createDefault(){

        BlogPostCategoryRel ret = new BlogPostCategoryRel();        
//      ret.setBlogPostId("");           
//      ret.setBlogCategoryId("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static BlogPostCategoryRel copy(BlogPostCategoryRel org){

    	BlogPostCategoryRel ret = new BlogPostCategoryRel();

		ret.setBlogPostId(org.getBlogPostId());
		ret.setBlogCategoryId(org.getBlogCategoryId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(BlogPostCategoryRel blogPostCategoryRel, Logger logger){
		logger.debug("BlogPostCategoryRel [" + blogPostCategoryRel.getId() + "]" + objectToString(blogPostCategoryRel));		
    }

	public static String objectToString(BlogPostCategoryRel blogPostCategoryRel){
		StringBuffer buf = new StringBuffer();
        buf.append("BlogPostCategoryRel=");
		buf.append("Id=").append(blogPostCategoryRel.getId()).append(", ");
		buf.append("SiteId=").append(blogPostCategoryRel.getSiteId()).append(", ");
		return buf.toString();    
    }
}
