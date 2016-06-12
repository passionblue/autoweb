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
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	CleanerPickupDeliveryDS ds = CleanerPickupDeliveryDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 

<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->

<h3> form displayed by script (request type getscriptform or getmodalform2 </h3>
<script type="text/javascript" src="/cleanerPickupDeliveryAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_cleaner_pickup_delivery_form.html?prv_returnPage=cleaner_pickup_delivery_home"> Add New </a> |
            <a href="v_cleaner_pickup_delivery_list.html?"> List Page </a> |
            <a href="v_cleaner_pickup_delivery_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/cleanerPickupDeliveryAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/cleanerPickupDeliveryAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/cleanerPickupDeliveryAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/cleanerPickupDeliveryAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/cleanerPickupDeliveryAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/cleanerPickupDeliveryAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/cleanerPickupDeliveryAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |

        	<a href="javascript:submit_cmd_confirmToRequest();">run cmd confirmToRequest</a>|
        	<a href="javascript:submit_cmd_cancelRequest();">run cmd cancelRequest</a>|
        	<a href="javascript:submit_cmd_setReadyToRequest();">run cmd setReadyToRequest</a>|
        	<a href="javascript:submit_cmd_setCompletedToRequest();">run cmd setCompletedToRequest</a>|
        	<a href="javascript:submit_cmd_sendCustomNotification();">run cmd sendCustomNotification</a>|
		</TD>        
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="cleanerPickupDeliveryForm_locationId_label" style="font-size: normal normal bold 10px verdana;" >Location Id </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_customerId_label" style="font-size: normal normal bold 10px verdana;" >Customer Id </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_ticketId_label" style="font-size: normal normal bold 10px verdana;" >Ticket Id </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_ticketUid_label" style="font-size: normal normal bold 10px verdana;" >Ticket Uid </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_pickupTicket_label" style="font-size: normal normal bold 10px verdana;" >Pickup Ticket </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_label" style="font-size: normal normal bold 10px verdana;" >Checkin Ticket For Delivery </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_isDeliveryRequest_label" style="font-size: normal normal bold 10px verdana;" >Is Delivery Request </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_isWebRequest_label" style="font-size: normal normal bold 10px verdana;" >Is Web Request </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_isRecurringRequest_label" style="font-size: normal normal bold 10px verdana;" >Is Recurring Request </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_isReceiveReady_label" style="font-size: normal normal bold 10px verdana;" >Is Receive Ready </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_isReceiveComplete_label" style="font-size: normal normal bold 10px verdana;" >Is Receive Complete </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_recurId_label" style="font-size: normal normal bold 10px verdana;" >Recur Id </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_cancelled_label" style="font-size: normal normal bold 10px verdana;" >Cancelled </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_completed_label" style="font-size: normal normal bold 10px verdana;" >Completed </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_customerName_label" style="font-size: normal normal bold 10px verdana;" >Customer Name </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_address_label" style="font-size: normal normal bold 10px verdana;" >Address </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_aptNumber_label" style="font-size: normal normal bold 10px verdana;" >Apt Number </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_phone_label" style="font-size: normal normal bold 10px verdana;" >Phone </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_ackReceiveMethod_label" style="font-size: normal normal bold 10px verdana;" >Ack Receive Method </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_customerInstruction_label" style="font-size: normal normal bold 10px verdana;" >Customer Instruction </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_label" style="font-size: normal normal bold 10px verdana;" >Pickup Delivery By Day </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_pickupDeliveryByTime_label" style="font-size: normal normal bold 10px verdana;" >Pickup Delivery By Time </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_ackedByUserId_label" style="font-size: normal normal bold 10px verdana;" >Acked By User Id </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_note_label" style="font-size: normal normal bold 10px verdana;" >Note </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_pickupNote_label" style="font-size: normal normal bold 10px verdana;" >Pickup Note </div>
    </td> 
    <td> 
	    <div id="cleanerPickupDeliveryForm_deliveryNote_label" style="font-size: normal normal bold 10px verdana;" >Delivery Note </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        CleanerPickupDelivery _CleanerPickupDelivery = (CleanerPickupDelivery) iter.next();
		//TODO 
        fieldString += "\"" +  _CleanerPickupDelivery.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _CleanerPickupDelivery.getId() %> </td>


    <td> 
	<form name="cleanerPickupDeliveryFormEditField_LocationId_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_locationId_field">
	    <div id="cleanerPickupDeliveryForm_locationId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="locationId" value="<%=WebUtil.display(_CleanerPickupDelivery.getLocationId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="locationId_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getLocationId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_LocationId_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'locationId', '<%=_CleanerPickupDelivery.getLocationId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="locationId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="locationId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="locationId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_CustomerId_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_customerId_field">
	    <div id="cleanerPickupDeliveryForm_customerId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="customerId" value="<%=WebUtil.display(_CleanerPickupDelivery.getCustomerId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="customerId_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getCustomerId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_CustomerId_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'customerId', '<%=_CleanerPickupDelivery.getCustomerId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="customerId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="customerId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="customerId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_TicketId_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_ticketId_field">
	    <div id="cleanerPickupDeliveryForm_ticketId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ticketId" value="<%=WebUtil.display(_CleanerPickupDelivery.getTicketId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="ticketId_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getTicketId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_TicketId_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'ticketId', '<%=_CleanerPickupDelivery.getTicketId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="ticketId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="ticketId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="ticketId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_TicketUid_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_ticketUid_field">
	    <div id="cleanerPickupDeliveryForm_ticketUid_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ticketUid" value="<%=WebUtil.display(_CleanerPickupDelivery.getTicketUid())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="ticketUid_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getTicketUid() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_TicketUid_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'ticketUid', '<%=_CleanerPickupDelivery.getTicketUid()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="ticketUid">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="ticketUid">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="ticketUid">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_PickupTicket_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_pickupTicket_field">
	    <div id="cleanerPickupDeliveryForm_pickupTicket_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pickupTicket" value="<%=WebUtil.display(_CleanerPickupDelivery.getPickupTicket())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="pickupTicket_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getPickupTicket() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_PickupTicket_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'pickupTicket', '<%=_CleanerPickupDelivery.getPickupTicket()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="pickupTicket">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="pickupTicket">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="pickupTicket">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_CheckinTicketForDelivery_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_field">
	    <div id="cleanerPickupDeliveryForm_checkinTicketForDelivery_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="checkinTicketForDelivery" value="<%=WebUtil.display(_CleanerPickupDelivery.getCheckinTicketForDelivery())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="checkinTicketForDelivery_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getCheckinTicketForDelivery() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_CheckinTicketForDelivery_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'checkinTicketForDelivery', '<%=_CleanerPickupDelivery.getCheckinTicketForDelivery()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="checkinTicketForDelivery">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="checkinTicketForDelivery">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="checkinTicketForDelivery">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_IsDeliveryRequest_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_isDeliveryRequest_field">
	    <div id="cleanerPickupDeliveryForm_isDeliveryRequest_dropdown" class="formFieldDropDown" >       
	        <select name="isDeliveryRequest">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _CleanerPickupDelivery.getIsDeliveryRequest())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _CleanerPickupDelivery.getIsDeliveryRequest())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isDeliveryRequest_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getIsDeliveryRequest() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_IsDeliveryRequest_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'isDeliveryRequest', '<%=_CleanerPickupDelivery.getIsDeliveryRequest()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isDeliveryRequest">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isDeliveryRequest">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isDeliveryRequest">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_IsWebRequest_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_isWebRequest_field">
	    <div id="cleanerPickupDeliveryForm_isWebRequest_dropdown" class="formFieldDropDown" >       
	        <select name="isWebRequest">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _CleanerPickupDelivery.getIsWebRequest())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _CleanerPickupDelivery.getIsWebRequest())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isWebRequest_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getIsWebRequest() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_IsWebRequest_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'isWebRequest', '<%=_CleanerPickupDelivery.getIsWebRequest()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isWebRequest">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isWebRequest">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isWebRequest">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_IsRecurringRequest_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_isRecurringRequest_field">
	    <div id="cleanerPickupDeliveryForm_isRecurringRequest_dropdown" class="formFieldDropDown" >       
	        <select name="isRecurringRequest">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _CleanerPickupDelivery.getIsRecurringRequest())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _CleanerPickupDelivery.getIsRecurringRequest())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isRecurringRequest_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getIsRecurringRequest() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_IsRecurringRequest_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'isRecurringRequest', '<%=_CleanerPickupDelivery.getIsRecurringRequest()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isRecurringRequest">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isRecurringRequest">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isRecurringRequest">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_IsReceiveReady_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_isReceiveReady_field">
	    <div id="cleanerPickupDeliveryForm_isReceiveReady_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isReceiveReady" value="<%=WebUtil.display(_CleanerPickupDelivery.getIsReceiveReady())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isReceiveReady_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getIsReceiveReady() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_IsReceiveReady_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'isReceiveReady', '<%=_CleanerPickupDelivery.getIsReceiveReady()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isReceiveReady">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isReceiveReady">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isReceiveReady">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_IsReceiveComplete_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_isReceiveComplete_field">
	    <div id="cleanerPickupDeliveryForm_isReceiveComplete_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="isReceiveComplete" value="<%=WebUtil.display(_CleanerPickupDelivery.getIsReceiveComplete())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="isReceiveComplete_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getIsReceiveComplete() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_IsReceiveComplete_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'isReceiveComplete', '<%=_CleanerPickupDelivery.getIsReceiveComplete()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="isReceiveComplete">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="isReceiveComplete">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="isReceiveComplete">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_RecurId_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_recurId_field">
	    <div id="cleanerPickupDeliveryForm_recurId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="recurId" value="<%=WebUtil.display(_CleanerPickupDelivery.getRecurId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="recurId_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getRecurId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_RecurId_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'recurId', '<%=_CleanerPickupDelivery.getRecurId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="recurId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="recurId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="recurId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_Cancelled_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_cancelled_field">
	    <div id="cleanerPickupDeliveryForm_cancelled_dropdown" class="formFieldDropDown" >       
	        <select name="cancelled">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _CleanerPickupDelivery.getCancelled())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _CleanerPickupDelivery.getCancelled())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="cancelled_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getCancelled() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_Cancelled_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'cancelled', '<%=_CleanerPickupDelivery.getCancelled()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="cancelled">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="cancelled">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="cancelled">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_Completed_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_completed_field">
	    <div id="cleanerPickupDeliveryForm_completed_dropdown" class="formFieldDropDown" >       
	        <select name="completed">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _CleanerPickupDelivery.getCompleted())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _CleanerPickupDelivery.getCompleted())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="completed_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getCompleted() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_Completed_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'completed', '<%=_CleanerPickupDelivery.getCompleted()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="completed">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="completed">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="completed">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_CustomerName_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_customerName_field">
	    <div id="cleanerPickupDeliveryForm_customerName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="customerName" value="<%=WebUtil.display(_CleanerPickupDelivery.getCustomerName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="customerName_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getCustomerName() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_CustomerName_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'customerName', '<%=_CleanerPickupDelivery.getCustomerName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="customerName">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="customerName">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="customerName">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_Address_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_address_field">
	    <div id="cleanerPickupDeliveryForm_address_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="address" value="<%=WebUtil.display(_CleanerPickupDelivery.getAddress())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="address_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getAddress() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_Address_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'address', '<%=_CleanerPickupDelivery.getAddress()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="address">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="address">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="address">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_AptNumber_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_aptNumber_field">
	    <div id="cleanerPickupDeliveryForm_aptNumber_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="aptNumber" value="<%=WebUtil.display(_CleanerPickupDelivery.getAptNumber())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="aptNumber_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getAptNumber() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_AptNumber_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'aptNumber', '<%=_CleanerPickupDelivery.getAptNumber()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="aptNumber">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="aptNumber">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="aptNumber">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_Phone_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_phone_field">
	    <div id="cleanerPickupDeliveryForm_phone_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="phone" value="<%=WebUtil.display(_CleanerPickupDelivery.getPhone())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="phone_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getPhone() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_Phone_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'phone', '<%=_CleanerPickupDelivery.getPhone()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="phone">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="phone">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="phone">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_Email_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_email_field">
	    <div id="cleanerPickupDeliveryForm_email_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="email" value="<%=WebUtil.display(_CleanerPickupDelivery.getEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="email_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getEmail() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_Email_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'email', '<%=_CleanerPickupDelivery.getEmail()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="email">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="email">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="email">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_AckReceiveMethod_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_ackReceiveMethod_field">
	    <div id="cleanerPickupDeliveryForm_ackReceiveMethod_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ackReceiveMethod" value="<%=WebUtil.display(_CleanerPickupDelivery.getAckReceiveMethod())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="ackReceiveMethod_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getAckReceiveMethod() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_AckReceiveMethod_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'ackReceiveMethod', '<%=_CleanerPickupDelivery.getAckReceiveMethod()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="ackReceiveMethod">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="ackReceiveMethod">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="ackReceiveMethod">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_CustomerInstruction_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_customerInstruction_field">
	    <div id="cleanerPickupDeliveryForm_customerInstruction_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="customerInstruction" value="<%=WebUtil.display(_CleanerPickupDelivery.getCustomerInstruction())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="customerInstruction_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getCustomerInstruction() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_CustomerInstruction_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'customerInstruction', '<%=_CleanerPickupDelivery.getCustomerInstruction()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="customerInstruction">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="customerInstruction">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="customerInstruction">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_PickupDeliveryByDay_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_field">
	    <div id="cleanerPickupDeliveryForm_pickupDeliveryByDay_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pickupDeliveryByDay" value="<%=WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByDay())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="pickupDeliveryByDay_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getPickupDeliveryByDay() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_PickupDeliveryByDay_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'pickupDeliveryByDay', '<%=_CleanerPickupDelivery.getPickupDeliveryByDay()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="pickupDeliveryByDay">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="pickupDeliveryByDay">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="pickupDeliveryByDay">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_PickupDeliveryByTime_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_pickupDeliveryByTime_field">
	    <div id="cleanerPickupDeliveryForm_pickupDeliveryByTime_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pickupDeliveryByTime" value="<%=WebUtil.display(_CleanerPickupDelivery.getPickupDeliveryByTime())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="pickupDeliveryByTime_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getPickupDeliveryByTime() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_PickupDeliveryByTime_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'pickupDeliveryByTime', '<%=_CleanerPickupDelivery.getPickupDeliveryByTime()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="pickupDeliveryByTime">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="pickupDeliveryByTime">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="pickupDeliveryByTime">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>




    <td> 
	<form name="cleanerPickupDeliveryFormEditField_AckedByUserId_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_ackedByUserId_field">
	    <div id="cleanerPickupDeliveryForm_ackedByUserId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="ackedByUserId" value="<%=WebUtil.display(_CleanerPickupDelivery.getAckedByUserId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="ackedByUserId_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getAckedByUserId() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_AckedByUserId_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'ackedByUserId', '<%=_CleanerPickupDelivery.getAckedByUserId()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="ackedByUserId">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="ackedByUserId">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="ackedByUserId">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>




    <td> 
	<form name="cleanerPickupDeliveryFormEditField_Note_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_note_field">
	    <div id="cleanerPickupDeliveryForm_note_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="note" value="<%=WebUtil.display(_CleanerPickupDelivery.getNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="note_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_Note_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'note', '<%=_CleanerPickupDelivery.getNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="note">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="note">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_PickupNote_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_pickupNote_field">
	    <div id="cleanerPickupDeliveryForm_pickupNote_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pickupNote" value="<%=WebUtil.display(_CleanerPickupDelivery.getPickupNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="pickupNote_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getPickupNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_PickupNote_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'pickupNote', '<%=_CleanerPickupDelivery.getPickupNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="pickupNote">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="pickupNote">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="pickupNote">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td> 
	<form name="cleanerPickupDeliveryFormEditField_DeliveryNote_<%=_CleanerPickupDelivery.getId()%>" method="get" action="/cleanerPickupDeliveryAction.html" >


		<div id="cleanerPickupDeliveryForm_deliveryNote_field">
	    <div id="cleanerPickupDeliveryForm_deliveryNote_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="deliveryNote" value="<%=WebUtil.display(_CleanerPickupDelivery.getDeliveryNote())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="deliveryNote_<%= _CleanerPickupDelivery.getId()%>"><%=_CleanerPickupDelivery.getDeliveryNote() %></div>
            <a id="formSubmit" href="javascript:document.cleanerPickupDeliveryFormEditField_DeliveryNote_<%=_CleanerPickupDelivery.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_CleanerPickupDelivery.getId()%>', 'deliveryNote', '<%=_CleanerPickupDelivery.getDeliveryNote()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="deliveryNote">	Update </a><br>
			<a id="update_field_by_ajax_open_reply" href="#" rel="deliveryNote">	Update </a><br>
			<a id="update_field_by_ajax_get2field" href="#" rel="deliveryNote">	Update Test With Get2Field value </a><br>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerPickupDelivery.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="cleaner_pickup_delivery_home">
	</form>
    
    
    </td>

    <td>
    <form name="cleanerPickupDeliveryForm<%=_CleanerPickupDelivery.getId()%>2" method="get" action="/v_cleaner_pickup_delivery_form.html" >
        <a href="javascript:document.cleanerPickupDeliveryForm<%=_CleanerPickupDelivery.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _CleanerPickupDelivery.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="cleaner_pickup_delivery_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_cleaner_pickup_delivery_form('<%=_CleanerPickupDelivery.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/cleanerPickupDeliveryAction.html?del=true&id=<%=_CleanerPickupDelivery.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_cleaner_pickup_delivery('<%=_CleanerPickupDelivery.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<a id="partition_test_ajax" href="#" rel="extInt">	Partition Test </a><br>
<a id="partition_test_ajax2" href="#" rel="extInt">	Partition Test2 </a><br>

<div id="partitionTestResult" style="border:1px solid #666666; "> Partition test to be loaded here </div> <br>


<script type="text/javascript">

	function submit_cmd_confirmToRequest(){
		location.href='/cleanerPickupDeliveryAction.html?confirmToRequest=true<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_cmd_cancelRequest(){
		location.href='/cleanerPickupDeliveryAction.html?cancelRequest=true<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_cmd_setReadyToRequest(){
		location.href='/cleanerPickupDeliveryAction.html?setReadyToRequest=true<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_cmd_setCompletedToRequest(){
		location.href='/cleanerPickupDeliveryAction.html?setCompletedToRequest=true<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_cmd_sendCustomNotification(){
		location.href='/cleanerPickupDeliveryAction.html?sendCustomNotification=true<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}



	function edit_cleaner_pickup_delivery_form(target){
		location.href='/v_cleaner_pickup_delivery_form.html?cmd=edit&prv_returnPage=cleaner_pickup_delivery_home&id=' + target;
	}

	function confirm_cleaner_pickup_delivery(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_cleaner_pickup_delivery(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/cleanerPickupDeliveryAction.html?del=true&id="+target;
				}
			}
		});
	}
	// 20120226 
	// On the list, added a little stupid fuction to prompt the change of values
	function update_cleaner_pickup_field_dialog(targetId, targetField, targetValue){
		var txt = 'Change field value for'+targetField +':<br/> <input type="text" id="alertName" name="myname" value="'+ targetValue +'" />';
		$ .prompt(txt,{ 
			buttons:{Submit:true, Cancel:false},
			callback: function(v,m,f){
				if (v){
					if (f.myname == "") {
						alert("Enter");
						return false;
					} else {
						location.href="/cleanerPickupDeliveryAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
					}
				}
				return true;
			}
		});
	}

	// Functions to update field in the list via ajax
	// This is primitive but field "update_field_by_ajax" should be right next level of form.
	// Because it uses parent to access to id and field name 20120226
	$(document).ready(function(){

		$("a#update_field_by_ajax").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		     		$("#" + _fieldName+"_"+_id).text(msg);
		   		}
	 		});
			
			return false;
		});

		$("a#update_field_by_ajax_open_reply").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg);
		    		$ .prompt("Value updated Success fully",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});
		
		$("a#update_field_by_ajax_get2field").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryAction.html?editfield=true&ajxr=get2field&id="+_id+"&"+_fieldName+"="+ _val,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg); // Update the field
		     		var displayMsg = "return from server-> " + msg + "<br>" + "result of getSuccessData()-> "+ getSuccessData(msg);
		    		$ .prompt(displayMsg,{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});


		$("a#partition_test_ajax").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-pickup-delivery",
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").html(msg);
		   		}
	 		});
			
			return false;
		});		

		// Display loader 
		$("a#partition_test_ajax2").click(function () {
			
			$ .ajax({
			   type: "GET",
		   		url: "/partitionTo.html?fmt=getpart&partType=custom&partId=partition-cleaner-pickup-delivery",
		   		beforeSend: function(jqXHR, settings){
		   			
					// 1 just display loader img on the target div 		   			
		   			// $("#partitionTestResult").html("<img src=\"/images/loader/arrows32.gif\"/>");

					
					//2 
					$("#partitionTestResult").css("height","100px").html("<img src=\"/images/loader/arrows32.gif\"/>");
					
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		alert(msg);
		     		$("#partitionTestResult").remove("height").html(msg);
		   		}
	 		});
			return false;
 		});
	});
