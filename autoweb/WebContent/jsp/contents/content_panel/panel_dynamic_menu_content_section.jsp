<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.session.*,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String pageName = (String) session.getAttribute("k_page_name");
	
	Page dynPage = DynPageUtil.getCurrentPage(request, site.getId());
	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 
	
	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());
	boolean isLogin = (sessionContext != null && sessionContext.isLogin());
	
%>	                    


<%
	Panel panel = (Panel) session.getAttribute("p_current_panel");
	if (panel == null) return;

	PanelPageConfigDS panelPageConfigDS = PanelPageConfigDS.getInstance();
			
	if ( panel.getPageOnly() == 1 && panel.getPageId() != dynPage.getId()) return;
			
		//Menu Style
		String menuStyleName = "panel-menu";
		if (panel.getHide() == 1 ) 	menuStyleName="panel-menu-hidden";

		// Styles configs
		StyleConfig styleConfig = PanelUtil.getPanelStyleConfig(panel.getId());
		LinkStyleConfig linkStyleConfig = PanelUtil.getPanelLinkStyleConfig(panel.getId());
		
		
		StyleTheme styleTheme = SiteStyleUtil.getTheme(site.getId());
		StyleConfig panelStyleConfig = null;
		if ( styleTheme != null) {
		    panelStyleConfig = StyleConfigDS.getInstance().getById(styleTheme.getContentPanelStyleId());
		}
		
		if ( styleConfig == null) styleConfig = PanelUtil.getDefaultPagePanelStyleConfig();

		// PanelPageConfig
		PanelPageConfig panelPageConfig = panelPageConfigDS.getByPanelId(panel.getId());
		long panelPageConfigId = 0;
		

		int summaryLengh = 10000000;
		boolean summaryViewEnabled = false;

		if ( panelPageConfig != null) { 
			panelPageConfigId = panelPageConfig.getId();
			if (panelPageConfig.getPageDisplaySummary() == Constants.PanelPageConfig_Summarized ) { // Summary View Type. will show part of article only
				summaryLengh = 200;		
				summaryViewEnabled = true;
			}
		}
		 
		// find style configs and IDs.	
		String panelStyleSuffix 	= PanelUtil.getPanelStyleSuffix(panel); 
		String panelLinkStyleSuffix = PanelUtil.getPanelLinkStyleSuffix(panel);
		String listContFrameSuffix  = PanelUtil.getPanelContListFrameStyleSuffix(panel);
		String listContSubSuffix 	= PanelUtil.getPanelContListSubjectStyleSuffix(panel);
		String listContDataSuffix 	= PanelUtil.getPanelContListDataStyleSuffix(panel);
		
		// Border Style config
		String verticalBorderStyle = StyleUtil.makeBorderStyle("left", panelStyleConfig);
		String horiztalBorderStyle = StyleUtil.makeBorderStyle("bottom", panelStyleConfig); // Need for title bottom
		
		
		// Append title string
		
		if (!WebUtil.isNull(panel.getTitleStyleString())){
			horiztalBorderStyle = horiztalBorderStyle + ";" + panel.getTitleStyleString();
		}
%>


<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% if (panel.getTopSpace()>0) { %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR><TD height="<%=panel.getTopSpace()%>">
	</TD></TR>
</TABLE>
<% } %>


<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% if (isLogin){ %>
<div class="<%=menuStyleName %>">
		<a href="/v_add_site_post.html?prv_panelId=<%=panel.getId()%>">A</a>|
		<a href="/v_panel_edit.html?id=<%=panel.getId()%>&hide=1&ef=true">E</a>|	
		<a href="/v_style_config_add.html?prv_panel_id=<%=panel.getId()%>">S</a>|
		<a href="/v_link_style_config_add.html?prv_panel_id=<%=panel.getId()%>">L</a>|
		<a href="/panelAction.html?del=true&id=<%=panel.getId()%>">D</a>|

<% if (panel.getHide() == 1) { %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=0&ef=true">Show</a>|
<% }else{ %>
		<a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a>|
<% } %>
		<%=panel.getId()+"(" + panel.getPanelType()+")"%>|

<% if (styleConfig != null) { %>
		<%=styleConfig.getId()%>|
<% } %>

		<b>Display </b>[
		<a href="/panelPageConfigAction.html?act=ef&pageDisplaySummary=0&id=<%=panelPageConfigId%>&panelId=<%=panel.getId()%>" >All</a>
		<a href="/panelPageConfigAction.html?act=ef&pageDisplaySummary=1&id=<%=panelPageConfigId%>&panelId=<%=panel.getId()%>" >Summary</a>						
		<a href="/panelPageConfigAction.html?act=ef&pageDisplaySummary=2&id=<%=panelPageConfigId%>&panelId=<%=panel.getId()%>" >Heading Only</a>						
		]						
		<b>Panel Col</b>[
		<a href="/panelAction.html?ef=true&columnCount=1&id=<%= panel.getId()%>" >1</a>						
		<a href="/panelAction.html?ef=true&columnCount=2&id=<%= panel.getId()%>" >2</a>						
		<a href="/panelAction.html?ef=true&columnCount=3&id=<%= panel.getId()%>" >3</a>						
		<a href="/panelAction.html?ef=true&columnCount=4&id=<%= panel.getId()%>" >4</a>						
		<a href="/panelAction.html?ef=true&columnCount=5&id=<%= panel.getId()%>" >5</a>						
		]
</div>			
<% } %>

