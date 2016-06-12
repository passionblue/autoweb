<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.ec.*,java.util.*,com.autosite.util.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);

	int userType = (sessionContext != null? sessionContext.getUserType(): Constants.UserAnonymous);
	boolean isLogin = (sessionContext != null? sessionContext.isLogin():false);

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	PageView pageView = (PageView) session.getAttribute("k_view_pageview");
	if ( pageView == null) {
		pageView = PageView.getDefaultPageView();
	}
	
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);

	String continueShoppingLink = "";
	if (dynPage == null) {
		continueShoppingLink = "home.html";
	} else {
		continueShoppingLink = "m_" + dynPage.getPageName() + ".html"; 
	}
%>

<%
    String rpcId = (String) session.getAttribute("k_RPCI");
    EcCart cart = EcCartManager.getCartMakeSure(sessionContext, rpcId, site.getId());

%>

