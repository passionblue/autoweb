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

    ContentFeedRel _ContentFeedRel = ContentFeedRelDS.getInstance().getById(id);

    if ( _ContentFeedRel == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _content_feed_idValue=  WebUtil.display(_ContentFeedRel.getContentFeedId());
    String _content_idValue=  WebUtil.display(_ContentFeedRel.getContentId());
    String _time_createdValue=  WebUtil.display(_ContentFeedRel.getTimeCreated());
%> 

<br>
<div id="contentFeedRelForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentFeedRelFormEdit" method="get" action="/contentFeedRelAction.html" >




	<div id="contentFeedRelForm_contentFeedId_field">
    <div id="contentFeedRelForm_contentFeedId_label" class="formLabel" >Content Feed Id </div>
    <div id="contentFeedRelForm_contentFeedId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="contentFeedId" value="<%=WebUtil.display(_content_feed_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentFeedRelForm_contentId_field">
    <div id="contentFeedRelForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentFeedRelForm_contentId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="contentFeedRelFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentFeedRelFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedRel.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
