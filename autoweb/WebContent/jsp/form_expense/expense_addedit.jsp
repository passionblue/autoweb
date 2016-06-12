<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
/* 
Template last modification history:


Source Generated: Sun Jul 12 20:40:22 EDT 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

	// This is ugrly not matured change. Just added to load previously entered values and put back to the fields. 
	boolean isEdit = false;	
    Map reqParams = (Map) request.getAttribute("k_previous_request_params");
    if ( reqParams == null) {
        reqParams = (Map) request.getAttribute("k_reserved_params");
    } else {
        isEdit = true;
    }

	String command = request.getParameter("cmd");

    String idStr  = "0";
    Expense _Expense = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_Expense = ExpenseDS.getInstance().getById(id);
		if ( _Expense == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _Expense = new Expense();// ExpenseDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _Expense == null) _Expense = new Expense();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "expense_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _expense_item_idValue= (reqParams.get("expenseItemId")==null?WebUtil.display(_Expense.getExpenseItemId()):WebUtil.display((String)reqParams.get("expenseItemId")));
    String _amountValue= (reqParams.get("amount")==null?WebUtil.display(_Expense.getAmount()):WebUtil.display((String)reqParams.get("amount")));
    String _descriptionValue= (reqParams.get("description")==null?WebUtil.display(_Expense.getDescription()):WebUtil.display((String)reqParams.get("description")));
    String _pay_methodValue= (reqParams.get("payMethod")==null?WebUtil.display(_Expense.getPayMethod()):WebUtil.display((String)reqParams.get("payMethod")));
    String _not_expenseValue= (reqParams.get("notExpense")==null?WebUtil.display(_Expense.getNotExpense()):WebUtil.display((String)reqParams.get("notExpense")));
    String _referenceValue= (reqParams.get("reference")==null?WebUtil.display(_Expense.getReference()):WebUtil.display((String)reqParams.get("reference")));
    String _date_expenseValue= (reqParams.get("dateExpense")==null?WebUtil.display(_Expense.getDateExpense()):WebUtil.display((String)reqParams.get("dateExpense")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_Expense.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_Expense.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "expense_" + System.nanoTime();
%> 

<br>
<div id="expenseForm" class="formFrame">
<div id="expenseFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="expenseForm_Form" method="POST" action="/expenseAction.html" id="expenseForm_Form">



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







<div class="submitFrame">

    <div id="expenseForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.expenseForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="expenseForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="expenseForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="expenseForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="expenseForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="expenseForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="expenseForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Expense.getId()%>">

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
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

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
		   		url: "/expenseAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_back_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
</script>
