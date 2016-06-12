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
	
	
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	
	List dbContents = ContentDS.getInstance().getByPageId(dynPage.getId());

	String pageDetail = "Page Details: pageName=" + 	dynPage.getPageName() + 
	                    ",site=" + site.getSiteUrl() +  	
	                    ",siteId=" + site.getId() +  	
	                    ",PageId=" + dynPage.getId();  	
	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}

	int columnCount = siteConfig.getHomeColCount();
	if (dynPage.getPageColCount() > 0 ) columnCount = dynPage.getPageColCount();
	
	int widthRate = (100 - columnCount + 1)/columnCount;

	// If it is summary view type, make 	
	int summaryLengh = 100000;
	if (dynPage.getPageViewType() == 1 ) { // Summary View Type. will show part of article only
		summaryLengh = 200;		
	}
	
	
%>     

<%   
    if (!WebUtil.isTrue(siteConfig.getDisplayAllHome())) {
%>     


<TABLE width="100%">
<TR>
	<TD>
		<TABLE border="0" bgcolor="#ffffff"  cellpadding="0" cellspacing="0" >
		<TR> 
		
			<td align="left" valign="top"> &nbsp; &nbsp;</td>
		
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

	<TD width="15%" align="right"> <b>Col </b>[
		<a href="/updatePage.html?act=config&col=1&pid=<%= dynPage.getId()%>" >1</a>						
		<a href="/updatePage.html?act=config&col=2&pid=<%= dynPage.getId()%>" >2</a>						
		<a href="/updatePage.html?act=config&col=3&pid=<%= dynPage.getId()%>" >3</a>						
		<a href="/updatePage.html?act=config&col=4&pid=<%= dynPage.getId()%>" >4</a>						
		<a href="/updatePage.html?act=config&col=5&pid=<%= dynPage.getId()%>" >5</a>
		]						
	</TD>
	
<%
	}
%>
	
</TR>
</TABLE>


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
    for (Iterator iterator = dbContents.iterator(); iterator.hasNext();) {
    
	    com.autosite.db.Content content = (com.autosite.db.Content) iterator.next();
	    idx++;
	    
	    if ( idx%columnCount == 0 ) {
	    	if ( columnCount != col) continue;
	    }
		else  if (idx%columnCount != col ) continue;

		// format content data		
		String contentData = ""; 
		if ( content.getContent() != null && content.getContent().length() > summaryLengh ) {
			contentData = content.getContent().substring(0, summaryLengh) + "....";
		} else {
			contentData = content.getContent();
		}
		//formatting source link
		
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
	if (content.getContentSubject() != null && !content.getContentSubject().trim().equals("")) {
%>

	<TR>
		<TD height="20" bgcolor="#e0e0e0"> &nbsp;<font size="2" ><b> <%= content.getContentSubject() %></b></font> </TD>
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
		    <%= sourceLink %>
			<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[read full article]</font> </a>

<% 	
	if ( loginContext != null && loginContext.isLogin() ) { 
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
<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>" > <b>add to db</b></a>
<% }%>		

<TABLE border="0" bgcolor="#ffffff"  width=100% height="50px" cellpadding="0" cellspacing="0">
<%


	List contents = com.autosite.content.ContentSilo.getInstance().getContent2(request.getServerName());

    for (Iterator iterator = contents.iterator(); iterator.hasNext();) {
    com.autosite.content.Content content = (com.autosite.content.Content) iterator.next();
    
    
    
%>    
	<TR>
	
		<TD style="border-bottom : 1px #e0e0e0 solid;"> <p><%= content.getContent() %> </p></TD>
		<TD width="5%" valign="bottom" style="border-bottom : 1px #e0e0e0 solid;"> <a href="/deleteContent.html?id=<%=content.getId() %>" > del </a> </TD>
	</TR>
<%   
    }
%>     
</TABLE> 

<%   
    } else {
%>     


<!-- ################################################################# -->
<%
	
	List siteContents = ContentDS.getInstance().getBySiteId(site.getId());

%>     

     
<TABLE border="0" bgcolor="#ffffff"  width=100% cellpadding="0" cellspacing="0">

<%  for (Iterator iterator = siteContents.iterator(); iterator.hasNext();) { 
		Content c = (Content) iterator.next();
		
		Page p = (Page) PageDS.getInstance().getById(c.getPageId());
		
		if (p == null || p.getHide() == 1 ) continue;

		String subject = WebUtil.display(c.getContentSubject(), "No Subject");
		String contentFooter = "Page: "+ WebUtil.displayPageTitle(p.getPageMenuTitle()) + " Category: " + WebUtil.display(c.getCategory()) + " Last Updated: " +  c.getUpdatedTime();							
		
%>
	<TR><TD width="100%">
		<TABLE width="100%">
			<TR> <TD bgcolor="#ccffff" width="100%">
			<b><%= subject %> </b>    
			</TD> </TR>
			
			<TR> <TD>
			<%= c.getContent() %>
			</TD> </TR>
			
			<TR> <TD>
			<font size="1"> <%= contentFooter %> </font>
			</TD> </TR>
			
			
		</TABLE>		
	</TD></TR>
<%	} %>	
</TABLE>

<%   
    }
%>     
     
     