<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _usernameValue= "";
    String _passwordValue= "";
    String _emailValue= "";
    String _user_typeValue= "";
    String _first_nameValue= "";
    String _last_nameValue= "";
    String _nicknameValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";
    String _disabledValue= "";
    String _time_disabledValue= "";
    String _confirmedValue= "";
    String _time_confirmedValue= "";
    String _opt_1Value= "";
    String _opt_2Value= "";

%>

<div id="partitionFormFrame_autosite_user_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /AutositeUser_artition.jsp -->

	<script type="text/javascript">
		function sendForm_autosite_user_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="autositeUserForm_username_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_username_label" class="formLabel${classSuffix}" >Username </div>
    <div id="autositeUserForm_username_text" class="formFieldText${classSuffix}" >       
        <input id="username" class="field" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_password_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_password_label" class="formLabel${classSuffix}" >Password </div>
    <div id="autositeUserForm_password_text" class="formFieldText${classSuffix}" >       
        <input id="password" class="field" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_email_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_email_label" class="formLabel${classSuffix}" >Email </div>
    <div id="autositeUserForm_email_text" class="formFieldText${classSuffix}" >       
        <input id="email" class="field" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_userType_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_userType_label" class="formLabel${classSuffix}" >User Type </div>
    <div id="autositeUserForm_userType_text" class="formFieldText${classSuffix}" >       
        <input id="userType" class="field" type="text" size="70" name="userType" value="<%=WebUtil.display(_user_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_firstName_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_firstName_label" class="formLabel${classSuffix}" >First Name </div>
    <div id="autositeUserForm_firstName_text" class="formFieldText${classSuffix}" >       
        <input id="firstName" class="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_lastName_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_lastName_label" class="formLabel${classSuffix}" >Last Name </div>
    <div id="autositeUserForm_lastName_text" class="formFieldText${classSuffix}" >       
        <input id="lastName" class="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_nickname_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_nickname_label" class="formLabel${classSuffix}" >Nickname </div>
    <div id="autositeUserForm_nickname_text" class="formFieldText${classSuffix}" >       
        <input id="nickname" class="field" type="text" size="70" name="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="autositeUserForm_timeUpdated_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_timeUpdated_label" class="formLabel${classSuffix}" >Time Updated </div>
    <div id="autositeUserForm_timeUpdated_text" class="formFieldText${classSuffix}" >       
        <input id="timeUpdated" class="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_disabled_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_disabled_label" class="formLabel${classSuffix}" >Disabled </div>
    <div id="autositeUserForm_disabled_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeDisabled_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_timeDisabled_label" class="formLabel${classSuffix}" >Time Disabled </div>
    <div id="autositeUserForm_timeDisabled_text" class="formFieldText${classSuffix}" >       
        <input id="timeDisabled" class="field" type="text" size="70" name="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_confirmed_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_confirmed_label" class="formLabel${classSuffix}" >Confirmed </div>
    <div id="autositeUserForm_confirmed_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="confirmed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _confirmedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _confirmedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeConfirmed_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_timeConfirmed_label" class="formLabel${classSuffix}" >Time Confirmed </div>
    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText${classSuffix}" >       
        <input id="timeConfirmed" class="field" type="text" size="70" name="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_opt1_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_opt1_label" class="formLabel${classSuffix}" >Opt 1 </div>
    <div id="autositeUserForm_opt1_text" class="formFieldText${classSuffix}" >       
        <input id="opt1" class="field" type="text" size="70" name="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_opt2_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_opt2_label" class="formLabel${classSuffix}" >Opt 2 </div>
    <div id="autositeUserForm_opt2_text" class="formFieldText${classSuffix}" >       
        <input id="opt2" class="field" type="text" size="70" name="opt2" value="<%=WebUtil.display(_opt_2Value)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_autosite_user_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
