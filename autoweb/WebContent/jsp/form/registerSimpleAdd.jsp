<%@page language="java" import="java.util.*,com.jtrend.session.*,com.autosite.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);
	

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    RegisterSimple _RegisterSimpleDefault = new RegisterSimple();// RegisterSimpleDS.getInstance().getDeafult();
    
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_RegisterSimpleDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_RegisterSimpleDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_RegisterSimpleDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _usernameValue= (reqParams.get("username")==null?WebUtil.display(_RegisterSimpleDefault.getUsername()):WebUtil.display((String)reqParams.get("username")));
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
<form name="registerSimpleForm" method="POST" action="/registerSimpleAction.html" >

	<div id="registerSimpleForm_firstName_field">
    <div id="registerSimpleForm_firstName_label" class="formRequiredLabel" >First Name* </div>
    <div id="registerSimpleForm_firstName_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_lastName_field">
    <div id="registerSimpleForm_lastName_label" class="formRequiredLabel" >Last Name* </div>
    <div id="registerSimpleForm_lastName_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_email_field">
    <div id="registerSimpleForm_email_label" class="formRequiredLabel" >Email* </div>
    <div id="registerSimpleForm_email_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="50" name="email" value="<%=WebUtil.display(_emailValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_username_field">
    <div id="registerSimpleForm_username_label" class="formRequiredLabel" >Username* </div>
    <div id="registerSimpleForm_username_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="username" value="<%=WebUtil.display(_usernameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_password_field">
    <div id="registerSimpleForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="registerSimpleForm_password_text" class="formFieldText" >       
        <input id="requiredField" type="password" size="30" name="password" value="<%=WebUtil.display(_passwordValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="registerSimpleForm_birthYear_field">
    <div id="registerSimpleForm_birthYear_label" class="formLabel" >Birth Year </div>
    <div id="registerSimpleForm_birthYear_dropdown" class="formFieldDropDown" >       
        <select id="field" name="birthYear">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _birth_yearValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_birthMonth_field">
    <div id="registerSimpleForm_birthMonth_label" class="formLabel" >Birth Month </div>
    <div id="registerSimpleForm_birthMonth_dropdown" class="formFieldDropDown" >       
        <select id="field" name="birthMonth">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _birth_monthValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


    <INPUT TYPE="HIDDEN" NAME="birthDay" value="<%=WebUtil.display(_birth_dayValue)%>" />

        <div id="registerSimpleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.registerSimpleForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->

<%
	if (!sessionContext.isSuperAdmin())
		return; 
%>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = RegisterSimpleDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        RegisterSimple _RegisterSimple = (RegisterSimple) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _RegisterSimple.getId() %> </td>

    <td> <%= WebUtil.display(_RegisterSimple.getFirstName()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getLastName()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getEmail()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getUsername()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getPassword()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getBirthYear()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getBirthMonth()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getBirthDay()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_RegisterSimple.getTimeUpdated()) %></td>


<td>
<form name="registerSimpleForm<%=_RegisterSimple.getId()%>" method="get" action="/v_register_simple_edit.html" >
    <a href="javascript:document.registerSimpleForm<%=_RegisterSimple.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _RegisterSimple.getId() %>">
</form>
<form name="registerSimpleForm<%=_RegisterSimple.getId()%>2" method="get" action="/v_register_simple_edit2.html" >
    <a href="javascript:document.registerSimpleForm<%=_RegisterSimple.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _RegisterSimple.getId() %>">
</form>

</td>
<td>
<a href="/registerSimpleAction.html?del=true&id=<%=_RegisterSimple.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>