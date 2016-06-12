<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	AutositeUserDS ds = AutositeUserDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 

<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->

<h3> form displayed by script (request type getscriptform or getmodalform2 </h3>
<script type="text/javascript" src="/autositeUserAction.html?ajxr=getscriptform"></script>

</div>


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_autosite_user_form.html?prv_returnPage=autosite_user_home"> Add New </a> |
            <a href="v_autosite_user_list.html?"> List Page </a> |
            <a href="v_autosite_user_ajax.html?"> Ajax Sampler </a> |
        </TD>
    </TR>
    <TR>
        <TD>
        	AJAX | 
			<a href="/autositeUserAction.html?ajxr=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox"> open form (custom field list)</a> |
			<a href="/autositeUserAction.html?ajxr=getmodalform" 			rel="facebox"> open form</a> |
			<a href="/autositeUserAction.html?ajxr=getlisthtml"  			rel="facebox"> getlisthtml</a> |
			<a href="/autositeUserAction.html?ajxr=getlistjson"  			rel="facebox"> getlistjson</a> |
			<a href="/autositeUserAction.html?ajxr=getjson&ajaxOutArg=first" rel="facebox"> getjson first</a> |
			<a href="/autositeUserAction.html?ajxr=getjson&ajaxOutArg=last" 	rel="facebox"> getjson last</a> |
			<a href="/autositeUserAction.html?ajxr=getlistdata" 				rel="facebox"> getlistdata</a> |
		</TD>        

    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="autositeUserForm_username_label" style="font-size: normal normal bold 10px verdana;" >Username </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_password_label" style="font-size: normal normal bold 10px verdana;" >Password </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_userType_label" style="font-size: normal normal bold 10px verdana;" >User Type </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_firstName_label" style="font-size: normal normal bold 10px verdana;" >First Name </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_lastName_label" style="font-size: normal normal bold 10px verdana;" >Last Name </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_nickname_label" style="font-size: normal normal bold 10px verdana;" >Nickname </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_timeUpdated_label" style="font-size: normal normal bold 10px verdana;" >Time Updated </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_disabled_label" style="font-size: normal normal bold 10px verdana;" >Disabled </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_timeDisabled_label" style="font-size: normal normal bold 10px verdana;" >Time Disabled </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_confirmed_label" style="font-size: normal normal bold 10px verdana;" >Confirmed </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_timeConfirmed_label" style="font-size: normal normal bold 10px verdana;" >Time Confirmed </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_opt1_label" style="font-size: normal normal bold 10px verdana;" >Opt 1 </div>
    </td> 
    <td> 
	    <div id="autositeUserForm_opt2_label" style="font-size: normal normal bold 10px verdana;" >Opt 2 </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%
	String fieldString = "";
    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeUser _AutositeUser = (AutositeUser) iter.next();
		//TODO 
        fieldString += "\"" +  _AutositeUser.getId() + "\",";
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeUser.getId() %> </td>


    <td> 
	<form name="autositeUserFormEditField_Username_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_username_field">
	    <div id="autositeUserForm_username_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="username" value="<%=WebUtil.display(_AutositeUser.getUsername())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="username_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getUsername() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Username_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'username', '<%=_AutositeUser.getUsername()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="username">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="username">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Password_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_password_field">
	    <div id="autositeUserForm_password_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="password" value="<%=WebUtil.display(_AutositeUser.getPassword())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="password_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getPassword() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Password_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'password', '<%=_AutositeUser.getPassword()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="password">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="password">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Email_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_email_field">
	    <div id="autositeUserForm_email_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="email" value="<%=WebUtil.display(_AutositeUser.getEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="email_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getEmail() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Email_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'email', '<%=_AutositeUser.getEmail()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="email">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="email">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_UserType_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_userType_field">
	    <div id="autositeUserForm_userType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="userType" value="<%=WebUtil.display(_AutositeUser.getUserType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="userType_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getUserType() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_UserType_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'userType', '<%=_AutositeUser.getUserType()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="userType">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="userType">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_FirstName_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_firstName_field">
	    <div id="autositeUserForm_firstName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="firstName" value="<%=WebUtil.display(_AutositeUser.getFirstName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="firstName_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getFirstName() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_FirstName_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'firstName', '<%=_AutositeUser.getFirstName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="firstName">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="firstName">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_LastName_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_lastName_field">
	    <div id="autositeUserForm_lastName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="lastName" value="<%=WebUtil.display(_AutositeUser.getLastName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="lastName_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getLastName() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_LastName_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'lastName', '<%=_AutositeUser.getLastName()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="lastName">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="lastName">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Nickname_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_nickname_field">
	    <div id="autositeUserForm_nickname_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="nickname" value="<%=WebUtil.display(_AutositeUser.getNickname())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="nickname_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getNickname() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Nickname_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'nickname', '<%=_AutositeUser.getNickname()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="nickname">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="nickname">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>


    <td> 
	<form name="autositeUserFormEditField_TimeUpdated_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_timeUpdated_field">
	    <div id="autositeUserForm_timeUpdated_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeUpdated" value="<%=WebUtil.display(_AutositeUser.getTimeUpdated())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeUpdated_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getTimeUpdated() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_TimeUpdated_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'timeUpdated', '<%=_AutositeUser.getTimeUpdated()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeUpdated">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeUpdated">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Disabled_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_disabled_field">
	    <div id="autositeUserForm_disabled_dropdown" class="formFieldDropDown" >       
	        <select name="disabled">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _AutositeUser.getDisabled())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _AutositeUser.getDisabled())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="disabled_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getDisabled() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Disabled_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'disabled', '<%=_AutositeUser.getDisabled()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="disabled">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="disabled">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_TimeDisabled_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_timeDisabled_field">
	    <div id="autositeUserForm_timeDisabled_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeDisabled" value="<%=WebUtil.display(_AutositeUser.getTimeDisabled())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeDisabled_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getTimeDisabled() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_TimeDisabled_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'timeDisabled', '<%=_AutositeUser.getTimeDisabled()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeDisabled">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeDisabled">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Confirmed_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_confirmed_field">
	    <div id="autositeUserForm_confirmed_dropdown" class="formFieldDropDown" >       
	        <select name="confirmed">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _AutositeUser.getConfirmed())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _AutositeUser.getConfirmed())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="confirmed_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getConfirmed() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Confirmed_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'confirmed', '<%=_AutositeUser.getConfirmed()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="confirmed">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="confirmed">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_TimeConfirmed_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_timeConfirmed_field">
	    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="timeConfirmed" value="<%=WebUtil.display(_AutositeUser.getTimeConfirmed())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="timeConfirmed_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getTimeConfirmed() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_TimeConfirmed_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'timeConfirmed', '<%=_AutositeUser.getTimeConfirmed()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="timeConfirmed">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="timeConfirmed">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Opt1_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_opt1_field">
	    <div id="autositeUserForm_opt1_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="opt1" value="<%=WebUtil.display(_AutositeUser.getOpt1())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="opt1_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getOpt1() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Opt1_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'opt1', '<%=_AutositeUser.getOpt1()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="opt1">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="opt1">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td> 
	<form name="autositeUserFormEditField_Opt2_<%=_AutositeUser.getId()%>" method="get" action="/autositeUserAction.html" >


		<div id="autositeUserForm_opt2_field">
	    <div id="autositeUserForm_opt2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="opt2" value="<%=WebUtil.display(_AutositeUser.getOpt2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
			<div id="opt2_<%= _AutositeUser.getId()%>"><%=_AutositeUser.getOpt2() %></div>
            <a id="formSubmit" href="javascript:document.autositeUserFormEditField_Opt2_<%=_AutositeUser.getId()%>.submit();">Submit</a>
            <br>
		    <a href="javascript:;" title="UpdatePrompt" class="deleteLink" onclick="update_cleaner_pickup_field_dialog('<%=_AutositeUser.getId()%>', 'opt2', '<%=_AutositeUser.getOpt2()%>');">
	    	EditPrompt
	    	</a>
			<br> <!-- update by ajax call -->
			<a id="update_field_by_ajax" href="#" rel="opt2">	Update </a>
			<a id="update_field_by_ajax_open_reply" href="#" rel="opt2">	Update </a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="autosite_user_home">
	</form>
    
    
    </td>

    <td>
    <form name="autositeUserForm<%=_AutositeUser.getId()%>2" method="get" action="/v_autosite_user_form.html" >
        <a href="javascript:document.autositeUserForm<%=_AutositeUser.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeUser.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="autosite_user_home">
    </form>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_autosite_user_form('<%=_AutositeUser.getId()%>');">Edit</a>
    </td>

    <td>
    <a href="/autositeUserAction.html?del=true&id=<%=_AutositeUser.getId()%>"> Delete </a><BR>
    <a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_autosite_user('<%=_AutositeUser.getId()%>');">DeleteWConfirm</a>
    </td>
</TR>

<%
    }
	if ( fieldString != null && fieldString.length() > 0 )
	fieldString = fieldString.substring(0, fieldString.length()-1);

%>
</TABLE>

<script type="text/javascript">

	function edit_autosite_user_form(target){
		location.href='/v_autosite_user_form.html?cmd=edit&prv_returnPage=autosite_user_home&id=' + target;
	}

	function confirm_autosite_user(target, txt){
		$ .prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=target;
				}
			}
		});
	}
	function confirm_remove_autosite_user(target){
		$ .prompt('Are you sure you want to remove this?',{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href="/autositeUserAction.html?del=true&id="+target;
				}
			}
		});
	}
	// 20120226 
	// On the list, added a little stupid fuction to prompt the change of values
	function update_cleaner_pickup_field_dialog(targetId, targetField, targetValue){
		var txt = 'Change field value for'+targetField +':<br/> <input type="text" id="alertName" name="myname" value="'+ targetValue +'" />';
		$ .prompt(txt,{ 
			buttons:{Submit:true, Cancel:false},
			callback: function(v,m,f){
				if (v){
					if (f.myname == "") {
						alert("Enter");
						return false;
					} else {
						location.href="/autositeUserAction.html?editfield=true&returnPage=cleaner_pickup_home&id="+targetId+"&"+targetField +"=" +f.myname;
					}
				}
				return true;
			}
		});
	}

	// Functions to update field in the list via ajax
	// This is primitive but field "update_field_by_ajax" should be right next level of form.
	// Because it uses parent to access to id and field name 20120226
	$(document).ready(function(){

		$("a#update_field_by_ajax").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			$ .ajax({
			   type: "GET",
		   		url: "/userManageAction.html?editfield=true&ajxr=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		     		$("#" + _fieldName+"_"+_id).text(msg);
		   		}
	 		});
			
			return false;
		});

		$("a#update_field_by_ajax_open_reply").click(function () {

	    	var _id = $(this).parent().find('input[name=id]').val();
	    	var _val = $(this).parent().find('input[id=field]').val();
	    	var _fieldName = $(this).attr("rel");
			
			 $(this).css("background-color", "red");

	    	$ .ajax({
			   type: "GET",
		   		url: "/userManageAction.html?editfield=true&ajaxRequest=true&ajaxOut=getfieldbyname&id="+_id+"&"+_fieldName+"="+ _val+"&fieldname=" + _fieldName,
				beforeSend: function(jqXHR, settings){
					//alert("sending");
				},
				complete: function(jqXHR, textStatus){
					//alert(textStatus);
				},
		  		success: function(msg){ 
		     		$("#" + _fieldName+"_"+_id).text(msg);
		    		$ .prompt("Value updated Success fully",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		},
		   		error: function(msg){
		    		$ .prompt("Request error",{ 
		    			buttons:{Submit:true},
		    			callback: function(v,m,f){ 
		    				if (v){
		    					if (f.myname == "") {
		    						return false;
		    					} else {
		    						//location.href="http://www.yahoo.com";
		    					}
		    				}
		    				return true;
		    			}
		    		});
		   		}
	 		});
			
			return false;
		});
	});
