package com.autosite.struts.action.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.Page;
import com.autosite.db.Site;
import com.autosite.db.SiteConfig;
import com.autosite.db.SynkNamespaceRecord;
import com.autosite.ds.AbstractDS;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.ds.SiteConfigDS;
import com.autosite.ds.SiteDS;
import com.autosite.holder.AbstractDataHolder;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.AutositeSessionContextUtil;
import com.autosite.session.ConfirmRegisterManager;
import com.autosite.session.ConfirmTo;
import com.autosite.session.storable.AutositeSessionObjectStore;
import com.autosite.session.storable.DefaultSessionObjectStore;
import com.autosite.struts.action.ActionExtentException;
import com.autosite.struts.action.AutositeActionService;
import com.autosite.synch.SynkHandlerFactory;
import com.autosite.synch.SynkModHandler;
import com.autosite.synch.SynkServerActionOnRecord;
import com.autosite.synch.SynkUpdateHandler;
import com.autosite.synch.impl.SynkHandlerImpl;
import com.autosite.synch.impl.SynkHanlderFactoryImpl;
import com.autosite.util.DynPageUtil;
import com.autosite.util.JsonUtil;
import com.autosite.util.access.AccessDef;
import com.autosite.util.access.AccessDef.SystemRole;
import com.jtrend.session.SessionStorable;
import com.jtrend.session.StringStorable;
import com.jtrend.stats.StatData;
import com.jtrend.struts.core.DefaultPageForwardManager;
import com.jtrend.util.JtStringUtil;
import com.jtrend.util.RequestUtil;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;
import com.seox.util.PropertiesLoader;

