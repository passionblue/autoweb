<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*,com.autosite.session.*,com.jtrend.session.SessionContext,com.autosite.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext loginContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	String pageName = (String) session.getAttribute("k_page_name");

	Panel panel = (Panel) session.getAttribute("p_current_panel");
	if (panel == null) return;
	if ( panel.getSiteId() != site.getId()) return;

	boolean isLogin = (loginContext != null && loginContext.isSiteAdminLogin());
	int panelHeight = (panel.getPanelHeight() ==0?20:panel.getPanelHeight());
	boolean isVerticalAlign = (panel.getAlign() == 0);

	//menu 
	String menuStyleName = "panel-menu";
	if (panel.getHide() == 1 ) 	menuStyleName="panel-menu-hidden";

	StyleConfig styleConfig = PanelUtil.getPanelStyleConfig(panel.getId());
	LinkStyleConfig linkStyleConfig = PanelUtil.getPanelLinkStyleConfig(panel.getId());


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
<div id="<%=menuStyleName %>" class="<%=menuStyleName %>">
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
		
</div>			
<% } %>


<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<% 
	// If panel is hidden 
	if (panel.getHide() == 1) return;
%>
<%	 
	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	long pageId  = (dynPage!=null?dynPage.getId():0);
	
	System.out.println("DYN PG" + dynPage.getId());
	
	// Check if this panel is hidden by page view option for panel. 
	boolean hideByPageOnlyOption = false;		
	if (panel.getPageOnly() == 1 && panel.getPageId() != pageId){
		hideByPageOnlyOption = true;	
	}

	if (hideByPageOnlyOption) { 
		if (isLogin) {
%>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="transparent">
	<TR>
		<TD> Will Show Only For Page <%= panel.getPageId() %></TD>
	</TR>
</TABLE>
<%
		}	 
		return;
	}
%>

<!-- ########################################################################################### -->
<!-- ########################################################################################### -->

<%
	String categoryName = (String) session.getAttribute("k_page_category");
	
	List allContents = ContentDS.getInstance().getByPageId(dynPage.getId());
	
	String pageDetail = "Page Details: pageName=" + 	dynPage.getPageName() + 
	                    ",site=" + site.getSiteUrl() +  	
	                    ",siteId=" + site.getId() +  	
	                    ",PageId=" + dynPage.getId(); 
	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 


	int columnCount = siteConfig.getHomeColCount();
	if (dynPage.getPageColCount() > 0 ) columnCount = dynPage.getPageColCount();
	
	int widthRate = (100 - columnCount + 1)/columnCount;

	// filter out by category	
	List contents = new ArrayList();
	
	TreeSet treeSet = new TreeSet();
	
	boolean filterByCategory = true;
	
	if (categoryName == null || categoryName.trim().equals("")) {
		filterByCategory = false;
	}

    for (Iterator iterator = allContents.iterator(); iterator.hasNext();) {

	    Content content = (Content) iterator.next();

		if ( filterByCategory ) {
			if ( categoryName.equals(content.getCategory()) ) {
				contents.add(content);
			} else if (categoryName.equals("All")) {
				contents.add(content);
			}
		} else {
		 	
		 	if (content.getCategory() == null || content.getCategory().trim().equals("")) {
				contents.add(content);
		 	}
		}

		// Category menu creation
		if (content.getCategory() != null && !content.getCategory().trim().equals("")) {
			treeSet.add(content.getCategory());
		}
	}
	
	if (categoryName == null) categoryName = "None"; // to highlitgt the menu

	
	// If it is summary view type, 	
	int summaryLengh = 100000;
	boolean summaryViewEnabled = false;
	if (dynPage.getPageViewType() == 1 ) { // Summary View Type. will show part of article only
		summaryLengh = 200;		
		summaryViewEnabled = true;
	}
	
	
	// Page option
	
	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	
%>	                    

<%
	if (loginContext != null && loginContext.isSiteAdminLogin() ) {
%>	
<TABLE width="100%" cellpadding="0"  cellspacing="0">
<TR>	
	<TD  align="left" height="20"> [
	<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>" > <b>ADD CONTENT <%= dynPage.getPageMenuTitle()%></b></a>
	]
	</TD>
	<TD align="left" height="20"> <b>Summary </b>[
		<a href="/updatePage.html?act=viewtype&type=1&pid=<%=dynPage.getId()%>" >Y</a>						
		<a href="/updatePage.html?act=viewtype&type=0&pid=<%=dynPage.getId()%>" >N</a>
		]						
	</TD>

	<TD align="left" height="20"> <b>Col </b>
		<a href="/updatePage.html?act=config&col=1&pid=<%= dynPage.getId()%>" >1</a>						
		<a href="/updatePage.html?act=config&col=2&pid=<%= dynPage.getId()%>" >2</a>						
		<a href="/updatePage.html?act=config&col=3&pid=<%= dynPage.getId()%>" >3</a>						
		<a href="/updatePage.html?act=config&col=4&pid=<%= dynPage.getId()%>" >4</a>						
		<a href="/updatePage.html?act=config&col=5&pid=<%= dynPage.getId()%>" >5</a>						
	</TD>
	</TR>
</TABLE>	
<%
	}
%>


<!-- ### Content Adverstisement -->
<Table>
	<TR><TD valign="top">
	<jsp:include page="/jsp/layout/content_add_top.jsp" />
	</TD></TR>
</Table>

<%
	if (allContents.size() == 0) return;
%>


