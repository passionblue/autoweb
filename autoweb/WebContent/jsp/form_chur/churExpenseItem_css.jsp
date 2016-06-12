<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ChurExpenseItem _ChurExpenseItem = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurExpenseItem = ChurExpenseItemDS.getInstance().getById(id);
		if ( _ChurExpenseItem == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurExpenseItem = new ChurExpenseItem();// ChurExpenseItemDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_item_home";

    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_ChurExpenseItem.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _expense_itemValue= (reqParams.get("expenseItem")==null?WebUtil.display(_ChurExpenseItem.getExpenseItem()):WebUtil.display((String)reqParams.get("expenseItem")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurExpenseItem.getDisplay()):WebUtil.display((String)reqParams.get("display")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churExpenseItemForm" class="formFrame">
<div id="churExpenseItemFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churExpenseItemForm_Form" method="POST" action="/churExpenseItemAction.html" id="churExpenseItemForm_Form">



	<div id="churExpenseItemForm_categoryId_field" class="formFieldFrame">
    <div id="churExpenseItemForm_categoryId_label" class="formLabel" >Category Id </div>
    <div id="churExpenseItemForm_categoryId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="categoryId" id="categoryId">
        <option value="" >- Please Select -</option>
<%
	List _listChurExpenseCategory_categoryId = ChurExpenseCategoryDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurExpenseCategory_categoryId.iterator(); iter.hasNext();){
		ChurExpenseCategory _obj = (ChurExpenseCategory) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_category_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseCategory() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseItemForm_expenseItem_field" class="formFieldFrame">
    <div id="churExpenseItemForm_expenseItem_label" class="formLabel" >Expense Item </div>
    <div id="churExpenseItemForm_expenseItem_text" class="formFieldText" >       
        <input id="expenseItem" class="field" type="text" size="70" name="expenseItem" value="<%=WebUtil.display(_expense_itemValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseItemForm_display_field" class="formFieldFrame">
    <div id="churExpenseItemForm_display_label" class="formLabel" >Display </div>
    <div id="churExpenseItemForm_display_text" class="formFieldText" >       
        <input id="display" class="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div class="submitFrame">

        <div id="churExpenseItemForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churExpenseItemForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churExpenseItemForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churExpenseItemForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churExpenseItemForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churExpenseItemForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpenseItem.getId()%>">

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
    <td class="columnTitle">  Category Id </td> 
    <td class="columnTitle">  Expense Item </td> 
    <td class="columnTitle">  Display </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpenseItem _oChurExpenseItem = (ChurExpenseItem) iter.next();
%>

<TR>
    <td> <%= _oChurExpenseItem.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurExpenseItem.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurExpenseItem.getCategoryId()  %> </td>
	<td> <%= _oChurExpenseItem.getExpenseItem()  %> </td>
	<td> <%= _oChurExpenseItem.getDisplay()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churExpenseItemAction.html?del=true&id=<%=_oChurExpenseItem.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
</script>