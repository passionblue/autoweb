package com.autosite.ds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.autosite.db.ForumSection;
import com.autosite.db.ForumSectionDAO;
import com.jtrend.service.DomainStore;

public class ForumSectionDS extends AbstractDS implements DomainStore {

    protected Map m_SiteIdToMap;

    protected Map m_SectionTitleToMap;



    private static Logger m_logger = Logger.getLogger(ForumSectionDS.class);
    private static ForumSectionDS m_ForumSectionDS = null;

    public static synchronized ForumSectionDS getInstance() {
        if (m_ForumSectionDS == null) {
            m_ForumSectionDS = new ForumSectionDS();
        }
        return m_ForumSectionDS;
    }

    public static synchronized ForumSectionDS getInstance(long id) {
        ForumSectionDS ret = (ForumSectionDS) m_dsMap.get(new Long(id));
        if (ret == null) {
            ret = new ForumSectionDS(id);
            m_dsMap.put(new Long(id), ret);
        }
        return m_ForumSectionDS;
    }



    private static Map m_dsMap = new HashMap();
    protected long m_loadById=0;

    public void loadFromDB() throws Exception {
        if ( m_loadById > 0) {
            m_logger.info("Loading data " + this.getClass().getName() + " by id=" + m_loadById);
            
            List list = ((ForumSectionDAO)m_dao).findBySiteId(new Long(m_loadById));
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                updateMaps(iter.next(), false);
            }
            
        } else {
            super.loadFromDB();
        }
    }


    protected ForumSectionDS() {
        m_dao = new ForumSectionDAO();
        m_idToMap = new HashMap();

        m_SiteIdToMap = new HashMap();
        m_SectionTitleToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }

    protected ForumSectionDS(long id) {
		m_loadById = id;
        m_dao = new ForumSectionDAO();
        m_idToMap = new HashMap();

        m_SiteIdToMap = new HashMap();
        m_SectionTitleToMap = new HashMap();
        
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error(e, e);
        }
    }



    public ForumSection getById(Long id) {
        return (ForumSection) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        ForumSection o = (ForumSection)obj;
        
        if (del) {
            m_idToMap.remove(new Long(o.getId()));
            m_logger.debug("ForumSection removed from DS " + o.getId());
        }
        else {
            m_idToMap.put(new Long(o.getId()), o);
            m_logger.debug("ForumSection added to DS " + o.getId());
        }

		updateSiteIdMap(obj, del);
		updateSectionTitleMap(obj, del);
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
        ForumSection o = (ForumSection)obj;

        if (del) {

            // delete from SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map != null ) {
                map.remove(new Long(o.getId()));
            }
            m_logger.debug("ForumSection removed from SiteIdToMap" + o.getId() + " from " + o.getSiteId());
        }
        else {
            
            // add to SiteIdToMap
            Map map  = (Map) m_SiteIdToMap.get(new Long(o.getSiteId()));
            if ( map == null ) {
                map = new TreeMap();
                m_SiteIdToMap.put(new Long(o.getSiteId()), map);
            	m_logger.debug("created new   SiteIdToMap for " + o.getSiteId());

            }
            map.put(new Long(o.getId()), o);            
            m_logger.debug("ForumSection added to SiteIdToMap " + o.getId() + " to " + o.getSiteId());
        }
	}


    

    public ForumSection getBySectionTitle(String keySectionTitle) {
		return (ForumSection)m_SectionTitleToMap.get(keySectionTitle);
    }

	private void updateSectionTitleMap(Object obj, boolean del) {
        ForumSection o = (ForumSection)obj;
		String _SectionTitle = 	o.getSectionTitle();

        if (del) {
            // delete from SectionTitleToMap

			if (m_SectionTitleToMap.containsKey(_SectionTitle)){
                m_SectionTitleToMap.remove(_SectionTitle);
            	m_logger.debug("ForumSection removed from SectionTitleToMap" + o.getId() + " for [" + _SectionTitle+ "]");
			} else {
            	m_logger.debug("ForumSection removed from SectionTitleToMap" + o.getId() + " for [" + _SectionTitle+ "]");
			} 
        }
        else {
            
			if (m_SectionTitleToMap.containsKey(_SectionTitle)){
            	m_logger.debug("ForumSection repalced SectionTitleToMap" + o.getId() + " for [" + _SectionTitle+ "]");
			} else {
            	m_logger.debug("ForumSection added to SectionTitleToMap" + o.getId() + " for [" + _SectionTitle+ "]");
			} 
            m_SectionTitleToMap.put(_SectionTitle, o);
        }
	}

    public static void main(String[] args) throws Exception {

        ForumSectionDS ds = new ForumSectionDS();
        ForumSection obj = ds.getById((long)1);
        System.out.println(obj);

    }
}
