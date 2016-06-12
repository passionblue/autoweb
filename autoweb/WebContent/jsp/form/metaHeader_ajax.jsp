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
	if (cancelPage == null) cancelPage = "meta_header_home";

    String _sourceValue= WebUtil.display((String)reqParams.get("source"));
    String _detail_idValue= WebUtil.display((String)reqParams.get("detailId"));
    String _nameValue= WebUtil.display((String)reqParams.get("name"));
    String _valueValue= WebUtil.display((String)reqParams.get("value"));
    String _http_equivValue= WebUtil.display((String)reqParams.get("httpEquiv"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
%> 

<a href="/v_meta_header_home.html"> MetaHeader Home </a>
<%
	
	List list = null;
	list = MetaHeaderDS.getInstance().getBySiteId(site.getId());

%>

<div id="metaHeaderList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		MetaHeader _MetaHeader = (MetaHeader) iter.next();	
%>

	<div id="metaHeaderFrame<%=_MetaHeader.getId() %>" >

		<div id="source" >
			source:<%= _MetaHeader.getSource() %>
		</div>
		<div id="detailId" >
			detailId:<%= _MetaHeader.getDetailId() %>
		</div>
		<div id="name" >
			name:<%= _MetaHeader.getName() %>
		</div>
		<div id="value" >
			value:<%= _MetaHeader.getValue() %>
		</div>
		<div id="httpEquiv" >
			httpEquiv:<%= _MetaHeader.getHttpEquiv() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _MetaHeader.getTimeCreated() %>
		</div>
		<div>
		<a id="metaHeaderDeleteButton" href="javascript:deleteThis('/metaHeaderAction.html',<%= _MetaHeader.getId()%>,'metaHeaderFrame<%=_MetaHeader.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="metaHeaderForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="metaHeaderFormAdd" method="POST" action="/metaHeaderAction.html" id="metaHeaderFormAdd">

	<div id="metaHeaderForm_source_field">
    <div id="metaHeaderForm_source_label" class="formRequiredLabel" >Source* </div>
    <div id="metaHeaderForm_source_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="source" id="_ffd_source">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sourceValue)%>>XX</option-->
        <option value="page" <%=HtmlUtil.getOptionSelect("page", _sourceValue)%>>Page</option>
        <option value="content" <%=HtmlUtil.getOptionSelect("content", _sourceValue)%>>Content</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="metaHeaderForm_detailId_field">
    <div id="metaHeaderForm_detailId_label" class="formLabel" >Detail Id </div>
    <div id="metaHeaderForm_detailId_text" class="formFieldText" >       
        <input class="field" id="_ffd_detailId" type="text" size="70" name="detailId" value="<%=WebUtil.display(_detail_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="metaHeaderForm_name_field">
    <div id="metaHeaderForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="metaHeaderForm_name_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_name" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="metaHeaderForm_value_field">
    <div id="metaHeaderForm_value_label" class="formLabel" >Value </div>
    <div id="metaHeaderForm_value_text" class="formFieldText" >       
        <input class="field" id="_ffd_value" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="metaHeaderForm_httpEquiv_field">
    <div id="metaHeaderForm_httpEquiv_label" class="formLabel" >Http Equiv </div>
    <div id="metaHeaderForm_httpEquiv_dropdown" class="formFieldDropDown" >       
        <select name="httpEquiv" id="_ffd_httpEquiv">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _http_equivValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _http_equivValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>

        <div id="metaHeaderFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/metaHeaderAction.html', 'metaHeaderFormAdd', 'formSubmit_ajax', 'metaHeader');">Submit</a>
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
	document.getElementById("resultDisplayMetaHeader").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('metaHeaderFormAddDis','/metaHeaderAction.html', 'resultDisplayMetaHeader', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="metaHeaderFormAddDis" method="POST" action="/metaHeaderAction.html" id="metaHeaderFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Source</div>
        <select class="requiredField" name="source" id="source">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sourceValue)%>>XX</option-->
        <option value="page" <%=HtmlUtil.getOptionSelect("page", _sourceValue)%>>Page</option>
        <option value="content" <%=HtmlUtil.getOptionSelect("content", _sourceValue)%>>Content</option>
        </select> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Detail Id</div>
        <input class="field" id="detailId" type="text" size="70" name="detailId" value="<%=WebUtil.display(_detail_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Name</div>
        <input class="requiredField" id="name" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Value</div>
        <input class="field" id="value" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Http Equiv</div>
        <select name="httpEquiv" id="httpEquiv">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _http_equivValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _http_equivValue)%>>Yes</option>
        </select><span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('metaHeaderFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayMetaHeader"></span>
<a href="javascript:backToXX('metaHeaderFormAddDis','resultDisplayMetaHeader')">show back</a><br>
<hr/>
