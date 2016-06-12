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
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.PollConfigDAO;
import com.autosite.db.PollConfig;
import com.surveygen.db.BaseMemoryDAO;

public class PollConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PollIdToOneMap;






    private static Logger m_logger = Logger.getLogger(PollConfigDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static PollConfigDS m_PollConfigDS = new PollConfigDS();



    public static PollConfigDS getInstance() {
        return m_PollConfigDS;
    }

    public static synchronized PollConfigDS getInstance(long id) {
        PollConfigDS ret = (PollConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PollConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PollConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PollConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PollConfig loaded from DB. num=" + m_idToMap.size());
        
    }


    protected PollConfigDS() {
        m_dao = new PollConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PollIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PollConfigDS(long id) {
        m_dao = new PollConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PollIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PollConfig getById(Long id) {
        return (PollConfig) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        PollConfig o = (PollConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PollConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PollConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePollIdOneMap(obj, del);
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
        PollConfig o = (PollConfig)obj;

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
            if (m_debug) m_logger.debug("PollConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PollConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneIdKey - PollId

    public PollConfig getObjectByPollId(long keyPollId) {
        return (PollConfig)m_PollIdToOneMap.get(new Long(keyPollId));
    }

    private void updatePollIdOneMap(Object obj, boolean del) {
        PollConfig o = (PollConfig)obj;
        Long _PollId = new Long(o.getPollId());

        if (del) {
            // delete from PollIdToOneMap

            if (m_PollIdToOneMap.containsKey(_PollId)){
                m_PollIdToOneMap.remove(_PollId);
                if (m_debug) m_logger.debug("PollConfig removed from PollIdToMap" + o.getId() + " for [" + _PollId+ "]");
            } else {
                if (m_debug) m_logger.debug("PollConfig not removed from PollIdToMap" + o.getId() + " for [" + _PollId+ "]. Does not exist");
            } 
        }
        else {
            if (m_PollIdToOneMap.containsKey(_PollId)){
                if (m_debug) m_logger.debug("PollConfig repalced PollIdToMap" + o.getId() + " for [" + _PollId+ "]");
            } else {
                if (m_debug) m_logger.debug("PollConfig added to PollIdToMap" + o.getId() + " for [" + _PollId+ "]");
            } 
            m_PollIdToOneMap.put(_PollId, o);
        }
    }











    public static void main(String[] args) throws Exception {

        PollConfigDS ds = new PollConfigDS();
        PollConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static PollConfig createDefault(){

        PollConfig _PollConfig = new PollConfig();        
	    _PollConfig = new PollConfig();// PollConfigDS.getInstance().getDeafult();
        return _PollConfig;
    }

    public static PollConfig copy(PollConfig org){

    	PollConfig ret = new PollConfig();

		ret.setPollId(org.getPollId());
		ret.setImageThumbHeight(org.getImageThumbHeight());
		ret.setImageThumbWidth(org.getImageThumbWidth());
		ret.setImageAlignVertical(org.getImageAlignVertical());
		ret.setBackground(org.getBackground());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(PollConfig pollConfig, Logger logger){
		logger.debug("PollConfig [" + pollConfig.getId() + "]" + objectToString(pollConfig));		
    }

	public static String objectToString(PollConfig pollConfig){
		StringBuilder buf = new StringBuilder();
        buf.append("PollConfig=");
		buf.append("Id=").append(pollConfig.getId()).append(", ");
		buf.append("SiteId=").append(pollConfig.getSiteId()).append(", ");
		buf.append("PollId=").append(pollConfig.getPollId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		PollConfig pollConfig = (PollConfig)object;
		StringBuilder buf = new StringBuilder();
        buf.append("PollConfig=");
		buf.append("Id=").append(pollConfig.getId()).append(", ");
		buf.append("SiteId=").append(pollConfig.getSiteId()).append(", ");
		buf.append("PollId=").append(pollConfig.getPollId()).append(", ");
		return buf.toString();    
    }



}
