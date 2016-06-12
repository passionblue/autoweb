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
import com.autosite.db.AutositeAccount;
import com.jtrend.service.DomainStore;

import com.autosite.db.AutositeAccountDAO;

public class AutositeAccountDS extends AbstractDS implements DomainStore {

    protected Map m_AccountOwnerIdToMap;
    protected Map m_SiteIdToOneMap;
    protected Map m_AccountNumToOneMap;



    private static Logger m_logger = Logger.getLogger(AutositeAccountDS.class);
    private static AutositeAccountDS m_AutositeAccountDS = new AutositeAccountDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static AutositeAccountDS getInstance() {
        return m_AutositeAccountDS;
    }

    public static synchronized AutositeAccountDS getInstance(long id) {
        AutositeAccountDS ret = (AutositeAccountDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new AutositeAccountDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_AutositeAccountDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((AutositeAccountDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected AutositeAccountDS() {
        m_dao = new AutositeAccountDAO();
        m_idToMap = new ConcurrentHashMap();

        m_AccountOwnerIdToMap = new ConcurrentHashMap();
        m_AccountNumToOneMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected AutositeAccountDS(long id) {
        m_dao = new AutositeAccountDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_AccountOwnerIdToMap = new ConcurrentHashMap();
        m_AccountNumToOneMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public AutositeAccount getById(Long id) {
        return (AutositeAccount) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        AutositeAccount o = (AutositeAccount)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("AutositeAccount removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("AutositeAccount added to DS " + o.getId());
        }

        updateAccountOwnerIdMap(obj, del);
        updateAccountNumOneMap(obj, del);
        updateSiteIdOneMap(obj, del);



    }


    public List getByAccountOwnerId(long AccountOwnerId) {
        
        Long _AccountOwnerId  = new Long(AccountOwnerId);
        if (m_AccountOwnerIdToMap.containsKey(_AccountOwnerId)) {
            return new ArrayList( ((Map)m_AccountOwnerIdToMap.get(_AccountOwnerId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateAccountOwnerIdMap(Object obj, boolean del) {
        AutositeAccount o = (AutositeAccount)obj;

        if (del) {

            // delete from AccountOwnerIdToMap
            Map map  = (Map) m_AccountOwnerIdToMap.get(new Long(o.getAccountOwnerId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("AutositeAccount removed from AccountOwnerIdToMap" + o.getId() + " from " + o.getAccountOwnerId());
        }
        else {
            
            // add to AccountOwnerIdToMap
            Map map  = (Map) m_AccountOwnerIdToMap.get(new Long(o.getAccountOwnerId()));
            if ( map == null ) {
                map = new TreeMap();
                m_AccountOwnerIdToMap.put(new Long(o.getAccountOwnerId()), map);
                if (m_debug) m_logger.debug("created new   AccountOwnerIdToMap for " + o.getAccountOwnerId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("AutositeAccount added to AccountOwnerIdToMap " + o.getId() + " to " + o.getAccountOwnerId());
        }
    }


    

    public AutositeAccount getObjectByAccountNum(String keyAccountNum) {
        return (AutositeAccount)m_AccountNumToOneMap.get(keyAccountNum);
    }

    private void updateAccountNumOneMap(Object obj, boolean del) {
        AutositeAccount o = (AutositeAccount)obj;
        String _AccountNum =  o.getAccountNum();

		if (  _AccountNum == null ) return;

        if (del) {
            // delete from AccountNumToMap

            if (m_AccountNumToOneMap.containsKey(_AccountNum)){
                m_AccountNumToOneMap.remove(_AccountNum);
                 if (m_debug) m_logger.debug("AutositeAccount removed from AccountNumToMap" + o.getId() + " for [" + _AccountNum+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeAccount not removed from AccountNumToMap" + o.getId() + " for [" + _AccountNum+ "]");
            } 
        }
        else {
            
            if (m_AccountNumToOneMap.containsKey(_AccountNum)){
                if (m_debug) m_logger.debug("AutositeAccount repalced AccountNumToMap" + o.getId() + " for [" + _AccountNum+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeAccount added to AccountNumToMap" + o.getId() + " for [" + _AccountNum+ "]");
            } 
            m_AccountNumToOneMap.put(_AccountNum, o);
        }
    }


    public AutositeAccount getObjectBySiteId(long keySiteId) {
        return (AutositeAccount)m_SiteIdToOneMap.get(new Long(keySiteId));
    }

    private void updateSiteIdOneMap(Object obj, boolean del) {
        AutositeAccount o = (AutositeAccount)obj;
        Long _SiteId = new Long(o.getSiteId());

        if (del) {
            // delete from SiteIdToOneMap

            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                m_SiteIdToOneMap.remove(_SiteId);
                if (m_debug) m_logger.debug("AutositeAccount removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeAccount not removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]. Does not exist");
            } 
        }
        else {
            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                if (m_debug) m_logger.debug("AutositeAccount repalced SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeAccount added to SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } 
            m_SiteIdToOneMap.put(_SiteId, o);
        }
    }








    public static void main(String[] args) throws Exception {

        AutositeAccountDS ds = new AutositeAccountDS();
        AutositeAccount obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static AutositeAccount createDefault(){

        AutositeAccount ret = new AutositeAccount();        
//      ret.setAccountNum("");           
//      ret.setEnabled("");           
//      ret.setEmail("");           
//      ret.setFirstName("");           
//      ret.setLastName("");           
//      ret.setCompany("");           
//      ret.setPhone("");           
//      ret.setEmailConfirmed("");           
//      ret.setInBetaTest("");           
//      ret.setInEvalution("");           
//      ret.setTimeEvalEnds("");           
//      ret.setTimeConfirmed("");           
//      ret.setTimeCreated("");           
//      ret.setAccountOwnerId("");           
        return ret;
    }

    public static AutositeAccount copy(AutositeAccount org){

    	AutositeAccount ret = new AutositeAccount();

		ret.setAccountNum(org.getAccountNum());
		ret.setEnabled(org.getEnabled());
		ret.setEmail(org.getEmail());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setCompany(org.getCompany());
		ret.setPhone(org.getPhone());
		ret.setEmailConfirmed(org.getEmailConfirmed());
		ret.setInBetaTest(org.getInBetaTest());
		ret.setInEvalution(org.getInEvalution());
		ret.setTimeEvalEnds(org.getTimeEvalEnds());
		ret.setTimeConfirmed(org.getTimeConfirmed());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setAccountOwnerId(org.getAccountOwnerId());

        return ret;
    }

	public static void objectToLog(AutositeAccount autositeAccount, Logger logger){
		logger.debug("AutositeAccount [" + autositeAccount.getId() + "]" + objectToString(autositeAccount));		
    }

	public static String objectToString(AutositeAccount autositeAccount){
		StringBuffer buf = new StringBuffer();
        buf.append("AutositeAccount=");
		buf.append("Id=").append(autositeAccount.getId()).append(", ");
		buf.append("SiteId=").append(autositeAccount.getSiteId()).append(", ");
		return buf.toString();    
    }
}
