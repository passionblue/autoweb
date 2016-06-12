<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _location_idValue= "";
    String _customer_idValue= "";
    String _ticket_idValue= "";
    String _ticket_uidValue= "";
    String _pickup_ticketValue= "";
    String _checkin_ticket_for_deliveryValue= "";
    String _is_delivery_requestValue= "";
    String _is_web_requestValue= "";
    String _is_recurring_requestValue= "";
    String _is_receive_readyValue= "";
    String _is_receive_completeValue= "";
    String _recur_idValue= "";
    String _cancelledValue= "";
    String _completedValue= "";
    String _customer_nameValue= "";
    String _addressValue= "";
    String _apt_numberValue= "";
    String _phoneValue= "";
    String _emailValue= "";
    String _ack_receive_methodValue= "";
    String _customer_instructionValue= "";
    String _pickup_delivery_by_dayValue= "";
    String _pickup_delivery_by_timeValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";
    String _time_ackedValue= "";
    String _acked_by_user_idValue= "";
    String _time_readyValue= "";
    String _time_notifiedValue= "";
    String _time_completedValue= "";
    String _noteValue= "";
    String _pickup_noteValue= "";
    String _delivery_noteValue= "";

%>

<div id="partitionFormFrame_cleaner_pickup_delivery_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerPickupDelivery_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_pickup_delivery_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerPickupDeliveryForm_locationId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerPickupDeliveryForm_locationId_text" class="formFieldText" >       
        <input id="locationId" class="field" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_customerId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerPickupDeliveryForm_customerId_text" class="formFieldText" >       
        <input id="customerId" class="field" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_ticketId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_ticketId_label" class="formLabel" >Ticket Id </div>
    <div id="cleanerPickupDeliveryForm_ticketId_text" class="formFieldText" >       
        <input id="ticketId" class="field" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_ticketUid_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_ticketUid_label" class="formLabel" >Ticket Uid </div>
    <div id="cleanerPickupDeliveryForm_ticketUid_text" class="formFieldText" >       
        <input id="ticketUid" class="field" type="text" size="70" name="ticketUid" value="<%=WebUtil.display(_ticket_uidValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_pickupTicket_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_pickupTicket_label" class="formLabel" >Pickup Ticket </div>
    <div id="cleanerPickupDeliveryForm_pickupTicket_text" class="formFieldText" >       
        <input id="pickupTicket" class="field" type="text" size="70" name="pickupTicket" value="<%=WebUtil.display(_pickup_ticketValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_label" class="formLabel" >Checkin Ticket For Delivery </div>
    <div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_text" class="formFieldText" >       
        <input id="checkinTicketForDelivery" class="field" type="text" size="70" name="checkinTicketForDelivery" value="<%=WebUtil.display(_checkin_ticket_for_deliveryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="cleanerPickupDeliveryForm_isDeliveryRequest_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_isDeliveryRequest_label" class="formLabel" >Is Delivery Request </div>
    <div id="cleanerPickupDeliveryForm_isDeliveryRequest_dropdown" class="formFieldDropDown" >       
        <select name="isDeliveryRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_delivery_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_delivery_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_isWebRequest_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_isWebRequest_label" class="formLabel" >Is Web Request </div>
    <div id="cleanerPickupDeliveryForm_isWebRequest_dropdown" class="formFieldDropDown" >       
        <select name="isWebRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_web_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_web_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_isRecurringRequest_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_isRecurringRequest_label" class="formLabel" >Is Recurring Request </div>
    <div id="cleanerPickupDeliveryForm_isRecurringRequest_dropdown" class="formFieldDropDown" >       
        <select name="isRecurringRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_recurring_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_recurring_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="cleanerPickupDeliveryForm_isReceiveReady_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_isReceiveReady_label" class="formLabel" >Is Receive Ready </div>
    <div id="cleanerPickupDeliveryForm_isReceiveReady_text" class="formFieldText" >       
        <input id="isReceiveReady" class="field" type="text" size="70" name="isReceiveReady" value="<%=WebUtil.display(_is_receive_readyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_isReceiveComplete_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_isReceiveComplete_label" class="formLabel" >Is Receive Complete </div>
    <div id="cleanerPickupDeliveryForm_isReceiveComplete_text" class="formFieldText" >       
        <input id="isReceiveComplete" class="field" type="text" size="70" name="isReceiveComplete" value="<%=WebUtil.display(_is_receive_completeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_recurId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_recurId_label" class="formLabel" >Recur Id </div>
    <div id="cleanerPickupDeliveryForm_recurId_text" class="formFieldText" >       
        <input id="recurId" class="field" type="text" size="70" name="recurId" value="<%=WebUtil.display(_recur_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="cleanerPickupDeliveryForm_cancelled_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_cancelled_label" class="formLabel" >Cancelled </div>
    <div id="cleanerPickupDeliveryForm_cancelled_dropdown" class="formFieldDropDown" >       
        <select name="cancelled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _cancelledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _cancelledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_completed_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_completed_label" class="formLabel" >Completed </div>
    <div id="cleanerPickupDeliveryForm_completed_dropdown" class="formFieldDropDown" >       
        <select name="completed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _completedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _completedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="cleanerPickupDeliveryForm_customerName_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_customerName_label" class="formLabel" >Customer Name </div>
    <div id="cleanerPickupDeliveryForm_customerName_text" class="formFieldText" >       
        <input id="customerName" class="field" type="text" size="70" name="customerName" value="<%=WebUtil.display(_customer_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_address_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_address_label" class="formLabel" >Address </div>
    <div id="cleanerPickupDeliveryForm_address_text" class="formFieldText" >       
        <input id="address" class="field" type="text" size="70" name="address" value="<%=WebUtil.display(_addressValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_aptNumber_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_aptNumber_label" class="formLabel" >Apt Number </div>
    <div id="cleanerPickupDeliveryForm_aptNumber_text" class="formFieldText" >       
        <input id="aptNumber" class="field" type="text" size="70" name="aptNumber" value="<%=WebUtil.display(_apt_numberValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_phone_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_phone_label" class="formLabel" >Phone </div>
    <div id="cleanerPickupDeliveryForm_phone_text" class="formFieldText" >       
        <input id="phone" class="field" type="text" size="70" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryForm_email_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_email_label" class="formLabel" >Email </div>
    <div id="cleanerPickupDeliveryForm_email_text" class="formFieldText" >       
        <input id="email" class="field" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="cleanerPickupDeliveryForm_ackReceiveMethod_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_ackReceiveMethod_label" class="formLabel" >Ack Receive Method </div>
    <div id="cleanerPickupDeliveryForm_ackReceiveMethod_dropdown" class="formFieldDropDown" >       
        <select class="field" name="ackReceiveMethod" id="ackReceiveMethod">
        <option value="" >- Please Select -</option>
