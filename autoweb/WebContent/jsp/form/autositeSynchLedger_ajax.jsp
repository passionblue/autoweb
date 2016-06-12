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
	if (cancelPage == null) cancelPage = "autosite_synch_ledger_home";

    String _device_idValue= WebUtil.display((String)reqParams.get("deviceId"));
    String _original_ledger_idValue= WebUtil.display((String)reqParams.get("originalLedgerId"));
    String _scopeValue= WebUtil.display((String)reqParams.get("scope"));
    String _targetValue= WebUtil.display((String)reqParams.get("target"));
    String _remote_tokenValue= WebUtil.display((String)reqParams.get("remoteToken"));
    String _object_idValue= WebUtil.display((String)reqParams.get("objectId"));
    String _synch_idValue= WebUtil.display((String)reqParams.get("synchId"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
%> 

<a href="/v_autosite_synch_ledger_home.html"> AutositeSynchLedger Home </a>
<%
	
	List list = null;
	list = AutositeSynchLedgerDS.getInstance().getBySiteId(site.getId());

%>

<div id="autositeSynchLedgerList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		AutositeSynchLedger _AutositeSynchLedger = (AutositeSynchLedger) iter.next();	
%>

	<div id="autositeSynchLedgerFrame<%=_AutositeSynchLedger.getId() %>" >

		<div id="deviceId" >
			deviceId:<%= _AutositeSynchLedger.getDeviceId() %>
		</div>
		<div id="originalLedgerId" >
			originalLedgerId:<%= _AutositeSynchLedger.getOriginalLedgerId() %>
		</div>
		<div id="scope" >
			scope:<%= _AutositeSynchLedger.getScope() %>
		</div>
		<div id="target" >
			target:<%= _AutositeSynchLedger.getTarget() %>
		</div>
		<div id="remoteToken" >
			remoteToken:<%= _AutositeSynchLedger.getRemoteToken() %>
		</div>
		<div id="objectId" >
			objectId:<%= _AutositeSynchLedger.getObjectId() %>
		</div>
		<div id="synchId" >
			synchId:<%= _AutositeSynchLedger.getSynchId() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _AutositeSynchLedger.getTimeCreated() %>
		</div>
		<div>
		<a id="autositeSynchLedgerDeleteButton" href="javascript:deleteThis('/autositeSynchLedgerAction.html',<%= _AutositeSynchLedger.getId()%>,'autositeSynchLedgerFrame<%=_AutositeSynchLedger.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="autositeSynchLedgerForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeSynchLedgerFormAdd" method="POST" action="/autositeSynchLedgerAction.html" id="autositeSynchLedgerFormAdd">

	<div id="autositeSynchLedgerForm_deviceId_field">
    <div id="autositeSynchLedgerForm_deviceId_label" class="formLabel" >Device Id </div>
    <div id="autositeSynchLedgerForm_deviceId_text" class="formFieldText" >       
        <input class="field" id="_ffd_deviceId" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSynchLedgerForm_originalLedgerId_field">
    <div id="autositeSynchLedgerForm_originalLedgerId_label" class="formLabel" >Original Ledger Id </div>
    <div id="autositeSynchLedgerForm_originalLedgerId_text" class="formFieldText" >       
        <input class="field" id="_ffd_originalLedgerId" type="text" size="70" name="originalLedgerId" value="<%=WebUtil.display(_original_ledger_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSynchLedgerForm_scope_field">
    <div id="autositeSynchLedgerForm_scope_label" class="formLabel" >Scope </div>
    <div id="autositeSynchLedgerForm_scope_text" class="formFieldText" >       
        <input class="field" id="_ffd_scope" type="text" size="70" name="scope" value="<%=WebUtil.display(_scopeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSynchLedgerForm_target_field">
    <div id="autositeSynchLedgerForm_target_label" class="formLabel" >Target </div>
    <div id="autositeSynchLedgerForm_target_text" class="formFieldText" >       
        <input class="field" id="_ffd_target" type="text" size="70" name="target" value="<%=WebUtil.display(_targetValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSynchLedgerForm_remoteToken_field">
    <div id="autositeSynchLedgerForm_remoteToken_label" class="formLabel" >Remote Token </div>
    <div id="autositeSynchLedgerForm_remoteToken_text" class="formFieldText" >       
        <input class="field" id="_ffd_remoteToken" type="text" size="70" name="remoteToken" value="<%=WebUtil.display(_remote_tokenValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSynchLedgerForm_objectId_field">
    <div id="autositeSynchLedgerForm_objectId_label" class="formLabel" >Object Id </div>
    <div id="autositeSynchLedgerForm_objectId_text" class="formFieldText" >       
        <input class="field" id="_ffd_objectId" type="text" size="70" name="objectId" value="<%=WebUtil.display(_object_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeSynchLedgerForm_synchId_field">
    <div id="autositeSynchLedgerForm_synchId_label" class="formLabel" >Synch Id </div>
    <div id="autositeSynchLedgerForm_synchId_text" class="formFieldText" >       
        <input class="field" id="_ffd_synchId" type="text" size="70" name="synchId" value="<%=WebUtil.display(_synch_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="autositeSynchLedgerFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/autositeSynchLedgerAction.html', 'autositeSynchLedgerFormAdd', 'formSubmit_ajax', 'autositeSynchLedger');">Submit</a>
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
	document.getElementById("resultDisplayAutositeSynchLedger").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('autositeSynchLedgerFormAddDis','/autositeSynchLedgerAction.html', 'resultDisplayAutositeSynchLedger', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="autositeSynchLedgerFormAddDis" method="POST" action="/autositeSynchLedgerAction.html" id="autositeSynchLedgerFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Device Id</div>
        <input class="field" id="deviceId" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Original Ledger Id</div>
        <input class="field" id="originalLedgerId" type="text" size="70" name="originalLedgerId" value="<%=WebUtil.display(_original_ledger_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Scope</div>
        <input class="field" id="scope" type="text" size="70" name="scope" value="<%=WebUtil.display(_scopeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Target</div>
        <input class="field" id="target" type="text" size="70" name="target" value="<%=WebUtil.display(_targetValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Remote Token</div>
        <input class="field" id="remoteToken" type="text" size="70" name="remoteToken" value="<%=WebUtil.display(_remote_tokenValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Object Id</div>
        <input class="field" id="objectId" type="text" size="70" name="objectId" value="<%=WebUtil.display(_object_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Synch Id</div>
        <input class="field" id="synchId" type="text" size="70" name="synchId" value="<%=WebUtil.display(_synch_idValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('autositeSynchLedgerFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayAutositeSynchLedger"></span>
<a href="javascript:backToXX('autositeSynchLedgerFormAddDis','resultDisplayAutositeSynchLedger')">show back</a><br>
<hr/>
