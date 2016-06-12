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
import com.autosite.db.PollAnswer;
import com.jtrend.service.DomainStore;

import com.autosite.db.PollAnswerDAO;

public class PollAnswerDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PollIdToMap;






    private static Logger m_logger = Logger.getLogger(PollAnswerDS.class);
    private static PollAnswerDS m_PollAnswerDS = new PollAnswerDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PollAnswerDS getInstance() {
        return m_PollAnswerDS;
    }

    public static synchronized PollAnswerDS getInstance(long id) {
        PollAnswerDS ret = (PollAnswerDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PollAnswerDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PollAnswerDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PollAnswerDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PollAnswer loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PollAnswerDS() {
        m_dao = new PollAnswerDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PollIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PollAnswerDS(long id) {
        m_dao = new PollAnswerDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PollIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PollAnswer getById(Long id) {
        return (PollAnswer) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PollAnswer o = (PollAnswer)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PollAnswer removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PollAnswer added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
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
        PollAnswer o = (PollAnswer)obj;

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
            if (m_debug) m_logger.debug("PollAnswer removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PollAnswer added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        PollAnswer o = (PollAnswer)obj;

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
            if (m_debug) m_logger.debug("PollAnswer removed from PollIdToMap" + o.getId() + " from " + o.getPollId());
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
            if (m_debug) m_logger.debug("PollAnswer added to PollIdToMap " + o.getId() + " to " + o.getPollId());
        }
    }


    











    public static void main(String[] args) throws Exception {

        PollAnswerDS ds = new PollAnswerDS();
        PollAnswer obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PollAnswer createDefault(){

        PollAnswer ret = new PollAnswer();        
//      ret.setPollId("");           
//      ret.setAnswerNum("");           
//      ret.setText("");           
//      ret.setImageUrl("");           
//      ret.setImageOnly("");           
        return ret;
    }

    public static PollAnswer copy(PollAnswer org){

    	PollAnswer ret = new PollAnswer();

		ret.setPollId(org.getPollId());
		ret.setAnswerNum(org.getAnswerNum());
		ret.setText(org.getText());
		ret.setImageUrl(org.getImageUrl());
		ret.setImageOnly(org.getImageOnly());

        return ret;
    }

	public static void objectToLog(PollAnswer pollAnswer, Logger logger){
		logger.debug("PollAnswer [" + pollAnswer.getId() + "]" + objectToString(pollAnswer));		
    }

	public static String objectToString(PollAnswer pollAnswer){
		StringBuffer buf = new StringBuffer();
        buf.append("PollAnswer=");
		buf.append("Id=").append(pollAnswer.getId()).append(", ");
		buf.append("SiteId=").append(pollAnswer.getSiteId()).append(", ");
		buf.append("PollId=").append(pollAnswer.getPollId()).append(", ");
		buf.append("AnswerNum=").append(pollAnswer.getAnswerNum()).append(", ");
		buf.append("Text=").append(pollAnswer.getText()).append(", ");
		return buf.toString();    
    }
}
