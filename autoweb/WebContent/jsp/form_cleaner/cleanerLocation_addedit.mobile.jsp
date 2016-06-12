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
    CleanerLocation _CleanerLocation = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerLocation = CleanerLocationDS.getInstance().getById(id);
		if ( _CleanerLocation == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerLocation = new CleanerLocation();// CleanerLocationDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerLocation == null) _CleanerLocation = new CleanerLocation();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_location_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _addressValue= (reqParams.get("address")==null?WebUtil.display(_CleanerLocation.getAddress()):WebUtil.display((String)reqParams.get("address")));
    String _city_zipValue= (reqParams.get("cityZip")==null?WebUtil.display(_CleanerLocation.getCityZip()):WebUtil.display((String)reqParams.get("cityZip")));
    String _phoneValue= (reqParams.get("phone")==null?WebUtil.display(_CleanerLocation.getPhone()):WebUtil.display((String)reqParams.get("phone")));
    String _manager_idValue= (reqParams.get("managerId")==null?WebUtil.display(_CleanerLocation.getManagerId()):WebUtil.display((String)reqParams.get("managerId")));

    String pagestamp = "cleaner_location_" + System.nanoTime();
%> 

<br>
<div id="cleanerLocationForm" class="formFrame">
<div id="cleanerLocationFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerLocationForm_Form" method="POST" action="/cleanerLocationAction.html" id="cleanerLocationForm_Form">

#macro_jspelements( [{displayInUpper=ADDRESS, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=ADDRESS, type=String, isDropDown=false, title=address, isDropDownAutoLoad=false, isAjaxForm=true, name=Address, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=address, display=Address, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=address, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=String, javaName=address, name2=address}, {displayInUpper=CITY ZIP, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=CITY_ZIP, type=String, isDropDown=false, title=city_zip, isDropDownAutoLoad=false, isAjaxForm=true, name=CityZip, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=city_zip, display=City Zip, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=city zip, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=String, javaName=cityZip, name2=cityZip}, {displayInUpper=PHONE, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=PHONE, type=String, isDropDown=false, title=phone, isDropDownAutoLoad=false, isAjaxForm=true, name=Phone, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=phone, display=Phone, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=phone, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=String, javaName=phone, name2=phone}, {displayInUpper=MANAGER ID, isFormIgnore=false, isRadio=false, isBoolean=false, isPassword=false, isImmutable=false, titleInUpper=MANAGER_ID, type=Long, isDropDown=false, title=manager_id, isDropDownAutoLoad=false, isAjaxForm=true, name=ManagerId, isNoPersist=false, isWyswygTextArea=false, isId=false, isAutosetUpdateDate=false, isCheckbox=false, isAutosetDate=false, prop=manager_id, display=Manager Id, isTextArea=false, isDsShortField=false, isAutosetCreateDate=false, isDate=false, displayInLower=manager id, size=70, isHidden=false, isDbField=true, isRequired=false, javaType=long, javaName=managerId, name2=managerId}] )

<div class="submitFrame">

    <div id="cleanerLocationForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerLocationForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerLocationForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerLocationForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerLocationForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerLocationForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerLocationForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerLocation.getId()%>">

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
<a href="/v_cleaner_location_home.html">home</a> | <a href="/v_cleaner_location_home.html">home</a> | <a href="/v_cleaner_location_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerLocation = new ArrayList();
	CleanerLocationDS ds_CleanerLocation = CleanerLocationDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerLocation = ds_CleanerLocation.getAll();
	else		
    	list_CleanerLocation = ds_CleanerLocation.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerLocation, numDisplayInPage, listPage);

	list_CleanerLocation = PagingUtil.getPagedList(pagingInfo, list_CleanerLocation);
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

    <td class="columnTitle">  Address </td> 
    <td class="columnTitle">  City Zip </td> 
    <td class="columnTitle">  Phone </td> 
    <td class="columnTitle">  Manager Id </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerLocation.iterator();iter.hasNext();) {
        CleanerLocation o_CleanerLocation = (CleanerLocation) iter.next();
%>

<TR id="tableRow<%= o_CleanerLocation.getId()%>">
    <td> <%= o_CleanerLocation.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerLocation.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerLocation.getAddress()  %> </td>
	<td> <%= o_CleanerLocation.getCityZip()  %> </td>
	<td> <%= o_CleanerLocation.getPhone()  %> </td>
	<td> <%= o_CleanerLocation.getManagerId()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_location_form('<%=o_CleanerLocation.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerLocationAction.html?del=true&id=<%=o_CleanerLocation.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerLocation.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_location_form('<%=o_CleanerLocation.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_location_form(target){
		location.href='/v_cleaner_location_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_location_form(target){
		javascript:sendFormAjaxSimple('/cleanerLocationAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerLocationAction.html?ajxr=hb",
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

</script>
