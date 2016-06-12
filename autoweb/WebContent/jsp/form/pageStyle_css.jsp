<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    PageStyle _PageStyle = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_PageStyle = PageStyleDS.getInstance().getById(id);
		if ( _PageStyle == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _PageStyle = new PageStyle();// PageStyleDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_PageStyle.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_PageStyle.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
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

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PageStyle.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
