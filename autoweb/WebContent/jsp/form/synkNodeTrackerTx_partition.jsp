<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _namespaceValue= "";
    String _device_idValue= "";
    String _tx_tokenValue= "";
    String _stamp_ackedValue= "";
    String _stamp_lastValue= "";
    String _num_recordsValue= "";
    String _ipValue= "";
    String _time_createdValue= "";

%>

<div id="partitionFormFrame_synk_node_tracker_tx_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /SynkNodeTrackerTx_artition.jsp -->

	<script type="text/javascript">
		function sendForm_synk_node_tracker_tx_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="synkNodeTrackerTxForm_namespace_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_namespace_label" class="formLabel" >Namespace </div>
    <div id="synkNodeTrackerTxForm_namespace_text" class="formFieldText" >       
        <input id="namespace" class="field" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_deviceId_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_deviceId_label" class="formLabel" >Device Id </div>
    <div id="synkNodeTrackerTxForm_deviceId_text" class="formFieldText" >       
        <input id="deviceId" class="field" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_txToken_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_txToken_label" class="formLabel" >Tx Token </div>
    <div id="synkNodeTrackerTxForm_txToken_text" class="formFieldText" >       
        <input id="txToken" class="field" type="text" size="70" name="txToken" value="<%=WebUtil.display(_tx_tokenValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_stampAcked_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_stampAcked_label" class="formLabel" >Stamp Acked </div>
    <div id="synkNodeTrackerTxForm_stampAcked_text" class="formFieldText" >       
        <input id="stampAcked" class="field" type="text" size="70" name="stampAcked" value="<%=WebUtil.display(_stamp_ackedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_stampLast_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_stampLast_label" class="formLabel" >Stamp Last </div>
    <div id="synkNodeTrackerTxForm_stampLast_text" class="formFieldText" >       
        <input id="stampLast" class="field" type="text" size="70" name="stampLast" value="<%=WebUtil.display(_stamp_lastValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_numRecords_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_numRecords_label" class="formLabel" >Num Records </div>
    <div id="synkNodeTrackerTxForm_numRecords_text" class="formFieldText" >       
        <input id="numRecords" class="field" type="text" size="70" name="numRecords" value="<%=WebUtil.display(_num_recordsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_ip_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_ip_label" class="formLabel" >Ip </div>
    <div id="synkNodeTrackerTxForm_ip_text" class="formFieldText" >       
        <input id="ip" class="field" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNodeTrackerTxForm_timeCreated_field" class="formFieldFrame">
    <div id="synkNodeTrackerTxForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="synkNodeTrackerTxForm_timeCreated_text" class="formFieldText" >       
        <input id="timeCreated" class="field" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_synk_node_tracker_tx_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
