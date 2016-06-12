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
	if (cancelPage == null) cancelPage = "site_object_home";

    String _site_urlValue= WebUtil.display((String)reqParams.get("siteUrl"));
    String _account_idValue= WebUtil.display((String)reqParams.get("accountId"));
    String _created_timeValue= WebUtil.display((String)reqParams.get("createdTime"));
    String _site_groupValue= WebUtil.display((String)reqParams.get("siteGroup"));
    String _registeredValue= WebUtil.display((String)reqParams.get("registered"));
    String _on_saleValue= WebUtil.display((String)reqParams.get("onSale"));
    String _super_admin_enableValue= WebUtil.display((String)reqParams.get("superAdminEnable"));
    String _site_register_enableValue= WebUtil.display((String)reqParams.get("siteRegisterEnable"));
    String _subdomain_enableValue= WebUtil.display((String)reqParams.get("subdomainEnable"));
    String _site_register_siteValue= WebUtil.display((String)reqParams.get("siteRegisterSite"));
    String _base_site_idValue= WebUtil.display((String)reqParams.get("baseSiteId"));
    String _subsiteValue= WebUtil.display((String)reqParams.get("subsite"));
    String _disabledValue= WebUtil.display((String)reqParams.get("disabled"));
%> 

<a href="/v_site_object_home.html"> Site Home </a>
<%
	
	List list = null;
	list = SiteDS.getInstance().getAll();

%>

