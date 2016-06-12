<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.session.*,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	int columnNumForPage = 10;
%>
<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	String pageName = (String) session.getAttribute("k_page_name");
	String categoryName = (String) session.getAttribute("k_page_category");
	
	Page dynPage = DynPageUtil.getCurrentPage(request, site.getId());
	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());
	
	
%>	                
<h3> This is mobile dynamic three</h3>     This is mobile dynamic three
<!-- ########################################################################################## -->
<%

	List pagePanels = PanelDS.getInstance().getBySiteIdColumnNum(site.getId(), columnNumForPage);
	PanelPageConfigDS panelPageConfigDS = PanelPageConfigDS.getInstance();
	
	for (Iterator iter = pagePanels.iterator();iter.hasNext();){
		Panel panel = (Panel) iter.next();
		
		if ( panel.getPageId() != dynPage.getId()) continue;
%>
		<% session.setAttribute("p_current_panel", panel); %>
		<jsp:include page="/jsp/contents/content_panel/panel_dynamic_menu_content_section.jsp" />
		<% session.removeAttribute("p_current_panel"); %>
<%   

	} // panels
%> 
<% if (sessionContext != null && sessionContext.isSuperAdmin()) { %>
<br><br>
	<a href="t_panel_add.html?prv_columnNum=<%=columnNumForPage%>&prv_pageOnly=0&prv_align=1&prv_panelType=10&prv_topSpace=0&prv_pageId=<%=dynPage.getId()%>" class="pageMenuText">Add Panel</a> | 
	<% if (pageConfig == null){ %>
		<a href="/t_page_config_add.html?prv_pageId=<%=dynPage.getId()%>" class="pageMenuText">Add Page Config</a>|
	<% }else { %>
		<a href="/v_page_config_edit.html?id=<%=pageConfig.getId()%>" class="pageMenuText">Edit Page Config</a>|
	<% }%>
<% } %>
