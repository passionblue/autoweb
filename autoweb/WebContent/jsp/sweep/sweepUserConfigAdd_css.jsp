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

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="sweepUserConfigForm_topArea" class="formTopArea"></div>
<div id="sweepUserConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="sweepUserConfigForm" method="get" action="/sweepUserConfigAction.html" >




	<div id="sweepUserConfigForm_userId_field">
    <div id="sweepUserConfigForm_userId_label" class="formLabel" >User Id </div>
    <div id="sweepUserConfigForm_userId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="sweepUserConfigForm_maxSweepAllowed_field">
    <div id="sweepUserConfigForm_maxSweepAllowed_label" class="formLabel" >Max Sweep Allowed </div>
    <div id="sweepUserConfigForm_maxSweepAllowed_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="maxSweepAllowed" value="<%=WebUtil.display(_max_sweep_allowedValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="sweepUserConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepUserConfigForm.submit();">Submit</a>
        </div>      

        <div id="sweepUserConfigForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.sweepUserConfigForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="sweepUserConfigForm_bottomArea" class="formBottomArea"></div>


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
<form name="sweepUserConfigForm<%=_SweepUserConfig.getId()%>" method="get" action="/v_sweep_user_config_edit.html" >
    <a href="javascript:document.sweepUserConfigForm<%=_SweepUserConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepUserConfig.getId() %>">
</form>
<form name="sweepUserConfigForm<%=_SweepUserConfig.getId()%>2" method="get" action="/v_sweep_user_config_edit2.html" >
    <a href="javascript:document.sweepUserConfigForm<%=_SweepUserConfig.getId()%>2.submit();">Edit2</a>           
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