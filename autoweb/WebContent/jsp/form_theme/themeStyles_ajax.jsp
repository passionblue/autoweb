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
	if (cancelPage == null) cancelPage = "theme_styles_home";

    String _body_widthValue= WebUtil.display((String)reqParams.get("bodyWidth"));
    String _body_alignValue= WebUtil.display((String)reqParams.get("bodyAlign"));
    String _body_backgroundValue= WebUtil.display((String)reqParams.get("bodyBackground"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_theme_styles_home.html"> ThemeStyles Home </a>
<%
	
	List list = null;
	list = ThemeStylesDS.getInstance().getBySiteId(site.getId());

%>

<div id="themeStylesList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		ThemeStyles _ThemeStyles = (ThemeStyles) iter.next();	
%>

	<div id="themeStylesFrame<%=_ThemeStyles.getId() %>" >

		<div id="bodyWidth" >
			bodyWidth:<%= _ThemeStyles.getBodyWidth() %>
		</div>
		<div id="bodyAlign" >
			bodyAlign:<%= _ThemeStyles.getBodyAlign() %>
		</div>
		<div id="bodyBackground" >
			bodyBackground:<%= _ThemeStyles.getBodyBackground() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _ThemeStyles.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _ThemeStyles.getTimeUpdated() %>
		</div>
		<div>
		<a id="themeStylesDeleteButton" href="javascript:deleteThis('/themeStylesAction.html',<%= _ThemeStyles.getId()%>,'themeStylesFrame<%=_ThemeStyles.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="themeStylesForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="themeStylesFormAdd" method="POST" action="/themeStylesAction.html" id="themeStylesFormAdd">

	<div id="themeStylesForm_bodyWidth_field">
    <div id="themeStylesForm_bodyWidth_label" class="formLabel" >Body Width </div>
    <div id="themeStylesForm_bodyWidth_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyWidth" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeStylesForm_bodyAlign_field" class="formFieldFrame">
    <div id="themeStylesForm_bodyAlign_label" class="formLabel" >Body Align </div>
    <div id="themeStylesForm_bodyAlign_dropdown" class="formFieldDropDown" >       
        <select class="field" name="bodyAlign" id="_ffd_bodyAlign">
        <option value="" >- Please Select -</option>
<%
	List dropMenusBodyAlign = DropMenuUtil.getDropMenus("ThemeStylesBodyAlignOption");
	for(Iterator iterItems = dropMenusBodyAlign.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _body_alignValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeStylesForm_bodyBackground_field">
    <div id="themeStylesForm_bodyBackground_label" class="formLabel" >Body Background </div>
    <div id="themeStylesForm_bodyBackground_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyBackground" type="text" size="70" name="bodyBackground" value="<%=WebUtil.display(_body_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeStylesForm_timeCreated_field">
    <div id="themeStylesForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="themeStylesForm_timeCreated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeCreated" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeStylesForm_timeUpdated_field">
    <div id="themeStylesForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="themeStylesForm_timeUpdated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="themeStylesFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/themeStylesAction.html', 'themeStylesFormAdd', 'formSubmit_ajax', 'themeStyles');">Submit</a>
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
	document.getElementById("resultDisplayThemeStyles").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('themeStylesFormAddDis','/themeStylesAction.html', 'resultDisplayThemeStyles', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="themeStylesFormAddDis" method="POST" action="/themeStylesAction.html" id="themeStylesFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Body Width</div>
        <input class="field" id="bodyWidth" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Align</div>
        <select class="field" name="bodyAlign" id="bodyAlign">
        <option value="" >- Please Select -</option>
<%
	dropMenusBodyAlign = DropMenuUtil.getDropMenus("BodyAlignment");
	for(Iterator iterItems = dropMenusBodyAlign.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _body_alignValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Background</div>
        <input class="field" id="bodyBackground" type="text" size="70" name="bodyBackground" value="<%=WebUtil.display(_body_backgroundValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Created</div>
        <input class="field" id="timeCreated" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Updated</div>
        <input class="field" id="timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('themeStylesFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayThemeStyles"></span>
<a href="javascript:backToXX('themeStylesFormAddDis','resultDisplayThemeStyles')">show back</a><br>
<hr/>
