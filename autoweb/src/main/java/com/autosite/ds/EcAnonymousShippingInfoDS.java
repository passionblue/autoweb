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
import com.autosite.db.EcAnonymousShippingInfo;
import com.jtrend.service.DomainStore;

import com.autosite.db.EcAnonymousShippingInfoDAO;

public class EcAnonymousShippingInfoDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_AnonymousUserIdToOneMap;

	protected Map m_SiteIdAnonymousUserIdToMap;


    private static Logger m_logger = Logger.getLogger(EcAnonymousShippingInfoDS.class);
    private static EcAnonymousShippingInfoDS m_EcAnonymousShippingInfoDS = new EcAnonymousShippingInfoDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcAnonymousShippingInfoDS getInstance() {
        return m_EcAnonymousShippingInfoDS;
    }

    public static synchronized EcAnonymousShippingInfoDS getInstance(long id) {
        EcAnonymousShippingInfoDS ret = (EcAnonymousShippingInfoDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcAnonymousShippingInfoDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcAnonymousShippingInfoDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcAnonymousShippingInfoDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcAnonymousShippingInfoDS() {
        m_dao = new EcAnonymousShippingInfoDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_AnonymousUserIdToOneMap = new ConcurrentHashMap();
		m_SiteIdAnonymousUserIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcAnonymousShippingInfoDS(long id) {
        m_dao = new EcAnonymousShippingInfoDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_AnonymousUserIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcAnonymousShippingInfo getById(Long id) {
        return (EcAnonymousShippingInfo) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcAnonymousShippingInfo o = (EcAnonymousShippingInfo)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcAnonymousShippingInfo removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcAnonymousShippingInfo added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateAnonymousUserIdOneMap(obj, del);

		updateSiteIdAnonymousUserIdMap(obj, del);


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
        EcAnonymousShippingInfo o = (EcAnonymousShippingInfo)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcAnonymousShippingInfo removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcAnonymousShippingInfo added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public EcAnonymousShippingInfo getObjectByAnonymousUserId(long keyAnonymousUserId) {
        return (EcAnonymousShippingInfo)m_AnonymousUserIdToOneMap.get(new Long(keyAnonymousUserId));
    }

    private void updateAnonymousUserIdOneMap(Object obj, boolean del) {
        EcAnonymousShippingInfo o = (EcAnonymousShippingInfo)obj;
        Long _AnonymousUserId = new Long(o.getAnonymousUserId());

        if (del) {
            // delete from AnonymousUserIdToOneMap

            if (m_AnonymousUserIdToOneMap.containsKey(_AnonymousUserId)){
                m_AnonymousUserIdToOneMap.remove(_AnonymousUserId);
                if (m_debug) m_logger.debug("EcAnonymousShippingInfo removed from AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcAnonymousShippingInfo not removed from AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]. Does not exist");
            } 
        }
        else {
            if (m_AnonymousUserIdToOneMap.containsKey(_AnonymousUserId)){
                if (m_debug) m_logger.debug("EcAnonymousShippingInfo repalced AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcAnonymousShippingInfo added to AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]");
            } 
            m_AnonymousUserIdToOneMap.put(_AnonymousUserId, o);
        }
    }



        
    public List getBySiteIdAnonymousUserId(long SiteId, long AnonymousUserId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdAnonymousUserIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdAnonymousUserIdToMap.get(keySiteId);

     	    Long keyAnonymousUserId = new Long(AnonymousUserId);
            
            if ( mapSiteId.containsKey(keyAnonymousUserId)){
                return new ArrayList( ((Map)mapSiteId.get(keyAnonymousUserId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdAnonymousUserId(long SiteId, long AnonymousUserId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdAnonymousUserIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdAnonymousUserIdToMap.get(keySiteId);

     	    Long keyAnonymousUserId = new Long(AnonymousUserId);
            
            if ( mapSiteId.containsKey(keyAnonymousUserId)){
                return (Map)mapSiteId.get(keyAnonymousUserId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdAnonymousUserIdMap(Object obj, boolean del) {
        EcAnonymousShippingInfo o = (EcAnonymousShippingInfo)obj;

     	    Long keyAnonymousUserId = new Long(o.getAnonymousUserId());

        if (del) {
            // delete from SiteIdAnonymousUserIdToMap
            Map mapSiteId  = (Map) m_SiteIdAnonymousUserIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapAnonymousUserId = (Map) mapSiteId.get(keyAnonymousUserId);
                if (mapAnonymousUserId != null){
                    mapAnonymousUserId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed from m_SiteIdAnonymousUserIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getAnonymousUserId());
        }
        else {
            
            // add to SiteIdAnonymousUserIdToMap
            Map mapSiteId  = (Map) m_SiteIdAnonymousUserIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdAnonymousUserIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapAnonymousUserId = (Map) mapSiteId.get(keyAnonymousUserId);
            
            if ( mapAnonymousUserId == null) {
                mapAnonymousUserId = new TreeMap();
                mapSiteId.put(keyAnonymousUserId, mapAnonymousUserId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapAnonymousUserId.put(new Long(o.getId()), o);            
            
            m_logger.debug("Added to SiteIdAnonymousUserIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        





    public static void main(String[] args) throws Exception {

        EcAnonymousShippingInfoDS ds = new EcAnonymousShippingInfoDS();
        EcAnonymousShippingInfo obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcAnonymousShippingInfo createDefault(){

        EcAnonymousShippingInfo ret = new EcAnonymousShippingInfo();        
//      ret.setAnonymousUserId("");           
//      ret.setFirstName("");           
//      ret.setMiddleInitial("");           
//      ret.setLastName("");           
//      ret.setAddress1("");           
//      ret.setAddress2("");           
//      ret.setCity("");           
//      ret.setState("");           
//      ret.setZip("");           
//      ret.setCountry("");           
//      ret.setSpecialInstruction("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static EcAnonymousShippingInfo copy(EcAnonymousShippingInfo org){

    	EcAnonymousShippingInfo ret = new EcAnonymousShippingInfo();

		ret.setAnonymousUserId(org.getAnonymousUserId());
		ret.setFirstName(org.getFirstName());
		ret.setMiddleInitial(org.getMiddleInitial());
		ret.setLastName(org.getLastName());
		ret.setAddress1(org.getAddress1());
		ret.setAddress2(org.getAddress2());
		ret.setCity(org.getCity());
		ret.setState(org.getState());
		ret.setZip(org.getZip());
		ret.setCountry(org.getCountry());
		ret.setSpecialInstruction(org.getSpecialInstruction());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(EcAnonymousShippingInfo ecAnonymousShippingInfo, Logger logger){
		logger.debug("EcAnonymousShippingInfo [" + ecAnonymousShippingInfo.getId() + "]" + objectToString(ecAnonymousShippingInfo));		
    }

	public static String objectToString(EcAnonymousShippingInfo ecAnonymousShippingInfo){
		StringBuffer buf = new StringBuffer();
        buf.append("EcAnonymousShippingInfo=");
		buf.append("Id=").append(ecAnonymousShippingInfo.getId()).append(", ");
		buf.append("SiteId=").append(ecAnonymousShippingInfo.getSiteId()).append(", ");
		buf.append("AnonymousUserId=").append(ecAnonymousShippingInfo.getAnonymousUserId()).append(", ");
		buf.append("FirstName=").append(ecAnonymousShippingInfo.getFirstName()).append(", ");
		buf.append("LastName=").append(ecAnonymousShippingInfo.getLastName()).append(", ");
		buf.append("State=").append(ecAnonymousShippingInfo.getState()).append(", ");
		buf.append("Country=").append(ecAnonymousShippingInfo.getCountry()).append(", ");
		buf.append("Zip=").append(ecAnonymousShippingInfo.getZip()).append(", ");
		return buf.toString();    
    }
}
