<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    SweepUserConfig _SweepUserConfig = SweepUserConfigDS.getInstance().getById(id);

    if ( _SweepUserConfig == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _user_idValue=  WebUtil.display(_SweepUserConfig.getUserId());
    String _max_sweep_allowedValue=  WebUtil.display(_SweepUserConfig.getMaxSweepAllowed());
%> 

<br>
<div id="sweepUserConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sweepUserConfigFormEdit" method="get" action="/sweepUserConfigAction.html" >




	<div id="sweepUserConfigForm_userId_field">
    <div id="sweepUserConfigForm_userId_label" class="formLabel" >User Id </div>
    <div id="sweepUserConfigForm_userId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sweepUserConfigForm_maxSweepAllowed_field">
    <div id="sweepUserConfigForm_maxSweepAllowed_label" class="formLabel" >Max Sweep Allowed </div>
    <div id="sweepUserConfigForm_maxSweepAllowed_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="maxSweepAllowed" value="<%=WebUtil.display(_max_sweep_allowedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="sweepUserConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sweepUserConfigFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepUserConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
