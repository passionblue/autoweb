<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	PanelLinkStyle _PanelLinkStyle = PanelLinkStyleDS.getInstance().getById(id);

	if ( _PanelLinkStyle == null ) {
		return;
	}

	String _panel_idValue=  String.valueOf(_PanelLinkStyle.getPanelId());
	String _style_idValue=  String.valueOf(_PanelLinkStyle.getStyleId());
	String _time_createdValue=  String.valueOf(_PanelLinkStyle.getTimeCreated());
%> 

<br>
<form name="panelLinkStyleFormEdit" method="post" action="/panelLinkStyleAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

	
		<TR bgcolor="#ffffff">
		<TD align="right" ><b>Panel Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="panelId" value="<%=WebUtil.display(_panel_idValue)%>"/></TD>
	</TR>
			
		<TR bgcolor="#ffffff">
		<TD align="right" ><b>Style Id</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="styleId" value="<%=WebUtil.display(_style_idValue)%>"/></TD>
	</TR>
			
			<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.panelLinkStyleFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PanelLinkStyle.getId()%>">
</form>
