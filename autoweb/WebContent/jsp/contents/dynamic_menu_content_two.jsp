<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.util.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	AutositeSessionContext loginContext = (AutositeSessionContext) session.getAttribute("k_session_context");

    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	if (siteConfig ==null) siteConfig = SiteConfigDS.getDefaultSiteConfig();  	
	 

	String pageName 	= (String) session.getAttribute("k_page_name");
	String categoryName = request.getParameter("cat");
	
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	
	ContentCategory contCat = ContentCategoryDS.getInstance().getByPageIdCategory(dynPage.getId(), categoryName);
	
	List allContents = ContentDS.getInstance().getByPageId(dynPage.getId());
	List contentsByCat = null;

	String categoryAllOptionSelect = "";
	if ( categoryName == null || contCat == null|| categoryName.equals("__all")){
		contentsByCat = ContentDS.getInstance().getByPageId(dynPage.getId());
		categoryAllOptionSelect = " selected ";
	} else {
		contentsByCat = ContentDS.getInstance().getByPageIdCategoryId(dynPage.getId(), contCat.getId());
	}

	int columnCount = dynPage.getPageColCount();
	if (columnCount == 0) columnCount = 1;
	int widthRate = (100 - columnCount + 1)/columnCount;
	
	// If it is summary view type, 	
	int summaryLengh = 100000;
	boolean summaryViewEnabled = false;
	if (dynPage.getPageViewType() == 1 ) { // Summary View Type. will show part of article only
		summaryLengh = 200;		
		summaryViewEnabled = true;
	}
	
%>	                    

<%
	if (loginContext != null && loginContext.isLogin() ) {
%>	
<TABLE width="100%" cellpadding="0"  cellspacing="0">
<TR>	
	<TD  align="left" height="20"> [
	<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>&prv_contentType=1" > <b>ADD (<%= dynPage.getPageMenuTitle()%>/<%= dynPage.getId()%>)</b></a>|
	<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>&prv_contentType=1&prv_noWyswyg=true" > <b>ADD Non-Html  (<%= dynPage.getPageMenuTitle()%>/<%= dynPage.getId()%>)</b></a>
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
	//if (allContents.size() == 0) return;
%>


<TABLE border="1" bgcolor="#ffffff"  width=100% cellpadding="3" cellspacing="0" style="border-width: 1px; border-style: solid;border-color:<%= siteConfig.getColorBorders()%>;border-collapse: collapse;">

<TR>
	<TD colspan="<%=columnCount%>">
			<form name="myform" >
			Category:
	 		<select name="mylist" onchange="nav()">
			<option value="/m_<%=pageName%>.html?cat=__all" <%= categoryAllOptionSelect %>>All</option>
		<% 
				List menuCats = ContentCategoryDS.getInstance().getByPageId(dynPage.getId());
		
				for (Iterator iterator = menuCats.iterator(); iterator.hasNext();) {
			
				ContentCategory cat = (ContentCategory) iterator.next();
		%>
			<option value="/m_<%=pageName%>.html?cat=<%=cat.getCategory()%>" <%=HtmlUtil.getOptionSelect(categoryName, cat.getCategory())%>><%=WebUtil.display(cat.getCategory())%></option>
		<%
			}
		%>
			</select>
			</form>
	</TD>
</TR>

<% if  ( contentsByCat.size() > 0) {%>

<TR> 

<%

for (int col = 1; col<= columnCount;col++) {

%>

<TD width="<%=widthRate%>%" valign="top" >

<%

	int idx = 0;
    for (Iterator iterator = contentsByCat.iterator(); iterator.hasNext();) {
    
	    com.autosite.db.Content content = (com.autosite.db.Content) iterator.next();
	    if (WebUtil.isTrue(content.getHide())) continue;
	    
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

	<TR>
		<TD bgcolor="#ffffff" >

			<jsp:include page="<%= ContentUtil.getContentPartByType(content.getContentType()) %>" >
   				<jsp:param name="contentId" value="<%= content.getId() %>"/>
			</jsp:include>

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


	<TR>
		<TD bgcolor="#ffffff" >

			<jsp:include page="<%= ContentUtil.getContentPartByType(content.getContentType()) %>" >
   				<jsp:param name="contentId" value="<%= content.getId() %>"/>
			</jsp:include>

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
		String noWyswyg = "false";
		if (!WebUtil.isTrue(content.getInHtml())){
			noWyswyg = "true";
		}
%>		
	<TR>
		<TD bgcolor="#ffffff"  align="right" valign="bottom" >
			<%= sourceLink %>
			<% 	if ( loginContext != null && loginContext.isLogin() ) { %>
			<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[read article]</font> </a>
			<a href="/t_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>&prv_noWyswyg=<%=noWyswyg%>" > <font size="1" color="blue">[edit]</font> </a>
			<a href="/addDynContent.html?id=<%=content.getId() %>&ef=true&hide=1" > <font size="1" color="blue">[hide]</font> </a>
			<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1" color="blue">[del]</font> </a> 
			<% }%>		
		</TD>
	</TR>
<% }%>		

	<TR>
		<TD width="100%" height="1" style="border-bottom: black solid 1px" >
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
<% 	if (loginContext.isSiteAdminLogin()){ %>
<jsp:include page="/jsp/contents/parts/partAllHideContents.jsp" />
<%	} %>
