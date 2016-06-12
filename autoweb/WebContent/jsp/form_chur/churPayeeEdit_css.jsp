<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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

    ChurPayee _ChurPayee = ChurPayeeDS.getInstance().getById(id);

    if ( _ChurPayee == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_payee_home";

    String _titleValue=  WebUtil.display(_ChurPayee.getTitle());
    String _remarkValue=  WebUtil.display(_ChurPayee.getRemark());
%> 

<br>
<div id="churPayeeForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churPayeeFormEdit" method="POST" action="/churPayeeAction.html" >




	<div id="churPayeeForm_title_field">
    <div id="churPayeeForm_title_label" class="formLabel" >Title </div>
    <div id="churPayeeForm_title_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churPayeeForm_remark_field">
    <div id="churPayeeForm_remark_label" class="formLabel" >Remark </div>
    <div id="churPayeeForm_remark_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="remark" value="<%=WebUtil.display(_remarkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churPayeeFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churPayeeFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurPayee.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