</script>


<script type="text/javascript" charset="utf-8">
// This Javascript is granted to the public domain.

// This is the javascript array holding the function list
// The PrintJavascriptArray ASP function can be used to print this array.
//var functionlist = Array("abs",
//"acos","acosh","addcslashes","addslashes","aggregate","stream_context_create",
//"swf_startbutton","swfmovie.remove","swfmovie.save","swftext.getwidth","swftext.moveto","sybase_fetch_field","sybase_fetch_object","tanh","tempnam",
//"textdomain","time","udm_errno","udm_error",
//"unset","urldecode","urlencode","user_error","usleep","usort","utf8_decode",
//"utf8_encode","var_dump","vpopmail_error","vpopmail_passwd","vpopmail_set_user_quota","vprintf","vsprintf","xml_parser_create","xml_parser_create_ns",
//"xml_parser_free","xmlrpc_server_add_introspection_data","xmlrpc_server_call_method","xmlrpc_server_create","xmlrpc_server_destroy","xmlrpc_server_register_introspection_callback","yaz_connect","yaz_database","yaz_element",
//"yaz_errno","yp_order","zend_logo_guid","zend_version","zip_close","zip_open","zip_read");



var functionlist = Array(<%=fieldString%>);



// This is the function that refreshes the list after a keypress.
// The maximum number to show can be limited to improve performance with
// huge lists (1000s of entries).
// The function clears the list, and then does a linear search through the
// globally defined array and adds the matches back to the list.
function handleKeyUp(maxNumToShow)
{
    var selectObj, textObj, functionListLength;
    var i, searchPattern, numShown;

	if (document.getElementById('auto-complete-input') == null){
		alert("Client side Error occurred. Please try again.");
		return;
	}
    
    // Set references to the form elements
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    // Remember the function list length for loop speedup
    functionListLength = functionlist.length;

    // Set the search pattern depending
    if(document.getElementById('auto-complete-input').functionradio[0].checked == true)
    {
        searchPattern = "^"+textObj.value;
    }
    else
    {
        searchPattern = textObj.value;
    }

    // Create a regulare expression
    re = new RegExp(searchPattern,"gi");
    // Clear the options list
    selectObj.length = 0;

    // Loop through the array and re-add matching options
    numShown = 0;
    for(i = 0; i < functionListLength; i++)
    {
        if(functionlist[i].search(re) != -1)
        {
            selectObj[numShown] = new Option(functionlist[i],"");
            numShown++;
        }
        // Stop when the number to show is reached
        if(numShown == maxNumToShow)
        {
            break;
        }
    }
    // When options list whittled to one, select that entry
    if(selectObj.length == 1)
    {
        selectObj.options[0].selected = true;
    }
}

