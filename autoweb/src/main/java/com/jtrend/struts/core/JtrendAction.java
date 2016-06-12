package com.jtrend.struts.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.holder.UserManageDataHolder;
import com.autosite.struts.act.GetPartitionAction;
import com.jtrend.session.CtmSessionManager;
import com.jtrend.session.CtmSessionManagerPool;
import com.jtrend.session.PageView;
import com.jtrend.session.SessionContext;
import com.jtrend.session.SessionRequestHandle;
import com.jtrend.session.UserContext;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.stats.StatData;
import com.jtrend.stats.StatManager;
import com.jtrend.stats.StatUtil;
import com.jtrend.struts.core.model.ActionInsert;
import com.jtrend.struts.core.model.ApplicationInitiator;
import com.jtrend.struts.core.viewproc.ViewProcFactory;
import com.jtrend.util.RequestUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.seox.db.UserDAO;
import com.seox.tools.UserAnalysis;
import com.seox.util.PropertiesLoader;
import com.seox.util.SeoxLogger;
import com.seox.work.AppInitiator;
import com.seox.work.UserBO;

/*

    k_site
    k_is_dynpage
    k_prev_uri
    k_prev_query
    k_request_uri
    k_request_query
    k_view_pageview
    k_page_name
    k_current_dyn_page
    k_page_obj
    k_site_config
    k_session_context   Custom Session context
    k_is_home
    k_reserved_params
    
    k_request_handle_id
    k_top_text
    k_top_error_text
    k_top_warn_text
    k_error_occurred
    k_org_uri
    k_org_query
    k_ignore_embedded_page
    k_userbo
    k_return_display
    k_user_analysis
    k_admin_logs
    k_admin_logs_login
    k_notes
    
 */



abstract public class JtrendAction extends Action implements JTrendActionInterface, WebKeys
{
    
    private static Logger m_logger = Logger.getLogger(JtrendAction.class);
    private static Thread m_thread;
    
    protected ViewManager m_viewManager;
    protected ViewManager m_viewManagerMobile;
    protected ActionInsert m_actionInsert;
    protected PropertiesLoader m_properties;
    private static Object m_initLock = new Object();
    private static boolean m_appInitialized = false;

    
    public JtrendAction()
    {
        super();
        
        if ( m_thread == null ) {
//            m_thread = new Lifeline();
//            m_thread.start();
        }
        
        initApp();
        
        m_logger.info("== Initializaing ACTION " + getClass().getName());
        m_properties = PropertiesLoader.getInstance("app.properties");
        m_properties.printAllProperties();

        m_viewManager = DefaultViewManager.getInstance();
        m_viewManagerMobile = DefaultViewManager.getInstanceMobile();
        m_actionInsert = getActionInsert();
    }
    
    public void initApp2() {
        try {
        	//TODO Disabled for other app.
            AppInitiator.init();
         }
         catch (Exception e) {
             m_logger.error("ERROR while init App", e);
         }
         
         m_viewManager = DefaultViewManager.getInstance();
         m_viewManagerMobile = DefaultViewManager.getInstanceMobile();
	}
    
    public void initApp() {
        synchronized(m_initLock) {
            if ( !m_appInitialized ) {
                try {
                    m_logger.info("----------------------------------------------------");
                    Class theClass  = Class.forName(PropertiesLoader.getInstance().getProperty("app.init.class","com.jtrend.struts.core.DefaultAppInitiator"));
                    m_logger.info("Initiating app with " + theClass.getName());
                    ApplicationInitiator initiator = (ApplicationInitiator)theClass.newInstance();
                    initiator.init();
                         
                    //AppInitiator.init();
                    m_logger.info("----------------------------------------------------");
                    
                } catch (Exception e) {
                    m_logger.error("ERROR while init App", e);
                }
                m_appInitialized = true;
            }
        }
    }

