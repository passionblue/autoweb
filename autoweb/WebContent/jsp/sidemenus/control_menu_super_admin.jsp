<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.jtrend.session.SessionContext,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
%>
<TABLE border="0" width="100%" height="20px" cellpadding="0" cellspacing="0" align="left">
	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
 
			<html:link page="/oway/test.html" > OWAY</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_jquery_test.html" > JQ TEST</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_map_test.html" > MAP</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_content_feed_home.html" > Feed</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_all_site_contents.html" > Contents</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_site_edit.html" > Sites Edit</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_open_site_list.html" > Site List</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_ec_product_home.html" > Ec Product</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_autosite_user_home.html" > User Update</html:link>&nbsp;|&nbsp; 
			<html:link page="/t_site_reg_start.html" > Site Register</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_content_home.html" > Contents</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_link_style_config_home.html" > Link Styles</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_style_config_home.html" > Styles</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_panel_home.html" > Panel</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_forum_home.html" > Forum</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_fileup.html" > FileUp Test</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_add_page.html" > DynPage Manage</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_page_config_home.html" > Dyn PageConfigs </html:link>&nbsp;|&nbsp; 
			<html:link page="/v_page_static_config_home.html" > Static PageCfg</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_page_static_alt_home.html" > PageAlt</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_site_config.html" > SiteConfig</html:link>&nbsp;|&nbsp; 
			<html:link page="/logout.html" > <font color="red"  size="2"> <b> Logout </b></font></html:link>
		</TD>
	</TR>
	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
			<a href="/v_style_theme_home.html" >Style Theme</a> |
			<a href="/devNoteAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Add Note</a> |
			<a href="/devNoteAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Notes List</a> |
			
<%
	    if ( WebUtil.isTrue(site.getSubdomainEnable() )) {
%>			
			<a href="/siteAction.html?editfield=true&subdomainEnable=0&id=<%= site.getId()%>" >Disable Subdomain/sites</a> |
		
<% 		} else { %>
			<a href="/siteAction.html?editfield=true&subdomainEnable=1&id=<%= site.getId()%>" >Enable Subdomain/sites</a> |
<%
	    }
%>				
			
			<html:link page="/v_admin_stats_home.html" > Stats</html:link>&nbsp;|&nbsp; 
 
		</TD>
	</TR>
	<TR align="left" >
		<TD width="100%" align="right" style="border-bottom : 1px #e0e0e0 solid;">
			This is control_menu_super_admin.jsp&nbsp;|&nbsp; 
			<html:link page="/v_style_central.html" > Style Central</html:link>&nbsp;|&nbsp; 
			<html:link page="/v_all_links.html" > All Links</html:link>&nbsp;|&nbsp; 
			<a href="javascript:sendFormAjaxSimple('/donotuse.html',false);" > Force Login</a>&nbsp;|&nbsp; 
			<a href="/siteAction.html?registered=1&editfield=true&id=<%=site.getId()%>" > Force Register </a>&nbsp;|&nbsp; 
		 	[&nbsp;<html:link page="/appconfig.html?cmd=wp" > <font size="1" ><b>printer</b> </font> </html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=wf" > <font size="1" ><b>full</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=ww" > <font size="1" ><b>wider</b></font></html:link>&nbsp;|
		 	<html:link page="/appconfig.html?cmd=wn" > <font size="1" ><b>narrower</b></font></html:link>]&nbsp;&nbsp;&nbsp;&nbsp;
			<html:link page="/cscr/main.css" > Scripts </html:link>&nbsp;|&nbsp; 
		</TD>
	</TR>
</TABLE>