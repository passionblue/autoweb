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
import com.autosite.db.EcWishList;
import com.autosite.db.EcWishListDAO;
import com.jtrend.service.DomainStore;

public class EcWishListDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_ProductIdToMap;
    protected Map m_UserIdToMap;

	protected Map m_SiteIdUserIdToMap;


    private static Logger m_logger = Logger.getLogger(EcWishListDS.class);
    private static EcWishListDS m_EcWishListDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcWishListDS getInstance() {
        if (m_EcWishListDS == null) {
            m_EcWishListDS = new EcWishListDS();
        }
        return m_EcWishListDS;
    }

    public static synchronized EcWishListDS getInstance(long id) {
        EcWishListDS ret = (EcWishListDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcWishListDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcWishListDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcWishListDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcWishListDS() {
        m_dao = new EcWishListDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ProductIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
		m_SiteIdUserIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcWishListDS(long id) {
        m_loadById = id;
        m_dao = new EcWishListDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ProductIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcWishList getById(Long id) {
        return (EcWishList) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcWishList o = (EcWishList)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcWishList removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcWishList added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateProductIdMap(obj, del);
        updateUserIdMap(obj, del);

		updateSiteIdUserIdMap(obj, del);


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
        EcWishList o = (EcWishList)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcWishList removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcWishList added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        EcWishList o = (EcWishList)obj;

        if (del) {

            // delete from ProductIdToMap
            Map map  = (Map) m_ProductIdToMap.get(new Long(o.getProductId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcWishList removed from ProductIdToMap" + o.getId() + " from " + o.getProductId());
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
            if (m_debug) m_logger.debug("EcWishList added to ProductIdToMap " + o.getId() + " to " + o.getProductId());
        }
    }

    public List getByUserId(long UserId) {
        
        Long _UserId  = new Long(UserId);
        if (m_UserIdToMap.containsKey(_UserId)) {
            return new ArrayList( ((Map)m_UserIdToMap.get(_UserId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateUserIdMap(Object obj, boolean del) {
        EcWishList o = (EcWishList)obj;

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcWishList removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
        }
        else {
            
            // add to UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map == null ) {
                map = new TreeMap();
                m_UserIdToMap.put(new Long(o.getUserId()), map);
                if (m_debug) m_logger.debug("created new   UserIdToMap for " + o.getUserId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("EcWishList added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    




        
    public List getBySiteIdUserId(long SiteId, long UserId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdUserIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUserIdToMap.get(keySiteId);

     	    Long keyUserId = new Long(UserId);
            
            if ( mapSiteId.containsKey(keyUserId)){
                return new ArrayList( ((Map)mapSiteId.get(keyUserId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdUserId(long SiteId, long UserId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdUserIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUserIdToMap.get(keySiteId);

     	    Long keyUserId = new Long(UserId);
            
            if ( mapSiteId.containsKey(keyUserId)){
                return (Map)mapSiteId.get(keyUserId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdUserIdMap(Object obj, boolean del) {
        EcWishList o = (EcWishList)obj;

     	    Long keyUserId = new Long(o.getUserId());

        if (del) {
            // delete from SiteIdUserIdToMap
            Map mapSiteId  = (Map) m_SiteIdUserIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapUserId = (Map) mapSiteId.get(keyUserId);
                if (mapUserId != null){
                    mapUserId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed from m_SiteIdUserIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getUserId());
        }
        else {
            
            // add to SiteIdUserIdToMap
            Map mapSiteId  = (Map) m_SiteIdUserIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdUserIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapUserId = (Map) mapSiteId.get(keyUserId);
            
            if ( mapUserId == null) {
                mapUserId = new TreeMap();
                mapSiteId.put(keyUserId, mapUserId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapUserId.put(new Long(o.getId()), o);            
            
            m_logger.debug("Panel added to SiteIdUserIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        EcWishListDS ds = new EcWishListDS();
        EcWishList obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcWishList createDefault(){

        EcWishList ret = new EcWishList();        
//      ret.setUserId("");           
//      ret.setProductId("");           
//      ret.setSizeVariation("");           
//      ret.setColorVariation("");           
//      ret.setSavedPrice("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static EcWishList copy(EcWishList org){

    	EcWishList ret = new EcWishList();

		ret.setUserId(org.getUserId());
		ret.setProductId(org.getProductId());
		ret.setSizeVariation(org.getSizeVariation());
		ret.setColorVariation(org.getColorVariation());
		ret.setSavedPrice(org.getSavedPrice());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(EcWishList ecWishList, Logger logger){
		logger.debug("EcWishList [" + ecWishList.getId() + "]" + objectToString(ecWishList));		
    }

	public static String objectToString(EcWishList ecWishList){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecWishList.getId()).append(", ");
		buf.append("SiteId=").append(ecWishList.getSiteId()).append(", ");
		buf.append("UserId=").append(ecWishList.getUserId()).append(", ");
		buf.append("ProductId=").append(ecWishList.getProductId()).append(", ");
		buf.append("SavedPrice=").append(ecWishList.getSavedPrice()).append(", ");
		return buf.toString();    
    }
}
