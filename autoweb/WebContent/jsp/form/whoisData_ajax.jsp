<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "whois_data_home";

    String _ipValue= WebUtil.display((String)reqParams.get("ip"));
    String _whois_dataValue= WebUtil.display((String)reqParams.get("whoisData"));
    String _serverValue= WebUtil.display((String)reqParams.get("server"));
    String _force_requestValue= WebUtil.display((String)reqParams.get("forceRequest"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_expiredValue= WebUtil.display((String)reqParams.get("timeExpired"));
%> 

<a href="/v_whois_data_home.html"> WhoisData Home </a>
<%
	
	List list = null;
	list = WhoisDataDS.getInstance().getBySiteId(site.getId());

%>

<div id="whoisDataList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		WhoisData _WhoisData = (WhoisData) iter.next();	
%>

	<div id="whoisDataFrame<%=_WhoisData.getId() %>" >

		<div id="ip" >
			ip:<%= _WhoisData.getIp() %>
		</div>
		<div id="whoisData" >
			whoisData:<%= _WhoisData.getWhoisData() %>
		</div>
		<div id="server" >
			server:<%= _WhoisData.getServer() %>
		</div>
		<div id="forceRequest" >
			forceRequest:<%= _WhoisData.getForceRequest() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _WhoisData.getTimeCreated() %>
		</div>
		<div id="timeExpired" >
			timeExpired:<%= _WhoisData.getTimeExpired() %>
		</div>
		<div>
		<a id="whoisDataDeleteButton" href="javascript:deleteThis('/whoisDataAction.html',<%= _WhoisData.getId()%>,'whoisDataFrame<%=_WhoisData.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="whoisDataForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="whoisDataFormAdd" method="POST" action="/whoisDataAction.html" id="whoisDataFormAdd">

	<div id="whoisDataForm_ip_field">
    <div id="whoisDataForm_ip_label" class="formRequiredLabel" >Ip* </div>
    <div id="whoisDataForm_ip_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_ip" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="whoisDataForm_whoisData_field">
    <div id="whoisDataForm_whoisData_label" class="formLabel" >Whois Data </div>
    <div id="whoisDataForm_whoisData_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_whoisData" class="field" NAME="whoisData" COLS="50" ROWS="8" ><%=WebUtil.display(_whois_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="whoisDataForm_server_field">
    <div id="whoisDataForm_server_label" class="formRequiredLabel" >Server*</div>
    <div id="whoisDataForm_server_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="server" id="_ffd_server">
        <option value="" >- Please Select -</option>
<%
	List dropMenusServer = DropMenuUtil.getDropMenus("WhoisDataServerOption");
	for(Iterator iterItems = dropMenusServer.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _serverValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="whoisDataForm_forceRequest_field">
    <div id="whoisDataForm_forceRequest_label" class="formLabel" >Force Request </div>
    <div id="whoisDataForm_forceRequest_dropdown" class="formFieldDropDown" >       
        <select name="forceRequest" id="_ffd_forceRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _force_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _force_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>

        <div id="whoisDataFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/whoisDataAction.html', 'whoisDataFormAdd', 'formSubmit_ajax', 'whoisData');">Submit</a>
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
function responseCallback(data){
	document.getElementById("resultDisplayWhoisData").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('whoisDataFormAddDis','/whoisDataAction.html', 'resultDisplayWhoisData', '${ajax_response_fields}', responseCallback);
}

function xmlhttpPostXX(formName, target, dispElementId, dispFieldList,  callback) {
	
	if (document.getElementById(formName) == null){
		alert("Client side Error occurred. Please try again.")
		return;
	}
	
	var parms = getXX(document.getElementById(formName));
	parms += "&ajaxRequest=true&ajaxOut=getlisthtml&formfieldlist="+dispFieldList;
	
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
        			updatepageXX(dispElementId, "resultDisplayWhoisData", self.xmlHttpReq.responseText);
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

function backToXX(eid){
	document.getElementById("resultDisplayWhoisData").innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

</script>


<form name="whoisDataFormAddDis" method="POST" action="/whoisDataAction.html" id="whoisDataFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Ip</div>
        <input class="requiredField" id="ip" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Whois Data</div>
		<TEXTAREA id="whoisData" class="field" NAME="whoisData" COLS="50" ROWS="8" ><%=WebUtil.display(_whois_dataValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Server</div>
        <select class="requiredField" name="server" id="server">
        <option value="" >- Please Select -</option>
<%
	List dropMenus = DropMenuUtil.getDropMenus("WhoisDataServerOption");
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _serverValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Force Request</div>
        <select name="forceRequest" id="forceRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _force_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _force_requestValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Created</div>
		<br/>
		 <div class="ajaxFormLabel"> Time Expired</div>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('whoisDataFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayWhoisData"></span>
<a href="javascript:backToXX('whoisDataFormAddDis')">show back</a><br>
<hr/>
