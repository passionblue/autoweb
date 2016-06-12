<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sun Mar 15 01:49:44 EDT 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_main_home";

    String _activeValue= WebUtil.display((String)reqParams.get("active"));
    String _valueValue= WebUtil.display((String)reqParams.get("value"));
    String _dataValue= WebUtil.display((String)reqParams.get("data"));
    String _requiredValue= WebUtil.display((String)reqParams.get("required"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_gen_main_home.html"> GenMain Home </a>
<%
	
	List list = null;
	list = GenMainDS.getInstance().getBySiteId(site.getId());

%>

<div id="genMainList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		GenMain _GenMain = (GenMain) iter.next();	
%>

	<div id="genMainFrame<%=_GenMain.getId() %>" >

		<div id="active" >
			active:<%= _GenMain.getActive() %>
		</div>
		<div id="value" >
			value:<%= _GenMain.getValue() %>
		</div>
		<div id="data" >
			data:<%= _GenMain.getData() %>
		</div>
		<div id="required" >
			required:<%= _GenMain.getRequired() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _GenMain.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _GenMain.getTimeUpdated() %>
		</div>
		<div>
		<a id="genMainDeleteButton" href="javascript:deleteThis('/genMainAction.html',<%= _GenMain.getId()%>,'genMainFrame<%=_GenMain.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="genMainForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genMainFormAdd" method="POST" action="/genMainAction.html" id="genMainFormAdd">

	<div id="genMainForm_active_field">
    <div id="genMainForm_active_label" class="formLabel" >Active </div>
    <div id="genMainForm_active_text" class="formFieldText" >       
        <input class="field" id="_ffd_active" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genMainForm_value_field">
    <div id="genMainForm_value_label" class="formLabel" >Value </div>
    <div id="genMainForm_value_text" class="formFieldText" >       
        <input class="field" id="_ffd_value" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genMainForm_data_field">
    <div id="genMainForm_data_label" class="formLabel" >Data </div>
    <div id="genMainForm_data_text" class="formFieldText" >       
        <input class="field" id="_ffd_data" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genMainForm_required_field">
    <div id="genMainForm_required_label" class="formLabel" >Required </div>
    <div id="genMainForm_required_text" class="formFieldText" >       
        <input class="field" id="_ffd_required" type="text" size="70" name="required" value="<%=WebUtil.display(_requiredValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genMainForm_timeUpdated_field">
    <div id="genMainForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="genMainForm_timeUpdated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="genMainFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/genMainAction.html', 'genMainFormAdd', 'formSubmit_ajax', 'genMain');">Submit</a>
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
	document.getElementById("resultDisplayGenMain").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('genMainFormAddDis','/genMainAction.html', 'resultDisplayGenMain', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="genMainFormAddDis" method="POST" action="/genMainAction.html" id="genMainFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Active</div>
        <input class="field" id="active" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Value</div>
        <input class="field" id="value" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Data</div>
        <input class="field" id="data" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Required</div>
        <input class="field" id="required" type="text" size="70" name="required" value="<%=WebUtil.display(_requiredValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Updated</div>
        <input class="field" id="timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('genMainFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayGenMain"></span>
<a href="javascript:backToXX('genMainFormAddDis','resultDisplayGenMain')">show back</a><br>
<hr/>
