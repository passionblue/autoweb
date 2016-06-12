<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    BlogComment _BlogComment = BlogCommentDS.getInstance().getById(id);

    if ( _BlogComment == null ) {
        return;
    }

    String _blog_main_idValue=  WebUtil.display(_BlogComment.getBlogMainId());
    String _blog_post_idValue=  WebUtil.display(_BlogComment.getBlogPostId());
    String _commentValue=  WebUtil.display(_BlogComment.getComment());
    String _ratingValue=  WebUtil.display(_BlogComment.getRating());
    String _ipaddressValue=  WebUtil.display(_BlogComment.getIpaddress());
    String _nameValue=  WebUtil.display(_BlogComment.getName());
    String _emailValue=  WebUtil.display(_BlogComment.getEmail());
    String _time_createdValue=  WebUtil.display(_BlogComment.getTimeCreated());
%> 

<br>
<form name="blogCommentFormEdit" method="post" action="/blogCommentAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Blog Main Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Blog Post Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Comment</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="comment" value="<%=WebUtil.display(_commentValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Rating</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="rating" value="<%=WebUtil.display(_ratingValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Ipaddress</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="ipaddress" value="<%=WebUtil.display(_ipaddressValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="name" value="<%=WebUtil.display(_nameValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Email</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
    </TR>
            
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogCommentFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
</form>
