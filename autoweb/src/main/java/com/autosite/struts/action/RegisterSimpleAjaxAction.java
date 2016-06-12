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
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import com.jtrend.util.RequestUtil;
import com.autosite.util.html.HtmlGen;
					
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.RegisterSimple;
import com.autosite.ds.RegisterSimpleDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.xform.AutositeXform;
import com.autosite.struts.xform.DefaultXformManager;
import com.autosite.struts.xform.XformManager;
import com.autosite.struts.xform.impl.DefaultErrorXform;
import com.autosite.struts.form.RegisterSimpleForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.RegisterSimpleDataHolder;


public class RegisterSimpleAjaxAction extends RegisterSimpleAction {

    public RegisterSimpleAjaxAction(){
    }


    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        HttpSession session = request.getSession();

        // Check permissions 
        Map ret = new HashMap();
        if (!haveAccessToUpdate(session) ){
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            ret.put("__error", "true");
            ret.put("__errorMsg","Permission error occurred.");
            return ret;
        }
        
        RegisterSimple target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (isActionCmd(request)){
            m_logger.debug("AjaxRequest contains ActionCommand. So will process it first");
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (RegisterSimple) working.get("target");
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

		//Modal status will be displayable/explanable to user directly. like "It did successfully" or "not succeeded" kind of text. 
		// So that it can be just displayed to end users. 
        if (hasRequestValue(request, "ajaxOut", "getmodalstatus") ||
            hasRequestValue(request, "ajxr", "getModalStatus")){
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

        } else if (hasRequestValue(request, "ajaxOut", "get2ModalStatus") ||hasRequestValue(request, "ajxr", "get2ModalStatus")){
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

        } else if (hasRequestValue(request, "ajaxOut", "getfield") ||
                   hasRequestValue(request, "ajaxOut", "get2field")||
                   hasRequestValue(request, "ajxr", "getfield")||
                   hasRequestValue(request, "ajxr", "get2field")) {
            m_logger.debug("Ajax Processing getfield/get2field arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            RegisterSimple _RegisterSimple = RegisterSimpleDS.getInstance().getById(id);
            if (_RegisterSimple == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _RegisterSimple);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            RegisterSimple _RegisterSimple = RegisterSimpleDS.getInstance().getById(id);
            if ( _RegisterSimple == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _RegisterSimple);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
		// Returns data in <div>
		// classes 
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ||
                   hasRequestValue(request, "ajxr", "getdata") || hasRequestValue(request, "ajxr", "getlistdata")  ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (WebUtil.isNull(fieldSetStr)? true: false); //IF no fieldString set, return all field

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();

            buf.append("<div id=\"registerSimple-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                RegisterSimple _RegisterSimple = (RegisterSimple) iterator.next();

                buf.append("<div id=\"registerSimple-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
                    buf.append("<div id=\"registerSimple-ajax-firstName\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getFirstName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
                    buf.append("<div id=\"registerSimple-ajax-lastName\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getLastName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("email")) {
                    buf.append("<div id=\"registerSimple-ajax-email\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getEmail()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("username")) {
                    buf.append("<div id=\"registerSimple-ajax-username\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getUsername()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("password")) {
                    buf.append("<div id=\"registerSimple-ajax-password\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getPassword()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
                    buf.append("<div id=\"registerSimple-ajax-birthYear\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getBirthYear()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
                    buf.append("<div id=\"registerSimple-ajax-birthMonth\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getBirthMonth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
                    buf.append("<div id=\"registerSimple-ajax-birthDay\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getBirthDay()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"registerSimple-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"registerSimple-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_RegisterSimple.getTimeUpdated()));
                    buf.append("</div>");

				}
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ||
                   hasRequestValue(request, "ajxr", "gethtml") || hasRequestValue(request, "ajxr", "getlisthtml")  ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();
            
            String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
            String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
            String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
            String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

            buf.append("<table "+ tableStyleStr +" >");

            buf.append("<tr "+ trStyleStr +" >");
            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("First Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Last Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("email")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Email");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("username")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Username");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("password")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Password");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Birth Year");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Birth Month");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Birth Day");
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
                RegisterSimple _RegisterSimple = (RegisterSimple) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getFirstName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getLastName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("email")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getEmail()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("username")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getUsername()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("password")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getPassword()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getBirthYear()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getBirthMonth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getBirthDay()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RegisterSimple.getTimeUpdated()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "gethtml2") || hasRequestValue(request, "ajaxOut", "getlisthtml2") ||
                   hasRequestValue(request, "ajxr", "gethtml2") || hasRequestValue(request, "ajxr", "getlisthtml2")  ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);
            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            HtmlGen gen = new HtmlGen(HtmlGen.TYPE_GEN_ROW, false); // TODO title of the table

            List fieldsTitle = RegisterSimpleDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                RegisterSimpleDataHolder _RegisterSimple  = new RegisterSimpleDataHolder( (RegisterSimple) iterator.next());
                gen.addTableRow(_RegisterSimple);
			}
            
            ret.put("__value", gen.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ||
                   hasRequestValue(request, "ajxr", "getjson") || hasRequestValue(request, "ajxr", "getlistjson")  ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = isAjaxListOutput(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                RegisterSimple _RegisterSimple = (RegisterSimple) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("firstName")) 
			            json.put("firstName", ""+_RegisterSimple.getFirstName());
		            if ( ignoreFieldSet || fieldSet.contains("lastName")) 
			            json.put("lastName", ""+_RegisterSimple.getLastName());
		            if ( ignoreFieldSet || fieldSet.contains("email")) 
			            json.put("email", ""+_RegisterSimple.getEmail());
		            if ( ignoreFieldSet || fieldSet.contains("username")) 
			            json.put("username", ""+_RegisterSimple.getUsername());
		            if ( ignoreFieldSet || fieldSet.contains("password")) 
			            json.put("password", ""+_RegisterSimple.getPassword());
		            if ( ignoreFieldSet || fieldSet.contains("birthYear")) 
			            json.put("birthYear", ""+_RegisterSimple.getBirthYear());
		            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) 
			            json.put("birthMonth", ""+_RegisterSimple.getBirthMonth());
		            if ( ignoreFieldSet || fieldSet.contains("birthDay")) 
			            json.put("birthDay", ""+_RegisterSimple.getBirthDay());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                RegisterSimple _RegisterSimple = list.size() >=1?(RegisterSimple) list.get(0): null; 

				if ( _RegisterSimple != null) {
		            top.put("id", ""+_RegisterSimple.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonFirstName = new JSONObject();
		            jsonFirstName.put("name", "firstName");
		            jsonFirstName.put("value", ""+_RegisterSimple.getFirstName());
		            array.put(jsonFirstName);
		            JSONObject jsonLastName = new JSONObject();
		            jsonLastName.put("name", "lastName");
		            jsonLastName.put("value", ""+_RegisterSimple.getLastName());
		            array.put(jsonLastName);
		            JSONObject jsonEmail = new JSONObject();
		            jsonEmail.put("name", "email");
		            jsonEmail.put("value", ""+_RegisterSimple.getEmail());
		            array.put(jsonEmail);
		            JSONObject jsonUsername = new JSONObject();
		            jsonUsername.put("name", "username");
		            jsonUsername.put("value", ""+_RegisterSimple.getUsername());
		            array.put(jsonUsername);
		            JSONObject jsonPassword = new JSONObject();
		            jsonPassword.put("name", "password");
		            jsonPassword.put("value", ""+_RegisterSimple.getPassword());
		            array.put(jsonPassword);
		            JSONObject jsonBirthYear = new JSONObject();
		            jsonBirthYear.put("name", "birthYear");
		            jsonBirthYear.put("value", ""+_RegisterSimple.getBirthYear());
		            array.put(jsonBirthYear);
		            JSONObject jsonBirthMonth = new JSONObject();
		            jsonBirthMonth.put("name", "birthMonth");
		            jsonBirthMonth.put("value", ""+_RegisterSimple.getBirthMonth());
		            array.put(jsonBirthMonth);
		            JSONObject jsonBirthDay = new JSONObject();
		            jsonBirthDay.put("name", "birthDay");
		            jsonBirthDay.put("value", ""+_RegisterSimple.getBirthDay());
		            array.put(jsonBirthDay);

	    	        top.put("fields", array);
				}
			}

            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform") ||
                   hasRequestValue(request, "ajxr", "getmodalform") || 
                   hasRequestValue(request, "ajxr", "getmodalform") ){
            m_logger.debug("Ajax Processing gethtml getmodalform arg = " + request.getParameter("ajaxOutArg"));
	
			// Example <a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=blogPostId,comment,email&blogPostId=111&forcehiddenlist=email&email=joshua@yahoo.com" rel="facebox">Ajax Add</a>

            // Returns the form for modal form display
            StringBuilder buf = new StringBuilder();
            String _wpId = WebProcManager.registerWebProcess();
            int randNum = RandomUtil.randomInt(1000000);

            String fieldSetStr = request.getParameter("formfieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            String forceHiddenStr = request.getParameter("forcehiddenlist");
            Set forceHiddenSet = JtStringUtil.convertToSet(forceHiddenStr);

            boolean ignoreFieldSet = (fieldSetStr == null||fieldSetStr.equals("_all") ? true: false);


            buf.append("<script type=\"text/javascript\">");
            //buf.append("<!--");
            buf.append("function sendForm_"+ randNum + "(){");
            buf.append("sendFormAjax('/registerSimpleAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/registerSimpleAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
                String value = WebUtil.display(request.getParameter("firstName"));

                if ( forceHiddenSet.contains("firstName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"firstName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">First Name</div>");
            buf.append("<INPUT NAME=\"firstName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
                String value = WebUtil.display(request.getParameter("lastName"));

                if ( forceHiddenSet.contains("lastName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"lastName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Last Name</div>");
            buf.append("<INPUT NAME=\"lastName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("username")) {
                String value = WebUtil.display(request.getParameter("username"));

                if ( forceHiddenSet.contains("username")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"username\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Username</div>");
            buf.append("<INPUT NAME=\"username\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("birthYear")) {
                String value = WebUtil.display(request.getParameter("birthYear"));

                if ( forceHiddenSet.contains("birthYear")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthYear\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthYear\"  value=\""+value+"\">");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("birthMonth")) {
                String value = WebUtil.display(request.getParameter("birthMonth"));

                if ( forceHiddenSet.contains("birthMonth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthMonth\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthMonth\"  value=\""+value+"\">");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("birthDay")) {
                String value = WebUtil.display(request.getParameter("birthDay"));

                if ( forceHiddenSet.contains("birthDay")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthDay\"  value=\""+value+"\">");
                } else {

            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"birthDay\"  value=\""+value+"\">");
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
            buf.append("<span id=\"ajaxSubmitResult"+randNum+"\"></span>");
            buf.append("<a id=\"ajaxSubmit"+randNum+"\" href=\"javascript:sendForm_"+ randNum + "();\">Submit</a>");
            
            //cancel should close modal dialog
            //buf.append("<a id=\"formSubmit\" href=\"\">Cancel</a>");
            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2") || (hasRequestValue(request, "ajaxOut", "getscriptform"))||
                   hasRequestValue(request, "ajxr", "getmodalform2") || (hasRequestValue(request, "ajxr", "getscriptform"))
){

			//This form is called by script such as e.g. <script type="text/javascript" src="/blockListAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>
			// inline_script will be attached to provide functionlities. 
			// This form will be used inside the same site to provide embedded page using <script> tags. But Refer to Poll "inline_script_poll" to 
			// send no-ajax submission. General no-ajax submission is not yet supported. 

            m_logger.debug("Ajax Processing getmodalform2 arg = " + request.getParameter("ajaxOutArg"));
	        StringBuilder buf = new StringBuilder();
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
            importedScripts += "function responseCallback_registerSimple(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayRegisterSimple\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_registerSimple(){\n";
            importedScripts +=     "xmlhttpPostXX('registerSimpleFormAddDis','/registerSimpleAction.html', 'resultDisplayRegisterSimple', '${ajax_response_fields}', responseCallback_registerSimple);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_registerSimple(){\n";
            importedScripts +=     "clearFormXX('registerSimpleFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_registerSimple(){\n";
            importedScripts +=     "backToXX('registerSimpleFormAddDis','resultDisplayRegisterSimple');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"registerSimpleFormAddDis\" method=\"POST\" action=\"/registerSimpleAction.html\" id=\"registerSimpleFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> First Name</div>");
        buf.append("<input class=\"field\" id=\"firstName\" type=\"text\" size=\"70\" name=\"firstName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Last Name</div>");
        buf.append("<input class=\"field\" id=\"lastName\" type=\"text\" size=\"70\" name=\"lastName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Email</div>");
        buf.append("<input class=\"field\" id=\"email\" type=\"text\" size=\"70\" name=\"email\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Username</div>");
        buf.append("<input class=\"field\" id=\"username\" type=\"text\" size=\"70\" name=\"username\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Password</div>");
        buf.append("<input class=\"field\" id=\"password\" type=\"password\" size=\"70\" name=\"password\" value=\"\"/><span></span>");
		buf.append("<br/>");
    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"birthYear\" NAME=\"birthYear\" value=\"\" />");
		buf.append("<br/>");
    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"birthMonth\" NAME=\"birthMonth\" value=\"\" />");
		buf.append("<br/>");
    	buf.append("<INPUT TYPE=\"HIDDEN\" id=\"birthDay\" NAME=\"birthDay\" value=\"\" />");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_registerSimple()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_registerSimple()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayRegisterSimple\"></span>");
			buf.append("<a href=\"javascript:showform_registerSimple()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());
        } else if (hasRequestValue(request, "ajxr", "xform")) {
			// transform implementation for ajax process from ajax request to html returns. 
			// the result will be loaded into web component on the client side. 
			// transform/xform properties file is xform.properties.
			// in that file, template is specified. and will be used to transform. 
			// in properties file, template is loaded by rqid. 

            String ajxrRqid     = getAjaxSubCommand(request, "rqid");
            String ajxrXformId  = getAjaxSubCommand(request, "xform-id");
            m_logger.debug("Ajax xform Processing --------> " + ajxrRqid);
            
            List list = prepareReturnData(request, target);

            AutositeXform xfmgr = DefaultXformManager.getInstance().getXform(ajxrRqid);
            try {
                ret.put("__value", xfmgr.transform(list)); //DO TRANSFORM!! 
            }
            catch (Exception e) {
                ret.put("__value", new DefaultErrorXform().transform(list)); //DO TRANSFORM!! 
            }
            
            
        } else if (hasRequestValue(request, "ajaxOut", "hb") ||
                   hasRequestValue(request, "ajxr", "hb") ){
            m_logger.debug("Ajax Processing --------> ");
            ret.put("__value", "success:" + new Date());
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
    protected List prepareReturnData(HttpServletRequest request, RegisterSimple target){

        boolean returnList = request.getParameter("ajaxOut") != null && 
                ( request.getParameter("ajaxOut").startsWith("getlist") ||  
                  request.getParameter("ajaxOut").startsWith("getlisthtml") ||  
                  request.getParameter("ajaxOut").startsWith("getlistjson"));

        boolean returnList2 = request.getParameter("ajxr") != null && 
                ( request.getParameter("ajxr").startsWith("getlist") ||  
                  request.getParameter("ajxr").startsWith("getlisthtml") ||  
                  request.getParameter("ajxr").startsWith("getlistjson"));

		return prepareReturnData(request, target, returnList||returnList2);
	}

    protected List prepareReturnData(HttpServletRequest request, RegisterSimple target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = RegisterSimpleDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            RegisterSimple _RegisterSimple = null; 
            List list = RegisterSimpleDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _RegisterSimple = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _RegisterSimple = (RegisterSimple) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _RegisterSimple = (RegisterSimple) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _RegisterSimple = RegisterSimpleDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_RegisterSimple);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( RegisterSimpleAjaxAction.class);
}
