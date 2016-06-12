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


import com.autosite.db.DevNoteDAO;
import com.autosite.db.DevNote;
import com.surveygen.db.BaseMemoryDAO;

public class DevNoteDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(DevNoteDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static DevNoteDS m_DevNoteDS = new DevNoteDSExtent();



    public static DevNoteDS getInstance() {
        return m_DevNoteDS;
    }

    public static synchronized DevNoteDS getInstance(long id) {
        DevNoteDS ret = (DevNoteDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new DevNoteDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_DevNoteDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((DevNoteDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("DevNote loaded from DB. num=" + m_idToMap.size());
        
    }


    protected DevNoteDS() {
        m_dao = new DevNoteDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected DevNoteDS(long id) {
        m_dao = new DevNoteDAO();
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

    public DevNote getById(Long id) {
        return (DevNote) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        DevNote o = (DevNote)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("DevNote removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("DevNote added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
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
        DevNote o = (DevNote)obj;

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
            if (m_debug) m_logger.debug("DevNote removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("DevNote added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    












    public static void main(String[] args) throws Exception {

        DevNoteDS ds = new DevNoteDS();
        DevNote obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static DevNote createDefault(){

        DevNote _DevNote = new DevNote();        
	    _DevNote = new DevNote();// DevNoteDS.getInstance().getDeafult();
        return _DevNote;
    }

    public static DevNote copy(DevNote org){

    	DevNote ret = new DevNote();

		ret.setNoteType(org.getNoteType());
		ret.setCompleted(org.getCompleted());
		ret.setCategory(org.getCategory());
		ret.setSubject(org.getSubject());
		ret.setNote(org.getNote());
		ret.setTags(org.getTags());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(DevNote devNote, Logger logger){
		logger.debug("DevNote [" + devNote.getId() + "]" + objectToString(devNote));		
    }

	public static String objectToString(DevNote devNote){
		StringBuilder buf = new StringBuilder();
        buf.append("DevNote=");
		buf.append("Id=").append(devNote.getId()).append(", ");
		buf.append("SiteId=").append(devNote.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		DevNote devNote = (DevNote)object;
		StringBuilder buf = new StringBuilder();
        buf.append("DevNote=");
		buf.append("Id=").append(devNote.getId()).append(", ");
		buf.append("SiteId=").append(devNote.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
