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
import com.autosite.db.EcUserAccount;
import com.autosite.db.EcUserAccountDAO;
import com.jtrend.service.DomainStore;

public class EcUserAccountDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToOneMap;



    private static Logger m_logger = Logger.getLogger(EcUserAccountDS.class);
    private static EcUserAccountDS m_EcUserAccountDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcUserAccountDS getInstance() {
        if (m_EcUserAccountDS == null) {
            m_EcUserAccountDS = new EcUserAccountDS();
        }
        return m_EcUserAccountDS;
    }

    public static synchronized EcUserAccountDS getInstance(long id) {
        EcUserAccountDS ret = (EcUserAccountDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcUserAccountDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcUserAccountDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcUserAccountDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcUserAccountDS() {
        m_dao = new EcUserAccountDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcUserAccountDS(long id) {
        m_loadById = id;
        m_dao = new EcUserAccountDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcUserAccount getById(Long id) {
        return (EcUserAccount) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcUserAccount o = (EcUserAccount)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcUserAccount removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcUserAccount added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdOneMap(obj, del);



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
        EcUserAccount o = (EcUserAccount)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcUserAccount removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcUserAccount added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public EcUserAccount getObjectByUserId(long keyUserId) {
        return (EcUserAccount)m_UserIdToOneMap.get(new Long(keyUserId));
    }

    private void updateUserIdOneMap(Object obj, boolean del) {
        EcUserAccount o = (EcUserAccount)obj;
        Long _UserId = new Long(o.getUserId());

        if (del) {
            // delete from UserIdToOneMap

            if (m_UserIdToOneMap.containsKey(_UserId)){
                m_UserIdToOneMap.remove(_UserId);
                if (m_debug) m_logger.debug("EcUserAccount removed from UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcUserAccount not removed from UserIdToMap" + o.getId() + " for [" + _UserId+ "]. Does not exist");
            } 
        }
        else {
            if (m_UserIdToOneMap.containsKey(_UserId)){
                if (m_debug) m_logger.debug("EcUserAccount repalced UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcUserAccount added to UserIdToMap" + o.getId() + " for [" + _UserId+ "]");
            } 
            m_UserIdToOneMap.put(_UserId, o);
        }
    }







    public static void main(String[] args) throws Exception {

        EcUserAccountDS ds = new EcUserAccountDS();
        EcUserAccount obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcUserAccount createDefault(){

        EcUserAccount ret = new EcUserAccount();        
//      ret.setUserId("");           
//      ret.setFirstName("");           
//      ret.setLastName("");           
        return ret;
    }

    public static EcUserAccount copy(EcUserAccount org){

    	EcUserAccount ret = new EcUserAccount();

		ret.setUserId(org.getUserId());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());

        return ret;
    }

	public static void objectToLog(EcUserAccount ecUserAccount, Logger logger){
		logger.debug("EcUserAccount [" + ecUserAccount.getId() + "]" + objectToString(ecUserAccount));		
    }

	public static String objectToString(EcUserAccount ecUserAccount){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecUserAccount.getId()).append(", ");
		buf.append("SiteId=").append(ecUserAccount.getSiteId()).append(", ");
		buf.append("UserId=").append(ecUserAccount.getUserId()).append(", ");
		return buf.toString();    
    }
}
