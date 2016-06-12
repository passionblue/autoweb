<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	ContentFeedRelDS ds = ContentFeedRelDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_content_feed_rel_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_content_feed_rel_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_content_feed_rel_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_content_feed_rel_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
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
            <a href="t_content_feed_rel_add2.html?prv_returnPage=content_feed_rel_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentFeedRelForm_contentFeedId_label" style="font-size: normal normal bold 10px verdana;" >Content Feed Id </div>
    </td> 
    <td> 
	    <div id="contentFeedRelForm_contentId_label" style="font-size: normal normal bold 10px verdana;" >Content Id </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedRel _ContentFeedRel = (ContentFeedRel) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedRel.getId() %> </td>


    <td> 
	<form name="contentFeedRelFormEditField_ContentFeedId_<%=_ContentFeedRel.getId()%>" method="get" action="/contentFeedRelAction.html" >


		<div id="contentFeedRelForm_contentFeedId_field">
	    <div id="contentFeedRelForm_contentFeedId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentFeedId" value="<%=WebUtil.display(_ContentFeedRel.getContentFeedId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentFeedRelFormEditField_ContentFeedId_<%=_ContentFeedRel.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedRel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_rel_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentFeedRelFormEditField_ContentId_<%=_ContentFeedRel.getId()%>" method="get" action="/contentFeedRelAction.html" >


		<div id="contentFeedRelForm_contentId_field">
	    <div id="contentFeedRelForm_contentId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentId" value="<%=WebUtil.display(_ContentFeedRel.getContentId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentFeedRelFormEditField_ContentId_<%=_ContentFeedRel.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedRel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_rel_home">
	</form>
    
    
    </td>


    <td>
    <form name="contentFeedRelForm<%=_ContentFeedRel.getId()%>" method="get" action="/v_content_feed_rel_edit.html" >
        <a href="javascript:document.contentFeedRelForm<%=_ContentFeedRel.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedRel.getId() %>">
    </form>
    <form name="contentFeedRelForm<%=_ContentFeedRel.getId()%>2" method="get" action="/v_content_feed_rel_edit2.html" >
        <a href="javascript:document.contentFeedRelForm<%=_ContentFeedRel.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedRel.getId() %>">
    </form>
    </td>

    <td>
    <a href="/contentFeedRelAction.html?del=true&id=<%=_ContentFeedRel.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>