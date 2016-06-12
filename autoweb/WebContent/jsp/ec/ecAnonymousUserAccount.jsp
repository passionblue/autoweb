<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _emailValue= "";
    String _subscribedValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_anonymous_user_account_add.html"> Add New</a>
            <a href="t_ec_anonymous_user_account_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EcAnonymousUserAccountDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousUserAccount.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getEmail()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getSubscribed()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getTimeCreated()) %></td>

    <td>
    <form name="ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>" method="get" action="/v_ec_anonymous_user_account_edit.html" >
        <a href="javascript:document.ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousUserAccount.getId() %>">
    </form>
    <form name="ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>2" method="get" action="/v_ec_anonymous_user_account_edit2.html" >
        <a href="javascript:document.ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousUserAccount.getId() %>">
    </form>
    </td>

    <td>
    <a href="/ecAnonymousUserAccountAction.html?del=true&id=<%=_EcAnonymousUserAccount.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>