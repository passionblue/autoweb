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
	ChurExpenseDS ds = ChurExpenseDS.getInstance();    

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

<a href="t_chur_expense_form.html?prv_returnPage=chur_expense_home"> Add New 2</a> <br>


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Year </td> 
    <td class="columnTitle">  Week </td> 
    <td class="columnTitle">  Expense Item Id </td> 
    <td class="columnTitle">  Payee Id </td> 
    <td class="columnTitle">  Amount </td> 
    <td class="columnTitle">  Is Cash </td> 
    <td class="columnTitle">  Check Number </td> 
    <td class="columnTitle">  Check Cleared </td> 
    <td class="columnTitle">  Comment </td> 
    <td class="columnTitle">  Cancelled </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpense _ChurExpense = (ChurExpense) iter.next();
%>

<TR id="tableRow<%= _ChurExpense.getId()%>">
    <td> <%= _ChurExpense.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _ChurExpense.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _ChurExpense.getYear()  %> </td>
	<td> <%= _ChurExpense.getWeek()  %> </td>
	<td> <%= _ChurExpense.getExpenseItemId()  %> </td>
	<td> <%= _ChurExpense.getPayeeId()  %> </td>
	<td> <%= _ChurExpense.getAmount()  %> </td>
	<td> <%= _ChurExpense.getIsCash()  %> </td>
	<td> <%= _ChurExpense.getCheckNumber()  %> </td>
	<td> <%= _ChurExpense.getCheckCleared()  %> </td>
	<td> <%= _ChurExpense.getComment()  %> </td>
	<td> <%= _ChurExpense.getCancelled()  %> </td>
	<td> <%= _ChurExpense.getTimeCreated()  %> </td>
	<td> <%= _ChurExpense.getTimeUpdated()  %> </td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_expense_form('<%=_ChurExpense.getId()%>');">Edit</a>
	</td>
	<td> <a href="javascript:sendFormAjaxSimple('/churExpenseAction.html?del=true&id=<%=_ChurExpense.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_ChurExpense.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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
	function edit_chur_expense_form(target){
		location.href='/v_chur_expense_form.html?cmd=edit&prv_returnPage=chur_expense_home&id=' + target;
	}

</script>
