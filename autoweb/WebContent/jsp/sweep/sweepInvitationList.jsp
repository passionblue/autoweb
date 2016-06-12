<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	SweepInvitationDS ds = SweepInvitationDS.getInstance();    
    list = ds.getBySiteId(site.getId());


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_sweep_invitation_add2.html?prv_returnPage=sweep_invitation_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">
    <td> ID </td>

    <td> 
	    <div id="sweepInvitationForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="sweepInvitationForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
    <td> 
	    <div id="sweepInvitationForm_message_label" style="font-size: normal normal bold 10px verdana;" >Message </div>
    </td> 
    <td> 
	    <div id="sweepInvitationForm_invitationSent_label" style="font-size: normal normal bold 10px verdana;" >Invitation Sent </div>
    </td> 
    <td> 
	    <div id="sweepInvitationForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
    <td> 
	    <div id="sweepInvitationForm_timeSent_label" style="font-size: normal normal bold 10px verdana;" >Time Sent </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepInvitation _SweepInvitation = (SweepInvitation) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _SweepInvitation.getId() %> </td>
    
	<td> <%= _SweepInvitation.getUserId()  %> </td>
	<td> <%= _SweepInvitation.getEmail()  %> </td>
	<td> <%= _SweepInvitation.getMessage()  %> </td>
	<td> <%= _SweepInvitation.getInvitationSent()  %> </td>
	<td> <%= _SweepInvitation.getTimeCreated()  %> </td>
	<td> <%= _SweepInvitation.getTimeSent()  %> </td>
</TR>

<%
    }
%>
</TABLE>