<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
    	SessionContext sessionContext = (SessionContext) session.getAttribute("k_session_context");
	
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if (panel == null) return;
		
		
		// Show Hidden, if no showhidden attr, set it true by default. can be turned off with cmd_hideHidden cmd.  
		boolean showHidden = false;
		
		if (session.getAttribute("showHidden") == null) {
			session.setAttribute("showHidden", "true");
			showHidden = true;
		} else {
			String sh = (String) session.getAttribute("showHidden");
			if (sh.equalsIgnoreCase("true")) showHidden = true;
			else showHidden = false;		
		}		

		if (showHidden) {
			String cmdHideHidden = (String) request.getParameter("cmd_hideHidden");
			if (cmdHideHidden != null ) { 
				showHidden = false;
				session.removeAttribute("showHidden");
			}
		} else {
			String cmdShowHidden = (String) request.getParameter("cmd_showHidden");
			if (cmdShowHidden != null ) { 
				showHidden = true;
				session.setAttribute("showHidden", "true");
			}
		}
		
		if ( panel.getHide() == 1 && !showHidden) return;

		
		boolean isLogin = (sessionContext != null && sessionContext.isLogin());
		int panelHeight = (panel.getPanelHeight() ==0?20:panel.getPanelHeight());
		boolean isVerticalAlign = (panel.getAlign() == 0);

		String panelHeightConfig = "";
		if (!isVerticalAlign){
			panelHeightConfig = "height=\"" + panelHeight +"px\"";
		}


		List contents = ContentDS.getInstance().getByPageId(panel.getId());
		List sitePosts = SitePostDS.getInstance().getByPanelId(panel.getId());
		
		if (sitePosts.size() == 0 && !isLogin) return;
		
		String style = panel.getStyleString();
		
		if (style == null || style.length()==0) style = "border-width: 0px; border-style: solid";
	
		// find style configs and IDs.	
		String panelStyleSuffix = PanelUtil.getPanelStyleSuffix(panel); 
		String panelLinkStyleSuffix = PanelUtil.getPanelLinkStyleSuffix(panel);
		
		List panelStyles = PanelStyleDS.getInstance().getByPanelId(panel.getId());

		StyleConfig styleConfig = PanelUtil.getPanelStyleConfig(panel.getId());
		LinkStyleConfig linkStyleConfig = PanelUtil.getPanelLinkStyleConfig(panel.getId());
			
		//menu 
		String menuStyleName = "panel-menu";
		if (panel.getHide() == 1 ) 	menuStyleName="panel-menu-hidden";
		
		// Page view check. 
		Site site = SiteDS.getInstance().registerSite(request.getServerName());
		String pageName = (String) session.getAttribute("k_page_name");
%>

<div id="panel-free" >
<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% if (isLogin){ %>
<div id="<%=menuStyleName %>" class="<%=menuStyleName %>">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">A</a>|
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">E</a>|	
		<a href="/v_style_config_add.html?prv_panel_id=<%=panel.getId()%>">S</a>|
		<a href="/v_link_style_config_add.html?prv_panel_id=<%=panel.getId()%>">L</a>|
		<a href="/panelAction.html?del=true&id=<%=panel.getId()%>">D</a>|

<% if (panel.getHide() == 1) { %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=0&ef=true">Show</a>|Contents:<%=sitePosts.size() %>|
<% }else{ %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
<% } %>
		<%=panel.getId()+"(" + panel.getPanelType()+")"%>|

<% if (styleConfig != null) { %>
		<%=styleConfig.getId()%>|
<% } %>
		
		
</div>			
<% } %>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% 
	// If panel is hidden 
	if (panel.getHide() == 1) {
%>
	</div>
<%
	return;
	}
%>
<%	 
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);
	
	// Check if this panel is hidden by page view option for panel. 
	boolean hideByPageOnlyOption = false;		
	if (panel.getPageOnly() == 1 && panel.getPageId() != pageId){
		hideByPageOnlyOption = true;	
	}

	if (hideByPageOnlyOption && isLogin) { 
%>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR>
		<TD> Will Show Only For Page <%= panel.getPageId() %></TD>
	</TR>
</TABLE>
	</div>
<%	 
		return;
	}
