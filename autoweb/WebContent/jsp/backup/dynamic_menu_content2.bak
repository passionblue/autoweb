<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.SessionContext,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	String pageName = (String) session.getAttribute("k_page_name");
	String categoryName = (String) session.getAttribute("k_page_category");
	
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	
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
	if (dynPage.getPageViewType() == 1 ) { // Summary View Type. will show part of article only
		summaryLengh = 200;		
	}

	
%>	                    

<% 
//	if ( treeSet.size() > 0 ) {
	if ( true ) {
%>

<TABLE width="100%">
<TR>
	<TD>
		<TABLE border="1" bgcolor="#ffffff"  cellpadding="0" cellspacing="0" >
		<TR> 
		
			<td bgcolor="#aaaaaa" align="left" valign="top"> &nbsp; <b>Category</b> &nbsp;</td>

<!-- 
			<td  align="left" valign="top"> &nbsp; <a href="/m_<%=pageName%>.html" ><b>None</b> </a> &nbsp;</td>
			<td  align="left" valign="top"> &nbsp; <a href="/m_<%=pageName%>.html?cat=All" ><b>All</b> </a> &nbsp;</td>
-->		
		<% 
				List menuCats = new ArrayList(treeSet);
				menuCats.add(0,"All");
				menuCats.add(0,"None");
		
				for (Iterator iterator = menuCats.iterator(); iterator.hasNext();) {
			
				String cat = (String) iterator.next();
		%>

		<%
			// Highlight the menu.
			if (categoryName != null && categoryName.trim().equals(cat)) {
			
		%>
			<td  align="left" valign="top">&nbsp;<a href="/m_<%=pageName%>.html?cat=<%=cat%>" ><font color="orange"><b><%= cat %></b> </font></a> &nbsp;</td>
		<% } else { %>		
			<td  align="left" valign="top">&nbsp;<a href="/m_<%=pageName%>.html?cat=<%=cat%>" ><b><%= cat %></b> </a> &nbsp;</td>
		<%
			} 
		%>			
			
		<%
				}
		%>
		</TR>
		</TABLE>
	</TD>

<%
	if (loginContext != null && loginContext.isLogin() ) {
%>	
	<TD width="15%" align="right"> <b>Summary </b>[
		<a href="/updatePage.html?act=viewtype&type=1&pid=<%=dynPage.getId()%>" >Y</a>						
		<a href="/updatePage.html?act=viewtype&type=0&pid=<%=dynPage.getId()%>" >N</a>
		]						
	</TD>

	<TD width="15%" align="right"> <b>Col </b>
		<a href="/updatePage.html?act=config&col=1&pid=<%= dynPage.getId()%>" >1</a>						
		<a href="/updatePage.html?act=config&col=2&pid=<%= dynPage.getId()%>" >2</a>						
		<a href="/updatePage.html?act=config&col=3&pid=<%= dynPage.getId()%>" >3</a>						
		<a href="/updatePage.html?act=config&col=4&pid=<%= dynPage.getId()%>" >4</a>						
		<a href="/updatePage.html?act=config&col=5&pid=<%= dynPage.getId()%>" >5</a>						
	</TD>
<%
	}
%>
	
</TR>
</TABLE>
 
<%
	}
%>


<TABLE border="0" bgcolor="#ffffff"  width=100% cellpadding="0" cellspacing="0">
<TR> 
<%

for (int col = 1; col<= columnCount;col++) {


%>
<%
	if ( col > 1 ) {
%>
<TD valign="top" align="center" height=100% width="1%" >    
      <table border="0" bgcolor="<%= siteConfig.getColorBorders() %>" cellpadding="0" cellspacing="0" width=1 height=100%>
         <tr><td></td></tr>
      </table>
</TD>
<%
	}
%>


<TD width="<%=widthRate%>%" valign="top">

<TABLE border="0" bgcolor="#ffffff"  width=100% cellpadding="0" cellspacing="0">
<%

	int idx = 0;
    for (Iterator iterator = contents.iterator(); iterator.hasNext();) {
    
	    com.autosite.db.Content content = (com.autosite.db.Content) iterator.next();
	    idx++;
	    
	    if ( idx%columnCount == 0 ) {
	    	if ( columnCount != col) continue;
	    }
		else  if (idx%columnCount != col ) continue;
		
		String contentData = ""; 
		if ( content.getContent() != null && content.getContent().length() > summaryLengh ) {
			contentData = content.getContent().substring(0, summaryLengh) + "....";
		} else {
			contentData = content.getContent();
		}
		
%>    

<%
	if (content.getContentSubject() != null && !content.getContentSubject().trim().equals("")) {
%>

	<TR>
		<TD> <b> <%= content.getContentSubject() %></b> </TD>
	</TR>
<%
	}
%>	
	<TR>
		<TD > <%= contentData %>
		</TD>
	</TR> 
	<TR>
		<TD align="right" valign="bottom" style="border-bottom : 1px <%= siteConfig.getColorBorders() %> solid;">
			<a href="/t_dyn_content_single.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[read article]</font> </a>

<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		
			<a href="/t_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[edit]</font> </a>
			<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[del]</font> </a> 
<% } else { %>
		</TD>
<% }%>		
	</TR>
	
	
	
<%   
    }
%>     
</TABLE> 
</TD>
<%   
    }
%> 
</TR>
</TABLE>

<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		
<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>" > <b>Add to page [<%= dynPage.getPageMenuTitle()%>]</b></a>
<%   
    }
%> 



<br><br>
