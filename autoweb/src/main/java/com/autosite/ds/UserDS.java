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
import com.autosite.db.User;
import com.autosite.db.UserDAO;
import com.jtrend.service.DomainStore;

public class UserDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

	protected Map m_SiteIdUsernameToMap;

    private static Logger m_logger = Logger.getLogger(UserDS.class);
    private static UserDS m_UserDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized UserDS getInstance() {
        if (m_UserDS == null) {
            m_UserDS = new UserDS();
        }
        return m_UserDS;
    }

    public static synchronized UserDS getInstance(long id) {
        UserDS ret = (UserDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new UserDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_UserDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((UserDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected UserDS() {
        m_dao = new UserDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdUsernameToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected UserDS(long id) {
        m_loadById = id;
        m_dao = new UserDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public User getById(Long id) {
        return (User) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        User o = (User)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("User removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("User added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);

		updateSiteIdUsernameMap(obj, del);

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
        User o = (User)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("User removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("User added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


        
    public List getBySiteIdUsername(long SiteId, String Username) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdUsernameToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUsernameToMap.get(keySiteId);

     	    String keyUsername =  Username;
            
            if ( mapSiteId.containsKey(keyUsername)){
                return new ArrayList( ((Map)mapSiteId.get(keyUsername)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    private void updateSiteIdUsernameMap(Object obj, boolean del) {
        User o = (User)obj;

     	    String keyUsername =  o.getUsername();

        if (del) {
            // delete from SiteIdUsernameToMap
            Map mapSiteId  = (Map) m_SiteIdUsernameToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapUsername = (Map) mapSiteId.get(keyUsername);
                if (mapUsername != null){
                    mapUsername.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed from m_SiteIdUsernameToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getUsername());
        }
        else {
            
            // add to SiteIdUsernameToMap
            Map mapSiteId  = (Map) m_SiteIdUsernameToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdUsernameToMap.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapUsername = (Map) mapSiteId.get(keyUsername);
            
            if ( mapUsername == null) {
                mapUsername = new TreeMap();
                mapSiteId.put(new Integer(o.getUsername()), mapUsername);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapUsername.put(new Long(o.getId()), o);            
            
            m_logger.debug("Panel added to SiteIdUsernameToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        



    public static void main(String[] args) throws Exception {

        UserDS ds = new UserDS();
        User obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static User createDefault(){

        User ret = new User();        
//      ret.setUsername("");           
//      ret.setPassword("");           
//      ret.setEmail("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static User copy(User org){

    	User ret = new User();

		ret.setUsername(org.getUsername());
		ret.setPassword(org.getPassword());
		ret.setEmail(org.getEmail());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(User user, Logger logger){
		logger.debug("User [" + user.getId() + "]" + objectToString(user));		
    }

	public static String objectToString(User user){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(user.getId()).append(", ");
		buf.append("SiteId=").append(user.getSiteId()).append(", ");
		buf.append("Username=").append(user.getUsername()).append(", ");
		buf.append("Email=").append(user.getEmail()).append(", ");
		return buf.toString();    
    }
}
