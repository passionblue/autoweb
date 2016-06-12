<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _forward_site_idValue= "";
    String _is_droppedValue= "";
    String _is_pagelessValue= "";
    String _is_loginValue= "";
    String _is_ajaxValue= "";
    String _is_robotValue= "";
    String _useridValue= "";
    String _user_agentValue= "";
    String _referValue= "";
    String _robotValue= "";
    String _remote_ipValue= "";
    String _site_urlValue= "";
    String _uriValue= "";
    String _queryValue= "";
    String _rpciValue= "";
    String _session_idValue= "";
    String _time_createdValue= "";

%>

<div id="partitionFormFrame_request_history_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /RequestHistory_artition.jsp -->

	<script type="text/javascript">
		function sendForm_request_history_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="requestHistoryForm_forwardSiteId_field" class="formFieldFrame">
    <div id="requestHistoryForm_forwardSiteId_label" class="formLabel" >Forward Site Id </div>
    <div id="requestHistoryForm_forwardSiteId_text" class="formFieldText" >       
        <input id="forwardSiteId" class="field" type="text" size="70" name="forwardSiteId" value="<%=WebUtil.display(_forward_site_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isDropped_field" class="formFieldFrame">
    <div id="requestHistoryForm_isDropped_label" class="formLabel" >Is Dropped </div>
    <div id="requestHistoryForm_isDropped_text" class="formFieldText" >       
        <input id="isDropped" class="field" type="text" size="70" name="isDropped" value="<%=WebUtil.display(_is_droppedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isPageless_field" class="formFieldFrame">
    <div id="requestHistoryForm_isPageless_label" class="formLabel" >Is Pageless </div>
    <div id="requestHistoryForm_isPageless_text" class="formFieldText" >       
        <input id="isPageless" class="field" type="text" size="70" name="isPageless" value="<%=WebUtil.display(_is_pagelessValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isLogin_field" class="formFieldFrame">
    <div id="requestHistoryForm_isLogin_label" class="formLabel" >Is Login </div>
    <div id="requestHistoryForm_isLogin_text" class="formFieldText" >       
        <input id="isLogin" class="field" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isAjax_field" class="formFieldFrame">
    <div id="requestHistoryForm_isAjax_label" class="formLabel" >Is Ajax </div>
    <div id="requestHistoryForm_isAjax_text" class="formFieldText" >       
        <input id="isAjax" class="field" type="text" size="70" name="isAjax" value="<%=WebUtil.display(_is_ajaxValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_isRobot_field" class="formFieldFrame">
    <div id="requestHistoryForm_isRobot_label" class="formLabel" >Is Robot </div>
    <div id="requestHistoryForm_isRobot_text" class="formFieldText" >       
        <input id="isRobot" class="field" type="text" size="70" name="isRobot" value="<%=WebUtil.display(_is_robotValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_userid_field" class="formFieldFrame">
    <div id="requestHistoryForm_userid_label" class="formLabel" >Userid </div>
    <div id="requestHistoryForm_userid_text" class="formFieldText" >       
        <input id="userid" class="field" type="text" size="70" name="userid" value="<%=WebUtil.display(_useridValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_userAgent_field" class="formFieldFrame">
    <div id="requestHistoryForm_userAgent_label" class="formLabel" >User Agent </div>
    <div id="requestHistoryForm_userAgent_text" class="formFieldText" >       
        <input id="userAgent" class="field" type="text" size="70" name="userAgent" value="<%=WebUtil.display(_user_agentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_refer_field" class="formFieldFrame">
    <div id="requestHistoryForm_refer_label" class="formLabel" >Refer </div>
    <div id="requestHistoryForm_refer_text" class="formFieldText" >       
        <input id="refer" class="field" type="text" size="70" name="refer" value="<%=WebUtil.display(_referValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_robot_field" class="formFieldFrame">
    <div id="requestHistoryForm_robot_label" class="formLabel" >Robot </div>
    <div id="requestHistoryForm_robot_text" class="formFieldText" >       
        <input id="robot" class="field" type="text" size="70" name="robot" value="<%=WebUtil.display(_robotValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_remoteIp_field" class="formFieldFrame">
    <div id="requestHistoryForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="requestHistoryForm_remoteIp_text" class="formFieldText" >       
        <input id="remoteIp" class="field" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_siteUrl_field" class="formFieldFrame">
    <div id="requestHistoryForm_siteUrl_label" class="formLabel" >Site Url </div>
    <div id="requestHistoryForm_siteUrl_text" class="formFieldText" >       
        <input id="siteUrl" class="field" type="text" size="70" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_uri_field" class="formFieldFrame">
    <div id="requestHistoryForm_uri_label" class="formLabel" >Uri </div>
    <div id="requestHistoryForm_uri_text" class="formFieldText" >       
        <input id="uri" class="field" type="text" size="70" name="uri" value="<%=WebUtil.display(_uriValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_query_field" class="formFieldFrame">
    <div id="requestHistoryForm_query_label" class="formLabel" >Query </div>
    <div id="requestHistoryForm_query_text" class="formFieldText" >       
        <input id="query" class="field" type="text" size="70" name="query" value="<%=WebUtil.display(_queryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_rpci_field" class="formFieldFrame">
    <div id="requestHistoryForm_rpci_label" class="formLabel" >Rpci </div>
    <div id="requestHistoryForm_rpci_text" class="formFieldText" >       
        <input id="rpci" class="field" type="text" size="70" name="rpci" value="<%=WebUtil.display(_rpciValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="requestHistoryForm_sessionId_field" class="formFieldFrame">
    <div id="requestHistoryForm_sessionId_label" class="formLabel" >Session Id </div>
    <div id="requestHistoryForm_sessionId_text" class="formFieldText" >       
        <input id="sessionId" class="field" type="text" size="70" name="sessionId" value="<%=WebUtil.display(_session_idValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_request_history_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
