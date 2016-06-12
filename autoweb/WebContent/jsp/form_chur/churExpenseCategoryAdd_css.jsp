<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurExpenseCategory _ChurExpenseCategoryDefault = new ChurExpenseCategory();// ChurExpenseCategoryDS.getInstance().getDeafult();
    
    String _expense_categoryValue= (reqParams.get("expenseCategory")==null?WebUtil.display(_ChurExpenseCategoryDefault.getExpenseCategory()):WebUtil.display((String)reqParams.get("expenseCategory")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurExpenseCategoryDefault.getDisplay()):WebUtil.display((String)reqParams.get("display")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_category_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churExpenseCategoryForm_topArea" class="formTopArea"></div>
<div id="churExpenseCategoryForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churExpenseCategoryForm" method="POST" action="/churExpenseCategoryAction.html" >




	<div id="churExpenseCategoryForm_expenseCategory_field">
    <div id="churExpenseCategoryForm_expenseCategory_label" class="formLabel" >Expense Category </div>
    <div id="churExpenseCategoryForm_expenseCategory_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="expenseCategory" value="<%=WebUtil.display(_expense_categoryValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseCategoryForm_display_field">
    <div id="churExpenseCategoryForm_display_label" class="formLabel" >Display </div>
    <div id="churExpenseCategoryForm_display_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churExpenseCategoryForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churExpenseCategoryForm.submit();">Submit</a>
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
<div id="churExpenseCategoryForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurExpenseCategoryDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpenseCategory _ChurExpenseCategory = (ChurExpenseCategory) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurExpenseCategory.getId() %> </td>

    <td> <%= WebUtil.display(_ChurExpenseCategory.getExpenseCategory()) %></td>
    <td> <%= WebUtil.display(_ChurExpenseCategory.getDisplay()) %></td>


<td>
<form name="churExpenseCategoryForm<%=_ChurExpenseCategory.getId()%>2" method="get" action="/v_chur_expense_category_edit2.html" >
    <a href="javascript:document.churExpenseCategoryForm<%=_ChurExpenseCategory.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurExpenseCategory.getId() %>">
</form>

</td>
<td>
<a href="/churExpenseCategoryAction.html?del=true&id=<%=_ChurExpenseCategory.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>