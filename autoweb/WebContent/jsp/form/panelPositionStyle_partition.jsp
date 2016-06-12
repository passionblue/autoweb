<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _colValue= "";
    String _style_idValue= "";
    String _time_createdValue= "";

%>

<div id="partitionFormFrame_panel_position_style_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /PanelPositionStyle_artition.jsp -->

	<script type="text/javascript">
		function sendForm_panel_position_style_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="panelPositionStyleForm_col_field" class="formFieldFrame">
    <div id="panelPositionStyleForm_col_label" class="formLabel" >Col </div>
    <div id="panelPositionStyleForm_col_text" class="formFieldText" >       
        <input id="col" class="field" type="text" size="70" name="col" value="<%=WebUtil.display(_colValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="panelPositionStyleForm_styleId_field" class="formFieldFrame">
    <div id="panelPositionStyleForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="panelPositionStyleForm_styleId_text" class="formFieldText" >       
        <input id="styleId" class="field" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_panel_position_style_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
