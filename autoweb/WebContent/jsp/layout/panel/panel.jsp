<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
		Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if ( panel == null) {
			JspUtil.getLogger().error("** Panel not found in session **");
			return;
		}
		if ( panel.getSiteId() != site.getId() && panel.getFeedId() == 0 ) return;


		boolean isAdminLogin = (sessionContext != null && sessionContext.isSiteAdminLogin());
		boolean isVerticalAlign = (panel.getAlign() == 0);
		
		// Get Page 
		String pageName = (String) session.getAttribute("k_page_name");
		Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);

		//=================================================================================================
		// Visiblity for printable
		//=================================================================================================
		if (WebUtil.isPritintable(request)){
			if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
				return;
		} else {
			if (panel.getShowOnlyPrint() == 1)
				return;
		}	

		//=================================================================================================
		// Admin Only Check
		//=================================================================================================
		if ( WebUtil.isTrue(panel.getAdminOnly()) && !sessionContext.isSuperAdminLogin()){
			return;
		}
%>
<%-- ############################################################################################## --%>
<%-- ################################# TOP SPACE ################################################## --%>
<% if (panel.getTopSpace()>0) { %>
	<div class="gapPadding" style="height: <%=panel.getTopSpace() %>">
	</div>	
<% } %>
<%-- ############################################################################################## --%>
<%-- ################################# PANEL CONTROL MENU ######################################### --%>
<% 	if (isAdminLogin){ %>
	<jsp:include page="/jsp/layout/panel/control/panel_control.jsp" />
<%	} %>
<%-- ############################################################################################## --%>
<%-- ################################# VISIBILITY CONTROL ######################################### --%>
<% 
	if ( WebUtil.isTrue(panel.getHide()) || 
	     PanelUtil.isHideByPage(panel, dynPage) ||
	     PanelUtil.isHideByPageExclusive(panel, dynPage)) {
	     
		return;
	}
%>
<%-- ############################################################################################## --%>
<%-- ############################################################################################## --%>
<%
	// find style configs and IDs.	
	String panelStyleSuffix = PanelUtil.getPanelStyleSuffix(panel); 
	String panelLinkStyleSuffix = PanelUtil.getPanelLinkStyleSuffix(panel);

	int panelHeight = (panel.getPanelHeight() ==0?20:panel.getPanelHeight());

	String panelHeightConfig = "";
	if (!isVerticalAlign){
		panelHeightConfig = "height=\"" + panelHeight +"px\"";
	}
	panelHeightConfig = "height=\"" + panelHeight +"px\"";

	List sitePosts = SitePostDS.getInstance().getByPanelId(panel.getId());
	if (sitePosts.size() == 0 && !isAdminLogin) return;
	if ( panel.getHide() == 1 && !isAdminLogin) return;
	
	String panelStyleString = panel.getStyleString();
	
%>

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
		String panelTitleStyleSuffix = PanelUtil.getPanelTitleStyleSuffix(panel);
%>
	<div class="panelTitle<%=panelTitleStyleSuffix %>" style="<%=panel.getTitleStyleString()%>">
	<%= panel.getPanelTitle() %>
	</div>
<% } %>

<%
	// Style application rules
	// 1. id will be set if getStyleString2() is specified. 
	// 2. Style string will be constructed and this will be applied simultaneously by importing in header section. 
	
	// 3. Also in-line style string is constructed from two configurations
	// (1) Style config from panel.getStyleString() + (2)  sitePost. getStyleString();
	//  will be added in line style="""" 
	
	// 

	long pageId  = (dynPage!=null?dynPage.getId():0);
	String alignStyle = (isVerticalAlign?"":"style=\"float:left\"");
	
	// This div ID string.
	// Two things could be set for this. 
	// (1) StyleStyring2 (2) Style Set specified by Default Code. (3) From Theme. 
	String dataStyleDivId = "";  

/*
	if (WebUtil.isNotNull(panel.getStyleString2())){
	    StyleConfig styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(panel.getStyleString2());
		if (styleConfig != null)
		    dataStyleDivId = "id=\"" + com.autosite.servlet.StyleUtil.getPanelDataStringDivId(panel, styleConfig) + "\"";
	} else if (WebUtil.isNotNull(panel.getStyleDefaultCode())) {
	    dataStyleDivId = "id=\"" + com.autosite.servlet.StyleUtil.getPanelDataStringDivId(panel, null) + "\"";
	} else { // Check the theme }
*/

    StyleConfig styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(panel.getStyleString2());
    dataStyleDivId = "id=\"" + com.autosite.servlet.StyleUtil.getPanelDataStringDivId(panel, styleConfig) + "\"";
	//System.out.println(">>>>>>>>>>>>>>>>>>>>>>> " + dataStyleDivId + " for Panel " + panel.getId());
%>
	<div id="panel<%=panelStyleSuffix%>">
