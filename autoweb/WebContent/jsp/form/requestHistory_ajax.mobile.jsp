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
	if (cancelPage == null) cancelPage = "request_history_home";

    String _forward_site_idValue= WebUtil.display((String)reqParams.get("forwardSiteId"));
    String _is_droppedValue= WebUtil.display((String)reqParams.get("isDropped"));
    String _is_pagelessValue= WebUtil.display((String)reqParams.get("isPageless"));
    String _is_loginValue= WebUtil.display((String)reqParams.get("isLogin"));
    String _is_ajaxValue= WebUtil.display((String)reqParams.get("isAjax"));
    String _is_robotValue= WebUtil.display((String)reqParams.get("isRobot"));
    String _useridValue= WebUtil.display((String)reqParams.get("userid"));
    String _user_agentValue= WebUtil.display((String)reqParams.get("userAgent"));
    String _referValue= WebUtil.display((String)reqParams.get("refer"));
    String _robotValue= WebUtil.display((String)reqParams.get("robot"));
    String _remote_ipValue= WebUtil.display((String)reqParams.get("remoteIp"));
    String _site_urlValue= WebUtil.display((String)reqParams.get("siteUrl"));
    String _uriValue= WebUtil.display((String)reqParams.get("uri"));
    String _queryValue= WebUtil.display((String)reqParams.get("query"));
    String _rpciValue= WebUtil.display((String)reqParams.get("rpci"));
    String _session_idValue= WebUtil.display((String)reqParams.get("sessionId"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
%> 

<a href="/v_request_history_home.html"> RequestHistory Home </a>
<%
	
	List list = null;
	list = RequestHistoryDS.getInstance().getBySiteId(site.getId());

%>

<div id="requestHistoryList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		RequestHistory _RequestHistory = (RequestHistory) iter.next();	
%>

	<div id="requestHistoryFrame<%=_RequestHistory.getId() %>" >

		<div id="forwardSiteId" >
			forwardSiteId:<%= _RequestHistory.getForwardSiteId() %>
		</div>
		<div id="isDropped" >
			isDropped:<%= _RequestHistory.getIsDropped() %>
		</div>
		<div id="isPageless" >
			isPageless:<%= _RequestHistory.getIsPageless() %>
		</div>
		<div id="isLogin" >
			isLogin:<%= _RequestHistory.getIsLogin() %>
		</div>
		<div id="isAjax" >
			isAjax:<%= _RequestHistory.getIsAjax() %>
		</div>
		<div id="isRobot" >
			isRobot:<%= _RequestHistory.getIsRobot() %>
		</div>
		<div id="userid" >
			userid:<%= _RequestHistory.getUserid() %>
		</div>
		<div id="userAgent" >
			userAgent:<%= _RequestHistory.getUserAgent() %>
		</div>
		<div id="refer" >
			refer:<%= _RequestHistory.getRefer() %>
		</div>
		<div id="robot" >
			robot:<%= _RequestHistory.getRobot() %>
		</div>
		<div id="remoteIp" >
			remoteIp:<%= _RequestHistory.getRemoteIp() %>
		</div>
		<div id="siteUrl" >
			siteUrl:<%= _RequestHistory.getSiteUrl() %>
		</div>
		<div id="uri" >
			uri:<%= _RequestHistory.getUri() %>
		</div>
		<div id="query" >
			query:<%= _RequestHistory.getQuery() %>
		</div>
		<div id="rpci" >
			rpci:<%= _RequestHistory.getRpci() %>
		</div>
		<div id="sessionId" >
			sessionId:<%= _RequestHistory.getSessionId() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _RequestHistory.getTimeCreated() %>
		</div>
		<div>
		<a id="requestHistoryDeleteButton" href="javascript:deleteThis('/requestHistoryAction.html',<%= _RequestHistory.getId()%>,'requestHistoryFrame<%=_RequestHistory.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="requestHistoryForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="requestHistoryFormAdd" method="POST" action="/requestHistoryAction.html" id="requestHistoryFormAdd">

	<div id="requestHistoryForm_forwardSiteId_field">
    <div id="requestHistoryForm_forwardSiteId_label" class="formLabel" >Forward Site Id </div>
    <div id="requestHistoryForm_forwardSiteId_text" class="formFieldText" >       
        <input class="field" id="_ffd_forwardSiteId" type="text" size="70" name="forwardSiteId" value="<%=WebUtil.display(_forward_site_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_isDropped_field">
    <div id="requestHistoryForm_isDropped_label" class="formLabel" >Is Dropped </div>
    <div id="requestHistoryForm_isDropped_text" class="formFieldText" >       
        <input class="field" id="_ffd_isDropped" type="text" size="70" name="isDropped" value="<%=WebUtil.display(_is_droppedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_isPageless_field">
    <div id="requestHistoryForm_isPageless_label" class="formLabel" >Is Pageless </div>
    <div id="requestHistoryForm_isPageless_text" class="formFieldText" >       
        <input class="field" id="_ffd_isPageless" type="text" size="70" name="isPageless" value="<%=WebUtil.display(_is_pagelessValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_isLogin_field">
    <div id="requestHistoryForm_isLogin_label" class="formLabel" >Is Login </div>
    <div id="requestHistoryForm_isLogin_text" class="formFieldText" >       
        <input class="field" id="_ffd_isLogin" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_isAjax_field">
    <div id="requestHistoryForm_isAjax_label" class="formLabel" >Is Ajax </div>
    <div id="requestHistoryForm_isAjax_text" class="formFieldText" >       
        <input class="field" id="_ffd_isAjax" type="text" size="70" name="isAjax" value="<%=WebUtil.display(_is_ajaxValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_isRobot_field">
    <div id="requestHistoryForm_isRobot_label" class="formLabel" >Is Robot </div>
    <div id="requestHistoryForm_isRobot_text" class="formFieldText" >       
        <input class="field" id="_ffd_isRobot" type="text" size="70" name="isRobot" value="<%=WebUtil.display(_is_robotValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_userid_field">
    <div id="requestHistoryForm_userid_label" class="formLabel" >Userid </div>
    <div id="requestHistoryForm_userid_text" class="formFieldText" >       
        <input class="field" id="_ffd_userid" type="text" size="70" name="userid" value="<%=WebUtil.display(_useridValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_userAgent_field">
    <div id="requestHistoryForm_userAgent_label" class="formLabel" >User Agent </div>
    <div id="requestHistoryForm_userAgent_text" class="formFieldText" >       
        <input class="field" id="_ffd_userAgent" type="text" size="70" name="userAgent" value="<%=WebUtil.display(_user_agentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_refer_field">
    <div id="requestHistoryForm_refer_label" class="formLabel" >Refer </div>
    <div id="requestHistoryForm_refer_text" class="formFieldText" >       
        <input class="field" id="_ffd_refer" type="text" size="70" name="refer" value="<%=WebUtil.display(_referValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_robot_field">
    <div id="requestHistoryForm_robot_label" class="formLabel" >Robot </div>
    <div id="requestHistoryForm_robot_text" class="formFieldText" >       
        <input class="field" id="_ffd_robot" type="text" size="70" name="robot" value="<%=WebUtil.display(_robotValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_remoteIp_field">
    <div id="requestHistoryForm_remoteIp_label" class="formLabel" >Remote Ip </div>
    <div id="requestHistoryForm_remoteIp_text" class="formFieldText" >       
        <input class="field" id="_ffd_remoteIp" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_siteUrl_field">
    <div id="requestHistoryForm_siteUrl_label" class="formLabel" >Site Url </div>
    <div id="requestHistoryForm_siteUrl_text" class="formFieldText" >       
        <input class="field" id="_ffd_siteUrl" type="text" size="70" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_uri_field">
    <div id="requestHistoryForm_uri_label" class="formLabel" >Uri </div>
    <div id="requestHistoryForm_uri_text" class="formFieldText" >       
        <input class="field" id="_ffd_uri" type="text" size="70" name="uri" value="<%=WebUtil.display(_uriValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_query_field">
    <div id="requestHistoryForm_query_label" class="formLabel" >Query </div>
    <div id="requestHistoryForm_query_text" class="formFieldText" >       
        <input class="field" id="_ffd_query" type="text" size="70" name="query" value="<%=WebUtil.display(_queryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_rpci_field">
    <div id="requestHistoryForm_rpci_label" class="formLabel" >Rpci </div>
    <div id="requestHistoryForm_rpci_text" class="formFieldText" >       
        <input class="field" id="_ffd_rpci" type="text" size="70" name="rpci" value="<%=WebUtil.display(_rpciValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="requestHistoryForm_sessionId_field">
    <div id="requestHistoryForm_sessionId_label" class="formLabel" >Session Id </div>
    <div id="requestHistoryForm_sessionId_text" class="formFieldText" >       
        <input class="field" id="_ffd_sessionId" type="text" size="70" name="sessionId" value="<%=WebUtil.display(_session_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="requestHistoryFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/requestHistoryAction.html', 'requestHistoryFormAdd', 'formSubmit_ajax', 'requestHistory');">Submit</a>
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
	document.getElementById("resultDisplayRequestHistory").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('requestHistoryFormAddDis','/requestHistoryAction.html', 'resultDisplayRequestHistory', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="requestHistoryFormAddDis" method="POST" action="/requestHistoryAction.html" id="requestHistoryFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Forward Site Id</div>
        <input class="field" id="forwardSiteId" type="text" size="70" name="forwardSiteId" value="<%=WebUtil.display(_forward_site_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Dropped</div>
        <input class="field" id="isDropped" type="text" size="70" name="isDropped" value="<%=WebUtil.display(_is_droppedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Pageless</div>
        <input class="field" id="isPageless" type="text" size="70" name="isPageless" value="<%=WebUtil.display(_is_pagelessValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Login</div>
        <input class="field" id="isLogin" type="text" size="70" name="isLogin" value="<%=WebUtil.display(_is_loginValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Ajax</div>
        <input class="field" id="isAjax" type="text" size="70" name="isAjax" value="<%=WebUtil.display(_is_ajaxValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Robot</div>
        <input class="field" id="isRobot" type="text" size="70" name="isRobot" value="<%=WebUtil.display(_is_robotValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Userid</div>
        <input class="field" id="userid" type="text" size="70" name="userid" value="<%=WebUtil.display(_useridValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> User Agent</div>
        <input class="field" id="userAgent" type="text" size="70" name="userAgent" value="<%=WebUtil.display(_user_agentValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Refer</div>
        <input class="field" id="refer" type="text" size="70" name="refer" value="<%=WebUtil.display(_referValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Robot</div>
        <input class="field" id="robot" type="text" size="70" name="robot" value="<%=WebUtil.display(_robotValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Remote Ip</div>
        <input class="field" id="remoteIp" type="text" size="70" name="remoteIp" value="<%=WebUtil.display(_remote_ipValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Site Url</div>
        <input class="field" id="siteUrl" type="text" size="70" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Uri</div>
        <input class="field" id="uri" type="text" size="70" name="uri" value="<%=WebUtil.display(_uriValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Query</div>
        <input class="field" id="query" type="text" size="70" name="query" value="<%=WebUtil.display(_queryValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Rpci</div>
        <input class="field" id="rpci" type="text" size="70" name="rpci" value="<%=WebUtil.display(_rpciValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Session Id</div>
        <input class="field" id="sessionId" type="text" size="70" name="sessionId" value="<%=WebUtil.display(_session_idValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('requestHistoryFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayRequestHistory"></span>
<a href="javascript:backToXX('requestHistoryFormAddDis','resultDisplayRequestHistory')">show back</a><br>
<hr/>
