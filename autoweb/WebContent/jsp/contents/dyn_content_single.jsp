<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.session.*,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	SessionContext loginContext = (SessionContext) session.getAttribute("k_session_context");
	boolean loggedIn = false;
	if (loginContext != null && loginContext.isLogin() ) loggedIn = true; 

	PageDS pageDS = PageDS.getInstance(); 

	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 

	// Get content first from url. If not then from session var 
	long contentId = WebParamUtil.getLongValue(request.getParameter("cid"));		
	Content content = (Content)ContentDS.getInstance().getById(contentId);
	
	if (content == null) {
		content = (Content) session.getAttribute("k_page_content");
	}
	
	if (content == null) {
	    return;
	}
	
	Page dynPage = (Page)PageDS.getInstance().getById(content.getPageId());

	String link = "";
	if ( dynPage != null) 
		link = "<a href=\"/m_" + dynPage.getPageName() + ".html\" > <b><font color=\"orange\">" + dynPage.getPageMenuTitle() +" </font></b></a>";
	
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
	
	List ads = ContentAdDS.getInstance().getBySiteIdToContentIdList(site.getId(), content.getId());
	
	String imgStr = "";

	if ( content.getContentType() == 1 && WebUtil.isNotNull(content.getImageUrl()) ) {

		imgStr = "<img class=\"contentSingleBodyImg\" src=\"" + content.getImageUrl() +"\""; 
			
		imgStr += " />";
	}

	
	PageContentConfig c = PageContentConfigDS.getInstance().getObjectByContentId(content.getId());
    StyleSetContent styleSet = StyleSetContentDS.getInstance().getById(c==null?0:c.getContentStyleSetId());	

    String idPrefix = (styleSet == null?"cnt": styleSet.getIdPrefix());
	
	String frameStyleSuffix 	= PanelUtil.getSingleContentStyleSuffix(content); 
	String subjectStyleSuffix 	= PanelUtil.getSingleContentStyleSuffix(content); 
	String dataStyleSuffix 	= PanelUtil.getSingleContentStyleSuffix(content); 

%>	             
<br><br>

<div class="<%=idPrefix %>SingleFrame<%=frameStyleSuffix %>" >
<div class="<%=idPrefix %>SingleSubject<%=subjectStyleSuffix %>" >
	<%= (content!=null&& content.getContentSubject() !=null?content.getContentSubject():"")%>
</div>

<div class="<%=idPrefix %>SingleData<%=dataStyleSuffix %>">
	<%=imgStr %>
	<%= (content!=null&& content.getContent() !=null?content.getContent():"")%>
</div>

</div>

			<%= sourceLink %>

<br><br>


<TABLE width="100%">
<TR>

<!-- 
	<TD valign="top">       
		<TABLE width="100%">
		<TR> <TD height="30" bgcolor="#e0e0e0">
			<font size=3> &nbsp;<b><%= (content!=null&& content.getContentSubject() !=null?content.getContentSubject():"")%> </b></font> 
		</TD></TR>
		<TR> <TD height="20" >
		</TD></TR>

		<TR> <TD>
			<p><%= (content!=null&& content.getContent() !=null?content.getContent():"")%></p> 
		</TD></TR>
		<TR> <TD>
			<%= sourceLink %>
		</TD></TR>
		
		</TABLE>
	</TD>
 -->

	<TD valign="top">


<TABLE >
<%
	for (Iterator iter = ads.iterator();iter.hasNext();) {
		ContentAd ad = (ContentAd) iter.next();
%>
<TR>
 <TD align="right">
		<% if (loggedIn) { %>
		<a href=""> [del] </a> <br>
		<% } %>

 	<%= ad.getAdContent() %>
 </TD>
</TR>
<%
	}
%>
</TABLE>


</TD>
</TR>

<TR>
	<TD colspan="2" >
		<% if (loggedIn) { %>
		<a href="/t_content_ad_add2.html?prv_contentId=<%= content.getId()%>"> Add Sponsor Link </a> |
		<a href="/t_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage==null?"0":dynPage.getId() %>" >Edit</a> |
		<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%= dynPage==null?"0":dynPage.getId() %>"> Delete </a> <br>
		<% } %>
	</TD>
</TR>
<TR >
	<td colspan="2" align="right" valign="bottom" style="border-bottom : 2px <%= siteConfig.getColorBorders() %> solid;"> &nbsp; </td>
</tr>
</TABLE>

go back to page <%= link %>
