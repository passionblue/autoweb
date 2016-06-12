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
    ChurIncomeAddDataHolder _ChurIncomeAdd = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurIncomeAdd = ChurIncomeAddDS.getInstance().getById(id);
		if ( _ChurIncomeAdd == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurIncomeAdd = new ChurIncomeAddDataHolder();// ChurIncomeAddDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_add_home";

    String _chur_member_idValue= (reqParams.get("churMemberId")==null?WebUtil.display(_ChurIncomeAdd.getChurMemberId()):WebUtil.display((String)reqParams.get("churMemberId")));
    String _titheValue= (reqParams.get("tithe")==null?WebUtil.display(_ChurIncomeAdd.getTithe()):WebUtil.display((String)reqParams.get("tithe")));
    String _weeklyValue= (reqParams.get("weekly")==null?WebUtil.display(_ChurIncomeAdd.getWeekly()):WebUtil.display((String)reqParams.get("weekly")));
    String _thanksValue= (reqParams.get("thanks")==null?WebUtil.display(_ChurIncomeAdd.getThanks()):WebUtil.display((String)reqParams.get("thanks")));
    String _missionValue= (reqParams.get("mission")==null?WebUtil.display(_ChurIncomeAdd.getMission()):WebUtil.display((String)reqParams.get("mission")));
    String _constructionValue= (reqParams.get("construction")==null?WebUtil.display(_ChurIncomeAdd.getConstruction()):WebUtil.display((String)reqParams.get("construction")));
    String _otherValue= (reqParams.get("other")==null?WebUtil.display(_ChurIncomeAdd.getOther()):WebUtil.display((String)reqParams.get("other")));
    String _weekValue= (reqParams.get("week")==null?WebUtil.display(_ChurIncomeAdd.getWeek()):WebUtil.display((String)reqParams.get("week")));
    String _yearValue= (reqParams.get("year")==null?WebUtil.display(_ChurIncomeAdd.getYear()):WebUtil.display((String)reqParams.get("year")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churIncomeAddForm" class="formFrame">
<div id="churIncomeAddFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeAddForm_Form" method="POST" action="/churIncomeAddAction.html" id="churIncomeAddForm_Form">



	<div id="churIncomeAddForm_churMemberId_field" class="formFieldFrame">
    <div id="churIncomeAddForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeAddForm_churMemberId_dropdown" class="formFieldDropDown" >       
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




	<div id="churIncomeAddForm_tithe_field" class="formFieldFrame">
    <div id="churIncomeAddForm_tithe_label" class="formLabel" >Tithe </div>
    <div id="churIncomeAddForm_tithe_text" class="formFieldText" >       
        <input id="tithe" class="field" type="text" size="70" name="tithe" value="<%=WebUtil.display(_titheValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_weekly_field" class="formFieldFrame">
    <div id="churIncomeAddForm_weekly_label" class="formLabel" >Weekly </div>
    <div id="churIncomeAddForm_weekly_text" class="formFieldText" >       
        <input id="weekly" class="field" type="text" size="70" name="weekly" value="<%=WebUtil.display(_weeklyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_thanks_field" class="formFieldFrame">
    <div id="churIncomeAddForm_thanks_label" class="formLabel" >Thanks </div>
    <div id="churIncomeAddForm_thanks_text" class="formFieldText" >       
        <input id="thanks" class="field" type="text" size="70" name="thanks" value="<%=WebUtil.display(_thanksValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_mission_field" class="formFieldFrame">
    <div id="churIncomeAddForm_mission_label" class="formLabel" >Mission </div>
    <div id="churIncomeAddForm_mission_text" class="formFieldText" >       
        <input id="mission" class="field" type="text" size="70" name="mission" value="<%=WebUtil.display(_missionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_construction_field" class="formFieldFrame">
    <div id="churIncomeAddForm_construction_label" class="formLabel" >Construction </div>
    <div id="churIncomeAddForm_construction_text" class="formFieldText" >       
        <input id="construction" class="field" type="text" size="70" name="construction" value="<%=WebUtil.display(_constructionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_other_field" class="formFieldFrame">
    <div id="churIncomeAddForm_other_label" class="formLabel" >Other </div>
    <div id="churIncomeAddForm_other_text" class="formFieldText" >       
        <input id="other" class="field" type="text" size="70" name="other" value="<%=WebUtil.display(_otherValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_week_field" class="formFieldFrame">
    <div id="churIncomeAddForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeAddForm_week_text" class="formFieldText" >       
        <input id="week" class="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_year_field" class="formFieldFrame">
    <div id="churIncomeAddForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeAddForm_year_text" class="formFieldText" >       
        <input id="year" class="field" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div class="submitFrame">

        <div id="churIncomeAddForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churIncomeAddForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churIncomeAddForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churIncomeAddForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churIncomeAddForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churIncomeAddForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeAdd.getId()%>">

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
    <td class="columnTitle">  Chur Member Id </td> 
    <td class="columnTitle">  Tithe </td> 
    <td class="columnTitle">  Weekly </td> 
    <td class="columnTitle">  Thanks </td> 
    <td class="columnTitle">  Mission </td> 
    <td class="columnTitle">  Construction </td> 
    <td class="columnTitle">  Other </td> 
    <td class="columnTitle">  Week </td> 
    <td class="columnTitle">  Year </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurIncomeAddDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeAddDataHolder _oChurIncomeAdd = (ChurIncomeAddDataHolder) iter.next();
%>

<TR>
    <td> <%= _oChurIncomeAdd.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurIncomeAdd.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurIncomeAdd.getChurMemberId()  %> </td>
	<td> <%= _oChurIncomeAdd.getTithe()  %> </td>
	<td> <%= _oChurIncomeAdd.getWeekly()  %> </td>
	<td> <%= _oChurIncomeAdd.getThanks()  %> </td>
	<td> <%= _oChurIncomeAdd.getMission()  %> </td>
	<td> <%= _oChurIncomeAdd.getConstruction()  %> </td>
	<td> <%= _oChurIncomeAdd.getOther()  %> </td>
	<td> <%= _oChurIncomeAdd.getWeek()  %> </td>
	<td> <%= _oChurIncomeAdd.getYear()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churIncomeAddAction.html?del=true&id=<%=_oChurIncomeAdd.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
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