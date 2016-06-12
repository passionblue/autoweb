<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_expense_home";

    String _yearValue= WebUtil.display((String)reqParams.get("year"));
    String _weekValue= WebUtil.display((String)reqParams.get("week"));
    String _expense_item_idValue= WebUtil.display((String)reqParams.get("expenseItemId"));
    String _payee_idValue= WebUtil.display((String)reqParams.get("payeeId"));
    String _amountValue= WebUtil.display((String)reqParams.get("amount"));
    String _is_cashValue= WebUtil.display((String)reqParams.get("isCash"));
    String _check_numberValue= WebUtil.display((String)reqParams.get("checkNumber"));
    String _check_clearedValue= WebUtil.display((String)reqParams.get("checkCleared"));
    String _commentValue= WebUtil.display((String)reqParams.get("comment"));
    String _cancelledValue= WebUtil.display((String)reqParams.get("cancelled"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_chur_expense_home.html"> ChurExpense Home </a>
<%
	
	List list = null;
	list = ChurExpenseDS.getInstance().getBySiteId(site.getId());

%>

<div id="churExpenseList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		ChurExpense _ChurExpense = (ChurExpense) iter.next();	
%>

	<div id="churExpenseFrame<%=_ChurExpense.getId() %>" >

		<div id="year" >
			year:<%= _ChurExpense.getYear() %>
		</div>
		<div id="week" >
			week:<%= _ChurExpense.getWeek() %>
		</div>
		<div id="expenseItemId" >
			expenseItemId:<%= _ChurExpense.getExpenseItemId() %>
		</div>
		<div id="payeeId" >
			payeeId:<%= _ChurExpense.getPayeeId() %>
		</div>
		<div id="amount" >
			amount:<%= _ChurExpense.getAmount() %>
		</div>
		<div id="isCash" >
			isCash:<%= _ChurExpense.getIsCash() %>
		</div>
		<div id="checkNumber" >
			checkNumber:<%= _ChurExpense.getCheckNumber() %>
		</div>
		<div id="checkCleared" >
			checkCleared:<%= _ChurExpense.getCheckCleared() %>
		</div>
		<div id="comment" >
			comment:<%= _ChurExpense.getComment() %>
		</div>
		<div id="cancelled" >
			cancelled:<%= _ChurExpense.getCancelled() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _ChurExpense.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _ChurExpense.getTimeUpdated() %>
		</div>
		<div>
		<a id="churExpenseDeleteButton" href="javascript:deleteThis('/churExpenseAction.html',<%= _ChurExpense.getId()%>,'churExpenseFrame<%=_ChurExpense.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="churExpenseForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churExpenseFormAdd" method="POST" action="/churExpenseAction.html" id="churExpenseFormAdd">

	<div id="churExpenseForm_year_field">
    <div id="churExpenseForm_year_label" class="formLabel" >Year </div>
    <div id="churExpenseForm_year_text" class="formFieldText" >       
        <input class="field" id="_ffd_year" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_week_field">
    <div id="churExpenseForm_week_label" class="formLabel" >Week </div>
    <div id="churExpenseForm_week_text" class="formFieldText" >       
        <input class="field" id="_ffd_week" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_expenseItemId_field" class="formFieldFrame">
    <div id="churExpenseForm_expenseItemId_label" class="formLabel" >Expense Item Id </div>
    <div id="churExpenseForm_expenseItemId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="expenseItemId" id="_ffd_expenseItemId">
        <option value="" >- Please Select -</option>
<%
	List _listChurExpenseItem_expenseItemId = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurExpenseItem_expenseItemId.iterator(); iter.hasNext();){
		ChurExpenseItem _obj = (ChurExpenseItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_expense_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_payeeId_field" class="formFieldFrame">
    <div id="churExpenseForm_payeeId_label" class="formLabel" >Payee Id </div>
    <div id="churExpenseForm_payeeId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="payeeId" id="_ffd_payeeId">
        <option value="" >- Please Select -</option>
<%
	List _listChurPayee_payeeId = ChurPayeeDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurPayee_payeeId.iterator(); iter.hasNext();){
		ChurPayee _obj = (ChurPayee) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_payee_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getTitle() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_amount_field">
    <div id="churExpenseForm_amount_label" class="formLabel" >Amount </div>
    <div id="churExpenseForm_amount_text" class="formFieldText" >       
        <input class="field" id="_ffd_amount" type="text" size="70" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_isCash_field">
    <div id="churExpenseForm_isCash_label" class="formLabel" >Is Cash </div>
    <div id="churExpenseForm_isCash_dropdown" class="formFieldDropDown" >       
        <select name="isCash" id="_ffd_isCash">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_cashValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_cashValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_checkNumber_field">
    <div id="churExpenseForm_checkNumber_label" class="formLabel" >Check Number </div>
    <div id="churExpenseForm_checkNumber_text" class="formFieldText" >       
        <input class="field" id="_ffd_checkNumber" type="text" size="70" name="checkNumber" value="<%=WebUtil.display(_check_numberValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_checkCleared_field">
    <div id="churExpenseForm_checkCleared_label" class="formLabel" >Check Cleared </div>
    <div id="churExpenseForm_checkCleared_dropdown" class="formFieldDropDown" >       
        <select name="checkCleared" id="_ffd_checkCleared">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _check_clearedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _check_clearedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_comment_field">
    <div id="churExpenseForm_comment_label" class="formLabel" >Comment </div>
    <div id="churExpenseForm_comment_text" class="formFieldText" >       
        <input class="field" id="_ffd_comment" type="text" size="70" name="comment" value="<%=WebUtil.display(_commentValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_cancelled_field">
    <div id="churExpenseForm_cancelled_label" class="formLabel" >Cancelled </div>
    <div id="churExpenseForm_cancelled_dropdown" class="formFieldDropDown" >       
        <select name="cancelled" id="_ffd_cancelled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _cancelledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _cancelledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="churExpenseForm_timeUpdated_field">
    <div id="churExpenseForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="churExpenseForm_timeUpdated_text" class="formFieldText" >       
        <input class="field" id="_ffd_timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churExpenseFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/churExpenseAction.html', 'churExpenseFormAdd', 'formSubmit_ajax', 'churExpense');">Submit</a>
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
	document.getElementById("resultDisplayChurExpense").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('churExpenseFormAddDis','/churExpenseAction.html', 'resultDisplayChurExpense', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="churExpenseFormAddDis" method="POST" action="/churExpenseAction.html" id="churExpenseFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Year</div>
        <input class="field" id="year" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Week</div>
        <input class="field" id="week" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Expense Item Id</div>
        <select class="field" name="expenseItemId" id="expenseItemId">
        <option value="" >- Please Select -</option>
<%
	_listChurExpenseItem_expenseItemId = ChurExpenseItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurExpenseItem_expenseItemId.iterator(); iter.hasNext();){
		ChurExpenseItem _obj = (ChurExpenseItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_expense_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Payee Id</div>
        <select class="field" name="payeeId" id="payeeId">
        <option value="" >- Please Select -</option>
<%
	_listChurPayee_payeeId = ChurPayeeDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurPayee_payeeId.iterator(); iter.hasNext();){
		ChurPayee _obj = (ChurPayee) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_payee_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getTitle() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Amount</div>
        <input class="field" id="amount" type="text" size="70" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Cash</div>
        <select name="isCash" id="isCash">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_cashValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_cashValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Check Number</div>
        <input class="field" id="checkNumber" type="text" size="70" name="checkNumber" value="<%=WebUtil.display(_check_numberValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Check Cleared</div>
        <select name="checkCleared" id="checkCleared">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _check_clearedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _check_clearedValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Comment</div>
        <input class="field" id="comment" type="text" size="70" name="comment" value="<%=WebUtil.display(_commentValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Cancelled</div>
        <select name="cancelled" id="cancelled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _cancelledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _cancelledValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Time Updated</div>
        <input class="field" id="timeUpdated" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('churExpenseFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayChurExpense"></span>
<a href="javascript:backToXX('churExpenseFormAddDis','resultDisplayChurExpense')">show back</a><br>
<hr/>
