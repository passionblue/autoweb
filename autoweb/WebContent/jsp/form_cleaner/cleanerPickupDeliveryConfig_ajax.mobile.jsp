<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sun Mar 15 13:54:46 EDT 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_pickup_delivery_config_home";

    String _location_idValue= WebUtil.display((String)reqParams.get("locationId"));
    String _apply_all_locationsValue= WebUtil.display((String)reqParams.get("applyAllLocations"));
    String _disable_web_requestValue= WebUtil.display((String)reqParams.get("disableWebRequest"));
    String _disallow_anonymous_requestValue= WebUtil.display((String)reqParams.get("disallowAnonymousRequest"));
    String _require_customer_registerValue= WebUtil.display((String)reqParams.get("requireCustomerRegister"));
    String _require_customer_loginValue= WebUtil.display((String)reqParams.get("requireCustomerLogin"));
%> 

<a href="/v_cleaner_pickup_delivery_config_home.html"> CleanerPickupDeliveryConfig Home </a>
<%
	
	List list = null;
	list = CleanerPickupDeliveryConfigDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerPickupDeliveryConfigList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerPickupDeliveryConfig _CleanerPickupDeliveryConfig = (CleanerPickupDeliveryConfig) iter.next();	
%>

	<div id="cleanerPickupDeliveryConfigFrame<%=_CleanerPickupDeliveryConfig.getId() %>" >

		<div id="locationId" >
			locationId:<%= _CleanerPickupDeliveryConfig.getLocationId() %>
		</div>
		<div id="applyAllLocations" >
			applyAllLocations:<%= _CleanerPickupDeliveryConfig.getApplyAllLocations() %>
		</div>
		<div id="disableWebRequest" >
			disableWebRequest:<%= _CleanerPickupDeliveryConfig.getDisableWebRequest() %>
		</div>
		<div id="disallowAnonymousRequest" >
			disallowAnonymousRequest:<%= _CleanerPickupDeliveryConfig.getDisallowAnonymousRequest() %>
		</div>
		<div id="requireCustomerRegister" >
			requireCustomerRegister:<%= _CleanerPickupDeliveryConfig.getRequireCustomerRegister() %>
		</div>
		<div id="requireCustomerLogin" >
			requireCustomerLogin:<%= _CleanerPickupDeliveryConfig.getRequireCustomerLogin() %>
		</div>
		<div>
		<a id="cleanerPickupDeliveryConfigDeleteButton" href="javascript:deleteThis('/cleanerPickupDeliveryConfigAction.html',<%= _CleanerPickupDeliveryConfig.getId()%>,'cleanerPickupDeliveryConfigFrame<%=_CleanerPickupDeliveryConfig.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerPickupDeliveryConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerPickupDeliveryConfigFormAdd" method="POST" action="/cleanerPickupDeliveryConfigAction.html" id="cleanerPickupDeliveryConfigFormAdd">

	<div id="cleanerPickupDeliveryConfigForm_locationId_field">
    <div id="cleanerPickupDeliveryConfigForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerPickupDeliveryConfigForm_locationId_text" class="formFieldText" >       
        <input class="field" id="_ffd_locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryConfigForm_applyAllLocations_field">
    <div id="cleanerPickupDeliveryConfigForm_applyAllLocations_label" class="formLabel" >Apply All Locations </div>
    <div id="cleanerPickupDeliveryConfigForm_applyAllLocations_dropdown" class="formFieldDropDown" >       
        <select name="applyAllLocations" id="_ffd_applyAllLocations">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _apply_all_locationsValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _apply_all_locationsValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryConfigForm_disableWebRequest_field">
    <div id="cleanerPickupDeliveryConfigForm_disableWebRequest_label" class="formLabel" >Disable Web Request </div>
    <div id="cleanerPickupDeliveryConfigForm_disableWebRequest_dropdown" class="formFieldDropDown" >       
        <select name="disableWebRequest" id="_ffd_disableWebRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disable_web_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disable_web_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_field">
    <div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_label" class="formLabel" >Disallow Anonymous Request </div>
    <div id="cleanerPickupDeliveryConfigForm_disallowAnonymousRequest_dropdown" class="formFieldDropDown" >       
        <select name="disallowAnonymousRequest" id="_ffd_disallowAnonymousRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disallow_anonymous_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disallow_anonymous_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_field">
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_label" class="formLabel" >Require Customer Register </div>
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerRegister_dropdown" class="formFieldDropDown" >       
        <select name="requireCustomerRegister" id="_ffd_requireCustomerRegister">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_registerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_registerValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_field">
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_label" class="formLabel" >Require Customer Login </div>
    <div id="cleanerPickupDeliveryConfigForm_requireCustomerLogin_dropdown" class="formFieldDropDown" >       
        <select name="requireCustomerLogin" id="_ffd_requireCustomerLogin">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_loginValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_loginValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerPickupDeliveryConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerPickupDeliveryConfigAction.html', 'cleanerPickupDeliveryConfigFormAdd', 'formSubmit_ajax', 'cleanerPickupDeliveryConfig');">Submit</a>
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
	document.getElementById("resultDisplayCleanerPickupDeliveryConfig").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerPickupDeliveryConfigFormAddDis','/cleanerPickupDeliveryConfigAction.html', 'resultDisplayCleanerPickupDeliveryConfig', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerPickupDeliveryConfigFormAddDis" method="POST" action="/cleanerPickupDeliveryConfigAction.html" id="cleanerPickupDeliveryConfigFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Location Id</div>
        <input class="field" id="locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Apply All Locations</div>
        <select name="applyAllLocations" id="applyAllLocations">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _apply_all_locationsValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _apply_all_locationsValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disable Web Request</div>
        <select name="disableWebRequest" id="disableWebRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disable_web_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disable_web_requestValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disallow Anonymous Request</div>
        <select name="disallowAnonymousRequest" id="disallowAnonymousRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disallow_anonymous_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disallow_anonymous_requestValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Require Customer Register</div>
        <select name="requireCustomerRegister" id="requireCustomerRegister">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_registerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_registerValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Require Customer Login</div>
        <select name="requireCustomerLogin" id="requireCustomerLogin">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _require_customer_loginValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _require_customer_loginValue)%>>Yes</option>
        </select><span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerPickupDeliveryConfigFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerPickupDeliveryConfig"></span>
<a href="javascript:backToXX('cleanerPickupDeliveryConfigFormAddDis','resultDisplayCleanerPickupDeliveryConfig')">show back</a><br>
<hr/>
