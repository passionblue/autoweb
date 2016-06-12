<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    AutositeUser _AutositeUser = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_AutositeUser = AutositeUserDS.getInstance().getById(id);
		if ( _AutositeUser == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _AutositeUser = new AutositeUser();// AutositeUserDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "autosite_user_home";

    String _usernameValue= (reqParams.get("username")==null?WebUtil.display(_AutositeUser.getUsername()):WebUtil.display((String)reqParams.get("username")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(_AutositeUser.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_AutositeUser.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _user_typeValue= (reqParams.get("userType")==null?WebUtil.display(_AutositeUser.getUserType()):WebUtil.display((String)reqParams.get("userType")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_AutositeUser.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_AutositeUser.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _nicknameValue= (reqParams.get("nickname")==null?WebUtil.display(_AutositeUser.getNickname()):WebUtil.display((String)reqParams.get("nickname")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_AutositeUser.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_AutositeUser.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _disabledValue= (reqParams.get("disabled")==null?WebUtil.display(_AutositeUser.getDisabled()):WebUtil.display((String)reqParams.get("disabled")));
    String _time_disabledValue= (reqParams.get("timeDisabled")==null?WebUtil.display(_AutositeUser.getTimeDisabled()):WebUtil.display((String)reqParams.get("timeDisabled")));
    String _confirmedValue= (reqParams.get("confirmed")==null?WebUtil.display(_AutositeUser.getConfirmed()):WebUtil.display((String)reqParams.get("confirmed")));
    String _time_confirmedValue= (reqParams.get("timeConfirmed")==null?WebUtil.display(_AutositeUser.getTimeConfirmed()):WebUtil.display((String)reqParams.get("timeConfirmed")));
    String _opt_1Value= (reqParams.get("opt1")==null?WebUtil.display(_AutositeUser.getOpt1()):WebUtil.display((String)reqParams.get("opt1")));
    String _opt_2Value= (reqParams.get("opt2")==null?WebUtil.display(_AutositeUser.getOpt2()):WebUtil.display((String)reqParams.get("opt2")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="autositeUserForm" class="formFrame${classSuffix}">
<div id="autositeUserFormInnerFrame" class="formInnerFrame${classSuffix}">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeUserForm_Form" method="POST" action="/autositeUserAction.html" id="autositeUserForm_Form">





	<div id="autositeUserForm_username_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_username_label" class="formLabel${classSuffix}" >Username </div>
    <div id="autositeUserForm_username_text" class="formFieldText${classSuffix}" >       
        <input id="username" class="field" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_password_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_password_label" class="formLabel${classSuffix}" >Password </div>
    <div id="autositeUserForm_password_text" class="formFieldText${classSuffix}" >       
        <input id="password" class="field" type="text" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_email_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_email_label" class="formLabel${classSuffix}" >Email </div>
    <div id="autositeUserForm_email_text" class="formFieldText${classSuffix}" >       
        <input id="email" class="field" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_userType_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_userType_label" class="formLabel${classSuffix}" >User Type </div>
    <div id="autositeUserForm_userType_text" class="formFieldText${classSuffix}" >       
        <input id="userType" class="field" type="text" size="70" name="userType" value="<%=WebUtil.display(_user_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_firstName_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_firstName_label" class="formLabel${classSuffix}" >First Name </div>
    <div id="autositeUserForm_firstName_text" class="formFieldText${classSuffix}" >       
        <input id="firstName" class="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_lastName_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_lastName_label" class="formLabel${classSuffix}" >Last Name </div>
    <div id="autositeUserForm_lastName_text" class="formFieldText${classSuffix}" >       
        <input id="lastName" class="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_nickname_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_nickname_label" class="formLabel${classSuffix}" >Nickname </div>
    <div id="autositeUserForm_nickname_text" class="formFieldText${classSuffix}" >       
        <input id="nickname" class="field" type="text" size="70" name="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="autositeUserForm_timeUpdated_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_timeUpdated_label" class="formLabel${classSuffix}" >Time Updated </div>
    <div id="autositeUserForm_timeUpdated_text" class="formFieldText${classSuffix}" >       
        <input id="timeUpdated" class="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_disabled_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_disabled_label" class="formLabel${classSuffix}" >Disabled </div>
    <div id="autositeUserForm_disabled_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeDisabled_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_timeDisabled_label" class="formLabel${classSuffix}" >Time Disabled </div>
    <div id="autositeUserForm_timeDisabled_text" class="formFieldText${classSuffix}" >       
        <input id="timeDisabled" class="field" type="text" size="70" name="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_confirmed_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_confirmed_label" class="formLabel${classSuffix}" >Confirmed </div>
    <div id="autositeUserForm_confirmed_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="confirmed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _confirmedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _confirmedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeConfirmed_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_timeConfirmed_label" class="formLabel${classSuffix}" >Time Confirmed </div>
    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText${classSuffix}" >       
        <input id="timeConfirmed" class="field" type="text" size="70" name="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_opt1_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_opt1_label" class="formLabel${classSuffix}" >Opt 1 </div>
    <div id="autositeUserForm_opt1_text" class="formFieldText${classSuffix}" >       
        <input id="opt1" class="field" type="text" size="70" name="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_opt2_field" class="formFieldFrame${classSuffix}">
    <div id="autositeUserForm_opt2_label" class="formLabel${classSuffix}" >Opt 2 </div>
    <div id="autositeUserForm_opt2_text" class="formFieldText${classSuffix}" >       
        <input id="opt2" class="field" type="text" size="70" name="opt2" value="<%=WebUtil.display(_opt_2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="autositeUserForm_submit" class="formButton${classSuffix}" >       
        <a id="formSubmit2" href="javascript:document.autositeUserForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="autositeUserForm_submit_cancel" class="formButton${classSuffix}" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="autositeUserForm_submit_ext" class="formButton${classSuffix}" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="autositeUserForm_submit_cancel" class="formButton${classSuffix}" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

    <div id="autositeUserForm_submit_ext" class="formButton${classSuffix}" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">

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


<%
	List list_AutositeUser = new ArrayList();
	AutositeUserDS ds_AutositeUser = AutositeUserDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_AutositeUser = ds_AutositeUser.getAll();
	else		
    	list_AutositeUser = ds_AutositeUser.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_AutositeUser, numDisplayInPage, listPage);

	list_AutositeUser = PagingUtil.getPagedList(pagingInfo, list_AutositeUser);
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

    <td class="columnTitle">  Username </td> 
    <td class="columnTitle">  Password </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  User Type </td> 
    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  Nickname </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
    <td class="columnTitle">  Disabled </td> 
    <td class="columnTitle">  Time Disabled </td> 
    <td class="columnTitle">  Confirmed </td> 
    <td class="columnTitle">  Time Confirmed </td> 
    <td class="columnTitle">  Opt 1 </td> 
    <td class="columnTitle">  Opt 2 </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_AutositeUser.iterator();iter.hasNext();) {
        AutositeUser o_AutositeUser = (AutositeUser) iter.next();
%>

<TR id="tableRow<%= o_AutositeUser.getId()%>">
    <td> <%= o_AutositeUser.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_AutositeUser.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_AutositeUser.getUsername()  %> </td>
	<td> <%= o_AutositeUser.getPassword()  %> </td>
	<td> <%= o_AutositeUser.getEmail()  %> </td>
	<td> <%= o_AutositeUser.getUserType()  %> </td>
	<td> <%= o_AutositeUser.getFirstName()  %> </td>
	<td> <%= o_AutositeUser.getLastName()  %> </td>
	<td> <%= o_AutositeUser.getNickname()  %> </td>
	<td> <%= o_AutositeUser.getTimeCreated()  %> </td>
	<td> <%= o_AutositeUser.getTimeUpdated()  %> </td>
	<td> <%= o_AutositeUser.getDisabled()  %> </td>
	<td> <%= o_AutositeUser.getTimeDisabled()  %> </td>
	<td> <%= o_AutositeUser.getConfirmed()  %> </td>
	<td> <%= o_AutositeUser.getTimeConfirmed()  %> </td>
	<td> <%= o_AutositeUser.getOpt1()  %> </td>
	<td> <%= o_AutositeUser.getOpt2()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_autosite_user_form('<%=o_AutositeUser.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/autositeUserAction.html?del=true&id=<%=o_AutositeUser.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_AutositeUser.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_autosite_user_form('<%=o_AutositeUser.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_autosite_user_form(target){
		location.href='/v_autosite_user_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target;
	}
	function delete_autosite_user_form(target){
		javascript:sendFormAjaxSimple('/autositeUserAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/autositeUserAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>
