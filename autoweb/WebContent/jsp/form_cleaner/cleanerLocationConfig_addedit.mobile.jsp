<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

	// This is ugrly not matured change. Just added to load previously entered values and put back to the fields. 
	boolean isEdit = false;	
    Map reqParams = (Map) request.getAttribute("k_previous_request_params");
    if ( reqParams == null) {
        reqParams = (Map) request.getAttribute("k_reserved_params");
    } else {
        isEdit = true;
    }

	String command = request.getParameter("cmd");

    String idStr  = "0";
    CleanerLocationConfig _CleanerLocationConfig = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerLocationConfig = CleanerLocationConfigDS.getInstance().getById(id);
		if ( _CleanerLocationConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerLocationConfig = new CleanerLocationConfig();// CleanerLocationConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerLocationConfig == null) _CleanerLocationConfig = new CleanerLocationConfig();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_location_config_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _location_idValue= (reqParams.get("locationId")==null?WebUtil.display(_CleanerLocationConfig.getLocationId()):WebUtil.display((String)reqParams.get("locationId")));
    String _open_hour_weekdayValue= (reqParams.get("openHourWeekday")==null?WebUtil.display(_CleanerLocationConfig.getOpenHourWeekday()):WebUtil.display((String)reqParams.get("openHourWeekday")));
    String _close_hour_weekdayValue= (reqParams.get("closeHourWeekday")==null?WebUtil.display(_CleanerLocationConfig.getCloseHourWeekday()):WebUtil.display((String)reqParams.get("closeHourWeekday")));
    String _open_hour_satValue= (reqParams.get("openHourSat")==null?WebUtil.display(_CleanerLocationConfig.getOpenHourSat()):WebUtil.display((String)reqParams.get("openHourSat")));
    String _close_hour_satValue= (reqParams.get("closeHourSat")==null?WebUtil.display(_CleanerLocationConfig.getCloseHourSat()):WebUtil.display((String)reqParams.get("closeHourSat")));
    String _open_hour_sunValue= (reqParams.get("openHourSun")==null?WebUtil.display(_CleanerLocationConfig.getOpenHourSun()):WebUtil.display((String)reqParams.get("openHourSun")));
    String _close_hour_sunValue= (reqParams.get("closeHourSun")==null?WebUtil.display(_CleanerLocationConfig.getCloseHourSun()):WebUtil.display((String)reqParams.get("closeHourSun")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_CleanerLocationConfig.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_CleanerLocationConfig.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "cleaner_location_config_" + System.nanoTime();
%> 

<br>
<div id="cleanerLocationConfigForm" class="formFrame">
<div id="cleanerLocationConfigFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerLocationConfigForm_Form" method="POST" action="/cleanerLocationConfigAction.html" id="cleanerLocationConfigForm_Form">





	<div id="cleanerLocationConfigForm_locationId_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_locationId_label" class="formLabel" >Location Id </div>
    <div id="cleanerLocationConfigForm_locationId_text" class="formFieldText" >       
        <input id="locationId" class="field" type="text" size="70" name="locationId" value="<%=WebUtil.display(_location_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_openHourWeekday_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_openHourWeekday_label" class="formLabel" >Open Hour Weekday </div>
    <div id="cleanerLocationConfigForm_openHourWeekday_text" class="formFieldText" >       
        <input id="openHourWeekday" class="field" type="text" size="70" name="openHourWeekday" value="<%=WebUtil.display(_open_hour_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_closeHourWeekday_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_closeHourWeekday_label" class="formLabel" >Close Hour Weekday </div>
    <div id="cleanerLocationConfigForm_closeHourWeekday_text" class="formFieldText" >       
        <input id="closeHourWeekday" class="field" type="text" size="70" name="closeHourWeekday" value="<%=WebUtil.display(_close_hour_weekdayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_openHourSat_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_openHourSat_label" class="formLabel" >Open Hour Sat </div>
    <div id="cleanerLocationConfigForm_openHourSat_text" class="formFieldText" >       
        <input id="openHourSat" class="field" type="text" size="70" name="openHourSat" value="<%=WebUtil.display(_open_hour_satValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_closeHourSat_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_closeHourSat_label" class="formLabel" >Close Hour Sat </div>
    <div id="cleanerLocationConfigForm_closeHourSat_text" class="formFieldText" >       
        <input id="closeHourSat" class="field" type="text" size="70" name="closeHourSat" value="<%=WebUtil.display(_close_hour_satValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_openHourSun_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_openHourSun_label" class="formLabel" >Open Hour Sun </div>
    <div id="cleanerLocationConfigForm_openHourSun_text" class="formFieldText" >       
        <input id="openHourSun" class="field" type="text" size="70" name="openHourSun" value="<%=WebUtil.display(_open_hour_sunValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerLocationConfigForm_closeHourSun_field" class="formFieldFrame">
    <div id="cleanerLocationConfigForm_closeHourSun_label" class="formLabel" >Close Hour Sun </div>
    <div id="cleanerLocationConfigForm_closeHourSun_text" class="formFieldText" >       
        <input id="closeHourSun" class="field" type="text" size="70" name="closeHourSun" value="<%=WebUtil.display(_close_hour_sunValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







<div class="submitFrame">

    <div id="cleanerLocationConfigForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerLocationConfigForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerLocationConfigForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerLocationConfigForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerLocationConfigForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerLocationConfigForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerLocationConfigForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="cleanerLocationConfigForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="cleanerLocationConfigForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerLocationConfig.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">
<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_cleaner_location_config_home.html">home</a> | <a href="/v_cleaner_location_config_home.html">home</a> | <a href="/v_cleaner_location_config_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerLocationConfig = new ArrayList();
	CleanerLocationConfigDS ds_CleanerLocationConfig = CleanerLocationConfigDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerLocationConfig = ds_CleanerLocationConfig.getAll();
	else		
    	list_CleanerLocationConfig = ds_CleanerLocationConfig.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerLocationConfig, numDisplayInPage, listPage);

	list_CleanerLocationConfig = PagingUtil.getPagedList(pagingInfo, list_CleanerLocationConfig);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Location Id </td> 
    <td class="columnTitle">  Open Hour Weekday </td> 
    <td class="columnTitle">  Close Hour Weekday </td> 
    <td class="columnTitle">  Open Hour Sat </td> 
    <td class="columnTitle">  Close Hour Sat </td> 
    <td class="columnTitle">  Open Hour Sun </td> 
    <td class="columnTitle">  Close Hour Sun </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerLocationConfig.iterator();iter.hasNext();) {
        CleanerLocationConfig o_CleanerLocationConfig = (CleanerLocationConfig) iter.next();
%>

<TR id="tableRow<%= o_CleanerLocationConfig.getId()%>">
    <td> <%= o_CleanerLocationConfig.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerLocationConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerLocationConfig.getLocationId()  %> </td>
	<td> <%= o_CleanerLocationConfig.getOpenHourWeekday()  %> </td>
	<td> <%= o_CleanerLocationConfig.getCloseHourWeekday()  %> </td>
	<td> <%= o_CleanerLocationConfig.getOpenHourSat()  %> </td>
	<td> <%= o_CleanerLocationConfig.getCloseHourSat()  %> </td>
	<td> <%= o_CleanerLocationConfig.getOpenHourSun()  %> </td>
	<td> <%= o_CleanerLocationConfig.getCloseHourSun()  %> </td>
	<td> <%= o_CleanerLocationConfig.getTimeCreated()  %> </td>
	<td> <%= o_CleanerLocationConfig.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_location_config_form('<%=o_CleanerLocationConfig.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerLocationConfigAction.html?del=true&id=<%=o_CleanerLocationConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerLocationConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_location_config_form('<%=o_CleanerLocationConfig.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function open_edit_cleaner_location_config_form(target){
		location.href='/v_cleaner_location_config_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_location_config_form(target){
		javascript:sendFormAjaxSimple('/cleanerLocationConfigAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerLocationConfigAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_back_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_ext_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=ext<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
</script>
