<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "blog_comment_home";

    String _blog_main_idValue= WebUtil.display((String)reqParams.get("blogMainId"));
    String _blog_post_idValue= WebUtil.display((String)reqParams.get("blogPostId"));
    String _commentValue= WebUtil.display((String)reqParams.get("comment"));
    String _ratingValue= WebUtil.display((String)reqParams.get("rating"));
    String _ipaddressValue= WebUtil.display((String)reqParams.get("ipaddress"));
    String _nameValue= WebUtil.display((String)reqParams.get("name"));
    String _passwordValue= WebUtil.display((String)reqParams.get("password"));
    String _websiteValue= WebUtil.display((String)reqParams.get("website"));
    String _emailValue= WebUtil.display((String)reqParams.get("email"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
%> 

<a href="/v_blog_comment_home.html"> BlogComment Home </a>
<%
	
	List list = null;
	list = BlogCommentDS.getInstance().getBySiteId(site.getId());

%>

<div id="blogCommentList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		BlogComment _BlogComment = (BlogComment) iter.next();	
%>

	<div id="blogCommentFrame<%=_BlogComment.getId() %>" >

		<div id="blogMainId" >
			blogMainId:<%= _BlogComment.getBlogMainId() %>
		</div>
		<div id="blogPostId" >
			blogPostId:<%= _BlogComment.getBlogPostId() %>
		</div>
		<div id="comment" >
			comment:<%= _BlogComment.getComment() %>
		</div>
		<div id="rating" >
			rating:<%= _BlogComment.getRating() %>
		</div>
		<div id="name" >
			name:<%= _BlogComment.getName() %>
		</div>
		<div id="password" >
			password:<%= _BlogComment.getPassword() %>
		</div>
		<div id="website" >
			website:<%= _BlogComment.getWebsite() %>
		</div>
		<div id="email" >
			email:<%= _BlogComment.getEmail() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _BlogComment.getTimeCreated() %>
		</div>
		<div>
		<a id="blogCommentDeleteButton" href="javascript:deleteThis('/blogCommentAction.html',<%= _BlogComment.getId()%>,'blogCommentFrame<%=_BlogComment.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="blogCommentForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blogCommentFormAdd" method="POST" action="/blogCommentAction.html" id="blogCommentFormAdd">

    <INPUT TYPE="HIDDEN" id="_ffd_blogMainId" NAME="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>" />

    <INPUT TYPE="HIDDEN" id="_ffd_blogPostId" NAME="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>" />

	<div id="blogCommentForm_comment_field">
    <div id="blogCommentForm_comment_label" class="formRequiredLabel" >Comment* </div>
    <div id="blogCommentForm_comment_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_comment" class="requiredField" NAME="comment" COLS="50" ROWS="8" ><%=WebUtil.display(_commentValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="blogCommentForm_rating_field">
    <div id="blogCommentForm_rating_label" class="formRequiredLabel" >Rating* </div>
    <div id="blogCommentForm_rating_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="rating" id="_ffd_rating">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ratingValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ratingValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _ratingValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _ratingValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _ratingValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _ratingValue)%>>5</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="blogCommentForm_name_field">
    <div id="blogCommentForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="blogCommentForm_name_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_name" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="blogCommentForm_password_field">
    <div id="blogCommentForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="blogCommentForm_password_password" class="formFieldPassword" > 
        <input class="requiredField" id="_ffd_password" type="password" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>      
    </div>      
	</div><div class="clear"></div>
	<div id="blogCommentForm_website_field">
    <div id="blogCommentForm_website_label" class="formLabel" >Website </div>
    <div id="blogCommentForm_website_text" class="formFieldText" >       
        <input class="field" id="_ffd_website" type="text" size="70" name="website" value="<%=WebUtil.display(_websiteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="blogCommentForm_email_field">
    <div id="blogCommentForm_email_label" class="formLabel" >Email </div>
    <div id="blogCommentForm_email_text" class="formFieldText" >       
        <input class="field" id="_ffd_email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="blogCommentFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/blogCommentAction.html', 'blogCommentFormAdd', 'formSubmit_ajax', 'blogComment');">Submit</a>
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
	document.getElementById("resultDisplay").innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

//--------------------------------- 

function sendVoteAndDisappear(pollId, target){

	var params = getPollAnswer('pollVoteForm' + pollId);
	if (params == null) return;
	
	params += "&ajaxRequest=true&ajaxOut=getResultHtml3";
	params += "&add=true&scriptSource=1&pollId=" + pollId;
	
	// Can't use. Security prevents the code from loading results from other servers. 
	//sendPollAnswer("http://"+ target +"/pollVoteAction.html", params, true, "pollVoteFormFrame"+pollId, "resultDisplayPoll"+pollId);

	// The code is working this way
	// Since ajax can't load from the other server, it appends the <script> code into head. 
	// This codes does look for result variable and assign html that were loaded. 
	// html code in head will look like,
 	// element = getById
	// element.innerHtml="<html code to show>"
	
	var url = "http://"+ target +"/pollVoteAction.html?"+params;
	alert(url);
	

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
	var params = getPollAnswer('pollVoteForm' + pollId);
	if (params == null) return;
	
	params += "&ajaxRequest=true&ajaxOut=getResultHtml3";
	params += "&add=true&pollId=" + pollId;
	
	sendPollAnswer("http://"+ target +"/pollVoteAction.html", params, false, "pollVoteFormFrame"+pollId, "resultDisplayPoll"+pollId);
}

function viewResults(target, serial){
	window.open("http://"+ target +"/t_poll_result_single.html?prv_serial="+serial);
}

function getPollAnswer(formId){

	var formObj = document.getElementById(formId);
	var val = getCheckedRadioValue(formObj.answer);
	
	if (val!= null && val != ""){
		return "answer="+val;	
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
    
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
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


<form name="blogCommentFormAddDis" method="POST" action="/blogCommentAction.html" id="blogCommentFormAddDis">

		<INPUT TYPE="HIDDEN" NAME="add" value="true" />

    	<INPUT TYPE="HIDDEN" id="blogMainId" NAME="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>" />
		<br/>
    	<INPUT TYPE="HIDDEN" id="blogPostId" NAME="blogPostId" value="<%=WebUtil.display(_blog_post_idValue)%>" />
		<br/>
		 <div class="ajaxFormLabel"> Comment</div>
		<TEXTAREA id="comment" class="requiredField" NAME="comment" COLS="50" ROWS="8" ><%=WebUtil.display(_commentValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Rating</div>
        <select class="requiredField" name="rating" id="rating">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ratingValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ratingValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _ratingValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _ratingValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _ratingValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _ratingValue)%>>5</option>
        </select> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Name</div>
        <input class="requiredField" id="name" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Password</div>
        <input class="requiredField" id="password" type="password" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>      
		<br/>
		 <div class="ajaxFormLabel"> Website</div>
        <input class="field" id="website" type="text" size="70" name="website" value="<%=WebUtil.display(_websiteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Email</div>
        <input class="field" id="email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Created</div>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('blogCommentFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplay"></span>
<a href="javascript:backToXX('blogCommentFormAddDis')">show back</a><br>
<hr/>



<span style="width: 175px;" id="resultDisplayPoll90"></span>

<br/>
<br/>
<br/>
<div style="background-color:white; padding: 0 0 10px 0;" id="pollVoteFormFrame90" ><form name="pollVoteForm90" method="post" action="http://www.zapoll.com:8080/pollVoteAction.html" id="pollVoteForm90" ><table width="100%"  style="border: 5px solid #F5A236; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana"><tr><td style="border-bottom: 5px solid #F5A236; font: normal normal bold 12px verdana;color: #F5A236; padding : 5 5 5 5; ">Who Will Won This Worldcup?</td></tr><tr><td style="border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; "><input id="answer90" type="radio" name="answer"  value="1"/>Argentina</td></tr><tr><td style="border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; "><input id="answer90" type="radio" name="answer"  value="2"/>England</td></tr><tr><td style="border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; "><input id="answer90" type="radio" name="answer"  value="3"/>Brazil</td></tr><tr><td style="border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; "><input id="answer90" type="radio" name="answer"  value="4"/>Spain</td></tr></table><INPUT TYPE="hidden" NAME="add" value="true"><INPUT TYPE="HIDDEN" NAME="pollId" value="90"></form><a  style="font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; " href="javascript:sendVoteAndDisappear(90, 'www.zapoll.com:8080');"  onMouseOver="this.style.color='blue';" onMouseOut="this.style.color='#CF2B19';">VOTE</a><a  style="font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; " href="javascript:viewResults('www.zapoll.com:8080','j6A79aK1ChcufV3NMiXt');" onMouseOver="this.style.color='blue';" onMouseOut="this.style.color='#CF2B19';">View Result</a></div>
