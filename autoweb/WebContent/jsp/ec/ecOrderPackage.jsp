<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _user_idValue= "";
    String _order_idValue= "";
    String _num_orderValue= "";
    String _shippedValue= "";
    String _time_shippedValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_order_package_add.html"> Add New</a>
            <a href="t_ec_order_package_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EcOrderPackageDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcOrderPackage _EcOrderPackage = (EcOrderPackage) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcOrderPackage.getId() %> </td>

    <td> <%= WebUtil.display(_EcOrderPackage.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getOrderId()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getNumOrder()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getShipped()) %></td>
    <td> <%= WebUtil.display(_EcOrderPackage.getTimeShipped()) %></td>

    <td>
    <form name="ecOrderPackageForm<%=_EcOrderPackage.getId()%>" method="get" action="/v_ec_order_package_edit.html" >
        <a href="javascript:document.ecOrderPackageForm<%=_EcOrderPackage.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcOrderPackage.getId() %>">
    </form>
    <form name="ecOrderPackageForm<%=_EcOrderPackage.getId()%>2" method="get" action="/v_ec_order_package_edit2.html" >
        <a href="javascript:document.ecOrderPackageForm<%=_EcOrderPackage.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcOrderPackage.getId() %>">
    </form>
    </td>

    <td>
    <a href="/ecOrderPackageAction.html?del=true&id=<%=_EcOrderPackage.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>