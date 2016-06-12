<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*,com.jtrend.struts.core.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<% 
	long startPage = System.currentTimeMillis();
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(site.getSiteUrl(), session);

	int userType = (sessionContext != null? sessionContext.getUserType(): Constants.UserAnonymous);
	boolean isLogin = (sessionContext != null? sessionContext.isLogin():false);

    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

	SessionWrapper wrap = SessionWrapper.wrapIt(request, site.getId());

	String partType = request.getParameter("partType"); // PartitionType
	if ( partType == null )
	    return;
%>


<%
	//==========================================================================================================
	if (partType.equalsIgnoreCase("panel")){	
		long partId = WebParamUtil.getLongValue(request.getParameter("partId"));
		Panel panel = PanelDS.getInstance().getById(partId);
		
		if (panel == null) {
		    JspUtil.getLogger().info("layout part returns nothing because panel not found for " + partId);
		    return;
		}

		if (panel.getSiteId() != site.getId()) {
		    JspUtil.getLogger().info("siteId from panel different from site in session panel Site=" + panel.getSiteId() + " session =" + site.getId());
		    return;
		}
		
		System.out.println("Panel page=" + PanelUtil.getPanelPage(panel));
%>		
		<% session.setAttribute("p_current_panel", panel); %>
		<jsp:include page="<%=PanelUtil.getPanelPage(panel) %>" />
		<% session.removeAttribute("p_current_panel"); %>				
<%		
	//==========================================================================================================
	} else if (partType.equalsIgnoreCase("custom"))  {	

	    // Access control is very critical and problematic here see GetPartitionAction for crude control
	    String partId = request.getParameter("partId");
		String returnPartitionPageName = "/jsp/layout/panel_custom/partition-test.jsp";

		if ( partId != null && !partId.trim().isEmpty()) {
		    returnPartitionPageName = DefaultPartitionPageManager.getInstance().getPartitionPage(partId);		    
			System.out.println("Custom Part page by partId =" + returnPartitionPageName);
		} else {
	            
		    String partPageName = request.getParameter("page");
			if (partPageName != null && !partPageName.trim().isEmpty())
			    returnPartitionPageName = "/jsp/layout/panel_custom/" + partPageName + ".jsp";
			System.out.println("Custom Part page by page =" + returnPartitionPageName);
		}
		
%>
		<jsp:include page="<%=returnPartitionPageName %>" />

<%		
	//==========================================================================================================
	} else {	
%>

<%	} %>



