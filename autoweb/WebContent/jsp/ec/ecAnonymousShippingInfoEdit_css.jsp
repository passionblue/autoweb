<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    EcAnonymousShippingInfo _EcAnonymousShippingInfo = EcAnonymousShippingInfoDS.getInstance().getById(id);

    if ( _EcAnonymousShippingInfo == null ) {
        return;
    }

    String _anonymous_user_idValue=  WebUtil.display(_EcAnonymousShippingInfo.getAnonymousUserId());
    String _first_nameValue=  WebUtil.display(_EcAnonymousShippingInfo.getFirstName());
    String _middle_initialValue=  WebUtil.display(_EcAnonymousShippingInfo.getMiddleInitial());
    String _last_nameValue=  WebUtil.display(_EcAnonymousShippingInfo.getLastName());
    String _address1Value=  WebUtil.display(_EcAnonymousShippingInfo.getAddress1());
    String _address2Value=  WebUtil.display(_EcAnonymousShippingInfo.getAddress2());
    String _cityValue=  WebUtil.display(_EcAnonymousShippingInfo.getCity());
    String _stateValue=  WebUtil.display(_EcAnonymousShippingInfo.getState());
    String _zipValue=  WebUtil.display(_EcAnonymousShippingInfo.getZip());
    String _countryValue=  WebUtil.display(_EcAnonymousShippingInfo.getCountry());
    String _special_instructionValue=  WebUtil.display(_EcAnonymousShippingInfo.getSpecialInstruction());
    String _time_createdValue=  WebUtil.display(_EcAnonymousShippingInfo.getTimeCreated());
%> 

<br>
<div id="ecAnonymousShippingInfoForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecAnonymousShippingInfoFormEdit" method="get" action="/ecAnonymousShippingInfoAction.html" >




	<div id="ecAnonymousShippingInfoForm_anonymousUserId_field">
    <div id="ecAnonymousShippingInfoForm_anonymousUserId_label" class="formLabel" >Anonymous User Id </div>
    <div id="ecAnonymousShippingInfoForm_anonymousUserId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousShippingInfoForm_firstName_field">
    <div id="ecAnonymousShippingInfoForm_firstName_label" class="formLabel" >First Name </div>
    <div id="ecAnonymousShippingInfoForm_firstName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousShippingInfoForm_middleInitial_field">
    <div id="ecAnonymousShippingInfoForm_middleInitial_label" class="formLabel" >Middle Initial </div>
    <div id="ecAnonymousShippingInfoForm_middleInitial_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="middleInitial" value="<%=WebUtil.display(_middle_initialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousShippingInfoForm_lastName_field">
    <div id="ecAnonymousShippingInfoForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="ecAnonymousShippingInfoForm_lastName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousShippingInfoForm_address1_field">
    <div id="ecAnonymousShippingInfoForm_address1_label" class="formLabel" >Address1 </div>
    <div id="ecAnonymousShippingInfoForm_address1_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="address1" value="<%=WebUtil.display(_address1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousShippingInfoForm_address2_field">
    <div id="ecAnonymousShippingInfoForm_address2_label" class="formLabel" >Address2 </div>
    <div id="ecAnonymousShippingInfoForm_address2_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="address2" value="<%=WebUtil.display(_address2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousShippingInfoForm_city_field">
    <div id="ecAnonymousShippingInfoForm_city_label" class="formLabel" >City </div>
    <div id="ecAnonymousShippingInfoForm_city_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="city" value="<%=WebUtil.display(_cityValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousShippingInfoForm_state_field">
    <div id="ecAnonymousShippingInfoForm_state_label" class="formLabel" >State </div>
    <div id="ecAnonymousShippingInfoForm_state_dropdown" class="formFieldDropDown" >       
        <select id="field" name="state">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _stateValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="ecAnonymousShippingInfoForm_zip_field">
    <div id="ecAnonymousShippingInfoForm_zip_label" class="formLabel" >Zip </div>
    <div id="ecAnonymousShippingInfoForm_zip_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="zip" value="<%=WebUtil.display(_zipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousShippingInfoForm_country_field">
    <div id="ecAnonymousShippingInfoForm_country_label" class="formLabel" >Country </div>
    <div id="ecAnonymousShippingInfoForm_country_dropdown" class="formFieldDropDown" >       
        <select id="field" name="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_specialInstruction_field">
    <div id="ecAnonymousShippingInfoForm_specialInstruction_label" class="formLabel" >Special Instruction </div>
    <div id="ecAnonymousShippingInfoForm_specialInstruction_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="specialInstruction" COLS="50" ROWS="8" ><%=WebUtil.display(_special_instructionValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




        <div id="ecAnonymousShippingInfoFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousShippingInfoFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousShippingInfo.getId()%>">
</form>
</div> <!-- form -->
