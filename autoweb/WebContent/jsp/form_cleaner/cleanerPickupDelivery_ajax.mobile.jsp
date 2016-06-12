<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:44 EDT 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_pickup_delivery_home";

    String _location_idValue= WebUtil.display((String)reqParams.get("locationId"));
    String _customer_idValue= WebUtil.display((String)reqParams.get("customerId"));
    String _ticket_idValue= WebUtil.display((String)reqParams.get("ticketId"));
    String _ticket_uidValue= WebUtil.display((String)reqParams.get("ticketUid"));
    String _pickup_ticketValue= WebUtil.display((String)reqParams.get("pickupTicket"));
    String _checkin_ticket_for_deliveryValue= WebUtil.display((String)reqParams.get("checkinTicketForDelivery"));
    String _is_delivery_requestValue= WebUtil.display((String)reqParams.get("isDeliveryRequest"));
    String _is_web_requestValue= WebUtil.display((String)reqParams.get("isWebRequest"));
    String _is_recurring_requestValue= WebUtil.display((String)reqParams.get("isRecurringRequest"));
    String _is_receive_readyValue= WebUtil.display((String)reqParams.get("isReceiveReady"));
    String _is_receive_completeValue= WebUtil.display((String)reqParams.get("isReceiveComplete"));
    String _recur_idValue= WebUtil.display((String)reqParams.get("recurId"));
    String _cancelledValue= WebUtil.display((String)reqParams.get("cancelled"));
    String _completedValue= WebUtil.display((String)reqParams.get("completed"));
    String _customer_nameValue= WebUtil.display((String)reqParams.get("customerName"));
    String _addressValue= WebUtil.display((String)reqParams.get("address"));
    String _apt_numberValue= WebUtil.display((String)reqParams.get("aptNumber"));
    String _phoneValue= WebUtil.display((String)reqParams.get("phone"));
    String _emailValue= WebUtil.display((String)reqParams.get("email"));
    String _ack_receive_methodValue= WebUtil.display((String)reqParams.get("ackReceiveMethod"));
    String _customer_instructionValue= WebUtil.display((String)reqParams.get("customerInstruction"));
    String _pickup_delivery_by_dayValue= WebUtil.display((String)reqParams.get("pickupDeliveryByDay"));
    String _pickup_delivery_by_timeValue= WebUtil.display((String)reqParams.get("pickupDeliveryByTime"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
    String _time_ackedValue= WebUtil.display((String)reqParams.get("timeAcked"));
    String _acked_by_user_idValue= WebUtil.display((String)reqParams.get("ackedByUserId"));
    String _time_readyValue= WebUtil.display((String)reqParams.get("timeReady"));
    String _time_notifiedValue= WebUtil.display((String)reqParams.get("timeNotified"));
    String _time_completedValue= WebUtil.display((String)reqParams.get("timeCompleted"));
    String _noteValue= WebUtil.display((String)reqParams.get("note"));
    String _pickup_noteValue= WebUtil.display((String)reqParams.get("pickupNote"));
    String _delivery_noteValue= WebUtil.display((String)reqParams.get("deliveryNote"));
%> 

<a href="/v_cleaner_pickup_delivery_home.html"> CleanerPickupDelivery Home </a>
<%
	
	List list = null;
	list = CleanerPickupDeliveryDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerPickupDeliveryList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) iter.next();	
%>

	<div id="cleanerPickupDeliveryFrame<%=_CleanerPickupDelivery.getId() %>" >

		<div id="locationId" >
			locationId:<%= _CleanerPickupDelivery.getLocationId() %>
		</div>
		<div id="customerId" >
			customerId:<%= _CleanerPickupDelivery.getCustomerId() %>
		</div>
		<div id="ticketId" >
			ticketId:<%= _CleanerPickupDelivery.getTicketId() %>
		</div>
		<div id="ticketUid" >
			ticketUid:<%= _CleanerPickupDelivery.getTicketUid() %>
		</div>
		<div id="pickupTicket" >
			pickupTicket:<%= _CleanerPickupDelivery.getPickupTicket() %>
		</div>
		<div id="checkinTicketForDelivery" >
			checkinTicketForDelivery:<%= _CleanerPickupDelivery.getCheckinTicketForDelivery() %>
		</div>
		<div id="isDeliveryRequest" >
			isDeliveryRequest:<%= _CleanerPickupDelivery.getIsDeliveryRequest() %>
		</div>
		<div id="isWebRequest" >
			isWebRequest:<%= _CleanerPickupDelivery.getIsWebRequest() %>
		</div>
		<div id="isRecurringRequest" >
			isRecurringRequest:<%= _CleanerPickupDelivery.getIsRecurringRequest() %>
		</div>
		<div id="isReceiveReady" >
			isReceiveReady:<%= _CleanerPickupDelivery.getIsReceiveReady() %>
		</div>
		<div id="isReceiveComplete" >
			isReceiveComplete:<%= _CleanerPickupDelivery.getIsReceiveComplete() %>
		</div>
		<div id="recurId" >
			recurId:<%= _CleanerPickupDelivery.getRecurId() %>
		</div>
		<div id="cancelled" >
			cancelled:<%= _CleanerPickupDelivery.getCancelled() %>
		</div>
		<div id="completed" >
			completed:<%= _CleanerPickupDelivery.getCompleted() %>
		</div>
		<div id="customerName" >
			customerName:<%= _CleanerPickupDelivery.getCustomerName() %>
		</div>
		<div id="address" >
			address:<%= _CleanerPickupDelivery.getAddress() %>
		</div>
		<div id="aptNumber" >
			aptNumber:<%= _CleanerPickupDelivery.getAptNumber() %>
		</div>
		<div id="phone" >
			phone:<%= _CleanerPickupDelivery.getPhone() %>
		</div>
		<div id="email" >
			email:<%= _CleanerPickupDelivery.getEmail() %>
		</div>
		<div id="ackReceiveMethod" >
			ackReceiveMethod:<%= _CleanerPickupDelivery.getAckReceiveMethod() %>
		</div>
		<div id="customerInstruction" >
			customerInstruction:<%= _CleanerPickupDelivery.getCustomerInstruction() %>
		</div>
		<div id="pickupDeliveryByDay" >
			pickupDeliveryByDay:<%= _CleanerPickupDelivery.getPickupDeliveryByDay() %>
		</div>
		<div id="pickupDeliveryByTime" >
			pickupDeliveryByTime:<%= _CleanerPickupDelivery.getPickupDeliveryByTime() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _CleanerPickupDelivery.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _CleanerPickupDelivery.getTimeUpdated() %>
		</div>
		<div id="timeAcked" >
			timeAcked:<%= _CleanerPickupDelivery.getTimeAcked() %>
		</div>
		<div id="ackedByUserId" >
			ackedByUserId:<%= _CleanerPickupDelivery.getAckedByUserId() %>
		</div>
		<div id="timeReady" >
			timeReady:<%= _CleanerPickupDelivery.getTimeReady() %>
		</div>
		<div id="timeNotified" >
			timeNotified:<%= _CleanerPickupDelivery.getTimeNotified() %>
		</div>
		<div id="timeCompleted" >
			timeCompleted:<%= _CleanerPickupDelivery.getTimeCompleted() %>
		</div>
		<div id="note" >
			note:<%= _CleanerPickupDelivery.getNote() %>
		</div>
		<div id="pickupNote" >
			pickupNote:<%= _CleanerPickupDelivery.getPickupNote() %>
		</div>
		<div id="deliveryNote" >
			deliveryNote:<%= _CleanerPickupDelivery.getDeliveryNote() %>
		</div>
		<div>
		<a id="cleanerPickupDeliveryDeleteButton" href="javascript:deleteThis('/cleanerPickupDeliveryAction.html',<%= _CleanerPickupDelivery.getId()%>,'cleanerPickupDeliveryFrame<%=_CleanerPickupDelivery.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerPickupDeliveryForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerPickupDeliveryFormAdd" method="POST" action="/cleanerPickupDeliveryAction.html" id="cleanerPickupDeliveryFormAdd">

	<div id="cleanerPickupDeliveryForm_locationId_field">
    <div id="cleanerPickupDeliveryForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerPickupDeliveryForm_locationId_text" class="formFieldText" >       
        <input class="field" id="_ffd_locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_customerId_field">
    <div id="cleanerPickupDeliveryForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerPickupDeliveryForm_customerId_text" class="formFieldText" >       
        <input class="field" id="_ffd_customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_ticketId_field">
    <div id="cleanerPickupDeliveryForm_ticketId_label" class="formLabel" >Ticket Id </div>
    <div id="cleanerPickupDeliveryForm_ticketId_text" class="formFieldText" >       
        <input class="field" id="_ffd_ticketId" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_ticketUid_field">
    <div id="cleanerPickupDeliveryForm_ticketUid_label" class="formLabel" >Ticket Uid </div>
    <div id="cleanerPickupDeliveryForm_ticketUid_text" class="formFieldText" >       
        <input class="field" id="_ffd_ticketUid" type="text" size="70" name="ticketUid" value="<%=WebUtil.display(_ticket_uidValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_pickupTicket_field">
    <div id="cleanerPickupDeliveryForm_pickupTicket_label" class="formLabel" >Pickup Ticket </div>
    <div id="cleanerPickupDeliveryForm_pickupTicket_text" class="formFieldText" >       
        <input class="field" id="_ffd_pickupTicket" type="text" size="70" name="pickupTicket" value="<%=WebUtil.display(_pickup_ticketValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_field">
    <div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_label" class="formLabel" >Checkin Ticket For Delivery </div>
    <div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_text" class="formFieldText" >       
        <input class="field" id="_ffd_checkinTicketForDelivery" type="text" size="70" name="checkinTicketForDelivery" value="<%=WebUtil.display(_checkin_ticket_for_deliveryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_isDeliveryRequest_field">
    <div id="cleanerPickupDeliveryForm_isDeliveryRequest_label" class="formLabel" >Is Delivery Request </div>
    <div id="cleanerPickupDeliveryForm_isDeliveryRequest_dropdown" class="formFieldDropDown" >       
        <select name="isDeliveryRequest" id="_ffd_isDeliveryRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_delivery_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_delivery_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_isWebRequest_field">
    <div id="cleanerPickupDeliveryForm_isWebRequest_label" class="formLabel" >Is Web Request </div>
    <div id="cleanerPickupDeliveryForm_isWebRequest_dropdown" class="formFieldDropDown" >       
        <select name="isWebRequest" id="_ffd_isWebRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_web_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_web_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_isRecurringRequest_field">
    <div id="cleanerPickupDeliveryForm_isRecurringRequest_label" class="formLabel" >Is Recurring Request </div>
    <div id="cleanerPickupDeliveryForm_isRecurringRequest_dropdown" class="formFieldDropDown" >       
        <select name="isRecurringRequest" id="_ffd_isRecurringRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_recurring_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_recurring_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_isReceiveReady_field">
    <div id="cleanerPickupDeliveryForm_isReceiveReady_label" class="formLabel" >Is Receive Ready </div>
    <div id="cleanerPickupDeliveryForm_isReceiveReady_text" class="formFieldText" >       
        <input class="field" id="_ffd_isReceiveReady" type="text" size="70" name="isReceiveReady" value="<%=WebUtil.display(_is_receive_readyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_isReceiveComplete_field">
    <div id="cleanerPickupDeliveryForm_isReceiveComplete_label" class="formLabel" >Is Receive Complete </div>
    <div id="cleanerPickupDeliveryForm_isReceiveComplete_text" class="formFieldText" >       
        <input class="field" id="_ffd_isReceiveComplete" type="text" size="70" name="isReceiveComplete" value="<%=WebUtil.display(_is_receive_completeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_recurId_field">
    <div id="cleanerPickupDeliveryForm_recurId_label" class="formLabel" >Recur Id </div>
    <div id="cleanerPickupDeliveryForm_recurId_text" class="formFieldText" >       
        <input class="field" id="_ffd_recurId" type="text" size="70" name="recurId" value="<%=WebUtil.display(_recur_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_cancelled_field">
    <div id="cleanerPickupDeliveryForm_cancelled_label" class="formLabel" >Cancelled </div>
    <div id="cleanerPickupDeliveryForm_cancelled_dropdown" class="formFieldDropDown" >       
        <select name="cancelled" id="_ffd_cancelled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _cancelledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _cancelledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_completed_field">
    <div id="cleanerPickupDeliveryForm_completed_label" class="formLabel" >Completed </div>
    <div id="cleanerPickupDeliveryForm_completed_dropdown" class="formFieldDropDown" >       
        <select name="completed" id="_ffd_completed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _completedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _completedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_customerName_field">
    <div id="cleanerPickupDeliveryForm_customerName_label" class="formLabel" >Customer Name </div>
    <div id="cleanerPickupDeliveryForm_customerName_text" class="formFieldText" >       
        <input class="field" id="_ffd_customerName" type="text" size="70" name="customerName" value="<%=WebUtil.display(_customer_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_address_field">
    <div id="cleanerPickupDeliveryForm_address_label" class="formLabel" >Address </div>
    <div id="cleanerPickupDeliveryForm_address_text" class="formFieldText" >       
        <input class="field" id="_ffd_address" type="text" size="70" name="address" value="<%=WebUtil.display(_addressValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_aptNumber_field">
    <div id="cleanerPickupDeliveryForm_aptNumber_label" class="formLabel" >Apt Number </div>
    <div id="cleanerPickupDeliveryForm_aptNumber_text" class="formFieldText" >       
        <input class="field" id="_ffd_aptNumber" type="text" size="70" name="aptNumber" value="<%=WebUtil.display(_apt_numberValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_phone_field">
    <div id="cleanerPickupDeliveryForm_phone_label" class="formLabel" >Phone </div>
    <div id="cleanerPickupDeliveryForm_phone_text" class="formFieldText" >       
        <input class="field" id="_ffd_phone" type="text" size="70" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_email_field">
    <div id="cleanerPickupDeliveryForm_email_label" class="formLabel" >Email </div>
    <div id="cleanerPickupDeliveryForm_email_text" class="formFieldText" >       
        <input class="field" id="_ffd_email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_ackReceiveMethod_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_ackReceiveMethod_label" class="formLabel" >Ack Receive Method </div>
    <div id="cleanerPickupDeliveryForm_ackReceiveMethod_dropdown" class="formFieldDropDown" >       
        <select class="field" name="ackReceiveMethod" id="_ffd_ackReceiveMethod">
        <option value="" >- Please Select -</option>
<%
	List dropMenusAckReceiveMethod = DropMenuUtil.getDropMenus("CleanerPickupDeliveryAckReceiveMethodOption");
	for(Iterator iterItems = dropMenusAckReceiveMethod.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _ack_receive_methodValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_customerInstruction_field">
    <div id="cleanerPickupDeliveryForm_customerInstruction_label" class="formLabel" >Customer Instruction </div>
    <div id="cleanerPickupDeliveryForm_customerInstruction_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_customerInstruction" class="field" NAME="customerInstruction" COLS="50" ROWS="8" ><%=WebUtil.display(_customer_instructionValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_label" class="formLabel" >Pickup Delivery By Day </div>
    <div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_dropdown" class="formFieldDropDown" >       
        <select class="field" name="pickupDeliveryByDay" id="_ffd_pickupDeliveryByDay">
        <option value="" >- Please Select -</option>
<%
	List dropMenusPickupDeliveryByDay = DropMenuUtil.getDropMenus("CleanerPickupDeliveryPickupDeliveryByDayOption");
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
        <select class="field" name="pickupDeliveryByTime" id="_ffd_pickupDeliveryByTime">
        <option value="" >- Please Select -</option>
<%
	List dropMenusPickupDeliveryByTime = DropMenuUtil.getDropMenus("CleanerPickupDeliveryPickupDeliveryByTimeOption");
	for(Iterator iterItems = dropMenusPickupDeliveryByTime.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pickup_delivery_by_timeValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_ackedByUserId_field">
    <div id="cleanerPickupDeliveryForm_ackedByUserId_label" class="formLabel" >Acked By User Id </div>
    <div id="cleanerPickupDeliveryForm_ackedByUserId_text" class="formFieldText" >       
        <input class="field" id="_ffd_ackedByUserId" type="text" size="70" name="ackedByUserId" value="<%=WebUtil.display(_acked_by_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_note_field">
    <div id="cleanerPickupDeliveryForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerPickupDeliveryForm_note_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_note" class="field" NAME="note" COLS="50" ROWS="8" ><%=WebUtil.display(_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_pickupNote_field">
    <div id="cleanerPickupDeliveryForm_pickupNote_label" class="formLabel" >Pickup Note </div>
    <div id="cleanerPickupDeliveryForm_pickupNote_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_pickupNote" class="field" NAME="pickupNote" COLS="50" ROWS="8" ><%=WebUtil.display(_pickup_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryForm_deliveryNote_field">
    <div id="cleanerPickupDeliveryForm_deliveryNote_label" class="formLabel" >Delivery Note </div>
    <div id="cleanerPickupDeliveryForm_deliveryNote_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_deliveryNote" class="field" NAME="deliveryNote" COLS="50" ROWS="8" ><%=WebUtil.display(_delivery_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerPickupDeliveryFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerPickupDeliveryAction.html', 'cleanerPickupDeliveryFormAdd', 'formSubmit_ajax', 'cleanerPickupDelivery');">Submit</a>
        </div> 

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->

<hr>

<br/><br/><br/><br/>

<hr>
<h3> Self hiding pure javascript form</h3>

<script type="text/javascript">

function xmlhttpPostXX(formName, target, dispElementId, dispFieldList,  callback) {
	
	if (document.getElementById(formName) == null){
		alert("Client side Error occurred. Please try again.")
		return;
	}
	
	var parms = getXX(document.getElementById(formName));
	parms += "&ajaxRequest=true&ajaxOut=getlisthtml&ajaxOutArg=last&formfieldlist="+dispFieldList;
	
    var xmlHttpReq = false;
    var self = this;
    
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var strURL = target+ "?" + parms;
    //alert(strURL);
    
    self.xmlHttpReq.open('POST', target, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
    	if (self.xmlHttpReq.readyState == 4) {
        	//alert(self.xmlHttpReq.responseText);
        	fade(formName, 1000, function() {
        		if (callback == null )
        			updatepageXX(dispElementId, dispElementId, self.xmlHttpReq.responseText);
        		else
        			callback(self.xmlHttpReq.responseText);
        	});
        }
    }
    self.xmlHttpReq.send(parms);
}

function updatepageXX(eid, str){
	document.getElementById(eid).innerHTML = str;
}

function getXX(obj) {
	var getstr = "";
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
       
           if (obj.childNodes[i].type == "text") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "password") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "hidden") {
           		alert(obj.childNodes[i].name + "=" + obj.childNodes[i].value);
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "file") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             } else {
                getstr += obj.childNodes[i].name + "=&";
             }
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             }
          }
       }   
       
       if (obj.childNodes[i].tagName == "SELECT") {
           var sel = obj.childNodes[i];
           getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
       }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
       }
    }
	alert(getstr);
    return getstr;
}



