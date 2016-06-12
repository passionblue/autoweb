<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.util.*,com.autosite.db.*,com.seox.work.*,java.util.*"%>
 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<% 
	PageView pageView = (PageView) session.getAttribute("k_view_pageview");
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
	String topText = (String) session.getAttribute("k_top_text");
	String topErrText = (String) session.getAttribute("k_top_error_text");
	String pageSize = (String) session.getAttribute("k_view_pagesize");
	String pageFull = (String) session.getAttribute("k_view_pagefull");
	long startPage = System.currentTimeMillis();

	
	if ( pageView == null) {
		pageView = PageView.getDefaultPageView();
	}
	

	boolean adminUser = false;
	UserBO userBO = (UserBO) session.getAttribute("k_userbo");
	if (userBO != null && userBO.getUserObj().getUsername().equals("user@joshua.com")) 
		adminUser = true;


//	String keywords = (String) session.getAttribute("k_site_config_keywords");

	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());


	String keywords = request.getServerName();
	String meta = "";
	String googleTrack = "";
	
	if (siteConfig !=null) {	
		keywords += "," + siteConfig.getKeywords();
		meta = siteConfig.getMeta();
		googleTrack = siteConfig.getSiteTrackGoogle();
	} else {
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}

	siteConfig = (SiteConfig) session.getAttribute("k_site_config");
	
		

	// Get current page.		
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);

	// Layout size, hide
	String width= (siteConfig.getWidth()==0?"1000":siteConfig.getWidth()+"");
	
	if ( !width.endsWith("%") ) width +="px"; 
	
	int sideSize = siteConfig.getMenuWidth();
	int adSize = siteConfig.getAdWidth();
	int midColumnSize = siteConfig.getMidColumnWidth();

	boolean displayAdSection = (siteConfig.getShowAdColumn() == 1);
	boolean displayMenuSection = (siteConfig.getShowMenuColumn() == 1);
	boolean displayUpperMenu = false;
	boolean isLogin = (loginContext != null && loginContext.isLogin());

	if ( sideSize == 99 ) {
		displayUpperMenu = true;
		displayMenuSection = false;
	}

	if ( sideSize == 98 ) {
		displayUpperMenu = true;
		displayMenuSection = true;
	}
	
	if ( adSize == 99 ) displayAdSection = false;

	if (pageFull != null && pageFull.equals("true") ) {
		width = "100%";
	} 
	
	// Colors
	
	String menuBackColor = "#ffffff";
	if (!WebUtil.isNull(siteConfig.getMenuBackColor()))
		menuBackColor = siteConfig.getMenuBackColor();

	//Panels	
	List panels = PanelDS.getInstance().getBySiteId(site.getId());


	// Debugging pupose
	if (false) {
    Enumeration names = session.getAttributeNames();
	System.out.println(">> SESSION KEYS ==================================");            
    while(names.hasMoreElements()){
		String name = (String) names.nextElement();
		Object val = session.getAttribute(name);
		System.out.println(">> " + name + "=" + val);            
     }
	System.out.println(">> END SESSION KEYS ==================================");
	}
%>

<!-- #### PANELS ####### -->
<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0" >
<%
		for (Iterator iter = panels.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 6) {
%>			
				<TR><TD valign="top" >			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel.getPanelType()) %>" />
				<% session.removeAttribute("p_current_panel"); %>
				</TD></TR>
<% 			
			}
		}
%>
</TABLE>
<% 
	if (isLogin) {
%>
<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0" >
<TR>
	<TD>
		<a href="t_panel_add.html?prv_columnNum=6&prv_align=1" ><img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png" title="Add section to Header Section"/></a>
	</TD>
</TR>
</TABLE>
<% 
	}
%>



<!-- ##### HEADER ####### -->



<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0">
	

	<TR>
		<TD><jsp:include page="/jsp/layout/header.jsp" /></TD>
	</TR>
	
<% 
	if (isLogin) {
%>

<!-- 
	<TR>
		<TD><jsp:include page="/jsp/layout/submenu.jsp" /></TD>
	</TR>
 -->
<% 
	}
%>

</TABLE>

<!-- ##### Site Top Menu ####### -->
<% if (true) { // If menu is hidden from the side show the top page menu %>
<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0">
	<TR><TD>
		<jsp:include page="/jsp/sidemenus/dyn_menu_top.jsp" />
	</TD></TR>
</TABLE>
<% } %>


<!-- ##### Divider ####### -->
<TABLE border="0" bgcolor="orange"  align="center" width="<%=width %>" cellpadding="0" cellspacing="0">
	<TR>
		<TD height="5"> </TD>
	</TR>		
</TABLE>

<!-- #### PANELS ####### -->
<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0" >
<%
		for (Iterator iter = panels.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 5) {
%>			
				<TR><TD valign="top" >			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel.getPanelType()) %>" />
				<% session.removeAttribute("p_current_panel"); %>
				</TD></TR>
