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
import com.autosite.db.AutositeSynchLedger;
					
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.AutositeSessionContextEntity;
import com.autosite.ds.AutositeSessionContextEntityDS;
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
import com.autosite.struts.form.AutositeSessionContextEntityForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.AutositeSessionContextEntityDataHolder;


public class AutositeSessionContextEntityAjaxAction extends AutositeSessionContextEntityAction {

    public AutositeSessionContextEntityAjaxAction(){
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
        
        AutositeSessionContextEntity target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (isActionCmd(request)){
            m_logger.debug("AjaxRequest contains ActionCommand. So will process it first");
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (AutositeSessionContextEntity) working.get("target");
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
            AutositeSessionContextEntity _AutositeSessionContextEntity = AutositeSessionContextEntityDS.getInstance().getById(id);
            if (_AutositeSessionContextEntity == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _AutositeSessionContextEntity);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            AutositeSessionContextEntity _AutositeSessionContextEntity = AutositeSessionContextEntityDS.getInstance().getById(id);
            if ( _AutositeSessionContextEntity == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _AutositeSessionContextEntity);
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

            buf.append("<div id=\"autositeSessionContextEntity-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                AutositeSessionContextEntity _AutositeSessionContextEntity = (AutositeSessionContextEntity) iterator.next();

                buf.append("<div id=\"autositeSessionContextEntity-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("serial")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-serial\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getSerial()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isLogin")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-isLogin\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getIsLogin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeLogin")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-timeLogin\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeLogin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeLastAccess")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-timeLastAccess\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeLastAccess()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("loginUserId")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-loginUserId\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getLoginUserId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("sessionType")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-sessionType\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getSessionType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("remoteDeviceId")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-remoteDeviceId\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getRemoteDeviceId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-remoteIp\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getRemoteIp()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"autositeSessionContextEntity-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("serial")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Serial");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isLogin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Login");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeLogin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Login");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeLastAccess")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Last Access");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("loginUserId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Login User Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("sessionType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Session Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("remoteDeviceId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Remote Device Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Remote Ip");
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
                AutositeSessionContextEntity _AutositeSessionContextEntity = (AutositeSessionContextEntity) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("serial")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getSerial()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isLogin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getIsLogin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeLogin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeLogin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeLastAccess")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeLastAccess()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("loginUserId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getLoginUserId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("sessionType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getSessionType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("remoteDeviceId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getRemoteDeviceId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getRemoteIp()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeSessionContextEntity.getTimeUpdated()));

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

            List fieldsTitle = AutositeSessionContextEntityDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                AutositeSessionContextEntityDataHolder _AutositeSessionContextEntity  = new AutositeSessionContextEntityDataHolder( (AutositeSessionContextEntity) iterator.next());
                gen.addTableRow(_AutositeSessionContextEntity);
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

                top.put("type", "list");
	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                AutositeSessionContextEntity _AutositeSessionContextEntity = (AutositeSessionContextEntity) iterator.next();

		            JSONObject json = new JSONObject();

		            json.put("id", ""+_AutositeSessionContextEntity.getId());

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("serial")) 
			            json.put("serial", ""+_AutositeSessionContextEntity.getSerial());
		            if ( ignoreFieldSet || fieldSet.contains("isLogin")) 
			            json.put("isLogin", ""+_AutositeSessionContextEntity.getIsLogin());
		            if ( ignoreFieldSet || fieldSet.contains("timeLogin")) 
			            json.put("timeLogin", ""+_AutositeSessionContextEntity.getTimeLogin());
		            if ( ignoreFieldSet || fieldSet.contains("timeLastAccess")) 
			            json.put("timeLastAccess", ""+_AutositeSessionContextEntity.getTimeLastAccess());
		            if ( ignoreFieldSet || fieldSet.contains("loginUserId")) 
			            json.put("loginUserId", ""+_AutositeSessionContextEntity.getLoginUserId());
		            if ( ignoreFieldSet || fieldSet.contains("sessionType")) 
			            json.put("sessionType", ""+_AutositeSessionContextEntity.getSessionType());
		            if ( ignoreFieldSet || fieldSet.contains("remoteDeviceId")) 
			            json.put("remoteDeviceId", ""+_AutositeSessionContextEntity.getRemoteDeviceId());
		            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) 
			            json.put("remoteIp", ""+_AutositeSessionContextEntity.getRemoteIp());
		            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
			            json.put("timeCreated", ""+_AutositeSessionContextEntity.getTimeCreated());
		            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
			            json.put("timeUpdated", ""+_AutositeSessionContextEntity.getTimeUpdated());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                AutositeSessionContextEntity _AutositeSessionContextEntity = list.size() >=1?(AutositeSessionContextEntity) list.get(0): null; 

				if ( _AutositeSessionContextEntity != null) {
	                top.put("type", "object");
		            top.put("id", ""+_AutositeSessionContextEntity.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonSerial = new JSONObject();
		            jsonSerial.put("name", "serial");
		            jsonSerial.put("value", ""+_AutositeSessionContextEntity.getSerial());
		            array.put(jsonSerial);
		            JSONObject jsonIsLogin = new JSONObject();
		            jsonIsLogin.put("name", "isLogin");
		            jsonIsLogin.put("value", ""+_AutositeSessionContextEntity.getIsLogin());
		            array.put(jsonIsLogin);
		            JSONObject jsonTimeLogin = new JSONObject();
		            jsonTimeLogin.put("name", "timeLogin");
		            jsonTimeLogin.put("value", ""+_AutositeSessionContextEntity.getTimeLogin());
		            array.put(jsonTimeLogin);
		            JSONObject jsonTimeLastAccess = new JSONObject();
		            jsonTimeLastAccess.put("name", "timeLastAccess");
		            jsonTimeLastAccess.put("value", ""+_AutositeSessionContextEntity.getTimeLastAccess());
		            array.put(jsonTimeLastAccess);
		            JSONObject jsonLoginUserId = new JSONObject();
		            jsonLoginUserId.put("name", "loginUserId");
		            jsonLoginUserId.put("value", ""+_AutositeSessionContextEntity.getLoginUserId());
		            array.put(jsonLoginUserId);
		            JSONObject jsonSessionType = new JSONObject();
		            jsonSessionType.put("name", "sessionType");
		            jsonSessionType.put("value", ""+_AutositeSessionContextEntity.getSessionType());
		            array.put(jsonSessionType);
		            JSONObject jsonRemoteDeviceId = new JSONObject();
		            jsonRemoteDeviceId.put("name", "remoteDeviceId");
		            jsonRemoteDeviceId.put("value", ""+_AutositeSessionContextEntity.getRemoteDeviceId());
		            array.put(jsonRemoteDeviceId);
		            JSONObject jsonRemoteIp = new JSONObject();
		            jsonRemoteIp.put("name", "remoteIp");
		            jsonRemoteIp.put("value", ""+_AutositeSessionContextEntity.getRemoteIp());
		            array.put(jsonRemoteIp);
	    	        top.put("fields", array);
	                AutositeSynchLedger synchLedger = null;
	                try {
	                    synchLedger = processSynchRequest(request, _AutositeSessionContextEntity);
                        if ( synchLedger != null)
                            top.put("_synchId", synchLedger.getSynchId());
                    }
                    catch (Exception e) {
                        m_logger.error(e.getMessage(),e);
                    }
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
            buf.append("sendFormAjax('/autositeSessionContextEntityAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/autositeSessionContextEntityAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("serial")) {
                String value = WebUtil.display(request.getParameter("serial"));

                if ( forceHiddenSet.contains("serial")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"serial\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Serial</div>");
            buf.append("<INPUT NAME=\"serial\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isLogin")) {
                String value = WebUtil.display(request.getParameter("isLogin"));

                if ( forceHiddenSet.contains("isLogin")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isLogin\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Login</div>");
            buf.append("<INPUT NAME=\"isLogin\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeLogin")) {
                String value = WebUtil.display(request.getParameter("timeLogin"));

                if ( forceHiddenSet.contains("timeLogin")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeLogin\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Login</div>");
            buf.append("<INPUT NAME=\"timeLogin\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeLastAccess")) {
                String value = WebUtil.display(request.getParameter("timeLastAccess"));

                if ( forceHiddenSet.contains("timeLastAccess")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeLastAccess\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Last Access</div>");
            buf.append("<INPUT NAME=\"timeLastAccess\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("loginUserId")) {
                String value = WebUtil.display(request.getParameter("loginUserId"));

                if ( forceHiddenSet.contains("loginUserId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"loginUserId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Login User Id</div>");
            buf.append("<INPUT NAME=\"loginUserId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("sessionType")) {
                String value = WebUtil.display(request.getParameter("sessionType"));

                if ( forceHiddenSet.contains("sessionType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"sessionType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Session Type</div>");
            buf.append("<INPUT NAME=\"sessionType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("remoteDeviceId")) {
                String value = WebUtil.display(request.getParameter("remoteDeviceId"));

                if ( forceHiddenSet.contains("remoteDeviceId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"remoteDeviceId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Remote Device Id</div>");
            buf.append("<INPUT NAME=\"remoteDeviceId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) {
                String value = WebUtil.display(request.getParameter("remoteIp"));

                if ( forceHiddenSet.contains("remoteIp")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"remoteIp\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Remote Ip</div>");
            buf.append("<INPUT NAME=\"remoteIp\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_autositeSessionContextEntity(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayAutositeSessionContextEntity\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_autositeSessionContextEntity(){\n";
            importedScripts +=     "xmlhttpPostXX('autositeSessionContextEntityFormAddDis','/autositeSessionContextEntityAction.html', 'resultDisplayAutositeSessionContextEntity', '${ajax_response_fields}', responseCallback_autositeSessionContextEntity);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_autositeSessionContextEntity(){\n";
            importedScripts +=     "clearFormXX('autositeSessionContextEntityFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_autositeSessionContextEntity(){\n";
            importedScripts +=     "backToXX('autositeSessionContextEntityFormAddDis','resultDisplayAutositeSessionContextEntity');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"autositeSessionContextEntityFormAddDis\" method=\"POST\" action=\"/autositeSessionContextEntityAction.html\" id=\"autositeSessionContextEntityFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Serial</div>");
        buf.append("<input class=\"field\" id=\"serial\" type=\"text\" size=\"70\" name=\"serial\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Login</div>");
        buf.append("<input class=\"field\" id=\"isLogin\" type=\"text\" size=\"70\" name=\"isLogin\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Login</div>");
        buf.append("<input class=\"field\" id=\"timeLogin\" type=\"text\" size=\"70\" name=\"timeLogin\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Last Access</div>");
        buf.append("<input class=\"field\" id=\"timeLastAccess\" type=\"text\" size=\"70\" name=\"timeLastAccess\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Login User Id</div>");
        buf.append("<input class=\"field\" id=\"loginUserId\" type=\"text\" size=\"70\" name=\"loginUserId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Session Type</div>");
        buf.append("<input class=\"field\" id=\"sessionType\" type=\"text\" size=\"70\" name=\"sessionType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Remote Device Id</div>");
        buf.append("<input class=\"field\" id=\"remoteDeviceId\" type=\"text\" size=\"70\" name=\"remoteDeviceId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Remote Ip</div>");
        buf.append("<input class=\"field\" id=\"remoteIp\" type=\"text\" size=\"70\" name=\"remoteIp\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_autositeSessionContextEntity()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_autositeSessionContextEntity()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayAutositeSessionContextEntity\"></span>");
			buf.append("<a href=\"javascript:showform_autositeSessionContextEntity()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, AutositeSessionContextEntity target){

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

    protected List prepareReturnData(HttpServletRequest request, AutositeSessionContextEntity target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = AutositeSessionContextEntityDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            AutositeSessionContextEntity _AutositeSessionContextEntity = null; 
            List list = AutositeSessionContextEntityDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _AutositeSessionContextEntity = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _AutositeSessionContextEntity = (AutositeSessionContextEntity) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _AutositeSessionContextEntity = (AutositeSessionContextEntity) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _AutositeSessionContextEntity = AutositeSessionContextEntityDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_AutositeSessionContextEntity);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( AutositeSessionContextEntityAjaxAction.class);
}
