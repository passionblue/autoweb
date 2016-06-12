<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
/* 
Template last modification history:


Source Generated: Sun Mar 15 01:49:44 EDT 2015
*/

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
    GenMain _GenMain = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_GenMain = GenMainDS.getInstance().getById(id);
		if ( _GenMain == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _GenMain = new GenMain();// GenMainDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _GenMain == null) _GenMain = new GenMain();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_main_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _activeValue= (reqParams.get("active")==null?WebUtil.display(_GenMain.getActive()):WebUtil.display((String)reqParams.get("active")));
    String _valueValue= (reqParams.get("value")==null?WebUtil.display(_GenMain.getValue()):WebUtil.display((String)reqParams.get("value")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_GenMain.getData()):WebUtil.display((String)reqParams.get("data")));
    String _requiredValue= (reqParams.get("required")==null?WebUtil.display(_GenMain.getRequired()):WebUtil.display((String)reqParams.get("required")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_GenMain.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_GenMain.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "gen_main_" + System.nanoTime();
%> 

<br>
<div id="genMainForm" class="formFrame">
<div id="genMainFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genMainForm_Form" method="POST" action="/genMainAction.html" id="genMainForm_Form">





	<div id="genMainForm_active_field" class="formFieldFrame">
    <div id="genMainForm_active_label" class="formLabel" >Active </div>
    <div id="genMainForm_active_text" class="formFieldText" >       
        <input id="active" class="field" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genMainForm_value_field" class="formFieldFrame">
    <div id="genMainForm_value_label" class="formLabel" >Value </div>
    <div id="genMainForm_value_text" class="formFieldText" >       
        <input id="value" class="field" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genMainForm_data_field" class="formFieldFrame">
    <div id="genMainForm_data_label" class="formLabel" >Data </div>
    <div id="genMainForm_data_text" class="formFieldText" >       
        <input id="data" class="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genMainForm_required_field" class="formFieldFrame">
    <div id="genMainForm_required_label" class="formLabel" >Required </div>
    <div id="genMainForm_required_text" class="formFieldText" >       
        <input id="required" class="field" type="text" size="70" name="required" value="<%=WebUtil.display(_requiredValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="genMainForm_timeUpdated_field" class="formFieldFrame">
    <div id="genMainForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="genMainForm_timeUpdated_text" class="formFieldText" >       
        <input id="timeUpdated" class="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="genMainForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.genMainForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="genMainForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="genMainForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="genMainForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="genMainForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="genMainForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenMain.getId()%>">

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
<a href="/v_gen_main_home.html">home</a> | <a href="/v_gen_main_home.html">home</a> | <a href="/v_gen_main_home.html">home</a>
<br/>
<br/>



<%
	List list_GenMain = new ArrayList();
	GenMainDS ds_GenMain = GenMainDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_GenMain = ds_GenMain.getAll();
	else		
    	list_GenMain = ds_GenMain.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_GenMain, numDisplayInPage, listPage);

	list_GenMain = PagingUtil.getPagedList(pagingInfo, list_GenMain);
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

    <td class="columnTitle">  Active </td> 
    <td class="columnTitle">  Value </td> 
    <td class="columnTitle">  Data </td> 
    <td class="columnTitle">  Required </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_GenMain.iterator();iter.hasNext();) {
        GenMain o_GenMain = (GenMain) iter.next();
%>

<TR id="tableRow<%= o_GenMain.getId()%>">
    <td> <%= o_GenMain.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_GenMain.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_GenMain.getActive()  %> </td>
	<td> <%= o_GenMain.getValue()  %> </td>
	<td> <%= o_GenMain.getData()  %> </td>
	<td> <%= o_GenMain.getRequired()  %> </td>
	<td> <%= o_GenMain.getTimeCreated()  %> </td>
	<td> <%= o_GenMain.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_gen_main_form('<%=o_GenMain.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/genMainAction.html?del=true&id=<%=o_GenMain.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_GenMain.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_gen_main_form('<%=o_GenMain.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_gen_main_form(target){
		location.href='/v_gen_main_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_gen_main_form(target){
		javascript:sendFormAjaxSimple('/genMainAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/genMainAction.html?ajxr=hb",
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
