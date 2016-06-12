<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
/* 
Template last modification history:


Source Generated: Sun Mar 15 14:31:01 EDT 2015
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
    GenTableDataHolder _GenTable = null;
	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_GenTable = GenTableDS.getInstance().getById(id);
		if ( _GenTable == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _GenTable = new GenTableDataHolder();// GenTableDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
	    if ( _GenTable == null) _GenTable = new GenTableDataHolder();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_table_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _countryValue= (reqParams.get("country")==null?WebUtil.display(_GenTable.getCountry()):WebUtil.display((String)reqParams.get("country")));
    String _ageValue= (reqParams.get("age")==null?WebUtil.display(_GenTable.getAge()):WebUtil.display((String)reqParams.get("age")));
    String _disabledValue= (reqParams.get("disabled")==null?WebUtil.display(_GenTable.getDisabled()):WebUtil.display((String)reqParams.get("disabled")));
    String _commentsValue= (reqParams.get("comments")==null?WebUtil.display(_GenTable.getComments()):WebUtil.display((String)reqParams.get("comments")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_GenTable.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_GenTable.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _ext_stringValue= (reqParams.get("extString")==null?WebUtil.display(_GenTable.getExtString()):WebUtil.display((String)reqParams.get("extString")));
    String _ext_intValue= (reqParams.get("extInt")==null?WebUtil.display(_GenTable.getExtInt()):WebUtil.display((String)reqParams.get("extInt")));

    String pagestamp = "gen_table_" + System.nanoTime();
%> 

<br>
<div id="genTableForm" class="formFrame">
<div id="genTableFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genTableForm_Form" method="POST" action="/genTableAction.html" id="genTableForm_Form">



	<div id="genTableForm_country_field" class="formFieldFrame">
    <div id="genTableForm_country_label" class="formLabel" >Country </div>
    <div id="genTableForm_country_dropdown" class="formFieldDropDown" >       
        <select class="field" name="country" id="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        <option value="usa" <%=HtmlUtil.getOptionSelect("usa", _countryValue)%>>United States</option>
        <option value="korea" <%=HtmlUtil.getOptionSelect("korea", _countryValue)%>>SouthKorea</option>
        <option value="congo" <%=HtmlUtil.getOptionSelect("congo", _countryValue)%>>Congo</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genTableForm_age_field" class="formFieldFrame">
    <div id="genTableForm_age_label" class="formLabel" >Age </div>
    <div id="genTableForm_age_dropdown" class="formFieldDropDown" >       
        <select class="field" name="age" id="age">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ageValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genTableForm_disabled_field" class="formFieldFrame">
    <div id="genTableForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="genTableForm_disabled_dropdown" class="formFieldDropDown" >       
        <select name="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



	<div id="genTableForm_comments_field" class="formFieldFrame">
    <div id="genTableForm_comments_label" class="formLabel" >Comments </div>
    <div id="genTableForm_comments_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="comments" class="field" NAME="comments" COLS="50" ROWS="8" ><%=WebUtil.display(_commentsValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>











	<div id="genTableForm_extString_field" class="formFieldFrame">
    <div id="genTableForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genTableForm_extString_text" class="formFieldText" >       
        <input id="extString" class="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="genTableForm_extInt_field" class="formFieldFrame">
    <div id="genTableForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genTableForm_extInt_text" class="formFieldText" >       
        <input id="extInt" class="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="genTableForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.genTableForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="genTableForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="genTableForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="genTableForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="genTableForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="genTableForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    


</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">

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
<a href="/v_gen_table_home.html">home</a> | <a href="/v_gen_table_home.html">home</a> | <a href="/v_gen_table_home.html">home</a>
<br/>
<br/>



<%
	List list_GenTable = new ArrayList();
	GenTableDS ds_GenTable = GenTableDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_GenTable = ds_GenTable.getAll();
	else		
    	list_GenTable = ds_GenTable.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) request.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_GenTable, numDisplayInPage, listPage);

	list_GenTable = PagingUtil.getPagedList(pagingInfo, list_GenTable);
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

    <td class="columnTitle">  Country </td> 
    <td class="columnTitle">  Age </td> 
    <td class="columnTitle">  Disabled </td> 
    <td class="columnTitle">  Comments </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  ExtraString </td> 
    <td class="columnTitle">  Ext Int </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_GenTable.iterator();iter.hasNext();) {
        GenTableDataHolder o_GenTable = (GenTableDataHolder) iter.next();
%>

<TR id="tableRow<%= o_GenTable.getId()%>">
    <td> <%= o_GenTable.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_GenTable.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_GenTable.getCountry()  %> </td>
	<td> <%= o_GenTable.getAge()  %> </td>
	<td> <%= o_GenTable.getDisabled()  %> </td>
	<td> <%= o_GenTable.getComments()  %> </td>
	<td> <%= o_GenTable.getTimeCreated()  %> </td>
	<td> <%= o_GenTable.getTimeUpdated()  %> </td>
	<td> <%= o_GenTable.getExtString()  %> </td>
	<td> <%= o_GenTable.getExtInt()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_gen_table_form('<%=o_GenTable.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/genTableAction.html?del=true&id=<%=o_GenTable.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_GenTable.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_gen_table_form('<%=o_GenTable.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_gen_table_form(target){
		location.href='/v_gen_table_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_gen_table_form(target){
		javascript:sendFormAjaxSimple('/genTableAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/genTableAction.html?ajxr=hb",
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
