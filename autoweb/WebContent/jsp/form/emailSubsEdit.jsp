<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EmailSubs _EmailSubs = EmailSubsDS.getInstance().getById(id);

    if ( _EmailSubs == null ) {
        return;
    }

    String _subjectValue=  WebUtil.display(_EmailSubs.getSubject());
    String _emailValue=  WebUtil.display(_EmailSubs.getEmail());
    String _confirmedValue=  WebUtil.display(_EmailSubs.getConfirmed());
    String _disabledValue=  WebUtil.display(_EmailSubs.getDisabled());
    String _check_opt_1Value=  WebUtil.display(_EmailSubs.getCheckOpt1());
    String _check_opt_2Value=  WebUtil.display(_EmailSubs.getCheckOpt2());
    String _extra_infoValue=  WebUtil.display(_EmailSubs.getExtraInfo());
    String _time_createdValue=  WebUtil.display(_EmailSubs.getTimeCreated());
%> 

<br>
<form name="emailSubsFormEdit" method="post" action="/emailSubsAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Subject</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="subject" value="<%=WebUtil.display(_subjectValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Email</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="email" value="<%=WebUtil.display(_emailValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Confirmed</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="confirmed" value="<%=WebUtil.display(_confirmedValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Disabled</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="disabled" value="<%=WebUtil.display(_disabledValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Check Opt 1</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="checkOpt1" value="<%=WebUtil.display(_check_opt_1Value)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Check Opt 2</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="checkOpt2" value="<%=WebUtil.display(_check_opt_2Value)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Extra Info</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="extraInfo" value="<%=WebUtil.display(_extra_infoValue)%>"/></TD>
    </TR>
            
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.emailSubsFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EmailSubs.getId()%>">
</form>