function clearFormXX(name) {
	var obj = document.getElementById(name);
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
           if (obj.childNodes[i].type == "text") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "password") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "file") {
               obj.childNodes[i].value = "";
           }
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             } 
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             }
          }
       }   
       if (obj.childNodes[i].tagName == "SELECT") {
			obj.childNodes[i].selectedIndex = 0;
        }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           obj.childNodes[i].value = "";
        }
        
    }
}


function  animateFadeXX(lastTick, eid, timeToFade)
{  
  var curTick = new Date().getTime();
  var elapsedTicks = curTick - lastTick;
 
  var element = document.getElementById(eid);
 
  if(element.FadeTimeLeft <= elapsedTicks)
  {
    element.style.opacity = element.FadeState == 1 ? '1' : '0';
    element.style.filter = 'alpha(opacity = '
        + (element.FadeState == 1 ? '100' : '0') + ')';
    element.FadeState = element.FadeState == 1 ? 2 : -2;
	document.getElementById(eid).style.display = 'none';
	element.callbackAfter(element.callbackArg);
    return;
  }
 
  element.FadeTimeLeft -= elapsedTicks;
  var newOpVal = element.FadeTimeLeft/timeToFade;
  if(element.FadeState == 1)
    newOpVal = 1 - newOpVal;

  element.style.opacity = newOpVal;
  element.style.filter = 'alpha(opacity = ' + (newOpVal*100) + ')';
 
  setTimeout("animateFadeXX(" + curTick + ",'" + eid + "','" + timeToFade + "')", 33);
}


