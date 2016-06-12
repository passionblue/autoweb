<%@page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.jtrend.session.SessionContext,com.autosite.*,com.autosite.util.*,java.util.*,com.autosite.servlet.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 

	boolean isLogin = (sessionContext != null && sessionContext.isLogin());

	if (!isLogin) return;


	SessionWrapper wrap = sessionContext.getSessionWrapper(); 
	
	PageView pview = wrap.getViewPage();

	long contentId = WebParamUtil.getLongValue(request.getParameter("cid"));		
	Content content = (Content)ContentDS.getInstance().getById(contentId);
	
	if (content == null) {
		content = (Content) session.getAttribute("k_page_content");
	}
	
	PageContentConfig contentConfig = null;
	
	if (content != null) 
	    contentConfig = PageContentConfigDS.getInstance().getObjectByContentId(content.getId());

%>

<% 	
	if (wrap.isDynPage2()|| wrap.isDynMenuClicked()){ 

		String pageName = (String) session.getAttribute("k_page_name");
		Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
		if ( dynPage == null) {
			return; 
		}			
		
		PageConfig pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());
		EcCategory ECcategory = EcCategoryDS.getInstance().getObjectByPageId(dynPage.getId());

%>
<div id="pageMenu" style="font: normal normal normal 10px verdana;background-color: orange;color: black; padding: 3px 3px 3px 3px">

DYNAMIC PAGE:<%= dynPage.getId() %>|

<a href="/v_page_edit.html?id=<%=dynPage.getId()%>&prv_returnPage=<%= (pview == null?"":pview.getAlias()) %>" style="color:black">Edit</a>|

<% if (pageConfig == null){ %>
<a href="/t_page_config_add.html?prv_pageId=<%=dynPage.getId()%>" style="color:black">Add Page Config(<%=dynPage.getPageMenuTitle()%>)</a> |
<% }else { %>
<a href="/v_page_config_edit.html?id=<%=pageConfig.getId()%>" style="color:black">Edit Page Config(<%=dynPage.getPageMenuTitle()%>)</a>|
<% }%>

<% if (ECcategory == null){ %>
<a href="/v_ec_category_add.html?prv_pageId=<%=dynPage.getId()%>&prv_categoryName=<%=dynPage.getPageMenuTitle() %>" style="color:black">Set EC Category</a>|
<% } else { %>
<a href="/v_ec_category_edit.html?id=<%=ECcategory.getId()%>" style="color:black">Edit Category</a>|
<a href="/v_ec_product_add.html?prv_categoryId=<%=ECcategory.getId()%>" style="color:black">Add Product</a>|
<% }%>

<a href="/v_content_category_add.html?prv_pageId=<%=dynPage.getId()%>&prv_returnPage=<%= (pview == null?"":pview.getAlias()) %>" style="color:black">Add Content Category</a> |
<a href="#" rel="TEST" class="popMenu"><img src="/images/icons/arrow_57.gif" style="border-width:0" /></a>|

</div>

<% 	} else { 

		PageStaticConfig pageConfig = PageStaticConfigDS.getInstance().getBySiteIdPageAlias(site.getId(), pview.getAlias());

%>
<div id="pageMenu" style="font: normal normal normal 10px verdana;background-color: #99EEF7; color: black; padding: 3px 3px 3px 3px">

STATIC PAGE |

<% if (pageConfig == null){ %>
<a href="/t_page_static_config_add2.html?prv_returnPage=<%=pview.getAlias()%>&prv_pageAlias=<%=pview.getAlias()%>" style="color:black">Add Page Config(<%=pview.getAlias()%>)</a> |
<% }else { %>
<a href="/v_page_static_config_edit2.html?id=<%=pageConfig.getId()%>&prv_returnPage=<%=pview.getAlias()%>" style="color:black">Edit Page Config(<%=pview.getAlias()%>)</a>|
<% }%>

<% 
//Do not show there is no content in displauy
if (content!= null){

if (contentConfig == null){ 
%>
<a href="/t_page_content_config_form.html?cmd=add&prx_fwdTo=/t_<%=pview.getAlias()%>.html?cid=<%=content.getId()%>&prv_contentId=<%=content.getId()%>" style="color:black">Add Content Config</a> |
<% }else { %>
<a href="/t_page_content_config_form.html?cmd=edit&id=<%=contentConfig.getId()%>&prx_fwdTo=/t_<%=pview.getAlias()%>.html?cid=<%=content.getId()%>" style="color:black">Edit Content Config</a>|
<% } %>

<a href="/v_meta_header_form.html?cmd=add&prx_fwdTo=/t_<%=pview.getAlias()%>.html?cid=<%=content.getId()%>&prv_detailId=<%=content.getId()%>" style="color:black">Add Img Source</a> |
<% } %>

<a href="#" rel="TEST" class="popMenu"><img src="/images/icons/arrow_57.gif" style="border-width:0" /></a>|

</div>
<% } %>