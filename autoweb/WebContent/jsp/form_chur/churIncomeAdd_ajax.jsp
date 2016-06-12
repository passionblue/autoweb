<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_add_home";

    String _chur_member_idValue= WebUtil.display((String)reqParams.get("churMemberId"));
    String _titheValue= WebUtil.display((String)reqParams.get("tithe"));
    String _weeklyValue= WebUtil.display((String)reqParams.get("weekly"));
    String _thanksValue= WebUtil.display((String)reqParams.get("thanks"));
    String _missionValue= WebUtil.display((String)reqParams.get("mission"));
    String _constructionValue= WebUtil.display((String)reqParams.get("construction"));
    String _otherValue= WebUtil.display((String)reqParams.get("other"));
    String _weekValue= WebUtil.display((String)reqParams.get("week"));
    String _yearValue= WebUtil.display((String)reqParams.get("year"));
%> 

<a href="/v_chur_income_add_home.html"> ChurIncomeAdd Home </a>
<%
	
	List list = null;
	list = ChurIncomeAddDS.getInstance().getBySiteId(site.getId());

%>

<div id="churIncomeAddList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		ChurIncomeAddDataHolder _ChurIncomeAdd = (ChurIncomeAddDataHolder) iter.next();	
%>

	<div id="churIncomeAddFrame<%=_ChurIncomeAdd.getId() %>" >

		<div id="churMemberId" >
			churMemberId:<%= _ChurIncomeAdd.getChurMemberId() %>
		</div>
		<div id="tithe" >
			tithe:<%= _ChurIncomeAdd.getTithe() %>
		</div>
		<div id="weekly" >
			weekly:<%= _ChurIncomeAdd.getWeekly() %>
		</div>
		<div id="thanks" >
			thanks:<%= _ChurIncomeAdd.getThanks() %>
		</div>
		<div id="mission" >
			mission:<%= _ChurIncomeAdd.getMission() %>
		</div>
		<div id="construction" >
			construction:<%= _ChurIncomeAdd.getConstruction() %>
		</div>
		<div id="other" >
			other:<%= _ChurIncomeAdd.getOther() %>
		</div>
		<div id="week" >
			week:<%= _ChurIncomeAdd.getWeek() %>
		</div>
		<div id="year" >
			year:<%= _ChurIncomeAdd.getYear() %>
		</div>
		<div>
		<a id="churIncomeAddDeleteButton" href="javascript:deleteThis('/churIncomeAddAction.html',<%= _ChurIncomeAdd.getId()%>,'churIncomeAddFrame<%=_ChurIncomeAdd.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="churIncomeAddForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeAddFormAdd" method="POST" action="/churIncomeAddAction.html" id="churIncomeAddFormAdd">

	<div id="churIncomeAddForm_churMemberId_field" class="formFieldFrame">
    <div id="churIncomeAddForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeAddForm_churMemberId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="churMemberId" id="_ffd_churMemberId">
        <option value="" >- Please Select -</option>
<%
	List _listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_chur_member_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_tithe_field">
    <div id="churIncomeAddForm_tithe_label" class="formLabel" >Tithe </div>
    <div id="churIncomeAddForm_tithe_text" class="formFieldText" >       
        <input class="field" id="_ffd_tithe" type="text" size="70" name="tithe" value="<%=WebUtil.display(_titheValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_weekly_field">
    <div id="churIncomeAddForm_weekly_label" class="formLabel" >Weekly </div>
    <div id="churIncomeAddForm_weekly_text" class="formFieldText" >       
        <input class="field" id="_ffd_weekly" type="text" size="70" name="weekly" value="<%=WebUtil.display(_weeklyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_thanks_field">
    <div id="churIncomeAddForm_thanks_label" class="formLabel" >Thanks </div>
    <div id="churIncomeAddForm_thanks_text" class="formFieldText" >       
        <input class="field" id="_ffd_thanks" type="text" size="70" name="thanks" value="<%=WebUtil.display(_thanksValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_mission_field">
    <div id="churIncomeAddForm_mission_label" class="formLabel" >Mission </div>
    <div id="churIncomeAddForm_mission_text" class="formFieldText" >       
        <input class="field" id="_ffd_mission" type="text" size="70" name="mission" value="<%=WebUtil.display(_missionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_construction_field">
    <div id="churIncomeAddForm_construction_label" class="formLabel" >Construction </div>
    <div id="churIncomeAddForm_construction_text" class="formFieldText" >       
        <input class="field" id="_ffd_construction" type="text" size="70" name="construction" value="<%=WebUtil.display(_constructionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_other_field">
    <div id="churIncomeAddForm_other_label" class="formLabel" >Other </div>
    <div id="churIncomeAddForm_other_text" class="formFieldText" >       
        <input class="field" id="_ffd_other" type="text" size="70" name="other" value="<%=WebUtil.display(_otherValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_week_field">
    <div id="churIncomeAddForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeAddForm_week_text" class="formFieldText" >       
        <input class="field" id="_ffd_week" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeAddForm_year_field">
    <div id="churIncomeAddForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeAddForm_year_text" class="formFieldText" >       
        <input class="field" id="_ffd_year" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeAddFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/churIncomeAddAction.html', 'churIncomeAddFormAdd', 'formSubmit_ajax', 'churIncomeAdd');">Submit</a>
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
	document.getElementById("resultDisplayChurIncomeAdd").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('churIncomeAddFormAddDis','/churIncomeAddAction.html', 'resultDisplayChurIncomeAdd', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="churIncomeAddFormAddDis" method="POST" action="/churIncomeAddAction.html" id="churIncomeAddFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Chur Member Id</div>
        <select class="field" name="churMemberId" id="churMemberId">
        <option value="" >- Please Select -</option>
<%
	_listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_chur_member_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Tithe</div>
        <input class="field" id="tithe" type="text" size="70" name="tithe" value="<%=WebUtil.display(_titheValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Weekly</div>
        <input class="field" id="weekly" type="text" size="70" name="weekly" value="<%=WebUtil.display(_weeklyValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Thanks</div>
        <input class="field" id="thanks" type="text" size="70" name="thanks" value="<%=WebUtil.display(_thanksValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Mission</div>
        <input class="field" id="mission" type="text" size="70" name="mission" value="<%=WebUtil.display(_missionValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Construction</div>
        <input class="field" id="construction" type="text" size="70" name="construction" value="<%=WebUtil.display(_constructionValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Other</div>
        <input class="field" id="other" type="text" size="70" name="other" value="<%=WebUtil.display(_otherValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Week</div>
        <input class="field" id="week" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Year</div>
        <input class="field" id="year" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('churIncomeAddFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayChurIncomeAdd"></span>
<a href="javascript:backToXX('churIncomeAddFormAddDis','resultDisplayChurIncomeAdd')">show back</a><br>
<hr/>
