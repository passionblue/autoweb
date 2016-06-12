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
	if (cancelPage == null) cancelPage = "style_set_content_home";

    String _nameValue= WebUtil.display((String)reqParams.get("name"));
    String _id_prefixValue= WebUtil.display((String)reqParams.get("idPrefix"));
    String _list_frame_style_idValue= WebUtil.display((String)reqParams.get("listFrameStyleId"));
    String _list_subject_style_idValue= WebUtil.display((String)reqParams.get("listSubjectStyleId"));
    String _list_data_style_idValue= WebUtil.display((String)reqParams.get("listDataStyleId"));
    String _frame_style_idValue= WebUtil.display((String)reqParams.get("frameStyleId"));
    String _subject_style_idValue= WebUtil.display((String)reqParams.get("subjectStyleId"));
    String _data_style_idValue= WebUtil.display((String)reqParams.get("dataStyleId"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_style_set_content_home.html"> StyleSetContent Home </a>
<%
	
	List list = null;
	list = StyleSetContentDS.getInstance().getBySiteId(site.getId());

%>

<div id="styleSetContentList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		StyleSetContent _StyleSetContent = (StyleSetContent) iter.next();	
%>

	<div id="styleSetContentFrame<%=_StyleSetContent.getId() %>" >

		<div id="name" >
			name:<%= _StyleSetContent.getName() %>
		</div>
		<div id="idPrefix" >
			idPrefix:<%= _StyleSetContent.getIdPrefix() %>
		</div>
		<div id="listFrameStyleId" >
			listFrameStyleId:<%= _StyleSetContent.getListFrameStyleId() %>
		</div>
		<div id="listSubjectStyleId" >
			listSubjectStyleId:<%= _StyleSetContent.getListSubjectStyleId() %>
		</div>
		<div id="listDataStyleId" >
			listDataStyleId:<%= _StyleSetContent.getListDataStyleId() %>
		</div>
		<div id="frameStyleId" >
			frameStyleId:<%= _StyleSetContent.getFrameStyleId() %>
		</div>
		<div id="subjectStyleId" >
			subjectStyleId:<%= _StyleSetContent.getSubjectStyleId() %>
		</div>
		<div id="dataStyleId" >
			dataStyleId:<%= _StyleSetContent.getDataStyleId() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _StyleSetContent.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _StyleSetContent.getTimeUpdated() %>
		</div>
		<div>
		<a id="styleSetContentDeleteButton" href="javascript:deleteThis('/styleSetContentAction.html',<%= _StyleSetContent.getId()%>,'styleSetContentFrame<%=_StyleSetContent.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="styleSetContentForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleSetContentFormAdd" method="POST" action="/styleSetContentAction.html" id="styleSetContentFormAdd">

	<div id="styleSetContentForm_name_field">
    <div id="styleSetContentForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="styleSetContentForm_name_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_name" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleSetContentForm_idPrefix_field">
    <div id="styleSetContentForm_idPrefix_label" class="formRequiredLabel" >Id Prefix* </div>
    <div id="styleSetContentForm_idPrefix_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_idPrefix" type="text" size="70" name="idPrefix" value="<%=WebUtil.display(_id_prefixValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleSetContentForm_listFrameStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_listFrameStyleId_label" class="formLabel${classSuffix}" >List Frame Style Id </div>
    <div id="styleSetContentForm_listFrameStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="listFrameStyleId" id="_ffd_listFrameStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_listFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleSetContentForm_listSubjectStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_listSubjectStyleId_label" class="formLabel${classSuffix}" >List Subject Style Id </div>
    <div id="styleSetContentForm_listSubjectStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="listSubjectStyleId" id="_ffd_listSubjectStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_listSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleSetContentForm_listDataStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_listDataStyleId_label" class="formLabel${classSuffix}" >List Data Style Id </div>
    <div id="styleSetContentForm_listDataStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="listDataStyleId" id="_ffd_listDataStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_listDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleSetContentForm_frameStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_frameStyleId_label" class="formLabel${classSuffix}" >Frame Style Id </div>
    <div id="styleSetContentForm_frameStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="frameStyleId" id="_ffd_frameStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_frameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_frameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleSetContentForm_subjectStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_subjectStyleId_label" class="formLabel${classSuffix}" >Subject Style Id </div>
    <div id="styleSetContentForm_subjectStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="subjectStyleId" id="_ffd_subjectStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_subjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_subjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleSetContentForm_dataStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_dataStyleId_label" class="formLabel${classSuffix}" >Data Style Id </div>
    <div id="styleSetContentForm_dataStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="dataStyleId" id="_ffd_dataStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_dataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_dataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="styleSetContentFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/styleSetContentAction.html', 'styleSetContentFormAdd', 'formSubmit_ajax', 'styleSetContent');">Submit</a>
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
	document.getElementById("resultDisplayStyleSetContent").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('styleSetContentFormAddDis','/styleSetContentAction.html', 'resultDisplayStyleSetContent', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="styleSetContentFormAddDis" method="POST" action="/styleSetContentAction.html" id="styleSetContentFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Name</div>
        <input class="requiredField" id="name" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Id Prefix</div>
        <input class="requiredField" id="idPrefix" type="text" size="70" name="idPrefix" value="<%=WebUtil.display(_id_prefixValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Frame Style Id</div>
        <select class="field" name="listFrameStyleId" id="listFrameStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_listFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Subject Style Id</div>
        <select class="field" name="listSubjectStyleId" id="listSubjectStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_listSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Data Style Id</div>
        <select class="field" name="listDataStyleId" id="listDataStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_listDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Frame Style Id</div>
        <select class="field" name="frameStyleId" id="frameStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_frameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_frameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Subject Style Id</div>
        <select class="field" name="subjectStyleId" id="subjectStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_subjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_subjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Data Style Id</div>
        <select class="field" name="dataStyleId" id="dataStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_dataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_dataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('styleSetContentFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayStyleSetContent"></span>
<a href="javascript:backToXX('styleSetContentFormAddDis','resultDisplayStyleSetContent')">show back</a><br>
<hr/>
