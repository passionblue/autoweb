<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_theme_home";

    String _titleValue= WebUtil.display((String)reqParams.get("title"));
    String _body_widthValue= WebUtil.display((String)reqParams.get("bodyWidth"));
    String _body_alignValue= WebUtil.display((String)reqParams.get("bodyAlign"));
    String _body_bg_colorValue= WebUtil.display((String)reqParams.get("bodyBgColor"));
    String _body_bg_imageValue= WebUtil.display((String)reqParams.get("bodyBgImage"));
    String _body_bg_attachValue= WebUtil.display((String)reqParams.get("bodyBgAttach"));
    String _body_bg_repeatValue= WebUtil.display((String)reqParams.get("bodyBgRepeat"));
    String _body_bg_positionValue= WebUtil.display((String)reqParams.get("bodyBgPosition"));
    String _content_bg_colorValue= WebUtil.display((String)reqParams.get("contentBgColor"));
    String _use_absoluteValue= WebUtil.display((String)reqParams.get("useAbsolute"));
    String _absolute_topValue= WebUtil.display((String)reqParams.get("absoluteTop"));
    String _absolute_leftValue= WebUtil.display((String)reqParams.get("absoluteLeft"));
    String _absolute_rightValue= WebUtil.display((String)reqParams.get("absoluteRight"));
    String _absolute_bottomValue= WebUtil.display((String)reqParams.get("absoluteBottom"));
    String _panel_style_idValue= WebUtil.display((String)reqParams.get("panelStyleId"));
    String _panel_data_style_idValue= WebUtil.display((String)reqParams.get("panelDataStyleId"));
    String _panel_link_style_idValue= WebUtil.display((String)reqParams.get("panelLinkStyleId"));
    String _panel_title_style_idValue= WebUtil.display((String)reqParams.get("panelTitleStyleId"));
    String _menu_style_idValue= WebUtil.display((String)reqParams.get("menuStyleId"));
    String _menu_link_style_idValue= WebUtil.display((String)reqParams.get("menuLinkStyleId"));
    String _header_menu_style_idValue= WebUtil.display((String)reqParams.get("headerMenuStyleId"));
    String _header_menu_link_style_idValue= WebUtil.display((String)reqParams.get("headerMenuLinkStyleId"));
    String _list_frame_style_idValue= WebUtil.display((String)reqParams.get("listFrameStyleId"));
    String _list_subject_style_idValue= WebUtil.display((String)reqParams.get("listSubjectStyleId"));
    String _list_data_style_idValue= WebUtil.display((String)reqParams.get("listDataStyleId"));
    String _subject_style_idValue= WebUtil.display((String)reqParams.get("subjectStyleId"));
    String _data_style_idValue= WebUtil.display((String)reqParams.get("dataStyleId"));
    String _single_frame_style_idValue= WebUtil.display((String)reqParams.get("singleFrameStyleId"));
    String _single_subject_style_idValue= WebUtil.display((String)reqParams.get("singleSubjectStyleId"));
    String _single_data_style_idValue= WebUtil.display((String)reqParams.get("singleDataStyleId"));
    String _content_panel_style_idValue= WebUtil.display((String)reqParams.get("contentPanelStyleId"));
    String _content_panel_title_style_idValue= WebUtil.display((String)reqParams.get("contentPanelTitleStyleId"));
    String _globalValue= WebUtil.display((String)reqParams.get("global"));
    String _disableValue= WebUtil.display((String)reqParams.get("disable"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_style_theme_home.html"> StyleTheme Home </a>
<%
	
	List list = null;
	list = StyleThemeDS.getInstance().getBySiteId(site.getId());

%>

<div id="styleThemeList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		StyleTheme _StyleTheme = (StyleTheme) iter.next();	
%>

	<div id="styleThemeFrame<%=_StyleTheme.getId() %>" >

		<div id="title" >
			title:<%= _StyleTheme.getTitle() %>
		</div>
		<div id="bodyWidth" >
			bodyWidth:<%= _StyleTheme.getBodyWidth() %>
		</div>
		<div id="bodyAlign" >
			bodyAlign:<%= _StyleTheme.getBodyAlign() %>
		</div>
		<div id="bodyBgColor" >
			bodyBgColor:<%= _StyleTheme.getBodyBgColor() %>
		</div>
		<div id="bodyBgImage" >
			bodyBgImage:<%= _StyleTheme.getBodyBgImage() %>
		</div>
		<div id="bodyBgAttach" >
			bodyBgAttach:<%= _StyleTheme.getBodyBgAttach() %>
		</div>
		<div id="bodyBgRepeat" >
			bodyBgRepeat:<%= _StyleTheme.getBodyBgRepeat() %>
		</div>
		<div id="bodyBgPosition" >
			bodyBgPosition:<%= _StyleTheme.getBodyBgPosition() %>
		</div>
		<div id="contentBgColor" >
			contentBgColor:<%= _StyleTheme.getContentBgColor() %>
		</div>
		<div id="useAbsolute" >
			useAbsolute:<%= _StyleTheme.getUseAbsolute() %>
		</div>
		<div id="absoluteTop" >
			absoluteTop:<%= _StyleTheme.getAbsoluteTop() %>
		</div>
		<div id="absoluteLeft" >
			absoluteLeft:<%= _StyleTheme.getAbsoluteLeft() %>
		</div>
		<div id="absoluteRight" >
			absoluteRight:<%= _StyleTheme.getAbsoluteRight() %>
		</div>
		<div id="absoluteBottom" >
			absoluteBottom:<%= _StyleTheme.getAbsoluteBottom() %>
		</div>
		<div id="panelStyleId" >
			panelStyleId:<%= _StyleTheme.getPanelStyleId() %>
		</div>
		<div id="panelDataStyleId" >
			panelDataStyleId:<%= _StyleTheme.getPanelDataStyleId() %>
		</div>
		<div id="panelLinkStyleId" >
			panelLinkStyleId:<%= _StyleTheme.getPanelLinkStyleId() %>
		</div>
		<div id="panelTitleStyleId" >
			panelTitleStyleId:<%= _StyleTheme.getPanelTitleStyleId() %>
		</div>
		<div id="menuStyleId" >
			menuStyleId:<%= _StyleTheme.getMenuStyleId() %>
		</div>
		<div id="menuLinkStyleId" >
			menuLinkStyleId:<%= _StyleTheme.getMenuLinkStyleId() %>
		</div>
		<div id="headerMenuStyleId" >
			headerMenuStyleId:<%= _StyleTheme.getHeaderMenuStyleId() %>
		</div>
		<div id="headerMenuLinkStyleId" >
			headerMenuLinkStyleId:<%= _StyleTheme.getHeaderMenuLinkStyleId() %>
		</div>
		<div id="listFrameStyleId" >
			listFrameStyleId:<%= _StyleTheme.getListFrameStyleId() %>
		</div>
		<div id="listSubjectStyleId" >
			listSubjectStyleId:<%= _StyleTheme.getListSubjectStyleId() %>
		</div>
		<div id="listDataStyleId" >
			listDataStyleId:<%= _StyleTheme.getListDataStyleId() %>
		</div>
		<div id="subjectStyleId" >
			subjectStyleId:<%= _StyleTheme.getSubjectStyleId() %>
		</div>
		<div id="dataStyleId" >
			dataStyleId:<%= _StyleTheme.getDataStyleId() %>
		</div>
		<div id="singleFrameStyleId" >
			singleFrameStyleId:<%= _StyleTheme.getSingleFrameStyleId() %>
		</div>
		<div id="singleSubjectStyleId" >
			singleSubjectStyleId:<%= _StyleTheme.getSingleSubjectStyleId() %>
		</div>
		<div id="singleDataStyleId" >
			singleDataStyleId:<%= _StyleTheme.getSingleDataStyleId() %>
		</div>
		<div id="contentPanelStyleId" >
			contentPanelStyleId:<%= _StyleTheme.getContentPanelStyleId() %>
		</div>
		<div id="contentPanelTitleStyleId" >
			contentPanelTitleStyleId:<%= _StyleTheme.getContentPanelTitleStyleId() %>
		</div>
		<div id="global" >
			global:<%= _StyleTheme.getGlobal() %>
		</div>
		<div id="disable" >
			disable:<%= _StyleTheme.getDisable() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _StyleTheme.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _StyleTheme.getTimeUpdated() %>
		</div>
		<div>
		<a id="styleThemeDeleteButton" href="javascript:deleteThis('/styleThemeAction.html',<%= _StyleTheme.getId()%>,'styleThemeFrame<%=_StyleTheme.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="styleThemeForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleThemeFormAdd" method="POST" action="/styleThemeAction.html" id="styleThemeFormAdd">

	<div id="styleThemeForm_title_field">
    <div id="styleThemeForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="styleThemeForm_title_text" class="formFieldText" >       
        <input class="requiredField" id="_ffd_title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_bodyWidth_field">
    <div id="styleThemeForm_bodyWidth_label" class="formLabel" >Body Width </div>
    <div id="styleThemeForm_bodyWidth_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyWidth" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_bodyAlign_field">
    <div id="styleThemeForm_bodyAlign_label" class="formLabel" >Body Align </div>
    <div id="styleThemeForm_bodyAlign_dropdown" class="formFieldDropDown" >       
        <select class="field" name="bodyAlign" id="_ffd_bodyAlign">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _body_alignValue)%>>XX</option-->
        <option value="top center" <%=HtmlUtil.getOptionSelect("top center", _body_alignValue)%>>top center</option>
        <option value="top left" <%=HtmlUtil.getOptionSelect("top left", _body_alignValue)%>>top left</option>
        <option value="top right" <%=HtmlUtil.getOptionSelect("top right", _body_alignValue)%>>top right</option>
        <option value="center center" <%=HtmlUtil.getOptionSelect("center center", _body_alignValue)%>>center center</option>
        <option value="center left" <%=HtmlUtil.getOptionSelect("center left", _body_alignValue)%>>center left</option>
        <option value="center right" <%=HtmlUtil.getOptionSelect("center right", _body_alignValue)%>>center right</option>
        <option value="bottom center" <%=HtmlUtil.getOptionSelect("bottom center", _body_alignValue)%>>bottom center</option>
        <option value="bottom left" <%=HtmlUtil.getOptionSelect("bottom left", _body_alignValue)%>>bottom left</option>
        <option value="bottom right" <%=HtmlUtil.getOptionSelect("bottom right", _body_alignValue)%>>bottom right</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_bodyBgColor_field">
    <div id="styleThemeForm_bodyBgColor_label" class="formLabel" >Body Bg Color </div>
    <div id="styleThemeForm_bodyBgColor_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyBgColor" type="text" size="70" name="bodyBgColor" value="<%=WebUtil.display(_body_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_bodyBgImage_field">
    <div id="styleThemeForm_bodyBgImage_label" class="formLabel" >Body Bg Image </div>
    <div id="styleThemeForm_bodyBgImage_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyBgImage" type="text" size="70" name="bodyBgImage" value="<%=WebUtil.display(_body_bg_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_bodyBgAttach_field">
    <div id="styleThemeForm_bodyBgAttach_label" class="formLabel" >Body Bg Attach </div>
    <div id="styleThemeForm_bodyBgAttach_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyBgAttach" type="text" size="70" name="bodyBgAttach" value="<%=WebUtil.display(_body_bg_attachValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_bodyBgRepeat_field">
    <div id="styleThemeForm_bodyBgRepeat_label" class="formLabel" >Body Bg Repeat </div>
    <div id="styleThemeForm_bodyBgRepeat_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyBgRepeat" type="text" size="70" name="bodyBgRepeat" value="<%=WebUtil.display(_body_bg_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_bodyBgPosition_field">
    <div id="styleThemeForm_bodyBgPosition_label" class="formLabel" >Body Bg Position </div>
    <div id="styleThemeForm_bodyBgPosition_text" class="formFieldText" >       
        <input class="field" id="_ffd_bodyBgPosition" type="text" size="70" name="bodyBgPosition" value="<%=WebUtil.display(_body_bg_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_contentBgColor_field">
    <div id="styleThemeForm_contentBgColor_label" class="formLabel" >Content Bg Color </div>
    <div id="styleThemeForm_contentBgColor_text" class="formFieldText" >       
        <input class="field" id="_ffd_contentBgColor" type="text" size="70" name="contentBgColor" value="<%=WebUtil.display(_content_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_useAbsolute_field">
    <div id="styleThemeForm_useAbsolute_label" class="formLabel" >Use Absolute </div>
    <div id="styleThemeForm_useAbsolute_dropdown" class="formFieldDropDown" >       
        <select name="useAbsolute" id="_ffd_useAbsolute">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _use_absoluteValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _use_absoluteValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_absoluteTop_field">
    <div id="styleThemeForm_absoluteTop_label" class="formLabel" >Absolute Top </div>
    <div id="styleThemeForm_absoluteTop_text" class="formFieldText" >       
        <input class="field" id="_ffd_absoluteTop" type="text" size="70" name="absoluteTop" value="<%=WebUtil.display(_absolute_topValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_absoluteLeft_field">
    <div id="styleThemeForm_absoluteLeft_label" class="formLabel" >Absolute Left </div>
    <div id="styleThemeForm_absoluteLeft_text" class="formFieldText" >       
        <input class="field" id="_ffd_absoluteLeft" type="text" size="70" name="absoluteLeft" value="<%=WebUtil.display(_absolute_leftValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_absoluteRight_field">
    <div id="styleThemeForm_absoluteRight_label" class="formLabel" >Absolute Right </div>
    <div id="styleThemeForm_absoluteRight_text" class="formFieldText" >       
        <input class="field" id="_ffd_absoluteRight" type="text" size="70" name="absoluteRight" value="<%=WebUtil.display(_absolute_rightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_absoluteBottom_field">
    <div id="styleThemeForm_absoluteBottom_label" class="formLabel" >Absolute Bottom </div>
    <div id="styleThemeForm_absoluteBottom_text" class="formFieldText" >       
        <input class="field" id="_ffd_absoluteBottom" type="text" size="70" name="absoluteBottom" value="<%=WebUtil.display(_absolute_bottomValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_panelStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_panelStyleId_label" class="formLabel" >Panel Style Id </div>
    <div id="styleThemeForm_panelStyleId_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="panelStyleId" id="_ffd_panelStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_panelStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_panelDataStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_panelDataStyleId_label" class="formLabel" >Panel Data Style Id </div>
    <div id="styleThemeForm_panelDataStyleId_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="panelDataStyleId" id="_ffd_panelDataStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_panelDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_panelLinkStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_panelLinkStyleId_label" class="formLabel" >Panel Link Style Id </div>
    <div id="styleThemeForm_panelLinkStyleId_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="panelLinkStyleId" id="_ffd_panelLinkStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listLinkStyleConfig_panelLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_panelLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_link_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_panelTitleStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_panelTitleStyleId_label" class="formLabel" >Panel Title Style Id </div>
    <div id="styleThemeForm_panelTitleStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="panelTitleStyleId" id="_ffd_panelTitleStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_panelTitleStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelTitleStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_title_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_menuStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_menuStyleId_label" class="formLabel" >Menu Style Id </div>
    <div id="styleThemeForm_menuStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="menuStyleId" id="_ffd_menuStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_menuStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_menuStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_menu_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_menuLinkStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_menuLinkStyleId_label" class="formLabel" >Menu Link Style Id </div>
    <div id="styleThemeForm_menuLinkStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="menuLinkStyleId" id="_ffd_menuLinkStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listLinkStyleConfig_menuLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_menuLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_menu_link_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_headerMenuStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_headerMenuStyleId_label" class="formLabel" >Header Menu Style Id </div>
    <div id="styleThemeForm_headerMenuStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="headerMenuStyleId" id="_ffd_headerMenuStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_headerMenuStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_headerMenuStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_header_menu_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_headerMenuLinkStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_headerMenuLinkStyleId_label" class="formLabel" >Header Menu Link Style Id </div>
    <div id="styleThemeForm_headerMenuLinkStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="headerMenuLinkStyleId" id="_ffd_headerMenuLinkStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listLinkStyleConfig_headerMenuLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_headerMenuLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_header_menu_link_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_listFrameStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_listFrameStyleId_label" class="formLabel" >List Frame Style Id </div>
    <div id="styleThemeForm_listFrameStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="listFrameStyleId" id="_ffd_listFrameStyleId">
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
	<div id="styleThemeForm_listSubjectStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_listSubjectStyleId_label" class="formLabel" >List Subject Style Id </div>
    <div id="styleThemeForm_listSubjectStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="listSubjectStyleId" id="_ffd_listSubjectStyleId">
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
	<div id="styleThemeForm_listDataStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_listDataStyleId_label" class="formLabel" >List Data Style Id </div>
    <div id="styleThemeForm_listDataStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="listDataStyleId" id="_ffd_listDataStyleId">
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
	<div id="styleThemeForm_subjectStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_subjectStyleId_label" class="formLabel" >Subject Style Id </div>
    <div id="styleThemeForm_subjectStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="subjectStyleId" id="_ffd_subjectStyleId">
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
	<div id="styleThemeForm_dataStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_dataStyleId_label" class="formLabel" >Data Style Id </div>
    <div id="styleThemeForm_dataStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="dataStyleId" id="_ffd_dataStyleId">
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
	<div id="styleThemeForm_singleFrameStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_singleFrameStyleId_label" class="formLabel" >Single Frame Style Id </div>
    <div id="styleThemeForm_singleFrameStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="singleFrameStyleId" id="_ffd_singleFrameStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_singleFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_single_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_singleSubjectStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_singleSubjectStyleId_label" class="formLabel" >Single Subject Style Id </div>
    <div id="styleThemeForm_singleSubjectStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="singleSubjectStyleId" id="_ffd_singleSubjectStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_singleSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_single_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_singleDataStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_singleDataStyleId_label" class="formLabel" >Single Data Style Id </div>
    <div id="styleThemeForm_singleDataStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="singleDataStyleId" id="_ffd_singleDataStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_singleDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_single_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_contentPanelStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_contentPanelStyleId_label" class="formLabel" >Content Panel Style Id </div>
    <div id="styleThemeForm_contentPanelStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="contentPanelStyleId" id="_ffd_contentPanelStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_contentPanelStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_contentPanelStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_panel_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_contentPanelTitleStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_contentPanelTitleStyleId_label" class="formLabel" >Content Panel Title Style Id </div>
    <div id="styleThemeForm_contentPanelTitleStyleId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="contentPanelTitleStyleId" id="_ffd_contentPanelTitleStyleId">
        <option value="" >- Please Select -</option>
<%
	List _listStyleConfig_contentPanelTitleStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_contentPanelTitleStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_panel_title_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_global_field">
    <div id="styleThemeForm_global_label" class="formLabel" >Global </div>
    <div id="styleThemeForm_global_dropdown" class="formFieldDropDown" >       
        <select name="global" id="_ffd_global">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _globalValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _globalValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>
	<div id="styleThemeForm_disable_field">
    <div id="styleThemeForm_disable_label" class="formLabel" >Disable </div>
    <div id="styleThemeForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable" id="_ffd_disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>

        <div id="styleThemeFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/styleThemeAction.html', 'styleThemeFormAdd', 'formSubmit_ajax', 'styleTheme');">Submit</a>
        </div> 

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->

<hr>

<br/><br/><br/><br/>

<hr>
<h3> Self hiding pure javascript form</h3>

<script type="text/javascript">

function xmlhttpPostXX(formName, target, dispElementId, dispFieldList,  callback) {
	
	if (document.getElementById(formName) == null){
		alert("Client side Error occurred. Please try again.")
		return;
	}
	
	var parms = getXX(document.getElementById(formName));
	parms += "&ajaxRequest=true&ajaxOut=getlisthtml&ajaxOutArg=last&formfieldlist="+dispFieldList;
	
    var xmlHttpReq = false;
    var self = this;
    
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var strURL = target+ "?" + parms;
    //alert(strURL);
    
    self.xmlHttpReq.open('POST', target, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
    	if (self.xmlHttpReq.readyState == 4) {
        	//alert(self.xmlHttpReq.responseText);
        	fade(formName, 1000, function() {
        		if (callback == null )
        			updatepageXX(dispElementId, dispElementId, self.xmlHttpReq.responseText);
        		else
        			callback(self.xmlHttpReq.responseText);
        	});
        }
    }
    self.xmlHttpReq.send(parms);
}

function updatepageXX(eid, str){
	document.getElementById(eid).innerHTML = str;
}

function getXX(obj) {
	var getstr = "";
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
       
           if (obj.childNodes[i].type == "text") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "password") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "hidden") {
           		alert(obj.childNodes[i].name + "=" + obj.childNodes[i].value);
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "file") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             } else {
                getstr += obj.childNodes[i].name + "=&";
             }
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             }
          }
       }   
       
       if (obj.childNodes[i].tagName == "SELECT") {
           var sel = obj.childNodes[i];
           getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
       }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
       }
    }
	alert(getstr);
    return getstr;
}



