<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
		SessionContext sessionContext = (SessionContext) session.getAttribute("k_session_context");
	
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if (panel == null) return;
		
		if ( panel.getHide() == 1) return;
		
		
		boolean isLogin = (sessionContext != null && sessionContext.isLogin());
		int panelHeight = (panel.getPanelHeight() ==0?20:panel.getPanelHeight());

		List contents = ContentDS.getInstance().getByPageId(panel.getId());
		List sitePosts = SitePostDS.getInstance().getByPanelId(panel.getId());
		
		if (sitePosts.size() == 0 && !isLogin) return;
		
		String style = panel.getStyleString();
		
		if (style == null || style.length()==0) style = "border-width: 0px; border-style: solid";
		
		boolean isVerticalAlign = (panel.getAlign() == 0);
		
		
%>


<% if (isLogin){ %>
<div id="panel-menu">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">Add</a>|
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">Edit</a>|	
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
		<a href="/v_style_config_add.html?prv_panel_id=<%=panel.getId()%>">Style</a>
		<%=panel.getId()%>
</div>			
<% } %>

<div id="paneldefault">

<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" style="<%=style%>">

<% if (panel.getTopSpace()>0) { %>
<TR><TD height="<%=panel.getTopSpace()%>">
</TD></TR>

<% } %>

<TR>		
	<TD bgcolor="transparent">

<% if (!isVerticalAlign){ %>
<TABLE border="0" cellpadding="0" cellspacing="0" ><TR> 
<%} %>
	
<%
	for(Iterator iter = sitePosts.iterator();iter.hasNext();){
	
		SitePost sitePost = (SitePost) iter.next();
		
		String data = "";
		
		if (sitePost.getPostType() == 2 ) { // LINK TYPE
			String linkDisplay = sitePost.getPostDataExtra() == null? sitePost.getPostData():sitePost.getPostDataExtra();
			
			data = "&#149;&nbsp;<a href=\"" + sitePost.getPostData() + "\" target=\"_blank\" >" + sitePost.getPostDataExtra() + "</a";
		} else if (sitePost.getPostType() == 5 ) { // LINK TYPE
			int paddingHeight = WebParamUtil.getIntValue(sitePost.getPostData());
			if ( paddingHeight <= 0 ) paddingHeight = 30;
		
			if (isVerticalAlign)		
				data = "<TABLE> <TR><TD height=\"" + paddingHeight + "\"> &nbsp; </TD></TR></TABLE>" ;
			else
				data = "<TABLE> <TR><TD width=\"" + paddingHeight + "\"> &nbsp; </TD></TR></TABLE>" ;
		} else {
			data = sitePost.getPostData();
		}
		
%>

<% if (!isVerticalAlign){ %>

<TD valign="top"> 
<%} %>
<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="transparent" style="<%=style%>">
<% 	if (isLogin) { 
%>		
		<TR><TD align="right" colspan="3"> 
			<a href="/t_edit_site_post.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[edit]</font> </a> 
			<a href="/deleteSitePost.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[del]</font> </a> 
		</TD></TR>
<% }%>

	<TR>
		<TD valign="top" width="100%"><%= data%> </TD>
	</TR>
</TABLE>

<% if (!isVerticalAlign){ %>
</TD>  
<%} %>


<%
	}
%>

<% if (!isVerticalAlign){ %>
</TR></TABLE> 
<%} %>

	
	</TD>
</TR>	
<% if (panel.getBottomSpace()>0) { %>
<TR><TD height="<%=panel.getBottomSpace()%>">
</TD></TR>

<% } %>
</TABLE>	
</div> <!-- end panel div -->