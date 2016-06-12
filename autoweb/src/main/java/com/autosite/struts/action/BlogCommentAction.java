package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.jtrend.util.JtStringUtil;
                    
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.BlogComment;
import com.autosite.ds.BlogCommentDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.BlogCommentForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class BlogCommentAction extends AutositeCoreAction {

    public BlogCommentAction(){
        m_ds = BlogCommentDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        BlogCommentForm _BlogCommentForm = (BlogCommentForm) form;
        HttpSession session = request.getSession();

        setPage(session, "blog_comment_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        
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


        BlogComment _BlogComment = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _BlogComment = m_ds.getById(cid);
        }


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //BlogComment _BlogComment = m_ds.getById(cid);

            if (_BlogComment == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogComment.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogComment.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _BlogCommentForm, _BlogComment);
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
            //BlogComment _BlogComment = m_ds.getById(cid);

            if (_BlogComment == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogComment.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogComment.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _BlogComment);
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
            //BlogComment _BlogComment = m_ds.getById(cid);

            if (_BlogComment == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_BlogComment.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlogComment.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _BlogComment);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_BlogComment);
            try { 
                m_actionExtent.afterDelete(request, response, _BlogComment);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new BlogComment" );
            BlogComment _BlogCommentNew = new BlogComment();   

            // Setting IDs for the object
            _BlogCommentNew.setSiteId(site.getId());

            _BlogCommentNew.setBlogMainId(WebParamUtil.getLongValue(_BlogCommentForm.getBlogMainId()));
            m_logger.debug("setting BlogMainId=" +_BlogCommentForm.getBlogMainId());
            _BlogCommentNew.setBlogPostId(WebParamUtil.getLongValue(_BlogCommentForm.getBlogPostId()));
            m_logger.debug("setting BlogPostId=" +_BlogCommentForm.getBlogPostId());
            _BlogCommentNew.setComment(WebParamUtil.getStringValue(_BlogCommentForm.getComment()));
            m_logger.debug("setting Comment=" +_BlogCommentForm.getComment());
            _BlogCommentNew.setRating(WebParamUtil.getIntValue(_BlogCommentForm.getRating()));
            m_logger.debug("setting Rating=" +_BlogCommentForm.getRating());
            _BlogCommentNew.setName(WebParamUtil.getStringValue(_BlogCommentForm.getName()));
            m_logger.debug("setting Name=" +_BlogCommentForm.getName());
            _BlogCommentNew.setPassword(WebParamUtil.getStringValue(_BlogCommentForm.getPassword()));
            m_logger.debug("setting Password=" +_BlogCommentForm.getPassword());
            _BlogCommentNew.setWebsite(WebParamUtil.getStringValue(_BlogCommentForm.getWebsite()));
            m_logger.debug("setting Website=" +_BlogCommentForm.getWebsite());
            _BlogCommentNew.setEmail(WebParamUtil.getStringValue(_BlogCommentForm.getEmail()));
            m_logger.debug("setting Email=" +_BlogCommentForm.getEmail());
            _BlogCommentNew.setTimeCreated(WebParamUtil.getDateValue(_BlogCommentForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_BlogCommentForm.getTimeCreated());

            try{
                m_actionExtent.beforeAdd(request, response, _BlogCommentNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_BlogCommentNew.skipPersist())
                m_logger.info("Skipping new object by skipPersist()");
            else                
                m_ds.put(_BlogCommentNew);
            try{
                m_actionExtent.afterAdd(request, response, _BlogCommentNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "blog_comment_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, BlogCommentForm _BlogCommentForm, BlogComment _BlogComment) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        BlogComment _BlogComment = m_ds.getById(cid);

        m_logger.debug("Before update " + BlogCommentDS.objectToString(_BlogComment));

        _BlogComment.setComment(WebParamUtil.getStringValue(_BlogCommentForm.getComment()));
        _BlogComment.setRating(WebParamUtil.getIntValue(_BlogCommentForm.getRating()));
        _BlogComment.setName(WebParamUtil.getStringValue(_BlogCommentForm.getName()));
        _BlogComment.setPassword(WebParamUtil.getStringValue(_BlogCommentForm.getPassword()));
        _BlogComment.setWebsite(WebParamUtil.getStringValue(_BlogCommentForm.getWebsite()));
        _BlogComment.setEmail(WebParamUtil.getStringValue(_BlogCommentForm.getEmail()));

        m_actionExtent.beforeUpdate(request, response, _BlogComment);
        m_ds.update(_BlogComment);
        m_actionExtent.afterUpdate(request, response, _BlogComment);
        m_logger.debug("After update " + BlogCommentDS.objectToString(_BlogComment));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, BlogComment _BlogComment) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        BlogComment _BlogComment = m_ds.getById(cid);

        if (!isMissing(request.getParameter("blogMainId"))) {
            m_logger.debug("updating param blogMainId from " +_BlogComment.getBlogMainId() + "->" + request.getParameter("blogMainId"));
            _BlogComment.setBlogMainId(WebParamUtil.getLongValue(request.getParameter("blogMainId")));
        }
        if (!isMissing(request.getParameter("blogPostId"))) {
            m_logger.debug("updating param blogPostId from " +_BlogComment.getBlogPostId() + "->" + request.getParameter("blogPostId"));
            _BlogComment.setBlogPostId(WebParamUtil.getLongValue(request.getParameter("blogPostId")));
        }
        if (!isMissing(request.getParameter("comment"))) {
            m_logger.debug("updating param comment from " +_BlogComment.getComment() + "->" + request.getParameter("comment"));
            _BlogComment.setComment(WebParamUtil.getStringValue(request.getParameter("comment")));
        }
        if (!isMissing(request.getParameter("rating"))) {
            m_logger.debug("updating param rating from " +_BlogComment.getRating() + "->" + request.getParameter("rating"));
            _BlogComment.setRating(WebParamUtil.getIntValue(request.getParameter("rating")));
        }
        if (!isMissing(request.getParameter("ipaddress"))) {
            m_logger.debug("updating param ipaddress from " +_BlogComment.getIpaddress() + "->" + request.getParameter("ipaddress"));
            _BlogComment.setIpaddress(WebParamUtil.getStringValue(request.getParameter("ipaddress")));
        }
        if (!isMissing(request.getParameter("name"))) {
            m_logger.debug("updating param name from " +_BlogComment.getName() + "->" + request.getParameter("name"));
            _BlogComment.setName(WebParamUtil.getStringValue(request.getParameter("name")));
        }
        if (!isMissing(request.getParameter("password"))) {
            m_logger.debug("updating param password from " +_BlogComment.getPassword() + "->" + request.getParameter("password"));
            _BlogComment.setPassword(WebParamUtil.getStringValue(request.getParameter("password")));
        }
        if (!isMissing(request.getParameter("website"))) {
            m_logger.debug("updating param website from " +_BlogComment.getWebsite() + "->" + request.getParameter("website"));
            _BlogComment.setWebsite(WebParamUtil.getStringValue(request.getParameter("website")));
        }
        if (!isMissing(request.getParameter("email"))) {
            m_logger.debug("updating param email from " +_BlogComment.getEmail() + "->" + request.getParameter("email"));
            _BlogComment.setEmail(WebParamUtil.getStringValue(request.getParameter("email")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_BlogComment.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _BlogComment.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _BlogComment);
        m_ds.update(_BlogComment);
        m_actionExtent.afterUpdate(request, response, _BlogComment);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, BlogComment _BlogComment) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        BlogComment _BlogComment = m_ds.getById(cid);

        if (!isMissing(request.getParameter("blogMainId"))) {
            return String.valueOf(_BlogComment.getBlogMainId());
        }
        if (!isMissing(request.getParameter("blogPostId"))) {
            return String.valueOf(_BlogComment.getBlogPostId());
        }
        if (!isMissing(request.getParameter("comment"))) {
            return String.valueOf(_BlogComment.getComment());
        }
        if (!isMissing(request.getParameter("rating"))) {
            return String.valueOf(_BlogComment.getRating());
        }
        if (!isMissing(request.getParameter("ipaddress"))) {
            return String.valueOf(_BlogComment.getIpaddress());
        }
        if (!isMissing(request.getParameter("name"))) {
            return String.valueOf(_BlogComment.getName());
        }
        if (!isMissing(request.getParameter("password"))) {
            return String.valueOf(_BlogComment.getPassword());
        }
        if (!isMissing(request.getParameter("website"))) {
            return String.valueOf(_BlogComment.getWebsite());
        }
        if (!isMissing(request.getParameter("email"))) {
            return String.valueOf(_BlogComment.getEmail());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            return String.valueOf(_BlogComment.getTimeCreated());
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
            BlogComment _BlogComment = BlogCommentDS.getInstance().getById(id);
            if (_BlogComment == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _BlogComment);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){

            // Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuffer buf = new StringBuffer();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/blogCommentAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("blogMainId")) {
                String value = WebUtil.display(request.getParameter("blogMainId"));

                if ( forceHiddenSet.contains("blogMainId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"blogMainId\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"blogMainId\"  value=\""+value+"\">");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("blogPostId")) {
                String value = WebUtil.display(request.getParameter("blogPostId"));

                if ( forceHiddenSet.contains("blogPostId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"blogPostId\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"blogPostId\"  value=\""+value+"\">");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("comment")) {
                String value = WebUtil.display(request.getParameter("comment"));

                if ( forceHiddenSet.contains("comment")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"comment\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Comment</div>");
            buf.append("<TEXTAREA NAME=\"comment\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("rating")) {
                String value = WebUtil.display(request.getParameter("rating"));

                if ( forceHiddenSet.contains("rating")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"rating\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Rating</div>");
            buf.append("<select id=\"requiredField\" name=\"rating\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("BlogCommentRatingOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("name")) {
                String value = WebUtil.display(request.getParameter("name"));

                if ( forceHiddenSet.contains("name")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"name\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Name</div>");
            buf.append("<INPUT NAME=\"name\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("password")) {
                String value = WebUtil.display(request.getParameter("password"));

                if ( forceHiddenSet.contains("password")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"password\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Password</div>");
            buf.append("<INPUT NAME=\"password\" type=\"password\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("website")) {
                String value = WebUtil.display(request.getParameter("website"));

                if ( forceHiddenSet.contains("website")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"website\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Website</div>");
            buf.append("<INPUT NAME=\"website\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("email")) {
                String value = WebUtil.display(request.getParameter("email"));

                if ( forceHiddenSet.contains("email")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"email\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Email</div>");
            buf.append("<INPUT NAME=\"email\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                String value = WebUtil.display(request.getParameter("timeCreated"));

                if ( forceHiddenSet.contains("timeCreated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCreated\"  value=\""+value+"\">");
                } else {

            buf.append("<br/>");
            }
            }

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxRequest\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ajaxOut\" value=\"getmodalstatus\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"wpid\" value=\""+ _wpId +"\">");
            buf.append("</form>");
            buf.append("<span id=\"ajaxSubmitResult\"></span>");
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/blogCommentAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into. 
            ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getlist")){

        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            BlogComment _BlogComment = null; 
            List list = BlogCommentDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _BlogComment = (BlogComment) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _BlogComment = (BlogComment) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _BlogComment = BlogCommentDS.getInstance().getById(id);
            }

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlisthtml");
            if (!returnList) {
                list = new ArrayList();
                list.add(_BlogComment);
            }


            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("comment")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Comment");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("rating")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Rating");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("name")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Name");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("password")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Password");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("website")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Website");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("email")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Email");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Time Created");
                buf.append("</td>");
            }
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                _BlogComment = (BlogComment) iterator.next();

                buf.append("<tr "+ trStyleStr +" >");

                if ( ignoreFieldSet || fieldSet.contains("comment")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(_BlogComment.getComment());
                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("rating")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(_BlogComment.getRating());
                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("name")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(_BlogComment.getName());
                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("password")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(_BlogComment.getPassword());
                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("website")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(_BlogComment.getWebsite());
                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("email")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(_BlogComment.getEmail());
                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(_BlogComment.getTimeCreated());
                    buf.append("</td>");
                }
                buf.append("</tr>");
            }
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            BlogComment _BlogComment = null; 
            List list = BlogCommentDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _BlogComment = (BlogComment) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _BlogComment = (BlogComment) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _BlogComment = BlogCommentDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last BlogComment=" + _BlogComment.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

                top.put("count", ""+list.size());
                JSONArray array = new JSONArray();

                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    _BlogComment = (BlogComment) iterator.next();

                    JSONObject json = new JSONObject();

                    // Fields
                    if ( ignoreFieldSet || fieldSet.contains("blogMainId")) 
                        json.put("blogMainId", ""+_BlogComment.getBlogMainId());
                    if ( ignoreFieldSet || fieldSet.contains("blogPostId")) 
                        json.put("blogPostId", ""+_BlogComment.getBlogPostId());
                    if ( ignoreFieldSet || fieldSet.contains("comment")) 
                        json.put("comment", ""+_BlogComment.getComment());
                    if ( ignoreFieldSet || fieldSet.contains("rating")) 
                        json.put("rating", ""+_BlogComment.getRating());
                    if ( ignoreFieldSet || fieldSet.contains("name")) 
                        json.put("name", ""+_BlogComment.getName());
                    if ( ignoreFieldSet || fieldSet.contains("password")) 
                        json.put("password", ""+_BlogComment.getPassword());
                    if ( ignoreFieldSet || fieldSet.contains("website")) 
                        json.put("website", ""+_BlogComment.getWebsite());
                    if ( ignoreFieldSet || fieldSet.contains("email")) 
                        json.put("email", ""+_BlogComment.getEmail());
                    array.put(json);
                }

                top.put("list", array);

            } else {

                top.put("id", ""+_BlogComment.getId());

                JSONArray array = new JSONArray();

                // Fields
                JSONObject jsonBlogMainId = new JSONObject();
                jsonBlogMainId.put("name", "blogMainId");
                jsonBlogMainId.put("value", ""+_BlogComment.getBlogMainId());
                array.put(jsonBlogMainId);

                JSONObject jsonBlogPostId = new JSONObject();
                jsonBlogPostId.put("name", "blogPostId");
                jsonBlogPostId.put("value", ""+_BlogComment.getBlogPostId());
                array.put(jsonBlogPostId);

                JSONObject jsonComment = new JSONObject();
                jsonComment.put("name", "comment");
                jsonComment.put("value", ""+_BlogComment.getComment());
                array.put(jsonComment);

                JSONObject jsonRating = new JSONObject();
                jsonRating.put("name", "rating");
                jsonRating.put("value", ""+_BlogComment.getRating());
                array.put(jsonRating);

                JSONObject jsonName = new JSONObject();
                jsonName.put("name", "name");
                jsonName.put("value", ""+_BlogComment.getName());
                array.put(jsonName);

                JSONObject jsonPassword = new JSONObject();
                jsonPassword.put("name", "password");
                jsonPassword.put("value", ""+_BlogComment.getPassword());
                array.put(jsonPassword);

                JSONObject jsonWebsite = new JSONObject();
                jsonWebsite.put("name", "website");
                jsonWebsite.put("value", ""+_BlogComment.getWebsite());
                array.put(jsonWebsite);

                JSONObject jsonEmail = new JSONObject();
                jsonEmail.put("name", "email");
                jsonEmail.put("value", ""+_BlogComment.getEmail());
                array.put(jsonEmail);


                top.put("fields", array);
            }


            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
            
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
        return false;
    }
    
    protected BlogCommentDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( BlogCommentAction.class);
}
