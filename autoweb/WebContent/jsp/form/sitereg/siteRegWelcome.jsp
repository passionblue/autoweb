<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

	//>>START<<
	SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute(SiteRegStore.getSessionKey());
	
	if ( siteRegStore == null) {
		String nextJSP = "/jsp/form/sitereg/siteRegStart.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		return;
	} 
    SiteRegPaymentInfo info = siteRegStore.getPayInfo();
	if (info == null) info = new SiteRegPaymentInfo();

	//>>END<<

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
    String _payment_typeValue= (reqParams.get("paymentType")==null?info.getPaymentType():WebUtil.display((String)reqParams.get("paymentType")));
    String _card_typeValue= (reqParams.get("cardType")==null?info.getCardType():WebUtil.display((String)reqParams.get("cardType")));
    String _payment_numValue= (reqParams.get("paymentNum")==null?info.getPaymentNum():WebUtil.display((String)reqParams.get("paymentNum")));
    String _expire_monthValue= (reqParams.get("expireMonth")==null?info.getExpireMonth():WebUtil.display((String)reqParams.get("expireMonth")));
    String _expire_yearValue= (reqParams.get("expireYear")==null?info.getExpireYear():WebUtil.display((String)reqParams.get("expireYear")));
    String _ccvValue= (reqParams.get("ccv")==null?info.getCcv():WebUtil.display((String)reqParams.get("ccv")));
    String _skipValue= (reqParams.get("skip")==null?WebUtil.display(info.getSkip()):WebUtil.display((String)reqParams.get("skip")));


%> 

