<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _anonymous_user_idValue= "";
    String _first_nameValue= "";
    String _middle_initialValue= "";
    String _last_nameValue= "";
    String _address1Value= "";
    String _address2Value= "";
    String _cityValue= "";
    String _stateValue= "";
    String _zipValue= "";
    String _countryValue= "";
    String _special_instructionValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_anonymous_shipping_info_add.html"> Add New</a>
            <a href="t_ec_anonymous_shipping_info_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EcAnonymousShippingInfoDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousShippingInfo.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAnonymousUserId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getMiddleInitial()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAddress1()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAddress2()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getCity()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getState()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getZip()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getCountry()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getSpecialInstruction()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getTimeCreated()) %></td>

    <td>
    <form name="ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>" method="get" action="/v_ec_anonymous_shipping_info_edit.html" >
        <a href="javascript:document.ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousShippingInfo.getId() %>">
    </form>
    <form name="ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>2" method="get" action="/v_ec_anonymous_shipping_info_edit2.html" >
        <a href="javascript:document.ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousShippingInfo.getId() %>">
    </form>
    </td>

    <td>
    <a href="/ecAnonymousShippingInfoAction.html?del=true&id=<%=_EcAnonymousShippingInfo.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>