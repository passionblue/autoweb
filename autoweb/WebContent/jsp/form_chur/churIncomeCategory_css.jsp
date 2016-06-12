<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ChurIncomeCategory _ChurIncomeCategory = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurIncomeCategory = ChurIncomeCategoryDS.getInstance().getById(id);
		if ( _ChurIncomeCategory == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurIncomeCategory = new ChurIncomeCategory();// ChurIncomeCategoryDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_category_home";

    String _income_categoryValue= (reqParams.get("incomeCategory")==null?WebUtil.display(_ChurIncomeCategory.getIncomeCategory()):WebUtil.display((String)reqParams.get("incomeCategory")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurIncomeCategory.getDisplay()):WebUtil.display((String)reqParams.get("display")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ChurIncomeCategory.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churIncomeCategoryForm" class="formFrame">
<div id="churIncomeCategoryFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeCategoryForm_Form" method="POST" action="/churIncomeCategoryAction.html" id="churIncomeCategoryForm_Form">





	<div id="churIncomeCategoryForm_incomeCategory_field" class="formFieldFrame">
    <div id="churIncomeCategoryForm_incomeCategory_label" class="formLabel" >Income Category </div>
    <div id="churIncomeCategoryForm_incomeCategory_text" class="formFieldText" >       
        <input id="incomeCategory" class="field" type="text" size="70" name="incomeCategory" value="<%=WebUtil.display(_income_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeCategoryForm_display_field" class="formFieldFrame">
    <div id="churIncomeCategoryForm_display_label" class="formLabel" >Display </div>
    <div id="churIncomeCategoryForm_display_text" class="formFieldText" >       
        <input id="display" class="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div class="submitFrame">

        <div id="churIncomeCategoryForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churIncomeCategoryForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churIncomeCategoryForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churIncomeCategoryForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churIncomeCategoryForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churIncomeCategoryForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeCategory.getId()%>">

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

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Income Category </td> 
    <td class="columnTitle">  Display </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurIncomeCategoryDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeCategory _oChurIncomeCategory = (ChurIncomeCategory) iter.next();
%>

<TR>
    <td> <%= _oChurIncomeCategory.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurIncomeCategory.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurIncomeCategory.getIncomeCategory()  %> </td>
	<td> <%= _oChurIncomeCategory.getDisplay()  %> </td>
	<td> <%= _oChurIncomeCategory.getTimeCreated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churIncomeCategoryAction.html?del=true&id=<%=_oChurIncomeCategory.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
</script>