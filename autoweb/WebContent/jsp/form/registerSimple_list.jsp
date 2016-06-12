<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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
	RegisterSimpleDS ds = RegisterSimpleDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_register_simple_form.html?prv_returnPage=register_simple_home"> Add New 2</a> <br>


<%
	List list_RegisterSimple = new ArrayList();
	RegisterSimpleDS ds_RegisterSimple = RegisterSimpleDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_RegisterSimple = ds_RegisterSimple.getAll();
	else		
    	list_RegisterSimple = ds_RegisterSimple.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_RegisterSimple, numDisplayInPage, listPage);

	list_RegisterSimple = PagingUtil.getPagedList(pagingInfo, list_RegisterSimple);
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

    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Username </td> 
    <td class="columnTitle">  Password </td> 
    <td class="columnTitle">  Birth Year </td> 
    <td class="columnTitle">  Birth Month </td> 
    <td class="columnTitle">  Birth Day </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_RegisterSimple.iterator();iter.hasNext();) {
        RegisterSimple o_RegisterSimple = (RegisterSimple) iter.next();
%>

<TR id="tableRow<%= o_RegisterSimple.getId()%>">
    <td> <%= o_RegisterSimple.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_RegisterSimple.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_RegisterSimple.getFirstName()  %> </td>
	<td> <%= o_RegisterSimple.getLastName()  %> </td>
	<td> <%= o_RegisterSimple.getEmail()  %> </td>
	<td> <%= o_RegisterSimple.getUsername()  %> </td>
	<td> <%= o_RegisterSimple.getPassword()  %> </td>
	<td> <%= o_RegisterSimple.getBirthYear()  %> </td>
	<td> <%= o_RegisterSimple.getBirthMonth()  %> </td>
	<td> <%= o_RegisterSimple.getBirthDay()  %> </td>
	<td> <%= o_RegisterSimple.getTimeCreated()  %> </td>
	<td> <%= o_RegisterSimple.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_register_simple_form('<%=o_RegisterSimple.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/registerSimpleAction.html?del=true&id=<%=o_RegisterSimple.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_RegisterSimple.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_register_simple_form('<%=o_RegisterSimple.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_register_simple_form(target){
		location.href='/v_register_simple_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target;
	}
	function delete_register_simple_form(target){
		javascript:sendFormAjaxSimple('/registerSimpleAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/registerSimpleAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

