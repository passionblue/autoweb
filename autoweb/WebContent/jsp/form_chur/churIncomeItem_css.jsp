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
    ChurIncomeItem _ChurIncomeItem = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurIncomeItem = ChurIncomeItemDS.getInstance().getById(id);
		if ( _ChurIncomeItem == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurIncomeItem = new ChurIncomeItem();// ChurIncomeItemDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_item_home";

    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_ChurIncomeItem.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _income_itemValue= (reqParams.get("incomeItem")==null?WebUtil.display(_ChurIncomeItem.getIncomeItem()):WebUtil.display((String)reqParams.get("incomeItem")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurIncomeItem.getDisplay()):WebUtil.display((String)reqParams.get("display")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churIncomeItemForm" class="formFrame">
<div id="churIncomeItemFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeItemForm_Form" method="POST" action="/churIncomeItemAction.html" id="churIncomeItemForm_Form">



	<div id="churIncomeItemForm_categoryId_field" class="formFieldFrame">
    <div id="churIncomeItemForm_categoryId_label" class="formLabel" >Category Id </div>
    <div id="churIncomeItemForm_categoryId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="categoryId" id="categoryId">
        <option value="" >- Please Select -</option>
<%
	List _listChurIncomeCategory_categoryId = ChurIncomeCategoryDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurIncomeCategory_categoryId.iterator(); iter.hasNext();){
		ChurIncomeCategory _obj = (ChurIncomeCategory) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_category_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getIncomeCategory() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeItemForm_incomeItem_field" class="formFieldFrame">
    <div id="churIncomeItemForm_incomeItem_label" class="formLabel" >Income Item </div>
    <div id="churIncomeItemForm_incomeItem_text" class="formFieldText" >       
        <input id="incomeItem" class="field" type="text" size="70" name="incomeItem" value="<%=WebUtil.display(_income_itemValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeItemForm_display_field" class="formFieldFrame">
    <div id="churIncomeItemForm_display_label" class="formLabel" >Display </div>
    <div id="churIncomeItemForm_display_text" class="formFieldText" >       
        <input id="display" class="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div class="submitFrame">

        <div id="churIncomeItemForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churIncomeItemForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churIncomeItemForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churIncomeItemForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churIncomeItemForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churIncomeItemForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeItem.getId()%>">

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
    <td class="columnTitle">  Category Id </td> 
    <td class="columnTitle">  Income Item </td> 
    <td class="columnTitle">  Display </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeItem _oChurIncomeItem = (ChurIncomeItem) iter.next();
%>

<TR>
    <td> <%= _oChurIncomeItem.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurIncomeItem.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurIncomeItem.getCategoryId()  %> </td>
	<td> <%= _oChurIncomeItem.getIncomeItem()  %> </td>
	<td> <%= _oChurIncomeItem.getDisplay()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churIncomeItemAction.html?del=true&id=<%=_oChurIncomeItem.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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