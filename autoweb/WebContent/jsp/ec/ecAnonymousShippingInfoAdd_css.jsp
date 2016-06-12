<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAnonymousShippingInfo _EcAnonymousShippingInfoDefault = new EcAnonymousShippingInfo();// EcAnonymousShippingInfoDS.getInstance().getDeafult();
    
    String _anonymous_user_idValue= (reqParams.get("anonymousUserId")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getAnonymousUserId()):WebUtil.display((String)reqParams.get("anonymousUserId")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _middle_initialValue= (reqParams.get("middleInitial")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getMiddleInitial()):WebUtil.display((String)reqParams.get("middleInitial")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _address1Value= (reqParams.get("address1")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getAddress1()):WebUtil.display((String)reqParams.get("address1")));
    String _address2Value= (reqParams.get("address2")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getAddress2()):WebUtil.display((String)reqParams.get("address2")));
    String _cityValue= (reqParams.get("city")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getCity()):WebUtil.display((String)reqParams.get("city")));
    String _stateValue= (reqParams.get("state")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getState()):WebUtil.display((String)reqParams.get("state")));
    String _zipValue= (reqParams.get("zip")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getZip()):WebUtil.display((String)reqParams.get("zip")));
    String _countryValue= (reqParams.get("country")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getCountry()):WebUtil.display((String)reqParams.get("country")));
    String _special_instructionValue= (reqParams.get("specialInstruction")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getSpecialInstruction()):WebUtil.display((String)reqParams.get("specialInstruction")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcAnonymousShippingInfoDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="ecAnonymousShippingInfoForm_topArea" class="formTopArea"></div>
<div id="ecAnonymousShippingInfoForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="ecAnonymousShippingInfoForm" method="get" action="/ecAnonymousShippingInfoAction.html" >


	<div id="ecAnonymousShippingInfoForm_anonymousUserId_field">
    <div id="ecAnonymousShippingInfoForm_anonymousUserId_label" class="formLabel" >Anonymous User Id </div>
    <div id="ecAnonymousShippingInfoForm_anonymousUserId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="anonymousUserId" value="<%=WebUtil.display(_anonymous_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_firstName_field">
    <div id="ecAnonymousShippingInfoForm_firstName_label" class="formLabel" >First Name </div>
    <div id="ecAnonymousShippingInfoForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_middleInitial_field">
    <div id="ecAnonymousShippingInfoForm_middleInitial_label" class="formLabel" >Middle Initial </div>
    <div id="ecAnonymousShippingInfoForm_middleInitial_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="middleInitial" value="<%=WebUtil.display(_middle_initialValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_lastName_field">
    <div id="ecAnonymousShippingInfoForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="ecAnonymousShippingInfoForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_address1_field">
    <div id="ecAnonymousShippingInfoForm_address1_label" class="formLabel" >Address1 </div>
    <div id="ecAnonymousShippingInfoForm_address1_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="address1" value="<%=WebUtil.display(_address1Value)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_address2_field">
    <div id="ecAnonymousShippingInfoForm_address2_label" class="formLabel" >Address2 </div>
    <div id="ecAnonymousShippingInfoForm_address2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="address2" value="<%=WebUtil.display(_address2Value)%>"/>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_city_field">
    <div id="ecAnonymousShippingInfoForm_city_label" class="formLabel" >City </div>
    <div id="ecAnonymousShippingInfoForm_city_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="city" value="<%=WebUtil.display(_cityValue)%>"/>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousShippingInfoForm_state_field">
    <div id="ecAnonymousShippingInfoForm_state_label" class="formLabel" >State </div>
    <div id="ecAnonymousShippingInfoForm_state_dropdown" class="formFieldDropDown" >       
        <select id="field" name="state">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _stateValue)%>>XX</option-->
        <option value="New York" <%=HtmlUtil.getOptionSelect("New York", _stateValue)%>>New York</option>
        <option value="California" <%=HtmlUtil.getOptionSelect("California", _stateValue)%>>California</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="ecAnonymousShippingInfoForm_zip_field">
    <div id="ecAnonymousShippingInfoForm_zip_label" class="formLabel" >Zip </div>
    <div id="ecAnonymousShippingInfoForm_zip_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="zip" value="<%=WebUtil.display(_zipValue)%>"/>
    </div>      
	</div><div class="clear"></div>

	<div id="ecAnonymousShippingInfoForm_country_field">
    <div id="ecAnonymousShippingInfoForm_country_label" class="formLabel" >Country </div>
    <div id="ecAnonymousShippingInfoForm_country_dropdown" class="formFieldDropDown" >       
        <select id="field" name="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        <option value="USA" <%=HtmlUtil.getOptionSelect("USA", _countryValue)%>>USA</option>
        <option value="Canada" <%=HtmlUtil.getOptionSelect("Canada", _countryValue)%>>Canada</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecAnonymousShippingInfoForm_specialInstruction_field">
    <div id="ecAnonymousShippingInfoForm_specialInstruction_label" class="formLabel" >Special Instruction </div>
    <div id="ecAnonymousShippingInfoForm_specialInstruction_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="specialInstruction" COLS="50" ROWS="8" ><%=WebUtil.display(_special_instructionValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




        <div id="ecAnonymousShippingInfoForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecAnonymousShippingInfoForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecAnonymousShippingInfoForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAnonymousShippingInfoDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousShippingInfo _EcAnonymousShippingInfo = (EcAnonymousShippingInfo) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousShippingInfo.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAnonymousUserId()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getMiddleInitial()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getLastName()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAddress1()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getAddress2()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getCity()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getState()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getZip()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getCountry()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getSpecialInstruction()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousShippingInfo.getTimeCreated()) %></td>


<td>
<form name="ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>" method="get" action="/v_ec_anonymous_shipping_info_edit.html" >
    <a href="javascript:document.ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousShippingInfo.getId() %>">
</form>
<form name="ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>2" method="get" action="/v_ec_anonymous_shipping_info_edit2.html" >
    <a href="javascript:document.ecAnonymousShippingInfoForm<%=_EcAnonymousShippingInfo.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousShippingInfo.getId() %>">
</form>

</td>
<td>
<a href="/ecAnonymousShippingInfoAction.html?del=true&id=<%=_EcAnonymousShippingInfo.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>