<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
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

	List list = new ArrayList();
	ContentConfigDS ds = ContentConfigDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
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
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="v_content_config_form.html?prv_returnPage=content_config_home"> Add New </a> |
            <a href="v_content_config_list.html?"> ListPage </a> |
            <a href="v_content_config_ajax.html?"> Ajax Sampler </a> |
			<a href="/contentConfigAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/contentConfigAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/contentConfigAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentConfigForm_contentId_label" style="font-size: normal normal bold 10px verdana;" >Content Id </div>
    </td> 
    <td> 
	    <div id="contentConfigForm_keywords_label" style="font-size: normal normal bold 10px verdana;" >Keywords </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentConfig _ContentConfig = (ContentConfig) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentConfig.getId() %> </td>


    <td> 
	<form name="contentConfigFormEditField_ContentId_<%=_ContentConfig.getId()%>" method="get" action="/contentConfigAction.html" >


		<div id="contentConfigForm_contentId_field">
	    <div id="contentConfigForm_contentId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentId" value="<%=WebUtil.display(_ContentConfig.getContentId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentConfigFormEditField_ContentId_<%=_ContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentConfigFormEditField_Keywords_<%=_ContentConfig.getId()%>" method="get" action="/contentConfigAction.html" >


		<div id="contentConfigForm_keywords_field">
	    <div id="contentConfigForm_keywords_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="keywords" value="<%=WebUtil.display(_ContentConfig.getKeywords())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentConfigFormEditField_Keywords_<%=_ContentConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_config_home">
	</form>
    
    
    </td>



    <td>
    <form name="contentConfigForm<%=_ContentConfig.getId()%>2" method="get" action="/v_content_config_form.html" >
        <a href="javascript:document.contentConfigForm<%=_ContentConfig.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentConfig.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="content_config_home">
    </form>
    </td>

    <td>
    <a href="/contentConfigAction.html?del=true&id=<%=_ContentConfig.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>