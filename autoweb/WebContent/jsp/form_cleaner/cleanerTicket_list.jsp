<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	CleanerTicketDS ds = CleanerTicketDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_ticket_form.html?prv_returnPage=cleaner_ticket_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_ticket_home.html">home</a> | <a href="/v_cleaner_ticket_home.html">home</a> | <a href="/v_cleaner_ticket_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerTicket = new ArrayList();
	CleanerTicketDS ds_CleanerTicket = CleanerTicketDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerTicket = ds_CleanerTicket.getAll();
	else		
    	list_CleanerTicket = ds_CleanerTicket.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerTicket, numDisplayInPage, listPage);

	list_CleanerTicket = PagingUtil.getPagedList(pagingInfo, list_CleanerTicket);
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

    <td class="columnTitle">  Serial </td> 
    <td class="columnTitle">  Parent Ticket Id </td> 
    <td class="columnTitle">  Customer Id </td> 
    <td class="columnTitle">  Enter User Id </td> 
    <td class="columnTitle">  Location Id </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Completed </td> 
    <td class="columnTitle">  Onhold </td> 
    <td class="columnTitle">  Original Ticket Id </td> 
    <td class="columnTitle">  Returned </td> 
    <td class="columnTitle">  Returned Reason Text </td> 
    <td class="columnTitle">  Returned Note </td> 
    <td class="columnTitle">  Total Charge </td> 
    <td class="columnTitle">  Final Charge </td> 
    <td class="columnTitle">  Discount Id </td> 
    <td class="columnTitle">  Discount Amount </td> 
    <td class="columnTitle">  Discount Note </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Time Completed </td> 
    <td class="columnTitle">  Time Onhold </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerTicket.iterator();iter.hasNext();) {
        CleanerTicket o_CleanerTicket = (CleanerTicket) iter.next();
%>

<TR id="tableRow<%= o_CleanerTicket.getId()%>">
    <td> <%= o_CleanerTicket.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerTicket.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerTicket.getSerial()  %> </td>
	<td> <%= o_CleanerTicket.getParentTicketId()  %> </td>
	<td> <%= o_CleanerTicket.getCustomerId()  %> </td>
	<td> <%= o_CleanerTicket.getEnterUserId()  %> </td>
	<td> <%= o_CleanerTicket.getLocationId()  %> </td>
	<td> <%= o_CleanerTicket.getNote()  %> </td>
	<td> <%= o_CleanerTicket.getCompleted()  %> </td>
	<td> <%= o_CleanerTicket.getOnhold()  %> </td>
	<td> <%= o_CleanerTicket.getOriginalTicketId()  %> </td>
	<td> <%= o_CleanerTicket.getReturned()  %> </td>
	<td> <%= o_CleanerTicket.getReturnedReasonText()  %> </td>
	<td> <%= o_CleanerTicket.getReturnedNote()  %> </td>
	<td> <%= o_CleanerTicket.getTotalCharge()  %> </td>
	<td> <%= o_CleanerTicket.getFinalCharge()  %> </td>
	<td> <%= o_CleanerTicket.getDiscountId()  %> </td>
	<td> <%= o_CleanerTicket.getDiscountAmount()  %> </td>
	<td> <%= o_CleanerTicket.getDiscountNote()  %> </td>
	<td> <%= o_CleanerTicket.getTimeCreated()  %> </td>
	<td> <%= o_CleanerTicket.getTimeUpdated()  %> </td>
	<td> <%= o_CleanerTicket.getTimeCompleted()  %> </td>
	<td> <%= o_CleanerTicket.getTimeOnhold()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_ticket_form('<%=o_CleanerTicket.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerTicketAction.html?del=true&id=<%=o_CleanerTicket.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerTicket.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_ticket_form('<%=o_CleanerTicket.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_ticket_form(target){
		location.href='/v_cleaner_ticket_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_ticket_form(target){
		javascript:sendFormAjaxSimple('/cleanerTicketAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerTicketAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