</script>


<script type="text/javascript" charset="utf-8">
// This Javascript is granted to the public domain.

// This is the javascript array holding the function list
// The PrintJavascriptArray ASP function can be used to print this array.
//var functionlist = Array("abs",
//"acos","acosh","addcslashes","addslashes","aggregate","stream_context_create",
//"swf_startbutton","swfmovie.remove","swfmovie.save","swftext.getwidth","swftext.moveto","sybase_fetch_field","sybase_fetch_object","tanh","tempnam",
//"textdomain","time","udm_errno","udm_error",
//"unset","urldecode","urlencode","user_error","usleep","usort","utf8_decode",
//"utf8_encode","var_dump","vpopmail_error","vpopmail_passwd","vpopmail_set_user_quota","vprintf","vsprintf","xml_parser_create","xml_parser_create_ns",
//"xml_parser_free","xmlrpc_server_add_introspection_data","xmlrpc_server_call_method","xmlrpc_server_create","xmlrpc_server_destroy","xmlrpc_server_register_introspection_callback","yaz_connect","yaz_database","yaz_element",
//"yaz_errno","yp_order","zend_logo_guid","zend_version","zip_close","zip_open","zip_read");



var functionlist = Array(<%=fieldString%>);



// This is the function that refreshes the list after a keypress.
// The maximum number to show can be limited to improve performance with
// huge lists (1000s of entries).
// The function clears the list, and then does a linear search through the
// globally defined array and adds the matches back to the list.
function handleKeyUp(maxNumToShow)
{
    var selectObj, textObj, functionListLength;
    var i, searchPattern, numShown;

	if (document.getElementById('auto-complete-input') == null){
		alert("Client side Error occurred. Please try again.");
		return;
	}
    
    // Set references to the form elements
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    // Remember the function list length for loop speedup
    functionListLength = functionlist.length;

    // Set the search pattern depending
    if(document.getElementById('auto-complete-input').functionradio[0].checked == true)
    {
        searchPattern = "^"+textObj.value;
    }
    else
    {
        searchPattern = textObj.value;
    }

    // Create a regulare expression
    re = new RegExp(searchPattern,"gi");
    // Clear the options list
    selectObj.length = 0;

    // Loop through the array and re-add matching options
    numShown = 0;
    for(i = 0; i < functionListLength; i++)
    {
        if(functionlist[i].search(re) != -1)
        {
            selectObj[numShown] = new Option(functionlist[i],"");
            numShown++;
        }
        // Stop when the number to show is reached
        if(numShown == maxNumToShow)
        {
            break;
        }
    }
    // When options list whittled to one, select that entry
    if(selectObj.length == 1)
    {
        selectObj.options[0].selected = true;
    }
}

