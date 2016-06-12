<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = ContentFeedConfigDS.getInstance().getAll();


%> 
<br/><h4>Feed Categories</h4>

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_content_feed_config_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_content_feed_config_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_content_feed_config_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_content_feed_config_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
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
            <a href="t_content_feed_config_add2.html?prv_returnPage=content_feed_config_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentFeedConfigForm_feedCategory_label" style="font-size: normal normal bold 10px verdana;" >Feed Category </div>
    </td> 
    <td> 
	    <div id="contentFeedConfigForm_feedType_label" style="font-size: normal normal bold 10px verdana;" >Feed Type </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedConfig _ContentFeedConfig = (ContentFeedConfig) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedConfig.getId() %> </td>


    <td> 
	<form name="contentFeedConfigFormEditField_FeedCategory_<%=_ContentFeedConfig.getId()%>" method="get" action="/contentFeedConfigAction.html" >


		<div id="contentFeedConfigForm_feedCategory_field">
	    <div id="contentFeedConfigForm_feedCategory_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="feedCategory" value="<%=WebUtil.display(_ContentFeedConfig.getFeedCategory())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentFeedConfigFormEditField_FeedCategory_<%=_ContentFeedConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentFeedConfigFormEditField_FeedType_<%=_ContentFeedConfig.getId()%>" method="get" action="/contentFeedConfigAction.html" >

		<div id="contentFeedConfigForm_feedType_field">
	    <div id="contentFeedConfigForm_feedType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="feedType">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ContentFeedConfig.getFeedType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentFeedConfigFormEditField_FeedType_<%=_ContentFeedConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_home">
	</form>
    
    
    </td>


    <td>
    <form name="contentFeedConfigForm<%=_ContentFeedConfig.getId()%>" method="get" action="/v_content_feed_config_edit.html" >
        <a href="javascript:document.contentFeedConfigForm<%=_ContentFeedConfig.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedConfig.getId() %>">
    </form>
    <form name="contentFeedConfigForm<%=_ContentFeedConfig.getId()%>2" method="get" action="/v_content_feed_config_edit2.html" >
        <a href="javascript:document.contentFeedConfigForm<%=_ContentFeedConfig.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedConfig.getId() %>">
    </form>
    </td>

    <td>
    <a href="/contentFeedConfigAction.html?del=true&id=<%=_ContentFeedConfig.getId()%>&returnPage=content_feed_home"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>



<%
	// ################################################# Form To add mapping betweein content and feed category.

    String _wpId = WebProcManager.registerWebProcess();

	List siteContents = ContentDS.getInstance().getBySiteId(site.getId());
	List contentFeeds = ContentFeedConfigDS.getInstance().getAll();
%>

<br/><h4>Assign content to Feed Category</h4>


<form name="contentFeedRelForm" method="get" action="/contentFeedRelAction.html" >
		<div id="contentFeedConfigForm_feedType_field">

	    <div id="contentFeedConfigForm_feedType_dropdown" class="formFieldDropDown" >       
	        <select id="requiredField" name="contentId">
	        <option value="" >- Please Select -</option>
<%
	for(Iterator iter = siteContents.iterator(); iter.hasNext();){
		Content content = (Content) iter.next();
%>
		     <option value="<%=content.getId() %>" ><%= content.getContentSubject()%></option>
<%
	}
%>
	        </select>  <span></span>
	    </div>      

	    <div id="contentFeedConfigForm_feedType_dropdown" class="formFieldDropDown" >       
	        <select id="requiredField" name="contentFeedId">
	        <option value="" >- Please Select -</option>
<%
	for(Iterator iter = contentFeeds.iterator(); iter.hasNext();){
		ContentFeedConfig contentConfig = (ContentFeedConfig) iter.next();
%>
		     <option value="<%=contentConfig.getId() %>" ><%=contentConfig.getFeedCategory() %></option-->
<%
	}
%>
	        </select>  <span></span>
	    </div>      


		</div><div class="clear"></div>

        <a id="formSubmit" href="javascript:document.contentFeedRelForm.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="add" value="true">
	<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_home">
</form>



<br/><h4>Content and FeedId Table</h4>
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentFeedRelForm_contentFeedId_label" style="font-size: normal normal bold 10px verdana;" >Feed Category </div>
    </td> 
    <td> 
	    <div id="contentFeedRelForm_contentId_label" style="font-size: normal normal bold 10px verdana;" >Content Subject </div>
    </td> 
	<td></td>
	<td></td>
</TR>





<%

	List feedRel = ContentFeedRelDS.getInstance().getAll();
    for(Iterator iter = feedRel.iterator();iter.hasNext();) {
        ContentFeedRel _ContentFeedRel = (ContentFeedRel) iter.next();
        
        Content c = ContentDS.getInstance().getById(_ContentFeedRel.getContentId());
        ContentFeedConfig cf = ContentFeedConfigDS.getInstance().getById(_ContentFeedRel.getContentFeedId());
        
        String sub = (c == null? "XXXX": c.getContentSubject());
        String cat = (cf == null? "XXXX": cf.getFeedCategory());
        
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedRel.getId() %> </td>


    <td> 
		<div id="contentFeedRelForm_contentFeedId_field">
	    <div id="contentFeedRelForm_contentFeedId_text" class="formFieldText" >       
	        <span><%=cat%></span>
	    </div>      
		</div><div class="clear"></div>
    </td>

    <td> 
		<div id="contentFeedRelForm_contentId_field">
	    <div id="contentFeedRelForm_contentId_text" class="formFieldText" >       
	        <span><%=sub%></span>
	    </div>      
		</div><div class="clear"></div>
    </td>


    <td>
    <form name="contentFeedRelForm<%=_ContentFeedRel.getId()%>2" method="get" action="/v_content_feed_rel_edit2.html" >
        <a href="javascript:document.contentFeedRelForm<%=_ContentFeedRel.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedRel.getId() %>">
		<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_feed_home">
    </form>
    </td>

    <td>
    <a href="/contentFeedRelAction.html?del=true&id=<%=_ContentFeedRel.getId()%>&returnPage=content_feed_home"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>
