<%@ page language="java" import="com.jtrend.util.*,com.seox.util.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.seox.work.UserBO,java.util.*,org.apache.commons.lang.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JspUtil.getLogger().info("###################################################################################################");
	JspUtil.getLogger().info("## START layout ###################################################################################");
	JspUtil.getLogger().info("###################################################################################################");

	WebUtil.printRequest(request);
	WebUtil.printSessionKeys(session);
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
	
	if (WebUtil.isNoFrame(request)){
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

