<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

%> 

<script type="text/javascript">

function responseCallback(data){
	document.getElementById("resultDisplay").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('blogCommentFormAddDis','/blogCommentAction.html', 'resultDisplay', 'comment,email', responseCallback);
}

function xmlhttpPostXX(formName, target, dispElementId, dispFieldList,  callback) {
	
	if (document.getElementById(formName) == null){
		alert("Client side Error occurred. Please try again.")
		return;
	}
	
	var parms = getXX(document.getElementById(formName));
	parms += "&ajaxRequest=true&ajaxOut=getlisthtml&ajaxOutArg=last&formfieldlist="+dispFieldList;
	
    var xmlHttpReq = false;
    var self = this;
    
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var strURL = target+ "?" + parms;
    
    self.xmlHttpReq.open('POST', target, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
    	if (self.xmlHttpReq.readyState == 4) {
        	fade(formName, 1000, function() {
        		if (callback == null )
        			updatepageXX(dispElementId, "resultDisplay", self.xmlHttpReq.responseText);
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
	document.getElementById("resultDisplay").innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}
function backToPoll(pollId){
	document.getElementById("resultDisplayPoll" + pollId).innerHTML = "";
	var eid = "pollVoteFormFrame" + pollId;
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

//========================================================================================================
function sendVoteAndDisappear(pollId, target){
	var pollAnswer = getPollAnswer('pollVoteForm' + pollId);
	if (pollAnswer == null) return;

	var params = "answer="+ pollAnswer;

	
	var obj = document.getElementById("ownAnswer" + pollId);
	if (obj!= null) {
		params +="&ownAnswer="+obj.value;
	}

	if (pollAnswer == -1 && obj == null) {
		return;
	} 

	if (pollAnswer == -1 && (obj.value == null || obj.value=="" )) {
		alert("Please enter your own answer.");
		return;
	} 
	
	params += "&ajaxRequest=true&ajaxOut=getResultHtml3";
	params += "&add=true&scriptSource=1&pollId=" + pollId;
	
	var url = "http://"+ target +"/pollVoteAction.html?"+params;

	fadeXX("pollVoteFormFrame"+pollId, 500, displayPollResult, url);
}

function sendViewAndDisappear(pollId, target){
	var params = "ajaxRequest=true&ajaxOut=getResultHtml3";
	params += "&scriptSource=1&pollId=" + pollId;
	
	var url = "http://"+ target +"/pollVoteAction.html?"+params;

	fadeXX("pollVoteFormFrame"+pollId, 500, displayPollResult, url);
}


function displayPollResult(url){
	
	var html_doc = document.getElementsByTagName('head').item(0);
  	var js = document.createElement('script');
  	js.setAttribute('type', 'text/javascript');
  	js.setAttribute('src', url);
	html_doc.appendChild(js);
}

function sendVote(pollId, target){
	var pollAnswer = getPollAnswer('pollVoteForm' + pollId);
	if (pollAnswer == null) return;

	var params = "answer="+ pollAnswer;

	params += "&ajaxRequest=true&ajaxOut=getResultHtml3";
	params += "&add=true&pollId=" + pollId;

	alert(params);
	sendPollAnswer("http://"+ target +"/pollVoteAction.html", params, false, "pollVoteFormFrame"+pollId, "resultDisplayPoll"+pollId);
}

function viewResults(target, serial){
	window.open("http://"+ target +"/t_poll_result_single.html?prv_serial="+serial);
}

function getPollAnswer(formId){

	var formObj = document.getElementById(formId);
	var val = getCheckedRadioValue(formObj.answer);
	if (val!= null && val != ""){
		return val;	
	} else {
		alert("Please select your vote. Thanks");
		return null;
	}
}

function getCheckedRadioValue(radioObj) {
	if(!radioObj) return "";
	
	var radioLength = radioObj.length;
	
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

function sendPollAnswer(target, parms, disappear, elementId, resultElementId, callback) {
	
    var xmlHttpReq = false;
    var self = this;
    
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }

    var strURL = target+ "?" + parms;
    alert(strURL);
    
    self.xmlHttpReq.open('GET', target+"?" + parms , true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
    	if (self.xmlHttpReq.readyState == 4) {
    		alert(self.xmlHttpReq.responseText);
    		if (disappear){
        	fade(elementId, 200, function() {
        		if (callback == null ) {
        			updatepageXX(resultElementId, self.xmlHttpReq.responseText);
        		}
        		else
        			callback(self.xmlHttpReq.responseText);
        	});
        	} else {
        		if (callback == null )
        			updatepageXX(resultElementId, self.xmlHttpReq.responseText);
        		else
        			callback(self.xmlHttpReq.responseText);
        	}
        }
    }
    self.xmlHttpReq.send();
}

function mouseover(obj){
	obj.style.color="blue";
}

function mouseout(obj){
	obj.style.color="#CF2B19";
}
</script>

<table width="175px"> <tr><td>
<div style="background-color: white; padding: 0 0 10px 0;"	id="pollVoteFormFrame99">
	<form name="pollVoteForm99" method="post"
		action="http://www.zapoll.com:8080/pollVoteAction.html"
		id="pollVoteForm99">
		<table width="100%"
			style="border: 5px solid #F5A236; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana">
			<tr>
				<td
					style="border-bottom: 5px solid #F5A236; font: normal normal bold 12px verdana; color: #F5A236; padding: 5 5 5 5;">
					Yes No Poll
				</td>
			</tr>
			<tr>
				<td
					style="border: 1px solid #F5A236; font: normal normal normal 12px verdana;; color: #F5A236; padding: 5 0 5 0;">
					<input id="answer99" type="radio" name="answer" value="1" />
					Yes
				</td>
			</tr>
			<tr>
				<td
					style="border: 1px solid #F5A236; font: normal normal normal 12px verdana;; color: #F5A236; padding: 5 0 5 0;">
					<input id="answer99" type="radio" name="answer" value="2" />
					No
				</td>
			</tr>

			<tr>
				<td
					style="border: 1px solid #F5A236; font: normal normal normal 12px verdana;; color: #F5A236; padding: 5 0 5 0;">
					<input id="answer99" type="radio" name="answer" value="-1" />
					-my Own Answer-<br/><br/>
					&nbsp;<input id="ownAnswer99" type="text" name="ownAnswer" value="" />
				</td>
			</tr>
			
		</table>
		<INPUT TYPE="hidden" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="pollId" value="99">
	</form>
	<a
		style="font: normal normal bold 12px verdana; padding: 5px 5px 5px 5px; text-decoration: none; margin: 0 0 5 0; color: #CF2B19;"
		href="javascript:sendVoteAndDisappear(99, 'www.zapoll.com:8080');"
		onMouseOver="this.style.color='blue';"
		onMouseOut="this.style.color='#CF2B19';">VOTE</a>
	<a
		style="font: normal normal bold 12px verdana; padding: 5px 5px 5px 5px; text-decoration: none; margin: 0 0 5 0; color: #CF2B19;"
		href="javascript:sendViewAndDisappear(99, 'www.zapoll.com:8080');"
		onMouseOver="this.style.color='blue';"
		onMouseOut="this.style.color='#CF2B19';">View Result</a>
</div>

<span id="resultDisplayPoll99"></span>

</td></tr>
</table>







