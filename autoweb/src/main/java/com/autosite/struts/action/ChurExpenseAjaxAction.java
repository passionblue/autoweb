package com.autosite.struts.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jtrend.util.RequestUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.jtrend.util.JtStringUtil;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
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

import com.autosite.db.ChurExpense;
import com.autosite.ds.ChurExpenseDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ChurExpenseForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check

import com.autosite.ds.ChurExpenseItemDS;
import com.autosite.db.ChurExpenseItem;
import com.autosite.ds.ChurPayeeDS;
import com.autosite.db.ChurPayee;

import com.autosite.holder.ChurExpenseDataHolder;


public class ChurExpenseAjaxAction extends ChurExpenseAction {

    public ChurExpenseAjaxAction(){
    }


    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        ChurExpense target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (ChurExpense) working.get("target");
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

        if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
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

        } else if (hasRequestValue(request, "ajaxOut", "getfield")){
            m_logger.debug("Ajax Processing getfield arg = " + request.getParameter("id"));
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ChurExpense _ChurExpense = ChurExpenseDS.getInstance().getById(id);
            if (_ChurExpense == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ChurExpense);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ChurExpense _ChurExpense = ChurExpenseDS.getInstance().getById(id);
            if ( _ChurExpense == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _ChurExpense);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
            m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
            String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
            String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
            
            
            m_logger.debug("Number of objects to return " + list.size());
            StringBuilder buf = new StringBuilder();

            buf.append("<div id=\"churExpense-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurExpense _ChurExpense = (ChurExpense) iterator.next();

                buf.append("<div id=\"churExpense-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("year")) {
                    buf.append("<div id=\"churExpense-ajax-year\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getYear()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("week")) {
                    buf.append("<div id=\"churExpense-ajax-week\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getWeek()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("expenseItemId")) {
                    buf.append("<div id=\"churExpense-ajax-expenseItemId\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getExpenseItemId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("payeeId")) {
                    buf.append("<div id=\"churExpense-ajax-payeeId\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getPayeeId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("amount")) {
                    buf.append("<div id=\"churExpense-ajax-amount\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getAmount()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isCash")) {
                    buf.append("<div id=\"churExpense-ajax-isCash\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getIsCash()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("checkNumber")) {
                    buf.append("<div id=\"churExpense-ajax-checkNumber\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getCheckNumber()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("checkCleared")) {
                    buf.append("<div id=\"churExpense-ajax-checkCleared\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getCheckCleared()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("comment")) {
                    buf.append("<div id=\"churExpense-ajax-comment\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getComment()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("cancelled")) {
                    buf.append("<div id=\"churExpense-ajax-cancelled\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getCancelled()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"churExpense-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"churExpense-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurExpense.getTimeUpdated()));
                    buf.append("</div>");

				}
                buf.append("</div><div class=\"clear\"></div>");

            }
            
            buf.append("</div>");
            ret.put("__value", buf.toString());
            return ret;



        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ){
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
            if ( ignoreFieldSet || fieldSet.contains("year")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Year");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("week")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Week");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("expenseItemId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Expense Item Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("payeeId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Payee Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("amount")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Amount");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isCash")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Cash");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("checkNumber")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Check Number");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("checkCleared")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Check Cleared");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("comment")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Comment");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("cancelled")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Cancelled");
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
                ChurExpense _ChurExpense = (ChurExpense) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("year")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getYear()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("week")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getWeek()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("expenseItemId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getExpenseItemId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("payeeId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getPayeeId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("amount")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getAmount()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isCash")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_ChurExpense.getIsCash()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/churExpenseAction.html?ef=true&id="+ _ChurExpense.getId()+"&isCash=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/churExpenseAction.html?ef=true&id="+ _ChurExpense.getId()+"&isCash=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("checkNumber")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getCheckNumber()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("checkCleared")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_ChurExpense.getCheckCleared()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/churExpenseAction.html?ef=true&id="+ _ChurExpense.getId()+"&checkCleared=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/churExpenseAction.html?ef=true&id="+ _ChurExpense.getId()+"&checkCleared=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("comment")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getComment()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("cancelled")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            if (WebUtil.isTrue(_ChurExpense.getCancelled()))
                        buf.append("[<a href=\"javascript:sendFormAjaxSimple('/churExpenseAction.html?ef=true&id="+ _ChurExpense.getId()+"&cancelled=0&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set No</a>]");
		            else
		                buf.append("[<a href=\"javascript:sendFormAjaxSimple('/churExpenseAction.html?ef=true&id="+ _ChurExpense.getId()+"&cancelled=1&ajaxRequest=true&ajaxOut=getModalStatus', true);\">Set Yes</a>]");

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurExpense.getTimeUpdated()));

		            buf.append("</td>");
				}
	            buf.append("</tr>");
			}
            buf.append("</table >");
            
            ret.put("__value", buf.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "gethtml2") || hasRequestValue(request, "ajaxOut", "getlisthtml2") ){
            m_logger.debug("Ajax Processing gethtml getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);
            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            HtmlGen gen = new HtmlGen(HtmlGen.TYPE_GEN_ROW, false); // TODO title of the table
            List fieldsTitle = ChurExpenseDataHolder.getFieldsName();
            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurExpenseDataHolder _ChurExpense  = new ChurExpenseDataHolder( (ChurExpense) iterator.next());
                gen.addTableRow(_ChurExpense);
			}
            
            ret.put("__value", gen.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = hasRequestValue(request, "ajaxOut", "getlistjson");

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);


            JSONObject top = new JSONObject();

            if (returnList) {

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                ChurExpense _ChurExpense = (ChurExpense) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("year")) 
			            json.put("year", ""+_ChurExpense.getYear());
		            if ( ignoreFieldSet || fieldSet.contains("week")) 
			            json.put("week", ""+_ChurExpense.getWeek());
		            if ( ignoreFieldSet || fieldSet.contains("expenseItemId")) 
			            json.put("expenseItemId", ""+_ChurExpense.getExpenseItemId());
		            if ( ignoreFieldSet || fieldSet.contains("payeeId")) 
			            json.put("payeeId", ""+_ChurExpense.getPayeeId());
		            if ( ignoreFieldSet || fieldSet.contains("amount")) 
			            json.put("amount", ""+_ChurExpense.getAmount());
		            if ( ignoreFieldSet || fieldSet.contains("isCash")) 
			            json.put("isCash", ""+_ChurExpense.getIsCash());
		            if ( ignoreFieldSet || fieldSet.contains("checkNumber")) 
			            json.put("checkNumber", ""+_ChurExpense.getCheckNumber());
		            if ( ignoreFieldSet || fieldSet.contains("checkCleared")) 
			            json.put("checkCleared", ""+_ChurExpense.getCheckCleared());
		            if ( ignoreFieldSet || fieldSet.contains("comment")) 
			            json.put("comment", ""+_ChurExpense.getComment());
		            if ( ignoreFieldSet || fieldSet.contains("cancelled")) 
			            json.put("cancelled", ""+_ChurExpense.getCancelled());
		            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
			            json.put("timeUpdated", ""+_ChurExpense.getTimeUpdated());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                ChurExpense _ChurExpense = list.size() >=1?(ChurExpense) list.get(0): null; 

				if ( _ChurExpense != null) {
		            top.put("id", ""+_ChurExpense.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonYear = new JSONObject();
		            jsonYear.put("name", "year");
		            jsonYear.put("value", ""+_ChurExpense.getYear());
		            array.put(jsonYear);
		            JSONObject jsonWeek = new JSONObject();
		            jsonWeek.put("name", "week");
		            jsonWeek.put("value", ""+_ChurExpense.getWeek());
		            array.put(jsonWeek);
		            JSONObject jsonExpenseItemId = new JSONObject();
		            jsonExpenseItemId.put("name", "expenseItemId");
		            jsonExpenseItemId.put("value", ""+_ChurExpense.getExpenseItemId());
		            array.put(jsonExpenseItemId);
		            JSONObject jsonPayeeId = new JSONObject();
		            jsonPayeeId.put("name", "payeeId");
		            jsonPayeeId.put("value", ""+_ChurExpense.getPayeeId());
		            array.put(jsonPayeeId);
		            JSONObject jsonAmount = new JSONObject();
		            jsonAmount.put("name", "amount");
		            jsonAmount.put("value", ""+_ChurExpense.getAmount());
		            array.put(jsonAmount);
		            JSONObject jsonIsCash = new JSONObject();
		            jsonIsCash.put("name", "isCash");
		            jsonIsCash.put("value", ""+_ChurExpense.getIsCash());
		            array.put(jsonIsCash);
		            JSONObject jsonCheckNumber = new JSONObject();
		            jsonCheckNumber.put("name", "checkNumber");
		            jsonCheckNumber.put("value", ""+_ChurExpense.getCheckNumber());
		            array.put(jsonCheckNumber);
		            JSONObject jsonCheckCleared = new JSONObject();
		            jsonCheckCleared.put("name", "checkCleared");
		            jsonCheckCleared.put("value", ""+_ChurExpense.getCheckCleared());
		            array.put(jsonCheckCleared);
		            JSONObject jsonComment = new JSONObject();
		            jsonComment.put("name", "comment");
		            jsonComment.put("value", ""+_ChurExpense.getComment());
		            array.put(jsonComment);
		            JSONObject jsonCancelled = new JSONObject();
		            jsonCancelled.put("name", "cancelled");
		            jsonCancelled.put("value", ""+_ChurExpense.getCancelled());
		            array.put(jsonCancelled);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            jsonTimeUpdated.put("value", ""+_ChurExpense.getTimeUpdated());
		            array.put(jsonTimeUpdated);

	    	        top.put("fields", array);
				}
			}

            m_logger.debug(top.toString());
            ret.put("__value", top.toString());
            return ret;
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")){
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
            buf.append("sendFormAjax('/churExpenseAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/churExpenseAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("year")) {
                String value = WebUtil.display(request.getParameter("year"));

                if ( forceHiddenSet.contains("year")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"year\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Year</div>");
            buf.append("<INPUT NAME=\"year\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("week")) {
                String value = WebUtil.display(request.getParameter("week"));

                if ( forceHiddenSet.contains("week")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"week\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Week</div>");
            buf.append("<INPUT NAME=\"week\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("expenseItemId")) {
                String value = WebUtil.display(request.getParameter("expenseItemId"));

                if ( forceHiddenSet.contains("expenseItemId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"expenseItemId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Expense Item Id</div>");
            buf.append("<select id=\"requiredField\" name=\"expenseItemId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("ChurExpenseExpense Item IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("payeeId")) {
                String value = WebUtil.display(request.getParameter("payeeId"));

                if ( forceHiddenSet.contains("payeeId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"payeeId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Payee Id</div>");
            buf.append("<select id=\"requiredField\" name=\"payeeId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("ChurExpensePayee IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("amount")) {
                String value = WebUtil.display(request.getParameter("amount"));

                if ( forceHiddenSet.contains("amount")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"amount\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Amount</div>");
            buf.append("<INPUT NAME=\"amount\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isCash")) {
                String value = WebUtil.display(request.getParameter("isCash"));

                if ( forceHiddenSet.contains("isCash")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isCash\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Cash</div>");
            buf.append("<select name=\"isCash\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("checkNumber")) {
                String value = WebUtil.display(request.getParameter("checkNumber"));

                if ( forceHiddenSet.contains("checkNumber")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"checkNumber\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Check Number</div>");
            buf.append("<INPUT NAME=\"checkNumber\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("checkCleared")) {
                String value = WebUtil.display(request.getParameter("checkCleared"));

                if ( forceHiddenSet.contains("checkCleared")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"checkCleared\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Check Cleared</div>");
            buf.append("<select name=\"checkCleared\">");
        	buf.append("<option value=\"0\" >No</option>");
        	buf.append("<option value=\"1\" >Yes</option>");
            buf.append("</select><br/>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("comment")) {
                String value = WebUtil.display(request.getParameter("comment"));

                if ( forceHiddenSet.contains("comment")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"comment\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Comment</div>");
            buf.append("<INPUT NAME=\"comment\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Time Updated</div>");
            buf.append("<INPUT NAME=\"timeUpdated\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform2") || (hasRequestValue(request, "ajaxOut", "getscriptform"))){

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
            importedScripts += "function responseCallback_churExpense(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayChurExpense\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_churExpense(){\n";
            importedScripts +=     "xmlhttpPostXX('churExpenseFormAddDis','/churExpenseAction.html', 'resultDisplayChurExpense', '${ajax_response_fields}', responseCallback_churExpense);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_churExpense(){\n";
            importedScripts +=     "clearFormXX('churExpenseFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_churExpense(){\n";
            importedScripts +=     "backToXX('churExpenseFormAddDis','resultDisplayChurExpense');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"churExpenseFormAddDis\" method=\"POST\" action=\"/churExpenseAction.html\" id=\"churExpenseFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Year</div>");
        buf.append("<input class=\"field\" id=\"year\" type=\"text\" size=\"70\" name=\"year\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Week</div>");
        buf.append("<input class=\"field\" id=\"week\" type=\"text\" size=\"70\" name=\"week\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Expense Item Id</div>");
        buf.append("<select class=\"field\" name=\"expenseItemId\" id=\"expenseItemId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listChurExpenseItem_expenseItemId = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurExpenseItem_expenseItemId.iterator(); iter.hasNext();){
		ChurExpenseItem _obj = (ChurExpenseItem) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getExpenseItem() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Payee Id</div>");
        buf.append("<select class=\"field\" name=\"payeeId\" id=\"payeeId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listChurPayee_payeeId = ChurPayeeDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurPayee_payeeId.iterator(); iter.hasNext();){
		ChurPayee _obj = (ChurPayee) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getTitle() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Amount</div>");
        buf.append("<input class=\"field\" id=\"amount\" type=\"text\" size=\"70\" name=\"amount\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Cash</div>");
        buf.append("<select name=\"isCash\" id=\"isCash\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Check Number</div>");
        buf.append("<input class=\"field\" id=\"checkNumber\" type=\"text\" size=\"70\" name=\"checkNumber\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Check Cleared</div>");
        buf.append("<select name=\"checkCleared\" id=\"checkCleared\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Comment</div>");
        buf.append("<input class=\"field\" id=\"comment\" type=\"text\" size=\"70\" name=\"comment\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Cancelled</div>");
        buf.append("<select name=\"cancelled\" id=\"cancelled\">");
        buf.append("<option value=\"0\" >No</option>");
        buf.append("<option value=\"1\" >Yes</option>");
        buf.append("</select><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Time Updated</div>");
        buf.append("<input class=\"field\" id=\"timeUpdated\" type=\"text\" size=\"70\" name=\"timeUpdated\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_churExpense()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_churExpense()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayChurExpense\"></span>");
			buf.append("<a href=\"javascript:showform_churExpense()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());
            
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
    protected List prepareReturnData(HttpServletRequest request, ChurExpense target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            ChurExpense _ChurExpense = null; 
            List list = ChurExpenseDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _ChurExpense = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _ChurExpense = (ChurExpense) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ChurExpense = (ChurExpense) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ChurExpense = ChurExpenseDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_ChurExpense);

        } else {
            
            List list = ChurExpenseDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }

    private static Logger m_logger = Logger.getLogger( ChurExpenseAjaxAction.class);
}
