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
import com.autosite.db.EcAutositeUserAccount;
import com.jtrend.service.DomainStore;

import com.autosite.db.EcAutositeUserAccountDAO;

public class EcAutositeUserAccountDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToOneMap;


	protected Map m_SiteIdUserIdToOneMap;

    private static Logger m_logger = Logger.getLogger(EcAutositeUserAccountDS.class);
    private static EcAutositeUserAccountDS m_EcAutositeUserAccountDS = new EcAutositeUserAccountDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static EcAutositeUserAccountDS getInstance() {
        return m_EcAutositeUserAccountDS;
    }

    public static synchronized EcAutositeUserAccountDS getInstance(long id) {
        EcAutositeUserAccountDS ret = (EcAutositeUserAccountDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcAutositeUserAccountDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcAutositeUserAccountDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcAutositeUserAccountDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcAutositeUserAccountDS() {
        m_dao = new EcAutositeUserAccountDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToOneMap = new ConcurrentHashMap();
		m_SiteIdUserIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcAutositeUserAccountDS(long id) {
        m_dao = new EcAutositeUserAccountDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcAutositeUserAccount getById(Long id) {
        return (EcAutositeUserAccount) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcAutositeUserAccount o = (EcAutositeUserAccount)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcAutositeUserAccount removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcAutositeUserAccount added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdOneMap(obj, del);


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
        EcAutositeUserAccount o = (EcAutositeUserAccount)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcAutositeUserAccount removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcAutositeUserAccount added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public EcAutositeUserAccount getObjectByUserId(long keyUserId) {
        return (EcAutositeUserAccount)m_UserIdToOneMap.get(new Long(keyUserId));
    }

    private void updateUserIdOneMap(Object obj, boolean del) {
        EcAutositeUserAccount o = (EcAutositeUserAccount)obj;
        Long _UserId = new Long(o.getUserId());

        if (del) {
            // delete from UserIdToOneMap

            if (m_UserIdToOneMap.containsKey(_UserId)){
                m_UserIdToOneMap.remove(_UserId);
                if (m_debug) m_logger.debug("EcAutositeUserAccount removed from UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcAutositeUserAccount not removed from UserIdToMap" + o.getId() + " for [" + _UserId+ "]. Does not exist");
            } 
        }
        else {
            if (m_UserIdToOneMap.containsKey(_UserId)){
                if (m_debug) m_logger.debug("EcAutositeUserAccount repalced UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcAutositeUserAccount added to UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } 
            m_UserIdToOneMap.put(_UserId, o);
        }
    }




        
    public  EcAutositeUserAccount getBySiteIdUserId(long SiteId, long UserId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdUserIdToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUserIdToOneMap.get(keySiteId);

     	    Long keyUserId = new Long(UserId);
            
            if ( mapSiteId.containsKey(keyUserId)){
                return ( EcAutositeUserAccount)mapSiteId.get(keyUserId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdUserIdOneMap(Object obj, boolean del) {
        EcAutositeUserAccount o = (EcAutositeUserAccount)obj;

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
            
            
			EcAutositeUserAccount replaced = (EcAutositeUserAccount) mapSiteId.put(keyUserId,o);			
            if ( replaced != null){
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            m_logger.debug("Panel added to SiteIdUserIdToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        




    public static void main(String[] args) throws Exception {

        EcAutositeUserAccountDS ds = new EcAutositeUserAccountDS();
        EcAutositeUserAccount obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcAutositeUserAccount createDefault(){

        EcAutositeUserAccount ret = new EcAutositeUserAccount();        
//      ret.setUserId("");           
//      ret.setFirstName("");           
//      ret.setLastName("");           
//      ret.setSubscribed("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static EcAutositeUserAccount copy(EcAutositeUserAccount org){

    	EcAutositeUserAccount ret = new EcAutositeUserAccount();

		ret.setUserId(org.getUserId());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setSubscribed(org.getSubscribed());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(EcAutositeUserAccount ecAutositeUserAccount, Logger logger){
		logger.debug("EcAutositeUserAccount [" + ecAutositeUserAccount.getId() + "]" + objectToString(ecAutositeUserAccount));		
    }

	public static String objectToString(EcAutositeUserAccount ecAutositeUserAccount){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecAutositeUserAccount.getId()).append(", ");
		buf.append("SiteId=").append(ecAutositeUserAccount.getSiteId()).append(", ");
		buf.append("UserId=").append(ecAutositeUserAccount.getUserId()).append(", ");
		return buf.toString();    
    }
}
