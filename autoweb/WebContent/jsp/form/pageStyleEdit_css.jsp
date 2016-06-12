<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    PageStyle _PageStyle = PageStyleDS.getInstance().getById(id);

    if ( _PageStyle == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    

    String _page_idValue=  WebUtil.display(_PageStyle.getPageId());
    String _style_idValue=  WebUtil.display(_PageStyle.getStyleId());
%> 

<br>
<div id="pageStyleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pageStyleFormEdit" method="POST" action="/pageStyleAction.html" >




	<div id="pageStyleForm_pageId_field">
    <div id="pageStyleForm_pageId_label" class="formLabel" >Page Id </div>
    <div id="pageStyleForm_pageId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="pageId" value="<%=WebUtil.display(_page_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pageStyleForm_styleId_field">
    <div id="pageStyleForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="pageStyleForm_styleId_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="styleId" value="<%=WebUtil.display(_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="pageStyleFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pageStyleFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStyle.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
