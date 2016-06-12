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
    ContentData _ContentData = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ContentData = ContentDataDS.getInstance().getById(id);
		if ( _ContentData == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ContentData = new ContentData();// ContentDataDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "content_data_home";

    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentData.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_ContentData.getData()):WebUtil.display((String)reqParams.get("data")));
    String _option1Value= (reqParams.get("option1")==null?WebUtil.display(_ContentData.getOption1()):WebUtil.display((String)reqParams.get("option1")));
    String _option2Value= (reqParams.get("option2")==null?WebUtil.display(_ContentData.getOption2()):WebUtil.display((String)reqParams.get("option2")));
    String _option3Value= (reqParams.get("option3")==null?WebUtil.display(_ContentData.getOption3()):WebUtil.display((String)reqParams.get("option3")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentData.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ContentData.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="contentDataForm" class="formFrame">
<div id="contentDataFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentDataForm_Form" method="POST" action="/contentDataAction.html" id="contentDataForm_Form">





	<div id="contentDataForm_contentId_field" class="formFieldFrame">
    <div id="contentDataForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentDataForm_contentId_text" class="formFieldText" >       
        <input id="contentId" class="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="contentDataForm_data_field" class="formFieldFrame">
    <div id="contentDataForm_data_label" class="formLabel" >Data </div>
    <div id="contentDataForm_data_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="data" class="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="contentDataForm_option1_field" class="formFieldFrame">
    <div id="contentDataForm_option1_label" class="formLabel" >Option1 </div>
    <div id="contentDataForm_option1_text" class="formFieldText" >       
        <input id="option1" class="field" type="text" size="70" name="option1" value="<%=WebUtil.display(_option1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="contentDataForm_option2_field" class="formFieldFrame">
    <div id="contentDataForm_option2_label" class="formLabel" >Option2 </div>
    <div id="contentDataForm_option2_text" class="formFieldText" >       
        <input id="option2" class="field" type="text" size="70" name="option2" value="<%=WebUtil.display(_option2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="contentDataForm_option3_field" class="formFieldFrame">
    <div id="contentDataForm_option3_label" class="formLabel" >Option3 </div>
    <div id="contentDataForm_option3_text" class="formFieldText" >       
        <input id="option3" class="field" type="text" size="70" name="option3" value="<%=WebUtil.display(_option3Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div class="submitFrame">

        <div id="contentDataForm_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.contentDataForm_Form.submit();">Submit</a>
        </div>      

        <div id="contentDataForm_submit_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="contentDataForm_submit_ext" class="formSubmitExt" >       
            <a href="#">Ext</a>
        </div>      
	<div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentData.getId()%>">

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