<!-- ########################################################################################## -->

<%
	if (panel.getHide() == 1) return;
	int columnCount = ( panel.getColumnCount() == 0?1: panel.getColumnCount());
%>

<div id="panel<%=panelStyleSuffix%>" class="panel">
<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
		String panelTitleStyleSuffix = PanelUtil.getPanelTitleStyleSuffix(panel);
%>

<div class="panelTitle<%=panelTitleStyleSuffix %>"  style="<%=WebUtil.display(panel.getTitleStyleString()) %>">
	<%= panel.getPanelTitle() %>
</div>

<div style="<%=horiztalBorderStyle%>">
</div>

<% } %>

<TABLE bgcolor="transparent"  width="100%" cellpadding="0" cellspacing="0">
<TR> 
<%

/*
	int widthRate = (100 - columnCount + 1)/columnCount;
	int widthRateArray[] = new int[columnCount];
	
	if ( WebUtil.isNull(panel.getOption1())){
	    
		for (int col = 1; col<= columnCount;col++) {
		    widthRateArray[col-1] = widthRate; 
		}	    
	    
	} else {
	    
	    // expecting option1 value of Panel, contains like 50-30-20 to represent rate of each column
	    StringTokenizer tok = new StringTokenizer(panel.getOption1(), ",|=-:");
	    
	    int idx = 0;
	    while(tok.hasMoreTokens()){
	        widthRateArray[idx] = WebParamUtil.getIntValue(tok.nextToken());
	        idx++;
	    }
	}
	
	// Auto fill for the empty rate
	for (int idx = 0; idx< columnCount;idx++) {
	    if ( widthRateArray[idx] == 0 && idx > 0){
			int rateOfPrevColumn =widthRateArray[idx-1];
			
			int newRate = rateOfPrevColumn/2;
			
			widthRateArray[idx-1] = newRate;
			widthRateArray[idx] = newRate;
	    }
	}	    
	
	// in case sum over 100, decduct evenly 
	int total = 0;	
	for (int idx = 0; idx< columnCount;idx++) {
	    total += widthRateArray[idx];
	}

	if ( total > 100) {
	    
	   	double ratio = 100.0/(long)total;
	    
		for (int idx = 0; idx< columnCount;idx++) {
		    widthRateArray[idx] = (int)(widthRateArray[idx]*ratio);
		}
	}
*/	
	
	List panelContents = ContentDS.getInstance().getByPanelId(panel.getId());
	int widthRateArray[] = PanelStyleUtil.getWidthForMultiColumnPanels(columnCount, panel.getOption1());
	
	System.out.println(" panel " + panel.getId() + " column " + columnCount);
	for (int col = 1; col<= columnCount;col++) {
	    System.out.println("    width: " + widthRateArray[col-1]);
	}
	
	
	for (int col = 1; col<= columnCount; col++) {
	
		String verticalBorderStr = "";
		if ( col > 1 ) verticalBorderStr +=verticalBorderStyle;
		
%>

<TD width="<%=widthRateArray[col-1]%>%" valign="top" style="<%= verticalBorderStr%>">
<TABLE class="content-panel-table" style="width:100%">

<%
	// ##################################### Content Table laying out contents in vertical manner ##################################

	int idxContent = 0;
	String borderStyleStr = "";
	if (panelStyleConfig != null){
	    borderStyleStr = "bgcolor=\""+panelStyleConfig.getBorderColor()+ "\" height=\""+panelStyleConfig.getBorderWidth()+"px\""; 
	}

	for (Iterator iterator = panelContents.iterator(); iterator.hasNext();) {
	    Content content = (Content) iterator.next();

		if ( content.getPanelId() != panel.getId()) {
			continue;
		}

		if ( content.getColumnNum() != col ) {
			if ( content.getColumnNum() != 0 || col != 1){
				continue;
			}
		}
		idxContent++; 

		// Formatting content data
		String subjectData = "";		
		String contentData = "";

		String imageUrl="";
		String imageHeight="";
		if ( content.getImageHeight() >0 ) 
			imageHeight="height=\"" + content.getImageHeight() + "\"";
	
		String imageWidth="";
		if ( content.getImageWidth() >0 ) 
			imageWidth="width=\"" + content.getImageWidth() + "\"";
		String imgHtmlStr = "";
		
		if ( WebUtil.isNotNull(content.getImageUrl())){
		    imgHtmlStr = "<img src=\""+WebUtil.display(content.getImageUrl()) +"\"" + imageHeight + " " + imageWidth +"/>";
		}
%>    

<% 
	if (idxContent > 1 ) {
%>
<TR><TD <%=borderStyleStr %> ></TD></TR>
<% } %>

<TR><TD valign="top" class="content-panel-table-td" >
<%
	//########### Left Image Content ##########################################################
	if ( content.getContentType() == 1) {
		contentData = ContentUtil.getContentAbstract(content, summaryLengh);
	
%>
		<div id="cntContFrame<%=listContFrameSuffix %>" class="content-panel-frame" >
<%
		if (!WebUtil.isNull(content.getContentSubject())) {
%>
			<div id="cntListContSubject<%=listContSubSuffix %>" class="content-panel-subject" >
			<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" >  
			    <%=content.getContentSubject() %>  
			</a>
			</div>
<%
	 	}
%>	
			<div id="cntListContData<%=listContDataSuffix %>"  class="content-panel-data" >
				<%= imgHtmlStr %>
				<%= contentData %>
			</div>
		</div> 		
<%	

	//########### Link Type Content ##########################################################
	} else if ( content.getContentType() == 4) {
%>
			   <div id="link-content<%=panelLinkStyleSuffix %>" class="content-link">
				   <a href="<%= content.getContent()%>" target="_blank" >  
				       <%=content.getContentSubject() %> 
				   </a>
			   </div>
<%

	//########################## Default Content ##############################################
	} else { 

			subjectData = ContentUtil.getContentHeading(content, 20); 			
			contentData = ContentUtil.getContentAbstract(content, summaryLengh);
%>
		<div id="cntContFrame<%=listContFrameSuffix %>" class="content-panel-frame">
<%
		//SUBJECT -------------------------------------------------------------------
		if (!WebUtil.isNull(content.getContentSubject())) {
%>
				<div id="cntListContSubject<%=listContSubSuffix %>"  class="content-panel-subject" >
				   	<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>&title=<%= subjectData %>" >  
				       <%=subjectData %> 
				   	</a>
			   	</div>
<%
		}
		if (panelPageConfig != null && panelPageConfig.getPageDisplaySummary() !=  Constants.PanelPageConfig_HeadingOnly) {
		    
%>	
				<div id="cntListContData<%=listContDataSuffix %>"  class="content-panel-data" >
					<%= imgHtmlStr %>
					<%= contentData %>
				</div>
			</div>
<%
		}
	}
%>	

<%
	//########### Source Link ##########################################################
		String sourceLink = "";
		if ( content.getSourceUrl() != null) {
			
			String sourceUrl = content.getSourceUrl();
			String sourceName = content.getSourceName();
			
			if (!sourceUrl.startsWith("http://")) sourceUrl = "http://" + sourceUrl;

			if ( !WebUtil.isNull(sourceName)) 
				sourceLink = "<a href=\"" + sourceUrl + "\"> <font size=\"1\">[source:" + sourceName + "]</font> </a>";
			else
				sourceLink ="";
		}
%>	 
<% 	
	//########### Content Control Menu ##########################################################
	if ( (isLogin) || !WebUtil.isNull(content.getSourceUrl()) ) { 
%>		
		<div style="display:block;">
			<%= sourceLink %>
			<% 	if ( isLogin ) { %>
			<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[read article]</font> </a>
			<a href="/t_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[edit]</font> </a>
			<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[del]</font> </a> 
			<% }%>		
		</div>			
<% 
		} 
%>	
	</TD></TR>
<%   
    } // colContents
%>     

<% 	
	if ( isLogin )  { 
%>		
	<TR>
		<TD align="right" style="border-top: 2px double orange;">
			<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>&prv_panelId=<%= panel.getId()%>&prv_columnNum=<%=col%>" > <b>Add To This Column </b></a>
		</TD>
	</TR>
<%   
    } // if
%>     

<%
	if (idxContent == 0 ){
%>
	<TR><TD>&nbsp;</TD></TR> <!-- need this line to keep the empty column to hold the shape-->
<%
	}
%>

</TABLE>
</TD> <!-- Columns TD -->
<%   
    } // columnCounts
%> 
</TR>

</TABLE><!-- Outer table -->
</div>

<% if (panel.getBottomSpace()>0) { %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR><TD height="<%=panel.getBottomSpace()%>">
	</TD></TR>
</TABLE>
<% } %>





