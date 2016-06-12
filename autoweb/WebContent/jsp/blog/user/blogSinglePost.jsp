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


	long postId = WebParamUtil.getLongValue(request.getParameter("id"));
	BlogPost post= BlogPostDS.getInstance().getById(postId);
	if ( post == null) {
		Map reqParams = (Map) session.getAttribute("k_reserved_params");
		postId = WebParamUtil.getLongValue((String)reqParams.get("id"));
		post= BlogPostDS.getInstance().getById(postId);
	}
	
	List cats = BlogUtil.getCategories(site.getId(), postId);
	
%>	                    

<%
	if (post == null){ 
%>
	Post not found
	
<% return;
	} %>

<div id="blogShareControl">
	<div class="controlLabel">Source</div>
	<div class="controlEmail">Email</div>
	<div class="controlShare">Share</div>
	<div class="controlTweet">Tweet</div>
	<div class="controlComment">Comments : </div>
</div><div class="clear"></div>


<div id="blogPostSingleFrame">

	<div id="blogPostSingleSubject">
	<%= post.getSubject() %>
	</div>
	

	<div id="blogPostSingleContent">
	<%= post.getContent() %>
	</div>
</div>

<div id="tagsList">
<%
	if (!WebUtil.isNull(post.getTags())){
		String tags[] = post.getTags().split(",");
		for(int i = 0; i <tags.length; i++){
%>
		<div id="tagItem" style="float:left; margin-right:5px"> 
			<a href="#"><%= tags[i].trim() %></a>
		</div>	
<%
		}
	}
%>
</div><div class="clear"></div>
<hr/>

<!-- ============ Comments =============== -->
<div id='blogCommentList'>
<%
	List comments = BlogCommentDS.getInstance().getByBlogPostId(post.getId());
		for (Iterator iterator = comments.iterator(); iterator.hasNext();) {
       	BlogComment blogComm = (BlogComment) iterator.next();
%>
	<div id='blogCommentFrame<%=blogComm.getId()%>'>
		<div id="blogCommentBy"><%= blogComm.getName() %></div>
		<div id="blogCommentTime"><%= blogComm.getTimeCreated() %></div>
		<div id="blogComment">
			<p><%= blogComm.getComment() %> </p>
		</div>
			<!-- a href="/blogCommentAction.html?del=true&id=<%=blogComm.getId()%>&returnPage=blog_single_post,<%=request.getQueryString()%>" > <img src="/images/icons/led/cancel.png" style="float:right"/> </a-->
			<!-- a id="blogCommentDelete" rel="<%=blogComm.getId()%>" href="#" > <img src="/images/icons/led/cancel.png" style="float:right"/> </a -->
			<a id="blogCommentDelete2" title="xx" href="javascript:deleteThis('/blogCommentAction.html',<%= blogComm.getId()%>,'blogCommentFrame<%=blogComm.getId()%>' );" > <img src="/images/icons/led/cancel.png" style="float:right"/> </a>
	</div>
<%
	}
%>
</div>
<A NAME="blogCommentForm"></A>
<div id='blogCommentForm'>
<form name="blogCommentForm" method="POST" action="/blogCommentAction.html" >
        Name (Required)<br/>
        <input id="field" type="text" size="40" name="name" value=""/><input id="field" type="password" size="10" name="password" value=""/><br/>
		Email (Optional)<br/>
        <input id="field" type="text" size="40" name="email" value=""/><br/>
        Comment<br/>
		<TEXTAREA id="field" NAME="comment" COLS="70" ROWS="8" ></TEXTAREA><br/>

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="blogMainId" value="<%=post.getBlogMainId() %>">
<INPUT TYPE="HIDDEN" NAME="blogPostId" value="<%=post.getId() %>">
<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/t_blog_single_post.html?<%=request.getQueryString()%>">

</form>

<a id="formSubmit" href="javascript:document.blogCommentForm.submit();">Submit</a>
</div>
