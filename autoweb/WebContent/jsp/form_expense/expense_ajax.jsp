<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
/* 
Template last modification history:


Source Generated: Sun Jul 12 20:40:22 EDT 2015
*/

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "expense_home";

    String _expense_item_idValue= WebUtil.display((String)reqParams.get("expenseItemId"));
    String _amountValue= WebUtil.display((String)reqParams.get("amount"));
    String _descriptionValue= WebUtil.display((String)reqParams.get("description"));
    String _pay_methodValue= WebUtil.display((String)reqParams.get("payMethod"));
    String _not_expenseValue= WebUtil.display((String)reqParams.get("notExpense"));
    String _referenceValue= WebUtil.display((String)reqParams.get("reference"));
    String _date_expenseValue= WebUtil.display((String)reqParams.get("dateExpense"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_expense_home.html"> Expense Home </a>
<%
	
	List list = null;
	list = ExpenseDS.getInstance().getBySiteId(site.getId());

%>

<div id="expenseList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		Expense _Expense = (Expense) iter.next();	
%>

	<div id="expenseFrame<%=_Expense.getId() %>" >

		<div id="expenseItemId" >
			expenseItemId:<%= _Expense.getExpenseItemId() %>
		</div>
		<div id="amount" >
			amount:<%= _Expense.getAmount() %>
		</div>
		<div id="description" >
			description:<%= _Expense.getDescription() %>
		</div>
		<div id="payMethod" >
			payMethod:<%= _Expense.getPayMethod() %>
		</div>
		<div id="notExpense" >
			notExpense:<%= _Expense.getNotExpense() %>
		</div>
		<div id="reference" >
			reference:<%= _Expense.getReference() %>
		</div>
		<div id="dateExpense" >
			dateExpense:<%= _Expense.getDateExpense() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _Expense.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _Expense.getTimeUpdated() %>
		</div>
		<div>
		<a id="expenseDeleteButton" href="javascript:deleteThis('/expenseAction.html',<%= _Expense.getId()%>,'expenseFrame<%=_Expense.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="expenseForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="expenseFormAdd" method="POST" action="/expenseAction.html" id="expenseFormAdd">

	<div id="expenseForm_expenseItemId_field" class="formFieldFrame">
    <div id="expenseForm_expenseItemId_label" class="formLabel" >Expense Item Id </div>
    <div id="expenseForm_expenseItemId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="expenseItemId" id="_ffd_expenseItemId">
        <option value="" >- Please Select -</option>
<%
	List _listExpenseItem_expenseItemId = ExpenseItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listExpenseItem_expenseItemId.iterator(); iter.hasNext();){
		ExpenseItem _obj = (ExpenseItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_expense_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="expenseForm_amount_field">
    <div id="expenseForm_amount_label" class="formLabel" >Amount </div>
    <div id="expenseForm_amount_text" class="formFieldText" >       
        <input class="field" id="_ffd_amount" type="text" size="70" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="expenseForm_description_field">
    <div id="expenseForm_description_label" class="formLabel" >Description </div>
    <div id="expenseForm_description_text" class="formFieldText" >       
        <input class="field" id="_ffd_description" type="text" size="70" name="description" value="<%=WebUtil.display(_descriptionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="expenseForm_payMethod_field" class="formFieldFrame">
    <div id="expenseForm_payMethod_label" class="formLabel" >Pay Method </div>
    <div id="expenseForm_payMethod_dropdown" class="formFieldDropDown" >       
        <select class="field" name="payMethod" id="_ffd_payMethod">
        <option value="" >- Please Select -</option>
<%
	List dropMenusPayMethod = DropMenuUtil.getDropMenus("ExpensePayMethodOption");
	for(Iterator iterItems = dropMenusPayMethod.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pay_methodValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="expenseForm_notExpense_field">
    <div id="expenseForm_notExpense_label" class="formLabel" >Not Expense </div>
    <div id="expenseForm_notExpense_dropdown" class="formFieldDropDown" >       
        <select name="notExpense" id="_ffd_notExpense">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _not_expenseValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _not_expenseValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="expenseForm_reference_field">
    <div id="expenseForm_reference_label" class="formLabel" >Reference </div>
    <div id="expenseForm_reference_text" class="formFieldText" >       
        <input class="field" id="_ffd_reference" type="text" size="70" name="reference" value="<%=WebUtil.display(_referenceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="expenseForm_dateExpense_field">
    <div id="expenseForm_dateExpense_label" class="formLabel" >Date Expense </div>
    <div id="expenseForm_dateExpense_text" class="formFieldText" >       
        <input class="field" id="_ffd_dateExpense" type="text" size="70" name="dateExpense" value="<%=WebUtil.display(_date_expenseValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="expenseFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/expenseAction.html', 'expenseFormAdd', 'formSubmit_ajax', 'expense');">Submit</a>
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
	document.getElementById("resultDisplayExpense").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('expenseFormAddDis','/expenseAction.html', 'resultDisplayExpense', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="expenseFormAddDis" method="POST" action="/expenseAction.html" id="expenseFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Expense Item Id</div>
        <select class="field" name="expenseItemId" id="expenseItemId">
        <option value="" >- Please Select -</option>
<%
	_listExpenseItem_expenseItemId = ExpenseItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listExpenseItem_expenseItemId.iterator(); iter.hasNext();){
		ExpenseItem _obj = (ExpenseItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_expense_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getExpenseItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Amount</div>
        <input class="field" id="amount" type="text" size="70" name="amount" value="<%=WebUtil.display(_amountValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Description</div>
        <input class="field" id="description" type="text" size="70" name="description" value="<%=WebUtil.display(_descriptionValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Pay Method</div>
        <select class="field" name="payMethod" id="payMethod">
        <option value="" >- Please Select -</option>
<%
	dropMenusPayMethod = DropMenuUtil.getDropMenus("ExpensePayMethod");
	for(Iterator iterItems = dropMenusPayMethod.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _pay_methodValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Not Expense</div>
        <select name="notExpense" id="notExpense">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _not_expenseValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _not_expenseValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Reference</div>
        <input class="field" id="reference" type="text" size="70" name="reference" value="<%=WebUtil.display(_referenceValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Date Expense</div>
        <input class="field" id="dateExpense" type="text" size="70" name="dateExpense" value="<%=WebUtil.display(_date_expenseValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('expenseFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayExpense"></span>
<a href="javascript:backToXX('expenseFormAddDis','resultDisplayExpense')">show back</a><br>
<hr/>
