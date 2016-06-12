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

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="autositeUserForm" method="post" action="/autositeUserAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Username</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="username" value="<%=WebUtil.display(_usernameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Password</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="password" value="<%=WebUtil.display(_passwordValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Email</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userType" value="<%=WebUtil.display(_user_typeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>First Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Last Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Nickname</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="nickname" value="<%=WebUtil.display(_nicknameValue)%>"/></TD>
	    </TR>
	            	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Time Updated</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Disabled</b> &nbsp;</TD>
        <TD>&nbsp;<select name="disabled">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Time Disabled</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="timeDisabled" value="<%=WebUtil.display(_time_disabledValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Confirmed</b> &nbsp;</TD>
        <TD>&nbsp;<select name="confirmed">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _confirmedValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _confirmedValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Time Confirmed</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="timeConfirmed" value="<%=WebUtil.display(_time_confirmedValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Opt 1</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="opt1" value="<%=WebUtil.display(_opt_1Value)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Opt 2</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="opt2" value="<%=WebUtil.display(_opt_2Value)%>"/></TD>
	    </TR>
	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.autositeUserForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="autositeUserForm<%=_AutositeUser.getId()%>" method="post" action="/v_autosite_user_edit.html" >
    <a href="javascript:document.autositeUserForm<%=_AutositeUser.getId()%>.submit();">Edit</a>           
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