<% 			
			}
		}
%>
</TABLE>

<% 
	if (isLogin) {
%>
<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0" >
<TR>
	<TD>
		<a href="t_panel_add.html?prv_columnNum=5&prv_align=1" ><img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png" title="Add section to Header Section"/></a>
	</TD>
</TR>
</TABLE>
<% 
	}
%>

<!-- #### PANELS ####### -->


<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0">
	<TR>
	
	<% if (displayMenuSection) { %>
		<!-- ############  MENU COLUMN ########################################################################## -->
		<TD width="<%=sideSize%>" valign="top" bgcolor="<%=menuBackColor%>" >
		<TABLE border="1" width="100%" cellpadding="0" cellspacing="0" style="border-width: 1px; border-style: solid;border-color:<%= siteConfig.getColorBorders()%>;border-collapse: collapse;">
		<TR> <TD>
			<TABLE border="0" width="100%" cellpadding="0" cellspacing="5" >
						
<%
	if (pageView != null && pageView.getSideMenu() != null ) {
%>				
				<TR><TD valign="top" style="border-style: none">
 				<jsp:include page="<%=pageView.getSideMenu()%>" />
				</TD></TR>
<%
	} else { 
%>				
				<TR><TD valign="top" style="border-style: none">
				<jsp:include page="/jsp/layout/sideempty.jsp" />
				</TD></TR>
<%
	} 
%>		

<%
	if (!displayUpperMenu) {
%>
		
				<TR><TD valign="top" style="border-style: none">
				<jsp:include page="/jsp/sidemenus/dyn_menu_left.jsp" />
				</TD></TR>
<%
	} 
%>		
<!--
				<TR><TD valign="top" height="30" style="border-style: none"> &nbsp;
				</TD></TR>

				<TR><TD valign="top" style="border-style: none">
				<% session.setAttribute("p_site_post_position", "3"); %>
				<jsp:include page="/jsp/layout/side_common.jsp" />
				<% session.removeAttribute("p_site_post_position"); %>
				</TD></TR>

				<TR><TD valign="top" height="30" style="border-style: none"> &nbsp;
				</TD></TR>
				
				<TR><TD valign="top" >
				<% session.setAttribute("p_site_post_position", "4"); %>
				<jsp:include page="/jsp/layout/side_common.jsp" />
				<% session.removeAttribute("p_site_post_position"); %>
				</TD></TR>
-->		
			</TABLE>
			</TD></TR>

		</TABLE>
		<BR/>	
		<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
<%
		for (Iterator iter = panels.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 1) {
%>			
				<TR><TD valign="top" >			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel.getPanelType()) %>" />
				<% session.removeAttribute("p_current_panel"); %>
				</TD></TR>
				
				<TR><TD height="1"></TD></TR>
			
<% 			
			}
		}
%>
		</TABLE>
<% 
	if (isLogin) {
%>
		<a href="t_panel_add.html?prv_columnNum=1"><img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png" title="Add panel to menu column"/></a>
<% 
	}
%>		
		</TD>

		<!-- ========================================================================================================= -->
		<TD class="v_divider" valign="top" align="center"  >		
			<table class="v_divider" border="0" bgcolor="white" cellpadding="0" cellspacing="0" width="3" height="600">
				<tr><td></td></tr>
			</table>
		</TD>		
	<% } %>
		
		<!-- ############  CONTENT COLUMN ################################################################# -->
		<TD  valign="top">
			<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >

<%
	if ((topText != null && !topText.equals("")) || (topErrText != null && !topErrText.equals("") ))  {
%>
				<TR><TD valign="top">
				<jsp:include page="/jsp/layout/toptext.jsp" />
				</TD></TR>
<%
	} 
%>

				<TR><TD valign="top">
				<jsp:include page="<%=pageView.getContentPage()%>" />
				</TD></TR>

<!-- 
				<TR><TD valign="top">
				<jsp:include page="/jsp/layout/content_add_bottom.jsp" />
				</TD></TR>
-->
<%
		for (Iterator iter = panels.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 2) {
%>			
				<TR><TD valign="top" >			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel.getPanelType()) %>" />
				<% session.removeAttribute("p_current_panel"); %>				
				</TD></TR>
			
<% 			
			}
		}
%>				


				<TR><TD valign="top">

<% 
	if (isLogin) {
%>
				<a href="t_panel_add.html?prv_columnNum=2"><img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png" title="Add panel to content column"/></a>
<% 
	}
