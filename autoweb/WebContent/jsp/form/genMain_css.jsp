<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    GenMain _GenMain = null;
	
	boolean isEdit = false;	
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
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_main_home";

    String _activeValue= (reqParams.get("active")==null?WebUtil.display(_GenMain.getActive()):WebUtil.display((String)reqParams.get("active")));
    String _valueValue= (reqParams.get("value")==null?WebUtil.display(_GenMain.getValue()):WebUtil.display((String)reqParams.get("value")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_GenMain.getData()):WebUtil.display((String)reqParams.get("data")));
    String _requiredValue= (reqParams.get("required")==null?WebUtil.display(_GenMain.getRequired()):WebUtil.display((String)reqParams.get("required")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_GenMain.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_GenMain.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    long pagestamp = System.nanoTime();
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

        <div id="genMainForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

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
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR  >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Active </td> 
    <td class="columnTitle">  Value </td> 
    <td class="columnTitle">  Data </td> 
    <td class="columnTitle">  Required </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> EDIT </td>
</TR>

<%
   	List list = GenMainDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        GenMain _oGenMain = (GenMain) iter.next();
%>

<TR id="tableRow<%= _oGenMain.getId()%>" >
    <td> <%= _oGenMain.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oGenMain.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oGenMain.getActive()  %> </td>
	<td> <%= _oGenMain.getValue()  %> </td>
	<td> <%= _oGenMain.getData()  %> </td>
	<td> <%= _oGenMain.getRequired()  %> </td>
	<td> <%= _oGenMain.getTimeCreated()  %> </td>
	<td> <%= _oGenMain.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/genMainAction.html?del=true&id=<%=_oGenMain.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oGenMain.getId()%> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_gen_main_form('<%=_oGenMain.getId()%>');">Edit</a>
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
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
	function edit_gen_main_form(target){
		location.href='/v_gen_main_form.html?cmd=edit&prv_returnPage=gen_main_home&id=' + target;
	}
</script>