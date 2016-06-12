<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlogPost _BlogPostDefault = new BlogPost();// BlogPostDS.getInstance().getDeafult();
    
    String _blog_main_idValue= (reqParams.get("blogMainId")==null?WebUtil.display(_BlogPostDefault.getBlogMainId()):WebUtil.display((String)reqParams.get("blogMainId")));
    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_BlogPostDefault.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _subjectValue= (reqParams.get("subject")==null?WebUtil.display(_BlogPostDefault.getSubject()):WebUtil.display((String)reqParams.get("subject")));
    String _contentValue= (reqParams.get("content")==null?WebUtil.display(_BlogPostDefault.getContent()):WebUtil.display((String)reqParams.get("content")));
    String _post_typeValue= (reqParams.get("postType")==null?WebUtil.display(_BlogPostDefault.getPostType()):WebUtil.display((String)reqParams.get("postType")));
    String _authorValue= (reqParams.get("author")==null?WebUtil.display(_BlogPostDefault.getAuthor()):WebUtil.display((String)reqParams.get("author")));
    String _content_imageValue= (reqParams.get("contentImage")==null?WebUtil.display(_BlogPostDefault.getContentImage()):WebUtil.display((String)reqParams.get("contentImage")));
    String _image_url1Value= (reqParams.get("imageUrl1")==null?WebUtil.display(_BlogPostDefault.getImageUrl1()):WebUtil.display((String)reqParams.get("imageUrl1")));
    String _image_url2Value= (reqParams.get("imageUrl2")==null?WebUtil.display(_BlogPostDefault.getImageUrl2()):WebUtil.display((String)reqParams.get("imageUrl2")));
    String _tagsValue= (reqParams.get("tags")==null?WebUtil.display(_BlogPostDefault.getTags()):WebUtil.display((String)reqParams.get("tags")));
    String _shorcut_urlValue= (reqParams.get("shorcutUrl")==null?WebUtil.display(_BlogPostDefault.getShorcutUrl()):WebUtil.display((String)reqParams.get("shorcutUrl")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_BlogPostDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _post_yearValue= (reqParams.get("postYear")==null?WebUtil.display(_BlogPostDefault.getPostYear()):WebUtil.display((String)reqParams.get("postYear")));
    String _post_monthValue= (reqParams.get("postMonth")==null?WebUtil.display(_BlogPostDefault.getPostMonth()):WebUtil.display((String)reqParams.get("postMonth")));
    String _post_dayValue= (reqParams.get("postDay")==null?WebUtil.display(_BlogPostDefault.getPostDay()):WebUtil.display((String)reqParams.get("postDay")));
    String _post_yearmonthValue= (reqParams.get("postYearmonth")==null?WebUtil.display(_BlogPostDefault.getPostYearmonth()):WebUtil.display((String)reqParams.get("postYearmonth")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlogPostDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_BlogPostDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<% if (false && siteConfig.getUseWysiwygContent() == 1) { %>
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
	oFCKeditor.Width = '550';
	oFCKeditor.ReplaceTextarea('mainContent') ;
}
</script>
<%} %>


<br>
<div id="blogPostForm_topArea" class="formTopArea"></div>
<div id="blogPostForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="blogPostForm" method="post" action="/blogPostAction.html" >

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





        <div id="blogPostForm_submit" class="formSubmit" >       
            <a  href="javascript:document.blogPostForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="blogPostForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlogPostDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogPost _BlogPost = (BlogPost) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogPost.getId() %> </td>

    <td> <%= WebUtil.display(_BlogPost.getBlogMainId()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getCategoryId()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getSubject()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getContent()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getPostType()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getAuthor()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getContentImage()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getImageUrl1()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getImageUrl2()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getTags()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getShorcutUrl()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getHide()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getPostYear()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getPostMonth()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getPostDay()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getPostYearmonth()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_BlogPost.getTimeUpdated()) %></td>


<td>
<form name="blogPostForm<%=_BlogPost.getId()%>" method="get" action="/v_blog_post_edit.html" >
    <a href="javascript:document.blogPostForm<%=_BlogPost.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogPost.getId() %>">
</form>
<form name="blogPostForm<%=_BlogPost.getId()%>2" method="get" action="/v_blog_post_edit2.html" >
    <a href="javascript:document.blogPostForm<%=_BlogPost.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogPost.getId() %>">
</form>

</td>
<td>
<a href="/blogPostAction.html?del=true&id=<%=_BlogPost.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>