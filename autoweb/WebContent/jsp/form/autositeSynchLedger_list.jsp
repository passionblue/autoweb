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
	AutositeSynchLedgerDS ds = AutositeSynchLedgerDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_autosite_synch_ledger_form.html?prv_returnPage=autosite_synch_ledger_home"> Add New 2</a> <br>
<br/>
<a href="/v_autosite_synch_ledger_home.html">home</a> | <a href="/v_autosite_synch_ledger_home.html">home</a> | <a href="/v_autosite_synch_ledger_home.html">home</a>
<br/>
<br/>



<%
	List list_AutositeSynchLedger = new ArrayList();
	AutositeSynchLedgerDS ds_AutositeSynchLedger = AutositeSynchLedgerDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_AutositeSynchLedger = ds_AutositeSynchLedger.getAll();
	else		
    	list_AutositeSynchLedger = ds_AutositeSynchLedger.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_AutositeSynchLedger, numDisplayInPage, listPage);

	list_AutositeSynchLedger = PagingUtil.getPagedList(pagingInfo, list_AutositeSynchLedger);
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
    <td class="columnTitle">  Original Ledger Id </td> 
    <td class="columnTitle">  Scope </td> 
    <td class="columnTitle">  Target </td> 
    <td class="columnTitle">  Remote Token </td> 
    <td class="columnTitle">  Object Id </td> 
    <td class="columnTitle">  Synch Id </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_AutositeSynchLedger.iterator();iter.hasNext();) {
        AutositeSynchLedger o_AutositeSynchLedger = (AutositeSynchLedger) iter.next();
%>

<TR id="tableRow<%= o_AutositeSynchLedger.getId()%>">
    <td> <%= o_AutositeSynchLedger.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_AutositeSynchLedger.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_AutositeSynchLedger.getDeviceId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getOriginalLedgerId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getScope()  %> </td>
	<td> <%= o_AutositeSynchLedger.getTarget()  %> </td>
	<td> <%= o_AutositeSynchLedger.getRemoteToken()  %> </td>
	<td> <%= o_AutositeSynchLedger.getObjectId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getSynchId()  %> </td>
	<td> <%= o_AutositeSynchLedger.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_autosite_synch_ledger_form('<%=o_AutositeSynchLedger.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/autositeSynchLedgerAction.html?del=true&id=<%=o_AutositeSynchLedger.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_AutositeSynchLedger.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_autosite_synch_ledger_form('<%=o_AutositeSynchLedger.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_autosite_synch_ledger_form(target){
		location.href='/v_autosite_synch_ledger_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_autosite_synch_ledger_form(target){
		javascript:sendFormAjaxSimple('/autositeSynchLedgerAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/autositeSynchLedgerAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

