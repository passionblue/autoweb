<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    AutositeAccount _AutositeAccount = AutositeAccountDS.getInstance().getById(id);

    if ( _AutositeAccount == null ) {
        return;
    }

    String _account_numValue=  WebUtil.display(_AutositeAccount.getAccountNum());
    String _enabledValue=  WebUtil.display(_AutositeAccount.getEnabled());
    String _email_confirmedValue=  WebUtil.display(_AutositeAccount.getEmailConfirmed());
    String _time_confirmedValue=  WebUtil.display(_AutositeAccount.getTimeConfirmed());
    String _time_createdValue=  WebUtil.display(_AutositeAccount.getTimeCreated());
%> 

<br>
<form name="autositeAccountFormEdit" method="post" action="/autositeAccountAction.html" >
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
            <b><a href="javascript:document.autositeAccountFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeAccount.getId()%>">
</form>
