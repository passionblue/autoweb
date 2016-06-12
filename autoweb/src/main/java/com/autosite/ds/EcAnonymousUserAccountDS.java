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
import com.autosite.db.EcAnonymousUserAccount;
import com.jtrend.service.DomainStore;

import com.autosite.db.EcAnonymousUserAccountDAO;

public class EcAnonymousUserAccountDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;


	protected Map m_SiteIdEmailToOneMap;

    private static Logger m_logger = Logger.getLogger(EcAnonymousUserAccountDS.class);
    private static EcAnonymousUserAccountDS m_EcAnonymousUserAccountDS = new EcAnonymousUserAccountDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcAnonymousUserAccountDS getInstance() {
        return m_EcAnonymousUserAccountDS;
    }

    public static synchronized EcAnonymousUserAccountDS getInstance(long id) {
        EcAnonymousUserAccountDS ret = (EcAnonymousUserAccountDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcAnonymousUserAccountDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcAnonymousUserAccountDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcAnonymousUserAccountDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcAnonymousUserAccountDS() {
        m_dao = new EcAnonymousUserAccountDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdEmailToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcAnonymousUserAccountDS(long id) {
        m_dao = new EcAnonymousUserAccountDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcAnonymousUserAccount getById(Long id) {
        return (EcAnonymousUserAccount) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcAnonymousUserAccount o = (EcAnonymousUserAccount)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcAnonymousUserAccount removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcAnonymousUserAccount added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);


		updateSiteIdEmailOneMap(obj, del);

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
        EcAnonymousUserAccount o = (EcAnonymousUserAccount)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcAnonymousUserAccount removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcAnonymousUserAccount added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    





        
    public  EcAnonymousUserAccount getBySiteIdEmail(long SiteId, long Email) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdEmailToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdEmailToOneMap.get(keySiteId);

     	    Long keyEmail = new Long(Email);
            
            if ( mapSiteId.containsKey(keyEmail)){
                return ( EcAnonymousUserAccount)mapSiteId.get(keyEmail);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdEmailOneMap(Object obj, boolean del) {
        EcAnonymousUserAccount o = (EcAnonymousUserAccount)obj;

     	    String keyEmail =  o.getEmail();

        if (del) {
            // delete from SiteIdEmailToOneMap
            Map mapSiteId  = (Map) m_SiteIdEmailToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyEmail)){
                    mapSiteId.remove(keyEmail);
                }
            }
            m_logger.debug("removed from m_SiteIdEmailToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getEmail());
        }
        else {
            
            // add to SiteIdEmailToOneMap
            Map mapSiteId  = (Map) m_SiteIdEmailToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdEmailToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            
            
			EcAnonymousUserAccount replaced = (EcAnonymousUserAccount) mapSiteId.put(keyEmail,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdEmailToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        EcAnonymousUserAccountDS ds = new EcAnonymousUserAccountDS();
        EcAnonymousUserAccount obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcAnonymousUserAccount createDefault(){

        EcAnonymousUserAccount ret = new EcAnonymousUserAccount();        
//      ret.setEmail("");           
//      ret.setSubscribed("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static EcAnonymousUserAccount copy(EcAnonymousUserAccount org){

    	EcAnonymousUserAccount ret = new EcAnonymousUserAccount();

		ret.setEmail(org.getEmail());
		ret.setSubscribed(org.getSubscribed());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(EcAnonymousUserAccount ecAnonymousUserAccount, Logger logger){
		logger.debug("EcAnonymousUserAccount [" + ecAnonymousUserAccount.getId() + "]" + objectToString(ecAnonymousUserAccount));		
    }

	public static String objectToString(EcAnonymousUserAccount ecAnonymousUserAccount){
		StringBuffer buf = new StringBuffer();
        buf.append("EcAnonymousUserAccount=");
		buf.append("Id=").append(ecAnonymousUserAccount.getId()).append(", ");
        buf.append("SiteId=").append(ecAnonymousUserAccount.getSiteId()).append(", ");
        buf.append("Email=").append(ecAnonymousUserAccount.getEmail()).append(", ");
		return buf.toString();    
    }
}
