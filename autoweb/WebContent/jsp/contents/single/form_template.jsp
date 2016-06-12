<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);

	SessionWrapper wrap = sessionContext.getSessionWrapper();

	Page dynPage = wrap.getPage();
	PageConfig pageConfig = null;
	if (dynPage != null)
		pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	PageView pageView = wrap.getViewPage();


    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

