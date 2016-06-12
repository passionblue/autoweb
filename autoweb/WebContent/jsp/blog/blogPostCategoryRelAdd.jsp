<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlogPostCategoryRel _BlogPostCategoryRelDefault = new BlogPostCategoryRel();// BlogPostCategoryRelDS.getInstance().getDeafult();
    
    String _blog_post_idValue= (reqParams.get("blogPostId")==null?WebUtil.display(_BlogPostCategoryRelDefault.getBlogPostId()):WebUtil.display((String)reqParams.get("blogPostId")));
    String _blog_category_idValue= (reqParams.get("blogCategoryId")==null?WebUtil.display(_BlogPostCategoryRelDefault.getBlogCategoryId()):WebUtil.display((String)reqParams.get("blogCategoryId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlogPostCategoryRelDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="blogPostCategoryRelForm" method="post" action="/blogPostCategoryRelAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Post Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Category Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogCategoryId" value="<%=WebUtil.display(_blog_category_idValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogPostCategoryRelForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlogPostCategoryRelDS.getInstance().getAll();
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogPostCategoryRel.getId() %> </td>

    <td> <%= WebUtil.display(_BlogPostCategoryRel.getBlogPostId()) %></td>
    <td> <%= WebUtil.display(_BlogPostCategoryRel.getBlogCategoryId()) %></td>
    <td> <%= WebUtil.display(_BlogPostCategoryRel.getTimeCreated()) %></td>


<td>
<form name="blogPostCategoryRelForm<%=_BlogPostCategoryRel.getId()%>" method="post" action="/v_blog_post_category_rel_edit.html" >
    <a href="javascript:document.blogPostCategoryRelForm<%=_BlogPostCategoryRel.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogPostCategoryRel.getId() %>">
</form>
</td>
<td>
<a href="/blogPostCategoryRelAction.html?del=true&id=<%=_BlogPostCategoryRel.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>