<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _service_idValue= "";
    String _service_item_idValue= "";
    String _item_typeValue= "";
    String _titleValue= "";
    String _image_pathValue= "";
    String _image_path_localValue= "";
    String _base_priceValue= "";
    String _noteValue= "";

%>

<div id="partitionFormFrame_cleaner_service_item_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerServiceItem_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_service_item_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerServiceItemForm_serviceId_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_serviceId_label" class="formLabel" >Service Id </div>
    <div id="cleanerServiceItemForm_serviceId_text" class="formFieldText" >       
        <input id="serviceId" class="field" type="text" size="70" name="serviceId" value="<%=WebUtil.display(_service_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_serviceItemId_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_serviceItemId_label" class="formLabel" >Service Item Id </div>
    <div id="cleanerServiceItemForm_serviceItemId_text" class="formFieldText" >       
        <input id="serviceItemId" class="field" type="text" size="70" name="serviceItemId" value="<%=WebUtil.display(_service_item_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_itemType_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_itemType_label" class="formLabel" >Item Type </div>
    <div id="cleanerServiceItemForm_itemType_text" class="formFieldText" >       
        <input id="itemType" class="field" type="text" size="70" name="itemType" value="<%=WebUtil.display(_item_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_title_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_title_label" class="formLabel" >Title </div>
    <div id="cleanerServiceItemForm_title_text" class="formFieldText" >       
        <input id="title" class="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_imagePath_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_imagePath_label" class="formLabel" >Image Path </div>
    <div id="cleanerServiceItemForm_imagePath_text" class="formFieldText" >       
        <input id="imagePath" class="field" type="text" size="70" name="imagePath" value="<%=WebUtil.display(_image_pathValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_imagePathLocal_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_imagePathLocal_label" class="formLabel" >Image Path Local </div>
    <div id="cleanerServiceItemForm_imagePathLocal_text" class="formFieldText" >       
        <input id="imagePathLocal" class="field" type="text" size="70" name="imagePathLocal" value="<%=WebUtil.display(_image_path_localValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_basePrice_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_basePrice_label" class="formLabel" >Base Price </div>
    <div id="cleanerServiceItemForm_basePrice_text" class="formFieldText" >       
        <input id="basePrice" class="field" type="text" size="70" name="basePrice" value="<%=WebUtil.display(_base_priceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_note_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerServiceItemForm_note_text" class="formFieldText" >       
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_service_item_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
