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
	if (cancelPage == null) cancelPage = "dev_table_home";

    String _dev_note_idValue= WebUtil.display((String)reqParams.get("devNoteId"));
    String _titleValue= WebUtil.display((String)reqParams.get("title"));
    String _subjectValue= WebUtil.display((String)reqParams.get("subject"));
    String _dataValue= WebUtil.display((String)reqParams.get("data"));
    String _typeValue= WebUtil.display((String)reqParams.get("type"));
    String _disableValue= WebUtil.display((String)reqParams.get("disable"));
    String _radio_valueValue= WebUtil.display((String)reqParams.get("radioValue"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_dev_table_home.html"> DevTable Home </a>
<%
	
	List list = null;
	list = DevTableDS.getInstance().getBySiteId(site.getId());

%>

<div id="devTableList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		DevTable _DevTable = (DevTable) iter.next();	
%>

	<div id="devTableFrame<%=_DevTable.getId() %>" >

		<div id="devNoteId" >
			devNoteId:<%= _DevTable.getDevNoteId() %>
		</div>
		<div id="title" >
			title:<%= _DevTable.getTitle() %>
		</div>
		<div id="subject" >
			subject:<%= _DevTable.getSubject() %>
		</div>
		<div id="data" >
			data:<%= _DevTable.getData() %>
		</div>
		<div id="type" >
			type:<%= _DevTable.getType() %>
		</div>
		<div id="disable" >
			disable:<%= _DevTable.getDisable() %>
		</div>
		<div id="radioValue" >
			radioValue:<%= _DevTable.getRadioValue() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _DevTable.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _DevTable.getTimeUpdated() %>
		</div>
		<div>
		<a id="devTableDeleteButton" href="javascript:deleteThis('/devTableAction.html',<%= _DevTable.getId()%>,'devTableFrame<%=_DevTable.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="devTableForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="devTableFormAdd" method="POST" action="/devTableAction.html" id="devTableFormAdd">

	<div id="devTableForm_devNoteId_field" class="formFieldFrame">
    <div id="devTableForm_devNoteId_label" class="formLabel" >Dev Note Id </div>
    <div id="devTableForm_devNoteId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="devNoteId" id="_ffd_devNoteId">
        <option value="" >- Please Select -</option>
<%
	List _listDevNote_devNoteId = DevNoteDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listDevNote_devNoteId.iterator(); iter.hasNext();){
		DevNote _obj = (DevNote) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_dev_note_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getNote() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="devTableForm_title_field">
    <div id="devTableForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="devTableForm_title_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="devTableForm_subject_field">
    <div id="devTableForm_subject_label" class="formLabel" >Subject </div>
    <div id="devTableForm_subject_text" class="formFieldText" >       
        <input class="field" id="_ffd_subject" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="devTableForm_data_field">
    <div id="devTableForm_data_label" class="formLabel" >Data </div>
    <div id="devTableForm_data_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_data" class="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="devTableForm_type_field">
    <div id="devTableForm_type_label" class="formRequiredLabel" >Type* </div>
    <div id="devTableForm_type_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="type" id="_ffd_type">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _typeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _typeValue)%>>Newyork</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _typeValue)%>>Ca</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _typeValue)%>>seela</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _typeValue)%>>Ocean</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _typeValue)%>>5</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="devTableForm_disable_field">
    <div id="devTableForm_disable_label" class="formLabel" >Disable </div>
    <div id="devTableForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable" id="_ffd_disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="devTableForm_radioValue_field">
    <div id="devTableForm_radioValue_label" class="formLabel" >Radio Value </div>
    <div id="devTableForm_radioValue_text" class="formFieldText" >       
        <input class="field" id="_ffd_radioValue" type="text" size="70" name="radioValue" value="<%=WebUtil.display(_radio_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="devTableFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/devTableAction.html', 'devTableFormAdd', 'formSubmit_ajax', 'devTable');">Submit</a>
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
	document.getElementById("resultDisplayDevTable").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('devTableFormAddDis','/devTableAction.html', 'resultDisplayDevTable', 'title,subject,data', responseCallback);
}
</script>



<form name="devTableFormAddDis" method="POST" action="/devTableAction.html" id="devTableFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Dev Note Id</div>
        <select class="field" name="devNoteId" id="devNoteId">
        <option value="" >- Please Select -</option>
<%
	_listDevNote_devNoteId = DevNoteDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listDevNote_devNoteId.iterator(); iter.hasNext();){
		DevNote _obj = (DevNote) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_dev_note_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getNote() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Title</div>
        <input class="requiredField" id="title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Subject</div>
        <input class="field" id="subject" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Data</div>
		<TEXTAREA id="data" class="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Type</div>
        <select class="requiredField" name="type" id="type">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _typeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _typeValue)%>>Newyork</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _typeValue)%>>Ca</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _typeValue)%>>seela</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _typeValue)%>>Ocean</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _typeValue)%>>5</option>
        </select> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disable</div>
        <select name="disable" id="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Radio Value</div>
        <input class="field" id="radioValue" type="text" size="70" name="radioValue" value="<%=WebUtil.display(_radio_valueValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('devTableFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayDevTable"></span>
<a href="javascript:backToXX('devTableFormAddDis','resultDisplayDevTable')">show back</a><br>
<hr/>
