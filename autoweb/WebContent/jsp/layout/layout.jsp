<%@ page language="java" import="com.jtrend.util.*,com.seox.util.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.seox.work.UserBO,java.util.*,org.apache.commons.lang.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JspUtil.getLogger().info("###################################################################################################");
	JspUtil.getLogger().info("## START layout.jsp ###################################################################################");
	JspUtil.getLogger().info("###################################################################################################");

//	WebUtil.printRequest(request);
//	WebUtil.printSessionKeys(session);
//	WebUtil.printPageContext(pageContext);
	
	long startPage = System.currentTimeMillis();
	//

	Site 			site 		= SiteDS.getInstance().registerSite(request.getServerName());
	SessionWrapper	wrap 		= SessionWrapper.wrapIt(request, site.getId());
    SiteConfig 		siteConfig 	= SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = wrap.getSessionCtx(); //()(AutositeSessionContext) session.getAttribute("k_session_context");

	//Default main layout page
	String layoutPage = "/jsp/layout/layout/layout-css.jsp";
    
	// from site Config
    if ( siteConfig != null && !WebUtil.isNull(siteConfig.getLayoutPage())) {
    	layoutPage = siteConfig.getLayoutPage();
		JspUtil.getLogger().info("Layout page set by SiteConfig " + layoutPage); 
    }

	// From Theme 
	ThemeAggregator themeAggregator = StyleThemeUtil.getThemeAggregatorForSite(site.getId());
	if (themeAggregator != null && themeAggregator.getLayoutPage() != null) {
	    layoutPage = themeAggregator.getLayoutPage();
		JspUtil.getLogger().info("Layout page set by theme " + layoutPage); 
	}
	
    // Get current page.		
	String pageName = (String) session.getAttribute("k_page_name");
	Page dynPage = (pageName==null?null:PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName));
	boolean hideDebug = false;
	
	//======================================================================================================
	// Checking various condition and determine which format the output shoould be. 	    
	//======================================================================================================

	boolean setByContextBoundType = false;
	if ( sessionContext.getSelectedSessionType() != SessionContext.SESSION_TYPE_NOTSET ) {
	  
	    switch (sessionContext.getSelectedSessionType()) {
	    case SessionContext.SESSION_TYPE_DEFAULT:
	        break;
	    case SessionContext.SESSION_TYPE_MOBILE_PHONE:
		    layoutPage = "/jsp/layout/layout/layout-mobile.jsp";
			JspUtil.getLogger().info("Layout page set by context type Mobile " + layoutPage); 
			setByContextBoundType = true;
	        break;
	    case SessionContext.SESSION_TYPE_MOBILE_TABLET:
		    layoutPage = "/jsp/layout/layout/layout-tablet.jsp";
			JspUtil.getLogger().info("Layout page set by context type Tablet " + layoutPage); 
			setByContextBoundType = true;
	        break;
	    case SessionContext.SESSION_TYPE_MOBILE_PAGELESS:
	        //Shoud not happen
	        break;
		default:	        
	    }
	}
	
	if (setByContextBoundType) {
	// has been set above	    
	}
	else if (WebUtil.isNoFrame(request)){
//		layoutPage = "/jsp/layout/layout-noframe.jsp";
		layoutPage = "/jsp/layout/layout/layout-css-content-only.jsp";
		JspUtil.getLogger().info("Layout page set by noFrame " + layoutPage); 
	
	} else if (WebUtil.isPartDisplay(request)){
	    layoutPage = "/jsp/layout/layout/layout-partition.jsp";
		JspUtil.getLogger().info("Layout page set by partition " + layoutPage); 
	    hideDebug = true;

	} else if (WebUtil.isPritintable(request)){
	    layoutPage = "/jsp/layout/layout/layout-printable.jsp";
		JspUtil.getLogger().info("Layout page set by printable " + layoutPage); 
		hideDebug = true;
		
	} else if (WebUtil.isTablet(request)){ // TODO for different mobile types
	    layoutPage = "/jsp/layout/layout/layout-tablet.jsp";
		JspUtil.getLogger().info("Layout page set by Mobile " + layoutPage); 
	
	} else if (WebUtil.isMobilePhone(request)){ // TODO for different mobile types
	    layoutPage = "/jsp/layout/layout/layout-mobile.jsp";
		JspUtil.getLogger().info("Layout page set by Mobile " + layoutPage); 
	        
	} else if (WebUtil.isChurSingleExpenseReport(request)){
	    layoutPage = "/jsp/layout/chur/layout-single-report.jsp"; // Chur Single Expense Form
		JspUtil.getLogger().info("Layout page set by expense report " + layoutPage); 
	
	} else if (WebUtil.isPritintable(request)){
	    layoutPage = "/jsp/layout/layout/layout-printable.jsp";
		JspUtil.getLogger().info("Layout page set by printable " + layoutPage); 
	} else {
	    
	}
	
	boolean siteRegistered = WebParamUtil.getBooleanValue(site.getRegistered());

	if (!siteRegistered && ! sessionContext.isSuperAdminLogin()) {
		layoutPage = "/jsp/layout/layout/layout-nonregister.jsp";
	}	
	
	//======================================================================================================
    WebDebug.getInstance().clear(session);
	//########################################################################################################
	// MAIN LAYOUT PAGE
	try{
%>

		<jsp:include page="<%=layoutPage%>" />

<%
	}catch(Exception e){
		e.printStackTrace(); //TODO have to properly report it and display the correct display
	}
	
	if (hideDebug) return;

	//########################################################################################################

	long endPage = System.currentTimeMillis();
	long timeTakenPage = (endPage-startPage);

	String 		actionTimeTaken = (String) pageContext.getAttribute("g_action_time_taken");
	PageView 	pageView        = (PageView) session.getAttribute("k_view_pageview");

    String sessionUser = sessionContext.isLogin()? sessionContext.getUsername():"#ANONYMOUS";
    String paramStr = "XXXXX"; //RequestUtil.getParameterString(request);
    
    SeoxLogger.filelog("timelog",  sessionUser + "|" + StringUtils.leftPad(String.valueOf(timeTakenPage), 5) + "|"+ request.getServerName() + "|"+ request.getRemoteAddr() + "|" + request.getRequestURI() + "?" + paramStr);
	
	
