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
	if (cancelPage == null) cancelPage = "page_content_config_home";

    String _content_idValue= WebUtil.display((String)reqParams.get("contentId"));
    String _content_typeValue= WebUtil.display((String)reqParams.get("contentType"));
    String _sort_typeValue= WebUtil.display((String)reqParams.get("sortType"));
    String _arrange_typeValue= WebUtil.display((String)reqParams.get("arrangeType"));
    String _page_cssValue= WebUtil.display((String)reqParams.get("pageCss"));
    String _page_scriptValue= WebUtil.display((String)reqParams.get("pageScript"));
    String _page_css_importsValue= WebUtil.display((String)reqParams.get("pageCssImports"));
    String _page_script_importsValue= WebUtil.display((String)reqParams.get("pageScriptImports"));
    String _hide_menuValue= WebUtil.display((String)reqParams.get("hideMenu"));
    String _hide_midValue= WebUtil.display((String)reqParams.get("hideMid"));
    String _hide_adValue= WebUtil.display((String)reqParams.get("hideAd"));
    String _style_idValue= WebUtil.display((String)reqParams.get("styleId"));
    String _content_style_set_idValue= WebUtil.display((String)reqParams.get("contentStyleSetId"));
    String _header_metaValue= WebUtil.display((String)reqParams.get("headerMeta"));
    String _header_linkValue= WebUtil.display((String)reqParams.get("headerLink"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_page_content_config_home.html"> PageContentConfig Home </a>
<%
	
	List list = null;
	list = PageContentConfigDS.getInstance().getBySiteId(site.getId());

%>

<div id="pageContentConfigList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		PageContentConfig _PageContentConfig = (PageContentConfig) iter.next();	
%>

	<div id="pageContentConfigFrame<%=_PageContentConfig.getId() %>" >

		<div id="contentId" >
			contentId:<%= _PageContentConfig.getContentId() %>
		</div>
		<div id="contentType" >
			contentType:<%= _PageContentConfig.getContentType() %>
		</div>
		<div id="sortType" >
			sortType:<%= _PageContentConfig.getSortType() %>
		</div>
		<div id="arrangeType" >
			arrangeType:<%= _PageContentConfig.getArrangeType() %>
		</div>
		<div id="pageCss" >
			pageCss:<%= _PageContentConfig.getPageCss() %>
		</div>
		<div id="pageScript" >
			pageScript:<%= _PageContentConfig.getPageScript() %>
		</div>
		<div id="pageCssImports" >
			pageCssImports:<%= _PageContentConfig.getPageCssImports() %>
		</div>
		<div id="pageScriptImports" >
			pageScriptImports:<%= _PageContentConfig.getPageScriptImports() %>
		</div>
		<div id="hideMenu" >
			hideMenu:<%= _PageContentConfig.getHideMenu() %>
		</div>
		<div id="hideMid" >
			hideMid:<%= _PageContentConfig.getHideMid() %>
		</div>
		<div id="hideAd" >
			hideAd:<%= _PageContentConfig.getHideAd() %>
		</div>
		<div id="styleId" >
			styleId:<%= _PageContentConfig.getStyleId() %>
		</div>
		<div id="contentStyleSetId" >
			contentStyleSetId:<%= _PageContentConfig.getContentStyleSetId() %>
		</div>
		<div id="headerMeta" >
			headerMeta:<%= _PageContentConfig.getHeaderMeta() %>
		</div>
		<div id="headerLink" >
			headerLink:<%= _PageContentConfig.getHeaderLink() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _PageContentConfig.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _PageContentConfig.getTimeUpdated() %>
		</div>
		<div>
		<a id="pageContentConfigDeleteButton" href="javascript:deleteThis('/pageContentConfigAction.html',<%= _PageContentConfig.getId()%>,'pageContentConfigFrame<%=_PageContentConfig.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="pageContentConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageContentConfigFormAdd" method="POST" action="/pageContentConfigAction.html" id="pageContentConfigFormAdd">

    <INPUT TYPE="HIDDEN" id="_ffd_contentId" NAME="contentId" value="<%=WebUtil.display(_content_idValue)%>" />

	<div id="pageContentConfigForm_contentType_field">
    <div id="pageContentConfigForm_contentType_label" class="formLabel" >Content Type </div>
    <div id="pageContentConfigForm_contentType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="contentType" id="_ffd_contentType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _content_typeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _content_typeValue)%>>Default</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _content_typeValue)%>>Blog</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _content_typeValue)%>>Forum Content</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _content_typeValue)%>>TBD</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _content_typeValue)%>>TBD</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_sortType_field">
    <div id="pageContentConfigForm_sortType_label" class="formLabel" >Sort Type </div>
    <div id="pageContentConfigForm_sortType_text" class="formFieldText" >       
        <input class="field" id="_ffd_sortType" type="text" size="70" name="sortType" value="<%=WebUtil.display(_sort_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_arrangeType_field">
    <div id="pageContentConfigForm_arrangeType_label" class="formLabel" >Arrange Type </div>
    <div id="pageContentConfigForm_arrangeType_text" class="formFieldText" >       
        <input class="field" id="_ffd_arrangeType" type="text" size="70" name="arrangeType" value="<%=WebUtil.display(_arrange_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_pageCss_field">
    <div id="pageContentConfigForm_pageCss_label" class="formLabel" >Page Css </div>
    <div id="pageContentConfigForm_pageCss_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_pageCss" class="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_pageScript_field">
    <div id="pageContentConfigForm_pageScript_label" class="formLabel" >Page Script </div>
    <div id="pageContentConfigForm_pageScript_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="_ffd_pageScript" class="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_pageCssImports_field">
    <div id="pageContentConfigForm_pageCssImports_label" class="formLabel" >Page Css Imports </div>
    <div id="pageContentConfigForm_pageCssImports_text" class="formFieldText" >       
        <input class="field" id="_ffd_pageCssImports" type="text" size="70" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_pageScriptImports_field">
    <div id="pageContentConfigForm_pageScriptImports_label" class="formLabel" >Page Script Imports </div>
    <div id="pageContentConfigForm_pageScriptImports_text" class="formFieldText" >       
        <input class="field" id="_ffd_pageScriptImports" type="text" size="70" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_hideMenu_field">
    <div id="pageContentConfigForm_hideMenu_label" class="formLabel" >Hide Menu </div>
    <div id="pageContentConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
        <select name="hideMenu" id="_ffd_hideMenu">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_menuValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_menuValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_hideMid_field">
    <div id="pageContentConfigForm_hideMid_label" class="formLabel" >Hide Mid </div>
    <div id="pageContentConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
        <select name="hideMid" id="_ffd_hideMid">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_midValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_midValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_hideAd_field">
    <div id="pageContentConfigForm_hideAd_label" class="formLabel" >Hide Ad </div>
    <div id="pageContentConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
        <select name="hideAd" id="_ffd_hideAd">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_adValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_adValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_styleId_field">
    <div id="pageContentConfigForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="pageContentConfigForm_styleId_text" class="formFieldText" >       
        <input class="field" id="_ffd_styleId" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_contentStyleSetId_field" class="formFieldFrame">
    <div id="pageContentConfigForm_contentStyleSetId_label" class="formLabel" >Content Style Set Id </div>
    <div id="pageContentConfigForm_contentStyleSetId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="contentStyleSetId" id="_ffd_contentStyleSetId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleSetContent_contentStyleSetId = StyleSetContentDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleSetContent_contentStyleSetId.iterator(); iter.hasNext();){
		StyleSetContent _obj = (StyleSetContent) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_style_set_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_headerMeta_field">
    <div id="pageContentConfigForm_headerMeta_label" class="formLabel" >Header Meta </div>
    <div id="pageContentConfigForm_headerMeta_text" class="formFieldText" >       
        <input class="field" id="_ffd_headerMeta" type="text" size="70" name="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="pageContentConfigForm_headerLink_field">
    <div id="pageContentConfigForm_headerLink_label" class="formLabel" >Header Link </div>
    <div id="pageContentConfigForm_headerLink_text" class="formFieldText" >       
        <input class="field" id="_ffd_headerLink" type="text" size="70" name="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="pageContentConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/pageContentConfigAction.html', 'pageContentConfigFormAdd', 'formSubmit_ajax', 'pageContentConfig');">Submit</a>
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
	document.getElementById("resultDisplayPageContentConfig").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('pageContentConfigFormAddDis','/pageContentConfigAction.html', 'resultDisplayPageContentConfig', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="pageContentConfigFormAddDis" method="POST" action="/pageContentConfigAction.html" id="pageContentConfigFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


    	<INPUT TYPE="HIDDEN" id="contentId" NAME="contentId" value="<%=WebUtil.display(_content_idValue)%>" />
		<br/>
		 <div class="ajaxFormLabel"> Content Type</div>
        <select class="field" name="contentType" id="contentType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _content_typeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _content_typeValue)%>>Default</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _content_typeValue)%>>Blog</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _content_typeValue)%>>Forum Content</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _content_typeValue)%>>TBD</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _content_typeValue)%>>TBD</option>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Sort Type</div>
        <input class="field" id="sortType" type="text" size="70" name="sortType" value="<%=WebUtil.display(_sort_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Arrange Type</div>
        <input class="field" id="arrangeType" type="text" size="70" name="arrangeType" value="<%=WebUtil.display(_arrange_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Page Css</div>
		<TEXTAREA id="pageCss" class="field" NAME="pageCss" COLS="50" ROWS="8" ><%=WebUtil.display(_page_cssValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Page Script</div>
		<TEXTAREA id="pageScript" class="field" NAME="pageScript" COLS="50" ROWS="8" ><%=WebUtil.display(_page_scriptValue)%></TEXTAREA><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Page Css Imports</div>
        <input class="field" id="pageCssImports" type="text" size="70" name="pageCssImports" value="<%=WebUtil.display(_page_css_importsValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Page Script Imports</div>
        <input class="field" id="pageScriptImports" type="text" size="70" name="pageScriptImports" value="<%=WebUtil.display(_page_script_importsValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Hide Menu</div>
        <select name="hideMenu" id="hideMenu">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_menuValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_menuValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Hide Mid</div>
        <select name="hideMid" id="hideMid">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_midValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_midValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Hide Ad</div>
        <select name="hideAd" id="hideAd">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hide_adValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hide_adValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Style Id</div>
        <input class="field" id="styleId" type="text" size="70" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Content Style Set Id</div>
        <select class="field" name="contentStyleSetId" id="contentStyleSetId">
        <option value="" >- Please Select -</option>
<%
	_listStyleSetContent_contentStyleSetId = StyleSetContentDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleSetContent_contentStyleSetId.iterator(); iter.hasNext();){
		StyleSetContent _obj = (StyleSetContent) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_style_set_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Header Meta</div>
        <input class="field" id="headerMeta" type="text" size="70" name="headerMeta" value="<%=WebUtil.display(_header_metaValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Header Link</div>
        <input class="field" id="headerLink" type="text" size="70" name="headerLink" value="<%=WebUtil.display(_header_linkValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('pageContentConfigFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayPageContentConfig"></span>
<a href="javascript:backToXX('pageContentConfigFormAddDis','resultDisplayPageContentConfig')">show back</a><br>
<hr/>
