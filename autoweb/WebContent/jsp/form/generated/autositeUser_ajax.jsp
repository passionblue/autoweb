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
	if (cancelPage == null) cancelPage = "autosite_user_home";

    String _usernameValue= WebUtil.display((String)reqParams.get("username"));
    String _passwordValue= WebUtil.display((String)reqParams.get("password"));
    String _emailValue= WebUtil.display((String)reqParams.get("email"));
    String _user_typeValue= WebUtil.display((String)reqParams.get("userType"));
    String _first_nameValue= WebUtil.display((String)reqParams.get("firstName"));
    String _last_nameValue= WebUtil.display((String)reqParams.get("lastName"));
    String _nicknameValue= WebUtil.display((String)reqParams.get("nickname"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
    String _disabledValue= WebUtil.display((String)reqParams.get("disabled"));
    String _time_disabledValue= WebUtil.display((String)reqParams.get("timeDisabled"));
    String _confirmedValue= WebUtil.display((String)reqParams.get("confirmed"));
    String _time_confirmedValue= WebUtil.display((String)reqParams.get("timeConfirmed"));
    String _opt_1Value= WebUtil.display((String)reqParams.get("opt1"));
    String _opt_2Value= WebUtil.display((String)reqParams.get("opt2"));
%> 

<a href="/v_autosite_user_home.html"> AutositeUser Home </a>
<%
	
	List list = null;
	list = AutositeUserDS.getInstance().getBySiteId(site.getId());

%>

<div id="autositeUserList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		AutositeUser _AutositeUser = (AutositeUser) iter.next();	
%>

	<div id="autositeUserFrame<%=_AutositeUser.getId() %>" >

		<div id="username" >
			username:<%= _AutositeUser.getUsername() %>
		</div>
		<div id="password" >
			password:<%= _AutositeUser.getPassword() %>
		</div>
		<div id="email" >
			email:<%= _AutositeUser.getEmail() %>
		</div>
		<div id="userType" >
			userType:<%= _AutositeUser.getUserType() %>
		</div>
		<div id="firstName" >
			firstName:<%= _AutositeUser.getFirstName() %>
		</div>
		<div id="lastName" >
			lastName:<%= _AutositeUser.getLastName() %>
		</div>
		<div id="nickname" >
			nickname:<%= _AutositeUser.getNickname() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _AutositeUser.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _AutositeUser.getTimeUpdated() %>
		</div>
		<div id="disabled" >
			disabled:<%= _AutositeUser.getDisabled() %>
		</div>
		<div id="timeDisabled" >
			timeDisabled:<%= _AutositeUser.getTimeDisabled() %>
		</div>
		<div id="confirmed" >
			confirmed:<%= _AutositeUser.getConfirmed() %>
		</div>
		<div id="timeConfirmed" >
			timeConfirmed:<%= _AutositeUser.getTimeConfirmed() %>
		</div>
		<div id="opt1" >
			opt1:<%= _AutositeUser.getOpt1() %>
		</div>
		<div id="opt2" >
			opt2:<%= _AutositeUser.getOpt2() %>
		</div>
		<div>
		<a id="autositeUserDeleteButton" href="javascript:deleteThis('/autositeUserAction.html',<%= _AutositeUser.getId()%>,'autositeUserFrame<%=_AutositeUser.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="autositeUserForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeUserFormAdd" method="POST" action="/autositeUserAction.html" id="autositeUserFormAdd">

	<div id="autositeUserForm_username_field">
    <div id="autositeUserForm_username_label" class="formLabel" >Username </div>
    <div id="autositeUserForm_username_text" class="formFieldText" >       
        <input class="field" id="_ffd_username" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_password_field">
    <div id="autositeUserForm_password_label" class="formLabel" >Password </div>
    <div id="autositeUserForm_password_text" class="formFieldText" >       
        <input class="field" id="_ffd_password" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_email_field">
    <div id="autositeUserForm_email_label" class="formLabel" >Email </div>
    <div id="autositeUserForm_email_text" class="formFieldText" >       
        <input class="field" id="_ffd_email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_userType_field">
    <div id="autositeUserForm_userType_label" class="formLabel" >User Type </div>
    <div id="autositeUserForm_userType_text" class="formFieldText" >       
        <input class="field" id="_ffd_userType" type="text" size="70" name="userType" value="<%=WebUtil.display(_user_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_firstName_field">
    <div id="autositeUserForm_firstName_label" class="formLabel" >First Name </div>
    <div id="autositeUserForm_firstName_text" class="formFieldText" >       
        <input class="field" id="_ffd_firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_lastName_field">
    <div id="autositeUserForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="autositeUserForm_lastName_text" class="formFieldText" >       
        <input class="field" id="_ffd_lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_nickname_field">
    <div id="autositeUserForm_nickname_label" class="formLabel" >Nickname </div>
    <div id="autositeUserForm_nickname_text" class="formFieldText" >       
        <input class="field" id="_ffd_nickname" type="text" size="70" name="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_timeUpdated_field">
    <div id="autositeUserForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="autositeUserForm_timeUpdated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_disabled_field">
    <div id="autositeUserForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="autositeUserForm_disabled_dropdown" class="formFieldDropDown" >       
        <select name="disabled" id="_ffd_disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_timeDisabled_field">
    <div id="autositeUserForm_timeDisabled_label" class="formLabel" >Time Disabled </div>
    <div id="autositeUserForm_timeDisabled_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeDisabled" type="text" size="70" name="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_confirmed_field">
    <div id="autositeUserForm_confirmed_label" class="formLabel" >Confirmed </div>
    <div id="autositeUserForm_confirmed_dropdown" class="formFieldDropDown" >       
        <select name="confirmed" id="_ffd_confirmed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _confirmedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _confirmedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_timeConfirmed_field">
    <div id="autositeUserForm_timeConfirmed_label" class="formLabel" >Time Confirmed </div>
    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeConfirmed" type="text" size="70" name="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_opt1_field">
    <div id="autositeUserForm_opt1_label" class="formLabel" >Opt 1 </div>
    <div id="autositeUserForm_opt1_text" class="formFieldText" >       
        <input class="field" id="_ffd_opt1" type="text" size="70" name="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="autositeUserForm_opt2_field">
    <div id="autositeUserForm_opt2_label" class="formLabel" >Opt 2 </div>
    <div id="autositeUserForm_opt2_text" class="formFieldText" >       
        <input class="field" id="_ffd_opt2" type="text" size="70" name="opt2" value="<%=WebUtil.display(_opt_2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="autositeUserFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/autositeUserAction.html', 'autositeUserFormAdd', 'formSubmit_ajax', 'autositeUser');">Submit</a>
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
	document.getElementById("resultDisplayAutositeUser").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('autositeUserFormAddDis','/autositeUserAction.html', 'resultDisplayAutositeUser', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="autositeUserFormAddDis" method="POST" action="/autositeUserAction.html" id="autositeUserFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Username</div>
        <input class="field" id="username" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Password</div>
        <input class="field" id="password" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Email</div>
        <input class="field" id="email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> User Type</div>
        <input class="field" id="userType" type="text" size="70" name="userType" value="<%=WebUtil.display(_user_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> First Name</div>
        <input class="field" id="firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Last Name</div>
        <input class="field" id="lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Nickname</div>
        <input class="field" id="nickname" type="text" size="70" name="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Updated</div>
        <input class="field" id="timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disabled</div>
        <select name="disabled" id="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Disabled</div>
        <input class="field" id="timeDisabled" type="text" size="70" name="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Confirmed</div>
        <select name="confirmed" id="confirmed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _confirmedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _confirmedValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Confirmed</div>
        <input class="field" id="timeConfirmed" type="text" size="70" name="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Opt 1</div>
        <input class="field" id="opt1" type="text" size="70" name="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Opt 2</div>
        <input class="field" id="opt2" type="text" size="70" name="opt2" value="<%=WebUtil.display(_opt_2Value)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('autositeUserFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayAutositeUser"></span>
<a href="javascript:backToXX('autositeUserFormAddDis','resultDisplayAutositeUser')">show back</a><br>
<hr/>
