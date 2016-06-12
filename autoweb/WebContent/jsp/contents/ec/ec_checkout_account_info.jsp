<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.ec.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	PageDS pageDS = PageDS.getInstance();

	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	PageConfig pageConfig = null;
	if (dynPage != null)
		pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());

    String rpcId = (String) session.getAttribute("k_RPCI");
    EcCart cart = EcCartManager.getCartMakeSure(sessionContext, rpcId, site.getId());

	EcCheckoutAccountInfo accountInfo = null;

	if (cart != null) {
		accountInfo = EcCheckoutAccountInfoDS.getInstance().getObjectByCartSerial(cart.getSerial());
		if (accountInfo == null){
			accountInfo = new EcCheckoutAccountInfo();
			accountInfo.setCartSerial(cart.getSerial());
			accountInfo.setSiteId(site.getId());
			EcCheckoutAccountInfoDS.getInstance().put(accountInfo);
		}
	}	

	WebDebug.getInstance().putDebug(session, "accountInfo",accountInfo);
	WebDebug.getInstance().putDebug(session, "accountInfo",EcCheckoutAccountInfoDS.objectToString(accountInfo));
	
	System.out.println("XXXX = " + accountInfo.getAddress1());


    Map reqParams = (Map) session.getAttribute("k_reserved_params");
	
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(accountInfo.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(accountInfo.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _email_retypeValue= (reqParams.get("emailRetype")==null?"":WebUtil.display((String)reqParams.get("emailRetype")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(accountInfo.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _middle_initialValue= (reqParams.get("middleInitial")==null?WebUtil.display(accountInfo.getMiddleInitial()):WebUtil.display((String)reqParams.get("middleInitial")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(accountInfo.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _address1Value= (reqParams.get("address1")==null?WebUtil.display(accountInfo.getAddress1()):WebUtil.display((String)reqParams.get("address1")));
    String _address2Value= (reqParams.get("address2")==null?WebUtil.display(accountInfo.getAddress2()):WebUtil.display((String)reqParams.get("address2")));
    String _cityValue= (reqParams.get("city")==null?WebUtil.display(accountInfo.getCity()):WebUtil.display((String)reqParams.get("city")));
    String _country_regionValue= (reqParams.get("countryRegion")==null?WebUtil.display(accountInfo.getCountryRegion()):WebUtil.display((String)reqParams.get("countryRegion")));
    String _state_provinceValue= (reqParams.get("stateProvince")==null?WebUtil.display(accountInfo.getStateProvince()):WebUtil.display((String)reqParams.get("stateProvince")));
    String _zipValue= (reqParams.get("zip")==null?WebUtil.display(accountInfo.getZip()):WebUtil.display((String)reqParams.get("zip")));
    String _phoneValue= (reqParams.get("phone")==null?WebUtil.display(accountInfo.getPhone()):WebUtil.display((String)reqParams.get("phone")));
    String _billing_first_nameValue= (reqParams.get("billingFirstName")==null?WebUtil.display(accountInfo.getBillingFirstName()):WebUtil.display((String)reqParams.get("billingFirstName")));
    String _billing_middle_initialValue= (reqParams.get("billingMiddleInitial")==null?WebUtil.display(accountInfo.getBillingMiddleInitial()):WebUtil.display((String)reqParams.get("billingMiddleInitial")));
    String _billing_last_nameValue= (reqParams.get("billingLastName")==null?WebUtil.display(accountInfo.getBillingLastName()):WebUtil.display((String)reqParams.get("billingLastName")));
    String _billing_address1Value= (reqParams.get("billingAddress1")==null?WebUtil.display(accountInfo.getBillingAddress1()):WebUtil.display((String)reqParams.get("billingAddress1")));
    String _billing_address2Value= (reqParams.get("billingAddress2")==null?WebUtil.display(accountInfo.getBillingAddress2()):WebUtil.display((String)reqParams.get("billingAddress2")));
    String _billing_cityValue= (reqParams.get("billingCity")==null?WebUtil.display(accountInfo.getBillingCity()):WebUtil.display((String)reqParams.get("billingCity")));
    String _billing_countryValue= (reqParams.get("billingCountry")==null?WebUtil.display(accountInfo.getBillingCountry()):WebUtil.display((String)reqParams.get("billingCountry")));
    String _billing_stateValue= (reqParams.get("billingState")==null?WebUtil.display(accountInfo.getBillingState()):WebUtil.display((String)reqParams.get("billingState")));
    String _billing_zipValue= (reqParams.get("billingZip")==null?WebUtil.display(accountInfo.getBillingZip()):WebUtil.display((String)reqParams.get("billingZip")));
    String _billing_phoneValue= (reqParams.get("billingPhone")==null?WebUtil.display(accountInfo.getBillingPhone()):WebUtil.display((String)reqParams.get("billingPhone")));
    String _term_agreeValue= (reqParams.get("termAgree")==null?WebUtil.display(accountInfo.getTermAgree()):WebUtil.display((String)reqParams.get("termAgree")));
    String _subs_emailValue= (reqParams.get("subsEmail")==null?WebUtil.display(accountInfo.getSubsEmail()):WebUtil.display((String)reqParams.get("subsEmail")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(accountInfo.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _password_retypeValue= (reqParams.get("passwordRetype")==null?WebUtil.display(accountInfo.getPasswordRetype()):WebUtil.display((String)reqParams.get("passwordRetype")));
    String _return_emailValue= (reqParams.get("returnEmail")==null?WebUtil.display(accountInfo.getReturnEmail()):WebUtil.display((String)reqParams.get("returnEmail")));
    String _return_passwordValue= (reqParams.get("returnPassword")==null?WebUtil.display(accountInfo.getReturnPassword()):WebUtil.display((String)reqParams.get("returnPassword")));

    String _wpId = WebProcManager.registerWebProcess();

%> 
<%
	if (cart == null || cart.getCartItems().size() == 0){
%>
	<h4 style="color: red"> Shopping cart is empty.</h4>
<%
	} else {
%>
	<br/>
<%
	} 
%>

<div id="ecCheckoutAccountInfoForm_topArea" class="formTopArea"></div>

<div id="ecCheckoutAccountInfoFormFrame">

<div id="ecCheckoutAccountInfoForm">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecCheckoutAccountInfoForm" method="get" action="/ecCheckoutAccountInfoAction.html" >

<div id="addressForm">

	<div id="ecCheckoutAccountInfoForm_email_field">
    <div id="ecCheckoutAccountInfoForm_email_label" class="formRequiredLabel" >Email address* </div>
    <div id="ecCheckoutAccountInfoForm_email_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_emailRetype_field">
    <div id="ecCheckoutAccountInfoForm_emailRetype_label" class="formRequiredLabel" >Re-enter Email* </div>
    <div id="ecCheckoutAccountInfoForm_emailRetype_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="emailRetype" value="<%=WebUtil.display(_email_retypeValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_firstName_field">
    <div id="ecCheckoutAccountInfoForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="ecCheckoutAccountInfoForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="ecCheckoutAccountInfoForm_middleInitial_field">
    <div id="ecCheckoutAccountInfoForm_middleInitial_label" class="formLabel" >Middle Initial </div>
    <div id="ecCheckoutAccountInfoForm_middleInitial_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="3" name="middleInitial" value="<%=WebUtil.display(_middle_initialValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_lastName_field">
    <div id="ecCheckoutAccountInfoForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="ecCheckoutAccountInfoForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_address1_field">
    <div id="ecCheckoutAccountInfoForm_address1_label" class="formRequiredLabel" >Address Line 1* </div>
    <div id="ecCheckoutAccountInfoForm_address1_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="address1" value="<%=WebUtil.display(_address1Value)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_address2_field">
    <div id="ecCheckoutAccountInfoForm_address2_label" class="formLabel" >Address Line 2 </div>
    <div id="ecCheckoutAccountInfoForm_address2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="address2" value="<%=WebUtil.display(_address2Value)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_city_field">
    <div id="ecCheckoutAccountInfoForm_city_label" class="formRequiredLabel" >City* </div>
    <div id="ecCheckoutAccountInfoForm_city_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="city" value="<%=WebUtil.display(_cityValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="ecCheckoutAccountInfoForm_countryRegion_field">
    <div id="ecCheckoutAccountInfoForm_countryRegion_label" class="formLabel" >Country Region </div>
    <div id="ecCheckoutAccountInfoForm_countryRegion_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="countryRegion">
        <option value="" >- Please Select -</option>
        <option value="USA" <%=HtmlUtil.getOptionSelect("USA", _country_regionValue)%>>USA</option>
        <option value="Canada" <%=HtmlUtil.getOptionSelect("Canada", _country_regionValue)%>>Canada</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_stateProvince_field">
    <div id="ecCheckoutAccountInfoForm_stateProvince_label" class="formLabel" >State Province </div>
    <div id="ecCheckoutAccountInfoForm_stateProvince_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="stateProvince">
        <option value="" >- Please Select -</option>
        <option value="New York" <%=HtmlUtil.getOptionSelect("New York", _state_provinceValue)%>>New York</option>
        <option value="California" <%=HtmlUtil.getOptionSelect("California", _state_provinceValue)%>>California</option>
        <option value="New Jersey" <%=HtmlUtil.getOptionSelect("New Jersey", _state_provinceValue)%>>New Jersey</option>
        <option value="Idaho" <%=HtmlUtil.getOptionSelect("Idaho", _state_provinceValue)%>>Idaho</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecCheckoutAccountInfoForm_zip_field">
    <div id="ecCheckoutAccountInfoForm_zip_label" class="formRequiredLabel" >Zip* </div>
    <div id="ecCheckoutAccountInfoForm_zip_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="zip" value="<%=WebUtil.display(_zipValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_phone_field">
    <div id="ecCheckoutAccountInfoForm_phone_label" class="formRequiredLabel" >Phone* </div>
    <div id="ecCheckoutAccountInfoForm_phone_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="ecCheckoutAccountInfoForm_useBilling_field">
    <div id="ecCheckoutAccountInfoForm_useBilling_label" class="formLabel" >Use This For Billing </div>
    <div id="ecCheckoutAccountInfoForm_useBilling_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="useBilling"  />
    </div>      
	</div><div class="clear"></div>
</div>
<div id="addressBillingForm">

	<div id="ecCheckoutAccountInfoForm_billingFirstName_field">
    <div id="ecCheckoutAccountInfoForm_billingFirstName_label" class="formLabel" >Billing First Name </div>
    <div id="ecCheckoutAccountInfoForm_billingFirstName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="billingFirstName" value="<%=WebUtil.display(_billing_first_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_billingLastName_field">
    <div id="ecCheckoutAccountInfoForm_billingLastName_label" class="formLabel" >Billing Last Name </div>
    <div id="ecCheckoutAccountInfoForm_billingLastName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="billingLastName" value="<%=WebUtil.display(_billing_last_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_billingAddress1_field">
    <div id="ecCheckoutAccountInfoForm_billingAddress1_label" class="formLabel" >Address Line 1 </div>
    <div id="ecCheckoutAccountInfoForm_billingAddress1_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="billingAddress1" value="<%=WebUtil.display(_billing_address1Value)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_billingAddress2_field">
    <div id="ecCheckoutAccountInfoForm_billingAddress2_label" class="formLabel" >Address Line 2 </div>
    <div id="ecCheckoutAccountInfoForm_billingAddress2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="billingAddress2" value="<%=WebUtil.display(_billing_address2Value)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_billingCity_field">
    <div id="ecCheckoutAccountInfoForm_billingCity_label" class="formLabel" >Billing City </div>
    <div id="ecCheckoutAccountInfoForm_billingCity_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="billingCity" value="<%=WebUtil.display(_billing_cityValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="ecCheckoutAccountInfoForm_billingCountry_field">
    <div id="ecCheckoutAccountInfoForm_billingCountry_label" class="formLabel" >Billing Country </div>
    <div id="ecCheckoutAccountInfoForm_billingCountry_dropdown" class="formFieldDropDown" >       
        <select id="field" name="billingCountry">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _billing_countryValue)%>>XX</option-->
        <option value="USA" <%=HtmlUtil.getOptionSelect("USA", _billing_countryValue)%>>USA</option>
        <option value="Canada" <%=HtmlUtil.getOptionSelect("Canada", _billing_countryValue)%>>Canada</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_billingState_field">
    <div id="ecCheckoutAccountInfoForm_billingState_label" class="formLabel" >Billing State </div>
    <div id="ecCheckoutAccountInfoForm_billingState_dropdown" class="formFieldDropDown" >       
        <select id="field" name="billingState">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _billing_stateValue)%>>XX</option-->
        <option value="New York" <%=HtmlUtil.getOptionSelect("New York", _billing_stateValue)%>>New York</option>
        <option value="California" <%=HtmlUtil.getOptionSelect("California", _billing_stateValue)%>>California</option>
        <option value="New Jersey" <%=HtmlUtil.getOptionSelect("New Jersey", _billing_stateValue)%>>New Jersey</option>
        <option value="Idaho" <%=HtmlUtil.getOptionSelect("Idaho", _billing_stateValue)%>>Idaho</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecCheckoutAccountInfoForm_billingZip_field">
    <div id="ecCheckoutAccountInfoForm_billingZip_label" class="formLabel" >Billing Zip </div>
    <div id="ecCheckoutAccountInfoForm_billingZip_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="billingZip" value="<%=WebUtil.display(_billing_zipValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_billingPhone_field">
    <div id="ecCheckoutAccountInfoForm_billingPhone_label" class="formLabel" >Billing Phone </div>
    <div id="ecCheckoutAccountInfoForm_billingPhone_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="billingPhone" value="<%=WebUtil.display(_billing_phoneValue)%>"/> 
    </div>      
	</div><div class="clear"></div>
</div>

<div id="agreementForm">
	<div id="ecCheckoutAccountInfoForm_termAgree_field">
    <div id="ecCheckoutAccountInfoForm_termAgree_label" class="formLabel" >Term Agree </div>
    <div id="ecCheckoutAccountInfoForm_termAgree_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="termAgree" />
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_subsEmail_field">
    <div id="ecCheckoutAccountInfoForm_subsEmail_label" class="formLabel" >Subs Email </div>
    <div id="ecCheckoutAccountInfoForm_subsEmail_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="subsEmail" checked="checked" />
    </div>      
	</div><div class="clear"></div>

</div>

<div id="optionalForm">
	<div id="ecCheckoutAccountInfoForm_password_field">
    <div id="ecCheckoutAccountInfoForm_password_label" class="formLabel" >Password </div>
    <div id="ecCheckoutAccountInfoForm_password_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="password" />  
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_passwordRetype_field">
    <div id="ecCheckoutAccountInfoForm_passwordRetype_label" class="formLabel" >Password Retype </div>
    <div id="ecCheckoutAccountInfoForm_passwordRetype_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="passwordRetype" /> 
    </div>      
	</div><div class="clear"></div>
</div>
        <div id="ecCheckoutAccountInfoForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecCheckoutAccountInfoForm.submit();">Continue Checkout</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="cmd" value="without">
<INPUT TYPE="HIDDEN" NAME="cartSerial" value="<%=cart.getSerial() %>">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
<br/><br/>
</div> <!-- form -->

<div id="ecCheckoutReturningCustomerForm">

<div id="returnCredentialForm">
<form name="ecCheckoutReturningCustomerForm" method="post" action="/ecCheckoutAccountInfoAction.html" >
	<div id="ecCheckoutAccountInfoForm_email_field">
    <div id="ecCheckoutAccountInfoForm_email_label" class="formRequiredLabel" >Email address* </div>
    <div id="ecCheckoutAccountInfoForm_email_text" class="formFieldText" > <span></span>      
        <input id="requiredFieldLogin" type="text" size="20" name="return_email" value="<%=WebUtil.display(_emailValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="ecCheckoutAccountInfoForm_email_field">
    <div id="ecCheckoutAccountInfoForm_email_label" class="formRequiredLabel" >Password* </div>
    <div id="ecCheckoutAccountInfoForm_email_text" class="formFieldText" > <span></span>      
        <input id="requiredFieldLogin" type="password" size="20" name="return_password" /> 
    </div>      
	</div><div class="clear"></div>
        <div id="ecCheckoutAccountInfoForm_submit" class="formSubmitLogin" >       
            <a id="formSubmitLogin" href="javascript:document.ecCheckoutReturningCustomerForm.submit();">Continue Checkout</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="cmd" value="ret">
</form>
</div>
</div>

</div><div class="clear"></div>
<div id="ecCheckoutAccountInfoForm_bottomArea" class="formBottomArea"></div>