<%
	
	
	for(Iterator iter = sitePosts.iterator();iter.hasNext();){
	
		SitePost sitePost = (SitePost) iter.next();

		// Do not include if it has page scope and not in the page. 
		if (sitePost.getPostScope() == 3){
			if (pageId != 0 && sitePost.getPageId() != pageId) 
				continue;
		}

		// SitePost specific style strings
		String sitePostStyleString = panelStyleString;
		if (sitePost.getStyleString() != null && sitePost.getStyleString().length()> 1)
			sitePostStyleString = panelStyleString + ";" +sitePost.getStyleString();
		
		String sizeConfigs = "";
		String strictSizeConfig = "";
		if (sitePost.getHeight() > 0 )  {
			sizeConfigs += sizeConfigs + " height=" +  sitePost.getHeight() + "px";
			strictSizeConfig += strictSizeConfig + " height=" +  sitePost.getHeight() + "px";
		}
		if (sitePost.getWidth() > 0 )  {
				strictSizeConfig += strictSizeConfig + " width=" +  sitePost.getWidth() + "px";
		}

		if (!isVerticalAlign) {
			if (sitePost.getWidth() > 0 ) 
				sizeConfigs += sizeConfigs + " width=" +  sitePost.getWidth() + "px";
		} else {
			sizeConfigs += sizeConfigs + " width=\"100%\"";
		}		

		
		String data = "";

		//======================================== Link Type ==============================================================
		if (sitePost.getPostType() == 2 ) { // LINK TYPE
			String linkDisplay = sitePost.getPostDataExtra() == null? sitePost.getPostData():sitePost.getPostDataExtra();
			// This Style string will only be affected by the css file "link-content" section. 
			data = "<div id=\"link-content" + panelLinkStyleSuffix + "\"> <a href=\"" + sitePost.getPostData() + "\" target=\"_blank\" >" + sitePost.getPostDataExtra() + "</a> </div>";
			
		//======================================== IMAGES ==============================================================
		} else if (sitePost.getPostType() == 3 ) { // IMAGES 
			
			String imgSrc = sitePost.getPostData();
			if (!imgSrc.startsWith("http://") && !imgSrc.startsWith("/"))
				imgSrc = "http://" + imgSrc;
				
			String imgStyleString = sitePostStyleString + "; float:left;";										
			
			data = "<img " + strictSizeConfig + " src=\"" + imgSrc + "\" style=\"" + imgStyleString +"\" />";  
			
			if (!WebUtil.isNull(sitePost.getOption1())){
				data += sitePost.getOption1();
			}
			
			if (!WebUtil.isNull(sitePost.getPostDataExtra())){
				data = "<a href=\"" + sitePost.getPostDataExtra() + "\"> " + data + "</a>"; 
			}
			
		//======================================== PADDING ==============================================================
		} else if (sitePost.getPostType() == 5 ) { // PADDING
			int paddingHeight = WebParamUtil.getIntValue(sitePost.getPostData());
			if ( paddingHeight <= 0 ) paddingHeight = 30;
			sitePostStyleString = sitePost.getStyleString();
			if (isVerticalAlign)		
				data = "<div  style=\"" + sitePostStyleString + "\" height=\"" + paddingHeight + "\">";
			else
				data = "<div  style=\"display:block; height:"+sitePost.getHeight()+"px; width:"+sitePost.getWidth()+"px;" + sitePostStyleString + "\">";
			
//			if (isVerticalAlign)		
//				data += "<TABLE> <TR><TD height=\"" + paddingHeight + "\" > &nbsp; </TD></TR></TABLE>" ;
//			else
//				data += "<TABLE> <TR><TD width=\"" + paddingHeight + "\" height=\"100%\"> &nbsp; </TD></TR></TABLE>" ;
				
			data += "</div>";		
				
		} else {

		//======================================== Default ==============================================================
			data = sitePost.getPostData();
			
			data = "<div " + dataStyleDivId + " style=\"" + sitePostStyleString + "\">" + data + "</div>";
		}
		
%>
	<div id="panelItem" <%=alignStyle %>>
<%
	if (WebUtil.isTrue(sitePost.getHide())){
%>

<% 	
		if (isAdminLogin) { 
%>		
	<div class="panelCotentMenu" style="float:none">
			<a href="/addSitePost.html?id=<%=sitePost.getId() %>&ef=true&hide=0" > <font size="1">[s]</font> </a>
	</div>
	<div style="background-color: grey;color:black;font-size:8px">hidden</div>
<%  	} %>
<%
	} else {
%>
<% 		if (isAdminLogin) { 
%>		
	<div class="panelCotentMenu" style="float:none">
			<a href="/t_edit_site_post.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[e]</font> </a> 
			<a href="/deleteSitePost.html?id=<%=sitePost.getId() %>&stid=<%=sitePost.getSiteId() %>" > <font size="1">[d]</font> </a>
			<a href="/addSitePost.html?id=<%=sitePost.getId() %>&ef=true&hide=1" > <font size="1">[h]</font> </a>
	</div>
<%  	} %>
	<%= data%>
<%	} // if(hided) %>
	</div>
<%	} //for %>

<div class="clear"></div>
</div> <!-- end panel div -->
<%-- ############################################################################################## --%>
<%-- ################################# BOTTOM SPACE ############################################### --%>
<% if (panel.getBottomSpace()>0) { %>
<div class="gapPadding" style="height: <%=panel.getBottomSpace() %>">
</div>	
<% } %>
