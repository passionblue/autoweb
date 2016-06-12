<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
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

    ChurExpenseCategory _ChurExpenseCategory = ChurExpenseCategoryDS.getInstance().getById(id);

    if ( _ChurExpenseCategory == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_category_home";

    String _expense_categoryValue=  WebUtil.display(_ChurExpenseCategory.getExpenseCategory());
    String _displayValue=  WebUtil.display(_ChurExpenseCategory.getDisplay());
%> 

<br>
<div id="churExpenseCategoryForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churExpenseCategoryFormEdit" method="POST" action="/churExpenseCategoryAction.html" >




	<div id="churExpenseCategoryForm_expenseCategory_field">
    <div id="churExpenseCategoryForm_expenseCategory_label" class="formLabel" >Expense Category </div>
    <div id="churExpenseCategoryForm_expenseCategory_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="expenseCategory" value="<%=WebUtil.display(_expense_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseCategoryForm_display_field">
    <div id="churExpenseCategoryForm_display_label" class="formLabel" >Display </div>
    <div id="churExpenseCategoryForm_display_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churExpenseCategoryFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churExpenseCategoryFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpenseCategory.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
