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
import com.jtrend.util.AggregatedIdMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.AutositeUserDAO;
import com.autosite.db.AutositeUserExtentDAO;
import com.autosite.db.AutositeUser;
import com.surveygen.db.BaseMemoryDAO;

public class AutositeUserDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;

	protected Map m_SiteIdUserTypeToMap;

	protected Map m_SiteIdUsernameToOneMap;




    private static Logger m_logger = Logger.getLogger(AutositeUserDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static AutositeUserDS m_AutositeUserDS = new AutositeUserDSExtent();



    public static AutositeUserDS getInstance() {
        return m_AutositeUserDS;
    }

    public static synchronized AutositeUserDS getInstance(long id) {
        AutositeUserDS ret = (AutositeUserDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new AutositeUserDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_AutositeUserDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((AutositeUserDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("AutositeUser loaded from DB. num=" + m_idToMap.size());
        
    }


    protected AutositeUserDS() {
        m_dao = new AutositeUserExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdUserTypeToMap = new ConcurrentHashMap();
		m_SiteIdUsernameToOneMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected AutositeUserDS(long id) {
        m_dao = new AutositeUserDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public AutositeUser getById(Long id) {
        return (AutositeUser) m_idToMap.get(id);
    }

    public AutositeUser getById(Long id, boolean synch) {
        if (persistEnable()) {
            try {
                AutositeUser loaded = (AutositeUser) synchFromDB(id);
                return loaded;
            }
            catch (Exception e) {
                m_logger.error("",e);
            }
        }
        
        return null;
    }
    
    public String getEntityClass(){
        return AutositeUser.class.getName();
    }


    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        AutositeUser o = (AutositeUser)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("AutositeUser removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("AutositeUser added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateSiteIdUserTypeMap(obj, del);
		updateSiteIdUsernameOneMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            if ( m_aggregateAllSites) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        AutositeUser o = (AutositeUser)obj;

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
            if (m_debug) m_logger.debug("AutositeUser removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("AutositeUser added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    



        
    public List getBySiteIdUserType(long SiteId, int UserType) {

     	    Long keySiteId = new Long(SiteId);

        if (m_SiteIdUserTypeToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUserTypeToMap.get(keySiteId);

     	    Integer keyUserType = new Integer(UserType);
            
            if ( mapSiteId.containsKey(keyUserType)){
                return new ArrayList( ((Map)mapSiteId.get(keyUserType)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdUserType(long SiteId, int UserType) {

     	    Long keySiteId = new Long(SiteId);

        if (m_SiteIdUserTypeToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUserTypeToMap.get(keySiteId);

     	    Integer keyUserType = new Integer(UserType);
            
            if ( mapSiteId.containsKey(keyUserType)){
                return (Map)mapSiteId.get(keyUserType);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdUserTypeMap(Object obj, boolean del) {
        AutositeUser o = (AutositeUser)obj;

     	    Long keySiteId = new Long(o.getSiteId());

   	    Integer keyUserType = new Integer(o.getUserType());

        if (del) {
            // delete from SiteIdUserTypeToMap
            Map mapSiteId  = (Map) m_SiteIdUserTypeToMap.get(keySiteId);
            if ( mapSiteId != null ) {
                Map mapUserType = (Map) mapSiteId.get(keyUserType);
                if (mapUserType != null){
                    mapUserType.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed AutositeUser from m_SiteIdUserTypeToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getUserType());
        }
        else {
            
            // add to SiteIdUserTypeToMap
            Map mapSiteId  = (Map) m_SiteIdUserTypeToMap.get(keySiteId);
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdUserTypeToMap.put(keySiteId, mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapUserType = (Map) mapSiteId.get(keyUserType);
            
            if ( mapUserType == null) {
                mapUserType = new TreeMap();
                mapSiteId.put(keyUserType, mapUserType);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapUserType.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdUserTypeToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        

        
    public  AutositeUser getBySiteIdUsername(long SiteId, String Username) {

	        Long keySiteId  = new Long(SiteId);

        if (m_SiteIdUsernameToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdUsernameToOneMap.get(keySiteId);

     	    String keyUsername =  Username;
            if (  keyUsername == null || keyUsername.isEmpty()) return null;
            
            if ( mapSiteId.containsKey(keyUsername)){
                return ( AutositeUser)mapSiteId.get(keyUsername);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdUsernameOneMap(Object obj, boolean del) {
        AutositeUser o = (AutositeUser)obj;

	        Long keySiteId  = new Long(o.getSiteId());


     	    String keyUsername =  o.getUsername();
            if ( keyUsername == null || keyUsername.isEmpty()) return;

        if (del) {
            // delete from SiteIdUsernameToOneMap
            Map mapSiteId  = (Map) m_SiteIdUsernameToOneMap.get(keySiteId);
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyUsername)){
                    mapSiteId.remove(keyUsername);
                }
            }
            m_logger.debug("removed AutositeUser from m_SiteIdUsernameToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getUsername());
        }
        else {
            
            // add to SiteIdUsernameToOneMap
            Map mapSiteId  = (Map) m_SiteIdUsernameToOneMap.get(keySiteId);
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdUsernameToOneMap.put(keySiteId, mapSiteId);
                if (m_debug) m_logger.debug("created new mapSiteId for " + o.getSiteId());
            }
            
            
			AutositeUser replaced = (AutositeUser) mapSiteId.put(keyUsername,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdUsernameOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to SiteIdUsernameToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        







	//

    public static void main(String[] args) throws Exception {

        AutositeUserDS ds = new AutositeUserDS();
        AutositeUser obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static AutositeUser createDefault(){

        AutositeUser _AutositeUser = new AutositeUser();        
	    _AutositeUser = new AutositeUser();// AutositeUserDS.getInstance().getDeafult();
        return _AutositeUser;
    }

    public static AutositeUser copy(AutositeUser org){

    	AutositeUser ret = new AutositeUser();

		ret.setUsername(org.getUsername());
		ret.setPassword(org.getPassword());
		ret.setEmail(org.getEmail());
		ret.setUserType(org.getUserType());
		ret.setFirstName(org.getFirstName());
		ret.setLastName(org.getLastName());
		ret.setNickname(org.getNickname());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());
		ret.setDisabled(org.getDisabled());
		ret.setTimeDisabled(org.getTimeDisabled());
		ret.setConfirmed(org.getConfirmed());
		ret.setTimeConfirmed(org.getTimeConfirmed());
		ret.setPagelessSession(org.getPagelessSession());
		ret.setOpt1(org.getOpt1());
		ret.setOpt2(org.getOpt2());

        return ret;
    }

	public static void objectToLog(AutositeUser autositeUser, Logger logger){
		logger.debug("AutositeUser [" + autositeUser.getId() + "]" + objectToString(autositeUser));		
    }

	public static String objectToString(AutositeUser autositeUser){
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeUser=");
		buf.append("Id=").append(autositeUser.getId()).append(", ");
		buf.append("SiteId=").append(autositeUser.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		AutositeUser autositeUser = (AutositeUser)object;
		StringBuilder buf = new StringBuilder();
        buf.append("AutositeUser=");
		buf.append("Id=").append(autositeUser.getId()).append(", ");
		buf.append("SiteId=").append(autositeUser.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
