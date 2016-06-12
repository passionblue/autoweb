<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

    Map reqParams = (Map) request.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ThemeStyles _ThemeStyles = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ThemeStyles = ThemeStylesDS.getInstance().getById(id);
		if ( _ThemeStyles == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ThemeStyles = new ThemeStyles();// ThemeStylesDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "theme_styles_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _body_widthValue= (reqParams.get("bodyWidth")==null?WebUtil.display(_ThemeStyles.getBodyWidth()):WebUtil.display((String)reqParams.get("bodyWidth")));
    String _body_alignValue= (reqParams.get("bodyAlign")==null?WebUtil.display(_ThemeStyles.getBodyAlign()):WebUtil.display((String)reqParams.get("bodyAlign")));
    String _body_backgroundValue= (reqParams.get("bodyBackground")==null?WebUtil.display(_ThemeStyles.getBodyBackground()):WebUtil.display((String)reqParams.get("bodyBackground")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ThemeStyles.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ThemeStyles.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "theme_styles_" + System.nanoTime();
%> 

<br>
<div id="themeStylesForm" class="formFrame">
<div id="themeStylesFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="themeStylesForm_Form" method="POST" action="/themeStylesAction.html" id="themeStylesForm_Form">





	<div id="themeStylesForm_bodyWidth_field" class="formFieldFrame">
    <div id="themeStylesForm_bodyWidth_label" class="formLabel" >Body Width </div>
    <div id="themeStylesForm_bodyWidth_text" class="formFieldText" >       
        <input id="bodyWidth" class="field" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


  	<div id="themeStylesForm_bodyAlign_field" class="formFieldFrame">
    <div id="themeStylesForm_bodyAlign_label" class="formLabel" >Body Align </div>
    <div id="themeStylesForm_bodyAlign_dropdown" class="formFieldDropDown" >       
        <select class="field" name="bodyAlign" id="bodyAlign">
        <option value="" >- Please Select -</option>
<%
	List dropMenusBodyAlign = DropMenuUtil.getDropMenus("BodyAlignment");
	for(Iterator iterItems = dropMenusBodyAlign.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _body_alignValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeStylesForm_bodyBackground_field" class="formFieldFrame">
    <div id="themeStylesForm_bodyBackground_label" class="formLabel" >Body Background </div>
    <div id="themeStylesForm_bodyBackground_text" class="formFieldText" >       
        <input id="bodyBackground" class="field" type="text" size="70" name="bodyBackground" value="<%=WebUtil.display(_body_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeStylesForm_timeCreated_field" class="formFieldFrame">
    <div id="themeStylesForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="themeStylesForm_timeCreated_text" class="formFieldText" >       
        <input id="timeCreated" class="field" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeStylesForm_timeUpdated_field" class="formFieldFrame">
    <div id="themeStylesForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="themeStylesForm_timeUpdated_text" class="formFieldText" >       
        <input id="timeUpdated" class="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="themeStylesForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.themeStylesForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="themeStylesForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="themeStylesForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="themeStylesForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="themeStylesForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="themeStylesForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ThemeStyles.getId()%>">

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
<a href="/v_theme_styles_home.html">home</a> | <a href="/v_theme_styles_home.html">home</a> | <a href="/v_theme_styles_home.html">home</a>
<br/>
<br/>



<%
	List list_ThemeStyles = new ArrayList();
	ThemeStylesDS ds_ThemeStyles = ThemeStylesDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_ThemeStyles = ds_ThemeStyles.getAll();
	else		
    	list_ThemeStyles = ds_ThemeStyles.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_ThemeStyles, numDisplayInPage, listPage);

	list_ThemeStyles = PagingUtil.getPagedList(pagingInfo, list_ThemeStyles);
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

    <td class="columnTitle">  Body Width </td> 
    <td class="columnTitle">  Body Align </td> 
    <td class="columnTitle">  Body Background </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_ThemeStyles.iterator();iter.hasNext();) {
        ThemeStyles o_ThemeStyles = (ThemeStyles) iter.next();
%>

<TR id="tableRow<%= o_ThemeStyles.getId()%>">
    <td> <%= o_ThemeStyles.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_ThemeStyles.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_ThemeStyles.getBodyWidth()  %> </td>
	<td> <%= o_ThemeStyles.getBodyAlign()  %> </td>
	<td> <%= o_ThemeStyles.getBodyBackground()  %> </td>
	<td> <%= o_ThemeStyles.getTimeCreated()  %> </td>
	<td> <%= o_ThemeStyles.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_theme_styles_form('<%=o_ThemeStyles.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/themeStylesAction.html?del=true&id=<%=o_ThemeStyles.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_ThemeStyles.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_theme_styles_form('<%=o_ThemeStyles.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_theme_styles_form(target){
		location.href='/v_theme_styles_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_theme_styles_form(target){
		javascript:sendFormAjaxSimple('/themeStylesAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/themeStylesAction.html?ajxr=hb",
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
