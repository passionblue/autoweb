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
	StyleSetContentDS ds = StyleSetContentDS.getInstance();    

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
            <a href="v_style_set_content_form.html?prv_returnPage=style_set_content_home"> Add New </a> |
            <a href="v_style_set_content_list.html?"> ListPage </a> |
            <a href="v_style_set_content_ajax.html?"> Ajax Sampler </a> |
			<a href="/styleSetContentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/styleSetContentAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |
			<a href="/styleSetContentAction.html?ajaxRequest=true&ajaxOut=getlisthtml" rel="facebox">Ajax List</a> |
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="styleSetContentForm_name_label" style="font-size: normal normal bold 10px verdana;" >Name </div>
    </td> 
    <td> 
	    <div id="styleSetContentForm_idPrefix_label" style="font-size: normal normal bold 10px verdana;" >Id Prefix </div>
    </td> 
    <td> 
	    <div id="styleSetContentForm_listFrameStyleId_label" style="font-size: normal normal bold 10px verdana;" >List Frame Style Id </div>
    </td> 
    <td> 
	    <div id="styleSetContentForm_listSubjectStyleId_label" style="font-size: normal normal bold 10px verdana;" >List Subject Style Id </div>
    </td> 
    <td> 
	    <div id="styleSetContentForm_listDataStyleId_label" style="font-size: normal normal bold 10px verdana;" >List Data Style Id </div>
    </td> 
    <td> 
	    <div id="styleSetContentForm_frameStyleId_label" style="font-size: normal normal bold 10px verdana;" >Frame Style Id </div>
    </td> 
    <td> 
	    <div id="styleSetContentForm_subjectStyleId_label" style="font-size: normal normal bold 10px verdana;" >Subject Style Id </div>
    </td> 
    <td> 
	    <div id="styleSetContentForm_dataStyleId_label" style="font-size: normal normal bold 10px verdana;" >Data Style Id </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleSetContent _StyleSetContent = (StyleSetContent) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleSetContent.getId() %> </td>


    <td> 
	<form name="styleSetContentFormEditField_Name_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_name_field">
	    <div id="styleSetContentForm_name_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="name" value="<%=WebUtil.display(_StyleSetContent.getName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_Name_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetContentFormEditField_IdPrefix_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_idPrefix_field">
	    <div id="styleSetContentForm_idPrefix_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="idPrefix" value="<%=WebUtil.display(_StyleSetContent.getIdPrefix())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_IdPrefix_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetContentFormEditField_ListFrameStyleId_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_listFrameStyleId_field">
	    <div id="styleSetContentForm_listFrameStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listFrameStyleId" value="<%=WebUtil.display(_StyleSetContent.getListFrameStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_ListFrameStyleId_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetContentFormEditField_ListSubjectStyleId_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_listSubjectStyleId_field">
	    <div id="styleSetContentForm_listSubjectStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listSubjectStyleId" value="<%=WebUtil.display(_StyleSetContent.getListSubjectStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_ListSubjectStyleId_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetContentFormEditField_ListDataStyleId_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_listDataStyleId_field">
	    <div id="styleSetContentForm_listDataStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="listDataStyleId" value="<%=WebUtil.display(_StyleSetContent.getListDataStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_ListDataStyleId_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetContentFormEditField_FrameStyleId_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_frameStyleId_field">
	    <div id="styleSetContentForm_frameStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="frameStyleId" value="<%=WebUtil.display(_StyleSetContent.getFrameStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_FrameStyleId_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetContentFormEditField_SubjectStyleId_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_subjectStyleId_field">
	    <div id="styleSetContentForm_subjectStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="subjectStyleId" value="<%=WebUtil.display(_StyleSetContent.getSubjectStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_SubjectStyleId_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>

    <td> 
	<form name="styleSetContentFormEditField_DataStyleId_<%=_StyleSetContent.getId()%>" method="get" action="/styleSetContentAction.html" >


		<div id="styleSetContentForm_dataStyleId_field">
	    <div id="styleSetContentForm_dataStyleId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="dataStyleId" value="<%=WebUtil.display(_StyleSetContent.getDataStyleId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.styleSetContentFormEditField_DataStyleId_<%=_StyleSetContent.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="style_set_content_home">
	</form>
    
    
    </td>



    <td>
    <form name="styleSetContentForm<%=_StyleSetContent.getId()%>2" method="get" action="/v_style_set_content_form.html" >
        <a href="javascript:document.styleSetContentForm<%=_StyleSetContent.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleSetContent.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="style_set_content_home">
    </form>
    </td>

    <td>
    <a href="/styleSetContentAction.html?del=true&id=<%=_StyleSetContent.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>