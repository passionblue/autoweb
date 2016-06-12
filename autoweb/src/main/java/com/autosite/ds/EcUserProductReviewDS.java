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
import com.autosite.db.EcUserProductReview;
import com.autosite.db.EcUserProductReviewDAO;
import com.jtrend.service.DomainStore;

public class EcUserProductReviewDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_ProductIdToMap;
    protected Map m_UserIdToMap;



    private static Logger m_logger = Logger.getLogger(EcUserProductReviewDS.class);
    private static EcUserProductReviewDS m_EcUserProductReviewDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcUserProductReviewDS getInstance() {
        if (m_EcUserProductReviewDS == null) {
            m_EcUserProductReviewDS = new EcUserProductReviewDS();
        }
        return m_EcUserProductReviewDS;
    }

    public static synchronized EcUserProductReviewDS getInstance(long id) {
        EcUserProductReviewDS ret = (EcUserProductReviewDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcUserProductReviewDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcUserProductReviewDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcUserProductReviewDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcUserProductReviewDS() {
        m_dao = new EcUserProductReviewDAO();
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

    protected EcUserProductReviewDS(long id) {
        m_loadById = id;
        m_dao = new EcUserProductReviewDAO();
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

    public EcUserProductReview getById(Long id) {
        return (EcUserProductReview) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcUserProductReview o = (EcUserProductReview)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcUserProductReview removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcUserProductReview added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateProductIdMap(obj, del);
        updateUserIdMap(obj, del);



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
        EcUserProductReview o = (EcUserProductReview)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcUserProductReview removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcUserProductReview added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        EcUserProductReview o = (EcUserProductReview)obj;

        if (del) {

            // delete from ProductIdToMap
            Map map  = (Map) m_ProductIdToMap.get(new Long(o.getProductId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcUserProductReview removed from ProductIdToMap" + o.getId() + " from " + o.getProductId());
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
            if (m_debug) m_logger.debug("EcUserProductReview added to ProductIdToMap " + o.getId() + " to " + o.getProductId());
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
        EcUserProductReview o = (EcUserProductReview)obj;

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcUserProductReview removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("EcUserProductReview added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    








    public static void main(String[] args) throws Exception {

        EcUserProductReviewDS ds = new EcUserProductReviewDS();
        EcUserProductReview obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcUserProductReview createDefault(){

        EcUserProductReview ret = new EcUserProductReview();        
//      ret.setProductId("");           
//      ret.setUserId("");           
//      ret.setRate("");           
//      ret.setReview("");           
//      ret.setTrackBack("");           
//      ret.setNumVoteYes("");           
//      ret.setNumVoteNo("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static EcUserProductReview copy(EcUserProductReview org){

    	EcUserProductReview ret = new EcUserProductReview();

		ret.setProductId(org.getProductId());
		ret.setUserId(org.getUserId());
		ret.setRate(org.getRate());
		ret.setReview(org.getReview());
		ret.setTrackBack(org.getTrackBack());
		ret.setNumVoteYes(org.getNumVoteYes());
		ret.setNumVoteNo(org.getNumVoteNo());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(EcUserProductReview ecUserProductReview, Logger logger){
		logger.debug("EcUserProductReview [" + ecUserProductReview.getId() + "]" + objectToString(ecUserProductReview));		
    }

	public static String objectToString(EcUserProductReview ecUserProductReview){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecUserProductReview.getId()).append(", ");
		buf.append("SiteId=").append(ecUserProductReview.getSiteId()).append(", ");
		buf.append("ProductId=").append(ecUserProductReview.getProductId()).append(", ");
		buf.append("UserId=").append(ecUserProductReview.getUserId()).append(", ");
		buf.append("Rate=").append(ecUserProductReview.getRate()).append(", ");
		buf.append("TimeCreated=").append(ecUserProductReview.getTimeCreated()).append(", ");
		return buf.toString();    
    }
}
