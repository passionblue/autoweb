<%@page import="com.autosite.util.chur.ChurUtil"%>
<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.chur.*,com.autosite.content.*,java.util.*,com.autosite.util.*,com.autosite.util.access.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
		Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
		Panel panel = (Panel) session.getAttribute("p_current_panel");
		if ( panel == null) {
			JspUtil.getLogger().error("** Panel not found in session **");
			return;
		}
		//if ( panel.getSiteId() != site.getId() && panel.getFeedId() == 0 ) return;

		boolean isAdminLogin = (sessionContext != null && sessionContext.isSiteAdminLogin());
		boolean isVerticalAlign = (panel.getAlign() == 0);
		
		// Get Page 
		//String pageName = (String) session.getAttribute("k_page_name");
		//Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
		Page dynPage = DynPageUtil.getCurrentPage(request, site.getId());

		//=================================================================================================
		// Visiblity for printable
		//=================================================================================================
		if (WebUtil.isPritintable(request)){
			if ( panel.getShowInPrint() == 0 && panel.getShowOnlyPrint() == 0)
				return;
		} else {
			if (panel.getShowOnlyPrint() == 1)
				return;
		}	

		//=================================================================================================
		// Admin Only Check
		//=================================================================================================
		if ( WebUtil.isTrue(panel.getAdminOnly()) && !sessionContext.isSuperAdminLogin()){
			return;
		}
%>
<%-- ############################################################################################## --%>
<%-- ################################# TOP SPACE ################################################## --%>
<% if (panel.getTopSpace()>0) { %>
	<div class="gapPadding" style="height: <%=panel.getTopSpace() %>">
	</div>	
<% } %>
<%-- ############################################################################################## --%>
<%-- ################################# PANEL CONTROL MENU ######################################### --%>
<% 	if (isAdminLogin){ %>
	<jsp:include page="/jsp/layout/panel/control/panel_control.jsp" />
<%	} %>
<%-- ############################################################################################## --%>
<%-- ################################# VISIBILITY CONTROL ######################################### --%>
<% 
	if ( WebUtil.isTrue(panel.getHide()) || 
	     PanelUtil.isHideByPage(panel, dynPage) ||
	     PanelUtil.isHideByPageExclusive(panel, dynPage)) {
	     System.out.println("Hide by");
		return;
	}
%>
<%-- ############################################################################################## --%>
<%-- ############################################################################################## --%>
<%
	// find style configs and IDs.	
	String panelStyleSuffix = PanelUtil.getPanelStyleSuffix(panel); 
	String panelLinkStyleSuffix = PanelUtil.getPanelLinkStyleSuffix(panel);

	int panelHeight = (panel.getPanelHeight() ==0?20:panel.getPanelHeight());

	String panelHeightConfig = "";
	if (!isVerticalAlign){
		panelHeightConfig = "height=\"" + panelHeight +"px\"";
	}
	panelHeightConfig = "height=\"" + panelHeight +"px\"";

	List sitePosts = SitePostDS.getInstance().getByPanelId(panel.getId());
	if ( panel.getHide() == 1 && !isAdminLogin) return;
	String panelStyleString = panel.getStyleString();

	if (!sessionContext.isLogin()) return;
%>

<%
	if (!WebUtil.isNull(panel.getPanelTitle())) {
		String panelTitleStyleSuffix = PanelUtil.getPanelTitleStyleSuffix(panel);
%>
	<div class="panelTitle<%=panelTitleStyleSuffix %>" style="<%=panel.getTitleStyleString()%>">
	<%= panel.getPanelTitle() %>
	</div>
<% } %>

<%
	// Style application rules
	// 1. id will be set if getStyleString2() is specified. 
	// 2. Style string will be constructed and this will be applied simultaneously by importing in header section. 
	
	// 3. Also in-line style string is constructed from two configurations
	// (1) Style config from panel.getStyleString() + (2)  sitePost. getStyleString();
	//  will be added in line style="""" 
	
	// 

	long pageId  = (dynPage!=null?dynPage.getId():0);
	String alignStyle = (isVerticalAlign?"":"style=\"float:left\"");
	
	// This div ID string.
	// Two things could be set for this. 
	// (1) StyleStyring2 (2) Style Set specified by Default Code. (3) From Theme. 
	String dataStyleDivId = "";  

