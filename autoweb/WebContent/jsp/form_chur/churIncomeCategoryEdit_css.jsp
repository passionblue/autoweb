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

    ChurIncomeCategory _ChurIncomeCategory = ChurIncomeCategoryDS.getInstance().getById(id);

    if ( _ChurIncomeCategory == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_category_home";

    String _income_categoryValue=  WebUtil.display(_ChurIncomeCategory.getIncomeCategory());
    String _displayValue=  WebUtil.display(_ChurIncomeCategory.getDisplay());
    String _time_createdValue=  WebUtil.display(_ChurIncomeCategory.getTimeCreated());
%> 

<br>
<div id="churIncomeCategoryForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeCategoryFormEdit" method="POST" action="/churIncomeCategoryAction.html" >




	<div id="churIncomeCategoryForm_incomeCategory_field">
    <div id="churIncomeCategoryForm_incomeCategory_label" class="formLabel" >Income Category </div>
    <div id="churIncomeCategoryForm_incomeCategory_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="incomeCategory" value="<%=WebUtil.display(_income_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeCategoryForm_display_field">
    <div id="churIncomeCategoryForm_display_label" class="formLabel" >Display </div>
    <div id="churIncomeCategoryForm_display_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="churIncomeCategoryFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeCategoryFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeCategory.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
