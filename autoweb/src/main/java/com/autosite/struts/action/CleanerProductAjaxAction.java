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

import com.autosite.db.CleanerProduct;
import com.autosite.ds.CleanerProductDS;
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
import com.autosite.struts.form.CleanerProductForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.CleanerProductDataHolder;


public class CleanerProductAjaxAction extends CleanerProductAction {

    public CleanerProductAjaxAction(){
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
        
        CleanerProduct target = null;
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
                target = (CleanerProduct) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = CleanerProductDS.getInstance().getById(synchLedger.getObjectId());
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
            CleanerProduct _CleanerProduct = CleanerProductDS.getInstance().getById(id);
            if (_CleanerProduct == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerProduct);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerProduct _CleanerProduct = CleanerProductDS.getInstance().getById(id);
            if ( _CleanerProduct == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerProduct);
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

            buf.append("<div id=\"cleanerProduct-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerProduct _CleanerProduct = (CleanerProduct) iterator.next();

                buf.append("<div id=\"cleanerProduct-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("garmentTypeId")) {
                    buf.append("<div id=\"cleanerProduct-ajax-garmentTypeId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerProduct.getGarmentTypeId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("garmentServiceId")) {
                    buf.append("<div id=\"cleanerProduct-ajax-garmentServiceId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerProduct.getGarmentServiceId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("regularPrice")) {
                    buf.append("<div id=\"cleanerProduct-ajax-regularPrice\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerProduct.getRegularPrice()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"cleanerProduct-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerProduct.getNote()));
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
            if ( ignoreFieldSet || fieldSet.contains("garmentTypeId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Garment Type Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("garmentServiceId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Garment Service Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("regularPrice")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Regular Price");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerProduct _CleanerProduct = (CleanerProduct) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("garmentTypeId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerProduct.getGarmentTypeId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("garmentServiceId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerProduct.getGarmentServiceId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("regularPrice")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerProduct.getRegularPrice()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerProduct.getNote()));

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

            List fieldsTitle = CleanerProductDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerProductDataHolder _CleanerProduct  = new CleanerProductDataHolder( (CleanerProduct) iterator.next());
                gen.addTableRow(_CleanerProduct);
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
	                CleanerProduct _CleanerProduct = (CleanerProduct) iterator.next();

					JSONObject json = CleanerProductDataHolder.convertToJSON(_CleanerProduct, fieldSet, ignoreFieldSet, false);

/*
		            JSONObject json = new JSONObject();
		            json.put("id", ""+_CleanerProduct.getId());

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("garmentTypeId")) 
			            json.put("garmentTypeId", ""+_CleanerProduct.getGarmentTypeId());
		            if ( ignoreFieldSet || fieldSet.contains("garmentServiceId")) 
			            json.put("garmentServiceId", ""+_CleanerProduct.getGarmentServiceId());
		            if ( ignoreFieldSet || fieldSet.contains("regularPrice")) 
			            json.put("regularPrice", ""+_CleanerProduct.getRegularPrice());
		            if ( ignoreFieldSet || fieldSet.contains("note")) 
			            json.put("note", ""+_CleanerProduct.getNote());
*/
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                CleanerProduct _CleanerProduct = list.size() >=1?(CleanerProduct) list.get(0): null; 

					top = CleanerProductDataHolder.convertToJSON(_CleanerProduct, null, false, true);

/*
				if ( _CleanerProduct != null) {
	                top.put("type", "object");
		            top.put("id", ""+_CleanerProduct.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonGarmentTypeId = new JSONObject();
		            jsonGarmentTypeId.put("name", "garmentTypeId");
		            jsonGarmentTypeId.put("value", ""+_CleanerProduct.getGarmentTypeId());
		            array.put(jsonGarmentTypeId);
		            JSONObject jsonGarmentServiceId = new JSONObject();
		            jsonGarmentServiceId.put("name", "garmentServiceId");
		            jsonGarmentServiceId.put("value", ""+_CleanerProduct.getGarmentServiceId());
		            array.put(jsonGarmentServiceId);
		            JSONObject jsonRegularPrice = new JSONObject();
		            jsonRegularPrice.put("name", "regularPrice");
		            jsonRegularPrice.put("value", ""+_CleanerProduct.getRegularPrice());
		            array.put(jsonRegularPrice);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerProduct.getNote());
		            array.put(jsonNote);
	    	        top.put("fields", array);
	                //AutositeSynchLedger synchLedger = null;
*/
                    if ( isSynchRequired() ){
		                try {
	                        if ( synchLedger == null)
			                    synchLedger = processSynchRequest(request, _CleanerProduct);

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

                CleanerProduct  _CleanerProduct = CleanerProductDS.getInstance().getById(synchTrackingEntry.getObjectId());
                
//                JSONObject json = new JSONObject();
                JSONObject json = CleanerProductDataHolder.convertToJSON(_CleanerProduct, null, false, true);

/*                
                if ( _CleanerProduct != null) {
                    json.put("type", "object");
                    json.put("id", ""+_CleanerProduct.getId());
                    JSONArray array = new JSONArray();

		            JSONObject jsonGarmentTypeId = new JSONObject();
		            jsonGarmentTypeId.put("name", "garmentTypeId");
		            jsonGarmentTypeId.put("value", ""+_CleanerProduct.getGarmentTypeId());
		            array.put(jsonGarmentTypeId);
		            JSONObject jsonGarmentServiceId = new JSONObject();
		            jsonGarmentServiceId.put("name", "garmentServiceId");
		            jsonGarmentServiceId.put("value", ""+_CleanerProduct.getGarmentServiceId());
		            array.put(jsonGarmentServiceId);
		            JSONObject jsonRegularPrice = new JSONObject();
		            jsonRegularPrice.put("name", "regularPrice");
		            jsonRegularPrice.put("value", ""+_CleanerProduct.getRegularPrice());
		            array.put(jsonRegularPrice);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerProduct.getNote());
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
            buf.append("sendFormAjax('/cleanerProductAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerProductAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("garmentTypeId")) {
                String value = WebUtil.display(request.getParameter("garmentTypeId"));

                if ( forceHiddenSet.contains("garmentTypeId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"garmentTypeId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Garment Type Id</div>");
            buf.append("<INPUT NAME=\"garmentTypeId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("garmentServiceId")) {
                String value = WebUtil.display(request.getParameter("garmentServiceId"));

                if ( forceHiddenSet.contains("garmentServiceId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"garmentServiceId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Garment Service Id</div>");
            buf.append("<INPUT NAME=\"garmentServiceId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("regularPrice")) {
                String value = WebUtil.display(request.getParameter("regularPrice"));

                if ( forceHiddenSet.contains("regularPrice")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"regularPrice\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Regular Price</div>");
            buf.append("<INPUT NAME=\"regularPrice\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_cleanerProduct(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerProduct\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerProduct(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerProductFormAddDis','/cleanerProductAction.html', 'resultDisplayCleanerProduct', '${ajax_response_fields}', responseCallback_cleanerProduct);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerProduct(){\n";
            importedScripts +=     "clearFormXX('cleanerProductFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerProduct(){\n";
            importedScripts +=     "backToXX('cleanerProductFormAddDis','resultDisplayCleanerProduct');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerProductFormAddDis\" method=\"POST\" action=\"/cleanerProductAction.html\" id=\"cleanerProductFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Garment Type Id</div>");
        buf.append("<input class=\"field\" id=\"garmentTypeId\" type=\"text\" size=\"70\" name=\"garmentTypeId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Garment Service Id</div>");
        buf.append("<input class=\"field\" id=\"garmentServiceId\" type=\"text\" size=\"70\" name=\"garmentServiceId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Regular Price</div>");
        buf.append("<input class=\"field\" id=\"regularPrice\" type=\"text\" size=\"70\" name=\"regularPrice\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
        buf.append("<input class=\"field\" id=\"note\" type=\"text\" size=\"70\" name=\"note\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerProduct()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerProduct()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerProduct\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerProduct()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, CleanerProduct target){

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

    protected List prepareReturnData(HttpServletRequest request, CleanerProduct target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = CleanerProductDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerProduct _CleanerProduct = null; 
            List list = CleanerProductDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerProduct = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerProduct = (CleanerProduct) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerProduct = (CleanerProduct) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerProduct = CleanerProductDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerProduct);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( CleanerProductAjaxAction.class);
}
