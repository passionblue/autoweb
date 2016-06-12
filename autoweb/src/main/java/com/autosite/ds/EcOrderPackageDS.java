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
import com.autosite.db.EcOrderPackage;
import com.autosite.db.EcOrderPackageDAO;
import com.jtrend.service.DomainStore;

public class EcOrderPackageDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;

	protected Map m_SiteIdUserIdToMap;

	protected Map m_SiteIdOrderIdToOneMap;

    private static Logger m_logger = Logger.getLogger(EcOrderPackageDS.class);
    private static EcOrderPackageDS m_EcOrderPackageDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcOrderPackageDS getInstance() {
        if (m_EcOrderPackageDS == null) {
            m_EcOrderPackageDS = new EcOrderPackageDS();
        }
        return m_EcOrderPackageDS;
    }

    public static synchronized EcOrderPackageDS getInstance(long id) {
        EcOrderPackageDS ret = (EcOrderPackageDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcOrderPackageDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcOrderPackageDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcOrderPackageDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcOrderPackageDS() {
        m_dao = new EcOrderPackageDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
		m_SiteIdUserIdToMap = new ConcurrentHashMap();
		m_SiteIdOrderIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcOrderPackageDS(long id) {
        m_loadById = id;
        m_dao = new EcOrderPackageDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcOrderPackage getById(Long id) {
        return (EcOrderPackage) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcOrderPackage o = (EcOrderPackage)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcOrderPackage removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcOrderPackage added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);

		updateSiteIdUserIdMap(obj, del);

		updateSiteIdOrderIdOneMap(obj, del);

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
        EcOrderPackage o = (EcOrderPackage)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcOrderPackage removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcOrderPackage added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        EcOrderPackage o = (EcOrderPackage)obj;

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcOrderPackage removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("EcOrderPackage added to UserIdToMap " + o.getId() + " to " + o.getUserId());
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
        EcOrderPackage o = (EcOrderPackage)obj;

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
        

        
    public  EcOrderPackage getBySiteIdOrderId(long SiteId, long OrderId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdOrderIdToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdOrderIdToOneMap.get(keySiteId);

     	    Long keyOrderId = new Long(OrderId);
            
            if ( mapSiteId.containsKey(keyOrderId)){
                return ( EcOrderPackage)mapSiteId.get(keyOrderId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdOrderIdOneMap(Object obj, boolean del) {
        EcOrderPackage o = (EcOrderPackage)obj;

     	    Long keyOrderId = new Long(o.getOrderId());

        if (del) {
            // delete from SiteIdOrderIdToOneMap
            Map mapSiteId  = (Map) m_SiteIdOrderIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapOrderId = (Map) mapSiteId.get(keyOrderId);
                if (mapSiteId.containsKey(keyOrderId)){
                    mapSiteId.remove(keyOrderId);
                }
            }
            m_logger.debug("removed from m_SiteIdOrderIdToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getOrderId());
        }
        else {
            
            // add to SiteIdOrderIdToOneMap
            Map mapSiteId  = (Map) m_SiteIdOrderIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdOrderIdToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            
            
			EcOrderPackage replaced = (EcOrderPackage) mapSiteId.put(keyOrderId,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdOrderIdToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        



    public static void main(String[] args) throws Exception {

        EcOrderPackageDS ds = new EcOrderPackageDS();
        EcOrderPackage obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcOrderPackage createDefault(){

        EcOrderPackage ret = new EcOrderPackage();        
//      ret.setUserId("");           
//      ret.setOrderId("");           
//      ret.setNumOrder("");           
//      ret.setShipped("");           
//      ret.setTimeShipped("");           
        return ret;
    }

    public static EcOrderPackage copy(EcOrderPackage org){

    	EcOrderPackage ret = new EcOrderPackage();

		ret.setUserId(org.getUserId());
		ret.setOrderId(org.getOrderId());
		ret.setNumOrder(org.getNumOrder());
		ret.setShipped(org.getShipped());
		ret.setTimeShipped(org.getTimeShipped());

        return ret;
    }

	public static void objectToLog(EcOrderPackage ecOrderPackage, Logger logger){
		logger.debug("EcOrderPackage [" + ecOrderPackage.getId() + "]" + objectToString(ecOrderPackage));		
    }

	public static String objectToString(EcOrderPackage ecOrderPackage){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecOrderPackage.getId()).append(", ");
		buf.append("SiteId=").append(ecOrderPackage.getSiteId()).append(", ");
		buf.append("UserId=").append(ecOrderPackage.getUserId()).append(", ");
		buf.append("OrderId=").append(ecOrderPackage.getOrderId()).append(", ");
		buf.append("NumOrder=").append(ecOrderPackage.getNumOrder()).append(", ");
		buf.append("Shipped=").append(ecOrderPackage.getShipped()).append(", ");
		return buf.toString();    
    }
}