<%
	List dropMenusAckReceiveMethod = DropMenuUtil.getDropMenus("CleanerCustomerNotificationPreference");
	for(Iterator iterItems = dropMenusAckReceiveMethod.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _ack_receive_methodValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="cleanerPickupDeliveryForm_customerInstruction_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_customerInstruction_label" class="formLabel" >Customer Instruction </div>
    <div id="cleanerPickupDeliveryForm_customerInstruction_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="customerInstruction" class="field" NAME="customerInstruction" COLS="50" ROWS="8" ><%=WebUtil.display(_customer_instructionValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_label" class="formLabel" >Pickup Delivery By Day </div>
    <div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_dropdown" class="formFieldDropDown" >       
        <select class="field" name="pickupDeliveryByDay" id="pickupDeliveryByDay">
        <option value="" >- Please Select -</option>
<%
	List dropMenusPickupDeliveryByDay = DropMenuUtil.getDropMenus("CleanerPickupDeliveryTargetDay");
	for(Iterator iterItems = dropMenusPickupDeliveryByDay.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pickup_delivery_by_dayValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="cleanerPickupDeliveryForm_pickupDeliveryByTime_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_pickupDeliveryByTime_label" class="formLabel" >Pickup Delivery By Time </div>
    <div id="cleanerPickupDeliveryForm_pickupDeliveryByTime_dropdown" class="formFieldDropDown" >       
        <select class="field" name="pickupDeliveryByTime" id="pickupDeliveryByTime">
        <option value="" >- Please Select -</option>
<%
	List dropMenusPickupDeliveryByTime = DropMenuUtil.getDropMenus("CleanerPickupDeliveryTargetTime");
	for(Iterator iterItems = dropMenusPickupDeliveryByTime.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pickup_delivery_by_timeValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>













	<div id="cleanerPickupDeliveryForm_ackedByUserId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_ackedByUserId_label" class="formLabel" >Acked By User Id </div>
    <div id="cleanerPickupDeliveryForm_ackedByUserId_text" class="formFieldText" >       
        <input id="ackedByUserId" class="field" type="text" size="70" name="ackedByUserId" value="<%=WebUtil.display(_acked_by_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>











	<div id="cleanerPickupDeliveryForm_note_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerPickupDeliveryForm_note_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="note" class="field" NAME="note" COLS="50" ROWS="8" ><%=WebUtil.display(_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="cleanerPickupDeliveryForm_pickupNote_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_pickupNote_label" class="formLabel" >Pickup Note </div>
    <div id="cleanerPickupDeliveryForm_pickupNote_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="pickupNote" class="field" NAME="pickupNote" COLS="50" ROWS="8" ><%=WebUtil.display(_pickup_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="cleanerPickupDeliveryForm_deliveryNote_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_deliveryNote_label" class="formLabel" >Delivery Note </div>
    <div id="cleanerPickupDeliveryForm_deliveryNote_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="deliveryNote" class="field" NAME="deliveryNote" COLS="50" ROWS="8" ><%=WebUtil.display(_delivery_noteValue)%></TEXTAREA>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_pickup_delivery_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
