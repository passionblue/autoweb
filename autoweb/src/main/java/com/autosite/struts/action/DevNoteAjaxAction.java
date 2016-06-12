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

import com.autosite.db.DevNote;
import com.autosite.ds.DevNoteDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.storable.SessionStorableDataHolderWrapper;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;
import com.jtrend.util.FileUtil;
import com.autosite.db.BaseAutositeDataObject;

import com.autosite.struts.form.DevNoteForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.DevNoteDataHolder;


public class DevNoteAjaxAction extends DevNoteAction {

    public DevNoteAjaxAction(){
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
        
        DevNote target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (isActionBasicCmd(request)){
//        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
//            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
//            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
//            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (DevNote) working.get("target");
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
            DevNote _DevNote = DevNoteDS.getInstance().getById(id);
            if (_DevNote == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _DevNote);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            DevNote _DevNote = DevNoteDS.getInstance().getById(id);
            if ( _DevNote == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _DevNote);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
		// Returns data in <div>
		// classes 
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ){
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

            buf.append("<div id=\"devNote-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                DevNote _DevNote = (DevNote) iterator.next();

                buf.append("<div id=\"devNote-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("noteType")) {
                    buf.append("<div id=\"devNote-ajax-noteType\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getNoteType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("completed")) {
                    buf.append("<div id=\"devNote-ajax-completed\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getCompleted()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("category")) {
                    buf.append("<div id=\"devNote-ajax-category\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getCategory()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("subject")) {
                    buf.append("<div id=\"devNote-ajax-subject\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getSubject()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("note")) {
                    buf.append("<div id=\"devNote-ajax-note\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getNote()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("tags")) {
                    buf.append("<div id=\"devNote-ajax-tags\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getTags()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"devNote-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"devNote-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_DevNote.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("noteType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("completed")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Completed");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("category")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Category");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("subject")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Subject");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("note")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Note");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("tags")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Tags");
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
                DevNote _DevNote = (DevNote) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("noteType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getNoteType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("completed")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getCompleted()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("category")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getCategory()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("subject")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getSubject()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("note")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getNote()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("tags")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getTags()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_DevNote.getTimeUpdated()));

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

            List fieldsTitle = DevNoteDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                DevNoteDataHolder _DevNote  = new DevNoteDataHolder( (DevNote) iterator.next());
                gen.addTableRow(_DevNote);
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
	                DevNote _DevNote = (DevNote) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("noteType")) 
			            json.put("noteType", ""+_DevNote.getNoteType());
		            if ( ignoreFieldSet || fieldSet.contains("completed")) 
			            json.put("completed", ""+_DevNote.getCompleted());
		            if ( ignoreFieldSet || fieldSet.contains("category")) 
			            json.put("category", ""+_DevNote.getCategory());
		            if ( ignoreFieldSet || fieldSet.contains("subject")) 
			            json.put("subject", ""+_DevNote.getSubject());
		            if ( ignoreFieldSet || fieldSet.contains("note")) 
			            json.put("note", ""+_DevNote.getNote());
		            if ( ignoreFieldSet || fieldSet.contains("tags")) 
			            json.put("tags", ""+_DevNote.getTags());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                DevNote _DevNote = list.size() >=1?(DevNote) list.get(0): null; 

				if ( _DevNote != null) {
		            top.put("id", ""+_DevNote.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonNoteType = new JSONObject();
		            jsonNoteType.put("name", "noteType");
		            jsonNoteType.put("value", ""+_DevNote.getNoteType());
		            array.put(jsonNoteType);
		            JSONObject jsonCompleted = new JSONObject();
		            jsonCompleted.put("name", "completed");
		            jsonCompleted.put("value", ""+_DevNote.getCompleted());
		            array.put(jsonCompleted);
		            JSONObject jsonCategory = new JSONObject();
		            jsonCategory.put("name", "category");
		            jsonCategory.put("value", ""+_DevNote.getCategory());
		            array.put(jsonCategory);
		            JSONObject jsonSubject = new JSONObject();
		            jsonSubject.put("name", "subject");
		            jsonSubject.put("value", ""+_DevNote.getSubject());
		            array.put(jsonSubject);
		            JSONObject jsonNote = new JSONObject();
		            jsonNote.put("name", "note");
		            jsonNote.put("value", ""+_DevNote.getNote());
		            array.put(jsonNote);
		            JSONObject jsonTags = new JSONObject();
		            jsonTags.put("name", "tags");
		            jsonTags.put("value", ""+_DevNote.getTags());
		            array.put(jsonTags);

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
            buf.append("sendFormAjax('/devNoteAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/devNoteAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("noteType")) {
                String value = WebUtil.display(request.getParameter("noteType"));

                if ( forceHiddenSet.contains("noteType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"noteType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Note Type</div>");
            buf.append("<INPUT NAME=\"noteType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("category")) {
                String value = WebUtil.display(request.getParameter("category"));

                if ( forceHiddenSet.contains("category")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"category\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Category</div>");
            buf.append("<INPUT NAME=\"category\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("subject")) {
                String value = WebUtil.display(request.getParameter("subject"));

                if ( forceHiddenSet.contains("subject")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"subject\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Subject</div>");
            buf.append("<INPUT NAME=\"subject\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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

            if ( ignoreFieldSet || fieldSet.contains("tags")) {
                String value = WebUtil.display(request.getParameter("tags"));

                if ( forceHiddenSet.contains("tags")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"tags\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Tags</div>");
            buf.append("<INPUT NAME=\"tags\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_devNote(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayDevNote\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_devNote(){\n";
            importedScripts +=     "xmlhttpPostXX('devNoteFormAddDis','/devNoteAction.html', 'resultDisplayDevNote', '${ajax_response_fields}', responseCallback_devNote);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_devNote(){\n";
            importedScripts +=     "clearFormXX('devNoteFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_devNote(){\n";
            importedScripts +=     "backToXX('devNoteFormAddDis','resultDisplayDevNote');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"devNoteFormAddDis\" method=\"POST\" action=\"/devNoteAction.html\" id=\"devNoteFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Note Type</div>");
        buf.append("<input class=\"field\" id=\"noteType\" type=\"text\" size=\"70\" name=\"noteType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Completed</div>");
        buf.append("<input class=\"field\" id=\"completed\" type=\"text\" size=\"70\" name=\"completed\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Category</div>");
        buf.append("<input class=\"field\" id=\"category\" type=\"text\" size=\"70\" name=\"category\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Subject</div>");
        buf.append("<input class=\"field\" id=\"subject\" type=\"text\" size=\"70\" name=\"subject\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Note</div>");
		buf.append("<TEXTAREA id=\"note\" class=\"field\" NAME=\"note\" COLS=\"50\" ROWS=\"8\" ></TEXTAREA><span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Tags</div>");
        buf.append("<input class=\"field\" id=\"tags\" type=\"text\" size=\"70\" name=\"tags\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_devNote()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_devNote()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayDevNote\"></span>");
			buf.append("<a href=\"javascript:showform_devNote()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, DevNote target){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        boolean returnList = request.getParameter("ajaxOut") != null && ( request.getParameter("ajaxOut").startsWith("getlist") ||  request.getParameter("ajaxOut").startsWith("getlisthtml")||  request.getParameter("ajaxOut").startsWith("getlistjson"));

        if (!returnList) {
            
            String arg = request.getParameter("ajaxOutArg");
            DevNote _DevNote = null; 
            List list = DevNoteDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _DevNote = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _DevNote = (DevNote) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _DevNote = (DevNote) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _DevNote = DevNoteDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_DevNote);

        } else {
            
            List list = DevNoteDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
        }
        
        return ret;
    }

    private static Logger m_logger = Logger.getLogger( DevNoteAjaxAction.class);
}