%>
				<TABLE border="1" bgcolor="#ffffff"  align="center" width="100%" cellpadding="0" cellspacing="0" style="border-width: 1px; border-style: solid;border-color:<%= siteConfig.getColorBorders()%>;border-collapse: collapse;">
					<TR><TD valign="top">
					<% session.setAttribute("p_site_post_position", "8"); %>
					<jsp:include page="/jsp/layout/side_common.jsp" />
					<% session.removeAttribute("p_site_post_position"); %>				
					</TD></TR>
				</TABLE>
				</TD></TR>


				<TR><TD valign="top">
					<Table width="100%" cellpadding="0"  align="left">
						<Tr>
							<td width="100%" align="left" >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</Td>
						</Tr>
					</Table>
				</TD></TR>
			</TABLE>
		</TD>

		<% if (siteConfig.getShowMidColumn() == 1) { %>
		<!-- =============================================================================================== -->
		
		<TD valign="top" align="center">		
			<table class="v_divider" border="0" bgcolor="white" cellpadding="0" cellspacing="0" width="3" height="600">
				<tr><td> </td></tr>
			</table>
		</TD>		
		<!-- ############  MID COLUMN ################################################################################### -->

		<TD width="<%=midColumnSize%>" valign="top">
		<TABLE border="1" width="100%" cellpadding="0" cellspacing="0" style="border-width: 1px; border-style: solid;border-color:<%= siteConfig.getColorBorders()%>;border-collapse: collapse;">
		<TR> <TD>
			<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
		
				<TR><TD valign="top">
				<% session.setAttribute("p_site_post_position", "9"); %>
				<jsp:include page="/jsp/layout/side_common.jsp" />
				<% session.removeAttribute("p_site_post_position"); %>				
				</TD></TR>

			</TABLE>
		</TD></TR>
		</TABLE>
		
		
		<BR/>	
		<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
<%
		for (Iterator iter = panels.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 3) {
%>			
				<TR><TD valign="top" >			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel.getPanelType()) %>" />
				<% session.removeAttribute("p_current_panel"); %>				
				</TD></TR>
				
				<TR><TD height="1"></TD></TR>
			
<% 			
			}
		}
%>				
		</TABLE>		
<% 
	if (isLogin) {
%>
		<a href="t_panel_add.html?prv_columnNum=3"><img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png" title="Add panel to mid column"/></a>
<% 
	}
%>		
		</TD>



		<% } // Mid Column%>

		<% if (displayAdSection) { %>

		<!-- =============================================================================================== -->
		
		<TD valign="top" align="center">		
			<table class="v_divider" border="0" bgcolor="white" cellpadding="0" cellspacing="0" width="3" height="600">
				<tr><td> </td></tr>
			</table>
		</TD>		
		
		<!-- ############  AD COLUMN ################################################################################### -->

		<TD width="<%=adSize%>" valign="top">

<!-- 		
		<TABLE border="1" width="100%" cellpadding="0" cellspacing="0" style="border-width: 1px; border-style: solid;border-color:<%= siteConfig.getColorBorders()%>;border-collapse: collapse;">
		<TR> <TD>
			<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
		
				<TR><TD valign="top">
				<% session.setAttribute("p_site_post_position", "2"); %>
				<jsp:include page="/jsp/layout/side_common.jsp" />
				<% session.removeAttribute("p_site_post_position"); %>				
				</TD></TR>
				
				<TR><TD valign="top">
				<% session.setAttribute("p_site_post_position", "7"); %>
				<jsp:include page="/jsp/layout/side_common.jsp" />
				<% session.removeAttribute("p_site_post_position"); %>				
				</TD></TR>
			</TABLE>
		</TD></TR>
		</TABLE>	
		
		<BR/>
-->			
		<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
<%
		for (Iterator iter = panels.iterator();iter.hasNext();){
			Panel panel = (Panel) iter.next();
		
			if ( panel.getColumnNum() == 4) {
%>			
				<TR><TD valign="top" >			
				<% session.setAttribute("p_current_panel", panel); %>
				<jsp:include page="<%=PanelUtil.getPanelPage(panel.getPanelType()) %>" />
				<% session.removeAttribute("p_current_panel"); %>				
				</TD></TR>
				
				<TR><TD height="1"></TD></TR>
<% 			
			}
		}
%>				
		</TABLE>		
<% 
	if (isLogin) {
%>
		<a href="t_panel_add.html?prv_columnNum=4"><img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png" title="Add panel to ad column" /></a>
<% 
	}
%>		
		</TD>
		
		<% } %>
	</TR>
</TABLE>


<TABLE border="0" bgcolor="#ffffff"  align="center" width="<%=width %>" cellpadding="0" cellspacing="0">
	<TR>
		<TD height="20" style="border-bottom : 5px orange solid;" colspan="2">  &nbsp;</TD>
	</TR>		
</TABLE>


<TABLE border="0" width="<%=width %>" align="center" cellpadding="0" cellspacing="0">
	<TR><TD>
	<jsp:include page="/jsp/layout/footer.jsp" />
	</TD></TR>
</TABLE>

