<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _subjectValue= "";
    String _emailValue= "";
    String _confirmedValue= "";
    String _disabledValue= "";
    String _check_opt_1Value= "";
    String _check_opt_2Value= "";
    String _extra_infoValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_email_subs_add.html"> Add New</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

    List list = EmailSubsDS.getInstance().getBySiteId(site.getId());
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EmailSubs _EmailSubs = (EmailSubs) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EmailSubs.getId() %> </td>

    <td> <%= WebUtil.display(_EmailSubs.getSubject()) %></td>
    <td> <%= WebUtil.display(_EmailSubs.getEmail()) %></td>
    <td> <%= WebUtil.display(_EmailSubs.getConfirmed()) %></td>
    <td> <%= WebUtil.display(_EmailSubs.getDisabled()) %></td>
    <td> <%= WebUtil.display(_EmailSubs.getCheckOpt1()) %></td>
    <td> <%= WebUtil.display(_EmailSubs.getCheckOpt2()) %></td>
    <td> <%= WebUtil.display(_EmailSubs.getExtraInfo()) %></td>
    <td> <%= WebUtil.display(_EmailSubs.getTimeCreated()) %></td>

    <td>
    <form name="emailSubsForm<%=_EmailSubs.getId()%>" method="post" action="/v_email_subs_edit.html" >
        <a href="javascript:document.emailSubsForm<%=_EmailSubs.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EmailSubs.getId() %>">
    </form>
    </td>

    <td>
    <a href="/emailSubsAction.html?del=true&id=<%=_EmailSubs.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>