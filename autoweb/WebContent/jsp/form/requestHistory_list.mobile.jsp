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
	RequestHistoryDS ds = RequestHistoryDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_request_history_form.html?prv_returnPage=request_history_home"> Add New 2</a> <br>
<br/>
<a href="/v_request_history_home.html">home</a> | <a href="/v_request_history_home.html">home</a> | <a href="/v_request_history_home.html">home</a>
<br/>
<br/>



<%
	List list_RequestHistory = new ArrayList();
	RequestHistoryDS ds_RequestHistory = RequestHistoryDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_RequestHistory = ds_RequestHistory.getAll();
	else		
    	list_RequestHistory = ds_RequestHistory.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_RequestHistory, numDisplayInPage, listPage);

	list_RequestHistory = PagingUtil.getPagedList(pagingInfo, list_RequestHistory);
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

    <td class="columnTitle">  Forward Site Id </td> 
    <td class="columnTitle">  Is Dropped </td> 
    <td class="columnTitle">  Is Pageless </td> 
    <td class="columnTitle">  Is Login </td> 
    <td class="columnTitle">  Is Ajax </td> 
    <td class="columnTitle">  Is Robot </td> 
    <td class="columnTitle">  Userid </td> 
    <td class="columnTitle">  User Agent </td> 
    <td class="columnTitle">  Refer </td> 
    <td class="columnTitle">  Robot </td> 
    <td class="columnTitle">  Remote Ip </td> 
    <td class="columnTitle">  Site Url </td> 
    <td class="columnTitle">  Uri </td> 
    <td class="columnTitle">  Query </td> 
    <td class="columnTitle">  Rpci </td> 
    <td class="columnTitle">  Session Id </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_RequestHistory.iterator();iter.hasNext();) {
        RequestHistory o_RequestHistory = (RequestHistory) iter.next();
%>

<TR id="tableRow<%= o_RequestHistory.getId()%>">
    <td> <%= o_RequestHistory.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_RequestHistory.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_RequestHistory.getForwardSiteId()  %> </td>
	<td> <%= o_RequestHistory.getIsDropped()  %> </td>
	<td> <%= o_RequestHistory.getIsPageless()  %> </td>
	<td> <%= o_RequestHistory.getIsLogin()  %> </td>
	<td> <%= o_RequestHistory.getIsAjax()  %> </td>
	<td> <%= o_RequestHistory.getIsRobot()  %> </td>
	<td> <%= o_RequestHistory.getUserid()  %> </td>
	<td> <%= o_RequestHistory.getUserAgent()  %> </td>
	<td> <%= o_RequestHistory.getRefer()  %> </td>
	<td> <%= o_RequestHistory.getRobot()  %> </td>
	<td> <%= o_RequestHistory.getRemoteIp()  %> </td>
	<td> <%= o_RequestHistory.getSiteUrl()  %> </td>
	<td> <%= o_RequestHistory.getUri()  %> </td>
	<td> <%= o_RequestHistory.getQuery()  %> </td>
	<td> <%= o_RequestHistory.getRpci()  %> </td>
	<td> <%= o_RequestHistory.getSessionId()  %> </td>
	<td> <%= o_RequestHistory.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_request_history_form('<%=o_RequestHistory.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/requestHistoryAction.html?del=true&id=<%=o_RequestHistory.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_RequestHistory.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_request_history_form('<%=o_RequestHistory.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_request_history_form(target){
		location.href='/v_request_history_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_request_history_form(target){
		javascript:sendFormAjaxSimple('/requestHistoryAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/requestHistoryAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

