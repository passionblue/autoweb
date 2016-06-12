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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="blogConfigForm_topArea" class="formTopArea"></div>
<div id="blogConfigForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="blogConfigForm" method="get" action="/blogConfigAction.html" >




	<div id="blogConfigForm_blogMainId_field">
    <div id="blogConfigForm_blogMainId_label" class="formLabel" >Blog Main Id </div>
    <div id="blogConfigForm_blogMainId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="blogConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogConfigForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="blogConfigForm_bottomArea" class="formBottomArea"></div>


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