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

import com.autosite.db.CleanerServiceItem;
import com.autosite.ds.CleanerServiceItemDS;
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
import com.autosite.struts.form.CleanerServiceItemForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.CleanerServiceItemDataHolder;


public class CleanerServiceItemAjaxAction extends CleanerServiceItemAction {

    public CleanerServiceItemAjaxAction(){
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
        
        CleanerServiceItem target = null;
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
                target = (CleanerServiceItem) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = CleanerServiceItemDS.getInstance().getById(synchLedger.getObjectId());
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
            CleanerServiceItem _CleanerServiceItem = CleanerServiceItemDS.getInstance().getById(id);
            if (_CleanerServiceItem == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerServiceItem);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerServiceItem _CleanerServiceItem = CleanerServiceItemDS.getInstance().getById(id);
            if ( _CleanerServiceItem == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerServiceItem);
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

            buf.append("<div id=\"cleanerServiceItem-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerServiceItem _CleanerServiceItem = (CleanerServiceItem) iterator.next();

                buf.append("<div id=\"cleanerServiceItem-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("serviceId")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-serviceId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getServiceId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("serviceItemId")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-serviceItemId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getServiceItemId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("itemType")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-itemType\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getItemType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("title")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-title\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getTitle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("imagePath")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-imagePath\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getImagePath()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("imagePathLocal")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-imagePathLocal\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getImagePathLocal()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("basePrice")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-basePrice\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getBasePrice()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"cleanerServiceItem-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerServiceItem.getNote()));
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
            if ( ignoreFieldSet || fieldSet.contains("serviceId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Service Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("serviceItemId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Service Item Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("itemType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Item Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("title")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Title");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("imagePath")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Path");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("imagePathLocal")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Image Path Local");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("basePrice")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Base Price");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerServiceItem _CleanerServiceItem = (CleanerServiceItem) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("serviceId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getServiceId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("serviceItemId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getServiceItemId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("itemType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getItemType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("title")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getTitle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("imagePath")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getImagePath()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("imagePathLocal")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getImagePathLocal()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("basePrice")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getBasePrice()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerServiceItem.getNote()));

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

            List fieldsTitle = CleanerServiceItemDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerServiceItemDataHolder _CleanerServiceItem  = new CleanerServiceItemDataHolder( (CleanerServiceItem) iterator.next());
                gen.addTableRow(_CleanerServiceItem);
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
	                CleanerServiceItem _CleanerServiceItem = (CleanerServiceItem) iterator.next();

					JSONObject json = CleanerServiceItemDataHolder.convertToJSON(_CleanerServiceItem, fieldSet, ignoreFieldSet, false);

/*
		            JSONObject json = new JSONObject();
		            json.put("id", ""+_CleanerServiceItem.getId());

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("serviceId")) 
			            json.put("serviceId", ""+_CleanerServiceItem.getServiceId());
		            if ( ignoreFieldSet || fieldSet.contains("serviceItemId")) 
			            json.put("serviceItemId", ""+_CleanerServiceItem.getServiceItemId());
		            if ( ignoreFieldSet || fieldSet.contains("itemType")) 
			            json.put("itemType", ""+_CleanerServiceItem.getItemType());
		            if ( ignoreFieldSet || fieldSet.contains("title")) 
			            json.put("title", ""+_CleanerServiceItem.getTitle());
		            if ( ignoreFieldSet || fieldSet.contains("imagePath")) 
			            json.put("imagePath", ""+_CleanerServiceItem.getImagePath());
		            if ( ignoreFieldSet || fieldSet.contains("imagePathLocal")) 
			            json.put("imagePathLocal", ""+_CleanerServiceItem.getImagePathLocal());
		            if ( ignoreFieldSet || fieldSet.contains("basePrice")) 
			            json.put("basePrice", ""+_CleanerServiceItem.getBasePrice());
		            if ( ignoreFieldSet || fieldSet.contains("note")) 
			            json.put("note", ""+_CleanerServiceItem.getNote());
*/
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                CleanerServiceItem _CleanerServiceItem = list.size() >=1?(CleanerServiceItem) list.get(0): null; 

					top = CleanerServiceItemDataHolder.convertToJSON(_CleanerServiceItem, null, false, true);

/*
				if ( _CleanerServiceItem != null) {
	                top.put("type", "object");
		            top.put("id", ""+_CleanerServiceItem.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonServiceId = new JSONObject();
		            jsonServiceId.put("name", "serviceId");
		            jsonServiceId.put("value", ""+_CleanerServiceItem.getServiceId());
		            array.put(jsonServiceId);
		            JSONObject jsonServiceItemId = new JSONObject();
		            jsonServiceItemId.put("name", "serviceItemId");
		            jsonServiceItemId.put("value", ""+_CleanerServiceItem.getServiceItemId());
		            array.put(jsonServiceItemId);
		            JSONObject jsonItemType = new JSONObject();
		            jsonItemType.put("name", "itemType");
		            jsonItemType.put("value", ""+_CleanerServiceItem.getItemType());
		            array.put(jsonItemType);
		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            jsonTitle.put("value", ""+_CleanerServiceItem.getTitle());
		            array.put(jsonTitle);
		            JSONObject jsonImagePath = new JSONObject();
		            jsonImagePath.put("name", "imagePath");
		            jsonImagePath.put("value", ""+_CleanerServiceItem.getImagePath());
		            array.put(jsonImagePath);
		            JSONObject jsonImagePathLocal = new JSONObject();
		            jsonImagePathLocal.put("name", "imagePathLocal");
		            jsonImagePathLocal.put("value", ""+_CleanerServiceItem.getImagePathLocal());
		            array.put(jsonImagePathLocal);
		            JSONObject jsonBasePrice = new JSONObject();
		            jsonBasePrice.put("name", "basePrice");
		            jsonBasePrice.put("value", ""+_CleanerServiceItem.getBasePrice());
		            array.put(jsonBasePrice);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerServiceItem.getNote());
		            array.put(jsonNote);
	    	        top.put("fields", array);
	                //AutositeSynchLedger synchLedger = null;
*/
                    if ( isSynchRequired() ){
		                try {
	                        if ( synchLedger == null)
			                    synchLedger = processSynchRequest(request, _CleanerServiceItem);

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
            List<SynchTrackingEntry> unsynched      = tracker.findNotSynchedBySynchScope(request.getParameter("_synchScope"));

            m_logger.debug("number of records to be synched to remote devices " + unsynched.size());

            JSONObject top = new JSONObject();

            top.put("type", "list");
            top.put("count", ""+unsynched.size());

            JSONArray arrayObjects = new JSONArray();

            for (Iterator iterator = unsynched.iterator(); iterator.hasNext();) {
                SynchTrackingEntry synchTrackingEntry = (SynchTrackingEntry) iterator.next();

                CleanerServiceItem  _CleanerServiceItem = CleanerServiceItemDS.getInstance().getById(synchTrackingEntry.getObjectId());
                
//                JSONObject json = new JSONObject();
                JSONObject json = CleanerServiceItemDataHolder.convertToJSON(_CleanerServiceItem, null, false, true);

/*                
                if ( _CleanerServiceItem != null) {
                    json.put("type", "object");
                    json.put("id", ""+_CleanerServiceItem.getId());
                    JSONArray array = new JSONArray();

		            JSONObject jsonServiceId = new JSONObject();
		            jsonServiceId.put("name", "serviceId");
		            jsonServiceId.put("value", ""+_CleanerServiceItem.getServiceId());
		            array.put(jsonServiceId);
		            JSONObject jsonServiceItemId = new JSONObject();
		            jsonServiceItemId.put("name", "serviceItemId");
		            jsonServiceItemId.put("value", ""+_CleanerServiceItem.getServiceItemId());
		            array.put(jsonServiceItemId);
		            JSONObject jsonItemType = new JSONObject();
		            jsonItemType.put("name", "itemType");
		            jsonItemType.put("value", ""+_CleanerServiceItem.getItemType());
		            array.put(jsonItemType);
		            JSONObject jsonTitle = new JSONObject();
		            jsonTitle.put("name", "title");
		            jsonTitle.put("value", ""+_CleanerServiceItem.getTitle());
		            array.put(jsonTitle);
		            JSONObject jsonImagePath = new JSONObject();
		            jsonImagePath.put("name", "imagePath");
		            jsonImagePath.put("value", ""+_CleanerServiceItem.getImagePath());
		            array.put(jsonImagePath);
		            JSONObject jsonImagePathLocal = new JSONObject();
		            jsonImagePathLocal.put("name", "imagePathLocal");
		            jsonImagePathLocal.put("value", ""+_CleanerServiceItem.getImagePathLocal());
		            array.put(jsonImagePathLocal);
		            JSONObject jsonBasePrice = new JSONObject();
		            jsonBasePrice.put("name", "basePrice");
		            jsonBasePrice.put("value", ""+_CleanerServiceItem.getBasePrice());
		            array.put(jsonBasePrice);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerServiceItem.getNote());
		            array.put(jsonNote);

                    json.put("fields", array);
                }
*/

                AutositeSynchLedger newSynchLedger = updateSynchLedgerToConfirmDeviceSynch(request, synchTrackingEntry.getSynchObjectId());
                if ( newSynchLedger != null)
                    json.put("_synchId", newSynchLedger.getSynchId());
                arrayObjects.put(json);
			} //for

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
            buf.append("sendFormAjax('/cleanerServiceItemAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerServiceItemAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("serviceId")) {
                String value = WebUtil.display(request.getParameter("serviceId"));

                if ( forceHiddenSet.contains("serviceId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"serviceId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Service Id</div>");
            buf.append("<INPUT NAME=\"serviceId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("serviceItemId")) {
                String value = WebUtil.display(request.getParameter("serviceItemId"));

                if ( forceHiddenSet.contains("serviceItemId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"serviceItemId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Service Item Id</div>");
            buf.append("<INPUT NAME=\"serviceItemId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("itemType")) {
                String value = WebUtil.display(request.getParameter("itemType"));

                if ( forceHiddenSet.contains("itemType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"itemType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Item Type</div>");
            buf.append("<INPUT NAME=\"itemType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("imagePath")) {
                String value = WebUtil.display(request.getParameter("imagePath"));

                if ( forceHiddenSet.contains("imagePath")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imagePath\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Path</div>");
            buf.append("<INPUT NAME=\"imagePath\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("imagePathLocal")) {
                String value = WebUtil.display(request.getParameter("imagePathLocal"));

                if ( forceHiddenSet.contains("imagePathLocal")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"imagePathLocal\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Image Path Local</div>");
            buf.append("<INPUT NAME=\"imagePathLocal\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("basePrice")) {
                String value = WebUtil.display(request.getParameter("basePrice"));

                if ( forceHiddenSet.contains("basePrice")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"basePrice\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Base Price</div>");
            buf.append("<INPUT NAME=\"basePrice\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("note")) {
                String value = WebUtil.display(request.getParameter("note"));

                if ( forceHiddenSet.contains("note")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"note\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Note</div>");
            buf.append("<INPUT NAME=\"note\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_cleanerServiceItem(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerServiceItem\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerServiceItem(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerServiceItemFormAddDis','/cleanerServiceItemAction.html', 'resultDisplayCleanerServiceItem', '${ajax_response_fields}', responseCallback_cleanerServiceItem);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerServiceItem(){\n";
            importedScripts +=     "clearFormXX('cleanerServiceItemFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerServiceItem(){\n";
            importedScripts +=     "backToXX('cleanerServiceItemFormAddDis','resultDisplayCleanerServiceItem');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerServiceItemFormAddDis\" method=\"POST\" action=\"/cleanerServiceItemAction.html\" id=\"cleanerServiceItemFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Service Id</div>");
        buf.append("<input class=\"field\" id=\"serviceId\" type=\"text\" size=\"70\" name=\"serviceId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Service Item Id</div>");
        buf.append("<input class=\"field\" id=\"serviceItemId\" type=\"text\" size=\"70\" name=\"serviceItemId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Item Type</div>");
        buf.append("<input class=\"field\" id=\"itemType\" type=\"text\" size=\"70\" name=\"itemType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Title</div>");
        buf.append("<input class=\"field\" id=\"title\" type=\"text\" size=\"70\" name=\"title\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Path</div>");
        buf.append("<input class=\"field\" id=\"imagePath\" type=\"text\" size=\"70\" name=\"imagePath\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Image Path Local</div>");
        buf.append("<input class=\"field\" id=\"imagePathLocal\" type=\"text\" size=\"70\" name=\"imagePathLocal\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Base Price</div>");
        buf.append("<input class=\"field\" id=\"basePrice\" type=\"text\" size=\"70\" name=\"basePrice\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
        buf.append("<input class=\"field\" id=\"note\" type=\"text\" size=\"70\" name=\"note\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerServiceItem()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerServiceItem()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerServiceItem\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerServiceItem()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, CleanerServiceItem target){

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

    protected List prepareReturnData(HttpServletRequest request, CleanerServiceItem target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = CleanerServiceItemDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerServiceItem _CleanerServiceItem = null; 
            List list = CleanerServiceItemDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerServiceItem = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerServiceItem = (CleanerServiceItem) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerServiceItem = (CleanerServiceItem) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerServiceItem = CleanerServiceItemDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerServiceItem);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( CleanerServiceItemAjaxAction.class);
}