    protected boolean setSuperLogin(HttpSession session) throws Exception{
        throw new Exception("Login not supported");
    }

    
    //#########################################################################################################################
    //#########################################################################################################################
    //#########################################################################################################################
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {

        long        startTime             = System.currentTimeMillis();
        HttpSession session               = request.getSession();
        String      paramStr              = RequestUtil.getParameterString(request);
        StatData    statData              = StatUtil.populate(request, response, paramStr, this);
        
        boolean     willBeDroppedByFilter = filter(request, statData);
        boolean     isPostRequest         = "Post".equalsIgnoreCase(request.getMethod());
        
//        m_logger.info("\n\n\n");
        m_logger.info("################################################################################################################## ");
        m_logger.info("# " + getClass().getName()+ " " + System.currentTimeMillis() + " " + "############################################ ");
        m_logger.info("################################################################################################################## ");
        m_logger.info("\n");
        
        m_logger.debug("======= Filter for invalid Request ==================================================================================");
        
        if ( willBeDroppedByFilter ){
            StatManager.getInstance().dropStatData(statData);
            m_logger.info("*********** DROPPED **************");
            return null;
        } 
        
        m_logger.debug("======= Session Checking checking ==============================================================================");

        if (session.isNew()){
            session.setMaxInactiveInterval(36000);
            m_logger.debug(">>>>>>>>>>>>>>>>>>>> New Session Created <<<<<<<<<<<<<<<<<<<<<<<<<<<<< " + session.getId());
            
            // Auto Login 2013059 moved after context setting. for the pageless session, autologin should not be applied
//            boolean allowAutoSuperLogin =  WebUtil.isTrue(PropertiesLoader.getInstance().getProperty("app.cfg.allow_auto_superadmin_login"));
//            if ( allowAutoSuperLogin && request.getRemoteAddr() != null && request.getRemoteAddr().equals("108.27.45.7") &&) {
//                m_logger.info("SUPER ADMIN AUTO LOGIN");
//                
//                try {
//                    setSuperLogin(session);
//                    m_logger.debug(">>>>>>>>>>>>>>>>>>>> SUPER ADMIN LOGIN <<<<<<<<<<<<<<<<<<<<<<<<<<<<< " + session.getId());
//                }
//                catch (Exception e) {
//                    m_logger.error("Auto login not supported",e);
//                }
//            }
        }
        
        m_logger.debug("======= actionInsert.abort checking ==============================================================================");

        if (isSuperAdmin(session) && m_actionInsert != null && m_actionInsert.abort(request, response)){
            m_logger.debug("Request set to abort. Will be aborted now ");
            statData.setBlocked(true);
            StatManager.getInstance().dropStatData(statData);
            session.removeAttribute("k_cur_req");
            return null;
        }
        
        m_logger.debug("======= actionInsert.executeBegining ==============================================================================");
        try {
            if (m_actionInsert != null)
                m_actionInsert.executeBegining(request, response);
        }
        catch (Exception e1) { m_logger.error("",e1); }
        
        m_logger.debug("======= Request Info ===============================================================================================");

        statData.setHeaderAttributes(RequestUtil.getHeadersAttributes(request));

        WebUtil.printRequestHeader(request);
        WebUtil.printRequestProperties(request);
        WebUtil.printRequest(request);        
        
        m_logger.debug("ParameterString     = " + paramStr);
        m_logger.debug("referer             = " + statData.getReferer());
        m_logger.debug("userAgent           = " + statData.getUserAgent());
        m_logger.debug("headerHost          = " + statData.getHeaderHost());
        
        // Setting character Encoding. 
        //response.setCharacterEncoding("UTF-8");
        // For the encoding issue
        // get to http://wiki.apache.org/tomcat/FAQ/CharacterEncoding for TOMCAT and other things. 
        //
//        try {
//            request.setCharacterEncoding("UTF-8");
//        }
//        catch (UnsupportedEncodingException e3) {
//            m_logger.error("",e3);
//        }
        
        
        // #################################################################################
        // SESSION REQUEST HANDLE
        // Check if it is redirected request. If it is redirect, _reqid parameter should exist
        // #################################################################################

        m_logger.debug("======= Request Handel & Check Internal Redirect ================================================================================================================");
        
        SessionRequestHandle currentRequestHandle   = registerCurrentRequestHandleToSession(request);;
        SessionRequestHandle previousRequestHandle  = getRequestHandleFromHttpRequest(session, request.getParameter("_reqhid"));;
        boolean              internallyForwarded    = isInternallyForwardedRequest(request, previousRequestHandle, currentRequestHandle);
        boolean              isAjaxRequest          = isAjaxRequest(request);
        boolean              isPagelessSession      = isPagelessSession(request);
        if ( internallyForwarded ) {
            m_logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            m_logger.info(">>>>>>>>>>>>>>>>>> INTERNALLY REDIRECTED : <<<<<<<<<<<<<< " + previousRequestHandle );
            m_logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }

        if ( isAjaxRequest ) {
            m_logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            m_logger.info(">>>>>>>>>>>>>>>>>> AJAX AJAX : <<<<<<<<<<<<<< ");
            m_logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        if ( isPagelessSession ) {
            m_logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            m_logger.info(">>>>>>>>>>>>>>>>>> PAGE-LESS MOBILE SESSION : <<<<<<<<<<<<<< ");
            m_logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        
        
        // Request handle descript will be found above the method 
        
        //_reqhid is embedded into redirect request by requestHndleObject in attachIdToUrl(); Search requestHandle in this source
        if ( previousRequestHandle != null){
            currentRequestHandle.setPrevHandleId(previousRequestHandle.getId());
            if ( previousRequestHandle.isHasFollowupAction()){
                m_logger.debug("PrevRequest handle has followup action with params=" + previousRequestHandle.getFollowUpParameters());
                updateActionFormWithFollowupForums(form, previousRequestHandle.getFollowUpParameters());
            }
        }
        
        RequestUtil.setInternallyForwardedRequest(request, internallyForwarded);

        printHandles(request);
        
        // ######################################################################################################################################
        //  COOKIE Management
        // ######################################################################################################################################
        m_logger.debug("======= Cookie Info Load from Client  ===========================================================================================================");
        // printCookieValue(request.getCookies());
        
        String COOKIE_KEY_RPCI = "RPCI"; // Random PC ID
        String COOKIE_KEY_LLU  = "LLUI"; // Last Login User Id
        String COOKIE_KEY_SI   = "SI"; // Last Session ID
        
        printCookieValue(request.getCookies());
        String randomPcId = getCookieValue(request.getCookies(), COOKIE_KEY_RPCI, null);
        if ( randomPcId == null) {
            randomPcId = System.currentTimeMillis()+":"+ System.nanoTime();
            Cookie newC = new Cookie(COOKIE_KEY_RPCI, randomPcId);
            newC.setDomain(request.getServerName());
            newC.setMaxAge(8640000);
            response.addCookie(newC);
            
            m_logger.debug("New RPCI set for cooki " + randomPcId);
        } else {
            m_logger.debug("RPCI received " + randomPcId);
        }
        
        session.setAttribute("k_RPCI", randomPcId);
        statData.setRpcid(randomPcId);
        
        String designatedUserId = getCookieValue(request.getCookies(), "DUI", null);
        String useridInCookie   = getCookieValue(request.getCookies(), COOKIE_KEY_LLU, ""); // Last Login User
        String sessionId        = getCookieValue(request.getCookies(), COOKIE_KEY_SI, null);
        
        if (sessionId == null) {
            Cookie newC = new Cookie(COOKIE_KEY_SI, ""+System.currentTimeMillis());
            response.addCookie(newC);
        }

        // #################################################################################
        // Session Context Creation/Update/Manage 
        // #################################################################################
        m_logger.debug("------------------ SESSION CONTEXT MANAGE --------------------------------------------------");
        Site existingSiteInSession = (Site) session.getAttribute("k_site");
        
        SessionContext sessionContext = null;
        SessionContext sessionContextByRequest = getSessionContextByIdFromRequest(request);
        
        boolean pagelessSessionInvalidRequireClientLogin = false;
        
        // See if request id contains session context serial. If there is an error, the returned session context might have error status. 
        if ( sessionContextByRequest != null && sessionContextByRequest.getSessionStatus() == SessionContext.SESSION_STATUS_NORMAL) {

            m_logger.info("Session serial found in request " + sessionContextByRequest.getSerial() + " created at " + sessionContextByRequest.getCreatedTime());
            SessionContext sessionBoundExisting = (SessionContext) session.getAttribute(SessionContext.getSessionKey());
            m_logger.info(">>>>>>>>>>>>>>>>>> PAGELESS SESSION <<<<<<<<<<<<<< " + sessionContextByRequest.getRemoteIp() + "/" + sessionContextByRequest.getUsername() + "/Device" + sessionContextByRequest.getSourceDeviceId());
            statData.setPageLess(true);
            // check the site URL
            if ( sessionContextByRequest.getServer().equalsIgnoreCase(existingSiteInSession.getSiteUrl())) {
            
                if ( sessionBoundExisting == null ) {
                    sessionContext = sessionContextByRequest;
                    session.setAttribute(SessionContext.getSessionKey(), sessionContext);
                    m_logger.info("Session context found by request " + sessionContextByRequest.getSerial());
                } else {
                    sessionContext = sessionContextByRequest;
                    session.setAttribute(SessionContext.getSessionKey(), sessionContext);
                    m_logger.info("Session bound context already exists...........ignore request however not during the development");
                }
            } else {
                m_logger.info("Session context found by request but site doesn match so will be ignore session= " + existingSiteInSession.getSiteUrl() + " context " + sessionContextByRequest.getServer());
            }
        } else if (  sessionContextByRequest != null && sessionContextByRequest.getSessionStatus() == SessionContext.SESSION_STATUS_NOT_INVALID_LOGIN_REQUIRED) {
            m_logger.warn("Session context is pageless or persisted but seems incorrect status. this might have require relogin fromthe client side.");
            pagelessSessionInvalidRequireClientLogin = true;
        } else {
            m_logger.info("NOT PAGELESS SESSION" );
        }
        
        // context is null still, created usual way. 
        if ( sessionContext == null) {
            sessionContext = getSessionContext(session);
        }

        // Update SessionContext 
        sessionContext.resetLastAccess();
        sessionContext.addRequestHandle(currentRequestHandle);
        
        if (session.isNew()){
            sessionContext.setRemoteIp(request.getRemoteAddr());
            sessionContext.setServer(existingSiteInSession.getSiteUrl());
        }

        sessionContext.setDefaultSessionType(SessionContext.getSessionContextTypeForRequest(request));
        
        if (session.isNew()){
            // Auto Login 
            boolean allowAutoSuperLogin =  WebUtil.isTrue(PropertiesLoader.getInstance().getProperty("app.cfg.allow_auto_superadmin_login"));
            if ( allowAutoSuperLogin && request.getRemoteAddr() != null && request.getRemoteAddr().equals("108.27.45.7") && !SessionContext.isPagelessSession(sessionContext)) {
                try {
                    setSuperLogin(session);
                    m_logger.debug(">>>>>>>>>>>>>>>>>>>> SUPER ADMIN LOGIN <<<<<<<<<<<<<<<<<<<<<<<<<<<<< " + session.getId());
                }
                catch (Exception e) {
                    m_logger.error("Auto login not supported",e);
                }
            }
        }
        
        
    	boolean loginBefore  = sessionContext.isLogin();
    	String  userIdBefore = sessionContext.getUsername(); 

    	m_logger.debug("userid from cookie     :" + useridInCookie);
    	m_logger.debug("current username       :" + sessionContext.getUsername());
    	m_logger.debug("currently login        :" + sessionContext.isLogin());
        m_logger.debug("current sesssionID     :" + session.getId());
        m_logger.debug("randomPcId             :" + randomPcId);
        m_logger.debug("designatedUserId       :" + designatedUserId);

        statData.setLoginBefore(loginBefore);
        statData.setCookieUser(useridInCookie);

    	// Session manager and user context. Common work for all
        boolean loginRequired = loginRequired() && !sessionContext.isLogin();
        m_logger.debug("login required         :" + loginRequired);
        

        if (isPagelessSessionOnly() && sessionContext.getSessionType() != SessionContext.SESSION_TYPE_MOBILE_PAGELESS ) {
            //TODO
            m_logger.warn("**WARNING** This is pageless session but web request received.");
            return null;
        }
        
        if ( sessionContext.getSessionType() != SessionContext.SESSION_TYPE_MOBILE_PAGELESS) {
            m_logger.debug("## PAGELESS SESSION  ###  by device " + sessionContext.getSourceDeviceId());
        }
        
        if (sessionContext.isLogin()) {
            statData.setLoggedinUser(sessionContext.getUsername());
            statData.setLogin(true);
        }
        
        // #################################################################################
        // Response Configurations
        // #################################################################################
        
        if (isJSONResponse(request)){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
        }

        // #################################################################################
        // AJAX
        // #################################################################################
        //boolean isAjax = isAjaxRequest(request);

        if (isAjaxRequest) {
            m_logger.debug("======= THIS IS AJAX START ==============================================================================================");
        
            if ( pagelessSessionInvalidRequireClientLogin) {
                response.setContentType("application/json");
                
                JSONObject top = new JSONObject();
                top.put("code", "-9");
                top.put("errorMsg", "Invalid session. Require reset or relogin the session");
                try {
                    response.getWriter().print(top.toString());
                }
                catch (IOException e) {
                    m_logger.error(e.getMessage(),e);
                    return null;
                }
            } else {
            
                try {
                    String ajaxResponse = processAjaxRequest(mapping, form, request, response);
    
                    if (isJSONResponse(request)){
                        response.setContentType("application/json");
                    } else {
                        response.setContentType("text/html");
                    }
                    
                    if ( ajaxResponse != null) {
                        response.getWriter().print(ajaxResponse);
                    }
                }
                catch (IOException e2) {
                    m_logger.error(e2.getMessage(),e2);
                    statData.setExcpetion(e2);
                }
            }
                
            long endAjax = System.currentTimeMillis();
            m_logger.debug("processing time (AJAX) = " + (endAjax-startTime) + " msec");
            m_logger.debug("======= AJAX END ===============================================================================================");

            //printSessionKeys(session);
            //WebUtil.printSessionKeys(session);

            statData.setTotalTime((endAjax-startTime));
            statData.setAjax(true);
            StatManager.getInstance().dropStatData(statData);
            
            return null;
        } // End of AJAX
        
        // #################################################################################
        // Setting subcommand
        // #################################################################################
//        String subc = request.getParameter("subc");
//        if (subc == null) {
//            subc = "default";
//        }

        // #################################################################################
        // Check if should keep the previous view. This is just HACK.
        // #################################################################################
        String noResponse = request.getParameter("noresp");
        String keepView = request.getParameter("keepview");

        if ( "TRUE".equalsIgnoreCase(noResponse)) {
            session.setAttribute("k_no_response", "true");
        } else {
            session.setAttribute("k_no_response", "false");
        }
        
//        if( "TRUE".equalsIgnoreCase(keepView) || "TRUE".equalsIgnoreCase(noResponse) ) {
//            if ( session.getAttribute("k_view_pageview")  != null ) {
//                session.setAttribute("k_keepview", "true");
//                session.setAttribute("k_last_pageview", session.getAttribute("k_view_pageview"));
//                request.setAttribute("k_last_pageview", request.getAttribute("k_view_pageview"));
//                
//                m_logger.debug("Will keep the last page="+session.getAttribute("k_view_pageview"));
//                
//            } else {
//                session.setAttribute("k_keepview", "false");
//            }
//        } else {
//            session.setAttribute("k_keepview", "false");
//        }
//

          if ( session.getAttribute("k_view_pageview")  != null ) {
              session.setAttribute("k_last_pageview", session.getAttribute("k_view_pageview"));
              request.setAttribute("k_last_pageview", request.getAttribute("k_view_pageview"));
              m_logger.debug("PageView from the last request saved " + session.getAttribute("k_view_pageview"));
          }      
        
        // #################################################################################
        // Resetting Session Keys
        // #################################################################################
        resetSessionViewKeys(session);
        // need request in session for SetPage() method migration from session based to request based
        session.setAttribute("k_current_request", request);
        
        if (!internallyForwarded) {
            session.removeAttribute("returnPage");
        }
        
        // #################################################################################
        // PRE
        // #################################################################################

        try {
            if (m_actionInsert != null)
                m_actionInsert.executePreExecute(request, response);
        }
        catch (Exception e1) { m_logger.error("",e1); }
        
        // #################################################################################
        // Main Servelet
        // #################################################################################
        
        m_logger.debug("\n\n===========> MAIN SERVLET <=============================================================================================================");
        ActionForward actionForward = null;
        boolean forwardToLogin = false;

        if (loginRequired)
        {
            forwardToLogin = true;
            SeoxLogger.filelog("main", request.getRemoteAddr() + " -> " + statData.getRemoteAddress() + "/" + request.getRequestURI() + ",user=NOT LOGGED" + "," + WebUtil.display(request.getQueryString()) );
        }
        else
        {
            
            try {
                SeoxLogger.filelog("main", request.getRemoteAddr() + " -> " + statData.getRemoteAddress() + "/" + request.getRequestURI() + ",user=" + sessionContext.getUsername() + "," + WebUtil.display(request.getQueryString()) );
                
                // ##################################################################################################################################################################
                // Parameters organize
                // ##################################################################################################################################################################
                Map requestParams = RequestUtil.getParameters(request);
                session.setAttribute("k_request_params", requestParams); 
                request.setAttribute("k_request_params", requestParams); 

                Map reservedParams = RequestUtil.getPreservedParameters(request);
                session.setAttribute("k_reserved_params", reservedParams); 
                request.setAttribute("k_reserved_params", reservedParams); 
                
                Map reservedAndTransferParams = RequestUtil.getPreservedAndTransferParameters(request);
                session.setAttribute("k_reserve_xfer_params", reservedAndTransferParams); 
                request.setAttribute("k_reserve_xfer_params", reservedAndTransferParams); 
                

                // For internally forwarded request, it would lose all parameters. It is okay but preserved ones need to be passed back.  
                // parameters for internal transfer can get from requestHandle
                
                if (internallyForwarded) {
                    Map preservedForInternalTransfer = previousRequestHandle.getPreservedParameters();
                    session.setAttribute("k_reserved_params", preservedForInternalTransfer); 
                    request.setAttribute("k_reserved_params", preservedForInternalTransfer); 

                    Map previousRequestParams = previousRequestHandle.getParameters();
                    session.setAttribute("k_previous_request_params", previousRequestParams); 
                    request.setAttribute("k_previous_request_params", previousRequestParams); 
                }
                
                // ##################################################################################################################################################################
                // ##################################################################################################################################################################
                // ##################################################################################################################################################################
                WebUtil.printSessionKeys(session);
                WebUtil.printRequest(request);
                actionForward =  ex(mapping, form, request, response);
                WebUtil.printSessionKeys(session);
                WebUtil.printRequest(request);
                // ##################################################################################################################################################################
                // ##################################################################################################################################################################
                // ##################################################################################################################################################################
                
                // #################################################################################
                // RETURN PAGE or Forward 
                // #################################################################################

                // If it is confirm required action, this is set. 
                boolean ignoreRequestEmbeddedPage = isIgnoreEmbeddedPage(session);
                boolean forwardToInEffect = false;
                
                m_logger.debug("======= FORWARD/RETURN PAGE SETTING  ====================================================================================");
                if (!ignoreRequestEmbeddedPage){
                    if ( WebUtil.isNotNull(request.getParameter("fwdTo"))  ||  
                         WebUtil.isNotNull(request.getParameter("fwdToErr"))  ||
                         WebUtil.isNotNull(request.getParameter("fwdToNorm"))   ){
    
                        if ( isSessionErrorFlag(session) && WebUtil.isNotNull(request.getParameter("fwdToErr")) ) {
                            PageView pv = getViewManager(sessionContext).getPageView(request.getParameter("fwdToErr"), getServerForPage(session));
                            if (pv != null)
                                actionForward = new ActionForward(pv.getContentPage(), true);
                            else
                                actionForward = new ActionForward(request.getParameter("fwdToErr"), true);
                            m_logger.debug("Err Forward URL set to " + request.getParameter("fwdToErr"));
                            forwardToInEffect = true;
                        }
                        if ( !isSessionErrorFlag(session)  && WebUtil.isNotNull(request.getParameter("fwdToNorm")) ) {
                            PageView pv = getViewManager(sessionContext).getPageView(request.getParameter("fwdToNorm"), getServerForPage(session));
                            if (pv != null)
                                actionForward = new ActionForward(pv.getContentPage(), true);
                            else
                                actionForward = new ActionForward(request.getParameter("fwdToNorm"), true);
                            m_logger.debug("Norm Forward URL set to " + request.getParameter("fwdToNorm"));
                            forwardToInEffect = true;
                        }
                        if ( !forwardToInEffect && WebUtil.isNotNull(request.getParameter("fwdTo") )){
                            
                            PageView pv = getViewManager(sessionContext).getPageView(request.getParameter("fwdTo"), getServerForPage(session));
                            if (pv != null)
                                actionForward = new ActionForward(pv.getContentPage(), true);
                            else
                                actionForward = new ActionForward(request.getParameter("fwdTo"), true);
                            
                            m_logger.debug("Forward URL set to " + request.getParameter("fwdTo") + " by fwdTo");
                            forwardToInEffect = true;
                        }
                    } 
                } //ignoreRequestEmbeddedPage
                
                // __fwdTo is set as a result of processing request. It means it is redirecting to another action instead of 
                // returning the page. 
                if ( WebUtil.isNotNull((String)session.getAttribute("__fwdTo"))){

                    //TODO refactor
                    String fwdToName = (String)session.getAttribute("__fwdTo");
                    
                    //Added / at the beging, seems without it, it goes to relative path
                    actionForward = new ActionForward(  "/"+currentRequestHandle.attachIdToUrl(fwdToName), true);
                    m_logger.debug("Forward URL set to " + fwdToName + " by session set __fwdTo");
                    session.removeAttribute("__fwdTo");
                    forwardToInEffect = true;
                    currentRequestHandle.setRequestRedirected(true);

                } else if ( WebUtil.isNotNull((String)request.getAttribute("__fwdTo"))){
                    actionForward = new ActionForward(  currentRequestHandle.attachIdToUrl((String)request.getAttribute("__fwdTo")), true);
                    m_logger.debug("Forward URL set to " + (String)request.getAttribute("__fwdTo") + " by session set __fwdTo");
                    request.removeAttribute("__fwdTo");
                    forwardToInEffect = true;
                    currentRequestHandle.setRequestRedirected(true);
                }
                
                
                if (forwardToInEffect) {

                    // Set this flag when fowarding to different URL. 
                    currentRequestHandle.setRequestRedirected(true);
                    currentRequestHandle.copyKeyAttributesFrom(request); //save for the redirected to. 

                } else if (ignoreRequestEmbeddedPage) {
                    
                } else {

                    // Set this flag when fowarding to different URL. 
                    //session.removeAttribute("k_internally_forwarded");
                    
                    if (isSessionErrorFlag(session) && WebUtil.isNotNull(request.getParameter("errorPage"))){
    
                        String errorPage = request.getParameter("errorPage");
                        PageView pageView = getViewManager(sessionContext).getPageView(errorPage, getServerForPage(session));
                        if (pageView != null) {
                            m_logger.info("## Setting the error Return page from the request. errorPage=" + errorPage);
                            setPage(session, errorPage);
                        }
                        
                    } else {
                    
                        // Check if returnPage has been sent in the request. If so, use it. 
                        String returnPage = request.getParameter("returnPage") == null ?  request.getParameter("retpg"): request.getParameter("returnPage") ;
                        if (returnPage != null && returnPage.length() > 1){
                            
                            int pos = returnPage.indexOf(",");
                            if (pos > 0){
                                String pagePart = returnPage.substring(0, pos);
                                String parmPart = returnPage.substring(pos+1);
                                
                                returnPage = pagePart;
                                m_logger.debug("returnPage embedded params. Copying to reserved Params=" +parmPart );
                                copyToPreserveParams(parmPart, session);
                            }
                            
                            PageView pageView = getViewManager(sessionContext).getPageView(returnPage, getServerForPage(session));
                            if (pageView != null) {
                                m_logger.info("## Setting the return page from the request. returnPage=" + returnPage);
                                setPage(session, returnPage);
                            }
                        }
                    }
                } 

            }
            catch (Exception e) {
                m_logger.error("Unexpected exception occured", e);
                setPage(session, "error");
                actionForward = mapping.findForward("default");
                statData.setExcpetion(e);
            }
        }

        if (forwardToLogin) {
            actionForward =  forceLogin(mapping, request);
            
            session.setAttribute("k_org_uri", request.getRequestURI());
            if (WebUtil.isNotNull(request.getQueryString()))
                session.setAttribute("k_org_query", request.getQueryString());
        }
        
        
        // #################################################################################
        // POST EX
        // #################################################################################

        try {
            if (m_actionInsert != null)
                m_actionInsert.executePostExecute(request, response); //TODO need move them into filtering
        }
        catch (Exception e1) { m_logger.error("",e1); }
        
        // Check if logged in. If so, update UserContext and add cookie.
        m_logger.debug("\n=======> MAIN SERVLET END  <======================================================================================================\n\n");
        WebUtil.printSessionKeys(session);
        WebUtil.printRequest(request);       
        currentRequestHandle.setPageView(getCurrentPageView(request));
        statData.setPageAlias(getPage(request));
        
        // #################################################################################
        // User just logged in.
        // #################################################################################
        if ( sessionContext.isLogin() && !loginBefore )
        {
            m_logger.debug("User seemed to login in this request");

            Cookie newCookie = new Cookie("user", sessionContext.getUsername());
            newCookie.setDomain(request.getServerName());
            newCookie.setMaxAge(8640000);
            response.addCookie(newCookie);
            
            if (designatedUserId == null) {
                
                newCookie = new Cookie("DUI", sessionContext.getCanonicalUserId());
                newCookie.setDomain(request.getServerName());
                newCookie.setMaxAge(8640000);
                response.addCookie(newCookie);
            }
            
            m_logger.debug("Created cookie userid=" + sessionContext.getUsername());
            SeoxLogger.filelog("event/login", "site=" + request.getServerName() + ",from=" + request.getRemoteAddr() + ",user=" + sessionContext.getUsername() + ",rpci=" + statData.getRpcid());
            statData.setLoginNow(true);
        }

        
        // #################################################################################
        // User seemed logged in while it is already logged in. 
        // #################################################################################
        else if (sessionContext.isLogin() && loginBefore && userIdBefore != null && !userIdBefore.equals(sessionContext.getUsername()))
        {
            m_logger.debug("Just loggedin with diff user. userid=" + sessionContext.getUsername());

            
            Cookie newC = new Cookie("user", sessionContext.getUsername());
            newC.setDomain(request.getServerName());
            newC.setMaxAge(8640000);
            response.addCookie(newC);
            
        	CtmSessionManager sessionMgr = getSessionManager(sessionContext.getUsername(), true);

        	if ( !sessionMgr.getSessionContext().getSerial().equals(sessionContext.getSerial()))
        	{
        		session.setAttribute(SessionContext.getSessionKey(), sessionMgr.getSessionContext());
        		m_logger.debug("SessionContext has been replaced ");
        	}
        }
        
        long end = System.currentTimeMillis();
        m_logger.debug("processing time = " + (end-startTime) + " msec");
        m_logger.debug("################### END ######################### " + new Date(end));
        statData.setTotalTime((end-startTime));
        request.setAttribute("g_action_time_taken", String.valueOf(end-startTime));

        
        // #################################################################################
        // Processing View Proc k_view_pageview
        // #################################################################################

        PageView currentPageView = getCurrentPageView(request);
        
        if (currentPageView != null) {
            if ( ViewProcFactory.getInstance().getViewProc(currentPageView.getAlias()) != null) {
                m_logger.debug("ViewProc=" + ViewProcFactory.getInstance().getViewProc(currentPageView.getAlias()).getName() + ",alias=" + currentPageView.getAlias() + ",page=" + currentPageView.getContentPage());
                try {
                    ViewProcFactory.getInstance().getViewProc(currentPageView.getAlias()).process(request, session, currentPageView, true);
                }
                catch (Exception e) {
                    setPage(session, "error");
                    m_logger.error("ERROR occured while process view proc", e);
                }
            } else {
                m_logger.debug("No View Proc found for " + currentPageView.getAlias());
            }
        }
        
        //printSessionKeys(session);

        // #################################################################################
        // Page Size
        // #################################################################################
        
        // commented out. seems pageSize not being used
//        String pageSize = (String) session.getAttribute("pageSize");
//        if (pageSize == null) {
//            String defaultWidth = m_properties.getProperty("app.cfg.default_page_width");
//            session.setAttribute("pageSize", defaultWidth);
//        }
        
        if ( session.getAttribute("k_no_response").equals("true")) return null;
        
        StatManager.getInstance().dropStatData(statData);

        // #################################################################################
        // Execute Ending Insertion
        // #################################################################################
        m_logger.debug("--- actionInsert.executeEnding -------------------------------------------------------------");
        try {
            if (m_actionInsert != null)
                m_actionInsert.executeEnding(request, response);
        }
        catch (Exception e1) { m_logger.error("",e1); }

        m_logger.debug(actionForward.getName());
        m_logger.debug(actionForward.getRedirect());
        m_logger.debug(actionForward.getModule());
        m_logger.debug(actionForward.getPath());
        
        // #################################################################################
        // TimeLog Logging
        // #################################################################################
        String sessionUser = sessionContext.isLogin()? sessionContext.getUsername():"#ANONYMOUS";
        SeoxLogger.filelog("timelog",  sessionUser + "|" + StringUtils.leftPad(String.valueOf(end-startTime), 5) + "|"+ request.getServerName() + "|"+ request.getRemoteAddr() + "|" + request.getRequestURI() + "?" + paramStr);
        session.removeAttribute("k_cur_req");

        // #################################################################################
        // Some Cleanup
        // #################################################################################
        
        WebUtil.printSessionKeys(session);
        WebUtil.printRequest(request);
        
        m_logger.info("\n");
        m_logger.info("################################################################################################################################## ");
        m_logger.info("# ACTION: " + getClass().getSimpleName()+ " TIME: " + (end-startTime) + "(ms) PAGE: " + currentPageView.getAlias() + "--->" + currentPageView.getContent());
        m_logger.info("##################################################################################################################################");
        
        m_logger.info("\n\n\n");
        
        if ( isPagelessAction()){
            return null;
        } else {
            return actionForward;
        }
    } // -------------- END of execution ------------------------------

    // #################################################################################
    // AJAX
    // #################################################################################

	protected String processAjaxRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

	    Map res = exAjax(mapping, form, request, response);
	    String ret = null;
	    
	    //=========== RAW DATA ================
	    if (hasRequestValue(request, "ajaxOut", "getfield")||  hasRequestValue(request, "ajxr", "getfield") ){
            m_logger.debug("processing response getfield");
	        if (WebUtil.isNotTrue((String)res.get("__error"))){
	            ret = (String)res.get("__value");
	        }
	        
        } else if (hasRequestValue(request, "ajaxOut", "get2field")  ||  hasRequestValue(request, "ajxr", "get2field")){
            m_logger.debug("processing response get2");
            
            if (WebUtil.isTrue((String)res.get("__error"))){
                ret = "error:" + (String) res.get("__errorMsg");
            } else {
                ret = "success:" + (String)res.get("__value");
            }            

        //=========== STATUS ================
        } else if (hasRequestValue(request, "ajaxOut", "getModalStatus")||  hasRequestValue(request, "ajxr", "getModalStatus")){
            m_logger.debug("processing response getModalStatus");
            m_logger.debug("__error = " + res.get("__error"));
            m_logger.debug("__errorMsg = " + res.get("__errorMsg"));
            
            if (WebUtil.isTrue((String)res.get("__error"))){
                ret = (String) res.get("__errorMsg");
            } else {
                ret = (String)res.get("__value");
            }
        } else if (hasRequestValue(request, "ajaxOut", "get2ModalStatus")||  hasRequestValue(request, "ajxr", "get2ModalStatus")){
            m_logger.debug("processing response getModalStatus");
            m_logger.debug("__error = " + res.get("__error"));
            m_logger.debug("__errorMsg = " + res.get("__errorMsg"));
            
            if (WebUtil.isTrue((String)res.get("__error"))){
                ret = (String) res.get("__errorMsg");
            } else {
                ret = (String)res.get("__value");
            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getstatus")  ||  hasRequestValue(request, "ajxr", "getstatus")){

            m_logger.debug("processing response getModalStatus");
            m_logger.debug("__error = " + res.get("__error"));
            m_logger.debug("__errorMsg = " + res.get("__errorMsg"));
            
            if (WebUtil.isTrue((String)res.get("__error"))){
                ret = (String) res.get("__errorMsg");
                if ( ret == null || ret.equals(""))
                    ret = "Error occurred Please try again.";
            } else {
                ret = "";
            }
        } else if (hasRequestValue(request, "ajaxOut", "get2status")  ||  hasRequestValue(request, "ajxr", "get2status")){
            m_logger.debug("processing response get2");
            
            if (WebUtil.isTrue((String)res.get("__error"))){
                ret = "error:" + (String) res.get("__errorMsg");
            } else {
                ret = "success:";
            }            
            
        // =================== HTML ================    
        } else if (hasRequestValue(request, "ajaxOut", "getmodalform")  ||  hasRequestValue(request, "ajxr", "getmodalform")){
            m_logger.debug("processing response getmodalform");
            ret = (String)res.get("__value");

        } else if (isJSONResponse(request) && isPagelessRequest(request)) {

                
                if (WebUtil.isTrue((String)res.get("__error"))){
                    ret = "error:" + (String) res.get("__errorMsg");
            
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("errorMsg", res.get("__errorMsg"));
                    
                    if ( res.containsKey("__errorCode")){
                        jsonObject.put("errorCode", res.get("__errorCode"));
                        jsonObject.put("code", res.get("__errorCode"));
                        
                    } else {
                        jsonObject.put("errorCode", "-1");
                        jsonObject.put("code", "-1");
                    }

                    ret = jsonObject.toString();
                    
                } else {
                    ret = (String)res.get("__value");
                }    

        } else {
	        ret = (String)res.get("__value");
	    }
	    
	    m_logger.info("AJAX RES=" + ret);
	    return ret;
	}
	
    // Prepares data to be returned in ajax.
    protected boolean isAjaxListOutput(HttpServletRequest request){

        boolean returnList = request.getParameter("ajaxOut") != null && 
                ( request.getParameter("ajaxOut").startsWith("getlist") ||  
                  request.getParameter("ajaxOut").startsWith("getlisthtml") ||  
                  request.getParameter("ajaxOut").startsWith("getlistjson"));

        boolean returnList2 = request.getParameter("ajxr") != null && 
                ( request.getParameter("ajxr").startsWith("getlist") ||  
                  request.getParameter("ajxr").startsWith("getlisthtml") ||  
                  request.getParameter("ajxr").startsWith("getlistjson"));

        return returnList||returnList2;
    }	

    protected boolean isJsonSynchOutput(HttpServletRequest request){

        boolean returnList2 = request.getParameter("ajxr") != null && 
                ( request.getParameter("ajxr").startsWith("getjsonsynch") ||  
                  request.getParameter("ajxr").startsWith("getlistjsonsynch"));

        return returnList2;
    }       
    
    /*
     * created to support pageless session. See PagelessSessionManagerAction
     * If pageless action, it doesn return page forward mapper.  
     */
    protected boolean isPagelessAction(){
        return false;
    }

    protected boolean isPagelessRequest(HttpServletRequest request ){
        return WebUtil.isNotNull(request.getParameter("_ctxId"));
        
    }
    
    
    // #################################################################################
    // 
    // #################################################################################
	public boolean filter(HttpServletRequest request, StatData statData) {
	    
	    // Check whether user agent is all good. 
	    String userAgent = request.getHeader("user-agent");
	    if (userAgent == null) {
	        m_logger.warn("**DROP** : user-agent mssing in header");
	        statData.setDropped(true);
	        statData.setDropReason("User-agent missing in header");
	        return true;
	    }
	    
	    //check whether host server and request headers are same. 
	    String headerHost = request.getHeader("host");
	    String requestHost = request.getServerName();
	    
	    if (headerHost.indexOf(requestHost) >=0 ||requestHost.indexOf(headerHost)>=0) {
	        
	    } else {
            m_logger.warn("**DROP** : host are diff headerHost=" + headerHost + " reqHost=" + requestHost);
            statData.setDropped(true);
            statData.setDropReason("host are diff headerHost=" + headerHost + " reqHost=" + requestHost);
	        return true;
	    }
	    
	    return false;
	}
	
    public ActionForward forceLogin(ActionMapping mapping, HttpServletRequest request)
    {
        m_logger.debug("&&&&&&&&&&& FORCE LOGIN &&&&&&&&&&&&&&&&&&&&&&&");
        HttpSession session = request.getSession();
        session.setAttribute("k_view_page_name", "/jsp/statics/login_form.jsp");
        setPage(session, "login_form");
        return mapping.findForward("default");
    }

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        try {
            return ex(mapping, form, request, response, false);
        }
        catch (Exception e) {
            m_logger.error("",e);
            throw new RuntimeException(e);
        }
    }
    
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception 
    {
        m_logger.debug("&&&&&&&&&&& SHOULDN'T BE HERE &&&&&&&&&&&&&&&&&&&&&&&");
        return null;
    }

    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        m_logger.debug("&&&&&&&&&&& AJAX PROCESS NOT SUPPORTED &&&&&&&&&&&&&&&&&&&&&&&");
        return new HashMap();
    }

