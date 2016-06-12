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

	List list = new ArrayList();
	PageStyleDS ds = PageStyleDS.getInstance();    

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
            <a href="t_page_style_add2.html?prv_returnPage=page_style_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pageStyleForm_pageId_label" style="font-size: normal normal bold 10px verdana;" >Page Id </div>
    </td> 
    <td> 
	    <div id="pageStyleForm_styleId_label" style="font-size: normal normal bold 10px verdana;" >Style Id </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageStyle _PageStyle = (PageStyle) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageStyle.getId() %> </td>


    <td> 
	<form name="pageStyleFormEditField_PageId_<%=_PageStyle.getId()%>" method="get" action="/pageStyleAction.html" >


		<div id="pageStyleForm_pageId_field">
	    <div id="pageStyleForm_pageId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageId" value="<%=WebUtil.display(_PageStyle.getPageId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStyleFormEditField_PageId_<%=_PageStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStyleFormEditField_StyleId_<%=_PageStyle.getId()%>" method="get" action="/pageStyleAction.html" >


		<div id="pageStyleForm_styleId_field">
	    <div id="pageStyleForm_styleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="styleId" value="<%=WebUtil.display(_PageStyle.getStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStyleFormEditField_StyleId_<%=_PageStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_style_home">
	</form>
    
    
    </td>

    <td>
    <form name="pageStyleForm<%=_PageStyle.getId()%>" method="get" action="/v_page_style_edit.html" >
        <a href="javascript:document.pageStyleForm<%=_PageStyle.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStyle.getId() %>">
    </form>
    <form name="pageStyleForm<%=_PageStyle.getId()%>2" method="get" action="/v_page_style_edit2.html" >
        <a href="javascript:document.pageStyleForm<%=_PageStyle.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStyle.getId() %>">
    </form>
    </td>

    <td>
    <a href="/pageStyleAction.html?del=true&id=<%=_PageStyle.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>