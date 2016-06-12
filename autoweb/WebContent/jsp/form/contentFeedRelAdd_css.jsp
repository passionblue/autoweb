<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentFeedRel _ContentFeedRelDefault = new ContentFeedRel();// ContentFeedRelDS.getInstance().getDeafult();
    
    String _content_feed_idValue= (reqParams.get("contentFeedId")==null?WebUtil.display(_ContentFeedRelDefault.getContentFeedId()):WebUtil.display((String)reqParams.get("contentFeedId")));
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentFeedRelDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentFeedRelDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="contentFeedRelForm_topArea" class="formTopArea"></div>
<div id="contentFeedRelForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="contentFeedRelForm" method="get" action="/contentFeedRelAction.html" >




	<div id="contentFeedRelForm_contentFeedId_field">
    <div id="contentFeedRelForm_contentFeedId_label" class="formLabel" >Content Feed Id </div>
    <div id="contentFeedRelForm_contentFeedId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="contentFeedId" value="<%=WebUtil.display(_content_feed_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="contentFeedRelForm_contentId_field">
    <div id="contentFeedRelForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentFeedRelForm_contentId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="contentFeedRelForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentFeedRelForm.submit();">Submit</a>
        </div>      

        <div id="contentFeedRelForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.contentFeedRelForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="contentFeedRelForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentFeedRelDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedRel _ContentFeedRel = (ContentFeedRel) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedRel.getId() %> </td>

    <td> <%= WebUtil.display(_ContentFeedRel.getContentFeedId()) %></td>
    <td> <%= WebUtil.display(_ContentFeedRel.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentFeedRel.getTimeCreated()) %></td>


<td>
<form name="contentFeedRelForm<%=_ContentFeedRel.getId()%>" method="get" action="/v_content_feed_rel_edit.html" >
    <a href="javascript:document.contentFeedRelForm<%=_ContentFeedRel.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedRel.getId() %>">
</form>
<form name="contentFeedRelForm<%=_ContentFeedRel.getId()%>2" method="get" action="/v_content_feed_rel_edit2.html" >
    <a href="javascript:document.contentFeedRelForm<%=_ContentFeedRel.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedRel.getId() %>">
</form>

</td>
<td>
<a href="/contentFeedRelAction.html?del=true&id=<%=_ContentFeedRel.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>