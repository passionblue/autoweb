<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();
    String subname = (String) session.getAttribute("autosite.session.cleaner.register.subsite");

%> 

<div class="list-frame" >
<%
	List list = SiteDS.getInstance().getByBaseSiteId(site.getId());
	java.net.URL url = new java.net.URL(request.getRequestURL().toString());
	
	for(Iterator iter = list.iterator();iter.hasNext();){
	    Site subsite = (Site) iter.next();
	    
	    
%>
	<div class="list-item">
		<a href="http://<%= subsite.getSiteUrl()  + ":" + url.getPort()%>" target="_blank"> <%= subsite.getSiteUrl()%> </a> 
	</div>
<%	    
	}
%>
</div>