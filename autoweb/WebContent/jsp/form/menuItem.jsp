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
	MenuItemDS ds = MenuItemDS.getInstance();    

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
            <a href="v_menu_item_form.html?prv_returnPage=menu_item_home"> Add New </a> |
            <a href="v_menu_item_list.html?"> ListPage </a> |
            <a href="v_menu_item_ajax.html?"> Ajax Sampler </a> |
			<a href="/menuItemAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/menuItemAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/menuItemAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=panelId,title,data,targetType&forcehiddenlist=panelId,targetType&panelId=342&targetType=1" rel="facebox">Ajax Add</a> |
			<a href="/menuItemAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="menuItemForm_panelId_label" style="font-size: normal normal bold 10px verdana;" >Panel Id </div>
    </td> 
    <td> 
	    <div id="menuItemForm_parentId_label" style="font-size: normal normal bold 10px verdana;" >Parent Id </div>
    </td> 
    <td> 
	    <div id="menuItemForm_title_label" style="font-size: normal normal bold 10px verdana;" >Title </div>
    </td> 
    <td> 
	    <div id="menuItemForm_data_label" style="font-size: normal normal bold 10px verdana;" >Data </div>
    </td> 
    <td> 
	    <div id="menuItemForm_data2_label" style="font-size: normal normal bold 10px verdana;" >Data2 </div>
    </td> 
    <td> 
	    <div id="menuItemForm_targetType_label" style="font-size: normal normal bold 10px verdana;" >Target Type </div>
    </td> 
    <td> 
	    <div id="menuItemForm_orderIdx_label" style="font-size: normal normal bold 10px verdana;" >Order Idx </div>
    </td> 
    <td> 
	    <div id="menuItemForm_pageId_label" style="font-size: normal normal bold 10px verdana;" >Page Id </div>
    </td> 
    <td> 
	    <div id="menuItemForm_contentId_label" style="font-size: normal normal bold 10px verdana;" >Content Id </div>
    </td> 
    <td> 
	    <div id="menuItemForm_hide_label" style="font-size: normal normal bold 10px verdana;" >Hide </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        MenuItem _MenuItem = (MenuItem) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _MenuItem.getId() %> </td>


    <td> 
	<form name="menuItemFormEditField_PanelId_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_panelId_field">
	    <div id="menuItemForm_panelId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="panelId" value="<%=WebUtil.display(_MenuItem.getPanelId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_PanelId_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_ParentId_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_parentId_field">
	    <div id="menuItemForm_parentId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="parentId" value="<%=WebUtil.display(_MenuItem.getParentId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_ParentId_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_Title_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_title_field">
	    <div id="menuItemForm_title_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="title" value="<%=WebUtil.display(_MenuItem.getTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_Title_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_Data_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_data_field">
	    <div id="menuItemForm_data_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="data" value="<%=WebUtil.display(_MenuItem.getData())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_Data_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_Data2_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_data2_field">
	    <div id="menuItemForm_data2_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="data2" value="<%=WebUtil.display(_MenuItem.getData2())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_Data2_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_TargetType_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >

		<div id="menuItemForm_targetType_field">
	    <div id="menuItemForm_targetType_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="targetType">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _MenuItem.getTargetType())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_TargetType_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_OrderIdx_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_orderIdx_field">
	    <div id="menuItemForm_orderIdx_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="orderIdx" value="<%=WebUtil.display(_MenuItem.getOrderIdx())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_OrderIdx_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_PageId_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_pageId_field">
	    <div id="menuItemForm_pageId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pageId" value="<%=WebUtil.display(_MenuItem.getPageId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_PageId_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_ContentId_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_contentId_field">
	    <div id="menuItemForm_contentId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="contentId" value="<%=WebUtil.display(_MenuItem.getContentId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_ContentId_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>

    <td> 
	<form name="menuItemFormEditField_Hide_<%=_MenuItem.getId()%>" method="get" action="/menuItemAction.html" >


		<div id="menuItemForm_hide_field">
	    <div id="menuItemForm_hide_dropdown" class="formFieldDropDown" >       
	        <select name="hide">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _MenuItem.getHide())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _MenuItem.getHide())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.menuItemFormEditField_Hide_<%=_MenuItem.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="menu_item_home">
	</form>
    
    
    </td>


    <td>
    <form name="menuItemForm<%=_MenuItem.getId()%>2" method="get" action="/v_menu_item_form.html" >
        <a href="javascript:document.menuItemForm<%=_MenuItem.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _MenuItem.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="menu_item_home">
    </form>
    </td>

    <td>
    <a href="/menuItemAction.html?del=true&id=<%=_MenuItem.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>