abstract public class AutositeCoreAction extends AutositeSynchAction implements AutositeActionService {
    
    
    protected SynkHandlerFactory synkHandlerFactory;
    protected boolean synkRequired;
    protected boolean enforceSingleRecordPerSite;
    
    
    public AutositeCoreAction() {
        super();
        
        synkHandlerFactory = SynkHanlderFactoryImpl.getInstance(); 
        
        PropertiesLoader synProperties = PropertiesLoader.getInstance("conf/synk.properties");
        String groupList = synProperties.getProperty("synk.action_group");
        String actionList = synProperties.getProperty("synk.action_group");
        
        Set groupSet = new HashSet(Arrays.asList(StringUtils.split(groupList,',')));
        Set actionSet = new HashSet(Arrays.asList(StringUtils.split(actionList,',')));

        synkRequired = groupSet.contains(getActionGroupName()) ||groupSet.contains(getActionName());
        m_logger.info("SYNK REQUIRED: Action(" +getActionGroupName()+"/" + getActionName() + ") " + synkRequired );
        
        PropertiesLoader actionProperties = PropertiesLoader.getInstance("conf/action_configs.properties");
        enforceSingleRecordPerSite = BooleanUtils.toBoolean(actionProperties.getProperty(getActionName() + ".enforceSingleRecordPerSite"));
        m_logger.info(getActionName() +" : enforceSingleRecordPerSite=" + enforceSingleRecordPerSite);
        
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
    {
        HttpSession session = request.getSession();
  
        m_logger.info("\n\n\n");
        m_logger.info("##################################################################################################################");
        m_logger.info("#################################### START #######################################################################");
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

        if (session.getAttribute("k_site") == null) {
            session.setAttribute("k_site", site);
        }

        m_logger.info("### SOURCE : " +  request.getRemoteAddr() + "->" + request.getServerName());
        m_logger.info("### TARGET : " + site.getSiteUrl() + " session=" + session.getId());
        m_logger.info("##################################################################################################################");
        m_logger.info("\n");
        
        String keywords = request.getServerName();
        String meta = "";
        String googleTrack = "";
        
        if (siteConfig !=null) {    
            keywords += "," + siteConfig.getKeywords();
            meta = siteConfig.getMeta();
            googleTrack = siteConfig.getSiteTrackGoogle();
        }
        
        session.setAttribute("k_site_config_keywords", keywords);
        session.setAttribute("k_site_config_meta", meta);
        session.setAttribute("k_site_config_google_track", googleTrack);
        
        return super.execute(mapping, form, request, response);
    }    
    
    //==================================================================================================================================
    // ShortCut getter/setter for session keys/values
    //==================================================================================================================================
    
    protected void resetSessionViewKeys (HttpSession session ) {
        super.resetSessionViewKeys(session);
        session.removeAttribute("k_pollId");
        session.removeAttribute("k_meta_headers");
        session.removeAttribute("k_meta_headers_http_equiv");
        session.removeAttribute("k_page_content");
    }
    

    protected void setSessionCurrentPageId(HttpSession session, String id) {
        session.setAttribute("k_current_dyn_page", id);
    }
    
    protected String getStringUnderlyingPage(HttpServletRequest request){
        
        Page page = getCurrentPage(request);
        
        if ( page == null || page.getUnderlyingPage() == null || page.getUnderlyingPage().equals("")) 
            return "dynamic_menu_content";
        
        return page.getUnderlyingPage();
    }
   
    protected String getServerForPage(HttpSession session){
        Site site = (Site) session.getAttribute("k_site");
        m_logger.info("#################### return site for page " + site.getSiteUrl());
        return site.getSiteUrl();
    }

    protected Page getCurrentPage(HttpServletRequest request) {

        return DynPageUtil.getCurrentPage(request);
        
/*        HttpSession session = request.getSession();
        
        long dynPageId = WebParamUtil.getLongValue( (String) session.getAttribute("k_current_dyn_page"));

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        Page dynPage = PageDS.getInstance().getById(dynPageId);
        
        // if null for the page ID, return home dyn page.
        if (dynPage == null || dynPage.invalid() ) {
            dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), "XHOME");
        }

        return dynPage; 
*/    }

    
    //==================================================================================================================================
    // Object Session Store
    //==================================================================================================================================

    // By default it returns action group name
    protected String getSessionStorableGroup() {
        return getActionGroupName();
    }
    
    protected AutositeSessionObjectStore createNewSessionStoreInstance(String name ){
        return new DefaultSessionObjectStore(name);
    }
    
    public void addSessionStorable(HttpSession session, String storableGroup, String storableName, String storable){
        AutositeSessionObjectStore sessionObjectStore = (DefaultSessionObjectStore)session.getAttribute("_OBJECT_STORE_"+storableGroup);
        if ( sessionObjectStore == null ){
            sessionObjectStore = createNewSessionStoreInstance(storableName);
            session.setAttribute("_OBJECT_STORE_"+storableGroup,sessionObjectStore );
        }
        sessionObjectStore.addStorable(storableName, new StringStorable(storable));
    }
    
    public void addSessionStorable(HttpSession session, String storableGroup, String storableName, SessionStorable storable){
        AutositeSessionObjectStore sessionObjectStore = (DefaultSessionObjectStore)session.getAttribute("_OBJECT_STORE_"+storableGroup);
        if ( sessionObjectStore == null ){
            sessionObjectStore = createNewSessionStoreInstance(storableName);
            session.setAttribute("_OBJECT_STORE_"+storableGroup,sessionObjectStore );
        }
        sessionObjectStore.addStorable(storableName, storable);
    }
    
    public void updateSessionStorable(HttpSession session, String storableGroup, String storableName, SessionStorable storable){
        DefaultSessionObjectStore sessionObjectStore = (DefaultSessionObjectStore)session.getAttribute("_OBJECT_STORE_"+storableGroup);
        if ( sessionObjectStore == null ){
            addSessionStorable(session, storableGroup, storableName, storable);
            return;
        }
        sessionObjectStore.removeStorable(storableName);
        sessionObjectStore.addStorable(storableName, storable);
    }
    
    public SessionStorable getSessionStorable(HttpSession session, String storableGroup, String storableName){
        DefaultSessionObjectStore sessionObjectStore = (DefaultSessionObjectStore)session.getAttribute("_OBJECT_STORE_"+storableGroup);
        if ( sessionObjectStore == null ){
            return null;
        }
        return (SessionStorable)sessionObjectStore.getStorable(storableName);
    }
    
    public void removeSessionStorable(HttpSession session, String storableGroup, String storableName){
        DefaultSessionObjectStore sessionObjectStore = (DefaultSessionObjectStore)session.getAttribute("_OBJECT_STORE_"+storableGroup);
        if ( sessionObjectStore == null ){
            return;
        }
        sessionObjectStore.removeStorable(storableName);
    }
    
    //==================================================================================================================================
    // Object getter
    //==================================================================================================================================

    protected Site getSite(HttpServletRequest request){
        return SiteDS.getInstance().registerSite(request.getServerName());
    }
    
//    protected Site get(HttpServletRequest request){
//        return SiteDS.getInstance().registerSite(request.getServerName());
//    }

    //==================================================================================================================================
    // Confirm TO process
    //==================================================================================================================================
   /* 
    * 1. Defined by confirm required action
    * 
    * 2. If it is confirm required action, the action creats confirmToObject that holds the request information. then
    * return the key to find that object and reutnr page is confirmation return page. 
    * 3. user confirms will send confirmToKey 
    * 4. Then this action will converts the confirmTo objec to request and forward the page.
    *  
    */
    protected boolean forwardConfirmTo(HttpServletRequest request) {
        HttpSession session = request.getSession();
    
        String confirmToKey = request.getParameter("confTo");
    
        m_logger.debug("ConfirmTo Key=" + confirmToKey);
        if ( ConfirmRegisterManager.getInstance().find(session.getId(),confirmToKey) != null &&  ConfirmRegisterManager.getInstance().find(session.getId(), confirmToKey).confirmed() ){
            m_logger.debug("ConfirmTo has been confirmed. Will proceed");
            return false;
        } else {
            String paramStr = RequestUtil.getParameterString(request, "&");
            ConfirmTo newConfirmTo = ConfirmRegisterManager.getInstance().registerNew(session.getId(), request.getRequestURI(), paramStr);
            
            Map params = RequestUtil.getParameters(request);
            newConfirmTo.setExtParams(params); //Save params 1
            
            request.setAttribute("confTo", newConfirmTo);
            setPage(session, "confirm_required");
            session.setAttribute("k_ignore_embedded_page", "true");
            return true;
        }
    }

    // == page
//    protected String getConfirmPage()        {return "confirm_required";}

    protected String getPageByConfig(String config ) {
        return getActionExtent().getPageByConfig(config);
    }

    // Action Group, if supported is unique ID set by AutoGen to represent the group of actions
    // Action group name can be set in creation level by AutoGen
    // This action group name can be used for ...
    // 1. session storables. By default, getSessionStorableGroup() returns action group name
    // 2. Anything that need to be done for the group of actions or features or app level, this can be used.
    // Action group and session Storable are different in scope. 
    // Session Storable are mostly same as action group but session storable is designed to cover across the groups. 
    
    //==================================================================================================================================
    // 
    //==================================================================================================================================

    public boolean filter(HttpServletRequest request, StatData statData) {
        if ( request.getServletPath() != null && 
             ( request.getServletPath().indexOf("login_form") >= 0  || request.getServletPath().indexOf("register_simple_add") >= 0 ) &&
             isThere(request, "test")) {
            
            m_logger.info("Drop auto linked");
            return true; 
        }
        
        return super.filter(request, statData);
    }    

    protected void processPageForAction(HttpServletRequest request, String cmd, String scopeOfResult, String defaulPage){
        processPageForAction(request, cmd, scopeOfResult, defaulPage, null);
    }
    
    protected void processPageForAction(HttpServletRequest request, String cmd, String scopeOfResult, String defaulPage, String sentPage){
        DefaultPageForwardManager pageManager = DefaultPageForwardManager.getInstance(getSite(request).getSiteUrl());
        setPage(request,  
                pageManager.getPageForwardToByCommand(getActionName(), cmd, scopeOfResult, sentPage!= null? sentPage: defaulPage),
                pageManager.isInternalForward(getActionName(), cmd, scopeOfResult), 
                pageManager.isPageUrl(getActionName(), cmd, scopeOfResult));

    }
    
    //==================================================================================================================================
    // AJAX related 
    //==================================================================================================================================

    protected String getAjaxSubCommand(HttpServletRequest request, String subType ){
        return request.getParameter("ajxr-" + subType);
    }
    

    //==================================================================================================================================
    // JSON
    //==================================================================================================================================
    
    protected JSONObject decorate(JSONObject json){
        if ( json == null) return null;
        
        json.put("version" , "1.0");
        json.put("timestamp" , new Long(System.currentTimeMillis()));
        json.put("type", getActionName());
        
        return json;
                
    }
    
  //######################################################################################################################################################
  // Copied from Base
  //######################################################################################################################################################
  //######################################################################################################################################################
  //######################################################################################################################################################
  //######################################################################################################################################################
  //######################################################################################################################################################
  //######################################################################################################################################################
  //######################################################################################################################################################
  //######################################################################################################################################################
  //######################################################################################################################################################
    

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException) throws Exception {
        try {
            return ex(mapping, form, request, response, throwException, null);
        }
        catch (Exception e) {
            //TODO Need set page in case not caught under neath. that is more safe
            m_logger.error("",e);
            throw e;
        }
    }
    
    
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException, Map returnObjects) throws Exception {

        ActionForm _GenMainForm = (ActionForm) form;
        HttpSession session = request.getSession();
        DefaultPageForwardManager pageManager = DefaultPageForwardManager.getInstance();

        String sentPage = getSentPage(request);
        //setPage(request, sentPage); //TODO changed all setPage to  processPageForAction() in other places. dont know what to do here
        setPage(request, null, sentPage, false, false, false);
        
        
        Site site = getSite(request);
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            return mapping.findForward("default");
        }

        //===================================================================================================================
        // Check if needs confirmTo step
        if ( !isAjaxRequest(request) && (
            hasRequestValue(request, "XXXXXXXXXXX", "true"))) // This line is just added for template. if you dont see any command above, add command in template field
        {    
            if (forwardConfirmTo(request))
                return mapping.findForward("default");
        
        }


        //===================================================================================================================
        // Check the user has permission to run this action 
        if (!haveAccessToUpdate(session) ){
            sessionErrorText(session, "Permission error occurred.");
            processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));

            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }

        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            if (throwException) throw e;
            sessionErrorText(session, "Internal error occurred.");

            //Default error page but will be overridden by exception specific error page
            processPageForAction(request, "general", "error", getErrorPage(), getSentPage(request));

            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        //===================================================================================================================
        // Find object if parameter has "id" field
        AutositeDataObject autositeDataObject = null;
        long cid = (long)0;
        if (!isActionCmdAdd(request) && isThere(request, "id")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            autositeDataObject = (AutositeDataObject) m_ds.getById(cid);
        }



        boolean addCommandRoutedToEdit = false;

        // added by enforceSingleRecordPerSite switch in AutoGen
        // This site enforces the sing record per site. If there is one m
        if ( enforceSingleRecordPerSite && isActionCmdAdd(request)) {
            List recordsForTheSite  = m_ds.getBySiteId(site.getId());
            if ( recordsForTheSite.size() > 0) { //This should be counted as only 1. If not something not right.
                if ( recordsForTheSite.size() > 1) {
                    m_logger.warn("There must only single record per site for CleanerPickupDeliveryConfig. But " + recordsForTheSite.size() + " records found");
                }
    
                autositeDataObject = (AutositeDataObject)recordsForTheSite.get(0);
                m_logger.debug("Add command received but CleanerPickupDeliveryConfig enforces single record per site. So existing record will be updated id ");
                addCommandRoutedToEdit = true;
            }        
        }             

        //================== EDIT =====================================================================================
        if (isActionCmdEdit(request) || addCommandRoutedToEdit) {
            if (!haveAccessToCommand(session,getActionCmd(request))){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //GenMain _GenMain = m_ds.getById(cid);

            if (autositeDataObject == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            }

            if (autositeDataObject.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + autositeDataObject.getSiteId()); 
                //Default error page but will be overridden by exception specific error page
                setPage(session, 
                        pageManager.getPageForwardToByCommand(getActionName(), "edit", "error", getAfterEditPage()), 
                        pageManager.isInternalForward(getActionName(), "error", "success"));
                return mapping.findForward("default");
            }


            try {
                checkDepedenceIntegrity(autositeDataObject);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

            try{
                if ( _GenMainForm == null) {
                    editField(request, response, autositeDataObject);
                } else {
                    edit(request, response, _GenMainForm, autositeDataObject);
                }
                if (returnObjects != null) returnObjects.put("target", autositeDataObject);
            }

            catch (Exception e) {

                //In case database fails, cache and db should be in synch. getById(Long, boolean) could do the job
                AutositeDataObject o = (AutositeDataObject)m_ds.getById( autositeDataObject.getId(), true);

                m_logger.error("Error occurred", e);
                if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session, ((ActionExtentException)e).getForwardPage(), 
                            pageManager.isInternalForward(getActionName(), "edit", "error"));
                return mapping.findForward("default");
            }

            //Default error page but will be overridden by exception specific error page
            processPageForAction(request, "edit", "success", getAfterEditPage());

            //return mapping.findForward("default");
    
        // ================== EDIT FIELD =====================================================================================
        } else if (isActionCmdEditfield(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //GenMain _GenMain = m_ds.getById(cid);

            if (autositeDataObject == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (autositeDataObject.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + autositeDataObject.getSiteId()); 

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "editfield", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }


            try{
                editField(request, response, autositeDataObject);
                if (returnObjects != null) returnObjects.put("target", autositeDataObject);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                if (throwException) throw e;
                sessionErrorText(session, "Internal error occurred.");

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "edit", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "editfield", "error"));
                return mapping.findForward("default");
            }

            processPageForAction(request, "editfield", "success", getAfterEditPage());
            //return mapping.findForward("default");

        // ================== DEL =====================================================================================
        } else if (isActionCmdDelete(request)) {
            if (!haveAccessToCommand(session, getActionCmd(request) ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //GenMain _GenMain = m_ds.getById(cid);

            if (autositeDataObject == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }

            if (autositeDataObject.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + autositeDataObject.getSiteId()); 

                //setPage(session, getErrorPage());
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                return mapping.findForward("default");
            }



            try {
                m_actionExtent.beforeDelete(request, response, autositeDataObject);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "delete", "error"));

                return mapping.findForward("default");
            }

            m_ds.delete(autositeDataObject); //TODO exception handling
            if (returnObjects != null) returnObjects.put("target", autositeDataObject);
            //setPageForward(session, getAfterDeletePage());
            processPageForAction(request, "delete", "success", getAfterDeletePage());

            try { 
                m_actionExtent.afterDelete(request, response, autositeDataObject);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());

                //Default error page but will be overridden by exception specific error page
                processPageForAction(request, "delete", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException) 
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "delete", "error"));

                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }

        // ================== ADD =====================================================================================
        } else if (isActionCmdAdd(request)) {
            if (!haveAccessToCommand(session,getActionCmd(request)) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 


            AutositeDataObject _GenMainNew = createNewObject();  
            m_logger.info("Creating new " + _GenMainNew.getClass().getName() );

            // Setting IDs for the object
            _GenMainNew.setSiteId(site.getId());
            
            if ( _GenMainForm == null) {
                setFields(request, response, _GenMainNew);
            } else {

                setFields(request, response, _GenMainForm, _GenMainNew);
            }

            try {
                checkDepedenceIntegrity(_GenMainNew);
            }
            catch (Exception e1) {
                if (throwException) throw new Exception("Internal Error Occurred.");
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }


            try{
                m_actionExtent.beforeAdd(request, response, _GenMainNew);
                m_ds.put(_GenMainNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

                //setPage(session, getErrorPage());
                processPageForAction(request, "add", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "add", "error"));
                return mapping.findForward("default");
            }
            
//            if (_GenMainNew.skipPersist())
//              m_logger.info("Skipping new object by skipPersist()");
//          else                
            
            if (returnObjects != null) returnObjects.put("target", _GenMainNew);

            processPageForAction(request, "add", "success", getAfterAddPage());

            try{
                m_actionExtent.afterAdd(request, response, _GenMainNew);
            }
            catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

                //setPage(session, getErrorPage());
                processPageForAction(request, "add", "error", getErrorPage(), getSentPage(request));

                if (e instanceof ActionExtentException)
                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), "add", "error"));
                return mapping.findForward("default");
            }
             autositeDataObject =  _GenMainNew;

        } else {
            //===============================================================================================================
            // processing non-standard command
            //===============================================================================================================

            String command = getActionCmd(request);
            
            if ( command != null && !command.trim().isEmpty()) {
                    
            if (!haveAccessToCommand(session, command ) ){
                sessionErrorText(session, "Permission error occurred.");
                processPageForAction(request, "access", "error", getErrorPage(), getSentPage(request));
                return mapping.findForward("default");
            } 
            
            try {
                
                m_actionExtent.processCommand(request, response, command, autositeDataObject);
                if (returnObjects != null &&  autositeDataObject!= null ) returnObjects.put("target", autositeDataObject);
                
            } catch (Exception e) {
                if (throwException) throw e;
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);

                processPageForAction(request, command, "error", getErrorPage(), getSentPage(request));

                    setPage(session,
                            ((ActionExtentException)e).getForwardPage(),
                            pageManager.isInternalForward(getActionName(), command, "error"));
                return mapping.findForward("default");
            }
            processPageForAction(request, command, "success", getAfterDeletePage());
            return mapping.findForward("default");
            
            } else {
                //
                sessionErrorText(session, "Invalid request.");
                m_logger.error("There was no proper command like add or update or delete");
    
                processPageForAction(request, "$cmd", "error", getErrorPage(), getSentPage(request));
            
                return mapping.findForward("default");
            }
        }
        
        // save the affected object
        if ( autositeDataObject != null) {
            request.setAttribute("autosite.request.current", autositeDataObject);
            request.setAttribute("autosite.request.current.id", new Long(autositeDataObject.getId()));
        }
        
        // @Deprecated
        // if (isSynchRequired()) registerSynchLedgerForServerEvents(request,  autositeDataObject, "cleaner-ticket" );
        
        if (isSynchRequired()) {
            long stamp = synkop_process(autositeDataObject, request, isActionCmdDelete(request));
            request.setAttribute("autosite.synk.stamp", new Long(stamp));
        }

        return mapping.findForward("default");
    }
    
    // By removing AutositeActionBase, change abstract to the methods
    
    protected String getField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject _GenMain){
        throw new NotImplementedException("createNewObject()");
    }

    protected AutositeDataObject createNewObject() {
        throw new NotImplementedException("createNewObject()");
    }
    protected void checkDepedenceIntegrity(AutositeDataObject _GenMain) throws Exception {
        throw new NotImplementedException("checkDepedenceIntegrity(AutositeDataObject _GenMain) throws Exception");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, ActionForm _GenMainForm, AutositeDataObject _GenMain) throws Exception {
        throw new NotImplementedException("edit(HttpServletRequest request, HttpServletResponse response, ActionForm _GenMainForm, AutositeDataObject _GenMain) throws Exception");
    }
    protected void editField(HttpServletRequest request, HttpServletResponse response, AutositeDataObject _GenMain) throws Exception {
        throw new NotImplementedException("edit(HttpServletRequest request, HttpServletResponse response, ActionForm _GenMainForm, AutositeDataObject _GenMain) throws Exception");
    }
    
    void setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject _GenMain) throws Exception{
        throw new NotImplementedException("setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject _GenMain) throws Exception");
    }
    protected void setFields(HttpServletRequest request, HttpServletResponse response, ActionForm _GenMainForm, AutositeDataObject _GenMain) throws Exception {
        throw new NotImplementedException("setFields(HttpServletRequest request, HttpServletResponse response, AutositeDataObject _GenMain) throws Exception");
    }
    
    
    protected boolean loginRequired() {
        return true;
    }

    public boolean isSynchRequired(){
        return false;
    }
    public boolean isPagelessSessionOnly(){
        return false;
    }

    //  // Configuration Option
    //
    protected String getErrorPage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return getDefaultPage();
        }
        return m_actionExtent.getErrorPage();
    }

    protected String getWarningPage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return getDefaultPage();
        }
        return m_actionExtent.getWarningPage();
    }

    protected String getAfterAddPage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return getDefaultPage();
        }
        return m_actionExtent.getAfterAddPage();
    }

    protected String getAfterEditPage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return getDefaultPage();
        }
        return m_actionExtent.getAfterEditPage();
    }

    protected String getAfterEditFieldPage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return getDefaultPage();
        }
        return m_actionExtent.getAfterEditFieldPage();
    }

    protected String getAfterDeletePage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return getDefaultPage();
        }
        return m_actionExtent.getAfterDeletePage();
    }

    protected String getDefaultPage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return "";
        }
        return m_actionExtent.getDefaultPage();
    }

    protected String getConfirmPage() {
        if ( m_actionExtent == null) {
            m_logger.debug("Extent not configured. returning default page");
            return getDefaultPage();
        }
        return m_actionExtent.getConfirmPage();
    }

    public String getActionGroupName(){ return "ChurApp";} 


    // This is default access, but cae overrided by config file Default and detail setting. 
    protected SystemRole getDefaultSystemRole(){
        if (true) return AccessDef.SystemRole.SiteAdmin;
        return AccessDef.SystemRole.User;
    }
    
    protected AbstractDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    /*
     * ========================================================================================================================================
     * ========================================================================================================================================
     * ========================================================================================================================================
     * ========================================================================================================================================
     * ========================================================================================================================================
     * 
     * 
     */
    
    
    /*
     * add to SYNK and returns stamp id for the record.
     *  
     */

    protected SynkNamespaceRecord synkop_findSynkRecord ( HttpServletRequest request, long objId){

        if (! synkRequired ) return null;
        
        AutositeSessionContext ctx = (AutositeSessionContext) AutositeSessionContextUtil.getSessionContextFromHttpSession(request.getSession());

        String devideId = "webserver"; //TODO how to get web site device id
        if( ctx.getRemoteDevice() != null){
            devideId = ctx.getRemoteDevice().getDeviceId();
        }
        
        SynkUpdateHandler handler =  synkHandlerFactory.getUpdateHandler(devideId, getActionName(), ctx.getRemoteIp());
        
        
        return ((SynkHandlerImpl)handler).findRecordById(objId);
    }
    
    public long synkop_process(AutositeDataObject obj, HttpServletRequest request, boolean deleted){

        AutositeSessionContext ctx = (AutositeSessionContext) AutositeSessionContextUtil.getSessionContextFromHttpSession(request.getSession());

        String devideId = "webserver"; //TODO how to get web site device id
        if( ctx.getRemoteDevice() != null){
            devideId = ctx.getRemoteDevice().getDeviceId();
        }
        
        SynkModHandler handler =  synkHandlerFactory.getModHandler(devideId, getActionName(), ctx.getRemoteIp());
        
        if (deleted)
            return handler.delete(obj.getId(), ctx.getUsername());
        else
            return handler.insert(obj, ctx.getUsername());
        
    }

    public Map<Long, SynkNamespaceRecord> synkop_getRecordsFromLastConfirm( HttpServletRequest request){
        
        AutositeSessionContext ctx = (AutositeSessionContext) AutositeSessionContextUtil.getSessionContextFromHttpSession(request.getSession());

        String devideId = "webserver"; //TODO how to get web site device id
        if( ctx.getRemoteDevice() != null){
            devideId = ctx.getRemoteDevice().getDeviceId();
        }
        
        SynkUpdateHandler handler =  synkHandlerFactory.getUpdateHandler(devideId, getActionName(), ctx.getRemoteIp());
        
        List<SynkNamespaceRecord> list =   handler.getUpdatesFromLastConfirm();
        
        
        Map<Long, SynkNamespaceRecord> ret = new HashMap<>();
        
        for (SynkNamespaceRecord synkNamespaceRecord : list) {
            
            ret.put(new Long(synkNamespaceRecord.getRecordId()), synkNamespaceRecord);
        }
        
        return ret;
    }

    public Map<Long, SynkNamespaceRecord> synkop_getAllRecords( HttpServletRequest request){
        
        AutositeSessionContext ctx = (AutositeSessionContext) AutositeSessionContextUtil.getSessionContextFromHttpSession(request.getSession());

        String devideId = "webserver"; //TODO how to get web site device id
        if( ctx.getRemoteDevice() != null){
            devideId = ctx.getRemoteDevice().getDeviceId();
        }
        
        SynkUpdateHandler handler =  synkHandlerFactory.getUpdateHandler(devideId, getActionName(), ctx.getRemoteIp());
        
        List<SynkNamespaceRecord> list =   handler.getAllRecords();
        
        
        Map<Long, SynkNamespaceRecord> ret = new HashMap<>();
        
        for (SynkNamespaceRecord synkNamespaceRecord : list) {
            
            ret.put(new Long(synkNamespaceRecord.getRecordId()), synkNamespaceRecord);
        }
        
        return ret;
    }
    
    
    public Map<Long, SynkNamespaceRecord> synkop_getNotSynkedRecords( HttpServletRequest request){

        AutositeSessionContext ctx = (AutositeSessionContext) AutositeSessionContextUtil.getSessionContextFromHttpSession(request.getSession());

        String devideId = "webserver"; //TODO how to get web site device id
        if( ctx.getRemoteDevice() != null){
            devideId = ctx.getRemoteDevice().getDeviceId();
        }
                
        String synchBoundStamp = request.getParameter("_synchLowBound");
        SynkUpdateHandler handler =  synkHandlerFactory.getUpdateHandler(devideId, getActionName(), ctx.getRemoteIp());
        
        
        
        List<SynkNamespaceRecord> list =  null;
        
        if ( StringUtils.isBlank(synchBoundStamp))
            list = handler.getUpdatesFromLastConfirm();
        else
            list = handler.getUpdates(Long.parseLong(synchBoundStamp));
        
        Map<Long, SynkNamespaceRecord> ret = new HashMap<>();
        
        for (SynkNamespaceRecord synkNamespaceRecord : list) {
            
            ret.put(new Long(synkNamespaceRecord.getRecordId()), synkNamespaceRecord);
        }
        
        return ret;
    }
    
    
    //=========================================================================
    
    
    protected List prepareReturnData(HttpServletRequest request, AutositeDataObject target, boolean isList){
        
        List ret = new ArrayList();
        
        Site site = SiteDS.getInstance().registerSite(request.getServerName());

        if (isList) {

            List list = m_ds.getBySiteId(site.getId());
            ret = new ArrayList(list);
            
        } else {            
            
            String arg = request.getParameter("ajaxOutArg");
            AutositeDataObject _autositeDataObject = null; 
            List list = m_ds.getBySiteId(site.getId());
            
            if (arg == null){
                _autositeDataObject = target;
            } else if (arg.equals("last")) {
                if (list.size() > 0)
                    _autositeDataObject = (AutositeDataObject) list.get(list.size()-1);
            } else if (arg.equals("first")) {
                if (list.size() > 0)
                    _autositeDataObject = (AutositeDataObject) list.get(0);
            } else {
                long id = WebParamUtil.getLongValue(arg);
                _autositeDataObject = (AutositeDataObject) m_ds.getById(id);
            }
            
            ret = new ArrayList();
            ret.add(_autositeDataObject);

        }
        
        return ret;
    }
    
    protected  String processAndReturnJSONResponse(HttpServletRequest request, AutositeDataObject target ) {
        m_logger.debug("Ajax Processing getjson getlistjson arg = " + request.getParameter("ajaxOutArg"));

        boolean returnList                      = isAjaxListOutput(request);
        List list = prepareReturnData(request, target, returnList);
        
        String fieldSetStr = request.getParameter("fieldlist");
        Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);
       
        Site site                               = findSessionBoundSite(request);
        AutositeSessionContext sessionContext   = (AutositeSessionContext)getSessionContext(request.getSession());
        AutositeRemoteDevice device             = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());

