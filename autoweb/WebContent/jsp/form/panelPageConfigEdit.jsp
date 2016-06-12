<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	PanelPageConfig _PanelPageConfig = PanelPageConfigDS.getInstance().getById(id);

	if ( _PanelPageConfig == null ) {
		return;
	}

	String _panel_idValue=  WebUtil.display(_PanelPageConfig.getPanelId());
	String _page_display_summaryValue=  WebUtil.display(_PanelPageConfig.getPageDisplaySummary());
	String _time_createdValue=  WebUtil.display(_PanelPageConfig.getTimeCreated());
	String _time_updatedValue=  WebUtil.display(_PanelPageConfig.getTimeUpdated());
%> 

<br>
<form name="panelPageConfigFormEdit" method="post" action="/panelPageConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

	
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Panel Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="panelId" value="<%=WebUtil.display(_panel_idValue)%>"/></TD>
    </TR>
			    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Page Display Summary</b> &nbsp;</TD>
        <TD>&nbsp;<select name="pageDisplaySummary">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _page_display_summaryValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _page_display_summaryValue)%>>Yes</option>
            </select>
    </TD>
    </TR>
		
			
		    <TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.panelPageConfigFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PanelPageConfig.getId()%>">
</form>
