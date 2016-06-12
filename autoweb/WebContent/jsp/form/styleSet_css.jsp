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
    StyleSet _StyleSet = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_StyleSet = StyleSetDS.getInstance().getById(id);
		if ( _StyleSet == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _StyleSet = new StyleSet();// StyleSetDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_StyleSet.getName()):WebUtil.display((String)reqParams.get("name")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_StyleSet.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
    String _data_style_idValue= (reqParams.get("dataStyleId")==null?WebUtil.display(_StyleSet.getDataStyleId()):WebUtil.display((String)reqParams.get("dataStyleId")));
    String _link_style_idValue= (reqParams.get("linkStyleId")==null?WebUtil.display(_StyleSet.getLinkStyleId()):WebUtil.display((String)reqParams.get("linkStyleId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleSet.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleSet.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="styleSetForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleSetFormEdit" method="POST" action="/styleSetAction.html" >




	<div id="styleSetForm_name_field">
    <div id="styleSetForm_name_label" class="formLabel" >Name </div>
    <div id="styleSetForm_name_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="styleSetForm_styleId_field">
    <div id="styleSetForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="styleSetForm_styleId_dropdown" class="formFieldDropDown" >       
        <select id="field" name="styleId">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _style_idValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetForm_dataStyleId_field">
    <div id="styleSetForm_dataStyleId_label" class="formLabel" >Data Style Id </div>
    <div id="styleSetForm_dataStyleId_dropdown" class="formFieldDropDown" >       
        <select id="field" name="dataStyleId">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _data_style_idValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetForm_linkStyleId_field">
    <div id="styleSetForm_linkStyleId_label" class="formLabel" >Link Style Id </div>
    <div id="styleSetForm_linkStyleId_dropdown" class="formFieldDropDown" >       
        <select id="field" name="linkStyleId">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _link_style_idValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>






        <div id="styleSetFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleSetFormEdit.submit();">Submit</a>
        </div>      

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSet.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
