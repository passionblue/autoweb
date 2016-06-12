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
import com.jtrend.util.AggregatedIdMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.AutositeDataObject;
import com.jtrend.service.DomainStore;
import com.autosite.holder.DataHolderObject;


import com.autosite.db.ThemeStylesDAO;
import com.autosite.db.ThemeStyles;
import com.surveygen.db.BaseMemoryDAO;

public class ThemeStylesDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = true; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;






    private static Logger m_logger = Logger.getLogger(ThemeStylesDS.class);
    public static boolean m_debug = AutositeGlobals.m_debug;

    private static ThemeStylesDS m_ThemeStylesDS = new ThemeStylesDSExtent();



    public static ThemeStylesDS getInstance() {
        return m_ThemeStylesDS;
    }

    public static synchronized ThemeStylesDS getInstance(long id) {
        ThemeStylesDS ret = (ThemeStylesDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ThemeStylesDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ThemeStylesDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ThemeStylesDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("ThemeStyles loaded from DB. num=" + m_idToMap.size());
        
    }


    protected ThemeStylesDS() {
        m_dao = new ThemeStylesDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
 
        if ( m_aggregateAllSites )
            m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ThemeStylesDS(long id) {
        m_dao = new ThemeStylesDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        if ( m_aggregateAllSites )
            m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public ThemeStyles getById(Long id) {
        return (ThemeStyles) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        ThemeStyles o = (ThemeStyles)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ThemeStyles removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ThemeStyles added to DS " + o.getId());
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
            if ( m_aggregateAllSites && m_SiteIdToMap.containsKey(new Long(0)) ) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        ThemeStyles o = (ThemeStyles)obj;

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
            if (m_debug) m_logger.debug("ThemeStyles removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ThemeStyles added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    











    //

    public static void main(String[] args) throws Exception {

        ThemeStylesDS ds = new ThemeStylesDS();
        ThemeStyles obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static ThemeStyles createDefault(){

        ThemeStyles _ThemeStyles = new ThemeStyles();        
        _ThemeStyles = new ThemeStyles();// ThemeStylesDS.getInstance().getDeafult();
        return _ThemeStyles;
    }

    public static ThemeStyles copy(ThemeStyles org){

        ThemeStyles ret = new ThemeStyles();

        ret.setBodyWidth(org.getBodyWidth());
        ret.setBodyAlign(org.getBodyAlign());
        ret.setBodyBackground(org.getBodyBackground());
        ret.setTimeCreated(org.getTimeCreated());
        ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

    public static void objectToLog(ThemeStyles themeStyles, Logger logger){
        logger.debug("ThemeStyles [" + themeStyles.getId() + "]" + objectToString(themeStyles));        
    }

    public static String objectToString(ThemeStyles themeStyles){
        StringBuilder buf = new StringBuilder();
        buf.append("ThemeStyles=");
        buf.append("Id=").append(themeStyles.getId()).append(", ");
        return buf.toString();    
    }





    public String objectToString2(AutositeDataObject object){
        ThemeStyles themeStyles = (ThemeStyles)object;
        StringBuilder buf = new StringBuilder();
        buf.append("ThemeStyles=");
        buf.append("Id=").append(themeStyles.getId()).append(", ");
        return buf.toString();    
    }


    // Empty methods for 

}
