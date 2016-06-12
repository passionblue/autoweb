<%@page language="java" import="java.util.*,com.jtrend.session.*,com.autosite.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);
	

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    PageView pageView = (PageView) request.getAttribute("k_view_pageview");
    
    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    RegisterSimple _RegisterSimpleDefault = new RegisterSimple();// RegisterSimpleDS.getInstance().getDeafult();
    
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_RegisterSimpleDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_RegisterSimpleDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_RegisterSimpleDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _usernameValue= (reqParams.get("username")==null?WebUtil.display(_RegisterSimpleDefault.getUsername()):WebUtil.display((String)reqParams.get("username")));
    String _user_typeValue= (reqParams.get("userType")==null?WebUtil.display(_RegisterSimpleDefault.getUserType()):WebUtil.display((String)reqParams.get("userType")));
    String _passwordValue= (reqParams.get("password")==null?WebUtil.display(_RegisterSimpleDefault.getPassword()):WebUtil.display((String)reqParams.get("password")));
    String _birth_yearValue= (reqParams.get("birthYear")==null?WebUtil.display(_RegisterSimpleDefault.getBirthYear()):WebUtil.display((String)reqParams.get("birthYear")));
    String _birth_monthValue= (reqParams.get("birthMonth")==null?WebUtil.display(_RegisterSimpleDefault.getBirthMonth()):WebUtil.display((String)reqParams.get("birthMonth")));
    String _birth_dayValue= (reqParams.get("birthDay")==null?WebUtil.display(_RegisterSimpleDefault.getBirthDay()):WebUtil.display((String)reqParams.get("birthDay")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_RegisterSimpleDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_RegisterSimpleDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="registerSimpleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="registerSimpleForm" method="POST" action="/registerSimpleAction.html?ActionGroup=ChurApp" >

	<div id="registerSimpleForm_email_field">
    <div id="registerSimpleForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="registerSimpleForm_email_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="50" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	
	<div id="registerSimpleForm_password_field">
    <div id="registerSimpleForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="registerSimpleForm_password_text" class="formFieldText" >       
        <input id="requiredField" type="password" size="30" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="registerSimpleForm_password_field">
    <div id="registerSimpleForm_password_label" class="formRequiredLabel" >Password Retype* </div>
    <div id="registerSimpleForm_password_text" class="formFieldText" >       
        <input id="requiredField" type="password" size="30" name="password2" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="registerSimpleForm_userType_field" class="formFieldFrame">
    <div id="registerSimpleForm_userType_label" class="formLabel" >User Type </div>
    <div id="registerSimpleForm_userType_dropdown" class="formFieldDropDown" >       
        <select class="field" name="userType" id="userType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _user_typeValue)%>>XX</option-->
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _user_typeValue)%>>View Only User</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _user_typeValue)%>>Enter User</option>
        <option value="11" <%=HtmlUtil.getOptionSelect("11", _user_typeValue)%>>Admin Level</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>

    <INPUT TYPE="HIDDEN" NAME="birthDay" value="<%=WebUtil.display(_birth_dayValue)%>" />

        <div id="registerSimpleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.registerSimpleForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="fwdTo" value="chur_register_add">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->



<br><br><br><br>


<%
	if (!sessionContext.isSiteAdmin())
		return; 
%>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = AutositeUserDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeUser _AutositeUser = (AutositeUser) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeUser.getId() %> </td>

    <td> <%= WebUtil.display(_AutositeUser.getFirstName()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getLastName()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getEmail()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getUsername()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getUserType()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getNickname()) %></td>
    <td> <%= WebUtil.display(_AutositeUser.getTimeCreated()) %></td>

	<td>
		<a href=""> Edit (Need to fix) </a>
	</td>
	<td>
		<a href=""> Delete (Need to fix) 
		</a>
		
		<a href="javascript:;" title="Delete User" class="deleteLink" onclick="confirm_remove_autosite_user('<%=_AutositeUser.getId()%>');">DeleteWConfirm</a>
		
	</td>
</TR>
<%
    }
%>
</TABLE>

<script type="text/javascript">
function confirm_remove_autosite_user(target){
	$ .prompt('Are you sure you want to remove this?',{ 
		buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			if(v){
				//location.href="/autositeUserAction.html?del=true&id="+target + "&fromto=<%=pageView.getAlias()%>";
				location.href="/autositeUserAction.html?del=true&id="+target + "<%=HttpUrlUtil.getCommonUrlAppends(request)%>";
			}
		}
	});
}
</script>


