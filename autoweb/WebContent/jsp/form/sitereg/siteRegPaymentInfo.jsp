<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

	//>>START<<
	SiteRegStore siteRegStore = (SiteRegStore) session.getAttribute(SiteRegStore.getSessionKey());
	
	if ( siteRegStore == null) {
		String nextJSP = "/jsp/form/sitereg/siteRegStart.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
		return;
	} 
    SiteRegPaymentInfo info = siteRegStore.getPayInfo();
	if (info == null) info = new SiteRegPaymentInfo();

	//>>END<<

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
    String _first_nameValue= (reqParams.get("firstName")==null?info.getFirstName():WebUtil.display((String)reqParams.get("firstName")));
    String _middle_initialValue= (reqParams.get("middleInitial")==null?info.getMiddleInitial():WebUtil.display((String)reqParams.get("middleInitial")));
    String _last_nameValue= (reqParams.get("lastName")==null?info.getLastName():WebUtil.display((String)reqParams.get("lastName")));
    String _address1Value= (reqParams.get("address1")==null?info.getAddress1():WebUtil.display((String)reqParams.get("address1")));
    String _address2Value= (reqParams.get("address2")==null?info.getAddress2():WebUtil.display((String)reqParams.get("address2")));
    String _cityValue= (reqParams.get("city")==null?info.getCity():WebUtil.display((String)reqParams.get("city")));
    String _country_regionValue= (reqParams.get("countryRegion")==null?info.getCountryRegion():WebUtil.display((String)reqParams.get("countryRegion")));
    String _state_provinceValue= (reqParams.get("stateProvince")==null?info.getStateProvince():WebUtil.display((String)reqParams.get("stateProvince")));
    String _zipValue= (reqParams.get("zip")==null?info.getZip():WebUtil.display((String)reqParams.get("zip")));
    String _phoneValue= (reqParams.get("phone")==null?info.getPhone():WebUtil.display((String)reqParams.get("phone")));
    String _payment_typeValue= (reqParams.get("paymentType")==null?info.getPaymentType():WebUtil.display((String)reqParams.get("paymentType")));
    String _card_typeValue= (reqParams.get("cardType")==null?info.getCardType():WebUtil.display((String)reqParams.get("cardType")));
    String _payment_numValue= (reqParams.get("paymentNum")==null?info.getPaymentNum():WebUtil.display((String)reqParams.get("paymentNum")));
    String _expire_monthValue= (reqParams.get("expireMonth")==null?info.getExpireMonth():WebUtil.display((String)reqParams.get("expireMonth")));
    String _expire_yearValue= (reqParams.get("expireYear")==null?info.getExpireYear():WebUtil.display((String)reqParams.get("expireYear")));
    String _ccvValue= (reqParams.get("ccv")==null?info.getCcv():WebUtil.display((String)reqParams.get("ccv")));
    String _skipValue= (reqParams.get("skip")==null?WebUtil.display(info.getSkip()):WebUtil.display((String)reqParams.get("skip")));


%> 

