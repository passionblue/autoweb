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
	if (cancelPage == null) cancelPage = "cleaner_register_start_home";

    String _site_titleValue= WebUtil.display((String)reqParams.get("siteTitle"));
    String _site_nameValue= WebUtil.display((String)reqParams.get("siteName"));
    String _usernameValue= WebUtil.display((String)reqParams.get("username"));
    String _emailValue= WebUtil.display((String)reqParams.get("email"));
    String _passwordValue= WebUtil.display((String)reqParams.get("password"));
    String _password_repeatValue= WebUtil.display((String)reqParams.get("passwordRepeat"));
    String _locationValue= WebUtil.display((String)reqParams.get("location"));
    String _created_site_urlValue= WebUtil.display((String)reqParams.get("createdSiteUrl"));
%> 

<a href="/v_cleaner_register_start_home.html"> CleanerRegisterStart Home </a>
<%
	
	List list = null;
	list = CleanerRegisterStartDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerRegisterStartList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerRegisterStartDataHolder _CleanerRegisterStart = (CleanerRegisterStartDataHolder) iter.next();	
%>

	<div id="cleanerRegisterStartFrame<%=_CleanerRegisterStart.getId() %>" >

		<div id="siteTitle" >
			siteTitle:<%= _CleanerRegisterStart.getSiteTitle() %>
		</div>
		<div id="siteName" >
			siteName:<%= _CleanerRegisterStart.getSiteName() %>
		</div>
		<div id="username" >
			username:<%= _CleanerRegisterStart.getUsername() %>
		</div>
		<div id="email" >
			email:<%= _CleanerRegisterStart.getEmail() %>
		</div>
		<div id="password" >
			password:<%= _CleanerRegisterStart.getPassword() %>
		</div>
		<div id="passwordRepeat" >
			passwordRepeat:<%= _CleanerRegisterStart.getPasswordRepeat() %>
		</div>
		<div id="location" >
			location:<%= _CleanerRegisterStart.getLocation() %>
		</div>
		<div id="createdSiteUrl" >
			createdSiteUrl:<%= _CleanerRegisterStart.getCreatedSiteUrl() %>
		</div>
		<div>
		<a id="cleanerRegisterStartDeleteButton" href="javascript:deleteThis('/cleanerRegisterStartAction.html',<%= _CleanerRegisterStart.getId()%>,'cleanerRegisterStartFrame<%=_CleanerRegisterStart.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerRegisterStartForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerRegisterStartFormAdd" method="POST" action="/cleanerRegisterStartAction.html" id="cleanerRegisterStartFormAdd">

	<div id="cleanerRegisterStartForm_siteTitle_field">
    <div id="cleanerRegisterStartForm_siteTitle_label" class="formLabel" >Site Title </div>
    <div id="cleanerRegisterStartForm_siteTitle_text" class="formFieldText" >       
        <input class="field" id="_ffd_siteTitle" type="text" size="70" name="siteTitle" value="<%=WebUtil.display(_site_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerRegisterStartForm_siteName_field">
    <div id="cleanerRegisterStartForm_siteName_label" class="formRequiredLabel" >Site Name* </div>
    <div id="cleanerRegisterStartForm_siteName_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_siteName" type="text" size="70" name="siteName" value="<%=WebUtil.display(_site_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerRegisterStartForm_username_field">
    <div id="cleanerRegisterStartForm_username_label" class="formLabel" >Username </div>
    <div id="cleanerRegisterStartForm_username_text" class="formFieldText" >       
        <input class="field" id="_ffd_username" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerRegisterStartForm_email_field">
    <div id="cleanerRegisterStartForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="cleanerRegisterStartForm_email_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerRegisterStartForm_password_field">
    <div id="cleanerRegisterStartForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="cleanerRegisterStartForm_password_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_password" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerRegisterStartForm_passwordRepeat_field">
    <div id="cleanerRegisterStartForm_passwordRepeat_label" class="formRequiredLabel" >Password Repeat* </div>
    <div id="cleanerRegisterStartForm_passwordRepeat_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_passwordRepeat" type="text" size="70" name="passwordRepeat" value="<%=WebUtil.display(_password_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerRegisterStartForm_location_field">
    <div id="cleanerRegisterStartForm_location_label" class="formLabel" >Location </div>
    <div id="cleanerRegisterStartForm_location_text" class="formFieldText" >       
        <input class="field" id="_ffd_location" type="text" size="70" name="location" value="<%=WebUtil.display(_locationValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerRegisterStartForm_createdSiteUrl_field">
    <div id="cleanerRegisterStartForm_createdSiteUrl_label" class="formLabel" >Created Site Url </div>
    <div id="cleanerRegisterStartForm_createdSiteUrl_text" class="formFieldText" >       
        <input class="field" id="_ffd_createdSiteUrl" type="text" size="70" name="createdSiteUrl" value="<%=WebUtil.display(_created_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerRegisterStartFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerRegisterStartAction.html', 'cleanerRegisterStartFormAdd', 'formSubmit_ajax', 'cleanerRegisterStart');">Submit</a>
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
	document.getElementById("resultDisplayCleanerRegisterStart").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerRegisterStartFormAddDis','/cleanerRegisterStartAction.html', 'resultDisplayCleanerRegisterStart', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerRegisterStartFormAddDis" method="POST" action="/cleanerRegisterStartAction.html" id="cleanerRegisterStartFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Site Title</div>
        <input class="field" id="siteTitle" type="text" size="70" name="siteTitle" value="<%=WebUtil.display(_site_titleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Site Name</div>
        <input class="requiredField" id="siteName" type="text" size="70" name="siteName" value="<%=WebUtil.display(_site_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Username</div>
        <input class="field" id="username" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Email</div>
        <input class="requiredField" id="email" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Password</div>
        <input class="requiredField" id="password" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Password Repeat</div>
        <input class="requiredField" id="passwordRepeat" type="text" size="70" name="passwordRepeat" value="<%=WebUtil.display(_password_repeatValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Location</div>
        <input class="field" id="location" type="text" size="70" name="location" value="<%=WebUtil.display(_locationValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Created Site Url</div>
        <input class="field" id="createdSiteUrl" type="text" size="70" name="createdSiteUrl" value="<%=WebUtil.display(_created_site_urlValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerRegisterStartFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerRegisterStart"></span>
<a href="javascript:backToXX('cleanerRegisterStartFormAddDis','resultDisplayCleanerRegisterStart')">show back</a><br>
<hr/>
