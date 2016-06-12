<%@page language="java" import="com.jtrend.session.SessionContext,com.autosite.content.*,com.autosite.db.*,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>



<h3> Registered Sites </h3>
<TABLE border="0" bgcolor="#ffffff"  width=100% height="50px" cellpadding="0" cellspacing="0">

<%

    SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
    


	List sites = SiteDS.getInstance().getAllSite();
	TreeMap treeMap = new TreeMap();
	
    for (Iterator iterator = sites.iterator(); iterator.hasNext();) {
		Site site = (Site) iterator.next();
		treeMap.put(site.getSiteUrl(), site);
	}
	
    for (Iterator iterator = treeMap.values().iterator(); iterator.hasNext();) {
    	Site site = (Site) iterator.next();
		
		SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    	
    	String targetLink = site.getSiteUrl();
        if (loginContext != null && loginContext.isLogin() && loginContext.getUsername().equals("siteadmin")) {
        	
        	targetLink +=   "/donotuse.html";
	    }
     
     
     	String keywords = "";
     	String siteTitle = "";
     	
     	if (siteConfig != null&& siteConfig.getKeywords() != null) keywords = " (" +siteConfig.getKeywords() + ")";
     	if (siteConfig != null&& siteConfig.getMeta() != null) siteTitle = " - <b>" + siteConfig.getMeta() + "</b>";
     	 
    
%>    
	<TR>
	
		<TD style="border-bottom : 1px #e0e0e0 solid;"> <p><a href="http://<%= targetLink %>" target="_blank" > <%= site.getSiteUrl() %> </a>  <%= siteTitle %> <%= keywords %> <a href="http://<%= site.getSiteUrl() %>/v_site_config.html" target="_blank" > <font size=1> [edit] </font></a></p></TD>
	</TR>
<%   
    }
%>     
</TABLE> 
     
          