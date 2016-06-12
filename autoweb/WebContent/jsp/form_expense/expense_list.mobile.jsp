<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:
20150215: increased heartbeat to long number


Source Generated: Sun Jul 12 20:40:22 EDT 2015
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
	ExpenseDS ds = ExpenseDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 


<a href="t_expense_form.html?prv_returnPage=expense_home"> Add New 2</a> <br>
<br/>
<a href="/v_expense_home.html">home</a> | <a href="/v_expense_home.html">home</a> | <a href="/v_expense_home.html">home</a>
<br/>
<br/>



<%
	List list_Expense = new ArrayList();
	ExpenseDS ds_Expense = ExpenseDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_Expense = ds_Expense.getAll();
	else		
    	list_Expense = ds_Expense.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_Expense, numDisplayInPage, listPage);

	list_Expense = PagingUtil.getPagedList(pagingInfo, list_Expense);
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

    <td class="columnTitle">  Expense Item Id </td> 
    <td class="columnTitle">  Amount </td> 
    <td class="columnTitle">  Description </td> 
    <td class="columnTitle">  Pay Method </td> 
    <td class="columnTitle">  Not Expense </td> 
    <td class="columnTitle">  Reference </td> 
    <td class="columnTitle">  Date Expense </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_Expense.iterator();iter.hasNext();) {
        Expense o_Expense = (Expense) iter.next();
%>

<TR id="tableRow<%= o_Expense.getId()%>">
    <td> <%= o_Expense.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_Expense.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_Expense.getExpenseItemId()  %> </td>
	<td> <%= o_Expense.getAmount()  %> </td>
	<td> <%= o_Expense.getDescription()  %> </td>
	<td> <%= o_Expense.getPayMethod()  %> </td>
	<td> <%= o_Expense.getNotExpense()  %> </td>
	<td> <%= o_Expense.getReference()  %> </td>
	<td> <%= o_Expense.getDateExpense()  %> </td>
	<td> <%= o_Expense.getTimeCreated()  %> </td>
	<td> <%= o_Expense.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_expense_form('<%=o_Expense.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/expenseAction.html?del=true&id=<%=o_Expense.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_Expense.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_expense_form('<%=o_Expense.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_expense_form(target){
		location.href='/v_expense_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_expense_form(target){
		javascript:sendFormAjaxSimple('/expenseAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>



<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/expenseAction.html?ajaxRequest=true&ajaxOut=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 100000000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>

