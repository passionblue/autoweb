<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_service_process_home";

    String _ticket_idValue= WebUtil.display((String)reqParams.get("ticketId"));
    String _process_user_idValue= WebUtil.display((String)reqParams.get("processUserId"));
    String _process_typeValue= WebUtil.display((String)reqParams.get("processType"));
    String _time_startedValue= WebUtil.display((String)reqParams.get("timeStarted"));
    String _time_endedValue= WebUtil.display((String)reqParams.get("timeEnded"));
    String _noteValue= WebUtil.display((String)reqParams.get("note"));
%> 

<a href="/v_cleaner_service_process_home.html"> CleanerServiceProcess Home </a>
<%
	
	List list = null;
	list = CleanerServiceProcessDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerServiceProcessList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerServiceProcess _CleanerServiceProcess = (CleanerServiceProcess) iter.next();	
%>

	<div id="cleanerServiceProcessFrame<%=_CleanerServiceProcess.getId() %>" >

		<div id="ticketId" >
			ticketId:<%= _CleanerServiceProcess.getTicketId() %>
		</div>
		<div id="processUserId" >
			processUserId:<%= _CleanerServiceProcess.getProcessUserId() %>
		</div>
		<div id="processType" >
			processType:<%= _CleanerServiceProcess.getProcessType() %>
		</div>
		<div id="timeStarted" >
			timeStarted:<%= _CleanerServiceProcess.getTimeStarted() %>
		</div>
		<div id="timeEnded" >
			timeEnded:<%= _CleanerServiceProcess.getTimeEnded() %>
		</div>
		<div id="note" >
			note:<%= _CleanerServiceProcess.getNote() %>
		</div>
		<div>
		<a id="cleanerServiceProcessDeleteButton" href="javascript:deleteThis('/cleanerServiceProcessAction.html',<%= _CleanerServiceProcess.getId()%>,'cleanerServiceProcessFrame<%=_CleanerServiceProcess.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerServiceProcessForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerServiceProcessFormAdd" method="POST" action="/cleanerServiceProcessAction.html" id="cleanerServiceProcessFormAdd">

	<div id="cleanerServiceProcessForm_ticketId_field">
    <div id="cleanerServiceProcessForm_ticketId_label" class="formLabel" >Ticket Id </div>
    <div id="cleanerServiceProcessForm_ticketId_text" class="formFieldText" >       
        <input class="field" id="_ffd_ticketId" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceProcessForm_processUserId_field">
    <div id="cleanerServiceProcessForm_processUserId_label" class="formLabel" >Process User Id </div>
    <div id="cleanerServiceProcessForm_processUserId_text" class="formFieldText" >       
        <input class="field" id="_ffd_processUserId" type="text" size="70" name="processUserId" value="<%=WebUtil.display(_process_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceProcessForm_processType_field">
    <div id="cleanerServiceProcessForm_processType_label" class="formLabel" >Process Type </div>
    <div id="cleanerServiceProcessForm_processType_text" class="formFieldText" >       
        <input class="field" id="_ffd_processType" type="text" size="70" name="processType" value="<%=WebUtil.display(_process_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceProcessForm_timeStarted_field">
    <div id="cleanerServiceProcessForm_timeStarted_label" class="formLabel" >Time Started </div>
    <div id="cleanerServiceProcessForm_timeStarted_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeStarted" type="text" size="70" name="timeStarted" value="<%=WebUtil.display(_time_startedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceProcessForm_timeEnded_field">
    <div id="cleanerServiceProcessForm_timeEnded_label" class="formLabel" >Time Ended </div>
    <div id="cleanerServiceProcessForm_timeEnded_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeEnded" type="text" size="70" name="timeEnded" value="<%=WebUtil.display(_time_endedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceProcessForm_note_field">
    <div id="cleanerServiceProcessForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerServiceProcessForm_note_text" class="formFieldText" >       
        <input class="field" id="_ffd_note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerServiceProcessFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerServiceProcessAction.html', 'cleanerServiceProcessFormAdd', 'formSubmit_ajax', 'cleanerServiceProcess');">Submit</a>
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
	document.getElementById("resultDisplayCleanerServiceProcess").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerServiceProcessFormAddDis','/cleanerServiceProcessAction.html', 'resultDisplayCleanerServiceProcess', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerServiceProcessFormAddDis" method="POST" action="/cleanerServiceProcessAction.html" id="cleanerServiceProcessFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Ticket Id</div>
        <input class="field" id="ticketId" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Process User Id</div>
        <input class="field" id="processUserId" type="text" size="70" name="processUserId" value="<%=WebUtil.display(_process_user_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Process Type</div>
        <input class="field" id="processType" type="text" size="70" name="processType" value="<%=WebUtil.display(_process_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Started</div>
        <input class="field" id="timeStarted" type="text" size="70" name="timeStarted" value="<%=WebUtil.display(_time_startedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Ended</div>
        <input class="field" id="timeEnded" type="text" size="70" name="timeEnded" value="<%=WebUtil.display(_time_endedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Note</div>
        <input class="field" id="note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerServiceProcessFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerServiceProcess"></span>
<a href="javascript:backToXX('cleanerServiceProcessFormAddDis','resultDisplayCleanerServiceProcess')">show back</a><br>
<hr/>
