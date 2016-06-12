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

    ContentFeedSite _ContentFeedSite = ContentFeedSiteDS.getInstance().getById(id);

    if ( _ContentFeedSite == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _content_feed_idValue=  WebUtil.display(_ContentFeedSite.getContentFeedId());
    String _display_typeValue=  WebUtil.display(_ContentFeedSite.getDisplayType());
    String _time_createdValue=  WebUtil.display(_ContentFeedSite.getTimeCreated());
%> 

<br>
<div id="contentFeedSiteForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentFeedSiteFormEdit" method="get" action="/contentFeedSiteAction.html" >




	<div id="contentFeedSiteForm_contentFeedId_field">
    <div id="contentFeedSiteForm_contentFeedId_label" class="formLabel" >Content Feed Id </div>
    <div id="contentFeedSiteForm_contentFeedId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="contentFeedId" value="<%=WebUtil.display(_content_feed_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentFeedSiteForm_displayType_field">
    <div id="contentFeedSiteForm_displayType_label" class="formLabel" >Display Type </div>
    <div id="contentFeedSiteForm_displayType_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="displayType" value="<%=WebUtil.display(_display_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="contentFeedSiteFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentFeedSiteFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedSite.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
