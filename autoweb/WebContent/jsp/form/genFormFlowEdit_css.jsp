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

    GenFormFlowDataHolder _GenFormFlow = GenFormFlowDS.getInstance().getById(id);

    if ( _GenFormFlow == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_form_flow_home";

    String _ext_stringValue=  WebUtil.display(_GenFormFlow.getExtString());
    String _ext_intValue=  WebUtil.display(_GenFormFlow.getExtInt());
%> 

<br>
<div id="genFormFlowForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genFormFlowFormEdit" method="POST" action="/genFormFlowAction.html" >




	<div id="genFormFlowForm_extString_field">
    <div id="genFormFlowForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genFormFlowForm_extString_text" class="formFieldText" >       
        <input id="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genFormFlowForm_extInt_field">
    <div id="genFormFlowForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genFormFlowForm_extInt_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="genFormFlowFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genFormFlowFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenFormFlow.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
