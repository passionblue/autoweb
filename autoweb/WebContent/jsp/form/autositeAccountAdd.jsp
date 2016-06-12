<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    AutositeAccount _AutositeAccountDefault = new AutositeAccount();// AutositeAccountDS.getInstance().getDeafult();
    
    String _account_numValue= (reqParams.get("accountNum")==null?WebUtil.display(_AutositeAccountDefault.getAccountNum()):WebUtil.display((String)reqParams.get("accountNum")));
    String _enabledValue= (reqParams.get("enabled")==null?WebUtil.display(_AutositeAccountDefault.getEnabled()):WebUtil.display((String)reqParams.get("enabled")));
    String _email_confirmedValue= (reqParams.get("emailConfirmed")==null?WebUtil.display(_AutositeAccountDefault.getEmailConfirmed()):WebUtil.display((String)reqParams.get("emailConfirmed")));
    String _time_confirmedValue= (reqParams.get("timeConfirmed")==null?WebUtil.display(_AutositeAccountDefault.getTimeConfirmed()):WebUtil.display((String)reqParams.get("timeConfirmed")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_AutositeAccountDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="autositeAccountForm" method="post" action="/autositeAccountAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Account Num</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="accountNum" value="<%=WebUtil.display(_account_numValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Enabled</b> &nbsp;</TD>
        <TD>&nbsp;<select name="enabled">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _enabledValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _enabledValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Email Confirmed</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="emailConfirmed" value="<%=WebUtil.display(_email_confirmedValue)%>"/></TD>
	    </TR>
	            	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.autositeAccountForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = AutositeAccountDS.getInstance().getAll();
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        AutositeAccount _AutositeAccount = (AutositeAccount) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _AutositeAccount.getId() %> </td>

    <td> <%= WebUtil.display(_AutositeAccount.getAccountNum()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getEnabled()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getEmailConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getTimeConfirmed()) %></td>
    <td> <%= WebUtil.display(_AutositeAccount.getTimeCreated()) %></td>


<td>
<form name="autositeAccountForm<%=_AutositeAccount.getId()%>" method="post" action="/v_autosite_account_edit.html" >
    <a href="javascript:document.autositeAccountForm<%=_AutositeAccount.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _AutositeAccount.getId() %>">
</form>
</td>
<td>
<a href="/autositeAccountAction.html?del=true&id=<%=_AutositeAccount.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>