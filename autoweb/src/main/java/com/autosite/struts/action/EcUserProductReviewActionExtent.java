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
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.ds.EcUserProductReviewDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcUserProductReviewForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcUserProductReviewActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserProductReviewAction#xtent.beforeAdd");		
        EcUserProductReview _EcUserProductReview = (EcUserProductReview)baseDbOject;
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#EcUserProductReviewAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        EcUserProductReview _EcUserProductReview = (EcUserProductReview)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserProductReviewAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        EcUserProductReview _EcUserProductReview = (EcUserProductReview)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserProductReviewAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        EcUserProductReview _EcUserProductReview = (EcUserProductReview)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserProductReviewAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        EcUserProductReview _EcUserProductReview = (EcUserProductReview)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#EcUserProductReviewAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        EcUserProductReview _EcUserProductReview = (EcUserProductReview)baseDbOject;
    }

	private EcUserProductReviewDS m_ds = EcUserProductReviewDS.getInstance();
    private static Logger m_logger = Logger.getLogger( EcUserProductReviewActionExtent.class);
    
}
