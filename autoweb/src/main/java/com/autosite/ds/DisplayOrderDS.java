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
import com.autosite.db.DisplayOrder;
import com.autosite.db.DisplayOrderDAO;
import com.jtrend.service.DomainStore;

public class DisplayOrderDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_OrderKeyToMap;


    private static Logger m_logger = Logger.getLogger(DisplayOrderDS.class);
    private static DisplayOrderDS m_DisplayOrderDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized DisplayOrderDS getInstance() {
        if (m_DisplayOrderDS == null) {
            m_DisplayOrderDS = new DisplayOrderDS();
        }
        return m_DisplayOrderDS;
    }

    public static synchronized DisplayOrderDS getInstance(long id) {
        DisplayOrderDS ret = (DisplayOrderDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new DisplayOrderDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_DisplayOrderDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((DisplayOrderDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected DisplayOrderDS() {
        m_dao = new DisplayOrderDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_OrderKeyToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected DisplayOrderDS(long id) {
        m_loadById = id;
        m_dao = new DisplayOrderDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_OrderKeyToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public DisplayOrder getById(Long id) {
        return (DisplayOrder) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        DisplayOrder o = (DisplayOrder)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("DisplayOrder removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("DisplayOrder added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateOrderKeyMap(obj, del);


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
        DisplayOrder o = (DisplayOrder)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("DisplayOrder removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("DisplayOrder added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

    public DisplayOrder getByOrderKey(String keyOrderKey) {
        return (DisplayOrder)m_OrderKeyToMap.get(keyOrderKey);
    }

    private void updateOrderKeyMap(Object obj, boolean del) {
        DisplayOrder o = (DisplayOrder)obj;
        String _OrderKey =  o.getOrderKey();

        if (del) {
            // delete from OrderKeyToMap

            if (m_OrderKeyToMap.containsKey(_OrderKey)){
                m_OrderKeyToMap.remove(_OrderKey);
                 if (m_debug) m_logger.debug("DisplayOrder removed from OrderKeyToMap" + o.getId() + " for [" + _OrderKey+ "]");
            } else {
                if (m_debug) m_logger.debug("DisplayOrder not removed from OrderKeyToMap" + o.getId() + " for [" + _OrderKey+ "]");
            } 
        }
        else {
            
            if (m_OrderKeyToMap.containsKey(_OrderKey)){
                if (m_debug) m_logger.debug("DisplayOrder repalced OrderKeyToMap" + o.getId() + " for [" + _OrderKey+ "]");
            } else {
                if (m_debug) m_logger.debug("DisplayOrder added to OrderKeyToMap" + o.getId() + " for [" + _OrderKey+ "]");
            } 
            m_OrderKeyToMap.put(_OrderKey, o);
        }
    }





    public static void main(String[] args) throws Exception {

        DisplayOrderDS ds = new DisplayOrderDS();
        DisplayOrder obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static DisplayOrder createDefault(){

        DisplayOrder ret = new DisplayOrder();        
//      ret.setOrderKey("");           
//      ret.setOrderList("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static DisplayOrder copy(DisplayOrder org){

    	DisplayOrder ret = new DisplayOrder();

		ret.setOrderKey(org.getOrderKey());
		ret.setOrderList(org.getOrderList());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(DisplayOrder displayOrder, Logger logger){
		logger.debug("DisplayOrder [" + displayOrder.getId() + "]" + objectToString(displayOrder));		
    }

	public static String objectToString(DisplayOrder displayOrder){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(displayOrder.getId()).append(", ");
		buf.append("SiteId=").append(displayOrder.getSiteId()).append(", ");
		buf.append("OrderKey=").append(displayOrder.getOrderKey()).append(", ");
		buf.append("OrderList=").append(displayOrder.getOrderList()).append(", ");
		return buf.toString();    
    }
}
