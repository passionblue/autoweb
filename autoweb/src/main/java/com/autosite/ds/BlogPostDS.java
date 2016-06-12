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
import com.autosite.db.BlogPost;
import com.jtrend.service.DomainStore;

import com.autosite.db.BlogPostDAO;

public class BlogPostDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_BlogMainIdToMap;
    protected Map m_CategoryIdToMap;

    protected Map m_BlogMainIdPostYearmonthToMap;


    private static Logger m_logger = Logger.getLogger(BlogPostDS.class);
    private static BlogPostDS m_BlogPostDS = new BlogPostDS();
    public static boolean m_debug = AutositeGlobals.m_debug;

    public static BlogPostDS getInstance() {
        return m_BlogPostDS;
    }

    public static synchronized BlogPostDS getInstance(long id) {
        BlogPostDS ret = (BlogPostDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new BlogPostDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_BlogPostDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((BlogPostDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected BlogPostDS() {
        m_dao = new BlogPostDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogMainIdToMap = new ConcurrentHashMap();
        m_CategoryIdToMap = new ConcurrentHashMap();
        m_BlogMainIdPostYearmonthToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected BlogPostDS(long id) {
        m_dao = new BlogPostDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogMainIdToMap = new ConcurrentHashMap();
        m_CategoryIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public BlogPost getById(Long id) {
        return (BlogPost) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        BlogPost o = (BlogPost)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("BlogPost removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("BlogPost added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateBlogMainIdMap(obj, del);
        updateCategoryIdMap(obj, del);

        updateBlogMainIdPostYearmonthMap(obj, del);


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
        BlogPost o = (BlogPost)obj;

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
            if (m_debug) m_logger.debug("BlogPost removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("BlogPost added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }

    public List getByBlogMainId(long BlogMainId) {
        
        Long _BlogMainId  = new Long(BlogMainId);
        if (m_BlogMainIdToMap.containsKey(_BlogMainId)) {
            return new ArrayList( ((Map)m_BlogMainIdToMap.get(_BlogMainId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateBlogMainIdMap(Object obj, boolean del) {
        BlogPost o = (BlogPost)obj;

        if ( o.getBlogMainId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
            return;
        }

        if (del) {

            // delete from BlogMainIdToMap
            Map map  = (Map) m_BlogMainIdToMap.get(new Long(o.getBlogMainId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("BlogPost removed from BlogMainIdToMap" + o.getId() + " from " + o.getBlogMainId());
        }
        else {
            
            // add to BlogMainIdToMap
            Map map  = (Map) m_BlogMainIdToMap.get(new Long(o.getBlogMainId()));
            if ( map == null ) {
                map = new TreeMap();
                m_BlogMainIdToMap.put(new Long(o.getBlogMainId()), map);
                if (m_debug) m_logger.debug("created new   BlogMainIdToMap for " + o.getBlogMainId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("BlogPost added to BlogMainIdToMap " + o.getId() + " to " + o.getBlogMainId());
        }
    }

    public List getByCategoryId(long CategoryId) {
        
        Long _CategoryId  = new Long(CategoryId);
        if (m_CategoryIdToMap.containsKey(_CategoryId)) {
            return new ArrayList( ((Map)m_CategoryIdToMap.get(_CategoryId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateCategoryIdMap(Object obj, boolean del) {
        BlogPost o = (BlogPost)obj;

        if ( o.getCategoryId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
            return;
        }

        if (del) {

            // delete from CategoryIdToMap
            Map map  = (Map) m_CategoryIdToMap.get(new Long(o.getCategoryId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("BlogPost removed from CategoryIdToMap" + o.getId() + " from " + o.getCategoryId());
        }
        else {
            
            // add to CategoryIdToMap
            Map map  = (Map) m_CategoryIdToMap.get(new Long(o.getCategoryId()));
            if ( map == null ) {
                map = new TreeMap();
                m_CategoryIdToMap.put(new Long(o.getCategoryId()), map);
                if (m_debug) m_logger.debug("created new   CategoryIdToMap for " + o.getCategoryId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("BlogPost added to CategoryIdToMap " + o.getId() + " to " + o.getCategoryId());
        }
    }


    




        
    public List getByBlogMainIdPostYearmonth(long BlogMainId, int PostYearmonth) {

        Long keyBlogMainId  = new Long(BlogMainId);
        if (m_BlogMainIdPostYearmonthToMap.containsKey(keyBlogMainId)) {
            
            Map mapBlogMainId = (Map)m_BlogMainIdPostYearmonthToMap.get(keyBlogMainId);

            Integer keyPostYearmonth = new Integer(PostYearmonth);
            
            if ( mapBlogMainId.containsKey(keyPostYearmonth)){
                return new ArrayList( ((Map)mapBlogMainId.get(keyPostYearmonth)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }   

    public Map getMapByBlogMainIdPostYearmonth(long BlogMainId, int PostYearmonth) {

        Long keyBlogMainId  = new Long(BlogMainId);
        if (m_BlogMainIdPostYearmonthToMap.containsKey(keyBlogMainId)) {
            
            Map mapBlogMainId = (Map)m_BlogMainIdPostYearmonthToMap.get(keyBlogMainId);

            Integer keyPostYearmonth = new Integer(PostYearmonth);
            
            if ( mapBlogMainId.containsKey(keyPostYearmonth)){
                return (Map)mapBlogMainId.get(keyPostYearmonth);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }   



    private void updateBlogMainIdPostYearmonthMap(Object obj, boolean del) {
        BlogPost o = (BlogPost)obj;

        Integer keyPostYearmonth = new Integer(o.getPostYearmonth());

        if (del) {
            // delete from BlogMainIdPostYearmonthToMap
            Map mapBlogMainId  = (Map) m_BlogMainIdPostYearmonthToMap.get(new Long(o.getBlogMainId()));
            if ( mapBlogMainId != null ) {
                Map mapPostYearmonth = (Map) mapBlogMainId.get(keyPostYearmonth);
                if (mapPostYearmonth != null){
                    mapPostYearmonth.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed from m_BlogMainIdPostYearmonthToMap" + o.getId() + " from " + o.getBlogMainId() + " # " + o.getPostYearmonth());
        }
        else {
            
            // add to BlogMainIdPostYearmonthToMap
            Map mapBlogMainId  = (Map) m_BlogMainIdPostYearmonthToMap.get(new Long(o.getBlogMainId()));
            if ( mapBlogMainId == null ) {
                mapBlogMainId = new TreeMap();
                m_BlogMainIdPostYearmonthToMap.put(new Long(o.getBlogMainId()), mapBlogMainId);
                m_logger.debug("created new   mapBlogMainId for " + o.getBlogMainId());
            }
            Map mapPostYearmonth = (Map) mapBlogMainId.get(keyPostYearmonth);
            
            if ( mapPostYearmonth == null) {
                mapPostYearmonth = new TreeMap();
                mapBlogMainId.put(keyPostYearmonth, mapPostYearmonth);
                m_logger.debug("created new   mapBlogMainId for " + o.getBlogMainId());
            }
            mapPostYearmonth.put(new Long(o.getId()), o);            
            
            m_logger.debug("Added to BlogMainIdPostYearmonthToMap " + o.getId() + " to " + o.getBlogMainId());
        }
        
    }    
        





    public static void main(String[] args) throws Exception {

        BlogPostDS ds = new BlogPostDS();
        BlogPost obj = ds.getById((long)1);
        System.out.println(obj);

    }

    //
    public static BlogPost createDefault(){

        BlogPost ret = new BlogPost();        
//      ret.setBlogMainId("");           
//      ret.setCategoryId("");           
//      ret.setSubject("");           
//      ret.setContent("");           
//      ret.setPostType("");           
//      ret.setAuthor("");           
//      ret.setContentImage("");           
//      ret.setImageUrl1("");           
//      ret.setImageUrl2("");           
//      ret.setTags("");           
//      ret.setShorcutUrl("");           
//      ret.setHide("");           
//      ret.setPostYear("");           
//      ret.setPostMonth("");           
//      ret.setPostDay("");           
//      ret.setPostYearmonth("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static BlogPost copy(BlogPost org){

        BlogPost ret = new BlogPost();

        ret.setBlogMainId(org.getBlogMainId());
        ret.setCategoryId(org.getCategoryId());
        ret.setSubject(org.getSubject());
        ret.setContent(org.getContent());
        ret.setPostType(org.getPostType());
        ret.setAuthor(org.getAuthor());
        ret.setContentImage(org.getContentImage());
        ret.setImageUrl1(org.getImageUrl1());
        ret.setImageUrl2(org.getImageUrl2());
        ret.setTags(org.getTags());
        ret.setShorcutUrl(org.getShorcutUrl());
        ret.setHide(org.getHide());
        ret.setPostYear(org.getPostYear());
        ret.setPostMonth(org.getPostMonth());
        ret.setPostDay(org.getPostDay());
        ret.setPostYearmonth(org.getPostYearmonth());
        ret.setTimeCreated(org.getTimeCreated());
        ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

    public static void objectToLog(BlogPost blogPost, Logger logger){
        logger.debug("BlogPost [" + blogPost.getId() + "]" + objectToString(blogPost));     
    }

    public static String objectToString(BlogPost blogPost){
        StringBuffer buf = new StringBuffer();
        buf.append("BlogPost=");
        buf.append("Id=").append(blogPost.getId()).append(", ");
        buf.append("SiteId=").append(blogPost.getSiteId()).append(", ");
        return buf.toString();    
    }
}