    protected UserContext getUserContext()
    {
        UserContext ret = null;
        return ret;
    }

    // ##################################################################################################
    // Handle Management Handle is object that holds some information for each request gone through this session. 
    // It is linked map so order is also preserved. 
    // ##################################################################################################
    
    // Request handle is created to hand over request ID to redirected so that it knows request is redirected to the next one
    // 
    
    protected SessionRequestHandle registerCurrentRequestHandleToSession(HttpServletRequest req){
        
        HttpSession session = req.getSession();
        
        Map requestHandlePool = (Map)session.getAttribute(SESSION_KEY_HANDLE_POOL);
        if (requestHandlePool == null) {
            requestHandlePool = new ConcurrentSkipListMap();
            session.setAttribute(SESSION_KEY_HANDLE_POOL, requestHandlePool);
            
            m_logger.debug("Session " + session.getId() + " created Handle Pool.");
       }
        
       SessionRequestHandle handle = new SessionRequestHandle(req); 
       requestHandlePool.put(handle.getId(), handle);

       req.setAttribute(REQ_KEY_HANDLE, handle);
       
       m_logger.debug("Reuqest Handle [" + handle.getId() + "] registered to Session " + session.getId());
       return handle;
    }
    
    //return request handle map from id 
    protected SessionRequestHandle getRequestHandleFromHttpRequest(HttpSession session, String handleId){
        
        if ( handleId == null) return null;
//        Long handleId = Long.valueOf(id);
        
        if ( session.getAttribute("k_request_handle") != null){
            
             return (SessionRequestHandle) ((Map)session.getAttribute("k_request_handle")).get(handleId);
        }
        
        return null;
    }
    
