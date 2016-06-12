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
import com.autosite.db.FormMain;
import com.jtrend.service.DomainStore;

import com.autosite.db.FormMainDAO;

public class FormMainDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;







    private static Logger m_logger = Logger.getLogger(FormMainDS.class);
    private static FormMainDS m_FormMainDS = new FormMainDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static FormMainDS getInstance() {
        return m_FormMainDS;
    }

    public static synchronized FormMainDS getInstance(long id) {
        FormMainDS ret = (FormMainDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new FormMainDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_FormMainDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((FormMainDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("FormMain loaded from DB. num=" + m_idToMap.size());
        
    }

    protected FormMainDS() {
        m_dao = new FormMainDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected FormMainDS(long id) {
        m_dao = new FormMainDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_UserIdToMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public FormMain getById(Long id) {
        return (FormMain) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        FormMain o = (FormMain)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("FormMain removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("FormMain added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateUserIdMap(obj, del);
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
        FormMain o = (FormMain)obj;

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
            if (m_debug) m_logger.debug("FormMain removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("FormMain added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }
	// ListKeys - UserId
    public List getByUserId(long UserId) {
        
        Long _UserId  = new Long(UserId);
        if (m_UserIdToMap.containsKey(_UserId)) {
            return new ArrayList( ((Map)m_UserIdToMap.get(_UserId)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateUserIdMap(Object obj, boolean del) {
        FormMain o = (FormMain)obj;

		if ( o.getUserId() == 0 ){
            if (m_debug) m_logger.debug("key id is 0. not putting in the map"); 
        	return;
        }

        if (del) {

            // delete from UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            if (m_debug) m_logger.debug("FormMain removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
        }
        else {
            
            // add to UserIdToMap
            Map map  = (Map) m_UserIdToMap.get(new Long(o.getUserId()));
            if ( map == null ) {
                map = new TreeMap();
                m_UserIdToMap.put(new Long(o.getUserId()), map);
                if (m_debug) m_logger.debug("created new   UserIdToMap for " + o.getUserId());

            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("FormMain added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    













    public static void main(String[] args) throws Exception {

        FormMainDS ds = new FormMainDS();
        FormMain obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static FormMain createDefault(){

        FormMain _FormMain = new FormMain();        
	    _FormMain = new FormMain();// FormMainDS.getInstance().getDeafult();
        return _FormMain;
    }

    public static FormMain copy(FormMain org){

    	FormMain ret = new FormMain();

		ret.setUserId(org.getUserId());
		ret.setTitle(org.getTitle());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(FormMain formMain, Logger logger){
		logger.debug("FormMain [" + formMain.getId() + "]" + objectToString(formMain));		
    }

	public static String objectToString(FormMain formMain){
		StringBuffer buf = new StringBuffer();
        buf.append("FormMain=");
		buf.append("Id=").append(formMain.getId()).append(", ");
		buf.append("SiteId=").append(formMain.getSiteId()).append(", ");
		return buf.toString();    
    }
}
