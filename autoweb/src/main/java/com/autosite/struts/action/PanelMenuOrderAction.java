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

import com.autosite.db.PanelMenuOrder;
import com.autosite.ds.PanelMenuOrderDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PanelMenuOrderForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class PanelMenuOrderAction extends AutositeCoreAction {

    public PanelMenuOrderAction(){
        m_ds = PanelMenuOrderDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        PanelMenuOrderForm _PanelMenuOrderForm = (PanelMenuOrderForm) form;
        HttpSession session = request.getSession();

        setPage(session, "panel_menu_order_home");

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


        PanelMenuOrder _PanelMenuOrder = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _PanelMenuOrder = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //PanelMenuOrder _PanelMenuOrder = m_ds.getById(cid);

            if (_PanelMenuOrder == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelMenuOrder.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelMenuOrder.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PanelMenuOrderForm, _PanelMenuOrder);
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
            //PanelMenuOrder _PanelMenuOrder = m_ds.getById(cid);

            if (_PanelMenuOrder == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelMenuOrder.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelMenuOrder.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _PanelMenuOrder);
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
            //PanelMenuOrder _PanelMenuOrder = m_ds.getById(cid);

            if (_PanelMenuOrder == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_PanelMenuOrder.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _PanelMenuOrder.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _PanelMenuOrder);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_PanelMenuOrder);
            try { 
                m_actionExtent.afterDelete(request, response, _PanelMenuOrder);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new PanelMenuOrder" );
            PanelMenuOrder _PanelMenuOrderNew = new PanelMenuOrder();   

            // Setting IDs for the object
            _PanelMenuOrderNew.setSiteId(site.getId());

            _PanelMenuOrderNew.setOrderedIds(WebParamUtil.getStringValue(_PanelMenuOrderForm.getOrderedIds()));
            m_logger.debug("setting OrderedIds=" +_PanelMenuOrderForm.getOrderedIds());
            _PanelMenuOrderNew.setReverse(WebParamUtil.getIntValue(_PanelMenuOrderForm.getReverse()));
            m_logger.debug("setting Reverse=" +_PanelMenuOrderForm.getReverse());
            _PanelMenuOrderNew.setTimeCreated(WebParamUtil.getDateValue(_PanelMenuOrderForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PanelMenuOrderForm.getTimeCreated());
            _PanelMenuOrderNew.setTimeUpdated(WebParamUtil.getDateValue(_PanelMenuOrderForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_PanelMenuOrderForm.getTimeUpdated());

            try{
                m_actionExtent.beforeAdd(request, response, _PanelMenuOrderNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PanelMenuOrderNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PanelMenuOrderNew);
            try{
                m_actionExtent.afterAdd(request, response, _PanelMenuOrderNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "panel_menu_order_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PanelMenuOrderForm _PanelMenuOrderForm, PanelMenuOrder _PanelMenuOrder) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PanelMenuOrder _PanelMenuOrder = m_ds.getById(cid);

        m_logger.debug("Before update " + PanelMenuOrderDS.objectToString(_PanelMenuOrder));

        _PanelMenuOrder.setOrderedIds(WebParamUtil.getStringValue(_PanelMenuOrderForm.getOrderedIds()));
        _PanelMenuOrder.setReverse(WebParamUtil.getIntValue(_PanelMenuOrderForm.getReverse()));
        _PanelMenuOrder.setTimeUpdated(WebParamUtil.getDateValue(_PanelMenuOrderForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _PanelMenuOrder);
        m_ds.update(_PanelMenuOrder);
        m_actionExtent.afterUpdate(request, response, _PanelMenuOrder);
        m_logger.debug("After update " + PanelMenuOrderDS.objectToString(_PanelMenuOrder));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, PanelMenuOrder _PanelMenuOrder) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PanelMenuOrder _PanelMenuOrder = m_ds.getById(cid);

        if (!isMissing(request.getParameter("panelId"))) {
            m_logger.debug("updating param panelId from " +_PanelMenuOrder.getPanelId() + "->" + request.getParameter("panelId"));
            _PanelMenuOrder.setPanelId(WebParamUtil.getLongValue(request.getParameter("panelId")));
        }
        if (!isMissing(request.getParameter("orderedIds"))) {
            m_logger.debug("updating param orderedIds from " +_PanelMenuOrder.getOrderedIds() + "->" + request.getParameter("orderedIds"));
            _PanelMenuOrder.setOrderedIds(WebParamUtil.getStringValue(request.getParameter("orderedIds")));
        }
        if (!isMissing(request.getParameter("reverse"))) {
            m_logger.debug("updating param reverse from " +_PanelMenuOrder.getReverse() + "->" + request.getParameter("reverse"));
            _PanelMenuOrder.setReverse(WebParamUtil.getIntValue(request.getParameter("reverse")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_PanelMenuOrder.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _PanelMenuOrder.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_PanelMenuOrder.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _PanelMenuOrder.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _PanelMenuOrder.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _PanelMenuOrder);
        m_ds.update(_PanelMenuOrder);
        m_actionExtent.afterUpdate(request, response, _PanelMenuOrder);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, PanelMenuOrder _PanelMenuOrder) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        PanelMenuOrder _PanelMenuOrder = m_ds.getById(cid);

        if (!isMissing(request.getParameter("panelId"))) {
			return String.valueOf(_PanelMenuOrder.getPanelId());
        }
        if (!isMissing(request.getParameter("orderedIds"))) {
			return String.valueOf(_PanelMenuOrder.getOrderedIds());
        }
        if (!isMissing(request.getParameter("reverse"))) {
			return String.valueOf(_PanelMenuOrder.getReverse());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_PanelMenuOrder.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_PanelMenuOrder.getTimeUpdated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, PanelMenuOrder _PanelMenuOrder) {
        if (fieldName == null || fieldName.equals("")|| _PanelMenuOrder == null) return null;
        
        if (fieldName.equals("panelId")) {
            return WebUtil.display(_PanelMenuOrder.getPanelId());
        }
        if (fieldName.equals("orderedIds")) {
            return WebUtil.display(_PanelMenuOrder.getOrderedIds());
        }
        if (fieldName.equals("reverse")) {
            return WebUtil.display(_PanelMenuOrder.getReverse());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_PanelMenuOrder.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_PanelMenuOrder.getTimeUpdated());
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
            PanelMenuOrder _PanelMenuOrder = PanelMenuOrderDS.getInstance().getById(id);
            if (_PanelMenuOrder == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _PanelMenuOrder);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            PanelMenuOrder _PanelMenuOrder = PanelMenuOrderDS.getInstance().getById(id);
            if ( _PanelMenuOrder == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _PanelMenuOrder);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/panelMenuOrderAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("orderedIds")) {
                String value = WebUtil.display(request.getParameter("orderedIds"));

                if ( forceHiddenSet.contains("orderedIds")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"orderedIds\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ordered Ids</div>");
            buf.append("<INPUT NAME=\"orderedIds\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("reverse")) {
                String value = WebUtil.display(request.getParameter("reverse"));

                if ( forceHiddenSet.contains("reverse")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"reverse\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Reverse</div>");
            buf.append("<select name=\"reverse\">");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/panelMenuOrderAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
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
            PanelMenuOrder _PanelMenuOrder = null; 
            List list = PanelMenuOrderDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _PanelMenuOrder = (PanelMenuOrder) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _PanelMenuOrder = (PanelMenuOrder) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _PanelMenuOrder = PanelMenuOrderDS.getInstance().getById(id);
            }

            String 	fieldSetStr = request.getParameter("formfieldlist");
            Set 	fieldSet = JtStringUtil.convertToSet(fieldSetStr);
            boolean ignoreFieldSet = (WebUtil.isNull(fieldSetStr)? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlisthtml");
            if (!returnList) {
                list = new ArrayList();
                list.add(_PanelMenuOrder);
            }
            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("orderedIds")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Ordered Ids");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("reverse")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Reverse");
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
                _PanelMenuOrder = (PanelMenuOrder) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("orderedIds")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PanelMenuOrder.getOrderedIds()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("reverse")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_PanelMenuOrder.getReverse()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/panelMenuOrderAction.html?ef=true&id="+ _PanelMenuOrder.getId()+"&reverse=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/panelMenuOrderAction.html?ef=true&id="+ _PanelMenuOrder.getId()+"&reverse=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PanelMenuOrder.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_PanelMenuOrder.getTimeUpdated()));

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
            PanelMenuOrder _PanelMenuOrder = null; 
            List list = PanelMenuOrderDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _PanelMenuOrder = (PanelMenuOrder) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _PanelMenuOrder = (PanelMenuOrder) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _PanelMenuOrder = PanelMenuOrderDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last PanelMenuOrder=" + _PanelMenuOrder.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _PanelMenuOrder = (PanelMenuOrder) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("orderedIds")) 
			            json.put("orderedIds", ""+_PanelMenuOrder.getOrderedIds());
		            if ( ignoreFieldSet || fieldSet.contains("reverse")) 
			            json.put("reverse", ""+_PanelMenuOrder.getReverse());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_PanelMenuOrder.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonOrderedIds = new JSONObject();
	            jsonOrderedIds.put("name", "orderedIds");
	            jsonOrderedIds.put("value", ""+_PanelMenuOrder.getOrderedIds());
	            array.put(jsonOrderedIds);

	            JSONObject jsonReverse = new JSONObject();
	            jsonReverse.put("name", "reverse");
	            jsonReverse.put("value", ""+_PanelMenuOrder.getReverse());
	            array.put(jsonReverse);


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
        return true;
    }
    
    protected PanelMenuOrderDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PanelMenuOrderAction.class);
}
