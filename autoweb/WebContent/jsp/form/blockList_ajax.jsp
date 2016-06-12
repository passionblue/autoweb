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
	if (cancelPage == null) cancelPage = "block_list_home";

    String _ip_dataValue= WebUtil.display((String)reqParams.get("ipData"));
    String _range_checkValue= WebUtil.display((String)reqParams.get("rangeCheck"));
    String _reason_codeValue= WebUtil.display((String)reqParams.get("reasonCode"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
%> 

<a href="/v_block_list_home.html"> BlockList Home </a>
<%
	
	List list = null;
	list = BlockListDS.getInstance().getBySiteId(site.getId());

%>

<div id="blockListList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		BlockList _BlockList = (BlockList) iter.next();	
%>

	<div id="blockListFrame<%=_BlockList.getId() %>" >

		<div id="ipData" >
			ipData:<%= _BlockList.getIpData() %>
		</div>
		<div id="rangeCheck" >
			rangeCheck:<%= _BlockList.getRangeCheck() %>
		</div>
		<div id="reasonCode" >
			reasonCode:<%= _BlockList.getReasonCode() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _BlockList.getTimeCreated() %>
		</div>
		<div>
		<a id="blockListDeleteButton" href="javascript:deleteThis('/blockListAction.html',<%= _BlockList.getId()%>,'blockListFrame<%=_BlockList.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="blockListForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blockListFormAdd" method="POST" action="/blockListAction.html" id="blockListFormAdd">

	<div id="blockListForm_ipData_field">
    <div id="blockListForm_ipData_label" class="formLabel" >Ip Data </div>
    <div id="blockListForm_ipData_text" class="formFieldText" >       
        <input class="field" id="_ffd_ipData" type="text" size="70" name="ipData" value="<%=WebUtil.display(_ip_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="blockListForm_rangeCheck_field">
    <div id="blockListForm_rangeCheck_label" class="formLabel" >Range Check </div>
    <div id="blockListForm_rangeCheck_dropdown" class="formFieldDropDown" >       
        <select name="rangeCheck" id="_ffd_rangeCheck">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _range_checkValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _range_checkValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="blockListForm_reasonCode_field">
    <div id="blockListForm_reasonCode_label" class="formLabel" >Reason Code </div>
    <div id="blockListForm_reasonCode_dropdown" class="formFieldDropDown" >       
        <select class="field" name="reasonCode" id="_ffd_reasonCode">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _reason_codeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _reason_codeValue)%>>SPAM-AutoBlock</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _reason_codeValue)%>>SPAM-ManualBlock</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _reason_codeValue)%>>Attack-AutoBlock</option>
        <option value="99" <%=HtmlUtil.getOptionSelect("99", _reason_codeValue)%>>Other</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="blockListFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/blockListAction.html', 'blockListFormAdd', 'formSubmit_ajax', 'blockList');">Submit</a>
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
	document.getElementById("resultDisplayBlockList").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('blockListFormAddDis','/blockListAction.html', 'resultDisplayBlockList', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="blockListFormAddDis" method="POST" action="/blockListAction.html" id="blockListFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Ip Data</div>
        <input class="field" id="ipData" type="text" size="70" name="ipData" value="<%=WebUtil.display(_ip_dataValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Range Check</div>
        <select name="rangeCheck" id="rangeCheck">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _range_checkValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _range_checkValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Reason Code</div>
        <select class="field" name="reasonCode" id="reasonCode">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _reason_codeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _reason_codeValue)%>>SPAM-AutoBlock</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _reason_codeValue)%>>SPAM-ManualBlock</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _reason_codeValue)%>>Attack-AutoBlock</option>
        <option value="99" <%=HtmlUtil.getOptionSelect("99", _reason_codeValue)%>>Other</option>
        </select>  <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('blockListFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayBlockList"></span>
<a href="javascript:backToXX('blockListFormAddDis','resultDisplayBlockList')">show back</a><br>
<hr/>
