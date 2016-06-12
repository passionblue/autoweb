<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

    Map reqParams = (Map) request.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    CleanerRegisterStartDataHolder _CleanerRegisterStart = null;
    
    String subname = (String) session.getAttribute("autosite.session.cleaner.register.subsite");
    AutositeUser subSiteUser = (AutositeUser) session.getAttribute("autosite.session.cleaner.register.user");
    Site subSiteObj = (Site) session.getAttribute("autosite.session.cleaner.register.subsiteObj");

    java.net.URL url = new java.net.URL(request.getRequestURL().toString());

    
    InstantAccessToken token = InstantAccessTokenManager.createLoginToken(site.getSiteUrl(), session.getId(),  subname + "." + site.getSiteUrl(), subSiteUser, 60000);
    
%> 

<h1> <a href="http://<%= subname + "." + site.getSiteUrl()  + ":" + url.getPort()%>?_instaid=<%= token.getTokenId()%>"> <%= subname + "." + site.getSiteUrl()%> </a> </h1>