<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:
20150215: increased heartbeat to long number


Source Generated: Sun Mar 15 13:54:46 EDT 2015
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
	CleanerPickupDeliveryConfigDS ds = CleanerPickupDeliveryConfigDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_pickup_delivery_config_form.html?prv_returnPage=cleaner_pickup_delivery_config_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_pickup_delivery_config_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_config_home.html">home</a> | <a href="/v_cleaner_pickup_delivery_config_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerPickupDeliveryConfig = new ArrayList();
	CleanerPickupDeliveryConfigDS ds_CleanerPickupDeliveryConfig = CleanerPickupDeliveryConfigDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerPickupDeliveryConfig = ds_CleanerPickupDeliveryConfig.getAll();
	else		
    	list_CleanerPickupDeliveryConfig = ds_CleanerPickupDeliveryConfig.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerPickupDeliveryConfig, numDisplayInPage, listPage);

	list_CleanerPickupDeliveryConfig = PagingUtil.getPagedList(pagingInfo, list_CleanerPickupDeliveryConfig);
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
    <td class="columnTitle">  Apply All Locations </td> 
    <td class="columnTitle">  Disable Web Request </td> 
    <td class="columnTitle">  Disallow Anonymous Request </td> 
    <td class="columnTitle">  Require Customer Register </td> 
    <td class="columnTitle">  Require Customer Login </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerPickupDeliveryConfig.iterator();iter.hasNext();) {
        CleanerPickupDeliveryConfig o_CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) iter.next();
%>

<TR id="tableRow<%= o_CleanerPickupDeliveryConfig.getId()%>">
    <td> <%= o_CleanerPickupDeliveryConfig.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerPickupDeliveryConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerPickupDeliveryConfig.getLocationId()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getApplyAllLocations()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getDisableWebRequest()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getDisallowAnonymousRequest()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getRequireCustomerRegister()  %> </td>
	<td> <%= o_CleanerPickupDeliveryConfig.getRequireCustomerLogin()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_pickup_delivery_config_form('<%=o_CleanerPickupDeliveryConfig.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerPickupDeliveryConfigAction.html?del=true&id=<%=o_CleanerPickupDeliveryConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerPickupDeliveryConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_pickup_delivery_config_form('<%=o_CleanerPickupDeliveryConfig.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_pickup_delivery_config_form(target){
		location.href='/v_cleaner_pickup_delivery_config_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_pickup_delivery_config_form(target){
		javascript:sendFormAjaxSimple('/cleanerPickupDeliveryConfigAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerPickupDeliveryConfigAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 100000000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

