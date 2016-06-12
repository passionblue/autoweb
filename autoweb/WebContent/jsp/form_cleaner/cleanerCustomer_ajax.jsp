<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_customer_home";

    String _autosite_user_idValue= WebUtil.display((String)reqParams.get("autositeUserId"));
    String _emailValue= WebUtil.display((String)reqParams.get("email"));
    String _phoneValue= WebUtil.display((String)reqParams.get("phone"));
    String _phone2Value= WebUtil.display((String)reqParams.get("phone2"));
    String _phone_preferredValue= WebUtil.display((String)reqParams.get("phonePreferred"));
    String _titleValue= WebUtil.display((String)reqParams.get("title"));
    String _last_nameValue= WebUtil.display((String)reqParams.get("lastName"));
    String _first_nameValue= WebUtil.display((String)reqParams.get("firstName"));
    String _addressValue= WebUtil.display((String)reqParams.get("address"));
    String _aptValue= WebUtil.display((String)reqParams.get("apt"));
    String _cityValue= WebUtil.display((String)reqParams.get("city"));
    String _stateValue= WebUtil.display((String)reqParams.get("state"));
    String _zipValue= WebUtil.display((String)reqParams.get("zip"));
    String _customer_typeValue= WebUtil.display((String)reqParams.get("customerType"));
    String _pay_typeValue= WebUtil.display((String)reqParams.get("payType"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _remote_ipValue= WebUtil.display((String)reqParams.get("remoteIp"));
    String _noteValue= WebUtil.display((String)reqParams.get("note"));
    String _pickup_noteValue= WebUtil.display((String)reqParams.get("pickupNote"));
    String _delivery_noteValue= WebUtil.display((String)reqParams.get("deliveryNote"));
    String _disabledValue= WebUtil.display((String)reqParams.get("disabled"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
    String _pickup_delivery_disallowedValue= WebUtil.display((String)reqParams.get("pickupDeliveryDisallowed"));
    String _handle_instValue= WebUtil.display((String)reqParams.get("handleInst"));
%> 

<a href="/v_cleaner_customer_home.html"> CleanerCustomer Home </a>
<%
	
	List list = null;
	list = CleanerCustomerDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerCustomerList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerCustomer _CleanerCustomer = (CleanerCustomer) iter.next();	
%>

	<div id="cleanerCustomerFrame<%=_CleanerCustomer.getId() %>" >

		<div id="autositeUserId" >
			autositeUserId:<%= _CleanerCustomer.getAutositeUserId() %>
		</div>
		<div id="email" >
			email:<%= _CleanerCustomer.getEmail() %>
		</div>
		<div id="phone" >
			phone:<%= _CleanerCustomer.getPhone() %>
		</div>
		<div id="phone2" >
			phone2:<%= _CleanerCustomer.getPhone2() %>
		</div>
		<div id="phonePreferred" >
			phonePreferred:<%= _CleanerCustomer.getPhonePreferred() %>
		</div>
		<div id="title" >
			title:<%= _CleanerCustomer.getTitle() %>
		</div>
		<div id="lastName" >
			lastName:<%= _CleanerCustomer.getLastName() %>
		</div>
		<div id="firstName" >
			firstName:<%= _CleanerCustomer.getFirstName() %>
		</div>
		<div id="address" >
			address:<%= _CleanerCustomer.getAddress() %>
		</div>
		<div id="apt" >
			apt:<%= _CleanerCustomer.getApt() %>
		</div>
		<div id="city" >
			city:<%= _CleanerCustomer.getCity() %>
		</div>
		<div id="state" >
			state:<%= _CleanerCustomer.getState() %>
		</div>
		<div id="zip" >
			zip:<%= _CleanerCustomer.getZip() %>
		</div>
		<div id="customerType" >
			customerType:<%= _CleanerCustomer.getCustomerType() %>
		</div>
		<div id="payType" >
			payType:<%= _CleanerCustomer.getPayType() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _CleanerCustomer.getTimeCreated() %>
		</div>
		<div id="remoteIp" >
			remoteIp:<%= _CleanerCustomer.getRemoteIp() %>
		</div>
		<div id="note" >
			note:<%= _CleanerCustomer.getNote() %>
		</div>
		<div id="pickupNote" >
			pickupNote:<%= _CleanerCustomer.getPickupNote() %>
		</div>
		<div id="deliveryNote" >
			deliveryNote:<%= _CleanerCustomer.getDeliveryNote() %>
		</div>
		<div id="disabled" >
			disabled:<%= _CleanerCustomer.getDisabled() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _CleanerCustomer.getTimeUpdated() %>
		</div>
		<div id="pickupDeliveryDisallowed" >
			pickupDeliveryDisallowed:<%= _CleanerCustomer.getPickupDeliveryDisallowed() %>
		</div>
		<div id="handleInst" >
			handleInst:<%= _CleanerCustomer.getHandleInst() %>
		</div>
		<div>
		<a id="cleanerCustomerDeleteButton" href="javascript:deleteThis('/cleanerCustomerAction.html',<%= _CleanerCustomer.getId()%>,'cleanerCustomerFrame<%=_CleanerCustomer.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerCustomerForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerCustomerFormAdd" method="POST" action="/cleanerCustomerAction.html" id="cleanerCustomerFormAdd">

	<div id="cleanerCustomerForm_autositeUserId_field">
    <div id="cleanerCustomerForm_autositeUserId_label" class="formLabel" >Autosite User Id </div>
    <div id="cleanerCustomerForm_autositeUserId_text" class="formFieldText" >       
        <input class="field" id="_ffd_autositeUserId" type="text" size="70" name="autositeUserId" value="<%=WebUtil.display(_autosite_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_email_field">
    <div id="cleanerCustomerForm_email_label" class="formLabel" >Email </div>
    <div id="cleanerCustomerForm_email_text" class="formFieldText" >       
        <input class="field" id="_ffd_email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_phone_field">
    <div id="cleanerCustomerForm_phone_label" class="formLabel" >Phone </div>
    <div id="cleanerCustomerForm_phone_text" class="formFieldText" >       
        <input class="field" id="_ffd_phone" type="text" size="70" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_phone2_field">
    <div id="cleanerCustomerForm_phone2_label" class="formLabel" >Phone2 </div>
    <div id="cleanerCustomerForm_phone2_text" class="formFieldText" >       
        <input class="field" id="_ffd_phone2" type="text" size="70" name="phone2" value="<%=WebUtil.display(_phone2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_phonePreferred_field">
    <div id="cleanerCustomerForm_phonePreferred_label" class="formLabel" >Phone Preferred </div>
    <div id="cleanerCustomerForm_phonePreferred_text" class="formFieldText" >       
        <input class="field" id="_ffd_phonePreferred" type="text" size="70" name="phonePreferred" value="<%=WebUtil.display(_phone_preferredValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_title_field">
    <div id="cleanerCustomerForm_title_label" class="formLabel" >Title </div>
    <div id="cleanerCustomerForm_title_text" class="formFieldText" >       
        <input class="field" id="_ffd_title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_lastName_field">
    <div id="cleanerCustomerForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="cleanerCustomerForm_lastName_text" class="formFieldText" >       
        <input class="field" id="_ffd_lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_firstName_field">
    <div id="cleanerCustomerForm_firstName_label" class="formLabel" >First Name </div>
    <div id="cleanerCustomerForm_firstName_text" class="formFieldText" >       
        <input class="field" id="_ffd_firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_address_field">
    <div id="cleanerCustomerForm_address_label" class="formLabel" >Address </div>
    <div id="cleanerCustomerForm_address_text" class="formFieldText" >       
        <input class="field" id="_ffd_address" type="text" size="70" name="address" value="<%=WebUtil.display(_addressValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_apt_field">
    <div id="cleanerCustomerForm_apt_label" class="formLabel" >Apt </div>
    <div id="cleanerCustomerForm_apt_text" class="formFieldText" >       
        <input class="field" id="_ffd_apt" type="text" size="70" name="apt" value="<%=WebUtil.display(_aptValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_city_field">
    <div id="cleanerCustomerForm_city_label" class="formLabel" >City </div>
    <div id="cleanerCustomerForm_city_text" class="formFieldText" >       
        <input class="field" id="_ffd_city" type="text" size="70" name="city" value="<%=WebUtil.display(_cityValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_state_field">
    <div id="cleanerCustomerForm_state_label" class="formLabel" >State </div>
    <div id="cleanerCustomerForm_state_text" class="formFieldText" >       
        <input class="field" id="_ffd_state" type="text" size="70" name="state" value="<%=WebUtil.display(_stateValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_zip_field">
    <div id="cleanerCustomerForm_zip_label" class="formLabel" >Zip </div>
    <div id="cleanerCustomerForm_zip_text" class="formFieldText" >       
        <input class="field" id="_ffd_zip" type="text" size="70" name="zip" value="<%=WebUtil.display(_zipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_customerType_field">
    <div id="cleanerCustomerForm_customerType_label" class="formLabel" >Customer Type </div>
    <div id="cleanerCustomerForm_customerType_text" class="formFieldText" >       
        <input class="field" id="_ffd_customerType" type="text" size="70" name="customerType" value="<%=WebUtil.display(_customer_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_payType_field">
    <div id="cleanerCustomerForm_payType_label" class="formLabel" >Pay Type </div>
    <div id="cleanerCustomerForm_payType_text" class="formFieldText" >       
        <input class="field" id="_ffd_payType" type="text" size="70" name="payType" value="<%=WebUtil.display(_pay_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_remoteIp_field">
    <div id="cleanerCustomerForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="cleanerCustomerForm_remoteIp_text" class="formFieldText" >       
        <input class="field" id="_ffd_remoteIp" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_note_field">
    <div id="cleanerCustomerForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerCustomerForm_note_text" class="formFieldText" >       
        <input class="field" id="_ffd_note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_pickupNote_field">
    <div id="cleanerCustomerForm_pickupNote_label" class="formLabel" >Pickup Note </div>
    <div id="cleanerCustomerForm_pickupNote_text" class="formFieldText" >       
        <input class="field" id="_ffd_pickupNote" type="text" size="70" name="pickupNote" value="<%=WebUtil.display(_pickup_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_deliveryNote_field">
    <div id="cleanerCustomerForm_deliveryNote_label" class="formLabel" >Delivery Note </div>
    <div id="cleanerCustomerForm_deliveryNote_text" class="formFieldText" >       
        <input class="field" id="_ffd_deliveryNote" type="text" size="70" name="deliveryNote" value="<%=WebUtil.display(_delivery_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_disabled_field">
    <div id="cleanerCustomerForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="cleanerCustomerForm_disabled_text" class="formFieldText" >       
        <input class="field" id="_ffd_disabled" type="text" size="70" name="disabled" value="<%=WebUtil.display(_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_pickupDeliveryDisallowed_field">
    <div id="cleanerCustomerForm_pickupDeliveryDisallowed_label" class="formLabel" >Pickup Delivery Disallowed </div>
    <div id="cleanerCustomerForm_pickupDeliveryDisallowed_text" class="formFieldText" >       
        <input class="field" id="_ffd_pickupDeliveryDisallowed" type="text" size="70" name="pickupDeliveryDisallowed" value="<%=WebUtil.display(_pickup_delivery_disallowedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerForm_handleInst_field">
    <div id="cleanerCustomerForm_handleInst_label" class="formLabel" >Handle Inst </div>
    <div id="cleanerCustomerForm_handleInst_text" class="formFieldText" >       
        <input class="field" id="_ffd_handleInst" type="text" size="70" name="handleInst" value="<%=WebUtil.display(_handle_instValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerCustomerFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerCustomerAction.html', 'cleanerCustomerFormAdd', 'formSubmit_ajax', 'cleanerCustomer');">Submit</a>
        </div> 

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->

<hr>

<br/><br/><br/><br/>

<hr>
<h3> Self hiding pure javascript form</h3>

<script type="text/javascript">

function xmlhttpPostXX(formName, target, dispElementId, dispFieldList,  callback) {
	
	if (document.getElementById(formName) == null){
		alert("Client side Error occurred. Please try again.")
		return;
	}
	
	var parms = getXX(document.getElementById(formName));
	parms += "&ajaxRequest=true&ajaxOut=getlisthtml&ajaxOutArg=last&formfieldlist="+dispFieldList;
	
    var xmlHttpReq = false;
    var self = this;
    
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var strURL = target+ "?" + parms;
    //alert(strURL);
    
    self.xmlHttpReq.open('POST', target, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
    	if (self.xmlHttpReq.readyState == 4) {
        	//alert(self.xmlHttpReq.responseText);
        	fade(formName, 1000, function() {
        		if (callback == null )
        			updatepageXX(dispElementId, dispElementId, self.xmlHttpReq.responseText);
        		else
        			callback(self.xmlHttpReq.responseText);
        	});
        }
    }
    self.xmlHttpReq.send(parms);
}

function updatepageXX(eid, str){
	document.getElementById(eid).innerHTML = str;
}

function getXX(obj) {
	var getstr = "";
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
       
           if (obj.childNodes[i].type == "text") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "password") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "hidden") {
           		alert(obj.childNodes[i].name + "=" + obj.childNodes[i].value);
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "file") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             } else {
                getstr += obj.childNodes[i].name + "=&";
             }
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             }
          }
       }   
       
       if (obj.childNodes[i].tagName == "SELECT") {
           var sel = obj.childNodes[i];
           getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
       }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
       }
    }
	alert(getstr);
    return getstr;
}



