<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SweepUserConfig _SweepUserConfigDefault = new SweepUserConfig();// SweepUserConfigDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_SweepUserConfigDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _max_sweep_allowedValue= (reqParams.get("maxSweepAllowed")==null?WebUtil.display(_SweepUserConfigDefault.getMaxSweepAllowed()):WebUtil.display((String)reqParams.get("maxSweepAllowed")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="sweepUserConfigForm" method="post" action="/sweepUserConfigAction.html" >
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
            <b><a href="javascript:document.sweepUserConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SweepUserConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepUserConfig _SweepUserConfig = (SweepUserConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepUserConfig.getId() %> </td>

    <td> <%= WebUtil.display(_SweepUserConfig.getUserId()) %></td>
    <td> <%= WebUtil.display(_SweepUserConfig.getMaxSweepAllowed()) %></td>


<td>
<form name="sweepUserConfigForm<%=_SweepUserConfig.getId()%>" method="post" action="/v_sweep_user_config_edit.html" >
    <a href="javascript:document.sweepUserConfigForm<%=_SweepUserConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepUserConfig.getId() %>">
</form>
</td>
<td>
<a href="/sweepUserConfigAction.html?del=true&id=<%=_SweepUserConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>