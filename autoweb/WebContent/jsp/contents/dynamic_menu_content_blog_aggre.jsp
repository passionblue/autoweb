<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.*,com.autosite.util.*,com.autosite.session.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SessionWrapper wrap = SessionWrapper.wrapIt(request, site.getId());
	
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	Page dynPage = wrap.getPage();
	
	if (dynPage == null) return;
	
	// Page option
	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());
	boolean isLogin = (sessionContext != null && sessionContext.isLogin());
	
	BlogMain blogMain = BlogMainDS.getInstance().getBySiteIdBlogName(site.getId(), dynPage.getPageName());
	
	// First check if page pointed by menu selection is init for blog. If not check request parameter
	if (blogMain == null){
		String blogName = request.getParameter("blogName");
		if (blogName != null)
			blogMain = BlogMainDS.getInstance().getBySiteIdBlogName(site.getId(), blogName);
	}
	
%>	                    

<div id="blogListFrame">
<%
	List blogPosts = BlogPostDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = blogPosts.iterator();iter.hasNext();){

		BlogPost post = (BlogPost) iter.next();
		BlogCategory catForThis = BlogCategoryDS.getInstance().getById(post.getCategoryId());
		BlogMain blogMainForThis = BlogMainDS.getInstance().getById(catForThis.getBlogMainId());
		Page pageForThis = PageDS.getInstance().getBySiteIdPageName(site.getId(), blogMainForThis.getBlogName());		
%>


<% 	if ( wrap.getSessionCtx().isSiteAdmin() ){ %>
	<div id="blogPostControl">
		<a href="/t_blog_post_edit2.html?id=<%=post.getId()%>&prv_returnPage=<%=wrap.getViewPage().getAlias()%>"> edit </a>|
		<a href="/blogPostAction.html?del=true&id=<%=post.getId() %>&returnPage=<%=wrap.getViewPage().getAlias()%>"> delete </a> |
		<a href="/blogPostAction.html?ef=true&id=<%=post.getId() %>&hide=1&returnPage=<%=wrap.getViewPage().getAlias()%>"> hide </a> |
		<a href="/blogPostAction.html?ef=true&id=<%=post.getId() %>&hide=0&returnPage=<%=wrap.getViewPage().getAlias()%>"> show </a>
	</div>
	
<%	
	} 
%>


<%
	if ( WebUtil.isTrue(post.getHide())){
		if ( wrap.getSessionCtx().isSiteAdmin() ){
%>
<div id="blogPostFrame">
	<div id="blogPostHeadingHidden">
		<div id="blogPostSubject"><%= post.getSubject() %></div>
		<div id="blogPostCategory"><%= catForThis.getTitle()%> in <%= pageForThis.getPageMenuTitle() %></div>
		<div id="blogPostTime"><%= post.getTimeCreated() %> updated:<%= post.getTimeUpdated() %></div>
	</div>
</div>
<%
		}	
	} else {
%>

<div id="blogPostFrame">
	<div id="blogPostHeading">
		<div id="blogPostSubject"><%= post.getSubject() %></div>
		<div id="blogPostCategory">Category:<%= catForThis.getTitle()%></div>
		<div id="blogPostTime">Created: <%= post.getTimeCreated() %> &nbsp;Updated:<%= post.getTimeUpdated() %></div>
	</div>
	
	<div id="blogPostBody">
		<div id="blogPostContent"><%= post.getContent() %></div>
	</div>
	
	<div id="blogPostFooter">
		<div id="blogPostTags">Tags : <%=post.getTags() %></div>
		<div id="blogPostContentLink"><a href="/t_blog_single_post.html?id=<%=post.getId() %>&subject=<%= post.getSubject()%>">>>read more</a></div>
	</div>
</div>

<%
	}
%>

<%
	}
%>
</div>