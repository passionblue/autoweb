<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _user_idValue= "";
    String _first_nameValue= "";
    String _last_nameValue= "";
    String _subscribedValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_autosite_user_account_add.html"> Add New</a>
            <a href="t_ec_autosite_user_account_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EcAutositeUserAccountDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAutositeUserAccount _EcAutositeUserAccount = (EcAutositeUserAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAutositeUserAccount.getId() %> </td>

    <td> <%= WebUtil.display(_EcAutositeUserAccount.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getSubscribed()) %></td>
    <td> <%= WebUtil.display(_EcAutositeUserAccount.getTimeCreated()) %></td>

    <td>
    <form name="ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>" method="get" action="/v_ec_autosite_user_account_edit.html" >
        <a href="javascript:document.ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAutositeUserAccount.getId() %>">
    </form>
    <form name="ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>2" method="get" action="/v_ec_autosite_user_account_edit2.html" >
        <a href="javascript:document.ecAutositeUserAccountForm<%=_EcAutositeUserAccount.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAutositeUserAccount.getId() %>">
    </form>
    </td>

    <td>
    <a href="/ecAutositeUserAccountAction.html?del=true&id=<%=_EcAutositeUserAccount.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>