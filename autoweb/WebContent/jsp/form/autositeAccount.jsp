<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _account_numValue= "";
    String _enabledValue= "";
    String _email_confirmedValue= "";
    String _time_confirmedValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_autosite_account_add.html"> Add New</a>
            <a href="t_autosite_account_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = AutositeAccountDS.getInstance().getAll();
    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeAccount _AutositeAccount = (AutositeAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeAccount.getId() %> </td>

    <td> <%= WebUtil.display(_AutositeAccount.getAccountNum()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getEnabled()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getEmailConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getTimeConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getTimeCreated()) %></td>

    <td>
    <form name="autositeAccountForm<%=_AutositeAccount.getId()%>" method="get" action="/v_autosite_account_edit.html" >
        <a href="javascript:document.autositeAccountForm<%=_AutositeAccount.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeAccount.getId() %>">
    </form>
    <form name="autositeAccountForm<%=_AutositeAccount.getId()%>2" method="get" action="/v_autosite_account_edit2.html" >
        <a href="javascript:document.autositeAccountForm<%=_AutositeAccount.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeAccount.getId() %>">
    </form>
    </td>

    <td>
    <a href="/autositeAccountAction.html?del=true&id=<%=_AutositeAccount.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>