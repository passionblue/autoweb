<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	StyleConfigDS ds = StyleConfigDS.getInstance();    
	List list = ds.getBySiteIdToStyleUseList(site.getId(), StyleConfigUtil.STYLE_USE_CUSTOM); 
	
	SiteDS siteDS = SiteDS.getInstance();


%> 
<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);

	JspUtil.getLogger().debug("Paging>> " + html);	


	StyleConfig styleConfigCntListSubject   = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContListSubjectKey(site.getId()));
	StyleConfig styleConfigCntListData      = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContListDataKey(site.getId()));
	StyleConfig styleConfigCntSingleSubject = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContSingleSubjectKey(site.getId()));
	StyleConfig styleConfigCntSingleData    = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContSingleDataKey(site.getId()));

%>	

<!-- =================== END PAGING =================== -->

<a href="t_style_config_add.html?prv_returnPage=style_config_custom_list&prv_styleUse=1&prv_styleKey=<%="__customClass-" + System.nanoTime() %>"> Add Custom</a> <br>

<%	if (styleConfigCntListSubject == null) { %>
			<a href="/v_style_config_add.html?prv_styleKey=<%=StyleConfigUtil.getContListSubjectKey(site.getId())%>" > Add ContList Subject Style</a>&nbsp;|&nbsp;
<%	} else { %>			
			<a href="/v_style_config_edit.html?id=<%=styleConfigCntListSubject.getId()%>" > Edit ContList Subject Style</a>&nbsp;|&nbsp;
<%	}  %>			
			
<%	if (styleConfigCntListData == null) { %>
			<a href="/v_style_config_add.html?prv_styleKey=<%=StyleConfigUtil.getContListDataKey(site.getId())%>" > Add ContList Data Style</a>&nbsp;|&nbsp;
<%	} else { %>			
			<a href="/v_style_config_edit.html?id=<%=styleConfigCntListData.getId()%>" > Edit ContList Data Style</a>&nbsp;|&nbsp;
<%	}  %>			

<%	if (styleConfigCntSingleSubject == null) { %>
			<a href="/v_style_config_add.html?prv_styleKey=<%=StyleConfigUtil.getContSingleSubjectKey(site.getId())%>" > Add ContSingle Subject Style</a>&nbsp;|&nbsp;
<%	} else { %>			
			<a href="/v_style_config_edit.html?id=<%=styleConfigCntSingleSubject.getId()%>" > Edit ContSingle Subject Style</a>&nbsp;|&nbsp;
<%	}  %>			

<%	if (styleConfigCntSingleData == null) { %>
			<a href="/v_style_config_add.html?prv_styleKey=<%=StyleConfigUtil.getContSingleDataKey(site.getId())%>" > Add ContSingle Data Style</a>
<%	} else { %>			
			<a href="/v_style_config_edit.html?id=<%=styleConfigCntSingleData.getId()%>" > Edit ContSingle Data Style</a>
<%	}  %>			

<br/>
<br/>
<a href="v_style_set_home.html"> Style Set Home</a><br/>

<a href="v_style_set_content_home.html"> Style Set Content Home</a>

<br/>
<%= html %>

<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> Edit </td>
    <td class="columnTitle"> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Style Key </td> 
    <td class="columnTitle">  Is ID </td> 
    <td class="columnTitle">  Id Class </td> 
    <td class="columnTitle">  Bg Color </td> 
    <td class="columnTitle">  Bg Image </td> 
    <td class="columnTitle">  Bg Repeat </td> 
    <td class="columnTitle">  Bg Attach </td> 
    <td class="columnTitle">  Bg Position </td> 
    <td class="columnTitle">  Text Align </td> 
    <td class="columnTitle">  Font Family </td> 
    <td class="columnTitle">  Font Size </td> 
    <td class="columnTitle">  Font Style </td> 
    <td class="columnTitle">  Font Variant </td> 
    <td class="columnTitle">  Font Weight </td> 
    <td class="columnTitle">  Border Direction </td> 
    <td class="columnTitle">  Border Width </td> 
    <td class="columnTitle">  Border Style </td> 
    <td class="columnTitle">  Border Color </td> 
    <td class="columnTitle">  Margin </td> 
    <td class="columnTitle">  Padding </td> 
    <td class="columnTitle">  List Style Type </td> 
    <td class="columnTitle">  List Style Position </td> 
    <td class="columnTitle">  List Style Image </td> 
    <td class="columnTitle">  Floating </td> 
    <td class="columnTitle">  Extra Style Str </td> 
    <td class="columnTitle">  Item Style Str </td> 
    <td class="columnTitle">  Link </td> 
    <td class="columnTitle">  Link Hover </td> 
    <td class="columnTitle">  Link Active </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
</TR>

<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleConfig _StyleConfig = (StyleConfig) iter.next();
%>

<TR>
<td>
<form name="styleConfigForm<%=_StyleConfig.getId()%>2" method="get" action="/v_style_config_edit.html" >
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleConfig.getId() %>">
    <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="style_config_custom_list">
</form>
<a href="javascript:document.styleConfigForm<%=_StyleConfig.getId()%>2.submit();">Edit</a><br/>
<a href="/styleConfigAction.html?del=true&id=<%=_StyleConfig.getId()%>"> Del </a>
</td>

    <td> <%= _StyleConfig.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _StyleConfig.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _StyleConfig.getStyleKey()  %> </td>
	<td> <%= _StyleConfig.getIsId()  %> </td>
	<td> <%= _StyleConfig.getIdClass()  %> </td>
	<td> <%= _StyleConfig.getBgColor()  %> </td>
	<td> <%= _StyleConfig.getBgImage()  %> </td>
	<td> <%= _StyleConfig.getBgRepeat()  %> </td>
	<td> <%= _StyleConfig.getBgAttach()  %> </td>
	<td> <%= _StyleConfig.getBgPosition()  %> </td>
	<td> <%= _StyleConfig.getTextAlign()  %> </td>
	<td> <%= _StyleConfig.getFontFamily()  %> </td>
	<td> <%= _StyleConfig.getFontSize()  %> </td>
	<td> <%= _StyleConfig.getFontStyle()  %> </td>
	<td> <%= _StyleConfig.getFontVariant()  %> </td>
	<td> <%= _StyleConfig.getFontWeight()  %> </td>
	<td> <%= _StyleConfig.getBorderDirection()  %> </td>
	<td> <%= _StyleConfig.getBorderWidth()  %> </td>
	<td> <%= _StyleConfig.getBorderStyle()  %> </td>
	<td> <%= _StyleConfig.getBorderColor()  %> </td>
	<td> <%= _StyleConfig.getMargin()  %> </td>
	<td> <%= _StyleConfig.getPadding()  %> </td>
	<td> <%= _StyleConfig.getListStyleType()  %> </td>
	<td> <%= _StyleConfig.getListStylePosition()  %> </td>
	<td> <%= _StyleConfig.getListStyleImage()  %> </td>
	<td> <%= _StyleConfig.getFloating()  %> </td>
	<td> <%= _StyleConfig.getExtraStyleStr()  %> </td>
	<td> <%= _StyleConfig.getItemStyleStr()  %> </td>
	<td> <%= _StyleConfig.getLink()  %> </td>
	<td> <%= _StyleConfig.getLinkHover()  %> </td>
	<td> <%= _StyleConfig.getLinkActive()  %> </td>
	<td> <%= _StyleConfig.getTimeCreated()  %> </td>
	<td> <%= _StyleConfig.getTimeUpdated()  %> </td>
</TR>

<%
    }
%>
</TABLE>