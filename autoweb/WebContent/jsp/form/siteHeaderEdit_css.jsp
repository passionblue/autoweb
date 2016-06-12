<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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

    SiteHeader _SiteHeader = SiteHeaderDS.getInstance().getById(id);

    if ( _SiteHeader == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_header_home";

    String _as_isValue=  WebUtil.display(_SiteHeader.getAsIs());
    String _include_typeValue=  WebUtil.display(_SiteHeader.getIncludeType());
    String _include_textValue=  WebUtil.display(_SiteHeader.getIncludeText());
    String _time_createdValue=  WebUtil.display(_SiteHeader.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_SiteHeader.getTimeUpdated());
%> 

<br>
<div id="siteHeaderForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteHeaderFormEdit" method="POST" action="/siteHeaderAction.html" >



	<div id="siteHeaderForm_asIs_field">
    <div id="siteHeaderForm_asIs_label" class="formLabel" >As Is </div>
    <div id="siteHeaderForm_asIs_dropdown" class="formFieldDropDown" >       
        <select name="asIs">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _as_isValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _as_isValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



	<div id="siteHeaderForm_includeType_field">
    <div id="siteHeaderForm_includeType_label" class="formLabel" >Include Type </div>
    <div id="siteHeaderForm_includeType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="includeType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _include_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _include_typeValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _include_typeValue)%>>ScriptLink</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _include_typeValue)%>>ScriptText</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _include_typeValue)%>>StyleLink</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _include_typeValue)%>>StyleText</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="siteHeaderForm_includeText_field">
    <div id="siteHeaderForm_includeText_label" class="formLabel" >Include Text </div>
    <div id="siteHeaderForm_includeText_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="includeText" COLS="50" ROWS="8" ><%=WebUtil.display(_include_textValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





        <div id="siteHeaderFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteHeaderFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteHeader.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
