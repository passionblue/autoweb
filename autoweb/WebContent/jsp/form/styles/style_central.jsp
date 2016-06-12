<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


<%--################################################################################################################################################# --%>
<%--################################################################################################################################################# --%>
<%--################################################################################################################################################# --%>

<h3>StyleConfig List</h3>

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

	StyleConfig styleConfigCntListSubject   = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContListSubjectKey(site.getId()));
	StyleConfig styleConfigCntListData      = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContListDataKey(site.getId()));
	StyleConfig styleConfigCntSingleSubject = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContSingleSubjectKey(site.getId()));
	StyleConfig styleConfigCntSingleData    = StyleConfigDS.getInstance().getObjectByStyleKey(StyleConfigUtil.getContSingleDataKey(site.getId()));

%> 
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

<h3>StyleConfig List</h3>

