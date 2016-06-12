<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    SweepUserConfig _SweepUserConfig = SweepUserConfigDS.getInstance().getById(id);

    if ( _SweepUserConfig == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_SweepUserConfig.getUserId());
    String _max_sweep_allowedValue=  WebUtil.display(_SweepUserConfig.getMaxSweepAllowed());
%> 

<br>
<form name="sweepUserConfigFormEdit" method="post" action="/sweepUserConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>User Id</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
    </TR>
            
        <TR bgcolor="#ffffff">
        <TD align="right" ><b>Max Sweep Allowed</b> &nbsp;</TD>
        <TD>&nbsp;<html:text size="70" property="maxSweepAllowed" value="<%=WebUtil.display(_max_sweep_allowedValue)%>"/></TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.sweepUserConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepUserConfig.getId()%>">
</form>
