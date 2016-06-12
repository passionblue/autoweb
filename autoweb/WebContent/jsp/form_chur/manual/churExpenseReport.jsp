<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	// This is weekly report

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

	SiteDS siteDS = SiteDS.getInstance();

    //>>
	String _selectedWeek = ChurWebUtil.getSelectedWeek(request );
	int _selectedYear = ChurWebUtil.getSelectedYear(request);

	list = ds.getBySiteIdWeekList(site.getId(), _selectedWeek);
	//<<        

	
%> 
<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 30;
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
<a href="t_chur_expense_individual_printable_report.html"> Add New 2</a> <br>




<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
    <td class="columnTitle">  Week </td> 
    <td class="columnTitle">  Expense Item Id </td> 
    <td class="columnTitle">  Payee Id </td> 
    <td class="columnTitle">  Amount </td> 
    <td class="columnTitle">  Is Cash </td> 
    <td class="columnTitle">  Check Number </td> 
    <td class="columnTitle">  Check Cleared </td> 
    <td class="columnTitle">  Comment </td> 
    <td class="columnTitle">  Cancelled </td> 
    <td class="columnTitle">  Print </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%



	double weeklyTotal = 0.0;

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpense _ChurExpense = (ChurExpense) iter.next();
        
        if (_ChurExpense.getYear() != _selectedYear) continue;
        
        ChurPayee payee = ChurPayeeDS.getInstance().getById(_ChurExpense.getPayeeId());
        String payeeDisplay = (payee == null? "INVALID(" + _ChurExpense.getPayeeId()+")": payee.getTitle());
        
        ChurExpenseItem expenseItem = ChurExpenseItemDS.getInstance().getById(_ChurExpense.getExpenseItemId());
        String expenseItemDisplay = (expenseItem == null? "INVALID(" + _ChurExpense.getExpenseItemId()+")": expenseItem.getExpenseItem());
        
        weeklyTotal += _ChurExpense.getAmount();
        
%>

<TR id="tableRow<%= _ChurExpense.getId()%>">
    <td> <%= _ChurExpense.getId() %> </td>
	<td> <%= _ChurExpense.getWeek()  %> </td>
	<td> <%= expenseItemDisplay  %> </td>
	<td> <%= payeeDisplay  %> </td>
	<td> <%= _ChurExpense.getAmount()  %> </td>
	<td> <%= WebUtil.displayYesNo(_ChurExpense.getIsCash())  %> </td>
	<td> <%= _ChurExpense.getCheckNumber()  %> </td>
	<td> <%= WebUtil.displayYesNo(_ChurExpense.getCheckCleared())  %> </td>
	<td> <%= _ChurExpense.getComment()  %> </td>
	<td> <%= WebUtil.displayYesNo(_ChurExpense.getCancelled())  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_chur_expense_single_print_form('<%=_ChurExpense.getId()%>');return null;">Print
	    
	    </a>
	</td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_expense_form('<%=_ChurExpense.getId()%>');">Edit</a>
	</td>
	<td> <a href="javascript:sendFormAjaxSimple('/churExpenseAction.html?del=true&id=<%=_ChurExpense.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_ChurExpense.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>
<br>

<h4> Total Expense : <%= weeklyTotal %></h4>

<%
	double ytdTotal = ChurReportUtil.getYearTotalExpenseAmount(site.getId(), _selectedYear);
	double ytdIncomeTotal = ChurReportUtil.getYearTotalIncomeAmount(site.getId(), _selectedYear);
%>

<h4> Total Expense : <%= ytdTotal %></h4>
<h4> Total Income : <%= ytdIncomeTotal %></h4>



<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function edit_chur_expense_form(target){
		location.href='/v_chur_expense_form.html?cmd=edit&prv_returnPage=chur_expense_weekly_report&fmt=chur-single-expense-report&id=' + target;
	}
	function open_chur_expense_single_print_form(target){
		window.open('/t_chur_expense_individual_printable_report.html?fmt=prt&id=' + target,'',
				    'resizable=yes,status=yes,location=no,toolbar=no,menubar=no,fullscreen=no,scrollbars=yes,dependent=no,width=600,left=100,height=600,top=100');
	}

</script>
