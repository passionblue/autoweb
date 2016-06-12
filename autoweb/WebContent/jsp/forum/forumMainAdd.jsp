<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long forumMainId = WebParamUtil.getLongValue(request.getParameter("forumMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ForumMain _ForumMainDefault = new ForumMain();// ForumMainDS.getInstance().getDeafult();
    
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_ForumMainDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ForumMainDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="forumMainForm" method="post" action="/forumMainAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Title</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="title" value="<%=WebUtil.display(_titleValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.forumMainForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ForumMainDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ForumMain _ForumMain = (ForumMain) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ForumMain.getId() %> </td>

    <td> <%= WebUtil.display(_ForumMain.getTitle()) %></td>
    <td> <%= WebUtil.display(_ForumMain.getTimeCreated()) %></td>


<td>
<form name="forumMainForm<%=_ForumMain.getId()%>" method="post" action="/v_forum_main_edit.html" >
    <a href="javascript:document.forumMainForm<%=_ForumMain.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ForumMain.getId() %>">
</form>
</td>
<td>
<a href="/forumMainAction.html?del=true&id=<%=_ForumMain.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>