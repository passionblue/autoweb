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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="blogPostCategoryRelForm_topArea" class="formTopArea"></div>
<div id="blogPostCategoryRelForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="blogPostCategoryRelForm" method="get" action="/blogPostCategoryRelAction.html" >




	<div id="blogPostCategoryRelForm_blogPostId_field">
    <div id="blogPostCategoryRelForm_blogPostId_label" class="formLabel" >Blog Post Id </div>
    <div id="blogPostCategoryRelForm_blogPostId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="blogPostCategoryRelForm_blogCategoryId_field">
    <div id="blogPostCategoryRelForm_blogCategoryId_label" class="formLabel" >Blog Category Id </div>
    <div id="blogPostCategoryRelForm_blogCategoryId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="blogCategoryId" value="<%=WebUtil.display(_blog_category_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="blogPostCategoryRelForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogPostCategoryRelForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="blogPostCategoryRelForm_bottomArea" class="formBottomArea"></div>


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