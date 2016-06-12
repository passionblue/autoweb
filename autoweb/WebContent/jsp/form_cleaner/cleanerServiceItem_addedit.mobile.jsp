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
    CleanerServiceItem _CleanerServiceItem = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerServiceItem = CleanerServiceItemDS.getInstance().getById(id);
		if ( _CleanerServiceItem == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerServiceItem = new CleanerServiceItem();// CleanerServiceItemDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerServiceItem == null) _CleanerServiceItem = new CleanerServiceItem();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_service_item_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _service_idValue= (reqParams.get("serviceId")==null?WebUtil.display(_CleanerServiceItem.getServiceId()):WebUtil.display((String)reqParams.get("serviceId")));
    String _service_item_idValue= (reqParams.get("serviceItemId")==null?WebUtil.display(_CleanerServiceItem.getServiceItemId()):WebUtil.display((String)reqParams.get("serviceItemId")));
    String _item_typeValue= (reqParams.get("itemType")==null?WebUtil.display(_CleanerServiceItem.getItemType()):WebUtil.display((String)reqParams.get("itemType")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_CleanerServiceItem.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _image_pathValue= (reqParams.get("imagePath")==null?WebUtil.display(_CleanerServiceItem.getImagePath()):WebUtil.display((String)reqParams.get("imagePath")));
    String _image_path_localValue= (reqParams.get("imagePathLocal")==null?WebUtil.display(_CleanerServiceItem.getImagePathLocal()):WebUtil.display((String)reqParams.get("imagePathLocal")));
    String _base_priceValue= (reqParams.get("basePrice")==null?WebUtil.display(_CleanerServiceItem.getBasePrice()):WebUtil.display((String)reqParams.get("basePrice")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_CleanerServiceItem.getNote()):WebUtil.display((String)reqParams.get("note")));

    String pagestamp = "cleaner_service_item_" + System.nanoTime();
%> 

<br>
<div id="cleanerServiceItemForm" class="formFrame">
<div id="cleanerServiceItemFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerServiceItemForm_Form" method="POST" action="/cleanerServiceItemAction.html" id="cleanerServiceItemForm_Form">





	<div id="cleanerServiceItemForm_serviceId_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_serviceId_label" class="formLabel" >Service Id </div>
    <div id="cleanerServiceItemForm_serviceId_text" class="formFieldText" >       
        <input id="serviceId" class="field" type="text" size="70" name="serviceId" value="<%=WebUtil.display(_service_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_serviceItemId_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_serviceItemId_label" class="formLabel" >Service Item Id </div>
    <div id="cleanerServiceItemForm_serviceItemId_text" class="formFieldText" >       
        <input id="serviceItemId" class="field" type="text" size="70" name="serviceItemId" value="<%=WebUtil.display(_service_item_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_itemType_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_itemType_label" class="formLabel" >Item Type </div>
    <div id="cleanerServiceItemForm_itemType_text" class="formFieldText" >       
        <input id="itemType" class="field" type="text" size="70" name="itemType" value="<%=WebUtil.display(_item_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_title_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_title_label" class="formLabel" >Title </div>
    <div id="cleanerServiceItemForm_title_text" class="formFieldText" >       
        <input id="title" class="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_imagePath_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_imagePath_label" class="formLabel" >Image Path </div>
    <div id="cleanerServiceItemForm_imagePath_text" class="formFieldText" >       
        <input id="imagePath" class="field" type="text" size="70" name="imagePath" value="<%=WebUtil.display(_image_pathValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_imagePathLocal_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_imagePathLocal_label" class="formLabel" >Image Path Local </div>
    <div id="cleanerServiceItemForm_imagePathLocal_text" class="formFieldText" >       
        <input id="imagePathLocal" class="field" type="text" size="70" name="imagePathLocal" value="<%=WebUtil.display(_image_path_localValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_basePrice_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_basePrice_label" class="formLabel" >Base Price </div>
    <div id="cleanerServiceItemForm_basePrice_text" class="formFieldText" >       
        <input id="basePrice" class="field" type="text" size="70" name="basePrice" value="<%=WebUtil.display(_base_priceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerServiceItemForm_note_field" class="formFieldFrame">
    <div id="cleanerServiceItemForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerServiceItemForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="cleanerServiceItemForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerServiceItemForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerServiceItemForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerServiceItemForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerServiceItemForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerServiceItemForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerServiceItemForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="cleanerServiceItemForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="cleanerServiceItemForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerServiceItem.getId()%>">

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
<a href="/v_cleaner_service_item_home.html">home</a> | <a href="/v_cleaner_service_item_home.html">home</a> | <a href="/v_cleaner_service_item_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerServiceItem = new ArrayList();
	CleanerServiceItemDS ds_CleanerServiceItem = CleanerServiceItemDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerServiceItem = ds_CleanerServiceItem.getAll();
	else		
    	list_CleanerServiceItem = ds_CleanerServiceItem.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerServiceItem, numDisplayInPage, listPage);

	list_CleanerServiceItem = PagingUtil.getPagedList(pagingInfo, list_CleanerServiceItem);
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

    <td class="columnTitle">  Service Id </td> 
    <td class="columnTitle">  Service Item Id </td> 
    <td class="columnTitle">  Item Type </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Image Path </td> 
    <td class="columnTitle">  Image Path Local </td> 
    <td class="columnTitle">  Base Price </td> 
    <td class="columnTitle">  Note </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerServiceItem.iterator();iter.hasNext();) {
        CleanerServiceItem o_CleanerServiceItem = (CleanerServiceItem) iter.next();
%>

<TR id="tableRow<%= o_CleanerServiceItem.getId()%>">
    <td> <%= o_CleanerServiceItem.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerServiceItem.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerServiceItem.getServiceId()  %> </td>
	<td> <%= o_CleanerServiceItem.getServiceItemId()  %> </td>
	<td> <%= o_CleanerServiceItem.getItemType()  %> </td>
	<td> <%= o_CleanerServiceItem.getTitle()  %> </td>
	<td> <%= o_CleanerServiceItem.getImagePath()  %> </td>
	<td> <%= o_CleanerServiceItem.getImagePathLocal()  %> </td>
	<td> <%= o_CleanerServiceItem.getBasePrice()  %> </td>
	<td> <%= o_CleanerServiceItem.getNote()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_service_item_form('<%=o_CleanerServiceItem.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerServiceItemAction.html?del=true&id=<%=o_CleanerServiceItem.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerServiceItem.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_service_item_form('<%=o_CleanerServiceItem.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_service_item_form(target){
		location.href='/v_cleaner_service_item_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_service_item_form(target){
		javascript:sendFormAjaxSimple('/cleanerServiceItemAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerServiceItemAction.html?ajxr=hb",
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
