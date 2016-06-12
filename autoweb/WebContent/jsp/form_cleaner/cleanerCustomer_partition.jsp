<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _autosite_user_idValue= "";
    String _emailValue= "";
    String _phoneValue= "";
    String _phone2Value= "";
    String _phone_preferredValue= "";
    String _titleValue= "";
    String _last_nameValue= "";
    String _first_nameValue= "";
    String _addressValue= "";
    String _aptValue= "";
    String _cityValue= "";
    String _stateValue= "";
    String _zipValue= "";
    String _customer_typeValue= "";
    String _pay_typeValue= "";
    String _time_createdValue= "";
    String _remote_ipValue= "";
    String _noteValue= "";
    String _pickup_noteValue= "";
    String _delivery_noteValue= "";
    String _disabledValue= "";
    String _time_updatedValue= "";
    String _pickup_delivery_disallowedValue= "";
    String _handle_instValue= "";

%>

<div id="partitionFormFrame_cleaner_customer_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /CleanerCustomer_artition.jsp -->

	<script type="text/javascript">
		function sendForm_cleaner_customer_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="cleanerCustomerForm_autositeUserId_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_autositeUserId_label" class="formLabel" >Autosite User Id </div>
    <div id="cleanerCustomerForm_autositeUserId_text" class="formFieldText" >       
        <input id="autositeUserId" class="field" type="text" size="70" name="autositeUserId" value="<%=WebUtil.display(_autosite_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_email_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_email_label" class="formLabel" >Email </div>
    <div id="cleanerCustomerForm_email_text" class="formFieldText" >       
        <input id="email" class="field" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_phone_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_phone_label" class="formLabel" >Phone </div>
    <div id="cleanerCustomerForm_phone_text" class="formFieldText" >       
        <input id="phone" class="field" type="text" size="70" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_phone2_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_phone2_label" class="formLabel" >Phone2 </div>
    <div id="cleanerCustomerForm_phone2_text" class="formFieldText" >       
        <input id="phone2" class="field" type="text" size="70" name="phone2" value="<%=WebUtil.display(_phone2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_phonePreferred_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_phonePreferred_label" class="formLabel" >Phone Preferred </div>
    <div id="cleanerCustomerForm_phonePreferred_text" class="formFieldText" >       
        <input id="phonePreferred" class="field" type="text" size="70" name="phonePreferred" value="<%=WebUtil.display(_phone_preferredValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_title_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_title_label" class="formLabel" >Title </div>
    <div id="cleanerCustomerForm_title_text" class="formFieldText" >       
        <input id="title" class="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_lastName_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="cleanerCustomerForm_lastName_text" class="formFieldText" >       
        <input id="lastName" class="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_firstName_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_firstName_label" class="formLabel" >First Name </div>
    <div id="cleanerCustomerForm_firstName_text" class="formFieldText" >       
        <input id="firstName" class="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_address_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_address_label" class="formLabel" >Address </div>
    <div id="cleanerCustomerForm_address_text" class="formFieldText" >       
        <input id="address" class="field" type="text" size="70" name="address" value="<%=WebUtil.display(_addressValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_apt_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_apt_label" class="formLabel" >Apt </div>
    <div id="cleanerCustomerForm_apt_text" class="formFieldText" >       
        <input id="apt" class="field" type="text" size="70" name="apt" value="<%=WebUtil.display(_aptValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_city_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_city_label" class="formLabel" >City </div>
    <div id="cleanerCustomerForm_city_text" class="formFieldText" >       
        <input id="city" class="field" type="text" size="70" name="city" value="<%=WebUtil.display(_cityValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_state_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_state_label" class="formLabel" >State </div>
    <div id="cleanerCustomerForm_state_text" class="formFieldText" >       
        <input id="state" class="field" type="text" size="70" name="state" value="<%=WebUtil.display(_stateValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_zip_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_zip_label" class="formLabel" >Zip </div>
    <div id="cleanerCustomerForm_zip_text" class="formFieldText" >       
        <input id="zip" class="field" type="text" size="70" name="zip" value="<%=WebUtil.display(_zipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_customerType_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_customerType_label" class="formLabel" >Customer Type </div>
    <div id="cleanerCustomerForm_customerType_text" class="formFieldText" >       
        <input id="customerType" class="field" type="text" size="70" name="customerType" value="<%=WebUtil.display(_customer_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_payType_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_payType_label" class="formLabel" >Pay Type </div>
    <div id="cleanerCustomerForm_payType_text" class="formFieldText" >       
        <input id="payType" class="field" type="text" size="70" name="payType" value="<%=WebUtil.display(_pay_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="cleanerCustomerForm_remoteIp_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="cleanerCustomerForm_remoteIp_text" class="formFieldText" >       
        <input id="remoteIp" class="field" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_note_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerCustomerForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_pickupNote_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_pickupNote_label" class="formLabel" >Pickup Note </div>
    <div id="cleanerCustomerForm_pickupNote_text" class="formFieldText" >       
        <input id="pickupNote" class="field" type="text" size="70" name="pickupNote" value="<%=WebUtil.display(_pickup_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_deliveryNote_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_deliveryNote_label" class="formLabel" >Delivery Note </div>
    <div id="cleanerCustomerForm_deliveryNote_text" class="formFieldText" >       
        <input id="deliveryNote" class="field" type="text" size="70" name="deliveryNote" value="<%=WebUtil.display(_delivery_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_disabled_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="cleanerCustomerForm_disabled_text" class="formFieldText" >       
        <input id="disabled" class="field" type="text" size="70" name="disabled" value="<%=WebUtil.display(_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="cleanerCustomerForm_pickupDeliveryDisallowed_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_pickupDeliveryDisallowed_label" class="formLabel" >Pickup Delivery Disallowed </div>
    <div id="cleanerCustomerForm_pickupDeliveryDisallowed_text" class="formFieldText" >       
        <input id="pickupDeliveryDisallowed" class="field" type="text" size="70" name="pickupDeliveryDisallowed" value="<%=WebUtil.display(_pickup_delivery_disallowedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerCustomerForm_handleInst_field" class="formFieldFrame">
    <div id="cleanerCustomerForm_handleInst_label" class="formLabel" >Handle Inst </div>
    <div id="cleanerCustomerForm_handleInst_text" class="formFieldText" >       
        <input id="handleInst" class="field" type="text" size="70" name="handleInst" value="<%=WebUtil.display(_handle_instValue)%>"/> <span></span>
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
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_cleaner_customer_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
