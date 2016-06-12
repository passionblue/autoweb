<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	SessionContext sessionContext = (SessionContext) session.getAttribute("k_session_context");
	Panel panel = (Panel) session.getAttribute("p_current_panel");
	if ( panel == null || panel.getHide() == 1) return;
	
	
	String pageName = (String) session.getAttribute("k_page_name");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List pages = PageDS.getInstance().getBySiteId(site.getId());
	
	if (pages == null) {
		return;
	}


    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	if (siteConfig ==null) {
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}
	
	if ( siteConfig.getMenuReverse() == 1) {
		Collections.reverse(pages);
	}
	
	
	String lineColor = WebUtil.display(siteConfig.getMenuLineColor(), "#e0e0e0");
	String frontColor = WebUtil.display(siteConfig.getMenuFrontColor(), "orange");
	
	boolean isLogin = (sessionContext != null && sessionContext.isLogin());
	
%>

<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
<% if (isLogin){ %>
<TR>

	<TD bgcolor="#dddddd">
		<b> <a href="#">Add Content</a> | <a href="/panelAction.html?id=<%=panel.getId()%>&hide=1&ef=true">Hide</a></b>	
	</TD>
</TR>	
<% } %>
<TR>
<TD>
	<TABLE border="0" width="100%" cellpadding="5" cellspacing="0" >
		<TR> <TD height="20" style="border-bottom : 1px #e0e0e0 solid;"> </TD></TR>

	<%
		for(Iterator iter = pages.iterator();iter.hasNext();) {
		    Page p = (Page) iter.next();
		    
		    if ( p.getPageName().equals("XHOME")) 
		    	continue;
		    	
		    if ( p.getHide() == 1 ) 
		    	continue; 	
		    
            if ( p.getHeaderPage() == 1 ) 
                continue;   
		    // set different color for selected menu
		    
			String link = "";  
		    if ( pageName != null && pageName.trim().equals(p.getPageName())) {
		    	link = "<a href=\"/m_" + p.getPageName() + ".html\" > <b><font size=2 color=\"" + frontColor +"\">" + p.getPageMenuTitle() +" </font></b></a>";
		    } else {
				link = "<a href=\"/m_" + p.getPageName() + ".html\" > <b><font size=2 > " + p.getPageMenuTitle() +" </font></b></a>";  
		    }
		    
  
	 %>
	
        <TR> <TD height="20" style="border-bottom : 1px <%=lineColor %> solid;" > <%= link %> </TD></TR>
		
	<%
	}
	%>		

	</TABLE>    
</TD>
</TR>	
</TABLE>	



