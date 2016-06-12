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

import com.autosite.db.ContentData;
import com.autosite.ds.ContentDataDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.ContentDataForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check
import com.autosite.ds.ContentDS;


public class ContentDataAction extends AutositeCoreAction {

    public ContentDataAction(){
        m_ds = ContentDataDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        ContentDataForm _ContentDataForm = (ContentDataForm) form;
        HttpSession session = request.getSession();

        setPage(session, "content_data_home");

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


        ContentData _ContentData = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _ContentData = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //ContentData _ContentData = m_ds.getById(cid);

            if (_ContentData == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentData.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentData.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _ContentDataForm, _ContentData);
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
            //ContentData _ContentData = m_ds.getById(cid);

            if (_ContentData == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentData.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentData.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _ContentData);
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
            //ContentData _ContentData = m_ds.getById(cid);

            if (_ContentData == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_ContentData.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _ContentData.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _ContentData);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_ContentData);
            try { 
                m_actionExtent.afterDelete(request, response, _ContentData);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new ContentData" );
            ContentData _ContentDataNew = new ContentData();   

            // Setting IDs for the object
            _ContentDataNew.setSiteId(site.getId());

            _ContentDataNew.setContentId(WebParamUtil.getLongValue(_ContentDataForm.getContentId()));
            m_logger.debug("setting ContentId=" +_ContentDataForm.getContentId());
            _ContentDataNew.setData(WebParamUtil.getStringValue(_ContentDataForm.getData()));
            m_logger.debug("setting Data=" +_ContentDataForm.getData());
            _ContentDataNew.setOption1(WebParamUtil.getStringValue(_ContentDataForm.getOption1()));
            m_logger.debug("setting Option1=" +_ContentDataForm.getOption1());
            _ContentDataNew.setOption2(WebParamUtil.getStringValue(_ContentDataForm.getOption2()));
            m_logger.debug("setting Option2=" +_ContentDataForm.getOption2());
            _ContentDataNew.setOption3(WebParamUtil.getStringValue(_ContentDataForm.getOption3()));
            m_logger.debug("setting Option3=" +_ContentDataForm.getOption3());
            _ContentDataNew.setTimeCreated(WebParamUtil.getDateValue(_ContentDataForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_ContentDataForm.getTimeCreated());
            _ContentDataNew.setTimeUpdated(WebParamUtil.getDateValue(_ContentDataForm.getTimeUpdated()));
            m_logger.debug("setting TimeUpdated=" +_ContentDataForm.getTimeUpdated());

            try {
                checkDepedenceIntegrity(_ContentDataNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _ContentDataNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_ContentDataNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_ContentDataNew);
            try{
                m_actionExtent.afterAdd(request, response, _ContentDataNew);
            }
            catch (Exception e) {
	            if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "content_data_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, ContentDataForm _ContentDataForm, ContentData _ContentData) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentData _ContentData = m_ds.getById(cid);

        m_logger.debug("Before update " + ContentDataDS.objectToString(_ContentData));

        _ContentData.setContentId(WebParamUtil.getLongValue(_ContentDataForm.getContentId()));
        _ContentData.setData(WebParamUtil.getStringValue(_ContentDataForm.getData()));
        _ContentData.setOption1(WebParamUtil.getStringValue(_ContentDataForm.getOption1()));
        _ContentData.setOption2(WebParamUtil.getStringValue(_ContentDataForm.getOption2()));
        _ContentData.setOption3(WebParamUtil.getStringValue(_ContentDataForm.getOption3()));
        _ContentData.setTimeUpdated(WebParamUtil.getDateValue(_ContentDataForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _ContentData);
        m_ds.update(_ContentData);
        m_actionExtent.afterUpdate(request, response, _ContentData);
        m_logger.debug("After update " + ContentDataDS.objectToString(_ContentData));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, ContentData _ContentData) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentData _ContentData = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentId"))) {
            m_logger.debug("updating param contentId from " +_ContentData.getContentId() + "->" + request.getParameter("contentId"));
            _ContentData.setContentId(WebParamUtil.getLongValue(request.getParameter("contentId")));
        }
        if (!isMissing(request.getParameter("data"))) {
            m_logger.debug("updating param data from " +_ContentData.getData() + "->" + request.getParameter("data"));
            _ContentData.setData(WebParamUtil.getStringValue(request.getParameter("data")));
        }
        if (!isMissing(request.getParameter("option1"))) {
            m_logger.debug("updating param option1 from " +_ContentData.getOption1() + "->" + request.getParameter("option1"));
            _ContentData.setOption1(WebParamUtil.getStringValue(request.getParameter("option1")));
        }
        if (!isMissing(request.getParameter("option2"))) {
            m_logger.debug("updating param option2 from " +_ContentData.getOption2() + "->" + request.getParameter("option2"));
            _ContentData.setOption2(WebParamUtil.getStringValue(request.getParameter("option2")));
        }
        if (!isMissing(request.getParameter("option3"))) {
            m_logger.debug("updating param option3 from " +_ContentData.getOption3() + "->" + request.getParameter("option3"));
            _ContentData.setOption3(WebParamUtil.getStringValue(request.getParameter("option3")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_ContentData.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _ContentData.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_ContentData.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _ContentData.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }
        _ContentData.setTimeUpdated(new TimeNow());

        m_actionExtent.beforeUpdate(request, response, _ContentData);
        m_ds.update(_ContentData);
        m_actionExtent.afterUpdate(request, response, _ContentData);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, ContentData _ContentData) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        ContentData _ContentData = m_ds.getById(cid);

        if (!isMissing(request.getParameter("contentId"))) {
			return String.valueOf(_ContentData.getContentId());
        }
        if (!isMissing(request.getParameter("data"))) {
			return String.valueOf(_ContentData.getData());
        }
        if (!isMissing(request.getParameter("option1"))) {
			return String.valueOf(_ContentData.getOption1());
        }
        if (!isMissing(request.getParameter("option2"))) {
			return String.valueOf(_ContentData.getOption2());
        }
        if (!isMissing(request.getParameter("option3"))) {
			return String.valueOf(_ContentData.getOption3());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_ContentData.getTimeCreated());
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
			return String.valueOf(_ContentData.getTimeUpdated());
        }
		return null;
    }

    protected void checkDepedenceIntegrity(ContentData _ContentData) throws Exception {
        if (ContentDS.getInstance().getById(_ContentData.getContentId()) == null){
            m_logger.warn("Received an invalid reference by ContentId. content Id=" +_ContentData.getContentId() );
            throw new Exception("Internal Error Occurred.");
        }
    }

    protected String getFieldByName(String fieldName, ContentData _ContentData) {
        if (fieldName == null || fieldName.equals("")|| _ContentData == null) return null;
        
        if (fieldName.equals("contentId")) {
            return WebUtil.display(_ContentData.getContentId());
        }
        if (fieldName.equals("data")) {
            return WebUtil.display(_ContentData.getData());
        }
        if (fieldName.equals("option1")) {
            return WebUtil.display(_ContentData.getOption1());
        }
        if (fieldName.equals("option2")) {
            return WebUtil.display(_ContentData.getOption2());
        }
        if (fieldName.equals("option3")) {
            return WebUtil.display(_ContentData.getOption3());
        }
        if (fieldName.equals("timeCreated")) {
            return WebUtil.display(_ContentData.getTimeCreated());
        }
        if (fieldName.equals("timeUpdated")) {
            return WebUtil.display(_ContentData.getTimeUpdated());
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
            ContentData _ContentData = ContentDataDS.getInstance().getById(id);
            if (_ContentData == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ContentData);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing gethtml getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ContentData _ContentData = ContentDataDS.getInstance().getById(id);
            if ( _ContentData == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _ContentData);
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

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/contentDataAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


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

            if ( ignoreFieldSet || fieldSet.contains("option1")) {
                String value = WebUtil.display(request.getParameter("option1"));

                if ( forceHiddenSet.contains("option1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"option1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Option1</div>");
            buf.append("<INPUT NAME=\"option1\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("option2")) {
                String value = WebUtil.display(request.getParameter("option2"));

                if ( forceHiddenSet.contains("option2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"option2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Option2</div>");
            buf.append("<INPUT NAME=\"option2\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("option3")) {
                String value = WebUtil.display(request.getParameter("option3"));

                if ( forceHiddenSet.contains("option3")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"option3\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Option3</div>");
            buf.append("<INPUT NAME=\"option3\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            buf.append("<a id=\"ajaxSubmit\" href=\"javascript:sendFormAjax('/contentDataAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit', 'ajaxSubmitResult');\">Submit</a>");
            
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

            buf.append("<div id=\"contentData-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ContentData _ContentData = (ContentData) iterator.next();

                buf.append("<div id=\"contentData-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("data")) {
                    buf.append("<div id=\"contentData-ajax-data\" "+itemClass+">");
                    buf.append(WebUtil.display(_ContentData.getData()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("option1")) {
                    buf.append("<div id=\"contentData-ajax-option1\" "+itemClass+">");
                    buf.append(WebUtil.display(_ContentData.getOption1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("option2")) {
                    buf.append("<div id=\"contentData-ajax-option2\" "+itemClass+">");
                    buf.append(WebUtil.display(_ContentData.getOption2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("option3")) {
                    buf.append("<div id=\"contentData-ajax-option3\" "+itemClass+">");
                    buf.append(WebUtil.display(_ContentData.getOption3()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"contentData-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ContentData.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"contentData-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ContentData.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("data")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Data");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("option1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Option1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("option2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Option2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("option3")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Option3");
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
                ContentData _ContentData = (ContentData) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("data")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ContentData.getData()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("option1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ContentData.getOption1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("option2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ContentData.getOption2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("option3")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ContentData.getOption3()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ContentData.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ContentData.getTimeUpdated()));

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
            ContentData _ContentData = null; 
            List list = ContentDataDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _ContentData = (ContentData) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ContentData = (ContentData) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ContentData = ContentDataDS.getInstance().getById(id);
            }

            m_logger.debug("Getting the last ContentData=" + _ContentData.getId());

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                _ContentData = (ContentData) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("contentId")) 
			            json.put("contentId", ""+_ContentData.getContentId());
		            if ( ignoreFieldSet || fieldSet.contains("data")) 
			            json.put("data", ""+_ContentData.getData());
		            if ( ignoreFieldSet || fieldSet.contains("option1")) 
			            json.put("option1", ""+_ContentData.getOption1());
		            if ( ignoreFieldSet || fieldSet.contains("option2")) 
			            json.put("option2", ""+_ContentData.getOption2());
		            if ( ignoreFieldSet || fieldSet.contains("option3")) 
			            json.put("option3", ""+_ContentData.getOption3());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

	            top.put("id", ""+_ContentData.getId());

	            JSONArray array = new JSONArray();

				// Fields
	            JSONObject jsonContentId = new JSONObject();
	            jsonContentId.put("name", "contentId");
	            jsonContentId.put("value", ""+_ContentData.getContentId());
	            array.put(jsonContentId);

	            JSONObject jsonData = new JSONObject();
	            jsonData.put("name", "data");
	            jsonData.put("value", ""+_ContentData.getData());
	            array.put(jsonData);

	            JSONObject jsonOption1 = new JSONObject();
	            jsonOption1.put("name", "option1");
	            jsonOption1.put("value", ""+_ContentData.getOption1());
	            array.put(jsonOption1);

	            JSONObject jsonOption2 = new JSONObject();
	            jsonOption2.put("name", "option2");
	            jsonOption2.put("value", ""+_ContentData.getOption2());
	            array.put(jsonOption2);

	            JSONObject jsonOption3 = new JSONObject();
	            jsonOption3.put("name", "option3");
	            jsonOption3.put("value", ""+_ContentData.getOption3());
	            array.put(jsonOption3);


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
            ContentData _ContentData = null; 
            List list = ContentDataDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null || arg.equals("last")){
                if (list.size() > 0)
                    _ContentData = (ContentData) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ContentData = (ContentData) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ContentData = ContentDataDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_ContentData);

        } else {
            
            List list = ContentDataDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }



    protected boolean loginRequired() {
        return true;
    }
    
    protected ContentDataDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( ContentDataAction.class);
}
