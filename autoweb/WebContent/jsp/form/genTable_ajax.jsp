<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_table_home";

    String _countryValue= WebUtil.display((String)reqParams.get("country"));
    String _ageValue= WebUtil.display((String)reqParams.get("age"));
    String _disabledValue= WebUtil.display((String)reqParams.get("disabled"));
    String _commentsValue= WebUtil.display((String)reqParams.get("comments"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
    String _ext_stringValue= WebUtil.display((String)reqParams.get("extString"));
    String _ext_intValue= WebUtil.display((String)reqParams.get("extInt"));
%> 

<a href="/v_gen_table_home.html"> GenTable Home </a>
<%
	
	List list = null;
	list = GenTableDS.getInstance().getBySiteId(site.getId());

%>

<div id="genTableList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		GenTableDataHolder _GenTable = (GenTableDataHolder) iter.next();	
%>

	<div id="genTableFrame<%=_GenTable.getId() %>" >

		<div id="country" >
			country:<%= _GenTable.getCountry() %>
		</div>
		<div id="age" >
			age:<%= _GenTable.getAge() %>
		</div>
		<div id="disabled" >
			disabled:<%= _GenTable.getDisabled() %>
		</div>
		<div id="comments" >
			comments:<%= _GenTable.getComments() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _GenTable.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _GenTable.getTimeUpdated() %>
		</div>
		<div id="extString" >
			extString:<%= _GenTable.getExtString() %>
		</div>
		<div id="extInt" >
			extInt:<%= _GenTable.getExtInt() %>
		</div>
		<div>
		<a id="genTableDeleteButton" href="javascript:deleteThis('/genTableAction.html',<%= _GenTable.getId()%>,'genTableFrame<%=_GenTable.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="genTableForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genTableFormAdd" method="POST" action="/genTableAction.html" id="genTableFormAdd">

	<div id="genTableForm_country_field">
    <div id="genTableForm_country_label" class="formLabel" >Country </div>
    <div id="genTableForm_country_dropdown" class="formFieldDropDown" >       
        <select class="field" name="country" id="_ffd_country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        <option value="usa" <%=HtmlUtil.getOptionSelect("usa", _countryValue)%>>United States</option>
        <option value="korea" <%=HtmlUtil.getOptionSelect("korea", _countryValue)%>>SouthKorea</option>
        <option value="congo" <%=HtmlUtil.getOptionSelect("congo", _countryValue)%>>Congo</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genTableForm_age_field">
    <div id="genTableForm_age_label" class="formLabel" >Age </div>
    <div id="genTableForm_age_dropdown" class="formFieldDropDown" >       
        <select class="field" name="age" id="_ffd_age">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ageValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genTableForm_disabled_field">
    <div id="genTableForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="genTableForm_disabled_dropdown" class="formFieldDropDown" >       
        <select name="disabled" id="_ffd_disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="genTableForm_comments_field">
    <div id="genTableForm_comments_label" class="formLabel" >Comments </div>
    <div id="genTableForm_comments_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_comments" class="field" NAME="comments" COLS="50" ROWS="8" ><%=WebUtil.display(_commentsValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="genTableForm_extString_field">
    <div id="genTableForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genTableForm_extString_text" class="formFieldText" >       
        <input class="field" id="_ffd_extString" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="genTableForm_extInt_field">
    <div id="genTableForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genTableForm_extInt_text" class="formFieldText" >       
        <input class="field" id="_ffd_extInt" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="genTableFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/genTableAction.html', 'genTableFormAdd', 'formSubmit_ajax', 'genTable');">Submit</a>
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
	document.getElementById("resultDisplayGenTable").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('genTableFormAddDis','/genTableAction.html', 'resultDisplayGenTable', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="genTableFormAddDis" method="POST" action="/genTableAction.html" id="genTableFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Country</div>
        <select class="field" name="country" id="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        <option value="usa" <%=HtmlUtil.getOptionSelect("usa", _countryValue)%>>United States</option>
        <option value="korea" <%=HtmlUtil.getOptionSelect("korea", _countryValue)%>>SouthKorea</option>
        <option value="congo" <%=HtmlUtil.getOptionSelect("congo", _countryValue)%>>Congo</option>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Age</div>
        <select class="field" name="age" id="age">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ageValue)%>>XX</option-->
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disabled</div>
        <select name="disabled" id="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Comments</div>
		<TEXTAREA id="comments" class="field" NAME="comments" COLS="50" ROWS="8" ><%=WebUtil.display(_commentsValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> ExtraString</div>
        <input class="field" id="extString" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ext Int</div>
        <input class="field" id="extInt" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('genTableFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayGenTable"></span>
<a href="javascript:backToXX('genTableFormAddDis','resultDisplayGenTable')">show back</a><br>
<hr/>
