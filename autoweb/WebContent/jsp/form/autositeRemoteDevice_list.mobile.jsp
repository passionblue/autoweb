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
	AutositeRemoteDeviceDS ds = AutositeRemoteDeviceDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_autosite_remote_device_form.html?prv_returnPage=autosite_remote_device_home"> Add New 2</a> <br>
<br/>
<a href="/v_autosite_remote_device_home.html">home</a> | <a href="/v_autosite_remote_device_home.html">home</a> | <a href="/v_autosite_remote_device_home.html">home</a>
<br/>
<br/>



<%
	List list_AutositeRemoteDevice = new ArrayList();
	AutositeRemoteDeviceDS ds_AutositeRemoteDevice = AutositeRemoteDeviceDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_AutositeRemoteDevice = ds_AutositeRemoteDevice.getAll();
	else		
    	list_AutositeRemoteDevice = ds_AutositeRemoteDevice.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_AutositeRemoteDevice, numDisplayInPage, listPage);

	list_AutositeRemoteDevice = PagingUtil.getPagedList(pagingInfo, list_AutositeRemoteDevice);
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

    <td class="columnTitle">  Device Id </td> 
    <td class="columnTitle">  Device Type </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_AutositeRemoteDevice.iterator();iter.hasNext();) {
        AutositeRemoteDevice o_AutositeRemoteDevice = (AutositeRemoteDevice) iter.next();
%>

<TR id="tableRow<%= o_AutositeRemoteDevice.getId()%>">
    <td> <%= o_AutositeRemoteDevice.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_AutositeRemoteDevice.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_AutositeRemoteDevice.getDeviceId()  %> </td>
	<td> <%= o_AutositeRemoteDevice.getDeviceType()  %> </td>
	<td> <%= o_AutositeRemoteDevice.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_autosite_remote_device_form('<%=o_AutositeRemoteDevice.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/autositeRemoteDeviceAction.html?del=true&id=<%=o_AutositeRemoteDevice.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_AutositeRemoteDevice.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_autosite_remote_device_form('<%=o_AutositeRemoteDevice.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_autosite_remote_device_form(target){
		location.href='/v_autosite_remote_device_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_autosite_remote_device_form(target){
		javascript:sendFormAjaxSimple('/autositeRemoteDeviceAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/autositeRemoteDeviceAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

