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

    SiteSuggest _SiteSuggest = SiteSuggestDS.getInstance().getById(id);

    if ( _SiteSuggest == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_suggest_home";

    String _categoryValue=  WebUtil.display(_SiteSuggest.getCategory());
    String _suggestValue=  WebUtil.display(_SiteSuggest.getSuggest());
    String _resolvedValue=  WebUtil.display(_SiteSuggest.getResolved());
    String _time_createdValue=  WebUtil.display(_SiteSuggest.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_SiteSuggest.getTimeUpdated());
%> 

<br>
<div id="siteSuggestForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteSuggestFormEdit" method="POST" action="/siteSuggestAction.html" >




	<div id="siteSuggestForm_category_field">
    <div id="siteSuggestForm_category_label" class="formRequiredLabel" >Category* </div>
    <div id="siteSuggestForm_category_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="category" value="<%=WebUtil.display(_categoryValue)%>"/> <span></span>
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

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteSuggest.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
