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
import com.autosite.db.EcOrder;
import com.jtrend.service.DomainStore;

import com.autosite.db.EcOrderDAO;

public class EcOrderDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;
    protected Map m_OrderNumToOneMap;

	protected Map m_SiteIdUserIdToMap;

	protected Map m_SiteIdOrderNumToOneMap;

    private static Logger m_logger = Logger.getLogger(EcOrderDS.class);
    private static EcOrderDS m_EcOrderDS = new EcOrderDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcOrderDS getInstance() {
        return m_EcOrderDS;
    }

    public static synchronized EcOrderDS getInstance(long id) {
        EcOrderDS ret = (EcOrderDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcOrderDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcOrderDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcOrderDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcOrderDS() {
        m_dao = new EcOrderDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_OrderNumToOneMap = new ConcurrentHashMap();
		m_SiteIdUserIdToMap = new ConcurrentHashMap();
		m_SiteIdOrderNumToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcOrderDS(long id) {
        m_dao = new EcOrderDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_OrderNumToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcOrder getById(Long id) {
        return (EcOrder) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcOrder o = (EcOrder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcOrder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcOrder added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);
        updateOrderNumOneMap(obj, del);

		updateSiteIdUserIdMap(obj, del);

		updateSiteIdOrderNumOneMap(obj, del);

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
        EcOrder o = (EcOrder)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcOrder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcOrder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        EcOrder o = (EcOrder)obj;

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcOrder removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("EcOrder added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    

    public EcOrder getObjectByOrderNum(String keyOrderNum) {
        return (EcOrder)m_OrderNumToOneMap.get(keyOrderNum);
    }

    private void updateOrderNumOneMap(Object obj, boolean del) {
        EcOrder o = (EcOrder)obj;
        String _OrderNum =  o.getOrderNum();

        if (del) {
            // delete from OrderNumToMap

            if (m_OrderNumToOneMap.containsKey(_OrderNum)){
                m_OrderNumToOneMap.remove(_OrderNum);
                 if (m_debug) m_logger.debug("EcOrder removed from OrderNumToMap" + o.getId() + " for [" + _OrderNum+ "]");
            } else {
                if (m_debug) m_logger.debug("EcOrder not removed from OrderNumToMap" + o.getId() + " for [" + _OrderNum+ "]");
            } 
        }
        else {
            
            if (m_OrderNumToOneMap.containsKey(_OrderNum)){
                if (m_debug) m_logger.debug("EcOrder repalced OrderNumToMap" + o.getId() + " for [" + _OrderNum+ "]");
            } else {
                if (m_debug) m_logger.debug("EcOrder added to OrderNumToMap" + o.getId() + " for [" + _OrderNum+ "]");
            } 
            m_OrderNumToOneMap.put(_OrderNum, o);
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
        EcOrder o = (EcOrder)obj;

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
            
            m_logger.debug("Added to SiteIdUserIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        

        
    public  EcOrder getBySiteIdOrderNum(long SiteId, long OrderNum) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdOrderNumToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdOrderNumToOneMap.get(keySiteId);

     	    Long keyOrderNum = new Long(OrderNum);
            
            if ( mapSiteId.containsKey(keyOrderNum)){
                return ( EcOrder)mapSiteId.get(keyOrderNum);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdOrderNumOneMap(Object obj, boolean del) {
        EcOrder o = (EcOrder)obj;

     	    String keyOrderNum =  o.getOrderNum();

        if (del) {
            // delete from SiteIdOrderNumToOneMap
            Map mapSiteId  = (Map) m_SiteIdOrderNumToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyOrderNum)){
                    mapSiteId.remove(keyOrderNum);
                }
            }
            m_logger.debug("removed from m_SiteIdOrderNumToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getOrderNum());
        }
        else {
            
            // add to SiteIdOrderNumToOneMap
            Map mapSiteId  = (Map) m_SiteIdOrderNumToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdOrderNumToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            
            
			EcOrder replaced = (EcOrder) mapSiteId.put(keyOrderNum,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdOrderNumToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        EcOrderDS ds = new EcOrderDS();
        EcOrder obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcOrder createDefault(){

        EcOrder ret = new EcOrder();        
//      ret.setUserId("");           
//      ret.setAnonymousUserId("");           
//      ret.setOrderNum("");           
//      ret.setOrderStatus("");           
//      ret.setOrderTotal("");           
//      ret.setTimeReceived("");           
//      ret.setTimeApproved("");           
//      ret.setTimeHalt("");           
//      ret.setTimeCancelled("");           
//      ret.setTimeFulfilled("");           
//      ret.setTimeShipped("");           
//      ret.setTimeReturned("");           
//      ret.setReProcess("");           
//      ret.setOrgOrderId("");           
//      ret.setApprovedBy("");           
//      ret.setFulfilledBy("");           
//      ret.setShippedBy("");           
        return ret;
    }

    public static EcOrder copy(EcOrder org){

    	EcOrder ret = new EcOrder();

		ret.setUserId(org.getUserId());
		ret.setAnonymousUserId(org.getAnonymousUserId());
		ret.setOrderNum(org.getOrderNum());
		ret.setOrderStatus(org.getOrderStatus());
		ret.setOrderTotal(org.getOrderTotal());
		ret.setTimeReceived(org.getTimeReceived());
		ret.setTimeApproved(org.getTimeApproved());
		ret.setTimeHalt(org.getTimeHalt());
		ret.setTimeCancelled(org.getTimeCancelled());
		ret.setTimeFulfilled(org.getTimeFulfilled());
		ret.setTimeShipped(org.getTimeShipped());
		ret.setTimeReturned(org.getTimeReturned());
		ret.setReProcess(org.getReProcess());
		ret.setOrgOrderId(org.getOrgOrderId());
		ret.setApprovedBy(org.getApprovedBy());
		ret.setFulfilledBy(org.getFulfilledBy());
		ret.setShippedBy(org.getShippedBy());

        return ret;
    }

	public static void objectToLog(EcOrder ecOrder, Logger logger){
		logger.debug("EcOrder [" + ecOrder.getId() + "]" + objectToString(ecOrder));		
    }

	public static String objectToString(EcOrder ecOrder){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecOrder.getId()).append(", ");
		buf.append("SiteId=").append(ecOrder.getSiteId()).append(", ");
		buf.append("UserId=").append(ecOrder.getUserId()).append(", ");
		return buf.toString();    
    }
}
