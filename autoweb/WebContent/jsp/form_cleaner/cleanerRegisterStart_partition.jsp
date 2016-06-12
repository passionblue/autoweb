<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _site_titleValue= "";
    String _site_nameValue= "";
    String _usernameValue= "";
    String _emailValue= "";
    String _passwordValue= "";
    String _password_repeatValue= "";
    String _locationValue= "";
    String _created_site_urlValue= "";

%>

<div id="partitionFormFrame_cleaner_register_start_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerRegisterStart_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_register_start_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerRegisterStartForm_siteTitle_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_siteTitle_label" class="formLabel" >Site Title </div>
    <div id="cleanerRegisterStartForm_siteTitle_text" class="formFieldText" >       
        <input id="siteTitle" class="field" type="text" size="70" name="siteTitle" value="<%=WebUtil.display(_site_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_siteName_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_siteName_label" class="formRequiredLabel" >Site Name* </div>
    <div id="cleanerRegisterStartForm_siteName_text" class="formFieldText" >       
        <input id="siteName" class="requiredField" type="text" size="70" name="siteName" value="<%=WebUtil.display(_site_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_username_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_username_label" class="formLabel" >Username </div>
    <div id="cleanerRegisterStartForm_username_text" class="formFieldText" >       
        <input id="username" class="field" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_email_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="cleanerRegisterStartForm_email_text" class="formFieldText" >       
        <input id="email" class="requiredField" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_password_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="cleanerRegisterStartForm_password_text" class="formFieldText" >       
        <input id="password" class="requiredField" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_passwordRepeat_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_passwordRepeat_label" class="formRequiredLabel" >Password Repeat* </div>
    <div id="cleanerRegisterStartForm_passwordRepeat_text" class="formFieldText" >       
        <input id="passwordRepeat" class="requiredField" type="text" size="70" name="passwordRepeat" value="<%=WebUtil.display(_password_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_location_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_location_label" class="formLabel" >Location </div>
    <div id="cleanerRegisterStartForm_location_text" class="formFieldText" >       
        <input id="location" class="field" type="text" size="70" name="location" value="<%=WebUtil.display(_locationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerRegisterStartForm_createdSiteUrl_field" class="formFieldFrame">
    <div id="cleanerRegisterStartForm_createdSiteUrl_label" class="formLabel" >Created Site Url </div>
    <div id="cleanerRegisterStartForm_createdSiteUrl_text" class="formFieldText" >       
        <input id="createdSiteUrl" class="field" type="text" size="70" name="createdSiteUrl" value="<%=WebUtil.display(_created_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_register_start_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
