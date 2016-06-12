package com.autosite.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.autosite.db.Content;
import com.autosite.db.ContentDAO;
import com.jtrend.service.DomainStore;

public class ContentDS_Old extends AbstractDS  implements DomainStore {

    protected Map m_contentIdToMap;
    protected Map m_pageIdToMap;
    protected Map m_siteIdToMap;
    
    protected Map m_urlToMap;

    private static Logger m_logger = Logger.getLogger(ContentDS_Old.class);
    private static ContentDS_Old m_ContentDS = null;

    public static synchronized ContentDS_Old getInstance() {
        if (m_ContentDS == null) {
            m_ContentDS = new ContentDS_Old();
        }
        return m_ContentDS;
    }

    protected ContentDS_Old(){
        m_dao = new ContentDAO();
        m_idToMap = new HashMap();
        m_contentIdToMap = new HashMap();
        m_pageIdToMap = new HashMap();
        m_siteIdToMap = new HashMap();
        
        m_urlToMap = new HashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error("",e);
        }
    }
    
    protected ContentDS_Old(String siteId){
        m_dao = new ContentDAO();
        m_idToMap = new HashMap();
        m_contentIdToMap = new HashMap();
        m_pageIdToMap = new HashMap();
        m_siteIdToMap = new HashMap();
        
        m_urlToMap = new HashMap();
        try {
            loadFromDB();
        }
        catch (Exception e) {
            m_logger.error("",e);
        }
    }
    

    public Content getById(Long id) {
        return (Content) m_idToMap.get(id);
    }
    
    public void updateMaps(Object obj, boolean del) {
        Content content = (Content)obj;
        
        if (del) {
            m_idToMap.remove(new Long(content.getId()));

            // pageIdToMap
            Map contents  = (Map) m_pageIdToMap.get(new Long(content.getPageId()));
            if ( contents != null ) {
                contents.remove(new Long(content.getId()));
            }
            
            Map siteContents  = (Map) m_siteIdToMap.get(new Long(content.getSiteId()));
            if ( siteContents != null ) {
                siteContents.remove(new Long(content.getId()));
            }
            
            m_logger.debug("Content removed from DS " + content.getId());

        }
        else {
            m_idToMap.put(new Long(content.getId()), content);
            
            // pageIdToMap
            Map contents  = (Map) m_pageIdToMap.get(new Long(content.getPageId()));
            if ( contents == null ) {
                contents = new TreeMap();
                m_pageIdToMap.put(new Long(content.getPageId()), contents);
            }
            contents.put(new Long(content.getId()), content);

            // pageIdToMap
            
            Map siteContents  = (Map) m_siteIdToMap.get(new Long(content.getSiteId()));
            if ( siteContents == null ) {
                siteContents = new TreeMap();
                m_siteIdToMap.put(new Long(content.getSiteId()), siteContents);
            }
            siteContents.put(new Long(content.getId()), content);
            
            
            m_logger.debug("Content added to DS " + content.getId() + "-" + content.getSiteId());
        }
    }
    //#########################################################################################
    // Object Specific Getter
    //#########################################################################################
    
    
    public List getBySiteId(long id) {
        if ( m_siteIdToMap.containsKey(new Long(id))) {
            return new ArrayList(((Map) m_siteIdToMap.get(new Long(id))).values());
        } else {
            return new ArrayList();
        }
    }
    
    public List getByPageId(long id) {
        
        if ( m_pageIdToMap.containsKey(new Long(id))) {
            return new ArrayList(((Map) m_pageIdToMap.get(new Long(id))).values());
        } else {
            return new ArrayList();
        }
        
    }
    
    public Content getContentByUrl(String url) {
        return (Content) m_urlToMap.get(url);
    }

    //#########################################################################################
    // COMMON METHODS
    //#########################################################################################
/*    
    public void loadFromDB() throws Exception {
        m_logger.info("Loading data " + this.getClass().getName());
        List list = m_dao.findAll();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            updateMaps(iter.next(), false);
        }
    }

    public List getAll() {

        return new ArrayList(m_idToMap.values());
    }

    public boolean delete(Object obj) {
        m_dao.remove(obj);
        updateMaps(obj, true);
        return true;
    }

    public boolean put(Object obj) {
        m_dao.add(obj);
        updateMaps(obj, false);
        return true;
    }

    public boolean update(Object obj) {
        m_dao.update(obj);
        updateMaps(obj, false);
        return true;
    }
    
    
    public void reset() throws Exception {
    }
*/    
    
    
    public static void main(String[] args) throws Exception {

        ContentDS_Old ds = new ContentDS_Old();
        List list = ds.getBySiteId(29);
        System.out.println(list.size());
    }
}
