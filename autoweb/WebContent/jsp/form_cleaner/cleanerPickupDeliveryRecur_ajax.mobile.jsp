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
	if (cancelPage == null) cancelPage = "cleaner_pickup_delivery_recur_home";

    String _customer_idValue= WebUtil.display((String)reqParams.get("customerId"));
    String _weekdayValue= WebUtil.display((String)reqParams.get("weekday"));
    String _time_hhddValue= WebUtil.display((String)reqParams.get("timeHhdd"));
%> 

<a href="/v_cleaner_pickup_delivery_recur_home.html"> CleanerPickupDeliveryRecur Home </a>
<%
	
	List list = null;
	list = CleanerPickupDeliveryRecurDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerPickupDeliveryRecurList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerPickupDeliveryRecur _CleanerPickupDeliveryRecur = (CleanerPickupDeliveryRecur) iter.next();	
%>

	<div id="cleanerPickupDeliveryRecurFrame<%=_CleanerPickupDeliveryRecur.getId() %>" >

		<div id="customerId" >
			customerId:<%= _CleanerPickupDeliveryRecur.getCustomerId() %>
		</div>
		<div id="weekday" >
			weekday:<%= _CleanerPickupDeliveryRecur.getWeekday() %>
		</div>
		<div id="timeHhdd" >
			timeHhdd:<%= _CleanerPickupDeliveryRecur.getTimeHhdd() %>
		</div>
		<div>
		<a id="cleanerPickupDeliveryRecurDeleteButton" href="javascript:deleteThis('/cleanerPickupDeliveryRecurAction.html',<%= _CleanerPickupDeliveryRecur.getId()%>,'cleanerPickupDeliveryRecurFrame<%=_CleanerPickupDeliveryRecur.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerPickupDeliveryRecurForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerPickupDeliveryRecurFormAdd" method="POST" action="/cleanerPickupDeliveryRecurAction.html" id="cleanerPickupDeliveryRecurFormAdd">

	<div id="cleanerPickupDeliveryRecurForm_customerId_field">
    <div id="cleanerPickupDeliveryRecurForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerPickupDeliveryRecurForm_customerId_text" class="formFieldText" >       
        <input class="field" id="_ffd_customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryRecurForm_weekday_field">
    <div id="cleanerPickupDeliveryRecurForm_weekday_label" class="formLabel" >Weekday </div>
    <div id="cleanerPickupDeliveryRecurForm_weekday_text" class="formFieldText" >       
        <input class="field" id="_ffd_weekday" type="text" size="70" name="weekday" value="<%=WebUtil.display(_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerPickupDeliveryRecurForm_timeHhdd_field">
    <div id="cleanerPickupDeliveryRecurForm_timeHhdd_label" class="formLabel" >Time Hhdd </div>
    <div id="cleanerPickupDeliveryRecurForm_timeHhdd_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeHhdd" type="text" size="70" name="timeHhdd" value="<%=WebUtil.display(_time_hhddValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerPickupDeliveryRecurFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerPickupDeliveryRecurAction.html', 'cleanerPickupDeliveryRecurFormAdd', 'formSubmit_ajax', 'cleanerPickupDeliveryRecur');">Submit</a>
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
	document.getElementById("resultDisplayCleanerPickupDeliveryRecur").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerPickupDeliveryRecurFormAddDis','/cleanerPickupDeliveryRecurAction.html', 'resultDisplayCleanerPickupDeliveryRecur', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerPickupDeliveryRecurFormAddDis" method="POST" action="/cleanerPickupDeliveryRecurAction.html" id="cleanerPickupDeliveryRecurFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Customer Id</div>
        <input class="field" id="customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Weekday</div>
        <input class="field" id="weekday" type="text" size="70" name="weekday" value="<%=WebUtil.display(_weekdayValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Hhdd</div>
        <input class="field" id="timeHhdd" type="text" size="70" name="timeHhdd" value="<%=WebUtil.display(_time_hhddValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerPickupDeliveryRecurFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerPickupDeliveryRecur"></span>
<a href="javascript:backToXX('cleanerPickupDeliveryRecurFormAddDis','resultDisplayCleanerPickupDeliveryRecur')">show back</a><br>
<hr/>
