<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _titleValue= "";
    String _image_pathValue= "";
    String _image_path_localValue= "";

%>

<div id="partitionFormFrame_cleaner_service_category_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerServiceCategory_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_service_category_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerServiceCategoryForm_title_field" class="formFieldFrame">
    <div id="cleanerServiceCategoryForm_title_label" class="formLabel" >Title </div>
    <div id="cleanerServiceCategoryForm_title_text" class="formFieldText" >       
        <input id="title" class="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceCategoryForm_imagePath_field" class="formFieldFrame">
    <div id="cleanerServiceCategoryForm_imagePath_label" class="formLabel" >Image Path </div>
    <div id="cleanerServiceCategoryForm_imagePath_text" class="formFieldText" >       
        <input id="imagePath" class="field" type="text" size="70" name="imagePath" value="<%=WebUtil.display(_image_pathValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceCategoryForm_imagePathLocal_field" class="formFieldFrame">
    <div id="cleanerServiceCategoryForm_imagePathLocal_label" class="formLabel" >Image Path Local </div>
    <div id="cleanerServiceCategoryForm_imagePathLocal_text" class="formFieldText" >       
        <input id="imagePathLocal" class="field" type="text" size="70" name="imagePathLocal" value="<%=WebUtil.display(_image_path_localValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_service_category_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
