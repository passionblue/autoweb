package com.autosite.struts.action;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Panel;
import com.autosite.ds.PanelDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.db.AutositeUser;
import com.autosite.util.UserUtil;
import com.autosite.Constants;

import com.autosite.struts.form.PanelForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.TimeNow;

public class PanelAction extends AutositeCoreAction {

    public PanelAction(){
        m_ds = PanelDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
            m_actionExtent = new AutositeActionExtent();                
    }


    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PanelForm _PanelForm = (PanelForm) form;
        HttpSession session = request.getSession();

        setPage(session, "panel_home");

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        AutositeSessionContext  context = (AutositeSessionContext)getSessionContext(session);
        AutositeUser 			autoUser = context.getUserObject();
        boolean                 accessTestOkay = true;
        
        
        if (!accessTestOkay ){
            sessionErrorText(session, "Permission error occurred.");
            m_logger.error("Permission error occurred. Trying to access SuperAdmin/SiteAdmin operation without proper status");
            return mapping.findForward("default");
        }


        try {
            m_actionExtent.beforeMain(request, response);
        }
        catch (Exception e) {
            sessionErrorText(session, "Internal error occurred.");
            m_logger.error(e.getMessage(),e);
            return mapping.findForward("default");
        }


        Panel _Panel = null;
        long cid = (long)0;
        if (!hasRequestValue(request, "add", "true")) {
            cid = WebParamUtil.getLongValue(request.getParameter("id"));
            _Panel = m_ds.getById(cid);
		}


        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Panel _Panel = m_ds.getById(cid);

            if (_Panel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Panel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Panel.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                edit(request, response, _PanelForm, _Panel);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");

                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");
    
        } else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Panel _Panel = m_ds.getById(cid);

