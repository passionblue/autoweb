<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

	List list = new ArrayList();
	BlogPostDS ds = BlogPostDS.getInstance();    
    if (blogMainId > 0) 
		list = ds.getByBlogMainId(blogMainId);
	else
		list = ds.getBySiteId(site.getId());


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_blog_post_add2.html?prv_returnPage=blog_post_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="blogPostForm_blogMainId_label" style="font-size: normal normal bold 10px verdana;" >Blog Main Id </div>
    </td> 
    <td> 
	    <div id="blogPostForm_categoryId_label" style="font-size: normal normal bold 10px verdana;" >Category Id </div>
    </td> 
    <td> 
	    <div id="blogPostForm_subject_label" style="font-size: normal normal bold 10px verdana;" >Subject </div>
    </td> 
    <td> 
	    <div id="blogPostForm_content_label" style="font-size: normal normal bold 10px verdana;" >Content </div>
    </td> 
    <td> 
	    <div id="blogPostForm_postType_label" style="font-size: normal normal bold 10px verdana;" >Post Type </div>
    </td> 
    <td> 
	    <div id="blogPostForm_author_label" style="font-size: normal normal bold 10px verdana;" >Author </div>
    </td> 
    <td> 
	    <div id="blogPostForm_contentImage_label" style="font-size: normal normal bold 10px verdana;" >Content Image </div>
    </td> 
    <td> 
	    <div id="blogPostForm_imageUrl1_label" style="font-size: normal normal bold 10px verdana;" >Image Url1 </div>
    </td> 
    <td> 
	    <div id="blogPostForm_imageUrl2_label" style="font-size: normal normal bold 10px verdana;" >Image Url2 </div>
    </td> 
    <td> 
	    <div id="blogPostForm_tags_label" style="font-size: normal normal bold 10px verdana;" >Tags </div>
    </td> 
    <td> 
	    <div id="blogPostForm_shorcutUrl_label" style="font-size: normal normal bold 10px verdana;" >Shorcut Url </div>
    </td> 
    <td> 
	    <div id="blogPostForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogPost _BlogPost = (BlogPost) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogPost.getId() %> </td>


    <td> 
	<form name="blogPostFormEditField_BlogMainId_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_blogMainId_field">
	    <div id="blogPostForm_blogMainId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogMainId" value="<%=WebUtil.display(_BlogPost.getBlogMainId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_BlogMainId_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_CategoryId_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >

		<div id="blogPostForm_categoryId_field">
	    <div id="blogPostForm_categoryId_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="categoryId">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _BlogPost.getCategoryId())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_CategoryId_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_Subject_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_subject_field">
	    <div id="blogPostForm_subject_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="subject" value="<%=WebUtil.display(_BlogPost.getSubject())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_Subject_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_Content_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_content_field">
	    <div id="blogPostForm_content_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="content" value="<%=WebUtil.display(_BlogPost.getContent())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_Content_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_PostType_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >

		<div id="blogPostForm_postType_field">
	    <div id="blogPostForm_postType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="postType">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _BlogPost.getPostType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_PostType_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_Author_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_author_field">
	    <div id="blogPostForm_author_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="author" value="<%=WebUtil.display(_BlogPost.getAuthor())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_Author_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_ContentImage_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_contentImage_field">
	    <div id="blogPostForm_contentImage_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentImage" value="<%=WebUtil.display(_BlogPost.getContentImage())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_ContentImage_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_ImageUrl1_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_imageUrl1_field">
	    <div id="blogPostForm_imageUrl1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="imageUrl1" value="<%=WebUtil.display(_BlogPost.getImageUrl1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_ImageUrl1_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_ImageUrl2_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_imageUrl2_field">
	    <div id="blogPostForm_imageUrl2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="imageUrl2" value="<%=WebUtil.display(_BlogPost.getImageUrl2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_ImageUrl2_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_Tags_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_tags_field">
	    <div id="blogPostForm_tags_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="tags" value="<%=WebUtil.display(_BlogPost.getTags())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_Tags_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_ShorcutUrl_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_shorcutUrl_field">
	    <div id="blogPostForm_shorcutUrl_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="shorcutUrl" value="<%=WebUtil.display(_BlogPost.getShorcutUrl())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_ShorcutUrl_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogPostFormEditField_Hide_<%=_BlogPost.getId()%>" method="get" action="/blogPostAction.html" >


		<div id="blogPostForm_hide_field">
	    <div id="blogPostForm_hide_dropdown" class="formFieldDropDown" >       
	        <select name="hide">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _BlogPost.getHide())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _BlogPost.getHide())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogPostFormEditField_Hide_<%=_BlogPost.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_post_home">
	</form>
    
    
    </td>







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