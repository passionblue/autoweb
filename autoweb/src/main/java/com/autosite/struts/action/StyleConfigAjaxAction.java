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
import com.jtrend.util.JtStringUtil;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import com.jtrend.util.RequestUtil;
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

import com.autosite.db.StyleConfig;
import com.autosite.ds.StyleConfigDS;
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
import com.autosite.struts.form.StyleConfigForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.StyleConfigDataHolder;


public class StyleConfigAjaxAction extends StyleConfigAction {

    public StyleConfigAjaxAction(){
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
        
        StyleConfig target = null;
        
        // ================================================================================
        // Request Processing 
        // ================================================================================

        if (isActionCmd(request)){
            m_logger.debug("AjaxRequest contains ActionCommand. So will process it first");
            try {
                Map working = new HashMap();
                ex(mapping, form, request, response, true, working);
                target = (StyleConfig) working.get("target");
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
            StyleConfig _StyleConfig = StyleConfigDS.getInstance().getById(id);
            if (_StyleConfig == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _StyleConfig);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            StyleConfig _StyleConfig = StyleConfigDS.getInstance().getById(id);
            if ( _StyleConfig == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _StyleConfig);
                if (field != null)
                    ret.put("__value", field);
                else 
                    ret.put("__value", "");
            }
            
		// Returns data in <div>
		// classes 
        } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ||
                   hasRequestValue(request, "ajxr", "getdata") || hasRequestValue(request, "ajxr", "getlistdata")  ){
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

            buf.append("<div id=\"styleConfig-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                StyleConfig _StyleConfig = (StyleConfig) iterator.next();

                buf.append("<div id=\"styleConfig-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
                    buf.append("<div id=\"styleConfig-ajax-styleKey\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getStyleKey()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
                    buf.append("<div id=\"styleConfig-ajax-styleUse\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getStyleUse()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
                    buf.append("<div id=\"styleConfig-ajax-isGlobal\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIsGlobal()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
                    buf.append("<div id=\"styleConfig-ajax-idClass\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIdClass()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isId")) {
                    buf.append("<div id=\"styleConfig-ajax-isId\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIsId()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("color")) {
                    buf.append("<div id=\"styleConfig-ajax-color\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
                    buf.append("<div id=\"styleConfig-ajax-bgColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
                    buf.append("<div id=\"styleConfig-ajax-bgImage\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgImage()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
                    buf.append("<div id=\"styleConfig-ajax-bgRepeat\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgRepeat()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
                    buf.append("<div id=\"styleConfig-ajax-bgAttach\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgAttach()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
                    buf.append("<div id=\"styleConfig-ajax-bgPosition\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBgPosition()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
                    buf.append("<div id=\"styleConfig-ajax-textAlign\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTextAlign()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
                    buf.append("<div id=\"styleConfig-ajax-fontFamily\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontFamily()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
                    buf.append("<div id=\"styleConfig-ajax-fontSize\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontSize()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
                    buf.append("<div id=\"styleConfig-ajax-fontStyle\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontStyle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
                    buf.append("<div id=\"styleConfig-ajax-fontVariant\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontVariant()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
                    buf.append("<div id=\"styleConfig-ajax-fontWeight\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFontWeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
                    buf.append("<div id=\"styleConfig-ajax-borderDirection\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderDirection()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
                    buf.append("<div id=\"styleConfig-ajax-borderWidth\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
                    buf.append("<div id=\"styleConfig-ajax-borderStyle\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderStyle()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
                    buf.append("<div id=\"styleConfig-ajax-borderColor\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderColor()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("margin")) {
                    buf.append("<div id=\"styleConfig-ajax-margin\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getMargin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("padding")) {
                    buf.append("<div id=\"styleConfig-ajax-padding\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getPadding()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
                    buf.append("<div id=\"styleConfig-ajax-listStyleType\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getListStyleType()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
                    buf.append("<div id=\"styleConfig-ajax-listStylePosition\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getListStylePosition()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
                    buf.append("<div id=\"styleConfig-ajax-listStyleImage\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getListStyleImage()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("floating")) {
                    buf.append("<div id=\"styleConfig-ajax-floating\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getFloating()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
                    buf.append("<div id=\"styleConfig-ajax-extraStyleStr\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getExtraStyleStr()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
                    buf.append("<div id=\"styleConfig-ajax-itemStyleStr\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getItemStyleStr()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("link")) {
                    buf.append("<div id=\"styleConfig-ajax-link\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getLink()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
                    buf.append("<div id=\"styleConfig-ajax-linkHover\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getLinkHover()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
                    buf.append("<div id=\"styleConfig-ajax-linkActive\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getLinkActive()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("height")) {
                    buf.append("<div id=\"styleConfig-ajax-height\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getHeight()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("width")) {
                    buf.append("<div id=\"styleConfig-ajax-width\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getWidth()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
                    buf.append("<div id=\"styleConfig-ajax-isTable\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getIsTable()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
                    buf.append("<div id=\"styleConfig-ajax-borderCollapse\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderCollapse()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
                    buf.append("<div id=\"styleConfig-ajax-borderSpacing\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getBorderSpacing()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
                    buf.append("<div id=\"styleConfig-ajax-trStyleIds\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTrStyleIds()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
                    buf.append("<div id=\"styleConfig-ajax-tdStyleIds\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTdStyleIds()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"styleConfig-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"styleConfig-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_StyleConfig.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Style Key");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Style Use");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Global");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Id Class");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isId")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Id");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("color")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Image");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Repeat");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Attach");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Bg Position");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Text Align");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Family");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Size");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Style");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Variant");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Font Weight");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Direction");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Style");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Color");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("margin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Margin");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("padding")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Padding");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Style Type");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Style Position");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("List Style Image");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("floating")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Floating");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Extra Style Str");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Item Style Str");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("link")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Link");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Link Hover");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Link Active");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("height")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Height");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("width")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Width");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Is Table");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Collapse");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Border Spacing");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Tr Style Ids");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Td Style Ids");
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
                StyleConfig _StyleConfig = (StyleConfig) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getStyleKey()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getStyleUse()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIsGlobal()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIdClass()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isId")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIsId()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("color")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgImage()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgRepeat()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgAttach()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBgPosition()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTextAlign()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontFamily()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontSize()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontStyle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontVariant()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFontWeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderDirection()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderStyle()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderColor()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("margin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getMargin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("padding")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getPadding()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getListStyleType()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getListStylePosition()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getListStyleImage()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("floating")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getFloating()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getExtraStyleStr()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getItemStyleStr()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("link")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getLink()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getLinkHover()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getLinkActive()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("height")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getHeight()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("width")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getWidth()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getIsTable()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderCollapse()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getBorderSpacing()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTrStyleIds()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTdStyleIds()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_StyleConfig.getTimeUpdated()));

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

            List fieldsTitle = StyleConfigDataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                StyleConfigDataHolder _StyleConfig  = new StyleConfigDataHolder( (StyleConfig) iterator.next());
                gen.addTableRow(_StyleConfig);
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

	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                StyleConfig _StyleConfig = (StyleConfig) iterator.next();

		            JSONObject json = new JSONObject();

					// Fields
		            if ( ignoreFieldSet || fieldSet.contains("styleKey")) 
			            json.put("styleKey", ""+_StyleConfig.getStyleKey());
		            if ( ignoreFieldSet || fieldSet.contains("styleUse")) 
			            json.put("styleUse", ""+_StyleConfig.getStyleUse());
		            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) 
			            json.put("isGlobal", ""+_StyleConfig.getIsGlobal());
		            if ( ignoreFieldSet || fieldSet.contains("idClass")) 
			            json.put("idClass", ""+_StyleConfig.getIdClass());
		            if ( ignoreFieldSet || fieldSet.contains("isId")) 
			            json.put("isId", ""+_StyleConfig.getIsId());
		            if ( ignoreFieldSet || fieldSet.contains("color")) 
			            json.put("color", ""+_StyleConfig.getColor());
		            if ( ignoreFieldSet || fieldSet.contains("bgColor")) 
			            json.put("bgColor", ""+_StyleConfig.getBgColor());
		            if ( ignoreFieldSet || fieldSet.contains("bgImage")) 
			            json.put("bgImage", ""+_StyleConfig.getBgImage());
		            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) 
			            json.put("bgRepeat", ""+_StyleConfig.getBgRepeat());
		            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) 
			            json.put("bgAttach", ""+_StyleConfig.getBgAttach());
		            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) 
			            json.put("bgPosition", ""+_StyleConfig.getBgPosition());
		            if ( ignoreFieldSet || fieldSet.contains("textAlign")) 
			            json.put("textAlign", ""+_StyleConfig.getTextAlign());
		            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) 
			            json.put("fontFamily", ""+_StyleConfig.getFontFamily());
		            if ( ignoreFieldSet || fieldSet.contains("fontSize")) 
			            json.put("fontSize", ""+_StyleConfig.getFontSize());
		            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) 
			            json.put("fontStyle", ""+_StyleConfig.getFontStyle());
		            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) 
			            json.put("fontVariant", ""+_StyleConfig.getFontVariant());
		            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) 
			            json.put("fontWeight", ""+_StyleConfig.getFontWeight());
		            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) 
			            json.put("borderDirection", ""+_StyleConfig.getBorderDirection());
		            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) 
			            json.put("borderWidth", ""+_StyleConfig.getBorderWidth());
		            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) 
			            json.put("borderStyle", ""+_StyleConfig.getBorderStyle());
		            if ( ignoreFieldSet || fieldSet.contains("borderColor")) 
			            json.put("borderColor", ""+_StyleConfig.getBorderColor());
		            if ( ignoreFieldSet || fieldSet.contains("margin")) 
			            json.put("margin", ""+_StyleConfig.getMargin());
		            if ( ignoreFieldSet || fieldSet.contains("padding")) 
			            json.put("padding", ""+_StyleConfig.getPadding());
		            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) 
			            json.put("listStyleType", ""+_StyleConfig.getListStyleType());
		            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) 
			            json.put("listStylePosition", ""+_StyleConfig.getListStylePosition());
		            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) 
			            json.put("listStyleImage", ""+_StyleConfig.getListStyleImage());
		            if ( ignoreFieldSet || fieldSet.contains("floating")) 
			            json.put("floating", ""+_StyleConfig.getFloating());
		            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) 
			            json.put("extraStyleStr", ""+_StyleConfig.getExtraStyleStr());
		            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) 
			            json.put("itemStyleStr", ""+_StyleConfig.getItemStyleStr());
		            if ( ignoreFieldSet || fieldSet.contains("link")) 
			            json.put("link", ""+_StyleConfig.getLink());
		            if ( ignoreFieldSet || fieldSet.contains("linkHover")) 
			            json.put("linkHover", ""+_StyleConfig.getLinkHover());
		            if ( ignoreFieldSet || fieldSet.contains("linkActive")) 
			            json.put("linkActive", ""+_StyleConfig.getLinkActive());
		            if ( ignoreFieldSet || fieldSet.contains("height")) 
			            json.put("height", ""+_StyleConfig.getHeight());
		            if ( ignoreFieldSet || fieldSet.contains("width")) 
			            json.put("width", ""+_StyleConfig.getWidth());
		            if ( ignoreFieldSet || fieldSet.contains("isTable")) 
			            json.put("isTable", ""+_StyleConfig.getIsTable());
		            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) 
			            json.put("borderCollapse", ""+_StyleConfig.getBorderCollapse());
		            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) 
			            json.put("borderSpacing", ""+_StyleConfig.getBorderSpacing());
		            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) 
			            json.put("trStyleIds", ""+_StyleConfig.getTrStyleIds());
		            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) 
			            json.put("tdStyleIds", ""+_StyleConfig.getTdStyleIds());
		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                StyleConfig _StyleConfig = list.size() >=1?(StyleConfig) list.get(0): null; 

				if ( _StyleConfig != null) {
		            top.put("id", ""+_StyleConfig.getId());
		            JSONArray array = new JSONArray();

					// Fields
		            JSONObject jsonStyleKey = new JSONObject();
		            jsonStyleKey.put("name", "styleKey");
		            jsonStyleKey.put("value", ""+_StyleConfig.getStyleKey());
		            array.put(jsonStyleKey);
		            JSONObject jsonStyleUse = new JSONObject();
		            jsonStyleUse.put("name", "styleUse");
		            jsonStyleUse.put("value", ""+_StyleConfig.getStyleUse());
		            array.put(jsonStyleUse);
		            JSONObject jsonIsGlobal = new JSONObject();
		            jsonIsGlobal.put("name", "isGlobal");
		            jsonIsGlobal.put("value", ""+_StyleConfig.getIsGlobal());
		            array.put(jsonIsGlobal);
		            JSONObject jsonIdClass = new JSONObject();
		            jsonIdClass.put("name", "idClass");
		            jsonIdClass.put("value", ""+_StyleConfig.getIdClass());
		            array.put(jsonIdClass);
		            JSONObject jsonIsId = new JSONObject();
		            jsonIsId.put("name", "isId");
		            jsonIsId.put("value", ""+_StyleConfig.getIsId());
		            array.put(jsonIsId);
		            JSONObject jsonColor = new JSONObject();
		            jsonColor.put("name", "color");
		            jsonColor.put("value", ""+_StyleConfig.getColor());
		            array.put(jsonColor);
		            JSONObject jsonBgColor = new JSONObject();
		            jsonBgColor.put("name", "bgColor");
		            jsonBgColor.put("value", ""+_StyleConfig.getBgColor());
		            array.put(jsonBgColor);
		            JSONObject jsonBgImage = new JSONObject();
		            jsonBgImage.put("name", "bgImage");
		            jsonBgImage.put("value", ""+_StyleConfig.getBgImage());
		            array.put(jsonBgImage);
		            JSONObject jsonBgRepeat = new JSONObject();
		            jsonBgRepeat.put("name", "bgRepeat");
		            jsonBgRepeat.put("value", ""+_StyleConfig.getBgRepeat());
		            array.put(jsonBgRepeat);
		            JSONObject jsonBgAttach = new JSONObject();
		            jsonBgAttach.put("name", "bgAttach");
		            jsonBgAttach.put("value", ""+_StyleConfig.getBgAttach());
		            array.put(jsonBgAttach);
		            JSONObject jsonBgPosition = new JSONObject();
		            jsonBgPosition.put("name", "bgPosition");
		            jsonBgPosition.put("value", ""+_StyleConfig.getBgPosition());
		            array.put(jsonBgPosition);
		            JSONObject jsonTextAlign = new JSONObject();
		            jsonTextAlign.put("name", "textAlign");
		            jsonTextAlign.put("value", ""+_StyleConfig.getTextAlign());
		            array.put(jsonTextAlign);
		            JSONObject jsonFontFamily = new JSONObject();
		            jsonFontFamily.put("name", "fontFamily");
		            jsonFontFamily.put("value", ""+_StyleConfig.getFontFamily());
		            array.put(jsonFontFamily);
		            JSONObject jsonFontSize = new JSONObject();
		            jsonFontSize.put("name", "fontSize");
		            jsonFontSize.put("value", ""+_StyleConfig.getFontSize());
		            array.put(jsonFontSize);
		            JSONObject jsonFontStyle = new JSONObject();
		            jsonFontStyle.put("name", "fontStyle");
		            jsonFontStyle.put("value", ""+_StyleConfig.getFontStyle());
		            array.put(jsonFontStyle);
		            JSONObject jsonFontVariant = new JSONObject();
		            jsonFontVariant.put("name", "fontVariant");
		            jsonFontVariant.put("value", ""+_StyleConfig.getFontVariant());
		            array.put(jsonFontVariant);
		            JSONObject jsonFontWeight = new JSONObject();
		            jsonFontWeight.put("name", "fontWeight");
		            jsonFontWeight.put("value", ""+_StyleConfig.getFontWeight());
		            array.put(jsonFontWeight);
		            JSONObject jsonBorderDirection = new JSONObject();
		            jsonBorderDirection.put("name", "borderDirection");
		            jsonBorderDirection.put("value", ""+_StyleConfig.getBorderDirection());
		            array.put(jsonBorderDirection);
		            JSONObject jsonBorderWidth = new JSONObject();
		            jsonBorderWidth.put("name", "borderWidth");
		            jsonBorderWidth.put("value", ""+_StyleConfig.getBorderWidth());
		            array.put(jsonBorderWidth);
		            JSONObject jsonBorderStyle = new JSONObject();
		            jsonBorderStyle.put("name", "borderStyle");
		            jsonBorderStyle.put("value", ""+_StyleConfig.getBorderStyle());
		            array.put(jsonBorderStyle);
		            JSONObject jsonBorderColor = new JSONObject();
		            jsonBorderColor.put("name", "borderColor");
		            jsonBorderColor.put("value", ""+_StyleConfig.getBorderColor());
		            array.put(jsonBorderColor);
		            JSONObject jsonMargin = new JSONObject();
		            jsonMargin.put("name", "margin");
		            jsonMargin.put("value", ""+_StyleConfig.getMargin());
		            array.put(jsonMargin);
		            JSONObject jsonPadding = new JSONObject();
		            jsonPadding.put("name", "padding");
		            jsonPadding.put("value", ""+_StyleConfig.getPadding());
		            array.put(jsonPadding);
		            JSONObject jsonListStyleType = new JSONObject();
		            jsonListStyleType.put("name", "listStyleType");
		            jsonListStyleType.put("value", ""+_StyleConfig.getListStyleType());
		            array.put(jsonListStyleType);
		            JSONObject jsonListStylePosition = new JSONObject();
		            jsonListStylePosition.put("name", "listStylePosition");
		            jsonListStylePosition.put("value", ""+_StyleConfig.getListStylePosition());
		            array.put(jsonListStylePosition);
		            JSONObject jsonListStyleImage = new JSONObject();
		            jsonListStyleImage.put("name", "listStyleImage");
		            jsonListStyleImage.put("value", ""+_StyleConfig.getListStyleImage());
		            array.put(jsonListStyleImage);
		            JSONObject jsonFloating = new JSONObject();
		            jsonFloating.put("name", "floating");
		            jsonFloating.put("value", ""+_StyleConfig.getFloating());
		            array.put(jsonFloating);
		            JSONObject jsonExtraStyleStr = new JSONObject();
		            jsonExtraStyleStr.put("name", "extraStyleStr");
		            jsonExtraStyleStr.put("value", ""+_StyleConfig.getExtraStyleStr());
		            array.put(jsonExtraStyleStr);
		            JSONObject jsonItemStyleStr = new JSONObject();
		            jsonItemStyleStr.put("name", "itemStyleStr");
		            jsonItemStyleStr.put("value", ""+_StyleConfig.getItemStyleStr());
		            array.put(jsonItemStyleStr);
		            JSONObject jsonLink = new JSONObject();
		            jsonLink.put("name", "link");
		            jsonLink.put("value", ""+_StyleConfig.getLink());
		            array.put(jsonLink);
		            JSONObject jsonLinkHover = new JSONObject();
		            jsonLinkHover.put("name", "linkHover");
		            jsonLinkHover.put("value", ""+_StyleConfig.getLinkHover());
		            array.put(jsonLinkHover);
		            JSONObject jsonLinkActive = new JSONObject();
		            jsonLinkActive.put("name", "linkActive");
		            jsonLinkActive.put("value", ""+_StyleConfig.getLinkActive());
		            array.put(jsonLinkActive);
		            JSONObject jsonHeight = new JSONObject();
		            jsonHeight.put("name", "height");
		            jsonHeight.put("value", ""+_StyleConfig.getHeight());
		            array.put(jsonHeight);
		            JSONObject jsonWidth = new JSONObject();
		            jsonWidth.put("name", "width");
		            jsonWidth.put("value", ""+_StyleConfig.getWidth());
		            array.put(jsonWidth);
		            JSONObject jsonIsTable = new JSONObject();
		            jsonIsTable.put("name", "isTable");
		            jsonIsTable.put("value", ""+_StyleConfig.getIsTable());
		            array.put(jsonIsTable);
		            JSONObject jsonBorderCollapse = new JSONObject();
		            jsonBorderCollapse.put("name", "borderCollapse");
		            jsonBorderCollapse.put("value", ""+_StyleConfig.getBorderCollapse());
		            array.put(jsonBorderCollapse);
		            JSONObject jsonBorderSpacing = new JSONObject();
		            jsonBorderSpacing.put("name", "borderSpacing");
		            jsonBorderSpacing.put("value", ""+_StyleConfig.getBorderSpacing());
		            array.put(jsonBorderSpacing);
		            JSONObject jsonTrStyleIds = new JSONObject();
		            jsonTrStyleIds.put("name", "trStyleIds");
		            jsonTrStyleIds.put("value", ""+_StyleConfig.getTrStyleIds());
		            array.put(jsonTrStyleIds);
		            JSONObject jsonTdStyleIds = new JSONObject();
		            jsonTdStyleIds.put("name", "tdStyleIds");
		            jsonTdStyleIds.put("value", ""+_StyleConfig.getTdStyleIds());
		            array.put(jsonTdStyleIds);

	    	        top.put("fields", array);
				}
			}

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
            buf.append("sendFormAjax('/styleConfigAction.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/styleConfigAction.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("styleKey")) {
                String value = WebUtil.display(request.getParameter("styleKey"));

                if ( forceHiddenSet.contains("styleKey")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"styleKey\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Style Key</div>");
            buf.append("<INPUT NAME=\"styleKey\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("styleUse")) {
                String value = WebUtil.display(request.getParameter("styleUse"));

                if ( forceHiddenSet.contains("styleUse")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"styleUse\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Style Use</div>");
            buf.append("<select id=\"requiredField\" name=\"styleUse\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

        	buf.append("<option value=\"0\" >Default</option>");
        	buf.append("<option value=\"1\" >Custom</option>");
        	buf.append("<option value=\"2\" >TBD</option>");
        	buf.append("<option value=\"3\" >TBD</option>");
        	buf.append("<option value=\"4\" >TBD</option>");

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isGlobal")) {
                String value = WebUtil.display(request.getParameter("isGlobal"));

                if ( forceHiddenSet.contains("isGlobal")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isGlobal\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Global</div>");
            buf.append("<INPUT NAME=\"isGlobal\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("idClass")) {
                String value = WebUtil.display(request.getParameter("idClass"));

                if ( forceHiddenSet.contains("idClass")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"idClass\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Id Class</div>");
            buf.append("<INPUT NAME=\"idClass\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isId")) {
                String value = WebUtil.display(request.getParameter("isId"));

                if ( forceHiddenSet.contains("isId")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isId\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Id</div>");
            buf.append("<INPUT NAME=\"isId\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("color")) {
                String value = WebUtil.display(request.getParameter("color"));

                if ( forceHiddenSet.contains("color")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"color\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Color</div>");
            buf.append("<INPUT NAME=\"color\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgColor")) {
                String value = WebUtil.display(request.getParameter("bgColor"));

                if ( forceHiddenSet.contains("bgColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Color</div>");
            buf.append("<INPUT NAME=\"bgColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgImage")) {
                String value = WebUtil.display(request.getParameter("bgImage"));

                if ( forceHiddenSet.contains("bgImage")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgImage\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Image</div>");
            buf.append("<INPUT NAME=\"bgImage\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgRepeat")) {
                String value = WebUtil.display(request.getParameter("bgRepeat"));

                if ( forceHiddenSet.contains("bgRepeat")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgRepeat\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Repeat</div>");
            buf.append("<INPUT NAME=\"bgRepeat\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgAttach")) {
                String value = WebUtil.display(request.getParameter("bgAttach"));

                if ( forceHiddenSet.contains("bgAttach")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgAttach\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Attach</div>");
            buf.append("<INPUT NAME=\"bgAttach\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("bgPosition")) {
                String value = WebUtil.display(request.getParameter("bgPosition"));

                if ( forceHiddenSet.contains("bgPosition")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"bgPosition\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Bg Position</div>");
            buf.append("<INPUT NAME=\"bgPosition\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("textAlign")) {
                String value = WebUtil.display(request.getParameter("textAlign"));

                if ( forceHiddenSet.contains("textAlign")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"textAlign\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Text Align</div>");
            buf.append("<INPUT NAME=\"textAlign\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontFamily")) {
                String value = WebUtil.display(request.getParameter("fontFamily"));

                if ( forceHiddenSet.contains("fontFamily")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontFamily\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Family</div>");
            buf.append("<INPUT NAME=\"fontFamily\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontSize")) {
                String value = WebUtil.display(request.getParameter("fontSize"));

                if ( forceHiddenSet.contains("fontSize")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontSize\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Size</div>");
            buf.append("<INPUT NAME=\"fontSize\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontStyle")) {
                String value = WebUtil.display(request.getParameter("fontStyle"));

                if ( forceHiddenSet.contains("fontStyle")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontStyle\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Style</div>");
            buf.append("<INPUT NAME=\"fontStyle\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontVariant")) {
                String value = WebUtil.display(request.getParameter("fontVariant"));

                if ( forceHiddenSet.contains("fontVariant")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontVariant\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Variant</div>");
            buf.append("<INPUT NAME=\"fontVariant\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("fontWeight")) {
                String value = WebUtil.display(request.getParameter("fontWeight"));

                if ( forceHiddenSet.contains("fontWeight")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fontWeight\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Font Weight</div>");
            buf.append("<INPUT NAME=\"fontWeight\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderDirection")) {
                String value = WebUtil.display(request.getParameter("borderDirection"));

                if ( forceHiddenSet.contains("borderDirection")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderDirection\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Direction</div>");
            buf.append("<INPUT NAME=\"borderDirection\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderWidth")) {
                String value = WebUtil.display(request.getParameter("borderWidth"));

                if ( forceHiddenSet.contains("borderWidth")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderWidth\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Width</div>");
            buf.append("<INPUT NAME=\"borderWidth\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderStyle")) {
                String value = WebUtil.display(request.getParameter("borderStyle"));

                if ( forceHiddenSet.contains("borderStyle")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderStyle\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Style</div>");
            buf.append("<INPUT NAME=\"borderStyle\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderColor")) {
                String value = WebUtil.display(request.getParameter("borderColor"));

                if ( forceHiddenSet.contains("borderColor")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderColor\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Color</div>");
            buf.append("<INPUT NAME=\"borderColor\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("margin")) {
                String value = WebUtil.display(request.getParameter("margin"));

                if ( forceHiddenSet.contains("margin")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"margin\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Margin</div>");
            buf.append("<INPUT NAME=\"margin\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("padding")) {
                String value = WebUtil.display(request.getParameter("padding"));

                if ( forceHiddenSet.contains("padding")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"padding\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Padding</div>");
            buf.append("<INPUT NAME=\"padding\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listStyleType")) {
                String value = WebUtil.display(request.getParameter("listStyleType"));

                if ( forceHiddenSet.contains("listStyleType")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listStyleType\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Style Type</div>");
            buf.append("<INPUT NAME=\"listStyleType\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listStylePosition")) {
                String value = WebUtil.display(request.getParameter("listStylePosition"));

                if ( forceHiddenSet.contains("listStylePosition")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listStylePosition\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Style Position</div>");
            buf.append("<INPUT NAME=\"listStylePosition\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("listStyleImage")) {
                String value = WebUtil.display(request.getParameter("listStyleImage"));

                if ( forceHiddenSet.contains("listStyleImage")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"listStyleImage\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">List Style Image</div>");
            buf.append("<INPUT NAME=\"listStyleImage\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("floating")) {
                String value = WebUtil.display(request.getParameter("floating"));

                if ( forceHiddenSet.contains("floating")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"floating\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Floating</div>");
            buf.append("<INPUT NAME=\"floating\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("extraStyleStr")) {
                String value = WebUtil.display(request.getParameter("extraStyleStr"));

                if ( forceHiddenSet.contains("extraStyleStr")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"extraStyleStr\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Extra Style Str</div>");
            buf.append("<INPUT NAME=\"extraStyleStr\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("itemStyleStr")) {
                String value = WebUtil.display(request.getParameter("itemStyleStr"));

                if ( forceHiddenSet.contains("itemStyleStr")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"itemStyleStr\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Item Style Str</div>");
            buf.append("<INPUT NAME=\"itemStyleStr\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("link")) {
                String value = WebUtil.display(request.getParameter("link"));

                if ( forceHiddenSet.contains("link")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"link\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Link</div>");
            buf.append("<INPUT NAME=\"link\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("linkHover")) {
                String value = WebUtil.display(request.getParameter("linkHover"));

                if ( forceHiddenSet.contains("linkHover")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"linkHover\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Link Hover</div>");
            buf.append("<INPUT NAME=\"linkHover\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("linkActive")) {
                String value = WebUtil.display(request.getParameter("linkActive"));

                if ( forceHiddenSet.contains("linkActive")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"linkActive\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Link Active</div>");
            buf.append("<INPUT NAME=\"linkActive\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("height")) {
                String value = WebUtil.display(request.getParameter("height"));

                if ( forceHiddenSet.contains("height")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"height\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Height</div>");
            buf.append("<INPUT NAME=\"height\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("width")) {
                String value = WebUtil.display(request.getParameter("width"));

                if ( forceHiddenSet.contains("width")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"width\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Width</div>");
            buf.append("<INPUT NAME=\"width\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("isTable")) {
                String value = WebUtil.display(request.getParameter("isTable"));

                if ( forceHiddenSet.contains("isTable")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"isTable\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Is Table</div>");
            buf.append("<INPUT NAME=\"isTable\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderCollapse")) {
                String value = WebUtil.display(request.getParameter("borderCollapse"));

                if ( forceHiddenSet.contains("borderCollapse")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderCollapse\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Collapse</div>");
            buf.append("<INPUT NAME=\"borderCollapse\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("borderSpacing")) {
                String value = WebUtil.display(request.getParameter("borderSpacing"));

                if ( forceHiddenSet.contains("borderSpacing")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"borderSpacing\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Border Spacing</div>");
            buf.append("<INPUT NAME=\"borderSpacing\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("trStyleIds")) {
                String value = WebUtil.display(request.getParameter("trStyleIds"));

                if ( forceHiddenSet.contains("trStyleIds")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"trStyleIds\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Tr Style Ids</div>");
            buf.append("<INPUT NAME=\"trStyleIds\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("tdStyleIds")) {
                String value = WebUtil.display(request.getParameter("tdStyleIds"));

                if ( forceHiddenSet.contains("tdStyleIds")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"tdStyleIds\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Td Style Ids</div>");
            buf.append("<INPUT NAME=\"tdStyleIds\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_styleConfig(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplayStyleConfig\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_styleConfig(){\n";
            importedScripts +=     "xmlhttpPostXX('styleConfigFormAddDis','/styleConfigAction.html', 'resultDisplayStyleConfig', '${ajax_response_fields}', responseCallback_styleConfig);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_styleConfig(){\n";
            importedScripts +=     "clearFormXX('styleConfigFormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_styleConfig(){\n";
            importedScripts +=     "backToXX('styleConfigFormAddDis','resultDisplayStyleConfig');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"styleConfigFormAddDis\" method=\"POST\" action=\"/styleConfigAction.html\" id=\"styleConfigFormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Style Key</div>");
        buf.append("<input class=\"field\" id=\"styleKey\" type=\"text\" size=\"70\" name=\"styleKey\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Style Use</div>");
        buf.append("<select class=\"field\" name=\"styleUse\" id=\"styleUse\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
        buf.append("<!--option value=\"XX\" >XX</option-->");
        buf.append("<option value=\"0\" >Default</option>");
        buf.append("<option value=\"1\" >Custom</option>");
        buf.append("<option value=\"2\" >TBD</option>");
        buf.append("<option value=\"3\" >TBD</option>");
        buf.append("<option value=\"4\" >TBD</option>");
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Global</div>");
        buf.append("<input class=\"field\" id=\"isGlobal\" type=\"text\" size=\"70\" name=\"isGlobal\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Id Class</div>");
        buf.append("<input class=\"field\" id=\"idClass\" type=\"text\" size=\"70\" name=\"idClass\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Id</div>");
        buf.append("<input class=\"field\" id=\"isId\" type=\"text\" size=\"70\" name=\"isId\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Color</div>");
        buf.append("<input class=\"field\" id=\"color\" type=\"text\" size=\"70\" name=\"color\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Color</div>");
        buf.append("<input class=\"field\" id=\"bgColor\" type=\"text\" size=\"70\" name=\"bgColor\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Image</div>");
        buf.append("<input class=\"field\" id=\"bgImage\" type=\"text\" size=\"70\" name=\"bgImage\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Repeat</div>");
        buf.append("<input class=\"field\" id=\"bgRepeat\" type=\"text\" size=\"70\" name=\"bgRepeat\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Attach</div>");
        buf.append("<input class=\"field\" id=\"bgAttach\" type=\"text\" size=\"70\" name=\"bgAttach\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Bg Position</div>");
        buf.append("<input class=\"field\" id=\"bgPosition\" type=\"text\" size=\"70\" name=\"bgPosition\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Text Align</div>");
        buf.append("<input class=\"field\" id=\"textAlign\" type=\"text\" size=\"70\" name=\"textAlign\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Family</div>");
        buf.append("<input class=\"field\" id=\"fontFamily\" type=\"text\" size=\"70\" name=\"fontFamily\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Size</div>");
        buf.append("<input class=\"field\" id=\"fontSize\" type=\"text\" size=\"70\" name=\"fontSize\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Style</div>");
        buf.append("<input class=\"field\" id=\"fontStyle\" type=\"text\" size=\"70\" name=\"fontStyle\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Variant</div>");
        buf.append("<input class=\"field\" id=\"fontVariant\" type=\"text\" size=\"70\" name=\"fontVariant\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Font Weight</div>");
        buf.append("<input class=\"field\" id=\"fontWeight\" type=\"text\" size=\"70\" name=\"fontWeight\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Direction</div>");
        buf.append("<input class=\"field\" id=\"borderDirection\" type=\"text\" size=\"70\" name=\"borderDirection\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Width</div>");
        buf.append("<input class=\"field\" id=\"borderWidth\" type=\"text\" size=\"70\" name=\"borderWidth\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Style</div>");
        buf.append("<input class=\"field\" id=\"borderStyle\" type=\"text\" size=\"70\" name=\"borderStyle\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Color</div>");
        buf.append("<input class=\"field\" id=\"borderColor\" type=\"text\" size=\"70\" name=\"borderColor\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Margin</div>");
        buf.append("<input class=\"field\" id=\"margin\" type=\"text\" size=\"70\" name=\"margin\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Padding</div>");
        buf.append("<input class=\"field\" id=\"padding\" type=\"text\" size=\"70\" name=\"padding\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Style Type</div>");
        buf.append("<input class=\"field\" id=\"listStyleType\" type=\"text\" size=\"70\" name=\"listStyleType\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Style Position</div>");
        buf.append("<input class=\"field\" id=\"listStylePosition\" type=\"text\" size=\"70\" name=\"listStylePosition\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> List Style Image</div>");
        buf.append("<input class=\"field\" id=\"listStyleImage\" type=\"text\" size=\"70\" name=\"listStyleImage\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Floating</div>");
        buf.append("<input class=\"field\" id=\"floating\" type=\"text\" size=\"70\" name=\"floating\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Extra Style Str</div>");
        buf.append("<input class=\"field\" id=\"extraStyleStr\" type=\"text\" size=\"70\" name=\"extraStyleStr\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Item Style Str</div>");
        buf.append("<input class=\"field\" id=\"itemStyleStr\" type=\"text\" size=\"70\" name=\"itemStyleStr\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Link</div>");
        buf.append("<input class=\"field\" id=\"link\" type=\"text\" size=\"70\" name=\"link\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Link Hover</div>");
        buf.append("<input class=\"field\" id=\"linkHover\" type=\"text\" size=\"70\" name=\"linkHover\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Link Active</div>");
        buf.append("<input class=\"field\" id=\"linkActive\" type=\"text\" size=\"70\" name=\"linkActive\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Height</div>");
        buf.append("<input class=\"field\" id=\"height\" type=\"text\" size=\"70\" name=\"height\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Width</div>");
        buf.append("<input class=\"field\" id=\"width\" type=\"text\" size=\"70\" name=\"width\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Is Table</div>");
        buf.append("<input class=\"field\" id=\"isTable\" type=\"text\" size=\"70\" name=\"isTable\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Collapse</div>");
        buf.append("<input class=\"field\" id=\"borderCollapse\" type=\"text\" size=\"70\" name=\"borderCollapse\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Border Spacing</div>");
        buf.append("<input class=\"field\" id=\"borderSpacing\" type=\"text\" size=\"70\" name=\"borderSpacing\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Tr Style Ids</div>");
        buf.append("<input class=\"field\" id=\"trStyleIds\" type=\"text\" size=\"70\" name=\"trStyleIds\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Td Style Ids</div>");
        buf.append("<input class=\"field\" id=\"tdStyleIds\" type=\"text\" size=\"70\" name=\"tdStyleIds\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_styleConfig()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_styleConfig()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplayStyleConfig\"></span>");
			buf.append("<a href=\"javascript:showform_styleConfig()\">Show form</a><br>");
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
    protected List prepareReturnData(HttpServletRequest request, StyleConfig target){

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

    protected List prepareReturnData(HttpServletRequest request, StyleConfig target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = StyleConfigDS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            StyleConfig _StyleConfig = null; 
            List list = StyleConfigDS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _StyleConfig = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _StyleConfig = (StyleConfig) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _StyleConfig = (StyleConfig) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _StyleConfig = StyleConfigDS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_StyleConfig);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        super.isActionBasicCmd(request); 
    }

    private static Logger m_logger = Logger.getLogger( StyleConfigAjaxAction.class);
}
