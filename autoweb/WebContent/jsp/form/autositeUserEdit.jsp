<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    AutositeUser _AutositeUser = AutositeUserDS.getInstance().getById(id);

    if ( _AutositeUser == null ) {
        return;
    }

    String _usernameValue=  WebUtil.display(_AutositeUser.getUsername());
    String _passwordValue=  WebUtil.display(_AutositeUser.getPassword());
    String _emailValue=  WebUtil.display(_AutositeUser.getEmail());
    String _user_typeValue=  WebUtil.display(_AutositeUser.getUserType());
    String _first_nameValue=  WebUtil.display(_AutositeUser.getFirstName());
    String _last_nameValue=  WebUtil.display(_AutositeUser.getLastName());
    String _nicknameValue=  WebUtil.display(_AutositeUser.getNickname());
    String _time_createdValue=  WebUtil.display(_AutositeUser.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_AutositeUser.getTimeUpdated());
    String _disabledValue=  WebUtil.display(_AutositeUser.getDisabled());
    String _time_disabledValue=  WebUtil.display(_AutositeUser.getTimeDisabled());
    String _confirmedValue=  WebUtil.display(_AutositeUser.getConfirmed());
    String _time_confirmedValue=  WebUtil.display(_AutositeUser.getTimeConfirmed());
    String _opt_1Value=  WebUtil.display(_AutositeUser.getOpt1());
    String _opt_2Value=  WebUtil.display(_AutositeUser.getOpt2());
%> 

<br>
<form name="autositeUserFormEdit" method="post" action="/autositeUserAction.html" >
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
            <b><a href="javascript:document.autositeUserFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_AutositeUser.getId()%>">
</form>
