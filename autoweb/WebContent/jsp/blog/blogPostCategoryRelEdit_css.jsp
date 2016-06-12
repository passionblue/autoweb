<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    BlogPostCategoryRel _BlogPostCategoryRel = BlogPostCategoryRelDS.getInstance().getById(id);

    if ( _BlogPostCategoryRel == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _blog_post_idValue=  WebUtil.display(_BlogPostCategoryRel.getBlogPostId());
    String _blog_category_idValue=  WebUtil.display(_BlogPostCategoryRel.getBlogCategoryId());
    String _time_createdValue=  WebUtil.display(_BlogPostCategoryRel.getTimeCreated());
%> 

<br>
<div id="blogPostCategoryRelForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blogPostCategoryRelFormEdit" method="get" action="/blogPostCategoryRelAction.html" >




	<div id="blogPostCategoryRelForm_blogPostId_field">
    <div id="blogPostCategoryRelForm_blogPostId_label" class="formLabel" >Blog Post Id </div>
    <div id="blogPostCategoryRelForm_blogPostId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blogPostCategoryRelForm_blogCategoryId_field">
    <div id="blogPostCategoryRelForm_blogCategoryId_label" class="formLabel" >Blog Category Id </div>
    <div id="blogPostCategoryRelForm_blogCategoryId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="blogCategoryId" value="<%=WebUtil.display(_blog_category_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="blogPostCategoryRelFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogPostCategoryRelFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPostCategoryRel.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
