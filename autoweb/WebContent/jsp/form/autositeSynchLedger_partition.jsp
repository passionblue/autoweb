<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _device_idValue= "";
    String _original_ledger_idValue= "";
    String _scopeValue= "";
    String _targetValue= "";
    String _remote_tokenValue= "";
    String _object_idValue= "";
    String _synch_idValue= "";
    String _time_createdValue= "";

%>

<div id="partitionFormFrame_autosite_synch_ledger_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /AutositeSynchLedger_artition.jsp -->

	<script type="text/javascript">
		function sendForm_autosite_synch_ledger_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="autositeSynchLedgerForm_deviceId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_deviceId_label" class="formLabel" >Device Id </div>
    <div id="autositeSynchLedgerForm_deviceId_text" class="formFieldText" >       
        <input id="deviceId" class="field" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_originalLedgerId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_originalLedgerId_label" class="formLabel" >Original Ledger Id </div>
    <div id="autositeSynchLedgerForm_originalLedgerId_text" class="formFieldText" >       
        <input id="originalLedgerId" class="field" type="text" size="70" name="originalLedgerId" value="<%=WebUtil.display(_original_ledger_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_scope_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_scope_label" class="formLabel" >Scope </div>
    <div id="autositeSynchLedgerForm_scope_text" class="formFieldText" >       
        <input id="scope" class="field" type="text" size="70" name="scope" value="<%=WebUtil.display(_scopeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_target_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_target_label" class="formLabel" >Target </div>
    <div id="autositeSynchLedgerForm_target_text" class="formFieldText" >       
        <input id="target" class="field" type="text" size="70" name="target" value="<%=WebUtil.display(_targetValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_remoteToken_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_remoteToken_label" class="formLabel" >Remote Token </div>
    <div id="autositeSynchLedgerForm_remoteToken_text" class="formFieldText" >       
        <input id="remoteToken" class="field" type="text" size="70" name="remoteToken" value="<%=WebUtil.display(_remote_tokenValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_objectId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_objectId_label" class="formLabel" >Object Id </div>
    <div id="autositeSynchLedgerForm_objectId_text" class="formFieldText" >       
        <input id="objectId" class="field" type="text" size="70" name="objectId" value="<%=WebUtil.display(_object_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeSynchLedgerForm_synchId_field" class="formFieldFrame">
    <div id="autositeSynchLedgerForm_synchId_label" class="formLabel" >Synch Id </div>
    <div id="autositeSynchLedgerForm_synchId_text" class="formFieldText" >       
        <input id="synchId" class="field" type="text" size="70" name="synchId" value="<%=WebUtil.display(_synch_idValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_autosite_synch_ledger_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
