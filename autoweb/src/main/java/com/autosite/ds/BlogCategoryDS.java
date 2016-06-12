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
import com.autosite.db.BlogCategory;
import com.jtrend.service.DomainStore;

import com.autosite.db.BlogCategoryDAO;

public class BlogCategoryDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_BlogMainIdToMap;
    protected Map m_ParentCategoryIdToMap;
    protected Map m_RootCategoryIdToMap;



    private static Logger m_logger = Logger.getLogger(BlogCategoryDS.class);
    private static BlogCategoryDS m_BlogCategoryDS = new BlogCategoryDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static BlogCategoryDS getInstance() {
        return m_BlogCategoryDS;
    }

    public static synchronized BlogCategoryDS getInstance(long id) {
        BlogCategoryDS ret = (BlogCategoryDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new BlogCategoryDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_BlogCategoryDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((BlogCategoryDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected BlogCategoryDS() {
        m_dao = new BlogCategoryDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogMainIdToMap = new ConcurrentHashMap();
        m_ParentCategoryIdToMap = new ConcurrentHashMap();
        m_RootCategoryIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected BlogCategoryDS(long id) {
        m_dao = new BlogCategoryDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_BlogMainIdToMap = new ConcurrentHashMap();
        m_ParentCategoryIdToMap = new ConcurrentHashMap();
        m_RootCategoryIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public BlogCategory getById(Long id) {
        return (BlogCategory) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        BlogCategory o = (BlogCategory)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("BlogCategory removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("BlogCategory added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateBlogMainIdMap(obj, del);
        updateParentCategoryIdMap(obj, del);
        updateRootCategoryIdMap(obj, del);



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
        BlogCategory o = (BlogCategory)obj;

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
            if (m_debug) m_logger.debug("BlogCategory removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("BlogCategory added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        BlogCategory o = (BlogCategory)obj;

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
            if (m_debug) m_logger.debug("BlogCategory removed from BlogMainIdToMap" + o.getId() + " from " + o.getBlogMainId());
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
            if (m_debug) m_logger.debug("BlogCategory added to BlogMainIdToMap " + o.getId() + " to " + o.getBlogMainId());
        }
    }

    public List getByParentCategoryId(long ParentCategoryId) {
        
        Long _ParentCategoryId  = new Long(ParentCategoryId);
        if (m_ParentCategoryIdToMap.containsKey(_ParentCategoryId)) {
            return new ArrayList( ((Map)m_ParentCategoryIdToMap.get(_ParentCategoryId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateParentCategoryIdMap(Object obj, boolean del) {
        BlogCategory o = (BlogCategory)obj;

		if ( o.getParentCategoryId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from ParentCategoryIdToMap
            Map map  = (Map) m_ParentCategoryIdToMap.get(new Long(o.getParentCategoryId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("BlogCategory removed from ParentCategoryIdToMap" + o.getId() + " from " + o.getParentCategoryId());
        }
        else {
            
            // add to ParentCategoryIdToMap
            Map map  = (Map) m_ParentCategoryIdToMap.get(new Long(o.getParentCategoryId()));
            if ( map == null ) {
                map = new TreeMap();
                m_ParentCategoryIdToMap.put(new Long(o.getParentCategoryId()), map);
                if (m_debug) m_logger.debug("created new   ParentCategoryIdToMap for " + o.getParentCategoryId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("BlogCategory added to ParentCategoryIdToMap " + o.getId() + " to " + o.getParentCategoryId());
        }
    }

    public List getByRootCategoryId(long RootCategoryId) {
        
        Long _RootCategoryId  = new Long(RootCategoryId);
        if (m_RootCategoryIdToMap.containsKey(_RootCategoryId)) {
            return new ArrayList( ((Map)m_RootCategoryIdToMap.get(_RootCategoryId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateRootCategoryIdMap(Object obj, boolean del) {
        BlogCategory o = (BlogCategory)obj;

		if ( o.getRootCategoryId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from RootCategoryIdToMap
            Map map  = (Map) m_RootCategoryIdToMap.get(new Long(o.getRootCategoryId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("BlogCategory removed from RootCategoryIdToMap" + o.getId() + " from " + o.getRootCategoryId());
        }
        else {
            
            // add to RootCategoryIdToMap
            Map map  = (Map) m_RootCategoryIdToMap.get(new Long(o.getRootCategoryId()));
            if ( map == null ) {
                map = new TreeMap();
                m_RootCategoryIdToMap.put(new Long(o.getRootCategoryId()), map);
                if (m_debug) m_logger.debug("created new   RootCategoryIdToMap for " + o.getRootCategoryId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("BlogCategory added to RootCategoryIdToMap " + o.getId() + " to " + o.getRootCategoryId());
        }
    }


    









    public static void main(String[] args) throws Exception {

        BlogCategoryDS ds = new BlogCategoryDS();
        BlogCategory obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static BlogCategory createDefault(){

        BlogCategory ret = new BlogCategory();        
//      ret.setBlogMainId("");           
//      ret.setParentCategoryId("");           
//      ret.setRootCategoryId("");           
//      ret.setTitle("");           
//      ret.setHide("");           
//      ret.setImageUrl1("");           
//      ret.setImageUrl2("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static BlogCategory copy(BlogCategory org){

    	BlogCategory ret = new BlogCategory();

		ret.setBlogMainId(org.getBlogMainId());
		ret.setParentCategoryId(org.getParentCategoryId());
		ret.setRootCategoryId(org.getRootCategoryId());
		ret.setTitle(org.getTitle());
		ret.setHide(org.getHide());
		ret.setImageUrl1(org.getImageUrl1());
		ret.setImageUrl2(org.getImageUrl2());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(BlogCategory blogCategory, Logger logger){
		logger.debug("BlogCategory [" + blogCategory.getId() + "]" + objectToString(blogCategory));		
    }

	public static String objectToString(BlogCategory blogCategory){
		StringBuffer buf = new StringBuffer();
        buf.append("BlogCategory=");
		buf.append("Id=").append(blogCategory.getId()).append(", ");
		buf.append("SiteId=").append(blogCategory.getSiteId()).append(", ");
		return buf.toString();    
    }
}
