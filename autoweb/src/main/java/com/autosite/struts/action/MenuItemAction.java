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

import com.autosite.db.MenuItem;
import com.autosite.ds.MenuItemDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.MenuItemForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;

public class MenuItemAction extends AutositeCoreAction {

    public MenuItemAction(){
        m_ds = MenuItemDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        MenuItemForm _MenuItemForm = (MenuItemForm) form;
        HttpSession session = request.getSession();

        setPage(session, "menu_item_home");

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


        MenuItem _MenuItem = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _MenuItem = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //MenuItem _MenuItem = m_ds.getById(cid);

            if (_MenuItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_MenuItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _MenuItem.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _MenuItemForm, _MenuItem);
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
            //MenuItem _MenuItem = m_ds.getById(cid);

            if (_MenuItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_MenuItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _MenuItem.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _MenuItem);
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
            //MenuItem _MenuItem = m_ds.getById(cid);

            if (_MenuItem == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_MenuItem.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _MenuItem.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _MenuItem);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_MenuItem);
            try { 
                m_actionExtent.afterDelete(request, response, _MenuItem);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new MenuItem" );
            MenuItem _MenuItemNew = new MenuItem();   

            // Setting IDs for the object
            _MenuItemNew.setSiteId(site.getId());

            _MenuItemNew.setPanelId(WebParamUtil.getLongValue(_MenuItemForm.getPanelId()));
            m_logger.debug("setting PanelId=" +_MenuItemForm.getPanelId());
            _MenuItemNew.setParentId(WebParamUtil.getLongValue(_MenuItemForm.getParentId()));
            m_logger.debug("setting ParentId=" +_MenuItemForm.getParentId());
            _MenuItemNew.setTitle(WebParamUtil.getStringValue(_MenuItemForm.getTitle()));
            m_logger.debug("setting Title=" +_MenuItemForm.getTitle());
            _MenuItemNew.setData(WebParamUtil.getStringValue(_MenuItemForm.getData()));
            m_logger.debug("setting Data=" +_MenuItemForm.getData());
            _MenuItemNew.setTargetType(WebParamUtil.getIntValue(_MenuItemForm.getTargetType()));
            m_logger.debug("setting TargetType=" +_MenuItemForm.getTargetType());
            _MenuItemNew.setOrderIdx(WebParamUtil.getIntValue(_MenuItemForm.getOrderIdx()));
            m_logger.debug("setting OrderIdx=" +_MenuItemForm.getOrderIdx());
            _MenuItemNew.setPageId(WebParamUtil.getLongValue(_MenuItemForm.getPageId()));
            m_logger.debug("setting PageId=" +_MenuItemForm.getPageId());
            _MenuItemNew.setContentId(WebParamUtil.getLongValue(_MenuItemForm.getContentId()));
            m_logger.debug("setting ContentId=" +_MenuItemForm.getContentId());
            _MenuItemNew.setHide(WebParamUtil.getIntValue(_MenuItemForm.getHide()));
            m_logger.debug("setting Hide=" +_MenuItemForm.getHide());
            _MenuItemNew.setTimeCreated(WebParamUtil.getDateValue(_MenuItemForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_MenuItemForm.getTimeCreated());

            try{
                m_actionExtent.beforeAdd(request, response, _MenuItemNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_MenuItemNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_MenuItemNew);
            try{
                m_actionExtent.afterAdd(request, response, _MenuItemNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "menu_item_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, MenuItemForm _MenuItemForm, MenuItem _MenuItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        MenuItem _MenuItem = m_ds.getById(cid);

        m_logger.debug("Before update " + MenuItemDS.objectToString(_MenuItem));

        _MenuItem.setPanelId(WebParamUtil.getLongValue(_MenuItemForm.getPanelId()));
        _MenuItem.setParentId(WebParamUtil.getLongValue(_MenuItemForm.getParentId()));
        _MenuItem.setTitle(WebParamUtil.getStringValue(_MenuItemForm.getTitle()));
        _MenuItem.setData(WebParamUtil.getStringValue(_MenuItemForm.getData()));
        _MenuItem.setTargetType(WebParamUtil.getIntValue(_MenuItemForm.getTargetType()));
        _MenuItem.setOrderIdx(WebParamUtil.getIntValue(_MenuItemForm.getOrderIdx()));
        _MenuItem.setPageId(WebParamUtil.getLongValue(_MenuItemForm.getPageId()));
        _MenuItem.setContentId(WebParamUtil.getLongValue(_MenuItemForm.getContentId()));
        _MenuItem.setHide(WebParamUtil.getIntValue(_MenuItemForm.getHide()));

        m_actionExtent.beforeUpdate(request, response, _MenuItem);
        m_ds.update(_MenuItem);
        m_actionExtent.afterUpdate(request, response, _MenuItem);
        m_logger.debug("After update " + MenuItemDS.objectToString(_MenuItem));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, MenuItem _MenuItem) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        MenuItem _MenuItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("panelId"))) {
            m_logger.debug("updating param panelId from " +_MenuItem.getPanelId() + "->" + request.getParameter("panelId"));
            _MenuItem.setPanelId(WebParamUtil.getLongValue(request.getParameter("panelId")));
        }
        if (!isMissing(request.getParameter("parentId"))) {
            m_logger.debug("updating param parentId from " +_MenuItem.getParentId() + "->" + request.getParameter("parentId"));
            _MenuItem.setParentId(WebParamUtil.getLongValue(request.getParameter("parentId")));
        }
        if (!isMissing(request.getParameter("title"))) {
            m_logger.debug("updating param title from " +_MenuItem.getTitle() + "->" + request.getParameter("title"));
            _MenuItem.setTitle(WebParamUtil.getStringValue(request.getParameter("title")));
        }
        if (!isMissing(request.getParameter("data"))) {
            m_logger.debug("updating param data from " +_MenuItem.getData() + "->" + request.getParameter("data"));
            _MenuItem.setData(WebParamUtil.getStringValue(request.getParameter("data")));
        }
        if (!isMissing(request.getParameter("targetType"))) {
            m_logger.debug("updating param targetType from " +_MenuItem.getTargetType() + "->" + request.getParameter("targetType"));
            _MenuItem.setTargetType(WebParamUtil.getIntValue(request.getParameter("targetType")));
        }
        if (!isMissing(request.getParameter("orderIdx"))) {
            m_logger.debug("updating param orderIdx from " +_MenuItem.getOrderIdx() + "->" + request.getParameter("orderIdx"));
            _MenuItem.setOrderIdx(WebParamUtil.getIntValue(request.getParameter("orderIdx")));
        }
        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_MenuItem.getPageId() + "->" + request.getParameter("pageId"));
            _MenuItem.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("contentId"))) {
            m_logger.debug("updating param contentId from " +_MenuItem.getContentId() + "->" + request.getParameter("contentId"));
            _MenuItem.setContentId(WebParamUtil.getLongValue(request.getParameter("contentId")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_MenuItem.getHide() + "->" + request.getParameter("hide"));
            _MenuItem.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_MenuItem.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _MenuItem.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }

        m_actionExtent.beforeUpdate(request, response, _MenuItem);
        m_ds.update(_MenuItem);
        m_actionExtent.afterUpdate(request, response, _MenuItem);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, MenuItem _MenuItem) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        MenuItem _MenuItem = m_ds.getById(cid);

        if (!isMissing(request.getParameter("panelId"))) {
			return String.valueOf(_MenuItem.getPanelId());
        }
        if (!isMissing(request.getParameter("parentId"))) {
			return String.valueOf(_MenuItem.getParentId());
        }
        if (!isMissing(request.getParameter("title"))) {
			return String.valueOf(_MenuItem.getTitle());
        }
        if (!isMissing(request.getParameter("data"))) {
			return String.valueOf(_MenuItem.getData());
        }
        if (!isMissing(request.getParameter("targetType"))) {
			return String.valueOf(_MenuItem.getTargetType());
        }
        if (!isMissing(request.getParameter("orderIdx"))) {
			return String.valueOf(_MenuItem.getOrderIdx());
        }
        if (!isMissing(request.getParameter("pageId"))) {
			return String.valueOf(_MenuItem.getPageId());
        }
        if (!isMissing(request.getParameter("contentId"))) {
			return String.valueOf(_MenuItem.getContentId());
        }
        if (!isMissing(request.getParameter("hide"))) {
			return String.valueOf(_MenuItem.getHide());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_MenuItem.getTimeCreated());
        }
		return null;
    }

    protected String getFieldByName(String fieldName, MenuItem _MenuItem) {
        if (fieldName == null || fieldName.equals("")|| _MenuItem == null) return null;
        
        if (fieldName.equals("panelId")) {
            return WebUtil.display(_MenuItem.getPanelId());
        }
        if (fieldName.equals("parentId")) {
            return WebUtil.display(_MenuItem.getParentId());
        }
        if (fieldName.equals("title")) {
            return WebUtil.display(_MenuItem.getTitle());
        }
        if (fieldName.equals("data")) {
            return WebUtil.display(_MenuItem.getData());
        }
        if (fieldName.equals("targetType")) {
            return WebUtil.display(_MenuItem.getTargetType());
        }
        if (fieldName.equals("orderIdx")) {
            return WebUtil.display(_MenuItem.getOrderIdx());
        }
        if (fieldName.equals("pageId")) {
            return WebUtil.display(_MenuItem.getPageId());
        }
        if (fieldName.equals("contentId")) {
            return WebUtil.display(_MenuItem.getContentId());
        }
        if (fieldName.equals("hide")) {
            return WebUtil.display(_MenuItem.getHide());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_MenuItem.getTimeCreated());
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
            MenuItem _MenuItem = MenuItemDS.getInstance().getById(id);
            if (_MenuItem == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _MenuItem);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            MenuItem _MenuItem = MenuItemDS.getInstance().getById(id);
            if ( _MenuItem == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _MenuItem);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/menuItemAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("panelId")) {
                String value = WebUtil.display(request.getParameter("panelId"));

                if ( forceHiddenSet.contains("panelId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"panelId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Panel Id</div>");
            buf.append("<INPUT NAME=\"panelId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("parentId")) {
                String value = WebUtil.display(request.getParameter("parentId"));

                if ( forceHiddenSet.contains("parentId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"parentId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Parent Id</div>");
            buf.append("<INPUT NAME=\"parentId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("data")) {
                String value = WebUtil.display(request.getParameter("data"));

                if ( forceHiddenSet.contains("data")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"data\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Data</div>");
            buf.append("<INPUT NAME=\"data\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("targetType")) {
                String value = WebUtil.display(request.getParameter("targetType"));

                if ( forceHiddenSet.contains("targetType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"targetType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Target Type</div>");
            buf.append("<select id=\"requiredField\" name=\"targetType\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

        	buf.append("<option value=\"0\" >Page</option>");
        	buf.append("<option value=\"1\" >Link</option>");
        	buf.append("<option value=\"2\" >Content</option>");

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("orderIdx")) {
                String value = WebUtil.display(request.getParameter("orderIdx"));

                if ( forceHiddenSet.contains("orderIdx")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"orderIdx\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Order Idx</div>");
            buf.append("<INPUT NAME=\"orderIdx\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pageId")) {
                String value = WebUtil.display(request.getParameter("pageId"));

                if ( forceHiddenSet.contains("pageId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pageId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Page Id</div>");
            buf.append("<INPUT NAME=\"pageId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("contentId")) {
                String value = WebUtil.display(request.getParameter("contentId"));

                if ( forceHiddenSet.contains("contentId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"contentId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Content Id</div>");
            buf.append("<INPUT NAME=\"contentId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("hide")) {
                String value = WebUtil.display(request.getParameter("hide"));

                if ( forceHiddenSet.contains("hide")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"hide\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Hide</div>");
            buf.append("<select name=\"hide\">");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/menuItemAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
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
            MenuItem _MenuItem = null; 
            List list = MenuItemDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _MenuItem = (MenuItem) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _MenuItem = (MenuItem) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _MenuItem = MenuItemDS.getInstance().getById(id);
            }

            String 	fieldSetStr = request.getParameter("formfieldlist");
            Set 	fieldSet = JtStringUtil.convertToSet(fieldSetStr);
            boolean ignoreFieldSet = (WebUtil.isNull(fieldSetStr)? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlisthtml");
            if (!returnList) {
                list = new ArrayList();
                list.add(_MenuItem);
            }
            m_logger.debug("Number of objects to return " + list.size());
            StringBuffer buf = new StringBuffer();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("panelId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Panel Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("parentId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Parent Id");
	            buf.append("</td>");
			}
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
            if ( ignoreFieldSet || fieldSet.contains("targetType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Target Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("orderIdx")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Order Idx");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pageId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Page Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("contentId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Content Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("hide")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Hide");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                _MenuItem = (MenuItem) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("panelId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getPanelId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("parentId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getParentId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("title")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getTitle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("data")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getData()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("targetType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getTargetType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("orderIdx")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getOrderIdx()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pageId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getPageId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("contentId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getContentId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("hide")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_MenuItem.getHide()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/menuItemAction.html?ef=true&id="+ _MenuItem.getId()+"&hide=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/menuItemAction.html?ef=true&id="+ _MenuItem.getId()+"&hide=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_MenuItem.getTimeCreated()));

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
            MenuItem _MenuItem = null; 
            List list = MenuItemDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _MenuItem = (MenuItem) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _MenuItem = (MenuItem) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _MenuItem = MenuItemDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last MenuItem=" + _MenuItem.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _MenuItem = (MenuItem) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("panelId")) 
			            json.put("panelId", ""+_MenuItem.getPanelId());
		            if ( ignoreFieldSet || fieldSet.contains("parentId")) 
			            json.put("parentId", ""+_MenuItem.getParentId());
		            if ( ignoreFieldSet || fieldSet.contains("title")) 
			            json.put("title", ""+_MenuItem.getTitle());
		            if ( ignoreFieldSet || fieldSet.contains("data")) 
			            json.put("data", ""+_MenuItem.getData());
		            if ( ignoreFieldSet || fieldSet.contains("targetType")) 
			            json.put("targetType", ""+_MenuItem.getTargetType());
		            if ( ignoreFieldSet || fieldSet.contains("orderIdx")) 
			            json.put("orderIdx", ""+_MenuItem.getOrderIdx());
		            if ( ignoreFieldSet || fieldSet.contains("pageId")) 
			            json.put("pageId", ""+_MenuItem.getPageId());
		            if ( ignoreFieldSet || fieldSet.contains("contentId")) 
			            json.put("contentId", ""+_MenuItem.getContentId());
		            if ( ignoreFieldSet || fieldSet.contains("hide")) 
			            json.put("hide", ""+_MenuItem.getHide());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_MenuItem.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonPanelId = new JSONObject();
	            jsonPanelId.put("name", "panelId");
	            jsonPanelId.put("value", ""+_MenuItem.getPanelId());
	            array.put(jsonPanelId);

	            JSONObject jsonParentId = new JSONObject();
	            jsonParentId.put("name", "parentId");
	            jsonParentId.put("value", ""+_MenuItem.getParentId());
	            array.put(jsonParentId);

	            JSONObject jsonTitle = new JSONObject();
	            jsonTitle.put("name", "title");
	            jsonTitle.put("value", ""+_MenuItem.getTitle());
	            array.put(jsonTitle);

	            JSONObject jsonData = new JSONObject();
	            jsonData.put("name", "data");
	            jsonData.put("value", ""+_MenuItem.getData());
	            array.put(jsonData);

	            JSONObject jsonTargetType = new JSONObject();
	            jsonTargetType.put("name", "targetType");
	            jsonTargetType.put("value", ""+_MenuItem.getTargetType());
	            array.put(jsonTargetType);

	            JSONObject jsonOrderIdx = new JSONObject();
	            jsonOrderIdx.put("name", "orderIdx");
	            jsonOrderIdx.put("value", ""+_MenuItem.getOrderIdx());
	            array.put(jsonOrderIdx);

	            JSONObject jsonPageId = new JSONObject();
	            jsonPageId.put("name", "pageId");
	            jsonPageId.put("value", ""+_MenuItem.getPageId());
	            array.put(jsonPageId);

	            JSONObject jsonContentId = new JSONObject();
	            jsonContentId.put("name", "contentId");
	            jsonContentId.put("value", ""+_MenuItem.getContentId());
	            array.put(jsonContentId);

	            JSONObject jsonHide = new JSONObject();
	            jsonHide.put("name", "hide");
	            jsonHide.put("value", ""+_MenuItem.getHide());
	            array.put(jsonHide);


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
    
    protected MenuItemDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( MenuItemAction.class);
}
