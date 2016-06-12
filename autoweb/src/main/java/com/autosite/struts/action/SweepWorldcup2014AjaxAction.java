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

import com.autosite.db.SweepWorldcup2014;
import com.autosite.ds.SweepWorldcup2014DS;
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
import com.autosite.struts.form.SweepWorldcup2014Form;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.web.DropMenuItem;
import com.autosite.util.web.DropMenuUtil;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;
import com.autosite.util.RandomUtil;
// imported for Dependency Check


import com.autosite.holder.SweepWorldcup2014DataHolder;


public class SweepWorldcup2014AjaxAction extends SweepWorldcup2014Action {

    private static Logger m_logger = Logger.getLogger( SweepWorldcup2014AjaxAction.class);

    public SweepWorldcup2014AjaxAction(){
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
        
        SweepWorldcup2014 target = null;
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
                target = (SweepWorldcup2014) working.get("target");

            } catch( ActionExtentDuplicateAttemptException e) {
                
                if (isSynchRequired()){
	                String tokenFromRemote = request.getParameter("_otok");
	                Site site = findSessionBoundSite(request);
	                synchLedger = AutositeSynchLedgerDS.getInstance().getBySiteIdToRemoteToken(site.getId(), tokenFromRemote);
	                if ( synchLedger != null) {
	                    target = SweepWorldcup2014DS.getInstance().getById(synchLedger.getObjectId());
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
            SweepWorldcup2014 _SweepWorldcup2014 = SweepWorldcup2014DS.getInstance().getById(id);
            if (_SweepWorldcup2014 == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _SweepWorldcup2014);
                if (field != null)
                    ret.put("__value", field);
            }

        } else if (hasRequestValue(request, "ajaxOut", "getfieldbyname") || hasRequestValue(request, "ajxr", "getfieldbyname")){
            m_logger.debug("Ajax Processing getfieldbyname fieldname = " + request.getParameter("fieldname"));
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            SweepWorldcup2014 _SweepWorldcup2014 = SweepWorldcup2014DS.getInstance().getById(id);
            if ( _SweepWorldcup2014 == null) {
	            ret.put("__value", "");
            } else {
                String fieldName = request.getParameter("fieldname");
                String field = getFieldByName(fieldName, _SweepWorldcup2014);
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

            buf.append("<div id=\"sweepWorldcup2014-ajax-frame\" "+frameClass+">");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) iterator.next();

                buf.append("<div id=\"sweepWorldcup2014-ajax-item\" "+listClass+">");

	            if ( ignoreFieldSet || fieldSet.contains("player")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-player\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getPlayer()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game1Score")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game1Score\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame1Score()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game1ScoreOpp")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game1ScoreOpp\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame1ScoreOpp()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game2Score")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game2Score\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame2Score()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game2ScoreOpp")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game2ScoreOpp\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame2ScoreOpp()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game3")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game3\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame3()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game3Score")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game3Score\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame3Score()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("game3ScoreOpp")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-game3ScoreOpp\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getGame3ScoreOpp()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("advance")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-advance\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getAdvance()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16A1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16A1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16A1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16A2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16A2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16A2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16B1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16B1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16B1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16B2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16B2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16B2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16C1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16C1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16C1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16C2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16C2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16C2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16D1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16D1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16D1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16D2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16D2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16D2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16E1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16E1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16E1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16E2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16E2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16E2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16F1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16F1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16F1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16F2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16F2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16F2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16G1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16G1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16G1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16G2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16G2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16G2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16H1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16H1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16H1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("team16H2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-team16H2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16H2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal3")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal3\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal3()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal4")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal4\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal4()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal5")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal5\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal5()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal6")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal6\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal6()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal7")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal7\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal7()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal8")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-quarterFinal8\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal8()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("semiFinal1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-semiFinal1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("semiFinal2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-semiFinal2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("semiFinal3")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-semiFinal3\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal3()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("semiFinal4")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-semiFinal4\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal4()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("final1")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-final1\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getFinal1()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("final2")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-final2\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getFinal2()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("champion")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-champion\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getChampion()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("finalScoreWin")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-finalScoreWin\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getFinalScoreWin()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("finalScoreLose")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-finalScoreLose\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getFinalScoreLose()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-timeCreated\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTimeCreated()));
                    buf.append("</div>");

				}
	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
                    buf.append("<div id=\"sweepWorldcup2014-ajax-timeUpdated\" "+itemClass+">");
                    buf.append(WebUtil.display(_SweepWorldcup2014.getTimeUpdated()));
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
            if ( ignoreFieldSet || fieldSet.contains("player")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Player");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game1Score")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 1 Score");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game1ScoreOpp")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 1 Score Opp");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game2Score")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 2 Score");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game2ScoreOpp")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 2 Score Opp");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game3")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 3");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game3Score")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 3 Score");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("game3ScoreOpp")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Game 3 Score Opp");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("advance")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Advance");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16A1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 A1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16A2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 A2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16B1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 B1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16B2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 B2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16C1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 C1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16C2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 C2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16D1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 D1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16D2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 D2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16E1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 E1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16E2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 E2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16F1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 F1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16F2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 F2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16G1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 G1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16G2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 G2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16H1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 H1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("team16H2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Team16 H2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal3")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 3");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal4")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 4");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal5")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 5");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal6")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 6");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal7")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 7");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal8")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Quarter Final 8");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("semiFinal1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Semi Final 1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("semiFinal2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Semi Final 2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("semiFinal3")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Semi Final 3");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("semiFinal4")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Semi Final 4");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("final1")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Final 1");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("final2")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Final 2");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("champion")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Champion");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("finalScoreWin")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Final Score Win");
	            buf.append("</td>");
			}
            if ( ignoreFieldSet || fieldSet.contains("finalScoreLose")) {
	            buf.append("<td "+ tdBoldStyleStr +" >");
	            buf.append("Final Score Lose");
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
                SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) iterator.next();

            	buf.append("<tr "+ trStyleStr +" >");


	            if ( ignoreFieldSet || fieldSet.contains("player")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getPlayer()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game1Score")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame1Score()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game1ScoreOpp")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame1ScoreOpp()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game2Score")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame2Score()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game2ScoreOpp")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame2ScoreOpp()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game3")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame3()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game3Score")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame3Score()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("game3ScoreOpp")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getGame3ScoreOpp()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("advance")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getAdvance()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16A1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16A1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16A2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16A2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16B1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16B1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16B2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16B2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16C1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16C1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16C2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16C2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16D1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16D1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16D2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16D2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16E1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16E1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16E2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16E2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16F1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16F1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16F2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16F2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16G1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16G1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16G2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16G2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16H1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16H1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("team16H2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTeam16H2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal3")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal3()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal4")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal4()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal5")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal5()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal6")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal6()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal7")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal7()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("quarterFinal8")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getQuarterFinal8()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("semiFinal1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("semiFinal2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("semiFinal3")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal3()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("semiFinal4")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getSemiFinal4()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("final1")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getFinal1()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("final2")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getFinal2()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("champion")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getChampion()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("finalScoreWin")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getFinalScoreWin()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("finalScoreLose")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getFinalScoreLose()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTimeCreated()));

		            buf.append("</td>");
				}

	            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) {
		            buf.append("<td "+ tdStyleStr +" >");
		            buf.append(WebUtil.display(_SweepWorldcup2014.getTimeUpdated()));

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

            List fieldsTitle = SweepWorldcup2014DataHolder.getFieldsName();

            gen.addTableRow(fieldsTitle, true);

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                SweepWorldcup2014DataHolder _SweepWorldcup2014  = new SweepWorldcup2014DataHolder( (SweepWorldcup2014) iterator.next());
                gen.addTableRow(_SweepWorldcup2014);
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
            buf.append("sendFormAjax('/sweepWorldcup2014Action.html','ajaxSubmitForm"+randNum+"', 'ajaxSubmit"+randNum+"', 'ajaxSubmitResult"+randNum+"')");
            buf.append("}");
            //buf.append("// -->");
            buf.append("</script>");

            buf.append("<form name=\"ajaxSubmitForm"+randNum+"\" method=\"POST\" action=\"/sweepWorldcup2014Action.html\" id=\"ajaxSubmitForm"+randNum+"\" >");


            if ( ignoreFieldSet || fieldSet.contains("player")) {
                String value = WebUtil.display(request.getParameter("player"));

                if ( forceHiddenSet.contains("player")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"player\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Player</div>");
            buf.append("<INPUT NAME=\"player\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game1")) {
                String value = WebUtil.display(request.getParameter("game1"));

                if ( forceHiddenSet.contains("game1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 1</div>");
            buf.append("<select id=\"requiredField\" name=\"game1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Game 1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game1Score")) {
                String value = WebUtil.display(request.getParameter("game1Score"));

                if ( forceHiddenSet.contains("game1Score")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game1Score\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 1 Score</div>");
            buf.append("<INPUT NAME=\"game1Score\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game1ScoreOpp")) {
                String value = WebUtil.display(request.getParameter("game1ScoreOpp"));

                if ( forceHiddenSet.contains("game1ScoreOpp")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game1ScoreOpp\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 1 Score Opp</div>");
            buf.append("<INPUT NAME=\"game1ScoreOpp\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game2")) {
                String value = WebUtil.display(request.getParameter("game2"));

                if ( forceHiddenSet.contains("game2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 2</div>");
            buf.append("<select id=\"requiredField\" name=\"game2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Game 2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game2Score")) {
                String value = WebUtil.display(request.getParameter("game2Score"));

                if ( forceHiddenSet.contains("game2Score")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game2Score\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 2 Score</div>");
            buf.append("<INPUT NAME=\"game2Score\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game2ScoreOpp")) {
                String value = WebUtil.display(request.getParameter("game2ScoreOpp"));

                if ( forceHiddenSet.contains("game2ScoreOpp")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game2ScoreOpp\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 2 Score Opp</div>");
            buf.append("<INPUT NAME=\"game2ScoreOpp\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game3")) {
                String value = WebUtil.display(request.getParameter("game3"));

                if ( forceHiddenSet.contains("game3")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game3\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 3</div>");
            buf.append("<select id=\"requiredField\" name=\"game3\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Game 3Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game3Score")) {
                String value = WebUtil.display(request.getParameter("game3Score"));

                if ( forceHiddenSet.contains("game3Score")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game3Score\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 3 Score</div>");
            buf.append("<INPUT NAME=\"game3Score\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("game3ScoreOpp")) {
                String value = WebUtil.display(request.getParameter("game3ScoreOpp"));

                if ( forceHiddenSet.contains("game3ScoreOpp")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"game3ScoreOpp\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Game 3 Score Opp</div>");
            buf.append("<INPUT NAME=\"game3ScoreOpp\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("advance")) {
                String value = WebUtil.display(request.getParameter("advance"));

                if ( forceHiddenSet.contains("advance")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"advance\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Advance</div>");
            buf.append("<INPUT NAME=\"advance\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16A1")) {
                String value = WebUtil.display(request.getParameter("team16A1"));

                if ( forceHiddenSet.contains("team16A1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16A1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 A1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16A1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 A1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16A2")) {
                String value = WebUtil.display(request.getParameter("team16A2"));

                if ( forceHiddenSet.contains("team16A2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16A2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 A2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16A2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 A2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16B1")) {
                String value = WebUtil.display(request.getParameter("team16B1"));

                if ( forceHiddenSet.contains("team16B1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16B1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 B1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16B1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 B1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16B2")) {
                String value = WebUtil.display(request.getParameter("team16B2"));

                if ( forceHiddenSet.contains("team16B2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16B2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 B2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16B2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 B2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16C1")) {
                String value = WebUtil.display(request.getParameter("team16C1"));

                if ( forceHiddenSet.contains("team16C1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16C1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 C1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16C1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 C1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16C2")) {
                String value = WebUtil.display(request.getParameter("team16C2"));

                if ( forceHiddenSet.contains("team16C2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16C2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 C2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16C2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 C2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16D1")) {
                String value = WebUtil.display(request.getParameter("team16D1"));

                if ( forceHiddenSet.contains("team16D1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16D1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 D1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16D1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 D1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16D2")) {
                String value = WebUtil.display(request.getParameter("team16D2"));

                if ( forceHiddenSet.contains("team16D2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16D2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 D2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16D2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 D2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16E1")) {
                String value = WebUtil.display(request.getParameter("team16E1"));

                if ( forceHiddenSet.contains("team16E1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16E1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 E1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16E1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 E1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16E2")) {
                String value = WebUtil.display(request.getParameter("team16E2"));

                if ( forceHiddenSet.contains("team16E2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16E2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 E2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16E2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 E2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16F1")) {
                String value = WebUtil.display(request.getParameter("team16F1"));

                if ( forceHiddenSet.contains("team16F1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16F1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 F1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16F1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 F1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16F2")) {
                String value = WebUtil.display(request.getParameter("team16F2"));

                if ( forceHiddenSet.contains("team16F2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16F2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 F2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16F2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 F2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16G1")) {
                String value = WebUtil.display(request.getParameter("team16G1"));

                if ( forceHiddenSet.contains("team16G1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16G1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 G1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16G1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 G1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16G2")) {
                String value = WebUtil.display(request.getParameter("team16G2"));

                if ( forceHiddenSet.contains("team16G2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16G2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 G2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16G2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 G2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16H1")) {
                String value = WebUtil.display(request.getParameter("team16H1"));

                if ( forceHiddenSet.contains("team16H1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16H1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 H1</div>");
            buf.append("<select id=\"requiredField\" name=\"team16H1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 H1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("team16H2")) {
                String value = WebUtil.display(request.getParameter("team16H2"));

                if ( forceHiddenSet.contains("team16H2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"team16H2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Team16 H2</div>");
            buf.append("<select id=\"requiredField\" name=\"team16H2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Team16 H2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal1")) {
                String value = WebUtil.display(request.getParameter("quarterFinal1"));

                if ( forceHiddenSet.contains("quarterFinal1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 1</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal2")) {
                String value = WebUtil.display(request.getParameter("quarterFinal2"));

                if ( forceHiddenSet.contains("quarterFinal2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 2</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal3")) {
                String value = WebUtil.display(request.getParameter("quarterFinal3"));

                if ( forceHiddenSet.contains("quarterFinal3")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal3\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 3</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal3\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 3Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal4")) {
                String value = WebUtil.display(request.getParameter("quarterFinal4"));

                if ( forceHiddenSet.contains("quarterFinal4")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal4\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 4</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal4\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 4Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal5")) {
                String value = WebUtil.display(request.getParameter("quarterFinal5"));

                if ( forceHiddenSet.contains("quarterFinal5")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal5\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 5</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal5\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 5Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal6")) {
                String value = WebUtil.display(request.getParameter("quarterFinal6"));

                if ( forceHiddenSet.contains("quarterFinal6")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal6\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 6</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal6\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 6Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal7")) {
                String value = WebUtil.display(request.getParameter("quarterFinal7"));

                if ( forceHiddenSet.contains("quarterFinal7")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal7\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 7</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal7\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 7Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("quarterFinal8")) {
                String value = WebUtil.display(request.getParameter("quarterFinal8"));

                if ( forceHiddenSet.contains("quarterFinal8")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"quarterFinal8\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Quarter Final 8</div>");
            buf.append("<select id=\"requiredField\" name=\"quarterFinal8\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Quarter Final 8Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("semiFinal1")) {
                String value = WebUtil.display(request.getParameter("semiFinal1"));

                if ( forceHiddenSet.contains("semiFinal1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"semiFinal1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Semi Final 1</div>");
            buf.append("<select id=\"requiredField\" name=\"semiFinal1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Semi Final 1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("semiFinal2")) {
                String value = WebUtil.display(request.getParameter("semiFinal2"));

                if ( forceHiddenSet.contains("semiFinal2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"semiFinal2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Semi Final 2</div>");
            buf.append("<select id=\"requiredField\" name=\"semiFinal2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Semi Final 2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("semiFinal3")) {
                String value = WebUtil.display(request.getParameter("semiFinal3"));

                if ( forceHiddenSet.contains("semiFinal3")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"semiFinal3\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Semi Final 3</div>");
            buf.append("<select id=\"requiredField\" name=\"semiFinal3\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Semi Final 3Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("semiFinal4")) {
                String value = WebUtil.display(request.getParameter("semiFinal4"));

                if ( forceHiddenSet.contains("semiFinal4")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"semiFinal4\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Semi Final 4</div>");
            buf.append("<select id=\"requiredField\" name=\"semiFinal4\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Semi Final 4Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("final1")) {
                String value = WebUtil.display(request.getParameter("final1"));

                if ( forceHiddenSet.contains("final1")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"final1\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Final 1</div>");
            buf.append("<select id=\"requiredField\" name=\"final1\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Final 1Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("final2")) {
                String value = WebUtil.display(request.getParameter("final2"));

                if ( forceHiddenSet.contains("final2")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"final2\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Final 2</div>");
            buf.append("<select id=\"requiredField\" name=\"final2\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014Final 2Option");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("champion")) {
                String value = WebUtil.display(request.getParameter("champion"));

                if ( forceHiddenSet.contains("champion")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"champion\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Champion</div>");
            buf.append("<select id=\"requiredField\" name=\"champion\">");
            buf.append("<option value=\"\" >- Please Select -</option>");

            List dropMenus = DropMenuUtil.getDropMenus("SweepWorldcup2014ChampionOption");
            for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
                DropMenuItem it = (DropMenuItem) iterItems.next();
                buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay()+"</option>");
            }

            buf.append("</select>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("finalScoreWin")) {
                String value = WebUtil.display(request.getParameter("finalScoreWin"));

                if ( forceHiddenSet.contains("finalScoreWin")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"finalScoreWin\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Final Score Win</div>");
            buf.append("<INPUT NAME=\"finalScoreWin\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
			buf.append("<br/>");
			}
			}

            if ( ignoreFieldSet || fieldSet.contains("finalScoreLose")) {
                String value = WebUtil.display(request.getParameter("finalScoreLose"));

                if ( forceHiddenSet.contains("finalScoreLose")){
                    buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"finalScoreLose\"  value=\""+value+"\">");
                } else {

			buf.append("<div class=\"ajaxFormLabel\" style=\"font-weight:bold;\">Final Score Lose</div>");
            buf.append("<INPUT NAME=\"finalScoreLose\" type=\"text\" size=\"70\" value=\""+value+"\"></INPUT>");
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
            importedScripts += "function responseCallback_sweepWorldcup2014(data){\n";
            importedScripts += "    document.getElementById(\"resultDisplaySweepWorldcup2014\").innerHTML = data;\n";
            importedScripts += "}\n";
            importedScripts += "function sendRequest_sweepWorldcup2014(){\n";
            importedScripts +=     "xmlhttpPostXX('sweepWorldcup2014FormAddDis','/sweepWorldcup2014Action.html', 'resultDisplaySweepWorldcup2014', '${ajax_response_fields}', responseCallback_sweepWorldcup2014);\n";
            importedScripts += "}\n";
            importedScripts += "function clearform_sweepWorldcup2014(){\n";
            importedScripts +=     "clearFormXX('sweepWorldcup2014FormAddDis');\n";
            importedScripts += "}\n";
            importedScripts += "function showform_sweepWorldcup2014(){\n";
            importedScripts +=     "backToXX('sweepWorldcup2014FormAddDis','resultDisplaySweepWorldcup2014');\n";
            importedScripts += "}\n";

			buf.append(importedScripts);
            buf.append("\n");

	        buf.append("document.write('");
			buf.append("<form name=\"sweepWorldcup2014FormAddDis\" method=\"POST\" action=\"/sweepWorldcup2014Action.html\" id=\"sweepWorldcup2014FormAddDis\">");
			buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");

		buf.append("<div class=\"ajaxFormLabel\"> Player</div>");
        buf.append("<input class=\"field\" id=\"player\" type=\"text\" size=\"70\" name=\"player\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 1</div>");
        buf.append("<select class=\"field\" name=\"game1\" id=\"game1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusGame1 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 1 Score</div>");
        buf.append("<input class=\"field\" id=\"game1Score\" type=\"text\" size=\"70\" name=\"game1Score\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 1 Score Opp</div>");
        buf.append("<input class=\"field\" id=\"game1ScoreOpp\" type=\"text\" size=\"70\" name=\"game1ScoreOpp\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 2</div>");
        buf.append("<select class=\"field\" name=\"game2\" id=\"game2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusGame2 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 2 Score</div>");
        buf.append("<input class=\"field\" id=\"game2Score\" type=\"text\" size=\"70\" name=\"game2Score\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 2 Score Opp</div>");
        buf.append("<input class=\"field\" id=\"game2ScoreOpp\" type=\"text\" size=\"70\" name=\"game2ScoreOpp\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 3</div>");
        buf.append("<select class=\"field\" name=\"game3\" id=\"game3\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusGame3 = DropMenuUtil.getDropMenus("SweepWorldcup2014WinLose");
	for(Iterator iterItems = dropMenusGame3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 3 Score</div>");
        buf.append("<input class=\"field\" id=\"game3Score\" type=\"text\" size=\"70\" name=\"game3Score\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Game 3 Score Opp</div>");
        buf.append("<input class=\"field\" id=\"game3ScoreOpp\" type=\"text\" size=\"70\" name=\"game3ScoreOpp\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Advance</div>");
        buf.append("<input class=\"field\" id=\"advance\" type=\"text\" size=\"70\" name=\"advance\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 A1</div>");
        buf.append("<select class=\"field\" name=\"team16A1\" id=\"team16A1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16A1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16A1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 A2</div>");
        buf.append("<select class=\"field\" name=\"team16A2\" id=\"team16A2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16A2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16A2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 B1</div>");
        buf.append("<select class=\"field\" name=\"team16B1\" id=\"team16B1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16B1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16B1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 B2</div>");
        buf.append("<select class=\"field\" name=\"team16B2\" id=\"team16B2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16B2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16B2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 C1</div>");
        buf.append("<select class=\"field\" name=\"team16C1\" id=\"team16C1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16C1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16C1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 C2</div>");
        buf.append("<select class=\"field\" name=\"team16C2\" id=\"team16C2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16C2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16C2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 D1</div>");
        buf.append("<select class=\"field\" name=\"team16D1\" id=\"team16D1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16D1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16D1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 D2</div>");
        buf.append("<select class=\"field\" name=\"team16D2\" id=\"team16D2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16D2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16D2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 E1</div>");
        buf.append("<select class=\"field\" name=\"team16E1\" id=\"team16E1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16E1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16E1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 E2</div>");
        buf.append("<select class=\"field\" name=\"team16E2\" id=\"team16E2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16E2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16E2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 F1</div>");
        buf.append("<select class=\"field\" name=\"team16F1\" id=\"team16F1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16F1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16F1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 F2</div>");
        buf.append("<select class=\"field\" name=\"team16F2\" id=\"team16F2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16F2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16F2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 G1</div>");
        buf.append("<select class=\"field\" name=\"team16G1\" id=\"team16G1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16G1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16G1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 G2</div>");
        buf.append("<select class=\"field\" name=\"team16G2\" id=\"team16G2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16G2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16G2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 H1</div>");
        buf.append("<select class=\"field\" name=\"team16H1\" id=\"team16H1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16H1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16H1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Team16 H2</div>");
        buf.append("<select class=\"field\" name=\"team16H2\" id=\"team16H2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusTeam16H2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusTeam16H2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 1</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal1\" id=\"quarterFinal1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 2</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal2\" id=\"quarterFinal2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 3</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal3\" id=\"quarterFinal3\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 4</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal4\" id=\"quarterFinal4\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal4.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 5</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal5\" id=\"quarterFinal5\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal5 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal5.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 6</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal6\" id=\"quarterFinal6\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal6 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal6.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 7</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal7\" id=\"quarterFinal7\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal7 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal7.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Quarter Final 8</div>");
        buf.append("<select class=\"field\" name=\"quarterFinal8\" id=\"quarterFinal8\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusQuarterFinal8 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusQuarterFinal8.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Semi Final 1</div>");
        buf.append("<select class=\"field\" name=\"semiFinal1\" id=\"semiFinal1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusSemiFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Semi Final 2</div>");
        buf.append("<select class=\"field\" name=\"semiFinal2\" id=\"semiFinal2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusSemiFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Semi Final 3</div>");
        buf.append("<select class=\"field\" name=\"semiFinal3\" id=\"semiFinal3\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusSemiFinal3 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal3.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Semi Final 4</div>");
        buf.append("<select class=\"field\" name=\"semiFinal4\" id=\"semiFinal4\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusSemiFinal4 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusSemiFinal4.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Final 1</div>");
        buf.append("<select class=\"field\" name=\"final1\" id=\"final1\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusFinal1 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusFinal1.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Final 2</div>");
        buf.append("<select class=\"field\" name=\"final2\" id=\"final2\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusFinal2 = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusFinal2.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Champion</div>");
        buf.append("<select class=\"field\" name=\"champion\" id=\"champion\">");
        buf.append("<option value=\"\" >- Please Select -</option>");
	List dropMenusChampion = DropMenuUtil.getDropMenus("SweepWorldcup2014AllTeams");
	for(Iterator iterItems = dropMenusChampion.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
				
		buf.append("<option value=\""+it.getValue() +"\" >"+it.getDisplay() +"</option>");
	} 
        buf.append("</select>  <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Final Score Win</div>");
        buf.append("<input class=\"field\" id=\"finalScoreWin\" type=\"text\" size=\"70\" name=\"finalScoreWin\" value=\"\"/> <span></span>");
		buf.append("<br/>");
		buf.append("<div class=\"ajaxFormLabel\"> Final Score Lose</div>");
        buf.append("<input class=\"field\" id=\"finalScoreLose\" type=\"text\" size=\"70\" name=\"finalScoreLose\" value=\"\"/> <span></span>");
		buf.append("<br/>");


			buf.append("<a id=\"formSubmit_ajax_simpleform\" href=\"javascript:sendRequest_sweepWorldcup2014()\">Submit</a><br>");
            buf.append("<a href=\"javascript:clearform_sweepWorldcup2014()\">Clear Form</a><br>");
		    //buf.append("<a href=\"/moveTo.html?dest=<%=cancelPage%>\">Cancel</a><br>");
			buf.append("</form>");
			buf.append("<span id=\"resultDisplaySweepWorldcup2014\"></span>");
			buf.append("<a href=\"javascript:showform_sweepWorldcup2014()\">Show form</a><br>");
	        buf.append("');");

            ret.put("__value", buf.toString());

        } else if (hasRequestValue(request, "ajaxOut", "getjson") || hasRequestValue(request, "ajaxOut", "getlistjson") ||
                   hasRequestValue(request, "ajxr", "getjson") || hasRequestValue(request, "ajxr", "getlistjson")  ){
            m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

            List list = prepareReturnData(request, target);
            boolean returnList = isAjaxListOutput(request);

            String fieldSetStr = request.getParameter("fieldlist");
            Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

            boolean ignoreFieldSet = (fieldSetStr == null? true: false);

            Site site                               = findSessionBoundSite(request);
            AutositeSessionContext sessionContext   = (AutositeSessionContext)getSessionContext(session);
            AutositeRemoteDevice device             = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());

            DeviceSynchTracker tracker              = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);

            JSONObject top = new JSONObject();

            if (returnList) {

                top.put("type", "list");
	            top.put("count", ""+list.size());
	            JSONArray array = new JSONArray();

	            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	                SweepWorldcup2014 _SweepWorldcup2014 = (SweepWorldcup2014) iterator.next();

					JSONObject json = SweepWorldcup2014DataHolder.convertToJSON(_SweepWorldcup2014, fieldSet, ignoreFieldSet, true, true);

                    if ( isSynchRequired() ){
                        try {
                            synchLedger = findOrRegisterSynchLedger(request, _SweepWorldcup2014.getId(), "cleaner-ticket", true );
                            if ( synchLedger != null) {
                                json.put("_synchId", synchLedger.getSynchId());
                                json.put("_synchScope", synchLedger.getScope());
                            }
                        }
                        catch (Exception e) {
                            m_logger.error(e.getMessage(),e);
                        }
                    }					

		            array.put(json);
				}

    	        top.put("list", array);

            } else {

                SweepWorldcup2014 _SweepWorldcup2014 = list.size() >=1?(SweepWorldcup2014) list.get(0): null; 

					top = SweepWorldcup2014DataHolder.convertToJSON(_SweepWorldcup2014, null, false, true, true);

                    if ( isSynchRequired() ){
                        try {
                            synchLedger = findOrRegisterSynchLedger(request, _SweepWorldcup2014.getId(), "cleaner-ticket", true );
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
	                unsynched = tracker.findNotSynchedForAllScopes("cleaner-ticket");
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

                SweepWorldcup2014  _SweepWorldcup2014 = SweepWorldcup2014DS.getInstance().getById(synchTrackingEntry.getObjectId());


				//20130513 Finally implemented the delete support. But getjson may have to also support that returns synch id for delted objects. currently not doing that. 
				//Change summary: when deleted, the object must be gone by this poing. server just returns the synchID with ID only. The device should take care by itself. 

                if (  _SweepWorldcup2014 != null) {
    				totalCount++;
                    JSONObject json = SweepWorldcup2014DataHolder.convertToJSON(_SweepWorldcup2014, null, false, true, true);
    
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
    protected List prepareReturnData(HttpServletRequest request, SweepWorldcup2014 target){

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

    protected List prepareReturnData(HttpServletRequest request, SweepWorldcup2014 target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = SweepWorldcup2014DS.getInstance().getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            SweepWorldcup2014 _SweepWorldcup2014 = null; 
            List list = SweepWorldcup2014DS.getInstance().getBySiteId(site.getId());
            
            if (arg == null){
                _SweepWorldcup2014 = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _SweepWorldcup2014 = (SweepWorldcup2014) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _SweepWorldcup2014 = (SweepWorldcup2014) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _SweepWorldcup2014 = SweepWorldcup2014DS.getInstance().getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_SweepWorldcup2014);

        }
        
        return ret;
    }

    protected boolean isActionCmd(HttpServletRequest request){
        return 
        hasRequestValue(request, "verify", "true")||hasRequestValue(request, "cmd", "verify")||
        super.isActionBasicCmd(request); 
    }
    protected boolean isActionSubCmd(HttpServletRequest request){
        return 
        false; 
    }

}