    protected boolean isInternallyForwardedRequest(HttpServletRequest request, SessionRequestHandle previousRequestHandle, SessionRequestHandle currentRequestHandle) {
        boolean internallyForwarded = false; 
        if ( isThere(request, "_reqhid")){

            if ( previousRequestHandle != null){
                
                // This is how to determine whether this is "INTERNALLY FORWARDED"....
                //if (System.currentTimeMillis() < 1000 +  previousRequestHandle.getTimestamp() && ! previousRequestHandle.isRedirectReceived() ) {
                if (previousRequestHandle.isRequestRedirected() && ! previousRequestHandle.isRedirectReceived() ) {
                    internallyForwarded = previousRequestHandle.isRequestRedirected();
                    previousRequestHandle.setRedirectReceived(true);
                    previousRequestHandle.copyKeyAttributesTo(request); // copy carry-over attributes to the current request
                } 
                
                m_logger.debug("Previous Handle Embedded: [" + previousRequestHandle.getId() + "] --------------> Current Handle[" + currentRequestHandle.getId() + "]");

            }
        }         
        
        
        return internallyForwarded;
    }
    
    
    
    //return current handle
    protected SessionRequestHandle getHandle(HttpServletRequest req){
        return (SessionRequestHandle) req.getAttribute("r_handle");
    }
    
