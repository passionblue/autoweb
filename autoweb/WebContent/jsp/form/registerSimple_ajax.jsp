<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "register_simple_home";

    String _first_nameValue= WebUtil.display((String)reqParams.get("firstName"));
    String _last_nameValue= WebUtil.display((String)reqParams.get("lastName"));
    String _emailValue= WebUtil.display((String)reqParams.get("email"));
    String _usernameValue= WebUtil.display((String)reqParams.get("username"));
    String _passwordValue= WebUtil.display((String)reqParams.get("password"));
    String _birth_yearValue= WebUtil.display((String)reqParams.get("birthYear"));
    String _birth_monthValue= WebUtil.display((String)reqParams.get("birthMonth"));
    String _birth_dayValue= WebUtil.display((String)reqParams.get("birthDay"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_register_simple_home.html"> RegisterSimple Home </a>
<%
	
	List list = null;
	list = RegisterSimpleDS.getInstance().getBySiteId(site.getId());

%>

<div id="registerSimpleList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		RegisterSimple _RegisterSimple = (RegisterSimple) iter.next();	
%>

	<div id="registerSimpleFrame<%=_RegisterSimple.getId() %>" >

		<div id="firstName" >
			firstName:<%= _RegisterSimple.getFirstName() %>
		</div>
		<div id="lastName" >
			lastName:<%= _RegisterSimple.getLastName() %>
		</div>
		<div id="email" >
			email:<%= _RegisterSimple.getEmail() %>
		</div>
		<div id="username" >
			username:<%= _RegisterSimple.getUsername() %>
		</div>
		<div id="password" >
			password:<%= _RegisterSimple.getPassword() %>
		</div>
		<div id="birthYear" >
			birthYear:<%= _RegisterSimple.getBirthYear() %>
		</div>
		<div id="birthMonth" >
			birthMonth:<%= _RegisterSimple.getBirthMonth() %>
		</div>
		<div id="birthDay" >
			birthDay:<%= _RegisterSimple.getBirthDay() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _RegisterSimple.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _RegisterSimple.getTimeUpdated() %>
		</div>
		<div>
		<a id="registerSimpleDeleteButton" href="javascript:deleteThis('/registerSimpleAction.html',<%= _RegisterSimple.getId()%>,'registerSimpleFrame<%=_RegisterSimple.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="registerSimpleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="registerSimpleFormAdd" method="POST" action="/registerSimpleAction.html" id="registerSimpleFormAdd">

	<div id="registerSimpleForm_firstName_field">
    <div id="registerSimpleForm_firstName_label" class="formLabel" >First Name </div>
    <div id="registerSimpleForm_firstName_text" class="formFieldText" >       
        <input class="field" id="_ffd_firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="registerSimpleForm_lastName_field">
    <div id="registerSimpleForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="registerSimpleForm_lastName_text" class="formFieldText" >       
        <input class="field" id="_ffd_lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="registerSimpleForm_email_field">
    <div id="registerSimpleForm_email_label" class="formLabel" >Email </div>
    <div id="registerSimpleForm_email_text" class="formFieldText" >       
        <input class="field" id="_ffd_email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="registerSimpleForm_username_field">
    <div id="registerSimpleForm_username_label" class="formLabel" >Username </div>
    <div id="registerSimpleForm_username_text" class="formFieldText" >       
        <input class="field" id="_ffd_username" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="registerSimpleForm_password_field">
    <div id="registerSimpleForm_password_label" class="formLabel" >Password </div>
    <div id="registerSimpleForm_password_password" class="formFieldPassword" >
        <input class="field" id="_ffd_password" type="password" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/><span></span>      
    </div>      
	</div><div class="clear"></div>
    <INPUT TYPE="HIDDEN" id="_ffd_birthYear" NAME="birthYear" value="<%=WebUtil.display(_birth_yearValue)%>" />

    <INPUT TYPE="HIDDEN" id="_ffd_birthMonth" NAME="birthMonth" value="<%=WebUtil.display(_birth_monthValue)%>" />

    <INPUT TYPE="HIDDEN" id="_ffd_birthDay" NAME="birthDay" value="<%=WebUtil.display(_birth_dayValue)%>" />


        <div id="registerSimpleFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/registerSimpleAction.html', 'registerSimpleFormAdd', 'formSubmit_ajax', 'registerSimple');">Submit</a>
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
	document.getElementById("resultDisplayRegisterSimple").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('registerSimpleFormAddDis','/registerSimpleAction.html', 'resultDisplayRegisterSimple', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="registerSimpleFormAddDis" method="POST" action="/registerSimpleAction.html" id="registerSimpleFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> First Name</div>
        <input class="field" id="firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Last Name</div>
        <input class="field" id="lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Email</div>
        <input class="field" id="email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Username</div>
        <input class="field" id="username" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Password</div>
        <input class="field" id="password" type="password" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/><span></span>      
		<br/>
    	<INPUT TYPE="HIDDEN" id="birthYear" NAME="birthYear" value="<%=WebUtil.display(_birth_yearValue)%>" />
		<br/>
    	<INPUT TYPE="HIDDEN" id="birthMonth" NAME="birthMonth" value="<%=WebUtil.display(_birth_monthValue)%>" />
		<br/>
    	<INPUT TYPE="HIDDEN" id="birthDay" NAME="birthDay" value="<%=WebUtil.display(_birth_dayValue)%>" />
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('registerSimpleFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayRegisterSimple"></span>
<a href="javascript:backToXX('registerSimpleFormAddDis','resultDisplayRegisterSimple')">show back</a><br>
<hr/>
