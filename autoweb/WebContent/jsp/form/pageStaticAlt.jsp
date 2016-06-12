<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PageStaticAltDS ds = PageStaticAltDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_page_static_alt_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_page_static_alt_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_page_static_alt_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_page_static_alt_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
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
	<%=pageIndexShortcut[p] %> /
<%
	}
%>	
	<%= nextLinkStr %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_page_static_alt_add2.html?prv_returnPage=page_static_alt_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pageStaticAltForm_pageAlias_label" style="font-size: normal normal bold 10px verdana;" >Page Alias </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_pageUrl_label" style="font-size: normal normal bold 10px verdana;" >Page Url </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_menuPage_label" style="font-size: normal normal bold 10px verdana;" >Menu Page </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_errorPage_label" style="font-size: normal normal bold 10px verdana;" >Error Page </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_loginRequired_label" style="font-size: normal normal bold 10px verdana;" >Login Required </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_viewProc_label" style="font-size: normal normal bold 10px verdana;" >View Proc </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_dynamicContent_label" style="font-size: normal normal bold 10px verdana;" >Dynamic Content </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_hideMenu_label" style="font-size: normal normal bold 10px verdana;" >Hide Menu </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_hideMid_label" style="font-size: normal normal bold 10px verdana;" >Hide Mid </div>
    </td> 
    <td> 
	    <div id="pageStaticAltForm_hideAd_label" style="font-size: normal normal bold 10px verdana;" >Hide Ad </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageStaticAlt _PageStaticAlt = (PageStaticAlt) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageStaticAlt.getId() %> </td>


    <td> 
	<form name="pageStaticAltFormEditField_PageAlias_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_pageAlias_field">
	    <div id="pageStaticAltForm_pageAlias_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageAlias" value="<%=WebUtil.display(_PageStaticAlt.getPageAlias())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_PageAlias_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_PageUrl_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_pageUrl_field">
	    <div id="pageStaticAltForm_pageUrl_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageUrl" value="<%=WebUtil.display(_PageStaticAlt.getPageUrl())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_PageUrl_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_MenuPage_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_menuPage_field">
	    <div id="pageStaticAltForm_menuPage_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="menuPage" value="<%=WebUtil.display(_PageStaticAlt.getMenuPage())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_MenuPage_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_ErrorPage_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_errorPage_field">
	    <div id="pageStaticAltForm_errorPage_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="errorPage" value="<%=WebUtil.display(_PageStaticAlt.getErrorPage())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_ErrorPage_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_LoginRequired_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_loginRequired_field">
	    <div id="pageStaticAltForm_loginRequired_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="loginRequired" value="<%=WebUtil.display(_PageStaticAlt.getLoginRequired())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_LoginRequired_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_ViewProc_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_viewProc_field">
	    <div id="pageStaticAltForm_viewProc_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="viewProc" value="<%=WebUtil.display(_PageStaticAlt.getViewProc())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_ViewProc_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_DynamicContent_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_dynamicContent_field">
	    <div id="pageStaticAltForm_dynamicContent_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="dynamicContent" value="<%=WebUtil.display(_PageStaticAlt.getDynamicContent())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_DynamicContent_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_HideMenu_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_hideMenu_field">
	    <div id="pageStaticAltForm_hideMenu_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="hideMenu" value="<%=WebUtil.display(_PageStaticAlt.getHideMenu())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_HideMenu_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_HideMid_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_hideMid_field">
	    <div id="pageStaticAltForm_hideMid_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="hideMid" value="<%=WebUtil.display(_PageStaticAlt.getHideMid())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_HideMid_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticAltFormEditField_HideAd_<%=_PageStaticAlt.getId()%>" method="get" action="/pageStaticAltAction.html" >


		<div id="pageStaticAltForm_hideAd_field">
	    <div id="pageStaticAltForm_hideAd_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="hideAd" value="<%=WebUtil.display(_PageStaticAlt.getHideAd())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticAltFormEditField_HideAd_<%=_PageStaticAlt.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticAlt.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_alt_home">
	</form>
    
    
    </td>


    <td>
    <form name="pageStaticAltForm<%=_PageStaticAlt.getId()%>" method="get" action="/v_page_static_alt_edit.html" >
        <a href="javascript:document.pageStaticAltForm<%=_PageStaticAlt.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticAlt.getId() %>">
    </form>
    <form name="pageStaticAltForm<%=_PageStaticAlt.getId()%>2" method="get" action="/v_page_static_alt_edit2.html" >
        <a href="javascript:document.pageStaticAltForm<%=_PageStaticAlt.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticAlt.getId() %>">
    </form>
    </td>

    <td>
    <a href="/pageStaticAltAction.html?del=true&id=<%=_PageStaticAlt.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>