<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.SessionContext,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%

	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	String pageName = (String) session.getAttribute("k_page_name");
	
	
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName); 
	
	List dbContents = ContentDS.getInstance().getByPageId(dynPage.getId());

	String pageDetail = "Page Details: pageName=" + 	dynPage.getPageName() + 
	                    ",site=" + site.getSiteUrl() +  	
	                    ",siteId=" + site.getId() +  	
	                    ",PageId=" + dynPage.getId();  	
	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}

	int columnCount = siteConfig.getHomeColCount();
	if (dynPage.getPageColCount() > 0 ) columnCount = dynPage.getPageColCount();
	
	int widthRate = (100 - columnCount + 1)/columnCount;

	// If it is summary view type, make 	
	int summaryLengh = 100000;
	if (dynPage.getPageViewType() == 1 ) { // Summary View Type. will show part of article only
		summaryLengh = 200;		
	}
	
	
%>     

<h3> Home Mobile/Phone</h3>