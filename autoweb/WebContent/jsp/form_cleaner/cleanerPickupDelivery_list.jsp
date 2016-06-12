<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:
20150215: increased heartbeat to long number


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


<a href="t_cleaner_pickup_delivery_form.html?prv_returnPage=cleaner_pickup_delivery_home"> Add New 2</a> <br>
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
		   		url: "/cleanerPickupDeliveryAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 100000000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

