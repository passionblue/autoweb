<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _serialValue= "";
    String _parent_ticket_idValue= "";
    String _customer_idValue= "";
    String _enter_user_idValue= "";
    String _location_idValue= "";
    String _noteValue= "";
    String _completedValue= "";
    String _onholdValue= "";
    String _original_ticket_idValue= "";
    String _returnedValue= "";
    String _returned_reason_textValue= "";
    String _returned_noteValue= "";
    String _total_chargeValue= "";
    String _final_chargeValue= "";
    String _discount_idValue= "";
    String _discount_amountValue= "";
    String _discount_noteValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";
    String _time_completedValue= "";
    String _time_onholdValue= "";

%>

<div id="partitionFormFrame_cleaner_ticket_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerTicket_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_ticket_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerTicketForm_serial_field" class="formFieldFrame">
    <div id="cleanerTicketForm_serial_label" class="formLabel" >Serial </div>
    <div id="cleanerTicketForm_serial_text" class="formFieldText" >       
        <input id="serial" class="field" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_parentTicketId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_parentTicketId_label" class="formLabel" >Parent Ticket Id </div>
    <div id="cleanerTicketForm_parentTicketId_text" class="formFieldText" >       
        <input id="parentTicketId" class="field" type="text" size="70" name="parentTicketId" value="<%=WebUtil.display(_parent_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_customerId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerTicketForm_customerId_text" class="formFieldText" >       
        <input id="customerId" class="field" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_enterUserId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_enterUserId_label" class="formLabel" >Enter User Id </div>
    <div id="cleanerTicketForm_enterUserId_text" class="formFieldText" >       
        <input id="enterUserId" class="field" type="text" size="70" name="enterUserId" value="<%=WebUtil.display(_enter_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_locationId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerTicketForm_locationId_text" class="formFieldText" >       
        <input id="locationId" class="field" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_note_field" class="formFieldFrame">
    <div id="cleanerTicketForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerTicketForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_completed_field" class="formFieldFrame">
    <div id="cleanerTicketForm_completed_label" class="formLabel" >Completed </div>
    <div id="cleanerTicketForm_completed_text" class="formFieldText" >       
        <input id="completed" class="field" type="text" size="70" name="completed" value="<%=WebUtil.display(_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_onhold_field" class="formFieldFrame">
    <div id="cleanerTicketForm_onhold_label" class="formLabel" >Onhold </div>
    <div id="cleanerTicketForm_onhold_text" class="formFieldText" >       
        <input id="onhold" class="field" type="text" size="70" name="onhold" value="<%=WebUtil.display(_onholdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_originalTicketId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_originalTicketId_label" class="formLabel" >Original Ticket Id </div>
    <div id="cleanerTicketForm_originalTicketId_text" class="formFieldText" >       
        <input id="originalTicketId" class="field" type="text" size="70" name="originalTicketId" value="<%=WebUtil.display(_original_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_returned_field" class="formFieldFrame">
    <div id="cleanerTicketForm_returned_label" class="formLabel" >Returned </div>
    <div id="cleanerTicketForm_returned_text" class="formFieldText" >       
        <input id="returned" class="field" type="text" size="70" name="returned" value="<%=WebUtil.display(_returnedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_returnedReasonText_field" class="formFieldFrame">
    <div id="cleanerTicketForm_returnedReasonText_label" class="formLabel" >Returned Reason Text </div>
    <div id="cleanerTicketForm_returnedReasonText_text" class="formFieldText" >       
        <input id="returnedReasonText" class="field" type="text" size="70" name="returnedReasonText" value="<%=WebUtil.display(_returned_reason_textValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_returnedNote_field" class="formFieldFrame">
    <div id="cleanerTicketForm_returnedNote_label" class="formLabel" >Returned Note </div>
    <div id="cleanerTicketForm_returnedNote_text" class="formFieldText" >       
        <input id="returnedNote" class="field" type="text" size="70" name="returnedNote" value="<%=WebUtil.display(_returned_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_totalCharge_field" class="formFieldFrame">
    <div id="cleanerTicketForm_totalCharge_label" class="formLabel" >Total Charge </div>
    <div id="cleanerTicketForm_totalCharge_text" class="formFieldText" >       
        <input id="totalCharge" class="field" type="text" size="70" name="totalCharge" value="<%=WebUtil.display(_total_chargeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_finalCharge_field" class="formFieldFrame">
    <div id="cleanerTicketForm_finalCharge_label" class="formLabel" >Final Charge </div>
    <div id="cleanerTicketForm_finalCharge_text" class="formFieldText" >       
        <input id="finalCharge" class="field" type="text" size="70" name="finalCharge" value="<%=WebUtil.display(_final_chargeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_discountId_field" class="formFieldFrame">
    <div id="cleanerTicketForm_discountId_label" class="formLabel" >Discount Id </div>
    <div id="cleanerTicketForm_discountId_text" class="formFieldText" >       
        <input id="discountId" class="field" type="text" size="70" name="discountId" value="<%=WebUtil.display(_discount_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_discountAmount_field" class="formFieldFrame">
    <div id="cleanerTicketForm_discountAmount_label" class="formLabel" >Discount Amount </div>
    <div id="cleanerTicketForm_discountAmount_text" class="formFieldText" >       
        <input id="discountAmount" class="field" type="text" size="70" name="discountAmount" value="<%=WebUtil.display(_discount_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_discountNote_field" class="formFieldFrame">
    <div id="cleanerTicketForm_discountNote_label" class="formLabel" >Discount Note </div>
    <div id="cleanerTicketForm_discountNote_text" class="formFieldText" >       
        <input id="discountNote" class="field" type="text" size="70" name="discountNote" value="<%=WebUtil.display(_discount_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>










	<div id="cleanerTicketForm_timeCompleted_field" class="formFieldFrame">
    <div id="cleanerTicketForm_timeCompleted_label" class="formLabel" >Time Completed </div>
    <div id="cleanerTicketForm_timeCompleted_text" class="formFieldText" >       
        <input id="timeCompleted" class="field" type="text" size="70" name="timeCompleted" value="<%=WebUtil.display(_time_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerTicketForm_timeOnhold_field" class="formFieldFrame">
    <div id="cleanerTicketForm_timeOnhold_label" class="formLabel" >Time Onhold </div>
    <div id="cleanerTicketForm_timeOnhold_text" class="formFieldText" >       
        <input id="timeOnhold" class="field" type="text" size="70" name="timeOnhold" value="<%=WebUtil.display(_time_onholdValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_ticket_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
