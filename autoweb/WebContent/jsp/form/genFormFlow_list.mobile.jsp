<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:
20150215: increased heartbeat to long number


Source Generated: Sun Mar 15 14:31:01 EDT 2015
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
	GenFormFlowDS ds = GenFormFlowDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_gen_form_flow_form.html?prv_returnPage=gen_form_flow_home"> Add New 2</a> <br>
<br/>
<a href="/v_gen_form_flow_home.html">home</a> | <a href="/v_gen_form_flow_home.html">home</a> | <a href="/v_gen_form_flow_home.html">home</a>
<br/>
<br/>



<%
	List list_GenFormFlow = new ArrayList();
	GenFormFlowDS ds_GenFormFlow = GenFormFlowDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_GenFormFlow = ds_GenFormFlow.getAll();
	else		
    	list_GenFormFlow = ds_GenFormFlow.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_GenFormFlow, numDisplayInPage, listPage);

	list_GenFormFlow = PagingUtil.getPagedList(pagingInfo, list_GenFormFlow);
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

    <td class="columnTitle">  ExtraString </td> 
    <td class="columnTitle">  Ext Int </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_GenFormFlow.iterator();iter.hasNext();) {
        GenFormFlowDataHolder o_GenFormFlow = (GenFormFlowDataHolder) iter.next();
%>

<TR id="tableRow<%= o_GenFormFlow.getId()%>">
    <td> <%= o_GenFormFlow.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_GenFormFlow.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_GenFormFlow.getExtString()  %> </td>
	<td> <%= o_GenFormFlow.getExtInt()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_gen_form_flow_form('<%=o_GenFormFlow.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/genFormFlowAction.html?del=true&id=<%=o_GenFormFlow.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_GenFormFlow.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_gen_form_flow_form('<%=o_GenFormFlow.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_gen_form_flow_form(target){
		location.href='/v_gen_form_flow_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_gen_form_flow_form(target){
		javascript:sendFormAjaxSimple('/genFormFlowAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/genFormFlowAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 100000000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