function clearFormXX(name) {
	var obj = document.getElementById(name);
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
           if (obj.childNodes[i].type == "text") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "password") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "file") {
               obj.childNodes[i].value = "";
           }
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             } 
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             }
          }
       }   
       if (obj.childNodes[i].tagName == "SELECT") {
			obj.childNodes[i].selectedIndex = 0;
        }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           obj.childNodes[i].value = "";
        }
        
    }
}


function  animateFadeXX(lastTick, eid, timeToFade)
{  
  var curTick = new Date().getTime();
  var elapsedTicks = curTick - lastTick;
 
  var element = document.getElementById(eid);
 
  if(element.FadeTimeLeft <= elapsedTicks)
  {
    element.style.opacity = element.FadeState == 1 ? '1' : '0';
    element.style.filter = 'alpha(opacity = '
        + (element.FadeState == 1 ? '100' : '0') + ')';
    element.FadeState = element.FadeState == 1 ? 2 : -2;
	document.getElementById(eid).style.display = 'none';
	element.callbackAfter(element.callbackArg);
    return;
  }
 
  element.FadeTimeLeft -= elapsedTicks;
  var newOpVal = element.FadeTimeLeft/timeToFade;
  if(element.FadeState == 1)
    newOpVal = 1 - newOpVal;

  element.style.opacity = newOpVal;
  element.style.filter = 'alpha(opacity = ' + (newOpVal*100) + ')';
 
  setTimeout("animateFadeXX(" + curTick + ",'" + eid + "','" + timeToFade + "')", 33);
}