    protected void printHandles(HttpServletRequest req){
        HttpSession session = req.getSession();
        Map<String, SessionRequestHandle>  requestMap = (Map<String, SessionRequestHandle> )session.getAttribute("k_request_handle");
        
        for (Map.Entry<String, SessionRequestHandle> entry : requestMap.entrySet()) {
            String key = (String) entry.getKey();
            SessionRequestHandle value = (SessionRequestHandle) entry.getValue();
            m_logger.debug(">>>>>>>>>>SessionRequestHandle: " + value);
        }
    }
    
    
    
    //###############################################################################################
    // Attributtes handling
    //###############################################################################################
    
    protected void carryOverAttributesFromPreviousRequest(HttpServletRequest request, Map attributes){
        
    }
    
    
    /** 
     * reset session keys that need to be reset not to interfere new requests 
     */
    protected void resetSessionViewKeys(HttpSession session) {
        session.removeAttribute("k_top_text");
        session.removeAttribute("k_top_error_text");
        session.removeAttribute("k_error_occurred");
        session.removeAttribute("k_org_uri");
        session.removeAttribute("k_org_query");
        session.removeAttribute("k_ignore_embedded_page");
        session.removeAttribute("__fwdTo");
        session.removeAttribute("k_page_name");
        session.removeAttribute("k_current_request");
        
    }
    
    protected void clearSessionKeysForLoggingIn(HttpSession session) {
        
        session.removeAttribute("k_userbo");
        session.removeAttribute("k_return_display");
        
        /*         
        session.removeAttribute("k_user_analysis");
        session.removeAttribute("k_admin_logs");
        session.removeAttribute("k_admin_logs_login");
        session.removeAttribute("kv_domains");
        session.removeAttribute("kv_keywords");
        session.removeAttribute("k_notes");

       
        Enumeration enu = session.getAttributeNames();
        
        while(enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            session.removeAttribute(name);
            DELTE BY FILTER
        }
        
*/        
    }

    // ###################### SESSION KEYS ###################################################
    
/*    
    protected void printSessionKeys(HttpSession session) {

        m_logger.debug("---- Session Keys------");
        
        Enumeration keysEnum = session.getAttributeNames();
        
        while(keysEnum.hasMoreElements()) {
            
            Object sessionObj = keysEnum.nextElement();

            String key = sessionObj.toString();
            
            if (!key.startsWith("k_request_handle")){ // This too much
                m_logger.debug("SESSION KEY :" + key + "=" + session.getAttribute(key));
            }
        }
        
        m_logger.debug("---- END Session Keys------");
    }
    
    protected void printSessionKeys(HttpServletRequest request) {

        m_logger.debug("---- Request Attributes Keys------");
        
        Enumeration keysEnum = request.getAttributeNames();
        while(keysEnum.hasMoreElements()) {
            
            Object sessionObj = keysEnum.nextElement();

            String key = sessionObj.toString();
            
            if (key.startsWith("k_") || true){
                m_logger.debug("REQUEST KEY :" + key + "=" + request.getAttribute(key));
            }
        }
        m_logger.debug("---- END REQUEST Keys------");
    }
    */

