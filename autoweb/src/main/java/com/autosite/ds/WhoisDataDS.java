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
import com.autosite.db.WhoisData;
import com.jtrend.service.DomainStore;

import com.autosite.db.WhoisDataDAO;

public class WhoisDataDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;



    protected Map m_SiteIdToIpToMap;




    private static Logger m_logger = Logger.getLogger(WhoisDataDS.class);
    private static WhoisDataDS m_WhoisDataDS = new WhoisDataDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static WhoisDataDS getInstance() {
        return m_WhoisDataDS;
    }

    public static synchronized WhoisDataDS getInstance(long id) {
        WhoisDataDS ret = (WhoisDataDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new WhoisDataDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_WhoisDataDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((WhoisDataDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("WhoisData loaded from DB. num=" + m_idToMap.size());
        
    }

    protected WhoisDataDS() {
        m_dao = new WhoisDataDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToIpToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected WhoisDataDS(long id) {
        m_dao = new WhoisDataDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToIpToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public WhoisData getById(Long id) {
        return (WhoisData) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        WhoisData o = (WhoisData)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("WhoisData removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("WhoisData added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdToIpMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        WhoisData o = (WhoisData)obj;

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
            if (m_debug) m_logger.debug("WhoisData removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("WhoisData added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    







    public List getBySiteIdIpList(long SiteId, String Ip) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToIpToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToIpToMap.get(keySiteId);

     	    String keyIp =  Ip;
			if (  keyIp == null || keyIp.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyIp)){
                return new ArrayList( ((Map)mapSiteId.get(keyIp)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdIpList(long SiteId, String Ip) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToIpToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToIpToMap.get(keySiteId);

     	    String keyIp =  Ip;
			if (  keyIp == null || keyIp.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyIp)){
                return (Map)mapSiteId.get(keyIp);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToIpMap(Object obj, boolean del) {
        WhoisData o = (WhoisData)obj;

   	    String keyIp =  o.getIp();
		if (  keyIp == null || keyIp.isEmpty()) return;

        if (del) {
            // delete from SiteIdIpToMap
            Map mapSiteId  = (Map) m_SiteIdToIpToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapIp = (Map) mapSiteId.get(keyIp);
                if (mapIp != null){
                    mapIp.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed WhoisData from m_SiteIdToIpToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getIp());
        }
        else {
            
            // add to SiteIdIpToMap
            Map mapSiteId  = (Map) m_SiteIdToIpToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToIpToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapIp = (Map) mapSiteId.get(keyIp);
            
            if ( mapIp == null) {
                mapIp = new TreeMap();
                mapSiteId.put(keyIp, mapIp);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapIp.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdIpToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    







    public static void main(String[] args) throws Exception {

        WhoisDataDS ds = new WhoisDataDS();
        WhoisData obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static WhoisData createDefault(){

        WhoisData ret = new WhoisData();        
//      ret.setIp("");           
//      ret.setWhoisData("");           
//      ret.setServer("");           
//      ret.setForceRequest("");           
//      ret.setTimeCreated("");           
//      ret.setTimeExpired("");           
        return ret;
    }

    public static WhoisData copy(WhoisData org){

    	WhoisData ret = new WhoisData();

		ret.setIp(org.getIp());
		ret.setWhoisData(org.getWhoisData());
		ret.setServer(org.getServer());
		ret.setForceRequest(org.getForceRequest());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeExpired(org.getTimeExpired());

        return ret;
    }

	public static void objectToLog(WhoisData whoisData, Logger logger){
		logger.debug("WhoisData [" + whoisData.getId() + "]" + objectToString(whoisData));		
    }

	public static String objectToString(WhoisData whoisData){
		StringBuffer buf = new StringBuffer();
        buf.append("WhoisData=");
		buf.append("Id=").append(whoisData.getId()).append(", ");
		buf.append("SiteId=").append(whoisData.getSiteId()).append(", ");
		return buf.toString();    
    }
}
