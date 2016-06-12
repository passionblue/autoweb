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

    GenSimpleDataHolder _GenSimple = GenSimpleDS.getInstance().getById(id);

    if ( _GenSimple == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_simple_home";

    String _dataValue=  WebUtil.display(_GenSimple.getData());
    String _activeValue=  WebUtil.display(_GenSimple.getActive());
    String _ext_stringValue=  WebUtil.display(_GenSimple.getExtString());
    String _ext_intValue=  WebUtil.display(_GenSimple.getExtInt());
%> 

<br>
<div id="genSimpleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genSimpleFormEdit" method="POST" action="/genSimpleAction.html" >




	<div id="genSimpleForm_data_field">
    <div id="genSimpleForm_data_label" class="formLabel" >Data </div>
    <div id="genSimpleForm_data_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genSimpleForm_active_field">
    <div id="genSimpleForm_active_label" class="formLabel" >Active </div>
    <div id="genSimpleForm_active_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genSimpleForm_extString_field">
    <div id="genSimpleForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genSimpleForm_extString_text" class="formFieldText" >       
        <input id="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genSimpleForm_extInt_field">
    <div id="genSimpleForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genSimpleForm_extInt_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="genSimpleFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genSimpleFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenSimple.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