    //###############################################################################################
    // Login
    //###############################################################################################

    
    protected void processLogin(HttpSession session, UserBO userBO) {
        clearSessionKeysForLoggingIn(session);
        session.setAttribute("k_userbo", userBO);
        getSessionContext(session).setLogin(true);
        getSessionContext(session).setUsername(userBO.getUserObj().getEmail());
        
        //###### USER ANAL ######
        
        UserAnalysis analysis = (UserAnalysis)userBO.getSessionSave("k_user_analysis");

        if (analysis != null ) {
            session.setAttribute("k_user_analysis", analysis);
        } else {
            try {
                analysis = new UserAnalysis(userBO.getUserObj());
                analysis.initAllData();
                session.setAttribute("k_user_analysis", analysis);
                userBO.setSessionSave("k_user_analysis", analysis);
            }
            catch (Exception e) {
                m_logger.error("Error while creating user anal ", e);
            }
        }
    }

    //###############################################################################################
    // Page setting.
    //###############################################################################################
    
    
    protected String getPage(HttpSession session) {
        PageView pageView = (PageView) session.getAttribute("k_view_pageview");
        if (pageView == null) return "**NOT SET **";
        return pageView.getAlias();
    }

    protected String getPage(HttpServletRequest request) {
        PageView pageView = (PageView) request.getAttribute("k_view_pageview");
        if (pageView == null) return "**NOT SET **";
        return pageView.getAlias();
    }
    
    protected PageView getCurrentPageView(HttpSession session) {
        return (PageView) session.getAttribute("k_view_pageview");
    }
    protected PageView getCurrentPageView(HttpServletRequest request) {
        return (PageView) request.getAttribute("k_view_pageview");
    }
    
    protected String getSentPage(HttpServletRequest request) {
        PageView setPageView = null;
        
        // From request.
        if ( request.getParameter("fromto") != null) {
            String sentPageName = request.getParameter("fromto");
            return sentPageName;
        }

        // From Handle Chain
        SessionRequestHandle h = getHandle(request);
        if ( h != null) {
            
            SessionRequestHandle prvHandle = getRequestHandleFromHttpRequest(request.getSession(), ""+h.getPrevHandleId());
            if ( prvHandle != null && prvHandle.getPageView() != null) {
                setPageView = prvHandle.getPageView();
            }
        }

//      forgot this. seems obsolete        
//        if (setPageView == null)
//            setPageView = (PageView) request.getAttribute("k_last_pageview");
        
        if (setPageView != null)
            return setPageView.getAlias();
        
        return null;
    }
    
    // return server name for page view manager
    // Sub class should implement this if server specific alternative page needs to work. 
    protected String getServerForPage(HttpSession session){
        return null;
    }

    
    //-------------- setPage with Reuqest
    
    protected PageView setPageForward(HttpServletRequest request, String pageAlias) {
        HttpSession session = request.getSession();
        return setPage(session, null, pageAlias, false, true);
    }    
    protected PageView setPage(HttpServletRequest request, String pageAlias) {
        HttpSession session = request.getSession();
        return setPage(session, null, pageAlias, false, false);
    }
    protected PageView setPage(HttpServletRequest request, String server, String pageAlias) {
        HttpSession session = request.getSession();
        return setPage(session, server, pageAlias, false, false);
    }
    
    protected PageView setPage(HttpServletRequest request, String pageAlias, boolean nocache) {
        HttpSession session = request.getSession();
        return setPage(session, null, pageAlias, nocache, false);
    }    

    protected PageView setPage(HttpServletRequest request, String pageAlias, boolean forwardToPage, boolean isUrl) {
        return setPage(request, null, pageAlias, false, forwardToPage, isUrl);
    }    
    
    //-------------- setPage with Session
    
    
    protected PageView setPageForward(HttpSession session, String pageAlias) {
        return setPage(session, null, pageAlias, false, true);
    }    
    protected PageView setPage(HttpSession session, String pageAlias) {
        return setPage(session, null, pageAlias, false, false);
    }
    protected PageView setPage(HttpSession session, String server, String pageAlias) {
        return setPage(session, server, pageAlias, false, false);
    }
    protected PageView setPage(HttpSession session, String pageAlias, boolean forwardToPage) {
        return setPage(session, null, pageAlias, false, forwardToPage, false);
    }    
    
    protected PageView setPage(HttpSession session, String pageAlias, boolean forwardToPage, boolean isUrl) {
        return setPage(session, null, pageAlias, false, forwardToPage, isUrl);
    }    

    protected PageView setPage(HttpSession session, String server, String pageAlias, boolean nocache, boolean forwardToPage) {
        return setPage(session, null, pageAlias, false, forwardToPage, false);
    }    

    //MAIN for request
    protected PageView setPage(HttpServletRequest request, String server, String pageAlias, boolean nocache, boolean forwardToPage, boolean isUrl) {
        
        m_logger.debug("SETTING PAGE: " + pageAlias  + " forwardToPage:"+ forwardToPage + " isUrl:" + isUrl);
        HttpSession session = request.getSession();
        PageView pageView = getPageViewFrom(session, request, server, pageAlias, nocache, forwardToPage, isUrl);
        
        // From page_forward, fromto can be selected as a page. If detected, it gets page name from request.
        
        // If _FROMTO_ was set by forward configuration,
        // 1. check whether FromTo page was embedded in the request. 
        // 2. if not, figure out
        // 3. if not, set error page
        if ( pageView.getAlias().equals("_FROMTO_") && (WebParamUtil.isThere("fromto") || WebParamUtil.isThere("_fromto")) ){
            
            String fromToAlias  = request.getParameter("fromto")!=null?request.getParameter("fromto"):request.getParameter("_fromto");
            m_logger.info("FROMTO view page found, expecting request has fromto for this request " + fromToAlias);
            
            if ( fromToAlias == null) {
                
                String fromToPageFromSession = getSentPage(request);
                if ( fromToPageFromSession != null) 
                    pageView = getPageViewFrom(session, request, server, fromToPageFromSession, nocache, forwardToPage, false);
                else {
                    pageView = getPageViewFrom(session, request, server, "error_not_found", nocache, forwardToPage, false);
                    m_logger.warn("_FROMTO_ is target but cant get neither from request nor figureout. error page would be displayed");
                }
                    
                m_logger.info("FROMTO view page found, but could not get fromto value from the request : " + request.getQueryString());
            } else {
                pageView = getPageViewFrom(session, request, server, fromToAlias, nocache, forwardToPage, false);
                m_logger.info("FROMTO view page found, using from to " + pageView.getAlias());
            }
        }
        
        if (pageView != null) request.setAttribute("k_view_pageview", pageView);

        if (forwardToPage){
            m_logger.debug("SETTING PAGE: Setting FORWARD page " + pageAlias);

            if (isUrl) {
                request.setAttribute("__fwdTo", pageAlias);
            } else {
                if (pageView.isLoginRequired())
                    request.setAttribute("__fwdTo", pageView.getPageUrl());
                else
                    request.setAttribute("__fwdTo", pageView.getPageUrl());
            }
        }
        
        request.setAttribute("k_is_home", session.getAttribute("k_is_home"));
        session.setAttribute("k_view_pageview", pageView);    
        request.setAttribute("k_view_pageview", pageView);

        return pageView;
    }
    
