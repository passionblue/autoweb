<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
		AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if (panel == null) return;
		
		boolean isLogin = (sessionContext != null && sessionContext.isSiteAdminLogin());
		int panelHeight = (panel.getPanelHeight() ==0?20:panel.getPanelHeight());

		String menuStyleName = "panel-menu";
		if (panel.getHide() == 1 ) 	menuStyleName="panel-menu-hidden";

		// Visiblity for printable
		if (WebUtil.isPritintable(request)){
			if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
				return;
		} else {
			if (panel.getShowOnlyPrint() == 1)
				return;
		}	

%>


<% if (isLogin){ %>
<div id="<%=menuStyleName %>" class="<%=menuStyleName %>">
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">Edit</a> |
		<a href="/panelAction.html?del=true&id=<%=panel.getId()%>">D</a>|
			
<% if (panel.getHide() == 1) { %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=0&ef=true">Show</a>|
<% }else{ %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
<% } %>

		<%=panel.getId()%>
</div>		
	
<% } %>

<% 
	// If panel is hidden 
	if (panel.getHide() == 1) return; 
%>


<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" style="<%=panel.getStyleString() %>">
	<TR>
		<TD height="<%=panelHeight %>">
		</TD>
	</TR>
</TABLE>
