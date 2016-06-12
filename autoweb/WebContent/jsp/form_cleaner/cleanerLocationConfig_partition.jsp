<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _location_idValue= "";
    String _open_hour_weekdayValue= "";
    String _close_hour_weekdayValue= "";
    String _open_hour_satValue= "";
    String _close_hour_satValue= "";
    String _open_hour_sunValue= "";
    String _close_hour_sunValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_cleaner_location_config_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerLocationConfig_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_location_config_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerLocationConfigForm_locationId_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerLocationConfigForm_locationId_text" class="formFieldText" >       
        <input id="locationId" class="field" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_openHourWeekday_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_openHourWeekday_label" class="formLabel" >Open Hour Weekday </div>
    <div id="cleanerLocationConfigForm_openHourWeekday_text" class="formFieldText" >       
        <input id="openHourWeekday" class="field" type="text" size="70" name="openHourWeekday" value="<%=WebUtil.display(_open_hour_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_closeHourWeekday_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_closeHourWeekday_label" class="formLabel" >Close Hour Weekday </div>
    <div id="cleanerLocationConfigForm_closeHourWeekday_text" class="formFieldText" >       
        <input id="closeHourWeekday" class="field" type="text" size="70" name="closeHourWeekday" value="<%=WebUtil.display(_close_hour_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_openHourSat_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_openHourSat_label" class="formLabel" >Open Hour Sat </div>
    <div id="cleanerLocationConfigForm_openHourSat_text" class="formFieldText" >       
        <input id="openHourSat" class="field" type="text" size="70" name="openHourSat" value="<%=WebUtil.display(_open_hour_satValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_closeHourSat_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_closeHourSat_label" class="formLabel" >Close Hour Sat </div>
    <div id="cleanerLocationConfigForm_closeHourSat_text" class="formFieldText" >       
        <input id="closeHourSat" class="field" type="text" size="70" name="closeHourSat" value="<%=WebUtil.display(_close_hour_satValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_openHourSun_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_openHourSun_label" class="formLabel" >Open Hour Sun </div>
    <div id="cleanerLocationConfigForm_openHourSun_text" class="formFieldText" >       
        <input id="openHourSun" class="field" type="text" size="70" name="openHourSun" value="<%=WebUtil.display(_open_hour_sunValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_closeHourSun_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_closeHourSun_label" class="formLabel" >Close Hour Sun </div>
    <div id="cleanerLocationConfigForm_closeHourSun_text" class="formFieldText" >       
        <input id="closeHourSun" class="field" type="text" size="70" name="closeHourSun" value="<%=WebUtil.display(_close_hour_sunValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_location_config_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
