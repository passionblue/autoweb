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

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="blogPostForm" method="post" action="/blogPostAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Main Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Category Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Subject</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="subject" value="<%=WebUtil.display(_subjectValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Content</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="content" value="<%=WebUtil.display(_contentValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Post Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="postType" value="<%=WebUtil.display(_post_typeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Author</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="author" value="<%=WebUtil.display(_authorValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Content Image</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="contentImage" value="<%=WebUtil.display(_content_imageValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Image Url1</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="imageUrl1" value="<%=WebUtil.display(_image_url1Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Image Url2</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="imageUrl2" value="<%=WebUtil.display(_image_url2Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Tags</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="tags" value="<%=WebUtil.display(_tagsValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Shorcut Url</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="shorcutUrl" value="<%=WebUtil.display(_shorcut_urlValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide</b> &nbsp;</TD>
        <TD>&nbsp;<select name="hide">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Post Year</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="postYear" value="<%=WebUtil.display(_post_yearValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Post Month</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="postMonth" value="<%=WebUtil.display(_post_monthValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Post Day</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="postDay" value="<%=WebUtil.display(_post_dayValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Post Yearmonth</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="postYearmonth" value="<%=WebUtil.display(_post_yearmonthValue)%>"/></TD>
	    </TR>
	            	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogPostForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="blogPostForm<%=_BlogPost.getId()%>" method="post" action="/v_blog_post_edit.html" >
    <a href="javascript:document.blogPostForm<%=_BlogPost.getId()%>.submit();">Edit</a>           
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