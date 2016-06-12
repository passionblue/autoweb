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
	CleanerLocationConfigDS ds = CleanerLocationConfigDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_location_config_form.html?prv_returnPage=cleaner_location_config_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_location_config_home.html">home</a> | <a href="/v_cleaner_location_config_home.html">home</a> | <a href="/v_cleaner_location_config_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerLocationConfig = new ArrayList();
	CleanerLocationConfigDS ds_CleanerLocationConfig = CleanerLocationConfigDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerLocationConfig = ds_CleanerLocationConfig.getAll();
	else		
    	list_CleanerLocationConfig = ds_CleanerLocationConfig.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerLocationConfig, numDisplayInPage, listPage);

	list_CleanerLocationConfig = PagingUtil.getPagedList(pagingInfo, list_CleanerLocationConfig);
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
    <td class="columnTitle">  Open Hour Weekday </td> 
    <td class="columnTitle">  Close Hour Weekday </td> 
    <td class="columnTitle">  Open Hour Sat </td> 
    <td class="columnTitle">  Close Hour Sat </td> 
    <td class="columnTitle">  Open Hour Sun </td> 
    <td class="columnTitle">  Close Hour Sun </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerLocationConfig.iterator();iter.hasNext();) {
        CleanerLocationConfig o_CleanerLocationConfig = (CleanerLocationConfig) iter.next();
%>

<TR id="tableRow<%= o_CleanerLocationConfig.getId()%>">
    <td> <%= o_CleanerLocationConfig.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerLocationConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerLocationConfig.getLocationId()  %> </td>
	<td> <%= o_CleanerLocationConfig.getOpenHourWeekday()  %> </td>
	<td> <%= o_CleanerLocationConfig.getCloseHourWeekday()  %> </td>
	<td> <%= o_CleanerLocationConfig.getOpenHourSat()  %> </td>
	<td> <%= o_CleanerLocationConfig.getCloseHourSat()  %> </td>
	<td> <%= o_CleanerLocationConfig.getOpenHourSun()  %> </td>
	<td> <%= o_CleanerLocationConfig.getCloseHourSun()  %> </td>
	<td> <%= o_CleanerLocationConfig.getTimeCreated()  %> </td>
	<td> <%= o_CleanerLocationConfig.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_location_config_form('<%=o_CleanerLocationConfig.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerLocationConfigAction.html?del=true&id=<%=o_CleanerLocationConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerLocationConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_location_config_form('<%=o_CleanerLocationConfig.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_location_config_form(target){
		location.href='/v_cleaner_location_config_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_location_config_form(target){
		javascript:sendFormAjaxSimple('/cleanerLocationConfigAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerLocationConfigAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

