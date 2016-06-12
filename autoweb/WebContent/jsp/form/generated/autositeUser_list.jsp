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
	AutositeUserDS ds = AutositeUserDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_autosite_user_form.html?prv_returnPage=autosite_user_home"> Add New 2</a> <br>


<%
	List list_AutositeUser = new ArrayList();
	AutositeUserDS ds_AutositeUser = AutositeUserDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_AutositeUser = ds_AutositeUser.getAll();
	else		
    	list_AutositeUser = ds_AutositeUser.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_AutositeUser, numDisplayInPage, listPage);

	list_AutositeUser = PagingUtil.getPagedList(pagingInfo, list_AutositeUser);
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

    <td class="columnTitle">  Username </td> 
    <td class="columnTitle">  Password </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  User Type </td> 
    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  Nickname </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Disabled </td> 
    <td class="columnTitle">  Time Disabled </td> 
    <td class="columnTitle">  Confirmed </td> 
    <td class="columnTitle">  Time Confirmed </td> 
    <td class="columnTitle">  Opt 1 </td> 
    <td class="columnTitle">  Opt 2 </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_AutositeUser.iterator();iter.hasNext();) {
        AutositeUser o_AutositeUser = (AutositeUser) iter.next();
%>

<TR id="tableRow<%= o_AutositeUser.getId()%>">
    <td> <%= o_AutositeUser.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_AutositeUser.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_AutositeUser.getUsername()  %> </td>
	<td> <%= o_AutositeUser.getPassword()  %> </td>
	<td> <%= o_AutositeUser.getEmail()  %> </td>
	<td> <%= o_AutositeUser.getUserType()  %> </td>
	<td> <%= o_AutositeUser.getFirstName()  %> </td>
	<td> <%= o_AutositeUser.getLastName()  %> </td>
	<td> <%= o_AutositeUser.getNickname()  %> </td>
	<td> <%= o_AutositeUser.getTimeCreated()  %> </td>
	<td> <%= o_AutositeUser.getTimeUpdated()  %> </td>
	<td> <%= o_AutositeUser.getDisabled()  %> </td>
	<td> <%= o_AutositeUser.getTimeDisabled()  %> </td>
	<td> <%= o_AutositeUser.getConfirmed()  %> </td>
	<td> <%= o_AutositeUser.getTimeConfirmed()  %> </td>
	<td> <%= o_AutositeUser.getOpt1()  %> </td>
	<td> <%= o_AutositeUser.getOpt2()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_autosite_user_form('<%=o_AutositeUser.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/autositeUserAction.html?del=true&id=<%=o_AutositeUser.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_AutositeUser.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_autosite_user_form('<%=o_AutositeUser.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_autosite_user_form(target){
		location.href='/v_autosite_user_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target;
	}
	function delete_autosite_user_form(target){
		javascript:sendFormAjaxSimple('/autositeUserAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/autositeUserAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

