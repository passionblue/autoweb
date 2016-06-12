<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ContentConfig _ContentConfig = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ContentConfig = ContentConfigDS.getInstance().getById(id);
		if ( _ContentConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ContentConfig = new ContentConfig();// ContentConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "content_config_home";

    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentConfig.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _keywordsValue= (reqParams.get("keywords")==null?WebUtil.display(_ContentConfig.getKeywords()):WebUtil.display((String)reqParams.get("keywords")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentConfig.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ContentConfig.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="contentConfigForm" class="formFrame">
<div id="contentConfigFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentConfigForm_Form" method="POST" action="/contentConfigAction.html" id="contentConfigForm_Form">





	<div id="contentConfigForm_contentId_field" class="formFieldFrame">
    <div id="contentConfigForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentConfigForm_contentId_text" class="formFieldText" >       
        <input id="contentId" class="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="contentConfigForm_keywords_field" class="formFieldFrame">
    <div id="contentConfigForm_keywords_label" class="formLabel" >Keywords </div>
    <div id="contentConfigForm_keywords_text" class="formFieldText" >       
        <input id="keywords" class="field" type="text" size="70" name="keywords" value="<%=WebUtil.display(_keywordsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div class="submitFrame">

        <div id="contentConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.contentConfigForm_Form.submit();">Submit</a>
        </div>      

        <div id="contentConfigForm_submit_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="contentConfigForm_submit_ext" class="formSubmitExt" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentConfig.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->
