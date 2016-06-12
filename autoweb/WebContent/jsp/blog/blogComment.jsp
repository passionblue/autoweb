<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	BlogCommentDS ds = BlogCommentDS.getInstance();    

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
            <a href="v_blog_comment_form.html?prv_returnPage=blog_comment_home"> Add New </a> |
            <a href="v_blog_comment_list.html?"> ListPage </a> |
            <a href="v_blog_comment_ajax.html?"> Ajax Sampler </a> |
			<a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform&formfieldlist=&forcehiddenlist=" rel="facebox">Ajax Add</a> |
			<a href="/blogCommentAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Add</a> |

        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="blogCommentForm_blogMainId_label" style="font-size: normal normal bold 10px verdana;" >Blog Main Id </div>
    </td> 
    <td> 
	    <div id="blogCommentForm_blogPostId_label" style="font-size: normal normal bold 10px verdana;" >Blog Post Id </div>
    </td> 
    <td> 
	    <div id="blogCommentForm_comment_label" style="font-size: normal normal bold 10px verdana;" >Comment </div>
    </td> 
    <td> 
	    <div id="blogCommentForm_rating_label" style="font-size: normal normal bold 10px verdana;" >Rating </div>
    </td> 
    <td> 
	    <div id="blogCommentForm_name_label" style="font-size: normal normal bold 10px verdana;" >Name </div>
    </td> 
    <td> 
	    <div id="blogCommentForm_password_label" style="font-size: normal normal bold 10px verdana;" >Password </div>
    </td> 
    <td> 
	    <div id="blogCommentForm_website_label" style="font-size: normal normal bold 10px verdana;" >Website </div>
    </td> 
    <td> 
	    <div id="blogCommentForm_email_label" style="font-size: normal normal bold 10px verdana;" >Email </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogComment _BlogComment = (BlogComment) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogComment.getId() %> </td>


    <td> 
	<form name="blogCommentFormEditField_BlogMainId_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >


		<div id="blogCommentForm_blogMainId_field">
	    <div id="blogCommentForm_blogMainId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogMainId" value="<%=WebUtil.display(_BlogComment.getBlogMainId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_BlogMainId_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCommentFormEditField_BlogPostId_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >


		<div id="blogCommentForm_blogPostId_field">
	    <div id="blogCommentForm_blogPostId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="blogPostId" value="<%=WebUtil.display(_BlogComment.getBlogPostId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_BlogPostId_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCommentFormEditField_Comment_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >


		<div id="blogCommentForm_comment_field">
	    <div id="blogCommentForm_comment_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="comment" value="<%=WebUtil.display(_BlogComment.getComment())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_Comment_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCommentFormEditField_Rating_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >

		<div id="blogCommentForm_rating_field">
	    <div id="blogCommentForm_rating_dropdown" class="formFieldDropDown" >       
	        <select id="field" name="rating">
	        <option value="" >- Please Select -</option>
	        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _BlogComment.getRating())%>>XX</option-->
	        </select>  <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_Rating_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>


    <td> 
	<form name="blogCommentFormEditField_Name_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >


		<div id="blogCommentForm_name_field">
	    <div id="blogCommentForm_name_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="name" value="<%=WebUtil.display(_BlogComment.getName())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_Name_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCommentFormEditField_Password_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >


		<div id="blogCommentForm_password_field">
	    <div id="blogCommentForm_password_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="password" value="<%=WebUtil.display(_BlogComment.getPassword())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_Password_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCommentFormEditField_Website_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >


		<div id="blogCommentForm_website_field">
	    <div id="blogCommentForm_website_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="website" value="<%=WebUtil.display(_BlogComment.getWebsite())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_Website_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>

    <td> 
	<form name="blogCommentFormEditField_Email_<%=_BlogComment.getId()%>" method="get" action="/blogCommentAction.html" >


		<div id="blogCommentForm_email_field">
	    <div id="blogCommentForm_email_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="email" value="<%=WebUtil.display(_BlogComment.getEmail())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.blogCommentFormEditField_Email_<%=_BlogComment.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogComment.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="blog_comment_home">
	</form>
    
    
    </td>


    <td>
    <form name="blogCommentForm<%=_BlogComment.getId()%>2" method="get" action="/v_blog_comment_form.html" >
        <a href="javascript:document.blogCommentForm<%=_BlogComment.getId()%>2.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogComment.getId() %>">
        <INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
        <INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="blog_comment_home">
    </form>
    </td>

    <td>
    <a href="/blogCommentAction.html?del=true&id=<%=_BlogComment.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>