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
	if (cancelPage == null) cancelPage = "chur_income_home";

    String _yearValue= WebUtil.display((String)reqParams.get("year"));
    String _weekValue= WebUtil.display((String)reqParams.get("week"));
    String _chur_member_idValue= WebUtil.display((String)reqParams.get("churMemberId"));
    String _income_item_idValue= WebUtil.display((String)reqParams.get("incomeItemId"));
    String _ammountValue= WebUtil.display((String)reqParams.get("ammount"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
%> 

<a href="/v_chur_income_home.html"> ChurIncome Home </a>
<%
	
	List list = null;
	list = ChurIncomeDS.getInstance().getBySiteId(site.getId());

%>

<div id="churIncomeList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		ChurIncomeDataHolder _ChurIncome = (ChurIncomeDataHolder) iter.next();	
%>

	<div id="churIncomeFrame<%=_ChurIncome.getId() %>" >

		<div id="year" >
			year:<%= _ChurIncome.getYear() %>
		</div>
		<div id="week" >
			week:<%= _ChurIncome.getWeek() %>
		</div>
		<div id="churMemberId" >
			churMemberId:<%= _ChurIncome.getChurMemberId() %>
		</div>
		<div id="incomeItemId" >
			incomeItemId:<%= _ChurIncome.getIncomeItemId() %>
		</div>
		<div id="ammount" >
			ammount:<%= _ChurIncome.getAmmount() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _ChurIncome.getTimeCreated() %>
		</div>
		<div>
		<a id="churIncomeDeleteButton" href="javascript:deleteThis('/churIncomeAction.html',<%= _ChurIncome.getId()%>,'churIncomeFrame<%=_ChurIncome.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="churIncomeForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeFormAdd" method="POST" action="/churIncomeAction.html" id="churIncomeFormAdd">

	<div id="churIncomeForm_year_field">
    <div id="churIncomeForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeForm_year_dropdown" class="formFieldDropDown" >       
        <select class="field" name="year" id="_ffd_year">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _yearValue)%>>XX</option-->
        <option value="2012" <%=HtmlUtil.getOptionSelect("2012", _yearValue)%>>2012</option>
        <option value="2013" <%=HtmlUtil.getOptionSelect("2013", _yearValue)%>>2013</option>
        <option value="2014" <%=HtmlUtil.getOptionSelect("2014", _yearValue)%>>2014</option>
        <option value="2015" <%=HtmlUtil.getOptionSelect("2015", _yearValue)%>>2015</option>
        <option value="2016" <%=HtmlUtil.getOptionSelect("2016", _yearValue)%>>2016</option>
        <option value="2017" <%=HtmlUtil.getOptionSelect("2017", _yearValue)%>>2017</option>
        <option value="2018" <%=HtmlUtil.getOptionSelect("2018", _yearValue)%>>2018</option>
        <option value="2019" <%=HtmlUtil.getOptionSelect("2019", _yearValue)%>>2019</option>
        <option value="2020" <%=HtmlUtil.getOptionSelect("2020", _yearValue)%>>2020</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeForm_week_field">
    <div id="churIncomeForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeForm_week_text" class="formFieldText" >       
        <input class="field" id="_ffd_week" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeForm_churMemberId_field" class="formFieldFrame">
    <div id="churIncomeForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeForm_churMemberId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="churMemberId" id="_ffd_churMemberId">
        <option value="" >- Please Select -</option>
<%
	List _listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_chur_member_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeForm_incomeItemId_field" class="formFieldFrame">
    <div id="churIncomeForm_incomeItemId_label" class="formLabel" >Income Item Id </div>
    <div id="churIncomeForm_incomeItemId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="incomeItemId" id="_ffd_incomeItemId">
        <option value="" >- Please Select -</option>
<%
	List _listChurIncomeItem_incomeItemId = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurIncomeItem_incomeItemId.iterator(); iter.hasNext();){
		ChurIncomeItem _obj = (ChurIncomeItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_income_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getIncomeItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="churIncomeForm_ammount_field">
    <div id="churIncomeForm_ammount_label" class="formLabel" >Ammount </div>
    <div id="churIncomeForm_ammount_text" class="formFieldText" >       
        <input class="field" id="_ffd_ammount" type="text" size="70" name="ammount" value="<%=WebUtil.display(_ammountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/churIncomeAction.html', 'churIncomeFormAdd', 'formSubmit_ajax', 'churIncome');">Submit</a>
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
	document.getElementById("resultDisplayChurIncome").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('churIncomeFormAddDis','/churIncomeAction.html', 'resultDisplayChurIncome', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="churIncomeFormAddDis" method="POST" action="/churIncomeAction.html" id="churIncomeFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Year</div>
        <select class="field" name="year" id="year">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _yearValue)%>>XX</option-->
        <option value="2012" <%=HtmlUtil.getOptionSelect("2012", _yearValue)%>>2012</option>
        <option value="2013" <%=HtmlUtil.getOptionSelect("2013", _yearValue)%>>2013</option>
        <option value="2014" <%=HtmlUtil.getOptionSelect("2014", _yearValue)%>>2014</option>
        <option value="2015" <%=HtmlUtil.getOptionSelect("2015", _yearValue)%>>2015</option>
        <option value="2016" <%=HtmlUtil.getOptionSelect("2016", _yearValue)%>>2016</option>
        <option value="2017" <%=HtmlUtil.getOptionSelect("2017", _yearValue)%>>2017</option>
        <option value="2018" <%=HtmlUtil.getOptionSelect("2018", _yearValue)%>>2018</option>
        <option value="2019" <%=HtmlUtil.getOptionSelect("2019", _yearValue)%>>2019</option>
        <option value="2020" <%=HtmlUtil.getOptionSelect("2020", _yearValue)%>>2020</option>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Week</div>
        <input class="field" id="week" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Chur Member Id</div>
        <select class="field" name="churMemberId" id="churMemberId">
        <option value="" >- Please Select -</option>
<%
	_listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_chur_member_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Income Item Id</div>
        <select class="field" name="incomeItemId" id="incomeItemId">
        <option value="" >- Please Select -</option>
<%
	_listChurIncomeItem_incomeItemId = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurIncomeItem_incomeItemId.iterator(); iter.hasNext();){
		ChurIncomeItem _obj = (ChurIncomeItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_income_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getIncomeItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Ammount</div>
        <input class="field" id="ammount" type="text" size="70" name="ammount" value="<%=WebUtil.display(_ammountValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('churIncomeFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayChurIncome"></span>
<a href="javascript:backToXX('churIncomeFormAddDis','resultDisplayChurIncome')">show back</a><br>
<hr/>
