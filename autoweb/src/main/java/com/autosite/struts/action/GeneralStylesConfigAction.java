package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Site;
import com.autosite.db.StyleConfig;
import com.autosite.ds.SiteDS;
import com.autosite.ds.StyleConfigDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.PanelLinkStyleForm;
import com.jtrend.util.WebParamUtil;

public class GeneralStylesConfigAction extends AutositeCoreAction {

    public GeneralStylesConfigAction(){
	    m_ds = StyleConfigDS.getInstance();
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PanelLinkStyleForm _PanelLinkStyleForm = (PanelLinkStyleForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        long styleId = Long.parseLong(request.getParameter("styleConfigId"));
        StyleConfig _StyleConfig = m_ds.getById(styleId);
        
        long siteId = _StyleConfig.getSiteId();
        if (site.getId() == siteId ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }
        
        if (hasRequestValue(request, "act", "updateBackground")){
            updateBackgroundStyle(request, response, _StyleConfig);
        }
        
        
        
        return mapping.findForward("default");
    }
    
    public void updateBackgroundStyle(HttpServletRequest request, HttpServletResponse response, StyleConfig _StyleConfig){
        
        if (!isMissing(request.getParameter("bgColor"))) {
            m_logger.debug("updating param bgColor from " +_StyleConfig.getBgColor() + "->" + request.getParameter("bgColor"));
            _StyleConfig.setBgColor(WebParamUtil.getStringValue(request.getParameter("bgColor")));
        }
        if (!isMissing(request.getParameter("bgImage"))) {
            m_logger.debug("updating param bgImage from " +_StyleConfig.getBgImage() + "->" + request.getParameter("bgImage"));
            _StyleConfig.setBgImage(WebParamUtil.getStringValue(request.getParameter("bgImage")));
        }
        if (!isMissing(request.getParameter("bgRepeat"))) {
            m_logger.debug("updating param bgRepeat from " +_StyleConfig.getBgRepeat() + "->" + request.getParameter("bgRepeat"));
            _StyleConfig.setBgRepeat(WebParamUtil.getStringValue(request.getParameter("bgRepeat")));
        }
        if (!isMissing(request.getParameter("bgAttach"))) {
            m_logger.debug("updating param bgAttach from " +_StyleConfig.getBgAttach() + "->" + request.getParameter("bgAttach"));
            _StyleConfig.setBgAttach(WebParamUtil.getStringValue(request.getParameter("bgAttach")));
        }
        if (!isMissing(request.getParameter("bgPosition"))) {
            m_logger.debug("updating param bgPosition from " +_StyleConfig.getBgPosition() + "->" + request.getParameter("bgPosition"));
            _StyleConfig.setBgPosition(WebParamUtil.getStringValue(request.getParameter("bgPosition")));
        }
        
    }
    

    protected boolean loginRequired() {
        return true;
    }

	protected StyleConfigDS m_ds;

    private static Logger m_logger = Logger.getLogger( GeneralStylesConfigAction.class);
}
