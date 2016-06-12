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
	ChurMemberDS ds = ChurMemberDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();


%> 
<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->

<a href="t_chur_member_form.html?prv_returnPage=chur_member_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
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

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurMember _ChurMember = (ChurMember) iter.next();
%>

<TR id="tableRow<%= _ChurMember.getId()%>">
    <td> <%= _ChurMember.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _ChurMember.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _ChurMember.getFullName()  %> </td>
	<td> <%= _ChurMember.getFirstName()  %> </td>
	<td> <%= _ChurMember.getLastName()  %> </td>
	<td> <%= _ChurMember.getTitle()  %> </td>
	<td> <%= _ChurMember.getOtherName()  %> </td>
	<td> <%= _ChurMember.getHousehold()  %> </td>
	<td> <%= _ChurMember.getHouseholdId()  %> </td>
	<td> <%= _ChurMember.getIsGroup()  %> </td>
	<td> <%= _ChurMember.getIsGuest()  %> </td>
	<td> <%= _ChurMember.getIsSpeaker()  %> </td>
	<td> <%= _ChurMember.getTimeCreated()  %> </td>
	<td> <%= _ChurMember.getListIndex()  %> </td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_member_form('<%=_ChurMember.getId()%>');">Edit</a>
	</td>
	<td> <a href="javascript:sendFormAjaxSimple('/churMemberAction.html?del=true&id=<%=_ChurMember.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_ChurMember.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
	function edit_chur_member_form(target){
		location.href='/v_chur_member_form.html?cmd=edit&prv_returnPage=chur_member_home&id=' + target;
	}

</script>
