package com.autosite.struts.action;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Panel;
import com.autosite.db.PanelLinkStyle;
import com.autosite.db.PanelPageConfig;
import com.autosite.db.PanelStyle;
import com.autosite.ds.PanelLinkStyleDS;
import com.autosite.ds.PanelPageConfigDS;
import com.autosite.ds.PanelStyleDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.PanelUtil;
import com.jtrend.util.WebUtil;

public class PanelActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelAction#xtent.beforeAdd");		
        Panel _Panel = (Panel)baseDbOject;
        
        if ( WebUtil.isTrue(_Panel.getPageOnly())){
            if (_Panel.getPageId() == 0){
                throw new Exception("Page Only View option is on. Need to specify what page it depends.");
            }
        } else {
            _Panel.setPageId(0);
        }
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PanelAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        Panel _Panel = (Panel)baseDbOject;
        
        if (_Panel.getColumnNum() == 10 || _Panel.getColumnNum() == 11 ) {
            PanelPageConfig config = new PanelPageConfig();
            config.setPageDisplaySummary(0);
            config.setSiteId(_Panel.getSiteId());
            config.setPanelId(_Panel.getId());
            config.setTimeCreated(new Timestamp(System.currentTimeMillis()));
            config.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
            
            PanelPageConfigDS.getInstance().put(config);
        }
        
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        Panel _Panel = (Panel)baseDbOject;

        if ( WebUtil.isTrue(_Panel.getPageOnly())){
            if (_Panel.getPageId() == 0){
                throw new Exception("Page Only View option is on. Need to specify what page it depends.");
            }
        } else {
            _Panel.setPageId(0);
        }
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        Panel _Panel = (Panel)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        Panel _Panel = (Panel)baseDbOject;
        
/*
        PanelStyleDS panelStyleDS = PanelStyleDS.getInstance();
        List panelStyles = panelStyleDS.getByPanelId(_Panel.getId());
        for (Iterator iterator = panelStyles.iterator(); iterator.hasNext();) {
            PanelStyle panelStyle = (PanelStyle) iterator.next();
            if (panelStyleDS.delete(panelStyle))
                m_logger.debug("deleted panelStyle for panel=" + _Panel.getId() + ", panelStyleId=" + panelStyle.getId());
            else
                m_logger.debug("failed to delete panelStyle for panel=" + _Panel.getId() + ", panelStyleId=" + panelStyle.getId());
        }
*/
        PanelStyle panelStyle = PanelUtil.getPanelStyleObject(_Panel.getId());
        if ( panelStyle != null) {
            if (PanelStyleDS.getInstance().delete(panelStyle))
                m_logger.debug("deleted panelStyle for panel=" + _Panel.getId() + ", panelStyleId=" + panelStyle.getId());
            else
                m_logger.debug("failed to delete panelStyle for panel=" + _Panel.getId() + ", panelStyleId=" + panelStyle.getId());
        }
        
        PanelLinkStyle panelLinkStyle = PanelUtil.getPanelLinkStyleObject(_Panel.getId());
        if ( panelLinkStyle != null) {
            if (PanelLinkStyleDS.getInstance().delete(panelLinkStyle))
                m_logger.debug("deleted PanelLinkStyle for panel=" + _Panel.getId() + ", panelLinkStyle=" + panelLinkStyle.getId());
            else
                m_logger.debug("failed to delete PanelLinkStyle for panel=" + _Panel.getId() + ", panelLinkStyle=" + panelLinkStyle.getId());
        }
        
        PanelPageConfig _PanelPageConfig = PanelPageConfigDS.getInstance().getByPanelId(_Panel.getId());
        if (_PanelPageConfig != null) {
            if (PanelPageConfigDS.getInstance().delete(_PanelPageConfig))
                m_logger.debug("deleted PanelPageConfig for panel=" + _Panel.getId() + ", PanelPageConfig=" + _PanelPageConfig.getId());
            else
                m_logger.debug("failed to delete PanelPageConfig for panel=" + _Panel.getId() + ", PanelPageConfig=" + _PanelPageConfig.getId());
        }
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PanelAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        Panel _Panel = (Panel)baseDbOject;
    }

    private static Logger m_logger = Logger.getLogger( PanelActionExtent.class);
    
}
