<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sat Feb 14 00:17:16 EST 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "synk_namespace_record_home";

    String _namespaceValue= WebUtil.display((String)reqParams.get("namespace"));
    String _record_idValue= WebUtil.display((String)reqParams.get("recordId"));
    String _stampValue= WebUtil.display((String)reqParams.get("stamp"));
    String _org_stampValue= WebUtil.display((String)reqParams.get("orgStamp"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_synk_namespace_record_home.html"> SynkNamespaceRecord Home </a>
<%
	
	List list = null;
	list = SynkNamespaceRecordDS.getInstance().getBySiteId(site.getId());

%>

<div id="synkNamespaceRecordList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		SynkNamespaceRecord _SynkNamespaceRecord = (SynkNamespaceRecord) iter.next();	
%>

	<div id="synkNamespaceRecordFrame<%=_SynkNamespaceRecord.getId() %>" >

		<div id="namespace" >
			namespace:<%= _SynkNamespaceRecord.getNamespace() %>
		</div>
		<div id="recordId" >
			recordId:<%= _SynkNamespaceRecord.getRecordId() %>
		</div>
		<div id="stamp" >
			stamp:<%= _SynkNamespaceRecord.getStamp() %>
		</div>
		<div id="orgStamp" >
			orgStamp:<%= _SynkNamespaceRecord.getOrgStamp() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _SynkNamespaceRecord.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _SynkNamespaceRecord.getTimeUpdated() %>
		</div>
		<div>
		<a id="synkNamespaceRecordDeleteButton" href="javascript:deleteThis('/synkNamespaceRecordAction.html',<%= _SynkNamespaceRecord.getId()%>,'synkNamespaceRecordFrame<%=_SynkNamespaceRecord.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="synkNamespaceRecordForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="synkNamespaceRecordFormAdd" method="POST" action="/synkNamespaceRecordAction.html" id="synkNamespaceRecordFormAdd">

	<div id="synkNamespaceRecordForm_namespace_field">
    <div id="synkNamespaceRecordForm_namespace_label" class="formLabel" >Namespace </div>
    <div id="synkNamespaceRecordForm_namespace_text" class="formFieldText" >       
        <input class="field" id="_ffd_namespace" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNamespaceRecordForm_recordId_field">
    <div id="synkNamespaceRecordForm_recordId_label" class="formLabel" >Record Id </div>
    <div id="synkNamespaceRecordForm_recordId_text" class="formFieldText" >       
        <input class="field" id="_ffd_recordId" type="text" size="70" name="recordId" value="<%=WebUtil.display(_record_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNamespaceRecordForm_stamp_field">
    <div id="synkNamespaceRecordForm_stamp_label" class="formLabel" >Stamp </div>
    <div id="synkNamespaceRecordForm_stamp_text" class="formFieldText" >       
        <input class="field" id="_ffd_stamp" type="text" size="70" name="stamp" value="<%=WebUtil.display(_stampValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="synkNamespaceRecordForm_orgStamp_field">
    <div id="synkNamespaceRecordForm_orgStamp_label" class="formLabel" >Org Stamp </div>
    <div id="synkNamespaceRecordForm_orgStamp_text" class="formFieldText" >       
        <input class="field" id="_ffd_orgStamp" type="text" size="70" name="orgStamp" value="<%=WebUtil.display(_org_stampValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="synkNamespaceRecordFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/synkNamespaceRecordAction.html', 'synkNamespaceRecordFormAdd', 'formSubmit_ajax', 'synkNamespaceRecord');">Submit</a>
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
	document.getElementById("resultDisplaySynkNamespaceRecord").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('synkNamespaceRecordFormAddDis','/synkNamespaceRecordAction.html', 'resultDisplaySynkNamespaceRecord', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="synkNamespaceRecordFormAddDis" method="POST" action="/synkNamespaceRecordAction.html" id="synkNamespaceRecordFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Namespace</div>
        <input class="field" id="namespace" type="text" size="70" name="namespace" value="<%=WebUtil.display(_namespaceValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Record Id</div>
        <input class="field" id="recordId" type="text" size="70" name="recordId" value="<%=WebUtil.display(_record_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Stamp</div>
        <input class="field" id="stamp" type="text" size="70" name="stamp" value="<%=WebUtil.display(_stampValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Org Stamp</div>
        <input class="field" id="orgStamp" type="text" size="70" name="orgStamp" value="<%=WebUtil.display(_org_stampValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('synkNamespaceRecordFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplaySynkNamespaceRecord"></span>
<a href="javascript:backToXX('synkNamespaceRecordFormAddDis','resultDisplaySynkNamespaceRecord')">show back</a><br>
<hr/>
