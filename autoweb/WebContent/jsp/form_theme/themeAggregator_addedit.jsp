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
    ThemeAggregator _ThemeAggregator = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ThemeAggregator = ThemeAggregatorDS.getInstance().getById(id);
		if ( _ThemeAggregator == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ThemeAggregator = new ThemeAggregator();// ThemeAggregatorDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "theme_aggregator_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _theme_nameValue= (reqParams.get("themeName")==null?WebUtil.display(_ThemeAggregator.getThemeName()):WebUtil.display((String)reqParams.get("themeName")));
    String _layout_pageValue= (reqParams.get("layoutPage")==null?WebUtil.display(_ThemeAggregator.getLayoutPage()):WebUtil.display((String)reqParams.get("layoutPage")));
    String _css_indexValue= (reqParams.get("cssIndex")==null?WebUtil.display(_ThemeAggregator.getCssIndex()):WebUtil.display((String)reqParams.get("cssIndex")));
    String _theme_style_idValue= (reqParams.get("themeStyleId")==null?WebUtil.display(_ThemeAggregator.getThemeStyleId()):WebUtil.display((String)reqParams.get("themeStyleId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ThemeAggregator.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ThemeAggregator.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "theme_aggregator_" + System.nanoTime();
%> 

<br>
<div id="themeAggregatorForm" class="formFrame">
<div id="themeAggregatorFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="themeAggregatorForm_Form" method="POST" action="/themeAggregatorAction.html" id="themeAggregatorForm_Form">





	<div id="themeAggregatorForm_themeName_field" class="formFieldFrame">
    <div id="themeAggregatorForm_themeName_label" class="formLabel" >Theme Name </div>
    <div id="themeAggregatorForm_themeName_text" class="formFieldText" >       
        <input id="themeName" class="field" type="text" size="70" name="themeName" value="<%=WebUtil.display(_theme_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_layoutPage_field" class="formFieldFrame">
    <div id="themeAggregatorForm_layoutPage_label" class="formLabel" >Layout Page </div>
    <div id="themeAggregatorForm_layoutPage_text" class="formFieldText" >       
        <input id="layoutPage" class="field" type="text" size="70" name="layoutPage" value="<%=WebUtil.display(_layout_pageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_cssIndex_field" class="formFieldFrame">
    <div id="themeAggregatorForm_cssIndex_label" class="formLabel" >Css Index </div>
    <div id="themeAggregatorForm_cssIndex_text" class="formFieldText" >       
        <input id="cssIndex" class="field" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


  	<div id="themeAggregatorForm_themeStyleId_field" class="formFieldFrame">
    <div id="themeAggregatorForm_themeStyleId_label" class="formLabel" >Theme Style Id </div>
    <div id="themeAggregatorForm_themeStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="themeStyleId" id="themeStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listThemeStyles_themeStyleId = ThemeStylesDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listThemeStyles_themeStyleId.iterator(); iter.hasNext();){
		ThemeStyles _obj = (ThemeStyles) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_theme_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getId() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_timeCreated_field" class="formFieldFrame">
    <div id="themeAggregatorForm_timeCreated_label" class="formLabel" >Time Created </div>
    <div id="themeAggregatorForm_timeCreated_text" class="formFieldText" >       
        <input id="timeCreated" class="field" type="text" size="70" name="timeCreated" value="<%=WebUtil.display(_time_createdValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="themeAggregatorForm_timeUpdated_field" class="formFieldFrame">
    <div id="themeAggregatorForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="themeAggregatorForm_timeUpdated_text" class="formFieldText" >       
        <input id="timeUpdated" class="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="themeAggregatorForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.themeAggregatorForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="themeAggregatorForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="themeAggregatorForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="themeAggregatorForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="themeAggregatorForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="themeAggregatorForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="themeAggregatorForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="themeAggregatorForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ThemeAggregator.getId()%>">

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
<a href="/v_theme_aggregator_home.html">home</a> | <a href="/v_theme_aggregator_home.html">home</a> | <a href="/v_theme_aggregator_home.html">home</a>
<br/>
<br/>



<%
	List list_ThemeAggregator = new ArrayList();
	ThemeAggregatorDS ds_ThemeAggregator = ThemeAggregatorDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_ThemeAggregator = ds_ThemeAggregator.getAll();
	else		
    	list_ThemeAggregator = ds_ThemeAggregator.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_ThemeAggregator, numDisplayInPage, listPage);

	list_ThemeAggregator = PagingUtil.getPagedList(pagingInfo, list_ThemeAggregator);
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

    <td class="columnTitle">  Theme Name </td> 
    <td class="columnTitle">  Layout Page </td> 
    <td class="columnTitle">  Css Index </td> 
    <td class="columnTitle">  Theme Style Id </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_ThemeAggregator.iterator();iter.hasNext();) {
        ThemeAggregator o_ThemeAggregator = (ThemeAggregator) iter.next();
%>

<TR id="tableRow<%= o_ThemeAggregator.getId()%>">
    <td> <%= o_ThemeAggregator.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_ThemeAggregator.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_ThemeAggregator.getThemeName()  %> </td>
	<td> <%= o_ThemeAggregator.getLayoutPage()  %> </td>
	<td> <%= o_ThemeAggregator.getCssIndex()  %> </td>
	<td> <%= o_ThemeAggregator.getThemeStyleId()  %> </td>
	<td> <%= o_ThemeAggregator.getTimeCreated()  %> </td>
	<td> <%= o_ThemeAggregator.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_theme_aggregator_form('<%=o_ThemeAggregator.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/themeAggregatorAction.html?del=true&id=<%=o_ThemeAggregator.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_ThemeAggregator.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_theme_aggregator_form('<%=o_ThemeAggregator.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_theme_aggregator_form(target){
		location.href='/v_theme_aggregator_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_theme_aggregator_form(target){
		javascript:sendFormAjaxSimple('/themeAggregatorAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/themeAggregatorAction.html?ajxr=hb",
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
