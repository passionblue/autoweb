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

import com.autosite.db.GenExtra;
import com.autosite.ds.GenExtraDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.GenExtraForm;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class GenExtraAction extends AutositeCoreAction {

    public GenExtraAction(){
	    m_ds = GenExtraDS.getInstance();
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        GenExtraForm _GenExtraForm = (GenExtraForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            GenExtra _GenExtra = m_ds.getById(cid);

            if (_GenExtra == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

			try{
	            edit(request, _GenExtraForm);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			setPage(session, "gen_extra_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            GenExtra _GenExtra = m_ds.getById(cid);

            if (_GenExtra == null) {
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
			setPage(session, "gen_extra");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")) {

            long cid = Long.parseLong(request.getParameter("id"));
            GenExtra _GenExtra = m_ds.getById(cid);

            if (_GenExtra == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }


        	m_ds.delete(_GenExtra);
        	setPage(session, "gen_extra_home");    
        }
        else {


			m_logger.info("Creating new GenExtra" );
			GenExtra _GenExtra = new GenExtra();	

			// Setting IDs for the object

            _GenExtra.setMainId(WebParamUtil.getLongValue(_GenExtraForm.getMainId()));
			m_logger.debug("setting MainId=" +_GenExtraForm.getMainId());
            _GenExtra.setSubId(WebParamUtil.getLongValue(_GenExtraForm.getSubId()));
			m_logger.debug("setting SubId=" +_GenExtraForm.getSubId());
            _GenExtra.setExtraValue(WebParamUtil.getIntValue(_GenExtraForm.getExtraValue()));
			m_logger.debug("setting ExtraValue=" +_GenExtraForm.getExtraValue());
            _GenExtra.setExtraData(WebParamUtil.getStringValue(_GenExtraForm.getExtraData()));
			m_logger.debug("setting ExtraData=" +_GenExtraForm.getExtraData());
            _GenExtra.setTimeCreated(WebParamUtil.getDateValue(_GenExtraForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_GenExtraForm.getTimeCreated());
            _GenExtra.setTimeUpdated(WebParamUtil.getDateValue(_GenExtraForm.getTimeUpdated()));
			m_logger.debug("setting TimeUpdated=" +_GenExtraForm.getTimeUpdated());

			setPage(session, "gen_extra_home");
            m_ds.put(_GenExtra);

        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, GenExtraForm _GenExtraForm) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        GenExtra _GenExtra = m_ds.getById(cid);

        _GenExtra.setMainId(WebParamUtil.getLongValue(_GenExtraForm.getMainId()));
        _GenExtra.setSubId(WebParamUtil.getLongValue(_GenExtraForm.getSubId()));
        _GenExtra.setExtraValue(WebParamUtil.getIntValue(_GenExtraForm.getExtraValue()));
        _GenExtra.setExtraData(WebParamUtil.getStringValue(_GenExtraForm.getExtraData()));
        _GenExtra.setTimeCreated(WebParamUtil.getDateValue(_GenExtraForm.getTimeCreated()));
        _GenExtra.setTimeUpdated(WebParamUtil.getDateValue(_GenExtraForm.getTimeUpdated()));
        m_ds.update(_GenExtra);
    }

    protected void editField(HttpServletRequest request) throws Exception{

        long cid = Long.parseLong(request.getParameter("id"));
        GenExtra _GenExtra = m_ds.getById(cid);

        if (!isMissing(request.getParameter("mainId"))) {
            m_logger.debug("updating param mainId from " +_GenExtra.getMainId() + "->" + request.getParameter("mainId"));
            _GenExtra.setMainId(WebParamUtil.getLongValue(request.getParameter("mainId")));
        }
        if (!isMissing(request.getParameter("subId"))) {
            m_logger.debug("updating param subId from " +_GenExtra.getSubId() + "->" + request.getParameter("subId"));
            _GenExtra.setSubId(WebParamUtil.getLongValue(request.getParameter("subId")));
        }
        if (!isMissing(request.getParameter("extraValue"))) {
            m_logger.debug("updating param extraValue from " +_GenExtra.getExtraValue() + "->" + request.getParameter("extraValue"));
            _GenExtra.setExtraValue(WebParamUtil.getIntValue(request.getParameter("extraValue")));
        }
        if (!isMissing(request.getParameter("extraData"))) {
            m_logger.debug("updating param extraData from " +_GenExtra.getExtraData() + "->" + request.getParameter("extraData"));
            _GenExtra.setExtraData(WebParamUtil.getStringValue(request.getParameter("extraData")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_GenExtra.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _GenExtra.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_GenExtra.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _GenExtra.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_ds.update(_GenExtra);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected GenExtraDS m_ds;

    private static Logger m_logger = Logger.getLogger( GenExtraAction.class);
}
