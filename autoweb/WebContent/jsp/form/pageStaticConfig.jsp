<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	PageStaticConfigDS ds = PageStaticConfigDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 3);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "<<";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_page_static_config_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = ">>";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_page_static_config_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_page_static_config_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_page_static_config_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
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
            <a href="t_page_static_config_add2.html?prv_returnPage=page_static_config_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pageStaticConfigForm_pageAlias_label" style="font-size: normal normal bold 10px verdana;" >Page Alias </div>
    </td> 
    <td> 
	    <div id="pageStaticConfigForm_pageCss_label" style="font-size: normal normal bold 10px verdana;" >Page Css </div>
    </td> 
    <td> 
	    <div id="pageStaticConfigForm_pageScript_label" style="font-size: normal normal bold 10px verdana;" >Page Script </div>
    </td> 
    <td> 
	    <div id="pageStaticConfigForm_pageCssImports_label" style="font-size: normal normal bold 10px verdana;" >Page Css Imports </div>
    </td> 
    <td> 
	    <div id="pageStaticConfigForm_pageScriptImports_label" style="font-size: normal normal bold 10px verdana;" >Page Script Imports </div>
    </td> 
    <td> 
	    <div id="pageStaticConfigForm_hideMenu_label" style="font-size: normal normal bold 10px verdana;" >Hide Menu </div>
    </td> 
    <td> 
	    <div id="pageStaticConfigForm_hideMid_label" style="font-size: normal normal bold 10px verdana;" >Hide Mid </div>
    </td> 
    <td> 
	    <div id="pageStaticConfigForm_hideAd_label" style="font-size: normal normal bold 10px verdana;" >Hide Ad </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PageStaticConfig _PageStaticConfig = (PageStaticConfig) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PageStaticConfig.getId() %> </td>


    <td> 
	<form name="pageStaticConfigFormEditField_PageAlias_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_pageAlias_field">
	    <div id="pageStaticConfigForm_pageAlias_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageAlias" value="<%=WebUtil.display(_PageStaticConfig.getPageAlias())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_PageAlias_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticConfigFormEditField_PageCss_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_pageCss_field">
	    <div id="pageStaticConfigForm_pageCss_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageCss" value="<%=WebUtil.display(_PageStaticConfig.getPageCss())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_PageCss_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticConfigFormEditField_PageScript_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_pageScript_field">
	    <div id="pageStaticConfigForm_pageScript_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageScript" value="<%=WebUtil.display(_PageStaticConfig.getPageScript())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_PageScript_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticConfigFormEditField_PageCssImports_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_pageCssImports_field">
	    <div id="pageStaticConfigForm_pageCssImports_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageCssImports" value="<%=WebUtil.display(_PageStaticConfig.getPageCssImports())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_PageCssImports_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticConfigFormEditField_PageScriptImports_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_pageScriptImports_field">
	    <div id="pageStaticConfigForm_pageScriptImports_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageScriptImports" value="<%=WebUtil.display(_PageStaticConfig.getPageScriptImports())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_PageScriptImports_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticConfigFormEditField_HideMenu_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_hideMenu_field">
	    <div id="pageStaticConfigForm_hideMenu_dropdown" class="formFieldDropDown" >       
	        <select name="hideMenu">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageStaticConfig.getHideMenu())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageStaticConfig.getHideMenu())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_HideMenu_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticConfigFormEditField_HideMid_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_hideMid_field">
	    <div id="pageStaticConfigForm_hideMid_dropdown" class="formFieldDropDown" >       
	        <select name="hideMid">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageStaticConfig.getHideMid())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageStaticConfig.getHideMid())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_HideMid_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pageStaticConfigFormEditField_HideAd_<%=_PageStaticConfig.getId()%>" method="get" action="/pageStaticConfigAction.html" >


		<div id="pageStaticConfigForm_hideAd_field">
	    <div id="pageStaticConfigForm_hideAd_dropdown" class="formFieldDropDown" >       
	        <select name="hideAd">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PageStaticConfig.getHideAd())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PageStaticConfig.getHideAd())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pageStaticConfigFormEditField_HideAd_<%=_PageStaticConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStaticConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="page_static_config_home">
	</form>
    
    
    </td>



    <td>
    <form name="pageStaticConfigForm<%=_PageStaticConfig.getId()%>" method="get" action="/v_page_static_config_edit.html" >
        <a href="javascript:document.pageStaticConfigForm<%=_PageStaticConfig.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticConfig.getId() %>">
    </form>
    <form name="pageStaticConfigForm<%=_PageStaticConfig.getId()%>2" method="get" action="/v_page_static_config_edit2.html" >
        <a href="javascript:document.pageStaticConfigForm<%=_PageStaticConfig.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PageStaticConfig.getId() %>">
    </form>
    </td>

    <td>
    <a href="/pageStaticConfigAction.html?del=true&id=<%=_PageStaticConfig.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>