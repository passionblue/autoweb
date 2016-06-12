<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	// This panel displays category in links for a page that browswer displaying. 

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Panel panel = (Panel) session.getAttribute("p_current_panel");
	if ( panel == null) {
		JspUtil.getLogger().error("** Panel not found in session **");
		return;
	}

	
	String pageName = (String) session.getAttribute("k_page_name");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List pages = PageDS.getInstance().getBySiteId(site.getId());
	
	if (pages == null) {
		return;
	}


    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	if (siteConfig ==null) {
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}
	
	if ( siteConfig.getMenuReverse() == 1) {
		Collections.reverse(pages);
	}
	
	
	String lineColor = WebUtil.display(siteConfig.getMenuLineColor(), "#e0e0e0");
	String frontColor = WebUtil.display(siteConfig.getMenuFrontColor(), "orange");
	
	boolean isLogin = (sessionContext != null && sessionContext.isSuperAdminLogin());
	
	
	// Page view check. 
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);

		
	//=================================================================================================
	// Visiblity for printable
	if (WebUtil.isPritintable(request)){
		if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
			return;
	} else {
		if (panel.getShowOnlyPrint() == 1)
			return;
	}	

	//=================================================================================================
	// Admin Only Check
	
	if ( WebUtil.isTrue(panel.getAdminOnly()) && !sessionContext.isSuperAdminLogin()){
		return;
	}

	//=================================================================================================
	// Panel Specific
	
	List pageCats = ContentCategoryDS.getInstance().getByPageId(pageId);
		
%>

<!-- ########################################################################################### -->
<!-- ################################# TOP SPACE ############################################### -->

<% if (panel.getTopSpace()>0) { %>
<div class="gapPadding" style="height: <%=panel.getTopSpace() %>">
</div>	
<% } %>

<!-- ########################################################################################### -->
<!-- ################################# Panel Control MENU ###################################### -->

<% 	if (isLogin){ %>
<jsp:include page="/jsp/layout/panel/control/panel_control.jsp" />
<%	} %>

<!-- ########################################################################################### -->
<!-- ################################# Visbility Control ####################################### -->

<% 
	if ( WebUtil.isTrue(panel.getHide()) || 
	     PanelUtil.isHideByPage(panel, dynPage) ||
	     PanelUtil.isHideByPageExclusive(panel, dynPage)) {
	     
		return;
	}
	
%>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
%>
<div class="panelTitle" style="<%=panel.getTitleStyleString()%>">
<%= panel.getPanelTitle() %>
</div>
<% } %>


<%
	
	String panelStyleSuffix = PanelUtil.getPanelStyleSuffix(panel); 
	String panelLinkStyleSuffix = PanelUtil.getPanelLinkStyleSuffix(panel);
	
	
	String styleString = "";
	
	if (panel.getAlign() == PanelUtil.PANEL_ALIGN_HORIZONTAL)
		styleString="style=\"float:left;\"";
	
%>

<div id="panel<%=panelStyleSuffix%>">

<%
	for(Iterator iter = pageCats.iterator();iter.hasNext();){
		ContentCategory cat = (ContentCategory) iter.next();	
		String linkHref = "m_" + dynPage.getPageName() + ".html?categoryId="+cat.getId()+"&cat=" + cat.getCategory();
		
		// This Style string will only be affected by the css file "link-content" section. 
		String data = "<div id=\"link-content" + panelLinkStyleSuffix + "\" "+ styleString +" > <a href=\"" + linkHref + "\" >" + cat.getCategory() + "</a> </div>";

%>
	<%= data %>
<%
	}
%>

<div class="clear"></div>

</div>


<% if (panel.getBottomSpace()>0) { %>
<div class="gapPadding" style="height: <%=panel.getBottomSpace() %>">
</div>	
<% } %>

