<%@page import="com.jtrend.struts.core.*,com.jtrend.session.*"%>
<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	ViewManager vm = DefaultViewManager.getInstance();
	List pageList = DefaultViewManager.getInstance().getPageList();

%>


<table class="mytable">

<%
	for(Iterator iter = pageList.iterator(); iter.hasNext();) {
	    String name = (String) iter.next();
	    
	//	    if ( ! name.endsWith("home")) continue;

	    
	    PageView pv = vm.getPageView(name, site.getSiteUrl());
	    
%>
<tr>
	<td>
		<a href="/v_<%= name  %>.html"> <%= name %></a> 
	</td>	
	<td>
		<%= pv== null? "": pv.getContentPage()  %>
	</td>	
	<td>
		<%= pv== null? "": WebUtil.display(pv.getTentativeAlt()) %>
	</td>	
</tr>
<%
	}
%>

</table>