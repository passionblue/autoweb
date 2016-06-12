<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
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

    FormField _FormField = FormFieldDS.getInstance().getById(id);

    if ( _FormField == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "form_field_home";

    String _user_idValue=  WebUtil.display(_FormField.getUserId());
    String _form_idValue=  WebUtil.display(_FormField.getFormId());
    String _field_textValue=  WebUtil.display(_FormField.getFieldText());
    String _field_typeValue=  WebUtil.display(_FormField.getFieldType());
    String _requiredValue=  WebUtil.display(_FormField.getRequired());
    String _time_createdValue=  WebUtil.display(_FormField.getTimeCreated());
%> 

<br>
<div id="formFieldForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="formFieldFormEdit" method="POST" action="/formFieldAction.html" >




	<div id="formFieldForm_formId_field">
    <div id="formFieldForm_formId_label" class="formLabel" >Form Id </div>
    <div id="formFieldForm_formId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="formId" value="<%=WebUtil.display(_form_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="formFieldForm_fieldText_field">
    <div id="formFieldForm_fieldText_label" class="formRequiredLabel" >Field Text* </div>
    <div id="formFieldForm_fieldText_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="fieldText" value="<%=WebUtil.display(_field_textValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="formFieldForm_fieldType_field">
    <div id="formFieldForm_fieldType_label" class="formLabel" >Field Type </div>
    <div id="formFieldForm_fieldType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="fieldType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _field_typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="formFieldForm_required_field">
    <div id="formFieldForm_required_label" class="formLabel" >Required </div>
    <div id="formFieldForm_required_dropdown" class="formFieldDropDown" >       
        <select name="required">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _requiredValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _requiredValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





        <div id="formFieldFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.formFieldFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_FormField.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
