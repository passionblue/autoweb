<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

	List list = new ArrayList();
	BlogPostCategoryRelDS ds = BlogPostCategoryRelDS.getInstance();    
    


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_blog_post_category_rel_add2.html?prv_returnPage=blog_post_category_rel_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="blogPostCategoryRelForm_blogPostId_label" style="font-size: normal normal bold 10px verdana;" >Blog Post Id </div>
    </td> 
    <td> 
	    <div id="blogPostCategoryRelForm_blogCategoryId_label" style="font-size: normal normal bold 10px verdana;" >Blog Category Id </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogPostCategoryRel _BlogPostCategoryRel = (BlogPostCategoryRel) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogPostCategoryRel.getId() %> </td>


    <td> 
	<form name="blogPostCategoryRelFormEditField_BlogPostId_<%=_BlogPostCategoryRel.getId()%>" method="get" action="/blogPostCategoryRelAction.html" >


		<div id="blogPostCategoryRelForm_blogPostId_field">
	    <div id="blogPostCategoryRelForm_blogPostId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogPostId" value="<%=WebUtil.display(_BlogPostCategoryRel.getBlogPostId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostCategoryRelFormEditField_BlogPostId_<%=_BlogPostCategoryRel.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPostCategoryRel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_category_rel_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostCategoryRelFormEditField_BlogCategoryId_<%=_BlogPostCategoryRel.getId()%>" method="get" action="/blogPostCategoryRelAction.html" >


		<div id="blogPostCategoryRelForm_blogCategoryId_field">
	    <div id="blogPostCategoryRelForm_blogCategoryId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogCategoryId" value="<%=WebUtil.display(_BlogPostCategoryRel.getBlogCategoryId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostCategoryRelFormEditField_BlogCategoryId_<%=_BlogPostCategoryRel.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPostCategoryRel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_category_rel_home">
	</form>
    
    
    </td>


    <td>
    <form name="blogPostCategoryRelForm<%=_BlogPostCategoryRel.getId()%>" method="get" action="/v_blog_post_category_rel_edit.html" >
        <a href="javascript:document.blogPostCategoryRelForm<%=_BlogPostCategoryRel.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogPostCategoryRel.getId() %>">
    </form>
    <form name="blogPostCategoryRelForm<%=_BlogPostCategoryRel.getId()%>2" method="get" action="/v_blog_post_category_rel_edit2.html" >
        <a href="javascript:document.blogPostCategoryRelForm<%=_BlogPostCategoryRel.getId()%>2.submit();">Edit2</a>           
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