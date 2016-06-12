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
import com.autosite.db.Poll;
import com.jtrend.service.DomainStore;

import com.autosite.db.PollDAO;

public class PollDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_SerialToOneMap;



    protected Map m_SiteIdToUserIdToMap;
    protected Map m_SiteIdToCategoryToMap;


    protected Map m_SiteIdToSerialToOneMap;


    private static Logger m_logger = Logger.getLogger(PollDS.class);
    private static PollDS m_PollDS = new PollDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PollDS getInstance() {
        return m_PollDS;
    }

    public static synchronized PollDS getInstance(long id) {
        PollDS ret = (PollDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PollDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PollDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PollDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("Poll loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PollDS() {
        m_dao = new PollDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SerialToOneMap = new ConcurrentHashMap();
        m_SiteIdToUserIdToMap = new ConcurrentHashMap();
        m_SiteIdToCategoryToMap = new ConcurrentHashMap();
        m_SiteIdToSerialToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PollDS(long id) {
        m_dao = new PollDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SerialToOneMap = new ConcurrentHashMap();
        m_SiteIdToUserIdToMap = new ConcurrentHashMap();
        m_SiteIdToCategoryToMap = new ConcurrentHashMap();
        m_SiteIdToSerialToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public Poll getById(Long id) {
        return (Poll) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        Poll o = (Poll)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("Poll removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("Poll added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSerialOneMap(obj, del);
        updateSiteIdToUserIdMap(obj, del);
        updateSiteIdToCategoryMap(obj, del);
        updateSiteIdToSerialOneMap(obj, del);
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
        Poll o = (Poll)obj;

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
            if (m_debug) m_logger.debug("Poll removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("Poll added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneStringKey - Serial

    public Poll getObjectBySerial(String keySerial) {
    	if (keySerial == null) return null; 
        return (Poll)m_SerialToOneMap.get(keySerial);
    }

    private void updateSerialOneMap(Object obj, boolean del) {
        Poll o = (Poll)obj;
        String _Serial =  o.getSerial();

		if (  _Serial == null || _Serial.isEmpty() ) return;

        if (del) {
            // delete from SerialToMap

            if (m_SerialToOneMap.containsKey(_Serial)){
                m_SerialToOneMap.remove(_Serial);
                 if (m_debug) m_logger.debug("Poll removed from SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } else {
                if (m_debug) m_logger.debug("Poll not removed from SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } 
        }
        else {
            
            if (m_SerialToOneMap.containsKey(_Serial)){
                if (m_debug) m_logger.debug("Poll repalced SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } else {
                if (m_debug) m_logger.debug("Poll added to SerialToMap" + o.getId() + " for [" + _Serial+ "]");
            } 
            m_SerialToOneMap.put(_Serial, o);
        }
    }






    public List getBySiteIdToUserIdList (long SiteId, long UserId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToUserIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToUserIdToMap.get(keySiteId);

     	    Long keyUserId = UserId;
            
            if ( mapSiteId.containsKey(keyUserId)){
                return new ArrayList( ((Map)mapSiteId.get(keyUserId)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdUserId(long SiteId, long UserId) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToUserIdToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToUserIdToMap.get(keySiteId);

     	    Long keyUserId = new Long(UserId);
            
            if ( mapSiteId.containsKey(keyUserId)){
                return (Map)mapSiteId.get(keyUserId);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToUserIdMap(Object obj, boolean del) {
        Poll o = (Poll)obj;

   	    Long keyUserId = new Long(o.getUserId());

        if (del) {
            // delete from SiteIdUserIdToMap
            Map mapSiteId  = (Map) m_SiteIdToUserIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapUserId = (Map) mapSiteId.get(keyUserId);
                if (mapUserId != null){
                    mapUserId.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed Poll from m_SiteIdToUserIdToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getUserId());
        }
        else {
            
            // add to SiteIdUserIdToMap
            Map mapSiteId  = (Map) m_SiteIdToUserIdToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToUserIdToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapUserId = (Map) mapSiteId.get(keyUserId);
            
            if ( mapUserId == null) {
                mapUserId = new TreeMap();
                mapSiteId.put(keyUserId, mapUserId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapUserId.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdUserIdToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    

    public List getBySiteIdCategoryList(long SiteId, String Category) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToCategoryToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToCategoryToMap.get(keySiteId);

     	    String keyCategory =  Category;
			if (  keyCategory == null || keyCategory.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyCategory)){
                return new ArrayList( ((Map)mapSiteId.get(keyCategory)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdCategoryList(long SiteId, String Category) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToCategoryToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToCategoryToMap.get(keySiteId);

     	    String keyCategory =  Category;
			if (  keyCategory == null || keyCategory.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyCategory)){
                return (Map)mapSiteId.get(keyCategory);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToCategoryMap(Object obj, boolean del) {
        Poll o = (Poll)obj;

   	    String keyCategory =  o.getCategory();
		if (  keyCategory == null || keyCategory.isEmpty()) return;

        if (del) {
            // delete from SiteIdCategoryToMap
            Map mapSiteId  = (Map) m_SiteIdToCategoryToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapCategory = (Map) mapSiteId.get(keyCategory);
                if (mapCategory != null){
                    mapCategory.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed Poll from m_SiteIdToCategoryToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getCategory());
        }
        else {
            
            // add to SiteIdCategoryToMap
            Map mapSiteId  = (Map) m_SiteIdToCategoryToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToCategoryToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapCategory = (Map) mapSiteId.get(keyCategory);
            
            if ( mapCategory == null) {
                mapCategory = new TreeMap();
                mapSiteId.put(keyCategory, mapCategory);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapCategory.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdCategoryToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    



	// listOneToOneStringToSiteIdKey - Serial

    public  Poll getBySiteIdToSerial(long siteId, String Serial) {

        Long keySiteIdTo  = new Long(siteId);
        if (m_SiteIdToSerialToOneMap.containsKey(keySiteIdTo)) {
            
            Map mapSiteIdTo = (Map)m_SiteIdToSerialToOneMap.get(keySiteIdTo);

     	    String keySerial = Serial;
            
            if ( keySerial!= null && mapSiteIdTo.containsKey(keySerial)){
                return ( Poll)mapSiteIdTo.get(keySerial);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    public List getByOrderSerial(){
        List ret = new ArrayList();
    
        List contentsBySerial = new ArrayList( m_SiteIdToSerialToOneMap.values());
        Map map  = new TreeMap();
        
        for (Iterator iterator = contentsBySerial.iterator(); iterator.hasNext();) {
            Poll obj = (Poll) iterator.next();
            if ( obj.getSerial() == null ) continue;
            map.put(obj.getSerial(), obj);
        }
        
        return new ArrayList(map.values());
    }

    private void updateSiteIdToSerialOneMap(Object obj, boolean del) {
        Poll o = (Poll)obj;

   	    String keySerial = o.getSerial();

		if ( keySerial == null || keySerial.isEmpty() ) {
			m_logger.warn("Passed key is null in updateSiteIdToSerialOneMap for the Poll object. ABORTED id = " + o.getId());
            return;
        }

        if (del) {
            // delete from PageIdSerialToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToSerialToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo != null ) {
                if (mapSiteIdTo.containsKey(keySerial)){
                    mapSiteIdTo.remove(keySerial);
                }
            }
            m_logger.debug("removed Poll from m_SiteIdToSerialToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getSerial());
        }
        else {
            
            // add to SiteIdToSerialToOneMap
            Map mapSiteIdTo  = (Map) m_SiteIdToSerialToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteIdTo == null ) {
                mapSiteIdTo = new TreeMap();
                m_SiteIdToSerialToOneMap.put(new Long(o.getSiteId()), mapSiteIdTo);
                if (m_debug) m_logger.debug("created new   mapSiteIdTo for " + o.getSiteId());
            }
            
			Poll replaced = (Poll) mapSiteIdTo.put(keySerial,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdToSerialOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Poll added to SiteIdToSerialToOneMap " + o.getId() + " to " + o.getSiteId());
        }
    }    




    public static void main(String[] args) throws Exception {

        PollDS ds = new PollDS();
        Poll obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static Poll createDefault(){

        Poll ret = new Poll();        
//      ret.setUserId("");           
//      ret.setSerial("");           
//      ret.setType("");           
//      ret.setCategory("");           
//      ret.setTitle("");           
//      ret.setDescription("");           
//      ret.setQuestion("");           
//      ret.setTags("");           
//      ret.setPublished("");           
//      ret.setHide("");           
//      ret.setDisable("");           
//      ret.setAllowMultiple("");           
//      ret.setRandomAnswer("");           
//      ret.setHideComments("");           
//      ret.setHideResults("");           
//      ret.setUseCookieForDup("");           
//      ret.setRepeatEveryDay("");           
//      ret.setMaxRepeatVote("");           
//      ret.setNumDaysOpen("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
//      ret.setTimeExpired("");           
        return ret;
    }

    public static Poll copy(Poll org){

    	Poll ret = new Poll();

		ret.setUserId(org.getUserId());
		ret.setSerial(org.getSerial());
		ret.setType(org.getType());
		ret.setCategory(org.getCategory());
		ret.setTitle(org.getTitle());
		ret.setDescription(org.getDescription());
		ret.setQuestion(org.getQuestion());
		ret.setTags(org.getTags());
		ret.setPublished(org.getPublished());
		ret.setHide(org.getHide());
		ret.setDisable(org.getDisable());
		ret.setAllowMultiple(org.getAllowMultiple());
		ret.setRandomAnswer(org.getRandomAnswer());
		ret.setHideComments(org.getHideComments());
		ret.setHideResults(org.getHideResults());
		ret.setUseCookieForDup(org.getUseCookieForDup());
		ret.setRepeatEveryDay(org.getRepeatEveryDay());
		ret.setMaxRepeatVote(org.getMaxRepeatVote());
		ret.setNumDaysOpen(org.getNumDaysOpen());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());
		ret.setTimeExpired(org.getTimeExpired());

        return ret;
    }

	public static void objectToLog(Poll poll, Logger logger){
		logger.debug("Poll [" + poll.getId() + "]" + objectToString(poll));		
    }

	public static String objectToString(Poll poll){
		StringBuffer buf = new StringBuffer();
        buf.append("Poll=");
		buf.append("Id=").append(poll.getId()).append(", ");
		buf.append("SiteId=").append(poll.getSiteId()).append(", ");
		buf.append("Category=").append(poll.getCategory()).append(", ");
		buf.append("Question=").append(poll.getQuestion()).append(", ");
		return buf.toString();    
    }
}
