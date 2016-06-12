/* 
Template last modification history: 
20150214 - added synk support
20150223 - moved json processing up to CoreAction processAndReturnJSONResponse() 

Source Generated: Sun Mar 15 13:54:44 EDT 2015
*/

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
import com.autosite.db.SynkNamespaceRecord;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.CleanerPickupDelivery;
import com.autosite.ds.CleanerPickupDeliveryDS;
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
import com.autosite.struts.form.CleanerPickupDeliveryForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.JsonUtil;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.CleanerPickupDeliveryDataHolder;


public class CleanerPickupDeliveryAjaxAction extends CleanerPickupDeliveryAction {

    private static Logger m_logger = Logger.getLogger( CleanerPickupDeliveryAjaxAction.class);

    public CleanerPickupDeliveryAjaxAction(){
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
        
        CleanerPickupDelivery target = null;
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
                target = (CleanerPickupDelivery) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = CleanerPickupDeliveryDS.getInstance().getById(synchLedger.getObjectId());
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
            CleanerPickupDelivery _CleanerPickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(id);
            if (_CleanerPickupDelivery == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerPickupDelivery);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerPickupDelivery _CleanerPickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(id);
            if ( _CleanerPickupDelivery == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerPickupDelivery);
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

            buf.append("<div id=\"cleanerPickupDelivery-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) iterator.next();

                buf.append("<div id=\"cleanerPickupDelivery-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-locationId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getLocationId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-customerId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getCustomerId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-ticketId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTicketId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("ticketUid")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-ticketUid\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTicketUid()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pickupTicket")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-pickupTicket\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupTicket()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("checkinTicketForDelivery")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-checkinTicketForDelivery\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getCheckinTicketForDelivery()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isDeliveryRequest")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-isDeliveryRequest\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getIsDeliveryRequest()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isWebRequest")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-isWebRequest\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getIsWebRequest()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isRecurringRequest")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-isRecurringRequest\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getIsRecurringRequest()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isReceiveReady")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-isReceiveReady\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getIsReceiveReady()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isReceiveComplete")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-isReceiveComplete\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getIsReceiveComplete()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("recurId")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-recurId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getRecurId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("cancelled")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-cancelled\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getCancelled()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("completed")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-completed\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getCompleted()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("customerName")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-customerName\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getCustomerName()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("address")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-address\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getAddress()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("aptNumber")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-aptNumber\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getAptNumber()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("phone")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-phone\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getPhone()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("email")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-email\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getEmail()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("ackReceiveMethod")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-ackReceiveMethod\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getAckReceiveMethod()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("customerInstruction")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-customerInstruction\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getCustomerInstruction()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByDay")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-pickupDeliveryByDay\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByDay()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByTime")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-pickupDeliveryByTime\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByTime()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeUpdated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeAcked")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-timeAcked\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeAcked()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("ackedByUserId")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-ackedByUserId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getAckedByUserId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeReady")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-timeReady\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeReady()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeNotified")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-timeNotified\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeNotified()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-timeCompleted\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeCompleted()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getNote()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("pickupNote")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-pickupNote\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupNote()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("deliveryNote")) {
                    buf.append("<div id=\"cleanerPickupDelivery-ajax-deliveryNote\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerPickupDelivery.getDeliveryNote()));
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
            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Customer Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Ticket Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("ticketUid")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Ticket Uid");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pickupTicket")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Pickup Ticket");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("checkinTicketForDelivery")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Checkin Ticket For Delivery");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isDeliveryRequest")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Delivery Request");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isWebRequest")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Web Request");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isRecurringRequest")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Recurring Request");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isReceiveReady")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Receive Ready");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isReceiveComplete")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Receive Complete");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("recurId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Recur Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("cancelled")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Cancelled");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("completed")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Completed");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("customerName")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Customer Name");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("address")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Address");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("aptNumber")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Apt Number");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("phone")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Phone");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("email")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Email");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("ackReceiveMethod")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Ack Receive Method");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("customerInstruction")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Customer Instruction");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByDay")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Pickup Delivery By Day");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByTime")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Pickup Delivery By Time");
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
            if ( ignoreFieldSet || fieldSet.contains("timeAcked")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Acked");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("ackedByUserId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Acked By User Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeReady")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Ready");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeNotified")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Notified");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Completed");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("pickupNote")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Pickup Note");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("deliveryNote")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Delivery Note");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getLocationId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getCustomerId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("ticketId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTicketId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("ticketUid")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTicketUid()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pickupTicket")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupTicket()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("checkinTicketForDelivery")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getCheckinTicketForDelivery()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isDeliveryRequest")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_CleanerPickupDelivery.getIsDeliveryRequest()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&isDeliveryRequest=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&isDeliveryRequest=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isWebRequest")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_CleanerPickupDelivery.getIsWebRequest()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&isWebRequest=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&isWebRequest=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isRecurringRequest")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_CleanerPickupDelivery.getIsRecurringRequest()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&isRecurringRequest=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&isRecurringRequest=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isReceiveReady")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getIsReceiveReady()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isReceiveComplete")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getIsReceiveComplete()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("recurId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getRecurId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("cancelled")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_CleanerPickupDelivery.getCancelled()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&cancelled=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&cancelled=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("completed")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_CleanerPickupDelivery.getCompleted()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&completed=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?ef=true&id="+ _CleanerPickupDelivery.getId()+"&completed=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("customerName")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getCustomerName()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("address")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getAddress()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("aptNumber")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getAptNumber()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("phone")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getPhone()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("email")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getEmail()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("ackReceiveMethod")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getAckReceiveMethod()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("customerInstruction")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getCustomerInstruction()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByDay")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByDay()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByTime")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByTime()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeUpdated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeAcked")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeAcked()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("ackedByUserId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getAckedByUserId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeReady")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeReady()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeNotified")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeNotified()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getTimeCompleted()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("pickupNote")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getPickupNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("deliveryNote")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerPickupDelivery.getDeliveryNote()));

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

            List fieldsTitle = CleanerPickupDeliveryDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerPickupDeliveryDataHolder _CleanerPickupDelivery  = new CleanerPickupDeliveryDataHolder( (CleanerPickupDelivery) iterator.next());
                gen.addTableRow(_CleanerPickupDelivery);
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
            buf.append("sendFormAjax('/cleanerPickupDeliveryAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerPickupDeliveryAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


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

            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
                String value = WebUtil.display(request.getParameter("customerId"));

                if ( forceHiddenSet.contains("customerId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"customerId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Customer Id</div>");
            buf.append("<INPUT NAME=\"customerId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

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

            if ( ignoreFieldSet || fieldSet.contains("ticketUid")) {
                String value = WebUtil.display(request.getParameter("ticketUid"));

                if ( forceHiddenSet.contains("ticketUid")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ticketUid\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ticket Uid</div>");
            buf.append("<INPUT NAME=\"ticketUid\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pickupTicket")) {
                String value = WebUtil.display(request.getParameter("pickupTicket"));

                if ( forceHiddenSet.contains("pickupTicket")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pickupTicket\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Pickup Ticket</div>");
            buf.append("<INPUT NAME=\"pickupTicket\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("checkinTicketForDelivery")) {
                String value = WebUtil.display(request.getParameter("checkinTicketForDelivery"));

                if ( forceHiddenSet.contains("checkinTicketForDelivery")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"checkinTicketForDelivery\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Checkin Ticket For Delivery</div>");
            buf.append("<INPUT NAME=\"checkinTicketForDelivery\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isDeliveryRequest")) {
                String value = WebUtil.display(request.getParameter("isDeliveryRequest"));

                if ( forceHiddenSet.contains("isDeliveryRequest")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isDeliveryRequest\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Delivery Request</div>");
            buf.append("<select name=\"isDeliveryRequest\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isWebRequest")) {
                String value = WebUtil.display(request.getParameter("isWebRequest"));

                if ( forceHiddenSet.contains("isWebRequest")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isWebRequest\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Web Request</div>");
            buf.append("<select name=\"isWebRequest\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isRecurringRequest")) {
                String value = WebUtil.display(request.getParameter("isRecurringRequest"));

                if ( forceHiddenSet.contains("isRecurringRequest")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isRecurringRequest\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Recurring Request</div>");
            buf.append("<select name=\"isRecurringRequest\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isReceiveReady")) {
                String value = WebUtil.display(request.getParameter("isReceiveReady"));

                if ( forceHiddenSet.contains("isReceiveReady")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isReceiveReady\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Receive Ready</div>");
            buf.append("<INPUT NAME=\"isReceiveReady\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isReceiveComplete")) {
                String value = WebUtil.display(request.getParameter("isReceiveComplete"));

                if ( forceHiddenSet.contains("isReceiveComplete")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isReceiveComplete\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Receive Complete</div>");
            buf.append("<INPUT NAME=\"isReceiveComplete\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("recurId")) {
                String value = WebUtil.display(request.getParameter("recurId"));

                if ( forceHiddenSet.contains("recurId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"recurId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Recur Id</div>");
            buf.append("<INPUT NAME=\"recurId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("cancelled")) {
                String value = WebUtil.display(request.getParameter("cancelled"));

                if ( forceHiddenSet.contains("cancelled")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"cancelled\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Cancelled</div>");
            buf.append("<select name=\"cancelled\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("completed")) {
                String value = WebUtil.display(request.getParameter("completed"));

                if ( forceHiddenSet.contains("completed")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"completed\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Completed</div>");
            buf.append("<select name=\"completed\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("customerName")) {
                String value = WebUtil.display(request.getParameter("customerName"));

                if ( forceHiddenSet.contains("customerName")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"customerName\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Customer Name</div>");
            buf.append("<INPUT NAME=\"customerName\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("address")) {
                String value = WebUtil.display(request.getParameter("address"));

                if ( forceHiddenSet.contains("address")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"address\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Address</div>");
            buf.append("<INPUT NAME=\"address\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("aptNumber")) {
                String value = WebUtil.display(request.getParameter("aptNumber"));

                if ( forceHiddenSet.contains("aptNumber")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"aptNumber\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Apt Number</div>");
            buf.append("<INPUT NAME=\"aptNumber\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("phone")) {
                String value = WebUtil.display(request.getParameter("phone"));

                if ( forceHiddenSet.contains("phone")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"phone\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Phone</div>");
            buf.append("<INPUT NAME=\"phone\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("ackReceiveMethod")) {
                String value = WebUtil.display(request.getParameter("ackReceiveMethod"));

                if ( forceHiddenSet.contains("ackReceiveMethod")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ackReceiveMethod\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Ack Receive Method</div>");
            buf.append("<select id=\"requiredField\" name=\"ackReceiveMethod\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("CleanerPickupDeliveryAck Receive MethodOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("customerInstruction")) {
                String value = WebUtil.display(request.getParameter("customerInstruction"));

                if ( forceHiddenSet.contains("customerInstruction")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"customerInstruction\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Customer Instruction</div>");
            buf.append("<TEXTAREA NAME=\"customerInstruction\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByDay")) {
                String value = WebUtil.display(request.getParameter("pickupDeliveryByDay"));

                if ( forceHiddenSet.contains("pickupDeliveryByDay")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pickupDeliveryByDay\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Pickup Delivery By Day</div>");
            buf.append("<select id=\"requiredField\" name=\"pickupDeliveryByDay\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("CleanerPickupDeliveryPickup Delivery By DayOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pickupDeliveryByTime")) {
                String value = WebUtil.display(request.getParameter("pickupDeliveryByTime"));

                if ( forceHiddenSet.contains("pickupDeliveryByTime")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pickupDeliveryByTime\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Pickup Delivery By Time</div>");
            buf.append("<select id=\"requiredField\" name=\"pickupDeliveryByTime\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("CleanerPickupDeliveryPickup Delivery By TimeOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
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

            if ( ignoreFieldSet || fieldSet.contains("timeAcked")) {
                String value = WebUtil.display(request.getParameter("timeAcked"));

                if ( forceHiddenSet.contains("timeAcked")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeAcked\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("ackedByUserId")) {
                String value = WebUtil.display(request.getParameter("ackedByUserId"));

                if ( forceHiddenSet.contains("ackedByUserId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"ackedByUserId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Acked By User Id</div>");
            buf.append("<INPUT NAME=\"ackedByUserId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeReady")) {
                String value = WebUtil.display(request.getParameter("timeReady"));

                if ( forceHiddenSet.contains("timeReady")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeReady\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeNotified")) {
                String value = WebUtil.display(request.getParameter("timeNotified"));

                if ( forceHiddenSet.contains("timeNotified")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeNotified\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
                String value = WebUtil.display(request.getParameter("timeCompleted"));

                if ( forceHiddenSet.contains("timeCompleted")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCompleted\"  value=\""+value+"\">");
                } else {

			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("note")) {
                String value = WebUtil.display(request.getParameter("note"));

                if ( forceHiddenSet.contains("note")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"note\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Note</div>");
            buf.append("<TEXTAREA NAME=\"note\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("pickupNote")) {
                String value = WebUtil.display(request.getParameter("pickupNote"));

                if ( forceHiddenSet.contains("pickupNote")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pickupNote\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Pickup Note</div>");
            buf.append("<TEXTAREA NAME=\"pickupNote\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("deliveryNote")) {
                String value = WebUtil.display(request.getParameter("deliveryNote"));

                if ( forceHiddenSet.contains("deliveryNote")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"deliveryNote\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Delivery Note</div>");
            buf.append("<TEXTAREA NAME=\"deliveryNote\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA>");
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
            importedScripts += "function responseCallback_cleanerPickupDelivery(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerPickupDelivery\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerPickupDelivery(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerPickupDeliveryFormAddDis','/cleanerPickupDeliveryAction.html', 'resultDisplayCleanerPickupDelivery', '${ajax_response_fields}', responseCallback_cleanerPickupDelivery);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerPickupDelivery(){\n";
            importedScripts +=     "clearFormXX('cleanerPickupDeliveryFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerPickupDelivery(){\n";
            importedScripts +=     "backToXX('cleanerPickupDeliveryFormAddDis','resultDisplayCleanerPickupDelivery');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerPickupDeliveryFormAddDis\" method=\"POST\" action=\"/cleanerPickupDeliveryAction.html\" id=\"cleanerPickupDeliveryFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Location Id</div>");
        buf.append("<input class=\"field\" id=\"locationId\" type=\"text\" size=\"70\" name=\"locationId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Customer Id</div>");
        buf.append("<input class=\"field\" id=\"customerId\" type=\"text\" size=\"70\" name=\"customerId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Ticket Id</div>");
        buf.append("<input class=\"field\" id=\"ticketId\" type=\"text\" size=\"70\" name=\"ticketId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Ticket Uid</div>");
        buf.append("<input class=\"field\" id=\"ticketUid\" type=\"text\" size=\"70\" name=\"ticketUid\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Pickup Ticket</div>");
        buf.append("<input class=\"field\" id=\"pickupTicket\" type=\"text\" size=\"70\" name=\"pickupTicket\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Checkin Ticket For Delivery</div>");
        buf.append("<input class=\"field\" id=\"checkinTicketForDelivery\" type=\"text\" size=\"70\" name=\"checkinTicketForDelivery\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Delivery Request</div>");
        buf.append("<select name=\"isDeliveryRequest\" id=\"isDeliveryRequest\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Web Request</div>");
        buf.append("<select name=\"isWebRequest\" id=\"isWebRequest\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Recurring Request</div>");
        buf.append("<select name=\"isRecurringRequest\" id=\"isRecurringRequest\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Receive Ready</div>");
        buf.append("<input class=\"field\" id=\"isReceiveReady\" type=\"text\" size=\"70\" name=\"isReceiveReady\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Receive Complete</div>");
        buf.append("<input class=\"field\" id=\"isReceiveComplete\" type=\"text\" size=\"70\" name=\"isReceiveComplete\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Recur Id</div>");
        buf.append("<input class=\"field\" id=\"recurId\" type=\"text\" size=\"70\" name=\"recurId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Cancelled</div>");
        buf.append("<select name=\"cancelled\" id=\"cancelled\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Completed</div>");
        buf.append("<select name=\"completed\" id=\"completed\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Customer Name</div>");
        buf.append("<input class=\"field\" id=\"customerName\" type=\"text\" size=\"70\" name=\"customerName\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Address</div>");
        buf.append("<input class=\"field\" id=\"address\" type=\"text\" size=\"70\" name=\"address\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Apt Number</div>");
        buf.append("<input class=\"field\" id=\"aptNumber\" type=\"text\" size=\"70\" name=\"aptNumber\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Phone</div>");
        buf.append("<input class=\"field\" id=\"phone\" type=\"text\" size=\"70\" name=\"phone\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Email</div>");
        buf.append("<input class=\"field\" id=\"email\" type=\"text\" size=\"70\" name=\"email\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Ack Receive Method</div>");
        buf.append("<select class=\"field\" name=\"ackReceiveMethod\" id=\"ackReceiveMethod\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusAckReceiveMethod = DropMenuUtil.getDropMenus("CleanerCustomerNotificationPreference");
	for(Iterator iterItems = dropMenusAckReceiveMethod.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Customer Instruction</div>");
		buf.append("<TEXTAREA id=\"customerInstruction\" class=\"field\" NAME=\"customerInstruction\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Pickup Delivery By Day</div>");
        buf.append("<select class=\"field\" name=\"pickupDeliveryByDay\" id=\"pickupDeliveryByDay\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusPickupDeliveryByDay = DropMenuUtil.getDropMenus("CleanerPickupDeliveryTargetDay");
	for(Iterator iterItems = dropMenusPickupDeliveryByDay.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Pickup Delivery By Time</div>");
        buf.append("<select class=\"field\" name=\"pickupDeliveryByTime\" id=\"pickupDeliveryByTime\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusPickupDeliveryByTime = DropMenuUtil.getDropMenus("CleanerPickupDeliveryTargetTime");
	for(Iterator iterItems = dropMenusPickupDeliveryByTime.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Acked By User Id</div>");
        buf.append("<input class=\"field\" id=\"ackedByUserId\" type=\"text\" size=\"70\" name=\"ackedByUserId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
		buf.append("<TEXTAREA id=\"note\" class=\"field\" NAME=\"note\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Pickup Note</div>");
		buf.append("<TEXTAREA id=\"pickupNote\" class=\"field\" NAME=\"pickupNote\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Delivery Note</div>");
		buf.append("<TEXTAREA id=\"deliveryNote\" class=\"field\" NAME=\"deliveryNote\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerPickupDelivery()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerPickupDelivery()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerPickupDelivery\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerPickupDelivery()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ||
                   hasRequestValue(request, "ajxr",    "getjson") || hasRequestValue(request, "ajxr", "getlistjson")  || 
                   hasRequestValue(request, "ajxr",    "getjsonsynch")  || hasRequestValue(request, "ajxr", "getlistjsonsynch")){ // merged to here from down

            //This logic is moved up
            if (true) {
                String jsonResponse = processAndReturnJSONResponse(request, target);
                ret.put("__value", jsonResponse);
                return ret;
            }
/*
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            boolean returnList                      = isAjaxListOutput(request);
            List list = prepareReturnData(request, target, returnList);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);
           
            Site site                               = findSessionBoundSite(request);
            AutositeSessionContext sessionContext   = (AutositeSessionContext)getSessionContext(session);
            AutositeRemoteDevice device             = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());

//            DeviceSynchTracker tracker            = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);
            boolean ignoreFieldSet                  = (fieldSetStr == null? true: false);
            boolean isRemoteDevice                  = sessionContext.getRemoteDevice() != null; 
            boolean synchRequired                   = isRemoteDevice && isSynchRequired() && isJsonSynchOutput(request);
            boolean synchRequiredRecordsOnly        = hasRequestValue(request, "ajxr", "getjsonsynch");
            boolean isRemoteChangesSynchedToServer  = isActionCmdChangedData(request);

            JSONObject top = new JSONObject();

            if (returnList || true) {

                JSONArray array = new JSONArray();

                top.put("type", "list");
                top.put("namesapce", getActionName());
                top.put("timestamp", System.currentTimeMillis());
                int count = 0;
                
                if ( synchRequired ) {

                    Map<Long,SynkNamespaceRecord> map = null;
                    
                     // Prepare data from synk records not from DB records.   

                    if (synchRequiredRecordsOnly)
                        map = synkop_getNotSynkedRecords(request);
                    else
                        map = synkop_getAllRecords(request);
                    m_logger.debug("Records in processing: " + map.size());
                    
                    for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
                        
                        SynkNamespaceRecord record = (SynkNamespaceRecord) iterator.next();
                        JSONObject json = null;

                        if ( WebUtil.isTrue(record.getDeleted())) {
                            json = new JSONObject();

                            json.put("_synchId",        record.getStamp());
                            json.put("_synchScope",     record.getNamespace());
                            json.put("_stamp",          record.getStamp());
                            json.put("_deleted",        true);

                            json.put("id", record.getRecordId());
                            
                        } else {
                    
                            CleanerPickupDelivery _CleanerPickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(record.getRecordId());
                            
                            if ( _CleanerPickupDelivery == null ) {
                                m_logger.error("Synch record failed to locate " + record.getRecordId() + " is int SYNK but can't find object. skipped", new Exception());
                                continue;
                            }

                            json                = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, true, true);

                            json.put("_synchId",        record.getStamp());
                            json.put("_synchScope",     record.getNamespace());
                            json.put("_stamp",          record.getStamp());
                            json.put("_deleted",        false);

                            
                            JSONObject jsonTest    = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, false, true);

                            m_logger.debug("---------------------------------------------------------------------------------");
                            m_logger.debug(JsonUtil.beautify(jsonTest.toString()));
                            m_logger.debug("---------------------------------------------------------------------------------");

                        }
    
                        array.put(json);
                        count++;
                    }
                    
                } else {  
                
                    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) iterator.next();
    
                        json     = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, true, true);
                        JSONObject json2    = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, false, true);
    
                        SynkNamespaceRecord synkRecord = synkop_findSynkRecord(request, _CleanerPickupDelivery.getAckedByUserId());
                        if ( synkRecord != null)
                            json.put("_synchId", synkRecord.getStamp());

                        m_logger.debug("---------------------------------------------------------------------------------");
                        m_logger.debug(JsonUtil.beautify(json2.toString()));
                        m_logger.debug("---------------------------------------------------------------------------------");
    
                        array.put(json);
                        count++;
                    }
                
                }

                top.put("list", array);
                top.put("count", count);

            } else { // Below part is obsoleted

                // @Deprecated. as List will be applied by prepareData()
                CleanerPickupDelivery _CleanerPickupDelivery = list.size() >=1?(CleanerPickupDelivery) list.get(0): null; 

                    top = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, null, false, true, true);

                    if ( isSynchRequired() ){
                        try {
                            synchLedger = findOrRegisterSynchLedger(request, _CleanerPickupDelivery.getId(), "cleaner-pickup-delivery", true );
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

            m_logger.debug(JsonUtil.beautify(top.toString()));
            ret.put("__value", top.toString());
            return ret;
*/            
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
	                unsynched = tracker.findNotSynchedForAllScopes("cleaner-pickup-delivery");
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

                CleanerPickupDelivery  _CleanerPickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(synchTrackingEntry.getObjectId());


				//20130513 Finally implemented the delete support. But getjson may have to also support that returns synch id for delted objects. currently not doing that. 
				//Change summary: when deleted, the object must be gone by this poing. server just returns the synchID with ID only. The device should take care by itself. 

                if (  _CleanerPickupDelivery != null) {
    				totalCount++;
                    JSONObject json = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, null, false, true, true);
    
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
    protected List prepareReturnData(HttpServletRequest request, CleanerPickupDelivery target){

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

    protected List prepareReturnData(HttpServletRequest request, CleanerPickupDelivery target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = CleanerPickupDeliveryDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerPickupDelivery _CleanerPickupDelivery = null; 
            List list = CleanerPickupDeliveryDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerPickupDelivery = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerPickupDelivery = (CleanerPickupDelivery) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerPickupDelivery = (CleanerPickupDelivery) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerPickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerPickupDelivery);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        hasRequestValue(request, "confirmToRequest", "true")||hasRequestValue(request, "cmd", "confirmToRequest")||
        hasRequestValue(request, "cancelRequest", "true")||hasRequestValue(request, "cmd", "cancelRequest")||
        hasRequestValue(request, "setReadyToRequest", "true")||hasRequestValue(request, "cmd", "setReadyToRequest")||
        hasRequestValue(request, "setCompletedToRequest", "true")||hasRequestValue(request, "cmd", "setCompletedToRequest")||
        hasRequestValue(request, "sendCustomNotification", "true")||hasRequestValue(request, "cmd", "sendCustomNotification")||
        super.isActionBasicCmd(request); 
    }
    protected boolean isActionSubCmd(HttpServletRequest request){
        return 
        hasRequestValue(request, "subcmd", "brushUp")||
        false; 
    }

}
