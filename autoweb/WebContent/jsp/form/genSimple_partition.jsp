<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _dataValue= "";
    String _activeValue= "";
    String _ext_stringValue= "";
    String _ext_intValue= "";

%>

<div id="partitionFormFrame_gen_simple_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /GenSimple_artition.jsp -->

	<script type="text/javascript">
		function sendForm_gen_simple_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="genSimpleForm_data_field" class="formFieldFrame">
    <div id="genSimpleForm_data_label" class="formLabel" >Data </div>
    <div id="genSimpleForm_data_text" class="formFieldText" >       
        <input id="data" class="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genSimpleForm_active_field" class="formFieldFrame">
    <div id="genSimpleForm_active_label" class="formLabel" >Active </div>
    <div id="genSimpleForm_active_text" class="formFieldText" >       
        <input id="active" class="field" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genSimpleForm_extString_field" class="formFieldFrame">
    <div id="genSimpleForm_extString_label" class="formLabel" >Ext String </div>
    <div id="genSimpleForm_extString_text" class="formFieldText" >       
        <input id="extString" class="field" type="text" size="70" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genSimpleForm_extInt_field" class="formFieldFrame">
    <div id="genSimpleForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genSimpleForm_extInt_text" class="formFieldText" >       
        <input id="extInt" class="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_gen_simple_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
