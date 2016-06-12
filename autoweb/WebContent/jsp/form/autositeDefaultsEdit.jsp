<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	AutositeDefaults _AutositeDefaults = AutositeDefaultsDS.getInstance().getById(id);

	if ( _AutositeDefaults == null ) {
		return;
	}

	String _style_idValue=  WebUtil.display(_AutositeDefaults.getStyleId());
	String _link_style_idValue=  WebUtil.display(_AutositeDefaults.getLinkStyleId());
	String _time_createdValue=  WebUtil.display(_AutositeDefaults.getTimeCreated());
	String _time_updatedValue=  WebUtil.display(_AutositeDefaults.getTimeUpdated());
%> 

<br>
<form name="autositeDefaultsFormEdit" method="post" action="/autositeDefaultsAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

	
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Style Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="styleId" value="<%=WebUtil.display(_style_idValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Link Style Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="linkStyleId" value="<%=WebUtil.display(_link_style_idValue)%>"/></TD>
    </TR>
			
			
		    <TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.autositeDefaultsFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeDefaults.getId()%>">
</form>
