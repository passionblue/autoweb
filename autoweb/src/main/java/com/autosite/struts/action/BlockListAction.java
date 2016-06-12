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

import com.autosite.db.BlockList;
import com.autosite.ds.BlockListDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;

import com.autosite.struts.form.BlockListForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check




public class BlockListAction extends AutositeCoreAction {

    public BlockListAction(){
        m_ds = BlockListDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }




    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        return ex(mapping, form, request, response, throwException, null);
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        BlockListForm _BlockListForm = (BlockListForm) form;
        HttpSession session = request.getSession();

        setPage(session, getDefaultPage());

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            //Default error page but will be overridden by exception specific error page
            setPage(session, getDefaultPage());
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser            autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        if (context == null || !context.isSiteAdmin()) accessTestOkay = false;
        if (context == null || !context.isSuperAdmin()) accessTestOkay = false;
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            setPage(session, getErrorPage());
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");
            //Default error page but will be overridden by exception specific error page
            setPage(session, getErrorPage());
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        BlockList _BlockList = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _BlockList = m_ds.getById(cid);
        }


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //BlockList _BlockList = m_ds.getById(cid);

            if (_BlockList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_BlockList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlockList.getSiteId()); 
                //Default error page but will be overridden by exception specific error page
                setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_BlockList);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _BlockListForm, _BlockList);
                if (returnObjects != null) returnObjects.put("target", _BlockList);
                setPage(session, getAfterEditPage());
            }

            catch (Exception e) {
                m_logger.error("Error occurred", e);
                if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                //Default error page but will be overridden by exception specific error page
                setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            //Default error page but will be overridden by exception specific error page
            setPage(session, getAfterEditPage());
            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //BlockList _BlockList = m_ds.getById(cid);

            if (_BlockList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
            //Default error page but will be overridden by exception specific error page
            setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_BlockList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlockList.getSiteId()); 
                //Default error page but will be overridden by exception specific error page
                setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _BlockList);
                if (returnObjects != null) returnObjects.put("target", _BlockList);
                setPage(session, getAfterEditFieldPage());
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");
            //Default error page but will be overridden by exception specific error page
            setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            setPage(session, getAfterEditFieldPage());
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //BlockList _BlockList = m_ds.getById(cid);

            if (_BlockList == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                //Default error page but will be overridden by exception specific error page
                setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            if (_BlockList.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _BlockList.getSiteId()); 
                setPage(session, getErrorPage());
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _BlockList);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                //Default error page but will be overridden by exception specific error page
                setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            m_ds.delete(_BlockList); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", _BlockList);
            setPageForward(session, getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, _BlockList);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                //Default error page but will be overridden by exception specific error page
                setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            setPage(session, getAfterDeletePage());
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new BlockList" );
            BlockList _BlockListNew = new BlockList();   

            // Setting IDs for the object
            _BlockListNew.setSiteId(site.getId());

            _BlockListNew.setIpData(WebParamUtil.getStringValue(_BlockListForm.getIpData()));
            m_logger.debug("setting IpData=" +_BlockListForm.getIpData());
            _BlockListNew.setRangeCheck(WebParamUtil.getIntValue(_BlockListForm.getRangeCheck()));
            m_logger.debug("setting RangeCheck=" +_BlockListForm.getRangeCheck());
            _BlockListNew.setReasonCode(WebParamUtil.getIntValue(_BlockListForm.getReasonCode()));
            m_logger.debug("setting ReasonCode=" +_BlockListForm.getReasonCode());
            _BlockListNew.setTimeCreated(WebParamUtil.getDateValue(_BlockListForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_BlockListForm.getTimeCreated());

            try {
                checkDepedenceIntegrity(_BlockListNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _BlockListNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_BlockListNew.skipPersist())
                m_logger.info("Skipping new object by skipPersist()");
            else                
                m_ds.put(_BlockListNew);
            if (returnObjects != null) returnObjects.put("target", _BlockList);
            setPage(session, getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _BlockListNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                setPage(session, getErrorPage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "block_list_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            setPage(session, getErrorPage());
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, BlockListForm _BlockListForm, BlockList _BlockList) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        BlockList _BlockList = m_ds.getById(cid);

        m_logger.debug("Before update " + BlockListDS.objectToString(_BlockList));

        _BlockList.setIpData(WebParamUtil.getStringValue(_BlockListForm.getIpData()));
        _BlockList.setRangeCheck(WebParamUtil.getIntValue(_BlockListForm.getRangeCheck()));
        _BlockList.setReasonCode(WebParamUtil.getIntValue(_BlockListForm.getReasonCode()));

        m_actionExtent.beforeUpdate(request, response, _BlockList);
        m_ds.update(_BlockList);
        m_actionExtent.afterUpdate(request, response, _BlockList);
        m_logger.debug("After update " + BlockListDS.objectToString(_BlockList));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, BlockList _BlockList) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        BlockList _BlockList = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ipData"))) {
            m_logger.debug("updating param ipData from " +_BlockList.getIpData() + "->" + request.getParameter("ipData"));
            _BlockList.setIpData(WebParamUtil.getStringValue(request.getParameter("ipData")));
        }
        if (!isMissing(request.getParameter("rangeCheck"))) {
            m_logger.debug("updating param rangeCheck from " +_BlockList.getRangeCheck() + "->" + request.getParameter("rangeCheck"));
            _BlockList.setRangeCheck(WebParamUtil.getIntValue(request.getParameter("rangeCheck")));
        }
        if (!isMissing(request.getParameter("reasonCode"))) {
            m_logger.debug("updating param reasonCode from " +_BlockList.getReasonCode() + "->" + request.getParameter("reasonCode"));
            _BlockList.setReasonCode(WebParamUtil.getIntValue(request.getParameter("reasonCode")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_BlockList.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _BlockList.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _BlockList);
        m_ds.update(_BlockList);
        m_actionExtent.afterUpdate(request, response, _BlockList);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, BlockList _BlockList) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        BlockList _BlockList = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ipData"))) {
            return String.valueOf(_BlockList.getIpData());
        }
        if (!isMissing(request.getParameter("rangeCheck"))) {
            return String.valueOf(_BlockList.getRangeCheck());
        }
        if (!isMissing(request.getParameter("reasonCode"))) {
            return String.valueOf(_BlockList.getReasonCode());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            return String.valueOf(_BlockList.getTimeCreated());
        }
        return null;
    }

    protected void checkDepedenceIntegrity(BlockList _BlockList) throws Exception {
    }

    protected String getFieldByName(String fieldName, BlockList _BlockList) {
        if (fieldName == null || fieldName.equals("")|| _BlockList == null) return null;
        
        if (fieldName.equals("ipData")) {
            return WebUtil.display(_BlockList.getIpData());
        }
        if (fieldName.equals("rangeCheck")) {
            return WebUtil.display(_BlockList.getRangeCheck());
        }
        if (fieldName.equals("reasonCode")) {
            return WebUtil.display(_BlockList.getReasonCode());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_BlockList.getTimeCreated());
        }
        return null;
    }



    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        BlockList target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (BlockList) working.get("target");
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        // ================================================================================
        // Response Processing 
        // ================================================================================

        if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // If there is no error, following message will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") )
                ret.put("__value", "Successfully deleted.");
            if (hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") )
                ret.put("__value", "Successfully created.");
            if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef"))     
                ret.put("__value", "Successfully changed.");
            if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") )
                ret.put("__value", "Successfully changed.");
            else 
                ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getcode")){
            // If there is no error, nothing will be returned
            // if there is an error, error message will be returned
            
            m_logger.debug("Ajax Processing getstatus arg = " + request.getParameter("ajaxOutArg"));

        } else if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            BlockList _BlockList = BlockListDS.getInstance().getById(id);
            if (_BlockList == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _BlockList);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            BlockList _BlockList = BlockListDS.getInstance().getById(id);
            if ( _BlockList == null) {
                ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _BlockList);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/blockListAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("ipData")) {
                String value = WebUtil.display(request.getParameter("ipData"));

                if ( forceHiddenSet.contains("ipData")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ipData\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ip Data</div>");
            buf.append("<INPUT NAME=\"ipData\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("rangeCheck")) {
                String value = WebUtil.display(request.getParameter("rangeCheck"));

                if ( forceHiddenSet.contains("rangeCheck")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"rangeCheck\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Range Check</div>");
            buf.append("<select name=\"rangeCheck\">");
            buf.append("<option value=\"0\" >No</option>");
            buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
            buf.append("<br/>");
            }
            }

            if ( ignoreFieldSet || fieldSet.contains("reasonCode")) {
                String value = WebUtil.display(request.getParameter("reasonCode"));

                if ( forceHiddenSet.contains("reasonCode")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"reasonCode\"  value=\""+value+"\">");
                } else {

            buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Reason Code</div>");
            buf.append("<select id=\"requiredField\" name=\"reasonCode\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            buf.append("<option value=\"1\" >SPAM-AutoBlock</option>");
            buf.append("<option value=\"2\" >SPAM-ManualBlock</option>");
            buf.append("<option value=\"3\" >Attack-AutoBlock</option>");
            buf.append("<option value=\"99\" >Other</option>");

            buf.append("</select>");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/blockListAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2") || (hasRequestValue(request, "ajaxOut", "getscriptform"))){
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
            importedScripts += "function responseCallback_blockList(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayBlockList\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_blockList(){\n";
            importedScripts +=     "xmlhttpPostXX('blockListFormAddDis','/blockListAction.html', 'resultDisplayBlockList', '${ajax_response_fields}', responseCallback_blockList);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_blockList(){\n";
            importedScripts +=     "clearFormXX('blockListFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_blockList(){\n";
            importedScripts +=     "backToXX('blockListFormAddDis','resultDisplayBlockList');\n";
            importedScripts += "}\n";

            buf.append(importedScripts);
            buf.append("\n");

            buf.append("document.write('");
            buf.append("<form name=\"blockListFormAddDis\" method=\"POST\" action=\"/blockListAction.html\" id=\"blockListFormAddDis\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

        buf.append("<div class=\"ajaxFormLabel\"> Ip Data</div>");
        buf.append("<input class=\"field\" id=\"ipData\" type=\"text\" size=\"70\" name=\"ipData\" value=\"\"/> <span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Range Check</div>");
        buf.append("<select name=\"rangeCheck\" id=\"rangeCheck\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
        buf.append("<br/>");
        buf.append("<div class=\"ajaxFormLabel\"> Reason Code</div>");
        buf.append("<select class=\"field\" name=\"reasonCode\" id=\"reasonCode\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<!--option value=\"XX\" >XX</option-->");
        buf.append("<option value=\"1\" >SPAM-AutoBlock</option>");
        buf.append("<option value=\"2\" >SPAM-ManualBlock</option>");
        buf.append("<option value=\"3\" >Attack-AutoBlock</option>");
        buf.append("<option value=\"99\" >Other</option>");
        buf.append("</select>  <span></span>");
        buf.append("<br/>");


            buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_blockList()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearForm_blockList()\">Clear Form</a><br>");
            buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
            buf.append("</form>");
            buf.append("<span id=\"resultDisplayBlockList\"></span>");
            buf.append("<a href=\"javascript:showform_blockList()\">show form</a><br>");
            buf.append("');");

            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();

            buf.append("<div id=\"blockList-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                BlockList _BlockList = (BlockList) iterator.next();

                buf.append("<div id=\"blockList-ajax-item\" "+listClass+">");

                if ( ignoreFieldSet || fieldSet.contains("ipData")) {
                    buf.append("<div id=\"blockList-ajax-ipData\" "+itemClass+">");
                    buf.append(WebUtil.display(_BlockList.getIpData()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("rangeCheck")) {
                    buf.append("<div id=\"blockList-ajax-rangeCheck\" "+itemClass+">");
                    buf.append(WebUtil.display(_BlockList.getRangeCheck()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("reasonCode")) {
                    buf.append("<div id=\"blockList-ajax-reasonCode\" "+itemClass+">");
                    buf.append(WebUtil.display(_BlockList.getReasonCode()));
                    buf.append("</div>");

                }
                if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"blockList-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_BlockList.getTimeCreated()));
                    buf.append("</div>");

                }
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

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
            if ( ignoreFieldSet || fieldSet.contains("ipData")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Ip Data");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("rangeCheck")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Range Check");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("reasonCode")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Reason Code");
                buf.append("</td>");
            }
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                buf.append("<td "+ tdBoldStyleStr +" >");
                buf.append("Time Created");
                buf.append("</td>");
            }
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                BlockList _BlockList = (BlockList) iterator.next();

                buf.append("<tr "+ trStyleStr +" >");


                if ( ignoreFieldSet || fieldSet.contains("ipData")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_BlockList.getIpData()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("rangeCheck")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    if (WebUtil.isTrue(_BlockList.getRangeCheck()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/blockListAction.html?ef=true&id="+ _BlockList.getId()+"&rangeCheck=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
                    else
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/blockListAction.html?ef=true&id="+ _BlockList.getId()+"&rangeCheck=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("reasonCode")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_BlockList.getReasonCode()));

                    buf.append("</td>");
                }

                if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(_BlockList.getTimeCreated()));

                    buf.append("</td>");
                }
                buf.append("</tr>");
            }
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            if (returnList) {

                top.put("count", ""+list.size());
                JSONArray array = new JSONArray();

                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    BlockList _BlockList = (BlockList) iterator.next();

                    JSONObject json = new JSONObject();

                    // Fields
                    if ( ignoreFieldSet || fieldSet.contains("ipData")) 
                        json.put("ipData", ""+_BlockList.getIpData());
                    if ( ignoreFieldSet || fieldSet.contains("rangeCheck")) 
                        json.put("rangeCheck", ""+_BlockList.getRangeCheck());
                    if ( ignoreFieldSet || fieldSet.contains("reasonCode")) 
                        json.put("reasonCode", ""+_BlockList.getReasonCode());
                    array.put(json);
                }

                top.put("list", array);

            } else {

                BlockList _BlockList = list.size() >=1?(BlockList) list.get(0): null; 

                if ( _BlockList != null) {
                    top.put("id", ""+_BlockList.getId());
                    JSONArray array = new JSONArray();

                    // Fields
                    JSONObject jsonIpData = new JSONObject();
                    jsonIpData.put("name", "ipData");
                    jsonIpData.put("value", ""+_BlockList.getIpData());
                    array.put(jsonIpData);
                    JSONObject jsonRangeCheck = new JSONObject();
                    jsonRangeCheck.put("name", "rangeCheck");
                    jsonRangeCheck.put("value", ""+_BlockList.getRangeCheck());
                    array.put(jsonRangeCheck);
                    JSONObject jsonReasonCode = new JSONObject();
                    jsonReasonCode.put("name", "reasonCode");
                    jsonReasonCode.put("value", ""+_BlockList.getReasonCode());
                    array.put(jsonReasonCode);

                    top.put("fields", array);
                }
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
    protected List prepareReturnData(HttpServletRequest request, BlockList target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            BlockList _BlockList = null; 
            List list = BlockListDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _BlockList = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _BlockList = (BlockList) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _BlockList = (BlockList) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _BlockList = BlockListDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_BlockList);

        } else {
            
            List list = BlockListDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }

    //  // Configuration Option
    //
    protected String getErrorPage(){return m_actionExtent.getErrorPage();}
    protected String getWarningPage(){return m_actionExtent.getWarningPage();}
    protected String getAfterAddPage(){return m_actionExtent.getAfterAddPage();}
    protected String getAfterEditPage(){return m_actionExtent.getAfterEditPage();}
    protected String getAfterEditFieldPage(){return m_actionExtent.getAfterEditFieldPage();}
    protected String getAfterDeletePage(){return m_actionExtent.getAfterDeletePage();}
    protected String getDefaultPage(){return m_actionExtent.getDefaultPage();}

    
    protected BlockListDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( BlockListAction.class);





}
