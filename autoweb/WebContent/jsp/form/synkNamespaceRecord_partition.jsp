<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _namespaceValue= "";
    String _record_idValue= "";
    String _stampValue= "";
    String _org_stampValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_synk_namespace_record_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /SynkNamespaceRecord_artition.jsp -->

	<script type="text/javascript">
		function sendForm_synk_namespace_record_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="synkNamespaceRecordForm_namespace_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_namespace_label" class="formLabel" >Namespace </div>
    <div id="synkNamespaceRecordForm_namespace_text" class="formFieldText" >       
        <input id="namespace" class="field" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNamespaceRecordForm_recordId_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_recordId_label" class="formLabel" >Record Id </div>
    <div id="synkNamespaceRecordForm_recordId_text" class="formFieldText" >       
        <input id="recordId" class="field" type="text" size="70" name="recordId" value="<%=WebUtil.display(_record_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNamespaceRecordForm_stamp_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_stamp_label" class="formLabel" >Stamp </div>
    <div id="synkNamespaceRecordForm_stamp_text" class="formFieldText" >       
        <input id="stamp" class="field" type="text" size="70" name="stamp" value="<%=WebUtil.display(_stampValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="synkNamespaceRecordForm_orgStamp_field" class="formFieldFrame">
    <div id="synkNamespaceRecordForm_orgStamp_label" class="formLabel" >Org Stamp </div>
    <div id="synkNamespaceRecordForm_orgStamp_text" class="formFieldText" >       
        <input id="orgStamp" class="field" type="text" size="70" name="orgStamp" value="<%=WebUtil.display(_org_stampValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_synk_namespace_record_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
