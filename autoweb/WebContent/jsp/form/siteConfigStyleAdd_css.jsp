<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SiteConfigStyle _SiteConfigStyleDefault = new SiteConfigStyle();// SiteConfigStyleDS.getInstance().getDeafult();
    
    String _theme_idValue= (reqParams.get("themeId")==null?WebUtil.display(_SiteConfigStyleDefault.getThemeId()):WebUtil.display((String)reqParams.get("themeId")));
    String _css_indexValue= (reqParams.get("cssIndex")==null?WebUtil.display(_SiteConfigStyleDefault.getCssIndex()):WebUtil.display((String)reqParams.get("cssIndex")));
    String _css_importValue= (reqParams.get("cssImport")==null?WebUtil.display(_SiteConfigStyleDefault.getCssImport()):WebUtil.display((String)reqParams.get("cssImport")));
    String _layout_indexValue= (reqParams.get("layoutIndex")==null?WebUtil.display(_SiteConfigStyleDefault.getLayoutIndex()):WebUtil.display((String)reqParams.get("layoutIndex")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteConfigStyleDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteConfigStyleDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_config_style_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="siteConfigStyleForm_topArea" class="formTopArea"></div>
<div id="siteConfigStyleForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="siteConfigStyleForm" method="POST" action="/siteConfigStyleAction.html" >




	<div id="siteConfigStyleForm_themeId_field">
    <div id="siteConfigStyleForm_themeId_label" class="formLabel" >Theme Id </div>
    <div id="siteConfigStyleForm_themeId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="themeId" value="<%=WebUtil.display(_theme_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="siteConfigStyleForm_cssIndex_field">
    <div id="siteConfigStyleForm_cssIndex_label" class="formLabel" >Css Index </div>
    <div id="siteConfigStyleForm_cssIndex_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="cssIndex" value="<%=WebUtil.display(_css_indexValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="siteConfigStyleForm_cssImport_field">
    <div id="siteConfigStyleForm_cssImport_label" class="formLabel" >Css Import </div>
    <div id="siteConfigStyleForm_cssImport_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="cssImport" COLS="50" ROWS="8" ><%=WebUtil.display(_css_importValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="siteConfigStyleForm_layoutIndex_field">
    <div id="siteConfigStyleForm_layoutIndex_label" class="formLabel" >Layout Index </div>
    <div id="siteConfigStyleForm_layoutIndex_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="layoutIndex" value="<%=WebUtil.display(_layout_indexValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="siteConfigStyleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteConfigStyleForm.submit();">Submit</a>
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
<div id="siteConfigStyleForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SiteConfigStyleDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteConfigStyle _SiteConfigStyle = (SiteConfigStyle) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SiteConfigStyle.getId() %> </td>

    <td> <%= WebUtil.display(_SiteConfigStyle.getThemeId()) %></td>
    <td> <%= WebUtil.display(_SiteConfigStyle.getCssIndex()) %></td>
    <td> <%= WebUtil.display(_SiteConfigStyle.getCssImport()) %></td>
    <td> <%= WebUtil.display(_SiteConfigStyle.getLayoutIndex()) %></td>
    <td> <%= WebUtil.display(_SiteConfigStyle.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_SiteConfigStyle.getTimeUpdated()) %></td>


<td>
<form name="siteConfigStyleForm<%=_SiteConfigStyle.getId()%>2" method="get" action="/v_site_config_style_edit2.html" >
    <a href="javascript:document.siteConfigStyleForm<%=_SiteConfigStyle.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SiteConfigStyle.getId() %>">
</form>

</td>
<td>
<a href="/siteConfigStyleAction.html?del=true&id=<%=_SiteConfigStyle.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>