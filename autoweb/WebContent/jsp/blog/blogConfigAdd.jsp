<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlogConfig _BlogConfigDefault = new BlogConfig();// BlogConfigDS.getInstance().getDeafult();
    
    String _blog_main_idValue= (reqParams.get("blogMainId")==null?WebUtil.display(_BlogConfigDefault.getBlogMainId()):WebUtil.display((String)reqParams.get("blogMainId")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="blogConfigForm" method="post" action="/blogConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Main Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/></TD>
	    </TR>
	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlogConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogConfig _BlogConfig = (BlogConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogConfig.getId() %> </td>

    <td> <%= WebUtil.display(_BlogConfig.getBlogMainId()) %></td>


<td>
<form name="blogConfigForm<%=_BlogConfig.getId()%>" method="post" action="/v_blog_config_edit.html" >
    <a href="javascript:document.blogConfigForm<%=_BlogConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogConfig.getId() %>">
</form>
</td>
<td>
<a href="/blogConfigAction.html?del=true&id=<%=_BlogConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>