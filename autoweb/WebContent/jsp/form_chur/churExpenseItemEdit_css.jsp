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

    ChurExpenseItem _ChurExpenseItem = ChurExpenseItemDS.getInstance().getById(id);

    if ( _ChurExpenseItem == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_item_home";

    String _category_idValue=  WebUtil.display(_ChurExpenseItem.getCategoryId());
    String _expense_itemValue=  WebUtil.display(_ChurExpenseItem.getExpenseItem());
    String _displayValue=  WebUtil.display(_ChurExpenseItem.getDisplay());
%> 

<br>
<div id="churExpenseItemForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churExpenseItemFormEdit" method="POST" action="/churExpenseItemAction.html" >




	<div id="churExpenseItemForm_categoryId_field">
    <div id="churExpenseItemForm_categoryId_label" class="formLabel" >Category Id </div>
    <div id="churExpenseItemForm_categoryId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseItemForm_expenseItem_field">
    <div id="churExpenseItemForm_expenseItem_label" class="formLabel" >Expense Item </div>
    <div id="churExpenseItemForm_expenseItem_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="expenseItem" value="<%=WebUtil.display(_expense_itemValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churExpenseItemForm_display_field">
    <div id="churExpenseItemForm_display_label" class="formLabel" >Display </div>
    <div id="churExpenseItemForm_display_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churExpenseItemFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churExpenseItemFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurExpenseItem.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
