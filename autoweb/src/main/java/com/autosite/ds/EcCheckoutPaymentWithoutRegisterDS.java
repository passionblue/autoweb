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
import com.autosite.db.EcCheckoutPaymentWithoutRegister;
import com.jtrend.service.DomainStore;


public class EcCheckoutPaymentWithoutRegisterDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;
    protected Map m_CartSerialToOneMap;


	protected Map m_SiteIdUserIdToOneMap;

    private static Logger m_logger = Logger.getLogger(EcCheckoutPaymentWithoutRegisterDS.class);
    private static EcCheckoutPaymentWithoutRegisterDS m_EcCheckoutPaymentWithoutRegisterDS = new EcCheckoutPaymentWithoutRegisterDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcCheckoutPaymentWithoutRegisterDS getInstance() {
        return m_EcCheckoutPaymentWithoutRegisterDS;
    }

    public static synchronized EcCheckoutPaymentWithoutRegisterDS getInstance(long id) {
        EcCheckoutPaymentWithoutRegisterDS ret = (EcCheckoutPaymentWithoutRegisterDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcCheckoutPaymentWithoutRegisterDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcCheckoutPaymentWithoutRegisterDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;


    protected EcCheckoutPaymentWithoutRegisterDS() {
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

    protected EcCheckoutPaymentWithoutRegisterDS(long id) {
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

    public EcCheckoutPaymentWithoutRegister getById(Long id) {
        return (EcCheckoutPaymentWithoutRegister) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcCheckoutPaymentWithoutRegister o = (EcCheckoutPaymentWithoutRegister)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister added to DS " + o.getId());
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
        EcCheckoutPaymentWithoutRegister o = (EcCheckoutPaymentWithoutRegister)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        EcCheckoutPaymentWithoutRegister o = (EcCheckoutPaymentWithoutRegister)obj;

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    

    public EcCheckoutPaymentWithoutRegister getObjectByCartSerial(String keyCartSerial) {
        return (EcCheckoutPaymentWithoutRegister)m_CartSerialToOneMap.get(keyCartSerial);
    }

    private void updateCartSerialOneMap(Object obj, boolean del) {
        EcCheckoutPaymentWithoutRegister o = (EcCheckoutPaymentWithoutRegister)obj;
        String _CartSerial =  o.getCartSerial();

        if (del) {
            // delete from CartSerialToMap

            if (m_CartSerialToOneMap.containsKey(_CartSerial)){
                m_CartSerialToOneMap.remove(_CartSerial);
                 if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister removed from CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } else {
                if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister not removed from CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } 
        }
        else {
            
            if (m_CartSerialToOneMap.containsKey(_CartSerial)){
                if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister repalced CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } else {
                if (m_debug) m_logger.debug("EcCheckoutPaymentWithoutRegister added to CartSerialToMap" + o.getId() + " for [" + _CartSerial+ "]");
            } 
            m_CartSerialToOneMap.put(_CartSerial, o);
        }
    }





        
    public  EcCheckoutPaymentWithoutRegister getBySiteIdUserId(long SiteId, long UserId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdUserIdToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUserIdToOneMap.get(keySiteId);

     	    Long keyUserId = new Long(UserId);
            
            if ( mapSiteId.containsKey(keyUserId)){
                return ( EcCheckoutPaymentWithoutRegister)mapSiteId.get(keyUserId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdUserIdOneMap(Object obj, boolean del) {
        EcCheckoutPaymentWithoutRegister o = (EcCheckoutPaymentWithoutRegister)obj;

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
            
            
			EcCheckoutPaymentWithoutRegister replaced = (EcCheckoutPaymentWithoutRegister) mapSiteId.put(keyUserId,o);			
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

        EcCheckoutPaymentWithoutRegisterDS ds = new EcCheckoutPaymentWithoutRegisterDS();
        EcCheckoutPaymentWithoutRegister obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcCheckoutPaymentWithoutRegister createDefault(){

        EcCheckoutPaymentWithoutRegister ret = new EcCheckoutPaymentWithoutRegister();        
//      ret.setUserId("");           
//      ret.setCartSerial("");           
//      ret.setPaymentType("");           
//      ret.setCardType("");           
//      ret.setPaymentNum("");           
//      ret.setExpireMonth("");           
//      ret.setExpireYear("");           
//      ret.setCcv("");           
        return ret;
    }

    public static EcCheckoutPaymentWithoutRegister copy(EcCheckoutPaymentWithoutRegister org){

    	EcCheckoutPaymentWithoutRegister ret = new EcCheckoutPaymentWithoutRegister();

		ret.setUserId(org.getUserId());
		ret.setCartSerial(org.getCartSerial());
		ret.setPaymentType(org.getPaymentType());
		ret.setCardType(org.getCardType());
		ret.setPaymentNum(org.getPaymentNum());
		ret.setExpireMonth(org.getExpireMonth());
		ret.setExpireYear(org.getExpireYear());
		ret.setCcv(org.getCcv());

        return ret;
    }

	public static void objectToLog(EcCheckoutPaymentWithoutRegister ecCheckoutPaymentWithoutRegister, Logger logger){
		logger.debug("EcCheckoutPaymentWithoutRegister [" + ecCheckoutPaymentWithoutRegister.getId() + "]" + objectToString(ecCheckoutPaymentWithoutRegister));		
    }

	public static String objectToString(EcCheckoutPaymentWithoutRegister ecCheckoutPaymentWithoutRegister){
		StringBuffer buf = new StringBuffer();
        buf.append("EcCheckoutPaymentWithoutRegister=");
		buf.append("Id=").append(ecCheckoutPaymentWithoutRegister.getId()).append(", ");
		buf.append("SiteId=").append(ecCheckoutPaymentWithoutRegister.getSiteId()).append(", ");
		buf.append("UserId=").append(ecCheckoutPaymentWithoutRegister.getUserId()).append(", ");
		buf.append("CartSerial=").append(ecCheckoutPaymentWithoutRegister.getCartSerial()).append(", ");
		buf.append("PaymentType=").append(ecCheckoutPaymentWithoutRegister.getPaymentType()).append(", ");
		buf.append("CardType=").append(ecCheckoutPaymentWithoutRegister.getCardType()).append(", ");
		buf.append("PaymentNum=").append(ecCheckoutPaymentWithoutRegister.getPaymentNum()).append(", ");
		buf.append("ExpireMonth=").append(ecCheckoutPaymentWithoutRegister.getExpireMonth()).append(", ");
		buf.append("ExpireYear=").append(ecCheckoutPaymentWithoutRegister.getExpireYear()).append(", ");
		buf.append("Ccv=").append(ecCheckoutPaymentWithoutRegister.getCcv()).append(", ");
		return buf.toString();    
    }
}
