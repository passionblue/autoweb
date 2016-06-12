<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    BlogPostCategoryRel _BlogPostCategoryRel = BlogPostCategoryRelDS.getInstance().getById(id);

    if ( _BlogPostCategoryRel == null ) {
        return;
    }

    String _blog_post_idValue=  WebUtil.display(_BlogPostCategoryRel.getBlogPostId());
    String _blog_category_idValue=  WebUtil.display(_BlogPostCategoryRel.getBlogCategoryId());
    String _time_createdValue=  WebUtil.display(_BlogPostCategoryRel.getTimeCreated());
%> 

<br>
<form name="blogPostCategoryRelFormEdit" method="post" action="/blogPostCategoryRelAction.html" >
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
            <b><a href="javascript:document.blogPostCategoryRelFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPostCategoryRel.getId()%>">
</form>
