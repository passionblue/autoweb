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
import com.autosite.db.PollVote;
import com.jtrend.service.DomainStore;

import com.autosite.db.PollVoteDAO;

public class PollVoteDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;
    protected Map m_PollIdToMap;

	protected Map m_PollIdDupCheckKeyToMap;

	protected Map m_PollIdUserIdToOneMap;





    private static Logger m_logger = Logger.getLogger(PollVoteDS.class);
    private static PollVoteDS m_PollVoteDS = new PollVoteDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PollVoteDS getInstance() {
        return m_PollVoteDS;
    }

    public static synchronized PollVoteDS getInstance(long id) {
        PollVoteDS ret = (PollVoteDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PollVoteDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PollVoteDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PollVoteDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PollVote loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PollVoteDS() {
        m_dao = new PollVoteDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_PollIdToMap = new ConcurrentHashMap();
		m_PollIdDupCheckKeyToMap = new ConcurrentHashMap();
		m_PollIdUserIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PollVoteDS(long id) {
        m_dao = new PollVoteDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        m_PollIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PollVote getById(Long id) {
        return (PollVote) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PollVote o = (PollVote)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PollVote removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PollVote added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);
        updatePollIdMap(obj, del);
		updatePollIdDupCheckKeyMap(obj, del);
		updatePollIdUserIdOneMap(obj, del);
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
        PollVote o = (PollVote)obj;

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
            if (m_debug) m_logger.debug("PollVote removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PollVote added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        PollVote o = (PollVote)obj;

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
            if (m_debug) m_logger.debug("PollVote removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("PollVote added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }
	// ListKeys - PollId
    public List getByPollId(long PollId) {
        
        Long _PollId  = new Long(PollId);
        if (m_PollIdToMap.containsKey(_PollId)) {
            return new ArrayList( ((Map)m_PollIdToMap.get(_PollId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updatePollIdMap(Object obj, boolean del) {
        PollVote o = (PollVote)obj;

		if ( o.getPollId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from PollIdToMap
            Map map  = (Map) m_PollIdToMap.get(new Long(o.getPollId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("PollVote removed from PollIdToMap" + o.getId() + " from " + o.getPollId());
        }
        else {
            
            // add to PollIdToMap
            Map map  = (Map) m_PollIdToMap.get(new Long(o.getPollId()));
            if ( map == null ) {
                map = new TreeMap();
                m_PollIdToMap.put(new Long(o.getPollId()), map);
                if (m_debug) m_logger.debug("created new   PollIdToMap for " + o.getPollId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("PollVote added to PollIdToMap " + o.getId() + " to " + o.getPollId());
        }
    }


    




        
    public List getByPollIdDupCheckKey(long PollId, String DupCheckKey) {

        Long keyPollId  = new Long(PollId);
        if (m_PollIdDupCheckKeyToMap.containsKey(keyPollId)) {
            
            Map mapPollId = (Map)m_PollIdDupCheckKeyToMap.get(keyPollId);

     	    String keyDupCheckKey =  DupCheckKey;
			if (  keyDupCheckKey == null || keyDupCheckKey.isEmpty()) return new ArrayList();
            
            if ( mapPollId.containsKey(keyDupCheckKey)){
                return new ArrayList( ((Map)mapPollId.get(keyDupCheckKey)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapByPollIdDupCheckKey(long PollId, String DupCheckKey) {

        Long keyPollId  = new Long(PollId);
        if (m_PollIdDupCheckKeyToMap.containsKey(keyPollId)) {
            
            Map mapPollId = (Map)m_PollIdDupCheckKeyToMap.get(keyPollId);

     	    String keyDupCheckKey =  DupCheckKey;
			if (  keyDupCheckKey == null || keyDupCheckKey.isEmpty()) return new HashMap();
            
            if ( mapPollId.containsKey(keyDupCheckKey)){
                return (Map)mapPollId.get(keyDupCheckKey);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updatePollIdDupCheckKeyMap(Object obj, boolean del) {
        PollVote o = (PollVote)obj;

   	    String keyDupCheckKey =  o.getDupCheckKey();

		if (  keyDupCheckKey == null || keyDupCheckKey.isEmpty()) return;

        if (del) {
            // delete from PollIdDupCheckKeyToMap
            Map mapPollId  = (Map) m_PollIdDupCheckKeyToMap.get(new Long(o.getPollId()));
            if ( mapPollId != null ) {
                Map mapDupCheckKey = (Map) mapPollId.get(keyDupCheckKey);
                if (mapDupCheckKey != null){
                    mapDupCheckKey.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed PollVote from m_PollIdDupCheckKeyToMap" + o.getId() + " from " + o.getPollId() + " # " + o.getDupCheckKey());
        }
        else {
            
            // add to PollIdDupCheckKeyToMap
            Map mapPollId  = (Map) m_PollIdDupCheckKeyToMap.get(new Long(o.getPollId()));
            if ( mapPollId == null ) {
                mapPollId = new TreeMap();
                m_PollIdDupCheckKeyToMap.put(new Long(o.getPollId()), mapPollId);
                if (m_debug) m_logger.debug("created new   mapPollId for " + o.getPollId());
            }
            Map mapDupCheckKey = (Map) mapPollId.get(keyDupCheckKey);
            
            if ( mapDupCheckKey == null) {
                mapDupCheckKey = new TreeMap();
                mapPollId.put(keyDupCheckKey, mapDupCheckKey);
                if (m_debug) m_logger.debug("created new   mapPollId for " + o.getPollId());
            }
            mapDupCheckKey.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to PollIdDupCheckKeyToMap " + o.getId() + " to " + o.getPollId());
        }
        
    }    
        

        
    public  PollVote getByPollIdUserId(long PollId, long UserId) {

        Long keyPollId  = new Long(PollId);
        if (m_PollIdUserIdToOneMap.containsKey(keyPollId)) {
            
            Map mapPollId = (Map)m_PollIdUserIdToOneMap.get(keyPollId);

     	    Long keyUserId = new Long(UserId);
            
            if ( mapPollId.containsKey(keyUserId)){
                return ( PollVote)mapPollId.get(keyUserId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updatePollIdUserIdOneMap(Object obj, boolean del) {
        PollVote o = (PollVote)obj;

     	    Long keyUserId = new Long(o.getUserId());

        if (del) {
            // delete from PollIdUserIdToOneMap
            Map mapPollId  = (Map) m_PollIdUserIdToOneMap.get(new Long(o.getPollId()));
            if ( mapPollId != null ) {
                if (mapPollId.containsKey(keyUserId)){
                    mapPollId.remove(keyUserId);
                }
            }
            m_logger.debug("removed PollVote from m_PollIdUserIdToOneMap" + o.getId() + " from " + o.getPollId() + " # " + o.getUserId());
        }
        else {
            
            // add to PollIdUserIdToOneMap
            Map mapPollId  = (Map) m_PollIdUserIdToOneMap.get(new Long(o.getPollId()));
            if ( mapPollId == null ) {
                mapPollId = new TreeMap();
                m_PollIdUserIdToOneMap.put(new Long(o.getPollId()), mapPollId);
                if (m_debug) m_logger.debug("created new mapPollId for " + o.getPollId());
            }
            
            
			PollVote replaced = (PollVote) mapPollId.put(keyUserId,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: PollIdUserIdOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to PollIdUserIdToOneMap " + o.getId() + " to " + o.getPollId());
        }
        
    }    
        









    public static void main(String[] args) throws Exception {

        PollVoteDS ds = new PollVoteDS();
        PollVote obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PollVote createDefault(){

        PollVote ret = new PollVote();        
//      ret.setPollId("");           
//      ret.setUserId("");           
//      ret.setAnswer("");           
//      ret.setMultipleAnswer("");           
//      ret.setByGuest("");           
//      ret.setIpAddress("");           
//      ret.setPcid("");           
//      ret.setDupCheckKey("");           
//      ret.setNote("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static PollVote copy(PollVote org){

    	PollVote ret = new PollVote();

		ret.setPollId(org.getPollId());
		ret.setUserId(org.getUserId());
		ret.setAnswer(org.getAnswer());
		ret.setMultipleAnswer(org.getMultipleAnswer());
		ret.setByGuest(org.getByGuest());
		ret.setIpAddress(org.getIpAddress());
		ret.setPcid(org.getPcid());
		ret.setDupCheckKey(org.getDupCheckKey());
		ret.setNote(org.getNote());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(PollVote pollVote, Logger logger){
		logger.debug("PollVote [" + pollVote.getId() + "]" + objectToString(pollVote));		
    }

	public static String objectToString(PollVote pollVote){
		StringBuffer buf = new StringBuffer();
        buf.append("PollVote=");
		buf.append("Id=").append(pollVote.getId()).append(", ");
		buf.append("SiteId=").append(pollVote.getSiteId()).append(", ");
		buf.append("PollId=").append(pollVote.getPollId()).append(", ");
		buf.append("Answer=").append(pollVote.getAnswer()).append(", ");
		buf.append("MultipleAnswer=").append(pollVote.getMultipleAnswer()).append(", ");
		buf.append("ByGuest=").append(pollVote.getByGuest()).append(", ");
		buf.append("IpAddress=").append(pollVote.getIpAddress()).append(", ");
		buf.append("Pcid=").append(pollVote.getPcid()).append(", ");
		buf.append("TimeCreated=").append(pollVote.getTimeCreated()).append(", ");
		return buf.toString();    
    }
}
