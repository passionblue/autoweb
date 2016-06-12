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

import com.autosite.db.FormField;
import com.autosite.ds.FormFieldDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;

import com.autosite.struts.form.FormFieldForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


public class FormFieldAction extends AutositeCoreAction {

    public FormFieldAction(){
        m_ds = FormFieldDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        FormFieldForm _FormFieldForm = (FormFieldForm) form;
        HttpSession session = request.getSession();

        setPage(session, "form_field_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
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


        FormField _FormField = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _FormField = m_ds.getById(cid);
		}

		if (autoUser == null) {
            sessionErrorText(session, "Internal error occurred.");
            m_logger.warn("User object not set. ");
            return mapping.findForward("default");
        }

        if (!hasRequestValue(request, "add", "true")) {

            if ( autoUser.getUserType() != Constants.UserSuperAdmin && autoUser.getId() != _FormField.getUserId() ) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Access Exception. Delete failed. Invalid user has attempted to delete for user " + _FormField.getUserId());
                return mapping.findForward("default");
            }
		}

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //FormField _FormField = m_ds.getById(cid);

            if (_FormField == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_FormField.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _FormField.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                checkDepedenceIntegrity(_FormField);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _FormFieldForm, _FormField);
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
            //FormField _FormField = m_ds.getById(cid);

            if (_FormField == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_FormField.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _FormField.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _FormField);
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
            //FormField _FormField = m_ds.getById(cid);

