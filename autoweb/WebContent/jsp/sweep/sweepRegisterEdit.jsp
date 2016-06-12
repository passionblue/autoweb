<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    SweepRegister _SweepRegister = SweepRegisterDS.getInstance().getById(id);

    if ( _SweepRegister == null ) {
        return;
    }

    String _emailValue=  WebUtil.display(_SweepRegister.getEmail());
    String _passwordValue=  WebUtil.display(_SweepRegister.getPassword());
    String _sexValue=  WebUtil.display(_SweepRegister.getSex());
    String _age_rangeValue=  WebUtil.display(_SweepRegister.getAgeRange());
    String _agree_termsValue=  WebUtil.display(_SweepRegister.getAgreeTerms());
    String _time_createdValue=  WebUtil.display(_SweepRegister.getTimeCreated());
%> 

<br>
<form name="sweepRegisterFormEdit" method="post" action="/sweepRegisterAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Email</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Password</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="password" value="<%=WebUtil.display(_passwordValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Gender</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="sex" value="<%=WebUtil.display(_sexValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Age Range</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="ageRange" value="<%=WebUtil.display(_age_rangeValue)%>"/></TD>
    </TR>
                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Agree Terms</b> &nbsp;</TD>
        <TD>&nbsp; <input type="checkbox" name="agreeTerms" <%=HtmlUtil.getCheckedBoxValue(_agree_termsValue)%> />
        </TD>
    </TR>
        
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.sweepRegisterFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepRegister.getId()%>">
</form>
