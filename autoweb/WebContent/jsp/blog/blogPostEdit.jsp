<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    BlogPost _BlogPost = BlogPostDS.getInstance().getById(id);

    if ( _BlogPost == null ) {
        return;
    }

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
%> 

<br>
<form name="blogPostFormEdit" method="post" action="/blogPostAction.html" >
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
            <b><a href="javascript:document.blogPostFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogPost.getId()%>">
</form>
