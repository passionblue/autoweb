<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.jtrend.session.SessionContext,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
	
	StyleConfig styleConfigCntListSubject   = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContListSubjectKey(site.getId()));
	StyleConfig styleConfigCntListData      = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContListDataKey(site.getId()));
	StyleConfig styleConfigCntSingleSubject = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContSingleSubjectKey(site.getId()));
	StyleConfig styleConfigCntSingleData    = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContSingleDataKey(site.getId()));
	
	SiteConfigStyle siteStyleConfig = SiteConfigStyleDS.getInstance().getObjectBySiteId(site.getId());
	
	
	String requestUri = (String)request.getAttribute("k_request_uri");
	String requestQuery = (String)request.getAttribute("k_request_query");
	
	
	String currentUrlForNoFrame = requestUri + "?" + requestQuery + "&fmt=nofrm";
	
	Site contentForwardSite = SiteDS.getInstance().getContentForwardSite(request.getServerName());
	Site surfaceSite = SiteDS.getInstance().getSiteByUrl(request.getServerName());
	
%>

<TABLE border="0" width="100%" height="20px" cellpadding="0" cellspacing="0" align="left">

<%
	Site siteForDomain = SiteDS.getInstance().registerSite(request.getServerName(), false);
	SiteConfig siteConfigForDomain = siteForDomain ==null?null:SiteConfigDS.getInstance().getSiteConfigBySiteId(siteForDomain.getId()); // To get siteconfig for a site that is not forwarded. 
	if (sessionContext != null && sessionContext.isSuperAdmin()) {
%>
	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
		[control_menu_site_admin.jsp] <%=PageViewUtil.getCurrentViewPage(request) %>
	
<%
	    if ( !WebUtil.isTrue(siteForDomain.getRegistered() )) {
%>			
			<a href="/siteAction.html?editfield=true&registered=1&id=<%= siteForDomain.getId()%>" >Register</a> |
		
<% 		} else { %>
			<a href="/siteAction.html?editfield=true&registered=0&id=<%= siteForDomain.getId()%>" >UnRegister</a> |
<%
	    }
%>	

<%
	    if ( WebUtil.isTrue(siteForDomain.getDisabled() )) {
%>			
			<a href="/siteAction.html?editfield=true&disabled=0&id=<%= siteForDomain.getId()%>" >Enable</a> |
		
<% 		} else { %>
			<a href="/siteAction.html?editfield=true&disabled=1&id=<%= siteForDomain.getId()%>" >Disable</a> |
<%
	    }
%>	

<%
	if (siteStyleConfig == null) {
%>			
			<a href="/v_site_config_style_form.html?prv_returnPage=site_config_style_home"> Add Site Style </a> &nbsp;|&nbsp;
<%	} else { %>
			<a href="/v_site_config_style_form.html?cmd=edit&id=<%=siteStyleConfig.getId()%>&prv_returnPage=site_config_style_home"> Edit Site Style </a> &nbsp;|&nbsp;
<%	} %>
			 
			<a href="<%=currentUrlForNoFrame%>" rel="facebox" >No frame</a> |
			<a href="/v_style_theme_home.html" >Style Theme</a> |
			<html:link page="/v_cleaner_register_subsites.html?" > <font size="1" ><b>Sub Sites</b></font></html:link>&nbsp;|
			<html:link page="/v_cleaner_register_start_form.html?prv_returnPage=cleaner_register_start_home" > <font size="1" ><b>Register SubSite</b></font></html:link>&nbsp;|
 
		</TD>
	</TR>
	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
			<html:link page="/v_style_config_custom_list.html" > CustomStyles</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_theme_aggregator_home.html" > Theme Agg</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_theme_styles_home.html" > Theme Styles</html:link>&nbsp;|&nbsp; 
		</TD>
	</TR>

<%
	}
%>


	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
			<html:link page="/v_content_feed_home.html" > Feed</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_page_static_alt_home.html" > PageAlt</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_page.html" > Page Manage</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_site_config.html" > Site Config</html:link>&nbsp;|&nbsp; 
		</TD>
	</TR>


	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
			
			<%=siteForDomain.getSiteUrl() + "[" + siteForDomain.getId()+ "] <<= " %> &nbsp;
			<%=contentForwardSite==null?"No Content Forward Site":contentForwardSite.getSiteUrl() %> &nbsp;|

<%-- 
		 	[&nbsp;<html:link page="/appconfig.html?cmd=wp" > <font size="1" ><b>printer</b> </font> </html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=wf" > <font size="1" ><b>full</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=ww" > <font size="1" ><b>wider</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=wn" > <font size="1" ><b>narrower</b></font></html:link>]&nbsp;&nbsp;&nbsp;&nbsp;
--%>			 
			<html:link page="/cscr/main.css" > Scripts </html:link>&nbsp;|&nbsp;
		</TD>
	</TR>
</TABLE>
