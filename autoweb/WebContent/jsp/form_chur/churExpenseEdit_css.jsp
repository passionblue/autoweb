<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    ChurExpense _ChurExpense = ChurExpenseDS.getInstance().getById(id);

    if ( _ChurExpense == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_home";

    String _yearValue=  WebUtil.display(_ChurExpense.getYear());
    String _weekValue=  WebUtil.display(_ChurExpense.getWeek());
    String _expense_item_idValue=  WebUtil.display(_ChurExpense.getExpenseItemId());
    String _payee_idValue=  WebUtil.display(_ChurExpense.getPayeeId());
    String _amountValue=  WebUtil.display(_ChurExpense.getAmount());
    String _is_cashValue=  WebUtil.display(_ChurExpense.getIsCash());
    String _check_numberValue=  WebUtil.display(_ChurExpense.getCheckNumber());
    String _check_clearedValue=  WebUtil.display(_ChurExpense.getCheckCleared());
    String _commentValue=  WebUtil.display(_ChurExpense.getComment());
    String _cancelledValue=  WebUtil.display(_ChurExpense.getCancelled());
    String _time_createdValue=  WebUtil.display(_ChurExpense.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_ChurExpense.getTimeUpdated());
%> 

<br>
<div id="churExpenseForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churExpenseFormEdit" method="POST" action="/churExpenseAction.html" >




	<div id="churExpenseForm_year_field">
    <div id="churExpenseForm_year_label" class="formLabel" >Year </div>
    <div id="churExpenseForm_year_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_week_field">
    <div id="churExpenseForm_week_label" class="formLabel" >Week </div>
    <div id="churExpenseForm_week_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_expenseItemId_field">
    <div id="churExpenseForm_expenseItemId_label" class="formLabel" >Expense Item Id </div>
    <div id="churExpenseForm_expenseItemId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="expenseItemId" value="<%=WebUtil.display(_expense_item_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_payeeId_field">
    <div id="churExpenseForm_payeeId_label" class="formLabel" >Payee Id </div>
    <div id="churExpenseForm_payeeId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="payeeId" value="<%=WebUtil.display(_payee_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_amount_field">
    <div id="churExpenseForm_amount_label" class="formLabel" >Amount </div>
    <div id="churExpenseForm_amount_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
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
    <div id="churExpenseForm_checkNumber_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="checkNumber" value="<%=WebUtil.display(_check_numberValue)%>"/> <span></span>
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
    <div id="churExpenseForm_comment_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="comment" value="<%=WebUtil.display(_commentValue)%>"/> <span></span>
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
    <div id="churExpenseForm_timeUpdated_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churExpenseFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churExpenseFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpense.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
