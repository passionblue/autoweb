<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurExpenseItem _ChurExpenseItemDefault = new ChurExpenseItem();// ChurExpenseItemDS.getInstance().getDeafult();
    
    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_ChurExpenseItemDefault.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _expense_itemValue= (reqParams.get("expenseItem")==null?WebUtil.display(_ChurExpenseItemDefault.getExpenseItem()):WebUtil.display((String)reqParams.get("expenseItem")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurExpenseItemDefault.getDisplay()):WebUtil.display((String)reqParams.get("display")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_item_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churExpenseItemForm_topArea" class="formTopArea"></div>
<div id="churExpenseItemForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churExpenseItemForm" method="POST" action="/churExpenseItemAction.html" >




	<div id="churExpenseItemForm_categoryId_field">
    <div id="churExpenseItemForm_categoryId_label" class="formLabel" >Category Id </div>
    <div id="churExpenseItemForm_categoryId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseItemForm_expenseItem_field">
    <div id="churExpenseItemForm_expenseItem_label" class="formLabel" >Expense Item </div>
    <div id="churExpenseItemForm_expenseItem_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="expenseItem" value="<%=WebUtil.display(_expense_itemValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churExpenseItemForm_display_field">
    <div id="churExpenseItemForm_display_label" class="formLabel" >Display </div>
    <div id="churExpenseItemForm_display_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churExpenseItemForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churExpenseItemForm.submit();">Submit</a>
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
<div id="churExpenseItemForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurExpenseItem _ChurExpenseItem = (ChurExpenseItem) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurExpenseItem.getId() %> </td>

    <td> <%= WebUtil.display(_ChurExpenseItem.getCategoryId()) %></td>
    <td> <%= WebUtil.display(_ChurExpenseItem.getExpenseItem()) %></td>
    <td> <%= WebUtil.display(_ChurExpenseItem.getDisplay()) %></td>


<td>
<form name="churExpenseItemForm<%=_ChurExpenseItem.getId()%>2" method="get" action="/v_chur_expense_item_edit2.html" >
    <a href="javascript:document.churExpenseItemForm<%=_ChurExpenseItem.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurExpenseItem.getId() %>">
</form>

</td>
<td>
<a href="/churExpenseItemAction.html?del=true&id=<%=_ChurExpenseItem.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>