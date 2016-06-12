<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = EcAnonymousPaymentInfoDS.getInstance().getById(id);

    if ( _EcAnonymousPaymentInfo == null ) {
        return;
    }

    String _anonymous_user_idValue=  WebUtil.display(_EcAnonymousPaymentInfo.getAnonymousUserId());
    String _first_nameValue=  WebUtil.display(_EcAnonymousPaymentInfo.getFirstName());
    String _middle_initialValue=  WebUtil.display(_EcAnonymousPaymentInfo.getMiddleInitial());
    String _last_nameValue=  WebUtil.display(_EcAnonymousPaymentInfo.getLastName());
    String _address1Value=  WebUtil.display(_EcAnonymousPaymentInfo.getAddress1());
    String _address2Value=  WebUtil.display(_EcAnonymousPaymentInfo.getAddress2());
    String _cityValue=  WebUtil.display(_EcAnonymousPaymentInfo.getCity());
    String _stateValue=  WebUtil.display(_EcAnonymousPaymentInfo.getState());
    String _zipValue=  WebUtil.display(_EcAnonymousPaymentInfo.getZip());
    String _countryValue=  WebUtil.display(_EcAnonymousPaymentInfo.getCountry());
    String _payment_typeValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentType());
    String _payment_numValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentNum());
    String _payment_expire_monthValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireMonth());
    String _payment_expire_yearValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireYear());
    String _payment_extra_numValue=  WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExtraNum());
    String _time_createdValue=  WebUtil.display(_EcAnonymousPaymentInfo.getTimeCreated());
%> 

<br>
<div id="ecAnonymousPaymentInfoForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecAnonymousPaymentInfoFormEdit" method="get" action="/ecAnonymousPaymentInfoAction.html" >




	<div id="ecAnonymousPaymentInfoForm_anonymousUserId_field">
    <div id="ecAnonymousPaymentInfoForm_anonymousUserId_label" class="formLabel" >Anonymous User Id </div>
    <div id="ecAnonymousPaymentInfoForm_anonymousUserId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_firstName_field">
    <div id="ecAnonymousPaymentInfoForm_firstName_label" class="formLabel" >First Name </div>
    <div id="ecAnonymousPaymentInfoForm_firstName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_middleInitial_field">
    <div id="ecAnonymousPaymentInfoForm_middleInitial_label" class="formLabel" >Middle Initial </div>
    <div id="ecAnonymousPaymentInfoForm_middleInitial_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="middleInitial" value="<%=WebUtil.display(_middle_initialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_lastName_field">
    <div id="ecAnonymousPaymentInfoForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="ecAnonymousPaymentInfoForm_lastName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_address1_field">
    <div id="ecAnonymousPaymentInfoForm_address1_label" class="formLabel" >Address1 </div>
    <div id="ecAnonymousPaymentInfoForm_address1_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="address1" value="<%=WebUtil.display(_address1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_address2_field">
    <div id="ecAnonymousPaymentInfoForm_address2_label" class="formLabel" >Address2 </div>
    <div id="ecAnonymousPaymentInfoForm_address2_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="address2" value="<%=WebUtil.display(_address2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_city_field">
    <div id="ecAnonymousPaymentInfoForm_city_label" class="formLabel" >City </div>
    <div id="ecAnonymousPaymentInfoForm_city_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="city" value="<%=WebUtil.display(_cityValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousPaymentInfoForm_state_field">
    <div id="ecAnonymousPaymentInfoForm_state_label" class="formLabel" >State </div>
    <div id="ecAnonymousPaymentInfoForm_state_dropdown" class="formFieldDropDown" >       
        <select id="field" name="state">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _stateValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="ecAnonymousPaymentInfoForm_zip_field">
    <div id="ecAnonymousPaymentInfoForm_zip_label" class="formLabel" >Zip </div>
    <div id="ecAnonymousPaymentInfoForm_zip_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="zip" value="<%=WebUtil.display(_zipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousPaymentInfoForm_country_field">
    <div id="ecAnonymousPaymentInfoForm_country_label" class="formLabel" >Country </div>
    <div id="ecAnonymousPaymentInfoForm_country_dropdown" class="formFieldDropDown" >       
        <select id="field" name="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="ecAnonymousPaymentInfoForm_paymentType_field">
    <div id="ecAnonymousPaymentInfoForm_paymentType_label" class="formLabel" >Payment Type </div>
    <div id="ecAnonymousPaymentInfoForm_paymentType_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="paymentType" value="<%=WebUtil.display(_payment_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_paymentNum_field">
    <div id="ecAnonymousPaymentInfoForm_paymentNum_label" class="formLabel" >Payment Num </div>
    <div id="ecAnonymousPaymentInfoForm_paymentNum_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="paymentNum" value="<%=WebUtil.display(_payment_numValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_paymentExpireMonth_field">
    <div id="ecAnonymousPaymentInfoForm_paymentExpireMonth_label" class="formLabel" >Payment Expire Month </div>
    <div id="ecAnonymousPaymentInfoForm_paymentExpireMonth_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="paymentExpireMonth" value="<%=WebUtil.display(_payment_expire_monthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_paymentExpireYear_field">
    <div id="ecAnonymousPaymentInfoForm_paymentExpireYear_label" class="formLabel" >Payment Expire Year </div>
    <div id="ecAnonymousPaymentInfoForm_paymentExpireYear_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="paymentExpireYear" value="<%=WebUtil.display(_payment_expire_yearValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_paymentExtraNum_field">
    <div id="ecAnonymousPaymentInfoForm_paymentExtraNum_label" class="formLabel" >Payment Extra Num </div>
    <div id="ecAnonymousPaymentInfoForm_paymentExtraNum_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="paymentExtraNum" value="<%=WebUtil.display(_payment_extra_numValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="ecAnonymousPaymentInfoFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousPaymentInfoFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousPaymentInfo.getId()%>">
</form>
</div> <!-- form -->
