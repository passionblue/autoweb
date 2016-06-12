<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _ticket_idValue= "";
    String _process_user_idValue= "";
    String _process_typeValue= "";
    String _time_startedValue= "";
    String _time_endedValue= "";
    String _noteValue= "";

%>

<div id="partitionFormFrame_cleaner_service_process_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerServiceProcess_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_service_process_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerServiceProcessForm_ticketId_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_ticketId_label" class="formLabel" >Ticket Id </div>
    <div id="cleanerServiceProcessForm_ticketId_text" class="formFieldText" >       
        <input id="ticketId" class="field" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_processUserId_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_processUserId_label" class="formLabel" >Process User Id </div>
    <div id="cleanerServiceProcessForm_processUserId_text" class="formFieldText" >       
        <input id="processUserId" class="field" type="text" size="70" name="processUserId" value="<%=WebUtil.display(_process_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_processType_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_processType_label" class="formLabel" >Process Type </div>
    <div id="cleanerServiceProcessForm_processType_text" class="formFieldText" >       
        <input id="processType" class="field" type="text" size="70" name="processType" value="<%=WebUtil.display(_process_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_timeStarted_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_timeStarted_label" class="formLabel" >Time Started </div>
    <div id="cleanerServiceProcessForm_timeStarted_text" class="formFieldText" >       
        <input id="timeStarted" class="field" type="text" size="70" name="timeStarted" value="<%=WebUtil.display(_time_startedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_timeEnded_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_timeEnded_label" class="formLabel" >Time Ended </div>
    <div id="cleanerServiceProcessForm_timeEnded_text" class="formFieldText" >       
        <input id="timeEnded" class="field" type="text" size="70" name="timeEnded" value="<%=WebUtil.display(_time_endedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceProcessForm_note_field" class="formFieldFrame">
    <div id="cleanerServiceProcessForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerServiceProcessForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_service_process_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
