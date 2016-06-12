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
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

	// This is ugrly not matured change. Just added to load previously entered values and put back to the fields. 
	boolean isEdit = false;	
    Map reqParams = (Map) request.getAttribute("k_previous_request_params");
    if ( reqParams == null) {
        reqParams = (Map) request.getAttribute("k_reserved_params");
    } else {
        isEdit = true;
    }

	String command = request.getParameter("cmd");

    String idStr  = "0";
    CleanerPickupDelivery _CleanerPickupDelivery = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerPickupDelivery = CleanerPickupDeliveryDS.getInstance().getById(id);
		if ( _CleanerPickupDelivery == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerPickupDelivery = new CleanerPickupDelivery();// CleanerPickupDeliveryDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerPickupDelivery == null) _CleanerPickupDelivery = new CleanerPickupDelivery();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_pickup_delivery_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _location_idValue= (reqParams.get("locationId")==null?WebUtil.display(_CleanerPickupDelivery.getLocationId()):WebUtil.display((String)reqParams.get("locationId")));
    String _customer_idValue= (reqParams.get("customerId")==null?WebUtil.display(_CleanerPickupDelivery.getCustomerId()):WebUtil.display((String)reqParams.get("customerId")));
    String _ticket_idValue= (reqParams.get("ticketId")==null?WebUtil.display(_CleanerPickupDelivery.getTicketId()):WebUtil.display((String)reqParams.get("ticketId")));
    String _ticket_uidValue= (reqParams.get("ticketUid")==null?WebUtil.display(_CleanerPickupDelivery.getTicketUid()):WebUtil.display((String)reqParams.get("ticketUid")));
    String _pickup_ticketValue= (reqParams.get("pickupTicket")==null?WebUtil.display(_CleanerPickupDelivery.getPickupTicket()):WebUtil.display((String)reqParams.get("pickupTicket")));
    String _checkin_ticket_for_deliveryValue= (reqParams.get("checkinTicketForDelivery")==null?WebUtil.display(_CleanerPickupDelivery.getCheckinTicketForDelivery()):WebUtil.display((String)reqParams.get("checkinTicketForDelivery")));
    String _is_delivery_requestValue= (reqParams.get("isDeliveryRequest")==null?WebUtil.display(_CleanerPickupDelivery.getIsDeliveryRequest()):WebUtil.display((String)reqParams.get("isDeliveryRequest")));
    String _is_web_requestValue= (reqParams.get("isWebRequest")==null?WebUtil.display(_CleanerPickupDelivery.getIsWebRequest()):WebUtil.display((String)reqParams.get("isWebRequest")));
    String _is_recurring_requestValue= (reqParams.get("isRecurringRequest")==null?WebUtil.display(_CleanerPickupDelivery.getIsRecurringRequest()):WebUtil.display((String)reqParams.get("isRecurringRequest")));
    String _is_receive_readyValue= (reqParams.get("isReceiveReady")==null?WebUtil.display(_CleanerPickupDelivery.getIsReceiveReady()):WebUtil.display((String)reqParams.get("isReceiveReady")));
    String _is_receive_completeValue= (reqParams.get("isReceiveComplete")==null?WebUtil.display(_CleanerPickupDelivery.getIsReceiveComplete()):WebUtil.display((String)reqParams.get("isReceiveComplete")));
    String _recur_idValue= (reqParams.get("recurId")==null?WebUtil.display(_CleanerPickupDelivery.getRecurId()):WebUtil.display((String)reqParams.get("recurId")));
    String _cancelledValue= (reqParams.get("cancelled")==null?WebUtil.display(_CleanerPickupDelivery.getCancelled()):WebUtil.display((String)reqParams.get("cancelled")));
    String _completedValue= (reqParams.get("completed")==null?WebUtil.display(_CleanerPickupDelivery.getCompleted()):WebUtil.display((String)reqParams.get("completed")));
    String _customer_nameValue= (reqParams.get("customerName")==null?WebUtil.display(_CleanerPickupDelivery.getCustomerName()):WebUtil.display((String)reqParams.get("customerName")));
    String _addressValue= (reqParams.get("address")==null?WebUtil.display(_CleanerPickupDelivery.getAddress()):WebUtil.display((String)reqParams.get("address")));
    String _apt_numberValue= (reqParams.get("aptNumber")==null?WebUtil.display(_CleanerPickupDelivery.getAptNumber()):WebUtil.display((String)reqParams.get("aptNumber")));
    String _phoneValue= (reqParams.get("phone")==null?WebUtil.display(_CleanerPickupDelivery.getPhone()):WebUtil.display((String)reqParams.get("phone")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_CleanerPickupDelivery.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _ack_receive_methodValue= (reqParams.get("ackReceiveMethod")==null?WebUtil.display(_CleanerPickupDelivery.getAckReceiveMethod()):WebUtil.display((String)reqParams.get("ackReceiveMethod")));
    String _customer_instructionValue= (reqParams.get("customerInstruction")==null?WebUtil.display(_CleanerPickupDelivery.getCustomerInstruction()):WebUtil.display((String)reqParams.get("customerInstruction")));
    String _pickup_delivery_by_dayValue= (reqParams.get("pickupDeliveryByDay")==null?WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByDay()):WebUtil.display((String)reqParams.get("pickupDeliveryByDay")));
    String _pickup_delivery_by_timeValue= (reqParams.get("pickupDeliveryByTime")==null?WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByTime()):WebUtil.display((String)reqParams.get("pickupDeliveryByTime")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_CleanerPickupDelivery.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_CleanerPickupDelivery.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _time_ackedValue= (reqParams.get("timeAcked")==null?WebUtil.display(_CleanerPickupDelivery.getTimeAcked()):WebUtil.display((String)reqParams.get("timeAcked")));
    String _acked_by_user_idValue= (reqParams.get("ackedByUserId")==null?WebUtil.display(_CleanerPickupDelivery.getAckedByUserId()):WebUtil.display((String)reqParams.get("ackedByUserId")));
    String _time_readyValue= (reqParams.get("timeReady")==null?WebUtil.display(_CleanerPickupDelivery.getTimeReady()):WebUtil.display((String)reqParams.get("timeReady")));
    String _time_notifiedValue= (reqParams.get("timeNotified")==null?WebUtil.display(_CleanerPickupDelivery.getTimeNotified()):WebUtil.display((String)reqParams.get("timeNotified")));
    String _time_completedValue= (reqParams.get("timeCompleted")==null?WebUtil.display(_CleanerPickupDelivery.getTimeCompleted()):WebUtil.display((String)reqParams.get("timeCompleted")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_CleanerPickupDelivery.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _pickup_noteValue= (reqParams.get("pickupNote")==null?WebUtil.display(_CleanerPickupDelivery.getPickupNote()):WebUtil.display((String)reqParams.get("pickupNote")));
    String _delivery_noteValue= (reqParams.get("deliveryNote")==null?WebUtil.display(_CleanerPickupDelivery.getDeliveryNote()):WebUtil.display((String)reqParams.get("deliveryNote")));

    String pagestamp = "cleaner_pickup_delivery_" + System.nanoTime();
%> 

<br>
<div id="cleanerPickupDeliveryForm" class="formFrame">
<div id="cleanerPickupDeliveryFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerPickupDeliveryForm_Form" method="POST" action="/cleanerPickupDeliveryAction.html" id="cleanerPickupDeliveryForm_Form">





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


<div class="submitFrame">

    <div id="cleanerPickupDeliveryForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerPickupDeliveryForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerPickupDeliveryForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerPickupDeliveryForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerPickupDeliveryForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerPickupDeliveryForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerPickupDeliveryForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">
<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_cleaner_pickup_delivery_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerPickupDelivery = new ArrayList();
	CleanerPickupDeliveryDS ds_CleanerPickupDelivery = CleanerPickupDeliveryDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerPickupDelivery = ds_CleanerPickupDelivery.getAll();
	else		
    	list_CleanerPickupDelivery = ds_CleanerPickupDelivery.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerPickupDelivery, numDisplayInPage, listPage);

	list_CleanerPickupDelivery = PagingUtil.getPagedList(pagingInfo, list_CleanerPickupDelivery);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Location Id </td> 
    <td class="columnTitle">  Customer Id </td> 
    <td class="columnTitle">  Ticket Id </td> 
    <td class="columnTitle">  Ticket Uid </td> 
    <td class="columnTitle">  Pickup Ticket </td> 
    <td class="columnTitle">  Checkin Ticket For Delivery </td> 
    <td class="columnTitle">  Is Delivery Request </td> 
    <td class="columnTitle">  Is Web Request </td> 
    <td class="columnTitle">  Is Recurring Request </td> 
    <td class="columnTitle">  Is Receive Ready </td> 
    <td class="columnTitle">  Is Receive Complete </td> 
    <td class="columnTitle">  Recur Id </td> 
    <td class="columnTitle">  Cancelled </td> 
    <td class="columnTitle">  Completed </td> 
    <td class="columnTitle">  Customer Name </td> 
    <td class="columnTitle">  Address </td> 
    <td class="columnTitle">  Apt Number </td> 
    <td class="columnTitle">  Phone </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Ack Receive Method </td> 
    <td class="columnTitle">  Customer Instruction </td> 
    <td class="columnTitle">  Pickup Delivery By Day </td> 
    <td class="columnTitle">  Pickup Delivery By Time </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Time Acked </td> 
    <td class="columnTitle">  Acked By User Id </td> 
    <td class="columnTitle">  Time Ready </td> 
    <td class="columnTitle">  Time Notified </td> 
    <td class="columnTitle">  Time Completed </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Pickup Note </td> 
    <td class="columnTitle">  Delivery Note </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerPickupDelivery.iterator();iter.hasNext();) {
        CleanerPickupDelivery o_CleanerPickupDelivery = (CleanerPickupDelivery) iter.next();
%>

<TR id="tableRow<%= o_CleanerPickupDelivery.getId()%>">
    <td> <%= o_CleanerPickupDelivery.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerPickupDelivery.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerPickupDelivery.getLocationId()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getCustomerId()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTicketId()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTicketUid()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getPickupTicket()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getCheckinTicketForDelivery()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getIsDeliveryRequest()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getIsWebRequest()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getIsRecurringRequest()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getIsReceiveReady()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getIsReceiveComplete()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getRecurId()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getCancelled()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getCompleted()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getCustomerName()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getAddress()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getAptNumber()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getPhone()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getEmail()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getAckReceiveMethod()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getCustomerInstruction()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getPickupDeliveryByDay()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getPickupDeliveryByTime()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTimeCreated()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTimeUpdated()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTimeAcked()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getAckedByUserId()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTimeReady()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTimeNotified()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getTimeCompleted()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getNote()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getPickupNote()  %> </td>
	<td> <%= o_CleanerPickupDelivery.getDeliveryNote()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_pickup_delivery_form('<%=o_CleanerPickupDelivery.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?del=true&id=<%=o_CleanerPickupDelivery.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerPickupDelivery.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_pickup_delivery_form('<%=o_CleanerPickupDelivery.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function open_edit_cleaner_pickup_delivery_form(target){
		location.href='/v_cleaner_pickup_delivery_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_pickup_delivery_form(target){
		javascript:sendFormAjaxSimple('/cleanerPickupDeliveryAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

</script>