function clearFormXX(name) {
	var obj = document.getElementById(name);
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
           if (obj.childNodes[i].type == "text") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "password") {
               obj.childNodes[i].value = "";
           }
           if (obj.childNodes[i].type == "file") {
               obj.childNodes[i].value = "";
           }
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             } 
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                obj.childNodes[i].checked = false;
             }
          }
       }   
       if (obj.childNodes[i].tagName == "SELECT") {
			obj.childNodes[i].selectedIndex = 0;
        }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           obj.childNodes[i].value = "";
        }
        
    }
}


function  animateFadeXX(lastTick, eid, timeToFade)
{  
  var curTick = new Date().getTime();
  var elapsedTicks = curTick - lastTick;
 
  var element = document.getElementById(eid);
 
  if(element.FadeTimeLeft <= elapsedTicks)
  {
    element.style.opacity = element.FadeState == 1 ? '1' : '0';
    element.style.filter = 'alpha(opacity = '
        + (element.FadeState == 1 ? '100' : '0') + ')';
    element.FadeState = element.FadeState == 1 ? 2 : -2;
	document.getElementById(eid).style.display = 'none';
	element.callbackAfter(element.callbackArg);
    return;
  }
 
  element.FadeTimeLeft -= elapsedTicks;
  var newOpVal = element.FadeTimeLeft/timeToFade;
  if(element.FadeState == 1)
    newOpVal = 1 - newOpVal;

  element.style.opacity = newOpVal;
  element.style.filter = 'alpha(opacity = ' + (newOpVal*100) + ')';
 
  setTimeout("animateFadeXX(" + curTick + ",'" + eid + "','" + timeToFade + "')", 33);
}


