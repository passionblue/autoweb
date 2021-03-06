/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.act;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.AutositeSynchLedger;
import com.autosite.db.Site;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.ds.AutositeSynchLedgerDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.util.TimeNow;

/** 
 * MyEclipse Struts
 * Creation date: 06-17-2008
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp" contextRelative="true"
 */
public class PagelessSessionSynchTrackingAction extends AutositeCoreAction {
    /*
     * Generated Methods
     */

    /**
     * the request would be like
     * 
     * http://www.uxsx.com:8080/pagelessSession.html?login=passionbluedirect@gmail.com&password=joshua&ajxr=getctxid&cmd=pagelesslogin
     * 
     * the response would be like 
     * 
     * {"ctxid":"cdac5e2a-0447-4abb-a24b-4d63ed5afe45:29-uxsx.com:20130211133657687","code":"0"}
     * 
     * then with that ctxid, the next request could be like without using the same session
     * 
     * http://www.uxsx.com:8080/userManageAction.html?ajxr=getlistjson&_ctxId=c51fc7d0-189e-44a7-9de6-4b3c41fda961:29-uxsx.com:20130211140423586
     * 
     */
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        Map ret = new HashMap();
        JSONObject top = new JSONObject();

        try {
            Map working = new HashMap();
            ex(mapping, form, request, response, true, working);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);

            top.put("errormsg", e.getMessage());
            top.put("code", "-1");
            
            response.setStatus(500);
            ret.put("__value", top.toString());
            return ret;
        }
        //
        if ( isThere(request, "ajxr") &&  request.getParameter("ajxr").startsWith("getjson")){
            top.put("code", "0");
            top.put("timestamp", String.valueOf(System.currentTimeMillis()));
        }

        ret.put("__value", top.toString());
        
        return ret;
    }    
    
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {
        HttpSession session = request.getSession();
        
        Site site = findSessionBoundSite(request);
        //Command
        if ( hasRequestValue(request, "cmd", "synchConfirm")  ){

            String confirmIds = request.getParameter("confirmIds");
            String confirmTarget = request.getParameter("confirmTarget");
            
            String [] confIds = confirmIds.split(",");

            Set idsSet = new HashSet(Arrays.asList(confIds));
            
            List ledgers = AutositeSynchLedgerDS.getInstance().getBySiteIdTargetList(site.getId(), confirmTarget);
            AutositeRemoteDevice device = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(),  request.getParameter("deviceId"));
            
            for (Iterator iterator = ledgers.iterator(); iterator.hasNext();) {
                AutositeSynchLedger ledger = (AutositeSynchLedger) iterator.next();
                
                if ( ledger.getDeviceId() == device.getId() &&  idsSet.contains(""+ledger.getObjectId())){
                    
                    String token = request.getParameter(ledger.getObjectId()+"");
                    
                    if ( token != null && !token.isEmpty()) 
                        ledger.setRemoteToken(token);
                    else 
                        ledger.setRemoteToken(confirmTarget + "-CONF-" + System.nanoTime());

                    AutositeSynchLedgerDS.getInstance().update(ledger);
                }
            }
            
        } if ( hasRequestValue(request, "cmd", "synchHb")  ){
            
            m_logger.info("Synch Heartbeat received from " + request.getParameter("deviceId") + " at " + new TimeNow());
            AutositeRemoteDevice device = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(),  request.getParameter("deviceId"));
        }

        return mapping.findForward("default");
    }

    protected boolean isPagelessAction(){
        return true;
    }    
    protected boolean loginRequired() {
        return false;
    }
    private static Logger m_logger = Logger.getLogger(PagelessSessionSynchTrackingAction.class);
}
