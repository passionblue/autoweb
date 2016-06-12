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


import com.autosite.db.StyleConfigDAO;
import com.autosite.db.StyleConfig;
import com.surveygen.db.BaseMemoryDAO;

public class StyleConfigDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;
    protected Map m_StyleKeyToOneMap;



    protected Map m_SiteIdToStyleUseToMap;



    private static Logger m_logger = Logger.getLogger(StyleConfigDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static StyleConfigDS m_StyleConfigDS = new StyleConfigDSExtent();



    public static StyleConfigDS getInstance() {
        return m_StyleConfigDS;
    }

    public static synchronized StyleConfigDS getInstance(long id) {
        StyleConfigDS ret = (StyleConfigDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new StyleConfigDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_StyleConfigDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((StyleConfigDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("StyleConfig loaded from DB. num=" + m_idToMap.size());
        
    }


    protected StyleConfigDS() {
        m_dao = new StyleConfigDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_StyleKeyToOneMap = new ConcurrentHashMap();
        m_SiteIdToStyleUseToMap = new AggregatedIdMap();
 
		if ( m_aggregateAllSites ) {
        	m_SiteIdToMap = new AggregatedIdMap();
            m_SiteIdToStyleUseToMap = new AggregatedIdMap();
		}

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected StyleConfigDS(long id) {
        m_dao = new StyleConfigDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_StyleKeyToOneMap = new ConcurrentHashMap();
        m_SiteIdToStyleUseToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public StyleConfig getById(Long id) {
        return (StyleConfig) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        StyleConfig o = (StyleConfig)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("StyleConfig removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("StyleConfig added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateStyleKeyOneMap(obj, del);
        updateSiteIdToStyleUseMap(obj, del);
    }
	
    //    //    //
	// ListKeys - SiteId
    public List getBySiteId(long SiteId) {
        
        Long _SiteId  = new Long(SiteId);
        if (m_SiteIdToMap.containsKey(_SiteId)) {
            return new ArrayList( ((Map)m_SiteIdToMap.get(_SiteId)).values() );
        } else {
            if ( m_aggregateAllSites) 
                return new ArrayList(((Map)m_SiteIdToMap.get(new Long(0))).values() ); //m_aggregateAllSites=true
            else 
                return new ArrayList();
        }
    }

    private void updateSiteIdMap(Object obj, boolean del) {
        StyleConfig o = (StyleConfig)obj;

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
            if (m_debug) m_logger.debug("StyleConfig removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("StyleConfig added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneStringKey - StyleKey

    public StyleConfig getObjectByStyleKey(String keyStyleKey) {
    	if (keyStyleKey == null) return null; 
        return (StyleConfig)m_StyleKeyToOneMap.get(keyStyleKey);
    }

    private void updateStyleKeyOneMap(Object obj, boolean del) {
        StyleConfig o = (StyleConfig)obj;
        String _StyleKey =  o.getStyleKey();

		if (  _StyleKey == null || _StyleKey.isEmpty() ) return;

        if (del) {
            // delete from StyleKeyToMap

            if (m_StyleKeyToOneMap.containsKey(_StyleKey)){
                m_StyleKeyToOneMap.remove(_StyleKey);
                 if (m_debug) m_logger.debug("StyleConfig removed from StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } else {
                if (m_debug) m_logger.debug("StyleConfig not removed from StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } 
        }
        else {
            
            if (m_StyleKeyToOneMap.containsKey(_StyleKey)){
                if (m_debug) m_logger.debug("StyleConfig repalced StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } else {
                if (m_debug) m_logger.debug("StyleConfig added to StyleKeyToMap" + o.getId() + " for [" + _StyleKey+ "]");
            } 
            m_StyleKeyToOneMap.put(_StyleKey, o);
        }
    }





    public List getBySiteIdToStyleUseList (long SiteId, long StyleUse) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToStyleUseToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToStyleUseToMap.get(keySiteId);

     	    Long keyStyleUse = StyleUse;
            
            if ( mapSiteId.containsKey(keyStyleUse)){
                return new ArrayList( ((Map)mapSiteId.get(keyStyleUse)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdStyleUse(long SiteId, long StyleUse) {

        Long keySiteId  = SiteId;
        if (m_SiteIdToStyleUseToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToStyleUseToMap.get(keySiteId);

     	    Long keyStyleUse = new Long(StyleUse);
            
            if ( mapSiteId.containsKey(keyStyleUse)){
                return (Map)mapSiteId.get(keyStyleUse);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToStyleUseMap(Object obj, boolean del) {
        StyleConfig o = (StyleConfig)obj;

   	    Long keyStyleUse = new Long(o.getStyleUse());

        if (del) {
            // delete from SiteIdStyleUseToMap
            Map mapSiteId  = (Map) m_SiteIdToStyleUseToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapStyleUse = (Map) mapSiteId.get(keyStyleUse);
                if (mapStyleUse != null){
                    mapStyleUse.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed StyleConfig from m_SiteIdToStyleUseToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getStyleUse());
        }
        else {
            
            // add to SiteIdStyleUseToMap
            Map mapSiteId  = (Map) m_SiteIdToStyleUseToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToStyleUseToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapStyleUse = (Map) mapSiteId.get(keyStyleUse);
            
            if ( mapStyleUse == null) {
                mapStyleUse = new TreeMap();
                mapSiteId.put(keyStyleUse, mapStyleUse);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapStyleUse.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdStyleUseToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    






	//

    public static void main(String[] args) throws Exception {

        StyleConfigDS ds = new StyleConfigDS();
        StyleConfig obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static StyleConfig createDefault(){

        StyleConfig _StyleConfig = new StyleConfig();        
	    _StyleConfig = new StyleConfig();// StyleConfigDS.getInstance().getDeafult();
        return _StyleConfig;
    }

    public static StyleConfig copy(StyleConfig org){

    	StyleConfig ret = new StyleConfig();

		ret.setStyleKey(org.getStyleKey());
		ret.setStyleUse(org.getStyleUse());
		ret.setIsGlobal(org.getIsGlobal());
		ret.setIdClass(org.getIdClass());
		ret.setIsId(org.getIsId());
		ret.setColor(org.getColor());
		ret.setBgColor(org.getBgColor());
		ret.setBgImage(org.getBgImage());
		ret.setBgRepeat(org.getBgRepeat());
		ret.setBgAttach(org.getBgAttach());
		ret.setBgPosition(org.getBgPosition());
		ret.setTextAlign(org.getTextAlign());
		ret.setFontFamily(org.getFontFamily());
		ret.setFontSize(org.getFontSize());
		ret.setFontStyle(org.getFontStyle());
		ret.setFontVariant(org.getFontVariant());
		ret.setFontWeight(org.getFontWeight());
		ret.setBorderDirection(org.getBorderDirection());
		ret.setBorderWidth(org.getBorderWidth());
		ret.setBorderStyle(org.getBorderStyle());
		ret.setBorderColor(org.getBorderColor());
		ret.setMargin(org.getMargin());
		ret.setPadding(org.getPadding());
		ret.setListStyleType(org.getListStyleType());
		ret.setListStylePosition(org.getListStylePosition());
		ret.setListStyleImage(org.getListStyleImage());
		ret.setFloating(org.getFloating());
		ret.setExtraStyleStr(org.getExtraStyleStr());
		ret.setItemStyleStr(org.getItemStyleStr());
		ret.setLink(org.getLink());
		ret.setLinkHover(org.getLinkHover());
		ret.setLinkActive(org.getLinkActive());
		ret.setHeight(org.getHeight());
		ret.setWidth(org.getWidth());
		ret.setIsTable(org.getIsTable());
		ret.setBorderCollapse(org.getBorderCollapse());
		ret.setBorderSpacing(org.getBorderSpacing());
		ret.setTrStyleIds(org.getTrStyleIds());
		ret.setTdStyleIds(org.getTdStyleIds());
		ret.setTimeCreated(org.getTimeCreated());
		ret.setTimeUpdated(org.getTimeUpdated());

        return ret;
    }

	public static void objectToLog(StyleConfig styleConfig, Logger logger){
		logger.debug("StyleConfig [" + styleConfig.getId() + "]" + objectToString(styleConfig));		
    }

	public static String objectToString(StyleConfig styleConfig){
		StringBuilder buf = new StringBuilder();
        buf.append("StyleConfig=");
		buf.append("Id=").append(styleConfig.getId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		StyleConfig styleConfig = (StyleConfig)object;
		StringBuilder buf = new StringBuilder();
        buf.append("StyleConfig=");
		buf.append("Id=").append(styleConfig.getId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
