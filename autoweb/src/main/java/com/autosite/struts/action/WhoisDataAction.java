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

import com.autosite.db.WhoisData;
import com.autosite.ds.WhoisDataDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.WhoisDataForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class WhoisDataAction extends AutositeCoreAction {

    public WhoisDataAction(){
        m_ds = WhoisDataDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        WhoisDataForm _WhoisDataForm = (WhoisDataForm) form;
        HttpSession session = request.getSession();

        setPage(session, "whois_data_home");

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


        WhoisData _WhoisData = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _WhoisData = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //WhoisData _WhoisData = m_ds.getById(cid);

            if (_WhoisData == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_WhoisData.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _WhoisData.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _WhoisDataForm, _WhoisData);
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
            //WhoisData _WhoisData = m_ds.getById(cid);

            if (_WhoisData == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_WhoisData.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _WhoisData.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _WhoisData);
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
            //WhoisData _WhoisData = m_ds.getById(cid);

            if (_WhoisData == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_WhoisData.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _WhoisData.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _WhoisData);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_WhoisData);
            try { 
                m_actionExtent.afterDelete(request, response, _WhoisData);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new WhoisData" );
            WhoisData _WhoisDataNew = new WhoisData();   

            // Setting IDs for the object
            _WhoisDataNew.setSiteId(site.getId());

            _WhoisDataNew.setIp(WebParamUtil.getStringValue(_WhoisDataForm.getIp()));
            m_logger.debug("setting Ip=" +_WhoisDataForm.getIp());
            _WhoisDataNew.setWhoisData(WebParamUtil.getStringValue(_WhoisDataForm.getWhoisData()));
            m_logger.debug("setting WhoisData=" +_WhoisDataForm.getWhoisData());
            _WhoisDataNew.setServer(WebParamUtil.getStringValue(_WhoisDataForm.getServer()));
            m_logger.debug("setting Server=" +_WhoisDataForm.getServer());
            _WhoisDataNew.setForceRequest(WebParamUtil.getIntValue(_WhoisDataForm.getForceRequest()));
            m_logger.debug("setting ForceRequest=" +_WhoisDataForm.getForceRequest());
            _WhoisDataNew.setTimeCreated(WebParamUtil.getDateValue(_WhoisDataForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_WhoisDataForm.getTimeCreated());
            _WhoisDataNew.setTimeExpired(WebParamUtil.getDateValue(_WhoisDataForm.getTimeExpired()));
            m_logger.debug("setting TimeExpired=" +_WhoisDataForm.getTimeExpired());

            try{
                m_actionExtent.beforeAdd(request, response, _WhoisDataNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_WhoisDataNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_WhoisDataNew);
            try{
                m_actionExtent.afterAdd(request, response, _WhoisDataNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "whois_data_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, WhoisDataForm _WhoisDataForm, WhoisData _WhoisData) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        WhoisData _WhoisData = m_ds.getById(cid);

        m_logger.debug("Before update " + WhoisDataDS.objectToString(_WhoisData));

        _WhoisData.setIp(WebParamUtil.getStringValue(_WhoisDataForm.getIp()));
        _WhoisData.setWhoisData(WebParamUtil.getStringValue(_WhoisDataForm.getWhoisData()));
        _WhoisData.setServer(WebParamUtil.getStringValue(_WhoisDataForm.getServer()));
        _WhoisData.setForceRequest(WebParamUtil.getIntValue(_WhoisDataForm.getForceRequest()));
        _WhoisData.setTimeExpired(WebParamUtil.getDateValue(_WhoisDataForm.getTimeExpired()));

        m_actionExtent.beforeUpdate(request, response, _WhoisData);
        m_ds.update(_WhoisData);
        m_actionExtent.afterUpdate(request, response, _WhoisData);
        m_logger.debug("After update " + WhoisDataDS.objectToString(_WhoisData));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, WhoisData _WhoisData) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        WhoisData _WhoisData = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ip"))) {
            m_logger.debug("updating param ip from " +_WhoisData.getIp() + "->" + request.getParameter("ip"));
            _WhoisData.setIp(WebParamUtil.getStringValue(request.getParameter("ip")));
        }
        if (!isMissing(request.getParameter("whoisData"))) {
            m_logger.debug("updating param whoisData from " +_WhoisData.getWhoisData() + "->" + request.getParameter("whoisData"));
            _WhoisData.setWhoisData(WebParamUtil.getStringValue(request.getParameter("whoisData")));
        }
        if (!isMissing(request.getParameter("server"))) {
            m_logger.debug("updating param server from " +_WhoisData.getServer() + "->" + request.getParameter("server"));
            _WhoisData.setServer(WebParamUtil.getStringValue(request.getParameter("server")));
        }
        if (!isMissing(request.getParameter("forceRequest"))) {
            m_logger.debug("updating param forceRequest from " +_WhoisData.getForceRequest() + "->" + request.getParameter("forceRequest"));
            _WhoisData.setForceRequest(WebParamUtil.getIntValue(request.getParameter("forceRequest")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_WhoisData.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _WhoisData.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeExpired"))) {
            m_logger.debug("updating param timeExpired from " +_WhoisData.getTimeExpired() + "->" + request.getParameter("timeExpired"));
            _WhoisData.setTimeExpired(WebParamUtil.getDateValue(request.getParameter("timeExpired")));
        }

        m_actionExtent.beforeUpdate(request, response, _WhoisData);
        m_ds.update(_WhoisData);
        m_actionExtent.afterUpdate(request, response, _WhoisData);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, WhoisData _WhoisData) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        WhoisData _WhoisData = m_ds.getById(cid);

        if (!isMissing(request.getParameter("ip"))) {
			return String.valueOf(_WhoisData.getIp());
        }
        if (!isMissing(request.getParameter("whoisData"))) {
			return String.valueOf(_WhoisData.getWhoisData());
        }
        if (!isMissing(request.getParameter("server"))) {
			return String.valueOf(_WhoisData.getServer());
        }
        if (!isMissing(request.getParameter("forceRequest"))) {
			return String.valueOf(_WhoisData.getForceRequest());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_WhoisData.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeExpired"))) {
			return String.valueOf(_WhoisData.getTimeExpired());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, WhoisData _WhoisData) {
        if (fieldName == null || fieldName.equals("")|| _WhoisData == null) return null;
        
        if (fieldName.equals("ip")) {
            return WebUtil.display(_WhoisData.getIp());
        }
        if (fieldName.equals("whoisData")) {
            return WebUtil.display(_WhoisData.getWhoisData());
        }
        if (fieldName.equals("server")) {
            return WebUtil.display(_WhoisData.getServer());
        }
        if (fieldName.equals("forceRequest")) {
            return WebUtil.display(_WhoisData.getForceRequest());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_WhoisData.getTimeCreated());
        }
        if (fieldName.equals("timeExpired")) {
            return WebUtil.display(_WhoisData.getTimeExpired());
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
            WhoisData _WhoisData = WhoisDataDS.getInstance().getById(id);
            if (_WhoisData == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _WhoisData);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            WhoisData _WhoisData = WhoisDataDS.getInstance().getById(id);
            if ( _WhoisData == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _WhoisData);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/whoisDataAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("ip")) {
                String value = WebUtil.display(request.getParameter("ip"));

                if ( forceHiddenSet.contains("ip")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ip\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ip</div>");
            buf.append("<INPUT NAME=\"ip\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("whoisData")) {
                String value = WebUtil.display(request.getParameter("whoisData"));

                if ( forceHiddenSet.contains("whoisData")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"whoisData\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Whois Data</div>");
            buf.append("<TEXTAREA NAME=\"whoisData\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("server")) {
                String value = WebUtil.display(request.getParameter("server"));

                if ( forceHiddenSet.contains("server")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"server\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Server</div>");
            buf.append("<select id=\"requiredField\" name=\"server\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("WhoisDataServerOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("forceRequest")) {
                String value = WebUtil.display(request.getParameter("forceRequest"));

                if ( forceHiddenSet.contains("forceRequest")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"forceRequest\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Force Request</div>");
            buf.append("<select name=\"forceRequest\">");
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

            if ( ignoreFieldSet || fieldSet.contains("timeExpired")) {
                String value = WebUtil.display(request.getParameter("timeExpired"));

                if ( forceHiddenSet.contains("timeExpired")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeExpired\"  value=\""+value+"\">");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/whoisDataAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            m_logger.debug("Ajax Processing gethtml getmodalstatus arg = " + request.getParameter("ajaxOutArg"));
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into. 
            ret.put("__value", "Successfully received.");

        } else if (hasRequestValue(request, "ajaxOut", "getlist")){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            
            String arg = request.getParameter("ajaxOutArg");
            WhoisData _WhoisData = null; 
            List list = WhoisDataDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _WhoisData = (WhoisData) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _WhoisData = (WhoisData) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _WhoisData = WhoisDataDS.getInstance().getById(id);
            }

            String 	fieldSetStr = request.getParameter("formfieldlist");
            Set 	fieldSet = JtStringUtil.convertToSet(fieldSetStr);
            boolean ignoreFieldSet = (WebUtil.isNull(fieldSetStr)? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlisthtml");
            if (!returnList) {
                list = new ArrayList();
                list.add(_WhoisData);
            }
            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("whoisData")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Whois Data");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("forceRequest")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Force Request");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeExpired")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Expired");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                _WhoisData = (WhoisData) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("whoisData")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_WhoisData.getWhoisData()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("forceRequest")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_WhoisData.getForceRequest()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/whoisDataAction.html?ef=true&id="+ _WhoisData.getId()+"&forceRequest=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/whoisDataAction.html?ef=true&id="+ _WhoisData.getId()+"&forceRequest=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_WhoisData.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeExpired")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_WhoisData.getTimeExpired()));

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
            WhoisData _WhoisData = null; 
            List list = WhoisDataDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _WhoisData = (WhoisData) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _WhoisData = (WhoisData) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _WhoisData = WhoisDataDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last WhoisData=" + _WhoisData.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _WhoisData = (WhoisData) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("ip")) 
			            json.put("ip", ""+_WhoisData.getIp());
		            if ( ignoreFieldSet || fieldSet.contains("whoisData")) 
			            json.put("whoisData", ""+_WhoisData.getWhoisData());
		            if ( ignoreFieldSet || fieldSet.contains("server")) 
			            json.put("server", ""+_WhoisData.getServer());
		            if ( ignoreFieldSet || fieldSet.contains("forceRequest")) 
			            json.put("forceRequest", ""+_WhoisData.getForceRequest());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_WhoisData.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonIp = new JSONObject();
	            jsonIp.put("name", "ip");
	            jsonIp.put("value", ""+_WhoisData.getIp());
	            array.put(jsonIp);

	            JSONObject jsonWhoisData = new JSONObject();
	            jsonWhoisData.put("name", "whoisData");
	            jsonWhoisData.put("value", ""+_WhoisData.getWhoisData());
	            array.put(jsonWhoisData);

	            JSONObject jsonServer = new JSONObject();
	            jsonServer.put("name", "server");
	            jsonServer.put("value", ""+_WhoisData.getServer());
	            array.put(jsonServer);

	            JSONObject jsonForceRequest = new JSONObject();
	            jsonForceRequest.put("name", "forceRequest");
	            jsonForceRequest.put("value", ""+_WhoisData.getForceRequest());
	            array.put(jsonForceRequest);


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
    
    protected WhoisDataDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( WhoisDataAction.class);
}
