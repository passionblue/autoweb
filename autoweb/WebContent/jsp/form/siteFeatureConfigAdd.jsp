<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SiteFeatureConfig _SiteFeatureConfigDefault = new SiteFeatureConfig();// SiteFeatureConfigDS.getInstance().getDeafult();
    
    String _user_register_enabledValue= (reqParams.get("userRegisterEnabled")==null?WebUtil.display(_SiteFeatureConfigDefault.getUserRegisterEnabled()):WebUtil.display((String)reqParams.get("userRegisterEnabled")));
    String _ec_enabledValue= (reqParams.get("ecEnabled")==null?WebUtil.display(_SiteFeatureConfigDefault.getEcEnabled()):WebUtil.display((String)reqParams.get("ecEnabled")));
    String _email_subs_enabledValue= (reqParams.get("emailSubsEnabled")==null?WebUtil.display(_SiteFeatureConfigDefault.getEmailSubsEnabled()):WebUtil.display((String)reqParams.get("emailSubsEnabled")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteFeatureConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteFeatureConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="siteFeatureConfigForm" method="post" action="/siteFeatureConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

        <TR bgcolor="#ffffff">
        <TD align="right" ><b>User Register Enabled</b> &nbsp;</TD>
        <TD>&nbsp;<select name="userRegisterEnabled">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _user_register_enabledValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _user_register_enabledValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Ec Enabled</b> &nbsp;</TD>
        <TD>&nbsp;<select name="ecEnabled">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _ec_enabledValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _ec_enabledValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Email Subs Enabled</b> &nbsp;</TD>
        <TD>&nbsp;<select name="emailSubsEnabled">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _email_subs_enabledValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _email_subs_enabledValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.siteFeatureConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SiteFeatureConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteFeatureConfig _SiteFeatureConfig = (SiteFeatureConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SiteFeatureConfig.getId() %> </td>

    <td> <%= WebUtil.display(_SiteFeatureConfig.getUserRegisterEnabled()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getEcEnabled()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getEmailSubsEnabled()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_SiteFeatureConfig.getTimeUpdated()) %></td>


<td>
<form name="siteFeatureConfigForm<%=_SiteFeatureConfig.getId()%>" method="post" action="/v_site_feature_config_edit.html" >
    <a href="javascript:document.siteFeatureConfigForm<%=_SiteFeatureConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SiteFeatureConfig.getId() %>">
</form>
</td>
<td>
<a href="/siteFeatureConfigAction.html?del=true&id=<%=_SiteFeatureConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>