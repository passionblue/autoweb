<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	SiteConfigTransient siteConfigTrans = SiteConfigTransient.getTransientConfig(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	if (sessionContext == null) 
		sessionContext = AutositeSessionContext.create(session);

	SessionWrapper wrap = sessionContext.getSessionWrapper();

	Page dynPage = wrap.getPage();
	PageConfig pageConfig = null;
	if (dynPage != null)
		pageConfig = PageConfigDS.getInstance().getObjectByPageId(dynPage.getId());

	PageView pageView = wrap.getViewPage();


    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="contentCategoryForm_topArea" class="formTopArea"></div>
<div id="contentCategoryForm" class="formFrame">
<div class="fieldSideText" style="color:red;">* Required fields</div>
<form name="contentCategoryForm" method="get" action="/contentCategoryAction.html" >




	<div id="contentCategoryForm_pageId_field">
    <div id="contentCategoryForm_pageId_label" class="formLabel" >Page Id </div>
    <div id="contentCategoryForm_pageId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="pageId" value="<%=WebUtil.display("_page_idValue")%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="contentCategoryForm_category_field">
    <div id="contentCategoryForm_category_label" class="formRequiredLabel" >Category* </div>
    <div id="contentCategoryForm_category_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="category" value="<%=WebUtil.display("_categoryValue")%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="contentCategoryForm_imageUrl_field">
    <div id="contentCategoryForm_imageUrl_label" class="formLabel" >Image Url </div>
    <div id="contentCategoryForm_imageUrl_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="imageUrl" value="<%=WebUtil.display("_image_urlValue")%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="contentCategoryForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentCategoryForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="contentCategoryForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentCategoryDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentCategory _ContentCategory = (ContentCategory) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentCategory.getId() %> </td>

    <td> <%= WebUtil.display(_ContentCategory.getPageId()) %></td>
    <td> <%= WebUtil.display(_ContentCategory.getCategory()) %></td>
    <td> <%= WebUtil.display(_ContentCategory.getImageUrl()) %></td>
    <td> <%= WebUtil.display(_ContentCategory.getTimeCreated()) %></td>


<td>
<form name="contentCategoryForm<%=_ContentCategory.getId()%>" method="get" action="/v_content_category_edit.html" >
    <a href="javascript:document.contentCategoryForm<%=_ContentCategory.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentCategory.getId() %>">
</form>
<form name="contentCategoryForm<%=_ContentCategory.getId()%>2" method="get" action="/v_content_category_edit2.html" >
    <a href="javascript:document.contentCategoryForm<%=_ContentCategory.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentCategory.getId() %>">
</form>

</td>
<td>
<a href="/contentCategoryAction.html?del=true&id=<%=_ContentCategory.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>