<TABLE border="1" bgcolor="#ffffff"  width=100% cellpadding="3" cellspacing="0" style="border-width: 1px; border-style: solid;border-color:<%= siteConfig.getColorBorders()%>;border-collapse: collapse;">

<TR>
	<TD colspan="<%=columnCount%>">
			<form name="myform" >
			Category:
	 		<select name="mylist" onchange="nav()">
			<option value="/m_<%=pageName%>.html?cat=All" <%=HtmlUtil.getOptionSelect(categoryName, "All")%>>All</option>
			<option value="/m_<%=pageName%>.html?cat=NoCat" <%=HtmlUtil.getOptionSelect(categoryName, "NoCat")%>>No Category Posts</option>
		<% 
				List menuCats = new ArrayList(treeSet);
				//menuCats.add(0,"All");
				//menuCats.add(0,"None");
		
				for (Iterator iterator = menuCats.iterator(); iterator.hasNext();) {
			
				String cat = (String) iterator.next();
		%>
			<option value="/m_<%=pageName%>.html?cat=<%=cat%>" <%=HtmlUtil.getOptionSelect(categoryName, cat)%>><%=WebUtil.display(cat)%></option>
		<%
			}
		%>
			</select>
			</form>
	</TD>
</TR>

<% if  ( contents.size() > 0) {%>


<TR> 


<%

for (int col = 1; col<= columnCount;col++) {

%>

<TD width="<%=widthRate%>%" valign="top" >

<%

	int idx = 0;
    for (Iterator iterator = contents.iterator(); iterator.hasNext();) {
    
	    com.autosite.db.Content content = (com.autosite.db.Content) iterator.next();
	    idx++;
	    
	    if ( idx%columnCount == 0 ) {
	    	if ( columnCount != col) continue;
	    }
		else  if (idx%columnCount != col ) continue;

		// Formatting content data		
		String contentData = ""; 
%>    
<TABLE border="0" bgcolor=white  width=100% cellpadding="1" cellspacing="1">



<%
	//########### Left Image Content ##########################################################
	if ( content.getContentType() == 1) {

		String imageUrl="";
	
		String imageHeight="";
		if ( content.getImageHeight() >0 ) 
			imageHeight="height=\"" + content.getImageHeight() + "\"";
	
		String imageWidth="";
		if ( content.getImageWidth() >0 ) 
			imageWidth="width=\"" + content.getImageWidth() + "\"";
	

		if ( content.getContent() != null && content.getContent().length() > summaryLengh ) {
			contentData = content.getContent().substring(0, summaryLengh) + "....";
		} else {
			contentData = content.getContent();
		}

	
%>

<%
	//# SUBJECT #
	if (!WebUtil.isNull(content.getContentSubject())) {
%>

	<TR >
		<TD height="15" bgcolor="#ffffff" > 
		   <a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" >  
		       <b><font size="2" ><%=content.getContentSubject() %></font></b> 
		   </a>
		</TD>
	</TR >
<%
	}
%>	

	<TR>
		<TD bgcolor="#ffffff" >

	<TABLE>
		<TR> 
			
			<% if (!WebUtil.isNull(content.getImageUrl()) && !summaryViewEnabled){ %>
		
			<TD valign="top">
				<img  src="<%=WebUtil.display(content.getImageUrl()) %>" <%= imageHeight%> <%= imageWidth%>/>
			</TD>
			<%} %>
			<TD valign="top">
				<%=  WebUtil.display(contentData) %>
			</TD>
		</TR>
	</TABLE>

		</TD>
	</TR> 

<%	

	//########### Link Type Content ##########################################################
	}else if ( content.getContentType() == 4) {
%>

	<TR >
		<TD height="15" bgcolor="#ffffff" > 
		   <a href="<%= content.getContent()%>" target="_blank" >  
		       <b><font size="2" ><%=content.getContentSubject() %></font></b> 
		   </a>
		</TD>
	</TR >

<%

	} else { //########################## DEFAULT ##############################################
		if ( content.getContent() != null && content.getContent().length() > summaryLengh ) {
			contentData = content.getContent().substring(0, summaryLengh) + "....";
		} else {
			contentData = content.getContent();
		}
%>

<%
	//# SUBJECT #
	if (!WebUtil.isNull(content.getContentSubject())) {
%>
	<TR >
		<TD height="15" bgcolor="#ffffff" > 
		   <a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" >  
		       <b><font size="2" ><%=content.getContentSubject() %></font></b> 
		   </a>
		</TD>
	</TR >
<%
	}
%>	


	<TR>
		<TD bgcolor="#ffffff" >
		<%= contentData %>
		</TD>
	</TR> 

<%
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
<% 	if ( (loginContext != null && loginContext.isLogin()) || !WebUtil.isNull(content.getSourceUrl()) ) { 
%>		
	<TR>
		<TD bgcolor="#ffffff"  align="right" valign="bottom" >
			<%= sourceLink %>
			<% 	if ( loginContext != null && loginContext.isLogin() ) { %>
			<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[read article]</font> </a>
			<a href="/t_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[edit]</font> </a>
			<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[del]</font> </a> 
			<% }%>		
		</TD>
	</TR>
<% }%>		

	<TR>
		<TD width="100%" height="1" bgcolor="<%=siteConfig.getColorBorders() %>" >
		</TD>
	</TR> 
</TABLE> 
<%   
    }
%>     
</TD>
<%   
    }
%> 
</TR>
<%   
    }
%> 

</TABLE>

<br><br>



