<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcUserAccount _EcUserAccountDefault = new EcUserAccount();// EcUserAccountDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_EcUserAccountDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _first_nameValue= (reqParams.get("firstName")==null?WebUtil.display(_EcUserAccountDefault.getFirstName()):WebUtil.display((String)reqParams.get("firstName")));
    String _last_nameValue= (reqParams.get("lastName")==null?WebUtil.display(_EcUserAccountDefault.getLastName()):WebUtil.display((String)reqParams.get("lastName")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecUserAccountForm" method="post" action="/ecUserAccountAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
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
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecUserAccountForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcUserAccountDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcUserAccount _EcUserAccount = (EcUserAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcUserAccount.getId() %> </td>

    <td> <%= WebUtil.display(_EcUserAccount.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcUserAccount.getFirstName()) %></td>
    <td> <%= WebUtil.display(_EcUserAccount.getLastName()) %></td>


<td>
<form name="ecUserAccountForm<%=_EcUserAccount.getId()%>" method="post" action="/v_ec_user_account_edit.html" >
    <a href="javascript:document.ecUserAccountForm<%=_EcUserAccount.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcUserAccount.getId() %>">
</form>
</td>
<td>
<a href="/ecUserAccountAction.html?del=true&id=<%=_EcUserAccount.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>