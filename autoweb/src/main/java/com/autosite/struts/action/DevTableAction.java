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

import com.autosite.db.DevTable;
import com.autosite.ds.DevTableDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;

import com.autosite.struts.form.DevTableForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check
import com.autosite.ds.DevNoteDS;
import com.autosite.db.DevNote;


public class DevTableAction extends AutositeCoreAction {

    public DevTableAction(){
        m_ds = DevTableDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        DevTableForm _DevTableForm = (DevTableForm) form;
        HttpSession session = request.getSession();

        setPage(session, "dev_table_home");

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


        DevTable _DevTable = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _DevTable = m_ds.getById(cid);
        }


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //DevTable _DevTable = m_ds.getById(cid);

            if (_DevTable == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_DevTable.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _DevTable.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_DevTable);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _DevTableForm, _DevTable);
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
            //DevTable _DevTable = m_ds.getById(cid);

            if (_DevTable == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_DevTable.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _DevTable.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _DevTable);
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
            //DevTable _DevTable = m_ds.getById(cid);

            if (_DevTable == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_DevTable.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _DevTable.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _DevTable);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_DevTable);
            try { 
                m_actionExtent.afterDelete(request, response, _DevTable);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new DevTable" );
            DevTable _DevTableNew = new DevTable();   

            // Setting IDs for the object
            _DevTableNew.setSiteId(site.getId());

            _DevTableNew.setDevNoteId(WebParamUtil.getLongValue(_DevTableForm.getDevNoteId()));
            m_logger.debug("setting DevNoteId=" +_DevTableForm.getDevNoteId());
            _DevTableNew.setTitle(WebParamUtil.getStringValue(_DevTableForm.getTitle()));
            m_logger.debug("setting Title=" +_DevTableForm.getTitle());
            _DevTableNew.setSubject(WebParamUtil.getStringValue(_DevTableForm.getSubject()));
            m_logger.debug("setting Subject=" +_DevTableForm.getSubject());
            _DevTableNew.setData(WebParamUtil.getStringValue(_DevTableForm.getData()));
            m_logger.debug("setting Data=" +_DevTableForm.getData());
            _DevTableNew.setType(WebParamUtil.getIntValue(_DevTableForm.getType()));
            m_logger.debug("setting Type=" +_DevTableForm.getType());
            _DevTableNew.setDisable(WebParamUtil.getIntValue(_DevTableForm.getDisable()));
            m_logger.debug("setting Disable=" +_DevTableForm.getDisable());
            _DevTableNew.setRadioValue(WebParamUtil.getIntValue(_DevTableForm.getRadioValue()));
            m_logger.debug("setting RadioValue=" +_DevTableForm.getRadioValue());
            _DevTableNew.setTimeCreated(WebParamUtil.getDateValue(_DevTableForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_DevTableForm.getTimeCreated());
            _DevTableNew.setTimeUpdated(WebParamUtil.getDateValue(_DevTableForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_DevTableForm.getTimeUpdated());

            try {
                checkDepedenceIntegrity(_DevTableNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _DevTableNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_DevTableNew.skipPersist())
                m_logger.info("Skipping new object by skipPersist()");
            else                
                m_ds.put(_DevTableNew);
            try{
                m_actionExtent.afterAdd(request, response, _DevTableNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "dev_table_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, DevTableForm _DevTableForm, DevTable _DevTable) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        DevTable _DevTable = m_ds.getById(cid);

        m_logger.debug("Before update " + DevTableDS.objectToString(_DevTable));

        _DevTable.setDevNoteId(WebParamUtil.getLongValue(_DevTableForm.getDevNoteId()));
        _DevTable.setTitle(WebParamUtil.getStringValue(_DevTableForm.getTitle()));
        _DevTable.setSubject(WebParamUtil.getStringValue(_DevTableForm.getSubject()));
        _DevTable.setData(WebParamUtil.getStringValue(_DevTableForm.getData()));
        _DevTable.setType(WebParamUtil.getIntValue(_DevTableForm.getType()));
        _DevTable.setDisable(WebParamUtil.getIntValue(_DevTableForm.getDisable()));
        _DevTable.setRadioValue(WebParamUtil.getIntValue(_DevTableForm.getRadioValue()));
        _DevTable.setTimeUpdated(WebParamUtil.getDateValue(_DevTableForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _DevTable);
        m_ds.update(_DevTable);
        m_actionExtent.afterUpdate(request, response, _DevTable);
        m_logger.debug("After update " + DevTableDS.objectToString(_DevTable));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, DevTable _DevTable) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        DevTable _DevTable = m_ds.getById(cid);

        if (!isMissing(request.getParameter("devNoteId"))) {
            m_logger.debug("updating param devNoteId from " +_DevTable.getDevNoteId() + "->" + request.getParameter("devNoteId"));
            _DevTable.setDevNoteId(WebParamUtil.getLongValue(request.getParameter("devNoteId")));
        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_DevTable.getTitle() + "->" + request.getParameter("title"));
            _DevTable.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));
        }
        if (!isMissing(request.getParameter("subject"))) {
            m_logger.debug("updating param subject from " +_DevTable.getSubject() + "->" + request.getParameter("subject"));
            _DevTable.setSubject(WebParamUtil.getStringValue(request.getParameter("subject")));
        }
        if (!isMissing(request.getParameter("data"))) {
            m_logger.debug("updating param data from " +_DevTable.getData() + "->" + request.getParameter("data"));
            _DevTable.setData(WebParamUtil.getStringValue(request.getParameter("data")));
        }
        if (!isMissing(request.getParameter("type"))) {
            m_logger.debug("updating param type from " +_DevTable.getType() + "->" + request.getParameter("type"));
            _DevTable.setType(WebParamUtil.getIntValue(request.getParameter("type")));
        }
        if (!isMissing(request.getParameter("disable"))) {
            m_logger.debug("updating param disable from " +_DevTable.getDisable() + "->" + request.getParameter("disable"));
            _DevTable.setDisable(WebParamUtil.getIntValue(request.getParameter("disable")));
        }
        if (!isMissing(request.getParameter("radioValue"))) {
            m_logger.debug("updating param radioValue from " +_DevTable.getRadioValue() + "->" + request.getParameter("radioValue"));
            _DevTable.setRadioValue(WebParamUtil.getIntValue(request.getParameter("radioValue")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_DevTable.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _DevTable.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_DevTable.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _DevTable.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _DevTable.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _DevTable);
        m_ds.update(_DevTable);
        m_actionExtent.afterUpdate(request, response, _DevTable);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, DevTable _DevTable) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        DevTable _DevTable = m_ds.getById(cid);

        if (!isMissing(request.getParameter("devNoteId"))) {
            return String.valueOf(_DevTable.getDevNoteId());
        }
        if (!isMissing(request.getParameter("title"))) {
            return String.valueOf(_DevTable.getTitle());
        }
        if (!isMissing(request.getParameter("subject"))) {
            return String.valueOf(_DevTable.getSubject());
        }
        if (!isMissing(request.getParameter("data"))) {
            return String.valueOf(_DevTable.getData());
        }
        if (!isMissing(request.getParameter("type"))) {
            return String.valueOf(_DevTable.getType());
        }
        if (!isMissing(request.getParameter("disable"))) {
            return String.valueOf(_DevTable.getDisable());
        }
        if (!isMissing(request.getParameter("radioValue"))) {
            return String.valueOf(_DevTable.getRadioValue());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            return String.valueOf(_DevTable.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            return String.valueOf(_DevTable.getTimeUpdated());
        }
        return null;
    }

    protected void checkDepedenceIntegrity(DevTable _DevTable) throws Exception {
        if (DevNoteDS.getInstance().getById(_DevTable.getDevNoteId()) == null){
            m_logger.warn("Received an invalid reference by DevNoteId. content Id=" +_DevTable.getDevNoteId() );
            throw new Exception("Internal Error Occurred.");
        }
    }

    protected String getFieldByName(String fieldName, DevTable _DevTable) {
        if (fieldName == null || fieldName.equals("")|| _DevTable == null) return null;
        
        if (fieldName.equals("devNoteId")) {
            return WebUtil.display(_DevTable.getDevNoteId());
        }
        if (fieldName.equals("title")) {
            return WebUtil.display(_DevTable.getTitle());
        }
        if (fieldName.equals("subject")) {
            return WebUtil.display(_DevTable.getSubject());
        }
        if (fieldName.equals("data")) {
            return WebUtil.display(_DevTable.getData());
        }
        if (fieldName.equals("type")) {
            return WebUtil.display(_DevTable.getType());
        }
        if (fieldName.equals("disable")) {
            return WebUtil.display(_DevTable.getDisable());
        }
        if (fieldName.equals("radioValue")) {
            return WebUtil.display(_DevTable.getRadioValue());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_DevTable.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_DevTable.getTimeUpdated());
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
            m_logger.debug("Ajax Processing gethtml getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            DevTable _DevTable = DevTableDS.getInstance().getById(id);
            if (_DevTable == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _DevTable);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            DevTable _DevTable = DevTableDS.getInstance().getById(id);
            if ( _DevTable == null) {
                ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _DevTable);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));

            // Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuffer buf = new StringBuffer();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/devTableAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("devNoteId")) {
                String value = WebUtil.display(request.getParameter("devNoteId"));

                if ( forceHiddenSet.contains("devNoteId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"devNoteId\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Dev Note Id</div>");
            buf.append("<select id=\"requiredField\" name=\"devNoteId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("DevTableDev Note IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("title")) {
                String value = WebUtil.display(request.getParameter("title"));

                if ( forceHiddenSet.contains("title")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"title\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Title</div>");
            buf.append("<INPUT NAME=\"title\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("subject")) {
                String value = WebUtil.display(request.getParameter("subject"));

                if ( forceHiddenSet.contains("subject")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subject\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subject</div>");
            buf.append("<INPUT NAME=\"subject\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("data")) {
                String value = WebUtil.display(request.getParameter("data"));

                if ( forceHiddenSet.contains("data")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"data\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Data</div>");
            buf.append("<TEXTAREA NAME=\"data\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("type")) {
                String value = WebUtil.display(request.getParameter("type"));

                if ( forceHiddenSet.contains("type")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"type\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Type</div>");
            buf.append("<select id=\"requiredField\" name=\"type\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            buf.append("<option value=\"1\" >Newyork</option>");
            buf.append("<option value=\"2\" >Ca</option>");
            buf.append("<option value=\"3\" >seela</option>");
            buf.append("<option value=\"4\" >Ocean</option>");
            buf.append("<option value=\"5\" >5</option>");

            buf.append("</select>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("disable")) {
                String value = WebUtil.display(request.getParameter("disable"));

                if ( forceHiddenSet.contains("disable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"disable\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Disable</div>");
            buf.append("<select name=\"disable\">");
            buf.append("<option value=\"0\" >No</option>");
            buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("radioValue")) {
                String value = WebUtil.display(request.getParameter("radioValue"));

                if ( forceHiddenSet.contains("radioValue")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"radioValue\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Radio Value</div>");
            buf.append("<INPUT NAME=\"radioValue\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                String value = WebUtil.display(request.getParameter("timeUpdated"));

                if ( forceHiddenSet.contains("timeUpdated")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeUpdated\"  value=\""+value+"\">");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/devTableAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2")){
            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
            StringBuffer buf = new StringBuffer();
            Site site = SiteDS.getInstance().registerSite(request.getServerName());

            String importedScripts = null;
            try {
                importedScripts = FileUtil.loadCodesToString("./inline_script.txt");
                m_logger.debug(importedScripts);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                importedScripts = "";
            }

            importedScripts += "\n";
            importedScripts += "function responseCallback(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayDevTable\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest(){\n";
            importedScripts +=     "xmlhttpPostXX('devTableFormAddDis','/devTableAction.html', 'resultDisplayDevTable', 'title,subject,data', responseCallback);\n";
            importedScripts += "}\n";

            buf.append(importedScripts);
            buf.append("\n");

            buf.append("document.write('");
            buf.append("<form name=\"devTableFormAddDis\" method=\"POST\" action=\"/devTableAction.html\" id=\"devTableFormAddDis\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

        buf.append("<div class=\"ajaxFormLabel\"> Dev Note Id</div>");
        buf.append("<select class=\"field\" name=\"devNoteId\" id=\"devNoteId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

    List _listDevNote_devNoteId = DevNoteDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = _listDevNote_devNoteId.iterator(); iter.hasNext();){
        DevNote _obj = (DevNote) iter.next();
        
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getNote() + " (" + _obj.getId() + ")</option>");
    } 
        buf.append("</select>  <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Title</div>");
        buf.append("<input class=\"requiredField\" id=\"title\" type=\"text\" size=\"70\" name=\"title\" value=\"\"/> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Subject</div>");
        buf.append("<input class=\"field\" id=\"subject\" type=\"text\" size=\"70\" name=\"subject\" value=\"\"/> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Data</div>");
        buf.append("<TEXTAREA id=\"data\" class=\"field\" NAME=\"data\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Type</div>");
        buf.append("<select class=\"requiredField\" name=\"type\" id=\"type\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<option value=\"1\" >Newyork</option>");
        buf.append("<option value=\"2\" >Ca</option>");
        buf.append("<option value=\"3\" >seela</option>");
        buf.append("<option value=\"4\" >Ocean</option>");
        buf.append("<option value=\"5\" >5</option>");
        buf.append("</select> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Disable</div>");
        buf.append("<select name=\"disable\" id=\"disable\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Radio Value</div>");
        buf.append("<input class=\"field\" id=\"radioValue\" type=\"text\" size=\"70\" name=\"radioValue\" value=\"\"/> <span></span>");
        buf.append("<br/>");


            buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearFormXX(\\'devTableFormAddDis\\')\">Clear Form</a><br>");
            buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
            buf.append("</form>");
            buf.append("<span id=\"resultDisplayDevTable\"></span>");
            buf.append("<a href=\"javascript:backToXX(\\'devTableFormAddDis\\',\\'resultDisplayDevTable\\')\">show back</a><br>");
            buf.append("');");

            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            m_logger.debug("Ajax Processing gethtml getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into. 
            ret.put("__value", "Successfully received.");
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();

            buf.append("<div id=\"devTable-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                DevTable _DevTable = (DevTable) iterator.next();

                buf.append("<div id=\"devTable-ajax-item\" "+listClass+">");

                if ( ignoreFieldSet || fieldSet.contains("title")) {
                    buf.append("<div id=\"devTable-ajax-title\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getTitle()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("subject")) {
                    buf.append("<div id=\"devTable-ajax-subject\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getSubject()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("data")) {
                    buf.append("<div id=\"devTable-ajax-data\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getData()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("type")) {
                    buf.append("<div id=\"devTable-ajax-type\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getType()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("disable")) {
                    buf.append("<div id=\"devTable-ajax-disable\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getDisable()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("radioValue")) {
                    buf.append("<div id=\"devTable-ajax-radioValue\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getRadioValue()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"devTable-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getTimeCreated()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"devTable-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevTable.getTimeUpdated()));
                    buf.append("</div>");

                }
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("title")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Title");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("subject")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Subject");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("data")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Data");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("type")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Type");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("disable")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Disable");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("radioValue")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Radio Value");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Time Created");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Time Updated");
                buf.append("</td>");
            }
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                DevTable _DevTable = (DevTable) iterator.next();

                buf.append("<tr "+ trStyleStr +" >");


                if ( ignoreFieldSet || fieldSet.contains("title")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevTable.getTitle()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("subject")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevTable.getSubject()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("data")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevTable.getData()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("type")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevTable.getType()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("disable")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    if (WebUtil.isTrue(_DevTable.getDisable()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/devTableAction.html?ef=true&id="+ _DevTable.getId()+"&disable=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
                    else
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/devTableAction.html?ef=true&id="+ _DevTable.getId()+"&disable=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("radioValue")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevTable.getRadioValue()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevTable.getTimeCreated()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_DevTable.getTimeUpdated()));

                    buf.append("</td>");
                }
                buf.append("</tr>");
            }
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            DevTable _DevTable = null; 
            List list = DevTableDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _DevTable = (DevTable) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _DevTable = (DevTable) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _DevTable = DevTableDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last DevTable=" + _DevTable.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

                top.put("count", ""+list.size());
                JSONArray array = new JSONArray();

                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    _DevTable = (DevTable) iterator.next();

                    JSONObject json = new JSONObject();

                    // Fields
                    if ( ignoreFieldSet || fieldSet.contains("devNoteId")) 
                        json.put("devNoteId", ""+_DevTable.getDevNoteId());
                    if ( ignoreFieldSet || fieldSet.contains("title")) 
                        json.put("title", ""+_DevTable.getTitle());
                    if ( ignoreFieldSet || fieldSet.contains("subject")) 
                        json.put("subject", ""+_DevTable.getSubject());
                    if ( ignoreFieldSet || fieldSet.contains("data")) 
                        json.put("data", ""+_DevTable.getData());
                    if ( ignoreFieldSet || fieldSet.contains("type")) 
                        json.put("type", ""+_DevTable.getType());
                    if ( ignoreFieldSet || fieldSet.contains("disable")) 
                        json.put("disable", ""+_DevTable.getDisable());
                    if ( ignoreFieldSet || fieldSet.contains("radioValue")) 
                        json.put("radioValue", ""+_DevTable.getRadioValue());
                    array.put(json);
                }

                top.put("list", array);

            } else {

                top.put("id", ""+_DevTable.getId());

                JSONArray array = new JSONArray();

                // Fields
                JSONObject jsonDevNoteId = new JSONObject();
                jsonDevNoteId.put("name", "devNoteId");
                jsonDevNoteId.put("value", ""+_DevTable.getDevNoteId());
                array.put(jsonDevNoteId);

                JSONObject jsonTitle = new JSONObject();
                jsonTitle.put("name", "title");
                jsonTitle.put("value", ""+_DevTable.getTitle());
                array.put(jsonTitle);

                JSONObject jsonSubject = new JSONObject();
                jsonSubject.put("name", "subject");
                jsonSubject.put("value", ""+_DevTable.getSubject());
                array.put(jsonSubject);

                JSONObject jsonData = new JSONObject();
                jsonData.put("name", "data");
                jsonData.put("value", ""+_DevTable.getData());
                array.put(jsonData);

                JSONObject jsonType = new JSONObject();
                jsonType.put("name", "type");
                jsonType.put("value", ""+_DevTable.getType());
                array.put(jsonType);

                JSONObject jsonDisable = new JSONObject();
                jsonDisable.put("name", "disable");
                jsonDisable.put("value", ""+_DevTable.getDisable());
                array.put(jsonDisable);

                JSONObject jsonRadioValue = new JSONObject();
                jsonRadioValue.put("name", "radioValue");
                jsonRadioValue.put("value", ""+_DevTable.getRadioValue());
                array.put(jsonRadioValue);


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


    // Prepares data to be returned in ajax.
    protected List prepareReturnData(HttpServletRequest request){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && request.getParameter("ajaxOut").startsWith("getlist");

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            DevTable _DevTable = null; 
            List list = DevTableDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _DevTable = (DevTable) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _DevTable = (DevTable) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _DevTable = DevTableDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_DevTable);

        } else {
            
            List list = DevTableDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected DevTableDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( DevTableAction.class);
}
