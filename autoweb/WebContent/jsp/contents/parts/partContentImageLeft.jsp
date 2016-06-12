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

%>

<div class="contFrameDefault">

	<div class="contSubject">
		<%= content.getContentSubject() %>
	</div>

	<div class="contData">
		<%= content.getContent() %>
	</div>

</div>
