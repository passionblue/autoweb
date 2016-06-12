<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String registerSiteUrl = site.getSiteRegisterSite();
	if (WebUtil.isNull(registerSiteUrl)){
		registerSiteUrl = "www.uxsx.com";
	}
%>


<h4> If you are interested in this domain, please contact us via passionbluedirect@gmail.com </h4>


<!-- <a href="http://<%=registerSiteUrl %>/t_site_reg_start.html?targetDomain=<%=request.getServerName()%>"> registerSite </a> -->

<!-- <a href="http://<%=registerSiteUrl %>:8080/t_site_reg_start.html?targetDomain=<%=request.getServerName()%>"> registerSite </a> -->
