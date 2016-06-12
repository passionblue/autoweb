<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlogMain _BlogMainDefault = new BlogMain();// BlogMainDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_BlogMainDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _blog_nameValue= (reqParams.get("blogName")==null?WebUtil.display(_BlogMainDefault.getBlogName()):WebUtil.display((String)reqParams.get("blogName")));
    String _blog_titleValue= (reqParams.get("blogTitle")==null?WebUtil.display(_BlogMainDefault.getBlogTitle()):WebUtil.display((String)reqParams.get("blogTitle")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlogMainDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _main_design_selectValue= (reqParams.get("mainDesignSelect")==null?WebUtil.display(_BlogMainDefault.getMainDesignSelect()):WebUtil.display((String)reqParams.get("mainDesignSelect")));
    String _use_custom_designValue= (reqParams.get("useCustomDesign")==null?WebUtil.display(_BlogMainDefault.getUseCustomDesign()):WebUtil.display((String)reqParams.get("useCustomDesign")));
    String _custom_design_fileValue= (reqParams.get("customDesignFile")==null?WebUtil.display(_BlogMainDefault.getCustomDesignFile()):WebUtil.display((String)reqParams.get("customDesignFile")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="blogMainForm_topArea" class="formTopArea"></div>
<div id="blogMainForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="blogMainForm" method="get" action="/blogMainAction.html" >




	<div id="blogMainForm_userId_field">
    <div id="blogMainForm_userId_label" class="formLabel" >User Id </div>
    <div id="blogMainForm_userId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="blogMainForm_blogName_field">
    <div id="blogMainForm_blogName_label" class="formRequiredLabel" >Blog Name* </div>
    <div id="blogMainForm_blogName_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="blogName" value="<%=WebUtil.display(_blog_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="blogMainForm_blogTitle_field">
    <div id="blogMainForm_blogTitle_label" class="formRequiredLabel" >Blog Title* </div>
    <div id="blogMainForm_blogTitle_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="blogTitle" value="<%=WebUtil.display(_blog_titleValue)%>"/> 
    </div>      
	</div><div class="clear"></div>







	<div id="blogMainForm_mainDesignSelect_field">
    <div id="blogMainForm_mainDesignSelect_label" class="formLabel" >Main Design Select </div>
    <div id="blogMainForm_mainDesignSelect_dropdown" class="formFieldDropDown" >       
        <select id="field" name="mainDesignSelect">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _main_design_selectValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="blogMainForm_useCustomDesign_field">
    <div id="blogMainForm_useCustomDesign_label" class="formLabel" >Use Custom Design </div>
    <div id="blogMainForm_useCustomDesign_dropdown" class="formFieldDropDown" >       
        <select name="useCustomDesign">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _use_custom_designValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _use_custom_designValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="blogMainForm_customDesignFile_field">
    <div id="blogMainForm_customDesignFile_label" class="formLabel" >Custom Design File </div>
    <div id="blogMainForm_customDesignFile_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="customDesignFile" value="<%=WebUtil.display(_custom_design_fileValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="blogMainForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogMainForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="blogMainForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlogMainDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogMain _BlogMain = (BlogMain) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogMain.getId() %> </td>

    <td> <%= WebUtil.display(_BlogMain.getUserId()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getBlogName()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getBlogTitle()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getMainDesignSelect()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getUseCustomDesign()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getCustomDesignFile()) %></td>


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