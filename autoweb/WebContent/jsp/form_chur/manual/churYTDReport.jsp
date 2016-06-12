<%@page import="java.math.BigDecimal"%>
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
	
	double ytdTotal = ChurReportUtil.getYearTotalExpenseAmount(site.getId(), _selectedYear);
	double ytdIncomeTotal = ChurReportUtil.getYearTotalIncomeAmount(site.getId(), _selectedYear);
%>

<h4> Total Expense : <%= ytdTotal %></h4>
<h4> Total Income : <%= ytdIncomeTotal %></h4>
<hr>

<TABLE class="mytable1">
<%
	List weeks = ChurUtil.getWeeksForYear(_selectedYear);
	
	double incomeYtd = 0.0;
	double expenseYtd = 0.0;

    for(Iterator it = weeks.iterator();it.hasNext();) {
		String weekStr = (String)it.next();
		double incomeTotal = ChurReportUtil.getWeeklyTotalIncomeAmount(site.getId(), _selectedYear, weekStr);		        
		double expenseTotal = ChurReportUtil.getWeeklyTotalExpenseAmount(site.getId(), _selectedYear, weekStr);		        
        
		incomeYtd += incomeTotal;
		expenseYtd += expenseTotal;
		
		
		
		incomeTotal =   (Math.round (incomeTotal*(100)))/100.0;
		incomeYtd =   (Math.round  (incomeYtd*(100)))/100.0;
		expenseTotal =   (Math.round(  expenseTotal*(100)))/100.0;
		expenseYtd =   (Math.round(  expenseYtd*(100)))/100.0;
		
		
%>
	<TR>
		<TD><%= weekStr %></TD>
		<TD><%= WebUtil.displayMoney(incomeTotal)%></TD>
		<TD><b><%= WebUtil.displayMoney(incomeYtd)%></b></TD>
		<TD><%= WebUtil.displayMoney(expenseTotal)%></TD>
		<TD><b><%= WebUtil.displayMoney(expenseYtd)%></b></TD>
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
		location.href='/v_chur_expense_form.html?cmd=edit&prv_returnPage=chur_expense_weekly_report&id=' + target;
	}
	function edit_chur_expense_single_print_form(target){
		location.href='/v_chur_expense_single_report.html?fmt=prt&id=' + target;
	}
	$(document).ready(function() { 
	    // call the tablesorter plugin 
	    $("table").tablesorter({ 
	        // sort on the first column and third column, order asc 
	        sortList: [[0,0],[2,0]] 
	    }); 
	}); 	

</script>
