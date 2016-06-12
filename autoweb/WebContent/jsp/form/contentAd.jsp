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
	ContentAdDS ds = ContentAdDS.getInstance();    

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
            <a href="t_content_ad_add2.html?prv_returnPage=content_ad_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="contentAdForm_contentId_label" style="font-size: normal normal bold 10px verdana;" >Content Id </div>
    </td> 
    <td> 
	    <div id="contentAdForm_positionCode_label" style="font-size: normal normal bold 10px verdana;" >Position Code </div>
    </td> 
    <td> 
	    <div id="contentAdForm_adContent_label" style="font-size: normal normal bold 10px verdana;" >Ad Content </div>
    </td> 
    <td> 
	    <div id="contentAdForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentAd _ContentAd = (ContentAd) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentAd.getId() %> </td>


    <td> 
	<form name="contentAdFormEditField_ContentId_<%=_ContentAd.getId()%>" method="get" action="/contentAdAction.html" >


		<div id="contentAdForm_contentId_field">
	    <div id="contentAdForm_contentId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentId" value="<%=WebUtil.display(_ContentAd.getContentId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentAdFormEditField_ContentId_<%=_ContentAd.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentAd.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_ad_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentAdFormEditField_PositionCode_<%=_ContentAd.getId()%>" method="get" action="/contentAdAction.html" >


		<div id="contentAdForm_positionCode_field">
	    <div id="contentAdForm_positionCode_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="positionCode" value="<%=WebUtil.display(_ContentAd.getPositionCode())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentAdFormEditField_PositionCode_<%=_ContentAd.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentAd.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_ad_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentAdFormEditField_AdContent_<%=_ContentAd.getId()%>" method="get" action="/contentAdAction.html" >


		<div id="contentAdForm_adContent_field">
	    <div id="contentAdForm_adContent_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="adContent" value="<%=WebUtil.display(_ContentAd.getAdContent())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentAdFormEditField_AdContent_<%=_ContentAd.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentAd.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_ad_home">
	</form>
    
    
    </td>

    <td> 
	<form name="contentAdFormEditField_Hide_<%=_ContentAd.getId()%>" method="get" action="/contentAdAction.html" >


		<div id="contentAdForm_hide_field">
	    <div id="contentAdForm_hide_dropdown" class="formFieldDropDown" >       
	        <select name="hide">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _ContentAd.getHide())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _ContentAd.getHide())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.contentAdFormEditField_Hide_<%=_ContentAd.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentAd.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="content_ad_home">
	</form>
    
    
    </td>



    <td>
    <form name="contentAdForm<%=_ContentAd.getId()%>" method="get" action="/v_content_ad_edit.html" >
        <a href="javascript:document.contentAdForm<%=_ContentAd.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentAd.getId() %>">
    </form>
    <form name="contentAdForm<%=_ContentAd.getId()%>2" method="get" action="/v_content_ad_edit2.html" >
        <a href="javascript:document.contentAdForm<%=_ContentAd.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentAd.getId() %>">
    </form>
    </td>

    <td>
    <a href="/contentAdAction.html?del=true&id=<%=_ContentAd.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>