<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    ContentConfig _ContentConfig = ContentConfigDS.getInstance().getById(id);

    if ( _ContentConfig == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "content_config_home";

    String _content_idValue=  WebUtil.display(_ContentConfig.getContentId());
    String _keywordsValue=  WebUtil.display(_ContentConfig.getKeywords());
    String _time_createdValue=  WebUtil.display(_ContentConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_ContentConfig.getTimeUpdated());
%> 

<br>
<div id="contentConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentConfigFormEdit" method="POST" action="/contentConfigAction.html" >




	<div id="contentConfigForm_contentId_field">
    <div id="contentConfigForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentConfigForm_contentId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentConfigForm_keywords_field">
    <div id="contentConfigForm_keywords_label" class="formLabel" >Keywords </div>
    <div id="contentConfigForm_keywords_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="keywords" value="<%=WebUtil.display(_keywordsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="contentConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentConfigFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
