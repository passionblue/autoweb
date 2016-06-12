<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _column_countValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_display_config_add.html"> Add New</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

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