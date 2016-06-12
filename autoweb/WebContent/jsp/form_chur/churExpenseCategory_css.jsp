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
    ChurExpenseCategory _ChurExpenseCategory = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurExpenseCategory = ChurExpenseCategoryDS.getInstance().getById(id);
		if ( _ChurExpenseCategory == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurExpenseCategory = new ChurExpenseCategory();// ChurExpenseCategoryDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_category_home";

    String _expense_categoryValue= (reqParams.get("expenseCategory")==null?WebUtil.display(_ChurExpenseCategory.getExpenseCategory()):WebUtil.display((String)reqParams.get("expenseCategory")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurExpenseCategory.getDisplay()):WebUtil.display((String)reqParams.get("display")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churExpenseCategoryForm" class="formFrame">
<div id="churExpenseCategoryFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churExpenseCategoryForm_Form" method="POST" action="/churExpenseCategoryAction.html" id="churExpenseCategoryForm_Form">





	<div id="churExpenseCategoryForm_expenseCategory_field" class="formFieldFrame">
    <div id="churExpenseCategoryForm_expenseCategory_label" class="formLabel" >Expense Category </div>
    <div id="churExpenseCategoryForm_expenseCategory_text" class="formFieldText" >       
        <input id="expenseCategory" class="field" type="text" size="70" name="expenseCategory" value="<%=WebUtil.display(_expense_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseCategoryForm_display_field" class="formFieldFrame">
    <div id="churExpenseCategoryForm_display_label" class="formLabel" >Display </div>
    <div id="churExpenseCategoryForm_display_text" class="formFieldText" >       
        <input id="display" class="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div class="submitFrame">

        <div id="churExpenseCategoryForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churExpenseCategoryForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churExpenseCategoryForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churExpenseCategoryForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churExpenseCategoryForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churExpenseCategoryForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpenseCategory.getId()%>">

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
    <td class="columnTitle">  Expense Category </td> 
    <td class="columnTitle">  Display </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurExpenseCategoryDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpenseCategory _oChurExpenseCategory = (ChurExpenseCategory) iter.next();
%>

<TR>
    <td> <%= _oChurExpenseCategory.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurExpenseCategory.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurExpenseCategory.getExpenseCategory()  %> </td>
	<td> <%= _oChurExpenseCategory.getDisplay()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churExpenseCategoryAction.html?del=true&id=<%=_oChurExpenseCategory.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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