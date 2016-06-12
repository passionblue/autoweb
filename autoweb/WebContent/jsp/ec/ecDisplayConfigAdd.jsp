<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcDisplayConfig _EcDisplayConfigDefault = new EcDisplayConfig();// EcDisplayConfigDS.getInstance().getDeafult();
    
    String _column_countValue= (reqParams.get("columnCount")==null?WebUtil.display(_EcDisplayConfigDefault.getColumnCount()):WebUtil.display((String)reqParams.get("columnCount")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecDisplayConfigForm" method="post" action="/ecDisplayConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Column Count</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="columnCount" value="<%=WebUtil.display(_column_countValue)%>"/></TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecDisplayConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcDisplayConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcDisplayConfig _EcDisplayConfig = (EcDisplayConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcDisplayConfig.getId() %> </td>

    <td> <%= WebUtil.display(_EcDisplayConfig.getColumnCount()) %></td>


<td>
<form name="ecDisplayConfigForm<%=_EcDisplayConfig.getId()%>" method="post" action="/v_ec_display_config_edit.html" >
    <a href="javascript:document.ecDisplayConfigForm<%=_EcDisplayConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcDisplayConfig.getId() %>">
</form>
</td>
<td>
<a href="/ecDisplayConfigAction.html?del=true&id=<%=_EcDisplayConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>