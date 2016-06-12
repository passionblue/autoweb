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
import com.autosite.db.EcCheckoutAccountInfo;
import com.jtrend.service.DomainStore;


public class EcCheckoutAccountInfoDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;
    protected Map m_CartSerialToOneMap;


	protected Map m_SiteIdUserIdToOneMap;

    private static Logger m_logger = Logger.getLogger(EcCheckoutAccountInfoDS.class);
    private static EcCheckoutAccountInfoDS m_EcCheckoutAccountInfoDS = new EcCheckoutAccountInfoDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcCheckoutAccountInfoDS getInstance() {
        return m_EcCheckoutAccountInfoDS;
    }

    public static synchronized EcCheckoutAccountInfoDS getInstance(long id) {
        EcCheckoutAccountInfoDS ret = (EcCheckoutAccountInfoDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcCheckoutAccountInfoDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcCheckoutAccountInfoDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;


    protected EcCheckoutAccountInfoDS() {
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_CartSerialToOneMap = new ConcurrentHashMap();
		m_SiteIdUserIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcCheckoutAccountInfoDS(long id) {
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_CartSerialToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcCheckoutAccountInfo getById(Long id) {
        return (EcCheckoutAccountInfo) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcCheckoutAccountInfo o = (EcCheckoutAccountInfo)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcCheckoutAccountInfo removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcCheckoutAccountInfo added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);
        updateCartSerialOneMap(obj, del);


		updateSiteIdUserIdOneMap(obj, del);

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
        EcCheckoutAccountInfo o = (EcCheckoutAccountInfo)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcCheckoutAccountInfo removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcCheckoutAccountInfo added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        EcCheckoutAccountInfo o = (EcCheckoutAccountInfo)obj;

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcCheckoutAccountInfo removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("EcCheckoutAccountInfo added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    

    public EcCheckoutAccountInfo getObjectByCartSerial(String keyCartSerial) {
        return (EcCheckoutAccountInfo)m_CartSerialToOneMap.get(keyCartSerial);
    }

    private void updateCartSerialOneMap(Object obj, boolean del) {
        EcCheckoutAccountInfo o = (EcCheckoutAccountInfo)obj;
        String _CartSerial =  o.getCartSerial();

        if (del) {
            // delete from CartSerialToMap

            if (m_CartSerialToOneMap.containsKey(_CartSerial)){
                m_CartSerialToOneMap.remove(_CartSerial);
                 if (m_debug) m_logger.debug("EcCheckoutAccountInfo removed from CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } else {
                if (m_debug) m_logger.debug("EcCheckoutAccountInfo not removed from CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } 
        }
        else {
            
            if (m_CartSerialToOneMap.containsKey(_CartSerial)){
                if (m_debug) m_logger.debug("EcCheckoutAccountInfo repalced CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } else {
                if (m_debug) m_logger.debug("EcCheckoutAccountInfo added to CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } 
            m_CartSerialToOneMap.put(_CartSerial, o);
        }
    }





        
    public  EcCheckoutAccountInfo getBySiteIdUserId(long SiteId, long UserId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdUserIdToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUserIdToOneMap.get(keySiteId);

     	    Long keyUserId = new Long(UserId);
            
            if ( mapSiteId.containsKey(keyUserId)){
                return ( EcCheckoutAccountInfo)mapSiteId.get(keyUserId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdUserIdOneMap(Object obj, boolean del) {
        EcCheckoutAccountInfo o = (EcCheckoutAccountInfo)obj;

     	    Long keyUserId = new Long(o.getUserId());

        if (del) {
            // delete from SiteIdUserIdToOneMap
            Map mapSiteId  = (Map) m_SiteIdUserIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyUserId)){
                    mapSiteId.remove(keyUserId);
                }
            }
            m_logger.debug("removed from m_SiteIdUserIdToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getUserId());
        }
        else {
            
            // add to SiteIdUserIdToOneMap
            Map mapSiteId  = (Map) m_SiteIdUserIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdUserIdToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            
            
			EcCheckoutAccountInfo replaced = (EcCheckoutAccountInfo) mapSiteId.put(keyUserId,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdUserIdToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        


    public boolean persistEnable(){
        return false;
    }


    public static void main(String[] args) throws Exception {

        EcCheckoutAccountInfoDS ds = new EcCheckoutAccountInfoDS();
        EcCheckoutAccountInfo obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcCheckoutAccountInfo createDefault(){

        EcCheckoutAccountInfo ret = new EcCheckoutAccountInfo();        
//      ret.setUserId("");           
//      ret.setCartSerial("");           
//      ret.setEmail("");           
//      ret.setEmailRetype("");           
//      ret.setFirstName("");           
//      ret.setLastName("");           
//      ret.setAddress1("");           
//      ret.setAddress2("");           
//      ret.setCity("");           
//      ret.setCountryRegion("");           
//      ret.setStateProvince("");           
//      ret.setZip("");           
//      ret.setPhone("");           
//      ret.setUseBilling("");           
//      ret.setBillingFirstName("");           
//      ret.setBillingLastName("");           
//      ret.setBillingAddress1("");           
//      ret.setBillingAddress2("");           
//      ret.setBillingCity("");           
//      ret.setBillingCountry("");           
//      ret.setBillingState("");           
//      ret.setBillingZip("");           
//      ret.setBillingPhone("");           
//      ret.setTermAgree("");           
//      ret.setSubsEmail("");           
//      ret.setPassword("");           
//      ret.setPasswordRetype("");           
//      ret.setReturnEmail("");           
//      ret.setReturnPassword("");           
        return ret;
    }

    public static EcCheckoutAccountInfo copy(EcCheckoutAccountInfo org){

    	EcCheckoutAccountInfo ret = new EcCheckoutAccountInfo();

		ret.setUserId(org.getUserId());
		ret.setCartSerial(org.getCartSerial());
		ret.setEmail(org.getEmail());
		ret.setEmailRetype(org.getEmailRetype());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setAddress1(org.getAddress1());
		ret.setAddress2(org.getAddress2());
		ret.setCity(org.getCity());
		ret.setCountryRegion(org.getCountryRegion());
		ret.setStateProvince(org.getStateProvince());
		ret.setZip(org.getZip());
		ret.setPhone(org.getPhone());
		ret.setUseBilling(org.getUseBilling());
		ret.setBillingFirstName(org.getBillingFirstName());
		ret.setBillingLastName(org.getBillingLastName());
		ret.setBillingAddress1(org.getBillingAddress1());
		ret.setBillingAddress2(org.getBillingAddress2());
		ret.setBillingCity(org.getBillingCity());
		ret.setBillingCountry(org.getBillingCountry());
		ret.setBillingState(org.getBillingState());
		ret.setBillingZip(org.getBillingZip());
		ret.setBillingPhone(org.getBillingPhone());
		ret.setTermAgree(org.getTermAgree());
		ret.setSubsEmail(org.getSubsEmail());
		ret.setPassword(org.getPassword());
		ret.setPasswordRetype(org.getPasswordRetype());
		ret.setReturnEmail(org.getReturnEmail());
		ret.setReturnPassword(org.getReturnPassword());

        return ret;
    }

	public static void objectToLog(EcCheckoutAccountInfo ecCheckoutAccountInfo, Logger logger){
		logger.debug("EcCheckoutAccountInfo [" + ecCheckoutAccountInfo.getId() + "]" + objectToString(ecCheckoutAccountInfo));		
    }

	public static String objectToString(EcCheckoutAccountInfo ecCheckoutAccountInfo){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecCheckoutAccountInfo.getId()).append(", ");
		buf.append("SiteId=").append(ecCheckoutAccountInfo.getSiteId()).append(", ");
		buf.append("UserId=").append(ecCheckoutAccountInfo.getUserId()).append(", ");
		buf.append("CartSerial=").append(ecCheckoutAccountInfo.getCartSerial()).append(", ");
		buf.append("Email=").append(ecCheckoutAccountInfo.getEmail()).append(", ");
		buf.append("FirstName=").append(ecCheckoutAccountInfo.getFirstName()).append(", ");
		buf.append("LastName=").append(ecCheckoutAccountInfo.getLastName()).append(", ");
		return buf.toString();    
    }
}
