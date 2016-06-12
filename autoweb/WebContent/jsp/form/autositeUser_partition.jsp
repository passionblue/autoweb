<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
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
    String _pageless_sessionValue= "";
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





	<div id="autositeUserForm_username_field" class="formFieldFrame">
    <div id="autositeUserForm_username_label" class="formLabel" >Username </div>
    <div id="autositeUserForm_username_text" class="formFieldText" >       
        <input id="username" class="field" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_password_field" class="formFieldFrame">
    <div id="autositeUserForm_password_label" class="formLabel" >Password </div>
    <div id="autositeUserForm_password_text" class="formFieldText" >       
        <input id="password" class="field" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_email_field" class="formFieldFrame">
    <div id="autositeUserForm_email_label" class="formLabel" >Email </div>
    <div id="autositeUserForm_email_text" class="formFieldText" >       
        <input id="email" class="field" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_userType_field" class="formFieldFrame">
    <div id="autositeUserForm_userType_label" class="formLabel" >User Type </div>
    <div id="autositeUserForm_userType_text" class="formFieldText" >       
        <input id="userType" class="field" type="text" size="70" name="userType" value="<%=WebUtil.display(_user_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_firstName_field" class="formFieldFrame">
    <div id="autositeUserForm_firstName_label" class="formLabel" >First Name </div>
    <div id="autositeUserForm_firstName_text" class="formFieldText" >       
        <input id="firstName" class="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_lastName_field" class="formFieldFrame">
    <div id="autositeUserForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="autositeUserForm_lastName_text" class="formFieldText" >       
        <input id="lastName" class="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_nickname_field" class="formFieldFrame">
    <div id="autositeUserForm_nickname_label" class="formLabel" >Nickname </div>
    <div id="autositeUserForm_nickname_text" class="formFieldText" >       
        <input id="nickname" class="field" type="text" size="70" name="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>









	<div id="autositeUserForm_disabled_field" class="formFieldFrame">
    <div id="autositeUserForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="autositeUserForm_disabled_dropdown" class="formFieldDropDown" >       
        <select name="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeDisabled_field" class="formFieldFrame">
    <div id="autositeUserForm_timeDisabled_label" class="formLabel" >Time Disabled </div>
    <div id="autositeUserForm_timeDisabled_text" class="formFieldText" >       
        <input id="timeDisabled" class="field" type="text" size="70" name="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_confirmed_field" class="formFieldFrame">
    <div id="autositeUserForm_confirmed_label" class="formLabel" >Confirmed </div>
    <div id="autositeUserForm_confirmed_dropdown" class="formFieldDropDown" >       
        <select name="confirmed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _confirmedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _confirmedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeConfirmed_field" class="formFieldFrame">
    <div id="autositeUserForm_timeConfirmed_label" class="formLabel" >Time Confirmed </div>
    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText" >       
        <input id="timeConfirmed" class="field" type="text" size="70" name="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_pagelessSession_field" class="formFieldFrame">
    <div id="autositeUserForm_pagelessSession_label" class="formLabel" >Pageless Session </div>
    <div id="autositeUserForm_pagelessSession_dropdown" class="formFieldDropDown" >       
        <select name="pagelessSession">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _pageless_sessionValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _pageless_sessionValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_opt1_field" class="formFieldFrame">
    <div id="autositeUserForm_opt1_label" class="formLabel" >Opt 1 </div>
    <div id="autositeUserForm_opt1_text" class="formFieldText" >       
        <input id="opt1" class="field" type="text" size="70" name="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_opt2_field" class="formFieldFrame">
    <div id="autositeUserForm_opt2_label" class="formLabel" >Opt 2 </div>
    <div id="autositeUserForm_opt2_text" class="formFieldText" >       
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
