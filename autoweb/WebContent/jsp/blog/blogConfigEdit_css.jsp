<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    BlogConfig _BlogConfig = BlogConfigDS.getInstance().getById(id);

    if ( _BlogConfig == null ) {
        return;
    }

    String _blog_main_idValue=  WebUtil.display(_BlogConfig.getBlogMainId());
%> 

<br>
<div id="blogConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blogConfigFormEdit" method="get" action="/blogConfigAction.html" >




	<div id="blogConfigForm_blogMainId_field">
    <div id="blogConfigForm_blogMainId_label" class="formLabel" >Blog Main Id </div>
    <div id="blogConfigForm_blogMainId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="blogMainId" value="<%=WebUtil.display(_blog_main_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="blogConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blogConfigFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogConfig.getId()%>">
</form>
</div> <!-- form -->
