<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAnonymousPaymentInfo _EcAnonymousPaymentInfoDefault = new EcAnonymousPaymentInfo();// EcAnonymousPaymentInfoDS.getInstance().getDeafult();
    
    String _anonymous_user_idValue= (reqParams.get("anonymousUserId")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getAnonymousUserId()):WebUtil.display((String)reqParams.get("anonymousUserId")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _middle_initialValue= (reqParams.get("middleInitial")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getMiddleInitial()):WebUtil.display((String)reqParams.get("middleInitial")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _address1Value= (reqParams.get("address1")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getAddress1()):WebUtil.display((String)reqParams.get("address1")));
    String _address2Value= (reqParams.get("address2")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getAddress2()):WebUtil.display((String)reqParams.get("address2")));
    String _cityValue= (reqParams.get("city")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getCity()):WebUtil.display((String)reqParams.get("city")));
    String _stateValue= (reqParams.get("state")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getState()):WebUtil.display((String)reqParams.get("state")));
    String _zipValue= (reqParams.get("zip")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getZip()):WebUtil.display((String)reqParams.get("zip")));
    String _countryValue= (reqParams.get("country")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getCountry()):WebUtil.display((String)reqParams.get("country")));
    String _payment_typeValue= (reqParams.get("paymentType")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentType()):WebUtil.display((String)reqParams.get("paymentType")));
    String _payment_numValue= (reqParams.get("paymentNum")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentNum()):WebUtil.display((String)reqParams.get("paymentNum")));
    String _payment_expire_monthValue= (reqParams.get("paymentExpireMonth")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentExpireMonth()):WebUtil.display((String)reqParams.get("paymentExpireMonth")));
    String _payment_expire_yearValue= (reqParams.get("paymentExpireYear")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentExpireYear()):WebUtil.display((String)reqParams.get("paymentExpireYear")));
    String _payment_extra_numValue= (reqParams.get("paymentExtraNum")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getPaymentExtraNum()):WebUtil.display((String)reqParams.get("paymentExtraNum")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcAnonymousPaymentInfoDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecAnonymousPaymentInfoForm_topArea" class="formTopArea"></div>
<div id="ecAnonymousPaymentInfoForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecAnonymousPaymentInfoForm" method="get" action="/ecAnonymousPaymentInfoAction.html" >


	<div id="ecAnonymousPaymentInfoForm_anonymousUserId_field">
    <div id="ecAnonymousPaymentInfoForm_anonymousUserId_label" class="formLabel" >Anonymous User Id </div>
    <div id="ecAnonymousPaymentInfoForm_anonymousUserId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_firstName_field">
    <div id="ecAnonymousPaymentInfoForm_firstName_label" class="formLabel" >First Name </div>
    <div id="ecAnonymousPaymentInfoForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_middleInitial_field">
    <div id="ecAnonymousPaymentInfoForm_middleInitial_label" class="formLabel" >Middle Initial </div>
    <div id="ecAnonymousPaymentInfoForm_middleInitial_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="middleInitial" value="<%=WebUtil.display(_middle_initialValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_lastName_field">
    <div id="ecAnonymousPaymentInfoForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="ecAnonymousPaymentInfoForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_address1_field">
    <div id="ecAnonymousPaymentInfoForm_address1_label" class="formLabel" >Address1 </div>
    <div id="ecAnonymousPaymentInfoForm_address1_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="address1" value="<%=WebUtil.display(_address1Value)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_address2_field">
    <div id="ecAnonymousPaymentInfoForm_address2_label" class="formLabel" >Address2 </div>
    <div id="ecAnonymousPaymentInfoForm_address2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="address2" value="<%=WebUtil.display(_address2Value)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_city_field">
    <div id="ecAnonymousPaymentInfoForm_city_label" class="formLabel" >City </div>
    <div id="ecAnonymousPaymentInfoForm_city_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="city" value="<%=WebUtil.display(_cityValue)%>"/>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousPaymentInfoForm_state_field">
    <div id="ecAnonymousPaymentInfoForm_state_label" class="formLabel" >State </div>
    <div id="ecAnonymousPaymentInfoForm_state_dropdown" class="formFieldDropDown" >       
        <select id="field" name="state">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _stateValue)%>>XX</option-->
        <option value="New York" <%=HtmlUtil.getOptionSelect("New York", _stateValue)%>>New York</option>
        <option value="California" <%=HtmlUtil.getOptionSelect("California", _stateValue)%>>California</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_zip_field">
    <div id="ecAnonymousPaymentInfoForm_zip_label" class="formLabel" >Zip </div>
    <div id="ecAnonymousPaymentInfoForm_zip_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="zip" value="<%=WebUtil.display(_zipValue)%>"/>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousPaymentInfoForm_country_field">
    <div id="ecAnonymousPaymentInfoForm_country_label" class="formLabel" >Country </div>
    <div id="ecAnonymousPaymentInfoForm_country_dropdown" class="formFieldDropDown" >       
        <select id="field" name="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        <option value="USA" <%=HtmlUtil.getOptionSelect("USA", _countryValue)%>>USA</option>
        <option value="Canada" <%=HtmlUtil.getOptionSelect("Canada", _countryValue)%>>Canada</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousPaymentInfoForm_paymentType_field">
    <div id="ecAnonymousPaymentInfoForm_paymentType_label" class="formLabel" >Payment Type </div>
    <div id="ecAnonymousPaymentInfoForm_paymentType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="paymentType" value="<%=WebUtil.display(_payment_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_paymentNum_field">
    <div id="ecAnonymousPaymentInfoForm_paymentNum_label" class="formLabel" >Payment Num </div>
    <div id="ecAnonymousPaymentInfoForm_paymentNum_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="paymentNum" value="<%=WebUtil.display(_payment_numValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_paymentExpireMonth_field">
    <div id="ecAnonymousPaymentInfoForm_paymentExpireMonth_label" class="formLabel" >Payment Expire Month </div>
    <div id="ecAnonymousPaymentInfoForm_paymentExpireMonth_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="paymentExpireMonth" value="<%=WebUtil.display(_payment_expire_monthValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_paymentExpireYear_field">
    <div id="ecAnonymousPaymentInfoForm_paymentExpireYear_label" class="formLabel" >Payment Expire Year </div>
    <div id="ecAnonymousPaymentInfoForm_paymentExpireYear_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="paymentExpireYear" value="<%=WebUtil.display(_payment_expire_yearValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousPaymentInfoForm_paymentExtraNum_field">
    <div id="ecAnonymousPaymentInfoForm_paymentExtraNum_label" class="formLabel" >Payment Extra Num </div>
    <div id="ecAnonymousPaymentInfoForm_paymentExtraNum_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="paymentExtraNum" value="<%=WebUtil.display(_payment_extra_numValue)%>"/>
    </div>      
	</div><div class="clear"></div>



        <div id="ecAnonymousPaymentInfoForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousPaymentInfoForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecAnonymousPaymentInfoForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAnonymousPaymentInfoDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousPaymentInfo _EcAnonymousPaymentInfo = (EcAnonymousPaymentInfo) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousPaymentInfo.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAnonymousUserId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getMiddleInitial()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAddress1()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getAddress2()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getCity()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getState()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getZip()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getCountry()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentType()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentNum()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireMonth()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExpireYear()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getPaymentExtraNum()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousPaymentInfo.getTimeCreated()) %></td>


<td>
<form name="ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>" method="get" action="/v_ec_anonymous_payment_info_edit.html" >
    <a href="javascript:document.ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousPaymentInfo.getId() %>">
</form>
<form name="ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>2" method="get" action="/v_ec_anonymous_payment_info_edit2.html" >
    <a href="javascript:document.ecAnonymousPaymentInfoForm<%=_EcAnonymousPaymentInfo.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousPaymentInfo.getId() %>">
</form>

</td>
<td>
<a href="/ecAnonymousPaymentInfoAction.html?del=true&id=<%=_EcAnonymousPaymentInfo.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>