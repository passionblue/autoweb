package com.autosite.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.autosite.db.EcProduct;
import com.autosite.db.Site;
import com.autosite.ds.EcProductDS;
import com.autosite.ds.SiteDS;
import com.autosite.ec.EcCart;
import com.autosite.ec.EcCartItem;
import com.autosite.ec.EcCartManager;
import com.autosite.ec.EcProductPriceWrapper;
import com.autosite.session.AutositeSessionContext;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.util.AutositeSystem;
import com.jtrend.session.webprocess.WebProcess;
import com.jtrend.util.WebParamUtil;

public class EcCartSubmitAction extends AutositeCoreAction {

    public EcCartSubmitAction(){
	    m_ds = EcProductDS.getInstance();
        m_actionExtent = (AutositeActionExtent)getActionExtent();
        if ( m_actionExtent == null)
			m_actionExtent = new AutositeActionExtent();        		
	}

    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        Site site = SiteDS.getInstance().registerSite(request.getServerName());
        if (site == null ) {
            sessionErrorText(session, "Internal error occurred. Please try once again");
            return mapping.findForward("default");
        }

        WebProcess webProc = null;
        try {
            webProc = checkWebProcess(request, session);
        }
        catch (Exception e) {
            setPage(session, "ec_cart_main");
            m_logger.error(e.getMessage(), e);
            return mapping.findForward("default");
        }
        
        
        if (hasRequestValue(request, "add", "true")||hasRequestValue(request, "ad", "true")||hasRequestValue(request, "act", "add")) {

            long cid = Long.parseLong(request.getParameter("id"));
            EcProduct product = m_ds.getById(cid);

            if (product == null) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. object not found for " + cid);
                return mapping.findForward("default");
            }

            if (product.getSiteId() != site.getId()) {
                sessionErrorText(session, "Internal error occurred.");
                m_logger.warn("Failed. Attempt to delete other site. This Site=" + site.getId() + ", Attempted=" + product.getSiteId()); 
                return mapping.findForward("default");
            }

            //=====================================================================
            // product found
            m_logger.debug(EcProductDS.objectToString(product));
            
            //=====================================================================
            // Get or Create Cart
            AutositeSessionContext ctx = (AutositeSessionContext) getSessionContext(session);
            String rpcId = (String) session.getAttribute("k_RPCI");
            EcCart cart = EcCartManager.getCartMakeSure(ctx, rpcId, site.getId());

            if (cart == null) {
                sessionErrorText(session, "Error occurred while processing. Please try again");
                m_logger.info("****Could not get or create cart. Need To Investigate The Code****");
                return mapping.findForward("default");
            }
            
            m_logger.info("Cart found=" + cart);
            
            //=====================================================================
            // Items checking
            String size = request.getParameter("size");
            String color = request.getParameter("color");
            String sku = request.getParameter("siteSku");
            int qty = WebParamUtil.getIntValue(request.getParameter("qty"));
            
            if (qty == 0){
                m_logger.debug("0 qty set. doing nothing and returns");
                return mapping.findForward("default");
            }
            
            EcCartItem item = new EcCartItem();

            EcProductPriceWrapper wrapper = new EcProductPriceWrapper(product);
            double effectiveBasePrice = wrapper.getEffectivePrice();
            
            double sizeOffset = wrapper.getSizeOffset(size);
            double colorOffset = wrapper.getColorOffset(color);
            
            
            m_logger.debug("================== ITEM DETAILS ======================");
            m_logger.debug("product=" + product.getName());
            m_logger.debug("qty=" + qty);
            m_logger.debug("effectiveBasePrice=" + effectiveBasePrice);
            m_logger.debug("sizeOffset=" + sizeOffset);
            m_logger.debug("colorOffset=" + colorOffset);
            m_logger.debug("======================================================");
            
            effectiveBasePrice += sizeOffset;
            effectiveBasePrice += colorOffset;

            item.setProduct(product);
            item.setCount(qty);
            item.setOrderPirce(effectiveBasePrice);
            item.setSize(size);
            item.setColor(color);
            
            try {
                cart.addCartItem(item);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                sessionErrorText(session, "Internal Error occurred. The item was not added to the cart");
                return mapping.findForward("default");
            }
            
