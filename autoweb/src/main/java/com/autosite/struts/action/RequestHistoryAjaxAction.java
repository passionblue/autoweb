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
import com.autosite.ds.AutositeSynchLedgerDS;
import com.jtrend.util.JtStringUtil;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import com.autosite.session.devicesynch.DeviceSynchTracker;
import com.autosite.session.devicesynch.SynchTrackingEntry;
import com.autosite.session.devicesynch.AutositeLedgerSynchTrackingManager;
import com.jtrend.util.RequestUtil;
import com.autosite.util.html.HtmlGen;
import com.autosite.db.AutositeSynchLedger;
import com.autosite.db.AutositeRemoteDevice;
import com.autosite.ds.AutositeRemoteDeviceDS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.RequestHistory;
import com.autosite.ds.RequestHistoryDS;
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
import com.autosite.struts.form.RequestHistoryForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.RequestHistoryDataHolder;


public class RequestHistoryAjaxAction extends RequestHistoryAction {

    private static Logger m_logger = Logger.getLogger( RequestHistoryAjaxAction.class);

    public RequestHistoryAjaxAction(){
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
        
        RequestHistory target = null;
        AutositeSynchLedger synchLedger = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (isActionCmd(request)){
            if ( isThere(request, "_synchCmd")){
                if (hasRequestValue(request, "_synchCmd", "remote-create")){
                
                } else if (hasRequestValue(request, "_synchCmd", "remote-update")){

                } else if (hasRequestValue(request, "_synchCmd", "remote-delete")){

                } else {
                    
                }
            }
            m_logger.debug("AjaxRequest contains ActionCommand. So will process it first");
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (RequestHistory) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = RequestHistoryDS.getInstance().getById(synchLedger.getObjectId());
	                }
                } else {
                    m_logger.error(e.getMessage(),e);
                    ret.put("__error", "true");
                    ret.put("__errorMsg", e.getMessage());
                }
            } catch (ActionExtentException e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorCode", e.getErrorCode());
                ret.put("__errorMsg", e.getMessage());
                return ret;

            } catch (Exception e) {
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
            RequestHistory _RequestHistory = RequestHistoryDS.getInstance().getById(id);
            if (_RequestHistory == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _RequestHistory);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            RequestHistory _RequestHistory = RequestHistoryDS.getInstance().getById(id);
            if ( _RequestHistory == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _RequestHistory);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ||
                   hasRequestValue(request, "ajxr", "getdata") || hasRequestValue(request, "ajxr", "getlistdata")  ){
		// Returns data in <div>
		// classes 

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

            buf.append("<div id=\"requestHistory-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                RequestHistory _RequestHistory = (RequestHistory) iterator.next();

                buf.append("<div id=\"requestHistory-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("forwardSiteId")) {
                    buf.append("<div id=\"requestHistory-ajax-forwardSiteId\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getForwardSiteId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isDropped")) {
                    buf.append("<div id=\"requestHistory-ajax-isDropped\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getIsDropped()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isPageless")) {
                    buf.append("<div id=\"requestHistory-ajax-isPageless\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getIsPageless()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isLogin")) {
                    buf.append("<div id=\"requestHistory-ajax-isLogin\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getIsLogin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isAjax")) {
                    buf.append("<div id=\"requestHistory-ajax-isAjax\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getIsAjax()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isRobot")) {
                    buf.append("<div id=\"requestHistory-ajax-isRobot\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getIsRobot()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("userid")) {
                    buf.append("<div id=\"requestHistory-ajax-userid\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getUserid()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("userAgent")) {
                    buf.append("<div id=\"requestHistory-ajax-userAgent\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getUserAgent()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("refer")) {
                    buf.append("<div id=\"requestHistory-ajax-refer\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getRefer()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("robot")) {
                    buf.append("<div id=\"requestHistory-ajax-robot\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getRobot()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) {
                    buf.append("<div id=\"requestHistory-ajax-remoteIp\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getRemoteIp()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) {
                    buf.append("<div id=\"requestHistory-ajax-siteUrl\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getSiteUrl()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("uri")) {
                    buf.append("<div id=\"requestHistory-ajax-uri\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getUri()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("query")) {
                    buf.append("<div id=\"requestHistory-ajax-query\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getQuery()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("rpci")) {
                    buf.append("<div id=\"requestHistory-ajax-rpci\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getRpci()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("sessionId")) {
                    buf.append("<div id=\"requestHistory-ajax-sessionId\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getSessionId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"requestHistory-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_RequestHistory.getTimeCreated()));
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
            if ( ignoreFieldSet || fieldSet.contains("forwardSiteId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Forward Site Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isDropped")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Dropped");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isPageless")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Pageless");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isLogin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Login");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isAjax")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Ajax");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isRobot")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Robot");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("userid")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Userid");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("userAgent")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("User Agent");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("refer")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Refer");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("robot")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Robot");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Remote Ip");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Site Url");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("uri")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Uri");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("query")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Query");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("rpci")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Rpci");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("sessionId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Session Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Created");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                RequestHistory _RequestHistory = (RequestHistory) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("forwardSiteId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getForwardSiteId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isDropped")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getIsDropped()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isPageless")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getIsPageless()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isLogin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getIsLogin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isAjax")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getIsAjax()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isRobot")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getIsRobot()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("userid")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getUserid()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("userAgent")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getUserAgent()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("refer")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getRefer()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("robot")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getRobot()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("remoteIp")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getRemoteIp()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getSiteUrl()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("uri")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getUri()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("query")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getQuery()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("rpci")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getRpci()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("sessionId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getSessionId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_RequestHistory.getTimeCreated()));

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

            List fieldsTitle = RequestHistoryDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                RequestHistoryDataHolder _RequestHistory  = new RequestHistoryDataHolder( (RequestHistory) iterator.next());
                gen.addTableRow(_RequestHistory);
			}
            
            ret.put("__value", gen.toString());
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
            buf.append("sendFormAjax('/requestHistoryAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/requestHistoryAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("forwardSiteId")) {
                String value = WebUtil.display(request.getParameter("forwardSiteId"));

                if ( forceHiddenSet.contains("forwardSiteId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"forwardSiteId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Forward Site Id</div>");
            buf.append("<INPUT NAME=\"forwardSiteId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isDropped")) {
                String value = WebUtil.display(request.getParameter("isDropped"));

                if ( forceHiddenSet.contains("isDropped")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isDropped\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Dropped</div>");
            buf.append("<INPUT NAME=\"isDropped\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isPageless")) {
                String value = WebUtil.display(request.getParameter("isPageless"));

                if ( forceHiddenSet.contains("isPageless")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isPageless\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Pageless</div>");
            buf.append("<INPUT NAME=\"isPageless\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("isAjax")) {
                String value = WebUtil.display(request.getParameter("isAjax"));

                if ( forceHiddenSet.contains("isAjax")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isAjax\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Ajax</div>");
            buf.append("<INPUT NAME=\"isAjax\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isRobot")) {
                String value = WebUtil.display(request.getParameter("isRobot"));

                if ( forceHiddenSet.contains("isRobot")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isRobot\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Robot</div>");
            buf.append("<INPUT NAME=\"isRobot\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("userid")) {
                String value = WebUtil.display(request.getParameter("userid"));

                if ( forceHiddenSet.contains("userid")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"userid\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Userid</div>");
            buf.append("<INPUT NAME=\"userid\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("userAgent")) {
                String value = WebUtil.display(request.getParameter("userAgent"));

                if ( forceHiddenSet.contains("userAgent")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"userAgent\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">User Agent</div>");
            buf.append("<INPUT NAME=\"userAgent\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("refer")) {
                String value = WebUtil.display(request.getParameter("refer"));

                if ( forceHiddenSet.contains("refer")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"refer\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Refer</div>");
            buf.append("<INPUT NAME=\"refer\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("robot")) {
                String value = WebUtil.display(request.getParameter("robot"));

                if ( forceHiddenSet.contains("robot")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"robot\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Robot</div>");
            buf.append("<INPUT NAME=\"robot\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) {
                String value = WebUtil.display(request.getParameter("siteUrl"));

                if ( forceHiddenSet.contains("siteUrl")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"siteUrl\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Site Url</div>");
            buf.append("<INPUT NAME=\"siteUrl\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("uri")) {
                String value = WebUtil.display(request.getParameter("uri"));

                if ( forceHiddenSet.contains("uri")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"uri\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Uri</div>");
            buf.append("<INPUT NAME=\"uri\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("query")) {
                String value = WebUtil.display(request.getParameter("query"));

                if ( forceHiddenSet.contains("query")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"query\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Query</div>");
            buf.append("<INPUT NAME=\"query\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("rpci")) {
                String value = WebUtil.display(request.getParameter("rpci"));

                if ( forceHiddenSet.contains("rpci")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"rpci\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Rpci</div>");
            buf.append("<INPUT NAME=\"rpci\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("sessionId")) {
                String value = WebUtil.display(request.getParameter("sessionId"));

                if ( forceHiddenSet.contains("sessionId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"sessionId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Session Id</div>");
            buf.append("<INPUT NAME=\"sessionId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_requestHistory(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayRequestHistory\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_requestHistory(){\n";
            importedScripts +=     "xmlhttpPostXX('requestHistoryFormAddDis','/requestHistoryAction.html', 'resultDisplayRequestHistory', '${ajax_response_fields}', responseCallback_requestHistory);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_requestHistory(){\n";
            importedScripts +=     "clearFormXX('requestHistoryFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_requestHistory(){\n";
            importedScripts +=     "backToXX('requestHistoryFormAddDis','resultDisplayRequestHistory');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"requestHistoryFormAddDis\" method=\"POST\" action=\"/requestHistoryAction.html\" id=\"requestHistoryFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Forward Site Id</div>");
        buf.append("<input class=\"field\" id=\"forwardSiteId\" type=\"text\" size=\"70\" name=\"forwardSiteId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Dropped</div>");
        buf.append("<input class=\"field\" id=\"isDropped\" type=\"text\" size=\"70\" name=\"isDropped\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Pageless</div>");
        buf.append("<input class=\"field\" id=\"isPageless\" type=\"text\" size=\"70\" name=\"isPageless\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Login</div>");
        buf.append("<input class=\"field\" id=\"isLogin\" type=\"text\" size=\"70\" name=\"isLogin\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Ajax</div>");
        buf.append("<input class=\"field\" id=\"isAjax\" type=\"text\" size=\"70\" name=\"isAjax\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Robot</div>");
        buf.append("<input class=\"field\" id=\"isRobot\" type=\"text\" size=\"70\" name=\"isRobot\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Userid</div>");
        buf.append("<input class=\"field\" id=\"userid\" type=\"text\" size=\"70\" name=\"userid\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> User Agent</div>");
        buf.append("<input class=\"field\" id=\"userAgent\" type=\"text\" size=\"70\" name=\"userAgent\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Refer</div>");
        buf.append("<input class=\"field\" id=\"refer\" type=\"text\" size=\"70\" name=\"refer\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Robot</div>");
        buf.append("<input class=\"field\" id=\"robot\" type=\"text\" size=\"70\" name=\"robot\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Remote Ip</div>");
        buf.append("<input class=\"field\" id=\"remoteIp\" type=\"text\" size=\"70\" name=\"remoteIp\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Site Url</div>");
        buf.append("<input class=\"field\" id=\"siteUrl\" type=\"text\" size=\"70\" name=\"siteUrl\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Uri</div>");
        buf.append("<input class=\"field\" id=\"uri\" type=\"text\" size=\"70\" name=\"uri\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Query</div>");
        buf.append("<input class=\"field\" id=\"query\" type=\"text\" size=\"70\" name=\"query\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Rpci</div>");
        buf.append("<input class=\"field\" id=\"rpci\" type=\"text\" size=\"70\" name=\"rpci\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Session Id</div>");
        buf.append("<input class=\"field\" id=\"sessionId\" type=\"text\" size=\"70\" name=\"sessionId\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_requestHistory()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_requestHistory()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayRequestHistory\"></span>");
			buf.append("<a href=\"javascript:showform_requestHistory()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ||
                   hasRequestValue(request, "ajxr", "getjson") || hasRequestValue(request, "ajxr", "getlistjson")  ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = isAjaxListOutput(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            Site site                               = findSessionBoundSite(request);
            AutositeSessionContext sessionContext   = (AutositeSessionContext)getSessionContext(session);
            AutositeRemoteDevice device             = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());

            DeviceSynchTracker tracker              = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);

            JSONObject top = new JSONObject();

            if (returnList) {

                top.put("type", "list");
	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                RequestHistory _RequestHistory = (RequestHistory) iterator.next();

					JSONObject json = RequestHistoryDataHolder.convertToJSON(_RequestHistory, fieldSet, ignoreFieldSet, true, true);

                    if ( isSynchRequired() ){
                        try {
                            synchLedger = findOrRegisterSynchLedger(request, _RequestHistory.getId(), "cleaner-ticket", true );
                            if ( synchLedger != null)
                                json.put("_synchId", synchLedger.getSynchId());
                        }
                        catch (Exception e) {
                            m_logger.error(e.getMessage(),e);
                        }
                    }					

		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                RequestHistory _RequestHistory = list.size() >=1?(RequestHistory) list.get(0): null; 

					top = RequestHistoryDataHolder.convertToJSON(_RequestHistory, null, false, true, true);

                    if ( isSynchRequired() ){
                        try {
                            synchLedger = findOrRegisterSynchLedger(request, _RequestHistory.getId(), "cleaner-ticket", true );
                            if ( synchLedger != null)
                                top.put("_synchId", synchLedger.getSynchId());
                        }
                        catch (Exception e) {
                            m_logger.error(e.getMessage(),e);
                        }
                    }					
				//}
			}

            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
        } else if (hasRequestValue(request, "ajxr", "getjsonsynch")) {
            /*
             * This is request from remote devices like iPad to get synched up with any web based changes or from another devices. 
             * The filtering out process is very complicated. Done mostly by SynchLedger and Tracker. 
             * @see AutositeSynchAction, AutositeLedgerSynchTrackingManager, DeviceSynchTracker
             */
            Site site                               = findSessionBoundSite(request);
            AutositeSessionContext sessionContext   = (AutositeSessionContext)getSessionContext(session);
            AutositeRemoteDevice device             = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());

            DeviceSynchTracker tracker              = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);
            List<SynchTrackingEntry> unsynched      = null;

            if ( tracker == null) {
                m_logger.warn("Tracker not returned for device " + device + " during processing synch request ");
                unsynched = new ArrayList();
            } else {

	 			String synchScope = request.getParameter("_synchScope");
	            if ( synchScope != null && synchScope.equalsIgnoreCase("ALL"))
	                unsynched = tracker.findNotSynchedForAllScopes("cleaner-ticket");
	            else
	                unsynched = tracker.findNotSynchedBySynchScope(request.getParameter("_synchScope"));
			}

            m_logger.debug("number of records to be synched to remote devices " + unsynched.size());

            JSONObject top = new JSONObject();

            int totalCount = 0;// count separately because 

            top.put("type", "list");
            //top.put("count", ""+unsynched.size());

            JSONArray arrayObjects = new JSONArray();

            for (Iterator iterator = unsynched.iterator(); iterator.hasNext();) {
                SynchTrackingEntry synchTrackingEntry = (SynchTrackingEntry) iterator.next();

                RequestHistory  _RequestHistory = RequestHistoryDS.getInstance().getById(synchTrackingEntry.getObjectId());
                
                if (  _RequestHistory == null)
                    continue;

				totalCount++;
                JSONObject json = RequestHistoryDataHolder.convertToJSON(_RequestHistory, null, false, true, true);

                AutositeSynchLedger newSynchLedger = updateSynchLedgerToConfirmDeviceSynch(request, synchTrackingEntry.getSynchObjectId());
                if ( newSynchLedger != null) {
                    json.put("_synchId", newSynchLedger.getSynchId());
                    json.put("_synchScope", newSynchLedger.getSynchId());
				}
                arrayObjects.put(json);
			} //for

            top.put("count", ""+totalCount);
            top.put("list", arrayObjects);
            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            
            return ret;

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
    protected List prepareReturnData(HttpServletRequest request, RequestHistory target){

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

    protected List prepareReturnData(HttpServletRequest request, RequestHistory target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = RequestHistoryDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            RequestHistory _RequestHistory = null; 
            List list = RequestHistoryDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _RequestHistory = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _RequestHistory = (RequestHistory) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _RequestHistory = (RequestHistory) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _RequestHistory = RequestHistoryDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_RequestHistory);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }
    protected boolean isActionSubCmd(HttpServletRequest request){
        return 
        false; 
    }

}
