<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepInvitation _SweepInvitationDefault = new SweepInvitation();// SweepInvitationDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_SweepInvitationDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_SweepInvitationDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _messageValue= (reqParams.get("message")==null?WebUtil.display(_SweepInvitationDefault.getMessage()):WebUtil.display((String)reqParams.get("message")));
    String _invitation_sentValue= (reqParams.get("invitationSent")==null?WebUtil.display(_SweepInvitationDefault.getInvitationSent()):WebUtil.display((String)reqParams.get("invitationSent")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SweepInvitationDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_sentValue= (reqParams.get("timeSent")==null?WebUtil.display(_SweepInvitationDefault.getTimeSent()):WebUtil.display((String)reqParams.get("timeSent")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="sweepInvitationForm" method="post" action="/sweepInvitationAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Email</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Message</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="message" value="<%=WebUtil.display(_messageValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Invitation Sent</b> &nbsp;</TD>
        <TD>&nbsp;<select name="invitationSent">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _invitation_sentValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _invitation_sentValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.sweepInvitationForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SweepInvitationDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepInvitation _SweepInvitation = (SweepInvitation) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepInvitation.getId() %> </td>

    <td> <%= WebUtil.display(_SweepInvitation.getUserId()) %></td>
    <td> <%= WebUtil.display(_SweepInvitation.getEmail()) %></td>
    <td> <%= WebUtil.display(_SweepInvitation.getMessage()) %></td>
    <td> <%= WebUtil.display(_SweepInvitation.getInvitationSent()) %></td>
    <td> <%= WebUtil.display(_SweepInvitation.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_SweepInvitation.getTimeSent()) %></td>


<td>
<form name="sweepInvitationForm<%=_SweepInvitation.getId()%>" method="post" action="/v_sweep_invitation_edit.html" >
    <a href="javascript:document.sweepInvitationForm<%=_SweepInvitation.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepInvitation.getId() %>">
</form>
</td>
<td>
<a href="/sweepInvitationAction.html?del=true&id=<%=_SweepInvitation.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>