//var  TimeToFade = 1000.0;

function fadeXX(eid, timeToFade, callback, callbackArg)
{
  var element = document.getElementById(eid);
  if(element == null)
    return;
   
  if(element.FadeState == null)
  {
    if(element.style.opacity == null
        || element.style.opacity == ''
        || element.style.opacity == '1')
    {
      element.FadeState = 2;
    }
    else
    {
      element.FadeState = -2;
    }
  }
   
  if(element.FadeState == 1 || element.FadeState == -1)
  {
    element.FadeState = element.FadeState == 1 ? -1 : 1;
    element.FadeTimeLeft = timeToFade - element.FadeTimeLeft;
  }
  else
  {
    element.FadeState = element.FadeState == 2 ? -1 : 1;
    element.FadeTimeLeft = timeToFade;
	
    element.callbackAfter = callback;
    element.callbackArg = callbackArg;
    setTimeout("animateFadeXX(" + new Date().getTime() + ",'" + eid + "','" + timeToFade + "')", 33);
  }  
}

function backToXX(eid, displayFormId){
	document.getElementById(displayFormId).innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

</script>

<script type="text/javascript">

function responseCallback(data){
	document.getElementById("resultDisplayCleanerCustomer").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerCustomerFormAddDis','/cleanerCustomerAction.html', 'resultDisplayCleanerCustomer', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerCustomerFormAddDis" method="POST" action="/cleanerCustomerAction.html" id="cleanerCustomerFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Autosite User Id</div>
        <input class="field" id="autositeUserId" type="text" size="70" name="autositeUserId" value="<%=WebUtil.display(_autosite_user_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Email</div>
        <input class="field" id="email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Phone</div>
        <input class="field" id="phone" type="text" size="70" name="phone" value="<%=WebUtil.display(_phoneValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Phone2</div>
        <input class="field" id="phone2" type="text" size="70" name="phone2" value="<%=WebUtil.display(_phone2Value)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Phone Preferred</div>
        <input class="field" id="phonePreferred" type="text" size="70" name="phonePreferred" value="<%=WebUtil.display(_phone_preferredValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Title</div>
        <input class="field" id="title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Last Name</div>
        <input class="field" id="lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> First Name</div>
        <input class="field" id="firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Address</div>
        <input class="field" id="address" type="text" size="70" name="address" value="<%=WebUtil.display(_addressValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Apt</div>
        <input class="field" id="apt" type="text" size="70" name="apt" value="<%=WebUtil.display(_aptValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> City</div>
        <input class="field" id="city" type="text" size="70" name="city" value="<%=WebUtil.display(_cityValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> State</div>
        <input class="field" id="state" type="text" size="70" name="state" value="<%=WebUtil.display(_stateValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Zip</div>
        <input class="field" id="zip" type="text" size="70" name="zip" value="<%=WebUtil.display(_zipValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Customer Type</div>
        <input class="field" id="customerType" type="text" size="70" name="customerType" value="<%=WebUtil.display(_customer_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pay Type</div>
        <input class="field" id="payType" type="text" size="70" name="payType" value="<%=WebUtil.display(_pay_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Remote Ip</div>
        <input class="field" id="remoteIp" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Note</div>
        <input class="field" id="note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pickup Note</div>
        <input class="field" id="pickupNote" type="text" size="70" name="pickupNote" value="<%=WebUtil.display(_pickup_noteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Delivery Note</div>
        <input class="field" id="deliveryNote" type="text" size="70" name="deliveryNote" value="<%=WebUtil.display(_delivery_noteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disabled</div>
        <input class="field" id="disabled" type="text" size="70" name="disabled" value="<%=WebUtil.display(_disabledValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pickup Delivery Disallowed</div>
        <input class="field" id="pickupDeliveryDisallowed" type="text" size="70" name="pickupDeliveryDisallowed" value="<%=WebUtil.display(_pickup_delivery_disallowedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Handle Inst</div>
        <input class="field" id="handleInst" type="text" size="70" name="handleInst" value="<%=WebUtil.display(_handle_instValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerCustomerFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerCustomer"></span>
<a href="javascript:backToXX('cleanerCustomerFormAddDis','resultDisplayCleanerCustomer')">show back</a><br>
<hr/>
