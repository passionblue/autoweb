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
	if (cancelPage == null) cancelPage = "cleaner_ticket_item_home";

    String _ticket_idValue= WebUtil.display((String)reqParams.get("ticketId"));
    String _parent_ticket_idValue= WebUtil.display((String)reqParams.get("parentTicketId"));
    String _product_idValue= WebUtil.display((String)reqParams.get("productId"));
    String _subtotal_amountValue= WebUtil.display((String)reqParams.get("subtotalAmount"));
    String _total_amountValue= WebUtil.display((String)reqParams.get("totalAmount"));
    String _discount_idValue= WebUtil.display((String)reqParams.get("discountId"));
    String _total_discount_amountValue= WebUtil.display((String)reqParams.get("totalDiscountAmount"));
    String _special_discount_amountValue= WebUtil.display((String)reqParams.get("specialDiscountAmount"));
    String _noteValue= WebUtil.display((String)reqParams.get("note"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_cleaner_ticket_item_home.html"> CleanerTicketItem Home </a>
<%
	
	List list = null;
	list = CleanerTicketItemDS.getInstance().getBySiteId(site.getId());

%>

<div id="cleanerTicketItemList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		CleanerTicketItem _CleanerTicketItem = (CleanerTicketItem) iter.next();	
%>

	<div id="cleanerTicketItemFrame<%=_CleanerTicketItem.getId() %>" >

		<div id="ticketId" >
			ticketId:<%= _CleanerTicketItem.getTicketId() %>
		</div>
		<div id="parentTicketId" >
			parentTicketId:<%= _CleanerTicketItem.getParentTicketId() %>
		</div>
		<div id="productId" >
			productId:<%= _CleanerTicketItem.getProductId() %>
		</div>
		<div id="subtotalAmount" >
			subtotalAmount:<%= _CleanerTicketItem.getSubtotalAmount() %>
		</div>
		<div id="totalAmount" >
			totalAmount:<%= _CleanerTicketItem.getTotalAmount() %>
		</div>
		<div id="discountId" >
			discountId:<%= _CleanerTicketItem.getDiscountId() %>
		</div>
		<div id="totalDiscountAmount" >
			totalDiscountAmount:<%= _CleanerTicketItem.getTotalDiscountAmount() %>
		</div>
		<div id="specialDiscountAmount" >
			specialDiscountAmount:<%= _CleanerTicketItem.getSpecialDiscountAmount() %>
		</div>
		<div id="note" >
			note:<%= _CleanerTicketItem.getNote() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _CleanerTicketItem.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _CleanerTicketItem.getTimeUpdated() %>
		</div>
		<div>
		<a id="cleanerTicketItemDeleteButton" href="javascript:deleteThis('/cleanerTicketItemAction.html',<%= _CleanerTicketItem.getId()%>,'cleanerTicketItemFrame<%=_CleanerTicketItem.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="cleanerTicketItemForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerTicketItemFormAdd" method="POST" action="/cleanerTicketItemAction.html" id="cleanerTicketItemFormAdd">

	<div id="cleanerTicketItemForm_ticketId_field">
    <div id="cleanerTicketItemForm_ticketId_label" class="formLabel" >Ticket Id </div>
    <div id="cleanerTicketItemForm_ticketId_text" class="formFieldText" >       
        <input class="field" id="_ffd_ticketId" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_parentTicketId_field">
    <div id="cleanerTicketItemForm_parentTicketId_label" class="formLabel" >Parent Ticket Id </div>
    <div id="cleanerTicketItemForm_parentTicketId_text" class="formFieldText" >       
        <input class="field" id="_ffd_parentTicketId" type="text" size="70" name="parentTicketId" value="<%=WebUtil.display(_parent_ticket_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_productId_field">
    <div id="cleanerTicketItemForm_productId_label" class="formLabel" >Product Id </div>
    <div id="cleanerTicketItemForm_productId_text" class="formFieldText" >       
        <input class="field" id="_ffd_productId" type="text" size="70" name="productId" value="<%=WebUtil.display(_product_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_subtotalAmount_field">
    <div id="cleanerTicketItemForm_subtotalAmount_label" class="formLabel" >Subtotal Amount </div>
    <div id="cleanerTicketItemForm_subtotalAmount_text" class="formFieldText" >       
        <input class="field" id="_ffd_subtotalAmount" type="text" size="70" name="subtotalAmount" value="<%=WebUtil.display(_subtotal_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_totalAmount_field">
    <div id="cleanerTicketItemForm_totalAmount_label" class="formLabel" >Total Amount </div>
    <div id="cleanerTicketItemForm_totalAmount_text" class="formFieldText" >       
        <input class="field" id="_ffd_totalAmount" type="text" size="70" name="totalAmount" value="<%=WebUtil.display(_total_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_discountId_field">
    <div id="cleanerTicketItemForm_discountId_label" class="formLabel" >Discount Id </div>
    <div id="cleanerTicketItemForm_discountId_text" class="formFieldText" >       
        <input class="field" id="_ffd_discountId" type="text" size="70" name="discountId" value="<%=WebUtil.display(_discount_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_totalDiscountAmount_field">
    <div id="cleanerTicketItemForm_totalDiscountAmount_label" class="formLabel" >Total Discount Amount </div>
    <div id="cleanerTicketItemForm_totalDiscountAmount_text" class="formFieldText" >       
        <input class="field" id="_ffd_totalDiscountAmount" type="text" size="70" name="totalDiscountAmount" value="<%=WebUtil.display(_total_discount_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_specialDiscountAmount_field">
    <div id="cleanerTicketItemForm_specialDiscountAmount_label" class="formLabel" >Special Discount Amount </div>
    <div id="cleanerTicketItemForm_specialDiscountAmount_text" class="formFieldText" >       
        <input class="field" id="_ffd_specialDiscountAmount" type="text" size="70" name="specialDiscountAmount" value="<%=WebUtil.display(_special_discount_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="cleanerTicketItemForm_note_field">
    <div id="cleanerTicketItemForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerTicketItemForm_note_text" class="formFieldText" >       
        <input class="field" id="_ffd_note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="cleanerTicketItemFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/cleanerTicketItemAction.html', 'cleanerTicketItemFormAdd', 'formSubmit_ajax', 'cleanerTicketItem');">Submit</a>
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
	document.getElementById("resultDisplayCleanerTicketItem").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('cleanerTicketItemFormAddDis','/cleanerTicketItemAction.html', 'resultDisplayCleanerTicketItem', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="cleanerTicketItemFormAddDis" method="POST" action="/cleanerTicketItemAction.html" id="cleanerTicketItemFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Ticket Id</div>
        <input class="field" id="ticketId" type="text" size="70" name="ticketId" value="<%=WebUtil.display(_ticket_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Parent Ticket Id</div>
        <input class="field" id="parentTicketId" type="text" size="70" name="parentTicketId" value="<%=WebUtil.display(_parent_ticket_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Product Id</div>
        <input class="field" id="productId" type="text" size="70" name="productId" value="<%=WebUtil.display(_product_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Subtotal Amount</div>
        <input class="field" id="subtotalAmount" type="text" size="70" name="subtotalAmount" value="<%=WebUtil.display(_subtotal_amountValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Total Amount</div>
        <input class="field" id="totalAmount" type="text" size="70" name="totalAmount" value="<%=WebUtil.display(_total_amountValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Discount Id</div>
        <input class="field" id="discountId" type="text" size="70" name="discountId" value="<%=WebUtil.display(_discount_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Total Discount Amount</div>
        <input class="field" id="totalDiscountAmount" type="text" size="70" name="totalDiscountAmount" value="<%=WebUtil.display(_total_discount_amountValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Special Discount Amount</div>
        <input class="field" id="specialDiscountAmount" type="text" size="70" name="specialDiscountAmount" value="<%=WebUtil.display(_special_discount_amountValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Note</div>
        <input class="field" id="note" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('cleanerTicketItemFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayCleanerTicketItem"></span>
<a href="javascript:backToXX('cleanerTicketItemFormAddDis','resultDisplayCleanerTicketItem')">show back</a><br>
<hr/>
