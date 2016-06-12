<%@page language="java" import="java.util.*,com.jtrend.session.*,com.autosite.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");



    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepRegister _SweepRegisterDefault = new SweepRegister();// SweepRegisterDS.getInstance().getDeafult();

	String email = (String) reqParams.get("email");
	
	if (WebUtil.isNull(email))
		email = request.getParameter("username");

%>


<%
	if (sessionContext.isLogin()){

%>
<br/><br/><br/>
<div style="margin-left:50px;">
	<h3><a href="/v_sweep_worldcup_bet.html?prv_returnPage=sweep_worldcup_home&prv_teamCode=KOR" > <img src="/images/worldcup/KOR2.gif" style="float:left"/> Worldcup Sweepstake </a></h3><br/>
</div>	

<%
	} else {
%>
<br/><br/>
<div id="sweepRegisterForm_topArea" class="formTopArea"></div>
<h3> Buffalo Gae Member Access Page</h3>
<div id="registerSimpleForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="loginFormSubmitForm" method="post" action="/loginFormSubmit.html" >

	<div id="registerSimpleForm_username_field">
    <div id="registerSimpleForm_username_label" class="formRequiredLabel" >Email: </div>
    <div id="registerSimpleForm_username_text" class="formFieldText" style="font:normal normal normal 12px verdana"> <span></span>     
        <%= WebUtil.display(email) %> 
    </div>      
	</div><div class="clear"></div><br/>


	<div id="registerSimpleForm_password_field">
    <div id="registerSimpleForm_password_label" class="formRequiredLabel" > Preset Password: </div>
    <div id="registerSimpleForm_password_text" class="formFieldText" >  <span></span>      
        <input id="requiredField" type="password" style="width: 150px;" name="password" value=""/>
    </div>      
	</div><div class="clear"></div>

        <div id="registerSimpleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.loginFormSubmitForm.submit();">Submit</a>
        </div>


<INPUT TYPE="HIDDEN" NAME="fwdToNorm" value="/v_sweep_worldcup_bet.html?prv_returnPage=select_team&prv_teamCode=KOR">
<INPUT TYPE="HIDDEN" NAME="username" value="<%=email %>">
<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_access_shortcut">
              
</form>
</div> <!-- form -->

<br/><br/>
 
<%
	}
%>

	