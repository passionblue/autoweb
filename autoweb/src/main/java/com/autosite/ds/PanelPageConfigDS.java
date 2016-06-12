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
import com.autosite.db.PanelPageConfig;
import com.autosite.db.PanelPageConfigDAO;
import com.jtrend.service.DomainStore;

public class PanelPageConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_PanelIdToMap;


    private static Logger m_logger = Logger.getLogger(PanelPageConfigDS.class);
    private static PanelPageConfigDS m_PanelPageConfigDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized PanelPageConfigDS getInstance() {
        if (m_PanelPageConfigDS == null) {
            m_PanelPageConfigDS = new PanelPageConfigDS();
        }
        return m_PanelPageConfigDS;
    }

    public static synchronized PanelPageConfigDS getInstance(long id) {
        PanelPageConfigDS ret = (PanelPageConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PanelPageConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PanelPageConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PanelPageConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected PanelPageConfigDS() {
        m_dao = new PanelPageConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PanelPageConfigDS(long id) {
        m_loadById = id;
        m_dao = new PanelPageConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_PanelIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PanelPageConfig getById(Long id) {
        return (PanelPageConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        PanelPageConfig o = (PanelPageConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PanelPageConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PanelPageConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updatePanelIdMap(obj, del);


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
        PanelPageConfig o = (PanelPageConfig)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("PanelPageConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PanelPageConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public PanelPageConfig getByPanelId(long keyPanelId) {
        return (PanelPageConfig)m_PanelIdToMap.get(new Long(keyPanelId));
    }

    private void updatePanelIdMap(Object obj, boolean del) {
        PanelPageConfig o = (PanelPageConfig)obj;
        Long _PanelId = new Long(o.getPanelId());

        if (del) {
            // delete from PanelIdToMap

            if (m_PanelIdToMap.containsKey(_PanelId)){
                m_PanelIdToMap.remove(_PanelId);
                if (m_debug) m_logger.debug("PanelPageConfig removed from PanelIdToMap" + o.getId() + " for [" + _PanelId+ "]");
            } else {
                if (m_debug) m_logger.debug("PanelPageConfig not removed from PanelIdToMap" + o.getId() + " for [" + _PanelId+ "]. Does not exist");
            } 
        }
        else {
            if (m_PanelIdToMap.containsKey(_PanelId)){
                if (m_debug) m_logger.debug("PanelPageConfig repalced PanelIdToMap" + o.getId() + " for [" + _PanelId+ "]");
            } else {
                if (m_debug) m_logger.debug("PanelPageConfig added to PanelIdToMap" + o.getId() + " for [" + _PanelId+ "]");
            } 
            m_PanelIdToMap.put(_PanelId, o);
        }
    }




    public static void main(String[] args) throws Exception {

        PanelPageConfigDS ds = new PanelPageConfigDS();
        PanelPageConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static PanelPageConfig createDefault(){

        PanelPageConfig ret = new PanelPageConfig();        
//      ret.setPanelId("");           
//      ret.setPageDisplaySummary("");           
//      ret.setTimeCreated("");           
//      ret.setTimeUpdated("");           
        return ret;
    }

    public static PanelPageConfig copy(PanelPageConfig org){

    	PanelPageConfig ret = new PanelPageConfig();

		ret.setPanelId(org.getPanelId());
		ret.setPageDisplaySummary(org.getPageDisplaySummary());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(PanelPageConfig panelPageConfig, Logger logger){
		logger.debug("PanelPageConfig [" + panelPageConfig.getId() + "]" + objectToString(panelPageConfig));		
    }

	public static String objectToString(PanelPageConfig panelPageConfig){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(panelPageConfig.getId()).append(", ");
		buf.append("PanelId=").append(panelPageConfig.getPanelId()).append(", ");
		buf.append("PageDisplaySummary=").append(panelPageConfig.getPageDisplaySummary()).append(", ");
		return buf.toString();    
    }
}
