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

import com.autosite.db.TestDataDepot;
import com.autosite.ds.TestDataDepotDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.TestDataDepotForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class TestDataDepotAction extends AutositeCoreAction {

    public TestDataDepotAction(){
        m_ds = TestDataDepotDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        TestDataDepotForm _TestDataDepotForm = (TestDataDepotForm) form;
        HttpSession session = request.getSession();

        setPage(session, "test_data_depot_home");

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


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        TestDataDepot _TestDataDepot = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _TestDataDepot = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //TestDataDepot _TestDataDepot = m_ds.getById(cid);

            if (_TestDataDepot == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_TestDataDepot.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _TestDataDepot.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _TestDataDepotForm, _TestDataDepot);
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
            //TestDataDepot _TestDataDepot = m_ds.getById(cid);

            if (_TestDataDepot == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_TestDataDepot.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _TestDataDepot.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _TestDataDepot);
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
            //TestDataDepot _TestDataDepot = m_ds.getById(cid);

            if (_TestDataDepot == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_TestDataDepot.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _TestDataDepot.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _TestDataDepot);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_TestDataDepot);
            try { 
                m_actionExtent.afterDelete(request, response, _TestDataDepot);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new TestDataDepot" );
            TestDataDepot _TestDataDepotNew = new TestDataDepot();   

            // Setting IDs for the object
            _TestDataDepotNew.setSiteId(site.getId());

            _TestDataDepotNew.setTitle(WebParamUtil.getStringValue(_TestDataDepotForm.getTitle()));
            m_logger.debug("setting Title=" +_TestDataDepotForm.getTitle());
            _TestDataDepotNew.setData(WebParamUtil.getStringValue(_TestDataDepotForm.getData()));
            m_logger.debug("setting Data=" +_TestDataDepotForm.getData());
            _TestDataDepotNew.setType(WebParamUtil.getIntValue(_TestDataDepotForm.getType()));
            m_logger.debug("setting Type=" +_TestDataDepotForm.getType());
            _TestDataDepotNew.setTimeCreated(WebParamUtil.getDateValue(_TestDataDepotForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_TestDataDepotForm.getTimeCreated());
            _TestDataDepotNew.setTimeUpdated(WebParamUtil.getDateValue(_TestDataDepotForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_TestDataDepotForm.getTimeUpdated());

			// Checking the uniqueness by configuration. 
			if ( m_ds.getBySiteIdTitle(_TestDataDepotNew.getSiteId(), _TestDataDepotNew.getTitle()) != null){
                sessionErrorText(session, "You can't enter the same value with the existing. Please try again");
                m_logger.error("Request failed becuase value exist " + _TestDataDepotNew.getTitle());
                return mapping.findForward("default");
            }
            try{
                m_actionExtent.beforeAdd(request, response, _TestDataDepotNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_TestDataDepotNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_TestDataDepotNew);
            try{
                m_actionExtent.afterAdd(request, response, _TestDataDepotNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "test_data_depot_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, TestDataDepotForm _TestDataDepotForm, TestDataDepot _TestDataDepot) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        TestDataDepot _TestDataDepot = m_ds.getById(cid);

        m_logger.debug("Before update " + TestDataDepotDS.objectToString(_TestDataDepot));

        _TestDataDepot.setTitle(WebParamUtil.getStringValue(_TestDataDepotForm.getTitle()));
        _TestDataDepot.setData(WebParamUtil.getStringValue(_TestDataDepotForm.getData()));
        _TestDataDepot.setType(WebParamUtil.getIntValue(_TestDataDepotForm.getType()));
        _TestDataDepot.setTimeUpdated(WebParamUtil.getDateValue(_TestDataDepotForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _TestDataDepot);
        m_ds.update(_TestDataDepot);
        m_actionExtent.afterUpdate(request, response, _TestDataDepot);
        m_logger.debug("After update " + TestDataDepotDS.objectToString(_TestDataDepot));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, TestDataDepot _TestDataDepot) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        TestDataDepot _TestDataDepot = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_TestDataDepot.getTitle() + "->" + request.getParameter("title"));
            _TestDataDepot.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));
        }
        if (!isMissing(request.getParameter("data"))) {
            m_logger.debug("updating param data from " +_TestDataDepot.getData() + "->" + request.getParameter("data"));
            _TestDataDepot.setData(WebParamUtil.getStringValue(request.getParameter("data")));
        }
        if (!isMissing(request.getParameter("type"))) {
            m_logger.debug("updating param type from " +_TestDataDepot.getType() + "->" + request.getParameter("type"));
            _TestDataDepot.setType(WebParamUtil.getIntValue(request.getParameter("type")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_TestDataDepot.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _TestDataDepot.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_TestDataDepot.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _TestDataDepot.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _TestDataDepot.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _TestDataDepot);
        m_ds.update(_TestDataDepot);
        m_actionExtent.afterUpdate(request, response, _TestDataDepot);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, TestDataDepot _TestDataDepot) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        TestDataDepot _TestDataDepot = m_ds.getById(cid);

        if (!isMissing(request.getParameter("title"))) {
			return String.valueOf(_TestDataDepot.getTitle());
        }
        if (!isMissing(request.getParameter("data"))) {
			return String.valueOf(_TestDataDepot.getData());
        }
        if (!isMissing(request.getParameter("type"))) {
			return String.valueOf(_TestDataDepot.getType());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_TestDataDepot.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_TestDataDepot.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, TestDataDepot _TestDataDepot) {
        if (fieldName == null || fieldName.equals("")|| _TestDataDepot == null) return null;
        
        if (fieldName.equals("title")) {
            return WebUtil.display(_TestDataDepot.getTitle());
        }
        if (fieldName.equals("data")) {
            return WebUtil.display(_TestDataDepot.getData());
        }
        if (fieldName.equals("type")) {
            return WebUtil.display(_TestDataDepot.getType());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_TestDataDepot.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_TestDataDepot.getTimeUpdated());
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
            TestDataDepot _TestDataDepot = TestDataDepotDS.getInstance().getById(id);
            if (_TestDataDepot == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _TestDataDepot);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            TestDataDepot _TestDataDepot = TestDataDepotDS.getInstance().getById(id);
            if ( _TestDataDepot == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _TestDataDepot);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/testDataDepotAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


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

        	buf.append("<option value=\"0\" >0</option>");
        	buf.append("<option value=\"1\" >1</option>");
        	buf.append("<option value=\"2\" >2</option>");
        	buf.append("<option value=\"3\" >3</option>");
        	buf.append("<option value=\"4\" >4</option>");
        	buf.append("<option value=\"5\" >5</option>");

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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/testDataDepotAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
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

            buf.append("<div id=\"testDataDepot-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                TestDataDepot _TestDataDepot = (TestDataDepot) iterator.next();

                buf.append("<div id=\"testDataDepot-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("title")) {
                    buf.append("<div id=\"testDataDepot-ajax-title\" "+itemClass+">");
                    buf.append(WebUtil.display(_TestDataDepot.getTitle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("data")) {
                    buf.append("<div id=\"testDataDepot-ajax-data\" "+itemClass+">");
                    buf.append(WebUtil.display(_TestDataDepot.getData()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("type")) {
                    buf.append("<div id=\"testDataDepot-ajax-type\" "+itemClass+">");
                    buf.append(WebUtil.display(_TestDataDepot.getType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"testDataDepot-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_TestDataDepot.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"testDataDepot-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_TestDataDepot.getTimeUpdated()));
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
                TestDataDepot _TestDataDepot = (TestDataDepot) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("title")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_TestDataDepot.getTitle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("data")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_TestDataDepot.getData()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("type")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_TestDataDepot.getType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_TestDataDepot.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_TestDataDepot.getTimeUpdated()));

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
            TestDataDepot _TestDataDepot = null; 
            List list = TestDataDepotDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _TestDataDepot = (TestDataDepot) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _TestDataDepot = (TestDataDepot) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _TestDataDepot = TestDataDepotDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last TestDataDepot=" + _TestDataDepot.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _TestDataDepot = (TestDataDepot) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("title")) 
			            json.put("title", ""+_TestDataDepot.getTitle());
		            if ( ignoreFieldSet || fieldSet.contains("data")) 
			            json.put("data", ""+_TestDataDepot.getData());
		            if ( ignoreFieldSet || fieldSet.contains("type")) 
			            json.put("type", ""+_TestDataDepot.getType());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_TestDataDepot.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonTitle = new JSONObject();
	            jsonTitle.put("name", "title");
	            jsonTitle.put("value", ""+_TestDataDepot.getTitle());
	            array.put(jsonTitle);

	            JSONObject jsonData = new JSONObject();
	            jsonData.put("name", "data");
	            jsonData.put("value", ""+_TestDataDepot.getData());
	            array.put(jsonData);

	            JSONObject jsonType = new JSONObject();
	            jsonType.put("name", "type");
	            jsonType.put("value", ""+_TestDataDepot.getType());
	            array.put(jsonType);


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
            TestDataDepot _TestDataDepot = null; 
            List list = TestDataDepotDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _TestDataDepot = (TestDataDepot) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _TestDataDepot = (TestDataDepot) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _TestDataDepot = TestDataDepotDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_TestDataDepot);

        } else {
            
            List list = TestDataDepotDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected TestDataDepotDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( TestDataDepotAction.class);
}
