<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcAnonymousUserAccount _EcAnonymousUserAccount = EcAnonymousUserAccountDS.getInstance().getById(id);

    if ( _EcAnonymousUserAccount == null ) {
        return;
    }

    String _emailValue=  WebUtil.display(_EcAnonymousUserAccount.getEmail());
    String _subscribedValue=  WebUtil.display(_EcAnonymousUserAccount.getSubscribed());
    String _time_createdValue=  WebUtil.display(_EcAnonymousUserAccount.getTimeCreated());
%> 

<br>
<form name="ecAnonymousUserAccountFormEdit" method="post" action="/ecAnonymousUserAccountAction.html" >
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
            <b><a href="javascript:document.ecAnonymousUserAccountFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAnonymousUserAccount.getId()%>">
</form>
