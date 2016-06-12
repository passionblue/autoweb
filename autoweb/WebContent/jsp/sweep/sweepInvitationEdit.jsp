<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    SweepInvitation _SweepInvitation = SweepInvitationDS.getInstance().getById(id);

    if ( _SweepInvitation == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_SweepInvitation.getUserId());
    String _emailValue=  WebUtil.display(_SweepInvitation.getEmail());
    String _messageValue=  WebUtil.display(_SweepInvitation.getMessage());
    String _invitation_sentValue=  WebUtil.display(_SweepInvitation.getInvitationSent());
    String _time_createdValue=  WebUtil.display(_SweepInvitation.getTimeCreated());
    String _time_sentValue=  WebUtil.display(_SweepInvitation.getTimeSent());
%> 

<br>
<form name="sweepInvitationFormEdit" method="post" action="/sweepInvitationAction.html" >
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
            <b><a href="javascript:document.sweepInvitationFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepInvitation.getId()%>">
</form>
