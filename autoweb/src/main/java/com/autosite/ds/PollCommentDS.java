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
import com.autosite.db.PollComment;
import com.jtrend.service.DomainStore;

import com.autosite.db.PollCommentDAO;

public class PollCommentDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;
    protected Map m_PollIdToMap;







    private static Logger m_logger = Logger.getLogger(PollCommentDS.class);
    private static PollCommentDS m_PollCommentDS = new PollCommentDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PollCommentDS getInstance() {
        return m_PollCommentDS;
    }

    public static synchronized PollCommentDS getInstance(long id) {
        PollCommentDS ret = (PollCommentDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PollCommentDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PollCommentDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PollCommentDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PollComment loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PollCommentDS() {
        m_dao = new PollCommentDAO();
        m_idToMap = new ConcurrentHashMap();

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

    protected PollCommentDS(long id) {
        m_dao = new PollCommentDAO();
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

    public PollComment getById(Long id) {
        return (PollComment) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PollComment o = (PollComment)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PollComment removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PollComment added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);
        updatePollIdMap(obj, del);
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
        PollComment o = (PollComment)obj;

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
            if (m_debug) m_logger.debug("PollComment removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PollComment added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        PollComment o = (PollComment)obj;

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
            if (m_debug) m_logger.debug("PollComment removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("PollComment added to UserIdToMap " + o.getId() + " to " + o.getUserId());
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
        PollComment o = (PollComment)obj;

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
            if (m_debug) m_logger.debug("PollComment removed from PollIdToMap" + o.getId() + " from " + o.getPollId());
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
            if (m_debug) m_logger.debug("PollComment added to PollIdToMap " + o.getId() + " to " + o.getPollId());
        }
    }


    














    public static void main(String[] args) throws Exception {

        PollCommentDS ds = new PollCommentDS();
        PollComment obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PollComment createDefault(){

        PollComment ret = new PollComment();        
//      ret.setPollId("");           
//      ret.setUserId("");           
//      ret.setComment("");           
//      ret.setHide("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static PollComment copy(PollComment org){

    	PollComment ret = new PollComment();

		ret.setPollId(org.getPollId());
		ret.setUserId(org.getUserId());
		ret.setComment(org.getComment());
		ret.setHide(org.getHide());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(PollComment pollComment, Logger logger){
		logger.debug("PollComment [" + pollComment.getId() + "]" + objectToString(pollComment));		
    }

	public static String objectToString(PollComment pollComment){
		StringBuffer buf = new StringBuffer();
        buf.append("PollComment=");
		buf.append("Id=").append(pollComment.getId()).append(", ");
		buf.append("SiteId=").append(pollComment.getSiteId()).append(", ");
		buf.append("UserId=").append(pollComment.getUserId()).append(", ");
		buf.append("PollId=").append(pollComment.getPollId()).append(", ");
		buf.append("Comment=").append(pollComment.getComment()).append(", ");
		return buf.toString();    
    }
}