    protected PageView setPage(HttpSession session, String server, String pageAlias, boolean nocache, boolean forwardToPage, boolean isUrl) {
        
        HttpServletRequest request = (HttpServletRequest)session.getAttribute("k_current_request");
        if ( request != null) 
            return setPage(request, server, pageAlias, nocache, forwardToPage, isUrl);
        
        return null; // TODO, what if no request??
    }
    
    
    protected PageView getPageViewFrom(HttpSession session, HttpServletRequest request, String server, String pageAlias, boolean nocache, boolean forwardToPage, boolean isUrl) {

        m_logger.debug("SETTING PAGE2: pageAlias=" + pageAlias + ", server=" + server);
        
        SessionContext sessionContext = (SessionContext)session.getAttribute(SessionContext.getSessionKey());
        PageView errorPageView = getViewManager(sessionContext).getPageView("error", server);
        PageView pageView = null;
        
        if (pageAlias == null || pageAlias.equals("")) {
            m_logger.debug("SETTING PAGE2:*ERROR*: pageAlias is Null");
            return errorPageView;
        }
        
        if ( !isUrl) {
            if (server  == null ) {
                pageView = getViewManager(sessionContext).getPageView(pageAlias, getServerForPage(session));
            } else {
                pageView = getViewManager(sessionContext).getPageView(pageAlias, server);
            }
            
            if( pageView == null ) {
                m_logger.warn("SETTING PAGE2:*ERROR*: PageView not found for " + pageAlias + " Error page view was set. " + errorPageView);
                return errorPageView;
            }
            m_logger.debug("SETTING PAGE2: alias: [" + pageAlias + "], pageUrl=" + pageView.getPageUrl() + ", login=" + pageView.isLoginRequired() + ", internalFwd="+forwardToPage );
            
        } else {
            m_logger.debug("SETTING PAGE2: url: [" + pageAlias + "] ,pageUrl=" +pageAlias + ", login=false" + ", internalFwd="+forwardToPage );
            
        }
        
        if (pageAlias.equals("home")){
            session.setAttribute("k_is_home", "true");
            m_logger.debug("Setting is_home to TRUE");
            
        } else {
            session.setAttribute("k_is_home", "false");
            if (pageView != null && pageView.isLoginRequired()) {
                if(!sessionContext.isLogin()) {
                    PageView loginPageView = getViewManager(sessionContext).getPageView("login_form", server);
                    m_logger.warn("SETTING PAGE2: login requried " + pageView + " but not logged in so forward to " + loginPageView);
                    
                    if ( loginPageView == null ) {
                        new Exception("Login page view is not defined").printStackTrace();
                    }
                    
                    session.setAttribute("k_view_pageview", loginPageView);
                    return loginPageView;
                }
            }
        }

        // Actually, I don't what this is for. As of 10/12/16. but if removed, there is error in one of jsp page. so keep it
        if (session.getAttribute("k_keepview")!= null && session.getAttribute("k_keepview").equals("true")) {
            m_logger.debug("Requested to keep the preview view");
            
        } else {
            m_logger.info("PageView =" + pageView);
            if (pageView != null) session.setAttribute("k_view_pageview", pageView);
        }

        // if forwardToPage
//        if (forwardToPage){
//            m_logger.debug("SETTING PAGE2: Setting FORWARD page " + pageAlias);
//
//            if (isUrl) {
//                session.setAttribute("__fwdTo", pageAlias);
//            } else {
//                if (pageView.isLoginRequired())
//                    session.setAttribute("__fwdTo", pageView.getPageUrl());
//                else
//                    session.setAttribute("__fwdTo", pageView.getPageUrl());
//            }
//        }
        
        return pageView;
    }  
        
    
    
    //Main for session
    /* Original 
    protected PageView setPage(HttpSession session, String server, String pageAlias, boolean nocache, boolean forwardToPage, boolean isUrl) {
        
        HttpServletRequest request = (HttpServletRequest)session.getAttribute("k_current_request");
        if ( request != null) 
            setPage(request, server, pageAlias, nocache, forwardToPage, isUrl);
        
        SessionContext sessionContext = (SessionContext)session.getAttribute(SessionContext.getSessionKey());
        m_logger.debug("SETTING PAGE2: pageAlias=" + pageAlias + ", server=" + server);
        PageView pageView = null;
        
        if (pageAlias == null || pageAlias.equals("")) {
            m_logger.debug("SETTING PAGE2:*ERROR*: pageAlias is Null");
            return null;
        }
        
        if ( !isUrl) {
            if (server  == null ) {
                pageView = getViewManager(sessionContext).getPageView(pageAlias, getServerForPage(session));
            } else {
                pageView = getViewManager(sessionContext).getPageView(pageAlias, server);
            }
        }
        
        if( !isUrl && pageView == null ) {
            PageView errorPageView = getViewManager(sessionContext).getPageView("error", server);
            session.setAttribute("k_view_pageview", errorPageView);
            m_logger.warn("SETTING PAGE2:*ERROR*: PageView not found for " + pageAlias + " Error page view was set. " + errorPageView);
        	return null;
        }

        if (pageView != null)
            m_logger.debug("SETTING PAGE2: alias: [" + pageAlias + "], pageUrl=" + pageView.getPageUrl() + ", login=" + pageView.isLoginRequired() + ", internalFwd="+forwardToPage );
        else
            m_logger.debug("SETTING PAGE2: url: [" + pageAlias + "] ,pageUrl=" +pageAlias + ", login=false" + ", internalFwd="+forwardToPage );
        
        if (pageAlias.equals("home")){
            session.setAttribute("k_is_home", "true");
            m_logger.debug("Setting is_home to TRUE");
        } else {
            session.setAttribute("k_is_home", "false");
            if (pageView != null && pageView.isLoginRequired()) {
                SessionContext sessCtx = getSessionContext(session);
                if(!sessCtx.isLogin()) {
                    PageView loginPageView = getViewManager(sessionContext).getPageView("login_form", server);
                    m_logger.warn("SETTING PAGE2: login requried " + pageView + " but not logged in so forward to " + loginPageView);
                    
                    if ( loginPageView == null ) {
                        new Exception("Login page view is not defined").printStackTrace();
                    }
                    
                    session.setAttribute("k_view_pageview", loginPageView);
                    return loginPageView;
                }
            }
        }

        // Actually, I don't what this is for. As of 10/12/16. but if removed, there is error in one of jsp page. so keep it
        if (session.getAttribute("k_keepview")!= null && session.getAttribute("k_keepview").equals("true")) {
            m_logger.debug("Requested to keep the preview view");
            
        } else {
            m_logger.info("PageView =" + pageView);
            if (pageView != null) session.setAttribute("k_view_pageview", pageView);
        }

        // if forwardToPage
        if (forwardToPage){
            m_logger.debug("SETTING PAGE2: Setting FORWARD page " + pageAlias);

            if (isUrl) {
                session.setAttribute("__fwdTo", pageAlias);
            } else {
                if (pageView.isLoginRequired())
                    session.setAttribute("__fwdTo", pageView.getPageUrl());
                else
                    session.setAttribute("__fwdTo", pageView.getPageUrl());
            }
        }
        
        return pageView;
    }  
    
    
    */
    
    protected void setSession(HttpSession session, String key, Object obj) {
        if( obj != null) 
            session.setAttribute(key, obj);
    }    

    protected SessionContext getSessionContextByIdFromRequest(HttpServletRequest request) {
        throw new NotImplementedException("Not implemented in this class. Sub class should implement this");
    }
    
    protected SessionContext getSessionContext(HttpSession session)
    {
    	SessionContext ret = (SessionContext) session.getAttribute(SessionContext.getSessionKey());
        if ( ret == null)
        {
            ret = SessionContext.create(session);
            session.setAttribute(SessionContext.getSessionKey(), ret);
            m_logger.debug(">>>> Creating New SessionContext " + ret.getSerial());
        } else
        {
            m_logger.debug("SessionContext found. created at " + ret.getCreatedTime() + " last=" + new Date(ret.getLastAccess())  + ",user=" + ret.getUsername());
        }

        return ret;
    }

    protected CtmSessionManager getSessionManager(String id, boolean login)
    {
        m_logger.debug("Retriving Session manager for " + id);
    	CtmSessionManager ret;
    	
    	ret = (CtmSessionManager )CtmSessionManagerPool.getInstance().get(id);
    	if (ret == null)
    	{
    		ret = new CtmSessionManager(id);
    		CtmSessionManagerPool.getInstance().put(id, ret);
    	}
    	ret.getSessionContext().setLogin(login);
    	return ret;
    }

    
    protected ViewManager getViewManager(HttpSession session){
        return getViewManager((SessionContext)session.getAttribute(SessionContext.getSessionKey()));
    }

    protected ViewManager getViewManager(SessionContext sessionContext){
        
        if ( sessionContext == null) return m_viewManager;
        
        switch(sessionContext.getSessionType()){
        case SessionContext.SESSION_TYPE_MOBILE_PHONE:
            return m_viewManagerMobile;
        default:
            return m_viewManager;
        }
    }

    // By default, it is false. each action should override.
    public boolean isPagelessSessionOnly(){
        return false;
    }
    
    protected boolean loginRequired()
    {
        return false;
    }

    protected void printCookieValue(Cookie[] cookies)
    {
        if (cookies == null)
            return;
        for (int i = 0; i < cookies.length; i++)
        {
            Cookie cookie = cookies[i];
            StringBuffer buf = new StringBuffer();
            buf.append("COOKIE=");
            buf.append("domain=" + cookie.getDomain());
            buf.append("|name=" + cookie.getName());
            buf.append("|value=" + cookie.getValue());
            buf.append("|path=" + cookie.getPath());
            buf.append("|secure=" + cookie.getSecure());
            m_logger.debug(buf.toString());
            m_logger.debug("|version=" + cookie.getVersion());
        }
    }

    protected String getCookieValue(Cookie[] cookies, String name, String value)
    {
        if (cookies == null || name == null)
            return value;
        for (int i = 0; i < cookies.length; i++)
        {
            Cookie cookie = cookies[i];
            if (cookie.getName().equals(name))
            {
                return cookie.getValue();
            }
        }

        return value;
    }

    protected Site findSessionBoundSite(HttpServletRequest request){

        HttpSession session = request.getSession();
        
        Site existingSiteInSession = (Site) session.getAttribute("k_site");

        if (existingSiteInSession != null)
            return existingSiteInSession;
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        return site;
    }
    
    
    //##################################################################################
    // Request summary is an object contains summarized flow of a single request.  
    
//    protected void createNewRequestSummary

    
    //##################################################################################

    protected boolean isSuperAdmin(HttpSession session){
        return false;
    }
    
    
    //#################################################################################################################################
    // Request Param Checks
    //#################################################################################################################################
    
    protected boolean isThere(HttpServletRequest request, String prop) {
        String propValue = request.getParameter(prop);
        if ( propValue != null && !propValue.trim().equals(""))
            return true;
        return false;
     }

    
    protected boolean hasRequestValue(HttpServletRequest request, String prop, String checkValue) {
        String propValue = request.getParameter(prop);
        return equalsTo(propValue, checkValue, true);
    }

