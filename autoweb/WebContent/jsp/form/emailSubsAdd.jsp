<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EmailSubs _EmailSubsDefault = new EmailSubs();// EmailSubsDS.getInstance().getDeafult();
    
    String _subjectValue= (reqParams.get("subject")==null?WebUtil.display(_EmailSubsDefault.getSubject()):WebUtil.display((String)reqParams.get("subject")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_EmailSubsDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EmailSubsDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EmailSubsDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _confirmedValue= (reqParams.get("confirmed")==null?WebUtil.display(_EmailSubsDefault.getConfirmed()):WebUtil.display((String)reqParams.get("confirmed")));
    String _disabledValue= (reqParams.get("disabled")==null?WebUtil.display(_EmailSubsDefault.getDisabled()):WebUtil.display((String)reqParams.get("disabled")));
    String _check_opt_1Value= (reqParams.get("checkOpt1")==null?WebUtil.display(_EmailSubsDefault.getCheckOpt1()):WebUtil.display((String)reqParams.get("checkOpt1")));
    String _check_opt_2Value= (reqParams.get("checkOpt2")==null?WebUtil.display(_EmailSubsDefault.getCheckOpt2()):WebUtil.display((String)reqParams.get("checkOpt2")));
    String _extra_infoValue= (reqParams.get("extraInfo")==null?WebUtil.display(_EmailSubsDefault.getExtraInfo()):WebUtil.display((String)reqParams.get("extraInfo")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EmailSubsDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="emailSubsForm" method="post" action="/emailSubsAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Email</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>First Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Last Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Check Opt 1</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="checkOpt1" value="<%=WebUtil.display(_check_opt_1Value)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Check Opt 2</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="checkOpt2" value="<%=WebUtil.display(_check_opt_2Value)%>"/></TD>
    </TR>
                    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Extra Info</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="extraInfo" value="<%=WebUtil.display(_extra_infoValue)%>"/></TD>
    </TR>
                        <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.emailSubsForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="subject" value="<%= _subjectValue%>">
<INPUT TYPE="HIDDEN" NAME="confirmed" value="1">

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

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