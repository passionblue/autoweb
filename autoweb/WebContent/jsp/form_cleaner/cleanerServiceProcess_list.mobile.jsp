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
	CleanerServiceProcessDS ds = CleanerServiceProcessDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_service_process_form.html?prv_returnPage=cleaner_service_process_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_service_process_home.html">home</a> | <a href="/v_cleaner_service_process_home.html">home</a> | <a href="/v_cleaner_service_process_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerServiceProcess = new ArrayList();
	CleanerServiceProcessDS ds_CleanerServiceProcess = CleanerServiceProcessDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerServiceProcess = ds_CleanerServiceProcess.getAll();
	else		
    	list_CleanerServiceProcess = ds_CleanerServiceProcess.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerServiceProcess, numDisplayInPage, listPage);

	list_CleanerServiceProcess = PagingUtil.getPagedList(pagingInfo, list_CleanerServiceProcess);
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
    <td class="columnTitle">  Process User Id </td> 
    <td class="columnTitle">  Process Type </td> 
    <td class="columnTitle">  Time Started </td> 
    <td class="columnTitle">  Time Ended </td> 
    <td class="columnTitle">  Note </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerServiceProcess.iterator();iter.hasNext();) {
        CleanerServiceProcess o_CleanerServiceProcess = (CleanerServiceProcess) iter.next();
%>

<TR id="tableRow<%= o_CleanerServiceProcess.getId()%>">
    <td> <%= o_CleanerServiceProcess.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerServiceProcess.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerServiceProcess.getTicketId()  %> </td>
	<td> <%= o_CleanerServiceProcess.getProcessUserId()  %> </td>
	<td> <%= o_CleanerServiceProcess.getProcessType()  %> </td>
	<td> <%= o_CleanerServiceProcess.getTimeStarted()  %> </td>
	<td> <%= o_CleanerServiceProcess.getTimeEnded()  %> </td>
	<td> <%= o_CleanerServiceProcess.getNote()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_service_process_form('<%=o_CleanerServiceProcess.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerServiceProcessAction.html?del=true&id=<%=o_CleanerServiceProcess.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerServiceProcess.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_service_process_form('<%=o_CleanerServiceProcess.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_service_process_form(target){
		location.href='/v_cleaner_service_process_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_service_process_form(target){
		javascript:sendFormAjaxSimple('/cleanerServiceProcessAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerServiceProcessAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

