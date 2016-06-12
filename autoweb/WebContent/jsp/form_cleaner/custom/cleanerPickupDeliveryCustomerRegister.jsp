<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	// Will let the customer to register self to this site


	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

    Map reqParams = (Map) request.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    CleanerPickupDelivery _CleanerPickupDelivery = null;
	
	boolean isEdit = false;	
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
    String _pickup_ticketValue= (reqParams.get("pickupTicket")==null?WebUtil.display(_CleanerPickupDelivery.getPickupTicket()):WebUtil.display((String)reqParams.get("pickupTicket")));
    String _is_delivery_requestValue= (reqParams.get("isDeliveryRequest")==null?WebUtil.display(_CleanerPickupDelivery.getIsDeliveryRequest()):WebUtil.display((String)reqParams.get("isDeliveryRequest")));
    String _is_web_requestValue= (reqParams.get("isWebRequest")==null?WebUtil.display(_CleanerPickupDelivery.getIsWebRequest()):WebUtil.display((String)reqParams.get("isWebRequest")));
    String _is_recurring_requestValue= (reqParams.get("isRecurringRequest")==null?WebUtil.display(_CleanerPickupDelivery.getIsRecurringRequest()):WebUtil.display((String)reqParams.get("isRecurringRequest")));
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

<form name="cleanerPickupDeliveryForm_Form" method="POST" action="/forwardTo.html" id="cleanerPickupDeliveryForm_Form">

<div class="submitFrame">

    <div id="cleanerPickupDeliveryForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_delivery_request();">Submit Delivery Request</a>
    </div>      

</div>


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

	function submit_delivery_request(){
		location.href='/fowardTo.html?cmd=tosubmitform<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

</script>
