<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    BlogMain _BlogMain = BlogMainDS.getInstance().getById(id);

    if ( _BlogMain == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_BlogMain.getUserId());
    String _blog_nameValue=  WebUtil.display(_BlogMain.getBlogName());
    String _blog_titleValue=  WebUtil.display(_BlogMain.getBlogTitle());
    String _time_createdValue=  WebUtil.display(_BlogMain.getTimeCreated());
    String _main_design_selectValue=  WebUtil.display(_BlogMain.getMainDesignSelect());
    String _use_custom_designValue=  WebUtil.display(_BlogMain.getUseCustomDesign());
    String _custom_design_fileValue=  WebUtil.display(_BlogMain.getCustomDesignFile());
%> 

<br>
<div id="blogMainForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blogMainFormEdit" method="get" action="/blogMainAction.html" >




	<div id="blogMainForm_userId_field">
    <div id="blogMainForm_userId_label" class="formLabel" >User Id </div>
    <div id="blogMainForm_userId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="userId" value="<%=WebUtil.display(_user_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blogMainForm_blogName_field">
    <div id="blogMainForm_blogName_label" class="formRequiredLabel" >Blog Name* </div>
    <div id="blogMainForm_blogName_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="blogName" value="<%=WebUtil.display(_blog_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="blogMainForm_blogTitle_field">
    <div id="blogMainForm_blogTitle_label" class="formRequiredLabel" >Blog Title* </div>
    <div id="blogMainForm_blogTitle_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="blogTitle" value="<%=WebUtil.display(_blog_titleValue)%>"/> <span></span>
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
    <div id="blogMainForm_customDesignFile_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="customDesignFile" value="<%=WebUtil.display(_custom_design_fileValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="blogMainFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogMainFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
</form>
</div> <!-- form -->
