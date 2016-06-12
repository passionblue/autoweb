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
	PollDS ds = PollDS.getInstance();    

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
            <a href="t_poll_add2.html?prv_returnPage=poll_home"> Add New 2</a>
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
	    <div id="pollForm_${field.name2}_label" style="font-size: normal normal bold 10px verdana;" >Site </div>
    </td> 
	
<% 	} %>

    <td> 
	    <div id="pollForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="pollForm_serial_label" style="font-size: normal normal bold 10px verdana;" >Serial </div>
    </td> 
    <td> 
	    <div id="pollForm_type_label" style="font-size: normal normal bold 10px verdana;" >Type </div>
    </td> 
    <td> 
	    <div id="pollForm_category_label" style="font-size: normal normal bold 10px verdana;" >Category </div>
    </td> 
    <td> 
	    <div id="pollForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="pollForm_question_label" style="font-size: normal normal bold 10px verdana;" >Question </div>
    </td> 
    <td> 
	    <div id="pollForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
    <td> 
	    <div id="pollForm_disable_label" style="font-size: normal normal bold 10px verdana;" >Disable </div>
    </td> 
    <td> 
	    <div id="pollForm_allowMultiple_label" style="font-size: normal normal bold 10px verdana;" >Allow Multiple </div>
    </td> 
    <td> 
	    <div id="pollForm_randomAnswer_label" style="font-size: normal normal bold 10px verdana;" >Random Answer </div>
    </td> 
    <td> 
	    <div id="pollForm_hideComments_label" style="font-size: normal normal bold 10px verdana;" >Hide Comments </div>
    </td> 
    <td> 
	    <div id="pollForm_hideResults_label" style="font-size: normal normal bold 10px verdana;" >Hide Results </div>
    </td> 
    <td> 
	    <div id="pollForm_timeCreated_label" style="font-size: normal normal bold 10px verdana;" >Time Created </div>
    </td> 
    <td> 
	    <div id="pollForm_timeUpdated_label" style="font-size: normal normal bold 10px verdana;" >Time Updated </div>
    </td> 
    <td> 
	    <div id="pollForm_timeExpired_label" style="font-size: normal normal bold 10px verdana;" >Time Expired </div>
    </td> 
</TR>
<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
%>

<TR bgcolor="#ffffff" valign="top">
    <td> <%= _Poll.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _Poll.getSiteId();
	Site st = siteDS.getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td> 
		<%= siteName %>	    
    </td> 
	
<% 	} %>
    
	<td> <%= _Poll.getUserId()  %> </td>
	<td> <%= _Poll.getSerial()  %> </td>
	<td> <%= _Poll.getType()  %> </td>
	<td> <%= _Poll.getCategory()  %> </td>
	<td> <%= _Poll.getTitle()  %> </td>
	<td> <%= _Poll.getQuestion()  %> </td>
	<td> <%= _Poll.getHide()  %> </td>
	<td> <%= _Poll.getDisable()  %> </td>
	<td> <%= _Poll.getAllowMultiple()  %> </td>
	<td> <%= _Poll.getRandomAnswer()  %> </td>
	<td> <%= _Poll.getHideComments()  %> </td>
	<td> <%= _Poll.getHideResults()  %> </td>
	<td> <%= _Poll.getTimeCreated()  %> </td>
	<td> <%= _Poll.getTimeUpdated()  %> </td>
	<td> <%= _Poll.getTimeExpired()  %> </td>
</TR>

<%
    }
%>
</TABLE>