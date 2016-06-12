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

        <div id="sweepPasswordForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepPasswordForm.submit();">Submit</a>
        </div>      

        <div style="float:left;margin:15px 0px 0px 160px;font-size: 12px; font-weight: bold;">       
            <a id="formSubmit" href="/t_login_form.html">Go to Login Page</a>
        </div>


<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>
</div> <!-- form -->
<div id="sweepPasswordForm_bottomArea" class="formBottomArea"></div>


