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
	if (cancelPage == null) cancelPage = "content_data_home";

    String _content_idValue= WebUtil.display((String)reqParams.get("contentId"));
    String _dataValue= WebUtil.display((String)reqParams.get("data"));
    String _option1Value= WebUtil.display((String)reqParams.get("option1"));
    String _option2Value= WebUtil.display((String)reqParams.get("option2"));
    String _option3Value= WebUtil.display((String)reqParams.get("option3"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_content_data_home.html"> ContentData Home </a>
<%
	
	List list = null;
	list = ContentDataDS.getInstance().getBySiteId(site.getId());

%>

<div id="contentDataList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		ContentData _ContentData = (ContentData) iter.next();	
%>

	<div id="contentDataFrame<%=_ContentData.getId() %>" >

		<div id="contentId" >
			contentId:<%= _ContentData.getContentId() %>
		</div>
		<div id="data" >
			data:<%= _ContentData.getData() %>
		</div>
		<div id="option1" >
			option1:<%= _ContentData.getOption1() %>
		</div>
		<div id="option2" >
			option2:<%= _ContentData.getOption2() %>
		</div>
		<div id="option3" >
			option3:<%= _ContentData.getOption3() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _ContentData.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _ContentData.getTimeUpdated() %>
		</div>
		<div>
		<a id="contentDataDeleteButton" href="javascript:deleteThis('/contentDataAction.html',<%= _ContentData.getId()%>,'contentDataFrame<%=_ContentData.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="contentDataForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentDataFormAdd" method="POST" action="/contentDataAction.html" id="contentDataFormAdd">

	<div id="contentDataForm_contentId_field">
    <div id="contentDataForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentDataForm_contentId_text" class="formFieldText" >       
        <input class="field" id="_ffd_contentId" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="contentDataForm_data_field">
    <div id="contentDataForm_data_label" class="formLabel" >Data </div>
    <div id="contentDataForm_data_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_data" class="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="contentDataForm_option1_field">
    <div id="contentDataForm_option1_label" class="formLabel" >Option1 </div>
    <div id="contentDataForm_option1_text" class="formFieldText" >       
        <input class="field" id="_ffd_option1" type="text" size="70" name="option1" value="<%=WebUtil.display(_option1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="contentDataForm_option2_field">
    <div id="contentDataForm_option2_label" class="formLabel" >Option2 </div>
    <div id="contentDataForm_option2_text" class="formFieldText" >       
        <input class="field" id="_ffd_option2" type="text" size="70" name="option2" value="<%=WebUtil.display(_option2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="contentDataForm_option3_field">
    <div id="contentDataForm_option3_label" class="formLabel" >Option3 </div>
    <div id="contentDataForm_option3_text" class="formFieldText" >       
        <input class="field" id="_ffd_option3" type="text" size="70" name="option3" value="<%=WebUtil.display(_option3Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="contentDataFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/contentDataAction.html', 'contentDataFormAdd', 'formSubmit_ajax', 'contentData');">Submit</a>
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
function responseCallback(data){
	document.getElementById("resultDisplayContentData").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('contentDataFormAddDis','/contentDataAction.html', 'resultDisplayContentData', '${ajax_response_fields}', responseCallback);
}

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
        			updatepageXX(dispElementId, "resultDisplayContentData", self.xmlHttpReq.responseText);
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

function backToXX(eid){
	document.getElementById("resultDisplayContentData").innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

</script>


<form name="contentDataFormAddDis" method="POST" action="/contentDataAction.html" id="contentDataFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Content Id</div>
        <input class="field" id="contentId" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Data</div>
		<TEXTAREA id="data" class="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Option1</div>
        <input class="field" id="option1" type="text" size="70" name="option1" value="<%=WebUtil.display(_option1Value)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Option2</div>
        <input class="field" id="option2" type="text" size="70" name="option2" value="<%=WebUtil.display(_option2Value)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Option3</div>
        <input class="field" id="option3" type="text" size="70" name="option3" value="<%=WebUtil.display(_option3Value)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Created</div>
		<br/>
		 <div class="ajaxFormLabel"> Time Updated</div>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('contentDataFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayContentData"></span>
<a href="javascript:backToXX('contentDataFormAddDis')">show back</a><br>
<hr/>
