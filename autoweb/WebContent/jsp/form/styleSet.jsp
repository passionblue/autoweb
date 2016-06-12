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
	StyleSetDS ds = StyleSetDS.getInstance();    

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
            <a href="v_style_set_form.html?prv_returnPage=style_set_home"> Add New </a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="styleSetForm_name_label" style="font-size: normal normal bold 10px verdana;" >Name </div>
    </td> 
    <td> 
	    <div id="styleSetForm_styleId_label" style="font-size: normal normal bold 10px verdana;" >Style Id </div>
    </td> 
    <td> 
	    <div id="styleSetForm_dataStyleId_label" style="font-size: normal normal bold 10px verdana;" >Data Style Id </div>
    </td> 
    <td> 
	    <div id="styleSetForm_linkStyleId_label" style="font-size: normal normal bold 10px verdana;" >Link Style Id </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleSet _StyleSet = (StyleSet) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleSet.getId() %> </td>


    <td> 
	<form name="styleSetFormEditField_Name_<%=_StyleSet.getId()%>" method="get" action="/styleSetAction.html" >


		<div id="styleSetForm_name_field">
	    <div id="styleSetForm_name_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="name" value="<%=WebUtil.display(_StyleSet.getName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetFormEditField_Name_<%=_StyleSet.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSet.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetFormEditField_StyleId_<%=_StyleSet.getId()%>" method="get" action="/styleSetAction.html" >

		<div id="styleSetForm_styleId_field">
	    <div id="styleSetForm_styleId_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="styleId">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _StyleSet.getStyleId())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetFormEditField_StyleId_<%=_StyleSet.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSet.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetFormEditField_DataStyleId_<%=_StyleSet.getId()%>" method="get" action="/styleSetAction.html" >

		<div id="styleSetForm_dataStyleId_field">
	    <div id="styleSetForm_dataStyleId_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="dataStyleId">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _StyleSet.getDataStyleId())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetFormEditField_DataStyleId_<%=_StyleSet.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSet.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetFormEditField_LinkStyleId_<%=_StyleSet.getId()%>" method="get" action="/styleSetAction.html" >

		<div id="styleSetForm_linkStyleId_field">
	    <div id="styleSetForm_linkStyleId_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="linkStyleId">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _StyleSet.getLinkStyleId())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetFormEditField_LinkStyleId_<%=_StyleSet.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSet.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_home">
	</form>
    
    
    </td>



    <td>
    <form name="styleSetForm<%=_StyleSet.getId()%>2" method="get" action="/v_style_set_form.html" >
        <a href="javascript:document.styleSetForm<%=_StyleSet.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleSet.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="style_set_home">
    </form>
    </td>

    <td>
    <a href="/styleSetAction.html?del=true&id=<%=_StyleSet.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>