<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentConfig _ContentConfigDefault = new ContentConfig();// ContentConfigDS.getInstance().getDeafult();
    
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentConfigDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _keywordsValue= (reqParams.get("keywords")==null?WebUtil.display(_ContentConfigDefault.getKeywords()):WebUtil.display((String)reqParams.get("keywords")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ContentConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "content_config_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="contentConfigForm_topArea" class="formTopArea"></div>
<div id="contentConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="contentConfigForm" method="POST" action="/contentConfigAction.html" >




	<div id="contentConfigForm_contentId_field">
    <div id="contentConfigForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentConfigForm_contentId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="contentConfigForm_keywords_field">
    <div id="contentConfigForm_keywords_label" class="formLabel" >Keywords </div>
    <div id="contentConfigForm_keywords_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="keywords" value="<%=WebUtil.display(_keywordsValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="contentConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentConfigForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="contentConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentConfig _ContentConfig = (ContentConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentConfig.getId() %> </td>

    <td> <%= WebUtil.display(_ContentConfig.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentConfig.getKeywords()) %></td>
    <td> <%= WebUtil.display(_ContentConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_ContentConfig.getTimeUpdated()) %></td>


<td>
<form name="contentConfigForm<%=_ContentConfig.getId()%>2" method="get" action="/v_content_config_edit2.html" >
    <a href="javascript:document.contentConfigForm<%=_ContentConfig.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentConfig.getId() %>">
</form>

</td>
<td>
<a href="/contentConfigAction.html?del=true&id=<%=_ContentConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>