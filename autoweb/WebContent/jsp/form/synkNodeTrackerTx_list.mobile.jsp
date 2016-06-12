<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015
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
	SynkNodeTrackerTxDS ds = SynkNodeTrackerTxDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_synk_node_tracker_tx_form.html?prv_returnPage=synk_node_tracker_tx_home"> Add New 2</a> <br>
<br/>
<a href="/v_synk_node_tracker_tx_home.html">home</a> | <a href="/v_synk_node_tracker_tx_home.html">home</a> | <a href="/v_synk_node_tracker_tx_home.html">home</a>
<br/>
<br/>



<%
	List list_SynkNodeTrackerTx = new ArrayList();
	SynkNodeTrackerTxDS ds_SynkNodeTrackerTx = SynkNodeTrackerTxDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_SynkNodeTrackerTx = ds_SynkNodeTrackerTx.getAll();
	else		
    	list_SynkNodeTrackerTx = ds_SynkNodeTrackerTx.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_SynkNodeTrackerTx, numDisplayInPage, listPage);

	list_SynkNodeTrackerTx = PagingUtil.getPagedList(pagingInfo, list_SynkNodeTrackerTx);
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

    <td class="columnTitle">  Namespace </td> 
    <td class="columnTitle">  Device Id </td> 
    <td class="columnTitle">  Tx Token </td> 
    <td class="columnTitle">  Stamp Acked </td> 
    <td class="columnTitle">  Stamp Last </td> 
    <td class="columnTitle">  Num Records </td> 
    <td class="columnTitle">  Ip </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_SynkNodeTrackerTx.iterator();iter.hasNext();) {
        SynkNodeTrackerTx o_SynkNodeTrackerTx = (SynkNodeTrackerTx) iter.next();
%>

<TR id="tableRow<%= o_SynkNodeTrackerTx.getId()%>">
    <td> <%= o_SynkNodeTrackerTx.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_SynkNodeTrackerTx.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_SynkNodeTrackerTx.getNamespace()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getDeviceId()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getTxToken()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getStampAcked()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getStampLast()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getNumRecords()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getIp()  %> </td>
	<td> <%= o_SynkNodeTrackerTx.getTimeCreated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_synk_node_tracker_tx_form('<%=o_SynkNodeTrackerTx.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/synkNodeTrackerTxAction.html?del=true&id=<%=o_SynkNodeTrackerTx.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_SynkNodeTrackerTx.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_synk_node_tracker_tx_form('<%=o_SynkNodeTrackerTx.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_synk_node_tracker_tx_form(target){
		location.href='/v_synk_node_tracker_tx_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_synk_node_tracker_tx_form(target){
		javascript:sendFormAjaxSimple('/synkNodeTrackerTxAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/synkNodeTrackerTxAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

