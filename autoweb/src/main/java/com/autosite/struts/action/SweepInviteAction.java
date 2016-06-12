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
import com.autosite.db.Site;
import com.autosite.db.SweepUserConfig;
import com.autosite.ds.SiteDS;
import com.autosite.ds.SweepInvitationDS;
import com.autosite.ds.SweepPasswordDS;
import com.autosite.ds.SweepUserConfigDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.SweepPasswordForm;
import com.jtrend.util.WebUtil;

public class SweepInviteAction extends AutositeCoreAction {

    public SweepInviteAction(){
        m_ds = SweepPasswordDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SweepPasswordForm _SweepPasswordForm = (SweepPasswordForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        String email = request.getParameter("email");

        if ( WebUtil.isNull(email)){
            return mapping.findForward("default");
        }
        
        AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
        AutositeUser user = ctx.getUserObject();
        if (user == null || !ctx.isLogin() ){
            return mapping.findForward("default");
        }
        
        
        SweepUserConfig userConfig = SweepUserConfigDS.getInstance().getObjectByUserId(user.getId());
        

        List invites = SweepInvitationDS.getInstance().getByUserId(user.getId());
        
        
        
        
        
        return mapping.findForward("default");
    }


    protected boolean loginRequired() {
        return true;
    }

    protected SweepPasswordDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SweepInviteAction.class);
}
