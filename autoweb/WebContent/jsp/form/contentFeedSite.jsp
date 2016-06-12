<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	ContentFeedSiteDS ds = ContentFeedSiteDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_content_feed_site_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_content_feed_site_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_content_feed_site_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_content_feed_site_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
	}

	List pageList = new ArrayList();
	for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){
		pageList.add(list.get(i));
	}
	list = pageList;
%>
	<%= prevLinkStr %>
<%
	for(int p = 0 ; p< pageIndexShortcut.length; p++){
%>
	<%=pageIndexShortcut[p] + (p>=pageIndexShortcut.length-1?"":"/")%>
<%
	}
%>	
	<%= nextLinkStr %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_content_feed_site_add2.html?prv_returnPage=content_feed_site_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentFeedSiteForm_contentFeedId_label" style="font-size: normal normal bold 10px verdana;" >Content Feed Id </div>
    </td> 
    <td> 
	    <div id="contentFeedSiteForm_displayType_label" style="font-size: normal normal bold 10px verdana;" >Display Type </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedSite _ContentFeedSite = (ContentFeedSite) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedSite.getId() %> </td>


    <td> 
	<form name="contentFeedSiteFormEditField_ContentFeedId_<%=_ContentFeedSite.getId()%>" method="get" action="/contentFeedSiteAction.html" >


		<div id="contentFeedSiteForm_contentFeedId_field">
	    <div id="contentFeedSiteForm_contentFeedId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentFeedId" value="<%=WebUtil.display(_ContentFeedSite.getContentFeedId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentFeedSiteFormEditField_ContentFeedId_<%=_ContentFeedSite.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedSite.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_site_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentFeedSiteFormEditField_DisplayType_<%=_ContentFeedSite.getId()%>" method="get" action="/contentFeedSiteAction.html" >


		<div id="contentFeedSiteForm_displayType_field">
	    <div id="contentFeedSiteForm_displayType_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="displayType" value="<%=WebUtil.display(_ContentFeedSite.getDisplayType())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentFeedSiteFormEditField_DisplayType_<%=_ContentFeedSite.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedSite.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_site_home">
	</form>
    
    
    </td>


    <td>
    <form name="contentFeedSiteForm<%=_ContentFeedSite.getId()%>" method="get" action="/v_content_feed_site_edit.html" >
        <a href="javascript:document.contentFeedSiteForm<%=_ContentFeedSite.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedSite.getId() %>">
    </form>
    <form name="contentFeedSiteForm<%=_ContentFeedSite.getId()%>2" method="get" action="/v_content_feed_site_edit2.html" >
        <a href="javascript:document.contentFeedSiteForm<%=_ContentFeedSite.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedSite.getId() %>">
    </form>
    </td>

    <td>
    <a href="/contentFeedSiteAction.html?del=true&id=<%=_ContentFeedSite.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>