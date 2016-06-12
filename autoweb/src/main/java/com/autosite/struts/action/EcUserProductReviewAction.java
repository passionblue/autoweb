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

import com.autosite.db.EcUserProductReview;
import com.autosite.ds.EcUserProductReviewDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcUserProductReviewForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcUserProductReviewAction extends AutositeCoreAction {

    public EcUserProductReviewAction(){
	    m_ds = EcUserProductReviewDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcUserProductReviewForm _EcUserProductReviewForm = (EcUserProductReviewForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcUserProductReview _EcUserProductReview = m_ds.getById(cid);

            if (_EcUserProductReview == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcUserProductReview.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcUserProductReview.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _EcUserProductReviewForm, _EcUserProductReview);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			//setPage(session, "ec_user_product_review_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcUserProductReview _EcUserProductReview = m_ds.getById(cid);

            if (_EcUserProductReview == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcUserProductReview.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcUserProductReview.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _EcUserProductReview);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			//setPage(session, "ec_user_product_review");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcUserProductReview _EcUserProductReview = m_ds.getById(cid);

            if (_EcUserProductReview == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcUserProductReview.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcUserProductReview.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _EcUserProductReview);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_EcUserProductReview);
            try { 
	        	m_actionExtent.afterDelete(request, response, _EcUserProductReview);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
            }

        	//setPage(session, "ec_user_product_review_home");    
        }
        else {

            
	        WebProcess webProc = null;
	        try {
	            webProc = checkWebProcess(request, session);
	        }
	        catch (Exception e) {
	            m_logger.error(e.getMessage(), e);
	            return mapping.findForward("default");
	        }

			m_logger.info("Creating new EcUserProductReview" );
			EcUserProductReview _EcUserProductReview = new EcUserProductReview();	

			// Setting IDs for the object
			_EcUserProductReview.setSiteId(site.getId());

                _EcUserProductReview.setProductId(WebParamUtil.getLongValue(_EcUserProductReviewForm.getProductId()));
    			m_logger.debug("setting ProductId=" +_EcUserProductReviewForm.getProductId());
                _EcUserProductReview.setUserId(WebParamUtil.getLongValue(_EcUserProductReviewForm.getUserId()));
    			m_logger.debug("setting UserId=" +_EcUserProductReviewForm.getUserId());
                _EcUserProductReview.setRate(WebParamUtil.getIntValue(_EcUserProductReviewForm.getRate()));
    			m_logger.debug("setting Rate=" +_EcUserProductReviewForm.getRate());
                _EcUserProductReview.setReview(WebParamUtil.getStringValue(_EcUserProductReviewForm.getReview()));
    			m_logger.debug("setting Review=" +_EcUserProductReviewForm.getReview());
                _EcUserProductReview.setTrackBack(WebParamUtil.getStringValue(_EcUserProductReviewForm.getTrackBack()));
    			m_logger.debug("setting TrackBack=" +_EcUserProductReviewForm.getTrackBack());
                _EcUserProductReview.setNumVoteYes(WebParamUtil.getIntValue(_EcUserProductReviewForm.getNumVoteYes()));
    			m_logger.debug("setting NumVoteYes=" +_EcUserProductReviewForm.getNumVoteYes());
                _EcUserProductReview.setNumVoteNo(WebParamUtil.getIntValue(_EcUserProductReviewForm.getNumVoteNo()));
    			m_logger.debug("setting NumVoteNo=" +_EcUserProductReviewForm.getNumVoteNo());
                _EcUserProductReview.setTimeCreated(WebParamUtil.getDateValue(_EcUserProductReviewForm.getTimeCreated()));
    			m_logger.debug("setting TimeCreated=" +_EcUserProductReviewForm.getTimeCreated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _EcUserProductReview);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcUserProductReview);
			try{
		        m_actionExtent.afterAdd(request, response, _EcUserProductReview);
            }
            catch (Exception e) {
                sessionErrorText(session, e.getMessage());
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            //setPage(session, "ec_user_product_review_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcUserProductReviewForm _EcUserProductReviewForm, EcUserProductReview _EcUserProductReview) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcUserProductReview _EcUserProductReview = m_ds.getById(cid);

            _EcUserProductReview.setProductId(WebParamUtil.getLongValue(_EcUserProductReviewForm.getProductId()));
	            _EcUserProductReview.setUserId(WebParamUtil.getLongValue(_EcUserProductReviewForm.getUserId()));
	            _EcUserProductReview.setRate(WebParamUtil.getIntValue(_EcUserProductReviewForm.getRate()));
	            _EcUserProductReview.setReview(WebParamUtil.getStringValue(_EcUserProductReviewForm.getReview()));
	            _EcUserProductReview.setTrackBack(WebParamUtil.getStringValue(_EcUserProductReviewForm.getTrackBack()));
	            _EcUserProductReview.setNumVoteYes(WebParamUtil.getIntValue(_EcUserProductReviewForm.getNumVoteYes()));
	            _EcUserProductReview.setNumVoteNo(WebParamUtil.getIntValue(_EcUserProductReviewForm.getNumVoteNo()));
	            _EcUserProductReview.setTimeCreated(WebParamUtil.getDateValue(_EcUserProductReviewForm.getTimeCreated()));
	
        m_actionExtent.beforeUpdate(request, response, _EcUserProductReview);
        m_ds.update(_EcUserProductReview);
        m_actionExtent.afterUpdate(request, response, _EcUserProductReview);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcUserProductReview _EcUserProductReview) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcUserProductReview _EcUserProductReview = m_ds.getById(cid);

        if (!isMissing(request.getParameter("productId"))) {
            m_logger.debug("updating param productId from " +_EcUserProductReview.getProductId() + "->" + request.getParameter("productId"));
                _EcUserProductReview.setProductId(WebParamUtil.getLongValue(request.getParameter("productId")));
	        }
        if (!isMissing(request.getParameter("userId"))) {
            m_logger.debug("updating param userId from " +_EcUserProductReview.getUserId() + "->" + request.getParameter("userId"));
                _EcUserProductReview.setUserId(WebParamUtil.getLongValue(request.getParameter("userId")));
	        }
        if (!isMissing(request.getParameter("rate"))) {
            m_logger.debug("updating param rate from " +_EcUserProductReview.getRate() + "->" + request.getParameter("rate"));
                _EcUserProductReview.setRate(WebParamUtil.getIntValue(request.getParameter("rate")));
	        }
        if (!isMissing(request.getParameter("review"))) {
            m_logger.debug("updating param review from " +_EcUserProductReview.getReview() + "->" + request.getParameter("review"));
                _EcUserProductReview.setReview(WebParamUtil.getStringValue(request.getParameter("review")));
	        }
        if (!isMissing(request.getParameter("trackBack"))) {
            m_logger.debug("updating param trackBack from " +_EcUserProductReview.getTrackBack() + "->" + request.getParameter("trackBack"));
                _EcUserProductReview.setTrackBack(WebParamUtil.getStringValue(request.getParameter("trackBack")));
	        }
        if (!isMissing(request.getParameter("numVoteYes"))) {
            m_logger.debug("updating param numVoteYes from " +_EcUserProductReview.getNumVoteYes() + "->" + request.getParameter("numVoteYes"));
                _EcUserProductReview.setNumVoteYes(WebParamUtil.getIntValue(request.getParameter("numVoteYes")));
	        }
        if (!isMissing(request.getParameter("numVoteNo"))) {
            m_logger.debug("updating param numVoteNo from " +_EcUserProductReview.getNumVoteNo() + "->" + request.getParameter("numVoteNo"));
                _EcUserProductReview.setNumVoteNo(WebParamUtil.getIntValue(request.getParameter("numVoteNo")));
	        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcUserProductReview.getTimeCreated() + "->" + request.getParameter("timeCreated"));
                _EcUserProductReview.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
	        }

        m_actionExtent.beforeUpdate(request, response, _EcUserProductReview);
        m_ds.update(_EcUserProductReview);
        m_actionExtent.afterUpdate(request, response, _EcUserProductReview);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected EcUserProductReviewDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcUserProductReviewAction.class);
}
