package com.autosite.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.PanelStyle;
import com.autosite.db.StyleConfig;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.PanelStyleDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.StyleConfigForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class StyleConfigActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleConfigAction#xtent.beforeAdd");		
        StyleConfig _StyleConfig = (StyleConfig)baseDbOject;

        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#StyleConfigAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        StyleConfig _StyleConfig = (StyleConfig)baseDbOject;
        
        String panelId = (String) request.getParameter("panel_id");
        if (!WebUtil.isNull(panelId)) {
            long pid = WebParamUtil.getLongValue(panelId);
            updatePanelStyle(_StyleConfig.getSiteId(), pid, _StyleConfig);
        }
        
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleConfigAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        StyleConfig _StyleConfig = (StyleConfig)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleConfigAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        StyleConfig _StyleConfig = (StyleConfig)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleConfigAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        StyleConfig _StyleConfig = (StyleConfig)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#StyleConfigAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        StyleConfig _StyleConfig = (StyleConfig)baseDbOject;
    }

    public void updatePanelStyle(long siteId, long panelId, StyleConfig styleConfig) {
        
        List panels = PanelStyleDS.getInstance().getByPanelId(panelId);
        
        if ( panels.size() == 1) {
            
            PanelStyle panelStyle = (PanelStyle) panels.get(0);
            
            long oldStyleId = panelStyle.getStyleId();
            panelStyle.setStyleId(styleConfig.getId());
            
            PanelStyleDS.getInstance().update(panelStyle);
            m_logger.debug("Updated panel from style " + oldStyleId + " for panel " + panelId);
            
        } else {
            PanelStyle panelStyle = new PanelStyle();
            panelStyle.setPanelId(panelId);
            panelStyle.setSiteId(siteId);
            panelStyle.setStyleId(styleConfig.getId());
            PanelStyleDS.getInstance().put(panelStyle);
            m_logger.debug("Create new Panel Style panel " + panelId);
        }
    }
    
    
	private StyleConfigDS m_ds = StyleConfigDS.getInstance();
    private static Logger m_logger = Logger.getLogger( StyleConfigActionExtent.class);
    
}
