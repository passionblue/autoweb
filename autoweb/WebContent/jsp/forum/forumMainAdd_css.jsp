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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="forumMainForm_topArea" class="formTopArea"></div>
<div id="forumMainForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="forumMainForm" method="get" action="/forumMainAction.html" >




	<div id="forumMainForm_title_field">
    <div id="forumMainForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="forumMainForm_title_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="title" value="<%=WebUtil.display(_titleValue)%>"/> 
    </div>      
	</div><div class="clear"></div>





        <div id="forumMainForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.forumMainForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="forumMainForm_bottomArea" class="formBottomArea"></div>


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