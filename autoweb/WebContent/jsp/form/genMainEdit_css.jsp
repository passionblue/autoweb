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

    GenMain _GenMain = GenMainDS.getInstance().getById(id);

    if ( _GenMain == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_main_home";

    String _activeValue=  WebUtil.display(_GenMain.getActive());
    String _valueValue=  WebUtil.display(_GenMain.getValue());
    String _dataValue=  WebUtil.display(_GenMain.getData());
    String _requiredValue=  WebUtil.display(_GenMain.getRequired());
    String _time_createdValue=  WebUtil.display(_GenMain.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_GenMain.getTimeUpdated());
%> 

<br>
<div id="genMainForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genMainFormEdit" method="POST" action="/genMainAction.html" >




	<div id="genMainForm_active_field">
    <div id="genMainForm_active_label" class="formLabel" >Active </div>
    <div id="genMainForm_active_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genMainForm_value_field">
    <div id="genMainForm_value_label" class="formLabel" >Value </div>
    <div id="genMainForm_value_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genMainForm_data_field">
    <div id="genMainForm_data_label" class="formLabel" >Data </div>
    <div id="genMainForm_data_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genMainForm_required_field">
    <div id="genMainForm_required_label" class="formLabel" >Required </div>
    <div id="genMainForm_required_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="required" value="<%=WebUtil.display(_requiredValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="genMainForm_timeUpdated_field">
    <div id="genMainForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="genMainForm_timeUpdated_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="genMainFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genMainFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenMain.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