/*
	if (WebUtil.isNotNull(panel.getStyleString2())){
	    StyleConfig styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(panel.getStyleString2());
		if (styleConfig != null)
		    dataStyleDivId = "id=\"" + com.autosite.servlet.StyleUtil.getPanelDataStringDivId(panel, styleConfig) + "\"";
	} else if (WebUtil.isNotNull(panel.getStyleDefaultCode())) {
	    dataStyleDivId = "id=\"" + com.autosite.servlet.StyleUtil.getPanelDataStringDivId(panel, null) + "\"";
	} else { // Check the theme }
*/

    StyleConfig styleConfig = StyleConfigDS.getInstance().getObjectByStyleKey(panel.getStyleString2());
    dataStyleDivId = "id=\"" + com.autosite.servlet.StyleUtil.getPanelDataStringDivId(panel, styleConfig) + "\"";


   	int _selectedYear = ChurWebUtil.getSelectedYear(request );

	String _selectedWeek = ChurWebUtil.getSelectedWeek(request );

	System.out.println(">>>>>>>>>>>>>>>>>>>>>>> " + _selectedYear);
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>> " + _selectedWeek);

%>

<script type="text/javascript">
function setweek()
{
	var w = document.getElementById( 'selectweeksite' ).selectedIndex;
	var week_add = document.getElementById( 'selectweeksite' ).options[w].value;
	var y = document.getElementById( 'selectyearsite' ).selectedIndex;
	var year_add = document.getElementById( 'selectyearsite' ).options[y].value;
	var url = '/setTo.html?group=ChurApp&key=x&value=y&week=' + week_add + '&year=' + year_add;
	//alert(url);
	//sendFormAjaxSimple(url,false);
	window.location.href = url;
}
</script>

<div id="panel<%=panelStyleSuffix%>" >

	Select Year: 
	<select id="selectyearsite" class="field" onchange="setweek()">
            <option value="2012" <%=HtmlUtil.getOptionSelect(2012, _selectedYear)%>>2012</option>
            <option value="2013" <%=HtmlUtil.getOptionSelect(2013, _selectedYear)%>>2013</option>
            <option value="2014" <%=HtmlUtil.getOptionSelect(2014, _selectedYear)%>>2014</option>
            <option value="2015" <%=HtmlUtil.getOptionSelect(2015, _selectedYear)%>>2015</option>
            <option value="2016" <%=HtmlUtil.getOptionSelect(2016, _selectedYear)%>>2016</option>
    </select>
    
    <select id="selectweeksite" class="field" onchange="setweek()">
        <option value="" >- Please Select -</option>
<%


	List _weeks = ChurUtil.getWeeksForYear(_selectedYear);
	//List _weeks = ChurUtil.getWeeksForYear(ChurUtil.getCurrentYear());
	for(Iterator iter = _weeks.iterator(); iter.hasNext();){
		String _week = (String) iter.next();
%>		
            <option value="<%= _week%>" <%=HtmlUtil.getOptionSelect(_week, _selectedWeek)%>><%=_week %></option>
<%	} %>            
    </select>  <span></span>    

<%
AccessConfigManager3 churAccessMgr = AccessConfigManager3.getInstanceForCustom("ChurApp");

    if ( AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "add_user", AccessDef.SystemRole.SiteAdmin))){ 
%>
<a href="/v_chur_register_add.html"> 사이트 유저 입력</a>&nbsp;&diams;&nbsp;
<%
	}
%>

<%
    if ( AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "add_member", AccessDef.SystemRole.SiteAdmin))){ 
%>
<a href="/v_chur_member_form.html"> 교인입력</a> &nbsp;&diams;&nbsp;
<%
	}
%>
<%
    if ( AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "add_member", AccessDef.SystemRole.SiteAdmin))){ 
%>
<a href="/v_chur_income_form.html"> 헌금개별입력</a> &nbsp;&diams;&nbsp;
<a href="/v_chur_income_add_form.html"> 헌금입력</a> &nbsp;&diams;&nbsp;
<a href="/v_chur_income_update.html"> 헌금정정</a> &nbsp;&diams;&nbsp;
<a href="v_chur_income_weekly_report.html"> 주간리포트</a> &nbsp;&diams;&nbsp;
<%
	}
%>
    
</div>


<%-- ############################################################################################## --%>
<%-- ################################# BOTTOM SPACE ############################################### --%>
<% if (panel.getBottomSpace()>0) { %>
<div class="gapPadding" style="height: <%=panel.getBottomSpace() %>">
</div>	
<% } %>
<span class="clear"></span>