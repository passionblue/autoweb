<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext loginContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long contentId = WebParamUtil.getLongValue(request.getParameter("contentId"));
	
	Content content = ContentDS.getInstance().getById(contentId);

	if (content == null) {
		JspUtil.getLogger().debug("**ERROR** content not found for " + contentId);
		return;
	}

	String contentData = content.getContent();
	
	if (WebUtil.isNotTrue(content.getInHtml())) {
	
		contentData = "<p>" + contentData + "</p>";
	}


	String imgStr = "";

	if ( content.getContentType() == 1 && WebUtil.isNotNull(content.getImageUrl()) ) {

		imgStr = "<img class=\"contImgLeft\" src=\"" + content.getImageUrl() +"\""; 
	
		if ( content.getImageHeight() > 0 ) imgStr += " height=\"" + content.getImageHeight() + "\" ";
		if ( content.getImageWidth() > 0 ) imgStr += " width=\"" + content.getImageWidth() + "\" ";
			
		imgStr += " />";
	}


%>
<div class="contFrameDefault">

	<div class="contSubject">
		<%= content.getContentSubject() %>
	</div>

	<div class="contData">
		<%=imgStr %>
		<%= contentData %>
	</div>

</div>
