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

    ChurIncomeItem _ChurIncomeItem = ChurIncomeItemDS.getInstance().getById(id);

    if ( _ChurIncomeItem == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_item_home";

    String _category_idValue=  WebUtil.display(_ChurIncomeItem.getCategoryId());
    String _income_itemValue=  WebUtil.display(_ChurIncomeItem.getIncomeItem());
    String _displayValue=  WebUtil.display(_ChurIncomeItem.getDisplay());
%> 

<br>
<div id="churIncomeItemForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeItemFormEdit" method="POST" action="/churIncomeItemAction.html" >




	<div id="churIncomeItemForm_categoryId_field">
    <div id="churIncomeItemForm_categoryId_label" class="formLabel" >Category Id </div>
    <div id="churIncomeItemForm_categoryId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeItemForm_incomeItem_field">
    <div id="churIncomeItemForm_incomeItem_label" class="formLabel" >Income Item </div>
    <div id="churIncomeItemForm_incomeItem_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="incomeItem" value="<%=WebUtil.display(_income_itemValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeItemForm_display_field">
    <div id="churIncomeItemForm_display_label" class="formLabel" >Display </div>
    <div id="churIncomeItemForm_display_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeItemFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeItemFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeItem.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
