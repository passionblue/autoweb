<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    FormField _FormField = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_FormField = FormFieldDS.getInstance().getById(id);
		if ( _FormField == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _FormField = new FormField();// FormFieldDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "form_field_home";

    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_FormField.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _form_idValue= (reqParams.get("formId")==null?WebUtil.display(_FormField.getFormId()):WebUtil.display((String)reqParams.get("formId")));
    String _field_textValue= (reqParams.get("fieldText")==null?WebUtil.display(_FormField.getFieldText()):WebUtil.display((String)reqParams.get("fieldText")));
    String _field_typeValue= (reqParams.get("fieldType")==null?WebUtil.display(_FormField.getFieldType()):WebUtil.display((String)reqParams.get("fieldType")));
    String _requiredValue= (reqParams.get("required")==null?WebUtil.display(_FormField.getRequired()):WebUtil.display((String)reqParams.get("required")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_FormField.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
%> 

<br>
<div id="formFieldForm" class="formFrame">
<div id="formFieldFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="formFieldForm_Form" method="POST" action="/formFieldAction.html" id="formFieldForm_Form">






	<div id="formFieldForm_formId_field" class="formFieldFrame">
    <div id="formFieldForm_formId_label" class="formLabel" >Form Id </div>
    <div id="formFieldForm_formId_text" class="formFieldText" >       
        <input id="formId" class="field" type="text" size="70" name="formId" value="<%=WebUtil.display(_form_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="formFieldForm_fieldText_field" class="formFieldFrame">
    <div id="formFieldForm_fieldText_label" class="formRequiredLabel" >Field Text* </div>
    <div id="formFieldForm_fieldText_text" class="formFieldText" >       
        <input id="fieldText" class="requiredField" type="text" size="70" name="fieldText" value="<%=WebUtil.display(_field_textValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="formFieldForm_fieldType_field" class="formFieldFrame">
    <div id="formFieldForm_fieldType_label" class="formLabel" >Field Type </div>
    <div id="formFieldForm_fieldType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="fieldType" id="fieldType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _field_typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="formFieldForm_required_field" class="formFieldFrame">
    <div id="formFieldForm_required_label" class="formLabel" >Required </div>
    <div id="formFieldForm_required_dropdown" class="formFieldDropDown" >       
        <select name="required">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _requiredValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _requiredValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div class="submitFrame">

        <div id="formFieldForm_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.formFieldForm_Form.submit();">Submit</a>
        </div>      

        <div id="formFieldForm_submit_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="formFieldForm_submit_ext" class="formSubmitExt" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_FormField.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  User Id </td> 
    <td class="columnTitle">  Form Id </td> 
    <td class="columnTitle">  Field Text </td> 
    <td class="columnTitle">  Field Type </td> 
    <td class="columnTitle">  Required </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = FormFieldDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        FormField _oFormField = (FormField) iter.next();
%>

<TR>
    <td> <%= _oFormField.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oFormField.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oFormField.getUserId()  %> </td>
	<td> <%= _oFormField.getFormId()  %> </td>
	<td> <%= _oFormField.getFieldText()  %> </td>
	<td> <%= _oFormField.getFieldType()  %> </td>
	<td> <%= _oFormField.getRequired()  %> </td>
	<td> <%= _oFormField.getTimeCreated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/formFieldAction.html?del=true&id=<%=_oFormField.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>