//var  TimeToFade = 1000.0;

function fadeXX(eid, timeToFade, callback, callbackArg)
{
  var element = document.getElementById(eid);
  if(element == null)
    return;
   
  if(element.FadeState == null)
  {
    if(element.style.opacity == null
        || element.style.opacity == ''
        || element.style.opacity == '1')
    {
      element.FadeState = 2;
    }
    else
    {
      element.FadeState = -2;
    }
  }
   
  if(element.FadeState == 1 || element.FadeState == -1)
  {
    element.FadeState = element.FadeState == 1 ? -1 : 1;
    element.FadeTimeLeft = timeToFade - element.FadeTimeLeft;
  }
  else
  {
    element.FadeState = element.FadeState == 2 ? -1 : 1;
    element.FadeTimeLeft = timeToFade;
	
    element.callbackAfter = callback;
    element.callbackArg = callbackArg;
    setTimeout("animateFadeXX(" + new Date().getTime() + ",'" + eid + "','" + timeToFade + "')", 33);
  }  
}

function backToXX(eid, displayFormId){
	document.getElementById(displayFormId).innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

</script>

<script type="text/javascript">

function responseCallback(data){
	document.getElementById("resultDisplayCleanerPickupDelivery").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerPickupDeliveryFormAddDis','/cleanerPickupDeliveryAction.html', 'resultDisplayCleanerPickupDelivery', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerPickupDeliveryFormAddDis" method="POST" action="/cleanerPickupDeliveryAction.html" id="cleanerPickupDeliveryFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Location Id</div>
        <input class="field" id="locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Customer Id</div>
        <input class="field" id="customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ticket Id</div>
        <input class="field" id="ticketId" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ticket Uid</div>
        <input class="field" id="ticketUid" type="text" size="70" name="ticketUid" value="<%=WebUtil.display(_ticket_uidValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pickup Ticket</div>
        <input class="field" id="pickupTicket" type="text" size="70" name="pickupTicket" value="<%=WebUtil.display(_pickup_ticketValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Checkin Ticket For Delivery</div>
        <input class="field" id="checkinTicketForDelivery" type="text" size="70" name="checkinTicketForDelivery" value="<%=WebUtil.display(_checkin_ticket_for_deliveryValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Delivery Request</div>
        <select name="isDeliveryRequest" id="isDeliveryRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_delivery_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_delivery_requestValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Web Request</div>
        <select name="isWebRequest" id="isWebRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_web_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_web_requestValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Recurring Request</div>
        <select name="isRecurringRequest" id="isRecurringRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_recurring_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_recurring_requestValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Receive Ready</div>
        <input class="field" id="isReceiveReady" type="text" size="70" name="isReceiveReady" value="<%=WebUtil.display(_is_receive_readyValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Receive Complete</div>
        <input class="field" id="isReceiveComplete" type="text" size="70" name="isReceiveComplete" value="<%=WebUtil.display(_is_receive_completeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Recur Id</div>
        <input class="field" id="recurId" type="text" size="70" name="recurId" value="<%=WebUtil.display(_recur_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Cancelled</div>
        <select name="cancelled" id="cancelled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _cancelledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _cancelledValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Completed</div>
        <select name="completed" id="completed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _completedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _completedValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Customer Name</div>
        <input class="field" id="customerName" type="text" size="70" name="customerName" value="<%=WebUtil.display(_customer_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Address</div>
        <input class="field" id="address" type="text" size="70" name="address" value="<%=WebUtil.display(_addressValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Apt Number</div>
        <input class="field" id="aptNumber" type="text" size="70" name="aptNumber" value="<%=WebUtil.display(_apt_numberValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Phone</div>
        <input class="field" id="phone" type="text" size="70" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Email</div>
        <input class="field" id="email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ack Receive Method</div>
        <select class="field" name="ackReceiveMethod" id="ackReceiveMethod">
        <option value="" >- Please Select -</option>
<%
	dropMenusAckReceiveMethod = DropMenuUtil.getDropMenus("CleanerCustomerNotificationPreference");
	for(Iterator iterItems = dropMenusAckReceiveMethod.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _ack_receive_methodValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Customer Instruction</div>
		<TEXTAREA id="customerInstruction" class="field" NAME="customerInstruction" COLS="50" ROWS="8" ><%=WebUtil.display(_customer_instructionValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pickup Delivery By Day</div>
        <select class="field" name="pickupDeliveryByDay" id="pickupDeliveryByDay">
        <option value="" >- Please Select -</option>
<%
	dropMenusPickupDeliveryByDay = DropMenuUtil.getDropMenus("CleanerPickupDeliveryTargetDay");
	for(Iterator iterItems = dropMenusPickupDeliveryByDay.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pickup_delivery_by_dayValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pickup Delivery By Time</div>
        <select class="field" name="pickupDeliveryByTime" id="pickupDeliveryByTime">
        <option value="" >- Please Select -</option>
<%
	dropMenusPickupDeliveryByTime = DropMenuUtil.getDropMenus("CleanerPickupDeliveryTargetTime");
	for(Iterator iterItems = dropMenusPickupDeliveryByTime.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pickup_delivery_by_timeValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Acked By User Id</div>
        <input class="field" id="ackedByUserId" type="text" size="70" name="ackedByUserId" value="<%=WebUtil.display(_acked_by_user_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Note</div>
		<TEXTAREA id="note" class="field" NAME="note" COLS="50" ROWS="8" ><%=WebUtil.display(_noteValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pickup Note</div>
		<TEXTAREA id="pickupNote" class="field" NAME="pickupNote" COLS="50" ROWS="8" ><%=WebUtil.display(_pickup_noteValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Delivery Note</div>
		<TEXTAREA id="deliveryNote" class="field" NAME="deliveryNote" COLS="50" ROWS="8" ><%=WebUtil.display(_delivery_noteValue)%></TEXTAREA><span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerPickupDeliveryFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerPickupDelivery"></span>
<a href="javascript:backToXX('cleanerPickupDeliveryFormAddDis','resultDisplayCleanerPickupDelivery')">show back</a><br>
<hr/>
