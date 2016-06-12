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
    ChurMember _ChurMember = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurMember = ChurMemberDS.getInstance().getById(id);
		if ( _ChurMember == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurMember = new ChurMember();// ChurMemberDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_member_home";

    String _full_nameValue= (reqParams.get("fullName")==null?WebUtil.display(_ChurMember.getFullName()):WebUtil.display((String)reqParams.get("fullName")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_ChurMember.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_ChurMember.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_ChurMember.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _other_nameValue= (reqParams.get("otherName")==null?WebUtil.display(_ChurMember.getOtherName()):WebUtil.display((String)reqParams.get("otherName")));
    String _householdValue= (reqParams.get("household")==null?WebUtil.display(_ChurMember.getHousehold()):WebUtil.display((String)reqParams.get("household")));
    String _household_idValue= (reqParams.get("householdId")==null?WebUtil.display(_ChurMember.getHouseholdId()):WebUtil.display((String)reqParams.get("householdId")));
    String _is_groupValue= (reqParams.get("isGroup")==null?WebUtil.display(_ChurMember.getIsGroup()):WebUtil.display((String)reqParams.get("isGroup")));
    String _is_guestValue= (reqParams.get("isGuest")==null?WebUtil.display(_ChurMember.getIsGuest()):WebUtil.display((String)reqParams.get("isGuest")));
    String _is_speakerValue= (reqParams.get("isSpeaker")==null?WebUtil.display(_ChurMember.getIsSpeaker()):WebUtil.display((String)reqParams.get("isSpeaker")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ChurMember.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _list_indexValue= (reqParams.get("listIndex")==null?WebUtil.display(_ChurMember.getListIndex()):WebUtil.display((String)reqParams.get("listIndex")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="churMemberForm" class="formFrame">
<div id="churMemberFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churMemberForm_Form" method="POST" action="/churMemberAction.html" id="churMemberForm_Form">





	<div id="churMemberForm_fullName_field" class="formFieldFrame">
    <div id="churMemberForm_fullName_label" class="formLabel" >Full Name </div>
    <div id="churMemberForm_fullName_text" class="formFieldText" >       
        <input id="fullName" class="field" type="text" size="70" name="fullName" value="<%=WebUtil.display(_full_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_firstName_field" class="formFieldFrame">
    <div id="churMemberForm_firstName_label" class="formLabel" >First Name </div>
    <div id="churMemberForm_firstName_text" class="formFieldText" >       
        <input id="firstName" class="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_lastName_field" class="formFieldFrame">
    <div id="churMemberForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="churMemberForm_lastName_text" class="formFieldText" >       
        <input id="lastName" class="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_title_field" class="formFieldFrame">
    <div id="churMemberForm_title_label" class="formLabel" >Title </div>
    <div id="churMemberForm_title_text" class="formFieldText" >       
        <input id="title" class="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_otherName_field" class="formFieldFrame">
    <div id="churMemberForm_otherName_label" class="formLabel" >Other Name </div>
    <div id="churMemberForm_otherName_text" class="formFieldText" >       
        <input id="otherName" class="field" type="text" size="70" name="otherName" value="<%=WebUtil.display(_other_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churMemberForm_household_field" class="formFieldFrame">
    <div id="churMemberForm_household_label" class="formLabel" >Household </div>
    <div id="churMemberForm_household_dropdown" class="formFieldDropDown" >       
        <select name="household">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _householdValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _householdValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



    <INPUT TYPE="HIDDEN" NAME="householdId" value="<%=WebUtil.display(_household_idValue)%>" />




	<div id="churMemberForm_isGroup_field" class="formFieldFrame">
    <div id="churMemberForm_isGroup_label" class="formLabel" >Is Group </div>
    <div id="churMemberForm_isGroup_dropdown" class="formFieldDropDown" >       
        <select name="isGroup">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_groupValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_groupValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_isGuest_field" class="formFieldFrame">
    <div id="churMemberForm_isGuest_label" class="formLabel" >Is Guest </div>
    <div id="churMemberForm_isGuest_dropdown" class="formFieldDropDown" >       
        <select name="isGuest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_guestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_guestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="churMemberForm_isSpeaker_field" class="formFieldFrame">
    <div id="churMemberForm_isSpeaker_label" class="formLabel" >Is Speaker </div>
    <div id="churMemberForm_isSpeaker_dropdown" class="formFieldDropDown" >       
        <select name="isSpeaker">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _is_speakerValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _is_speakerValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>








	<div id="churMemberForm_listIndex_field" class="formFieldFrame">
    <div id="churMemberForm_listIndex_label" class="formLabel" >List Index </div>
    <div id="churMemberForm_listIndex_text" class="formFieldText" >       
        <input id="listIndex" class="field" type="text" size="70" name="listIndex" value="<%=WebUtil.display(_list_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div class="submitFrame">

        <div id="churMemberForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churMemberForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churMemberForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churMemberForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churMemberForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churMemberForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurMember.getId()%>">

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
    <td class="columnTitle">  Full Name </td> 
    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Other Name </td> 
    <td class="columnTitle">  Household </td> 
    <td class="columnTitle">  Household Id </td> 
    <td class="columnTitle">  Is Group </td> 
    <td class="columnTitle">  Is Guest </td> 
    <td class="columnTitle">  Is Speaker </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  List Index </td> 
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> EDIT </td>
</TR>

<%
   	List list = ChurMemberDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurMember _oChurMember = (ChurMember) iter.next();
%>

<TR id="tableRow<%= _oChurMember.getId()%>" >
    <td> <%= _oChurMember.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurMember.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oChurMember.getFullName()  %> </td>
	<td> <%= _oChurMember.getFirstName()  %> </td>
	<td> <%= _oChurMember.getLastName()  %> </td>
	<td> <%= _oChurMember.getTitle()  %> </td>
	<td> <%= _oChurMember.getOtherName()  %> </td>
	<td> <%= _oChurMember.getHousehold()  %> </td>
	<td> <%= _oChurMember.getHouseholdId()  %> </td>
	<td> <%= _oChurMember.getIsGroup()  %> </td>
	<td> <%= _oChurMember.getIsGuest()  %> </td>
	<td> <%= _oChurMember.getIsSpeaker()  %> </td>
	<td> <%= _oChurMember.getTimeCreated()  %> </td>
	<td> <%= _oChurMember.getListIndex()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churMemberAction.html?del=true&id=<%=_oChurMember.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oChurMember.getId()%> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_chur_member_form('<%=_oChurMember.getId()%>');">Edit</a>
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
	function edit_chur_member_form(target){
		location.href='/v_chur_member_form.html?cmd=edit&prv_returnPage=chur_member_home&id=' + target;
	}
</script>