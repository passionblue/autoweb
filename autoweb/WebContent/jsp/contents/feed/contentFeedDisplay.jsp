<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = ContentFeedConfigDS.getInstance().getAll();

	ContentFeedSite _ContentFeedSite = ContentFeedSiteDS.getInstance().getObjectBySiteId(site.getId());
	long contentFeedId = (_ContentFeedSite == null?0:_ContentFeedSite.getContentFeedId());
	
	String _wpId = WebProcManager.registerWebProcess();
	
%> 

<%
	if (sessionContext!= null && sessionContext.isSuperAdminLogin()){
%>
<br/>
<form name="contentFeedSiteForm" method="get" action="/contentFeedSiteAction.html" >

		<div id="contentFeedConfigForm_feedType_field">
	    <div id="contentFeedConfigForm_feedType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="contentFeedId">
	        <option value="0" <%=HtmlUtil.getOptionSelect(contentFeedId, 0)%>>- Please Select -</option>
<%
	for(Iterator iter = list.iterator(); iter.hasNext();){
		ContentFeedConfig contFeed = (ContentFeedConfig) iter.next();
%>
	        <option value="<%=contFeed.getId() %>" <%=HtmlUtil.getOptionSelect(contentFeedId, contFeed.getId())%>><%=contFeed.getFeedCategory() %></option>
			
<%
	}
%>
	        </select>
		    <a id="formSubmit" href="javascript:document.contentFeedSiteForm.submit();">Set Feed</a>

<%
	if (_ContentFeedSite != null ) {
%>
			&nbsp;&nbsp;<a id="formSubmit" href="/contentFeedSiteAction.html?del=true&id=<%=_ContentFeedSite.getId()%>"> Delete Associated Feed</a>
<%	} %>
			&nbsp;&nbsp;<a id="formSubmit" href="/v_content_feed_home.html"> Go Feed Control </a>
		    
	    </div>      

		</div><div class="clear"></div>

		<br>

<%
	if (_ContentFeedSite == null ) {
%>
	<INPUT TYPE="HIDDEN" NAME="add" value="true">
	<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<%	} else { %>
	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedSite.getId() %>">
<%	} %>
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_display">

	</form>

<%
	}
%>



<%
	if (_ContentFeedSite == null) return;

	// ##################################################################################################
	
	List contents = ContentFeedRelDS.getInstance().getByContentFeedId(_ContentFeedSite.getContentFeedId());
%>


<div>
<%

	ContentDS contDs = ContentDS.getInstance();

	for(Iterator iter = contents.iterator(); iter.hasNext();){
		ContentFeedRel contRel = (ContentFeedRel) iter.next();
		Content content = contDs.getById(contRel.getContentId());
		
%>
	<div class="feedContentSubject" ><h4><%= content.getContentSubject() %></h4>
<%
	if (sessionContext!= null && sessionContext.isSuperAdminLogin()){
%>
		<a href="/v_edit_dyn_content_form.html?id=<%=content.getId()%>"> edit content</a>
<%
	}
%>
	</div>
	
	<div class="feedContentBody" ><%=content.getContent() %></div>					
<%
	}
%>
</div>