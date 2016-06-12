<%@page import="java.math.BigDecimal"%>
<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.access.*"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");

	AccessConfigManager3 churAccessMgr = AccessConfigManager3.registerInstanceForCustomTitle("ChurApp", "conf/churapp_access_config.properties" );
	 if ( !AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "income_report", AccessDef.SystemRole.SiteAdmin))){
	     return;
	 }
	
	
	
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


<%
	double ytdTotal = ChurReportUtil.getYearTotalExpenseAmount(site.getId(), _selectedYear);
	double ytdIncomeTotal = ChurReportUtil.getYearTotalIncomeAmount(site.getId(), _selectedYear);
%>

<h4> Total Expense : <%= WebUtil.displayMoney(ytdTotal)  %></h4>
<h4> Total Income : <%= WebUtil.displayMoney(ytdIncomeTotal)  %></h4>


<hr>

<br>

<TABLE class="tablesorter" cellspacing="1">
    <thead>
        <tr> 
            <th>Category</th> 
            <th>Item</th> 
            <th>Total</th> 
            <th>Monthly Average</th> 
        </tr> 
    </thead> 
<%
	Map itemizedExpenseTotal = ChurReportUtil.getYearlyTotalExpenseAmount(site.getId(), _selectedYear);
	
	double incomeYtd = 0.0;
	double expenseYtd = 0.0;

    for(Iterator it = itemizedExpenseTotal.keySet().iterator();it.hasNext();) {

        Long itemKey = (Long) it.next();
		BigDecimal total  = (BigDecimal) itemizedExpenseTotal.get(itemKey);
		
		ChurExpenseItem item = ChurExpenseItemDS.getInstance().getById(itemKey.longValue());
		ChurExpenseCategory category = ChurExpenseCategoryDS.getInstance().getById(item.getCategoryId());
		
%>

	<tr>
		<td><%= category.getExpenseCategory() %></td> 
		<td><%= item.getExpenseItem() %></td> 
		<td><%= WebUtil.displayMoney( total.doubleValue()) %></td> 
		<td><%= WebUtil.displayMoney( total.doubleValue()/12.0) %></td>
	</tr>

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