%>

<%
if (WebDebug.needsShowSimpleDebug(request) && sessionContext.isSuperAdminLogin()){
%>

<br>
<br>
<br>
<TABLE width="100%" align="center" cellpadding="0" cellspacing="0"  style="padding: 0px;border: 1px solid #6699CC;background-color: #FAFAFA;border-collapse: collapse;">
	<TR>
		<TD style="font-size: 9px" height="12px" style="padding: 0px 0px 0px 0px; text-align:left; ">Action: <%= actionTimeTaken %> </TD>
		<TD style="font-size: 9px" height="12px" style="padding: 0px 0px 0px 0px; text-align:left; ">Page: <%= timeTakenPage %> </TD>
		<TD style="font-size: 9px" height="12px" style="padding: 0px 0px 0px 0px; text-align:left; "><%= pageView.getAlias()+":"+pageView.getContentPage().replaceAll(".jsp", ".txt") %></TD>
	</TR>
	<TR>
		<TD style="font-size: 9px" height="12px" style="padding: 0px 0px 0px 0px; text-align:left; "><%=sessionContext.getSerial() +":"+sessionContext.getCreatedTime() %> </TD>
		<TD style="font-size: 9px" height="12px" style="padding: 0px 0px 0px 0px; text-align:left; "><%=session.getId()+":"+ new Date(session.getCreationTime())%> </TD>
		<TD><%= layoutPage %></TD>
	</TR>
</TABLE>

<%
}
%>

<%
String pageDubug = "------";
if (sessionContext != null && sessionContext.isSuperAdmin()) {
if ( dynPage != null) pageDubug=dynPage.getPageName() + ":" + dynPage.getPageMenuTitle();
%>

<TABLE border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
	<TR><TD>
	<jsp:include page="/jsp/layout/debug.jsp" />
	</TD></TR>
</TABLE>

<TABLE border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
	<TR>
	<TD width="200">Action Time Taken</TD><TD><%= actionTimeTaken %> msec</TD>
	</TR>
	<TR>
	<TD width="200">Page Time Taken</TD><TD><%= timeTakenPage %> msec</TD>
	</TR>
	<TR>
	<TD width="200">Layout Page</TD><TD><%= layoutPage %></TD>
	</TR>
</TABLE>

<%= pageDubug %>

<br/><br/>
<%
	List webDebugs = WebDebug.getInstance().getDebugs(session);
%>
<%= webDebugs.size() %>

<TABLE class="mytable1">
<%
	for(Iterator iter = webDebugs.iterator();iter.hasNext();){
		String line = (String) iter.next();
%>
	<TR>
		<TD width="100%">
			<%= line %>
		</TD>
	</TR>
<%
	}
%>
</TABLE>
<%
	}


JspUtil.getLogger().info("###################################################################################################");
JspUtil.getLogger().info("## END layout.jsp ####################################################################################");
JspUtil.getLogger().info("###################################################################################################");

%>
