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

    SiteConfigStyle _SiteConfigStyle = SiteConfigStyleDS.getInstance().getById(id);

    if ( _SiteConfigStyle == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_config_style_home";

    String _theme_idValue=  WebUtil.display(_SiteConfigStyle.getThemeId());
    String _css_indexValue=  WebUtil.display(_SiteConfigStyle.getCssIndex());
    String _css_importValue=  WebUtil.display(_SiteConfigStyle.getCssImport());
    String _layout_indexValue=  WebUtil.display(_SiteConfigStyle.getLayoutIndex());
    String _time_createdValue=  WebUtil.display(_SiteConfigStyle.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_SiteConfigStyle.getTimeUpdated());
%> 

<br>
<div id="siteConfigStyleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="siteConfigStyleFormEdit" method="POST" action="/siteConfigStyleAction.html" >




	<div id="siteConfigStyleForm_themeId_field">
    <div id="siteConfigStyleForm_themeId_label" class="formLabel" >Theme Id </div>
    <div id="siteConfigStyleForm_themeId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="themeId" value="<%=WebUtil.display(_theme_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteConfigStyleForm_cssIndex_field">
    <div id="siteConfigStyleForm_cssIndex_label" class="formLabel" >Css Index </div>
    <div id="siteConfigStyleForm_cssIndex_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="siteConfigStyleForm_cssImport_field">
    <div id="siteConfigStyleForm_cssImport_label" class="formLabel" >Css Import </div>
    <div id="siteConfigStyleForm_cssImport_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="cssImport" COLS="50" ROWS="8" ><%=WebUtil.display(_css_importValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="siteConfigStyleForm_layoutIndex_field">
    <div id="siteConfigStyleForm_layoutIndex_label" class="formLabel" >Layout Index </div>
    <div id="siteConfigStyleForm_layoutIndex_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="layoutIndex" value="<%=WebUtil.display(_layout_indexValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="siteConfigStyleFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteConfigStyleFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteConfigStyle.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