// this function gets the selected value and loads the appropriate
// php reference page in the display frame
// it can be modified to perform whatever action is needed, or nothing
function handleSelectClick()
{
    selectObj = document.getElementById('auto-complete-input').functionselect;
    textObj = document.getElementById('auto-complete-input').functioninput;

    selectedValue = selectObj.options[selectObj.selectedIndex].text;

    selectedValue = selectedValue.replace(/_/g, '-') ;
    document.location.href = 
	"http://www.php.net/manual/en/function."+selectedValue+".php";

}
function encode_utf8( string )
{
	string = string.replace(/\r\n/g,"\n");
	var utftext = "";

	for (var n = 0; n < string.length; n++) {

		var c = string.charCodeAt(n);

		if (c < 128) {
			utftext += String.fromCharCode(c);
		}
		else if((c > 127) && (c < 2048)) {
			utftext += String.fromCharCode((c >> 6) | 192);
			utftext += String.fromCharCode((c & 63) | 128);
		}
		else {
			utftext += String.fromCharCode((c >> 12) | 224);
			utftext += String.fromCharCode(((c >> 6) & 63) | 128);
			utftext += String.fromCharCode((c & 63) | 128);
		}

	}

	return utftext;
}

function decode_utf8( s )
{
  return decodeURIComponent( escape( s ) );
}
</script>



<table style="margin:auto;">
<tr>
	<td valign="top">
		<b>Search For Function Name</b>
		
		<form onSubmit="handleSelectClick();return false;" action="#" id='auto-complete-input'>

			<input type="radio" name="functionradio" checked>Starting With<br>
			<input type="radio" name="functionradio">Containing<br>
			<input  onKeyUp="handleKeyUp(20);" type="text" name="functioninput" VALUE="" style="font-size:10pt;width:34ex;"><br>
		
			<select onClick="handleSelectClick();" name="functionselect" size="20" style="font-size:10pt;width:34ex;">
			</select>
			<br>
			<input type="button" onClick="handleKeyUp(9999999);" value="Load All Matches">
		</form>
	</td>
</tr>

<tr>
	<td valign="top">
		<select>
		  <option>Volvo</option>
		  <option>Saab</option>
		  <option>Mercedes</option>
		  <option>Audi</option>
		</select>
	</td>
</tr>

</table>