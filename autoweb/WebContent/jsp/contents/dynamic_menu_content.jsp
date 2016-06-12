<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.session.*,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	AutositeSessionContext sessionCtx = (AutositeSessionContext) session.getAttribute("k_session_context");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	SessionWrapper wrap = sessionCtx.getSessionWrapper();

	String pageName = (String) session.getAttribute("k_page_name");

	String categoryName = (String) request.getParameter("cat");
	long catId = WebParamUtil.getLongValue((String) request.getParameter("categoryId"));
	ContentCategory catInReq = ContentCategoryDS.getInstance().getById(catId);

	boolean filterByCategory = false;
	
	if (catInReq != null) {
		categoryName = catInReq.getCategory();
		filterByCategory = true;
	} 
	
	Page dynPage = wrap.getPage();
	
	if ( dynPage == null ) {
	    //TODO, what can id do gracefully exit here or show something.
		System.out.println("XXXXXXXXXXXXXXXXXXXX Page now found");
	    return;
	}
	
	List pageContents = ContentDS.getInstance().getByPageId(dynPage.getId());

	WebDebug.getInstance().putDebug(session, "catId="+catId);
	WebDebug.getInstance().putDebug(session, ""+pageContents.size());
	WebDebug.getInstance().putDebug(session, "DynPageId="+dynPage.getId());
	                    
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
	

    for (Iterator iterator = pageContents.iterator(); iterator.hasNext();) {

	    Content content = (Content) iterator.next();

		if ( filterByCategory ) {
			if ( catId == content.getCategoryId() ) {
				contents.add(content);
			} 
		} else {
			contents.add(content);
		}
	}
	
	if (categoryName == null) categoryName = "None"; // to highlitgt the menu
	
	// If it is summary view type, 	
	int summaryLengh = 100000;
	if (dynPage.getPageViewType() == PageViewUtil.PAGE_VIEW_TYPE_SUMMARY ) { // Summary View Type. will show part of article only
		summaryLengh = 200;		
	} else if(dynPage.getPageViewType() == PageViewUtil.PAGE_VIEW_TYPE_TITLEONLY ){
		summaryLengh = 0;		
	}


	PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());
	StyleSetContent contStyleSet = null;
	String idPrefix = "cnt";
	
	if (pageConfig != null){
		contStyleSet = StyleSetContentDS.getInstance().getById(pageConfig.getContentStyleSetId());
		if (contStyleSet != null) 
			idPrefix = contStyleSet.getIdPrefix();
	}

	
%>	                    

<% 
	if (sessionCtx != null && sessionCtx.isSiteAdminLogin()) {
%>
<TABLE width="100%" cellpadding="0"  cellspacing="0">

<TR> <TD height="1" align="right" valign="bottom" style="border-bottom : 1px <%= siteConfig.getColorBorders() %> solid;" colspan="10"> &nbsp;</TD>
</TR>

<TR>
	<TD height="20">
		<TABLE border="0" bgcolor="#555555"  cellpadding="0" cellspacing="0" >
		<TR> 


		</TR>
		</TABLE>
	</TD>
	<TD  align="left" height="20"> <b>Add to </b>[
	<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>" > <b>Html</b></a> |
	<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>&prv_contentType=1&prv_noWyswyg=true" > <b>Non-Html  (<%= dynPage.getPageMenuTitle()%>/<%= dynPage.getId()%>)</b></a>
	]
	</TD>
	<TD width="25%" align="right" height="20"> <b>Summary </b>[
		<a href="/updatePage.html?act=viewtype&type=1&pid=<%=dynPage.getId()%>" >Y</a>						
		<a href="/updatePage.html?act=viewtype&type=0&pid=<%=dynPage.getId()%>" >N</a>
		<a href="/updatePage.html?act=viewtype&type=2&pid=<%=dynPage.getId()%>" >Title Only</a>
		]						
	</TD>

	<TD width="15%" align="right" height="20"> <b>Col </b>
		<a href="/updatePage.html?act=config&col=1&pid=<%= dynPage.getId()%>" >1</a>						
		<a href="/updatePage.html?act=config&col=2&pid=<%= dynPage.getId()%>" >2</a>						
		<a href="/updatePage.html?act=config&col=3&pid=<%= dynPage.getId()%>" >3</a>						
		<a href="/updatePage.html?act=config&col=4&pid=<%= dynPage.getId()%>" >4</a>						
		<a href="/updatePage.html?act=config&col=5&pid=<%= dynPage.getId()%>" >5</a>						
	</TD>
</TR>

<TR> 
	<TD align="right" valign="bottom" style="border-top : 1px <%= siteConfig.getColorBorders() %> solid;" colspan="10"> &nbsp; </TD>
</TR>
</TABLE>
 
<%
	}
%>


