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
	if (cancelPage == null) cancelPage = "autosite_session_context_entity_home";

    String _serialValue= WebUtil.display((String)reqParams.get("serial"));
    String _is_loginValue= WebUtil.display((String)reqParams.get("isLogin"));
    String _time_loginValue= WebUtil.display((String)reqParams.get("timeLogin"));
    String _time_last_accessValue= WebUtil.display((String)reqParams.get("timeLastAccess"));
    String _login_user_idValue= WebUtil.display((String)reqParams.get("loginUserId"));
    String _session_typeValue= WebUtil.display((String)reqParams.get("sessionType"));
    String _remote_device_idValue= WebUtil.display((String)reqParams.get("remoteDeviceId"));
    String _remote_ipValue= WebUtil.display((String)reqParams.get("remoteIp"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_autosite_session_context_entity_home.html"> AutositeSessionContextEntity Home </a>
<%
	
	List list = null;
	list = AutositeSessionContextEntityDS.getInstance().getBySiteId(site.getId());

%>

<div id="autositeSessionContextEntityList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		AutositeSessionContextEntity _AutositeSessionContextEntity = (AutositeSessionContextEntity) iter.next();	
%>

	<div id="autositeSessionContextEntityFrame<%=_AutositeSessionContextEntity.getId() %>" >

		<div id="serial" >
			serial:<%= _AutositeSessionContextEntity.getSerial() %>
		</div>
		<div id="isLogin" >
			isLogin:<%= _AutositeSessionContextEntity.getIsLogin() %>
		</div>
		<div id="timeLogin" >
			timeLogin:<%= _AutositeSessionContextEntity.getTimeLogin() %>
		</div>
		<div id="timeLastAccess" >
			timeLastAccess:<%= _AutositeSessionContextEntity.getTimeLastAccess() %>
		</div>
		<div id="loginUserId" >
			loginUserId:<%= _AutositeSessionContextEntity.getLoginUserId() %>
		</div>
		<div id="sessionType" >
			sessionType:<%= _AutositeSessionContextEntity.getSessionType() %>
		</div>
		<div id="remoteDeviceId" >
			remoteDeviceId:<%= _AutositeSessionContextEntity.getRemoteDeviceId() %>
		</div>
		<div id="remoteIp" >
			remoteIp:<%= _AutositeSessionContextEntity.getRemoteIp() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _AutositeSessionContextEntity.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _AutositeSessionContextEntity.getTimeUpdated() %>
		</div>
		<div>
		<a id="autositeSessionContextEntityDeleteButton" href="javascript:deleteThis('/autositeSessionContextEntityAction.html',<%= _AutositeSessionContextEntity.getId()%>,'autositeSessionContextEntityFrame<%=_AutositeSessionContextEntity.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="autositeSessionContextEntityForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeSessionContextEntityFormAdd" method="POST" action="/autositeSessionContextEntityAction.html" id="autositeSessionContextEntityFormAdd">

	<div id="autositeSessionContextEntityForm_serial_field">
    <div id="autositeSessionContextEntityForm_serial_label" class="formLabel" >Serial </div>
    <div id="autositeSessionContextEntityForm_serial_text" class="formFieldText" >       
        <input class="field" id="_ffd_serial" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSessionContextEntityForm_isLogin_field">
    <div id="autositeSessionContextEntityForm_isLogin_label" class="formLabel" >Is Login </div>
    <div id="autositeSessionContextEntityForm_isLogin_text" class="formFieldText" >       
        <input class="field" id="_ffd_isLogin" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSessionContextEntityForm_timeLogin_field">
    <div id="autositeSessionContextEntityForm_timeLogin_label" class="formLabel" >Time Login </div>
    <div id="autositeSessionContextEntityForm_timeLogin_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeLogin" type="text" size="70" name="timeLogin" value="<%=WebUtil.display(_time_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSessionContextEntityForm_timeLastAccess_field">
    <div id="autositeSessionContextEntityForm_timeLastAccess_label" class="formLabel" >Time Last Access </div>
    <div id="autositeSessionContextEntityForm_timeLastAccess_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeLastAccess" type="text" size="70" name="timeLastAccess" value="<%=WebUtil.display(_time_last_accessValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSessionContextEntityForm_loginUserId_field">
    <div id="autositeSessionContextEntityForm_loginUserId_label" class="formLabel" >Login User Id </div>
    <div id="autositeSessionContextEntityForm_loginUserId_text" class="formFieldText" >       
        <input class="field" id="_ffd_loginUserId" type="text" size="70" name="loginUserId" value="<%=WebUtil.display(_login_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSessionContextEntityForm_sessionType_field">
    <div id="autositeSessionContextEntityForm_sessionType_label" class="formLabel" >Session Type </div>
    <div id="autositeSessionContextEntityForm_sessionType_text" class="formFieldText" >       
        <input class="field" id="_ffd_sessionType" type="text" size="70" name="sessionType" value="<%=WebUtil.display(_session_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSessionContextEntityForm_remoteDeviceId_field">
    <div id="autositeSessionContextEntityForm_remoteDeviceId_label" class="formLabel" >Remote Device Id </div>
    <div id="autositeSessionContextEntityForm_remoteDeviceId_text" class="formFieldText" >       
        <input class="field" id="_ffd_remoteDeviceId" type="text" size="70" name="remoteDeviceId" value="<%=WebUtil.display(_remote_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSessionContextEntityForm_remoteIp_field">
    <div id="autositeSessionContextEntityForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="autositeSessionContextEntityForm_remoteIp_text" class="formFieldText" >       
        <input class="field" id="_ffd_remoteIp" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="autositeSessionContextEntityFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/autositeSessionContextEntityAction.html', 'autositeSessionContextEntityFormAdd', 'formSubmit_ajax', 'autositeSessionContextEntity');">Submit</a>
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
	document.getElementById("resultDisplayAutositeSessionContextEntity").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('autositeSessionContextEntityFormAddDis','/autositeSessionContextEntityAction.html', 'resultDisplayAutositeSessionContextEntity', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="autositeSessionContextEntityFormAddDis" method="POST" action="/autositeSessionContextEntityAction.html" id="autositeSessionContextEntityFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Serial</div>
        <input class="field" id="serial" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Login</div>
        <input class="field" id="isLogin" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Login</div>
        <input class="field" id="timeLogin" type="text" size="70" name="timeLogin" value="<%=WebUtil.display(_time_loginValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Last Access</div>
        <input class="field" id="timeLastAccess" type="text" size="70" name="timeLastAccess" value="<%=WebUtil.display(_time_last_accessValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Login User Id</div>
        <input class="field" id="loginUserId" type="text" size="70" name="loginUserId" value="<%=WebUtil.display(_login_user_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Session Type</div>
        <input class="field" id="sessionType" type="text" size="70" name="sessionType" value="<%=WebUtil.display(_session_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Remote Device Id</div>
        <input class="field" id="remoteDeviceId" type="text" size="70" name="remoteDeviceId" value="<%=WebUtil.display(_remote_device_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Remote Ip</div>
        <input class="field" id="remoteIp" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('autositeSessionContextEntityFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayAutositeSessionContextEntity"></span>
<a href="javascript:backToXX('autositeSessionContextEntityFormAddDis','resultDisplayAutositeSessionContextEntity')">show back</a><br>
<hr/>
