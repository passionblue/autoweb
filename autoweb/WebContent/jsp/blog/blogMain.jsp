<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

	List list = new ArrayList();
	BlogMainDS ds = BlogMainDS.getInstance();    
    
	list = ds.getBySiteId(site.getId());

%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_blog_main_add2.html?prv_returnPage=blog_main_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="blogMainForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="blogMainForm_blogName_label" style="font-size: normal normal bold 10px verdana;" >Blog Name </div>
    </td> 
    <td> 
	    <div id="blogMainForm_blogTitle_label" style="font-size: normal normal bold 10px verdana;" >Blog Title </div>
    </td> 
    <td> 
	    <div id="blogMainForm_mainDesignSelect_label" style="font-size: normal normal bold 10px verdana;" >Main Design Select </div>
    </td> 
    <td> 
	    <div id="blogMainForm_useCustomDesign_label" style="font-size: normal normal bold 10px verdana;" >Use Custom Design </div>
    </td> 
    <td> 
	    <div id="blogMainForm_customDesignFile_label" style="font-size: normal normal bold 10px verdana;" >Custom Design File </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogMain _BlogMain = (BlogMain) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogMain.getId() %> </td>


    <td> 
	<form name="blogMainFormEditField_UserId_<%=_BlogMain.getId()%>" method="get" action="/blogMainAction.html" >


		<div id="blogMainForm_userId_field">
	    <div id="blogMainForm_userId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="userId" value="<%=WebUtil.display(_BlogMain.getUserId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogMainFormEditField_UserId_<%=_BlogMain.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_main_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogMainFormEditField_BlogName_<%=_BlogMain.getId()%>" method="get" action="/blogMainAction.html" >


		<div id="blogMainForm_blogName_field">
	    <div id="blogMainForm_blogName_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogName" value="<%=WebUtil.display(_BlogMain.getBlogName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogMainFormEditField_BlogName_<%=_BlogMain.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_main_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogMainFormEditField_BlogTitle_<%=_BlogMain.getId()%>" method="get" action="/blogMainAction.html" >


		<div id="blogMainForm_blogTitle_field">
	    <div id="blogMainForm_blogTitle_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogTitle" value="<%=WebUtil.display(_BlogMain.getBlogTitle())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogMainFormEditField_BlogTitle_<%=_BlogMain.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_main_home">
	</form>
    
    
    </td>


    <td> 
	<form name="blogMainFormEditField_MainDesignSelect_<%=_BlogMain.getId()%>" method="get" action="/blogMainAction.html" >

		<div id="blogMainForm_mainDesignSelect_field">
	    <div id="blogMainForm_mainDesignSelect_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="mainDesignSelect">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _BlogMain.getMainDesignSelect())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogMainFormEditField_MainDesignSelect_<%=_BlogMain.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_main_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogMainFormEditField_UseCustomDesign_<%=_BlogMain.getId()%>" method="get" action="/blogMainAction.html" >


		<div id="blogMainForm_useCustomDesign_field">
	    <div id="blogMainForm_useCustomDesign_dropdown" class="formFieldDropDown" >       
	        <select name="useCustomDesign">
	        <option value="0" <%=HtmlUtil.getOptionSelect("0", _BlogMain.getUseCustomDesign())%>>No</option>
	        <option value="1" <%=HtmlUtil.getOptionSelect("1", _BlogMain.getUseCustomDesign())%>>Yes</option>
	        </select>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogMainFormEditField_UseCustomDesign_<%=_BlogMain.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_main_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogMainFormEditField_CustomDesignFile_<%=_BlogMain.getId()%>" method="get" action="/blogMainAction.html" >


		<div id="blogMainForm_customDesignFile_field">
	    <div id="blogMainForm_customDesignFile_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="customDesignFile" value="<%=WebUtil.display(_BlogMain.getCustomDesignFile())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogMainFormEditField_CustomDesignFile_<%=_BlogMain.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="/blog_main_home">
	</form>
    
    
    </td>

    <td>
    <form name="blogMainForm<%=_BlogMain.getId()%>" method="get" action="/v_blog_main_edit.html" >
        <a href="javascript:document.blogMainForm<%=_BlogMain.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogMain.getId() %>">
    </form>
    <form name="blogMainForm<%=_BlogMain.getId()%>2" method="get" action="/v_blog_main_edit2.html" >
        <a href="javascript:document.blogMainForm<%=_BlogMain.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogMain.getId() %>">
    </form>
    </td>

    <td>
    <a href="/blogMainAction.html?del=true&id=<%=_BlogMain.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>