<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long forumMainId = WebParamUtil.getLongValue(request.getParameter("forumMainId"));	

	List list = new ArrayList();
	ForumMainDS ds = ForumMainDS.getInstance();    
    


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_forum_main_add2.html?prv_returnPage=forum_main_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="forumMainForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ForumMain _ForumMain = (ForumMain) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ForumMain.getId() %> </td>


    <td> 
	<form name="forumMainFormEditField_Title_<%=_ForumMain.getId()%>" method="get" action="/forumMainAction.html" >


		<div id="forumMainForm_title_field">
	    <div id="forumMainForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_ForumMain.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.forumMainFormEditField_Title_<%=_ForumMain.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ForumMain.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/forum_main_home">
	</form>
    
    
    </td>


    <td>
    <form name="forumMainForm<%=_ForumMain.getId()%>" method="get" action="/v_forum_main_edit.html" >
        <a href="javascript:document.forumMainForm<%=_ForumMain.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ForumMain.getId() %>">
    </form>
    <form name="forumMainForm<%=_ForumMain.getId()%>2" method="get" action="/v_forum_main_edit2.html" >
        <a href="javascript:document.forumMainForm<%=_ForumMain.getId()%>2.submit();">Edit2</a>           
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