//var  TimeToFade = 1000.0;

function fadeXX(eid, timeToFade, callback, callbackArg)
{
  var element = document.getElementById(eid);
  if(element == null)
    return;
   
  if(element.FadeState == null)
  {
    if(element.style.opacity == null
        || element.style.opacity == ''
        || element.style.opacity == '1')
    {
      element.FadeState = 2;
    }
    else
    {
      element.FadeState = -2;
    }
  }
   
  if(element.FadeState == 1 || element.FadeState == -1)
  {
    element.FadeState = element.FadeState == 1 ? -1 : 1;
    element.FadeTimeLeft = timeToFade - element.FadeTimeLeft;
  }
  else
  {
    element.FadeState = element.FadeState == 2 ? -1 : 1;
    element.FadeTimeLeft = timeToFade;
	
    element.callbackAfter = callback;
    element.callbackArg = callbackArg;
    setTimeout("animateFadeXX(" + new Date().getTime() + ",'" + eid + "','" + timeToFade + "')", 33);
  }  
}

function backToXX(eid, displayFormId){
	document.getElementById(displayFormId).innerHTML = "";
	document.getElementById(eid).style.display = '';	
	document.getElementById(eid).style.opacity = 1.0;	
	document.getElementById(eid).style.filter = 1.0;	// For IE
}

