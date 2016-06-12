<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _nameValue= "";
    String _includeValue= "";
    String _resource_typeValue= "";

%>

<div id="partitionFormFrame_resource_inclusion_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /ResourceInclusion_artition.jsp -->

	<script type="text/javascript">
		function sendForm_resource_inclusion_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="resourceInclusionForm_name_field" class="formFieldFrame">
    <div id="resourceInclusionForm_name_label" class="formLabel" >Name </div>
    <div id="resourceInclusionForm_name_text" class="formFieldText" >       
        <input id="name" class="field" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="resourceInclusionForm_include_field" class="formFieldFrame">
    <div id="resourceInclusionForm_include_label" class="formLabel" >Include </div>
    <div id="resourceInclusionForm_include_dropdown" class="formFieldDropDown" >       
        <select name="include">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _includeValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _includeValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



  	<div id="resourceInclusionForm_resourceType_field" class="formFieldFrame">
    <div id="resourceInclusionForm_resourceType_label" class="formLabel" >Resource Type </div>
    <div id="resourceInclusionForm_resourceType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="resourceType" id="resourceType">
        <option value="" >- Please Select -</option>
<%
	List dropMenusResourceType = DropMenuUtil.getDropMenus("ResourceInclusionType");
	for(Iterator iterItems = dropMenusResourceType.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _resource_typeValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_resource_inclusion_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
