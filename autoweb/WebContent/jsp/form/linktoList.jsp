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
	LinktoDS ds = LinktoDS.getInstance();    

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
            <a href="t_linkto_add2.html?prv_returnPage=linkto_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">
    <td> ID </td>
<%	
	if (showListAllByAdmin) {
%>
    <td> 
	    <div id="linktoForm_${field.name2}_label" style="font-size: normal normal bold 10px verdana;" >Site </div>
    </td> 
	
<% 	} %>

    <td> 
	    <div id="linktoForm_linkKey_label" style="font-size: normal normal bold 10px verdana;" >Link Key </div>
    </td> 
    <td> 
	    <div id="linktoForm_linkTarget_label" style="font-size: normal normal bold 10px verdana;" >Link Target </div>
    </td> 
    <td> 
	    <div id="linktoForm_disable_label" style="font-size: normal normal bold 10px verdana;" >Disable </div>
    </td> 
    <td> 
	    <div id="linktoForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        Linkto _Linkto = (Linkto) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _Linkto.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _Linkto.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td> 
		<%= siteName %>	    
    </td> 
	
<% 	} %>
    
	<td> <%= _Linkto.getLinkKey()  %> </td>
	<td> <%= _Linkto.getLinkTarget()  %> </td>
	<td> <%= _Linkto.getDisable()  %> </td>
	<td> <%= _Linkto.getTimeCreated()  %> </td>
</TR>

<%
    }
%>
</TABLE>