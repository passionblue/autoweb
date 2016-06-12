<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
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

    StyleSetContent _StyleSetContent = StyleSetContentDS.getInstance().getById(id);

    if ( _StyleSetContent == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_set_content_home";

    String _nameValue=  WebUtil.display(_StyleSetContent.getName());
    String _id_prefixValue=  WebUtil.display(_StyleSetContent.getIdPrefix());
    String _list_frame_style_idValue=  WebUtil.display(_StyleSetContent.getListFrameStyleId());
    String _list_subject_style_idValue=  WebUtil.display(_StyleSetContent.getListSubjectStyleId());
    String _list_data_style_idValue=  WebUtil.display(_StyleSetContent.getListDataStyleId());
    String _frame_style_idValue=  WebUtil.display(_StyleSetContent.getFrameStyleId());
    String _subject_style_idValue=  WebUtil.display(_StyleSetContent.getSubjectStyleId());
    String _data_style_idValue=  WebUtil.display(_StyleSetContent.getDataStyleId());
    String _time_createdValue=  WebUtil.display(_StyleSetContent.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_StyleSetContent.getTimeUpdated());
%> 

<br>
<div id="styleSetContentForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleSetContentFormEdit" method="POST" action="/styleSetContentAction.html" >




	<div id="styleSetContentForm_name_field">
    <div id="styleSetContentForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="styleSetContentForm_name_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetContentForm_idPrefix_field">
    <div id="styleSetContentForm_idPrefix_label" class="formRequiredLabel" >Id Prefix* </div>
    <div id="styleSetContentForm_idPrefix_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="idPrefix" value="<%=WebUtil.display(_id_prefixValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetContentForm_listFrameStyleId_field">
    <div id="styleSetContentForm_listFrameStyleId_label" class="formLabel" >List Frame Style Id </div>
    <div id="styleSetContentForm_listFrameStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listFrameStyleId" value="<%=WebUtil.display(_list_frame_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetContentForm_listSubjectStyleId_field">
    <div id="styleSetContentForm_listSubjectStyleId_label" class="formLabel" >List Subject Style Id </div>
    <div id="styleSetContentForm_listSubjectStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listSubjectStyleId" value="<%=WebUtil.display(_list_subject_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetContentForm_listDataStyleId_field">
    <div id="styleSetContentForm_listDataStyleId_label" class="formLabel" >List Data Style Id </div>
    <div id="styleSetContentForm_listDataStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listDataStyleId" value="<%=WebUtil.display(_list_data_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetContentForm_frameStyleId_field">
    <div id="styleSetContentForm_frameStyleId_label" class="formLabel" >Frame Style Id </div>
    <div id="styleSetContentForm_frameStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="frameStyleId" value="<%=WebUtil.display(_frame_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetContentForm_subjectStyleId_field">
    <div id="styleSetContentForm_subjectStyleId_label" class="formLabel" >Subject Style Id </div>
    <div id="styleSetContentForm_subjectStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="subjectStyleId" value="<%=WebUtil.display(_subject_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetContentForm_dataStyleId_field">
    <div id="styleSetContentForm_dataStyleId_label" class="formLabel" >Data Style Id </div>
    <div id="styleSetContentForm_dataStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="dataStyleId" value="<%=WebUtil.display(_data_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="styleSetContentFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleSetContentFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSetContent.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
