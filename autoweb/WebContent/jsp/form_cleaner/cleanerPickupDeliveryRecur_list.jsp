<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
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
	CleanerPickupDeliveryRecurDS ds = CleanerPickupDeliveryRecurDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_pickup_delivery_recur_form.html?prv_returnPage=cleaner_pickup_delivery_recur_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_pickup_delivery_recur_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_recur_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_recur_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerPickupDeliveryRecur = new ArrayList();
	CleanerPickupDeliveryRecurDS ds_CleanerPickupDeliveryRecur = CleanerPickupDeliveryRecurDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerPickupDeliveryRecur = ds_CleanerPickupDeliveryRecur.getAll();
	else		
    	list_CleanerPickupDeliveryRecur = ds_CleanerPickupDeliveryRecur.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerPickupDeliveryRecur, numDisplayInPage, listPage);

	list_CleanerPickupDeliveryRecur = PagingUtil.getPagedList(pagingInfo, list_CleanerPickupDeliveryRecur);
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
    <td class="columnTitle">  Weekday </td> 
    <td class="columnTitle">  Time Hhdd </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerPickupDeliveryRecur.iterator();iter.hasNext();) {
        CleanerPickupDeliveryRecur o_CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) iter.next();
%>

<TR id="tableRow<%= o_CleanerPickupDeliveryRecur.getId()%>">
    <td> <%= o_CleanerPickupDeliveryRecur.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerPickupDeliveryRecur.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerPickupDeliveryRecur.getCustomerId()  %> </td>
	<td> <%= o_CleanerPickupDeliveryRecur.getWeekday()  %> </td>
	<td> <%= o_CleanerPickupDeliveryRecur.getTimeHhdd()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_pickup_delivery_recur_form('<%=o_CleanerPickupDeliveryRecur.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerPickupDeliveryRecurAction.html?del=true&id=<%=o_CleanerPickupDeliveryRecur.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerPickupDeliveryRecur.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_pickup_delivery_recur_form('<%=o_CleanerPickupDeliveryRecur.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_pickup_delivery_recur_form(target){
		location.href='/v_cleaner_pickup_delivery_recur_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_pickup_delivery_recur_form(target){
		javascript:sendFormAjaxSimple('/cleanerPickupDeliveryRecurAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryRecurAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