//        DeviceSynchTracker tracker            = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);
        boolean ignoreFieldSet                  = (fieldSetStr == null? true: false);
        boolean isRemoteDevice                  = sessionContext.getRemoteDevice() != null; 
        boolean synchRequired                   = isRemoteDevice && isSynchRequired() && isJsonSynchOutput(request);
        boolean synchRequiredRecordsOnly        = hasRequestValue(request, "ajxr", "getjsonsynch")||hasRequestValue(request, "ajxr", "getlistjsonsynch");
        boolean isRemoteChangesSynchedToServer  = isActionCmdChangedData(request);
        
        JSONObject top = new JSONObject();

        JSONArray array = new JSONArray();

        top.put("type", "list");
        top.put("namesapce", getActionName());
        top.put("timestamp", System.currentTimeMillis());
        int count = 0;

        AbstractDataHolder dataHolder = createModelDataHolder();      
        
        if ( synchRequired ) {

            Map<Long,SynkNamespaceRecord> map = null;
            
             // Prepare data from synk records not from DB records.   

            if (synchRequiredRecordsOnly)
                map = synkop_getNotSynkedRecords(request);
            else
                map = synkop_getAllRecords(request);

            m_logger.debug("Records in processing: " + map.size());

            for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
                
                SynkNamespaceRecord         record                   = (SynkNamespaceRecord) iterator.next();
                SynkServerActionOnRecord    synkServerActionOnRecord = SynkServerActionOnRecord.RecordSynchRetrieval;
                
                if ( target != null && record.getRecordId() == target.getId()) {
                    synkServerActionOnRecord = SynkServerActionOnRecord.RecordChangeConfirmed;
                }
                
                JSONObject json = null;
                if ( WebUtil.isTrue(record.getDeleted())) {
                    json = new JSONObject();
                    
                    json.put("id",          String.valueOf(record.getRecordId()));
                    json.put("_deleted",    true);
                    
                } else {
            
                    AutositeDataObject autositeDataObject = (AutositeDataObject) m_ds.getById(record.getRecordId());
                    
                    if ( autositeDataObject == null ) {
                        m_logger.error("Synch record failed to locate " + record.getRecordId() + " is int SYNK but can't find object. skipped", new Exception());
                        continue;
                    }
                    
//                    json                = dataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, true, true);
//                    JSONObject json2    = dataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, false, true);
                    
                    JSONObject json2 = null;
                    try {
                        Method method = dataHolder.getClass().getMethod("convertToJSON", autositeDataObject.getClass(), Set.class, boolean.class, boolean.class, boolean.class);
                        json = (JSONObject)method.invoke(null, autositeDataObject, fieldSet, ignoreFieldSet, true, true);
                        json2 = (JSONObject)method.invoke(null, autositeDataObject, fieldSet, ignoreFieldSet, false, true);
                    }
                    catch (Exception e) {
                        m_logger.error(e.getMessage(),e);
                    }
                    
                    m_logger.debug("---------------------------------------------------------------------------------");
                    m_logger.debug(JsonUtil.beautify(json2.toString()));
                    m_logger.debug("---------------------------------------------------------------------------------");

                    json.put("_deleted", false);
                }

                json.put("_synchId",                String.valueOf(record.getStamp()));
                json.put("_synchScope",             record.getNamespace());
                json.put("_stamp",                  String.valueOf(record.getStamp()));
                json.put("_synchResponseAction",    String.valueOf(synkServerActionOnRecord.ordinal()));

                array.put(json);
                count++;
            }
            
        } else { // No SYNK
        
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                AutositeDataObject autositeDataObject = (AutositeDataObject)iterator.next();

//                JSONObject json     = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, true, true);
//                JSONObject json2    = CleanerPickupDeliveryDataHolder.convertToJSON(_CleanerPickupDelivery, fieldSet, ignoreFieldSet, false, true);

                JSONObject json2 = null;
                JSONObject json = null;
                
                try {
                    Method method = dataHolder.getClass().getMethod("convertToJSON", autositeDataObject.getClass(), Set.class, boolean.class, boolean.class, boolean.class);
                    json = (JSONObject)method.invoke(null, autositeDataObject, fieldSet, ignoreFieldSet, true, true);
                    json2 = (JSONObject)method.invoke(null, autositeDataObject, fieldSet, ignoreFieldSet, false, true);
                }
                catch (Exception e) {
                    m_logger.error(e.getMessage(),e);
                }
                
                SynkNamespaceRecord synkRecord = synkop_findSynkRecord(request, autositeDataObject.getId());
                if ( synkRecord != null)
                    json.put("_synchId", String.valueOf(synkRecord.getStamp()));

                m_logger.debug("---------------------------------------------------------------------------------");
                m_logger.debug(JsonUtil.beautify(json2.toString()));
                m_logger.debug("---------------------------------------------------------------------------------");

                array.put(json);
                count++;
            }
        
        }

        top.put("list", array);
        top.put("count",  String.valueOf(count));

        m_logger.debug(JsonUtil.beautify(top.toString()));

        return top.toString();        
    }
    
    /**
     *         } else if (hasRequestValue(request, "ajaxOut", "getdata") || hasRequestValue(request, "ajaxOut", "getlistdata") ||
                   hasRequestValue(request, "ajxr", "getdata") || hasRequestValue(request, "ajxr", "getlistdata")  ){

     */
    protected Object processAndReturnJSONX(HttpServletRequest request,  AutositeDataObject target ) {
        
        
        m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

        boolean returnList                      = isAjaxListOutput(request);
        List list = prepareReturnData(request, target, returnList);

        String fieldSetStr = request.getParameter("fieldlist");
        Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

        boolean ignoreFieldSet = (WebUtil.isNull(fieldSetStr)? true: false); //IF no fieldString set, return all field

        String frameClass = isThere(request, "fromClass")? "class=\""+request.getParameter("frameClass")+"\"" : "";
        String listClass = isThere(request, "listClass")? "class=\""+request.getParameter("listClass")+"\"" : "";
        String itemClass = isThere(request, "itemClass")? "class=\""+request.getParameter("itemClass")+"\"" : "";
        
        
        m_logger.debug("Number of objects to return " + list.size());
        StringBuilder buf = new StringBuilder();

        
        buf.append("<div id=\"" + getActionName() +  "-ajax-frame\" "+frameClass+">");

        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            
            
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(iterator.next());
            PropertyDescriptor[] properties = beanWrapper.getPropertyDescriptors();
            
            buf.append("<div id=\"" + getActionName() +  "-ajax-item-" + WebUtil.display((String) beanWrapper.getPropertyValue("key") ) +"\" "+listClass+">");
            for (int i = 0; i < properties.length; i++) {
                
                buf.append("<div id=\"cleanerPickupDelivery-ajax-" + properties[i].getName()+"\" "+itemClass+">");                
                buf.append(WebUtil.display(properties[i].getPropertyType(), beanWrapper.getPropertyValue(properties[i].getName())));
                buf.append("</div>");

                
                
                
                
                try {
                    m_logger.debug("-------------------------------");
                    m_logger.debug(properties[i].getName() + " -> " + properties[i].getShortDescription() + " -> "+ beanWrapper.getPropertyValue(properties[i].getName()));
                    m_logger.debug("getClass : " +(properties[i].getClass()));
                    m_logger.debug("getPropertyType : " +(properties[i].getPropertyType()));
                    m_logger.debug("" +(properties[i].getPropertyEditorClass()));
                    m_logger.debug("" +(properties[i].getValue(properties[i].getName())));
                }
                catch (BeansException e) {
                    m_logger.error(e);
                }
            }
            
        }
        
        m_logger.debug("###############" + buf.toString());
        return buf.toString();
        
    }
    
    /*
     *        } else if (hasRequestValue(request, "ajaxOut", "gethtml") || hasRequestValue(request, "ajaxOut", "getlisthtml") ||
                   hasRequestValue(request, "ajxr", "gethtml") || hasRequestValue(request, "ajxr", "getlisthtml")  ){
     */
    
    protected Object processAndReturnJSONGetListHtml(HttpServletRequest request,  AutositeDataObject target ) {
        
        m_logger.debug("Ajax Processing getdata getlistdata arg = " + request.getParameter("ajaxOutArg"));

        boolean returnList                      = isAjaxListOutput(request);
        List list = prepareReturnData(request, target, returnList);

        String fieldSetStr = request.getParameter("fieldlist");
        Set fieldSet = JtStringUtil.convertToSet(fieldSetStr);

        boolean ignoreFieldSet = (WebUtil.isNull(fieldSetStr)? true: false); //IF no fieldString set, return all field
        StringBuilder buf = new StringBuilder();
        
        String tableStyleStr  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
        String trStyleStr     = "style=\"border-bottom: 1px #6699CC solid;\"";
        String tdBoldStyleStr = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
        String tdStyleStr     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";

        buf.append("<table "+ tableStyleStr +" >").append("\n");
        m_logger.debug("Number of objects to return " + list.size());

        /*
         * Header. How to make when there is no
         */
        
        AbstractDataHolder dataHolder = createModelDataHolder();                

        if ( dataHolder == null ) {
            //TODO well....
        }
        
        buf.append("<tr "+ trStyleStr +" >").append("\n");
        try {
            Method method = dataHolder.getClass().getDeclaredMethod("getFieldsName");
            List<String> fields = (List) method.invoke(null, null);
            
            for (String fieldName : fields) {
                if ( ignoreFieldSet || fieldSet.contains(fieldName)) {
                    buf.append("<td "+ tdBoldStyleStr +" >");
                    buf.append(fieldName);
                    buf.append("</td>").append("\n");
                }
            }
        }
        catch (Exception e) {
            m_logger.error("",e);
        }
        buf.append("</tr>").append("\n");
        
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            
            buf.append("<tr "+ trStyleStr +" >").append("\n");
            
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(iterator.next());
            PropertyDescriptor[] properties = beanWrapper.getPropertyDescriptors();
            
            for (int i = 0; i < properties.length; i++) {
                if ( ignoreFieldSet || fieldSet.contains(properties[i].getName())) {
                    buf.append("<td "+ tdStyleStr +" >");
                    buf.append(WebUtil.display(properties[i].getPropertyType(), beanWrapper.getPropertyValue(properties[i].getName())));
                    buf.append("</td>").append("\n");
                }
            }
            buf.append("</tr>").append("\n");
        }
        buf.append("</table >");
        
        m_logger.debug("###############" + buf.toString());
        return buf.toString();
    }
    
    // Should be implemented under class 
    // This might be used to fields. 
    protected AbstractDataHolder createModelDataHolder() {
        
        String className = "com.autosite.holder." + getActionName() + "DataHolder";
        
        try {
            Class c = Class.forName(className);
            return (AbstractDataHolder) c.newInstance();
        }
        catch (Exception e) {
            m_logger.error("",e);
            return null;
        }
        
    }
    
    private static Logger m_logger = Logger.getLogger(AutositeCoreAction.class);
}