            webProc.complete();
            setPage(session, "ec_cart_main");
            return mapping.findForward("default");
	
		} else if (hasRequestValue(request, "edit", "true")||hasRequestValue(request, "ed", "true")||hasRequestValue(request, "act", "edit")) {

            //=====================================================================
            // Get or Create Cart
            String itemId = WebParamUtil.getStringValue(request.getParameter("itemId"));
            String sku = WebParamUtil.getStringValue(request.getParameter("siteSku"));
            int qty = WebParamUtil.getIntValue(request.getParameter("qty"));

            m_logger.debug("================== ITEM UUPDATE DETAILS ======================");
            m_logger.debug("itemId=" + itemId);
            m_logger.debug("sku=" + sku);
            m_logger.debug("count=" + qty);
            m_logger.debug("======================================================");
            
            
		    AutositeSessionContext ctx = (AutositeSessionContext) getSessionContext(session);
            String rpcId = (String) session.getAttribute("k_RPCI");
            EcCart cart = EcCartManager.getCartMakeSure(ctx, rpcId, site.getId());
            
            if (cart == null) {
                setPage(session, "ec_cart_main");
                sessionErrorText(session, "Cart is empty.");
                m_logger.info("****Could not get or create cart. Need To Investigate The Code****");
                return mapping.findForward("default");
            }

            m_logger.info("Cart found=" + cart);
            m_logger.debug("Cart found. num items=" + cart.getCartItems().size());
            
            EcCartItem cartItem = cart.getItem(itemId);

            if (cartItem == null) {
                setPage(session, "ec_cart_main");
                sessionErrorText(session, "Failed to update the item. Please try again");
                m_logger.warn("Failed. cart item not found for " + cartItem);
                return mapping.findForward("default");
            }

            if ( !cartItem.getProduct().getSiteSku().equals(sku)){
                setPage(session, "ec_cart_main");
                sessionErrorText(session, "Failed to update the item. Please remove the item and re-add the item again");
                m_logger.warn("Sku in the request differ from cart. request=" + sku + " , cartsku=" + cartItem.getProduct().getSiteSku());
                
                return mapping.findForward("default");
            }
            
            
            if (qty <= 0) {
                cart.removeCartItem(cartItem);
            } else {
                cartItem.setCount(qty);
                cart.recalculate();
            }

            webProc.complete();
            setPage(session, "ec_cart_main");
            return mapping.findForward("default");
		    
		} else if (hasRequestValue(request, "del", "true")||hasRequestValue(request, "de", "true")||hasRequestValue(request, "act", "del")) {

		    
            //=====================================================================
            // Get or Create Cart
            String itemId = WebParamUtil.getStringValue(request.getParameter("itemId"));
            String sku = WebParamUtil.getStringValue(request.getParameter("siteSku"));

            m_logger.debug("================== ITEM UUPDATE DETAILS ======================");
            m_logger.debug("itemId=" + itemId);
            m_logger.debug("sku=" + sku);
            m_logger.debug("======================================================");
            
            
            AutositeSessionContext ctx = (AutositeSessionContext) getSessionContext(session);
            String rpcId = (String) session.getAttribute("k_RPCI");
            EcCart cart = EcCartManager.getCartMakeSure(ctx, rpcId, site.getId());
            
            if (cart == null) {
                setPage(session, "ec_cart_main");
                sessionErrorText(session, "Cart is empty.");
                m_logger.info("****Could not get or create cart. Need To Investigate The Code****");
                return mapping.findForward("default");
            }

            m_logger.info("Cart found=" + cart);
            m_logger.debug("Cart found. num items=" + cart.getCartItems().size());
            
            EcCartItem cartItem = cart.getItem(itemId);

            if (cartItem == null) {
                setPage(session, "ec_cart_main");
                sessionErrorText(session, "Failed to delete the item. Please try again");
                m_logger.warn("Failed. cart item not found for " + cartItem);
                return mapping.findForward("default");
            }

            if ( !cartItem.getProduct().getSiteSku().equals(sku)){
                setPage(session, "ec_cart_main");
                sessionErrorText(session, "Failed to delete the item. Please remove the item and re-add the item again");
                m_logger.warn("Sku in the request differ from cart. request=" + sku + " , cartsku=" + cartItem.getProduct().getSiteSku());
                
                return mapping.findForward("default");
            }
            
            
            if (!cart.removeCartItem(cartItem)){
                m_logger.debug("Seems that cart item was not removed");
            }

            webProc.complete();
            setPage(session, "ec_cart_main");
            return mapping.findForward("default");
		    
        }
        else {
            setPage(session, "ec_cart_main");
            sessionErrorText(session, "Error occurred. Please try again");
            m_logger.warn("Invalid request arrived");
            AutositeSystem.report(AutositeSystem.IssueTypeInvalidRequest, 
                                  AutositeSystem.ReportedBySystemAction,
                                  site.getId(), "Cart submit request contains invalid action", 
                                  request);
        }

        webProc.complete();
        return mapping.findForward("default");
    }


    protected boolean loginRequired() {
        return false;
    }

	protected EcProductDS m_ds;
	protected AutositeActionExtent m_actionExtent;

    private static Logger m_logger = Logger.getLogger( EcCartSubmitAction.class);
}
