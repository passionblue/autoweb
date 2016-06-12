<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	User _User = UserDS.getInstance().getById(id);

	if ( _User == null ) {
		return;
	}

	String _usernameValue=  WebUtil.display(_User.getUsername());
	String _passwordValue=  WebUtil.display(_User.getPassword());
	String _emailValue=  WebUtil.display(_User.getEmail());
	String _time_createdValue=  WebUtil.display(_User.getTimeCreated());
	String _time_updatedValue=  WebUtil.display(_User.getTimeUpdated());
%> 

<br>
<form name="userFormEdit" method="post" action="/userAction.html" >
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
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.userFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_User.getId()%>">
</form>
