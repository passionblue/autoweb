<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _expense_item_idValue= "";
    String _amountValue= "";
    String _descriptionValue= "";
    String _pay_methodValue= "";
    String _not_expenseValue= "";
    String _referenceValue= "";
    String _date_expenseValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_expense_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /Expense_artition.jsp -->

	<script type="text/javascript">
		function sendForm_expense_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">



	<div id="expenseForm_expenseItemId_field" class="formFieldFrame">
    <div id="expenseForm_expenseItemId_label" class="formLabel" >Expense Item Id </div>
    <div id="expenseForm_expenseItemId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="expenseItemId" id="expenseItemId">
        <option value="" >- Please Select -</option>
<%
	List _listExpenseItem_expenseItemId = ExpenseItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listExpenseItem_expenseItemId.iterator(); iter.hasNext();){
		ExpenseItem _obj = (ExpenseItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_expense_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="expenseForm_amount_field" class="formFieldFrame">
    <div id="expenseForm_amount_label" class="formLabel" >Amount </div>
    <div id="expenseForm_amount_text" class="formFieldText" >       
        <input id="amount" class="field" type="text" size="70" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="expenseForm_description_field" class="formFieldFrame">
    <div id="expenseForm_description_label" class="formLabel" >Description </div>
    <div id="expenseForm_description_text" class="formFieldText" >       
        <input id="description" class="field" type="text" size="70" name="description" value="<%=WebUtil.display(_descriptionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="expenseForm_payMethod_field" class="formFieldFrame">
    <div id="expenseForm_payMethod_label" class="formLabel" >Pay Method </div>
    <div id="expenseForm_payMethod_dropdown" class="formFieldDropDown" >       
        <select class="field" name="payMethod" id="payMethod">
        <option value="" >- Please Select -</option>
<%
	List dropMenusPayMethod = DropMenuUtil.getDropMenus("ExpensePayMethod");
	for(Iterator iterItems = dropMenusPayMethod.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pay_methodValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="expenseForm_notExpense_field" class="formFieldFrame">
    <div id="expenseForm_notExpense_label" class="formLabel" >Not Expense </div>
    <div id="expenseForm_notExpense_dropdown" class="formFieldDropDown" >       
        <select name="notExpense">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _not_expenseValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _not_expenseValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="expenseForm_reference_field" class="formFieldFrame">
    <div id="expenseForm_reference_label" class="formLabel" >Reference </div>
    <div id="expenseForm_reference_text" class="formFieldText" >       
        <input id="reference" class="field" type="text" size="70" name="reference" value="<%=WebUtil.display(_referenceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="expenseForm_dateExpense_field" class="formFieldFrame">
    <div id="expenseForm_dateExpense_label" class="formLabel" >Date Expense </div>
    <div id="expenseForm_dateExpense_text" class="formFieldText" >       
        <input id="dateExpense" class="field" type="text" size="70" name="dateExpense" value="<%=WebUtil.display(_date_expenseValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>






		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_expense_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
