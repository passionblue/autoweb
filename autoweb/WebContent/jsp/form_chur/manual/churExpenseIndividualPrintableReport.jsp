<%@page import="com.autosite.util.chur.ChurUtil"%>
<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
   	int _selectedYear = ChurWebUtil.getSelectedYear(request );
	String _selectedWeek = ChurWebUtil.getSelectedWeek(request );

	long expenseId = WebParamUtil.getLongValue(request.getParameter("id"));

	
	ChurExpense expense = ChurExpenseDS.getInstance().getById(expenseId);
	if ( expense == null) return;

	ChurExpenseItem expenseItem = ChurExpenseItemDS.getInstance().getById(expense.getExpenseItemId());
	ChurExpenseCategory expenseCategory = expenseItem == null? null:  ChurExpenseCategoryDS.getInstance().getById(expenseItem.getCategoryId());
	ChurPayee payee = ChurPayeeDS.getInstance().getById(expense.getPayeeId());
	
	
%> 

<br>
<br>
<table style="width:100%">
<tr>
	<td><h1>뉴욕주양장로교회</h1> The Chiu Yang Church of NY</td>
	<td style="vertical-align: bottom; text-align: right;">1830 Decatur St. Ridgewood, NY 11385</td>
</tr>
</table>

<hr>
<br>
<br>

<div style="text-align: center;"><h1> 지출결의서</h1></div>
<br>
<br>

<table class="chur-expense-individual-printable">
  <tr>
    <td class="chur-expense-individual-printable" width="10%" height="50px"  >&nbsp;
    </td>
    <td  class="chur-expense-individual-printable" width="30%" height="50px" ><div class="cell-label">일자</div><div class="cell-data"><%=expense.getWeek() %>/<%=expense.getYear() %></div><div class="clear"></div>
    </td>
    <td class="chur-expense-individual-printable"  width="30%" height="50px" ><div class="cell-label">재정부장</div><div class="cell-data">&nbsp;</div><div class="clear"></div></td>
    <td  class="chur-expense-individual-printable" width="30%" height="50px" ><div class="cell-label">당회장결제</div><div class="cell-data">&nbsp;</div><div class="clear"></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td height="50px"><div class="cell-label">금액</div><div class="cell-data"><%=WebUtil.displayMoney(expense.getAmount()) %></div><div class="clear"></div></td>
    <td height="50px"><div class="cell-label">수표번호</div><div class="cell-data"><%=WebUtil.display(expense.getCheckNumber()) %></div><div class="clear"></div></td>
    <td height="50px"><div class="cell-label">현금</div><div class="cell-data"><%= WebUtil.isTrue(expense.getIsCash()) ?WebUtil.displayMoney(expense.getAmount()):""     %></div><div class="clear"></div></td>
  </tr>
  <tr>
    <td height="150"><div class="cell-label">지출내역</div></td>
    <td colspan="2"><div class="cell-label">&nbsp;</div> <div class="cell-data"><%=expenseItem== null? "":WebUtil.display(expenseItem.getExpenseItem()) %></div></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="150"><div class="cell-label">수령</div></td>
    <td colspan="2"><div class="cell-label">&nbsp;</div><div class="cell-data"><%=payee== null? "":WebUtil.display(payee.getTitle()) %></div></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30px"><div class="cell-label">수령확인</div></td>
    <td colspan="3"><div class="cell-data"></div></td>
  </tr>
  <tr>
    <td height="250"><div class="cell-label">메모</div></td>
    <td colspan="3"><div class="cell-data"><%= WebUtil.display(expense.getComment()) %></div></td>
  </tr>
</table>
Printed at <%= new Date() %>
