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

    MetaHeader _MetaHeader = MetaHeaderDS.getInstance().getById(id);

    if ( _MetaHeader == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "meta_header_home";

    String _sourceValue=  WebUtil.display(_MetaHeader.getSource());
    String _detail_idValue=  WebUtil.display(_MetaHeader.getDetailId());
    String _nameValue=  WebUtil.display(_MetaHeader.getName());
    String _valueValue=  WebUtil.display(_MetaHeader.getValue());
    String _http_equivValue=  WebUtil.display(_MetaHeader.getHttpEquiv());
    String _time_createdValue=  WebUtil.display(_MetaHeader.getTimeCreated());
%> 

<br>
<div id="metaHeaderForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="metaHeaderFormEdit" method="POST" action="/metaHeaderAction.html" >


	<div id="metaHeaderForm_source_field">
    <div id="metaHeaderForm_source_label" class="formLabel" >Source </div>
    <div id="metaHeaderForm_source_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="source">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _sourceValue)%>>XX</option-->
        <option value="page" <%=HtmlUtil.getOptionSelect("page", _sourceValue)%>>Page</option>
        <option value="content" <%=HtmlUtil.getOptionSelect("content", _sourceValue)%>>Content</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="metaHeaderForm_detailId_field">
    <div id="metaHeaderForm_detailId_label" class="formLabel" >Detail Id </div>
    <div id="metaHeaderForm_detailId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="detailId" value="<%=WebUtil.display(_detail_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="metaHeaderForm_name_field">
    <div id="metaHeaderForm_name_label" class="formRequiredLabel" >Name* </div>
    <div id="metaHeaderForm_name_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="metaHeaderForm_value_field">
    <div id="metaHeaderForm_value_label" class="formLabel" >Value </div>
    <div id="metaHeaderForm_value_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="metaHeaderForm_httpEquiv_field">
    <div id="metaHeaderForm_httpEquiv_label" class="formLabel" >Http Equiv </div>
    <div id="metaHeaderForm_httpEquiv_dropdown" class="formFieldDropDown" >       
        <select name="httpEquiv">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _http_equivValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _http_equivValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





        <div id="metaHeaderFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.metaHeaderFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MetaHeader.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
