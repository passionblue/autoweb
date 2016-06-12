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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="sweepInvitationForm_topArea" class="formTopArea"></div>
<div id="sweepInvitationForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="sweepInvitationForm" method="get" action="/sweepInvitationAction.html" >






	<div id="sweepInvitationForm_email_field">
    <div id="sweepInvitationForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="sweepInvitationForm_email_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="sweepInvitationForm_message_field">
    <div id="sweepInvitationForm_message_label" class="formLabel" >Message </div>
    <div id="sweepInvitationForm_message_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="message" COLS="50" ROWS="8" ><%=WebUtil.display(_messageValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepInvitationForm_invitationSent_field">
    <div id="sweepInvitationForm_invitationSent_label" class="formLabel" >Invitation Sent </div>
    <div id="sweepInvitationForm_invitationSent_dropdown" class="formFieldDropDown" >       
        <select name="invitationSent">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _invitation_sentValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _invitation_sentValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>










        <div id="sweepInvitationForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepInvitationForm.submit();">Submit</a>
        </div>      

        <div id="sweepInvitationForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.sweepInvitationForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="sweepInvitationForm_bottomArea" class="formBottomArea"></div>


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
<form name="sweepInvitationForm<%=_SweepInvitation.getId()%>" method="get" action="/v_sweep_invitation_edit.html" >
    <a href="javascript:document.sweepInvitationForm<%=_SweepInvitation.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepInvitation.getId() %>">
</form>
<form name="sweepInvitationForm<%=_SweepInvitation.getId()%>2" method="get" action="/v_sweep_invitation_edit2.html" >
    <a href="javascript:document.sweepInvitationForm<%=_SweepInvitation.getId()%>2.submit();">Edit2</a>           
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