package com.autosite.struts.action;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.ContentAd;
import com.autosite.ds.ContentAdDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.ContentAdForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class ContentAdAction extends AutositeCoreAction {

    public ContentAdAction(){
        m_ds = ContentAdDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        ContentAdForm _ContentAdForm = (ContentAdForm) form;
        HttpSession session = request.getSession();

        setPage(session, "content_ad_home");

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


        ContentAd _ContentAd = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ContentAd = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ContentAd _ContentAd = m_ds.getById(cid);

            if (_ContentAd == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentAd.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentAd.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _ContentAdForm, _ContentAd);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
            	if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ContentAd _ContentAd = m_ds.getById(cid);

            if (_ContentAd == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentAd.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentAd.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _ContentAd);
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
            //ContentAd _ContentAd = m_ds.getById(cid);

            if (_ContentAd == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentAd.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentAd.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _ContentAd);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_ContentAd);
            try { 
                m_actionExtent.afterDelete(request, response, _ContentAd);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {

            
            WebProcess webProc = null;
            try {
                webProc = checkWebProcess(request, session);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(), e);
	            if (throwException) throw e;
                return mapping.findForward("default");
            }

            m_logger.info("Creating new ContentAd" );
            ContentAd _ContentAdNew = new ContentAd();   

            // Setting IDs for the object
            _ContentAdNew.setSiteId(site.getId());

            _ContentAdNew.setContentId(WebParamUtil.getLongValue(_ContentAdForm.getContentId()));
            m_logger.debug("setting ContentId=" +_ContentAdForm.getContentId());
            _ContentAdNew.setPositionCode(WebParamUtil.getIntValue(_ContentAdForm.getPositionCode()));
            m_logger.debug("setting PositionCode=" +_ContentAdForm.getPositionCode());
            _ContentAdNew.setAdContent(WebParamUtil.getStringValue(_ContentAdForm.getAdContent()));
            m_logger.debug("setting AdContent=" +_ContentAdForm.getAdContent());
            _ContentAdNew.setHide(WebParamUtil.getIntValue(_ContentAdForm.getHide()));
            m_logger.debug("setting Hide=" +_ContentAdForm.getHide());
            _ContentAdNew.setTimeCreated(WebParamUtil.getDateValue(_ContentAdForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ContentAdForm.getTimeCreated());
            _ContentAdNew.setTimeUpdated(WebParamUtil.getDateValue(_ContentAdForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ContentAdForm.getTimeUpdated());

            try{
                m_actionExtent.beforeAdd(request, response, _ContentAdNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_ContentAdNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ContentAdNew);
            try{
                m_actionExtent.afterAdd(request, response, _ContentAdNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "content_ad_home");

            webProc.complete();
        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentAdForm _ContentAdForm, ContentAd _ContentAd) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentAd _ContentAd = m_ds.getById(cid);

        m_logger.debug("Before update " + ContentAdDS.objectToString(_ContentAd));

        _ContentAd.setContentId(WebParamUtil.getLongValue(_ContentAdForm.getContentId()));
        _ContentAd.setPositionCode(WebParamUtil.getIntValue(_ContentAdForm.getPositionCode()));
        _ContentAd.setAdContent(WebParamUtil.getStringValue(_ContentAdForm.getAdContent()));
        _ContentAd.setHide(WebParamUtil.getIntValue(_ContentAdForm.getHide()));
        _ContentAd.setTimeUpdated(WebParamUtil.getDateValue(_ContentAdForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _ContentAd);
        m_ds.update(_ContentAd);
        m_actionExtent.afterUpdate(request, response, _ContentAd);
        m_logger.debug("After update " + ContentAdDS.objectToString(_ContentAd));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ContentAd _ContentAd) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentAd _ContentAd = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentId"))) {
            m_logger.debug("updating param contentId from " +_ContentAd.getContentId() + "->" + request.getParameter("contentId"));
            _ContentAd.setContentId(WebParamUtil.getLongValue(request.getParameter("contentId")));
        }
        if (!isMissing(request.getParameter("positionCode"))) {
            m_logger.debug("updating param positionCode from " +_ContentAd.getPositionCode() + "->" + request.getParameter("positionCode"));
            _ContentAd.setPositionCode(WebParamUtil.getIntValue(request.getParameter("positionCode")));
        }
        if (!isMissing(request.getParameter("adContent"))) {
            m_logger.debug("updating param adContent from " +_ContentAd.getAdContent() + "->" + request.getParameter("adContent"));
            _ContentAd.setAdContent(WebParamUtil.getStringValue(request.getParameter("adContent")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_ContentAd.getHide() + "->" + request.getParameter("hide"));
            _ContentAd.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ContentAd.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ContentAd.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_ContentAd.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _ContentAd.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _ContentAd.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _ContentAd);
        m_ds.update(_ContentAd);
        m_actionExtent.afterUpdate(request, response, _ContentAd);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ContentAd _ContentAd) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentAd _ContentAd = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentId"))) {
			return String.valueOf(_ContentAd.getContentId());
        }
        if (!isMissing(request.getParameter("positionCode"))) {
			return String.valueOf(_ContentAd.getPositionCode());
        }
        if (!isMissing(request.getParameter("adContent"))) {
			return String.valueOf(_ContentAd.getAdContent());
        }
        if (!isMissing(request.getParameter("hide"))) {
			return String.valueOf(_ContentAd.getHide());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_ContentAd.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_ContentAd.getTimeUpdated());
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
            ContentAd _ContentAd = ContentAdDS.getInstance().getById(id);
            if (_ContentAd == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ContentAd);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            // Returns the form for modal form display
            StringBuffer buf = new StringBuffer();
            String _wpId = WebProcManager.registerWebProcess();

            int randNum = RandomUtil.randomInt(1000000);
            
            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/siteSuggestAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentId\" value=\"\">");

            buf.append("<INPUT NAME=\"positionCode\" type=\"text\" Size=\"50\" ></INPUT>");

            buf.append("<TEXTAREA NAME=\"adContent\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");

            buf.append("<select name=\"hide\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");



/*
            buf.append("<select id=\"requiredField\" name=\"category\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SiteSuggestCategoryOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
            buf.append("<TEXTAREA NAME=\"suggest\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
*/
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
            }
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected ContentAdDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentAdAction.class);
}
