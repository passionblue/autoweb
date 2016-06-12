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
	ChurMemberDS ds = ChurMemberDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_chur_member_form.html?prv_returnPage=chur_member_home"> Add New 2</a> <br>
<br/>
<a href="/v_chur_member_home.html">home</a> | <a href="/v_chur_member_home.html">home</a> | <a href="/v_chur_member_home.html">home</a>
<br/>
<br/>



<%
	List list_ChurMember = new ArrayList();
	ChurMemberDS ds_ChurMember = ChurMemberDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_ChurMember = ds_ChurMember.getAll();
	else		
    	list_ChurMember = ds_ChurMember.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_ChurMember, numDisplayInPage, listPage);

	list_ChurMember = PagingUtil.getPagedList(pagingInfo, list_ChurMember);
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

    <td class="columnTitle">  Full Name </td> 
    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Other Name </td> 
    <td class="columnTitle">  Household </td> 
    <td class="columnTitle">  Household Id </td> 
    <td class="columnTitle">  Is Group </td> 
    <td class="columnTitle">  Is Guest </td> 
    <td class="columnTitle">  Is Speaker </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  List Index </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_ChurMember.iterator();iter.hasNext();) {
        ChurMember o_ChurMember = (ChurMember) iter.next();
%>

<TR id="tableRow<%= o_ChurMember.getId()%>">
    <td> <%= o_ChurMember.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_ChurMember.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_ChurMember.getFullName()  %> </td>
	<td> <%= o_ChurMember.getFirstName()  %> </td>
	<td> <%= o_ChurMember.getLastName()  %> </td>
	<td> <%= o_ChurMember.getTitle()  %> </td>
	<td> <%= o_ChurMember.getOtherName()  %> </td>
	<td> <%= o_ChurMember.getHousehold()  %> </td>
	<td> <%= o_ChurMember.getHouseholdId()  %> </td>
	<td> <%= o_ChurMember.getIsGroup()  %> </td>
	<td> <%= o_ChurMember.getIsGuest()  %> </td>
	<td> <%= o_ChurMember.getIsSpeaker()  %> </td>
	<td> <%= o_ChurMember.getTimeCreated()  %> </td>
	<td> <%= o_ChurMember.getListIndex()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_chur_member_form('<%=o_ChurMember.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/churMemberAction.html?del=true&id=<%=o_ChurMember.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_ChurMember.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_chur_member_form('<%=o_ChurMember.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_chur_member_form(target){
		location.href='/v_chur_member_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_chur_member_form(target){
		javascript:sendFormAjaxSimple('/churMemberAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/churMemberAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

