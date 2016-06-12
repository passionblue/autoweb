package com.autosite.struts.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.Panel;
import com.autosite.db.PanelStyle;
import com.autosite.db.Site;
import com.autosite.ds.PanelDS;
import com.autosite.ds.PanelStyleDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.PanelForm;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebParamUtil;

public class CopyOfPanelAction extends AutositeCoreAction {

    public CopyOfPanelAction(){
	    m_ds = PanelDS.getInstance();
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PanelForm _PanelForm = (PanelForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            Panel _Panel = m_ds.getById(cid);

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
	            edit(request, _PanelForm);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "panel_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            Panel _Panel = m_ds.getById(cid);

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
	            editField(request);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			setPage(session, "panel");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            Panel _Panel = m_ds.getById(cid);

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

        	m_ds.delete(_Panel);
        	setPage(session, "panel_home");
        	
        	//>>START<<
        	
        	PanelStyleDS panelStyleDS = PanelStyleDS.getInstance();
        	List panelStyles = panelStyleDS.getByPanelId(cid);
        	for (Iterator iterator = panelStyles.iterator(); iterator.hasNext();) {
                PanelStyle panelStyle = (PanelStyle) iterator.next();
                if (panelStyleDS.delete(panelStyle))
                    m_logger.debug("deleted panelStyle for panel=" + cid + ", panelStyleId=" + panelStyle.getId());
                else
                    m_logger.debug("failed to delete panelStyle for panel=" + cid + ", panelStyleId=" + panelStyle.getId());
            }
        	
        	//>>END<<
        }
        else {

            String id = request.getParameter("wpid");
            
            WebProcess webProc = WebProcManager.getWebProcess(id);
            if (webProc == null ) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. WebProcess not found for " + id); 
                return mapping.findForward("default");
            }
            
            if (webProc.isCompleted() || webProc.isExpired() || webProc.isClosed() ) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. WebProcess closed or expired. Aborted. id " + id); 
                m_logger.warn("Failed. WebProcess closed or expired. Aborted. id " + webProc.isCompleted()); 
                m_logger.warn("Failed. WebProcess closed or expired. Aborted. id " + webProc.isExpired()); 
                m_logger.warn("Failed. WebProcess closed or expired. Aborted. id " + webProc.isClosed()); 
                return mapping.findForward("default");
            }
            m_logger.debug("WebProc " + id + " good. created at " + new Date(webProc.getCreatedTimestamp()));

			m_logger.info("Creating new Panel" );
			Panel _Panel = new Panel();	

			// Setting IDs for the object
			_Panel.setSiteId(site.getId());

            _Panel.setPageId(WebParamUtil.getLongValue(_PanelForm.getPageId()));
            m_logger.debug("setting PageId=" +_PanelForm.getPageId());
            _Panel.setPageOnly(WebParamUtil.getIntValue(_PanelForm.getPageOnly()));
            m_logger.debug("setting PageOnly=" +_PanelForm.getPageOnly());
            _Panel.setColumnNum(WebParamUtil.getIntValue(_PanelForm.getColumnNum()));
			m_logger.debug("setting ColumnNum=" +_PanelForm.getColumnNum());
            _Panel.setPanelTitle(WebParamUtil.getStringValue(_PanelForm.getPanelTitle()));
            m_logger.debug("setting PanelTitle=" +_PanelForm.getPanelTitle());
            _Panel.setPanelType(WebParamUtil.getIntValue(_PanelForm.getPanelType()));
			m_logger.debug("setting PanelType=" +_PanelForm.getPanelType());
            _Panel.setPanelHeight(WebParamUtil.getIntValue(_PanelForm.getPanelHeight()));
			m_logger.debug("setting PanelHeight=" +_PanelForm.getPanelHeight());
            _Panel.setHide(WebParamUtil.getIntValue(_PanelForm.getHide()));
			m_logger.debug("setting Hide=" +_PanelForm.getHide());
            _Panel.setTimeCreated(WebParamUtil.getDateValue(_PanelForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_PanelForm.getTimeCreated());
            _Panel.setTopSpace(WebParamUtil.getIntValue(_PanelForm.getTopSpace()));
			m_logger.debug("setting TopSpace=" +_PanelForm.getTopSpace());
            _Panel.setBottomSpace(WebParamUtil.getIntValue(_PanelForm.getBottomSpace()));
			m_logger.debug("setting BottomSpace=" +_PanelForm.getBottomSpace());
            _Panel.setLeftSpace(WebParamUtil.getIntValue(_PanelForm.getLeftSpace()));
			m_logger.debug("setting LeftSpace=" +_PanelForm.getLeftSpace());
            _Panel.setRightSpace(WebParamUtil.getIntValue(_PanelForm.getRightSpace()));
			m_logger.debug("setting RightSpace=" +_PanelForm.getRightSpace());
            _Panel.setStyleString(WebParamUtil.getStringValue(_PanelForm.getStyleString()));
			m_logger.debug("setting StyleString=" +_PanelForm.getStyleString());
            _Panel.setAlign(WebParamUtil.getIntValue(_PanelForm.getAlign()));
            m_logger.debug("setting StyleString=" +_PanelForm.getStyleString());
            _Panel.setTitleStyleString(WebParamUtil.getStringValue(_PanelForm.getTitleStyleString()));
            m_logger.debug("setting TitleStyleString=" +_PanelForm.getTitleStyleString());
            _Panel.setStyleString2(WebParamUtil.getStringValue(_PanelForm.getStyleString2()));
			m_logger.debug("setting Align=" +_PanelForm.getAlign());

			setPage(session, "panel_home");
            m_ds.put(_Panel);
			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, PanelForm _PanelForm) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        Panel _Panel = m_ds.getById(cid);

