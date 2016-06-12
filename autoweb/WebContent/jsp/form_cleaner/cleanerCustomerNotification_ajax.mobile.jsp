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
	if (cancelPage == null) cancelPage = "cleaner_customer_notification_home";

    String _customer_idValue= WebUtil.display((String)reqParams.get("customerId"));
    String _reasonfor_idValue= WebUtil.display((String)reqParams.get("reasonforId"));
    String _reasonfor_targetValue= WebUtil.display((String)reqParams.get("reasonforTarget"));
    String _notification_typeValue= WebUtil.display((String)reqParams.get("notificationType"));
    String _source_typeValue= WebUtil.display((String)reqParams.get("sourceType"));
    String _trigger_typeValue= WebUtil.display((String)reqParams.get("triggerType"));
    String _is_retransmitValue= WebUtil.display((String)reqParams.get("isRetransmit"));
    String _method_typeValue= WebUtil.display((String)reqParams.get("methodType"));
    String _template_typeValue= WebUtil.display((String)reqParams.get("templateType"));
    String _contentValue= WebUtil.display((String)reqParams.get("content"));
    String _destinationValue= WebUtil.display((String)reqParams.get("destination"));
    String _referenceValue= WebUtil.display((String)reqParams.get("reference"));
    String _time_scheduledValue= WebUtil.display((String)reqParams.get("timeScheduled"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_sentValue= WebUtil.display((String)reqParams.get("timeSent"));
%> 

<a href="/v_cleaner_customer_notification_home.html"> CleanerCustomerNotification Home </a>
<%
	
	List list = null;
	list = CleanerCustomerNotificationDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerCustomerNotificationList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerCustomerNotification _CleanerCustomerNotification = (CleanerCustomerNotification) iter.next();	
%>

	<div id="cleanerCustomerNotificationFrame<%=_CleanerCustomerNotification.getId() %>" >

		<div id="customerId" >
			customerId:<%= _CleanerCustomerNotification.getCustomerId() %>
		</div>
		<div id="reasonforId" >
			reasonforId:<%= _CleanerCustomerNotification.getReasonforId() %>
		</div>
		<div id="reasonforTarget" >
			reasonforTarget:<%= _CleanerCustomerNotification.getReasonforTarget() %>
		</div>
		<div id="notificationType" >
			notificationType:<%= _CleanerCustomerNotification.getNotificationType() %>
		</div>
		<div id="sourceType" >
			sourceType:<%= _CleanerCustomerNotification.getSourceType() %>
		</div>
		<div id="triggerType" >
			triggerType:<%= _CleanerCustomerNotification.getTriggerType() %>
		</div>
		<div id="isRetransmit" >
			isRetransmit:<%= _CleanerCustomerNotification.getIsRetransmit() %>
		</div>
		<div id="methodType" >
			methodType:<%= _CleanerCustomerNotification.getMethodType() %>
		</div>
		<div id="templateType" >
			templateType:<%= _CleanerCustomerNotification.getTemplateType() %>
		</div>
		<div id="content" >
			content:<%= _CleanerCustomerNotification.getContent() %>
		</div>
		<div id="destination" >
			destination:<%= _CleanerCustomerNotification.getDestination() %>
		</div>
		<div id="reference" >
			reference:<%= _CleanerCustomerNotification.getReference() %>
		</div>
		<div id="timeScheduled" >
			timeScheduled:<%= _CleanerCustomerNotification.getTimeScheduled() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _CleanerCustomerNotification.getTimeCreated() %>
		</div>
		<div id="timeSent" >
			timeSent:<%= _CleanerCustomerNotification.getTimeSent() %>
		</div>
		<div>
		<a id="cleanerCustomerNotificationDeleteButton" href="javascript:deleteThis('/cleanerCustomerNotificationAction.html',<%= _CleanerCustomerNotification.getId()%>,'cleanerCustomerNotificationFrame<%=_CleanerCustomerNotification.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerCustomerNotificationForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerCustomerNotificationFormAdd" method="POST" action="/cleanerCustomerNotificationAction.html" id="cleanerCustomerNotificationFormAdd">

	<div id="cleanerCustomerNotificationForm_customerId_field">
    <div id="cleanerCustomerNotificationForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerCustomerNotificationForm_customerId_text" class="formFieldText" >       
        <input class="field" id="_ffd_customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_reasonforId_field">
    <div id="cleanerCustomerNotificationForm_reasonforId_label" class="formLabel" >Reasonfor Id </div>
    <div id="cleanerCustomerNotificationForm_reasonforId_text" class="formFieldText" >       
        <input class="field" id="_ffd_reasonforId" type="text" size="70" name="reasonforId" value="<%=WebUtil.display(_reasonfor_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_reasonforTarget_field">
    <div id="cleanerCustomerNotificationForm_reasonforTarget_label" class="formLabel" >Reasonfor Target </div>
    <div id="cleanerCustomerNotificationForm_reasonforTarget_text" class="formFieldText" >       
        <input class="field" id="_ffd_reasonforTarget" type="text" size="70" name="reasonforTarget" value="<%=WebUtil.display(_reasonfor_targetValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_notificationType_field">
    <div id="cleanerCustomerNotificationForm_notificationType_label" class="formLabel" >Notification Type </div>
    <div id="cleanerCustomerNotificationForm_notificationType_text" class="formFieldText" >       
        <input class="field" id="_ffd_notificationType" type="text" size="70" name="notificationType" value="<%=WebUtil.display(_notification_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_sourceType_field">
    <div id="cleanerCustomerNotificationForm_sourceType_label" class="formLabel" >Source Type </div>
    <div id="cleanerCustomerNotificationForm_sourceType_text" class="formFieldText" >       
        <input class="field" id="_ffd_sourceType" type="text" size="70" name="sourceType" value="<%=WebUtil.display(_source_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_triggerType_field">
    <div id="cleanerCustomerNotificationForm_triggerType_label" class="formLabel" >Trigger Type </div>
    <div id="cleanerCustomerNotificationForm_triggerType_text" class="formFieldText" >       
        <input class="field" id="_ffd_triggerType" type="text" size="70" name="triggerType" value="<%=WebUtil.display(_trigger_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_isRetransmit_field">
    <div id="cleanerCustomerNotificationForm_isRetransmit_label" class="formLabel" >Is Retransmit </div>
    <div id="cleanerCustomerNotificationForm_isRetransmit_text" class="formFieldText" >       
        <input class="field" id="_ffd_isRetransmit" type="text" size="70" name="isRetransmit" value="<%=WebUtil.display(_is_retransmitValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_methodType_field">
    <div id="cleanerCustomerNotificationForm_methodType_label" class="formLabel" >Method Type </div>
    <div id="cleanerCustomerNotificationForm_methodType_text" class="formFieldText" >       
        <input class="field" id="_ffd_methodType" type="text" size="70" name="methodType" value="<%=WebUtil.display(_method_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_templateType_field">
    <div id="cleanerCustomerNotificationForm_templateType_label" class="formLabel" >Template Type </div>
    <div id="cleanerCustomerNotificationForm_templateType_text" class="formFieldText" >       
        <input class="field" id="_ffd_templateType" type="text" size="70" name="templateType" value="<%=WebUtil.display(_template_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_content_field">
    <div id="cleanerCustomerNotificationForm_content_label" class="formLabel" >Content </div>
    <div id="cleanerCustomerNotificationForm_content_text" class="formFieldText" >       
        <input class="field" id="_ffd_content" type="text" size="70" name="content" value="<%=WebUtil.display(_contentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_destination_field">
    <div id="cleanerCustomerNotificationForm_destination_label" class="formLabel" >Destination </div>
    <div id="cleanerCustomerNotificationForm_destination_text" class="formFieldText" >       
        <input class="field" id="_ffd_destination" type="text" size="70" name="destination" value="<%=WebUtil.display(_destinationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_reference_field">
    <div id="cleanerCustomerNotificationForm_reference_label" class="formLabel" >Reference </div>
    <div id="cleanerCustomerNotificationForm_reference_text" class="formFieldText" >       
        <input class="field" id="_ffd_reference" type="text" size="70" name="reference" value="<%=WebUtil.display(_referenceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_timeScheduled_field">
    <div id="cleanerCustomerNotificationForm_timeScheduled_label" class="formLabel" >Time Scheduled </div>
    <div id="cleanerCustomerNotificationForm_timeScheduled_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeScheduled" type="text" size="70" name="timeScheduled" value="<%=WebUtil.display(_time_scheduledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerCustomerNotificationForm_timeSent_field">
    <div id="cleanerCustomerNotificationForm_timeSent_label" class="formLabel" >Time Sent </div>
    <div id="cleanerCustomerNotificationForm_timeSent_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeSent" type="text" size="70" name="timeSent" value="<%=WebUtil.display(_time_sentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerCustomerNotificationFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerCustomerNotificationAction.html', 'cleanerCustomerNotificationFormAdd', 'formSubmit_ajax', 'cleanerCustomerNotification');">Submit</a>
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
	document.getElementById("resultDisplayCleanerCustomerNotification").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerCustomerNotificationFormAddDis','/cleanerCustomerNotificationAction.html', 'resultDisplayCleanerCustomerNotification', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerCustomerNotificationFormAddDis" method="POST" action="/cleanerCustomerNotificationAction.html" id="cleanerCustomerNotificationFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Customer Id</div>
        <input class="field" id="customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Reasonfor Id</div>
        <input class="field" id="reasonforId" type="text" size="70" name="reasonforId" value="<%=WebUtil.display(_reasonfor_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Reasonfor Target</div>
        <input class="field" id="reasonforTarget" type="text" size="70" name="reasonforTarget" value="<%=WebUtil.display(_reasonfor_targetValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Notification Type</div>
        <input class="field" id="notificationType" type="text" size="70" name="notificationType" value="<%=WebUtil.display(_notification_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Source Type</div>
        <input class="field" id="sourceType" type="text" size="70" name="sourceType" value="<%=WebUtil.display(_source_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Trigger Type</div>
        <input class="field" id="triggerType" type="text" size="70" name="triggerType" value="<%=WebUtil.display(_trigger_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Retransmit</div>
        <input class="field" id="isRetransmit" type="text" size="70" name="isRetransmit" value="<%=WebUtil.display(_is_retransmitValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Method Type</div>
        <input class="field" id="methodType" type="text" size="70" name="methodType" value="<%=WebUtil.display(_method_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Template Type</div>
        <input class="field" id="templateType" type="text" size="70" name="templateType" value="<%=WebUtil.display(_template_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Content</div>
        <input class="field" id="content" type="text" size="70" name="content" value="<%=WebUtil.display(_contentValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Destination</div>
        <input class="field" id="destination" type="text" size="70" name="destination" value="<%=WebUtil.display(_destinationValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Reference</div>
        <input class="field" id="reference" type="text" size="70" name="reference" value="<%=WebUtil.display(_referenceValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Scheduled</div>
        <input class="field" id="timeScheduled" type="text" size="70" name="timeScheduled" value="<%=WebUtil.display(_time_scheduledValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Sent</div>
        <input class="field" id="timeSent" type="text" size="70" name="timeSent" value="<%=WebUtil.display(_time_sentValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerCustomerNotificationFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerCustomerNotification"></span>
<a href="javascript:backToXX('cleanerCustomerNotificationFormAddDis','resultDisplayCleanerCustomerNotification')">show back</a><br>
<hr/>
