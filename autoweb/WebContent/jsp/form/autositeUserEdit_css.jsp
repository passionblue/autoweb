<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    AutositeUser _AutositeUser = AutositeUserDS.getInstance().getById(id);

    if ( _AutositeUser == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _usernameValue=  WebUtil.display(_AutositeUser.getUsername());
    String _passwordValue=  WebUtil.display(_AutositeUser.getPassword());
    String _emailValue=  WebUtil.display(_AutositeUser.getEmail());
    String _user_typeValue=  WebUtil.display(_AutositeUser.getUserType());
    String _first_nameValue=  WebUtil.display(_AutositeUser.getFirstName());
    String _last_nameValue=  WebUtil.display(_AutositeUser.getLastName());
    String _nicknameValue=  WebUtil.display(_AutositeUser.getNickname());
    String _time_createdValue=  WebUtil.display(_AutositeUser.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_AutositeUser.getTimeUpdated());
    String _disabledValue=  WebUtil.display(_AutositeUser.getDisabled());
    String _time_disabledValue=  WebUtil.display(_AutositeUser.getTimeDisabled());
    String _confirmedValue=  WebUtil.display(_AutositeUser.getConfirmed());
    String _time_confirmedValue=  WebUtil.display(_AutositeUser.getTimeConfirmed());
    String _opt_1Value=  WebUtil.display(_AutositeUser.getOpt1());
    String _opt_2Value=  WebUtil.display(_AutositeUser.getOpt2());
%> 

<br>
<div id="autositeUserForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="autositeUserFormEdit" method="get" action="/autositeUserAction.html" >




	<div id="autositeUserForm_username_field">
    <div id="autositeUserForm_username_label" class="formLabel" >Username </div>
    <div id="autositeUserForm_username_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_password_field">
    <div id="autositeUserForm_password_label" class="formLabel" >Password </div>
    <div id="autositeUserForm_password_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_email_field">
    <div id="autositeUserForm_email_label" class="formLabel" >Email </div>
    <div id="autositeUserForm_email_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_userType_field">
    <div id="autositeUserForm_userType_label" class="formLabel" >User Type </div>
    <div id="autositeUserForm_userType_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="userType" value="<%=WebUtil.display(_user_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_firstName_field">
    <div id="autositeUserForm_firstName_label" class="formLabel" >First Name </div>
    <div id="autositeUserForm_firstName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_lastName_field">
    <div id="autositeUserForm_lastName_label" class="formLabel" >Last Name </div>
    <div id="autositeUserForm_lastName_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_nickname_field">
    <div id="autositeUserForm_nickname_label" class="formLabel" >Nickname </div>
    <div id="autositeUserForm_nickname_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="autositeUserForm_timeUpdated_field">
    <div id="autositeUserForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="autositeUserForm_timeUpdated_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/> <span></span>
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
    <div id="autositeUserForm_timeDisabled_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/> <span></span>
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
    <div id="autositeUserForm_timeConfirmed_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_opt1_field">
    <div id="autositeUserForm_opt1_label" class="formLabel" >Opt 1 </div>
    <div id="autositeUserForm_opt1_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="autositeUserForm_opt2_field">
    <div id="autositeUserForm_opt2_label" class="formLabel" >Opt 2 </div>
    <div id="autositeUserForm_opt2_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="opt2" value="<%=WebUtil.display(_opt_2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="autositeUserFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.autositeUserFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
