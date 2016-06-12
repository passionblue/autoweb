package com.autosite.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.Page;
import com.autosite.ds.PageDS;

public class DynMenuManager {

    protected PageDS m_pageDS;

    protected Map m_siteTopLevel;
    protected Map m_levelMaps[];
    protected int m_levelDepth = 10;

    private static Logger m_logger = Logger.getLogger(DynMenuManager.class);
    private static DynMenuManager m_instance = new DynMenuManager();;
    public static boolean m_debug = AutositeGlobals.m_debug;

    public static  DynMenuManager getInstance() {
        return m_instance;
    }

    public static synchronized DynMenuManager getInstance(long siteId) {
        if (m_instance == null) {
            m_instance = new DynMenuManager(siteId);
        }
        return m_instance;
    }

    public DynMenuManager() {
        m_pageDS = PageDS.getInstance();
        reset();

    }

    public DynMenuManager(long siteId) {
        m_pageDS = PageDS.getInstance();
        reset();
    }

    public void reset() {
        m_siteTopLevel = new ConcurrentHashMap();
        m_levelMaps = new Map[m_levelDepth];
        for (int i = 0; i < m_levelDepth; i++) {
            m_levelMaps[i] = new ConcurrentHashMap();
        }

        List pagesToAdd = m_pageDS.getAll();
        while (true) {

            int numStart = pagesToAdd.size();
            List pagesLeft = new ArrayList();
            for (Iterator iterator = pagesToAdd.iterator(); iterator.hasNext();) {
                Page page = (Page) iterator.next();
                if (page.getParentId() > 0) {
                    if (!insertSubMenuPage(page)) {
                        pagesLeft.add(page);
                    }
                }
                else {
                    insertTopMenuPage(page);
                }
            }
            
            if ( pagesLeft.size() == 0)
                break;
            
            if ( pagesLeft.size() == numStart){
                break;
            }
            
            pagesToAdd = new ArrayList(pagesLeft);
            m_logger.debug("Reiterating with " + pagesToAdd.size());
        }
    }

    public List getSiteTop(long siteId, long panelId) {
        Long idKey = new Long(siteId);
        if (m_siteTopLevel.containsKey(idKey)) {
            return new ArrayList(((Map) m_siteTopLevel.get(idKey)).values());
        }

        return new ArrayList();
    }

    public List getSubMenus(long id) {

        Map subMenuMap = getSubMenuMap(id);
        if (subMenuMap != null) {
            List list = new ArrayList(subMenuMap.values());
            return sortMenu(list);
        }
        return new ArrayList();
    }

    public boolean insertPage(Page page){
        if (page.getParentId() > 0) {
            if (!insertSubMenuPage(page)) {
                return false;
            }
        }
        else {
            insertTopMenuPage(page);
        }
        return true;
    }
    
    public boolean removePage(Page page){
        if (page.getParentId() > 0) {
            if (!removeSubMenuPage(page)) {
                return false;
            }
        }
        else {
            removeTopMenuPage(page);
        }
        return true;
    }

    public String [] getPagePathNames( long id){

        PageDS pageDS = PageDS.getInstance();
        
        ArrayList listBackward = new ArrayList();
        
        long searchId = id;
        while(true){
            Page page = pageDS.getById(searchId);
            listBackward.add(page.getPageMenuTitle());
            if ( page.getParentId() <= 0 ) break;
            searchId = page.getParentId();
        }
        
        String ret[] = new String[listBackward.size()];
        int curIdx = ret.length-1;
        for (Iterator iterator = listBackward.iterator(); iterator.hasNext();) {
            String path = (String) iterator.next();
            
            ret[curIdx] = path;
            curIdx--;
        }
        return ret;
    }
    
    
    public Page [] getPagePath( long id){
        PageDS pageDS = PageDS.getInstance();
        
        ArrayList listBackward = new ArrayList();
        
        long searchId = id;
        while(true){
            Page page = pageDS.getById(searchId);
            if ( page == null )
                break;
            listBackward.add(page);
            if ( page.getParentId() <= 0 ) break;
            searchId = page.getParentId();
        }
        
        if ( listBackward.size() == 0) return new Page[0];
        
        Page ret[] = new Page[listBackward.size()];
        int curIdx = ret.length-1;
        for (Iterator iterator = listBackward.iterator(); iterator.hasNext();) {
            Page path = (Page) iterator.next();
            
            ret[curIdx] = path;
            curIdx--;
        }
        return ret;
    }    
    
