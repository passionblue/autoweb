<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_service_item_home";

    String _service_idValue= WebUtil.display((String)reqParams.get("serviceId"));
    String _service_item_idValue= WebUtil.display((String)reqParams.get("serviceItemId"));
    String _item_typeValue= WebUtil.display((String)reqParams.get("itemType"));
    String _titleValue= WebUtil.display((String)reqParams.get("title"));
    String _image_pathValue= WebUtil.display((String)reqParams.get("imagePath"));
    String _image_path_localValue= WebUtil.display((String)reqParams.get("imagePathLocal"));
    String _base_priceValue= WebUtil.display((String)reqParams.get("basePrice"));
    String _noteValue= WebUtil.display((String)reqParams.get("note"));
%> 

<a href="/v_cleaner_service_item_home.html"> CleanerServiceItem Home </a>
<%
	
	List list = null;
	list = CleanerServiceItemDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerServiceItemList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerServiceItem _CleanerServiceItem = (CleanerServiceItem) iter.next();	
%>

	<div id="cleanerServiceItemFrame<%=_CleanerServiceItem.getId() %>" >

		<div id="serviceId" >
			serviceId:<%= _CleanerServiceItem.getServiceId() %>
		</div>
		<div id="serviceItemId" >
			serviceItemId:<%= _CleanerServiceItem.getServiceItemId() %>
		</div>
		<div id="itemType" >
			itemType:<%= _CleanerServiceItem.getItemType() %>
		</div>
		<div id="title" >
			title:<%= _CleanerServiceItem.getTitle() %>
		</div>
		<div id="imagePath" >
			imagePath:<%= _CleanerServiceItem.getImagePath() %>
		</div>
		<div id="imagePathLocal" >
			imagePathLocal:<%= _CleanerServiceItem.getImagePathLocal() %>
		</div>
		<div id="basePrice" >
			basePrice:<%= _CleanerServiceItem.getBasePrice() %>
		</div>
		<div id="note" >
			note:<%= _CleanerServiceItem.getNote() %>
		</div>
		<div>
		<a id="cleanerServiceItemDeleteButton" href="javascript:deleteThis('/cleanerServiceItemAction.html',<%= _CleanerServiceItem.getId()%>,'cleanerServiceItemFrame<%=_CleanerServiceItem.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerServiceItemForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerServiceItemFormAdd" method="POST" action="/cleanerServiceItemAction.html" id="cleanerServiceItemFormAdd">

	<div id="cleanerServiceItemForm_serviceId_field">
    <div id="cleanerServiceItemForm_serviceId_label" class="formLabel" >Service Id </div>
    <div id="cleanerServiceItemForm_serviceId_text" class="formFieldText" >       
        <input class="field" id="_ffd_serviceId" type="text" size="70" name="serviceId" value="<%=WebUtil.display(_service_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceItemForm_serviceItemId_field">
    <div id="cleanerServiceItemForm_serviceItemId_label" class="formLabel" >Service Item Id </div>
    <div id="cleanerServiceItemForm_serviceItemId_text" class="formFieldText" >       
        <input class="field" id="_ffd_serviceItemId" type="text" size="70" name="serviceItemId" value="<%=WebUtil.display(_service_item_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceItemForm_itemType_field">
    <div id="cleanerServiceItemForm_itemType_label" class="formLabel" >Item Type </div>
    <div id="cleanerServiceItemForm_itemType_text" class="formFieldText" >       
        <input class="field" id="_ffd_itemType" type="text" size="70" name="itemType" value="<%=WebUtil.display(_item_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceItemForm_title_field">
    <div id="cleanerServiceItemForm_title_label" class="formLabel" >Title </div>
    <div id="cleanerServiceItemForm_title_text" class="formFieldText" >       
        <input class="field" id="_ffd_title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceItemForm_imagePath_field">
    <div id="cleanerServiceItemForm_imagePath_label" class="formLabel" >Image Path </div>
    <div id="cleanerServiceItemForm_imagePath_text" class="formFieldText" >       
        <input class="field" id="_ffd_imagePath" type="text" size="70" name="imagePath" value="<%=WebUtil.display(_image_pathValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceItemForm_imagePathLocal_field">
    <div id="cleanerServiceItemForm_imagePathLocal_label" class="formLabel" >Image Path Local </div>
    <div id="cleanerServiceItemForm_imagePathLocal_text" class="formFieldText" >       
        <input class="field" id="_ffd_imagePathLocal" type="text" size="70" name="imagePathLocal" value="<%=WebUtil.display(_image_path_localValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceItemForm_basePrice_field">
    <div id="cleanerServiceItemForm_basePrice_label" class="formLabel" >Base Price </div>
    <div id="cleanerServiceItemForm_basePrice_text" class="formFieldText" >       
        <input class="field" id="_ffd_basePrice" type="text" size="70" name="basePrice" value="<%=WebUtil.display(_base_priceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerServiceItemForm_note_field">
    <div id="cleanerServiceItemForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerServiceItemForm_note_text" class="formFieldText" >       
        <input class="field" id="_ffd_note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerServiceItemFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerServiceItemAction.html', 'cleanerServiceItemFormAdd', 'formSubmit_ajax', 'cleanerServiceItem');">Submit</a>
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
	document.getElementById("resultDisplayCleanerServiceItem").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerServiceItemFormAddDis','/cleanerServiceItemAction.html', 'resultDisplayCleanerServiceItem', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerServiceItemFormAddDis" method="POST" action="/cleanerServiceItemAction.html" id="cleanerServiceItemFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Service Id</div>
        <input class="field" id="serviceId" type="text" size="70" name="serviceId" value="<%=WebUtil.display(_service_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Service Item Id</div>
        <input class="field" id="serviceItemId" type="text" size="70" name="serviceItemId" value="<%=WebUtil.display(_service_item_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Item Type</div>
        <input class="field" id="itemType" type="text" size="70" name="itemType" value="<%=WebUtil.display(_item_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Title</div>
        <input class="field" id="title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Image Path</div>
        <input class="field" id="imagePath" type="text" size="70" name="imagePath" value="<%=WebUtil.display(_image_pathValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Image Path Local</div>
        <input class="field" id="imagePathLocal" type="text" size="70" name="imagePathLocal" value="<%=WebUtil.display(_image_path_localValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Base Price</div>
        <input class="field" id="basePrice" type="text" size="70" name="basePrice" value="<%=WebUtil.display(_base_priceValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Note</div>
        <input class="field" id="note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerServiceItemFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerServiceItem"></span>
<a href="javascript:backToXX('cleanerServiceItemFormAddDis','resultDisplayCleanerServiceItem')">show back</a><br>
<hr/>
