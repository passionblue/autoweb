<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	RegisterSimple _RegisterSimple = RegisterSimpleDS.getInstance().getById(id);

	if ( _RegisterSimple == null ) {
		return;
	}

	String _first_nameValue=  WebUtil.display(_RegisterSimple.getFirstName());
	String _last_nameValue=  WebUtil.display(_RegisterSimple.getLastName());
	String _emailValue=  WebUtil.display(_RegisterSimple.getEmail());
	String _usernameValue=  WebUtil.display(_RegisterSimple.getUsername());
	String _passwordValue=  WebUtil.display(_RegisterSimple.getPassword());
	String _birth_yearValue=  WebUtil.display(_RegisterSimple.getBirthYear());
	String _birth_monthValue=  WebUtil.display(_RegisterSimple.getBirthMonth());
	String _birth_dayValue=  WebUtil.display(_RegisterSimple.getBirthDay());
	String _time_createdValue=  WebUtil.display(_RegisterSimple.getTimeCreated());
	String _time_updatedValue=  WebUtil.display(_RegisterSimple.getTimeUpdated());
%> 

<br>
<form name="registerSimpleFormEdit" method="post" action="/registerSimpleAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

	
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>First Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="firstName" value="<%=WebUtil.display(_first_nameValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Last Name</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="lastName" value="<%=WebUtil.display(_last_nameValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Email</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Username</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="username" value="<%=WebUtil.display(_usernameValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Password</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="password" value="<%=WebUtil.display(_passwordValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Birth Year</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="birthYear" value="<%=WebUtil.display(_birth_yearValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Birth Month</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="birthMonth" value="<%=WebUtil.display(_birth_monthValue)%>"/></TD>
    </TR>
			
	    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Birth Day</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="birthDay" value="<%=WebUtil.display(_birth_dayValue)%>"/></TD>
    </TR>
			
			
		    <TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.registerSimpleFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_RegisterSimple.getId()%>">
</form>
