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
        <input id="requiredField" type="text" size="50" name="email" value="<%=WebUtil.display(_emailValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="sweepInvitationForm_message_field">
    <div id="sweepInvitationForm_message_label" class="formLabel" >Message </div>
    <div id="sweepInvitationForm_message_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="message" COLS="50" ROWS="8" ><%=WebUtil.display(_messageValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




        <div id="sweepInvitationForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepInvitationForm.submit();">Submit</a>
        </div>      

        <div id="sweepInvitationForm_cancel" class="formCancel" >       
            <a href="/v_select_team.html">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->

<div style="margin: 50px 100px 0px 160px">
<p style="font: normal normal normal 10px verdana;color:#666">
	&#8226;&nbsp;We don't spam to Sweepstakes participants nor to any invitees. Your quest will receive one 2010 Worldcup Sweepstakes invitation only.  
</p>
</div>


<div id="sweepInvitationForm_bottomArea" class="formBottomArea"></div>

<br/>
<div style="margin:auto;width: 400px">
<%
    if (false) return;


	AutositeUser user = UserUtil.getUserFromSession(session);
	
    List list = null;
    
    if (user != null) 
	    list = SweepInvitationDS.getInstance().getByUserId(user.getId());
	else
		list = new ArrayList();	  
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepInvitation _SweepInvitation = (SweepInvitation) iter.next();
%>
	<div style="border-bottom:1px solid blue; font: normal normal normal 11px verdana:padding: 5px 5px 5px 5px">
    <div style="float:left;width:250px" ><%= WebUtil.display(_SweepInvitation.getEmail()) %> </div> 
    <div style="float:left;width:150px" > <%= "sent at " + WebUtil.displayDateOnly(_SweepInvitation.getTimeCreated()) %></div>
    <div class="clear"></div> 
	</div>
<%
    }
%>
</div>