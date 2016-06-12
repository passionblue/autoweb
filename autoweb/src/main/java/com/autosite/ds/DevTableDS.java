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
import com.autosite.db.DevTable;
import com.jtrend.service.DomainStore;

import com.autosite.db.DevTableDAO;

public class DevTableDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;







    private static Logger m_logger = Logger.getLogger(DevTableDS.class);
    private static DevTableDS m_DevTableDS = new DevTableDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static DevTableDS getInstance() {
        return m_DevTableDS;
    }

    public static synchronized DevTableDS getInstance(long id) {
        DevTableDS ret = (DevTableDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new DevTableDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_DevTableDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((DevTableDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("DevTable loaded from DB. num=" + m_idToMap.size());
        
    }

    protected DevTableDS() {
        m_dao = new DevTableDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected DevTableDS(long id) {
        m_dao = new DevTableDAO();
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

    public DevTable getById(Long id) {
        return (DevTable) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        DevTable o = (DevTable)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("DevTable removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("DevTable added to DS " + o.getId());
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
        DevTable o = (DevTable)obj;

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
            if (m_debug) m_logger.debug("DevTable removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("DevTable added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    














    public static void main(String[] args) throws Exception {

        DevTableDS ds = new DevTableDS();
        DevTable obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static DevTable createDefault(){

        DevTable _DevTable = new DevTable();        
	    _DevTable = new DevTable();// DevTableDS.getInstance().getDeafult();
        return _DevTable;
    }

    public static DevTable copy(DevTable org){

    	DevTable ret = new DevTable();

		ret.setDevNoteId(org.getDevNoteId());
		ret.setTitle(org.getTitle());
		ret.setSubject(org.getSubject());
		ret.setData(org.getData());
		ret.setType(org.getType());
		ret.setDisable(org.getDisable());
		ret.setRadioValue(org.getRadioValue());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(DevTable devTable, Logger logger){
		logger.debug("DevTable [" + devTable.getId() + "]" + objectToString(devTable));		
    }

	public static String objectToString(DevTable devTable){
		StringBuffer buf = new StringBuffer();
        buf.append("DevTable=");
		buf.append("Id=").append(devTable.getId()).append(", ");
		buf.append("SiteId=").append(devTable.getSiteId()).append(", ");
		return buf.toString();    
    }
}
