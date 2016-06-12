package com.autosite.struts.action;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.MenuItem;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Panel;
import com.autosite.ds.MenuItemDS;
import com.autosite.ds.PanelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.MenuItemForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.PanelMenuOrderUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebUtil;

public class MenuItemActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#MenuItemAction#xtent.beforeAdd");      
        MenuItem _MenuItem = (MenuItem)baseDbOject;

        if ( WebUtil.isNull(_MenuItem.getTitle())){
            m_logger.debug("title is missing in this requres " + MenuItemDS.objectToString(_MenuItem));
            throw new ActionExtentException("Title is missing.", "");
        }
        if ( WebUtil.isNull(_MenuItem.getTargetType())){
            m_logger.debug("targetType is missing in this requres " + MenuItemDS.objectToString(_MenuItem));
            throw new ActionExtentException("Target Type is missing.", "");
        }

        Panel panel = PanelDS.getInstance().getById(_MenuItem.getPanelId());
        
        if (panel == null) {
            m_logger.info("**WARNING** menuitem " + _MenuItem.getId() +" can't file panel by id "+ _MenuItem.getPanelId());
            return;
        }
        
        if (!PanelMenuOrderUtil.addMenuItem(panel, _MenuItem)){
            m_logger.info("**WARNING** menuitem " + _MenuItem.getId() +" added on request but failed to update on internal cache. Refreshing menu list for panel=" + panel.getId());
            PanelMenuOrderUtil.resetOrderedMenuList(panel);
        }

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
        m_logger.debug("#MenuItemAction#xtent.afterAdd. id=" + baseDbOject.getId());        
        MenuItem _MenuItem = (MenuItem)baseDbOject;
        
        
        
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#MenuItemAction#xtent.beforeUpdate. id=" + baseDbOject.getId());        
        MenuItem _MenuItem = (MenuItem)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#MenuItemAction#xtent.afterUpdate. id=" + baseDbOject.getId());     
        MenuItem _MenuItem = (MenuItem)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#MenuItemAction#xtent.beforeDelete. id=" + baseDbOject.getId());        
        MenuItem _MenuItem = (MenuItem)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
        m_logger.debug("#MenuItemAction#xtent.afterDelete. id=" + baseDbOject.getId());     
        MenuItem _MenuItem = (MenuItem)baseDbOject;
        
        Panel panel = PanelDS.getInstance().getById(_MenuItem.getPanelId());
        
        if (panel == null) {
            m_logger.info("**WARNING** menuitem " + _MenuItem.getId() +" can't file panel by id "+ _MenuItem.getPanelId());
            return;
        }
        
        if (!PanelMenuOrderUtil.deleteMenuItem(panel, _MenuItem)){
            m_logger.info("**WARNING** menuitem " + _MenuItem.getId() +" deleted on request but failed to update on internal cache. Refreshing menu list for panel=" + panel.getId());
            PanelMenuOrderUtil.resetOrderedMenuList(panel);
        }
    
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map ret = new HashMap();
        return ret;
    }

    private MenuItemDS m_ds = MenuItemDS.getInstance();
    private static Logger m_logger = Logger.getLogger( MenuItemActionExtent.class);
    
}
