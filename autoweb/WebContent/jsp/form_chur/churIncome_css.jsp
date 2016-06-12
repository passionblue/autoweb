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
    ChurIncomeDataHolder _ChurIncome = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurIncome = ChurIncomeDS.getInstance().getById(id);
		if ( _ChurIncome == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurIncome = new ChurIncomeDataHolder();// ChurIncomeDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_home";

    String _yearValue= (reqParams.get("year")==null?WebUtil.display(_ChurIncome.getYear()):WebUtil.display((String)reqParams.get("year")));
    String _weekValue= (reqParams.get("week")==null?WebUtil.display(_ChurIncome.getWeek()):WebUtil.display((String)reqParams.get("week")));
    String _chur_member_idValue= (reqParams.get("churMemberId")==null?WebUtil.display(_ChurIncome.getChurMemberId()):WebUtil.display((String)reqParams.get("churMemberId")));
    String _income_item_idValue= (reqParams.get("incomeItemId")==null?WebUtil.display(_ChurIncome.getIncomeItemId()):WebUtil.display((String)reqParams.get("incomeItemId")));
    String _ammountValue= (reqParams.get("ammount")==null?WebUtil.display(_ChurIncome.getAmmount()):WebUtil.display((String)reqParams.get("ammount")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ChurIncome.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churIncomeForm" class="formFrame">
<div id="churIncomeFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeForm_Form" method="POST" action="/churIncomeAction.html" id="churIncomeForm_Form">



	<div id="churIncomeForm_year_field" class="formFieldFrame">
    <div id="churIncomeForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeForm_year_dropdown" class="formFieldDropDown" >       
        <select class="field" name="year" id="year">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _yearValue)%>>XX</option-->
        <option value="2012" <%=HtmlUtil.getOptionSelect("2012", _yearValue)%>>2012</option>
        <option value="2013" <%=HtmlUtil.getOptionSelect("2013", _yearValue)%>>2013</option>
        <option value="2014" <%=HtmlUtil.getOptionSelect("2014", _yearValue)%>>2014</option>
        <option value="2015" <%=HtmlUtil.getOptionSelect("2015", _yearValue)%>>2015</option>
        <option value="2016" <%=HtmlUtil.getOptionSelect("2016", _yearValue)%>>2016</option>
        <option value="2017" <%=HtmlUtil.getOptionSelect("2017", _yearValue)%>>2017</option>
        <option value="2018" <%=HtmlUtil.getOptionSelect("2018", _yearValue)%>>2018</option>
        <option value="2019" <%=HtmlUtil.getOptionSelect("2019", _yearValue)%>>2019</option>
        <option value="2020" <%=HtmlUtil.getOptionSelect("2020", _yearValue)%>>2020</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="churIncomeForm_week_field" class="formFieldFrame">
    <div id="churIncomeForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeForm_week_text" class="formFieldText" >       
        <input id="week" class="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churIncomeForm_churMemberId_field" class="formFieldFrame">
    <div id="churIncomeForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeForm_churMemberId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="churMemberId" id="churMemberId">
        <option value="" >- Please Select -</option>
<%
	List _listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_chur_member_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churIncomeForm_incomeItemId_field" class="formFieldFrame">
    <div id="churIncomeForm_incomeItemId_label" class="formLabel" >Income Item Id </div>
    <div id="churIncomeForm_incomeItemId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="incomeItemId" id="incomeItemId">
        <option value="" >- Please Select -</option>
<%
	List _listChurIncomeItem_incomeItemId = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurIncomeItem_incomeItemId.iterator(); iter.hasNext();){
		ChurIncomeItem _obj = (ChurIncomeItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_income_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getIncomeItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeForm_ammount_field" class="formFieldFrame">
    <div id="churIncomeForm_ammount_label" class="formLabel" >Ammount </div>
    <div id="churIncomeForm_ammount_text" class="formFieldText" >       
        <input id="ammount" class="field" type="text" size="70" name="ammount" value="<%=WebUtil.display(_ammountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div class="submitFrame">

        <div id="churIncomeForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churIncomeForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churIncomeForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churIncomeForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churIncomeForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churIncomeForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncome.getId()%>">

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
    <td class="columnTitle">  Year </td> 
    <td class="columnTitle">  Week </td> 
    <td class="columnTitle">  Chur Member Id </td> 
    <td class="columnTitle">  Income Item Id </td> 
    <td class="columnTitle">  Ammount </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurIncomeDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeDataHolder _oChurIncome = (ChurIncomeDataHolder) iter.next();
%>

<TR>
    <td> <%= _oChurIncome.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurIncome.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurIncome.getYear()  %> </td>
	<td> <%= _oChurIncome.getWeek()  %> </td>
	<td> <%= _oChurIncome.getChurMemberId()  %> </td>
	<td> <%= _oChurIncome.getIncomeItemId()  %> </td>
	<td> <%= _oChurIncome.getAmmount()  %> </td>
	<td> <%= _oChurIncome.getTimeCreated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churIncomeAction.html?del=true&id=<%=_oChurIncome.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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