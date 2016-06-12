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
import com.autosite.db.SweepInvitation;
import com.jtrend.service.DomainStore;

import com.autosite.db.SweepInvitationDAO;

public class SweepInvitationDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;


	protected Map m_UserIdEmailToOneMap;




    private static Logger m_logger = Logger.getLogger(SweepInvitationDS.class);
    private static SweepInvitationDS m_SweepInvitationDS = new SweepInvitationDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static SweepInvitationDS getInstance() {
        return m_SweepInvitationDS;
    }

    public static synchronized SweepInvitationDS getInstance(long id) {
        SweepInvitationDS ret = (SweepInvitationDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new SweepInvitationDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_SweepInvitationDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((SweepInvitationDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("SweepInvitation loaded from DB. num=" + m_idToMap.size());
        
    }

    protected SweepInvitationDS() {
        m_dao = new SweepInvitationDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
		m_UserIdEmailToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected SweepInvitationDS(long id) {
        m_dao = new SweepInvitationDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public SweepInvitation getById(Long id) {
        return (SweepInvitation) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        SweepInvitation o = (SweepInvitation)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("SweepInvitation removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("SweepInvitation added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);
		updateUserIdEmailOneMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        SweepInvitation o = (SweepInvitation)obj;

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
            if (m_debug) m_logger.debug("SweepInvitation removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("SweepInvitation added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - UserId
    public List getByUserId(long UserId) {
        
        Long _UserId  = new Long(UserId);
        if (m_UserIdToMap.containsKey(_UserId)) {
            return new ArrayList( ((Map)m_UserIdToMap.get(_UserId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateUserIdMap(Object obj, boolean del) {
        SweepInvitation o = (SweepInvitation)obj;

		if ( o.getUserId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("SweepInvitation removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("SweepInvitation added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    





        
    public  SweepInvitation getByUserIdEmail(long UserId, String Email) {

        Long keyUserId  = new Long(UserId);
        if (m_UserIdEmailToOneMap.containsKey(keyUserId)) {
            
            Map mapUserId = (Map)m_UserIdEmailToOneMap.get(keyUserId);

     	    String keyEmail =  Email;
            if (  keyEmail == null || keyEmail.isEmpty()) return null;
            
            if ( mapUserId.containsKey(keyEmail)){
                return ( SweepInvitation)mapUserId.get(keyEmail);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateUserIdEmailOneMap(Object obj, boolean del) {
        SweepInvitation o = (SweepInvitation)obj;

     	    String keyEmail =  o.getEmail();
            if ( keyEmail == null || keyEmail.isEmpty()) return;

        if (del) {
            // delete from UserIdEmailToOneMap
            Map mapUserId  = (Map) m_UserIdEmailToOneMap.get(new Long(o.getUserId()));
            if ( mapUserId != null ) {
                if (mapUserId.containsKey(keyEmail)){
                    mapUserId.remove(keyEmail);
                }
            }
            m_logger.debug("removed SweepInvitation from m_UserIdEmailToOneMap" + o.getId() + " from " + o.getUserId() + " # " + o.getEmail());
        }
        else {
            
            // add to UserIdEmailToOneMap
            Map mapUserId  = (Map) m_UserIdEmailToOneMap.get(new Long(o.getUserId()));
            if ( mapUserId == null ) {
                mapUserId = new TreeMap();
                m_UserIdEmailToOneMap.put(new Long(o.getUserId()), mapUserId);
                if (m_debug) m_logger.debug("created new mapUserId for " + o.getUserId());
            }
            
            
			SweepInvitation replaced = (SweepInvitation) mapUserId.put(keyEmail,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: UserIdEmailOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to UserIdEmailToOneMap " + o.getId() + " to " + o.getUserId());
        }
        
    }    
        






    public static void main(String[] args) throws Exception {

        SweepInvitationDS ds = new SweepInvitationDS();
        SweepInvitation obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static SweepInvitation createDefault(){

        SweepInvitation ret = new SweepInvitation();        
//      ret.setUserId("");           
//      ret.setEmail("");           
//      ret.setMessage("");           
//      ret.setInvitationSent("");           
//      ret.setTimeCreated("");           
//      ret.setTimeSent("");           
        return ret;
    }

    public static SweepInvitation copy(SweepInvitation org){

    	SweepInvitation ret = new SweepInvitation();

		ret.setUserId(org.getUserId());
		ret.setEmail(org.getEmail());
		ret.setMessage(org.getMessage());
		ret.setInvitationSent(org.getInvitationSent());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeSent(org.getTimeSent());

        return ret;
    }

	public static void objectToLog(SweepInvitation sweepInvitation, Logger logger){
		logger.debug("SweepInvitation [" + sweepInvitation.getId() + "]" + objectToString(sweepInvitation));		
    }

	public static String objectToString(SweepInvitation sweepInvitation){
		StringBuffer buf = new StringBuffer();
        buf.append("SweepInvitation=");
		buf.append("Id=").append(sweepInvitation.getId()).append(", ");
		buf.append("SiteId=").append(sweepInvitation.getSiteId()).append(", ");
		buf.append("UserId=").append(sweepInvitation.getUserId()).append(", ");
		buf.append("Email=").append(sweepInvitation.getEmail()).append(", ");
		buf.append("InvitationSent=").append(sweepInvitation.getInvitationSent()).append(", ");
		return buf.toString();    
    }
}
