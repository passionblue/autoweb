<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sat Feb 14 00:12:25 EST 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "synk_node_tracker_tx_home";

    String _namespaceValue= WebUtil.display((String)reqParams.get("namespace"));
    String _device_idValue= WebUtil.display((String)reqParams.get("deviceId"));
    String _tx_tokenValue= WebUtil.display((String)reqParams.get("txToken"));
    String _stamp_ackedValue= WebUtil.display((String)reqParams.get("stampAcked"));
    String _stamp_lastValue= WebUtil.display((String)reqParams.get("stampLast"));
    String _num_recordsValue= WebUtil.display((String)reqParams.get("numRecords"));
    String _ipValue= WebUtil.display((String)reqParams.get("ip"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
%> 

<a href="/v_synk_node_tracker_tx_home.html"> SynkNodeTrackerTx Home </a>
<%
	
	List list = null;
	list = SynkNodeTrackerTxDS.getInstance().getBySiteId(site.getId());

%>

<div id="synkNodeTrackerTxList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		SynkNodeTrackerTx _SynkNodeTrackerTx = (SynkNodeTrackerTx) iter.next();	
%>

	<div id="synkNodeTrackerTxFrame<%=_SynkNodeTrackerTx.getId() %>" >

		<div id="namespace" >
			namespace:<%= _SynkNodeTrackerTx.getNamespace() %>
		</div>
		<div id="deviceId" >
			deviceId:<%= _SynkNodeTrackerTx.getDeviceId() %>
		</div>
		<div id="txToken" >
			txToken:<%= _SynkNodeTrackerTx.getTxToken() %>
		</div>
		<div id="stampAcked" >
			stampAcked:<%= _SynkNodeTrackerTx.getStampAcked() %>
		</div>
		<div id="stampLast" >
			stampLast:<%= _SynkNodeTrackerTx.getStampLast() %>
		</div>
		<div id="numRecords" >
			numRecords:<%= _SynkNodeTrackerTx.getNumRecords() %>
		</div>
		<div id="ip" >
			ip:<%= _SynkNodeTrackerTx.getIp() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _SynkNodeTrackerTx.getTimeCreated() %>
		</div>
		<div>
		<a id="synkNodeTrackerTxDeleteButton" href="javascript:deleteThis('/synkNodeTrackerTxAction.html',<%= _SynkNodeTrackerTx.getId()%>,'synkNodeTrackerTxFrame<%=_SynkNodeTrackerTx.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="synkNodeTrackerTxForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="synkNodeTrackerTxFormAdd" method="POST" action="/synkNodeTrackerTxAction.html" id="synkNodeTrackerTxFormAdd">

	<div id="synkNodeTrackerTxForm_namespace_field">
    <div id="synkNodeTrackerTxForm_namespace_label" class="formLabel" >Namespace </div>
    <div id="synkNodeTrackerTxForm_namespace_text" class="formFieldText" >       
        <input class="field" id="_ffd_namespace" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNodeTrackerTxForm_deviceId_field">
    <div id="synkNodeTrackerTxForm_deviceId_label" class="formLabel" >Device Id </div>
    <div id="synkNodeTrackerTxForm_deviceId_text" class="formFieldText" >       
        <input class="field" id="_ffd_deviceId" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNodeTrackerTxForm_txToken_field">
    <div id="synkNodeTrackerTxForm_txToken_label" class="formLabel" >Tx Token </div>
    <div id="synkNodeTrackerTxForm_txToken_text" class="formFieldText" >       
        <input class="field" id="_ffd_txToken" type="text" size="70" name="txToken" value="<%=WebUtil.display(_tx_tokenValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNodeTrackerTxForm_stampAcked_field">
    <div id="synkNodeTrackerTxForm_stampAcked_label" class="formLabel" >Stamp Acked </div>
    <div id="synkNodeTrackerTxForm_stampAcked_text" class="formFieldText" >       
        <input class="field" id="_ffd_stampAcked" type="text" size="70" name="stampAcked" value="<%=WebUtil.display(_stamp_ackedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNodeTrackerTxForm_stampLast_field">
    <div id="synkNodeTrackerTxForm_stampLast_label" class="formLabel" >Stamp Last </div>
    <div id="synkNodeTrackerTxForm_stampLast_text" class="formFieldText" >       
        <input class="field" id="_ffd_stampLast" type="text" size="70" name="stampLast" value="<%=WebUtil.display(_stamp_lastValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNodeTrackerTxForm_numRecords_field">
    <div id="synkNodeTrackerTxForm_numRecords_label" class="formLabel" >Num Records </div>
    <div id="synkNodeTrackerTxForm_numRecords_text" class="formFieldText" >       
        <input class="field" id="_ffd_numRecords" type="text" size="70" name="numRecords" value="<%=WebUtil.display(_num_recordsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNodeTrackerTxForm_ip_field">
    <div id="synkNodeTrackerTxForm_ip_label" class="formLabel" >Ip </div>
    <div id="synkNodeTrackerTxForm_ip_text" class="formFieldText" >       
        <input class="field" id="_ffd_ip" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNodeTrackerTxForm_timeCreated_field">
    <div id="synkNodeTrackerTxForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="synkNodeTrackerTxForm_timeCreated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeCreated" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="synkNodeTrackerTxFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/synkNodeTrackerTxAction.html', 'synkNodeTrackerTxFormAdd', 'formSubmit_ajax', 'synkNodeTrackerTx');">Submit</a>
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
	document.getElementById("resultDisplaySynkNodeTrackerTx").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('synkNodeTrackerTxFormAddDis','/synkNodeTrackerTxAction.html', 'resultDisplaySynkNodeTrackerTx', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="synkNodeTrackerTxFormAddDis" method="POST" action="/synkNodeTrackerTxAction.html" id="synkNodeTrackerTxFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Namespace</div>
        <input class="field" id="namespace" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Device Id</div>
        <input class="field" id="deviceId" type="text" size="70" name="deviceId" value="<%=WebUtil.display(_device_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Tx Token</div>
        <input class="field" id="txToken" type="text" size="70" name="txToken" value="<%=WebUtil.display(_tx_tokenValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Stamp Acked</div>
        <input class="field" id="stampAcked" type="text" size="70" name="stampAcked" value="<%=WebUtil.display(_stamp_ackedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Stamp Last</div>
        <input class="field" id="stampLast" type="text" size="70" name="stampLast" value="<%=WebUtil.display(_stamp_lastValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Num Records</div>
        <input class="field" id="numRecords" type="text" size="70" name="numRecords" value="<%=WebUtil.display(_num_recordsValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ip</div>
        <input class="field" id="ip" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Created</div>
        <input class="field" id="timeCreated" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('synkNodeTrackerTxFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplaySynkNodeTrackerTx"></span>
<a href="javascript:backToXX('synkNodeTrackerTxFormAddDis','resultDisplaySynkNodeTrackerTx')">show back</a><br>
<hr/>
