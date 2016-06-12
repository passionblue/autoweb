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

import com.autosite.db.SiteObject;
import com.autosite.ds.SiteDS;
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
import com.autosite.struts.form.SiteObjectForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.SiteObjectDataHolder;


public class SiteObjectAjaxAction extends SiteObjectAction {

    private static Logger m_logger = Logger.getLogger( SiteObjectAjaxAction.class);

    public SiteObjectAjaxAction(){
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
        
        Site target = null;
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
                target = (Site) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = SiteDS.getInstance().getById(synchLedger.getObjectId());
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
            Site _Site = SiteDS.getInstance().getById(id);
            if (_Site == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _Site);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            Site _Site = SiteDS.getInstance().getById(id);
            if ( _Site == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _Site);
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

            buf.append("<div id=\"siteObject-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Site _Site = (Site) iterator.next();

                buf.append("<div id=\"siteObject-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) {
                    buf.append("<div id=\"siteObject-ajax-siteUrl\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getSiteUrl()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("accountId")) {
                    buf.append("<div id=\"siteObject-ajax-accountId\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getAccountId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
                    buf.append("<div id=\"siteObject-ajax-createdTime\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getCreatedTime()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("siteGroup")) {
                    buf.append("<div id=\"siteObject-ajax-siteGroup\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getSiteGroup()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("registered")) {
                    buf.append("<div id=\"siteObject-ajax-registered\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getRegistered()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("onSale")) {
                    buf.append("<div id=\"siteObject-ajax-onSale\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getOnSale()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("superAdminEnable")) {
                    buf.append("<div id=\"siteObject-ajax-superAdminEnable\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getSuperAdminEnable()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("siteRegisterEnable")) {
                    buf.append("<div id=\"siteObject-ajax-siteRegisterEnable\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getSiteRegisterEnable()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("subdomainEnable")) {
                    buf.append("<div id=\"siteObject-ajax-subdomainEnable\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getSubdomainEnable()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("siteRegisterSite")) {
                    buf.append("<div id=\"siteObject-ajax-siteRegisterSite\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getSiteRegisterSite()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("baseSiteId")) {
                    buf.append("<div id=\"siteObject-ajax-baseSiteId\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getBaseSiteId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("subsite")) {
                    buf.append("<div id=\"siteObject-ajax-subsite\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getSubsite()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
                    buf.append("<div id=\"siteObject-ajax-disabled\" "+itemClass+">");
                    buf.append(WebUtil.display(_Site.getDisabled()));
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
            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Site Url");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("accountId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Account Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Created Time");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("siteGroup")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Site Group");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("registered")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Registered");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("onSale")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("On Sale");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("superAdminEnable")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Super Admin Enable");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("siteRegisterEnable")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Site Register Enable");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("subdomainEnable")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Subdomain Enable");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("siteRegisterSite")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Site Register Site");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("baseSiteId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Base Site Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("subsite")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Subsite");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Disabled");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Site _Site = (Site) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("siteUrl")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Site.getSiteUrl()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("accountId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Site.getAccountId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Site.getCreatedTime()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("siteGroup")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Site.getSiteGroup()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("registered")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Site.getRegistered()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&registered=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&registered=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("onSale")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Site.getOnSale()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&onSale=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&onSale=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("superAdminEnable")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Site.getSuperAdminEnable()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&superAdminEnable=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&superAdminEnable=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("siteRegisterEnable")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Site.getSiteRegisterEnable()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&siteRegisterEnable=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&siteRegisterEnable=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("subdomainEnable")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Site.getSubdomainEnable()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&subdomainEnable=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&subdomainEnable=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("siteRegisterSite")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Site.getSiteRegisterSite()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("baseSiteId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Site.getBaseSiteId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("subsite")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_Site.getSubsite()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&subsite=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/siteObjectAction.html?ef=true&id="+ _Site.getId()+"&subsite=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_Site.getDisabled()));

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

            List fieldsTitle = SiteObjectDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                SiteObjectDataHolder _Site  = new SiteObjectDataHolder( (SiteObject) iterator.next());
                gen.addTableRow(_Site);
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
            buf.append("sendFormAjax('/siteObjectAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/siteObjectAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


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

            if ( ignoreFieldSet || fieldSet.contains("accountId")) {
                String value = WebUtil.display(request.getParameter("accountId"));

                if ( forceHiddenSet.contains("accountId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"accountId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Account Id</div>");
            buf.append("<INPUT NAME=\"accountId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("createdTime")) {
                String value = WebUtil.display(request.getParameter("createdTime"));

                if ( forceHiddenSet.contains("createdTime")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"createdTime\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("siteGroup")) {
                String value = WebUtil.display(request.getParameter("siteGroup"));

                if ( forceHiddenSet.contains("siteGroup")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"siteGroup\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Site Group</div>");
            buf.append("<INPUT NAME=\"siteGroup\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("registered")) {
                String value = WebUtil.display(request.getParameter("registered"));

                if ( forceHiddenSet.contains("registered")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"registered\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Registered</div>");
            buf.append("<select name=\"registered\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("onSale")) {
                String value = WebUtil.display(request.getParameter("onSale"));

                if ( forceHiddenSet.contains("onSale")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"onSale\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">On Sale</div>");
            buf.append("<select name=\"onSale\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("superAdminEnable")) {
                String value = WebUtil.display(request.getParameter("superAdminEnable"));

                if ( forceHiddenSet.contains("superAdminEnable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"superAdminEnable\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Super Admin Enable</div>");
            buf.append("<select name=\"superAdminEnable\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("siteRegisterEnable")) {
                String value = WebUtil.display(request.getParameter("siteRegisterEnable"));

                if ( forceHiddenSet.contains("siteRegisterEnable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"siteRegisterEnable\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Site Register Enable</div>");
            buf.append("<select name=\"siteRegisterEnable\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("subdomainEnable")) {
                String value = WebUtil.display(request.getParameter("subdomainEnable"));

                if ( forceHiddenSet.contains("subdomainEnable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subdomainEnable\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subdomain Enable</div>");
            buf.append("<select name=\"subdomainEnable\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("siteRegisterSite")) {
                String value = WebUtil.display(request.getParameter("siteRegisterSite"));

                if ( forceHiddenSet.contains("siteRegisterSite")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"siteRegisterSite\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Site Register Site</div>");
            buf.append("<INPUT NAME=\"siteRegisterSite\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("baseSiteId")) {
                String value = WebUtil.display(request.getParameter("baseSiteId"));

                if ( forceHiddenSet.contains("baseSiteId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"baseSiteId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Base Site Id</div>");
            buf.append("<INPUT NAME=\"baseSiteId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("subsite")) {
                String value = WebUtil.display(request.getParameter("subsite"));

                if ( forceHiddenSet.contains("subsite")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subsite\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subsite</div>");
            buf.append("<select name=\"subsite\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("disabled")) {
                String value = WebUtil.display(request.getParameter("disabled"));

                if ( forceHiddenSet.contains("disabled")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"disabled\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Disabled</div>");
            buf.append("<INPUT NAME=\"disabled\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_siteObject(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplaySite\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_siteObject(){\n";
            importedScripts +=     "xmlhttpPostXX('siteObjectFormAddDis','/siteObjectAction.html', 'resultDisplaySite', '${ajax_response_fields}', responseCallback_siteObject);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_siteObject(){\n";
            importedScripts +=     "clearFormXX('siteObjectFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_siteObject(){\n";
            importedScripts +=     "backToXX('siteObjectFormAddDis','resultDisplaySite');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"siteObjectFormAddDis\" method=\"POST\" action=\"/siteObjectAction.html\" id=\"siteObjectFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Site Url</div>");
        buf.append("<input class=\"field\" id=\"siteUrl\" type=\"text\" size=\"70\" name=\"siteUrl\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Account Id</div>");
        buf.append("<input class=\"field\" id=\"accountId\" type=\"text\" size=\"70\" name=\"accountId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Site Group</div>");
        buf.append("<input class=\"field\" id=\"siteGroup\" type=\"text\" size=\"70\" name=\"siteGroup\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Registered</div>");
        buf.append("<select name=\"registered\" id=\"registered\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> On Sale</div>");
        buf.append("<select name=\"onSale\" id=\"onSale\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Super Admin Enable</div>");
        buf.append("<select name=\"superAdminEnable\" id=\"superAdminEnable\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Site Register Enable</div>");
        buf.append("<select name=\"siteRegisterEnable\" id=\"siteRegisterEnable\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Subdomain Enable</div>");
        buf.append("<select name=\"subdomainEnable\" id=\"subdomainEnable\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Site Register Site</div>");
        buf.append("<input class=\"field\" id=\"siteRegisterSite\" type=\"text\" size=\"70\" name=\"siteRegisterSite\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Base Site Id</div>");
        buf.append("<input class=\"field\" id=\"baseSiteId\" type=\"text\" size=\"70\" name=\"baseSiteId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Subsite</div>");
        buf.append("<select name=\"subsite\" id=\"subsite\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Disabled</div>");
        buf.append("<input class=\"field\" id=\"disabled\" type=\"text\" size=\"70\" name=\"disabled\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_siteObject()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_siteObject()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplaySite\"></span>");
			buf.append("<a href=\"javascript:showform_siteObject()\">Show form</a><br>");
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
	                Site _Site = (Site) iterator.next();

					JSONObject json = SiteObjectDataHolder.convertToJSON(_Site, fieldSet, ignoreFieldSet, true, true);

                    if ( isSynchRequired() ){
                        try {
                            synchLedger = findOrRegisterSynchLedger(request, _Site.getId(), "${ios_scopePrefix}", true );
                            if ( synchLedger != null) {
                                json.put("_synchId", synchLedger.getSynchId());
                                json.put("_synchScope", synchLedger.getScope());
                            }
                        }
                        catch (Exception e) {
                            m_logger.error(e.getMessage(),e);
                        }
                    }					

		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                Site _Site = list.size() >=1?(Site) list.get(0): null; 

					top = SiteObjectDataHolder.convertToJSON(_Site, null, false, true, true);

                    if ( isSynchRequired() ){
                        try {
                            synchLedger = findOrRegisterSynchLedger(request, _Site.getId(), "${ios_scopePrefix}", true );
                            if ( synchLedger != null){
                                top.put("_synchId", synchLedger.getSynchId());
                                top.put("_synchScope", synchLedger.getScope());
                            }
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
	                unsynched = tracker.findNotSynchedForAllScopes("${ios_scopePrefix}");
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

                Site  _Site = SiteDS.getInstance().getById(synchTrackingEntry.getObjectId());


				//20130513 Finally implemented the delete support. But getjson may have to also support that returns synch id for delted objects. currently not doing that. 
				//Change summary: when deleted, the object must be gone by this poing. server just returns the synchID with ID only. The device should take care by itself. 

                if (  _Site != null) {
    				totalCount++;
                    JSONObject json = SiteObjectDataHolder.convertToJSON(_Site, null, false, true, true);
    
                    AutositeSynchLedger newSynchLedger = updateSynchLedgerToConfirmDeviceSynch(request, synchTrackingEntry.getSynchObjectId());
                    if ( newSynchLedger != null) {
                        json.put("_synchId", newSynchLedger.getSynchId());
                        json.put("_synchScope", newSynchLedger.getScope());
    				}
                    arrayObjects.put(json);
                    
                } else {
                    // Check if this is to synch a already deleted object.  
                    totalCount++;
                    AutositeSynchLedger newSynchLedger = updateSynchLedgerToConfirmDeviceSynch(request, synchTrackingEntry.getSynchObjectId());

                    JSONObject json = new JSONObject();
                    json.put("id", ""+synchTrackingEntry.getObjectId());
                    json.put("type", "object");
                    
                    if ( newSynchLedger != null) {
                        json.put("_synchId", newSynchLedger.getSynchId());
                        json.put("_synchScope", newSynchLedger.getScope());
                    }
                    arrayObjects.put(json);
                }
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
    protected List prepareReturnData(HttpServletRequest request, Site target){

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

    protected List prepareReturnData(HttpServletRequest request, Site target, boolean isList){
        
        List ret = new ArrayList();
        
//        Site site = SiteDS.getInstance().registerSite(request.getServerName());
//
//        if (isList) {
//
//            List list = SiteDS.getInstance().getById(site.getId());
//            ret = new ArrayList(list);
//            
//        } else {            
//            
//            String arg = request.getParameter("ajaxOutArg");
//            Site _Site = null; 
//            List list = SiteDS.getInstance().getBySiteId(site.getId());
//            
//            if (arg == null){
//                _Site = target;
//            } else if (arg.equals("last")) {
//                if (list.size() > 0)
//                    _Site = (Site) list.get(list.size()-1);
//            } else if (arg.equals("first")) {
//                if (list.size() > 0)
//                    _Site = (Site) list.get(0);
//            } else {
//                long id = WebParamUtil.getLongValue(arg);
//                _Site = SiteDS.getInstance().getById(id);
//            }
//            
//            ret = new ArrayList();
//            ret.add(_Site);
//
//        }
        
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
