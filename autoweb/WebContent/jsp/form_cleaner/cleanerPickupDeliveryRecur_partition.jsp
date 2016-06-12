<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _customer_idValue= "";
    String _weekdayValue= "";
    String _time_hhddValue= "";

%>

<div id="partitionFormFrame_cleaner_pickup_delivery_recur_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerPickupDeliveryRecur_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_pickup_delivery_recur_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerPickupDeliveryRecurForm_customerId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryRecurForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerPickupDeliveryRecurForm_customerId_text" class="formFieldText" >       
        <input id="customerId" class="field" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryRecurForm_weekday_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryRecurForm_weekday_label" class="formLabel" >Weekday </div>
    <div id="cleanerPickupDeliveryRecurForm_weekday_text" class="formFieldText" >       
        <input id="weekday" class="field" type="text" size="70" name="weekday" value="<%=WebUtil.display(_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryRecurForm_timeHhdd_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryRecurForm_timeHhdd_label" class="formLabel" >Time Hhdd </div>
    <div id="cleanerPickupDeliveryRecurForm_timeHhdd_text" class="formFieldText" >       
        <input id="timeHhdd" class="field" type="text" size="70" name="timeHhdd" value="<%=WebUtil.display(_time_hhddValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_pickup_delivery_recur_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
