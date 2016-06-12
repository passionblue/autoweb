<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<% 
	long columnNum = 21;

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	ContentFeedSite _ContentFeedSite = ContentFeedSiteDS.getInstance().getObjectBySiteId(site.getId());
	long contentFeedId = (_ContentFeedSite == null?0:_ContentFeedSite.getContentFeedId());


	if (_ContentFeedSite == null) return;
%>

<%

	List feedPanels = PanelDS.getInstance().getByFeedId(contentFeedId);
		
	for(Iterator iter = feedPanels.iterator();iter.hasNext();){
		Panel panel = (Panel) iter.next();	
%>

		<% session.setAttribute("p_current_panel", panel); %>
		<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>" />
		<% session.removeAttribute("p_current_panel"); %>
	
<%
	}
%>










<% 
	if (sessionContext.isSuperAdminLogin()) {
%>
<div>
		[<a href="t_panel_add.html?prv_columnNum=21&prv_align=0&prv_topSpace=0&prv_feedId=<%= contentFeedId%>" ><b>21+</b></a>]
</div>
		
<% 
	}
%>






