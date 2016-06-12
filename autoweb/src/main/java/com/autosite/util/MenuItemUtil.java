package com.autosite.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.autosite.db.MenuItem;
import com.autosite.db.Page;
import com.autosite.db.Panel;
import com.autosite.db.PanelMenuOrder;
import com.autosite.ds.MenuItemDS;
import com.autosite.ds.PanelMenuOrderDS;
import com.jtrend.util.TimeNow;

public class MenuItemUtil {

    public final static int TYPE_PAGE       = 0;
    public final static int TYPE_LINK       = 1;
    public final static int TYPE_CONTENT    = 2;
    
    
    //Create menuitems and set order as passed. 
    public static List createMenuItems(Panel panel, List pages){

        List ret = new ArrayList();
        StringBuilder idsStringBuilder = new StringBuilder();
        
        
        for (Iterator iterator = pages.iterator(); iterator.hasNext();) {
            Page page = (Page) iterator.next();
            
            MenuItem mi = new MenuItem();
            mi.setSiteId(panel.getSiteId());
            mi.setPanelId(panel.getId());
            mi.setPageId(page.getId());
            mi.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            mi.setTitle(page.getPageMenuTitle());
            MenuItemDS.getInstance().put(mi);
            
            idsStringBuilder.append(mi.getId()).append("-");
        }

        PanelMenuOrder menuOrder = new PanelMenuOrder();
        menuOrder.setSiteId(panel.getSiteId());
        menuOrder.setPanelId(panel.getId());
        menuOrder.setOrderedIds(idsStringBuilder.toString());
        menuOrder.setTimeCreated(new TimeNow());
        menuOrder.setTimeUpdated(new TimeNow());
        PanelMenuOrderDS.getInstance().put(menuOrder);
        
        return ret;
    }
    
    public static void main(String[] args) {
        
    }
    
    private static Logger m_logger = Logger.getLogger(MenuItemUtil.class);
}
