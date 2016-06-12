
<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext loginContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	String pageName 	= (String) session.getAttribute("k_page_name");
	String categoryName = request.getParameter("cat");
	
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);


	List contentsByPage = ContentDS.getInstance().getByPageId(dynPage.getId());

%>
	<hr/>

	<h4> All hidden contents in this page</h4>
<%
	
    for (Iterator iterator = contentsByPage.iterator(); iterator.hasNext();) {
    
	    Content content = (com.autosite.db.Content) iterator.next();
	    if (content.getHide() == 0) continue;
%>
	<%= content.getContentSubject() %>&nbsp;<a href="/addDynContent.html?id=<%=content.getId() %>&ef=true&hide=0" > <font size="1" color="blue">[show]</font> </a><dr/>

<%	    
	}
%>	

	