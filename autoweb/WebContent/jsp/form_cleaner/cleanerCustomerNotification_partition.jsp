<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _customer_idValue= "";
    String _reasonfor_idValue= "";
    String _reasonfor_targetValue= "";
    String _notification_typeValue= "";
    String _source_typeValue= "";
    String _trigger_typeValue= "";
    String _is_retransmitValue= "";
    String _method_typeValue= "";
    String _template_typeValue= "";
    String _contentValue= "";
    String _destinationValue= "";
    String _referenceValue= "";
    String _time_scheduledValue= "";
    String _time_createdValue= "";
    String _time_sentValue= "";

%>

<div id="partitionFormFrame_cleaner_customer_notification_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerCustomerNotification_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_customer_notification_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerCustomerNotificationForm_customerId_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerCustomerNotificationForm_customerId_text" class="formFieldText" >       
        <input id="customerId" class="field" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_reasonforId_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_reasonforId_label" class="formLabel" >Reasonfor Id </div>
    <div id="cleanerCustomerNotificationForm_reasonforId_text" class="formFieldText" >       
        <input id="reasonforId" class="field" type="text" size="70" name="reasonforId" value="<%=WebUtil.display(_reasonfor_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_reasonforTarget_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_reasonforTarget_label" class="formLabel" >Reasonfor Target </div>
    <div id="cleanerCustomerNotificationForm_reasonforTarget_text" class="formFieldText" >       
        <input id="reasonforTarget" class="field" type="text" size="70" name="reasonforTarget" value="<%=WebUtil.display(_reasonfor_targetValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_notificationType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_notificationType_label" class="formLabel" >Notification Type </div>
    <div id="cleanerCustomerNotificationForm_notificationType_text" class="formFieldText" >       
        <input id="notificationType" class="field" type="text" size="70" name="notificationType" value="<%=WebUtil.display(_notification_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_sourceType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_sourceType_label" class="formLabel" >Source Type </div>
    <div id="cleanerCustomerNotificationForm_sourceType_text" class="formFieldText" >       
        <input id="sourceType" class="field" type="text" size="70" name="sourceType" value="<%=WebUtil.display(_source_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_triggerType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_triggerType_label" class="formLabel" >Trigger Type </div>
    <div id="cleanerCustomerNotificationForm_triggerType_text" class="formFieldText" >       
        <input id="triggerType" class="field" type="text" size="70" name="triggerType" value="<%=WebUtil.display(_trigger_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_isRetransmit_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_isRetransmit_label" class="formLabel" >Is Retransmit </div>
    <div id="cleanerCustomerNotificationForm_isRetransmit_text" class="formFieldText" >       
        <input id="isRetransmit" class="field" type="text" size="70" name="isRetransmit" value="<%=WebUtil.display(_is_retransmitValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_methodType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_methodType_label" class="formLabel" >Method Type </div>
    <div id="cleanerCustomerNotificationForm_methodType_text" class="formFieldText" >       
        <input id="methodType" class="field" type="text" size="70" name="methodType" value="<%=WebUtil.display(_method_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_templateType_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_templateType_label" class="formLabel" >Template Type </div>
    <div id="cleanerCustomerNotificationForm_templateType_text" class="formFieldText" >       
        <input id="templateType" class="field" type="text" size="70" name="templateType" value="<%=WebUtil.display(_template_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_content_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_content_label" class="formLabel" >Content </div>
    <div id="cleanerCustomerNotificationForm_content_text" class="formFieldText" >       
        <input id="content" class="field" type="text" size="70" name="content" value="<%=WebUtil.display(_contentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_destination_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_destination_label" class="formLabel" >Destination </div>
    <div id="cleanerCustomerNotificationForm_destination_text" class="formFieldText" >       
        <input id="destination" class="field" type="text" size="70" name="destination" value="<%=WebUtil.display(_destinationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_reference_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_reference_label" class="formLabel" >Reference </div>
    <div id="cleanerCustomerNotificationForm_reference_text" class="formFieldText" >       
        <input id="reference" class="field" type="text" size="70" name="reference" value="<%=WebUtil.display(_referenceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerNotificationForm_timeScheduled_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_timeScheduled_label" class="formLabel" >Time Scheduled </div>
    <div id="cleanerCustomerNotificationForm_timeScheduled_text" class="formFieldText" >       
        <input id="timeScheduled" class="field" type="text" size="70" name="timeScheduled" value="<%=WebUtil.display(_time_scheduledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="cleanerCustomerNotificationForm_timeSent_field" class="formFieldFrame">
    <div id="cleanerCustomerNotificationForm_timeSent_label" class="formLabel" >Time Sent </div>
    <div id="cleanerCustomerNotificationForm_timeSent_text" class="formFieldText" >       
        <input id="timeSent" class="field" type="text" size="70" name="timeSent" value="<%=WebUtil.display(_time_sentValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_customer_notification_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