<div id="categoryFrame" style="padding: 5px 0px 20px 5px">
<% 
	List pageCats = ContentCategoryDS.getInstance().getByPageId(dynPage.getId());
	for (Iterator iterator = pageCats.iterator(); iterator.hasNext();) {
		ContentCategory cat = (ContentCategory) iterator.next();
%>

<%
		// Highlight the menu.
		if (catId == cat.getId()) {
%>
		<div  bgcolor="#ffffff" style="font:normal normal bold 15px verdana; color:orange; float: left;margin:0px 20px 0px 0px">
			<a href="/m_<%=pageName%>.html?categoryId=<%=cat.getId()%>&cat=<%=cat.getCategory()%>" style="font:normal normal bold 15px verdana; color:orange;"><%= cat.getCategory().toUpperCase() %></a>
		</div>
<% 		} else if (categoryName != null ) { %>		
		<div  bgcolor="#ffffff" style="font:normal normal bold 15px verdana; color:#888; float: left;margin:0px 20px 0px 0px">
			<a href="/m_<%=pageName%>.html?categoryId=<%=cat.getId()%>&cat=<%=cat.getCategory()%>" style="font:normal normal bold 15px verdana; color:#888;"><%= cat.getCategory().toUpperCase() %></a>
		</div>
<%
		} 
%>			
<%
	}
%>

<%
		if (pageCats.size() > 0){
%>
		<div  bgcolor="#ffffff" style="font:normal normal bold 12px verdana; color:orange; float: left;margin:5px 20px 0px 0px">
			<a href="/m_<%=pageName%>.html?categoryId=<%=0%>" style="font:normal normal bold 10px verdana; color:orange">show all</a> 
		</div>
<%
		} else {
%>
<!-- 
		<div  bgcolor="#ffffff" style="font:normal normal bold 12px verdana; color:#888; float: left;margin:5px 20px 0px 0px">
			<a href="/m_<%=pageName%>.html?categoryId=<%=0%>" style="font:normal normal bold 10px verdana; color:#888;">show all</a> 
		</div>
-->		
<%
		} 
%>
</div><div class="clear"></div>

<div id="contentListFrame" style="padding: 5px 0px 20px 5px">

<TABLE border="0" bgcolor="#ffffff"  width="100%" cellpadding="0" cellspacing="0">
<TR> 
<%
for (int col = 1; col<= columnCount;col++) {

%>
<%
	if ( col > 1 ) {
%>

	<TD valign="top" align="center" height="100%" width="1%" >    
	      <table border="0" bgcolor="<%= siteConfig.getColorBorders() %>" cellpadding="0" cellspacing="0" width="0" height="100%" >
	         <tr><td></td></tr>
	      </table>
	</TD>
<%
	}
%>

	<TD width="<%=widthRate%>%" valign="top">

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
		<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">

<%
	//########### Subject/Title ##########################################################
	if (!WebUtil.isNull(content.getContentSubject())) {
%>

			<TR >
				<TD > &nbsp;
					<div class="<%=idPrefix%>ListContSubject">					
						<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId()%>" ><%= content.getContentSubject() %></a>
					</div>
				</TD>
			</TR >
<%
	}
%>	



			<TR>
				<TD bgcolor="#ffffff" >
<%
	//########### Default Content ##########################################################
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

	//########### Link Type Content ##########################################################
	}else if ( content.getContentType() == 4) {
%>

	<div class="<%=idPrefix%>ListContData">
	   <a href="<%= content.getContent()%>" target="_blank" >  
	       <%=content.getContentSubject() %> 
	   </a>
	</div>		

<%	
	//########### Link Type Content ##########################################################
	} else {
	
	    if ( summaryLengh > 0 ) {
			if ( content.getContent() != null && content.getContent().length() > summaryLengh ) {
				contentData = content.getContent().substring(0, summaryLengh) + "....";
			} else {
				contentData = content.getContent();
			}
%>

	<div class="<%=idPrefix%>ListContData">
		<%= contentData %>
	</div>		

<%
	    }
	}
%>	
				</TD>
			</TR> 

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

			<TR>
				<TD bgcolor="#ffffff"  align="right" valign="bottom" style="border-bottom : 1px <%= siteConfig.getColorBorders() %> solid;">
					<%= sourceLink %>
					<a href="/t_dyn_content_single.html?cid=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[read article]</font> </a>

<% 	if (sessionCtx != null && sessionCtx.isLogin() ) { 
%>		
					<a href="/t_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[edit]</font> </a>
					<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%=dynPage.getId() %>" > <font size="1">[del]</font> </a> 
<% } else { %>
				</TD>
<% }%>		
			</TR>
		</TABLE> <br/>
<%   
    } // for contents
%>     
	</TD>
<%   
    }
%> 
</TR>
</TABLE>
<!-- END -->
</div><div class="clear"></div>