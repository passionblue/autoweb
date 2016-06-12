package com.autosite.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeUser;
import com.autosite.db.Panel;
import com.autosite.db.Site;
import com.autosite.ds.MenuItemDS;
import com.autosite.ds.PanelDS;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.MenuItemForm;
import com.autosite.util.PanelMenuOrderUtil;
import com.jtrend.util.WebParamUtil;

public class PanelMenuReorderAction extends AutositeCoreAction {

    public PanelMenuReorderAction(){
        m_ds = MenuItemDS.getInstance();
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        MenuItemForm _MenuItemForm = (MenuItemForm) form;
        HttpSession session = request.getSession();

        setPage(session, "panel_menu_reorder");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        if (context == null || !context.isSuperAdmin()) accessTestOkay = false;
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        if (isMissing(request.getParameter("ids"))){
            return mapping.findForward("default");
        }
        if (isMissing(request.getParameter("panelId"))){
            return mapping.findForward("default");
        }
        
        long panelId = WebParamUtil.getLongValue(request.getParameter("panelId"));
        Panel panel = PanelDS.getInstance().getById(panelId);
        
        if (panel == null) {
            sessionErrorText(session, "Error occurred. Reorder not saved. Please try again");
            m_logger.error("Panel was not found with passed panel Id="+ panelId);
            return mapping.findForward("default");
            
        }
        String ids = request.getParameter("ids");
        
        
        List menus = PanelMenuOrderUtil.getMenusFromIds(ids);
        
        PanelMenuOrderUtil.reorderAndSave(panel, menus);
        
        

        return mapping.findForward("default");
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected MenuItemDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PanelMenuReorderAction.class);
}
