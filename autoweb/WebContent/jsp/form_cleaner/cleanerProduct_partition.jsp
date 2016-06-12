<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _garment_type_idValue= "";
    String _garment_service_idValue= "";
    String _regular_priceValue= "";
    String _noteValue= "";

%>

<div id="partitionFormFrame_cleaner_product_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerProduct_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_product_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerProductForm_garmentTypeId_field" class="formFieldFrame">
    <div id="cleanerProductForm_garmentTypeId_label" class="formLabel" >Garment Type Id </div>
    <div id="cleanerProductForm_garmentTypeId_text" class="formFieldText" >       
        <input id="garmentTypeId" class="field" type="text" size="70" name="garmentTypeId" value="<%=WebUtil.display(_garment_type_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerProductForm_garmentServiceId_field" class="formFieldFrame">
    <div id="cleanerProductForm_garmentServiceId_label" class="formLabel" >Garment Service Id </div>
    <div id="cleanerProductForm_garmentServiceId_text" class="formFieldText" >       
        <input id="garmentServiceId" class="field" type="text" size="70" name="garmentServiceId" value="<%=WebUtil.display(_garment_service_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerProductForm_regularPrice_field" class="formFieldFrame">
    <div id="cleanerProductForm_regularPrice_label" class="formLabel" >Regular Price </div>
    <div id="cleanerProductForm_regularPrice_text" class="formFieldText" >       
        <input id="regularPrice" class="field" type="text" size="70" name="regularPrice" value="<%=WebUtil.display(_regular_priceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerProductForm_note_field" class="formFieldFrame">
    <div id="cleanerProductForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerProductForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_product_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
