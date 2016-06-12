<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
		Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
%>

<div>
This is loaded from /panel_custom/partition-test.jsp 
</div>