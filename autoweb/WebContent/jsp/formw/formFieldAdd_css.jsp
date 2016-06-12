<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    FormField _FormFieldDefault = new FormField();// FormFieldDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_FormFieldDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _form_idValue= (reqParams.get("formId")==null?WebUtil.display(_FormFieldDefault.getFormId()):WebUtil.display((String)reqParams.get("formId")));
    String _field_textValue= (reqParams.get("fieldText")==null?WebUtil.display(_FormFieldDefault.getFieldText()):WebUtil.display((String)reqParams.get("fieldText")));
    String _field_typeValue= (reqParams.get("fieldType")==null?WebUtil.display(_FormFieldDefault.getFieldType()):WebUtil.display((String)reqParams.get("fieldType")));
    String _requiredValue= (reqParams.get("required")==null?WebUtil.display(_FormFieldDefault.getRequired()):WebUtil.display((String)reqParams.get("required")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_FormFieldDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "form_field_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="formFieldForm_topArea" class="formTopArea"></div>
<div id="formFieldForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="formFieldForm" method="POST" action="/formFieldAction.html" >






	<div id="formFieldForm_formId_field">
    <div id="formFieldForm_formId_label" class="formLabel" >Form Id </div>
    <div id="formFieldForm_formId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="formId" value="<%=WebUtil.display(_form_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="formFieldForm_fieldText_field">
    <div id="formFieldForm_fieldText_label" class="formRequiredLabel" >Field Text* </div>
    <div id="formFieldForm_fieldText_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="fieldText" value="<%=WebUtil.display(_field_textValue)%>"/> 
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






        <div id="formFieldForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.formFieldForm.submit();">Submit</a>
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
<div id="formFieldForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = FormFieldDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        FormField _FormField = (FormField) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _FormField.getId() %> </td>

    <td> <%= WebUtil.display(_FormField.getUserId()) %></td>
    <td> <%= WebUtil.display(_FormField.getFormId()) %></td>
    <td> <%= WebUtil.display(_FormField.getFieldText()) %></td>
    <td> <%= WebUtil.display(_FormField.getFieldType()) %></td>
    <td> <%= WebUtil.display(_FormField.getRequired()) %></td>
    <td> <%= WebUtil.display(_FormField.getTimeCreated()) %></td>


<td>
<form name="formFieldForm<%=_FormField.getId()%>2" method="get" action="/v_form_field_edit2.html" >
    <a href="javascript:document.formFieldForm<%=_FormField.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _FormField.getId() %>">
</form>

</td>
<td>
<a href="/formFieldAction.html?del=true&id=<%=_FormField.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>