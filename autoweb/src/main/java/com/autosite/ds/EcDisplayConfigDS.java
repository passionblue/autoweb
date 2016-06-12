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
import com.autosite.db.EcDisplayConfig;
import com.autosite.db.EcDisplayConfigDAO;
import com.jtrend.service.DomainStore;

public class EcDisplayConfigDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_SiteIdToOneMap;


    private static Logger m_logger = Logger.getLogger(EcDisplayConfigDS.class);
    private static EcDisplayConfigDS m_EcDisplayConfigDS = null;
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static synchronized EcDisplayConfigDS getInstance() {
        if (m_EcDisplayConfigDS == null) {
            m_EcDisplayConfigDS = new EcDisplayConfigDS();
        }
        return m_EcDisplayConfigDS;
    }

    public static synchronized EcDisplayConfigDS getInstance(long id) {
        EcDisplayConfigDS ret = (EcDisplayConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new EcDisplayConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_EcDisplayConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((EcDisplayConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected EcDisplayConfigDS() {
        m_dao = new EcDisplayConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected EcDisplayConfigDS(long id) {
        m_loadById = id;
        m_dao = new EcDisplayConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_SiteIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public EcDisplayConfig getById(Long id) {
        return (EcDisplayConfig) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        EcDisplayConfig o = (EcDisplayConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("EcDisplayConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("EcDisplayConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateSiteIdOneMap(obj, del);


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
        EcDisplayConfig o = (EcDisplayConfig)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("EcDisplayConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("EcDisplayConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    


    public EcDisplayConfig getObjectBySiteId(long keySiteId) {
        return (EcDisplayConfig)m_SiteIdToOneMap.get(new Long(keySiteId));
    }

    private void updateSiteIdOneMap(Object obj, boolean del) {
        EcDisplayConfig o = (EcDisplayConfig)obj;
        Long _SiteId = new Long(o.getSiteId());

        if (del) {
            // delete from SiteIdToOneMap

            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                m_SiteIdToOneMap.remove(_SiteId);
                if (m_debug) m_logger.debug("EcDisplayConfig removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcDisplayConfig not removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]. Does not exist");
            } 
        }
        else {
            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                if (m_debug) m_logger.debug("EcDisplayConfig repalced SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("EcDisplayConfig added to SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } 
            m_SiteIdToOneMap.put(_SiteId, o);
        }
    }




    public static void main(String[] args) throws Exception {

        EcDisplayConfigDS ds = new EcDisplayConfigDS();
        EcDisplayConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static EcDisplayConfig createDefault(){

        EcDisplayConfig ret = new EcDisplayConfig();        
//      ret.setColumnCount("");           
        return ret;
    }

    public static EcDisplayConfig copy(EcDisplayConfig org){

    	EcDisplayConfig ret = new EcDisplayConfig();

		ret.setColumnCount(org.getColumnCount());

        return ret;
    }

	public static void objectToLog(EcDisplayConfig ecDisplayConfig, Logger logger){
		logger.debug("EcDisplayConfig [" + ecDisplayConfig.getId() + "]" + objectToString(ecDisplayConfig));		
    }

	public static String objectToString(EcDisplayConfig ecDisplayConfig){
		StringBuffer buf = new StringBuffer();

		buf.append("Id=").append(ecDisplayConfig.getId()).append(", ");
		buf.append("SiteId=").append(ecDisplayConfig.getSiteId()).append(", ");
		return buf.toString();    
    }
}
