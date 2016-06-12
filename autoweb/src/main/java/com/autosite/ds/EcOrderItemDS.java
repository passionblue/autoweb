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
import com.autosite.db.EcOrderItem;
import com.jtrend.service.DomainStore;

import com.autosite.db.EcOrderItemDAO;

public class EcOrderItemDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_OrderIdToMap;

	protected Map m_SiteIdProductIdToMap;


    private static Logger m_logger = Logger.getLogger(EcOrderItemDS.class);
    private static EcOrderItemDS m_EcOrderItemDS = new EcOrderItemDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcOrderItemDS getInstance() {
        return m_EcOrderItemDS;
    }

    public static synchronized EcOrderItemDS getInstance(long id) {
        EcOrderItemDS ret = (EcOrderItemDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcOrderItemDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcOrderItemDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcOrderItemDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcOrderItemDS() {
        m_dao = new EcOrderItemDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_OrderIdToMap = new ConcurrentHashMap();
		m_SiteIdProductIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcOrderItemDS(long id) {
        m_dao = new EcOrderItemDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_OrderIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcOrderItem getById(Long id) {
        return (EcOrderItem) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcOrderItem o = (EcOrderItem)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcOrderItem removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcOrderItem added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateOrderIdMap(obj, del);

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
        EcOrderItem o = (EcOrderItem)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcOrderItem removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcOrderItem added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }

    public List getByOrderId(long OrderId) {
        
        Long _OrderId  = new Long(OrderId);
        if (m_OrderIdToMap.containsKey(_OrderId)) {
            return new ArrayList( ((Map)m_OrderIdToMap.get(_OrderId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateOrderIdMap(Object obj, boolean del) {
        EcOrderItem o = (EcOrderItem)obj;

        if (del) {

            // delete from OrderIdToMap
            Map map  = (Map) m_OrderIdToMap.get(new Long(o.getOrderId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcOrderItem removed from OrderIdToMap" + o.getId() + " from " + o.getOrderId());
        }
        else {
            
            // add to OrderIdToMap
            Map map  = (Map) m_OrderIdToMap.get(new Long(o.getOrderId()));
            if ( map == null ) {
                map = new TreeMap();
                m_OrderIdToMap.put(new Long(o.getOrderId()), map);
                if (m_debug) m_logger.debug("created new   OrderIdToMap for " + o.getOrderId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("EcOrderItem added to OrderIdToMap " + o.getId() + " to " + o.getOrderId());
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
        EcOrderItem o = (EcOrderItem)obj;

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
            
            m_logger.debug("Added to SiteIdProductIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        





    public static void main(String[] args) throws Exception {

        EcOrderItemDS ds = new EcOrderItemDS();
        EcOrderItem obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcOrderItem createDefault(){

        EcOrderItem ret = new EcOrderItem();        
//      ret.setOrderId("");           
//      ret.setProductId("");           
//      ret.setSiteSku("");           
//      ret.setSizeVariation("");           
//      ret.setColorVariation("");           
//      ret.setQty("");           
//      ret.setUnitPrice("");           
//      ret.setOrderPrice("");           
//      ret.setTimeCreated("");           
//      ret.setPackageId("");           
        return ret;
    }

    public static EcOrderItem copy(EcOrderItem org){

    	EcOrderItem ret = new EcOrderItem();

		ret.setOrderId(org.getOrderId());
		ret.setProductId(org.getProductId());
		ret.setSiteSku(org.getSiteSku());
		ret.setSizeVariation(org.getSizeVariation());
		ret.setColorVariation(org.getColorVariation());
		ret.setQty(org.getQty());
		ret.setUnitPrice(org.getUnitPrice());
		ret.setOrderPrice(org.getOrderPrice());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setPackageId(org.getPackageId());

        return ret;
    }

	public static void objectToLog(EcOrderItem ecOrderItem, Logger logger){
		logger.debug("EcOrderItem [" + ecOrderItem.getId() + "]" + objectToString(ecOrderItem));		
    }

	public static String objectToString(EcOrderItem ecOrderItem){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecOrderItem.getId()).append(", ");
		buf.append("SiteId=").append(ecOrderItem.getSiteId()).append(", ");
		buf.append("ProductId=").append(ecOrderItem.getProductId()).append(", ");
		buf.append("SiteSku=").append(ecOrderItem.getSiteSku()).append(", ");
		buf.append("Qty=").append(ecOrderItem.getQty()).append(", ");
		buf.append("UnitPrice=").append(ecOrderItem.getUnitPrice()).append(", ");
		buf.append("OrderPrice=").append(ecOrderItem.getOrderPrice()).append(", ");
		return buf.toString();    
    }
}
