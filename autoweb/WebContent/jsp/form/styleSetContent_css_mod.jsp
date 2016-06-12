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
    StyleSetContent _StyleSetContent = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_StyleSetContent = StyleSetContentDS.getInstance().getById(id);
		if ( _StyleSetContent == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _StyleSetContent = new StyleSetContent();// StyleSetContentDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_set_content_home";

    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_StyleSetContent.getName()):WebUtil.display((String)reqParams.get("name")));
    String _id_prefixValue= (reqParams.get("idPrefix")==null?WebUtil.display(_StyleSetContent.getIdPrefix()):WebUtil.display((String)reqParams.get("idPrefix")));
    String _list_frame_style_idValue= (reqParams.get("listFrameStyleId")==null?WebUtil.display(_StyleSetContent.getListFrameStyleId()):WebUtil.display((String)reqParams.get("listFrameStyleId")));
    String _list_subject_style_idValue= (reqParams.get("listSubjectStyleId")==null?WebUtil.display(_StyleSetContent.getListSubjectStyleId()):WebUtil.display((String)reqParams.get("listSubjectStyleId")));
    String _list_data_style_idValue= (reqParams.get("listDataStyleId")==null?WebUtil.display(_StyleSetContent.getListDataStyleId()):WebUtil.display((String)reqParams.get("listDataStyleId")));
    String _frame_style_idValue= (reqParams.get("frameStyleId")==null?WebUtil.display(_StyleSetContent.getFrameStyleId()):WebUtil.display((String)reqParams.get("frameStyleId")));
    String _subject_style_idValue= (reqParams.get("subjectStyleId")==null?WebUtil.display(_StyleSetContent.getSubjectStyleId()):WebUtil.display((String)reqParams.get("subjectStyleId")));
    String _data_style_idValue= (reqParams.get("dataStyleId")==null?WebUtil.display(_StyleSetContent.getDataStyleId()):WebUtil.display((String)reqParams.get("dataStyleId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleSetContent.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleSetContent.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="styleSetContentForm" class="formFrame${classSuffix}">
<div id="styleSetContentFormInnerFrame" class="formInnerFrame${classSuffix}">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleSetContentForm_Form" method="POST" action="/styleSetContentAction.html" id="styleSetContentForm_Form">





	<div id="styleSetContentForm_name_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_name_label" class="formRequiredLabel${classSuffix}" >Name* </div>
    <div id="styleSetContentForm_name_text" class="formFieldTex${classSuffix}t" >       
        <input id="name" class="requiredField" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_idPrefix_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_idPrefix_label" class="formRequiredLabel${classSuffix}" >Id Prefix* </div>
    <div id="styleSetContentForm_idPrefix_text" class="formFieldTex${classSuffix}t" >       
        <input id="idPrefix" class="requiredField" type="text" size="70" name="idPrefix" value="<%=WebUtil.display(_id_prefixValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetContentForm_listFrameStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_listFrameStyleId_label" class="formLabel${classSuffix}" >List Frame Style Id </div>
    <div id="styleSetContentForm_listFrameStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="listFrameStyleId" id="listFrameStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_listFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetContentForm_listSubjectStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_listSubjectStyleId_label" class="formLabel${classSuffix}" >List Subject Style Id </div>
    <div id="styleSetContentForm_listSubjectStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="listSubjectStyleId" id="listSubjectStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_listSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetContentForm_listDataStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_listDataStyleId_label" class="formLabel${classSuffix}" >List Data Style Id </div>
    <div id="styleSetContentForm_listDataStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="listDataStyleId" id="listDataStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_listDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetContentForm_frameStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_frameStyleId_label" class="formLabel${classSuffix}" >Frame Style Id </div>
    <div id="styleSetContentForm_frameStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="frameStyleId" id="frameStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_frameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_frameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetContentForm_subjectStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_subjectStyleId_label" class="formLabel${classSuffix}" >Subject Style Id </div>
    <div id="styleSetContentForm_subjectStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="subjectStyleId" id="subjectStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_subjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_subjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleSetContentForm_dataStyleId_field" class="formFieldFrame${classSuffix}">
    <div id="styleSetContentForm_dataStyleId_label" class="formLabel${classSuffix}" >Data Style Id </div>
    <div id="styleSetContentForm_dataStyleId_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select class="field" name="dataStyleId" id="dataStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_dataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_dataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>







	<div class="submitFrame">

        <div id="styleSetContentForm_submit" class="formSubmit${classSuffix}" >       
            <a id="formSubmit2" href="javascript:document.styleSetContentForm_Form.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_submit_cancel" class="formCancel${classSuffix}" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="styleSetContentForm_submit_ext" class="formSubmitExt${classSuffix}" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Name </td> 
    <td class="columnTitle">  Id Prefix </td> 
    <td class="columnTitle">  List Frame Style Id </td> 
    <td class="columnTitle">  List Subject Style Id </td> 
    <td class="columnTitle">  List Data Style Id </td> 
    <td class="columnTitle">  Frame Style Id </td> 
    <td class="columnTitle">  Subject Style Id </td> 
    <td class="columnTitle">  Data Style Id </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = StyleSetContentDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleSetContent _oStyleSetContent = (StyleSetContent) iter.next();
%>

<TR>
    <td> <%= _oStyleSetContent.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oStyleSetContent.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oStyleSetContent.getName()  %> </td>
	<td> <%= _oStyleSetContent.getIdPrefix()  %> </td>
	<td> <%= _oStyleSetContent.getListFrameStyleId()  %> </td>
	<td> <%= _oStyleSetContent.getListSubjectStyleId()  %> </td>
	<td> <%= _oStyleSetContent.getListDataStyleId()  %> </td>
	<td> <%= _oStyleSetContent.getFrameStyleId()  %> </td>
	<td> <%= _oStyleSetContent.getSubjectStyleId()  %> </td>
	<td> <%= _oStyleSetContent.getDataStyleId()  %> </td>
	<td> <%= _oStyleSetContent.getTimeCreated()  %> </td>
	<td> <%= _oStyleSetContent.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/styleSetContentAction.html?del=true&id=<%=_oStyleSetContent.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>