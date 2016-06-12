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
	if (cancelPage == null) cancelPage = "chur_member_home";

    String _full_nameValue= WebUtil.display((String)reqParams.get("fullName"));
    String _first_nameValue= WebUtil.display((String)reqParams.get("firstName"));
    String _last_nameValue= WebUtil.display((String)reqParams.get("lastName"));
    String _titleValue= WebUtil.display((String)reqParams.get("title"));
    String _other_nameValue= WebUtil.display((String)reqParams.get("otherName"));
    String _householdValue= WebUtil.display((String)reqParams.get("household"));
    String _household_idValue= WebUtil.display((String)reqParams.get("householdId"));
    String _is_groupValue= WebUtil.display((String)reqParams.get("isGroup"));
    String _is_guestValue= WebUtil.display((String)reqParams.get("isGuest"));
    String _is_speakerValue= WebUtil.display((String)reqParams.get("isSpeaker"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _list_indexValue= WebUtil.display((String)reqParams.get("listIndex"));
%> 

<a href="/v_chur_member_home.html"> ChurMember Home </a>
<%
	
	List list = null;
	list = ChurMemberDS.getInstance().getBySiteId(site.getId());

%>

<div id="churMemberList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		ChurMember _ChurMember = (ChurMember) iter.next();	
%>

	<div id="churMemberFrame<%=_ChurMember.getId() %>" >

		<div id="fullName" >
			fullName:<%= _ChurMember.getFullName() %>
		</div>
		<div id="firstName" >
			firstName:<%= _ChurMember.getFirstName() %>
		</div>
		<div id="lastName" >
			lastName:<%= _ChurMember.getLastName() %>
		</div>
		<div id="title" >
			title:<%= _ChurMember.getTitle() %>
		</div>
		<div id="otherName" >
			otherName:<%= _ChurMember.getOtherName() %>
		</div>
		<div id="household" >
			household:<%= _ChurMember.getHousehold() %>
		</div>
		<div id="householdId" >
			householdId:<%= _ChurMember.getHouseholdId() %>
		</div>
		<div id="isGroup" >
			isGroup:<%= _ChurMember.getIsGroup() %>
		</div>
		<div id="isGuest" >
			isGuest:<%= _ChurMember.getIsGuest() %>
		</div>
		<div id="isSpeaker" >
			isSpeaker:<%= _ChurMember.getIsSpeaker() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _ChurMember.getTimeCreated() %>
		</div>
		<div id="listIndex" >
			listIndex:<%= _ChurMember.getListIndex() %>
		</div>
		<div>
		<a id="churMemberDeleteButton" href="javascript:deleteThis('/churMemberAction.html',<%= _ChurMember.getId()%>,'churMemberFrame<%=_ChurMember.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="churMemberForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churMemberFormAdd" method="POST" action="/churMemberAction.html" id="churMemberFormAdd">

	<div id="churMemberForm_fullName_field">
    <div id="churMemberForm_fullName_label" class="formLabel" >Full Name </div>
    <div id="churMemberForm_fullName_text" class="formFieldText" >       
        <input class="field" id="_ffd_fullName" type="text" size="70" name="fullName" value="<%=WebUtil.display(_full_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_firstName_field">
    <div id="churMemberForm_firstName_label" class="formLabel" >First Name </div>
    <div id="churMemberForm_firstName_text" class="formFieldText" >       
        <input class="field" id="_ffd_firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_lastName_field">
    <div id="churMemberForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="churMemberForm_lastName_text" class="formFieldText" >       
        <input class="field" id="_ffd_lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_title_field">
    <div id="churMemberForm_title_label" class="formLabel" >Title </div>
    <div id="churMemberForm_title_text" class="formFieldText" >       
        <input class="field" id="_ffd_title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_otherName_field">
    <div id="churMemberForm_otherName_label" class="formLabel" >Other Name </div>
    <div id="churMemberForm_otherName_text" class="formFieldText" >       
        <input class="field" id="_ffd_otherName" type="text" size="70" name="otherName" value="<%=WebUtil.display(_other_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_household_field">
    <div id="churMemberForm_household_label" class="formLabel" >Household </div>
    <div id="churMemberForm_household_dropdown" class="formFieldDropDown" >       
        <select name="household" id="_ffd_household">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _householdValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _householdValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
    <INPUT TYPE="HIDDEN" id="_ffd_householdId" NAME="householdId" value="<%=WebUtil.display(_household_idValue)%>" />

	<div id="churMemberForm_isGroup_field">
    <div id="churMemberForm_isGroup_label" class="formLabel" >Is Group </div>
    <div id="churMemberForm_isGroup_dropdown" class="formFieldDropDown" >       
        <select name="isGroup" id="_ffd_isGroup">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_groupValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_groupValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_isGuest_field">
    <div id="churMemberForm_isGuest_label" class="formLabel" >Is Guest </div>
    <div id="churMemberForm_isGuest_dropdown" class="formFieldDropDown" >       
        <select name="isGuest" id="_ffd_isGuest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_guestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_guestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_isSpeaker_field">
    <div id="churMemberForm_isSpeaker_label" class="formLabel" >Is Speaker </div>
    <div id="churMemberForm_isSpeaker_dropdown" class="formFieldDropDown" >       
        <select name="isSpeaker" id="_ffd_isSpeaker">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_speakerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_speakerValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="churMemberForm_listIndex_field">
    <div id="churMemberForm_listIndex_label" class="formLabel" >List Index </div>
    <div id="churMemberForm_listIndex_text" class="formFieldText" >       
        <input class="field" id="_ffd_listIndex" type="text" size="70" name="listIndex" value="<%=WebUtil.display(_list_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churMemberFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/churMemberAction.html', 'churMemberFormAdd', 'formSubmit_ajax', 'churMember');">Submit</a>
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
	document.getElementById("resultDisplayChurMember").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('churMemberFormAddDis','/churMemberAction.html', 'resultDisplayChurMember', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="churMemberFormAddDis" method="POST" action="/churMemberAction.html" id="churMemberFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Full Name</div>
        <input class="field" id="fullName" type="text" size="70" name="fullName" value="<%=WebUtil.display(_full_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> First Name</div>
        <input class="field" id="firstName" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Last Name</div>
        <input class="field" id="lastName" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Title</div>
        <input class="field" id="title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Other Name</div>
        <input class="field" id="otherName" type="text" size="70" name="otherName" value="<%=WebUtil.display(_other_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Household</div>
        <select name="household" id="household">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _householdValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _householdValue)%>>Yes</option>
        </select><span></span>
		<br/>
    	<INPUT TYPE="HIDDEN" id="householdId" NAME="householdId" value="<%=WebUtil.display(_household_idValue)%>" />
		<br/>
		 <div class="ajaxFormLabel"> Is Group</div>
        <select name="isGroup" id="isGroup">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_groupValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_groupValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Guest</div>
        <select name="isGuest" id="isGuest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_guestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_guestValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Speaker</div>
        <select name="isSpeaker" id="isSpeaker">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_speakerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_speakerValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Index</div>
        <input class="field" id="listIndex" type="text" size="70" name="listIndex" value="<%=WebUtil.display(_list_indexValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('churMemberFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayChurMember"></span>
<a href="javascript:backToXX('churMemberFormAddDis','resultDisplayChurMember')">show back</a><br>
<hr/>
