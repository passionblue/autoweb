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
    ChurPayee _ChurPayee = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurPayee = ChurPayeeDS.getInstance().getById(id);
		if ( _ChurPayee == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurPayee = new ChurPayee();// ChurPayeeDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_payee_home";

    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_ChurPayee.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _remarkValue= (reqParams.get("remark")==null?WebUtil.display(_ChurPayee.getRemark()):WebUtil.display((String)reqParams.get("remark")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churPayeeForm" class="formFrame">
<div id="churPayeeFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churPayeeForm_Form" method="POST" action="/churPayeeAction.html" id="churPayeeForm_Form">





	<div id="churPayeeForm_title_field" class="formFieldFrame">
    <div id="churPayeeForm_title_label" class="formLabel" >Title </div>
    <div id="churPayeeForm_title_text" class="formFieldText" >       
        <input id="title" class="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churPayeeForm_remark_field" class="formFieldFrame">
    <div id="churPayeeForm_remark_label" class="formLabel" >Remark </div>
    <div id="churPayeeForm_remark_text" class="formFieldText" >       
        <input id="remark" class="field" type="text" size="70" name="remark" value="<%=WebUtil.display(_remarkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div class="submitFrame">

        <div id="churPayeeForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churPayeeForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churPayeeForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churPayeeForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churPayeeForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churPayeeForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurPayee.getId()%>">

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
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Remark </td> 
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> EDIT </td>
</TR>

<%
   	List list = ChurPayeeDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurPayee _oChurPayee = (ChurPayee) iter.next();
%>

<TR id="tableRow<%= _oChurPayee.getId()%>" >
    <td> <%= _oChurPayee.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurPayee.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurPayee.getTitle()  %> </td>
	<td> <%= _oChurPayee.getRemark()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churPayeeAction.html?del=true&id=<%=_oChurPayee.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oChurPayee.getId()%> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_payee_form('<%=_oChurPayee.getId()%>');">Edit</a>
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
	function edit_chur_payee_form(target){
		location.href='/v_chur_payee_form.html?cmd=edit&prv_returnPage=chur_payee_home&id=' + target;
	}
</script>