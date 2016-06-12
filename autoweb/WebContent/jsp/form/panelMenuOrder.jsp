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
	PanelMenuOrderDS ds = PanelMenuOrderDS.getInstance();    

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
            <a href="v_panel_menu_order_form.html?prv_returnPage=panel_menu_order_home"> Add New </a> |
            <a href="v_panel_menu_order_list.html?"> ListPage </a> |
            <a href="v_panel_menu_order_ajax.html?"> Ajax Sampler </a> |
			<a href="/panelMenuOrderAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/panelMenuOrderAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/panelMenuOrderAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="panelMenuOrderForm_orderedIds_label" style="font-size: normal normal bold 10px verdana;" >Ordered Ids </div>
    </td> 
    <td> 
	    <div id="panelMenuOrderForm_reverse_label" style="font-size: normal normal bold 10px verdana;" >Reverse </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PanelMenuOrder.getId() %> </td>



    <td> 
	<form name="panelMenuOrderFormEditField_OrderedIds_<%=_PanelMenuOrder.getId()%>" method="get" action="/panelMenuOrderAction.html" >


		<div id="panelMenuOrderForm_orderedIds_field">
	    <div id="panelMenuOrderForm_orderedIds_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="orderedIds" value="<%=WebUtil.display(_PanelMenuOrder.getOrderedIds())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.panelMenuOrderFormEditField_OrderedIds_<%=_PanelMenuOrder.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PanelMenuOrder.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="panel_menu_order_home">
	</form>
    
    
    </td>

    <td> 
	<form name="panelMenuOrderFormEditField_Reverse_<%=_PanelMenuOrder.getId()%>" method="get" action="/panelMenuOrderAction.html" >


		<div id="panelMenuOrderForm_reverse_field">
	    <div id="panelMenuOrderForm_reverse_dropdown" class="formFieldDropDown" >       
	        <select name="reverse">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _PanelMenuOrder.getReverse())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _PanelMenuOrder.getReverse())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.panelMenuOrderFormEditField_Reverse_<%=_PanelMenuOrder.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PanelMenuOrder.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="panel_menu_order_home">
	</form>
    
    
    </td>



    <td>
    <form name="panelMenuOrderForm<%=_PanelMenuOrder.getId()%>2" method="get" action="/v_panel_menu_order_form.html" >
        <a href="javascript:document.panelMenuOrderForm<%=_PanelMenuOrder.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PanelMenuOrder.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="panel_menu_order_home">
    </form>
    </td>

    <td>
    <a href="/panelMenuOrderAction.html?del=true&id=<%=_PanelMenuOrder.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>