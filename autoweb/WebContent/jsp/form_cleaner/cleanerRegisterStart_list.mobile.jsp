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
	CleanerRegisterStartDS ds = CleanerRegisterStartDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_cleaner_register_start_form.html?prv_returnPage=cleaner_register_start_home"> Add New 2</a> <br>
<br/>
<a href="/v_cleaner_register_start_home.html">home</a> | <a href="/v_cleaner_register_start_home.html">home</a> | <a href="/v_cleaner_register_start_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerRegisterStart = new ArrayList();
	CleanerRegisterStartDS ds_CleanerRegisterStart = CleanerRegisterStartDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerRegisterStart = ds_CleanerRegisterStart.getAll();
	else		
    	list_CleanerRegisterStart = ds_CleanerRegisterStart.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerRegisterStart, numDisplayInPage, listPage);

	list_CleanerRegisterStart = PagingUtil.getPagedList(pagingInfo, list_CleanerRegisterStart);
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

    <td class="columnTitle">  Site Title </td> 
    <td class="columnTitle">  Site Name </td> 
    <td class="columnTitle">  Username </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Password </td> 
    <td class="columnTitle">  Password Repeat </td> 
    <td class="columnTitle">  Location </td> 
    <td class="columnTitle">  Created Site Url </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerRegisterStart.iterator();iter.hasNext();) {
        CleanerRegisterStartDataHolder o_CleanerRegisterStart = (CleanerRegisterStartDataHolder) iter.next();
%>

<TR id="tableRow<%= o_CleanerRegisterStart.getId()%>">
    <td> <%= o_CleanerRegisterStart.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerRegisterStart.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerRegisterStart.getSiteTitle()  %> </td>
	<td> <%= o_CleanerRegisterStart.getSiteName()  %> </td>
	<td> <%= o_CleanerRegisterStart.getUsername()  %> </td>
	<td> <%= o_CleanerRegisterStart.getEmail()  %> </td>
	<td> <%= o_CleanerRegisterStart.getPassword()  %> </td>
	<td> <%= o_CleanerRegisterStart.getPasswordRepeat()  %> </td>
	<td> <%= o_CleanerRegisterStart.getLocation()  %> </td>
	<td> <%= o_CleanerRegisterStart.getCreatedSiteUrl()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_register_start_form('<%=o_CleanerRegisterStart.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerRegisterStartAction.html?del=true&id=<%=o_CleanerRegisterStart.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerRegisterStart.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_register_start_form('<%=o_CleanerRegisterStart.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_register_start_form(target){
		location.href='/v_cleaner_register_start_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_register_start_form(target){
		javascript:sendFormAjaxSimple('/cleanerRegisterStartAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerRegisterStartAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

