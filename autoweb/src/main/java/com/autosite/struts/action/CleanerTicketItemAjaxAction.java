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

import com.autosite.db.CleanerTicketItem;
import com.autosite.ds.CleanerTicketItemDS;
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
import com.autosite.struts.form.CleanerTicketItemForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.CleanerTicketItemDataHolder;


public class CleanerTicketItemAjaxAction extends CleanerTicketItemAction {

    public CleanerTicketItemAjaxAction(){
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
        
        CleanerTicketItem target = null;
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
                target = (CleanerTicketItem) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = CleanerTicketItemDS.getInstance().getById(synchLedger.getObjectId());
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
            CleanerTicketItem _CleanerTicketItem = CleanerTicketItemDS.getInstance().getById(id);
            if (_CleanerTicketItem == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerTicketItem);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerTicketItem _CleanerTicketItem = CleanerTicketItemDS.getInstance().getById(id);
            if ( _CleanerTicketItem == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerTicketItem);
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

            buf.append("<div id=\"cleanerTicketItem-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerTicketItem _CleanerTicketItem = (CleanerTicketItem) iterator.next();

                buf.append("<div id=\"cleanerTicketItem-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-ticketId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getTicketId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-parentTicketId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getParentTicketId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("productId")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-productId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getProductId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("subtotalAmount")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-subtotalAmount\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getSubtotalAmount()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("totalAmount")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-totalAmount\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getTotalAmount()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("discountId")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-discountId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getDiscountId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("totalDiscountAmount")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-totalDiscountAmount\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getTotalDiscountAmount()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("specialDiscountAmount")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-specialDiscountAmount\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getSpecialDiscountAmount()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getNote()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"cleanerTicketItem-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicketItem.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Ticket Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Parent Ticket Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("productId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Product Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("subtotalAmount")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Subtotal Amount");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("totalAmount")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Total Amount");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("discountId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Discount Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("totalDiscountAmount")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Total Discount Amount");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("specialDiscountAmount")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Special Discount Amount");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
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
                CleanerTicketItem _CleanerTicketItem = (CleanerTicketItem) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getTicketId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getParentTicketId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("productId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getProductId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("subtotalAmount")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getSubtotalAmount()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("totalAmount")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getTotalAmount()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("discountId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getDiscountId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("totalDiscountAmount")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getTotalDiscountAmount()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("specialDiscountAmount")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getSpecialDiscountAmount()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicketItem.getTimeUpdated()));

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

            List fieldsTitle = CleanerTicketItemDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerTicketItemDataHolder _CleanerTicketItem  = new CleanerTicketItemDataHolder( (CleanerTicketItem) iterator.next());
                gen.addTableRow(_CleanerTicketItem);
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
	                CleanerTicketItem _CleanerTicketItem = (CleanerTicketItem) iterator.next();

					JSONObject json = CleanerTicketItemDataHolder.convertToJSON(_CleanerTicketItem, fieldSet, ignoreFieldSet, false);

/*
		            JSONObject json = new JSONObject();
		            json.put("id", ""+_CleanerTicketItem.getId());

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("ticketId")) 
			            json.put("ticketId", ""+_CleanerTicketItem.getTicketId());
		            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) 
			            json.put("parentTicketId", ""+_CleanerTicketItem.getParentTicketId());
		            if ( ignoreFieldSet || fieldSet.contains("productId")) 
			            json.put("productId", ""+_CleanerTicketItem.getProductId());
		            if ( ignoreFieldSet || fieldSet.contains("subtotalAmount")) 
			            json.put("subtotalAmount", ""+_CleanerTicketItem.getSubtotalAmount());
		            if ( ignoreFieldSet || fieldSet.contains("totalAmount")) 
			            json.put("totalAmount", ""+_CleanerTicketItem.getTotalAmount());
		            if ( ignoreFieldSet || fieldSet.contains("discountId")) 
			            json.put("discountId", ""+_CleanerTicketItem.getDiscountId());
		            if ( ignoreFieldSet || fieldSet.contains("totalDiscountAmount")) 
			            json.put("totalDiscountAmount", ""+_CleanerTicketItem.getTotalDiscountAmount());
		            if ( ignoreFieldSet || fieldSet.contains("specialDiscountAmount")) 
			            json.put("specialDiscountAmount", ""+_CleanerTicketItem.getSpecialDiscountAmount());
		            if ( ignoreFieldSet || fieldSet.contains("note")) 
			            json.put("note", ""+_CleanerTicketItem.getNote());
		            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
			            json.put("timeCreated", ""+_CleanerTicketItem.getTimeCreated());
		            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
			            json.put("timeUpdated", ""+_CleanerTicketItem.getTimeUpdated());
*/
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                CleanerTicketItem _CleanerTicketItem = list.size() >=1?(CleanerTicketItem) list.get(0): null; 

					top = CleanerTicketItemDataHolder.convertToJSON(_CleanerTicketItem, null, false, true);

/*
				if ( _CleanerTicketItem != null) {
	                top.put("type", "object");
		            top.put("id", ""+_CleanerTicketItem.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonTicketId = new JSONObject();
		            jsonTicketId.put("name", "ticketId");
		            jsonTicketId.put("value", ""+_CleanerTicketItem.getTicketId());
		            array.put(jsonTicketId);
		            JSONObject jsonParentTicketId = new JSONObject();
		            jsonParentTicketId.put("name", "parentTicketId");
		            jsonParentTicketId.put("value", ""+_CleanerTicketItem.getParentTicketId());
		            array.put(jsonParentTicketId);
		            JSONObject jsonProductId = new JSONObject();
		            jsonProductId.put("name", "productId");
		            jsonProductId.put("value", ""+_CleanerTicketItem.getProductId());
		            array.put(jsonProductId);
		            JSONObject jsonSubtotalAmount = new JSONObject();
		            jsonSubtotalAmount.put("name", "subtotalAmount");
		            jsonSubtotalAmount.put("value", ""+_CleanerTicketItem.getSubtotalAmount());
		            array.put(jsonSubtotalAmount);
		            JSONObject jsonTotalAmount = new JSONObject();
		            jsonTotalAmount.put("name", "totalAmount");
		            jsonTotalAmount.put("value", ""+_CleanerTicketItem.getTotalAmount());
		            array.put(jsonTotalAmount);
		            JSONObject jsonDiscountId = new JSONObject();
		            jsonDiscountId.put("name", "discountId");
		            jsonDiscountId.put("value", ""+_CleanerTicketItem.getDiscountId());
		            array.put(jsonDiscountId);
		            JSONObject jsonTotalDiscountAmount = new JSONObject();
		            jsonTotalDiscountAmount.put("name", "totalDiscountAmount");
		            jsonTotalDiscountAmount.put("value", ""+_CleanerTicketItem.getTotalDiscountAmount());
		            array.put(jsonTotalDiscountAmount);
		            JSONObject jsonSpecialDiscountAmount = new JSONObject();
		            jsonSpecialDiscountAmount.put("name", "specialDiscountAmount");
		            jsonSpecialDiscountAmount.put("value", ""+_CleanerTicketItem.getSpecialDiscountAmount());
		            array.put(jsonSpecialDiscountAmount);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerTicketItem.getNote());
		            array.put(jsonNote);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_CleanerTicketItem.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_CleanerTicketItem.getTimeUpdated());
		            array.put(jsonTimeUpdated);
	    	        top.put("fields", array);
	                //AutositeSynchLedger synchLedger = null;
*/
                    if ( isSynchRequired() ){
		                try {
	                        if ( synchLedger == null)
			                    synchLedger = processSynchRequest(request, _CleanerTicketItem);

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

                CleanerTicketItem  _CleanerTicketItem = CleanerTicketItemDS.getInstance().getById(synchTrackingEntry.getObjectId());
                
//                JSONObject json = new JSONObject();
                JSONObject json = CleanerTicketItemDataHolder.convertToJSON(_CleanerTicketItem, null, false, true);

/*                
                if ( _CleanerTicketItem != null) {
                    json.put("type", "object");
                    json.put("id", ""+_CleanerTicketItem.getId());
                    JSONArray array = new JSONArray();

		            JSONObject jsonTicketId = new JSONObject();
		            jsonTicketId.put("name", "ticketId");
		            jsonTicketId.put("value", ""+_CleanerTicketItem.getTicketId());
		            array.put(jsonTicketId);
		            JSONObject jsonParentTicketId = new JSONObject();
		            jsonParentTicketId.put("name", "parentTicketId");
		            jsonParentTicketId.put("value", ""+_CleanerTicketItem.getParentTicketId());
		            array.put(jsonParentTicketId);
		            JSONObject jsonProductId = new JSONObject();
		            jsonProductId.put("name", "productId");
		            jsonProductId.put("value", ""+_CleanerTicketItem.getProductId());
		            array.put(jsonProductId);
		            JSONObject jsonSubtotalAmount = new JSONObject();
		            jsonSubtotalAmount.put("name", "subtotalAmount");
		            jsonSubtotalAmount.put("value", ""+_CleanerTicketItem.getSubtotalAmount());
		            array.put(jsonSubtotalAmount);
		            JSONObject jsonTotalAmount = new JSONObject();
		            jsonTotalAmount.put("name", "totalAmount");
		            jsonTotalAmount.put("value", ""+_CleanerTicketItem.getTotalAmount());
		            array.put(jsonTotalAmount);
		            JSONObject jsonDiscountId = new JSONObject();
		            jsonDiscountId.put("name", "discountId");
		            jsonDiscountId.put("value", ""+_CleanerTicketItem.getDiscountId());
		            array.put(jsonDiscountId);
		            JSONObject jsonTotalDiscountAmount = new JSONObject();
		            jsonTotalDiscountAmount.put("name", "totalDiscountAmount");
		            jsonTotalDiscountAmount.put("value", ""+_CleanerTicketItem.getTotalDiscountAmount());
		            array.put(jsonTotalDiscountAmount);
		            JSONObject jsonSpecialDiscountAmount = new JSONObject();
		            jsonSpecialDiscountAmount.put("name", "specialDiscountAmount");
		            jsonSpecialDiscountAmount.put("value", ""+_CleanerTicketItem.getSpecialDiscountAmount());
		            array.put(jsonSpecialDiscountAmount);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerTicketItem.getNote());
		            array.put(jsonNote);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_CleanerTicketItem.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_CleanerTicketItem.getTimeUpdated());
		            array.put(jsonTimeUpdated);

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
            buf.append("sendFormAjax('/cleanerTicketItemAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerTicketItemAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
                String value = WebUtil.display(request.getParameter("ticketId"));

                if ( forceHiddenSet.contains("ticketId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ticketId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ticket Id</div>");
            buf.append("<INPUT NAME=\"ticketId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) {
                String value = WebUtil.display(request.getParameter("parentTicketId"));

                if ( forceHiddenSet.contains("parentTicketId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"parentTicketId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Parent Ticket Id</div>");
            buf.append("<INPUT NAME=\"parentTicketId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("productId")) {
                String value = WebUtil.display(request.getParameter("productId"));

                if ( forceHiddenSet.contains("productId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"productId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Product Id</div>");
            buf.append("<INPUT NAME=\"productId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("subtotalAmount")) {
                String value = WebUtil.display(request.getParameter("subtotalAmount"));

                if ( forceHiddenSet.contains("subtotalAmount")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subtotalAmount\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subtotal Amount</div>");
            buf.append("<INPUT NAME=\"subtotalAmount\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("totalAmount")) {
                String value = WebUtil.display(request.getParameter("totalAmount"));

                if ( forceHiddenSet.contains("totalAmount")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"totalAmount\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Total Amount</div>");
            buf.append("<INPUT NAME=\"totalAmount\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("discountId")) {
                String value = WebUtil.display(request.getParameter("discountId"));

                if ( forceHiddenSet.contains("discountId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"discountId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Discount Id</div>");
            buf.append("<INPUT NAME=\"discountId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("totalDiscountAmount")) {
                String value = WebUtil.display(request.getParameter("totalDiscountAmount"));

                if ( forceHiddenSet.contains("totalDiscountAmount")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"totalDiscountAmount\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Total Discount Amount</div>");
            buf.append("<INPUT NAME=\"totalDiscountAmount\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("specialDiscountAmount")) {
                String value = WebUtil.display(request.getParameter("specialDiscountAmount"));

                if ( forceHiddenSet.contains("specialDiscountAmount")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"specialDiscountAmount\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Special Discount Amount</div>");
            buf.append("<INPUT NAME=\"specialDiscountAmount\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_cleanerTicketItem(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerTicketItem\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerTicketItem(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerTicketItemFormAddDis','/cleanerTicketItemAction.html', 'resultDisplayCleanerTicketItem', '${ajax_response_fields}', responseCallback_cleanerTicketItem);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerTicketItem(){\n";
            importedScripts +=     "clearFormXX('cleanerTicketItemFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerTicketItem(){\n";
            importedScripts +=     "backToXX('cleanerTicketItemFormAddDis','resultDisplayCleanerTicketItem');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerTicketItemFormAddDis\" method=\"POST\" action=\"/cleanerTicketItemAction.html\" id=\"cleanerTicketItemFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Ticket Id</div>");
        buf.append("<input class=\"field\" id=\"ticketId\" type=\"text\" size=\"70\" name=\"ticketId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Parent Ticket Id</div>");
        buf.append("<input class=\"field\" id=\"parentTicketId\" type=\"text\" size=\"70\" name=\"parentTicketId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Product Id</div>");
        buf.append("<input class=\"field\" id=\"productId\" type=\"text\" size=\"70\" name=\"productId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Subtotal Amount</div>");
        buf.append("<input class=\"field\" id=\"subtotalAmount\" type=\"text\" size=\"70\" name=\"subtotalAmount\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Total Amount</div>");
        buf.append("<input class=\"field\" id=\"totalAmount\" type=\"text\" size=\"70\" name=\"totalAmount\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Discount Id</div>");
        buf.append("<input class=\"field\" id=\"discountId\" type=\"text\" size=\"70\" name=\"discountId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Total Discount Amount</div>");
        buf.append("<input class=\"field\" id=\"totalDiscountAmount\" type=\"text\" size=\"70\" name=\"totalDiscountAmount\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Special Discount Amount</div>");
        buf.append("<input class=\"field\" id=\"specialDiscountAmount\" type=\"text\" size=\"70\" name=\"specialDiscountAmount\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
        buf.append("<input class=\"field\" id=\"note\" type=\"text\" size=\"70\" name=\"note\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerTicketItem()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerTicketItem()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerTicketItem\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerTicketItem()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, CleanerTicketItem target){

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

    protected List prepareReturnData(HttpServletRequest request, CleanerTicketItem target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = CleanerTicketItemDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerTicketItem _CleanerTicketItem = null; 
            List list = CleanerTicketItemDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerTicketItem = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerTicketItem = (CleanerTicketItem) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerTicketItem = (CleanerTicketItem) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerTicketItem = CleanerTicketItemDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerTicketItem);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( CleanerTicketItemAjaxAction.class);
}
