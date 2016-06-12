package com.autosite.struts.act;

import java.util.Date;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.BlogPost;
import com.autosite.ds.BlogPostDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;

import com.autosite.struts.form.BlogPostForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.SessionStoreUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class DefaulWebFlowStart extends AutositeCoreAction {

    public DefaulWebFlowStart(){
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        String webFlowKey = (String) request.getParameter("wf");

        //TODO First check the webFlow is login rquired. 
        SessionStoreUtil.createAndSave(session, webFlowKey, true);
        
        return mapping.findForward("default");
    }

    protected boolean loginRequired() {
        return true;
    }


    private static Logger m_logger = Logger.getLogger( DefaulWebFlowStart.class);
}
