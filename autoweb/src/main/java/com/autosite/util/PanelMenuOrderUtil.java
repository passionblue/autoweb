package com.autosite.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.db.MenuItem;
import com.autosite.db.Panel;
import com.autosite.db.PanelMenuOrder;
import com.autosite.ds.MenuItemDS;
import com.autosite.ds.PanelMenuOrderDS;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebParamUtil;

public class PanelMenuOrderUtil {
    
    private static Map m_orderedMenuMap= new ConcurrentHashMap();
    
    public static void contructSiteMenuOrders(long siteId){

    }
    
    // Difference is that this first gets menuItems by calling getOrderedMenuList and check menu Items from DataStore and compares whther the list contains everything.  
    // If needed, updates the order in PanelMenuOrder object. 
    // At 8/10/10, this does not do coversion from old page only system to new menu Item system. //TODO that is todo. 
    public static List resetOrderedMenuList(Panel panel){
        
        List orgList = getOrderedMenuList(panel);
        
        List newList = new ArrayList(orgList);
        List miList = MenuItemDS.getInstance().getBySiteIdToPanelIdList(panel.getSiteId(), panel.getId());
        
        m_logger.debug("Original list size=" + orgList.size() );
        for (Iterator iterator = miList.iterator(); iterator.hasNext();) {
            MenuItem mi = (MenuItem) iterator.next();
            
            if (!newList.contains(mi)) {
                m_logger.debug("MenuItem newly added during reset " + mi.getId());
                newList.add(mi);
            }
        }

        m_logger.debug("New list size=" + newList.size() );

        m_orderedMenuMap.put(new Long(panel.getId()), newList);

        PanelMenuOrder menuOrder = PanelMenuOrderDS.getInstance().getBySiteIdToPanelId(panel.getSiteId(), panel.getId());
        String newIds = getIdsFromList(newList);
        
        if (menuOrder != null && menuOrder.getOrderedIds() != null && !menuOrder.getOrderedIds().equalsIgnoreCase(newIds)) {
            m_logger.debug("New IDS of Menu: " + newIds);
            m_logger.debug("OLD IDS of Menu: " +  menuOrder.getOrderedIds());
            
            menuOrder.setOrderedIds(newIds);
            try {
                PanelMenuOrderDS.getInstance().update(menuOrder);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
            }
        } else if ( menuOrder != null) {
            m_logger.debug("Current IDS of Menu: " +  menuOrder.getOrderedIds());
            
        } else {
            if ( newList.size() > 0){
                menuOrder = new PanelMenuOrder();
                menuOrder.setSiteId(panel.getSiteId());
                menuOrder.setPanelId(panel.getId());
                menuOrder.setOrderedIds(newIds);
                menuOrder.setTimeCreated(new TimeNow());
                menuOrder.setTimeUpdated(new TimeNow());
                if (!PanelMenuOrderDS.getInstance().put(menuOrder)){
                    m_logger.error("Error while saving newOrderObject for panel " + panel.getId());
                }
            }
        }
        
        return newList;
    }
    
    public static List getOrderedMenuList(Panel panel){
        
        if (m_orderedMenuMap.containsKey(new Long(panel.getId())))
            return (List) m_orderedMenuMap.get(new Long(panel.getId()));
            
        PanelMenuOrder menuOrder = PanelMenuOrderDS.getInstance().getBySiteIdToPanelId(panel.getSiteId(), panel.getId());
        if (menuOrder == null) return new ArrayList();
        
        String idsStr = menuOrder.getOrderedIds();
        
        String ids[]= idsStr.split("-");
        
        List ret = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            long menuId = WebParamUtil.getLongValue(ids[i]);
            
            if (menuId == 0) continue;
            
            MenuItem menuItem = MenuItemDS.getInstance().getById(menuId);
            if (menuItem != null)
                ret.add(menuItem);
        }
            

        if (menuOrder.getReverse() == 1) {
            Collections.reverse(ret);
        }

        m_orderedMenuMap.put(new Long(panel.getId()), ret);
        
        return ret;
    }
    
    //New menuitem was added. Update menu orders and save in memory. 
    public static boolean addMenuItem(Panel panel, MenuItem item){
        
        List menus = getOrderedMenuList(panel);
        
        if (!menus.contains(item))
            menus.add(item);
        
        return reorderAndSave(panel, menus);
    }
    
    public static boolean deleteMenuItem(Panel panel, MenuItem item){
        
        if ( panel == null || item == null) return false;
        
        List menus = getOrderedMenuList(panel);

        List copyMenu = new ArrayList(menus);
        
        copyMenu.remove(item);
        
        return reorderAndSave(panel, copyMenu);
    }
    
    
    public static boolean reorderAndSave(Panel panel, List menuItems){
        String reorderedIds = getIdsFromList(menuItems);
        
        PanelMenuOrder pmo = PanelMenuOrderDS.getInstance().getBySiteIdToPanelId(panel.getSiteId(), panel.getId());
        
        if (pmo == null){
            pmo = new PanelMenuOrder();
            pmo.setSiteId(panel.getSiteId());
            pmo.setPanelId(panel.getId());
            pmo.setOrderedIds(reorderedIds);
            pmo.setTimeCreated(new TimeNow());
            pmo.setTimeUpdated(new TimeNow());
            if (!PanelMenuOrderDS.getInstance().put(pmo)){
                return false;
            }
        } else {
            pmo.setOrderedIds(reorderedIds);
            if (!PanelMenuOrderDS.getInstance().update(pmo)){
                return false;
            }
        }
    
        m_orderedMenuMap.put(new Long(panel.getId()), menuItems);
        return true;
    }
    
    private static String getIdsFromList(List menuItems){
        StringBuilder buf = new StringBuilder();
        
        for (Iterator iterator = menuItems.iterator(); iterator.hasNext();) {
            MenuItem mi = (MenuItem) iterator.next();
            buf.append(mi.getId()).append("-");
        }
        
        return buf.toString();
    }
    
    public static List getMenusFromIds(String idsStr){
        
        String ids[]= idsStr.split("-");
        
        List ret = new ArrayList();
        for (int i = 0; i < ids.length; i++) {
            long menuId = WebParamUtil.getLongValue(ids[i]);
            
            if (menuId == 0) continue;
            
            MenuItem menuItem = MenuItemDS.getInstance().getById(menuId);
            if (menuItem != null)
                ret.add(menuItem);
        }
        
        return ret;
    }    
    
    public static void main(String[] args) {
        
    }
    
    private static Logger m_logger = Logger.getLogger(PanelMenuOrderUtil.class);
    
}
