<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
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

    SectionDataHolder _Section = SectionDS.getInstance().getById(id);

    if ( _Section == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "section_home";

    String _titleValue=  WebUtil.display(_Section.getTitle());
    String _main_page_idValue=  WebUtil.display(_Section.getMainPageId());
    String _time_createdValue=  WebUtil.display(_Section.getTimeCreated());
%> 

<br>
<div id="sectionForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sectionFormEdit" method="POST" action="/sectionAction.html" >




	<div id="sectionForm_title_field">
    <div id="sectionForm_title_label" class="formLabel" >Title </div>
    <div id="sectionForm_title_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="sectionForm_mainPageId_field">
    <div id="sectionForm_mainPageId_label" class="formLabel" >Main Page Id </div>
    <div id="sectionForm_mainPageId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="mainPageId" value="<%=WebUtil.display(_main_page_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="sectionFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sectionFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Section.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
