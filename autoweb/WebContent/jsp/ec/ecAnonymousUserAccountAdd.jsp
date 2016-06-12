<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcAnonymousUserAccount _EcAnonymousUserAccountDefault = new EcAnonymousUserAccount();// EcAnonymousUserAccountDS.getInstance().getDeafult();
    
    String _emailValue= (reqParams.get("email")==null?WebUtil.display(_EcAnonymousUserAccountDefault.getEmail()):WebUtil.display((String)reqParams.get("email")));
    String _subscribedValue= (reqParams.get("subscribed")==null?WebUtil.display(_EcAnonymousUserAccountDefault.getSubscribed()):WebUtil.display((String)reqParams.get("subscribed")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcAnonymousUserAccountDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecAnonymousUserAccountForm" method="post" action="/ecAnonymousUserAccountAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Email</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Subscribed</b> &nbsp;</TD>
        <TD>&nbsp;<select name="subscribed">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _subscribedValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _subscribedValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecAnonymousUserAccountForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcAnonymousUserAccountDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcAnonymousUserAccount _EcAnonymousUserAccount = (EcAnonymousUserAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcAnonymousUserAccount.getId() %> </td>

    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getEmail()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getSubscribed()) %></td>
    <td> <%= WebUtil.display(_EcAnonymousUserAccount.getTimeCreated()) %></td>


<td>
<form name="ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>" method="post" action="/v_ec_anonymous_user_account_edit.html" >
    <a href="javascript:document.ecAnonymousUserAccountForm<%=_EcAnonymousUserAccount.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcAnonymousUserAccount.getId() %>">
</form>
</td>
<td>
<a href="/ecAnonymousUserAccountAction.html?del=true&id=<%=_EcAnonymousUserAccount.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>