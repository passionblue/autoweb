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
import com.autosite.db.EcPageProductRel;
import com.autosite.db.EcPageProductRelDAO;
import com.jtrend.service.DomainStore;

public class EcPageProductRelDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_CategoryIdToMap;
    protected Map m_ProductIdToMap;

	protected Map m_SiteIdProductIdToMap;


    private static Logger m_logger = Logger.getLogger(EcPageProductRelDS.class);
    private static EcPageProductRelDS m_EcPageProductRelDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcPageProductRelDS getInstance() {
        if (m_EcPageProductRelDS == null) {
            m_EcPageProductRelDS = new EcPageProductRelDS();
        }
        return m_EcPageProductRelDS;
    }

    public static synchronized EcPageProductRelDS getInstance(long id) {
        EcPageProductRelDS ret = (EcPageProductRelDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcPageProductRelDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcPageProductRelDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcPageProductRelDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcPageProductRelDS() {
        m_dao = new EcPageProductRelDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_CategoryIdToMap = new ConcurrentHashMap();
        m_ProductIdToMap = new ConcurrentHashMap();
		m_SiteIdProductIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcPageProductRelDS(long id) {
        m_loadById = id;
        m_dao = new EcPageProductRelDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_CategoryIdToMap = new ConcurrentHashMap();
        m_ProductIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcPageProductRel getById(Long id) {
        return (EcPageProductRel) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcPageProductRel o = (EcPageProductRel)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcPageProductRel removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcPageProductRel added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateCategoryIdMap(obj, del);
        updateProductIdMap(obj, del);

		updateSiteIdProductIdMap(obj, del);


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
        EcPageProductRel o = (EcPageProductRel)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcPageProductRel removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcPageProductRel added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        EcPageProductRel o = (EcPageProductRel)obj;

        if (del) {

            // delete from CategoryIdToMap
            Map map  = (Map) m_CategoryIdToMap.get(new Long(o.getCategoryId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcPageProductRel removed from CategoryIdToMap" + o.getId() + " from " + o.getCategoryId());
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
            if (m_debug) m_logger.debug("EcPageProductRel added to CategoryIdToMap " + o.getId() + " to " + o.getCategoryId());
        }
    }

    public List getByProductId(long ProductId) {
        
        Long _ProductId  = new Long(ProductId);
        if (m_ProductIdToMap.containsKey(_ProductId)) {
            return new ArrayList( ((Map)m_ProductIdToMap.get(_ProductId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateProductIdMap(Object obj, boolean del) {
        EcPageProductRel o = (EcPageProductRel)obj;

        if (del) {

            // delete from ProductIdToMap
            Map map  = (Map) m_ProductIdToMap.get(new Long(o.getProductId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcPageProductRel removed from ProductIdToMap" + o.getId() + " from " + o.getProductId());
        }
        else {
            
            // add to ProductIdToMap
            Map map  = (Map) m_ProductIdToMap.get(new Long(o.getProductId()));
            if ( map == null ) {
                map = new TreeMap();
                m_ProductIdToMap.put(new Long(o.getProductId()), map);
                if (m_debug) m_logger.debug("created new   ProductIdToMap for " + o.getProductId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("EcPageProductRel added to ProductIdToMap " + o.getId() + " to " + o.getProductId());
        }
    }


    




        
    public List getBySiteIdProductId(long SiteId, long ProductId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdProductIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdProductIdToMap.get(keySiteId);

     	    Long keyProductId = new Long(ProductId);
            
            if ( mapSiteId.containsKey(keyProductId)){
                return new ArrayList( ((Map)mapSiteId.get(keyProductId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdProductId(long SiteId, long ProductId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdProductIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdProductIdToMap.get(keySiteId);

     	    Long keyProductId = new Long(ProductId);
            
            if ( mapSiteId.containsKey(keyProductId)){
                return (Map)mapSiteId.get(keyProductId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdProductIdMap(Object obj, boolean del) {
        EcPageProductRel o = (EcPageProductRel)obj;

     	    Long keyProductId = new Long(o.getProductId());

        if (del) {
            // delete from SiteIdProductIdToMap
            Map mapSiteId  = (Map) m_SiteIdProductIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapProductId = (Map) mapSiteId.get(keyProductId);
                if (mapProductId != null){
                    mapProductId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed from m_SiteIdProductIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getProductId());
        }
        else {
            
            // add to SiteIdProductIdToMap
            Map mapSiteId  = (Map) m_SiteIdProductIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdProductIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapProductId = (Map) mapSiteId.get(keyProductId);
            
            if ( mapProductId == null) {
                mapProductId = new TreeMap();
                mapSiteId.put(keyProductId, mapProductId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapProductId.put(new Long(o.getId()), o);            
            
            m_logger.debug("Panel added to SiteIdProductIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        EcPageProductRelDS ds = new EcPageProductRelDS();
        EcPageProductRel obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcPageProductRel createDefault(){

        EcPageProductRel ret = new EcPageProductRel();        
//      ret.setProductId("");           
//      ret.setCategoryId("");           
//      ret.setHide("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static EcPageProductRel copy(EcPageProductRel org){

    	EcPageProductRel ret = new EcPageProductRel();

		ret.setProductId(org.getProductId());
		ret.setCategoryId(org.getCategoryId());
		ret.setHide(org.getHide());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(EcPageProductRel ecPageProductRel, Logger logger){
		logger.debug("EcPageProductRel [" + ecPageProductRel.getId() + "]" + objectToString(ecPageProductRel));		
    }

	public static String objectToString(EcPageProductRel ecPageProductRel){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecPageProductRel.getId()).append(", ");
		buf.append("SiteId=").append(ecPageProductRel.getSiteId()).append(", ");
		buf.append("CategoryId=").append(ecPageProductRel.getCategoryId()).append(", ");
		buf.append("ProductId=").append(ecPageProductRel.getProductId()).append(", ");
		return buf.toString();    
    }
}
