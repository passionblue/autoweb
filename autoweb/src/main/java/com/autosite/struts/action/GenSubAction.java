package com.autosite.struts.action;

import java.util.Date;
import com.jtrend.util.WebParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.GenSub;
import com.autosite.ds.GenSubDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.GenSubForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class GenSubAction extends AutositeCoreAction {

    public GenSubAction(){
	    m_ds = GenSubDS.getInstance();
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        GenSubForm _GenSubForm = (GenSubForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            GenSub _GenSub = m_ds.getById(cid);

            if (_GenSub == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

			try{
	            edit(request, _GenSubForm);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "gen_sub_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            GenSub _GenSub = m_ds.getById(cid);

            if (_GenSub == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
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
			setPage(session, "gen_sub");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            GenSub _GenSub = m_ds.getById(cid);

            if (_GenSub == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }


        	m_ds.delete(_GenSub);
        	setPage(session, "gen_sub_home");    
        }
        else {


			m_logger.info("Creating new GenSub" );
			GenSub _GenSub = new GenSub();	

			// Setting IDs for the object

            _GenSub.setMainId(WebParamUtil.getLongValue(_GenSubForm.getMainId()));
			m_logger.debug("setting MainId=" +_GenSubForm.getMainId());
            _GenSub.setStrKey(WebParamUtil.getStringValue(_GenSubForm.getStrKey()));
			m_logger.debug("setting StrKey=" +_GenSubForm.getStrKey());
            _GenSub.setSubData(WebParamUtil.getStringValue(_GenSubForm.getSubData()));
			m_logger.debug("setting SubData=" +_GenSubForm.getSubData());
            _GenSub.setTimeCreated(WebParamUtil.getDateValue(_GenSubForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_GenSubForm.getTimeCreated());
            _GenSub.setTimeUpdated(WebParamUtil.getDateValue(_GenSubForm.getTimeUpdated()));
			m_logger.debug("setting TimeUpdated=" +_GenSubForm.getTimeUpdated());

			setPage(session, "gen_sub_home");
            m_ds.put(_GenSub);

        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, GenSubForm _GenSubForm) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        GenSub _GenSub = m_ds.getById(cid);

        _GenSub.setMainId(WebParamUtil.getLongValue(_GenSubForm.getMainId()));
        _GenSub.setStrKey(WebParamUtil.getStringValue(_GenSubForm.getStrKey()));
        _GenSub.setSubData(WebParamUtil.getStringValue(_GenSubForm.getSubData()));
        _GenSub.setTimeCreated(WebParamUtil.getDateValue(_GenSubForm.getTimeCreated()));
        _GenSub.setTimeUpdated(WebParamUtil.getDateValue(_GenSubForm.getTimeUpdated()));
        m_ds.update(_GenSub);
    }

    protected void editField(HttpServletRequest request) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        GenSub _GenSub = m_ds.getById(cid);

        if (!isMissing(request.getParameter("mainId"))) {
            m_logger.debug("updating param mainId from " +_GenSub.getMainId() + "->" + request.getParameter("mainId"));
            _GenSub.setMainId(WebParamUtil.getLongValue(request.getParameter("mainId")));
        }
        if (!isMissing(request.getParameter("strKey"))) {
            m_logger.debug("updating param strKey from " +_GenSub.getStrKey() + "->" + request.getParameter("strKey"));
            _GenSub.setStrKey(WebParamUtil.getStringValue(request.getParameter("strKey")));
        }
        if (!isMissing(request.getParameter("subData"))) {
            m_logger.debug("updating param subData from " +_GenSub.getSubData() + "->" + request.getParameter("subData"));
            _GenSub.setSubData(WebParamUtil.getStringValue(request.getParameter("subData")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_GenSub.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _GenSub.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_GenSub.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _GenSub.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_ds.update(_GenSub);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected GenSubDS m_ds;

    private static Logger m_logger = Logger.getLogger( GenSubAction.class);
}
