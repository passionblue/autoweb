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

    <td> 
	    <div id="linktoForm_linkKey_label" style="font-size: normal normal bold 10px verdana;" >Link Key </div>
    </td> 
    <td> 
	    <div id="linktoForm_linkTarget_label" style="font-size: normal normal bold 10px verdana;" >Link Target </div>
    </td> 
    <td> 
	    <div id="linktoForm_disable_label" style="font-size: normal normal bold 10px verdana;" >Disable </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        Linkto _Linkto = (Linkto) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Linkto.getId() %> </td>


    <td> 
	<form name="linktoFormEditField_LinkKey_<%=_Linkto.getId()%>" method="get" action="/linktoAction.html" >


		<div id="linktoForm_linkKey_field">
	    <div id="linktoForm_linkKey_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="linkKey" value="<%=WebUtil.display(_Linkto.getLinkKey())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.linktoFormEditField_LinkKey_<%=_Linkto.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Linkto.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="linkto_home">
	</form>
    
    
    </td>

    <td> 
	<form name="linktoFormEditField_LinkTarget_<%=_Linkto.getId()%>" method="get" action="/linktoAction.html" >


		<div id="linktoForm_linkTarget_field">
	    <div id="linktoForm_linkTarget_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="linkTarget" value="<%=WebUtil.display(_Linkto.getLinkTarget())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.linktoFormEditField_LinkTarget_<%=_Linkto.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Linkto.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="linkto_home">
	</form>
    
    
    </td>

    <td> 
	<form name="linktoFormEditField_Disable_<%=_Linkto.getId()%>" method="get" action="/linktoAction.html" >


		<div id="linktoForm_disable_field">
	    <div id="linktoForm_disable_dropdown" class="formFieldDropDown" >       
	        <select name="disable">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _Linkto.getDisable())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _Linkto.getDisable())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.linktoFormEditField_Disable_<%=_Linkto.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Linkto.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="linkto_home">
	</form>
    
    
    </td>


    <td>
    <form name="linktoForm<%=_Linkto.getId()%>" method="get" action="/v_linkto_edit.html" >
        <a href="javascript:document.linktoForm<%=_Linkto.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Linkto.getId() %>">
    </form>
    <form name="linktoForm<%=_Linkto.getId()%>2" method="get" action="/v_linkto_edit2.html" >
        <a href="javascript:document.linktoForm<%=_Linkto.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Linkto.getId() %>">
    </form>
    </td>

    <td>
    <a href="/linktoAction.html?del=true&id=<%=_Linkto.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>