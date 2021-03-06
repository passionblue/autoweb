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
	AutositeSessionContextEntityDS ds = AutositeSessionContextEntityDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_autosite_session_context_entity_form.html?prv_returnPage=autosite_session_context_entity_home"> Add New 2</a> <br>
<br/>
<a href="/v_autosite_session_context_entity_home.html">home</a> | <a href="/v_autosite_session_context_entity_home.html">home</a> | <a href="/v_autosite_session_context_entity_home.html">home</a>
<br/>
<br/>



<%
	List list_AutositeSessionContextEntity = new ArrayList();
	AutositeSessionContextEntityDS ds_AutositeSessionContextEntity = AutositeSessionContextEntityDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_AutositeSessionContextEntity = ds_AutositeSessionContextEntity.getAll();
	else		
    	list_AutositeSessionContextEntity = ds_AutositeSessionContextEntity.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_AutositeSessionContextEntity, numDisplayInPage, listPage);

	list_AutositeSessionContextEntity = PagingUtil.getPagedList(pagingInfo, list_AutositeSessionContextEntity);
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

    <td class="columnTitle">  Serial </td> 
    <td class="columnTitle">  Is Login </td> 
    <td class="columnTitle">  Time Login </td> 
    <td class="columnTitle">  Time Last Access </td> 
    <td class="columnTitle">  Login User Id </td> 
    <td class="columnTitle">  Session Type </td> 
    <td class="columnTitle">  Remote Device Id </td> 
    <td class="columnTitle">  Remote Ip </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_AutositeSessionContextEntity.iterator();iter.hasNext();) {
        AutositeSessionContextEntity o_AutositeSessionContextEntity = (AutositeSessionContextEntity) iter.next();
%>

<TR id="tableRow<%= o_AutositeSessionContextEntity.getId()%>">
    <td> <%= o_AutositeSessionContextEntity.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_AutositeSessionContextEntity.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_AutositeSessionContextEntity.getSerial()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getIsLogin()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeLogin()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeLastAccess()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getLoginUserId()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getSessionType()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getRemoteDeviceId()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getRemoteIp()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeCreated()  %> </td>
	<td> <%= o_AutositeSessionContextEntity.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_autosite_session_context_entity_form('<%=o_AutositeSessionContextEntity.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/autositeSessionContextEntityAction.html?del=true&id=<%=o_AutositeSessionContextEntity.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_AutositeSessionContextEntity.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_autosite_session_context_entity_form('<%=o_AutositeSessionContextEntity.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_autosite_session_context_entity_form(target){
		location.href='/v_autosite_session_context_entity_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_autosite_session_context_entity_form(target){
		javascript:sendFormAjaxSimple('/autositeSessionContextEntityAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/autositeSessionContextEntityAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

