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
    SiteSuggest _SiteSuggest = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_SiteSuggest = SiteSuggestDS.getInstance().getById(id);
		if ( _SiteSuggest == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _SiteSuggest = new SiteSuggest();// SiteSuggestDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_suggest_home";

    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_SiteSuggest.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _suggestValue= (reqParams.get("suggest")==null?WebUtil.display(_SiteSuggest.getSuggest()):WebUtil.display((String)reqParams.get("suggest")));
    String _resolvedValue= (reqParams.get("resolved")==null?WebUtil.display(_SiteSuggest.getResolved()):WebUtil.display((String)reqParams.get("resolved")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteSuggest.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteSuggest.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="siteSuggestForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteSuggestFormEdit" method="POST" action="/siteSuggestAction.html" >



	<div id="siteSuggestForm_category_field">
    <div id="siteSuggestForm_category_label" class="formLabel" >Category </div>
    <div id="siteSuggestForm_category_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="category">
        <option value="" >- Please Select -</option>
<%
	List dropMenus = DropMenuUtil.getDropMenus("SiteSuggestCategoryOption");
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _categoryValue)%>><%=it.getDisplay() %></option>
<%} %>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteSuggestForm_suggest_field">
    <div id="siteSuggestForm_suggest_label" class="formRequiredLabel" >Suggest* </div>
    <div id="siteSuggestForm_suggest_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="requiredField" NAME="suggest" COLS="50" ROWS="8" ><%=WebUtil.display(_suggestValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="siteSuggestForm_resolved_field">
    <div id="siteSuggestForm_resolved_label" class="formLabel" >Resolved </div>
    <div id="siteSuggestForm_resolved_dropdown" class="formFieldDropDown" >       
        <select name="resolved">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _resolvedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _resolvedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>








        <div id="siteSuggestFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteSuggestFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteSuggest.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
