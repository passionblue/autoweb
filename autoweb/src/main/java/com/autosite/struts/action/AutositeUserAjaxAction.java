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

import com.autosite.db.AutositeUser;
import com.autosite.ds.AutositeUserDS;
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
import com.autosite.struts.form.AutositeUserForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.AutositeUserDataHolder;


public class AutositeUserAjaxAction extends AutositeUserAction {

    public AutositeUserAjaxAction(){
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
        
        AutositeUser target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (isActionCmd(request)){
            m_logger.debug("AjaxRequest contains ActionCommand. So will process it first");
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (AutositeUser) working.get("target");
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
            AutositeUser _AutositeUser = AutositeUserDS.getInstance().getById(id);
            if (_AutositeUser == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _AutositeUser);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            AutositeUser _AutositeUser = AutositeUserDS.getInstance().getById(id);
            if ( _AutositeUser == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _AutositeUser);
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

            buf.append("<div id=\"autositeUser-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                AutositeUser _AutositeUser = (AutositeUser) iterator.next();

                buf.append("<div id=\"autositeUser-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("username")) {
                    buf.append("<div id=\"autositeUser-ajax-username\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getUsername()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("password")) {
                    buf.append("<div id=\"autositeUser-ajax-password\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getPassword()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("email")) {
                    buf.append("<div id=\"autositeUser-ajax-email\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getEmail()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("userType")) {
                    buf.append("<div id=\"autositeUser-ajax-userType\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getUserType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
                    buf.append("<div id=\"autositeUser-ajax-firstName\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getFirstName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
                    buf.append("<div id=\"autositeUser-ajax-lastName\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getLastName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("nickname")) {
                    buf.append("<div id=\"autositeUser-ajax-nickname\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getNickname()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"autositeUser-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"autositeUser-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getTimeUpdated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
                    buf.append("<div id=\"autositeUser-ajax-disabled\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getDisabled()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeDisabled")) {
                    buf.append("<div id=\"autositeUser-ajax-timeDisabled\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getTimeDisabled()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("confirmed")) {
                    buf.append("<div id=\"autositeUser-ajax-confirmed\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getConfirmed()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeConfirmed")) {
                    buf.append("<div id=\"autositeUser-ajax-timeConfirmed\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getTimeConfirmed()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pagelessSession")) {
                    buf.append("<div id=\"autositeUser-ajax-pagelessSession\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getPagelessSession()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("opt1")) {
                    buf.append("<div id=\"autositeUser-ajax-opt1\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getOpt1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("opt2")) {
                    buf.append("<div id=\"autositeUser-ajax-opt2\" "+itemClass+">");
                    buf.append(WebUtil.display(_AutositeUser.getOpt2()));
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
            if ( ignoreFieldSet || fieldSet.contains("email")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Email");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("userType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("User Type");
	            buf.append("</td>");
			}
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
            if ( ignoreFieldSet || fieldSet.contains("nickname")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Nickname");
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
            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Disabled");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeDisabled")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Disabled");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("confirmed")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Confirmed");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeConfirmed")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Confirmed");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pagelessSession")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Pageless Session");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("opt1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Opt 1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("opt2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Opt 2");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                AutositeUser _AutositeUser = (AutositeUser) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("username")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getUsername()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("password")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getPassword()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("email")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getEmail()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("userType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getUserType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("firstName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getFirstName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("lastName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getLastName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("nickname")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getNickname()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getTimeUpdated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_AutositeUser.getDisabled()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/autositeUserAction.html?ef=true&id="+ _AutositeUser.getId()+"&disabled=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/autositeUserAction.html?ef=true&id="+ _AutositeUser.getId()+"&disabled=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeDisabled")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getTimeDisabled()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("confirmed")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_AutositeUser.getConfirmed()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/autositeUserAction.html?ef=true&id="+ _AutositeUser.getId()+"&confirmed=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/autositeUserAction.html?ef=true&id="+ _AutositeUser.getId()+"&confirmed=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeConfirmed")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getTimeConfirmed()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pagelessSession")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_AutositeUser.getPagelessSession()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/autositeUserAction.html?ef=true&id="+ _AutositeUser.getId()+"&pagelessSession=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/autositeUserAction.html?ef=true&id="+ _AutositeUser.getId()+"&pagelessSession=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("opt1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getOpt1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("opt2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_AutositeUser.getOpt2()));

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

            List fieldsTitle = AutositeUserDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                AutositeUserDataHolder _AutositeUser  = new AutositeUserDataHolder( (AutositeUser) iterator.next());
                gen.addTableRow(_AutositeUser);
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
	                AutositeUser _AutositeUser = (AutositeUser) iterator.next();

		            JSONObject json = new JSONObject();

		            json.put("id", ""+_AutositeUser.getId());

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("username")) 
			            json.put("username", ""+_AutositeUser.getUsername());
		            if ( ignoreFieldSet || fieldSet.contains("password")) 
			            json.put("password", ""+_AutositeUser.getPassword());
		            if ( ignoreFieldSet || fieldSet.contains("email")) 
			            json.put("email", ""+_AutositeUser.getEmail());
		            if ( ignoreFieldSet || fieldSet.contains("userType")) 
			            json.put("userType", ""+_AutositeUser.getUserType());
		            if ( ignoreFieldSet || fieldSet.contains("firstName")) 
			            json.put("firstName", ""+_AutositeUser.getFirstName());
		            if ( ignoreFieldSet || fieldSet.contains("lastName")) 
			            json.put("lastName", ""+_AutositeUser.getLastName());
		            if ( ignoreFieldSet || fieldSet.contains("nickname")) 
			            json.put("nickname", ""+_AutositeUser.getNickname());
		            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
			            json.put("timeCreated", ""+_AutositeUser.getTimeCreated());
		            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
			            json.put("timeUpdated", ""+_AutositeUser.getTimeUpdated());
		            if ( ignoreFieldSet || fieldSet.contains("disabled")) 
			            json.put("disabled", ""+_AutositeUser.getDisabled());
		            if ( ignoreFieldSet || fieldSet.contains("timeDisabled")) 
			            json.put("timeDisabled", ""+_AutositeUser.getTimeDisabled());
		            if ( ignoreFieldSet || fieldSet.contains("confirmed")) 
			            json.put("confirmed", ""+_AutositeUser.getConfirmed());
		            if ( ignoreFieldSet || fieldSet.contains("timeConfirmed")) 
			            json.put("timeConfirmed", ""+_AutositeUser.getTimeConfirmed());
		            if ( ignoreFieldSet || fieldSet.contains("pagelessSession")) 
			            json.put("pagelessSession", ""+_AutositeUser.getPagelessSession());
		            if ( ignoreFieldSet || fieldSet.contains("opt1")) 
			            json.put("opt1", ""+_AutositeUser.getOpt1());
		            if ( ignoreFieldSet || fieldSet.contains("opt2")) 
			            json.put("opt2", ""+_AutositeUser.getOpt2());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                AutositeUser _AutositeUser = list.size() >=1?(AutositeUser) list.get(0): null; 

				if ( _AutositeUser != null) {
		            top.put("id", ""+_AutositeUser.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonUsername = new JSONObject();
		            jsonUsername.put("name", "username");
		            jsonUsername.put("value", ""+_AutositeUser.getUsername());
		            array.put(jsonUsername);
		            JSONObject jsonPassword = new JSONObject();
		            jsonPassword.put("name", "password");
		            jsonPassword.put("value", ""+_AutositeUser.getPassword());
		            array.put(jsonPassword);
		            JSONObject jsonEmail = new JSONObject();
		            jsonEmail.put("name", "email");
		            jsonEmail.put("value", ""+_AutositeUser.getEmail());
		            array.put(jsonEmail);
		            JSONObject jsonUserType = new JSONObject();
		            jsonUserType.put("name", "userType");
		            jsonUserType.put("value", ""+_AutositeUser.getUserType());
		            array.put(jsonUserType);
		            JSONObject jsonFirstName = new JSONObject();
		            jsonFirstName.put("name", "firstName");
		            jsonFirstName.put("value", ""+_AutositeUser.getFirstName());
		            array.put(jsonFirstName);
		            JSONObject jsonLastName = new JSONObject();
		            jsonLastName.put("name", "lastName");
		            jsonLastName.put("value", ""+_AutositeUser.getLastName());
		            array.put(jsonLastName);
		            JSONObject jsonNickname = new JSONObject();
		            jsonNickname.put("name", "nickname");
		            jsonNickname.put("value", ""+_AutositeUser.getNickname());
		            array.put(jsonNickname);
		            JSONObject jsonDisabled = new JSONObject();
		            jsonDisabled.put("name", "disabled");
		            jsonDisabled.put("value", ""+_AutositeUser.getDisabled());
		            array.put(jsonDisabled);
		            JSONObject jsonTimeDisabled = new JSONObject();
		            jsonTimeDisabled.put("name", "timeDisabled");
		            jsonTimeDisabled.put("value", ""+_AutositeUser.getTimeDisabled());
		            array.put(jsonTimeDisabled);
		            JSONObject jsonConfirmed = new JSONObject();
		            jsonConfirmed.put("name", "confirmed");
		            jsonConfirmed.put("value", ""+_AutositeUser.getConfirmed());
		            array.put(jsonConfirmed);
		            JSONObject jsonTimeConfirmed = new JSONObject();
		            jsonTimeConfirmed.put("name", "timeConfirmed");
		            jsonTimeConfirmed.put("value", ""+_AutositeUser.getTimeConfirmed());
		            array.put(jsonTimeConfirmed);
		            JSONObject jsonPagelessSession = new JSONObject();
		            jsonPagelessSession.put("name", "pagelessSession");
		            jsonPagelessSession.put("value", ""+_AutositeUser.getPagelessSession());
		            array.put(jsonPagelessSession);
		            JSONObject jsonOpt1 = new JSONObject();
		            jsonOpt1.put("name", "opt1");
		            jsonOpt1.put("value", ""+_AutositeUser.getOpt1());
		            array.put(jsonOpt1);
		            JSONObject jsonOpt2 = new JSONObject();
		            jsonOpt2.put("name", "opt2");
		            jsonOpt2.put("value", ""+_AutositeUser.getOpt2());
		            array.put(jsonOpt2);

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
            buf.append("sendFormAjax('/autositeUserAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/autositeUserAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


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
            buf.append("<INPUT NAME=\"password\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("userType")) {
                String value = WebUtil.display(request.getParameter("userType"));

                if ( forceHiddenSet.contains("userType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"userType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">User Type</div>");
            buf.append("<INPUT NAME=\"userType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

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

            if ( ignoreFieldSet || fieldSet.contains("nickname")) {
                String value = WebUtil.display(request.getParameter("nickname"));

                if ( forceHiddenSet.contains("nickname")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"nickname\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Nickname</div>");
            buf.append("<INPUT NAME=\"nickname\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
                String value = WebUtil.display(request.getParameter("disabled"));

                if ( forceHiddenSet.contains("disabled")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"disabled\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Disabled</div>");
            buf.append("<select name=\"disabled\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeDisabled")) {
                String value = WebUtil.display(request.getParameter("timeDisabled"));

                if ( forceHiddenSet.contains("timeDisabled")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeDisabled\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Disabled</div>");
            buf.append("<INPUT NAME=\"timeDisabled\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("confirmed")) {
                String value = WebUtil.display(request.getParameter("confirmed"));

                if ( forceHiddenSet.contains("confirmed")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"confirmed\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Confirmed</div>");
            buf.append("<select name=\"confirmed\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeConfirmed")) {
                String value = WebUtil.display(request.getParameter("timeConfirmed"));

                if ( forceHiddenSet.contains("timeConfirmed")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeConfirmed\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Confirmed</div>");
            buf.append("<INPUT NAME=\"timeConfirmed\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pagelessSession")) {
                String value = WebUtil.display(request.getParameter("pagelessSession"));

                if ( forceHiddenSet.contains("pagelessSession")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pagelessSession\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Pageless Session</div>");
            buf.append("<select name=\"pagelessSession\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("opt1")) {
                String value = WebUtil.display(request.getParameter("opt1"));

                if ( forceHiddenSet.contains("opt1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"opt1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Opt 1</div>");
            buf.append("<INPUT NAME=\"opt1\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("opt2")) {
                String value = WebUtil.display(request.getParameter("opt2"));

                if ( forceHiddenSet.contains("opt2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"opt2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Opt 2</div>");
            buf.append("<INPUT NAME=\"opt2\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_autositeUser(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayAutositeUser\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_autositeUser(){\n";
            importedScripts +=     "xmlhttpPostXX('autositeUserFormAddDis','/autositeUserAction.html', 'resultDisplayAutositeUser', '${ajax_response_fields}', responseCallback_autositeUser);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_autositeUser(){\n";
            importedScripts +=     "clearFormXX('autositeUserFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_autositeUser(){\n";
            importedScripts +=     "backToXX('autositeUserFormAddDis','resultDisplayAutositeUser');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"autositeUserFormAddDis\" method=\"POST\" action=\"/autositeUserAction.html\" id=\"autositeUserFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Username</div>");
        buf.append("<input class=\"field\" id=\"username\" type=\"text\" size=\"70\" name=\"username\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Password</div>");
        buf.append("<input class=\"field\" id=\"password\" type=\"text\" size=\"70\" name=\"password\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Email</div>");
        buf.append("<input class=\"field\" id=\"email\" type=\"text\" size=\"70\" name=\"email\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> User Type</div>");
        buf.append("<input class=\"field\" id=\"userType\" type=\"text\" size=\"70\" name=\"userType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> First Name</div>");
        buf.append("<input class=\"field\" id=\"firstName\" type=\"text\" size=\"70\" name=\"firstName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Last Name</div>");
        buf.append("<input class=\"field\" id=\"lastName\" type=\"text\" size=\"70\" name=\"lastName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Nickname</div>");
        buf.append("<input class=\"field\" id=\"nickname\" type=\"text\" size=\"70\" name=\"nickname\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Disabled</div>");
        buf.append("<select name=\"disabled\" id=\"disabled\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Disabled</div>");
        buf.append("<input class=\"field\" id=\"timeDisabled\" type=\"text\" size=\"70\" name=\"timeDisabled\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Confirmed</div>");
        buf.append("<select name=\"confirmed\" id=\"confirmed\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Confirmed</div>");
        buf.append("<input class=\"field\" id=\"timeConfirmed\" type=\"text\" size=\"70\" name=\"timeConfirmed\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Pageless Session</div>");
        buf.append("<select name=\"pagelessSession\" id=\"pagelessSession\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Opt 1</div>");
        buf.append("<input class=\"field\" id=\"opt1\" type=\"text\" size=\"70\" name=\"opt1\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Opt 2</div>");
        buf.append("<input class=\"field\" id=\"opt2\" type=\"text\" size=\"70\" name=\"opt2\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_autositeUser()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_autositeUser()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayAutositeUser\"></span>");
			buf.append("<a href=\"javascript:showform_autositeUser()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, AutositeUser target){

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

    protected List prepareReturnData(HttpServletRequest request, AutositeUser target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = AutositeUserDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            AutositeUser _AutositeUser = null; 
            List list = AutositeUserDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _AutositeUser = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _AutositeUser = (AutositeUser) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _AutositeUser = (AutositeUser) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _AutositeUser = AutositeUserDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_AutositeUser);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( AutositeUserAjaxAction.class);
}