<div id="siteObjectList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		Site _Site = (Site) iter.next();	
%>

	<div id="siteObjectFrame<%=_Site.getId() %>" >

		<div id="siteUrl" >
			siteUrl:<%= _Site.getSiteUrl() %>
		</div>
		<div id="accountId" >
			accountId:<%= _Site.getAccountId() %>
		</div>
		<div id="createdTime" >
			createdTime:<%= _Site.getCreatedTime() %>
		</div>
		<div id="siteGroup" >
			siteGroup:<%= _Site.getSiteGroup() %>
		</div>
		<div id="registered" >
			registered:<%= _Site.getRegistered() %>
		</div>
		<div id="onSale" >
			onSale:<%= _Site.getOnSale() %>
		</div>
		<div id="superAdminEnable" >
			superAdminEnable:<%= _Site.getSuperAdminEnable() %>
		</div>
		<div id="siteRegisterEnable" >
			siteRegisterEnable:<%= _Site.getSiteRegisterEnable() %>
		</div>
		<div id="subdomainEnable" >
			subdomainEnable:<%= _Site.getSubdomainEnable() %>
		</div>
		<div id="siteRegisterSite" >
			siteRegisterSite:<%= _Site.getSiteRegisterSite() %>
		</div>
		<div id="baseSiteId" >
			baseSiteId:<%= _Site.getBaseSiteId() %>
		</div>
		<div id="subsite" >
			subsite:<%= _Site.getSubsite() %>
		</div>
		<div id="disabled" >
			disabled:<%= _Site.getDisabled() %>
		</div>
		<div>
		<a id="siteObjectDeleteButton" href="javascript:deleteThis('/siteObjectAction.html',<%= _Site.getId()%>,'siteObjectFrame<%=_Site.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="siteObjectForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteObjectFormAdd" method="POST" action="/siteObjectAction.html" id="siteObjectFormAdd">

	<div id="siteObjectForm_siteUrl_field">
    <div id="siteObjectForm_siteUrl_label" class="formLabel" >Site Url </div>
    <div id="siteObjectForm_siteUrl_text" class="formFieldText" >       
        <input class="field" id="_ffd_siteUrl" type="text" size="70" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_accountId_field">
    <div id="siteObjectForm_accountId_label" class="formLabel" >Account Id </div>
    <div id="siteObjectForm_accountId_text" class="formFieldText" >       
        <input class="field" id="_ffd_accountId" type="text" size="70" name="accountId" value="<%=WebUtil.display(_account_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_siteGroup_field">
    <div id="siteObjectForm_siteGroup_label" class="formLabel" >Site Group </div>
    <div id="siteObjectForm_siteGroup_text" class="formFieldText" >       
        <input class="field" id="_ffd_siteGroup" type="text" size="70" name="siteGroup" value="<%=WebUtil.display(_site_groupValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_registered_field">
    <div id="siteObjectForm_registered_label" class="formLabel" >Registered </div>
    <div id="siteObjectForm_registered_dropdown" class="formFieldDropDown" >       
        <select name="registered" id="_ffd_registered">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _registeredValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _registeredValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_onSale_field">
    <div id="siteObjectForm_onSale_label" class="formLabel" >On Sale </div>
    <div id="siteObjectForm_onSale_dropdown" class="formFieldDropDown" >       
        <select name="onSale" id="_ffd_onSale">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _on_saleValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _on_saleValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_superAdminEnable_field">
    <div id="siteObjectForm_superAdminEnable_label" class="formLabel" >Super Admin Enable </div>
    <div id="siteObjectForm_superAdminEnable_dropdown" class="formFieldDropDown" >       
        <select name="superAdminEnable" id="_ffd_superAdminEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _super_admin_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _super_admin_enableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_siteRegisterEnable_field">
    <div id="siteObjectForm_siteRegisterEnable_label" class="formLabel" >Site Register Enable </div>
    <div id="siteObjectForm_siteRegisterEnable_dropdown" class="formFieldDropDown" >       
        <select name="siteRegisterEnable" id="_ffd_siteRegisterEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _site_register_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _site_register_enableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_subdomainEnable_field">
    <div id="siteObjectForm_subdomainEnable_label" class="formLabel" >Subdomain Enable </div>
    <div id="siteObjectForm_subdomainEnable_dropdown" class="formFieldDropDown" >       
        <select name="subdomainEnable" id="_ffd_subdomainEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subdomain_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subdomain_enableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_siteRegisterSite_field">
    <div id="siteObjectForm_siteRegisterSite_label" class="formLabel" >Site Register Site </div>
    <div id="siteObjectForm_siteRegisterSite_text" class="formFieldText" >       
        <input class="field" id="_ffd_siteRegisterSite" type="text" size="70" name="siteRegisterSite" value="<%=WebUtil.display(_site_register_siteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_baseSiteId_field">
    <div id="siteObjectForm_baseSiteId_label" class="formLabel" >Base Site Id </div>
    <div id="siteObjectForm_baseSiteId_text" class="formFieldText" >       
        <input class="field" id="_ffd_baseSiteId" type="text" size="70" name="baseSiteId" value="<%=WebUtil.display(_base_site_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_subsite_field">
    <div id="siteObjectForm_subsite_label" class="formLabel" >Subsite </div>
    <div id="siteObjectForm_subsite_dropdown" class="formFieldDropDown" >       
        <select name="subsite" id="_ffd_subsite">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subsiteValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subsiteValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="siteObjectForm_disabled_field">
    <div id="siteObjectForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="siteObjectForm_disabled_text" class="formFieldText" >       
        <input class="field" id="_ffd_disabled" type="text" size="70" name="disabled" value="<%=WebUtil.display(_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="siteObjectFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/siteObjectAction.html', 'siteObjectFormAdd', 'formSubmit_ajax', 'siteObject');">Submit</a>
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
	document.getElementById("resultDisplaySite").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('siteObjectFormAddDis','/siteObjectAction.html', 'resultDisplaySite', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="siteObjectFormAddDis" method="POST" action="/siteObjectAction.html" id="siteObjectFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Site Url</div>
        <input class="field" id="siteUrl" type="text" size="70" name="siteUrl" value="<%=WebUtil.display(_site_urlValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Account Id</div>
        <input class="field" id="accountId" type="text" size="70" name="accountId" value="<%=WebUtil.display(_account_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Site Group</div>
        <input class="field" id="siteGroup" type="text" size="70" name="siteGroup" value="<%=WebUtil.display(_site_groupValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Registered</div>
        <select name="registered" id="registered">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _registeredValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _registeredValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> On Sale</div>
        <select name="onSale" id="onSale">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _on_saleValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _on_saleValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Super Admin Enable</div>
        <select name="superAdminEnable" id="superAdminEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _super_admin_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _super_admin_enableValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Site Register Enable</div>
        <select name="siteRegisterEnable" id="siteRegisterEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _site_register_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _site_register_enableValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Subdomain Enable</div>
        <select name="subdomainEnable" id="subdomainEnable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subdomain_enableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subdomain_enableValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Site Register Site</div>
        <input class="field" id="siteRegisterSite" type="text" size="70" name="siteRegisterSite" value="<%=WebUtil.display(_site_register_siteValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Base Site Id</div>
        <input class="field" id="baseSiteId" type="text" size="70" name="baseSiteId" value="<%=WebUtil.display(_base_site_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Subsite</div>
        <select name="subsite" id="subsite">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _subsiteValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _subsiteValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disabled</div>
        <input class="field" id="disabled" type="text" size="70" name="disabled" value="<%=WebUtil.display(_disabledValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('siteObjectFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplaySite"></span>
<a href="javascript:backToXX('siteObjectFormAddDis','resultDisplaySite')">show back</a><br>
<hr/>
