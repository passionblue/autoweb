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

import com.autosite.db.CleanerLocationConfig;
import com.autosite.ds.CleanerLocationConfigDS;
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
import com.autosite.struts.form.CleanerLocationConfigForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.CleanerLocationConfigDataHolder;


public class CleanerLocationConfigAjaxAction extends CleanerLocationConfigAction {

    public CleanerLocationConfigAjaxAction(){
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
        
        CleanerLocationConfig target = null;
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
                target = (CleanerLocationConfig) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = CleanerLocationConfigDS.getInstance().getById(synchLedger.getObjectId());
	                }
                } else {
                    m_logger.error(e.getMessage(),e);
                    ret.put("__error", "true");
                    ret.put("__errorMsg", e.getMessage());
                }

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
            CleanerLocationConfig _CleanerLocationConfig = CleanerLocationConfigDS.getInstance().getById(id);
            if (_CleanerLocationConfig == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerLocationConfig);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerLocationConfig _CleanerLocationConfig = CleanerLocationConfigDS.getInstance().getById(id);
            if ( _CleanerLocationConfig == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerLocationConfig);
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

            buf.append("<div id=\"cleanerLocationConfig-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerLocationConfig _CleanerLocationConfig = (CleanerLocationConfig) iterator.next();

                buf.append("<div id=\"cleanerLocationConfig-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-locationId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getLocationId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("openHourWeekday")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-openHourWeekday\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getOpenHourWeekday()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("closeHourWeekday")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-closeHourWeekday\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getCloseHourWeekday()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("openHourSat")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-openHourSat\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getOpenHourSat()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("closeHourSat")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-closeHourSat\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getCloseHourSat()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("openHourSun")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-openHourSun\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getOpenHourSun()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("closeHourSun")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-closeHourSun\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getCloseHourSun()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"cleanerLocationConfig-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerLocationConfig.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Location Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("openHourWeekday")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Open Hour Weekday");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("closeHourWeekday")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Close Hour Weekday");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("openHourSat")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Open Hour Sat");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("closeHourSat")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Close Hour Sat");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("openHourSun")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Open Hour Sun");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("closeHourSun")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Close Hour Sun");
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
                CleanerLocationConfig _CleanerLocationConfig = (CleanerLocationConfig) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getLocationId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("openHourWeekday")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getOpenHourWeekday()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("closeHourWeekday")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getCloseHourWeekday()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("openHourSat")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getOpenHourSat()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("closeHourSat")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getCloseHourSat()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("openHourSun")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getOpenHourSun()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("closeHourSun")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getCloseHourSun()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerLocationConfig.getTimeUpdated()));

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

            List fieldsTitle = CleanerLocationConfigDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerLocationConfigDataHolder _CleanerLocationConfig  = new CleanerLocationConfigDataHolder( (CleanerLocationConfig) iterator.next());
                gen.addTableRow(_CleanerLocationConfig);
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
	                CleanerLocationConfig _CleanerLocationConfig = (CleanerLocationConfig) iterator.next();

					JSONObject json = CleanerLocationConfigDataHolder.convertToJSON(_CleanerLocationConfig, fieldSet, ignoreFieldSet, true);

		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                CleanerLocationConfig _CleanerLocationConfig = list.size() >=1?(CleanerLocationConfig) list.get(0): null; 

					top = CleanerLocationConfigDataHolder.convertToJSON(_CleanerLocationConfig, null, false, true);