            if (_FormField == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_FormField.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _FormField.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _FormField);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_FormField);
            try { 
                m_actionExtent.afterDelete(request, response, _FormField);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new FormField" );
            FormField _FormFieldNew = new FormField();   

            // Setting IDs for the object
            _FormFieldNew.setSiteId(site.getId());

            AutositeSessionContext ctx = (AutositeSessionContext)getSessionContext(session);
            AutositeUser user = ctx.getUserObject();
            if (user != null && ctx.isLogin() ){
                m_logger.debug("Setting user id for this object to " + user.getId());
                _FormFieldNew.setUserId(user.getId());
            }

            _FormFieldNew.setFormId(WebParamUtil.getLongValue(_FormFieldForm.getFormId()));
            m_logger.debug("setting FormId=" +_FormFieldForm.getFormId());
            _FormFieldNew.setFieldText(WebParamUtil.getStringValue(_FormFieldForm.getFieldText()));
            m_logger.debug("setting FieldText=" +_FormFieldForm.getFieldText());
            _FormFieldNew.setFieldType(WebParamUtil.getIntValue(_FormFieldForm.getFieldType()));
            m_logger.debug("setting FieldType=" +_FormFieldForm.getFieldType());
            _FormFieldNew.setRequired(WebParamUtil.getIntValue(_FormFieldForm.getRequired()));
            m_logger.debug("setting Required=" +_FormFieldForm.getRequired());
            _FormFieldNew.setTimeCreated(WebParamUtil.getDateValue(_FormFieldForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_FormFieldForm.getTimeCreated());

            try {
                checkDepedenceIntegrity(_FormFieldNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _FormFieldNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_FormFieldNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_FormFieldNew);
            try{
                m_actionExtent.afterAdd(request, response, _FormFieldNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "form_field_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, FormFieldForm _FormFieldForm, FormField _FormField) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        FormField _FormField = m_ds.getById(cid);

        m_logger.debug("Before update " + FormFieldDS.objectToString(_FormField));

        _FormField.setFormId(WebParamUtil.getLongValue(_FormFieldForm.getFormId()));
        _FormField.setFieldText(WebParamUtil.getStringValue(_FormFieldForm.getFieldText()));
        _FormField.setFieldType(WebParamUtil.getIntValue(_FormFieldForm.getFieldType()));
        _FormField.setRequired(WebParamUtil.getIntValue(_FormFieldForm.getRequired()));

        m_actionExtent.beforeUpdate(request, response, _FormField);
        m_ds.update(_FormField);
        m_actionExtent.afterUpdate(request, response, _FormField);
        m_logger.debug("After update " + FormFieldDS.objectToString(_FormField));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, FormField _FormField) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        FormField _FormField = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_FormField.getUserId() + "->" + request.getParameter("userId"));
            _FormField.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
        }
        if (!isMissing(request.getParameter("formId"))) {
            m_logger.debug("updating param formId from " +_FormField.getFormId() + "->" + request.getParameter("formId"));
            _FormField.setFormId(WebParamUtil.getLongValue(request.getParameter("formId")));
        }
        if (!isMissing(request.getParameter("fieldText"))) {
            m_logger.debug("updating param fieldText from " +_FormField.getFieldText() + "->" + request.getParameter("fieldText"));
            _FormField.setFieldText(WebParamUtil.getStringValue(request.getParameter("fieldText")));
        }
        if (!isMissing(request.getParameter("fieldType"))) {
            m_logger.debug("updating param fieldType from " +_FormField.getFieldType() + "->" + request.getParameter("fieldType"));
            _FormField.setFieldType(WebParamUtil.getIntValue(request.getParameter("fieldType")));
        }
        if (!isMissing(request.getParameter("required"))) {
            m_logger.debug("updating param required from " +_FormField.getRequired() + "->" + request.getParameter("required"));
            _FormField.setRequired(WebParamUtil.getIntValue(request.getParameter("required")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_FormField.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _FormField.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _FormField);
        m_ds.update(_FormField);
        m_actionExtent.afterUpdate(request, response, _FormField);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, FormField _FormField) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        FormField _FormField = m_ds.getById(cid);

        if (!isMissing(request.getParameter("userId"))) {
			return String.valueOf(_FormField.getUserId());
        }
        if (!isMissing(request.getParameter("formId"))) {
			return String.valueOf(_FormField.getFormId());
        }
        if (!isMissing(request.getParameter("fieldText"))) {
			return String.valueOf(_FormField.getFieldText());
        }
        if (!isMissing(request.getParameter("fieldType"))) {
			return String.valueOf(_FormField.getFieldType());
        }
        if (!isMissing(request.getParameter("required"))) {
			return String.valueOf(_FormField.getRequired());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_FormField.getTimeCreated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(FormField _FormField) throws Exception {
    }

    protected String getFieldByName(String fieldName, FormField _FormField) {
        if (fieldName == null || fieldName.equals("")|| _FormField == null) return null;
        
        if (fieldName.equals("userId")) {
            return WebUtil.display(_FormField.getUserId());
        }
        if (fieldName.equals("formId")) {
            return WebUtil.display(_FormField.getFormId());
        }
        if (fieldName.equals("fieldText")) {
            return WebUtil.display(_FormField.getFieldText());
        }
        if (fieldName.equals("fieldType")) {
            return WebUtil.display(_FormField.getFieldType());
        }
        if (fieldName.equals("required")) {
            return WebUtil.display(_FormField.getRequired());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_FormField.getTimeCreated());
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
            FormField _FormField = FormFieldDS.getInstance().getById(id);
            if (_FormField == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _FormField);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            FormField _FormField = FormFieldDS.getInstance().getById(id);
            if ( _FormField == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _FormField);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/formFieldAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("formId")) {
                String value = WebUtil.display(request.getParameter("formId"));

                if ( forceHiddenSet.contains("formId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"formId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Form Id</div>");
            buf.append("<INPUT NAME=\"formId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fieldText")) {
                String value = WebUtil.display(request.getParameter("fieldText"));

                if ( forceHiddenSet.contains("fieldText")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fieldText\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Field Text</div>");
            buf.append("<INPUT NAME=\"fieldText\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fieldType")) {
                String value = WebUtil.display(request.getParameter("fieldType"));

                if ( forceHiddenSet.contains("fieldType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fieldType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Field Type</div>");
            buf.append("<select id=\"requiredField\" name=\"fieldType\">");
            buf.append("<option value=\"\" >- Please Select -</option>");


            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("required")) {
                String value = WebUtil.display(request.getParameter("required"));

                if ( forceHiddenSet.contains("required")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"required\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Required</div>");
            buf.append("<select name=\"required\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/formFieldAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
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
            importedScripts += "    document.getElementById(\"resultDisplayFormField\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest(){\n";
            importedScripts +=     "xmlhttpPostXX('formFieldFormAddDis','/formFieldAction.html', 'resultDisplayFormField', '${ajax_response_fields}', responseCallback);\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"formFieldFormAddDis\" method=\"POST\" action=\"/formFieldAction.html\" id=\"formFieldFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Form Id</div>");
        buf.append("<input class=\"field\" id=\"formId\" type=\"text\" size=\"70\" name=\"formId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Field Text</div>");
        buf.append("<input class=\"requiredField\" id=\"fieldText\" type=\"text\" size=\"70\" name=\"fieldText\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Field Type</div>");
        buf.append("<select class=\"field\" name=\"fieldType\" id=\"fieldType\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<!--option value=\"XX\" >XX</option-->");
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Required</div>");
        buf.append("<select name=\"required\" id=\"required\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest()\">Submit</a><br>");
			buf.append("<a href=\"javascript:clearFormXX(\\'formFieldFormAddDis\\')\">Clear Form</a><br>");
		    buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayFormField\"></span>");
			buf.append("<a href=\"javascript:backToXX(\\'formFieldFormAddDis\\',\\'resultDisplayFormField\\')\">show back</a><br>");
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

            buf.append("<div id=\"formField-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                FormField _FormField = (FormField) iterator.next();

                buf.append("<div id=\"formField-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("formId")) {
                    buf.append("<div id=\"formField-ajax-formId\" "+itemClass+">");
                    buf.append(WebUtil.display(_FormField.getFormId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fieldText")) {
                    buf.append("<div id=\"formField-ajax-fieldText\" "+itemClass+">");
                    buf.append(WebUtil.display(_FormField.getFieldText()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fieldType")) {
                    buf.append("<div id=\"formField-ajax-fieldType\" "+itemClass+">");
                    buf.append(WebUtil.display(_FormField.getFieldType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("required")) {
                    buf.append("<div id=\"formField-ajax-required\" "+itemClass+">");
                    buf.append(WebUtil.display(_FormField.getRequired()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"formField-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_FormField.getTimeCreated()));
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
            if ( ignoreFieldSet || fieldSet.contains("formId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Form Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fieldText")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Field Text");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fieldType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Field Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("required")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Required");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                FormField _FormField = (FormField) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("formId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_FormField.getFormId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fieldText")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_FormField.getFieldText()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fieldType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_FormField.getFieldType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("required")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_FormField.getRequired()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/formFieldAction.html?ef=true&id="+ _FormField.getId()+"&required=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/formFieldAction.html?ef=true&id="+ _FormField.getId()+"&required=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_FormField.getTimeCreated()));

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
            FormField _FormField = null; 
            List list = FormFieldDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _FormField = (FormField) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _FormField = (FormField) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _FormField = FormFieldDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last FormField=" + _FormField.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _FormField = (FormField) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("formId")) 
			            json.put("formId", ""+_FormField.getFormId());
		            if ( ignoreFieldSet || fieldSet.contains("fieldText")) 
			            json.put("fieldText", ""+_FormField.getFieldText());
		            if ( ignoreFieldSet || fieldSet.contains("fieldType")) 
			            json.put("fieldType", ""+_FormField.getFieldType());
		            if ( ignoreFieldSet || fieldSet.contains("required")) 
			            json.put("required", ""+_FormField.getRequired());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_FormField.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonFormId = new JSONObject();
	            jsonFormId.put("name", "formId");
	            jsonFormId.put("value", ""+_FormField.getFormId());
	            array.put(jsonFormId);

	            JSONObject jsonFieldText = new JSONObject();
	            jsonFieldText.put("name", "fieldText");
	            jsonFieldText.put("value", ""+_FormField.getFieldText());
	            array.put(jsonFieldText);

	            JSONObject jsonFieldType = new JSONObject();
	            jsonFieldType.put("name", "fieldType");
	            jsonFieldType.put("value", ""+_FormField.getFieldType());
	            array.put(jsonFieldType);

	            JSONObject jsonRequired = new JSONObject();
	            jsonRequired.put("name", "required");
	            jsonRequired.put("value", ""+_FormField.getRequired());
	            array.put(jsonRequired);


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
            FormField _FormField = null; 
            List list = FormFieldDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _FormField = (FormField) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _FormField = (FormField) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _FormField = FormFieldDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_FormField);

        } else {
            
            List list = FormFieldDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected FormFieldDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( FormFieldAction.class);
}
