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

    SweepPassword _SweepPassword = SweepPasswordDS.getInstance().getById(id);

    if ( _SweepPassword == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _send_password_emailValue=  WebUtil.display(_SweepPassword.getSendPasswordEmail());
    String _old_passwordValue=  WebUtil.display(_SweepPassword.getOldPassword());
    String _new_passwordValue=  WebUtil.display(_SweepPassword.getNewPassword());
    String _password_retypeValue=  WebUtil.display(_SweepPassword.getPasswordRetype());
%> 

<br>
<div id="sweepPasswordForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sweepPasswordFormEdit" method="get" action="/sweepPasswordAction.html" >




	<div id="sweepPasswordForm_sendPasswordEmail_field">
    <div id="sweepPasswordForm_sendPasswordEmail_label" class="formLabel" >Send Password Email </div>
    <div id="sweepPasswordForm_sendPasswordEmail_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="sendPasswordEmail" value="<%=WebUtil.display(_send_password_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepPasswordForm_oldPassword_field">
    <div id="sweepPasswordForm_oldPassword_label" class="formLabel" >Old Password </div>
    <div id="sweepPasswordForm_oldPassword_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="oldPassword" value="<%=WebUtil.display(_old_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepPasswordForm_newPassword_field">
    <div id="sweepPasswordForm_newPassword_label" class="formLabel" >New Password </div>
    <div id="sweepPasswordForm_newPassword_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="newPassword" value="<%=WebUtil.display(_new_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepPasswordForm_passwordRetype_field">
    <div id="sweepPasswordForm_passwordRetype_label" class="formLabel" >Password Retype </div>
    <div id="sweepPasswordForm_passwordRetype_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="passwordRetype" value="<%=WebUtil.display(_password_retypeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="sweepPasswordFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepPasswordFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepPassword.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
