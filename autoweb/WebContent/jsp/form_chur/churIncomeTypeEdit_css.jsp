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

    ChurIncomeType _ChurIncomeType = ChurIncomeTypeDS.getInstance().getById(id);

    if ( _ChurIncomeType == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_type_home";

    String _income_typeValue=  WebUtil.display(_ChurIncomeType.getIncomeType());
%> 

<br>
<div id="churIncomeTypeForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeTypeFormEdit" method="POST" action="/churIncomeTypeAction.html" >




	<div id="churIncomeTypeForm_incomeType_field">
    <div id="churIncomeTypeForm_incomeType_label" class="formLabel" >Income Type </div>
    <div id="churIncomeTypeForm_incomeType_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="incomeType" value="<%=WebUtil.display(_income_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeTypeFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeTypeFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeType.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
