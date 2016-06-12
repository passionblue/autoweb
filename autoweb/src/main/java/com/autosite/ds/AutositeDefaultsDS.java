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
import com.autosite.db.AutositeDefaults;
import com.autosite.db.AutositeDefaultsDAO;
import com.jtrend.service.DomainStore;

public class AutositeDefaultsDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;


    private static Logger m_logger = Logger.getLogger(AutositeDefaultsDS.class);
    private static AutositeDefaultsDS m_AutositeDefaultsDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized AutositeDefaultsDS getInstance() {
        if (m_AutositeDefaultsDS == null) {
            m_AutositeDefaultsDS = new AutositeDefaultsDS();
        }
        return m_AutositeDefaultsDS;
    }

    public static synchronized AutositeDefaultsDS getInstance(long id) {
        AutositeDefaultsDS ret = (AutositeDefaultsDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new AutositeDefaultsDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_AutositeDefaultsDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((AutositeDefaultsDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected AutositeDefaultsDS() {
        m_dao = new AutositeDefaultsDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected AutositeDefaultsDS(long id) {
        m_loadById = id;
        m_dao = new AutositeDefaultsDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public AutositeDefaults getById(Long id) {
        return (AutositeDefaults) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        AutositeDefaults o = (AutositeDefaults)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("AutositeDefaults removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("AutositeDefaults added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);


    }


    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        AutositeDefaults o = (AutositeDefaults)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("AutositeDefaults removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("AutositeDefaults added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    





    public static void main(String[] args) throws Exception {

        AutositeDefaultsDS ds = new AutositeDefaultsDS();
        AutositeDefaults obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static AutositeDefaults createDefault(){

        AutositeDefaults ret = new AutositeDefaults();        
//      ret.setStyleId("");           
//      ret.setLinkStyleId("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static AutositeDefaults copy(AutositeDefaults org){

    	AutositeDefaults ret = new AutositeDefaults();

		ret.setStyleId(org.getStyleId());
		ret.setLinkStyleId(org.getLinkStyleId());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(AutositeDefaults autositeDefaults, Logger logger){
		logger.debug("AutositeDefaults [" + autositeDefaults.getId() + "]" + objectToString(autositeDefaults));		
    }

	public static String objectToString(AutositeDefaults autositeDefaults){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(autositeDefaults.getId()).append(", ");
		buf.append("SiteId=").append(autositeDefaults.getSiteId()).append(", ");
		buf.append("TimeCreated=").append(autositeDefaults.getTimeCreated()).append(", ");
		return buf.toString();    
    }
}
