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
	CleanerCustomerNotificationDS ds = CleanerCustomerNotificationDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_customer_notification_form.html?prv_returnPage=cleaner_customer_notification_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_customer_notification_home.html">home</a> | <a href="/v_cleaner_customer_notification_home.html">home</a> | <a href="/v_cleaner_customer_notification_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerCustomerNotification = new ArrayList();
	CleanerCustomerNotificationDS ds_CleanerCustomerNotification = CleanerCustomerNotificationDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerCustomerNotification = ds_CleanerCustomerNotification.getAll();
	else		
    	list_CleanerCustomerNotification = ds_CleanerCustomerNotification.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerCustomerNotification, numDisplayInPage, listPage);

	list_CleanerCustomerNotification = PagingUtil.getPagedList(pagingInfo, list_CleanerCustomerNotification);
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

    <td class="columnTitle">  Customer Id </td> 
    <td class="columnTitle">  Reasonfor Id </td> 
    <td class="columnTitle">  Reasonfor Target </td> 
    <td class="columnTitle">  Notification Type </td> 
    <td class="columnTitle">  Source Type </td> 
    <td class="columnTitle">  Trigger Type </td> 
    <td class="columnTitle">  Is Retransmit </td> 
    <td class="columnTitle">  Method Type </td> 
    <td class="columnTitle">  Template Type </td> 
    <td class="columnTitle">  Content </td> 
    <td class="columnTitle">  Destination </td> 
    <td class="columnTitle">  Reference </td> 
    <td class="columnTitle">  Time Scheduled </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Sent </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerCustomerNotification.iterator();iter.hasNext();) {
        CleanerCustomerNotification o_CleanerCustomerNotification = (CleanerCustomerNotification) iter.next();
%>

<TR id="tableRow<%= o_CleanerCustomerNotification.getId()%>">
    <td> <%= o_CleanerCustomerNotification.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerCustomerNotification.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerCustomerNotification.getCustomerId()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getReasonforId()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getReasonforTarget()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getNotificationType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getSourceType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTriggerType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getIsRetransmit()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getMethodType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTemplateType()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getContent()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getDestination()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getReference()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTimeScheduled()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTimeCreated()  %> </td>
	<td> <%= o_CleanerCustomerNotification.getTimeSent()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_customer_notification_form('<%=o_CleanerCustomerNotification.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerCustomerNotificationAction.html?del=true&id=<%=o_CleanerCustomerNotification.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerCustomerNotification.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_customer_notification_form('<%=o_CleanerCustomerNotification.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_customer_notification_form(target){
		location.href='/v_cleaner_customer_notification_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_customer_notification_form(target){
		javascript:sendFormAjaxSimple('/cleanerCustomerNotificationAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerCustomerNotificationAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

