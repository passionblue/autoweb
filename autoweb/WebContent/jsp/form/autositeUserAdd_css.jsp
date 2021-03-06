<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    AutositeUser _AutositeUserDefault = new AutositeUser();// AutositeUserDS.getInstance().getDeafult();
    
    String _usernameValue= (reqParams.get("username")==null?WebUtil.display(_AutositeUserDefault.getUsername()):WebUtil.display((String)reqParams.get("username")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(_AutositeUserDefault.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_AutositeUserDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _user_typeValue= (reqParams.get("userType")==null?WebUtil.display(_AutositeUserDefault.getUserType()):WebUtil.display((String)reqParams.get("userType")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_AutositeUserDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_AutositeUserDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _nicknameValue= (reqParams.get("nickname")==null?WebUtil.display(_AutositeUserDefault.getNickname()):WebUtil.display((String)reqParams.get("nickname")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_AutositeUserDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_AutositeUserDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _disabledValue= (reqParams.get("disabled")==null?WebUtil.display(_AutositeUserDefault.getDisabled()):WebUtil.display((String)reqParams.get("disabled")));
    String _time_disabledValue= (reqParams.get("timeDisabled")==null?WebUtil.display(_AutositeUserDefault.getTimeDisabled()):WebUtil.display((String)reqParams.get("timeDisabled")));
    String _confirmedValue= (reqParams.get("confirmed")==null?WebUtil.display(_AutositeUserDefault.getConfirmed()):WebUtil.display((String)reqParams.get("confirmed")));
    String _time_confirmedValue= (reqParams.get("timeConfirmed")==null?WebUtil.display(_AutositeUserDefault.getTimeConfirmed()):WebUtil.display((String)reqParams.get("timeConfirmed")));
    String _opt_1Value= (reqParams.get("opt1")==null?WebUtil.display(_AutositeUserDefault.getOpt1()):WebUtil.display((String)reqParams.get("opt1")));
    String _opt_2Value= (reqParams.get("opt2")==null?WebUtil.display(_AutositeUserDefault.getOpt2()):WebUtil.display((String)reqParams.get("opt2")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="autositeUserForm_topArea" class="formTopArea"></div>
<div id="autositeUserForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="autositeUserForm" method="get" action="/autositeUserAction.html" >




	<div id="autositeUserForm_username_field">
    <div id="autositeUserForm_username_label" class="formLabel" >Username </div>
    <div id="autositeUserForm_username_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="username" value="<%=WebUtil.display(_usernameValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_password_field">
    <div id="autositeUserForm_password_label" class="formLabel" >Password </div>
    <div id="autositeUserForm_password_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="password" value="<%=WebUtil.display(_passwordValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_email_field">
    <div id="autositeUserForm_email_label" class="formLabel" >Email </div>
    <div id="autositeUserForm_email_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_userType_field">
    <div id="autositeUserForm_userType_label" class="formLabel" >User Type </div>
    <div id="autositeUserForm_userType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="userType" value="<%=WebUtil.display(_user_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_firstName_field">
    <div id="autositeUserForm_firstName_label" class="formLabel" >First Name </div>
    <div id="autositeUserForm_firstName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_lastName_field">
    <div id="autositeUserForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="autositeUserForm_lastName_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_nickname_field">
    <div id="autositeUserForm_nickname_label" class="formLabel" >Nickname </div>
    <div id="autositeUserForm_nickname_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/>
    </div>      
	</div><div class="clear"></div>








	<div id="autositeUserForm_timeUpdated_field">
    <div id="autositeUserForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="autositeUserForm_timeUpdated_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_disabled_field">
    <div id="autositeUserForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="autositeUserForm_disabled_dropdown" class="formFieldDropDown" >       
        <select name="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeDisabled_field">
    <div id="autositeUserForm_timeDisabled_label" class="formLabel" >Time Disabled </div>
    <div id="autositeUserForm_timeDisabled_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_confirmed_field">
    <div id="autositeUserForm_confirmed_label" class="formLabel" >Confirmed </div>
    <div id="autositeUserForm_confirmed_dropdown" class="formFieldDropDown" >       
        <select name="confirmed">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _confirmedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _confirmedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeConfirmed_field">
    <div id="autositeUserForm_timeConfirmed_label" class="formLabel" >Time Confirmed </div>
    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_opt1_field">
    <div id="autositeUserForm_opt1_label" class="formLabel" >Opt 1 </div>
    <div id="autositeUserForm_opt1_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="autositeUserForm_opt2_field">
    <div id="autositeUserForm_opt2_label" class="formLabel" >Opt 2 </div>
    <div id="autositeUserForm_opt2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="opt2" value="<%=WebUtil.display(_opt_2Value)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="autositeUserForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.autositeUserForm.submit();">Submit</a>
        </div>      

        <div id="autositeUserForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.autositeUserForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="autositeUserForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = AutositeUserDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeUser _AutositeUser = (AutositeUser) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeUser.getId() %> </td>

    <td> <%= WebUtil.display(_AutositeUser.getUsername()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getPassword()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getEmail()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getUserType()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getFirstName()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getLastName()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getNickname()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getTimeUpdated()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getDisabled()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getTimeDisabled()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getTimeConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getOpt1()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getOpt2()) %></td>


<td>
<form name="autositeUserForm<%=_AutositeUser.getId()%>" method="get" action="/v_autosite_user_edit.html" >
    <a href="javascript:document.autositeUserForm<%=_AutositeUser.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeUser.getId() %>">
</form>
<form name="autositeUserForm<%=_AutositeUser.getId()%>2" method="get" action="/v_autosite_user_edit2.html" >
    <a href="javascript:document.autositeUserForm<%=_AutositeUser.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeUser.getId() %>">
</form>

</td>
<td>
<a href="/autositeUserAction.html?del=true&id=<%=_AutositeUser.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>