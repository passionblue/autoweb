package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.AutositeUser;
import com.autosite.db.Site;
import com.autosite.db.SiteSuggest;
import com.autosite.ds.SiteDS;
import com.autosite.ds.SiteSuggestDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.SiteSuggestForm;
import com.autosite.util.RandomUtil;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.util.HtmlUtil;
import com.jtrend.util.TimeNow;
import com.jtrend.util.WebParamUtil;

public class SiteSuggestAction extends AutositeCoreAction {

    public SiteSuggestAction(){
        m_ds = SiteSuggestDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        SiteSuggestForm _SiteSuggestForm = (SiteSuggestForm) form;
        HttpSession session = request.getSession();

        setPage(session, "site_suggest_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        SiteSuggest _SiteSuggest = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _SiteSuggest = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //SiteSuggest _SiteSuggest = m_ds.getById(cid);

            if (_SiteSuggest == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SiteSuggest.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteSuggest.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _SiteSuggestForm, _SiteSuggest);
            }
            catch (Exception e) {
                if (throwException) throw e;
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //SiteSuggest _SiteSuggest = m_ds.getById(cid);

            if (_SiteSuggest == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SiteSuggest.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteSuggest.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _SiteSuggest);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //SiteSuggest _SiteSuggest = m_ds.getById(cid);

            if (_SiteSuggest == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_SiteSuggest.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _SiteSuggest.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _SiteSuggest);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                sessionErrorText(session, e.getMessage());
                if (throwException) throw e;
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_SiteSuggest);
            try { 
                m_actionExtent.afterDelete(request, response, _SiteSuggest);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new SiteSuggest" );
            SiteSuggest _SiteSuggestNew = new SiteSuggest();   

            // Setting IDs for the object
            _SiteSuggestNew.setSiteId(site.getId());

            _SiteSuggestNew.setCategory(WebParamUtil.getStringValue(_SiteSuggestForm.getCategory()));
            m_logger.debug("setting Category=" +_SiteSuggestForm.getCategory());
            _SiteSuggestNew.setSuggest(WebParamUtil.getStringValue(_SiteSuggestForm.getSuggest()));
            m_logger.debug("setting Suggest=" +_SiteSuggestForm.getSuggest());
            _SiteSuggestNew.setResolved(WebParamUtil.getIntValue(_SiteSuggestForm.getResolved()));
            m_logger.debug("setting Resolved=" +_SiteSuggestForm.getResolved());
            _SiteSuggestNew.setTimeCreated(WebParamUtil.getDateValue(_SiteSuggestForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_SiteSuggestForm.getTimeCreated());
            _SiteSuggestNew.setTimeUpdated(WebParamUtil.getDateValue(_SiteSuggestForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_SiteSuggestForm.getTimeUpdated());

            try{
                m_actionExtent.beforeAdd(request, response, _SiteSuggestNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_SiteSuggestNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_SiteSuggestNew);
            try{
                m_actionExtent.afterAdd(request, response, _SiteSuggestNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "site_suggest_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, SiteSuggestForm _SiteSuggestForm, SiteSuggest _SiteSuggest) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SiteSuggest _SiteSuggest = m_ds.getById(cid);

        m_logger.debug("Before update " + SiteSuggestDS.objectToString(_SiteSuggest));

        _SiteSuggest.setCategory(WebParamUtil.getStringValue(_SiteSuggestForm.getCategory()));
        _SiteSuggest.setSuggest(WebParamUtil.getStringValue(_SiteSuggestForm.getSuggest()));
        _SiteSuggest.setResolved(WebParamUtil.getIntValue(_SiteSuggestForm.getResolved()));
        _SiteSuggest.setTimeUpdated(WebParamUtil.getDateValue(_SiteSuggestForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _SiteSuggest);
        m_ds.update(_SiteSuggest);
        m_actionExtent.afterUpdate(request, response, _SiteSuggest);
        m_logger.debug("After update " + SiteSuggestDS.objectToString(_SiteSuggest));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, SiteSuggest _SiteSuggest) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SiteSuggest _SiteSuggest = m_ds.getById(cid);

        if (!isMissing(request.getParameter("category"))) {
            m_logger.debug("updating param category from " +_SiteSuggest.getCategory() + "->" + request.getParameter("category"));
            _SiteSuggest.setCategory(WebParamUtil.getStringValue(request.getParameter("category")));
        }
        if (!isMissing(request.getParameter("suggest"))) {
            m_logger.debug("updating param suggest from " +_SiteSuggest.getSuggest() + "->" + request.getParameter("suggest"));
            _SiteSuggest.setSuggest(WebParamUtil.getStringValue(request.getParameter("suggest")));
        }
        if (!isMissing(request.getParameter("resolved"))) {
            m_logger.debug("updating param resolved from " +_SiteSuggest.getResolved() + "->" + request.getParameter("resolved"));
            _SiteSuggest.setResolved(WebParamUtil.getIntValue(request.getParameter("resolved")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_SiteSuggest.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _SiteSuggest.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_SiteSuggest.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _SiteSuggest.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _SiteSuggest.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _SiteSuggest);
        m_ds.update(_SiteSuggest);
        m_actionExtent.afterUpdate(request, response, _SiteSuggest);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, SiteSuggest _SiteSuggest) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        SiteSuggest _SiteSuggest = m_ds.getById(cid);

        if (!isMissing(request.getParameter("category"))) {
			return String.valueOf(_SiteSuggest.getCategory());
        }
        if (!isMissing(request.getParameter("suggest"))) {
			return String.valueOf(_SiteSuggest.getSuggest());
        }
        if (!isMissing(request.getParameter("resolved"))) {
			return String.valueOf(_SiteSuggest.getResolved());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_SiteSuggest.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_SiteSuggest.getTimeUpdated());
        }
		return null;
    }


    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        
        //         // Request Processing 
        // 
        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                ex(mapping, form, request, response, true);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            SiteSuggest _SiteSuggest = SiteSuggestDS.getInstance().getById(id);
            if (_SiteSuggest == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _SiteSuggest);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            // Returns the form for modal form display
            StringBuffer buf = new StringBuffer();
            String _wpId = WebProcManager.registerWebProcess();

            int randNum = RandomUtil.randomInt(1000000);
            
            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/siteSuggestAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");

            buf.append("<select id=\"requiredField\" name=\"category\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SiteSuggestCategoryOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
            
            buf.append("<TEXTAREA NAME=\"suggest\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult\"></span>");
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/siteSuggestAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"javascript:document.siteSuggestFormEdit.submit();\">Cancel</a>");
            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into. 
            ret.put("__value", "Successfully received.");
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected SiteSuggestDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( SiteSuggestAction.class);
}
