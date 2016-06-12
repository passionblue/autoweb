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

import com.autosite.ds.ChurIncomeAddDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.ChurIncomeAddForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check

import com.autosite.ds.ChurMemberDS;
import com.autosite.db.ChurMember;

import com.autosite.holder.ChurIncomeAddDataHolder;


public class ChurIncomeAddAjaxAction extends ChurIncomeAddAction {

    public ChurIncomeAddAjaxAction(){
    }


    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        ChurIncomeAddDataHolder target = null;
        
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
                target = (ChurIncomeAddDataHolder) working.get("target");
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
            ChurIncomeAddDataHolder _ChurIncomeAdd = ChurIncomeAddDS.getInstance().getById(id);
            if (_ChurIncomeAdd == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _ChurIncomeAdd);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            ChurIncomeAddDataHolder _ChurIncomeAdd = ChurIncomeAddDS.getInstance().getById(id);
            if ( _ChurIncomeAdd == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _ChurIncomeAdd);
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

            buf.append("<div id=\"churIncomeAdd-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurIncomeAddDataHolder _ChurIncomeAdd = (ChurIncomeAddDataHolder) iterator.next();

                buf.append("<div id=\"churIncomeAdd-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("churMemberId")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-churMemberId\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getChurMemberId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("tithe")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-tithe\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getTithe()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("weekly")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-weekly\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getWeekly()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("thanks")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-thanks\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getThanks()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("mission")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-mission\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getMission()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("construction")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-construction\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getConstruction()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("other")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-other\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getOther()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("week")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-week\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getWeek()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("year")) {
                    buf.append("<div id=\"churIncomeAdd-ajax-year\" "+itemClass+">");
                    buf.append(WebUtil.display(_ChurIncomeAdd.getYear()));
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
            if ( ignoreFieldSet || fieldSet.contains("churMemberId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Chur Member Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("tithe")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Tithe");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("weekly")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Weekly");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("thanks")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Thanks");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("mission")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Mission");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("construction")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Construction");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("other")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Other");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("week")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Week");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("year")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Year");
	            buf.append("</td>");
			}
            buf.append("</tr>");

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurIncomeAddDataHolder _ChurIncomeAdd = (ChurIncomeAddDataHolder) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("churMemberId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getChurMemberId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("tithe")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getTithe()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("weekly")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getWeekly()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("thanks")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getThanks()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("mission")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getMission()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("construction")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getConstruction()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("other")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getOther()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("week")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getWeek()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("year")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_ChurIncomeAdd.getYear()));

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
            List fieldsTitle = ChurIncomeAddDataHolder.getFieldsName();
            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                ChurIncomeAddDataHolder _ChurIncomeAdd = (ChurIncomeAddDataHolder) iterator.next();
                gen.addTableRow(_ChurIncomeAdd);
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
	                ChurIncomeAddDataHolder _ChurIncomeAdd = (ChurIncomeAddDataHolder) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("churMemberId")) 
			            json.put("churMemberId", ""+_ChurIncomeAdd.getChurMemberId());
		            if ( ignoreFieldSet || fieldSet.contains("tithe")) 
			            json.put("tithe", ""+_ChurIncomeAdd.getTithe());
		            if ( ignoreFieldSet || fieldSet.contains("weekly")) 
			            json.put("weekly", ""+_ChurIncomeAdd.getWeekly());
		            if ( ignoreFieldSet || fieldSet.contains("thanks")) 
			            json.put("thanks", ""+_ChurIncomeAdd.getThanks());
		            if ( ignoreFieldSet || fieldSet.contains("mission")) 
			            json.put("mission", ""+_ChurIncomeAdd.getMission());
		            if ( ignoreFieldSet || fieldSet.contains("construction")) 
			            json.put("construction", ""+_ChurIncomeAdd.getConstruction());
		            if ( ignoreFieldSet || fieldSet.contains("other")) 
			            json.put("other", ""+_ChurIncomeAdd.getOther());
		            if ( ignoreFieldSet || fieldSet.contains("week")) 
			            json.put("week", ""+_ChurIncomeAdd.getWeek());
		            if ( ignoreFieldSet || fieldSet.contains("year")) 
			            json.put("year", ""+_ChurIncomeAdd.getYear());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                ChurIncomeAddDataHolder _ChurIncomeAdd = list.size() >=1?(ChurIncomeAddDataHolder) list.get(0): null; 

				if ( _ChurIncomeAdd != null) {
		            top.put("id", ""+_ChurIncomeAdd.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonChurMemberId = new JSONObject();
		            jsonChurMemberId.put("name", "churMemberId");
		            jsonChurMemberId.put("value", ""+_ChurIncomeAdd.getChurMemberId());
		            array.put(jsonChurMemberId);
		            JSONObject jsonTithe = new JSONObject();
		            jsonTithe.put("name", "tithe");
		            jsonTithe.put("value", ""+_ChurIncomeAdd.getTithe());
		            array.put(jsonTithe);
		            JSONObject jsonWeekly = new JSONObject();
		            jsonWeekly.put("name", "weekly");
		            jsonWeekly.put("value", ""+_ChurIncomeAdd.getWeekly());
		            array.put(jsonWeekly);
		            JSONObject jsonThanks = new JSONObject();
		            jsonThanks.put("name", "thanks");
		            jsonThanks.put("value", ""+_ChurIncomeAdd.getThanks());
		            array.put(jsonThanks);
		            JSONObject jsonMission = new JSONObject();
		            jsonMission.put("name", "mission");
		            jsonMission.put("value", ""+_ChurIncomeAdd.getMission());
		            array.put(jsonMission);
		            JSONObject jsonConstruction = new JSONObject();
		            jsonConstruction.put("name", "construction");
		            jsonConstruction.put("value", ""+_ChurIncomeAdd.getConstruction());
		            array.put(jsonConstruction);
		            JSONObject jsonOther = new JSONObject();
		            jsonOther.put("name", "other");
		            jsonOther.put("value", ""+_ChurIncomeAdd.getOther());
		            array.put(jsonOther);
		            JSONObject jsonWeek = new JSONObject();
		            jsonWeek.put("name", "week");
		            jsonWeek.put("value", ""+_ChurIncomeAdd.getWeek());
		            array.put(jsonWeek);
		            JSONObject jsonYear = new JSONObject();
		            jsonYear.put("name", "year");
		            jsonYear.put("value", ""+_ChurIncomeAdd.getYear());
		            array.put(jsonYear);

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
            buf.append("sendFormAjax('/churIncomeAddAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/churIncomeAddAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("churMemberId")) {
                String value = WebUtil.display(request.getParameter("churMemberId"));

                if ( forceHiddenSet.contains("churMemberId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"churMemberId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Chur Member Id</div>");
            buf.append("<select id=\"requiredField\" name=\"churMemberId\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("ChurIncomeAddChur Member IdOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("tithe")) {
                String value = WebUtil.display(request.getParameter("tithe"));

                if ( forceHiddenSet.contains("tithe")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"tithe\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Tithe</div>");
            buf.append("<INPUT NAME=\"tithe\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("weekly")) {
                String value = WebUtil.display(request.getParameter("weekly"));

                if ( forceHiddenSet.contains("weekly")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"weekly\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Weekly</div>");
            buf.append("<INPUT NAME=\"weekly\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("thanks")) {
                String value = WebUtil.display(request.getParameter("thanks"));

                if ( forceHiddenSet.contains("thanks")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"thanks\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Thanks</div>");
            buf.append("<INPUT NAME=\"thanks\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("mission")) {
                String value = WebUtil.display(request.getParameter("mission"));

                if ( forceHiddenSet.contains("mission")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"mission\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Mission</div>");
            buf.append("<INPUT NAME=\"mission\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("construction")) {
                String value = WebUtil.display(request.getParameter("construction"));

                if ( forceHiddenSet.contains("construction")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"construction\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Construction</div>");
            buf.append("<INPUT NAME=\"construction\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("other")) {
                String value = WebUtil.display(request.getParameter("other"));

                if ( forceHiddenSet.contains("other")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"other\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Other</div>");
            buf.append("<INPUT NAME=\"other\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_churIncomeAdd(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayChurIncomeAdd\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_churIncomeAdd(){\n";
            importedScripts +=     "xmlhttpPostXX('churIncomeAddFormAddDis','/churIncomeAddAction.html', 'resultDisplayChurIncomeAdd', '${ajax_response_fields}', responseCallback_churIncomeAdd);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_churIncomeAdd(){\n";
            importedScripts +=     "clearFormXX('churIncomeAddFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_churIncomeAdd(){\n";
            importedScripts +=     "backToXX('churIncomeAddFormAddDis','resultDisplayChurIncomeAdd');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"churIncomeAddFormAddDis\" method=\"POST\" action=\"/churIncomeAddAction.html\" id=\"churIncomeAddFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Chur Member Id</div>");
        buf.append("<select class=\"field\" name=\"churMemberId\" id=\"churMemberId\">");
        buf.append("<option value=\"\" >- Please Select -</option>");

	List _listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
		
        buf.append("<option value=\""+_obj.getId()+"\" >"+_obj.getFullName() + " (" + _obj.getId() + ")</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Tithe</div>");
        buf.append("<input class=\"field\" id=\"tithe\" type=\"text\" size=\"70\" name=\"tithe\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Weekly</div>");
        buf.append("<input class=\"field\" id=\"weekly\" type=\"text\" size=\"70\" name=\"weekly\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Thanks</div>");
        buf.append("<input class=\"field\" id=\"thanks\" type=\"text\" size=\"70\" name=\"thanks\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Mission</div>");
        buf.append("<input class=\"field\" id=\"mission\" type=\"text\" size=\"70\" name=\"mission\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Construction</div>");
        buf.append("<input class=\"field\" id=\"construction\" type=\"text\" size=\"70\" name=\"construction\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Other</div>");
        buf.append("<input class=\"field\" id=\"other\" type=\"text\" size=\"70\" name=\"other\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Week</div>");
        buf.append("<input class=\"field\" id=\"week\" type=\"text\" size=\"70\" name=\"week\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Year</div>");
        buf.append("<input class=\"field\" id=\"year\" type=\"text\" size=\"70\" name=\"year\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_churIncomeAdd()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_churIncomeAdd()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayChurIncomeAdd\"></span>");
			buf.append("<a href=\"javascript:showform_churIncomeAdd()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, ChurIncomeAddDataHolder target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            ChurIncomeAddDataHolder _ChurIncomeAdd = null; 
            List list = ChurIncomeAddDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _ChurIncomeAdd = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _ChurIncomeAdd = (ChurIncomeAddDataHolder) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _ChurIncomeAdd = (ChurIncomeAddDataHolder) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _ChurIncomeAdd = ChurIncomeAddDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_ChurIncomeAdd);

        } else {
            
            List list = ChurIncomeAddDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }

    private static Logger m_logger = Logger.getLogger( ChurIncomeAddAjaxAction.class);
}
