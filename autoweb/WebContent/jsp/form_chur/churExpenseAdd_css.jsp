<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurExpense _ChurExpenseDefault = new ChurExpense();// ChurExpenseDS.getInstance().getDeafult();
    
    String _yearValue= (reqParams.get("year")==null?WebUtil.display(_ChurExpenseDefault.getYear()):WebUtil.display((String)reqParams.get("year")));
    String _weekValue= (reqParams.get("week")==null?WebUtil.display(_ChurExpenseDefault.getWeek()):WebUtil.display((String)reqParams.get("week")));
    String _expense_item_idValue= (reqParams.get("expenseItemId")==null?WebUtil.display(_ChurExpenseDefault.getExpenseItemId()):WebUtil.display((String)reqParams.get("expenseItemId")));
    String _payee_idValue= (reqParams.get("payeeId")==null?WebUtil.display(_ChurExpenseDefault.getPayeeId()):WebUtil.display((String)reqParams.get("payeeId")));
    String _amountValue= (reqParams.get("amount")==null?WebUtil.display(_ChurExpenseDefault.getAmount()):WebUtil.display((String)reqParams.get("amount")));
    String _is_cashValue= (reqParams.get("isCash")==null?WebUtil.display(_ChurExpenseDefault.getIsCash()):WebUtil.display((String)reqParams.get("isCash")));
    String _check_numberValue= (reqParams.get("checkNumber")==null?WebUtil.display(_ChurExpenseDefault.getCheckNumber()):WebUtil.display((String)reqParams.get("checkNumber")));
    String _check_clearedValue= (reqParams.get("checkCleared")==null?WebUtil.display(_ChurExpenseDefault.getCheckCleared()):WebUtil.display((String)reqParams.get("checkCleared")));
    String _commentValue= (reqParams.get("comment")==null?WebUtil.display(_ChurExpenseDefault.getComment()):WebUtil.display((String)reqParams.get("comment")));
    String _cancelledValue= (reqParams.get("cancelled")==null?WebUtil.display(_ChurExpenseDefault.getCancelled()):WebUtil.display((String)reqParams.get("cancelled")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ChurExpenseDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ChurExpenseDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churExpenseForm_topArea" class="formTopArea"></div>
<div id="churExpenseForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churExpenseForm" method="POST" action="/churExpenseAction.html" >




	<div id="churExpenseForm_year_field">
    <div id="churExpenseForm_year_label" class="formLabel" >Year </div>
    <div id="churExpenseForm_year_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseForm_week_field">
    <div id="churExpenseForm_week_label" class="formLabel" >Week </div>
    <div id="churExpenseForm_week_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseForm_expenseItemId_field">
    <div id="churExpenseForm_expenseItemId_label" class="formLabel" >Expense Item Id </div>
    <div id="churExpenseForm_expenseItemId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="expenseItemId" value="<%=WebUtil.display(_expense_item_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseForm_payeeId_field">
    <div id="churExpenseForm_payeeId_label" class="formLabel" >Payee Id </div>
    <div id="churExpenseForm_payeeId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="payeeId" value="<%=WebUtil.display(_payee_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseForm_amount_field">
    <div id="churExpenseForm_amount_label" class="formLabel" >Amount </div>
    <div id="churExpenseForm_amount_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="amount" value="<%=WebUtil.display(_amountValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_isCash_field">
    <div id="churExpenseForm_isCash_label" class="formLabel" >Is Cash </div>
    <div id="churExpenseForm_isCash_dropdown" class="formFieldDropDown" >       
        <select name="isCash">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_cashValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_cashValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="churExpenseForm_checkNumber_field">
    <div id="churExpenseForm_checkNumber_label" class="formLabel" >Check Number </div>
    <div id="churExpenseForm_checkNumber_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="checkNumber" value="<%=WebUtil.display(_check_numberValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_checkCleared_field">
    <div id="churExpenseForm_checkCleared_label" class="formLabel" >Check Cleared </div>
    <div id="churExpenseForm_checkCleared_dropdown" class="formFieldDropDown" >       
        <select name="checkCleared">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _check_clearedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _check_clearedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="churExpenseForm_comment_field">
    <div id="churExpenseForm_comment_label" class="formLabel" >Comment </div>
    <div id="churExpenseForm_comment_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="comment" value="<%=WebUtil.display(_commentValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_cancelled_field">
    <div id="churExpenseForm_cancelled_label" class="formLabel" >Cancelled </div>
    <div id="churExpenseForm_cancelled_dropdown" class="formFieldDropDown" >       
        <select name="cancelled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _cancelledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _cancelledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>









	<div id="churExpenseForm_timeUpdated_field">
    <div id="churExpenseForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="churExpenseForm_timeUpdated_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churExpenseForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churExpenseForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="churExpenseForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurExpenseDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpense _ChurExpense = (ChurExpense) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurExpense.getId() %> </td>

    <td> <%= WebUtil.display(_ChurExpense.getYear()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getWeek()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getExpenseItemId()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getPayeeId()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getAmount()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getIsCash()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getCheckNumber()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getCheckCleared()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getComment()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getCancelled()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_ChurExpense.getTimeUpdated()) %></td>


<td>
<form name="churExpenseForm<%=_ChurExpense.getId()%>2" method="get" action="/v_chur_expense_edit2.html" >
    <a href="javascript:document.churExpenseForm<%=_ChurExpense.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurExpense.getId() %>">
</form>

</td>
<td>
<a href="/churExpenseAction.html?del=true&id=<%=_ChurExpense.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>