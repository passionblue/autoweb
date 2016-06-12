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
	if (cancelPage == null) cancelPage = "theme_aggregator_home";

    String _theme_nameValue= WebUtil.display((String)reqParams.get("themeName"));
    String _layout_pageValue= WebUtil.display((String)reqParams.get("layoutPage"));
    String _css_indexValue= WebUtil.display((String)reqParams.get("cssIndex"));
    String _theme_style_idValue= WebUtil.display((String)reqParams.get("themeStyleId"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_theme_aggregator_home.html"> ThemeAggregator Home </a>
<%
	
	List list = null;
	list = ThemeAggregatorDS.getInstance().getBySiteId(site.getId());

%>

<div id="themeAggregatorList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		ThemeAggregator _ThemeAggregator = (ThemeAggregator) iter.next();	
%>

	<div id="themeAggregatorFrame<%=_ThemeAggregator.getId() %>" >

		<div id="themeName" >
			themeName:<%= _ThemeAggregator.getThemeName() %>
		</div>
		<div id="layoutPage" >
			layoutPage:<%= _ThemeAggregator.getLayoutPage() %>
		</div>
		<div id="cssIndex" >
			cssIndex:<%= _ThemeAggregator.getCssIndex() %>
		</div>
		<div id="themeStyleId" >
			themeStyleId:<%= _ThemeAggregator.getThemeStyleId() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _ThemeAggregator.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _ThemeAggregator.getTimeUpdated() %>
		</div>
		<div>
		<a id="themeAggregatorDeleteButton" href="javascript:deleteThis('/themeAggregatorAction.html',<%= _ThemeAggregator.getId()%>,'themeAggregatorFrame<%=_ThemeAggregator.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="themeAggregatorForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="themeAggregatorFormAdd" method="POST" action="/themeAggregatorAction.html" id="themeAggregatorFormAdd">

	<div id="themeAggregatorForm_themeName_field">
    <div id="themeAggregatorForm_themeName_label" class="formLabel" >Theme Name </div>
    <div id="themeAggregatorForm_themeName_text" class="formFieldText" >       
        <input class="field" id="_ffd_themeName" type="text" size="70" name="themeName" value="<%=WebUtil.display(_theme_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeAggregatorForm_layoutPage_field">
    <div id="themeAggregatorForm_layoutPage_label" class="formLabel" >Layout Page </div>
    <div id="themeAggregatorForm_layoutPage_text" class="formFieldText" >       
        <input class="field" id="_ffd_layoutPage" type="text" size="70" name="layoutPage" value="<%=WebUtil.display(_layout_pageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeAggregatorForm_cssIndex_field">
    <div id="themeAggregatorForm_cssIndex_label" class="formLabel" >Css Index </div>
    <div id="themeAggregatorForm_cssIndex_text" class="formFieldText" >       
        <input class="field" id="_ffd_cssIndex" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeAggregatorForm_themeStyleId_field" class="formFieldFrame">
    <div id="themeAggregatorForm_themeStyleId_label" class="formLabel" >Theme Style Id </div>
    <div id="themeAggregatorForm_themeStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="themeStyleId" id="_ffd_themeStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listThemeStyles_themeStyleId = ThemeStylesDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listThemeStyles_themeStyleId.iterator(); iter.hasNext();){
		ThemeStyles _obj = (ThemeStyles) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_theme_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getId() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeAggregatorForm_timeCreated_field">
    <div id="themeAggregatorForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="themeAggregatorForm_timeCreated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeCreated" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="themeAggregatorForm_timeUpdated_field">
    <div id="themeAggregatorForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="themeAggregatorForm_timeUpdated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="themeAggregatorFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/themeAggregatorAction.html', 'themeAggregatorFormAdd', 'formSubmit_ajax', 'themeAggregator');">Submit</a>
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
	document.getElementById("resultDisplayThemeAggregator").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('themeAggregatorFormAddDis','/themeAggregatorAction.html', 'resultDisplayThemeAggregator', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="themeAggregatorFormAddDis" method="POST" action="/themeAggregatorAction.html" id="themeAggregatorFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Theme Name</div>
        <input class="field" id="themeName" type="text" size="70" name="themeName" value="<%=WebUtil.display(_theme_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Layout Page</div>
        <input class="field" id="layoutPage" type="text" size="70" name="layoutPage" value="<%=WebUtil.display(_layout_pageValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Css Index</div>
        <input class="field" id="cssIndex" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Theme Style Id</div>
        <select class="field" name="themeStyleId" id="themeStyleId">
        <option value="" >- Please Select -</option>
<%
	_listThemeStyles_themeStyleId = ThemeStylesDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listThemeStyles_themeStyleId.iterator(); iter.hasNext();){
		ThemeStyles _obj = (ThemeStyles) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_theme_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getId() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Created</div>
        <input class="field" id="timeCreated" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Updated</div>
        <input class="field" id="timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('themeAggregatorFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayThemeAggregator"></span>
<a href="javascript:backToXX('themeAggregatorFormAddDis','resultDisplayThemeAggregator')">show back</a><br>
<hr/>
