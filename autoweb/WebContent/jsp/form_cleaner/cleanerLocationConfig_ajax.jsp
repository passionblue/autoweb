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
	if (cancelPage == null) cancelPage = "cleaner_location_config_home";

    String _location_idValue= WebUtil.display((String)reqParams.get("locationId"));
    String _open_hour_weekdayValue= WebUtil.display((String)reqParams.get("openHourWeekday"));
    String _close_hour_weekdayValue= WebUtil.display((String)reqParams.get("closeHourWeekday"));
    String _open_hour_satValue= WebUtil.display((String)reqParams.get("openHourSat"));
    String _close_hour_satValue= WebUtil.display((String)reqParams.get("closeHourSat"));
    String _open_hour_sunValue= WebUtil.display((String)reqParams.get("openHourSun"));
    String _close_hour_sunValue= WebUtil.display((String)reqParams.get("closeHourSun"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_cleaner_location_config_home.html"> CleanerLocationConfig Home </a>
<%
	
	List list = null;
	list = CleanerLocationConfigDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerLocationConfigList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerLocationConfig _CleanerLocationConfig = (CleanerLocationConfig) iter.next();	
%>

	<div id="cleanerLocationConfigFrame<%=_CleanerLocationConfig.getId() %>" >

		<div id="locationId" >
			locationId:<%= _CleanerLocationConfig.getLocationId() %>
		</div>
		<div id="openHourWeekday" >
			openHourWeekday:<%= _CleanerLocationConfig.getOpenHourWeekday() %>
		</div>
		<div id="closeHourWeekday" >
			closeHourWeekday:<%= _CleanerLocationConfig.getCloseHourWeekday() %>
		</div>
		<div id="openHourSat" >
			openHourSat:<%= _CleanerLocationConfig.getOpenHourSat() %>
		</div>
		<div id="closeHourSat" >
			closeHourSat:<%= _CleanerLocationConfig.getCloseHourSat() %>
		</div>
		<div id="openHourSun" >
			openHourSun:<%= _CleanerLocationConfig.getOpenHourSun() %>
		</div>
		<div id="closeHourSun" >
			closeHourSun:<%= _CleanerLocationConfig.getCloseHourSun() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _CleanerLocationConfig.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _CleanerLocationConfig.getTimeUpdated() %>
		</div>
		<div>
		<a id="cleanerLocationConfigDeleteButton" href="javascript:deleteThis('/cleanerLocationConfigAction.html',<%= _CleanerLocationConfig.getId()%>,'cleanerLocationConfigFrame<%=_CleanerLocationConfig.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerLocationConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerLocationConfigFormAdd" method="POST" action="/cleanerLocationConfigAction.html" id="cleanerLocationConfigFormAdd">

	<div id="cleanerLocationConfigForm_locationId_field">
    <div id="cleanerLocationConfigForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerLocationConfigForm_locationId_text" class="formFieldText" >       
        <input class="field" id="_ffd_locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerLocationConfigForm_openHourWeekday_field">
    <div id="cleanerLocationConfigForm_openHourWeekday_label" class="formLabel" >Open Hour Weekday </div>
    <div id="cleanerLocationConfigForm_openHourWeekday_text" class="formFieldText" >       
        <input class="field" id="_ffd_openHourWeekday" type="text" size="70" name="openHourWeekday" value="<%=WebUtil.display(_open_hour_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerLocationConfigForm_closeHourWeekday_field">
    <div id="cleanerLocationConfigForm_closeHourWeekday_label" class="formLabel" >Close Hour Weekday </div>
    <div id="cleanerLocationConfigForm_closeHourWeekday_text" class="formFieldText" >       
        <input class="field" id="_ffd_closeHourWeekday" type="text" size="70" name="closeHourWeekday" value="<%=WebUtil.display(_close_hour_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerLocationConfigForm_openHourSat_field">
    <div id="cleanerLocationConfigForm_openHourSat_label" class="formLabel" >Open Hour Sat </div>
    <div id="cleanerLocationConfigForm_openHourSat_text" class="formFieldText" >       
        <input class="field" id="_ffd_openHourSat" type="text" size="70" name="openHourSat" value="<%=WebUtil.display(_open_hour_satValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerLocationConfigForm_closeHourSat_field">
    <div id="cleanerLocationConfigForm_closeHourSat_label" class="formLabel" >Close Hour Sat </div>
    <div id="cleanerLocationConfigForm_closeHourSat_text" class="formFieldText" >       
        <input class="field" id="_ffd_closeHourSat" type="text" size="70" name="closeHourSat" value="<%=WebUtil.display(_close_hour_satValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerLocationConfigForm_openHourSun_field">
    <div id="cleanerLocationConfigForm_openHourSun_label" class="formLabel" >Open Hour Sun </div>
    <div id="cleanerLocationConfigForm_openHourSun_text" class="formFieldText" >       
        <input class="field" id="_ffd_openHourSun" type="text" size="70" name="openHourSun" value="<%=WebUtil.display(_open_hour_sunValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerLocationConfigForm_closeHourSun_field">
    <div id="cleanerLocationConfigForm_closeHourSun_label" class="formLabel" >Close Hour Sun </div>
    <div id="cleanerLocationConfigForm_closeHourSun_text" class="formFieldText" >       
        <input class="field" id="_ffd_closeHourSun" type="text" size="70" name="closeHourSun" value="<%=WebUtil.display(_close_hour_sunValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerLocationConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerLocationConfigAction.html', 'cleanerLocationConfigFormAdd', 'formSubmit_ajax', 'cleanerLocationConfig');">Submit</a>
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
	document.getElementById("resultDisplayCleanerLocationConfig").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerLocationConfigFormAddDis','/cleanerLocationConfigAction.html', 'resultDisplayCleanerLocationConfig', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerLocationConfigFormAddDis" method="POST" action="/cleanerLocationConfigAction.html" id="cleanerLocationConfigFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Location Id</div>
        <input class="field" id="locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Open Hour Weekday</div>
        <input class="field" id="openHourWeekday" type="text" size="70" name="openHourWeekday" value="<%=WebUtil.display(_open_hour_weekdayValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Close Hour Weekday</div>
        <input class="field" id="closeHourWeekday" type="text" size="70" name="closeHourWeekday" value="<%=WebUtil.display(_close_hour_weekdayValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Open Hour Sat</div>
        <input class="field" id="openHourSat" type="text" size="70" name="openHourSat" value="<%=WebUtil.display(_open_hour_satValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Close Hour Sat</div>
        <input class="field" id="closeHourSat" type="text" size="70" name="closeHourSat" value="<%=WebUtil.display(_close_hour_satValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Open Hour Sun</div>
        <input class="field" id="openHourSun" type="text" size="70" name="openHourSun" value="<%=WebUtil.display(_open_hour_sunValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Close Hour Sun</div>
        <input class="field" id="closeHourSun" type="text" size="70" name="closeHourSun" value="<%=WebUtil.display(_close_hour_sunValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerLocationConfigFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerLocationConfig"></span>
<a href="javascript:backToXX('cleanerLocationConfigFormAddDis','resultDisplayCleanerLocationConfig')">show back</a><br>
<hr/>
