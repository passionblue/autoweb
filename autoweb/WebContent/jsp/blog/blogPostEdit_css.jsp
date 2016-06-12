<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.servlet.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    BlogPost _BlogPost = BlogPostDS.getInstance().getById(id);

    if ( _BlogPost == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _blog_main_idValue=  WebUtil.display(_BlogPost.getBlogMainId());
    String _category_idValue=  WebUtil.display(_BlogPost.getCategoryId());
    String _subjectValue=  WebUtil.display(_BlogPost.getSubject());
    String _contentValue=  WebUtil.display(_BlogPost.getContent());
    String _post_typeValue=  WebUtil.display(_BlogPost.getPostType());
    String _authorValue=  WebUtil.display(_BlogPost.getAuthor());
    String _content_imageValue=  WebUtil.display(_BlogPost.getContentImage());
    String _image_url1Value=  WebUtil.display(_BlogPost.getImageUrl1());
    String _image_url2Value=  WebUtil.display(_BlogPost.getImageUrl2());
    String _tagsValue=  WebUtil.display(_BlogPost.getTags());
    String _shorcut_urlValue=  WebUtil.display(_BlogPost.getShorcutUrl());
    String _hideValue=  WebUtil.display(_BlogPost.getHide());
    String _post_yearValue=  WebUtil.display(_BlogPost.getPostYear());
    String _post_monthValue=  WebUtil.display(_BlogPost.getPostMonth());
    String _post_dayValue=  WebUtil.display(_BlogPost.getPostDay());
    String _post_yearmonthValue=  WebUtil.display(_BlogPost.getPostYearmonth());
    String _time_createdValue=  WebUtil.display(_BlogPost.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_BlogPost.getTimeUpdated());
    
    //>>START
    SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());
    int contentWidth = StyleUtil.calculateContentWidth(siteConfigTrans, siteConfig);
    
%> 

<% if (false&& siteConfig.getUseWysiwygContent() == 1) { %>
<script type="text/javascript" src="/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
window.onload = function()
{
	// Automatically calculates the editor base path based on the _samples directory.
	// This is usefull only for these samples. A real application should use something like this:
	// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
	//var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
	var sBasePath = '/fckeditor/' ;

	var oFCKeditor = new FCKeditor( 'content' ) ;
	oFCKeditor.BasePath	= sBasePath ;
	oFCKeditor.Height = '600';
	oFCKeditor.Width = '<%=contentWidth-40%>';
	oFCKeditor.ReplaceTextarea('mainContent') ;
}
</script>
<%} %>

<br>
<div id="blogPostForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blogPostFormEdit" method="post" action="/blogPostAction.html" >


    <INPUT TYPE="HIDDEN" NAME="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>" />

    <INPUT TYPE="HIDDEN" NAME="categoryId" value="<%=WebUtil.display(_category_idValue)%>" />


	Subject<br/>
    <input id="requiredField" type="text" size="80" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/><br/>
     
	Content<br/>
	<TEXTAREA id="editor1" NAME="content" cols="80" style="width: 100%"><%=WebUtil.display(_contentValue)%></TEXTAREA>
	<script type="text/javascript">
				//<![CDATA[
					// This call can be placed at any point after the
					// <textarea>, or inside a <head><script> in a
					// window.onload event handler.

					// Replace the <textarea id="editor"> with an CKEditor
					// instance, using default configurations.
					CKEDITOR.replace( 'editor1' );
				//]]>
	</script>
	
	Post Type<br/>
	
	<select id="requiredField" name="postType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _post_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _post_typeValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _post_typeValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _post_typeValue)%>>2</option>
    </select><br/>
    
    
	
	Author<br/>
        <input id="field" type="text" size="30" name="author" value="<%=WebUtil.display(_authorValue)%>"/><br/>

   Content Image <br/>
        <input id="field" type="text" size="80" name="contentImage" value="<%=WebUtil.display(_content_imageValue)%>"/><br/>

    Image Url1<br/>
        <input id="field" type="text" size="80" name="imageUrl1" value="<%=WebUtil.display(_image_url1Value)%>"/><br/>

    Image Url2<br/>
        <input id="field" type="text" size="80" name="imageUrl2" value="<%=WebUtil.display(_image_url2Value)%>"/><br/>

    Tags<br/>
        <input id="field" type="text" size="80" name="tags" value="<%=WebUtil.display(_tagsValue)%>"/><br/>

    Shortcut Url<br/>
        <input id="field" type="text" size="80" name="shorcutUrl" value="<%=WebUtil.display(_shorcut_urlValue)%>"/><br/>

    Hide<br/>
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select><br/>

        <div id="blogPostFormEdit_submit" class="formSubmit" >       
            <a href="javascript:document.blogPostFormEdit.submit();">Submit</a>
        </div>      

        <div id="blogPostFormEdit_submit" class="formCancel" >       
            <a href="">Cancel</a>
        </div>      


<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
