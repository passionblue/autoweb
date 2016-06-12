<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlogComment _BlogCommentDefault = new BlogComment();// BlogCommentDS.getInstance().getDeafult();
    
    String _blog_main_idValue= (reqParams.get("blogMainId")==null?WebUtil.display(_BlogCommentDefault.getBlogMainId()):WebUtil.display((String)reqParams.get("blogMainId")));
    String _blog_post_idValue= (reqParams.get("blogPostId")==null?WebUtil.display(_BlogCommentDefault.getBlogPostId()):WebUtil.display((String)reqParams.get("blogPostId")));
    String _commentValue= (reqParams.get("comment")==null?WebUtil.display(_BlogCommentDefault.getComment()):WebUtil.display((String)reqParams.get("comment")));
    String _ratingValue= (reqParams.get("rating")==null?WebUtil.display(_BlogCommentDefault.getRating()):WebUtil.display((String)reqParams.get("rating")));
    String _ipaddressValue= (reqParams.get("ipaddress")==null?WebUtil.display(_BlogCommentDefault.getIpaddress()):WebUtil.display((String)reqParams.get("ipaddress")));
    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_BlogCommentDefault.getName()):WebUtil.display((String)reqParams.get("name")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_BlogCommentDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlogCommentDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="blogCommentForm" method="post" action="/blogCommentAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Main Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Post Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Comment</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="comment" value="<%=WebUtil.display(_commentValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Rating</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="rating" value="<%=WebUtil.display(_ratingValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Ipaddress</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="ipaddress" value="<%=WebUtil.display(_ipaddressValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="name" value="<%=WebUtil.display(_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Email</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogCommentForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlogCommentDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogComment _BlogComment = (BlogComment) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogComment.getId() %> </td>

    <td> <%= WebUtil.display(_BlogComment.getBlogMainId()) %></td>
    <td> <%= WebUtil.display(_BlogComment.getBlogPostId()) %></td>
    <td> <%= WebUtil.display(_BlogComment.getComment()) %></td>
    <td> <%= WebUtil.display(_BlogComment.getRating()) %></td>
    <td> <%= WebUtil.display(_BlogComment.getIpaddress()) %></td>
    <td> <%= WebUtil.display(_BlogComment.getName()) %></td>
    <td> <%= WebUtil.display(_BlogComment.getEmail()) %></td>
    <td> <%= WebUtil.display(_BlogComment.getTimeCreated()) %></td>


<td>
<form name="blogCommentForm<%=_BlogComment.getId()%>" method="post" action="/v_blog_comment_edit.html" >
    <a href="javascript:document.blogCommentForm<%=_BlogComment.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogComment.getId() %>">
</form>
</td>
<td>
<a href="/blogCommentAction.html?del=true&id=<%=_BlogComment.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>