// this function gets the selected value and loads the appropriate
// php reference page in the display frame
// it can be modified to perform whatever action is needed, or nothing
function handleSelectClick()
{
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    selectedValue = selectObj.options[selectObj.selectedIndex].text;

    selectedValue = selectedValue.replace(/_/g, '-') ;
    document.location.href = 
	"http://www.php.net/manual/en/function."+selectedValue+".php";

}
function encode_utf8( string )
{
	string = string.replace(/\r\n/g,"\n");
	var utftext = "";

	for (var n = 0; n < string.length; n++) {

		var c = string.charCodeAt(n);

		if (c < 128) {
			utftext += String.fromCharCode(c);
		}
		else if((c > 127) && (c < 2048)) {
			utftext += String.fromCharCode((c >> 6) | 192);
			utftext += String.fromCharCode((c & 63) | 128);
		}
		else {
			utftext += String.fromCharCode((c >> 12) | 224);
			utftext += String.fromCharCode(((c >> 6) & 63) | 128);
			utftext += String.fromCharCode((c & 63) | 128);
		}

	}

	return utftext;
}

function decode_utf8( s )
{
  return decodeURIComponent( escape( s ) );
}
</script>

<table style="margin:auto;">
<tr>
	<td valign="top">
		<b>Search For Function Name</b>
		
		<form onSubmit="handleSelectClick();return false;" action="#" id='auto-complete-input'>

			<input type="radio" name="functionradio" checked>Starting With<br>
			<input type="radio" name="functionradio">Containing<br>
			<input  onKeyUp="handleKeyUp(20);" type="text" name="functioninput" VALUE="" style="font-size:10pt;width:34ex;"><br>
		
			<select onClick="handleSelectClick();" name="functionselect" size="20" style="font-size:10pt;width:34ex;">
			</select>
			<br>
			<input type="button" onClick="handleKeyUp(9999999);" value="Load All Matches">
		</form>
	</td>
</tr>

<tr>
	<td valign="top">
		<select>
		  <option>Volvo</option>
		  <option>Saab</option>
		  <option>Mercedes</option>
		  <option>Audi</option>
		</select>
	</td>
</tr>

</table>