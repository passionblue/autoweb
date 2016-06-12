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
	
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());
	
	
%>	                    

<!-- ########################################################################################## -->

<%

	List pageContents = ContentDS.getInstance().getByPageId(dynPage.getId());
	
	for (Iterator iter = pageContents.iterator();iter.hasNext();){
		Content con = (Content) iter.next();

		
		String link  = null;
		if (  WebUtil.isNull(con.getShortcutUrl()))
			link =  "t_dyn_content_single.html?cid=" + con.getId() + "&pid=" + dynPage.getId();
		else 
			link = "/"+dynPage.getPageName() + "/" + con.getShortcutUrl() + ".html";
		
%>
	<a href="<%= link %>"><%= con.getContentSubject() %></a><br/>
<%   
    } // panels
%> 

<% if (sessionContext != null && sessionContext.isSuperAdmin()) { %>
<br><br>
		<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>&prv_contentType=1" > <b>ADD CONTENT (<%= dynPage.getPageMenuTitle()%>/<%= dynPage.getId()%>)</b></a>|
	<% if (pageConfig == null){ %>
		<a href="/t_page_config_add.html?prv_pageId=<%=dynPage.getId()%>" class="pageMenuText">Add Page Config</a>
	<% }else { %>
		<a href="/v_page_config_edit.html?id=<%=pageConfig.getId()%>" class="pageMenuText">Edit Page Config</a>
	<% }%>
<% } %>



