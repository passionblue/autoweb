<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    BlogConfig _BlogConfig = BlogConfigDS.getInstance().getById(id);

    if ( _BlogConfig == null ) {
        return;
    }

    String _blog_main_idValue=  WebUtil.display(_BlogConfig.getBlogMainId());
%> 

<br>
<form name="blogConfigFormEdit" method="post" action="/blogConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Blog Main Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/></TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogConfig.getId()%>">
</form>