        _Panel.setPageId(WebParamUtil.getLongValue(_PanelForm.getPageId()));
        _Panel.setPageOnly(WebParamUtil.getIntValue(_PanelForm.getPageOnly()));
        _Panel.setColumnNum(WebParamUtil.getIntValue(_PanelForm.getColumnNum()));
        _Panel.setPanelTitle(WebParamUtil.getStringValue(_PanelForm.getPanelTitle()));
        _Panel.setPanelType(WebParamUtil.getIntValue(_PanelForm.getPanelType()));
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
        _Panel.setAlign(WebParamUtil.getIntValue(_PanelForm.getAlign()));
        m_ds.update(_Panel);
    }

    protected void editField(HttpServletRequest request) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        Panel _Panel = m_ds.getById(cid);

        if (!isMissing(request.getParameter("pageId"))) {
            m_logger.debug("updating param pageId from " +_Panel.getPageId() + "->" + request.getParameter("pageId"));
            _Panel.setPageId(WebParamUtil.getLongValue(request.getParameter("pageId")));
        }
        if (!isMissing(request.getParameter("pageOnly"))) {
            m_logger.debug("updating param pageOnly from " +_Panel.getPageOnly() + "->" + request.getParameter("pageOnly"));
            _Panel.setPageOnly(WebParamUtil.getIntValue(request.getParameter("pageOnly")));
        }
        if (!isMissing(request.getParameter("columnNum"))) {
            m_logger.debug("updating param columnNum from " +_Panel.getColumnNum() + "->" + request.getParameter("columnNum"));
            _Panel.setColumnNum(WebParamUtil.getIntValue(request.getParameter("columnNum")));
        }
        if (!isMissing(request.getParameter("panelTitle"))) {
            m_logger.debug("updating param panelTitle from " +_Panel.getPanelTitle() + "->" + request.getParameter("panelTitle"));
            _Panel.setPanelTitle(WebParamUtil.getStringValue(request.getParameter("panelTitle")));
        }
        if (!isMissing(request.getParameter("panelType"))) {
            m_logger.debug("updating param panelType from " +_Panel.getPanelType() + "->" + request.getParameter("panelType"));
            _Panel.setPanelType(WebParamUtil.getIntValue(request.getParameter("panelType")));
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
        if (!isMissing(request.getParameter("align"))) {
            m_logger.debug("updating param align from " +_Panel.getAlign() + "->" + request.getParameter("align"));
            _Panel.setAlign(WebParamUtil.getIntValue(request.getParameter("align")));
        }

        m_ds.update(_Panel);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected PanelDS m_ds;

    private static Logger m_logger = Logger.getLogger( CopyOfPanelAction.class);
}
