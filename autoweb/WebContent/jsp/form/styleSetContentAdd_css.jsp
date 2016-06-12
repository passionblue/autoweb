<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    StyleSetContent _StyleSetContentDefault = new StyleSetContent();// StyleSetContentDS.getInstance().getDeafult();
    
    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_StyleSetContentDefault.getName()):WebUtil.display((String)reqParams.get("name")));
    String _id_prefixValue= (reqParams.get("idPrefix")==null?WebUtil.display(_StyleSetContentDefault.getIdPrefix()):WebUtil.display((String)reqParams.get("idPrefix")));
    String _list_frame_style_idValue= (reqParams.get("listFrameStyleId")==null?WebUtil.display(_StyleSetContentDefault.getListFrameStyleId()):WebUtil.display((String)reqParams.get("listFrameStyleId")));
    String _list_subject_style_idValue= (reqParams.get("listSubjectStyleId")==null?WebUtil.display(_StyleSetContentDefault.getListSubjectStyleId()):WebUtil.display((String)reqParams.get("listSubjectStyleId")));
    String _list_data_style_idValue= (reqParams.get("listDataStyleId")==null?WebUtil.display(_StyleSetContentDefault.getListDataStyleId()):WebUtil.display((String)reqParams.get("listDataStyleId")));
    String _frame_style_idValue= (reqParams.get("frameStyleId")==null?WebUtil.display(_StyleSetContentDefault.getFrameStyleId()):WebUtil.display((String)reqParams.get("frameStyleId")));
    String _subject_style_idValue= (reqParams.get("subjectStyleId")==null?WebUtil.display(_StyleSetContentDefault.getSubjectStyleId()):WebUtil.display((String)reqParams.get("subjectStyleId")));
    String _data_style_idValue= (reqParams.get("dataStyleId")==null?WebUtil.display(_StyleSetContentDefault.getDataStyleId()):WebUtil.display((String)reqParams.get("dataStyleId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleSetContentDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleSetContentDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_set_content_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="styleSetContentForm_topArea" class="formTopArea"></div>
<div id="styleSetContentForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="styleSetContentForm" method="POST" action="/styleSetContentAction.html" >




	<div id="styleSetContentForm_name_field">
    <div id="styleSetContentForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="styleSetContentForm_name_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_idPrefix_field">
    <div id="styleSetContentForm_idPrefix_label" class="formRequiredLabel" >Id Prefix* </div>
    <div id="styleSetContentForm_idPrefix_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="idPrefix" value="<%=WebUtil.display(_id_prefixValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_listFrameStyleId_field">
    <div id="styleSetContentForm_listFrameStyleId_label" class="formLabel" >List Frame Style Id </div>
    <div id="styleSetContentForm_listFrameStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listFrameStyleId" value="<%=WebUtil.display(_list_frame_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_listSubjectStyleId_field">
    <div id="styleSetContentForm_listSubjectStyleId_label" class="formLabel" >List Subject Style Id </div>
    <div id="styleSetContentForm_listSubjectStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listSubjectStyleId" value="<%=WebUtil.display(_list_subject_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_listDataStyleId_field">
    <div id="styleSetContentForm_listDataStyleId_label" class="formLabel" >List Data Style Id </div>
    <div id="styleSetContentForm_listDataStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listDataStyleId" value="<%=WebUtil.display(_list_data_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_frameStyleId_field">
    <div id="styleSetContentForm_frameStyleId_label" class="formLabel" >Frame Style Id </div>
    <div id="styleSetContentForm_frameStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="frameStyleId" value="<%=WebUtil.display(_frame_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_subjectStyleId_field">
    <div id="styleSetContentForm_subjectStyleId_label" class="formLabel" >Subject Style Id </div>
    <div id="styleSetContentForm_subjectStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="subjectStyleId" value="<%=WebUtil.display(_subject_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleSetContentForm_dataStyleId_field">
    <div id="styleSetContentForm_dataStyleId_label" class="formLabel" >Data Style Id </div>
    <div id="styleSetContentForm_dataStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="dataStyleId" value="<%=WebUtil.display(_data_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="styleSetContentForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleSetContentForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="styleSetContentForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = StyleSetContentDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleSetContent _StyleSetContent = (StyleSetContent) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleSetContent.getId() %> </td>

    <td> <%= WebUtil.display(_StyleSetContent.getName()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getIdPrefix()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getListFrameStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getListSubjectStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getListDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getFrameStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getSubjectStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_StyleSetContent.getTimeUpdated()) %></td>


<td>
<form name="styleSetContentForm<%=_StyleSetContent.getId()%>2" method="get" action="/v_style_set_content_edit2.html" >
    <a href="javascript:document.styleSetContentForm<%=_StyleSetContent.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleSetContent.getId() %>">
</form>

</td>
<td>
<a href="/styleSetContentAction.html?del=true&id=<%=_StyleSetContent.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>