/*
				if ( _CleanerLocationConfig != null) {
	                top.put("type", "object");
		            top.put("id", ""+_CleanerLocationConfig.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonLocationId = new JSONObject();
		            jsonLocationId.put("name", "locationId");
		            jsonLocationId.put("value", ""+_CleanerLocationConfig.getLocationId());
		            array.put(jsonLocationId);
		            JSONObject jsonOpenHourWeekday = new JSONObject();
		            jsonOpenHourWeekday.put("name", "openHourWeekday");
		            jsonOpenHourWeekday.put("value", ""+_CleanerLocationConfig.getOpenHourWeekday());
		            array.put(jsonOpenHourWeekday);
		            JSONObject jsonCloseHourWeekday = new JSONObject();
		            jsonCloseHourWeekday.put("name", "closeHourWeekday");
		            jsonCloseHourWeekday.put("value", ""+_CleanerLocationConfig.getCloseHourWeekday());
		            array.put(jsonCloseHourWeekday);
		            JSONObject jsonOpenHourSat = new JSONObject();
		            jsonOpenHourSat.put("name", "openHourSat");
		            jsonOpenHourSat.put("value", ""+_CleanerLocationConfig.getOpenHourSat());
		            array.put(jsonOpenHourSat);
		            JSONObject jsonCloseHourSat = new JSONObject();
		            jsonCloseHourSat.put("name", "closeHourSat");
		            jsonCloseHourSat.put("value", ""+_CleanerLocationConfig.getCloseHourSat());
		            array.put(jsonCloseHourSat);
		            JSONObject jsonOpenHourSun = new JSONObject();
		            jsonOpenHourSun.put("name", "openHourSun");
		            jsonOpenHourSun.put("value", ""+_CleanerLocationConfig.getOpenHourSun());
		            array.put(jsonOpenHourSun);
		            JSONObject jsonCloseHourSun = new JSONObject();
		            jsonCloseHourSun.put("name", "closeHourSun");
		            jsonCloseHourSun.put("value", ""+_CleanerLocationConfig.getCloseHourSun());
		            array.put(jsonCloseHourSun);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_CleanerLocationConfig.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_CleanerLocationConfig.getTimeUpdated());
		            array.put(jsonTimeUpdated);
	    	        top.put("fields", array);
	                //AutositeSynchLedger synchLedger = null;
*/
                    if ( isSynchRequired() ){
		                try {
	                        if ( synchLedger == null)
			                    synchLedger = processSynchRequest(request, _CleanerLocationConfig);

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
	                unsynched = tracker.findNotSynchedForAllScopes("cleaner-location-config");
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

                CleanerLocationConfig  _CleanerLocationConfig = CleanerLocationConfigDS.getInstance().getById(synchTrackingEntry.getObjectId());
                
                if (  _CleanerLocationConfig == null)
                    continue;

				totalCount++;
                JSONObject json = CleanerLocationConfigDataHolder.convertToJSON(_CleanerLocationConfig, null, false, true);


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
            buf.append("sendFormAjax('/cleanerLocationConfigAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerLocationConfigAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
                String value = WebUtil.display(request.getParameter("locationId"));

                if ( forceHiddenSet.contains("locationId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"locationId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Location Id</div>");
            buf.append("<INPUT NAME=\"locationId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("openHourWeekday")) {
                String value = WebUtil.display(request.getParameter("openHourWeekday"));

                if ( forceHiddenSet.contains("openHourWeekday")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"openHourWeekday\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Open Hour Weekday</div>");
            buf.append("<INPUT NAME=\"openHourWeekday\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("closeHourWeekday")) {
                String value = WebUtil.display(request.getParameter("closeHourWeekday"));

                if ( forceHiddenSet.contains("closeHourWeekday")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"closeHourWeekday\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Close Hour Weekday</div>");
            buf.append("<INPUT NAME=\"closeHourWeekday\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("openHourSat")) {
                String value = WebUtil.display(request.getParameter("openHourSat"));

                if ( forceHiddenSet.contains("openHourSat")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"openHourSat\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Open Hour Sat</div>");
            buf.append("<INPUT NAME=\"openHourSat\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("closeHourSat")) {
                String value = WebUtil.display(request.getParameter("closeHourSat"));

                if ( forceHiddenSet.contains("closeHourSat")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"closeHourSat\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Close Hour Sat</div>");
            buf.append("<INPUT NAME=\"closeHourSat\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("openHourSun")) {
                String value = WebUtil.display(request.getParameter("openHourSun"));

                if ( forceHiddenSet.contains("openHourSun")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"openHourSun\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Open Hour Sun</div>");
            buf.append("<INPUT NAME=\"openHourSun\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("closeHourSun")) {
                String value = WebUtil.display(request.getParameter("closeHourSun"));

                if ( forceHiddenSet.contains("closeHourSun")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"closeHourSun\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Close Hour Sun</div>");
            buf.append("<INPUT NAME=\"closeHourSun\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_cleanerLocationConfig(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerLocationConfig\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerLocationConfig(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerLocationConfigFormAddDis','/cleanerLocationConfigAction.html', 'resultDisplayCleanerLocationConfig', '${ajax_response_fields}', responseCallback_cleanerLocationConfig);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerLocationConfig(){\n";
            importedScripts +=     "clearFormXX('cleanerLocationConfigFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerLocationConfig(){\n";
            importedScripts +=     "backToXX('cleanerLocationConfigFormAddDis','resultDisplayCleanerLocationConfig');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerLocationConfigFormAddDis\" method=\"POST\" action=\"/cleanerLocationConfigAction.html\" id=\"cleanerLocationConfigFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Location Id</div>");
        buf.append("<input class=\"field\" id=\"locationId\" type=\"text\" size=\"70\" name=\"locationId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Open Hour Weekday</div>");
        buf.append("<input class=\"field\" id=\"openHourWeekday\" type=\"text\" size=\"70\" name=\"openHourWeekday\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Close Hour Weekday</div>");
        buf.append("<input class=\"field\" id=\"closeHourWeekday\" type=\"text\" size=\"70\" name=\"closeHourWeekday\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Open Hour Sat</div>");
        buf.append("<input class=\"field\" id=\"openHourSat\" type=\"text\" size=\"70\" name=\"openHourSat\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Close Hour Sat</div>");
        buf.append("<input class=\"field\" id=\"closeHourSat\" type=\"text\" size=\"70\" name=\"closeHourSat\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Open Hour Sun</div>");
        buf.append("<input class=\"field\" id=\"openHourSun\" type=\"text\" size=\"70\" name=\"openHourSun\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Close Hour Sun</div>");
        buf.append("<input class=\"field\" id=\"closeHourSun\" type=\"text\" size=\"70\" name=\"closeHourSun\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerLocationConfig()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerLocationConfig()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerLocationConfig\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerLocationConfig()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, CleanerLocationConfig target){

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

    protected List prepareReturnData(HttpServletRequest request, CleanerLocationConfig target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = CleanerLocationConfigDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerLocationConfig _CleanerLocationConfig = null; 
            List list = CleanerLocationConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerLocationConfig = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerLocationConfig = (CleanerLocationConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerLocationConfig = (CleanerLocationConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerLocationConfig = CleanerLocationConfigDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerLocationConfig);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( CleanerLocationConfigAjaxAction.class);
}
