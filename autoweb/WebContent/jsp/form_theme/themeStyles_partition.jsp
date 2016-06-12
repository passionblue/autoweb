<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _body_widthValue= "";
    String _body_alignValue= "";
    String _body_backgroundValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_theme_styles_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /ThemeStyles_artition.jsp -->

	<script type="text/javascript">
		function sendForm_theme_styles_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="themeStylesForm_bodyWidth_field" class="formFieldFrame">
    <div id="themeStylesForm_bodyWidth_label" class="formLabel" >Body Width </div>
    <div id="themeStylesForm_bodyWidth_text" class="formFieldText" >       
        <input id="bodyWidth" class="field" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


  	<div id="themeStylesForm_bodyAlign_field" class="formFieldFrame">
    <div id="themeStylesForm_bodyAlign_label" class="formLabel" >Body Align </div>
    <div id="themeStylesForm_bodyAlign_dropdown" class="formFieldDropDown" >       
        <select class="field" name="bodyAlign" id="bodyAlign">
        <option value="" >- Please Select -</option>
<%
	List dropMenusBodyAlign = DropMenuUtil.getDropMenus("BodyAlignment");
	for(Iterator iterItems = dropMenusBodyAlign.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _body_alignValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeStylesForm_bodyBackground_field" class="formFieldFrame">
    <div id="themeStylesForm_bodyBackground_label" class="formLabel" >Body Background </div>
    <div id="themeStylesForm_bodyBackground_text" class="formFieldText" >       
        <input id="bodyBackground" class="field" type="text" size="70" name="bodyBackground" value="<%=WebUtil.display(_body_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeStylesForm_timeCreated_field" class="formFieldFrame">
    <div id="themeStylesForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="themeStylesForm_timeCreated_text" class="formFieldText" >       
        <input id="timeCreated" class="field" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeStylesForm_timeUpdated_field" class="formFieldFrame">
    <div id="themeStylesForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="themeStylesForm_timeUpdated_text" class="formFieldText" >       
        <input id="timeUpdated" class="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_theme_styles_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