<br>
<div id="siteRegPaymentInfoForm_topArea" class="formTopArea"></div>
<div id="siteRegPaymentInfoForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="siteRegPaymentInfoForm" method="get" action="/siteRegPaymentInfoAction.html" >

	<div id="siteRegPaymentInfoForm_firstName_field">
    <div id="siteRegPaymentInfoForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="siteRegPaymentInfoForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_middleInitial_field">
    <div id="siteRegPaymentInfoForm_middleInitial_label" class="formLabel" >Middle Initial </div>
    <div id="siteRegPaymentInfoForm_middleInitial_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="middleInitial" value="<%=WebUtil.display(_middle_initialValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_lastName_field">
    <div id="siteRegPaymentInfoForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="siteRegPaymentInfoForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_address1_field">
    <div id="siteRegPaymentInfoForm_address1_label" class="formRequiredLabel" >Address1* </div>
    <div id="siteRegPaymentInfoForm_address1_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="address1" value="<%=WebUtil.display(_address1Value)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_address2_field">
    <div id="siteRegPaymentInfoForm_address2_label" class="formLabel" >Address2 </div>
    <div id="siteRegPaymentInfoForm_address2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="address2" value="<%=WebUtil.display(_address2Value)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_city_field">
    <div id="siteRegPaymentInfoForm_city_label" class="formRequiredLabel" >City* </div>
    <div id="siteRegPaymentInfoForm_city_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="city" value="<%=WebUtil.display(_cityValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="siteRegPaymentInfoForm_countryRegion_field">
    <div id="siteRegPaymentInfoForm_countryRegion_label" class="formLabel" >Country Region </div>
    <div id="siteRegPaymentInfoForm_countryRegion_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="countryRegion">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _country_regionValue)%>>XX</option-->
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_stateProvince_field">
    <div id="siteRegPaymentInfoForm_stateProvince_label" class="formLabel" >State Province </div>
    <div id="siteRegPaymentInfoForm_stateProvince_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="stateProvince">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _state_provinceValue)%>>XX</option-->
        <option value="New York" <%=HtmlUtil.getOptionSelect("New York", _state_provinceValue)%>>New York</option>
        <option value="California" <%=HtmlUtil.getOptionSelect("California", _state_provinceValue)%>>California</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteRegPaymentInfoForm_zip_field">
    <div id="siteRegPaymentInfoForm_zip_label" class="formRequiredLabel" >Zip* </div>
    <div id="siteRegPaymentInfoForm_zip_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="zip" value="<%=WebUtil.display(_zipValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_phone_field">
    <div id="siteRegPaymentInfoForm_phone_label" class="formRequiredLabel" >Phone* </div>
    <div id="siteRegPaymentInfoForm_phone_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> 
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_paymentType_field">
    <div id="siteRegPaymentInfoForm_paymentType_label" class="formLabel" >Payment Type </div>
    <div id="siteRegPaymentInfoForm_paymentType_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="paymentType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _payment_typeValue)%>>XX</option-->
        <option value="Credit Card" <%=HtmlUtil.getOptionSelect("Credit Card", _payment_typeValue)%>>Credit Card</option>
        <option value="Paypal" <%=HtmlUtil.getOptionSelect("Paypal", _payment_typeValue)%>>Paypal</option>
        <option value="Check" <%=HtmlUtil.getOptionSelect("Check", _payment_typeValue)%>>Check</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_cardType_field">
    <div id="siteRegPaymentInfoForm_cardType_label" class="formLabel" >Card Type </div>
    <div id="siteRegPaymentInfoForm_cardType_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="cardType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _card_typeValue)%>>XX</option-->
        <option value="AMEX" <%=HtmlUtil.getOptionSelect("AMEX", _card_typeValue)%>>AMEX</option>
        <option value="VISA" <%=HtmlUtil.getOptionSelect("VISA", _card_typeValue)%>>VISA</option>
        <option value="MASTER" <%=HtmlUtil.getOptionSelect("MASTER", _card_typeValue)%>>MASTER</option>
        <option value="DISCOVER" <%=HtmlUtil.getOptionSelect("DISCOVER", _card_typeValue)%>>DISCOVER</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteRegPaymentInfoForm_paymentNum_field">
    <div id="siteRegPaymentInfoForm_paymentNum_label" class="formRequiredLabel" >Payment Num* </div>
    <div id="siteRegPaymentInfoForm_paymentNum_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="paymentNum" value="<%=WebUtil.display(_payment_numValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="siteRegPaymentInfoForm_expireMonth_field">
    <div id="siteRegPaymentInfoForm_expireMonth_label" class="formLabel" >Expire Month </div>
    <div id="siteRegPaymentInfoForm_expireMonth_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="expireMonth">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _expire_monthValue)%>>XX</option-->
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="siteRegPaymentInfoForm_expireYear_field">
    <div id="siteRegPaymentInfoForm_expireYear_label" class="formLabel" >Expire Year </div>
    <div id="siteRegPaymentInfoForm_expireYear_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="expireYear">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _expire_yearValue)%>>XX</option-->
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteRegPaymentInfoForm_ccv_field">
    <div id="siteRegPaymentInfoForm_ccv_label" class="formRequiredLabel" >Ccv* </div>
    <div id="siteRegPaymentInfoForm_ccv_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="ccv" value="<%=WebUtil.display(_ccvValue)%>"/> 
    </div>      
	</div><div class="clear"></div>

	<div id="siteRegPaymentInfoForm_skip_field">
    <div id="siteRegPaymentInfoForm_skip_label" class="formLabel" >Skip </div>
    <div id="siteRegPaymentInfoForm_skip_checkbox" class="formFieldCheckbox" >       
        <input type="checkbox" name="skip" <%=HtmlUtil.getCheckedBoxValue(_skipValue)%> />
    </div>      
	</div><div class="clear"></div>

        <div id="siteRegPaymentInfoForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteRegPaymentInfoForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
</form>
</div> <!-- form -->
<div id="siteRegPaymentInfoForm_bottomArea" class="formBottomArea"></div>