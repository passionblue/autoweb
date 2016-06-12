<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _serialValue= "";
    String _is_loginValue= "";
    String _time_loginValue= "";
    String _time_last_accessValue= "";
    String _login_user_idValue= "";
    String _session_typeValue= "";
    String _remote_device_idValue= "";
    String _remote_ipValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_autosite_session_context_entity_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /AutositeSessionContextEntity_artition.jsp -->

	<script type="text/javascript">
		function sendForm_autosite_session_context_entity_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="autositeSessionContextEntityForm_serial_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_serial_label" class="formLabel" >Serial </div>
    <div id="autositeSessionContextEntityForm_serial_text" class="formFieldText" >       
        <input id="serial" class="field" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_isLogin_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_isLogin_label" class="formLabel" >Is Login </div>
    <div id="autositeSessionContextEntityForm_isLogin_text" class="formFieldText" >       
        <input id="isLogin" class="field" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_timeLogin_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_timeLogin_label" class="formLabel" >Time Login </div>
    <div id="autositeSessionContextEntityForm_timeLogin_text" class="formFieldText" >       
        <input id="timeLogin" class="field" type="text" size="70" name="timeLogin" value="<%=WebUtil.display(_time_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_timeLastAccess_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_timeLastAccess_label" class="formLabel" >Time Last Access </div>
    <div id="autositeSessionContextEntityForm_timeLastAccess_text" class="formFieldText" >       
        <input id="timeLastAccess" class="field" type="text" size="70" name="timeLastAccess" value="<%=WebUtil.display(_time_last_accessValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_loginUserId_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_loginUserId_label" class="formLabel" >Login User Id </div>
    <div id="autositeSessionContextEntityForm_loginUserId_text" class="formFieldText" >       
        <input id="loginUserId" class="field" type="text" size="70" name="loginUserId" value="<%=WebUtil.display(_login_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_sessionType_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_sessionType_label" class="formLabel" >Session Type </div>
    <div id="autositeSessionContextEntityForm_sessionType_text" class="formFieldText" >       
        <input id="sessionType" class="field" type="text" size="70" name="sessionType" value="<%=WebUtil.display(_session_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_remoteDeviceId_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_remoteDeviceId_label" class="formLabel" >Remote Device Id </div>
    <div id="autositeSessionContextEntityForm_remoteDeviceId_text" class="formFieldText" >       
        <input id="remoteDeviceId" class="field" type="text" size="70" name="remoteDeviceId" value="<%=WebUtil.display(_remote_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSessionContextEntityForm_remoteIp_field" class="formFieldFrame">
    <div id="autositeSessionContextEntityForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="autositeSessionContextEntityForm_remoteIp_text" class="formFieldText" >       
        <input id="remoteIp" class="field" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_autosite_session_context_entity_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
