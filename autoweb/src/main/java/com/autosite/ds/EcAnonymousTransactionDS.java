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
import com.autosite.db.EcAnonymousTransaction;
import com.jtrend.service.DomainStore;

import com.autosite.db.EcAnonymousTransactionDAO;

public class EcAnonymousTransactionDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_AnonymousUserIdToOneMap;



    private static Logger m_logger = Logger.getLogger(EcAnonymousTransactionDS.class);
    private static EcAnonymousTransactionDS m_EcAnonymousTransactionDS = new EcAnonymousTransactionDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcAnonymousTransactionDS getInstance() {
        return m_EcAnonymousTransactionDS;
    }

    public static synchronized EcAnonymousTransactionDS getInstance(long id) {
        EcAnonymousTransactionDS ret = (EcAnonymousTransactionDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcAnonymousTransactionDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcAnonymousTransactionDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcAnonymousTransactionDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcAnonymousTransactionDS() {
        m_dao = new EcAnonymousTransactionDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_AnonymousUserIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcAnonymousTransactionDS(long id) {
        m_dao = new EcAnonymousTransactionDAO();
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

    public EcAnonymousTransaction getById(Long id) {
        return (EcAnonymousTransaction) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcAnonymousTransaction o = (EcAnonymousTransaction)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcAnonymousTransaction removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcAnonymousTransaction added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateAnonymousUserIdOneMap(obj, del);



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
        EcAnonymousTransaction o = (EcAnonymousTransaction)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcAnonymousTransaction removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcAnonymousTransaction added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public EcAnonymousTransaction getObjectByAnonymousUserId(long keyAnonymousUserId) {
        return (EcAnonymousTransaction)m_AnonymousUserIdToOneMap.get(new Long(keyAnonymousUserId));
    }

    private void updateAnonymousUserIdOneMap(Object obj, boolean del) {
        EcAnonymousTransaction o = (EcAnonymousTransaction)obj;
        Long _AnonymousUserId = new Long(o.getAnonymousUserId());

        if (del) {
            // delete from AnonymousUserIdToOneMap

            if (m_AnonymousUserIdToOneMap.containsKey(_AnonymousUserId)){
                m_AnonymousUserIdToOneMap.remove(_AnonymousUserId);
                if (m_debug) m_logger.debug("EcAnonymousTransaction removed from AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcAnonymousTransaction not removed from AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]. Does not exist");
            } 
        }
        else {
            if (m_AnonymousUserIdToOneMap.containsKey(_AnonymousUserId)){
                if (m_debug) m_logger.debug("EcAnonymousTransaction repalced AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcAnonymousTransaction added to AnonymousUserIdToMap" + o.getId() + " for [" + _AnonymousUserId+ "]");
            } 
            m_AnonymousUserIdToOneMap.put(_AnonymousUserId, o);
        }
    }








    public static void main(String[] args) throws Exception {

        EcAnonymousTransactionDS ds = new EcAnonymousTransactionDS();
        EcAnonymousTransaction obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcAnonymousTransaction createDefault(){

        EcAnonymousTransaction ret = new EcAnonymousTransaction();        
//      ret.setAnonymousUserId("");           
//      ret.setOrderId("");           
//      ret.setPaymentInfoId("");           
//      ret.setAmount("");           
//      ret.setTransactionType("");           
//      ret.setResult("");           
//      ret.setTimeProcessed("");           
//      ret.setReturnCode("");           
//      ret.setReturnMsg("");           
        return ret;
    }

    public static EcAnonymousTransaction copy(EcAnonymousTransaction org){

    	EcAnonymousTransaction ret = new EcAnonymousTransaction();

		ret.setAnonymousUserId(org.getAnonymousUserId());
		ret.setOrderId(org.getOrderId());
		ret.setPaymentInfoId(org.getPaymentInfoId());
		ret.setAmount(org.getAmount());
		ret.setTransactionType(org.getTransactionType());
		ret.setResult(org.getResult());
		ret.setTimeProcessed(org.getTimeProcessed());
		ret.setReturnCode(org.getReturnCode());
		ret.setReturnMsg(org.getReturnMsg());

        return ret;
    }

	public static void objectToLog(EcAnonymousTransaction ecAnonymousTransaction, Logger logger){
		logger.debug("EcAnonymousTransaction [" + ecAnonymousTransaction.getId() + "]" + objectToString(ecAnonymousTransaction));		
    }

	public static String objectToString(EcAnonymousTransaction ecAnonymousTransaction){
		StringBuffer buf = new StringBuffer();
        buf.append("EcAnonymousTransaction=");
		buf.append("Id=").append(ecAnonymousTransaction.getId()).append(", ");
		buf.append("SiteId=").append(ecAnonymousTransaction.getSiteId()).append(", ");
		buf.append("AnonymousUserId=").append(ecAnonymousTransaction.getAnonymousUserId()).append(", ");
		buf.append("OrderId=").append(ecAnonymousTransaction.getOrderId()).append(", ");
		buf.append("PaymentInfoId=").append(ecAnonymousTransaction.getPaymentInfoId()).append(", ");
		buf.append("Amount=").append(ecAnonymousTransaction.getAmount()).append(", ");
		buf.append("TransactionType=").append(ecAnonymousTransaction.getTransactionType()).append(", ");
		buf.append("Result=").append(ecAnonymousTransaction.getResult()).append(", ");
		buf.append("TimeProcessed=").append(ecAnonymousTransaction.getTimeProcessed()).append(", ");
		return buf.toString();    
    }
}