</script>

<script type="text/javascript">

function responseCallback(data){
	document.getElementById("resultDisplayStyleTheme").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('styleThemeFormAddDis','/styleThemeAction.html', 'resultDisplayStyleTheme', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="styleThemeFormAddDis" method="POST" action="/styleThemeAction.html" id="styleThemeFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Title</div>
        <input class="requiredField" id="title" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Width</div>
        <input class="field" id="bodyWidth" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Align</div>
        <select class="field" name="bodyAlign" id="bodyAlign">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _body_alignValue)%>>XX</option-->
        <option value="top center" <%=HtmlUtil.getOptionSelect("top center", _body_alignValue)%>>top center</option>
        <option value="top left" <%=HtmlUtil.getOptionSelect("top left", _body_alignValue)%>>top left</option>
        <option value="top right" <%=HtmlUtil.getOptionSelect("top right", _body_alignValue)%>>top right</option>
        <option value="center center" <%=HtmlUtil.getOptionSelect("center center", _body_alignValue)%>>center center</option>
        <option value="center left" <%=HtmlUtil.getOptionSelect("center left", _body_alignValue)%>>center left</option>
        <option value="center right" <%=HtmlUtil.getOptionSelect("center right", _body_alignValue)%>>center right</option>
        <option value="bottom center" <%=HtmlUtil.getOptionSelect("bottom center", _body_alignValue)%>>bottom center</option>
        <option value="bottom left" <%=HtmlUtil.getOptionSelect("bottom left", _body_alignValue)%>>bottom left</option>
        <option value="bottom right" <%=HtmlUtil.getOptionSelect("bottom right", _body_alignValue)%>>bottom right</option>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Bg Color</div>
        <input class="field" id="bodyBgColor" type="text" size="70" name="bodyBgColor" value="<%=WebUtil.display(_body_bg_colorValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Bg Image</div>
        <input class="field" id="bodyBgImage" type="text" size="70" name="bodyBgImage" value="<%=WebUtil.display(_body_bg_imageValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Bg Attach</div>
        <input class="field" id="bodyBgAttach" type="text" size="70" name="bodyBgAttach" value="<%=WebUtil.display(_body_bg_attachValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Bg Repeat</div>
        <input class="field" id="bodyBgRepeat" type="text" size="70" name="bodyBgRepeat" value="<%=WebUtil.display(_body_bg_repeatValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Body Bg Position</div>
        <input class="field" id="bodyBgPosition" type="text" size="70" name="bodyBgPosition" value="<%=WebUtil.display(_body_bg_positionValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Content Bg Color</div>
        <input class="field" id="contentBgColor" type="text" size="70" name="contentBgColor" value="<%=WebUtil.display(_content_bg_colorValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Use Absolute</div>
        <select name="useAbsolute" id="useAbsolute">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _use_absoluteValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _use_absoluteValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Absolute Top</div>
        <input class="field" id="absoluteTop" type="text" size="70" name="absoluteTop" value="<%=WebUtil.display(_absolute_topValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Absolute Left</div>
        <input class="field" id="absoluteLeft" type="text" size="70" name="absoluteLeft" value="<%=WebUtil.display(_absolute_leftValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Absolute Right</div>
        <input class="field" id="absoluteRight" type="text" size="70" name="absoluteRight" value="<%=WebUtil.display(_absolute_rightValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Absolute Bottom</div>
        <input class="field" id="absoluteBottom" type="text" size="70" name="absoluteBottom" value="<%=WebUtil.display(_absolute_bottomValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Panel Style Id</div>
        <select class="requiredField" name="panelStyleId" id="panelStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_panelStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Panel Data Style Id</div>
        <select class="requiredField" name="panelDataStyleId" id="panelDataStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_panelDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Panel Link Style Id</div>
        <select class="requiredField" name="panelLinkStyleId" id="panelLinkStyleId">
        <option value="" >- Please Select -</option>
<%
	_listLinkStyleConfig_panelLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_panelLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_link_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Panel Title Style Id</div>
        <select class="field" name="panelTitleStyleId" id="panelTitleStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_panelTitleStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_panelTitleStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_panel_title_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Menu Style Id</div>
        <select class="field" name="menuStyleId" id="menuStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_menuStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_menuStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_menu_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Menu Link Style Id</div>
        <select class="field" name="menuLinkStyleId" id="menuLinkStyleId">
        <option value="" >- Please Select -</option>
<%
	_listLinkStyleConfig_menuLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_menuLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_menu_link_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Header Menu Style Id</div>
        <select class="field" name="headerMenuStyleId" id="headerMenuStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_headerMenuStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_headerMenuStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_header_menu_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Header Menu Link Style Id</div>
        <select class="field" name="headerMenuLinkStyleId" id="headerMenuLinkStyleId">
        <option value="" >- Please Select -</option>
<%
	_listLinkStyleConfig_headerMenuLinkStyleId = LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listLinkStyleConfig_headerMenuLinkStyleId.iterator(); iter.hasNext();){
		LinkStyleConfig _obj = (LinkStyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_header_menu_link_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Frame Style Id</div>
        <select class="field" name="listFrameStyleId" id="listFrameStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_listFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Subject Style Id</div>
        <select class="field" name="listSubjectStyleId" id="listSubjectStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_listSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Data Style Id</div>
        <select class="field" name="listDataStyleId" id="listDataStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_listDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_listDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_list_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Subject Style Id</div>
        <select class="field" name="subjectStyleId" id="subjectStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_subjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_subjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Data Style Id</div>
        <select class="field" name="dataStyleId" id="dataStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_dataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_dataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Single Frame Style Id</div>
        <select class="field" name="singleFrameStyleId" id="singleFrameStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_singleFrameStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleFrameStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_single_frame_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Single Subject Style Id</div>
        <select class="field" name="singleSubjectStyleId" id="singleSubjectStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_singleSubjectStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleSubjectStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_single_subject_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Single Data Style Id</div>
        <select class="field" name="singleDataStyleId" id="singleDataStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_singleDataStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_singleDataStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_single_data_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Content Panel Style Id</div>
        <select class="field" name="contentPanelStyleId" id="contentPanelStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_contentPanelStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_contentPanelStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_panel_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Content Panel Title Style Id</div>
        <select class="field" name="contentPanelTitleStyleId" id="contentPanelTitleStyleId">
        <option value="" >- Please Select -</option>
<%
	_listStyleConfig_contentPanelTitleStyleId = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listStyleConfig_contentPanelTitleStyleId.iterator(); iter.hasNext();){
		StyleConfig _obj = (StyleConfig) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_content_panel_title_style_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getStyleKey() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Global</div>
        <select name="global" id="global">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _globalValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _globalValue)%>>Yes</option>
        </select><span></span>
		<br/>
		 <div class="ajaxFormLabel"> Disable</div>
        <select name="disable" id="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select><span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('styleThemeFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayStyleTheme"></span>
<a href="javascript:backToXX('styleThemeFormAddDis','resultDisplayStyleTheme')">show back</a><br>
<hr/>