    public boolean isInPagePath(long pageId, long compareId ){
        Page page[] = getPagePath(pageId);
        
        for (int i = 0; i < page.length; i++) {
            if ( page[i].getId() == compareId)
                return true;
        }
        
        return false;
    }
    
    public boolean isPagePathTop(long pageId, long compareId ){
        Page page[] = getPagePath(pageId);

        if (page.length == 0) return false;
        
        if ( page[0].getId() == compareId) return true;
        
        return false;
    }
    
    protected int getSubMenuIdx(long id){
        Long idKey = new Long(id);

        for (int i = 0; i < m_levelDepth; i++) {
            if (m_levelMaps[i].containsKey(idKey)) {
                return i;
            }
        }

        return -1;
    }
    
    
    protected List sortMenu(List rawList) {
        return rawList;
    }

    protected Map getSubMenuMap(long id) {
        Long idKey = new Long(id);

        for (int i = 0; i < m_levelDepth; i++) {
            if (m_levelMaps[i].containsKey(idKey)) {
                m_logger.debug("founded sub menu for " + idKey );
                return (Map) m_levelMaps[i].get(idKey);
            }
        }
        return null;
    }

    protected boolean insertTopMenuPage(Page page) {
        Long idKey = new Long(page.getId());
        
        m_siteTopLevel.put(idKey, page);
        m_levelMaps[0].put(idKey, new ConcurrentHashMap());

        return true;
    }

    protected boolean removeTopMenuPage(Page page) {
        Long idKey = new Long(page.getId());

        boolean result = true;
        result = result & (m_siteTopLevel.remove(idKey) != null);
        result = result & (m_levelMaps[0].remove(idKey) != null);

        if (m_debug) m_logger.debug("removed from top level. " + PageDS.objectToString(page));
        return result;
    }

    protected boolean insertSubMenuPage(Page page) {

        Long pidKey = new Long(page.getParentId());
        Long idKey = new Long(page.getId());
        for (int i = 0; i < m_levelDepth; i++) {
            if (m_levelMaps[i].containsKey(pidKey)) {
                Map subMap = (Map) m_levelMaps[i].get(pidKey);
                subMap.put(idKey, page);
                m_levelMaps[i + 1].put(idKey, new ConcurrentHashMap());
                if (m_debug)
                    m_logger.debug("added sub menu page. " + PageDS.objectToString(page));
                return true;
            }
        }

        return false;
    }

    protected boolean removeSubMenuPage(Page page) {

        Long pidKey = new Long(page.getParentId());
        Long idKey = new Long(page.getId());
        for (int i = 0; i < m_levelDepth; i++) {
            if (m_levelMaps[i].containsKey(pidKey)) {
                Map subMap = (Map) m_levelMaps[i].get(pidKey);
                subMap.remove(idKey);
                m_levelMaps[i + 1].remove(idKey);
                if (m_debug)
                    m_logger.debug("removed sub menu page. " + PageDS.objectToString(page));
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        testPath();
    }
    
    public static void testPath(){
        Page path[] = DynMenuManager.getInstance().getPagePath(602);

        for (int i = 0; i < path.length; i++) {
            System.out.println(path[i].getId());
        }
        
        System.out.println(DynMenuManager.getInstance().isInPagePath(602, 601));
        System.out.println(DynMenuManager.getInstance().isPagePathTop(602, 30));
        
    }    
    public static void test(){
        
        Page p = PageDS.getInstance().getById(new Long(594));
        Page p2 = PageDS.getInstance().getById(new Long(585));
        
        List list = DynMenuManager.getInstance().getSubMenus(594);
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            System.out.println(PageDS.objectToString(page));
        }
        
        DynMenuManager.getInstance().removePage(p2);

        list = DynMenuManager.getInstance().getSubMenus(594);
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            System.out.println(PageDS.objectToString(page));
        }

        p2.setParentId(30);
        DynMenuManager.getInstance().insertPage(p2);
        
        
        list = DynMenuManager.getInstance().getSubMenus(30);
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            System.out.println(PageDS.objectToString(page));
        }
    }

}
