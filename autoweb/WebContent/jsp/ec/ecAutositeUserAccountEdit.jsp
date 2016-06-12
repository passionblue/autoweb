<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcAutositeUserAccount _EcAutositeUserAccount = EcAutositeUserAccountDS.getInstance().getById(id);

    if ( _EcAutositeUserAccount == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_EcAutositeUserAccount.getUserId());
    String _first_nameValue=  WebUtil.display(_EcAutositeUserAccount.getFirstName());
    String _last_nameValue=  WebUtil.display(_EcAutositeUserAccount.getLastName());
    String _subscribedValue=  WebUtil.display(_EcAutositeUserAccount.getSubscribed());
    String _time_createdValue=  WebUtil.display(_EcAutositeUserAccount.getTimeCreated());
%> 

<br>
<form name="ecAutositeUserAccountFormEdit" method="post" action="/ecAutositeUserAccountAction.html" >
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
            <b><a href="javascript:document.ecAutositeUserAccountFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcAutositeUserAccount.getId()%>">
</form>
