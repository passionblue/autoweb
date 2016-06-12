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


<TABLE border="0" bgcolor="#ffffff"  width="100%" cellpadding="0" cellspacing="0" >
<TR> 
<%
for (int col = 1; col<= columnCount;col++) {
%>
<%
	if ( col > 1 ) {
%>
<TD valign="top" align="center" height="100%" width="1%" >    
      <table border="0" bgcolor="<%= siteConfig.getColorBorders() %>" cellpadding="0" cellspacing="0" width="0" height="100%">
         <tr><td></td></tr>
      </table>
</TD>
<%
	}
%>


<TD width="<%=widthRate%>%" valign="top">

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
		
		if ( content.getContentType() == 1) {
		
		} else { // DEFAULT
			if ( content.getContent() != null && content.getContent().length() > summaryLengh ) {
				contentData = content.getContent().substring(0, summaryLengh) + "....";
			} else {
				contentData = content.getContent();
			}
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
<TABLE border="0" bgcolor="green"  width=100% cellpadding="5" cellspacing="1">

<%
	//########### SUBJECT ##########################################################
	if (!WebUtil.isNull(content.getContentSubject())) {
%>

	<TR >
		<TD height="20" bgcolor="#e0e0e0"> <b> &nbsp;<font size="2" ><%= content.getContentSubject() %></font></b> </TD>
	</TR >
<%
	}
%>	


	<TR>
		<TD bgcolor="#ffffff" >

<%
	//########### Content ##########################################################
	if ( content.getContentType() == 1) {

		String imageUrl="";
	
		String imageHeight="";
		if ( content.getImageHeight() >0 ) 
			imageHeight="height=\"" + content.getImageHeight() + "\"";
	
		String imageWidth="";
		if ( content.getImageWidth() >0 ) 
			imageWidth="width=\"" + content.getImageWidth() + "\"";
	
	
%>
	<TABLE>
		<TR> 
			<TD valign="top">
				<img  src="<%=WebUtil.display(content.getImageUrl()) %>" <%= imageHeight%> <%= imageWidth%>/>
			</TD>
			
			<TD valign="top">
				<%=  WebUtil.display(content.getContent()) %>
			</TD>
		</TR>
	</TABLE>


<%	
	} else {
		if ( content.getContent() != null && content.getContent().length() > summaryLengh ) {
			contentData = content.getContent().substring(0, summaryLengh) + "....";
		} else {
			contentData = content.getContent();
		}
%>

		<%= contentData %>

<%
	}
%>	
		</TD>
	</TR> 

		
	<TR>
		<TD align="right" valign="bottom"  bgcolor="#ffffff">
		    <%= sourceLink %>
			<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[read]</font> </a>

<% 	
	if ( loginContext != null && loginContext.isLogin() ) { 
%>		
			<a href="/t_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[edit]</font> </a>
			<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[del]</font> </a> 
<% } else { %>
		</TD>
<% }%>		
	</TR>
</TABLE>
<BR>
<%   
    }
%>     
</TD>
<%   
    }
%> 
</TR>

</TABLE>
<br>
<% 	if (loginContext != null && loginContext.isLogin() ) { 
%>		
<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>" > <b>add to db</b></a>
<% }%>		


<!-- ############################################################################################## -->
<!-- ############################################################################################## -->
<%   
    } else {
%>     
<!-- ############################################################################################## -->
<!-- ############################################################################################## -->

<%
	
	List siteContents = ContentDS.getInstance().getBySiteId(site.getId());

%>     

     
<TABLE border="0"   width=100% cellpadding="0" cellspacing="1">

<%  for (Iterator iterator = siteContents.iterator(); iterator.hasNext();) { 
		Content cont = (Content) iterator.next();
		
		Page p = (Page) PageDS.getInstance().getById(cont.getPageId());
		
		if (p == null || p.getHide() == 1 ) continue;

		String subject = WebUtil.display(cont.getContentSubject(), "No Subject");
		String contentFooter = "Page: "+ WebUtil.displayPageTitle(p.getPageMenuTitle()) + " Category: " + WebUtil.display(cont.getCategory()) + " Last Updated: " +  cont.getUpdatedTime();							
		String contentData = "";
%>
	<TR><TD width="100%">
		<TABLE width="100%" bgcolor="green" cellpadding="2" cellspacing="1">
		
		
<%
		//########### SUBJECT ##########################################################
		if (!WebUtil.isNull(cont.getContentSubject())) {
%>

			<TR >
				<TD height="20" bgcolor="ffcc66"> <b> &nbsp;<font size="2" ><%= cont.getContentSubject() %></font></b> </TD>
			</TR >
<%
		}
%>	
			
	<TR>
		<TD bgcolor="#ffffff" >

<%
	//########### Content ##########################################################
	if ( cont.getContentType() == 1) {

		String imageUrl="";
	
		String imageHeight="";
		if ( cont.getImageHeight() >0 ) 
			imageHeight="height=\"" + cont.getImageHeight() + "\"";
	
		String imageWidth="";
		if ( cont.getImageWidth() >0 ) 
			imageWidth="width=\"" + cont.getImageWidth() + "\"";
	
	
%>
	<TABLE>
		<TR> 
			<TD valign="top">
				<img  src="<%=WebUtil.display(cont.getImageUrl()) %>" <%= imageHeight%> <%= imageWidth%>/>
			</TD>
			
			<TD valign="top">
				<%=  WebUtil.display(cont.getContent()) %>
			</TD>
		</TR>
	</TABLE>


<%	
	} else {
		if ( cont.getContent() != null && cont.getContent().length() > summaryLengh ) {
			contentData = cont.getContent().substring(0, summaryLengh) + "....";
		} else {
			contentData = cont.getContent();
		}
%>

		<%= contentData %>

<%
	}
%>	
		</TD>
	</TR> 
			
			<TR> <TD bgcolor="#ffffff">
			<font size="1"> <%= contentFooter %> </font>
			</TD> </TR>
			
			
		</TABLE>		
	</TD></TR>
	<TR><TD> &nbsp;  </TD> </TR>
<%	} %>	

</TABLE>

<%   
    }
%>     
     
     