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

import com.autosite.db.CleanerTicket;
import com.autosite.ds.CleanerTicketDS;
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
import com.autosite.struts.form.CleanerTicketForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.CleanerTicketDataHolder;


public class CleanerTicketAjaxAction extends CleanerTicketAction {

    public CleanerTicketAjaxAction(){
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
        
        CleanerTicket target = null;
        AutositeSynchLedger synchLedger = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (isActionCmd(request)){
            m_logger.debug("AjaxRequest contains ActionCommand. So will process it first");
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (CleanerTicket) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = CleanerTicketDS.getInstance().getById(synchLedger.getObjectId());
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
            CleanerTicket _CleanerTicket = CleanerTicketDS.getInstance().getById(id);
            if (_CleanerTicket == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _CleanerTicket);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            CleanerTicket _CleanerTicket = CleanerTicketDS.getInstance().getById(id);
            if ( _CleanerTicket == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _CleanerTicket);
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

            buf.append("<div id=\"cleanerTicket-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerTicket _CleanerTicket = (CleanerTicket) iterator.next();

                buf.append("<div id=\"cleanerTicket-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("serial")) {
                    buf.append("<div id=\"cleanerTicket-ajax-serial\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getSerial()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) {
                    buf.append("<div id=\"cleanerTicket-ajax-parentTicketId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getParentTicketId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
                    buf.append("<div id=\"cleanerTicket-ajax-customerId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getCustomerId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("enterUserId")) {
                    buf.append("<div id=\"cleanerTicket-ajax-enterUserId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getEnterUserId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
                    buf.append("<div id=\"cleanerTicket-ajax-locationId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getLocationId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"cleanerTicket-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getNote()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("completed")) {
                    buf.append("<div id=\"cleanerTicket-ajax-completed\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getCompleted()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("onhold")) {
                    buf.append("<div id=\"cleanerTicket-ajax-onhold\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getOnhold()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("originalTicketId")) {
                    buf.append("<div id=\"cleanerTicket-ajax-originalTicketId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getOriginalTicketId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("returned")) {
                    buf.append("<div id=\"cleanerTicket-ajax-returned\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getReturned()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("returnedReasonText")) {
                    buf.append("<div id=\"cleanerTicket-ajax-returnedReasonText\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getReturnedReasonText()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("returnedNote")) {
                    buf.append("<div id=\"cleanerTicket-ajax-returnedNote\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getReturnedNote()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("totalCharge")) {
                    buf.append("<div id=\"cleanerTicket-ajax-totalCharge\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getTotalCharge()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("finalCharge")) {
                    buf.append("<div id=\"cleanerTicket-ajax-finalCharge\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getFinalCharge()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("discountId")) {
                    buf.append("<div id=\"cleanerTicket-ajax-discountId\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getDiscountId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("discountAmount")) {
                    buf.append("<div id=\"cleanerTicket-ajax-discountAmount\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getDiscountAmount()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("discountNote")) {
                    buf.append("<div id=\"cleanerTicket-ajax-discountNote\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getDiscountNote()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"cleanerTicket-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"cleanerTicket-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getTimeUpdated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
                    buf.append("<div id=\"cleanerTicket-ajax-timeCompleted\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getTimeCompleted()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeOnhold")) {
                    buf.append("<div id=\"cleanerTicket-ajax-timeOnhold\" "+itemClass+">");
                    buf.append(WebUtil.display(_CleanerTicket.getTimeOnhold()));
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
            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Parent Ticket Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Customer Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("enterUserId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Enter User Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Location Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("completed")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Completed");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("onhold")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Onhold");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("originalTicketId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Original Ticket Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("returned")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Returned");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("returnedReasonText")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Returned Reason Text");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("returnedNote")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Returned Note");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("totalCharge")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Total Charge");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("finalCharge")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Final Charge");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("discountId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Discount Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("discountAmount")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Discount Amount");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("discountNote")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Discount Note");
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
            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Completed");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("timeOnhold")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Time Onhold");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerTicket _CleanerTicket = (CleanerTicket) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("serial")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getSerial()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getParentTicketId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("customerId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getCustomerId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("enterUserId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getEnterUserId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("locationId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getLocationId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("completed")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getCompleted()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("onhold")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getOnhold()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("originalTicketId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getOriginalTicketId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("returned")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getReturned()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("returnedReasonText")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getReturnedReasonText()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("returnedNote")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getReturnedNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("totalCharge")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getTotalCharge()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("finalCharge")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getFinalCharge()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("discountId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getDiscountId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("discountAmount")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getDiscountAmount()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("discountNote")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getDiscountNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getTimeUpdated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getTimeCompleted()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeOnhold")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_CleanerTicket.getTimeOnhold()));

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

            List fieldsTitle = CleanerTicketDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CleanerTicketDataHolder _CleanerTicket  = new CleanerTicketDataHolder( (CleanerTicket) iterator.next());
                gen.addTableRow(_CleanerTicket);
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
	                CleanerTicket _CleanerTicket = (CleanerTicket) iterator.next();

		            JSONObject json = new JSONObject();

		            json.put("id", ""+_CleanerTicket.getId());

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("serial")) 
			            json.put("serial", ""+_CleanerTicket.getSerial());
		            if ( ignoreFieldSet || fieldSet.contains("parentTicketId")) 
			            json.put("parentTicketId", ""+_CleanerTicket.getParentTicketId());
		            if ( ignoreFieldSet || fieldSet.contains("customerId")) 
			            json.put("customerId", ""+_CleanerTicket.getCustomerId());
		            if ( ignoreFieldSet || fieldSet.contains("enterUserId")) 
			            json.put("enterUserId", ""+_CleanerTicket.getEnterUserId());
		            if ( ignoreFieldSet || fieldSet.contains("locationId")) 
			            json.put("locationId", ""+_CleanerTicket.getLocationId());
		            if ( ignoreFieldSet || fieldSet.contains("note")) 
			            json.put("note", ""+_CleanerTicket.getNote());
		            if ( ignoreFieldSet || fieldSet.contains("completed")) 
			            json.put("completed", ""+_CleanerTicket.getCompleted());
		            if ( ignoreFieldSet || fieldSet.contains("onhold")) 
			            json.put("onhold", ""+_CleanerTicket.getOnhold());
		            if ( ignoreFieldSet || fieldSet.contains("originalTicketId")) 
			            json.put("originalTicketId", ""+_CleanerTicket.getOriginalTicketId());
		            if ( ignoreFieldSet || fieldSet.contains("returned")) 
			            json.put("returned", ""+_CleanerTicket.getReturned());
		            if ( ignoreFieldSet || fieldSet.contains("returnedReasonText")) 
			            json.put("returnedReasonText", ""+_CleanerTicket.getReturnedReasonText());
		            if ( ignoreFieldSet || fieldSet.contains("returnedNote")) 
			            json.put("returnedNote", ""+_CleanerTicket.getReturnedNote());
		            if ( ignoreFieldSet || fieldSet.contains("totalCharge")) 
			            json.put("totalCharge", ""+_CleanerTicket.getTotalCharge());
		            if ( ignoreFieldSet || fieldSet.contains("finalCharge")) 
			            json.put("finalCharge", ""+_CleanerTicket.getFinalCharge());
		            if ( ignoreFieldSet || fieldSet.contains("discountId")) 
			            json.put("discountId", ""+_CleanerTicket.getDiscountId());
		            if ( ignoreFieldSet || fieldSet.contains("discountAmount")) 
			            json.put("discountAmount", ""+_CleanerTicket.getDiscountAmount());
		            if ( ignoreFieldSet || fieldSet.contains("discountNote")) 
			            json.put("discountNote", ""+_CleanerTicket.getDiscountNote());
		            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
			            json.put("timeCreated", ""+_CleanerTicket.getTimeCreated());
		            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
			            json.put("timeUpdated", ""+_CleanerTicket.getTimeUpdated());
		            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) 
			            json.put("timeCompleted", ""+_CleanerTicket.getTimeCompleted());
		            if ( ignoreFieldSet || fieldSet.contains("timeOnhold")) 
			            json.put("timeOnhold", ""+_CleanerTicket.getTimeOnhold());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                CleanerTicket _CleanerTicket = list.size() >=1?(CleanerTicket) list.get(0): null; 

				if ( _CleanerTicket != null) {
	                top.put("type", "object");
		            top.put("id", ""+_CleanerTicket.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonSerial = new JSONObject();
		            jsonSerial.put("name", "serial");
		            jsonSerial.put("value", ""+_CleanerTicket.getSerial());
		            array.put(jsonSerial);
		            JSONObject jsonParentTicketId = new JSONObject();
		            jsonParentTicketId.put("name", "parentTicketId");
		            jsonParentTicketId.put("value", ""+_CleanerTicket.getParentTicketId());
		            array.put(jsonParentTicketId);
		            JSONObject jsonCustomerId = new JSONObject();
		            jsonCustomerId.put("name", "customerId");
		            jsonCustomerId.put("value", ""+_CleanerTicket.getCustomerId());
		            array.put(jsonCustomerId);
		            JSONObject jsonEnterUserId = new JSONObject();
		            jsonEnterUserId.put("name", "enterUserId");
		            jsonEnterUserId.put("value", ""+_CleanerTicket.getEnterUserId());
		            array.put(jsonEnterUserId);
		            JSONObject jsonLocationId = new JSONObject();
		            jsonLocationId.put("name", "locationId");
		            jsonLocationId.put("value", ""+_CleanerTicket.getLocationId());
		            array.put(jsonLocationId);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerTicket.getNote());
		            array.put(jsonNote);
		            JSONObject jsonCompleted = new JSONObject();
		            jsonCompleted.put("name", "completed");
		            jsonCompleted.put("value", ""+_CleanerTicket.getCompleted());
		            array.put(jsonCompleted);
		            JSONObject jsonOnhold = new JSONObject();
		            jsonOnhold.put("name", "onhold");
		            jsonOnhold.put("value", ""+_CleanerTicket.getOnhold());
		            array.put(jsonOnhold);
		            JSONObject jsonOriginalTicketId = new JSONObject();
		            jsonOriginalTicketId.put("name", "originalTicketId");
		            jsonOriginalTicketId.put("value", ""+_CleanerTicket.getOriginalTicketId());
		            array.put(jsonOriginalTicketId);
		            JSONObject jsonReturned = new JSONObject();
		            jsonReturned.put("name", "returned");
		            jsonReturned.put("value", ""+_CleanerTicket.getReturned());
		            array.put(jsonReturned);
		            JSONObject jsonReturnedReasonText = new JSONObject();
		            jsonReturnedReasonText.put("name", "returnedReasonText");
		            jsonReturnedReasonText.put("value", ""+_CleanerTicket.getReturnedReasonText());
		            array.put(jsonReturnedReasonText);
		            JSONObject jsonReturnedNote = new JSONObject();
		            jsonReturnedNote.put("name", "returnedNote");
		            jsonReturnedNote.put("value", ""+_CleanerTicket.getReturnedNote());
		            array.put(jsonReturnedNote);
		            JSONObject jsonTotalCharge = new JSONObject();
		            jsonTotalCharge.put("name", "totalCharge");
		            jsonTotalCharge.put("value", ""+_CleanerTicket.getTotalCharge());
		            array.put(jsonTotalCharge);
		            JSONObject jsonFinalCharge = new JSONObject();
		            jsonFinalCharge.put("name", "finalCharge");
		            jsonFinalCharge.put("value", ""+_CleanerTicket.getFinalCharge());
		            array.put(jsonFinalCharge);
		            JSONObject jsonDiscountId = new JSONObject();
		            jsonDiscountId.put("name", "discountId");
		            jsonDiscountId.put("value", ""+_CleanerTicket.getDiscountId());
		            array.put(jsonDiscountId);
		            JSONObject jsonDiscountAmount = new JSONObject();
		            jsonDiscountAmount.put("name", "discountAmount");
		            jsonDiscountAmount.put("value", ""+_CleanerTicket.getDiscountAmount());
		            array.put(jsonDiscountAmount);
		            JSONObject jsonDiscountNote = new JSONObject();
		            jsonDiscountNote.put("name", "discountNote");
		            jsonDiscountNote.put("value", ""+_CleanerTicket.getDiscountNote());
		            array.put(jsonDiscountNote);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_CleanerTicket.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_CleanerTicket.getTimeUpdated());
		            array.put(jsonTimeUpdated);
		            JSONObject jsonTimeCompleted = new JSONObject();
		            jsonTimeCompleted.put("name", "timeCompleted");
		            jsonTimeCompleted.put("value", ""+_CleanerTicket.getTimeCompleted());
		            array.put(jsonTimeCompleted);
		            JSONObject jsonTimeOnhold = new JSONObject();
		            jsonTimeOnhold.put("name", "timeOnhold");
		            jsonTimeOnhold.put("value", ""+_CleanerTicket.getTimeOnhold());
		            array.put(jsonTimeOnhold);
	    	        top.put("fields", array);
	                //AutositeSynchLedger synchLedger = null;
                    if ( isSynchRequired() ){
		                try {
	                        if ( synchLedger == null)
			                    synchLedger = processSynchRequest(request, _CleanerTicket);

	                        if ( synchLedger != null)
	                            top.put("_synchId", synchLedger.getSynchId());
	                    }
	                    catch (Exception e) {
	                        m_logger.error(e.getMessage(),e);
	                    }
					}
				}
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

                CleanerTicket  _CleanerTicket = CleanerTicketDS.getInstance().getById(synchTrackingEntry.getObjectId());
                
                JSONObject json = new JSONObject();
                
                if ( _CleanerTicket != null) {
                    json.put("type", "object");
                    json.put("id", ""+_CleanerTicket.getId());
                    JSONArray array = new JSONArray();

		            JSONObject jsonSerial = new JSONObject();
		            jsonSerial.put("name", "serial");
		            jsonSerial.put("value", ""+_CleanerTicket.getSerial());
		            array.put(jsonSerial);
		            JSONObject jsonParentTicketId = new JSONObject();
		            jsonParentTicketId.put("name", "parentTicketId");
		            jsonParentTicketId.put("value", ""+_CleanerTicket.getParentTicketId());
		            array.put(jsonParentTicketId);
		            JSONObject jsonCustomerId = new JSONObject();
		            jsonCustomerId.put("name", "customerId");
		            jsonCustomerId.put("value", ""+_CleanerTicket.getCustomerId());
		            array.put(jsonCustomerId);
		            JSONObject jsonEnterUserId = new JSONObject();
		            jsonEnterUserId.put("name", "enterUserId");
		            jsonEnterUserId.put("value", ""+_CleanerTicket.getEnterUserId());
		            array.put(jsonEnterUserId);
		            JSONObject jsonLocationId = new JSONObject();
		            jsonLocationId.put("name", "locationId");
		            jsonLocationId.put("value", ""+_CleanerTicket.getLocationId());
		            array.put(jsonLocationId);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_CleanerTicket.getNote());
		            array.put(jsonNote);
		            JSONObject jsonCompleted = new JSONObject();
		            jsonCompleted.put("name", "completed");
		            jsonCompleted.put("value", ""+_CleanerTicket.getCompleted());
		            array.put(jsonCompleted);
		            JSONObject jsonOnhold = new JSONObject();
		            jsonOnhold.put("name", "onhold");
		            jsonOnhold.put("value", ""+_CleanerTicket.getOnhold());
		            array.put(jsonOnhold);
		            JSONObject jsonOriginalTicketId = new JSONObject();
		            jsonOriginalTicketId.put("name", "originalTicketId");
		            jsonOriginalTicketId.put("value", ""+_CleanerTicket.getOriginalTicketId());
		            array.put(jsonOriginalTicketId);
		            JSONObject jsonReturned = new JSONObject();
		            jsonReturned.put("name", "returned");
		            jsonReturned.put("value", ""+_CleanerTicket.getReturned());
		            array.put(jsonReturned);
		            JSONObject jsonReturnedReasonText = new JSONObject();
		            jsonReturnedReasonText.put("name", "returnedReasonText");
		            jsonReturnedReasonText.put("value", ""+_CleanerTicket.getReturnedReasonText());
		            array.put(jsonReturnedReasonText);
		            JSONObject jsonReturnedNote = new JSONObject();
		            jsonReturnedNote.put("name", "returnedNote");
		            jsonReturnedNote.put("value", ""+_CleanerTicket.getReturnedNote());
		            array.put(jsonReturnedNote);
		            JSONObject jsonTotalCharge = new JSONObject();
		            jsonTotalCharge.put("name", "totalCharge");
		            jsonTotalCharge.put("value", ""+_CleanerTicket.getTotalCharge());
		            array.put(jsonTotalCharge);
		            JSONObject jsonFinalCharge = new JSONObject();
		            jsonFinalCharge.put("name", "finalCharge");
		            jsonFinalCharge.put("value", ""+_CleanerTicket.getFinalCharge());
		            array.put(jsonFinalCharge);
		            JSONObject jsonDiscountId = new JSONObject();
		            jsonDiscountId.put("name", "discountId");
		            jsonDiscountId.put("value", ""+_CleanerTicket.getDiscountId());
		            array.put(jsonDiscountId);
		            JSONObject jsonDiscountAmount = new JSONObject();
		            jsonDiscountAmount.put("name", "discountAmount");
		            jsonDiscountAmount.put("value", ""+_CleanerTicket.getDiscountAmount());
		            array.put(jsonDiscountAmount);
		            JSONObject jsonDiscountNote = new JSONObject();
		            jsonDiscountNote.put("name", "discountNote");
		            jsonDiscountNote.put("value", ""+_CleanerTicket.getDiscountNote());
		            array.put(jsonDiscountNote);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            jsonTimeCreated.put("value", ""+_CleanerTicket.getTimeCreated());
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_CleanerTicket.getTimeUpdated());
		            array.put(jsonTimeUpdated);
		            JSONObject jsonTimeCompleted = new JSONObject();
		            jsonTimeCompleted.put("name", "timeCompleted");
		            jsonTimeCompleted.put("value", ""+_CleanerTicket.getTimeCompleted());
		            array.put(jsonTimeCompleted);
		            JSONObject jsonTimeOnhold = new JSONObject();
		            jsonTimeOnhold.put("name", "timeOnhold");
		            jsonTimeOnhold.put("value", ""+_CleanerTicket.getTimeOnhold());
		            array.put(jsonTimeOnhold);

                    json.put("fields", array);
                }

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
            buf.append("sendFormAjax('/cleanerTicketAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/cleanerTicketAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


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

            if ( ignoreFieldSet || fieldSet.contains("enterUserId")) {
                String value = WebUtil.display(request.getParameter("enterUserId"));

                if ( forceHiddenSet.contains("enterUserId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"enterUserId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Enter User Id</div>");
            buf.append("<INPUT NAME=\"enterUserId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

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

            if ( ignoreFieldSet || fieldSet.contains("completed")) {
                String value = WebUtil.display(request.getParameter("completed"));

                if ( forceHiddenSet.contains("completed")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"completed\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Completed</div>");
            buf.append("<INPUT NAME=\"completed\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("onhold")) {
                String value = WebUtil.display(request.getParameter("onhold"));

                if ( forceHiddenSet.contains("onhold")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"onhold\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Onhold</div>");
            buf.append("<INPUT NAME=\"onhold\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("originalTicketId")) {
                String value = WebUtil.display(request.getParameter("originalTicketId"));

                if ( forceHiddenSet.contains("originalTicketId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"originalTicketId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Original Ticket Id</div>");
            buf.append("<INPUT NAME=\"originalTicketId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("returned")) {
                String value = WebUtil.display(request.getParameter("returned"));

                if ( forceHiddenSet.contains("returned")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"returned\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Returned</div>");
            buf.append("<INPUT NAME=\"returned\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("returnedReasonText")) {
                String value = WebUtil.display(request.getParameter("returnedReasonText"));

                if ( forceHiddenSet.contains("returnedReasonText")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"returnedReasonText\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Returned Reason Text</div>");
            buf.append("<INPUT NAME=\"returnedReasonText\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("returnedNote")) {
                String value = WebUtil.display(request.getParameter("returnedNote"));

                if ( forceHiddenSet.contains("returnedNote")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"returnedNote\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Returned Note</div>");
            buf.append("<INPUT NAME=\"returnedNote\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("totalCharge")) {
                String value = WebUtil.display(request.getParameter("totalCharge"));

                if ( forceHiddenSet.contains("totalCharge")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"totalCharge\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Total Charge</div>");
            buf.append("<INPUT NAME=\"totalCharge\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("finalCharge")) {
                String value = WebUtil.display(request.getParameter("finalCharge"));

                if ( forceHiddenSet.contains("finalCharge")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"finalCharge\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Final Charge</div>");
            buf.append("<INPUT NAME=\"finalCharge\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("discountAmount")) {
                String value = WebUtil.display(request.getParameter("discountAmount"));

                if ( forceHiddenSet.contains("discountAmount")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"discountAmount\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Discount Amount</div>");
            buf.append("<INPUT NAME=\"discountAmount\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("discountNote")) {
                String value = WebUtil.display(request.getParameter("discountNote"));

                if ( forceHiddenSet.contains("discountNote")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"discountNote\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Discount Note</div>");
            buf.append("<INPUT NAME=\"discountNote\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("timeCompleted")) {
                String value = WebUtil.display(request.getParameter("timeCompleted"));

                if ( forceHiddenSet.contains("timeCompleted")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeCompleted\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Completed</div>");
            buf.append("<INPUT NAME=\"timeCompleted\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("timeOnhold")) {
                String value = WebUtil.display(request.getParameter("timeOnhold"));

                if ( forceHiddenSet.contains("timeOnhold")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"timeOnhold\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Onhold</div>");
            buf.append("<INPUT NAME=\"timeOnhold\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_cleanerTicket(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayCleanerTicket\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_cleanerTicket(){\n";
            importedScripts +=     "xmlhttpPostXX('cleanerTicketFormAddDis','/cleanerTicketAction.html', 'resultDisplayCleanerTicket', '${ajax_response_fields}', responseCallback_cleanerTicket);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_cleanerTicket(){\n";
            importedScripts +=     "clearFormXX('cleanerTicketFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_cleanerTicket(){\n";
            importedScripts +=     "backToXX('cleanerTicketFormAddDis','resultDisplayCleanerTicket');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"cleanerTicketFormAddDis\" method=\"POST\" action=\"/cleanerTicketAction.html\" id=\"cleanerTicketFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Serial</div>");
        buf.append("<input class=\"field\" id=\"serial\" type=\"text\" size=\"70\" name=\"serial\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Parent Ticket Id</div>");
        buf.append("<input class=\"field\" id=\"parentTicketId\" type=\"text\" size=\"70\" name=\"parentTicketId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Customer Id</div>");
        buf.append("<input class=\"field\" id=\"customerId\" type=\"text\" size=\"70\" name=\"customerId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Enter User Id</div>");
        buf.append("<input class=\"field\" id=\"enterUserId\" type=\"text\" size=\"70\" name=\"enterUserId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Location Id</div>");
        buf.append("<input class=\"field\" id=\"locationId\" type=\"text\" size=\"70\" name=\"locationId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
        buf.append("<input class=\"field\" id=\"note\" type=\"text\" size=\"70\" name=\"note\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Completed</div>");
        buf.append("<input class=\"field\" id=\"completed\" type=\"text\" size=\"70\" name=\"completed\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Onhold</div>");
        buf.append("<input class=\"field\" id=\"onhold\" type=\"text\" size=\"70\" name=\"onhold\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Original Ticket Id</div>");
        buf.append("<input class=\"field\" id=\"originalTicketId\" type=\"text\" size=\"70\" name=\"originalTicketId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Returned</div>");
        buf.append("<input class=\"field\" id=\"returned\" type=\"text\" size=\"70\" name=\"returned\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Returned Reason Text</div>");
        buf.append("<input class=\"field\" id=\"returnedReasonText\" type=\"text\" size=\"70\" name=\"returnedReasonText\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Returned Note</div>");
        buf.append("<input class=\"field\" id=\"returnedNote\" type=\"text\" size=\"70\" name=\"returnedNote\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Total Charge</div>");
        buf.append("<input class=\"field\" id=\"totalCharge\" type=\"text\" size=\"70\" name=\"totalCharge\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Final Charge</div>");
        buf.append("<input class=\"field\" id=\"finalCharge\" type=\"text\" size=\"70\" name=\"finalCharge\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Discount Id</div>");
        buf.append("<input class=\"field\" id=\"discountId\" type=\"text\" size=\"70\" name=\"discountId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Discount Amount</div>");
        buf.append("<input class=\"field\" id=\"discountAmount\" type=\"text\" size=\"70\" name=\"discountAmount\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Discount Note</div>");
        buf.append("<input class=\"field\" id=\"discountNote\" type=\"text\" size=\"70\" name=\"discountNote\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Completed</div>");
        buf.append("<input class=\"field\" id=\"timeCompleted\" type=\"text\" size=\"70\" name=\"timeCompleted\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Onhold</div>");
        buf.append("<input class=\"field\" id=\"timeOnhold\" type=\"text\" size=\"70\" name=\"timeOnhold\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_cleanerTicket()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_cleanerTicket()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayCleanerTicket\"></span>");
			buf.append("<a href=\"javascript:showform_cleanerTicket()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, CleanerTicket target){

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

    protected List prepareReturnData(HttpServletRequest request, CleanerTicket target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = CleanerTicketDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            CleanerTicket _CleanerTicket = null; 
            List list = CleanerTicketDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _CleanerTicket = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _CleanerTicket = (CleanerTicket) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _CleanerTicket = (CleanerTicket) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _CleanerTicket = CleanerTicketDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_CleanerTicket);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( CleanerTicketAjaxAction.class);
}
