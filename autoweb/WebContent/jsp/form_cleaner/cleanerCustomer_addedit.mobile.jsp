<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

	// This is ugrly not matured change. Just added to load previously entered values and put back to the fields. 
	boolean isEdit = false;	
    Map reqParams = (Map) request.getAttribute("k_previous_request_params");
    if ( reqParams == null) {
        reqParams = (Map) request.getAttribute("k_reserved_params");
    } else {
        isEdit = true;
    }

	String command = request.getParameter("cmd");

    String idStr  = "0";
    CleanerCustomer _CleanerCustomer = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerCustomer = CleanerCustomerDS.getInstance().getById(id);
		if ( _CleanerCustomer == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerCustomer = new CleanerCustomer();// CleanerCustomerDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerCustomer == null) _CleanerCustomer = new CleanerCustomer();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_customer_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _autosite_user_idValue= (reqParams.get("autositeUserId")==null?WebUtil.display(_CleanerCustomer.getAutositeUserId()):WebUtil.display((String)reqParams.get("autositeUserId")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_CleanerCustomer.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _phoneValue= (reqParams.get("phone")==null?WebUtil.display(_CleanerCustomer.getPhone()):WebUtil.display((String)reqParams.get("phone")));
    String _phone2Value= (reqParams.get("phone2")==null?WebUtil.display(_CleanerCustomer.getPhone2()):WebUtil.display((String)reqParams.get("phone2")));
    String _phone_preferredValue= (reqParams.get("phonePreferred")==null?WebUtil.display(_CleanerCustomer.getPhonePreferred()):WebUtil.display((String)reqParams.get("phonePreferred")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_CleanerCustomer.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_CleanerCustomer.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_CleanerCustomer.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _addressValue= (reqParams.get("address")==null?WebUtil.display(_CleanerCustomer.getAddress()):WebUtil.display((String)reqParams.get("address")));
    String _aptValue= (reqParams.get("apt")==null?WebUtil.display(_CleanerCustomer.getApt()):WebUtil.display((String)reqParams.get("apt")));
    String _cityValue= (reqParams.get("city")==null?WebUtil.display(_CleanerCustomer.getCity()):WebUtil.display((String)reqParams.get("city")));
    String _stateValue= (reqParams.get("state")==null?WebUtil.display(_CleanerCustomer.getState()):WebUtil.display((String)reqParams.get("state")));
    String _zipValue= (reqParams.get("zip")==null?WebUtil.display(_CleanerCustomer.getZip()):WebUtil.display((String)reqParams.get("zip")));
    String _customer_typeValue= (reqParams.get("customerType")==null?WebUtil.display(_CleanerCustomer.getCustomerType()):WebUtil.display((String)reqParams.get("customerType")));
    String _pay_typeValue= (reqParams.get("payType")==null?WebUtil.display(_CleanerCustomer.getPayType()):WebUtil.display((String)reqParams.get("payType")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_CleanerCustomer.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _remote_ipValue= (reqParams.get("remoteIp")==null?WebUtil.display(_CleanerCustomer.getRemoteIp()):WebUtil.display((String)reqParams.get("remoteIp")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_CleanerCustomer.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _pickup_noteValue= (reqParams.get("pickupNote")==null?WebUtil.display(_CleanerCustomer.getPickupNote()):WebUtil.display((String)reqParams.get("pickupNote")));
    String _delivery_noteValue= (reqParams.get("deliveryNote")==null?WebUtil.display(_CleanerCustomer.getDeliveryNote()):WebUtil.display((String)reqParams.get("deliveryNote")));
    String _disabledValue= (reqParams.get("disabled")==null?WebUtil.display(_CleanerCustomer.getDisabled()):WebUtil.display((String)reqParams.get("disabled")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_CleanerCustomer.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _pickup_delivery_disallowedValue= (reqParams.get("pickupDeliveryDisallowed")==null?WebUtil.display(_CleanerCustomer.getPickupDeliveryDisallowed()):WebUtil.display((String)reqParams.get("pickupDeliveryDisallowed")));
    String _handle_instValue= (reqParams.get("handleInst")==null?WebUtil.display(_CleanerCustomer.getHandleInst()):WebUtil.display((String)reqParams.get("handleInst")));

    String pagestamp = "cleaner_customer_" + System.nanoTime();
%> 

<br>
<div id="cleanerCustomerForm" class="formFrame">
<div id="cleanerCustomerFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerCustomerForm_Form" method="POST" action="/cleanerCustomerAction.html" id="cleanerCustomerForm_Form">





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

<div class="submitFrame">

    <div id="cleanerCustomerForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerCustomerForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerCustomerForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerCustomerForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerCustomerForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerCustomerForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerCustomerForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="cleanerCustomerForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="cleanerCustomerForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerCustomer.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">
<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_cleaner_customer_home.html">home</a> | <a href="/v_cleaner_customer_home.html">home</a> | <a href="/v_cleaner_customer_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerCustomer = new ArrayList();
	CleanerCustomerDS ds_CleanerCustomer = CleanerCustomerDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerCustomer = ds_CleanerCustomer.getAll();
	else		
    	list_CleanerCustomer = ds_CleanerCustomer.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerCustomer, numDisplayInPage, listPage);

	list_CleanerCustomer = PagingUtil.getPagedList(pagingInfo, list_CleanerCustomer);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Autosite User Id </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Phone </td> 
    <td class="columnTitle">  Phone2 </td> 
    <td class="columnTitle">  Phone Preferred </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Address </td> 
    <td class="columnTitle">  Apt </td> 
    <td class="columnTitle">  City </td> 
    <td class="columnTitle">  State </td> 
    <td class="columnTitle">  Zip </td> 
    <td class="columnTitle">  Customer Type </td> 
    <td class="columnTitle">  Pay Type </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Remote Ip </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Pickup Note </td> 
    <td class="columnTitle">  Delivery Note </td> 
    <td class="columnTitle">  Disabled </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Pickup Delivery Disallowed </td> 
    <td class="columnTitle">  Handle Inst </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerCustomer.iterator();iter.hasNext();) {
        CleanerCustomer o_CleanerCustomer = (CleanerCustomer) iter.next();
%>

<TR id="tableRow<%= o_CleanerCustomer.getId()%>">
    <td> <%= o_CleanerCustomer.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerCustomer.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerCustomer.getAutositeUserId()  %> </td>
	<td> <%= o_CleanerCustomer.getEmail()  %> </td>
	<td> <%= o_CleanerCustomer.getPhone()  %> </td>
	<td> <%= o_CleanerCustomer.getPhone2()  %> </td>
	<td> <%= o_CleanerCustomer.getPhonePreferred()  %> </td>
	<td> <%= o_CleanerCustomer.getTitle()  %> </td>
	<td> <%= o_CleanerCustomer.getLastName()  %> </td>
	<td> <%= o_CleanerCustomer.getFirstName()  %> </td>
	<td> <%= o_CleanerCustomer.getAddress()  %> </td>
	<td> <%= o_CleanerCustomer.getApt()  %> </td>
	<td> <%= o_CleanerCustomer.getCity()  %> </td>
	<td> <%= o_CleanerCustomer.getState()  %> </td>
	<td> <%= o_CleanerCustomer.getZip()  %> </td>
	<td> <%= o_CleanerCustomer.getCustomerType()  %> </td>
	<td> <%= o_CleanerCustomer.getPayType()  %> </td>
	<td> <%= o_CleanerCustomer.getTimeCreated()  %> </td>
	<td> <%= o_CleanerCustomer.getRemoteIp()  %> </td>
	<td> <%= o_CleanerCustomer.getNote()  %> </td>
	<td> <%= o_CleanerCustomer.getPickupNote()  %> </td>
	<td> <%= o_CleanerCustomer.getDeliveryNote()  %> </td>
	<td> <%= o_CleanerCustomer.getDisabled()  %> </td>
	<td> <%= o_CleanerCustomer.getTimeUpdated()  %> </td>
	<td> <%= o_CleanerCustomer.getPickupDeliveryDisallowed()  %> </td>
	<td> <%= o_CleanerCustomer.getHandleInst()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_customer_form('<%=o_CleanerCustomer.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerCustomerAction.html?del=true&id=<%=o_CleanerCustomer.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerCustomer.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_customer_form('<%=o_CleanerCustomer.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function open_edit_cleaner_customer_form(target){
		location.href='/v_cleaner_customer_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_customer_form(target){
		javascript:sendFormAjaxSimple('/cleanerCustomerAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerCustomerAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_back_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_ext_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=ext<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
</script>