%>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<div id="panel-free-content" >

<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent" <%= panelHeightConfig %>>

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
%>
	<TR >
		<TD valign="middle" align="left" height="20" bgcolor="#f0f0f0" style="<%=panel.getTitleStyleString()%>"> <b><%= panel.getPanelTitle() %></b></TD>
	</TR>
<% } %>

<TR>		
	<TD bgcolor="transparent" valign="top">

<% if (!isVerticalAlign){ %>
<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="transparent"><TR> 
<%} %>
	
<%
	for(Iterator iter = sitePosts.iterator();iter.hasNext();){
	
		SitePost sitePost = (SitePost) iter.next();
		
		// Do not include if it has page scope and not in the page. 
		System.out.println("-->" + sitePost.getPostScope() + " pageId=" + pageId);
		if (sitePost.getPostScope() == 3){
			if (pageId != 0 && sitePost.getPageId() != pageId) 
				continue;
		}
		
		
		String sizeConfigs = "";
		if (sitePost.getHeight() > 0 ) 
			sizeConfigs += sizeConfigs + " height=" +  sitePost.getHeight() + "px";

		if (!isVerticalAlign) {
			if (sitePost.getWidth() > 0 ) 
				sizeConfigs += sizeConfigs + " width=" +  sitePost.getWidth() + "px";
		} else {
			sizeConfigs += sizeConfigs + " width=\"100%\"";
		}		
		String data = "";

		
		if (sitePost.getPostType() == 2 ) { // LINK TYPE
			String linkDisplay = sitePost.getPostDataExtra() == null? sitePost.getPostData():sitePost.getPostDataExtra();
			
			data = "<div id=\"link-content" + panelLinkStyleSuffix + "\"> <a href=\"" + sitePost.getPostData() + "\" target=\"_blank\" >" + sitePost.getPostDataExtra() + "</a> </div>";
		} else if (sitePost.getPostType() == 3 ) { // IMAGES 
			
			String imgSrc = sitePost.getPostData();
			if (!imgSrc.startsWith("http://") && !imgSrc.startsWith("/"))
				imgSrc = "http://" + imgSrc;			
			
			data = "<img " + sizeConfigs + " src=\"" + imgSrc + "\"/>";  
		} else if (sitePost.getPostType() == 5 ) { // PADDING
			int paddingHeight = WebParamUtil.getIntValue(sitePost.getPostData());
			if ( paddingHeight <= 0 ) paddingHeight = 30;
		
			if (isVerticalAlign)		
				data = "<TABLE> <TR><TD height=\"" + paddingHeight + "\"> &nbsp; </TD></TR></TABLE>" ;
			else
				data = "<TABLE> <TR><TD width=\"" + paddingHeight + "\"> &nbsp; </TD></TR></TABLE>" ;
		} else {
			data = sitePost.getPostData();
		}
		
		
%>

<% if (!isVerticalAlign){ %>

<TD valign="top"  > 
<%} %>
<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0" bgcolor="transparent" >
<% 	if (isLogin) { 
%>		
		<TR><TD align="right" colspan="3"> 
			<a href="/t_edit_site_post.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[edit]</font> </a> 
			<a href="/deleteSitePost.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[del]</font> </a> 
		</TD></TR>
<% }%>

	<TR>
		<TD valign="top" align="center" >
			<TABLE border="0" <%=sizeConfigs %> cellpadding="0" cellspacing="0" bgcolor="transparent" style="<%=style%>">
				<TR>
					<TD valign="top"><%= data%></TD>
				</TR>
			</TABLE>	 
		</TD>
	</TR>
</TABLE>

<% if (!isVerticalAlign){ %>
</TD>  
<%} %>


<%
	}
%>

<% if (!isVerticalAlign){ %>
</TR></TABLE> 
<%} %>

	
	</TD>
</TR>	
</TABLE>	
</div> <!-- end panel div -->
</div>

