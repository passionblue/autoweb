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

    StyleSet _StyleSet = StyleSetDS.getInstance().getById(id);

    if ( _StyleSet == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    

    String _nameValue=  WebUtil.display(_StyleSet.getName());
    String _style_idValue=  WebUtil.display(_StyleSet.getStyleId());
    String _data_style_idValue=  WebUtil.display(_StyleSet.getDataStyleId());
    String _link_style_idValue=  WebUtil.display(_StyleSet.getLinkStyleId());
    String _time_createdValue=  WebUtil.display(_StyleSet.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_StyleSet.getTimeUpdated());
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

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleSet.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