    protected boolean hasRequestAttribute(HttpServletRequest request, String prop, String checkValue) {
        String propValue = (String) request.getAttribute(prop);
        return equalsTo(propValue, checkValue, true);
    }

    protected String getRequestParam(HttpServletRequest request, String... params) {
        
        for(String s : params) {
            if (isThere(request, s))
                return request.getParameter(s);
        }
        return null;
    }
    
    protected boolean isAllThere(HttpServletRequest request, String... params) {
        
        for(String s : params) {
            if (!isThere(request, s))
                return false;
        }
        return true;
    }
    
    
    protected boolean isAjaxRequest(HttpServletRequest request){
        boolean isAjax = hasRequestValue(request, "ajaxRequest", "true") || 
                         hasRequestValue(request, "ajx", "true") ||
                         isThere(request, "ajxr");
        return isAjax;
    }
    
    // Mobile??
    
    protected boolean isPagelessSession(HttpServletRequest request){
        SessionContext sessionContextByRequest = getSessionContextByIdFromRequest(request);
        String deviceId = request.getParameter("deviceId");
        return !StringUtils.isBlank(deviceId) && sessionContextByRequest != null;
    }
    
    
    protected boolean isJSONResponse(HttpServletRequest request){
        
        return hasRequestValue(request, "ajaxOut", "getjson") || 
        hasRequestValue(request, "ajaxOut", "getlistjson") ||
        hasRequestValue(request, "ajxr", "getjson") || 
        hasRequestValue(request, "ajxr", "getlistjsonsynch") || 
        hasRequestValue(request, "ajxr", "getjsonsynch") || 
        hasRequestValue(request, "ajxr", "getctxidjson") || 
        hasRequestValue(request, "ajxr", "getlistjson") ||
        (isThere(request, "ajxr") && request.getParameter("ajxr").startsWith("getjson"));
    }
    

    protected boolean isActionCmd(HttpServletRequest request){
        return isActionBasicCmd(request);
    }
    
    
    protected boolean isActionBasicCmd(HttpServletRequest request){
         return isActionCmdAdd(request) || isActionCmdEdit(request) || isActionCmdDelete(request)|| isActionCmdEditfield(request);
    }

    protected boolean isActionCmdAdd(HttpServletRequest request){
        return hasRequestValue(request, "add", "true");
    }
    protected boolean isActionCmdEdit(HttpServletRequest request){
        return hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed");
    }
    protected boolean isActionCmdDelete(HttpServletRequest request){
        return hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del");
    }
    protected boolean isActionCmdEditfield(HttpServletRequest request){
        return hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef");
    }
    
    protected boolean isActionCmdChangedData(HttpServletRequest request){
        return isActionCmdAdd(request)|| isActionCmdEdit(request)|| isActionCmdDelete(request)|| isActionCmdEditfield(request);
    }    
    
    protected String getActionCmd(HttpServletRequest request){
        
        if (isActionCmdAdd(request))return "add";
        if (isActionCmdEdit(request))return "edit";
        if (isActionCmdDelete(request))return "delete";
        if (isActionCmdEditfield(request))return "editfield";

        if ( isThere(request, "cmd"))
            return request.getParameter("cmd");

        return "";
    }
    
    
    
    //#################################################################################################################################
    // Util
    //#################################################################################################################################
    
    protected boolean isMissing(String val) {
        if ( val == null || val.trim().length() == 0) return true;
        return false;
     }

     protected boolean isThere(String val) {
         if ( val == null || val.trim().length() == 0) return false;
         return true;
      }
     
     protected boolean equalsTo(String val, int val2) {
         return equalsTo(val, ""+val2, false);
     }    
     protected boolean equalsTo(String val, int val2, int val3) {
         if ( !equalsTo(val, val2) ) return false;
         if ( !equalsTo(val, val3) ) return false;
         return true;
     }    
     
     protected boolean equalsTo(String val, String val2) {
         return equalsTo(val, val2, false);
     }

     protected boolean equalsTo(String val, String val2, String val3, boolean ignoreCase) {
         if ( !equalsTo(val, val2, ignoreCase) ) return false;
         if ( !equalsTo(val, val3, ignoreCase) ) return false;
         return true;
     }
     
     
     protected boolean equalsTo(String val, String val2, boolean ignoreCase) {
         
         if ( val == null || val2 == null) 
             return false;
         
         if (ignoreCase) {
             if ( val != null && val.trim().equalsIgnoreCase(val2)) {
                 return true;
             }
         } else {
             if ( val != null && val.trim().equals(val2)) {
                 return true;
             }
         }        
         return false;
     }

    
    
    //#################################################################################################################################
    // Session Text
    //#################################################################################################################################
    
    protected void sessionErrorText(HttpSession session, String obj) {
        session.setAttribute("k_top_error_text", obj);
        sessionErrorFlag(session);
    }
    
    protected void sessionErrorFlag(HttpSession session) {
        m_logger.debug("Error Flag has been set.");
        session.setAttribute("k_error_occurred", "true");
    }

    protected void sessionErrorFlagOff(HttpSession session) {
        m_logger.debug("Error Flag has been set.");
        session.removeAttribute("k_error_occurred");
    }
    
    protected boolean isSessionErrorFlag(HttpSession session) {
        return WebUtil.isTrue((String)session.getAttribute("k_error_occurred"));
    }
    protected boolean isIgnoreEmbeddedPage(HttpSession session) {
        return WebUtil.isTrue((String)session.getAttribute("k_ignore_embedded_page"));
    }
    protected void sessionTopText(HttpSession session, String obj) {
        session.setAttribute("k_top_text", obj);
    }

    protected void sessionWarnText(HttpSession session, String obj) {
        session.setAttribute("k_top_warn_text", obj);
    }
    
    protected String getUsername(HttpSession session) {
        SessionContext sessCtx = getSessionContext(session);
        return sessCtx.getUsername();
    }

    protected void copyPreserveParams(HttpServletRequest request, HttpSession session) {
        
        Enumeration names = request.getParameterNames();
        while(names.hasMoreElements()){
            String name = (String) names.nextElement();
            
            if (name.startsWith("prv_")){
                String newName = name.substring(4);
                String val = request.getParameter(name);
                if (val != null) {
                    session.setAttribute(newName, val);
                    m_logger.debug("Copying Preserved param: " + name + "=" + val + " to " + newName);
                }
            }
        }
    }
    
    protected void copyToPreserveParams(String paramString, HttpSession session) {
        
        Map reservedParams = (Map)session.getAttribute("k_reserved_params");
        if ( reservedParams == null){
            reservedParams = new HashMap();
            session.setAttribute("k_reserved_params", reservedParams);
        }

        StringTokenizer tokenizer = new StringTokenizer(paramString, "&");
        while(tokenizer.hasMoreTokens()){
            String tok = tokenizer.nextToken();
            
            String nvp[] = tok.split("=");
            if (nvp.length == 2){
                reservedParams.put(nvp[0], nvp[1]);
            }
        }
    }    
    
    //#################################################################################################################################
    
    private ActionInsert getActionInsert() {
        try {
            m_logger.info("----------------------------------------------------");
            Class theClass  = Class.forName(PropertiesLoader.getInstance().getProperty("app.action.insert.class","com.jtrend.struts.core.DefaultActionInsert"));
            m_logger.info("Creating action insert with " + theClass.getName());
            ActionInsert actionInsert = (ActionInsert)theClass.newInstance();
            m_logger.info("----------------------------------------------------");

            return actionInsert;
            
        } catch (Exception e) {
            m_logger.error("ERROR while init App", e);
            return null;
        }
    }
    
    //#################################################################################################################################
    // 
    //#################################################################################################################################
    
    protected String getWebProcessId(HttpServletRequest request) {
        return(String) request.getParameter("wpid");
    }
    
    protected WebProcess checkWebProcess(HttpServletRequest request, HttpSession session) throws Exception{

        String id = getWebProcessId(request);
        
        WebProcess webProc = WebProcManager.getWebProcess(id);
        if (webProc == null ) {
            //sessionErrorText(session, "Internal error occurred.");
            m_logger.warn("Failed. WebProcess not found for " + id);
            throw new Exception("Web process not found for " + id);
        }
        
        if (webProc.isCompleted() || webProc.isExpired() || webProc.isClosed() ) {
            //sessionErrorText(session, "Internal error occurred.");
            m_logger.warn("Failed. WebProcess closed or expired. Aborted. id " + id + ",completed=" + webProc.isCompleted() + ",expired=" + webProc.isExpired()+ ",closed=" + webProc.isClosed()); 
            throw new Exception("Failed. WebProcess closed or expired. Aborted. id " + id + ",completed=" + webProc.isCompleted() + ",expired=" + webProc.isExpired()+ ",closed=" + webProc.isClosed());
        }
        m_logger.debug("WebProc " + id + " good. created at " + new Date(webProc.getCreatedTimestamp()));
    
        return webProc;
    }
    
    //#################################################################################################################################
    // ActionForm update
    //#################################################################################################################################
    
    
    protected void updateActionFormWithFollowupForums(ActionForm form, Map requestParams){
        
    }
    
} // END JTrendAction

class Lifeline extends Thread{
    
    static Logger m_logger = Logger.getLogger(Lifeline.class);
    
    public Lifeline() {
        super("LIFELNE");
    }
    public void run() {
        
        
        while(true) {
            
            try {
                UserDAO dao = new UserDAO();
                List list =dao.findByEmail("ss@ss.com");
                m_logger.debug("User returned " + list);
                
                Thread.sleep(60000);
            }catch(Exception e) {
                m_logger.error(e);
            }
        }
    }
}
