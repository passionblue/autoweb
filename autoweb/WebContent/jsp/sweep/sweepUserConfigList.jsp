<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	SweepUserConfigDS ds = SweepUserConfigDS.getInstance();    
    list = ds.getBySiteId(site.getId());


%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_sweep_user_config_add2.html?prv_returnPage=sweep_user_config_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">
    <td> ID </td>

    <td> 
	    <div id="sweepUserConfigForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="sweepUserConfigForm_maxSweepAllowed_label" style="font-size: normal normal bold 10px verdana;" >Max Sweep Allowed </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepUserConfig _SweepUserConfig = (SweepUserConfig) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _SweepUserConfig.getId() %> </td>
    
	<td> <%= _SweepUserConfig.getUserId()  %> </td>
	<td> <%= _SweepUserConfig.getMaxSweepAllowed()  %> </td>
</TR>

<%
    }
%>
</TABLE>