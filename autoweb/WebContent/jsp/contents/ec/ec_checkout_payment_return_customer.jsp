<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.session.*,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	PageDS pageDS = PageDS.getInstance();

	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	PageConfig pageConfig = null;
	if (dynPage != null)
		pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
	
    String _emailValue= (reqParams.get("email")==null?"":WebUtil.display((String)reqParams.get("email")));
    String _email_retypeValue= (reqParams.get("emailRetype")==null?"":WebUtil.display((String)reqParams.get("emailRetype")));
    String _first_nameValue= (reqParams.get("firstName")==null?"":WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?"":WebUtil.display((String)reqParams.get("lastName")));
    String _address1Value= (reqParams.get("address1")==null?"":WebUtil.display((String)reqParams.get("address1")));
    String _address2Value= (reqParams.get("address2")==null?"":WebUtil.display((String)reqParams.get("address2")));
    String _cityValue= (reqParams.get("city")==null?"":WebUtil.display((String)reqParams.get("city")));
    String _country_regionValue= (reqParams.get("countryRegion")==null?"":WebUtil.display((String)reqParams.get("countryRegion")));
    String _state_provinceValue= (reqParams.get("stateProvince")==null?"":WebUtil.display((String)reqParams.get("stateProvince")));
    String _zipValue= (reqParams.get("zip")==null?"":WebUtil.display((String)reqParams.get("zip")));
    String _phoneValue= (reqParams.get("phone")==null?"":WebUtil.display((String)reqParams.get("phone")));
    String _use_billingValue= (reqParams.get("useBilling")==null?"":WebUtil.display((String)reqParams.get("useBilling")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<h1> Payment Info Return Customer </h1>