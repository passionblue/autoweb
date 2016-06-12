<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    List list = BlogPostDS.getInstance().getByBlogMainId(blogMainId);


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_blog_config_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="blogConfigForm_blogMainId_label" style="font-size: normal normal bold 10px verdana;" >Blog Main Id </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogConfig _BlogConfig = (BlogConfig) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogConfig.getId() %> </td>


    <td> 
	<form name="blogConfigFormEditField_BlogMainId_<%=_BlogConfig.getId()%>" method="get" action="/blogConfigAction.html" >


		<div id="blogConfigForm_blogMainId_field">
	    <div id="blogConfigForm_blogMainId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogMainId" value="<%=WebUtil.display(_BlogConfig.getBlogMainId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogConfigFormEditField_BlogMainId_<%=_BlogConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogConfig.getId()%>">
	</form>
    
    
    </td>

    <td>
    <form name="blogConfigForm<%=_BlogConfig.getId()%>" method="get" action="/v_blog_config_edit.html" >
        <a href="javascript:document.blogConfigForm<%=_BlogConfig.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogConfig.getId() %>">
    </form>
    <form name="blogConfigForm<%=_BlogConfig.getId()%>2" method="get" action="/v_blog_config_edit2.html" >
        <a href="javascript:document.blogConfigForm<%=_BlogConfig.getId()%>2.submit();">Edit2</a>           
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