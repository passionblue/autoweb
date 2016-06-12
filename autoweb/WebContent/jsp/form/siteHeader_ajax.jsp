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
	if (cancelPage == null) cancelPage = "site_header_home";

    String _as_isValue= WebUtil.display((String)reqParams.get("asIs"));
    String _include_typeValue= WebUtil.display((String)reqParams.get("includeType"));
    String _include_textValue= WebUtil.display((String)reqParams.get("includeText"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_site_header_home.html"> SiteHeader Home </a>
<%
	
	List list = null;
	list = SiteHeaderDS.getInstance().getBySiteId(site.getId());

%>

<div id="siteHeaderList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		SiteHeader _SiteHeader = (SiteHeader) iter.next();	
%>

	<div id="siteHeaderFrame<%=_SiteHeader.getId() %>" >

		<div id="asIs" >
			asIs:<%= _SiteHeader.getAsIs() %>
		</div>
		<div id="includeType" >
			includeType:<%= _SiteHeader.getIncludeType() %>
		</div>
		<div id="includeText" >
			includeText:<%= _SiteHeader.getIncludeText() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _SiteHeader.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _SiteHeader.getTimeUpdated() %>
		</div>
		<div>
		<a id="siteHeaderDeleteButton" href="javascript:deleteThis('/siteHeaderAction.html',<%= _SiteHeader.getId()%>,'siteHeaderFrame<%=_SiteHeader.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="siteHeaderForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteHeaderFormAdd" method="POST" action="/siteHeaderAction.html" id="siteHeaderFormAdd">

	<div id="siteHeaderForm_asIs_field">
    <div id="siteHeaderForm_asIs_label" class="formLabel" >As Is </div>
    <div id="siteHeaderForm_asIs_dropdown" class="formFieldDropDown" >       
        <select name="asIs" id="_ffd_asIs">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _as_isValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _as_isValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="siteHeaderForm_includeType_field">
    <div id="siteHeaderForm_includeType_label" class="formLabel" >Include Type </div>
    <div id="siteHeaderForm_includeType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="includeType" id="_ffd_includeType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _include_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _include_typeValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _include_typeValue)%>>ScriptLink</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _include_typeValue)%>>ScriptText</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _include_typeValue)%>>StyleLink</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _include_typeValue)%>>StyleText</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="siteHeaderForm_includeText_field">
    <div id="siteHeaderForm_includeText_label" class="formLabel" >Include Text </div>
    <div id="siteHeaderForm_includeText_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_includeText" class="field" NAME="includeText" COLS="50" ROWS="8" ><%=WebUtil.display(_include_textValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>

        <div id="siteHeaderFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/siteHeaderAction.html', 'siteHeaderFormAdd', 'formSubmit_ajax', 'siteHeader');">Submit</a>
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
	document.getElementById("resultDisplaySiteHeader").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('siteHeaderFormAddDis','/siteHeaderAction.html', 'resultDisplaySiteHeader', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="siteHeaderFormAddDis" method="POST" action="/siteHeaderAction.html" id="siteHeaderFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> As Is</div>
        <select name="asIs" id="asIs">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _as_isValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _as_isValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Include Type</div>
        <select class="field" name="includeType" id="includeType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _include_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _include_typeValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _include_typeValue)%>>ScriptLink</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _include_typeValue)%>>ScriptText</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _include_typeValue)%>>StyleLink</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _include_typeValue)%>>StyleText</option>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Include Text</div>
		<TEXTAREA id="includeText" class="field" NAME="includeText" COLS="50" ROWS="8" ><%=WebUtil.display(_include_textValue)%></TEXTAREA><span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('siteHeaderFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplaySiteHeader"></span>
<a href="javascript:backToXX('siteHeaderFormAddDis','resultDisplaySiteHeader')">show back</a><br>
<hr/>
