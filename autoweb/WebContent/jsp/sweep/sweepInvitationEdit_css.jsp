<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    SweepInvitation _SweepInvitation = SweepInvitationDS.getInstance().getById(id);

    if ( _SweepInvitation == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _user_idValue=  WebUtil.display(_SweepInvitation.getUserId());
    String _emailValue=  WebUtil.display(_SweepInvitation.getEmail());
    String _messageValue=  WebUtil.display(_SweepInvitation.getMessage());
    String _invitation_sentValue=  WebUtil.display(_SweepInvitation.getInvitationSent());
    String _time_createdValue=  WebUtil.display(_SweepInvitation.getTimeCreated());
    String _time_sentValue=  WebUtil.display(_SweepInvitation.getTimeSent());
%> 

<br>
<div id="sweepInvitationForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sweepInvitationFormEdit" method="get" action="/sweepInvitationAction.html" >




	<div id="sweepInvitationForm_email_field">
    <div id="sweepInvitationForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="sweepInvitationForm_email_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="sweepInvitationForm_message_field">
    <div id="sweepInvitationForm_message_label" class="formLabel" >Message </div>
    <div id="sweepInvitationForm_message_text" class="formFieldText" >       
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







        <div id="sweepInvitationFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepInvitationFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepInvitation.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
