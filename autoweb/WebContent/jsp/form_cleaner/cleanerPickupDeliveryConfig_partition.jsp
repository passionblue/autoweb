<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _location_idValue= "";
    String _apply_all_locationsValue= "";
    String _disable_web_requestValue= "";
    String _disallow_anonymous_requestValue= "";
    String _require_customer_registerValue= "";
    String _require_customer_loginValue= "";

%>

<div id="partitionFormFrame_cleaner_pickup_delivery_config_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerPickupDeliveryConfig_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_pickup_delivery_config_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerPickupDeliveryConfigForm_locationId_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerPickupDeliveryConfigForm_locationId_text" class="formFieldText" >       
        <input id="locationId" class="field" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="cleanerPickupDeliveryConfigForm_applyAllLocations_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_applyAllLocations_label" class="formLabel" >Apply All Locations </div>
    <div id="cleanerPickupDeliveryConfigForm_applyAllLocations_dropdown" class="formFieldDropDown" >       
        <select name="applyAllLocations">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _apply_all_locationsValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _apply_all_locationsValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_disableWebRequest_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_disableWebRequest_label" class="formLabel" >Disable Web Request </div>
    <div id="cleanerPickupDeliveryConfigForm_disableWebRequest_dropdown" class="formFieldDropDown" >       
        <select name="disableWebRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disable_web_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disable_web_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_label" class="formLabel" >Disallow Anonymous Request </div>
    <div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_dropdown" class="formFieldDropDown" >       
        <select name="disallowAnonymousRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disallow_anonymous_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disallow_anonymous_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_label" class="formLabel" >Require Customer Register </div>
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_dropdown" class="formFieldDropDown" >       
        <select name="requireCustomerRegister">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_registerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_registerValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_field" class="formFieldFrame">
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_label" class="formLabel" >Require Customer Login </div>
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_dropdown" class="formFieldDropDown" >       
        <select name="requireCustomerLogin">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_loginValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_loginValue)%>>Yes</option>
        </select>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_pickup_delivery_config_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
