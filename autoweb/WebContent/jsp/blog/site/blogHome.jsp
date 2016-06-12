<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


<% 
   	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

%> 


<%
	List blogMains = BlogMainDS.getInstance().getBySiteId(site.getId());

	for(Iterator iter = blogMains.iterator();iter.hasNext();){
		BlogMain blogMain = (BlogMain) iter.next();
		
%>
	<b><%= blogMain.getBlogTitle() %> </b> in Page <a href="/m_<%=blogMain.getBlogName()%>.html" > <%=blogMain.getBlogName()%> </a> <br/>

<%
	}
%>
<a href="/v_blog_category_home.html"> Category Home </a> <br/>
<a href="/v_blog_config_home.html"> Config Home </a> <br/>
<a href="/v_blog_post_home.html"> Post Home </a> <br/>
<a href="/v_blog_comment_home.html"> Comment Home </a> <br/>