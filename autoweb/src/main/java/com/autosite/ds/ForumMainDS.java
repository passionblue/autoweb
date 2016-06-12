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
import com.autosite.db.ForumMain;
import com.jtrend.service.DomainStore;

import com.autosite.db.ForumMainDAO;

public class ForumMainDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;



    private static Logger m_logger = Logger.getLogger(ForumMainDS.class);
    private static ForumMainDS m_ForumMainDS = new ForumMainDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static ForumMainDS getInstance() {
        return m_ForumMainDS;
    }

    public static synchronized ForumMainDS getInstance(long id) {
        ForumMainDS ret = (ForumMainDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ForumMainDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ForumMainDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ForumMainDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }
    }

    protected ForumMainDS() {
        m_dao = new ForumMainDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ForumMainDS(long id) {
        m_dao = new ForumMainDAO();
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

    public ForumMain getById(Long id) {
        return (ForumMain) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ForumMain o = (ForumMain)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("ForumMain removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("ForumMain added to DS " + o.getId());
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
        ForumMain o = (ForumMain)obj;

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
            if (m_debug) m_logger.debug("ForumMain removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("ForumMain added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    









    public static void main(String[] args) throws Exception {

        ForumMainDS ds = new ForumMainDS();
        ForumMain obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static ForumMain createDefault(){

        ForumMain ret = new ForumMain();        
//      ret.setTitle("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static ForumMain copy(ForumMain org){

    	ForumMain ret = new ForumMain();

		ret.setTitle(org.getTitle());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(ForumMain forumMain, Logger logger){
		logger.debug("ForumMain [" + forumMain.getId() + "]" + objectToString(forumMain));		
    }

	public static String objectToString(ForumMain forumMain){
		StringBuffer buf = new StringBuffer();
        buf.append("ForumMain=");
		buf.append("Id=").append(forumMain.getId()).append(", ");
		buf.append("SiteId=").append(forumMain.getSiteId()).append(", ");
		return buf.toString();    
    }
}
