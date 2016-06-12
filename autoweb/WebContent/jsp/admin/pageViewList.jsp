<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	
	List allPages = com.jtrend.struts.core.DefaultViewManager.getInstance().getPageObjectList();
	
	
%> 


<%
	for(Iterator iter = allPages.iterator(); iter.hasNext();){
	    PageView pv = (PageView) iter.next();
	    String link = "v_" + pv.getAlias() + ".html";
%>	    
		<a href="<%=link %>" > <%=pv.getAlias() %></a><br/>
		
<%		
	}
%>
