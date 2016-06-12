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
    RegisterSimple _RegisterSimple = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_RegisterSimple = RegisterSimpleDS.getInstance().getById(id);
		if ( _RegisterSimple == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _RegisterSimple = new RegisterSimple();// RegisterSimpleDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "register_simple_home";

    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_RegisterSimple.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_RegisterSimple.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_RegisterSimple.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _usernameValue= (reqParams.get("username")==null?WebUtil.display(_RegisterSimple.getUsername()):WebUtil.display((String)reqParams.get("username")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(_RegisterSimple.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _birth_yearValue= (reqParams.get("birthYear")==null?WebUtil.display(_RegisterSimple.getBirthYear()):WebUtil.display((String)reqParams.get("birthYear")));
    String _birth_monthValue= (reqParams.get("birthMonth")==null?WebUtil.display(_RegisterSimple.getBirthMonth()):WebUtil.display((String)reqParams.get("birthMonth")));
    String _birth_dayValue= (reqParams.get("birthDay")==null?WebUtil.display(_RegisterSimple.getBirthDay()):WebUtil.display((String)reqParams.get("birthDay")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_RegisterSimple.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_RegisterSimple.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="registerSimpleForm" class="formFrame">
<div id="registerSimpleFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="registerSimpleForm_Form" method="POST" action="/registerSimpleAction.html" id="registerSimpleForm_Form">






	<div id="registerSimpleForm_firstName_field" class="formFieldFrame">
    <div id="registerSimpleForm_firstName_label" class="formLabel" >First Name </div>
    <div id="registerSimpleForm_firstName_text" class="formFieldText" >       
        <input id="firstName" class="field" type="text" size="70" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="registerSimpleForm_lastName_field" class="formFieldFrame">
    <div id="registerSimpleForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="registerSimpleForm_lastName_text" class="formFieldText" >       
        <input id="lastName" class="field" type="text" size="70" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="registerSimpleForm_email_field" class="formFieldFrame">
    <div id="registerSimpleForm_email_label" class="formLabel" >Email </div>
    <div id="registerSimpleForm_email_text" class="formFieldText" >       
        <input id="email" class="field" type="text" size="70" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="registerSimpleForm_username_field" class="formFieldFrame">
    <div id="registerSimpleForm_username_label" class="formLabel" >Username </div>
    <div id="registerSimpleForm_username_text" class="formFieldText" >       
        <input id="username" class="field" type="text" size="70" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_password_field" class="formFieldFrame">
    <div id="registerSimpleForm_password_label" class="formLabel" >Password </div>
    <div id="registerSimpleForm_password_password" class="formFieldPassword" > <span></span>      
        <input id="password" class="field" type="password" size="70" name="password" value="<%=WebUtil.display(_passwordValue)%>"/>
    </div>      
	</div><div class="clear"></div>




    <INPUT TYPE="HIDDEN" NAME="birthYear" value="<%=WebUtil.display(_birth_yearValue)%>" />



    <INPUT TYPE="HIDDEN" NAME="birthMonth" value="<%=WebUtil.display(_birth_monthValue)%>" />



    <INPUT TYPE="HIDDEN" NAME="birthDay" value="<%=WebUtil.display(_birth_dayValue)%>" />








<div class="submitFrame">

    <div id="registerSimpleForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.registerSimpleForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="registerSimpleForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="registerSimpleForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="registerSimpleForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

    <div id="registerSimpleForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RegisterSimple.getId()%>">

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
	List list_RegisterSimple = new ArrayList();
	RegisterSimpleDS ds_RegisterSimple = RegisterSimpleDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_RegisterSimple = ds_RegisterSimple.getAll();
	else		
    	list_RegisterSimple = ds_RegisterSimple.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_RegisterSimple, numDisplayInPage, listPage);

	list_RegisterSimple = PagingUtil.getPagedList(pagingInfo, list_RegisterSimple);
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

    <td class="columnTitle">  First Name </td> 
    <td class="columnTitle">  Last Name </td> 
    <td class="columnTitle">  Email </td> 
    <td class="columnTitle">  Username </td> 
    <td class="columnTitle">  Password </td> 
    <td class="columnTitle">  Birth Year </td> 
    <td class="columnTitle">  Birth Month </td> 
    <td class="columnTitle">  Birth Day </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_RegisterSimple.iterator();iter.hasNext();) {
        RegisterSimple o_RegisterSimple = (RegisterSimple) iter.next();
%>

<TR id="tableRow<%= o_RegisterSimple.getId()%>">
    <td> <%= o_RegisterSimple.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_RegisterSimple.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_RegisterSimple.getFirstName()  %> </td>
	<td> <%= o_RegisterSimple.getLastName()  %> </td>
	<td> <%= o_RegisterSimple.getEmail()  %> </td>
	<td> <%= o_RegisterSimple.getUsername()  %> </td>
	<td> <%= o_RegisterSimple.getPassword()  %> </td>
	<td> <%= o_RegisterSimple.getBirthYear()  %> </td>
	<td> <%= o_RegisterSimple.getBirthMonth()  %> </td>
	<td> <%= o_RegisterSimple.getBirthDay()  %> </td>
	<td> <%= o_RegisterSimple.getTimeCreated()  %> </td>
	<td> <%= o_RegisterSimple.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_register_simple_form('<%=o_RegisterSimple.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/registerSimpleAction.html?del=true&id=<%=o_RegisterSimple.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_RegisterSimple.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_register_simple_form('<%=o_RegisterSimple.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_register_simple_form(target){
		location.href='/v_register_simple_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target;
	}
	function delete_register_simple_form(target){
		javascript:sendFormAjaxSimple('/registerSimpleAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/registerSimpleAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 10000);

//		setTimeout(function(){
//		}, 10000);
	});
</script>
