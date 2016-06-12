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

<% if (blogMain == null) {  %>

<%     	if ( wrap.getSessionCtx().isSiteAdmin() ){ %>
		<a href="/v_blog_main_add2.html?prv_blogName=<%=dynPage.getPageName()%>&prv_returnPage=<%=wrap.getViewPage().getAlias()%>"> Initialize Blog For this page </a>		
<%     	} else { %>
	<H3>Need init</H3>
<%	   	} %>
<% 
		return;
	} 
%>	

	<div id="blogMainTitle"><%= blogMain.getBlogTitle() %></div>
	
<% 	if ( wrap.getSessionCtx().isSiteAdmin() ){ %>

<table width="100%" class="blogMenuTable">
<tr class="blogMenuTable">
	<td class="blogMenuTable">
		<a href="/v_blog_category_add2.html?prv_blogMainId=<%=blogMain.getId()%>&prv_returnPage=<%=wrap.getViewPage().getAlias()%>"> Create New Category </a> 
	</td>
	<td class="blogMenuTable">
		<form name="blogMainFormEditField_BlogTitle_<%=blogMain.getId()%>" method="get" action="/blogMainAction.html" style="margin: 0px">
	        <input id="field" type="text" size="20" name="blogTitle" value="<%=WebUtil.display(blogMain.getBlogTitle())%>"/>
	            <a id="formSubmit" href="javascript:document.blogMainFormEditField_BlogTitle_<%=blogMain.getId()%>.submit();">Change Blog Title</a>
				<a href="/v_blog_home.html">Blog Home</a>
		<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
		<INPUT TYPE="HIDDEN" NAME="id" value="<%=blogMain.getId()%>">
		<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=wrap.getViewPage().getAlias()%>">
		</form>					
	</td>
</tr>
</table>



<h4> Categories</h4>
<%
	List blogCats = BlogCategoryDS.getInstance().getByBlogMainId(blogMain.getId());
	for(Iterator iter = blogCats.iterator();iter.hasNext();){
		BlogCategory cat = (BlogCategory) iter.next();
%>

	<%= cat.getTitle() %>
	
	<a href="v_blog_post_add2.html?prv_categoryId=<%=cat.getId() %>&prv_blogMainId=<%=blogMain.getId()%>&prv_returnPage=<%=wrap.getViewPage().getAlias()%>"> Add Blog Post</a>|
	<a href="v_blog_category_edit2.html?id=<%=cat.getId() %>&prv_returnPage=<%=wrap.getViewPage().getAlias()%>">Edit Category</a><br/>	
<%
	}
%>
<hr/><br/>

<% } %>


<div id="blogListFrame">
<%
	List blogPosts = BlogPostDS.getInstance().getByBlogMainId(blogMain.getId());
	for(Iterator iter = blogPosts.iterator();iter.hasNext();){
		BlogPost post = (BlogPost) iter.next();
		
		BlogCategory catForThis = BlogCategoryDS.getInstance().getById(post.getCategoryId());
		
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
		<div id="blogPostCategory"><%= catForThis.getTitle()%></div>
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
		<div id="blogPostContentLink">
			<a href="/t_blog_single_post.html?id=<%=post.getId() %>&subject=<%= post.getSubject()%>">read more</a> &nbsp;
			<a href="/t_blog_single_post.html?id=<%=post.getId() %>&subject=<%= post.getSubject()%>#blogCommentForm">post comment</a>
		</div>	
	</div>


<%
	List comments = BlogCommentDS.getInstance().getBySiteIdBlogPostId(site.getId(), post.getId());
%>

<%
	if (comments.size() > 0){
%>

	<div style="float:left"><a id="blogOpenComments" href="#" title="<%=post.getId() %>" >  comments [<%=comments.size()%>] </a></div>
	<div id="commentLoading<%=post.getId() %>" style="float:left;display:none;" ><img src="/images/loader/arrows16.gif"></div><div class="clear"></div>
	<div id="blogPostCommentFrame<%=post.getId() %>" style="min-height: 0px; padding: 0px 30px 0px 30px;"></div>
 	<div id="blogPostCommentFrame<%=post.getId() %>OpenClose" style="display:none;">0</div>
<% } else { %>
	<div style="float:left"> comments [0] </div><br/>

<% } %>

</div>

<%
	} // isHide
%>







<%
	} // Blog Loop
%>
</div>