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
import com.autosite.db.AutositeLoginExtent;
import com.jtrend.service.DomainStore;

import com.autosite.db.AutositeLoginExtentDAO;

public class AutositeLoginExtentDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToOneMap;






    private static Logger m_logger = Logger.getLogger(AutositeLoginExtentDS.class);
    private static AutositeLoginExtentDS m_AutositeLoginExtentDS = new AutositeLoginExtentDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static AutositeLoginExtentDS getInstance() {
        return m_AutositeLoginExtentDS;
    }

    public static synchronized AutositeLoginExtentDS getInstance(long id) {
        AutositeLoginExtentDS ret = (AutositeLoginExtentDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new AutositeLoginExtentDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_AutositeLoginExtentDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((AutositeLoginExtentDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("AutositeLoginExtent loaded from DB. num=" + m_idToMap.size());
        
    }

    protected AutositeLoginExtentDS() {
        m_dao = new AutositeLoginExtentDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected AutositeLoginExtentDS(long id) {
        m_dao = new AutositeLoginExtentDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public AutositeLoginExtent getById(Long id) {
        return (AutositeLoginExtent) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        AutositeLoginExtent o = (AutositeLoginExtent)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("AutositeLoginExtent removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("AutositeLoginExtent added to DS " + o.getId());
        }

        updateSiteIdOneMap(obj, del);
    }
	
    //    //    //


    

	// listOneToOneIdKey - SiteId

    public AutositeLoginExtent getObjectBySiteId(long keySiteId) {
        return (AutositeLoginExtent)m_SiteIdToOneMap.get(new Long(keySiteId));
    }

    private void updateSiteIdOneMap(Object obj, boolean del) {
        AutositeLoginExtent o = (AutositeLoginExtent)obj;
        Long _SiteId = new Long(o.getSiteId());

        if (del) {
            // delete from SiteIdToOneMap

            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                m_SiteIdToOneMap.remove(_SiteId);
                if (m_debug) m_logger.debug("AutositeLoginExtent removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeLoginExtent not removed from SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]. Does not exist");
            } 
        }
        else {
            if (m_SiteIdToOneMap.containsKey(_SiteId)){
                if (m_debug) m_logger.debug("AutositeLoginExtent repalced SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } else {
                if (m_debug) m_logger.debug("AutositeLoginExtent added to SiteIdToMap" + o.getId() + " for [" + _SiteId+ "]");
            } 
            m_SiteIdToOneMap.put(_SiteId, o);
        }
    }










    public static void main(String[] args) throws Exception {

        AutositeLoginExtentDS ds = new AutositeLoginExtentDS();
        AutositeLoginExtent obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static AutositeLoginExtent createDefault(){

        AutositeLoginExtent ret = new AutositeLoginExtent();        
        return ret;
    }

    public static AutositeLoginExtent copy(AutositeLoginExtent org){

    	AutositeLoginExtent ret = new AutositeLoginExtent();


        return ret;
    }

	public static void objectToLog(AutositeLoginExtent autositeLoginExtent, Logger logger){
		logger.debug("AutositeLoginExtent [" + autositeLoginExtent.getId() + "]" + objectToString(autositeLoginExtent));		
    }

	public static String objectToString(AutositeLoginExtent autositeLoginExtent){
		StringBuffer buf = new StringBuffer();
        buf.append("AutositeLoginExtent=");
		buf.append("Id=").append(autositeLoginExtent.getId()).append(", ");
		buf.append("SiteId=").append(autositeLoginExtent.getSiteId()).append(", ");
		buf.append("ClassName=").append(autositeLoginExtent.getClassName()).append(", ");
		return buf.toString();    
    }
}
