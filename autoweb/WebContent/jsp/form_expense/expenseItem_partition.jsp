<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _expense_category_idValue= "";
    String _expense_itemValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_expense_item_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /ExpenseItem_artition.jsp -->

	<script type="text/javascript">
		function sendForm_expense_item_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">



	<div id="expenseItemForm_expenseCategoryId_field" class="formFieldFrame">
    <div id="expenseItemForm_expenseCategoryId_label" class="formLabel" >Expense Category Id </div>
    <div id="expenseItemForm_expenseCategoryId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="expenseCategoryId" id="expenseCategoryId">
        <option value="" >- Please Select -</option>
<%
	List _listExpenseCategory_expenseCategoryId = ExpenseCategoryDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listExpenseCategory_expenseCategoryId.iterator(); iter.hasNext();){
		ExpenseCategory _obj = (ExpenseCategory) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_expense_category_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseCategory() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="expenseItemForm_expenseItem_field" class="formFieldFrame">
    <div id="expenseItemForm_expenseItem_label" class="formLabel" >Expense Item </div>
    <div id="expenseItemForm_expenseItem_text" class="formFieldText" >       
        <input id="expenseItem" class="field" type="text" size="70" name="expenseItem" value="<%=WebUtil.display(_expense_itemValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_expense_item_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
