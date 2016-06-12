<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _ext_stringValue= "";
    String _ext_intValue= "";

%>

<div id="partitionFormFrame_user_manage_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /UserManage_artition.jsp -->

	<script type="text/javascript">
		function sendForm_user_manage_<%= catchString %>() { 
			alert("xxx");
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm<%= catchString %>">






	<div id="userManageForm_extString_field" class="formFieldFrame">
    <div id="userManageForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="userManageForm_extString_text" class="formFieldText" >       
        <INPUT id="extString" class="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="userManageForm_extInt_field" class="formFieldFrame">
    <div id="userManageForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="userManageForm_extInt_text" class="formFieldText" >       
        <INPUT id="extInt" class="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_user_manage_<%= catchString %>();">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
