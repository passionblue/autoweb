<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepPassword _SweepPasswordDefault = new SweepPassword();// SweepPasswordDS.getInstance().getDeafult();
    
    String _send_password_emailValue= (reqParams.get("sendPasswordEmail")==null?"":WebUtil.display((String)reqParams.get("sendPasswordEmail")));
    String _old_passwordValue= (reqParams.get("oldPassword")==null?"":WebUtil.display((String)reqParams.get("oldPassword")));
    String _new_passwordValue= (reqParams.get("newPassword")==null?"":WebUtil.display((String)reqParams.get("newPassword")));
    String _password_retypeValue= (reqParams.get("passwordRetype")==null?"":WebUtil.display((String)reqParams.get("passwordRetype")));


%> 

<br>
<div id="sweepPasswordForm_topArea" class="formTopArea"></div>
<div id="sweepPasswordForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="sweepPasswordForm" method="get" action="/sweepPasswordAction.html" >


	<div id="sweepPasswordForm_sendPasswordEmail_field">
    <div id="sweepPasswordForm_sendPasswordEmail_label" class="formLabel" >Send Password Email </div>
    <div id="sweepPasswordForm_sendPasswordEmail_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="sendPasswordEmail" value="<%=WebUtil.display(_send_password_emailValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="sweepPasswordForm_oldPassword_field">
    <div id="sweepPasswordForm_oldPassword_label" class="formLabel" >Old Password </div>
    <div id="sweepPasswordForm_oldPassword_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="oldPassword" value="<%=WebUtil.display(_old_passwordValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="sweepPasswordForm_newPassword_field">
    <div id="sweepPasswordForm_newPassword_label" class="formLabel" >New Password </div>
    <div id="sweepPasswordForm_newPassword_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="newPassword" value="<%=WebUtil.display(_new_passwordValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="sweepPasswordForm_passwordRetype_field">
    <div id="sweepPasswordForm_passwordRetype_label" class="formLabel" >Password Retype </div>
    <div id="sweepPasswordForm_passwordRetype_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="passwordRetype" value="<%=WebUtil.display(_password_retypeValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

        <div id="sweepPasswordForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepPasswordForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>
</div> <!-- form -->
<div id="sweepPasswordForm_bottomArea" class="formBottomArea"></div>


