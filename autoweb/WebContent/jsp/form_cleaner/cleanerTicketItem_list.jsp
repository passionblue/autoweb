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
	CleanerTicketItemDS ds = CleanerTicketItemDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_ticket_item_form.html?prv_returnPage=cleaner_ticket_item_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_ticket_item_home.html">home</a> | <a href="/v_cleaner_ticket_item_home.html">home</a> | <a href="/v_cleaner_ticket_item_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerTicketItem = new ArrayList();
	CleanerTicketItemDS ds_CleanerTicketItem = CleanerTicketItemDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerTicketItem = ds_CleanerTicketItem.getAll();
	else		
    	list_CleanerTicketItem = ds_CleanerTicketItem.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerTicketItem, numDisplayInPage, listPage);

	list_CleanerTicketItem = PagingUtil.getPagedList(pagingInfo, list_CleanerTicketItem);
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

    <td class="columnTitle">  Ticket Id </td> 
    <td class="columnTitle">  Parent Ticket Id </td> 
    <td class="columnTitle">  Product Id </td> 
    <td class="columnTitle">  Subtotal Amount </td> 
    <td class="columnTitle">  Total Amount </td> 
    <td class="columnTitle">  Discount Id </td> 
    <td class="columnTitle">  Total Discount Amount </td> 
    <td class="columnTitle">  Special Discount Amount </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerTicketItem.iterator();iter.hasNext();) {
        CleanerTicketItem o_CleanerTicketItem = (CleanerTicketItem) iter.next();
%>

<TR id="tableRow<%= o_CleanerTicketItem.getId()%>">
    <td> <%= o_CleanerTicketItem.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerTicketItem.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerTicketItem.getTicketId()  %> </td>
	<td> <%= o_CleanerTicketItem.getParentTicketId()  %> </td>
	<td> <%= o_CleanerTicketItem.getProductId()  %> </td>
	<td> <%= o_CleanerTicketItem.getSubtotalAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getTotalAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getDiscountId()  %> </td>
	<td> <%= o_CleanerTicketItem.getTotalDiscountAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getSpecialDiscountAmount()  %> </td>
	<td> <%= o_CleanerTicketItem.getNote()  %> </td>
	<td> <%= o_CleanerTicketItem.getTimeCreated()  %> </td>
	<td> <%= o_CleanerTicketItem.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_ticket_item_form('<%=o_CleanerTicketItem.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerTicketItemAction.html?del=true&id=<%=o_CleanerTicketItem.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerTicketItem.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_ticket_item_form('<%=o_CleanerTicketItem.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_ticket_item_form(target){
		location.href='/v_cleaner_ticket_item_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_ticket_item_form(target){
		javascript:sendFormAjaxSimple('/cleanerTicketItemAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerTicketItemAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

