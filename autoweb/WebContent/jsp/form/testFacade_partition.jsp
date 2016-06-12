<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _dataValue= "";
    String _time_createdValue= "";
    String _facade_dataValue= "";

%>

<div id="partitionFormFrame_test_facade_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /TestFacade_artition.jsp -->

	<script type="text/javascript">
		function sendForm_test_facade_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="testFacadeForm_data_field" class="formFieldFrame">
    <div id="testFacadeForm_data_label" class="formLabel" >Data </div>
    <div id="testFacadeForm_data_text" class="formFieldText" >       
        <input id="data" class="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="testFacadeForm_facadeData_field" class="formFieldFrame">
    <div id="testFacadeForm_facadeData_label" class="formLabel" >Facade Data </div>
    <div id="testFacadeForm_facadeData_text" class="formFieldText" >       
        <input id="facadeData" class="field" type="text" size="70" name="facadeData" value="<%=WebUtil.display(_facade_dataValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_test_facade_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
