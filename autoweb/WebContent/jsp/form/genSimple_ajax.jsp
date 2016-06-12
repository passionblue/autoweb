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
	if (cancelPage == null) cancelPage = "gen_simple_home";

    String _dataValue= WebUtil.display((String)reqParams.get("data"));
    String _activeValue= WebUtil.display((String)reqParams.get("active"));
    String _ext_stringValue= WebUtil.display((String)reqParams.get("extString"));
    String _ext_intValue= WebUtil.display((String)reqParams.get("extInt"));
%> 

<a href="/v_gen_simple_home.html"> GenSimple Home </a>
<%
	
	List list = null;
	list = GenSimpleDS.getInstance().getBySiteId(site.getId());

%>

<div id="genSimpleList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		GenSimpleDataHolder _GenSimple = (GenSimpleDataHolder) iter.next();	
%>

	<div id="genSimpleFrame<%=_GenSimple.getId() %>" >

		<div id="data" >
			data:<%= _GenSimple.getData() %>
		</div>
		<div id="active" >
			active:<%= _GenSimple.getActive() %>
		</div>
		<div id="extString" >
			extString:<%= _GenSimple.getExtString() %>
		</div>
		<div id="extInt" >
			extInt:<%= _GenSimple.getExtInt() %>
		</div>
		<div>
		<a id="genSimpleDeleteButton" href="javascript:deleteThis('/genSimpleAction.html',<%= _GenSimple.getId()%>,'genSimpleFrame<%=_GenSimple.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="genSimpleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genSimpleFormAdd" method="POST" action="/genSimpleAction.html" id="genSimpleFormAdd">

	<div id="genSimpleForm_data_field">
    <div id="genSimpleForm_data_label" class="formLabel" >Data </div>
    <div id="genSimpleForm_data_text" class="formFieldText" >       
        <input class="field" id="_ffd_data" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genSimpleForm_active_field">
    <div id="genSimpleForm_active_label" class="formLabel" >Active </div>
    <div id="genSimpleForm_active_text" class="formFieldText" >       
        <input class="field" id="_ffd_active" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genSimpleForm_extString_field">
    <div id="genSimpleForm_extString_label" class="formLabel" >Ext String </div>
    <div id="genSimpleForm_extString_text" class="formFieldText" >       
        <input class="field" id="_ffd_extString" type="text" size="70" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genSimpleForm_extInt_field">
    <div id="genSimpleForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genSimpleForm_extInt_text" class="formFieldText" >       
        <input class="field" id="_ffd_extInt" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="genSimpleFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/genSimpleAction.html', 'genSimpleFormAdd', 'formSubmit_ajax', 'genSimple');">Submit</a>
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
	document.getElementById("resultDisplayGenSimple").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('genSimpleFormAddDis','/genSimpleAction.html', 'resultDisplayGenSimple', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="genSimpleFormAddDis" method="POST" action="/genSimpleAction.html" id="genSimpleFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Data</div>
        <input class="field" id="data" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Active</div>
        <input class="field" id="active" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ext String</div>
        <input class="field" id="extString" type="text" size="70" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ext Int</div>
        <input class="field" id="extInt" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('genSimpleFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayGenSimple"></span>
<a href="javascript:backToXX('genSimpleFormAddDis','resultDisplayGenSimple')">show back</a><br>
<hr/>
