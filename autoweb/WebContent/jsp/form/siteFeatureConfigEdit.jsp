<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    SiteFeatureConfig _SiteFeatureConfig = SiteFeatureConfigDS.getInstance().getById(id);

    if ( _SiteFeatureConfig == null ) {
        return;
    }

    String _user_register_enabledValue=  WebUtil.display(_SiteFeatureConfig.getUserRegisterEnabled());
    String _ec_enabledValue=  WebUtil.display(_SiteFeatureConfig.getEcEnabled());
    String _email_subs_enabledValue=  WebUtil.display(_SiteFeatureConfig.getEmailSubsEnabled());
    String _time_createdValue=  WebUtil.display(_SiteFeatureConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_SiteFeatureConfig.getTimeUpdated());
%> 

<br>
<form name="siteFeatureConfigFormEdit" method="post" action="/siteFeatureConfigAction.html" >
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
            <b><a href="javascript:document.siteFeatureConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteFeatureConfig.getId()%>">
</form>
