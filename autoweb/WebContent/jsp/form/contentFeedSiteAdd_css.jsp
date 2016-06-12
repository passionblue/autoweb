<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentFeedSite _ContentFeedSiteDefault = new ContentFeedSite();// ContentFeedSiteDS.getInstance().getDeafult();
    
    String _content_feed_idValue= (reqParams.get("contentFeedId")==null?WebUtil.display(_ContentFeedSiteDefault.getContentFeedId()):WebUtil.display((String)reqParams.get("contentFeedId")));
    String _display_typeValue= (reqParams.get("displayType")==null?WebUtil.display(_ContentFeedSiteDefault.getDisplayType()):WebUtil.display((String)reqParams.get("displayType")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentFeedSiteDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="contentFeedSiteForm_topArea" class="formTopArea"></div>
<div id="contentFeedSiteForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="contentFeedSiteForm" method="get" action="/contentFeedSiteAction.html" >




	<div id="contentFeedSiteForm_contentFeedId_field">
    <div id="contentFeedSiteForm_contentFeedId_label" class="formLabel" >Content Feed Id </div>
    <div id="contentFeedSiteForm_contentFeedId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="contentFeedId" value="<%=WebUtil.display(_content_feed_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="contentFeedSiteForm_displayType_field">
    <div id="contentFeedSiteForm_displayType_label" class="formLabel" >Display Type </div>
    <div id="contentFeedSiteForm_displayType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="displayType" value="<%=WebUtil.display(_display_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="contentFeedSiteForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentFeedSiteForm.submit();">Submit</a>
        </div>      

        <div id="contentFeedSiteForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.contentFeedSiteForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="contentFeedSiteForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentFeedSiteDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedSite _ContentFeedSite = (ContentFeedSite) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedSite.getId() %> </td>

    <td> <%= WebUtil.display(_ContentFeedSite.getContentFeedId()) %></td>
    <td> <%= WebUtil.display(_ContentFeedSite.getDisplayType()) %></td>
    <td> <%= WebUtil.display(_ContentFeedSite.getTimeCreated()) %></td>


<td>
<form name="contentFeedSiteForm<%=_ContentFeedSite.getId()%>" method="get" action="/v_content_feed_site_edit.html" >
    <a href="javascript:document.contentFeedSiteForm<%=_ContentFeedSite.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedSite.getId() %>">
</form>
<form name="contentFeedSiteForm<%=_ContentFeedSite.getId()%>2" method="get" action="/v_content_feed_site_edit2.html" >
    <a href="javascript:document.contentFeedSiteForm<%=_ContentFeedSite.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedSite.getId() %>">
</form>

</td>
<td>
<a href="/contentFeedSiteAction.html?del=true&id=<%=_ContentFeedSite.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>