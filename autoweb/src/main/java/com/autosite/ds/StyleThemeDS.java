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
import com.autosite.db.StyleTheme;
import com.jtrend.service.DomainStore;

import com.autosite.db.StyleThemeDAO;

public class StyleThemeDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

	protected Map m_GlobalDisableToMap;






    private static Logger m_logger = Logger.getLogger(StyleThemeDS.class);
    private static StyleThemeDS m_StyleThemeDS = new StyleThemeDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static StyleThemeDS getInstance() {
        return m_StyleThemeDS;
    }

    public static synchronized StyleThemeDS getInstance(long id) {
        StyleThemeDS ret = (StyleThemeDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new StyleThemeDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_StyleThemeDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((StyleThemeDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("StyleTheme loaded from DB. num=" + m_idToMap.size());
        
    }

    protected StyleThemeDS() {
        m_dao = new StyleThemeDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
		m_GlobalDisableToMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected StyleThemeDS(long id) {
        m_dao = new StyleThemeDAO();
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

    public StyleTheme getById(Long id) {
        return (StyleTheme) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        StyleTheme o = (StyleTheme)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("StyleTheme removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("StyleTheme added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
		updateGlobalDisableMap(obj, del);
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
        StyleTheme o = (StyleTheme)obj;

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
            if (m_debug) m_logger.debug("StyleTheme removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("StyleTheme added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    



        
    public List getByGlobalDisable(int Global, int Disable) {

     	    Integer keyGlobal = new Integer(Global);

        if (m_GlobalDisableToMap.containsKey(keyGlobal)) {
            
            Map mapGlobal = (Map)m_GlobalDisableToMap.get(keyGlobal);

     	    Integer keyDisable = new Integer(Disable);
            
            if ( mapGlobal.containsKey(keyDisable)){
                return new ArrayList( ((Map)mapGlobal.get(keyDisable)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapByGlobalDisable(int Global, int Disable) {

     	    Integer keyGlobal = new Integer(Global);

        if (m_GlobalDisableToMap.containsKey(keyGlobal)) {
            
            Map mapGlobal = (Map)m_GlobalDisableToMap.get(keyGlobal);

     	    Integer keyDisable = new Integer(Disable);
            
            if ( mapGlobal.containsKey(keyDisable)){
                return (Map)mapGlobal.get(keyDisable);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateGlobalDisableMap(Object obj, boolean del) {
        StyleTheme o = (StyleTheme)obj;

     	    Integer keyGlobal = new Integer(o.getGlobal());

   	    Integer keyDisable = new Integer(o.getDisable());

        if (del) {
            // delete from GlobalDisableToMap
            Map mapGlobal  = (Map) m_GlobalDisableToMap.get(keyGlobal);
            if ( mapGlobal != null ) {
                Map mapDisable = (Map) mapGlobal.get(keyDisable);
                if (mapDisable != null){
                    mapDisable.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed StyleTheme from m_GlobalDisableToMap" + o.getId() + " from " + o.getGlobal() + " # " + o.getDisable());
        }
        else {
            
            // add to GlobalDisableToMap
            Map mapGlobal  = (Map) m_GlobalDisableToMap.get(keyGlobal);
            if ( mapGlobal == null ) {
                mapGlobal = new TreeMap();
                m_GlobalDisableToMap.put(keyGlobal, mapGlobal);
                if (m_debug) m_logger.debug("created new   mapGlobal for " + o.getGlobal());
            }
            Map mapDisable = (Map) mapGlobal.get(keyDisable);
            
            if ( mapDisable == null) {
                mapDisable = new TreeMap();
                mapGlobal.put(keyDisable, mapDisable);
                if (m_debug) m_logger.debug("created new   mapGlobal for " + o.getGlobal());
            }
            mapDisable.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to GlobalDisableToMap " + o.getId() + " to " + o.getGlobal());
        }
        
    }    
        










    public static void main(String[] args) throws Exception {

        StyleThemeDS ds = new StyleThemeDS();
        StyleTheme obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static StyleTheme createDefault(){

        StyleTheme _StyleTheme = new StyleTheme();        
	    _StyleTheme = new StyleTheme();// StyleThemeDS.getInstance().getDeafult();
        return _StyleTheme;
    }

    public static StyleTheme copy(StyleTheme org){

    	StyleTheme ret = new StyleTheme();

		ret.setTitle(org.getTitle());
		ret.setBodyWidth(org.getBodyWidth());
		ret.setBodyAlign(org.getBodyAlign());
		ret.setBodyBgColor(org.getBodyBgColor());
		ret.setBodyBgImage(org.getBodyBgImage());
		ret.setBodyBgAttach(org.getBodyBgAttach());
		ret.setBodyBgRepeat(org.getBodyBgRepeat());
		ret.setBodyBgPosition(org.getBodyBgPosition());
		ret.setContentBgColor(org.getContentBgColor());
		ret.setUseAbsolute(org.getUseAbsolute());
		ret.setAbsoluteTop(org.getAbsoluteTop());
		ret.setAbsoluteLeft(org.getAbsoluteLeft());
		ret.setAbsoluteRight(org.getAbsoluteRight());
		ret.setAbsoluteBottom(org.getAbsoluteBottom());
		ret.setPanelStyleId(org.getPanelStyleId());
		ret.setPanelDataStyleId(org.getPanelDataStyleId());
		ret.setPanelLinkStyleId(org.getPanelLinkStyleId());
		ret.setPanelTitleStyleId(org.getPanelTitleStyleId());
		ret.setMenuStyleId(org.getMenuStyleId());
		ret.setMenuLinkStyleId(org.getMenuLinkStyleId());
		ret.setHeaderMenuStyleId(org.getHeaderMenuStyleId());
		ret.setHeaderMenuLinkStyleId(org.getHeaderMenuLinkStyleId());
		ret.setListFrameStyleId(org.getListFrameStyleId());
		ret.setListSubjectStyleId(org.getListSubjectStyleId());
		ret.setListDataStyleId(org.getListDataStyleId());
		ret.setSubjectStyleId(org.getSubjectStyleId());
		ret.setDataStyleId(org.getDataStyleId());
		ret.setSingleFrameStyleId(org.getSingleFrameStyleId());
		ret.setSingleSubjectStyleId(org.getSingleSubjectStyleId());
		ret.setSingleDataStyleId(org.getSingleDataStyleId());
		ret.setContentPanelStyleId(org.getContentPanelStyleId());
		ret.setContentPanelTitleStyleId(org.getContentPanelTitleStyleId());
		ret.setGlobal(org.getGlobal());
		ret.setDisable(org.getDisable());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(StyleTheme styleTheme, Logger logger){
		logger.debug("StyleTheme [" + styleTheme.getId() + "]" + objectToString(styleTheme));		
    }

	public static String objectToString(StyleTheme styleTheme){
		StringBuffer buf = new StringBuffer();
        buf.append("StyleTheme=");
		buf.append("Id=").append(styleTheme.getId()).append(", ");
		buf.append("SiteId=").append(styleTheme.getSiteId()).append(", ");
		return buf.toString();    
    }
}
