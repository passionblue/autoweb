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
	if (cancelPage == null) cancelPage = "cleaner_ticket_home";

    String _serialValue= WebUtil.display((String)reqParams.get("serial"));
    String _parent_ticket_idValue= WebUtil.display((String)reqParams.get("parentTicketId"));
    String _customer_idValue= WebUtil.display((String)reqParams.get("customerId"));
    String _enter_user_idValue= WebUtil.display((String)reqParams.get("enterUserId"));
    String _location_idValue= WebUtil.display((String)reqParams.get("locationId"));
    String _noteValue= WebUtil.display((String)reqParams.get("note"));
    String _completedValue= WebUtil.display((String)reqParams.get("completed"));
    String _onholdValue= WebUtil.display((String)reqParams.get("onhold"));
    String _original_ticket_idValue= WebUtil.display((String)reqParams.get("originalTicketId"));
    String _returnedValue= WebUtil.display((String)reqParams.get("returned"));
    String _returned_reason_textValue= WebUtil.display((String)reqParams.get("returnedReasonText"));
    String _returned_noteValue= WebUtil.display((String)reqParams.get("returnedNote"));
    String _total_chargeValue= WebUtil.display((String)reqParams.get("totalCharge"));
    String _final_chargeValue= WebUtil.display((String)reqParams.get("finalCharge"));
    String _discount_idValue= WebUtil.display((String)reqParams.get("discountId"));
    String _discount_amountValue= WebUtil.display((String)reqParams.get("discountAmount"));
    String _discount_noteValue= WebUtil.display((String)reqParams.get("discountNote"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
    String _time_completedValue= WebUtil.display((String)reqParams.get("timeCompleted"));
    String _time_onholdValue= WebUtil.display((String)reqParams.get("timeOnhold"));
%> 

<a href="/v_cleaner_ticket_home.html"> CleanerTicket Home </a>
<%
	
	List list = null;
	list = CleanerTicketDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerTicketList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerTicket _CleanerTicket = (CleanerTicket) iter.next();	
%>

	<div id="cleanerTicketFrame<%=_CleanerTicket.getId() %>" >

		<div id="serial" >
			serial:<%= _CleanerTicket.getSerial() %>
		</div>
		<div id="parentTicketId" >
			parentTicketId:<%= _CleanerTicket.getParentTicketId() %>
		</div>
		<div id="customerId" >
			customerId:<%= _CleanerTicket.getCustomerId() %>
		</div>
		<div id="enterUserId" >
			enterUserId:<%= _CleanerTicket.getEnterUserId() %>
		</div>
		<div id="locationId" >
			locationId:<%= _CleanerTicket.getLocationId() %>
		</div>
		<div id="note" >
			note:<%= _CleanerTicket.getNote() %>
		</div>
		<div id="completed" >
			completed:<%= _CleanerTicket.getCompleted() %>
		</div>
		<div id="onhold" >
			onhold:<%= _CleanerTicket.getOnhold() %>
		</div>
		<div id="originalTicketId" >
			originalTicketId:<%= _CleanerTicket.getOriginalTicketId() %>
		</div>
		<div id="returned" >
			returned:<%= _CleanerTicket.getReturned() %>
		</div>
		<div id="returnedReasonText" >
			returnedReasonText:<%= _CleanerTicket.getReturnedReasonText() %>
		</div>
		<div id="returnedNote" >
			returnedNote:<%= _CleanerTicket.getReturnedNote() %>
		</div>
		<div id="totalCharge" >
			totalCharge:<%= _CleanerTicket.getTotalCharge() %>
		</div>
		<div id="finalCharge" >
			finalCharge:<%= _CleanerTicket.getFinalCharge() %>
		</div>
		<div id="discountId" >
			discountId:<%= _CleanerTicket.getDiscountId() %>
		</div>
		<div id="discountAmount" >
			discountAmount:<%= _CleanerTicket.getDiscountAmount() %>
		</div>
		<div id="discountNote" >
			discountNote:<%= _CleanerTicket.getDiscountNote() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _CleanerTicket.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _CleanerTicket.getTimeUpdated() %>
		</div>
		<div id="timeCompleted" >
			timeCompleted:<%= _CleanerTicket.getTimeCompleted() %>
		</div>
		<div id="timeOnhold" >
			timeOnhold:<%= _CleanerTicket.getTimeOnhold() %>
		</div>
		<div>
		<a id="cleanerTicketDeleteButton" href="javascript:deleteThis('/cleanerTicketAction.html',<%= _CleanerTicket.getId()%>,'cleanerTicketFrame<%=_CleanerTicket.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerTicketForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerTicketFormAdd" method="POST" action="/cleanerTicketAction.html" id="cleanerTicketFormAdd">

	<div id="cleanerTicketForm_serial_field">
    <div id="cleanerTicketForm_serial_label" class="formLabel" >Serial </div>
    <div id="cleanerTicketForm_serial_text" class="formFieldText" >       
        <input class="field" id="_ffd_serial" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_parentTicketId_field">
    <div id="cleanerTicketForm_parentTicketId_label" class="formLabel" >Parent Ticket Id </div>
    <div id="cleanerTicketForm_parentTicketId_text" class="formFieldText" >       
        <input class="field" id="_ffd_parentTicketId" type="text" size="70" name="parentTicketId" value="<%=WebUtil.display(_parent_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_customerId_field">
    <div id="cleanerTicketForm_customerId_label" class="formLabel" >Customer Id </div>
    <div id="cleanerTicketForm_customerId_text" class="formFieldText" >       
        <input class="field" id="_ffd_customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_enterUserId_field">
    <div id="cleanerTicketForm_enterUserId_label" class="formLabel" >Enter User Id </div>
    <div id="cleanerTicketForm_enterUserId_text" class="formFieldText" >       
        <input class="field" id="_ffd_enterUserId" type="text" size="70" name="enterUserId" value="<%=WebUtil.display(_enter_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_locationId_field">
    <div id="cleanerTicketForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerTicketForm_locationId_text" class="formFieldText" >       
        <input class="field" id="_ffd_locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_note_field">
    <div id="cleanerTicketForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerTicketForm_note_text" class="formFieldText" >       
        <input class="field" id="_ffd_note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_completed_field">
    <div id="cleanerTicketForm_completed_label" class="formLabel" >Completed </div>
    <div id="cleanerTicketForm_completed_text" class="formFieldText" >       
        <input class="field" id="_ffd_completed" type="text" size="70" name="completed" value="<%=WebUtil.display(_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_onhold_field">
    <div id="cleanerTicketForm_onhold_label" class="formLabel" >Onhold </div>
    <div id="cleanerTicketForm_onhold_text" class="formFieldText" >       
        <input class="field" id="_ffd_onhold" type="text" size="70" name="onhold" value="<%=WebUtil.display(_onholdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_originalTicketId_field">
    <div id="cleanerTicketForm_originalTicketId_label" class="formLabel" >Original Ticket Id </div>
    <div id="cleanerTicketForm_originalTicketId_text" class="formFieldText" >       
        <input class="field" id="_ffd_originalTicketId" type="text" size="70" name="originalTicketId" value="<%=WebUtil.display(_original_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_returned_field">
    <div id="cleanerTicketForm_returned_label" class="formLabel" >Returned </div>
    <div id="cleanerTicketForm_returned_text" class="formFieldText" >       
        <input class="field" id="_ffd_returned" type="text" size="70" name="returned" value="<%=WebUtil.display(_returnedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_returnedReasonText_field">
    <div id="cleanerTicketForm_returnedReasonText_label" class="formLabel" >Returned Reason Text </div>
    <div id="cleanerTicketForm_returnedReasonText_text" class="formFieldText" >       
        <input class="field" id="_ffd_returnedReasonText" type="text" size="70" name="returnedReasonText" value="<%=WebUtil.display(_returned_reason_textValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_returnedNote_field">
    <div id="cleanerTicketForm_returnedNote_label" class="formLabel" >Returned Note </div>
    <div id="cleanerTicketForm_returnedNote_text" class="formFieldText" >       
        <input class="field" id="_ffd_returnedNote" type="text" size="70" name="returnedNote" value="<%=WebUtil.display(_returned_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_totalCharge_field">
    <div id="cleanerTicketForm_totalCharge_label" class="formLabel" >Total Charge </div>
    <div id="cleanerTicketForm_totalCharge_text" class="formFieldText" >       
        <input class="field" id="_ffd_totalCharge" type="text" size="70" name="totalCharge" value="<%=WebUtil.display(_total_chargeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_finalCharge_field">
    <div id="cleanerTicketForm_finalCharge_label" class="formLabel" >Final Charge </div>
    <div id="cleanerTicketForm_finalCharge_text" class="formFieldText" >       
        <input class="field" id="_ffd_finalCharge" type="text" size="70" name="finalCharge" value="<%=WebUtil.display(_final_chargeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_discountId_field">
    <div id="cleanerTicketForm_discountId_label" class="formLabel" >Discount Id </div>
    <div id="cleanerTicketForm_discountId_text" class="formFieldText" >       
        <input class="field" id="_ffd_discountId" type="text" size="70" name="discountId" value="<%=WebUtil.display(_discount_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_discountAmount_field">
    <div id="cleanerTicketForm_discountAmount_label" class="formLabel" >Discount Amount </div>
    <div id="cleanerTicketForm_discountAmount_text" class="formFieldText" >       
        <input class="field" id="_ffd_discountAmount" type="text" size="70" name="discountAmount" value="<%=WebUtil.display(_discount_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_discountNote_field">
    <div id="cleanerTicketForm_discountNote_label" class="formLabel" >Discount Note </div>
    <div id="cleanerTicketForm_discountNote_text" class="formFieldText" >       
        <input class="field" id="_ffd_discountNote" type="text" size="70" name="discountNote" value="<%=WebUtil.display(_discount_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_timeCompleted_field">
    <div id="cleanerTicketForm_timeCompleted_label" class="formLabel" >Time Completed </div>
    <div id="cleanerTicketForm_timeCompleted_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeCompleted" type="text" size="70" name="timeCompleted" value="<%=WebUtil.display(_time_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketForm_timeOnhold_field">
    <div id="cleanerTicketForm_timeOnhold_label" class="formLabel" >Time Onhold </div>
    <div id="cleanerTicketForm_timeOnhold_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeOnhold" type="text" size="70" name="timeOnhold" value="<%=WebUtil.display(_time_onholdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerTicketFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerTicketAction.html', 'cleanerTicketFormAdd', 'formSubmit_ajax', 'cleanerTicket');">Submit</a>
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
	document.getElementById("resultDisplayCleanerTicket").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerTicketFormAddDis','/cleanerTicketAction.html', 'resultDisplayCleanerTicket', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerTicketFormAddDis" method="POST" action="/cleanerTicketAction.html" id="cleanerTicketFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Serial</div>
        <input class="field" id="serial" type="text" size="70" name="serial" value="<%=WebUtil.display(_serialValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Parent Ticket Id</div>
        <input class="field" id="parentTicketId" type="text" size="70" name="parentTicketId" value="<%=WebUtil.display(_parent_ticket_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Customer Id</div>
        <input class="field" id="customerId" type="text" size="70" name="customerId" value="<%=WebUtil.display(_customer_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Enter User Id</div>
        <input class="field" id="enterUserId" type="text" size="70" name="enterUserId" value="<%=WebUtil.display(_enter_user_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Location Id</div>
        <input class="field" id="locationId" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Note</div>
        <input class="field" id="note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Completed</div>
        <input class="field" id="completed" type="text" size="70" name="completed" value="<%=WebUtil.display(_completedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Onhold</div>
        <input class="field" id="onhold" type="text" size="70" name="onhold" value="<%=WebUtil.display(_onholdValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Original Ticket Id</div>
        <input class="field" id="originalTicketId" type="text" size="70" name="originalTicketId" value="<%=WebUtil.display(_original_ticket_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Returned</div>
        <input class="field" id="returned" type="text" size="70" name="returned" value="<%=WebUtil.display(_returnedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Returned Reason Text</div>
        <input class="field" id="returnedReasonText" type="text" size="70" name="returnedReasonText" value="<%=WebUtil.display(_returned_reason_textValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Returned Note</div>
        <input class="field" id="returnedNote" type="text" size="70" name="returnedNote" value="<%=WebUtil.display(_returned_noteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Total Charge</div>
        <input class="field" id="totalCharge" type="text" size="70" name="totalCharge" value="<%=WebUtil.display(_total_chargeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Final Charge</div>
        <input class="field" id="finalCharge" type="text" size="70" name="finalCharge" value="<%=WebUtil.display(_final_chargeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Discount Id</div>
        <input class="field" id="discountId" type="text" size="70" name="discountId" value="<%=WebUtil.display(_discount_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Discount Amount</div>
        <input class="field" id="discountAmount" type="text" size="70" name="discountAmount" value="<%=WebUtil.display(_discount_amountValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Discount Note</div>
        <input class="field" id="discountNote" type="text" size="70" name="discountNote" value="<%=WebUtil.display(_discount_noteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Completed</div>
        <input class="field" id="timeCompleted" type="text" size="70" name="timeCompleted" value="<%=WebUtil.display(_time_completedValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Onhold</div>
        <input class="field" id="timeOnhold" type="text" size="70" name="timeOnhold" value="<%=WebUtil.display(_time_onholdValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerTicketFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerTicket"></span>
<a href="javascript:backToXX('cleanerTicketFormAddDis','resultDisplayCleanerTicket')">show back</a><br>
<hr/>
