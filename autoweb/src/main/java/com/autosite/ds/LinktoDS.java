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
import com.autosite.db.Linkto;
import com.jtrend.service.DomainStore;

import com.autosite.db.LinktoDAO;

public class LinktoDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;
    protected Map m_LinkKeyToOneMap;






    private static Logger m_logger = Logger.getLogger(LinktoDS.class);
    private static LinktoDS m_LinktoDS = new LinktoDS();
	public static boolean m_debug = AutositeGlobals.m_debug;

    public static LinktoDS getInstance() {
        return m_LinktoDS;
    }

    public static synchronized LinktoDS getInstance(long id) {
        LinktoDS ret = (LinktoDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new LinktoDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_LinktoDS;
    }

    private static Map m_dsMap = new ConcurrentHashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {

            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((LinktoDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
        } else {
            super.loadFromDB();
        }

        m_logger.info("Linkto loaded from DB. num=" + m_idToMap.size());
        
    }

    protected LinktoDS() {
        m_dao = new LinktoDAO();
        m_idToMap = new ConcurrentHashMap();

        m_SiteIdToMap = new ConcurrentHashMap();
        m_LinkKeyToOneMap = new ConcurrentHashMap();
 
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected LinktoDS(long id) {
        m_dao = new LinktoDAO();
        m_idToMap = new ConcurrentHashMap();
        m_loadById = id;

        m_SiteIdToMap = new ConcurrentHashMap();
        m_LinkKeyToOneMap = new ConcurrentHashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    public Linkto getById(Long id) {
        return (Linkto) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        Linkto o = (Linkto)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            if (m_debug) m_logger.debug("Linkto removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            if (m_debug) m_logger.debug("Linkto added to DS " + o.getId());
        }

        updateSiteIdMap(obj, del);
        updateLinkKeyOneMap(obj, del);
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
        Linkto o = (Linkto)obj;

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
            if (m_debug) m_logger.debug("Linkto removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
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
            if (m_debug) m_logger.debug("Linkto added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
    }


    

	// listOneToOneStringKey - LinkKey

    public Linkto getObjectByLinkKey(String keyLinkKey) {
    	if (keyLinkKey == null) return null; 
        return (Linkto)m_LinkKeyToOneMap.get(keyLinkKey);
    }

    private void updateLinkKeyOneMap(Object obj, boolean del) {
        Linkto o = (Linkto)obj;
        String _LinkKey =  o.getLinkKey();

		if (  _LinkKey == null || _LinkKey.isEmpty() ) return;

        if (del) {
            // delete from LinkKeyToMap

            if (m_LinkKeyToOneMap.containsKey(_LinkKey)){
                m_LinkKeyToOneMap.remove(_LinkKey);
                 if (m_debug) m_logger.debug("Linkto removed from LinkKeyToMap" + o.getId() + " for [" + _LinkKey+ "]");
            } else {
                if (m_debug) m_logger.debug("Linkto not removed from LinkKeyToMap" + o.getId() + " for [" + _LinkKey+ "]");
            } 
        }
        else {
            
            if (m_LinkKeyToOneMap.containsKey(_LinkKey)){
                if (m_debug) m_logger.debug("Linkto repalced LinkKeyToMap" + o.getId() + " for [" + _LinkKey+ "]");
            } else {
                if (m_debug) m_logger.debug("Linkto added to LinkKeyToMap" + o.getId() + " for [" + _LinkKey+ "]");
            } 
            m_LinkKeyToOneMap.put(_LinkKey, o);
        }
    }











    public static void main(String[] args) throws Exception {

        LinktoDS ds = new LinktoDS();
        Linkto obj = ds.getById((long)1);
        System.out.println(obj);

    }

	//
    public static Linkto createDefault(){

        Linkto ret = new Linkto();        
//      ret.setLinkKey("");           
//      ret.setLinkTarget("");           
//      ret.setDisable("");           
//      ret.setTimeCreated("");           
        return ret;
    }

    public static Linkto copy(Linkto org){

    	Linkto ret = new Linkto();

		ret.setLinkKey(org.getLinkKey());
		ret.setLinkTarget(org.getLinkTarget());
		ret.setDisable(org.getDisable());
		ret.setTimeCreated(org.getTimeCreated());

        return ret;
    }

	public static void objectToLog(Linkto linkto, Logger logger){
		logger.debug("Linkto [" + linkto.getId() + "]" + objectToString(linkto));		
    }

	public static String objectToString(Linkto linkto){
		StringBuffer buf = new StringBuffer();
        buf.append("Linkto=");
		buf.append("Id=").append(linkto.getId()).append(", ");
		buf.append("SiteId=").append(linkto.getSiteId()).append(", ");
		return buf.toString();    
    }
}