            if (_Panel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Panel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Panel.getSiteId()); 
                return mapping.findForward("default");
            }

            try{
                editField(request, response, _Panel);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }

            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            //long cid = WebParamUtil.getLongValue(request.getParameter("id"));
            //Panel _Panel = m_ds.getById(cid);

            if (_Panel == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_Panel.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _Panel.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
                m_actionExtent.beforeDelete(request, response, _Panel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            m_ds.delete(_Panel);
            try { 
                m_actionExtent.afterDelete(request, response, _Panel);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        } else if (hasRequestValue(request, "add", "true")) {


            m_logger.info("Creating new Panel" );
            Panel _PanelNew = new Panel();   

            // Setting IDs for the object
            _PanelNew.setSiteId(site.getId());

            _PanelNew.setPageId(WebParamUtil.getLongValue(_PanelForm.getPageId()));
            m_logger.debug("setting PageId=" +_PanelForm.getPageId());
            _PanelNew.setPageOnly(WebParamUtil.getIntValue(_PanelForm.getPageOnly()));
            m_logger.debug("setting PageOnly=" +_PanelForm.getPageOnly());
            _PanelNew.setPageOnlyGroup(WebParamUtil.getIntValue(_PanelForm.getPageOnlyGroup()));
            m_logger.debug("setting PageOnlyGroup=" +_PanelForm.getPageOnlyGroup());
            _PanelNew.setColumnNum(WebParamUtil.getIntValue(_PanelForm.getColumnNum()));
            m_logger.debug("setting ColumnNum=" +_PanelForm.getColumnNum());
            _PanelNew.setContentOnly(WebParamUtil.getIntValue(_PanelForm.getContentOnly()));
            m_logger.debug("setting ContentOnly=" +_PanelForm.getContentOnly());
            _PanelNew.setPanelType(WebParamUtil.getIntValue(_PanelForm.getPanelType()));
            m_logger.debug("setting PanelType=" +_PanelForm.getPanelType());
            _PanelNew.setPanelSubType(WebParamUtil.getIntValue(_PanelForm.getPanelSubType()));
            m_logger.debug("setting PanelSubType=" +_PanelForm.getPanelSubType());
            _PanelNew.setPanelTitle(WebParamUtil.getStringValue(_PanelForm.getPanelTitle()));
            m_logger.debug("setting PanelTitle=" +_PanelForm.getPanelTitle());
            _PanelNew.setPanelHeight(WebParamUtil.getIntValue(_PanelForm.getPanelHeight()));
            m_logger.debug("setting PanelHeight=" +_PanelForm.getPanelHeight());
            _PanelNew.setHide(WebParamUtil.getIntValue(_PanelForm.getHide()));
            m_logger.debug("setting Hide=" +_PanelForm.getHide());
            _PanelNew.setTimeCreated(WebParamUtil.getDateValue(_PanelForm.getTimeCreated()));
            m_logger.debug("setting TimeCreated=" +_PanelForm.getTimeCreated());
            _PanelNew.setTopSpace(WebParamUtil.getIntValue(_PanelForm.getTopSpace()));
            m_logger.debug("setting TopSpace=" +_PanelForm.getTopSpace());
            _PanelNew.setBottomSpace(WebParamUtil.getIntValue(_PanelForm.getBottomSpace()));
            m_logger.debug("setting BottomSpace=" +_PanelForm.getBottomSpace());
            _PanelNew.setLeftSpace(WebParamUtil.getIntValue(_PanelForm.getLeftSpace()));
            m_logger.debug("setting LeftSpace=" +_PanelForm.getLeftSpace());
            _PanelNew.setRightSpace(WebParamUtil.getIntValue(_PanelForm.getRightSpace()));
            m_logger.debug("setting RightSpace=" +_PanelForm.getRightSpace());
            _PanelNew.setStyleString(WebParamUtil.getStringValue(_PanelForm.getStyleString()));
            m_logger.debug("setting StyleString=" +_PanelForm.getStyleString());
            _PanelNew.setTitleStyleString(WebParamUtil.getStringValue(_PanelForm.getTitleStyleString()));
            m_logger.debug("setting TitleStyleString=" +_PanelForm.getTitleStyleString());
            _PanelNew.setStyleString2(WebParamUtil.getStringValue(_PanelForm.getStyleString2()));
            m_logger.debug("setting StyleString2=" +_PanelForm.getStyleString2());
            _PanelNew.setStyleDefaultCode(WebParamUtil.getStringValue(_PanelForm.getStyleDefaultCode()));
            m_logger.debug("setting StyleDefaultCode=" +_PanelForm.getStyleDefaultCode());
            _PanelNew.setAlign(WebParamUtil.getIntValue(_PanelForm.getAlign()));
            m_logger.debug("setting Align=" +_PanelForm.getAlign());
            _PanelNew.setColumnCount(WebParamUtil.getIntValue(_PanelForm.getColumnCount()));
            m_logger.debug("setting ColumnCount=" +_PanelForm.getColumnCount());
            _PanelNew.setPageDisplaySummary(WebParamUtil.getIntValue(_PanelForm.getPageDisplaySummary()));
            m_logger.debug("setting PageDisplaySummary=" +_PanelForm.getPageDisplaySummary());
            _PanelNew.setShowInPrint(WebParamUtil.getIntValue(_PanelForm.getShowInPrint()));
            m_logger.debug("setting ShowInPrint=" +_PanelForm.getShowInPrint());
            _PanelNew.setShowOnlyPrint(WebParamUtil.getIntValue(_PanelForm.getShowOnlyPrint()));
            m_logger.debug("setting ShowOnlyPrint=" +_PanelForm.getShowOnlyPrint());
            _PanelNew.setAdminOnly(WebParamUtil.getIntValue(_PanelForm.getAdminOnly()));
            m_logger.debug("setting AdminOnly=" +_PanelForm.getAdminOnly());
            _PanelNew.setFeedId(WebParamUtil.getLongValue(_PanelForm.getFeedId()));
            m_logger.debug("setting FeedId=" +_PanelForm.getFeedId());
            _PanelNew.setOption1(WebParamUtil.getStringValue(_PanelForm.getOption1()));
            m_logger.debug("setting Option1=" +_PanelForm.getOption1());
            _PanelNew.setOption2(WebParamUtil.getStringValue(_PanelForm.getOption2()));
            m_logger.debug("setting Option2=" +_PanelForm.getOption2());
            _PanelNew.setOption3(WebParamUtil.getStringValue(_PanelForm.getOption3()));
            m_logger.debug("setting Option3=" +_PanelForm.getOption3());

            try{
                m_actionExtent.beforeAdd(request, response, _PanelNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            if (_PanelNew.skipPersist())
				m_logger.info("Skipping new object by skipPersist()");
			else				
	            m_ds.put(_PanelNew);
            try{
                m_actionExtent.afterAdd(request, response, _PanelNew);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                if (e instanceof ActionExtentException) setPage(session, ((ActionExtentException)e).getForwardPage());
                return mapping.findForward("default");
            }
            
            //setPage(session, "panel_home");

        } else {

            sessionErrorText(session, "Invalid request.");
            m_logger.error("There was no proper command like add or update or delete");
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }


    protected void edit(HttpServletRequest request, HttpServletResponse response, PanelForm _PanelForm, Panel _Panel) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Panel _Panel = m_ds.getById(cid);

        m_logger.debug("Before update " + PanelDS.objectToString(_Panel));

        _Panel.setPageId(WebParamUtil.getLongValue(_PanelForm.getPageId()));
        _Panel.setPageOnly(WebParamUtil.getIntValue(_PanelForm.getPageOnly()));
        _Panel.setPageOnlyGroup(WebParamUtil.getIntValue(_PanelForm.getPageOnlyGroup()));
        _Panel.setColumnNum(WebParamUtil.getIntValue(_PanelForm.getColumnNum()));
        _Panel.setContentOnly(WebParamUtil.getIntValue(_PanelForm.getContentOnly()));
        _Panel.setPanelType(WebParamUtil.getIntValue(_PanelForm.getPanelType()));
        _Panel.setPanelSubType(WebParamUtil.getIntValue(_PanelForm.getPanelSubType()));
        _Panel.setPanelTitle(WebParamUtil.getStringValue(_PanelForm.getPanelTitle()));
        _Panel.setPanelHeight(WebParamUtil.getIntValue(_PanelForm.getPanelHeight()));
        _Panel.setHide(WebParamUtil.getIntValue(_PanelForm.getHide()));
        _Panel.setTimeCreated(WebParamUtil.getDateValue(_PanelForm.getTimeCreated()));
        _Panel.setTopSpace(WebParamUtil.getIntValue(_PanelForm.getTopSpace()));
        _Panel.setBottomSpace(WebParamUtil.getIntValue(_PanelForm.getBottomSpace()));
        _Panel.setLeftSpace(WebParamUtil.getIntValue(_PanelForm.getLeftSpace()));
        _Panel.setRightSpace(WebParamUtil.getIntValue(_PanelForm.getRightSpace()));
        _Panel.setStyleString(WebParamUtil.getStringValue(_PanelForm.getStyleString()));
        _Panel.setTitleStyleString(WebParamUtil.getStringValue(_PanelForm.getTitleStyleString()));
        _Panel.setStyleString2(WebParamUtil.getStringValue(_PanelForm.getStyleString2()));
        _Panel.setStyleDefaultCode(WebParamUtil.getStringValue(_PanelForm.getStyleDefaultCode()));
        _Panel.setAlign(WebParamUtil.getIntValue(_PanelForm.getAlign()));
        _Panel.setColumnCount(WebParamUtil.getIntValue(_PanelForm.getColumnCount()));
        _Panel.setPageDisplaySummary(WebParamUtil.getIntValue(_PanelForm.getPageDisplaySummary()));
        _Panel.setShowInPrint(WebParamUtil.getIntValue(_PanelForm.getShowInPrint()));
        _Panel.setShowOnlyPrint(WebParamUtil.getIntValue(_PanelForm.getShowOnlyPrint()));
        _Panel.setAdminOnly(WebParamUtil.getIntValue(_PanelForm.getAdminOnly()));
        _Panel.setFeedId(WebParamUtil.getLongValue(_PanelForm.getFeedId()));
        _Panel.setOption1(WebParamUtil.getStringValue(_PanelForm.getOption1()));
        _Panel.setOption2(WebParamUtil.getStringValue(_PanelForm.getOption2()));
        _Panel.setOption3(WebParamUtil.getStringValue(_PanelForm.getOption3()));

        m_actionExtent.beforeUpdate(request, response, _Panel);
        m_ds.update(_Panel);
        m_actionExtent.afterUpdate(request, response, _Panel);
        m_logger.debug("After update " + PanelDS.objectToString(_Panel));
    }


    protected void editField(HttpServletRequest request, HttpServletResponse response, Panel _Panel) throws Exception{

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Panel _Panel = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_Panel.getPageId() + "->" + request.getParameter("pageId"));
            _Panel.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("pageOnly"))) {
            m_logger.debug("updating param pageOnly from " +_Panel.getPageOnly() + "->" + request.getParameter("pageOnly"));
            _Panel.setPageOnly(WebParamUtil.getIntValue(request.getParameter("pageOnly")));
        }
        if (!isMissing(request.getParameter("pageOnlyGroup"))) {
            m_logger.debug("updating param pageOnlyGroup from " +_Panel.getPageOnlyGroup() + "->" + request.getParameter("pageOnlyGroup"));
            _Panel.setPageOnlyGroup(WebParamUtil.getIntValue(request.getParameter("pageOnlyGroup")));
        }
        if (!isMissing(request.getParameter("columnNum"))) {
            m_logger.debug("updating param columnNum from " +_Panel.getColumnNum() + "->" + request.getParameter("columnNum"));
            _Panel.setColumnNum(WebParamUtil.getIntValue(request.getParameter("columnNum")));
        }
        if (!isMissing(request.getParameter("contentOnly"))) {
            m_logger.debug("updating param contentOnly from " +_Panel.getContentOnly() + "->" + request.getParameter("contentOnly"));
            _Panel.setContentOnly(WebParamUtil.getIntValue(request.getParameter("contentOnly")));
        }
        if (!isMissing(request.getParameter("panelType"))) {
            m_logger.debug("updating param panelType from " +_Panel.getPanelType() + "->" + request.getParameter("panelType"));
            _Panel.setPanelType(WebParamUtil.getIntValue(request.getParameter("panelType")));
        }
        if (!isMissing(request.getParameter("panelSubType"))) {
            m_logger.debug("updating param panelSubType from " +_Panel.getPanelSubType() + "->" + request.getParameter("panelSubType"));
            _Panel.setPanelSubType(WebParamUtil.getIntValue(request.getParameter("panelSubType")));
        }
        if (!isMissing(request.getParameter("panelTitle"))) {
            m_logger.debug("updating param panelTitle from " +_Panel.getPanelTitle() + "->" + request.getParameter("panelTitle"));
            _Panel.setPanelTitle(WebParamUtil.getStringValue(request.getParameter("panelTitle")));
        }
        if (!isMissing(request.getParameter("panelHeight"))) {
            m_logger.debug("updating param panelHeight from " +_Panel.getPanelHeight() + "->" + request.getParameter("panelHeight"));
            _Panel.setPanelHeight(WebParamUtil.getIntValue(request.getParameter("panelHeight")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_Panel.getHide() + "->" + request.getParameter("hide"));
            _Panel.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_Panel.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _Panel.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("topSpace"))) {
            m_logger.debug("updating param topSpace from " +_Panel.getTopSpace() + "->" + request.getParameter("topSpace"));
            _Panel.setTopSpace(WebParamUtil.getIntValue(request.getParameter("topSpace")));
        }
        if (!isMissing(request.getParameter("bottomSpace"))) {
            m_logger.debug("updating param bottomSpace from " +_Panel.getBottomSpace() + "->" + request.getParameter("bottomSpace"));
            _Panel.setBottomSpace(WebParamUtil.getIntValue(request.getParameter("bottomSpace")));
        }
        if (!isMissing(request.getParameter("leftSpace"))) {
            m_logger.debug("updating param leftSpace from " +_Panel.getLeftSpace() + "->" + request.getParameter("leftSpace"));
            _Panel.setLeftSpace(WebParamUtil.getIntValue(request.getParameter("leftSpace")));
        }
        if (!isMissing(request.getParameter("rightSpace"))) {
            m_logger.debug("updating param rightSpace from " +_Panel.getRightSpace() + "->" + request.getParameter("rightSpace"));
            _Panel.setRightSpace(WebParamUtil.getIntValue(request.getParameter("rightSpace")));
        }
        if (!isMissing(request.getParameter("styleString"))) {
            m_logger.debug("updating param styleString from " +_Panel.getStyleString() + "->" + request.getParameter("styleString"));
            _Panel.setStyleString(WebParamUtil.getStringValue(request.getParameter("styleString")));
        }
        if (!isMissing(request.getParameter("titleStyleString"))) {
            m_logger.debug("updating param titleStyleString from " +_Panel.getTitleStyleString() + "->" + request.getParameter("titleStyleString"));
            _Panel.setTitleStyleString(WebParamUtil.getStringValue(request.getParameter("titleStyleString")));
        }
        if (!isMissing(request.getParameter("styleString2"))) {
            m_logger.debug("updating param styleString2 from " +_Panel.getStyleString2() + "->" + request.getParameter("styleString2"));
            _Panel.setStyleString2(WebParamUtil.getStringValue(request.getParameter("styleString2")));
        }
        if (!isMissing(request.getParameter("styleDefaultCode"))) {
            m_logger.debug("updating param styleDefaultCode from " +_Panel.getStyleDefaultCode() + "->" + request.getParameter("styleDefaultCode"));
            _Panel.setStyleDefaultCode(WebParamUtil.getStringValue(request.getParameter("styleDefaultCode")));
        }
        if (!isMissing(request.getParameter("align"))) {
            m_logger.debug("updating param align from " +_Panel.getAlign() + "->" + request.getParameter("align"));
            _Panel.setAlign(WebParamUtil.getIntValue(request.getParameter("align")));
        }
        if (!isMissing(request.getParameter("columnCount"))) {
            m_logger.debug("updating param columnCount from " +_Panel.getColumnCount() + "->" + request.getParameter("columnCount"));
            _Panel.setColumnCount(WebParamUtil.getIntValue(request.getParameter("columnCount")));
        }
        if (!isMissing(request.getParameter("pageDisplaySummary"))) {
            m_logger.debug("updating param pageDisplaySummary from " +_Panel.getPageDisplaySummary() + "->" + request.getParameter("pageDisplaySummary"));
            _Panel.setPageDisplaySummary(WebParamUtil.getIntValue(request.getParameter("pageDisplaySummary")));
        }
        if (!isMissing(request.getParameter("showInPrint"))) {
            m_logger.debug("updating param showInPrint from " +_Panel.getShowInPrint() + "->" + request.getParameter("showInPrint"));
            _Panel.setShowInPrint(WebParamUtil.getIntValue(request.getParameter("showInPrint")));
        }
        if (!isMissing(request.getParameter("showOnlyPrint"))) {
            m_logger.debug("updating param showOnlyPrint from " +_Panel.getShowOnlyPrint() + "->" + request.getParameter("showOnlyPrint"));
            _Panel.setShowOnlyPrint(WebParamUtil.getIntValue(request.getParameter("showOnlyPrint")));
        }
        if (!isMissing(request.getParameter("adminOnly"))) {
            m_logger.debug("updating param adminOnly from " +_Panel.getAdminOnly() + "->" + request.getParameter("adminOnly"));
            _Panel.setAdminOnly(WebParamUtil.getIntValue(request.getParameter("adminOnly")));
        }
        if (!isMissing(request.getParameter("feedId"))) {
            m_logger.debug("updating param feedId from " +_Panel.getFeedId() + "->" + request.getParameter("feedId"));
            _Panel.setFeedId(WebParamUtil.getLongValue(request.getParameter("feedId")));
        }
        if (!isMissing(request.getParameter("option1"))) {
            m_logger.debug("updating param option1 from " +_Panel.getOption1() + "->" + request.getParameter("option1"));
            _Panel.setOption1(WebParamUtil.getStringValue(request.getParameter("option1")));
        }
        if (!isMissing(request.getParameter("option2"))) {
            m_logger.debug("updating param option2 from " +_Panel.getOption2() + "->" + request.getParameter("option2"));
            _Panel.setOption2(WebParamUtil.getStringValue(request.getParameter("option2")));
        }
        if (!isMissing(request.getParameter("option3"))) {
            m_logger.debug("updating param option3 from " +_Panel.getOption3() + "->" + request.getParameter("option3"));
            _Panel.setOption3(WebParamUtil.getStringValue(request.getParameter("option3")));
        }

        m_actionExtent.beforeUpdate(request, response, _Panel);
        m_ds.update(_Panel);
        m_actionExtent.afterUpdate(request, response, _Panel);
    }

    protected String getField(HttpServletRequest request, HttpServletResponse response, Panel _Panel) {

//        long cid = WebParamUtil.getLongValue(request.getParameter("id"));
//        Panel _Panel = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
			return String.valueOf(_Panel.getPageId());
        }
        if (!isMissing(request.getParameter("pageOnly"))) {
			return String.valueOf(_Panel.getPageOnly());
        }
        if (!isMissing(request.getParameter("pageOnlyGroup"))) {
			return String.valueOf(_Panel.getPageOnlyGroup());
        }
        if (!isMissing(request.getParameter("columnNum"))) {
			return String.valueOf(_Panel.getColumnNum());
        }
        if (!isMissing(request.getParameter("contentOnly"))) {
			return String.valueOf(_Panel.getContentOnly());
        }
        if (!isMissing(request.getParameter("panelType"))) {
			return String.valueOf(_Panel.getPanelType());
        }
        if (!isMissing(request.getParameter("panelSubType"))) {
			return String.valueOf(_Panel.getPanelSubType());
        }
        if (!isMissing(request.getParameter("panelTitle"))) {
			return String.valueOf(_Panel.getPanelTitle());
        }
        if (!isMissing(request.getParameter("panelHeight"))) {
			return String.valueOf(_Panel.getPanelHeight());
        }
        if (!isMissing(request.getParameter("hide"))) {
			return String.valueOf(_Panel.getHide());
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
			return String.valueOf(_Panel.getTimeCreated());
        }
        if (!isMissing(request.getParameter("topSpace"))) {
			return String.valueOf(_Panel.getTopSpace());
        }
        if (!isMissing(request.getParameter("bottomSpace"))) {
			return String.valueOf(_Panel.getBottomSpace());
        }
        if (!isMissing(request.getParameter("leftSpace"))) {
			return String.valueOf(_Panel.getLeftSpace());
        }
        if (!isMissing(request.getParameter("rightSpace"))) {
			return String.valueOf(_Panel.getRightSpace());
        }
        if (!isMissing(request.getParameter("styleString"))) {
			return String.valueOf(_Panel.getStyleString());
        }
        if (!isMissing(request.getParameter("titleStyleString"))) {
			return String.valueOf(_Panel.getTitleStyleString());
        }
        if (!isMissing(request.getParameter("styleString2"))) {
			return String.valueOf(_Panel.getStyleString2());
        }
        if (!isMissing(request.getParameter("styleDefaultCode"))) {
			return String.valueOf(_Panel.getStyleDefaultCode());
        }
        if (!isMissing(request.getParameter("align"))) {
			return String.valueOf(_Panel.getAlign());
        }
        if (!isMissing(request.getParameter("columnCount"))) {
			return String.valueOf(_Panel.getColumnCount());
        }
        if (!isMissing(request.getParameter("pageDisplaySummary"))) {
			return String.valueOf(_Panel.getPageDisplaySummary());
        }
        if (!isMissing(request.getParameter("showInPrint"))) {
			return String.valueOf(_Panel.getShowInPrint());
        }
        if (!isMissing(request.getParameter("showOnlyPrint"))) {
			return String.valueOf(_Panel.getShowOnlyPrint());
        }
        if (!isMissing(request.getParameter("adminOnly"))) {
			return String.valueOf(_Panel.getAdminOnly());
        }
        if (!isMissing(request.getParameter("feedId"))) {
			return String.valueOf(_Panel.getFeedId());
        }
        if (!isMissing(request.getParameter("option1"))) {
			return String.valueOf(_Panel.getOption1());
        }
        if (!isMissing(request.getParameter("option2"))) {
			return String.valueOf(_Panel.getOption2());
        }
        if (!isMissing(request.getParameter("option3"))) {
			return String.valueOf(_Panel.getOption3());
        }
		return null;
    }


    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    
        Map ret = new HashMap();
        
        //         // Request Processing 
        // 
        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed") ||
            hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del") ||
            hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add") ||
            hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {    
        
            try {
                ex(mapping, form, request, response);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
            }
        }
        
        //         // Response Processing 
        // 
        if (hasRequestValue(request, "ajaxOut", "getfield")){
            
            
            long id = WebParamUtil.getLongValue(request.getParameter("id"));
            Panel _Panel = PanelDS.getInstance().getById(id);
            if (_Panel == null) {
                ret.put("__error", "true");
                ret.put("__errorMsg", "Object Not Found");
                
            } else {
                String field = getField(request, response, _Panel);
                if (field != null)
                    ret.put("__value", field);
            }
            
        } else{
            try {
                Map resultAjax = m_actionExtent.processAjax(request, response);
                ret.put("__value", resultAjax.get("__value"));
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        return ret;
    }


    protected boolean loginRequired() {
        return true;
    }
    
    protected PanelDS m_ds;
    protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( PanelAction.class);
}
