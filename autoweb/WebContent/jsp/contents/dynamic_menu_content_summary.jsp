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
	
	List siteContents2 = ContentDS.getInstance().getBySiteId(site.getId());

	Map pageMap = new HashMap();	
	for(Iterator iter = siteContents2.iterator();iter.hasNext();) {
	
		Content cont = (Content) iter.next();
		
		Page p = (Page) PageDS.getInstance().getById(cont.getPageId());
		if (p == null || p.getHide() == 1 ) continue;

		List forPage = (List) pageMap.get(new Long(p.getId()));
		if (forPage == null){
			forPage = new ArrayList();
			pageMap.put(new Long(p.getId()), forPage);
		}
		forPage.add(cont);
	}	

	for(Iterator iter = pageMap.keySet().iterator();iter.hasNext();) {
		Long key = (Long) iter.next();
		
		Page p = (Page) PageDS.getInstance().getById(key.longValue());
		
		if ( p == null) continue;
%>
		<table border=0 width=100% cellspacing="1" bgcolor="#99dae8">
			<tr> <td 	>
			<a href="/m_<%=p.getPageName()%>.html" > <b><%= p.getPageMenuTitle() %> </b> </a>
			</td></tr>
<% 
		List conts = (List) pageMap.get(key);
		
		for(Iterator iter2 = conts.iterator();iter2.hasNext();) {
	
			Content cont = (Content) iter2.next();
%>
			<tr> <td bgcolor="#ffffff">
			<a href="/t_dyn_content_single.html?cid=<%=cont.getId()%>&pid=<%=p.getId()%>" > <b> +<%= WebUtil.display(cont.getContentSubject(), "No Title " + cont.getContent().substring(0, 10)) %> </b> </a>
			</td></tr>
<% 			
			
		}
		
%>
		</table> <br><br>
<% 
		
	}

%>     
<%	

	if (true) return;
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

     