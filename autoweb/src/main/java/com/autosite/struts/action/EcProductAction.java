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

import com.autosite.db.EcProduct;
import com.autosite.ds.EcProductDS;
import com.autosite.db.Site;
import com.autosite.ds.SiteDS;

import com.autosite.struts.form.EcProductForm;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.jtrend.session.WebProcManager;
import com.jtrend.session.webprocess.WebProcess;

public class EcProductAction extends AutositeCoreAction {

    public EcProductAction(){
	    m_ds = EcProductDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        EcProductForm _EcProductForm = (EcProductForm) form;
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "ed")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcProduct _EcProduct = m_ds.getById(cid);

            if (_EcProduct == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcProduct.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcProduct.getSiteId()); 
                return mapping.findForward("default");
            }
			try{
	            edit(request, response, _EcProductForm, _EcProduct);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }

			//setPage(session, "ec_product_home");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "editfield", "true")||hasRequestValue(request, "ef", "true")||hasRequestValue(request, "act", "ef")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcProduct _EcProduct = m_ds.getById(cid);

            if (_EcProduct == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcProduct.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcProduct.getSiteId()); 
                return mapping.findForward("default");
            }

			try{
	            editField(request, response, _EcProduct);
            }
            catch (Exception e) {
                m_logger.error("Error occurred", e);
                sessionErrorText(session, "Internal error occurred.");
                return mapping.findForward("default");
            }
			//setPage(session, "ec_product");
            return mapping.findForward("default");

        } else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcProduct _EcProduct = m_ds.getById(cid);

            if (_EcProduct == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (_EcProduct.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + _EcProduct.getSiteId()); 
                return mapping.findForward("default");
            }

            try {
	        	m_actionExtent.beforeDelete(request, response, _EcProduct);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before deletion.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
        	m_ds.delete(_EcProduct);
            try { 
	        	m_actionExtent.afterDelete(request, response, _EcProduct);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after deletion process.");
                m_logger.error(e.getMessage(),e);
            }

        	//setPage(session, "ec_product_home");    
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

			m_logger.info("Creating new EcProduct" );
			EcProduct _EcProduct = new EcProduct();	

			// Setting IDs for the object
			_EcProduct.setSiteId(site.getId());

            _EcProduct.setCategoryId(WebParamUtil.getLongValue(_EcProductForm.getCategoryId()));
			m_logger.debug("setting CategoryId=" +_EcProductForm.getCategoryId());
            _EcProduct.setFactorySku(WebParamUtil.getStringValue(_EcProductForm.getFactorySku()));
			m_logger.debug("setting FactorySku=" +_EcProductForm.getFactorySku());
            _EcProduct.setSiteSku(WebParamUtil.getStringValue(_EcProductForm.getSiteSku()));
			m_logger.debug("setting SiteSku=" +_EcProductForm.getSiteSku());
            _EcProduct.setAltSku(WebParamUtil.getStringValue(_EcProductForm.getAltSku()));
			m_logger.debug("setting AltSku=" +_EcProductForm.getAltSku());
            _EcProduct.setItemCode(WebParamUtil.getStringValue(_EcProductForm.getItemCode()));
			m_logger.debug("setting ItemCode=" +_EcProductForm.getItemCode());
            _EcProduct.setMaker(WebParamUtil.getStringValue(_EcProductForm.getMaker()));
			m_logger.debug("setting Maker=" +_EcProductForm.getMaker());
            _EcProduct.setBrand(WebParamUtil.getStringValue(_EcProductForm.getBrand()));
			m_logger.debug("setting Brand=" +_EcProductForm.getBrand());
            _EcProduct.setName(WebParamUtil.getStringValue(_EcProductForm.getName()));
			m_logger.debug("setting Name=" +_EcProductForm.getName());
            _EcProduct.setSubName(WebParamUtil.getStringValue(_EcProductForm.getSubName()));
			m_logger.debug("setting SubName=" +_EcProductForm.getSubName());
            _EcProduct.setShortDescription(WebParamUtil.getStringValue(_EcProductForm.getShortDescription()));
			m_logger.debug("setting ShortDescription=" +_EcProductForm.getShortDescription());
            _EcProduct.setDescription(WebParamUtil.getStringValue(_EcProductForm.getDescription()));
			m_logger.debug("setting Description=" +_EcProductForm.getDescription());
            _EcProduct.setDescription2(WebParamUtil.getStringValue(_EcProductForm.getDescription2()));
			m_logger.debug("setting Description2=" +_EcProductForm.getDescription2());
            _EcProduct.setImageUrl(WebParamUtil.getStringValue(_EcProductForm.getImageUrl()));
			m_logger.debug("setting ImageUrl=" +_EcProductForm.getImageUrl());
            _EcProduct.setColor(WebParamUtil.getStringValue(_EcProductForm.getColor()));
			m_logger.debug("setting Color=" +_EcProductForm.getColor());
            _EcProduct.setSize(WebParamUtil.getStringValue(_EcProductForm.getSize()));
			m_logger.debug("setting Size=" +_EcProductForm.getSize());
            _EcProduct.setMsrp(WebParamUtil.getDoubleValue(_EcProductForm.getMsrp()));
			m_logger.debug("setting Msrp=" +_EcProductForm.getMsrp());
            _EcProduct.setMsrpCurrency(WebParamUtil.getStringValue(_EcProductForm.getMsrpCurrency()));
			m_logger.debug("setting MsrpCurrency=" +_EcProductForm.getMsrpCurrency());
            _EcProduct.setSalePrice(WebParamUtil.getDoubleValue(_EcProductForm.getSalePrice()));
			m_logger.debug("setting SalePrice=" +_EcProductForm.getSalePrice());
            _EcProduct.setSaleEnds(WebParamUtil.getIntValue(_EcProductForm.getSaleEnds()));
			m_logger.debug("setting SaleEnds=" +_EcProductForm.getSaleEnds());
            _EcProduct.setSoldout(WebParamUtil.getIntValue(_EcProductForm.getSoldout()));
			m_logger.debug("setting Soldout=" +_EcProductForm.getSoldout());
            _EcProduct.setHide(WebParamUtil.getIntValue(_EcProductForm.getHide()));
			m_logger.debug("setting Hide=" +_EcProductForm.getHide());
            _EcProduct.setPromoNote(WebParamUtil.getStringValue(_EcProductForm.getPromoNote()));
			m_logger.debug("setting PromoNote=" +_EcProductForm.getPromoNote());
            _EcProduct.setTimeCreated(WebParamUtil.getDateValue(_EcProductForm.getTimeCreated()));
			m_logger.debug("setting TimeCreated=" +_EcProductForm.getTimeCreated());
            _EcProduct.setTimeUpdated(WebParamUtil.getDateValue(_EcProductForm.getTimeUpdated()));
			m_logger.debug("setting TimeUpdated=" +_EcProductForm.getTimeUpdated());

        	
			try{
            	m_actionExtent.beforeAdd(request, response, _EcProduct);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during before add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
            m_ds.put(_EcProduct);
			try{
		        m_actionExtent.afterAdd(request, response, _EcProduct);
            }
            catch (Exception e) {
                sessionErrorText(session, "Internal error occurred during after add.");
                m_logger.error(e.getMessage(),e);
                return mapping.findForward("default");
            }
			
            //setPage(session, "ec_product_home");

			webProc.complete();
        }
        return mapping.findForward("default");
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response, EcProductForm _EcProductForm, EcProduct _EcProduct) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcProduct _EcProduct = m_ds.getById(cid);

        _EcProduct.setCategoryId(WebParamUtil.getLongValue(_EcProductForm.getCategoryId()));
        _EcProduct.setFactorySku(WebParamUtil.getStringValue(_EcProductForm.getFactorySku()));
        _EcProduct.setSiteSku(WebParamUtil.getStringValue(_EcProductForm.getSiteSku()));
        _EcProduct.setAltSku(WebParamUtil.getStringValue(_EcProductForm.getAltSku()));
        _EcProduct.setItemCode(WebParamUtil.getStringValue(_EcProductForm.getItemCode()));
        _EcProduct.setMaker(WebParamUtil.getStringValue(_EcProductForm.getMaker()));
        _EcProduct.setBrand(WebParamUtil.getStringValue(_EcProductForm.getBrand()));
        _EcProduct.setName(WebParamUtil.getStringValue(_EcProductForm.getName()));
        _EcProduct.setSubName(WebParamUtil.getStringValue(_EcProductForm.getSubName()));
        _EcProduct.setShortDescription(WebParamUtil.getStringValue(_EcProductForm.getShortDescription()));
        _EcProduct.setDescription(WebParamUtil.getStringValue(_EcProductForm.getDescription()));
        _EcProduct.setDescription2(WebParamUtil.getStringValue(_EcProductForm.getDescription2()));
        _EcProduct.setImageUrl(WebParamUtil.getStringValue(_EcProductForm.getImageUrl()));
        _EcProduct.setColor(WebParamUtil.getStringValue(_EcProductForm.getColor()));
        _EcProduct.setSize(WebParamUtil.getStringValue(_EcProductForm.getSize()));
        _EcProduct.setMsrp(WebParamUtil.getDoubleValue(_EcProductForm.getMsrp()));
        _EcProduct.setMsrpCurrency(WebParamUtil.getStringValue(_EcProductForm.getMsrpCurrency()));
        _EcProduct.setSalePrice(WebParamUtil.getDoubleValue(_EcProductForm.getSalePrice()));
        _EcProduct.setSaleEnds(WebParamUtil.getIntValue(_EcProductForm.getSaleEnds()));
        _EcProduct.setSoldout(WebParamUtil.getIntValue(_EcProductForm.getSoldout()));
        _EcProduct.setHide(WebParamUtil.getIntValue(_EcProductForm.getHide()));
        _EcProduct.setPromoNote(WebParamUtil.getStringValue(_EcProductForm.getPromoNote()));
        _EcProduct.setTimeCreated(WebParamUtil.getDateValue(_EcProductForm.getTimeCreated()));
        _EcProduct.setTimeUpdated(WebParamUtil.getDateValue(_EcProductForm.getTimeUpdated()));

        m_actionExtent.beforeUpdate(request, response, _EcProduct);
        m_ds.update(_EcProduct);
        m_actionExtent.afterUpdate(request, response, _EcProduct);
    }

    protected void editField(HttpServletRequest request, HttpServletResponse response, EcProduct _EcProduct) throws Exception{

//        long cid = Long.parseLong(request.getParameter("id"));
//        EcProduct _EcProduct = m_ds.getById(cid);

        if (!isMissing(request.getParameter("categoryId"))) {
            m_logger.debug("updating param categoryId from " +_EcProduct.getCategoryId() + "->" + request.getParameter("categoryId"));
            _EcProduct.setCategoryId(WebParamUtil.getLongValue(request.getParameter("categoryId")));
        }
        if (!isMissing(request.getParameter("factorySku"))) {
            m_logger.debug("updating param factorySku from " +_EcProduct.getFactorySku() + "->" + request.getParameter("factorySku"));
            _EcProduct.setFactorySku(WebParamUtil.getStringValue(request.getParameter("factorySku")));
        }
        if (!isMissing(request.getParameter("siteSku"))) {
            m_logger.debug("updating param siteSku from " +_EcProduct.getSiteSku() + "->" + request.getParameter("siteSku"));
            _EcProduct.setSiteSku(WebParamUtil.getStringValue(request.getParameter("siteSku")));
        }
        if (!isMissing(request.getParameter("altSku"))) {
            m_logger.debug("updating param altSku from " +_EcProduct.getAltSku() + "->" + request.getParameter("altSku"));
            _EcProduct.setAltSku(WebParamUtil.getStringValue(request.getParameter("altSku")));
        }
        if (!isMissing(request.getParameter("itemCode"))) {
            m_logger.debug("updating param itemCode from " +_EcProduct.getItemCode() + "->" + request.getParameter("itemCode"));
            _EcProduct.setItemCode(WebParamUtil.getStringValue(request.getParameter("itemCode")));
        }
        if (!isMissing(request.getParameter("maker"))) {
            m_logger.debug("updating param maker from " +_EcProduct.getMaker() + "->" + request.getParameter("maker"));
            _EcProduct.setMaker(WebParamUtil.getStringValue(request.getParameter("maker")));
        }
        if (!isMissing(request.getParameter("brand"))) {
            m_logger.debug("updating param brand from " +_EcProduct.getBrand() + "->" + request.getParameter("brand"));
            _EcProduct.setBrand(WebParamUtil.getStringValue(request.getParameter("brand")));
        }
        if (!isMissing(request.getParameter("name"))) {
            m_logger.debug("updating param name from " +_EcProduct.getName() + "->" + request.getParameter("name"));
            _EcProduct.setName(WebParamUtil.getStringValue(request.getParameter("name")));
        }
        if (!isMissing(request.getParameter("subName"))) {
            m_logger.debug("updating param subName from " +_EcProduct.getSubName() + "->" + request.getParameter("subName"));
            _EcProduct.setSubName(WebParamUtil.getStringValue(request.getParameter("subName")));
        }
        if (!isMissing(request.getParameter("shortDescription"))) {
            m_logger.debug("updating param shortDescription from " +_EcProduct.getShortDescription() + "->" + request.getParameter("shortDescription"));
            _EcProduct.setShortDescription(WebParamUtil.getStringValue(request.getParameter("shortDescription")));
        }
        if (!isMissing(request.getParameter("description"))) {
            m_logger.debug("updating param description from " +_EcProduct.getDescription() + "->" + request.getParameter("description"));
            _EcProduct.setDescription(WebParamUtil.getStringValue(request.getParameter("description")));
        }
        if (!isMissing(request.getParameter("description2"))) {
            m_logger.debug("updating param description2 from " +_EcProduct.getDescription2() + "->" + request.getParameter("description2"));
            _EcProduct.setDescription2(WebParamUtil.getStringValue(request.getParameter("description2")));
        }
        if (!isMissing(request.getParameter("imageUrl"))) {
            m_logger.debug("updating param imageUrl from " +_EcProduct.getImageUrl() + "->" + request.getParameter("imageUrl"));
            _EcProduct.setImageUrl(WebParamUtil.getStringValue(request.getParameter("imageUrl")));
        }
        if (!isMissing(request.getParameter("color"))) {
            m_logger.debug("updating param color from " +_EcProduct.getColor() + "->" + request.getParameter("color"));
            _EcProduct.setColor(WebParamUtil.getStringValue(request.getParameter("color")));
        }
        if (!isMissing(request.getParameter("size"))) {
            m_logger.debug("updating param size from " +_EcProduct.getSize() + "->" + request.getParameter("size"));
            _EcProduct.setSize(WebParamUtil.getStringValue(request.getParameter("size")));
        }
        if (!isMissing(request.getParameter("msrp"))) {
            m_logger.debug("updating param msrp from " +_EcProduct.getMsrp() + "->" + request.getParameter("msrp"));
            _EcProduct.setMsrp(WebParamUtil.getDoubleValue(request.getParameter("msrp")));
        }
        if (!isMissing(request.getParameter("msrpCurrency"))) {
            m_logger.debug("updating param msrpCurrency from " +_EcProduct.getMsrpCurrency() + "->" + request.getParameter("msrpCurrency"));
            _EcProduct.setMsrpCurrency(WebParamUtil.getStringValue(request.getParameter("msrpCurrency")));
        }
        if (!isMissing(request.getParameter("salePrice"))) {
            m_logger.debug("updating param salePrice from " +_EcProduct.getSalePrice() + "->" + request.getParameter("salePrice"));
            _EcProduct.setSalePrice(WebParamUtil.getDoubleValue(request.getParameter("salePrice")));
        }
        if (!isMissing(request.getParameter("saleEnds"))) {
            m_logger.debug("updating param saleEnds from " +_EcProduct.getSaleEnds() + "->" + request.getParameter("saleEnds"));
            _EcProduct.setSaleEnds(WebParamUtil.getIntValue(request.getParameter("saleEnds")));
        }
        if (!isMissing(request.getParameter("soldout"))) {
            m_logger.debug("updating param soldout from " +_EcProduct.getSoldout() + "->" + request.getParameter("soldout"));
            _EcProduct.setSoldout(WebParamUtil.getIntValue(request.getParameter("soldout")));
        }
        if (!isMissing(request.getParameter("hide"))) {
            m_logger.debug("updating param hide from " +_EcProduct.getHide() + "->" + request.getParameter("hide"));
            _EcProduct.setHide(WebParamUtil.getIntValue(request.getParameter("hide")));
        }
        if (!isMissing(request.getParameter("promoNote"))) {
            m_logger.debug("updating param promoNote from " +_EcProduct.getPromoNote() + "->" + request.getParameter("promoNote"));
            _EcProduct.setPromoNote(WebParamUtil.getStringValue(request.getParameter("promoNote")));
        }
        if (!isMissing(request.getParameter("timeCreated"))) {
            m_logger.debug("updating param timeCreated from " +_EcProduct.getTimeCreated() + "->" + request.getParameter("timeCreated"));
            _EcProduct.setTimeCreated(WebParamUtil.getDateValue(request.getParameter("timeCreated")));
        }
        if (!isMissing(request.getParameter("timeUpdated"))) {
            m_logger.debug("updating param timeUpdated from " +_EcProduct.getTimeUpdated() + "->" + request.getParameter("timeUpdated"));
            _EcProduct.setTimeUpdated(WebParamUtil.getDateValue(request.getParameter("timeUpdated")));
        }

        m_actionExtent.beforeUpdate(request, response, _EcProduct);
        m_ds.update(_EcProduct);
        m_actionExtent.afterUpdate(request, response, _EcProduct);
    }


    protected boolean loginRequired() {
        return true;
    }

	protected EcProductDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcProductAction.class);
}
