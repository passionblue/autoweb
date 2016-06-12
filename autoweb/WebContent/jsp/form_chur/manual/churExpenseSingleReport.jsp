<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ChurExpense _ChurExpense = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurExpense = ChurExpenseDS.getInstance().getById(id);
		if ( _ChurExpense == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurExpense = new ChurExpense();// ChurExpenseDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_home";

    String _yearValue= (reqParams.get("year")==null?WebUtil.display(_ChurExpense.getYear()):WebUtil.display((String)reqParams.get("year")));
    String _weekValue= (reqParams.get("week")==null?WebUtil.display(_ChurExpense.getWeek()):WebUtil.display((String)reqParams.get("week")));
    String _expense_item_idValue= (reqParams.get("expenseItemId")==null?WebUtil.display(_ChurExpense.getExpenseItemId()):WebUtil.display((String)reqParams.get("expenseItemId")));
    String _payee_idValue= (reqParams.get("payeeId")==null?WebUtil.display(_ChurExpense.getPayeeId()):WebUtil.display((String)reqParams.get("payeeId")));
    String _amountValue= (reqParams.get("amount")==null?WebUtil.display(_ChurExpense.getAmount()):WebUtil.display((String)reqParams.get("amount")));
    String _is_cashValue= (reqParams.get("isCash")==null?WebUtil.display(_ChurExpense.getIsCash()):WebUtil.display((String)reqParams.get("isCash")));
    String _check_numberValue= (reqParams.get("checkNumber")==null?WebUtil.display(_ChurExpense.getCheckNumber()):WebUtil.display((String)reqParams.get("checkNumber")));
    String _check_clearedValue= (reqParams.get("checkCleared")==null?WebUtil.display(_ChurExpense.getCheckCleared()):WebUtil.display((String)reqParams.get("checkCleared")));
    String _commentValue= (reqParams.get("comment")==null?WebUtil.display(_ChurExpense.getComment()):WebUtil.display((String)reqParams.get("comment")));
    String _cancelledValue= (reqParams.get("cancelled")==null?WebUtil.display(_ChurExpense.getCancelled()):WebUtil.display((String)reqParams.get("cancelled")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ChurExpense.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ChurExpense.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    long pagestamp = System.nanoTime();
    //>>
	String _selectedWeek = ChurWebUtil.getSelectedWeek(request );
	int _selectedYear = ChurWebUtil.getSelectedYear(request);
	//<<        
%> 

<br>
<div id="churExpenseForm" class="formFrame">
<div id="churExpenseFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churExpenseForm_Form" method="POST" action="/churExpenseAction.html" id="churExpenseForm_Form">





	<div id="churExpenseForm_year_field" class="formFieldFrame">
    <div id="churExpenseForm_year_label" class="formLabel" >Year </div>
    <div id="churExpenseForm_year_text" class="formFieldText" >       
        <input id="year" class="field" type="text" size="10" name="year" value="<%=WebUtil.display(_selectedYear)%>"/> <span></span>
    </div>      
	</div>




	<div id="churExpenseForm_week_field" class="formFieldFrame">
    <div id="churExpenseForm_year_label" class="formLabel" >Week </div>
    <div id="churExpenseForm_week_text" class="formFieldText" >       
        <input id="week" class="field" type="text" size="10" name="week" value="<%=WebUtil.display(_selectedWeek)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churExpenseForm_expenseItemId_field" class="formFieldFrame">
    <div id="churExpenseForm_expenseItemId_label" class="formLabel" >Expense Item Id </div>
    <div id="churExpenseForm_expenseItemId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="expenseItemId" id="expenseItemId">
        <option value="" >- Please Select -</option>
<%
	List _listChurExpenseItem_expenseItemId = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurExpenseItem_expenseItemId.iterator(); iter.hasNext();){
		ChurExpenseItem _obj = (ChurExpenseItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_expense_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churExpenseForm_payeeId_field" class="formFieldFrame">
    <div id="churExpenseForm_payeeId_label" class="formLabel" >Payee Id </div>
    <div id="churExpenseForm_payeeId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="payeeId" id="payeeId">
        <option value="" >- Please Select -</option>
<%
	List _listChurPayee_payeeId = ChurPayeeDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurPayee_payeeId.iterator(); iter.hasNext();){
		ChurPayee _obj = (ChurPayee) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_payee_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getTitle() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseForm_amount_field" class="formFieldFrame">
    <div id="churExpenseForm_amount_label" class="formLabel" >Amount </div>
    <div id="churExpenseForm_amount_text" class="formFieldText" >       
        <input id="amount" class="field" type="text" size="20" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churExpenseForm_checkNumber_field" class="formFieldFrame">
    <div id="churExpenseForm_checkNumber_label" class="formLabel" >Check Number </div>
    <div id="churExpenseForm_checkNumber_text" class="formFieldText" >       
        <input id="checkNumber" class="field" type="text" size="10" name="checkNumber" value="<%=WebUtil.display(_check_numberValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="churExpenseForm_isCash_field" class="formFieldFrame">
    <div id="churExpenseForm_isCash_label" class="formLabel" >Is Cash </div>
    <div id="churExpenseForm_isCash_dropdown" class="formFieldDropDown" >       
        <select name="isCash">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_cashValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_cashValue)%>>Yes</option>
        </select>
    </div>      
	</div>


	<div id="churExpenseForm_checkCleared_field" class="formFieldFrame">
    <div id="churExpenseForm_checkCleared_label" class="formLabel" >Check Cleared </div>
    <div id="churExpenseForm_checkCleared_dropdown" class="formFieldDropDown" >       
        <select name="checkCleared">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _check_clearedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _check_clearedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="churExpenseForm_comment_field" class="formFieldFrame">
    <div id="churExpenseForm_comment_label" class="formLabel" >Comment </div>
    <div id="churExpenseForm_comment_text" class="formFieldText" >       
        <input id="comment" class="field" type="text" size="70" name="comment" value="<%=WebUtil.display(_commentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseForm_cancelled_field" class="formFieldFrame">
    <div id="churExpenseForm_cancelled_label" class="formLabel" >Cancelled </div>
    <div id="churExpenseForm_cancelled_dropdown" class="formFieldDropDown" >       
        <select name="cancelled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _cancelledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _cancelledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>


	<div class="submitFrame">

        <div id="churExpenseForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churExpenseForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churExpenseForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churExpenseForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churExpenseForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churExpenseForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpense.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
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
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurExpenseDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpense _oChurExpense = (ChurExpense) iter.next();
%>

<TR id="tableRow<%= _oChurExpense.getId()%>">
    <td> <%= _oChurExpense.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurExpense.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurExpense.getYear()  %> </td>
	<td> <%= _oChurExpense.getWeek()  %> </td>
	<td> <%= _oChurExpense.getExpenseItemId()  %> </td>
	<td> <%= _oChurExpense.getPayeeId()  %> </td>
	<td> <%= _oChurExpense.getAmount()  %> </td>
	<td> <%= _oChurExpense.getIsCash()  %> </td>
	<td> <%= _oChurExpense.getCheckNumber()  %> </td>
	<td> <%= _oChurExpense.getCheckCleared()  %> </td>
	<td> <%= _oChurExpense.getComment()  %> </td>
	<td> <%= _oChurExpense.getCancelled()  %> </td>
	<td> <%= _oChurExpense.getTimeCreated()  %> </td>
	<td> <%= _oChurExpense.getTimeUpdated()  %> </td>
	<td> 
	<a href="javascript:sendFormAjaxSimple('/churExpenseAction.html?del=true&id=<%=_oChurExpense.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oChurExpense.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
	<td>
	<a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_expense_form('<%=_oChurExpense.getId()%>');">Edit</a>
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
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
	function edit_chur_expense_form(target){
		location.href='/v_chur_expense_form.html?cmd=edit&prv_returnPage=chur_expense_home&id=' + target;
	}
	
</script>