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


import com.autosite.db.PanelPositionStyleDAO;
import com.autosite.db.PanelPositionStyle;
import com.surveygen.db.BaseMemoryDAO;

public class PanelPositionStyleDS extends AbstractDS implements DomainStore {

    protected boolean m_aggregateAllSites = false; //If true, this will invalidate site id 

    protected Map m_SiteIdToMap;
    protected Map m_ColToMap;



    protected Map m_SiteIdToColToMap;



    private static Logger m_logger = Logger.getLogger(PanelPositionStyleDS.class);
	public static boolean m_debug = AutositeGlobals.m_debug;

    private static PanelPositionStyleDS m_PanelPositionStyleDS = new PanelPositionStyleDSExtent();



    public static PanelPositionStyleDS getInstance() {
        return m_PanelPositionStyleDS;
    }

    public static synchronized PanelPositionStyleDS getInstance(long id) {
        PanelPositionStyleDS ret = (PanelPositionStyleDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new PanelPositionStyleDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_PanelPositionStyleDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((PanelPositionStyleDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                if ( usingHolderObject() ) 
                    updateMapsByDbObj(iter.next(), false);
                else 
                    updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("PanelPositionStyle loaded from DB. num=" + m_idToMap.size());
        
    }


    protected PanelPositionStyleDS() {
        m_dao = new PanelPositionStyleDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ColToMap = new ConcurrentHashMap();
        m_SiteIdToColToMap = new ConcurrentHashMap();
 
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected PanelPositionStyleDS(long id) {
        m_dao = new PanelPositionStyleDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_ColToMap = new ConcurrentHashMap();
        m_SiteIdToColToMap = new ConcurrentHashMap();
		if ( m_aggregateAllSites )
        	m_SiteIdToMap = new AggregatedIdMap();

        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public PanelPositionStyle getById(Long id) {
        return (PanelPositionStyle) m_idToMap.get(id);
    }

    public void updateMapsByDbObj(Object obj, boolean del) {
        updateMaps(obj, del);
    }
    public void updateMaps(Object obj, boolean del) {
        PanelPositionStyle o = (PanelPositionStyle)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("PanelPositionStyle removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("PanelPositionStyle added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateColMap(obj, del);
        updateSiteIdToColMap(obj, del);
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
        PanelPositionStyle o = (PanelPositionStyle)obj;

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
            if (m_debug) m_logger.debug("PanelPositionStyle removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("PanelPositionStyle added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


	// ListStringKeys - Col

    public List getByCol(String keyCol) {
        
        if (m_ColToMap.containsKey(keyCol)) {
            return new ArrayList( ((Map)m_ColToMap.get(keyCol)).values() );
        } else {
            return new ArrayList();
        }
    }

    private void updateColMap(Object obj, boolean del) {
        PanelPositionStyle o = (PanelPositionStyle)obj;

        if (del) {

            // delete from ColToMap
            Map map  = (Map) m_ColToMap.get(o.getCol());
            if ( map != null ) {
                map.remove(new Long(o.getId()));
                if (m_debug) m_logger.debug("PanelPositionStyle removed from ColToMap" + o.getId() + " from [" + o.getCol() + "]");
            } else {
                if (m_debug) m_logger.debug("PanelPositionStyle NOT removed from ColToMap because no map found for [" + o.getCol() + "]");
            }
        }
        else {
            
            // add to ColToMap
            Map map  = (Map) m_ColToMap.get(o.getCol());
            if ( map == null ) {
                map = new TreeMap();
                m_ColToMap.put(o.getCol(), map);
                if (m_debug) m_logger.debug("created new   ColToMap for [" + o.getCol() + "]");
            }
            map.put(new Long(o.getId()), o);            
            if (m_debug) m_logger.debug("PanelPositionStyle added to ColToMap " + o.getId() + " to [" + o.getCol() + "]");
        }
    }

    






    public List getBySiteIdColList(long SiteId, String Col) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToColToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToColToMap.get(keySiteId);

     	    String keyCol =  Col;
			if (  keyCol == null || keyCol.isEmpty()) return new ArrayList();
            
            if ( mapSiteId.containsKey(keyCol)){
                return new ArrayList( ((Map)mapSiteId.get(keyCol)).values() );
            } else {
                return new ArrayList();
            }
                
        } else {
            return new ArrayList();
        }
    }	

    public Map getMapBySiteIdColList(long SiteId, String Col) {

        Long keySiteId  = new Long(SiteId);
        if (m_SiteIdToColToMap.containsKey(keySiteId)) {
            
            Map mapSiteId = (Map)m_SiteIdToColToMap.get(keySiteId);

     	    String keyCol =  Col;
			if (  keyCol == null || keyCol.isEmpty()) return new HashMap();
            
            if ( mapSiteId.containsKey(keyCol)){
                return (Map)mapSiteId.get(keyCol);
            } else {
                return new HashMap();
            }
                
        } else {
            return new HashMap();
        }
    }	



    private void updateSiteIdToColMap(Object obj, boolean del) {
        PanelPositionStyle o = (PanelPositionStyle)obj;

   	    String keyCol =  o.getCol();
		if (  keyCol == null || keyCol.isEmpty()) return;

        if (del) {
            // delete from SiteIdColToMap
            Map mapSiteId  = (Map) m_SiteIdToColToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId != null ) {
                Map mapCol = (Map) mapSiteId.get(keyCol);
                if (mapCol != null){
                    mapCol.remove(new Long(o.getId()));
                }
            }
            m_logger.debug("removed PanelPositionStyle from m_SiteIdToColToMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getCol());
        }
        else {
            
            // add to SiteIdColToMap
            Map mapSiteId  = (Map) m_SiteIdToColToMap.get(new Long(o.getSiteId()));
            if ( mapSiteId == null ) {
                mapSiteId = new TreeMap();
                m_SiteIdToColToMap.put(new Long(o.getSiteId()), mapSiteId);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            Map mapCol = (Map) mapSiteId.get(keyCol);
            
            if ( mapCol == null) {
                mapCol = new TreeMap();
                mapSiteId.put(keyCol, mapCol);
                if (m_debug) m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }
            mapCol.put(new Long(o.getId()), o);            
            
            if (m_debug) m_logger.debug("Added to SiteIdColToMap " + o.getId() + " to " + o.getSiteId());
        }
        
    }    





	//

    public static void main(String[] args) throws Exception {

        PanelPositionStyleDS ds = new PanelPositionStyleDS();
        PanelPositionStyle obj = ds.getById((long)1);
        System.out.println(obj);

    }


    public static PanelPositionStyle createDefault(){

        PanelPositionStyle _PanelPositionStyle = new PanelPositionStyle();        
	    _PanelPositionStyle = new PanelPositionStyle();// PanelPositionStyleDS.getInstance().getDeafult();
        return _PanelPositionStyle;
    }

    public static PanelPositionStyle copy(PanelPositionStyle org){

    	PanelPositionStyle ret = new PanelPositionStyle();

		ret.setCol(org.getCol());
		ret.setStyleId(org.getStyleId());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(PanelPositionStyle panelPositionStyle, Logger logger){
		logger.debug("PanelPositionStyle [" + panelPositionStyle.getId() + "]" + objectToString(panelPositionStyle));		
    }

	public static String objectToString(PanelPositionStyle panelPositionStyle){
		StringBuilder buf = new StringBuilder();
        buf.append("PanelPositionStyle=");
		buf.append("Id=").append(panelPositionStyle.getId()).append(", ");
		buf.append("SiteId=").append(panelPositionStyle.getSiteId()).append(", ");
		return buf.toString();    
    }





	public String objectToString2(AutositeDataObject object){
		PanelPositionStyle panelPositionStyle = (PanelPositionStyle)object;
		StringBuilder buf = new StringBuilder();
        buf.append("PanelPositionStyle=");
		buf.append("Id=").append(panelPositionStyle.getId()).append(", ");
		buf.append("SiteId=").append(panelPositionStyle.getSiteId()).append(", ");
		return buf.toString();    
    }


	// Empty methods for 

}
