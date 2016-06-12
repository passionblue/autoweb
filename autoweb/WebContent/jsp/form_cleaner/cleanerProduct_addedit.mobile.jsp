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
    CleanerProduct _CleanerProduct = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_CleanerProduct = CleanerProductDS.getInstance().getById(id);
		if ( _CleanerProduct == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _CleanerProduct = new CleanerProduct();// CleanerProductDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _CleanerProduct == null) _CleanerProduct = new CleanerProduct();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "cleaner_product_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _garment_type_idValue= (reqParams.get("garmentTypeId")==null?WebUtil.display(_CleanerProduct.getGarmentTypeId()):WebUtil.display((String)reqParams.get("garmentTypeId")));
    String _garment_service_idValue= (reqParams.get("garmentServiceId")==null?WebUtil.display(_CleanerProduct.getGarmentServiceId()):WebUtil.display((String)reqParams.get("garmentServiceId")));
    String _regular_priceValue= (reqParams.get("regularPrice")==null?WebUtil.display(_CleanerProduct.getRegularPrice()):WebUtil.display((String)reqParams.get("regularPrice")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_CleanerProduct.getNote()):WebUtil.display((String)reqParams.get("note")));

    String pagestamp = "cleaner_product_" + System.nanoTime();
%> 

<br>
<div id="cleanerProductForm" class="formFrame">
<div id="cleanerProductFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="cleanerProductForm_Form" method="POST" action="/cleanerProductAction.html" id="cleanerProductForm_Form">





	<div id="cleanerProductForm_garmentTypeId_field" class="formFieldFrame">
    <div id="cleanerProductForm_garmentTypeId_label" class="formLabel" >Garment Type Id </div>
    <div id="cleanerProductForm_garmentTypeId_text" class="formFieldText" >       
        <input id="garmentTypeId" class="field" type="text" size="70" name="garmentTypeId" value="<%=WebUtil.display(_garment_type_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerProductForm_garmentServiceId_field" class="formFieldFrame">
    <div id="cleanerProductForm_garmentServiceId_label" class="formLabel" >Garment Service Id </div>
    <div id="cleanerProductForm_garmentServiceId_text" class="formFieldText" >       
        <input id="garmentServiceId" class="field" type="text" size="70" name="garmentServiceId" value="<%=WebUtil.display(_garment_service_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerProductForm_regularPrice_field" class="formFieldFrame">
    <div id="cleanerProductForm_regularPrice_label" class="formLabel" >Regular Price </div>
    <div id="cleanerProductForm_regularPrice_text" class="formFieldText" >       
        <input id="regularPrice" class="field" type="text" size="70" name="regularPrice" value="<%=WebUtil.display(_regular_priceValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="cleanerProductForm_note_field" class="formFieldFrame">
    <div id="cleanerProductForm_note_label" class="formLabel" >Note </div>
    <div id="cleanerProductForm_note_text" class="formFieldText" >       
        <input id="note" class="field" type="text" size="70" name="note" value="<%=WebUtil.display(_noteValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="cleanerProductForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.cleanerProductForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="cleanerProductForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="cleanerProductForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="cleanerProductForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="cleanerProductForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="cleanerProductForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_CleanerProduct.getId()%>">

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
<a href="/v_cleaner_product_home.html">home</a> | <a href="/v_cleaner_product_home.html">home</a> | <a href="/v_cleaner_product_home.html">home</a>
<br/>
<br/>



<%
	List list_CleanerProduct = new ArrayList();
	CleanerProductDS ds_CleanerProduct = CleanerProductDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_CleanerProduct = ds_CleanerProduct.getAll();
	else		
    	list_CleanerProduct = ds_CleanerProduct.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_CleanerProduct, numDisplayInPage, listPage);

	list_CleanerProduct = PagingUtil.getPagedList(pagingInfo, list_CleanerProduct);
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

    <td class="columnTitle">  Garment Type Id </td> 
    <td class="columnTitle">  Garment Service Id </td> 
    <td class="columnTitle">  Regular Price </td> 
    <td class="columnTitle">  Note </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_CleanerProduct.iterator();iter.hasNext();) {
        CleanerProduct o_CleanerProduct = (CleanerProduct) iter.next();
%>

<TR id="tableRow<%= o_CleanerProduct.getId()%>">
    <td> <%= o_CleanerProduct.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_CleanerProduct.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_CleanerProduct.getGarmentTypeId()  %> </td>
	<td> <%= o_CleanerProduct.getGarmentServiceId()  %> </td>
	<td> <%= o_CleanerProduct.getRegularPrice()  %> </td>
	<td> <%= o_CleanerProduct.getNote()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_cleaner_product_form('<%=o_CleanerProduct.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/cleanerProductAction.html?del=true&id=<%=o_CleanerProduct.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_CleanerProduct.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_cleaner_product_form('<%=o_CleanerProduct.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_cleaner_product_form(target){
		location.href='/v_cleaner_product_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_cleaner_product_form(target){
		javascript:sendFormAjaxSimple('/cleanerProductAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/cleanerProductAction.html?ajxr=hb",
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
