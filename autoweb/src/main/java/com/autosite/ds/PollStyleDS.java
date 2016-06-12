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
import com.autosite.db.PollStyle;
import com.jtrend.service.DomainStore;

import com.autosite.db.PollStyleDAO;

public class PollStyleDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;


	protected Map m_SiteIdPollIdToOneMap;





    private static Logger m_logger = Logger.getLogger(PollStyleDS.class);
    private static PollStyleDS m_PollStyleDS = new PollStyleDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static PollStyleDS getInstance() {
        return m_PollStyleDS;
    }

    public static synchronized PollStyleDS getInstance(long id) {
        PollStyleDS ret = (PollStyleDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PollStyleDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PollStyleDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PollStyleDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PollStyle loaded from DB. num=" + m_idToMap.size());
        
    }

    protected PollStyleDS() {
        m_dao = new PollStyleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_SiteIdPollIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PollStyleDS(long id) {
        m_dao = new PollStyleDAO();
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

    public PollStyle getById(Long id) {
        return (PollStyle) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PollStyle o = (PollStyle)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PollStyle removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PollStyle added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateSiteIdPollIdOneMap(obj, del);
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
        PollStyle o = (PollStyle)obj;

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
            if (m_debug) m_logger.debug("PollStyle removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PollStyle added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    





        
    public  PollStyle getBySiteIdPollId(long SiteId, long PollId) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdPollIdToOneMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdPollIdToOneMap.get(keySiteId);

     	    Long keyPollId = new Long(PollId);
            
            if ( mapSiteId.containsKey(keyPollId)){
                return ( PollStyle)mapSiteId.get(keyPollId);
            } else {
                return null;
            }
                
        } else {
            return null;
        }
    }	

    private void updateSiteIdPollIdOneMap(Object obj, boolean del) {
        PollStyle o = (PollStyle)obj;

     	    Long keyPollId = new Long(o.getPollId());

        if (del) {
            // delete from SiteIdPollIdToOneMap
            Map mapSiteId  = (Map) m_SiteIdPollIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                if (mapSiteId.containsKey(keyPollId)){
                    mapSiteId.remove(keyPollId);
                }
            }
            m_logger.debug("removed PollStyle from m_SiteIdPollIdToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getPollId());
        }
        else {
            
            // add to SiteIdPollIdToOneMap
            Map mapSiteId  = (Map) m_SiteIdPollIdToOneMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdPollIdToOneMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new mapSiteId for " + o.getSiteId());
            }
            
            
			PollStyle replaced = (PollStyle) mapSiteId.put(keyPollId,o);			
            if ( replaced != null){
                if (m_debug) m_logger.debug("**WARNING**: SiteIdPollIdOneMap existing object was replaced in one-one map. id=" + replaced.getId());
            }
            
            if (m_debug) m_logger.debug("Panel added to SiteIdPollIdToOneMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    
        









    public static void main(String[] args) throws Exception {

        PollStyleDS ds = new PollStyleDS();
        PollStyle obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PollStyle createDefault(){

        PollStyle ret = new PollStyle();        
//      ret.setUserId("");           
//      ret.setPollId("");           
//      ret.setColor("");           
//      ret.setBackground("");           
//      ret.setBorder("");           
//      ret.setFont("");           
//      ret.setMargin("");           
//      ret.setPadding("");           
//      ret.setFloating("");           
//      ret.setTextAlign("");           
//      ret.setTextIndent("");           
//      ret.setHeight("");           
//      ret.setWidth("");           
//      ret.setExtra("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static PollStyle copy(PollStyle org){

    	PollStyle ret = new PollStyle();

		ret.setUserId(org.getUserId());
		ret.setPollId(org.getPollId());
		ret.setColor(org.getColor());
		ret.setBackground(org.getBackground());
		ret.setBorder(org.getBorder());
		ret.setFont(org.getFont());
		ret.setMargin(org.getMargin());
		ret.setPadding(org.getPadding());
		ret.setFloating(org.getFloating());
		ret.setTextAlign(org.getTextAlign());
		ret.setTextIndent(org.getTextIndent());
		ret.setHeight(org.getHeight());
		ret.setWidth(org.getWidth());
		ret.setExtra(org.getExtra());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(PollStyle pollStyle, Logger logger){
		logger.debug("PollStyle [" + pollStyle.getId() + "]" + objectToString(pollStyle));		
    }

	public static String objectToString(PollStyle pollStyle){
		StringBuffer buf = new StringBuffer();
        buf.append("PollStyle=");
		buf.append("Id=").append(pollStyle.getId()).append(", ");
		buf.append("SiteId=").append(pollStyle.getSiteId()).append(", ");
		buf.append("UserId=").append(pollStyle.getUserId()).append(", ");
		return buf.toString();    
    }
}
