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
    String _payment_typeValue= "";
    String _payment_numValue= "";
    String _payment_expire_monthValue= "";
    String _payment_expire_yearValue= "";
    String _payment_extra_numValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_anonymous_payment_info_add.html"> Add New</a>
            <a href="t_ec_anonymous_payment_info_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EcAnonymousPaymentInfoDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousPaymentInfo.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAnonymousUserId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getMiddleInitial()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAddress1()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAddress2()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getCity()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getState()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getZip()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getCountry()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentType()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentNum()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireMonth()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireYear()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExtraNum()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getTimeCreated()) %></td>

    <td>
    <form name="ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>" method="get" action="/v_ec_anonymous_payment_info_edit.html" >
        <a href="javascript:document.ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousPaymentInfo.getId() %>">
    </form>
    <form name="ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>2" method="get" action="/v_ec_anonymous_payment_info_edit2.html" >
        <a href="javascript:document.ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousPaymentInfo.getId() %>">
    </form>
    </td>

    <td>
    <a href="/ecAnonymousPaymentInfoAction.html?del=true&id=<%=_EcAnonymousPaymentInfo.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>