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
import com.autosite.db.FormField;
import com.jtrend.service.DomainStore;

import com.autosite.db.FormFieldDAO;

public class FormFieldDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_UserIdToMap;







    private static Logger m_logger = Logger.getLogger(FormFieldDS.class);
    private static FormFieldDS m_FormFieldDS = new FormFieldDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static FormFieldDS getInstance() {
        return m_FormFieldDS;
    }

    public static synchronized FormFieldDS getInstance(long id) {
        FormFieldDS ret = (FormFieldDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new FormFieldDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_FormFieldDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((FormFieldDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("FormField loaded from DB. num=" + m_idToMap.size());
        
    }

    protected FormFieldDS() {
        m_dao = new FormFieldDAO();
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

    protected FormFieldDS(long id) {
        m_dao = new FormFieldDAO();
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

    public FormField getById(Long id) {
        return (FormField) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        FormField o = (FormField)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("FormField removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("FormField added to DS " + o.getId());
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
        FormField o = (FormField)obj;

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
            if (m_debug) m_logger.debug("FormField removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("FormField added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
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
        FormField o = (FormField)obj;

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
            if (m_debug) m_logger.debug("FormField removed from UserIdToMap" + o.getId() + " from " + o.getUserId());
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
            if (m_debug) m_logger.debug("FormField added to UserIdToMap " + o.getId() + " to " + o.getUserId());
        }
    }


    













    public static void main(String[] args) throws Exception {

        FormFieldDS ds = new FormFieldDS();
        FormField obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static FormField createDefault(){

        FormField _FormField = new FormField();        
	    _FormField = new FormField();// FormFieldDS.getInstance().getDeafult();
        return _FormField;
    }

    public static FormField copy(FormField org){

    	FormField ret = new FormField();

		ret.setUserId(org.getUserId());
		ret.setFormId(org.getFormId());
		ret.setFieldText(org.getFieldText());
		ret.setFieldType(org.getFieldType());
		ret.setRequired(org.getRequired());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(FormField formField, Logger logger){
		logger.debug("FormField [" + formField.getId() + "]" + objectToString(formField));		
    }

	public static String objectToString(FormField formField){
		StringBuffer buf = new StringBuffer();
        buf.append("FormField=");
		buf.append("Id=").append(formField.getId()).append(", ");
		buf.append("SiteId=").append(formField.getSiteId()).append(", ");
		return buf.toString();    
    }
}
