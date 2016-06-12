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
	CleanerCustomerDS ds = CleanerCustomerDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_customer_form.html?prv_returnPage=cleaner_customer_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_customer_home.html">home</a> | <a href="/v_cleaner_customer_home.html">home</a> | <a href="/v_cleaner_customer_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerCustomer = new ArrayList();
	CleanerCustomerDS ds_CleanerCustomer = CleanerCustomerDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerCustomer = ds_CleanerCustomer.getAll();
	else		
    	list_CleanerCustomer = ds_CleanerCustomer.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerCustomer, numDisplayInPage, listPage);

	list_CleanerCustomer = PagingUtil.getPagedList(pagingInfo, list_CleanerCustomer);
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

    <td class="columnTitle">  Autosite User Id </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Phone </td> 
    <td class="columnTitle">  Phone2 </td> 
    <td class="columnTitle">  Phone Preferred </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Address </td> 
    <td class="columnTitle">  Apt </td> 
    <td class="columnTitle">  City </td> 
    <td class="columnTitle">  State </td> 
    <td class="columnTitle">  Zip </td> 
    <td class="columnTitle">  Customer Type </td> 
    <td class="columnTitle">  Pay Type </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Remote Ip </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Pickup Note </td> 
    <td class="columnTitle">  Delivery Note </td> 
    <td class="columnTitle">  Disabled </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Pickup Delivery Disallowed </td> 
    <td class="columnTitle">  Handle Inst </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerCustomer.iterator();iter.hasNext();) {
        CleanerCustomer o_CleanerCustomer = (CleanerCustomer) iter.next();
%>

<TR id="tableRow<%= o_CleanerCustomer.getId()%>">
    <td> <%= o_CleanerCustomer.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerCustomer.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerCustomer.getAutositeUserId()  %> </td>
	<td> <%= o_CleanerCustomer.getEmail()  %> </td>
	<td> <%= o_CleanerCustomer.getPhone()  %> </td>
	<td> <%= o_CleanerCustomer.getPhone2()  %> </td>
	<td> <%= o_CleanerCustomer.getPhonePreferred()  %> </td>
	<td> <%= o_CleanerCustomer.getTitle()  %> </td>
	<td> <%= o_CleanerCustomer.getLastName()  %> </td>
	<td> <%= o_CleanerCustomer.getFirstName()  %> </td>
	<td> <%= o_CleanerCustomer.getAddress()  %> </td>
	<td> <%= o_CleanerCustomer.getApt()  %> </td>
	<td> <%= o_CleanerCustomer.getCity()  %> </td>
	<td> <%= o_CleanerCustomer.getState()  %> </td>
	<td> <%= o_CleanerCustomer.getZip()  %> </td>
	<td> <%= o_CleanerCustomer.getCustomerType()  %> </td>
	<td> <%= o_CleanerCustomer.getPayType()  %> </td>
	<td> <%= o_CleanerCustomer.getTimeCreated()  %> </td>
	<td> <%= o_CleanerCustomer.getRemoteIp()  %> </td>
	<td> <%= o_CleanerCustomer.getNote()  %> </td>
	<td> <%= o_CleanerCustomer.getPickupNote()  %> </td>
	<td> <%= o_CleanerCustomer.getDeliveryNote()  %> </td>
	<td> <%= o_CleanerCustomer.getDisabled()  %> </td>
	<td> <%= o_CleanerCustomer.getTimeUpdated()  %> </td>
	<td> <%= o_CleanerCustomer.getPickupDeliveryDisallowed()  %> </td>
	<td> <%= o_CleanerCustomer.getHandleInst()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_customer_form('<%=o_CleanerCustomer.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerCustomerAction.html?del=true&id=<%=o_CleanerCustomer.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerCustomer.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_customer_form('<%=o_CleanerCustomer.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_customer_form(target){
		location.href='/v_cleaner_customer_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_customer_form(target){
		javascript:sendFormAjaxSimple('/cleanerCustomerAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerCustomerAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

