<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    StyleTheme _StyleTheme = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_StyleTheme = StyleThemeDS.getInstance().getById(id);
		if ( _StyleTheme == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _StyleTheme = new StyleTheme();// StyleThemeDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_theme_home";

    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_StyleTheme.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _body_widthValue= (reqParams.get("bodyWidth")==null?WebUtil.display(_StyleTheme.getBodyWidth()):WebUtil.display((String)reqParams.get("bodyWidth")));
    String _body_alignValue= (reqParams.get("bodyAlign")==null?WebUtil.display(_StyleTheme.getBodyAlign()):WebUtil.display((String)reqParams.get("bodyAlign")));
    String _body_bg_colorValue= (reqParams.get("bodyBgColor")==null?WebUtil.display(_StyleTheme.getBodyBgColor()):WebUtil.display((String)reqParams.get("bodyBgColor")));
    String _body_bg_imageValue= (reqParams.get("bodyBgImage")==null?WebUtil.display(_StyleTheme.getBodyBgImage()):WebUtil.display((String)reqParams.get("bodyBgImage")));
    String _body_bg_attachValue= (reqParams.get("bodyBgAttach")==null?WebUtil.display(_StyleTheme.getBodyBgAttach()):WebUtil.display((String)reqParams.get("bodyBgAttach")));
    String _body_bg_repeatValue= (reqParams.get("bodyBgRepeat")==null?WebUtil.display(_StyleTheme.getBodyBgRepeat()):WebUtil.display((String)reqParams.get("bodyBgRepeat")));
    String _body_bg_positionValue= (reqParams.get("bodyBgPosition")==null?WebUtil.display(_StyleTheme.getBodyBgPosition()):WebUtil.display((String)reqParams.get("bodyBgPosition")));
    String _content_bg_colorValue= (reqParams.get("contentBgColor")==null?WebUtil.display(_StyleTheme.getContentBgColor()):WebUtil.display((String)reqParams.get("contentBgColor")));
    String _use_absoluteValue= (reqParams.get("useAbsolute")==null?WebUtil.display(_StyleTheme.getUseAbsolute()):WebUtil.display((String)reqParams.get("useAbsolute")));
    String _absolute_topValue= (reqParams.get("absoluteTop")==null?WebUtil.display(_StyleTheme.getAbsoluteTop()):WebUtil.display((String)reqParams.get("absoluteTop")));
    String _absolute_leftValue= (reqParams.get("absoluteLeft")==null?WebUtil.display(_StyleTheme.getAbsoluteLeft()):WebUtil.display((String)reqParams.get("absoluteLeft")));
    String _absolute_rightValue= (reqParams.get("absoluteRight")==null?WebUtil.display(_StyleTheme.getAbsoluteRight()):WebUtil.display((String)reqParams.get("absoluteRight")));
    String _absolute_bottomValue= (reqParams.get("absoluteBottom")==null?WebUtil.display(_StyleTheme.getAbsoluteBottom()):WebUtil.display((String)reqParams.get("absoluteBottom")));
    String _panel_style_idValue= (reqParams.get("panelStyleId")==null?WebUtil.display(_StyleTheme.getPanelStyleId()):WebUtil.display((String)reqParams.get("panelStyleId")));
    String _panel_data_style_idValue= (reqParams.get("panelDataStyleId")==null?WebUtil.display(_StyleTheme.getPanelDataStyleId()):WebUtil.display((String)reqParams.get("panelDataStyleId")));
    String _panel_link_style_idValue= (reqParams.get("panelLinkStyleId")==null?WebUtil.display(_StyleTheme.getPanelLinkStyleId()):WebUtil.display((String)reqParams.get("panelLinkStyleId")));
    String _panel_title_style_idValue= (reqParams.get("panelTitleStyleId")==null?WebUtil.display(_StyleTheme.getPanelTitleStyleId()):WebUtil.display((String)reqParams.get("panelTitleStyleId")));
    String _menu_style_idValue= (reqParams.get("menuStyleId")==null?WebUtil.display(_StyleTheme.getMenuStyleId()):WebUtil.display((String)reqParams.get("menuStyleId")));
    String _menu_link_style_idValue= (reqParams.get("menuLinkStyleId")==null?WebUtil.display(_StyleTheme.getMenuLinkStyleId()):WebUtil.display((String)reqParams.get("menuLinkStyleId")));
    String _header_menu_style_idValue= (reqParams.get("headerMenuStyleId")==null?WebUtil.display(_StyleTheme.getHeaderMenuStyleId()):WebUtil.display((String)reqParams.get("headerMenuStyleId")));
    String _header_menu_link_style_idValue= (reqParams.get("headerMenuLinkStyleId")==null?WebUtil.display(_StyleTheme.getHeaderMenuLinkStyleId()):WebUtil.display((String)reqParams.get("headerMenuLinkStyleId")));
    String _list_frame_style_idValue= (reqParams.get("listFrameStyleId")==null?WebUtil.display(_StyleTheme.getListFrameStyleId()):WebUtil.display((String)reqParams.get("listFrameStyleId")));
    String _list_subject_style_idValue= (reqParams.get("listSubjectStyleId")==null?WebUtil.display(_StyleTheme.getListSubjectStyleId()):WebUtil.display((String)reqParams.get("listSubjectStyleId")));
    String _list_data_style_idValue= (reqParams.get("listDataStyleId")==null?WebUtil.display(_StyleTheme.getListDataStyleId()):WebUtil.display((String)reqParams.get("listDataStyleId")));
    String _subject_style_idValue= (reqParams.get("subjectStyleId")==null?WebUtil.display(_StyleTheme.getSubjectStyleId()):WebUtil.display((String)reqParams.get("subjectStyleId")));
    String _data_style_idValue= (reqParams.get("dataStyleId")==null?WebUtil.display(_StyleTheme.getDataStyleId()):WebUtil.display((String)reqParams.get("dataStyleId")));
    String _single_frame_style_idValue= (reqParams.get("singleFrameStyleId")==null?WebUtil.display(_StyleTheme.getSingleFrameStyleId()):WebUtil.display((String)reqParams.get("singleFrameStyleId")));
    String _single_subject_style_idValue= (reqParams.get("singleSubjectStyleId")==null?WebUtil.display(_StyleTheme.getSingleSubjectStyleId()):WebUtil.display((String)reqParams.get("singleSubjectStyleId")));
    String _single_data_style_idValue= (reqParams.get("singleDataStyleId")==null?WebUtil.display(_StyleTheme.getSingleDataStyleId()):WebUtil.display((String)reqParams.get("singleDataStyleId")));
    String _content_panel_style_idValue= (reqParams.get("contentPanelStyleId")==null?WebUtil.display(_StyleTheme.getContentPanelStyleId()):WebUtil.display((String)reqParams.get("contentPanelStyleId")));
    String _content_panel_title_style_idValue= (reqParams.get("contentPanelTitleStyleId")==null?WebUtil.display(_StyleTheme.getContentPanelTitleStyleId()):WebUtil.display((String)reqParams.get("contentPanelTitleStyleId")));
    String _globalValue= (reqParams.get("global")==null?WebUtil.display(_StyleTheme.getGlobal()):WebUtil.display((String)reqParams.get("global")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_StyleTheme.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleTheme.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleTheme.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="styleThemeForm" class="formFrame">
<div id="styleThemeFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleThemeForm_Form" method="POST" action="/styleThemeAction.html" id="styleThemeForm_Form">





	<div id="styleThemeForm_title_field" class="formFieldFrame">
    <div id="styleThemeForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="styleThemeForm_title_text" class="formFieldText" >       
        <input id="title" class="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyWidth_field" class="formFieldFrame">
    <div id="styleThemeForm_bodyWidth_label" class="formLabel" >Body Width </div>
    <div id="styleThemeForm_bodyWidth_text" class="formFieldText" >       
        <input id="bodyWidth" class="field" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleThemeForm_bodyAlign_field" class="formFieldFrame">
    <div id="styleThemeForm_bodyAlign_label" class="formLabel" >Body Align </div>
    <div id="styleThemeForm_bodyAlign_dropdown" class="formFieldDropDown" >       
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
    </div>      
	</div><div class="clear"></div>





	<div id="styleThemeForm_bodyBgColor_field" class="formFieldFrame">
    <div id="styleThemeForm_bodyBgColor_label" class="formLabel" >Body Bg Color </div>
    <div id="styleThemeForm_bodyBgColor_text" class="formFieldText" >       
        <input id="bodyBgColor" class="field" type="text" size="70" name="bodyBgColor" value="<%=WebUtil.display(_body_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgImage_field" class="formFieldFrame">
    <div id="styleThemeForm_bodyBgImage_label" class="formLabel" >Body Bg Image </div>
    <div id="styleThemeForm_bodyBgImage_text" class="formFieldText" >       
        <input id="bodyBgImage" class="field" type="text" size="70" name="bodyBgImage" value="<%=WebUtil.display(_body_bg_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgAttach_field" class="formFieldFrame">
    <div id="styleThemeForm_bodyBgAttach_label" class="formLabel" >Body Bg Attach </div>
    <div id="styleThemeForm_bodyBgAttach_text" class="formFieldText" >       
        <input id="bodyBgAttach" class="field" type="text" size="70" name="bodyBgAttach" value="<%=WebUtil.display(_body_bg_attachValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgRepeat_field" class="formFieldFrame">
    <div id="styleThemeForm_bodyBgRepeat_label" class="formLabel" >Body Bg Repeat </div>
    <div id="styleThemeForm_bodyBgRepeat_text" class="formFieldText" >       
        <input id="bodyBgRepeat" class="field" type="text" size="70" name="bodyBgRepeat" value="<%=WebUtil.display(_body_bg_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgPosition_field" class="formFieldFrame">
    <div id="styleThemeForm_bodyBgPosition_label" class="formLabel" >Body Bg Position </div>
    <div id="styleThemeForm_bodyBgPosition_text" class="formFieldText" >       
        <input id="bodyBgPosition" class="field" type="text" size="70" name="bodyBgPosition" value="<%=WebUtil.display(_body_bg_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_contentBgColor_field" class="formFieldFrame">
    <div id="styleThemeForm_contentBgColor_label" class="formLabel" >Content Bg Color </div>
    <div id="styleThemeForm_contentBgColor_text" class="formFieldText" >       
        <input id="contentBgColor" class="field" type="text" size="70" name="contentBgColor" value="<%=WebUtil.display(_content_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_useAbsolute_field" class="formFieldFrame">
    <div id="styleThemeForm_useAbsolute_label" class="formLabel" >Use Absolute </div>
    <div id="styleThemeForm_useAbsolute_dropdown" class="formFieldDropDown" >       
        <select name="useAbsolute">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _use_absoluteValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _use_absoluteValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="styleThemeForm_absoluteTop_field" class="formFieldFrame">
    <div id="styleThemeForm_absoluteTop_label" class="formLabel" >Absolute Top </div>
    <div id="styleThemeForm_absoluteTop_text" class="formFieldText" >       
        <input id="absoluteTop" class="field" type="text" size="70" name="absoluteTop" value="<%=WebUtil.display(_absolute_topValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_absoluteLeft_field" class="formFieldFrame">
    <div id="styleThemeForm_absoluteLeft_label" class="formLabel" >Absolute Left </div>
    <div id="styleThemeForm_absoluteLeft_text" class="formFieldText" >       
        <input id="absoluteLeft" class="field" type="text" size="70" name="absoluteLeft" value="<%=WebUtil.display(_absolute_leftValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_absoluteRight_field" class="formFieldFrame">
    <div id="styleThemeForm_absoluteRight_label" class="formLabel" >Absolute Right </div>
    <div id="styleThemeForm_absoluteRight_text" class="formFieldText" >       
        <input id="absoluteRight" class="field" type="text" size="70" name="absoluteRight" value="<%=WebUtil.display(_absolute_rightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_absoluteBottom_field" class="formFieldFrame">
    <div id="styleThemeForm_absoluteBottom_label" class="formLabel" >Absolute Bottom </div>
    <div id="styleThemeForm_absoluteBottom_text" class="formFieldText" >       
        <input id="absoluteBottom" class="field" type="text" size="70" name="absoluteBottom" value="<%=WebUtil.display(_absolute_bottomValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleThemeForm_panelStyleId_field" class="formFieldFrame">
    <div id="styleThemeForm_panelStyleId_label" class="formLabel" >Panel Style Id </div>
    <div id="styleThemeForm_panelStyleId_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="panelStyleId" id="panelStyleId">
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
        <select class="requiredField" name="panelDataStyleId" id="panelDataStyleId">
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
        <select class="requiredField" name="panelLinkStyleId" id="panelLinkStyleId">
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
        <select class="field" name="panelTitleStyleId" id="panelTitleStyleId">
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
        <select class="field" name="menuStyleId" id="menuStyleId">
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
        <select class="field" name="menuLinkStyleId" id="menuLinkStyleId">
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
        <select class="field" name="headerMenuStyleId" id="headerMenuStyleId">
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
        <select class="field" name="headerMenuLinkStyleId" id="headerMenuLinkStyleId">
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
        <select class="field" name="listFrameStyleId" id="listFrameStyleId">
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
        <select class="field" name="listSubjectStyleId" id="listSubjectStyleId">
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
        <select class="field" name="listDataStyleId" id="listDataStyleId">
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
        <select class="field" name="subjectStyleId" id="subjectStyleId">
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
        <select class="field" name="dataStyleId" id="dataStyleId">
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
        <select class="field" name="singleFrameStyleId" id="singleFrameStyleId">
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
        <select class="field" name="singleSubjectStyleId" id="singleSubjectStyleId">
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
        <select class="field" name="singleDataStyleId" id="singleDataStyleId">
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
        <select class="field" name="contentPanelStyleId" id="contentPanelStyleId">
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
        <select class="field" name="contentPanelTitleStyleId" id="contentPanelTitleStyleId">
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



	<div id="styleThemeForm_global_field" class="formFieldFrame">
    <div id="styleThemeForm_global_label" class="formLabel" >Global </div>
    <div id="styleThemeForm_global_dropdown" class="formFieldDropDown" >       
        <select name="global">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _globalValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _globalValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_disable_field" class="formFieldFrame">
    <div id="styleThemeForm_disable_label" class="formLabel" >Disable </div>
    <div id="styleThemeForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>








	<div class="submitFrame">

        <div id="styleThemeForm_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.styleThemeForm_Form.submit();">Submit</a>
        </div>      

        <div id="styleThemeForm_submit_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="styleThemeForm_submit_ext" class="formSubmitExt" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Body Width </td> 
    <td class="columnTitle">  Body Align </td> 
    <td class="columnTitle">  Body Bg Color </td> 
    <td class="columnTitle">  Body Bg Image </td> 
    <td class="columnTitle">  Body Bg Attach </td> 
    <td class="columnTitle">  Body Bg Repeat </td> 
    <td class="columnTitle">  Body Bg Position </td> 
    <td class="columnTitle">  Content Bg Color </td> 
    <td class="columnTitle">  Use Absolute </td> 
    <td class="columnTitle">  Absolute Top </td> 
    <td class="columnTitle">  Absolute Left </td> 
    <td class="columnTitle">  Absolute Right </td> 
    <td class="columnTitle">  Absolute Bottom </td> 
    <td class="columnTitle">  Panel Style Id </td> 
    <td class="columnTitle">  Panel Data Style Id </td> 
    <td class="columnTitle">  Panel Link Style Id </td> 
    <td class="columnTitle">  Panel Title Style Id </td> 
    <td class="columnTitle">  Menu Style Id </td> 
    <td class="columnTitle">  Menu Link Style Id </td> 
    <td class="columnTitle">  Header Menu Style Id </td> 
    <td class="columnTitle">  Header Menu Link Style Id </td> 
    <td class="columnTitle">  List Frame Style Id </td> 
    <td class="columnTitle">  List Subject Style Id </td> 
    <td class="columnTitle">  List Data Style Id </td> 
    <td class="columnTitle">  Subject Style Id </td> 
    <td class="columnTitle">  Data Style Id </td> 
    <td class="columnTitle">  Single Frame Style Id </td> 
    <td class="columnTitle">  Single Subject Style Id </td> 
    <td class="columnTitle">  Single Data Style Id </td> 
    <td class="columnTitle">  Content Panel Style Id </td> 
    <td class="columnTitle">  Content Panel Title Style Id </td> 
    <td class="columnTitle">  Global </td> 
    <td class="columnTitle">  Disable </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = StyleThemeDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleTheme _oStyleTheme = (StyleTheme) iter.next();
%>

<TR>
    <td> <%= _oStyleTheme.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oStyleTheme.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oStyleTheme.getTitle()  %> </td>
	<td> <%= _oStyleTheme.getBodyWidth()  %> </td>
	<td> <%= _oStyleTheme.getBodyAlign()  %> </td>
	<td> <%= _oStyleTheme.getBodyBgColor()  %> </td>
	<td> <%= _oStyleTheme.getBodyBgImage()  %> </td>
	<td> <%= _oStyleTheme.getBodyBgAttach()  %> </td>
	<td> <%= _oStyleTheme.getBodyBgRepeat()  %> </td>
	<td> <%= _oStyleTheme.getBodyBgPosition()  %> </td>
	<td> <%= _oStyleTheme.getContentBgColor()  %> </td>
	<td> <%= _oStyleTheme.getUseAbsolute()  %> </td>
	<td> <%= _oStyleTheme.getAbsoluteTop()  %> </td>
	<td> <%= _oStyleTheme.getAbsoluteLeft()  %> </td>
	<td> <%= _oStyleTheme.getAbsoluteRight()  %> </td>
	<td> <%= _oStyleTheme.getAbsoluteBottom()  %> </td>
	<td> <%= _oStyleTheme.getPanelStyleId()  %> </td>
	<td> <%= _oStyleTheme.getPanelDataStyleId()  %> </td>
	<td> <%= _oStyleTheme.getPanelLinkStyleId()  %> </td>
	<td> <%= _oStyleTheme.getPanelTitleStyleId()  %> </td>
	<td> <%= _oStyleTheme.getMenuStyleId()  %> </td>
	<td> <%= _oStyleTheme.getMenuLinkStyleId()  %> </td>
	<td> <%= _oStyleTheme.getHeaderMenuStyleId()  %> </td>
	<td> <%= _oStyleTheme.getHeaderMenuLinkStyleId()  %> </td>
	<td> <%= _oStyleTheme.getListFrameStyleId()  %> </td>
	<td> <%= _oStyleTheme.getListSubjectStyleId()  %> </td>
	<td> <%= _oStyleTheme.getListDataStyleId()  %> </td>
	<td> <%= _oStyleTheme.getSubjectStyleId()  %> </td>
	<td> <%= _oStyleTheme.getDataStyleId()  %> </td>
	<td> <%= _oStyleTheme.getSingleFrameStyleId()  %> </td>
	<td> <%= _oStyleTheme.getSingleSubjectStyleId()  %> </td>
	<td> <%= _oStyleTheme.getSingleDataStyleId()  %> </td>
	<td> <%= _oStyleTheme.getContentPanelStyleId()  %> </td>
	<td> <%= _oStyleTheme.getContentPanelTitleStyleId()  %> </td>
	<td> <%= _oStyleTheme.getGlobal()  %> </td>
	<td> <%= _oStyleTheme.getDisable()  %> </td>
	<td> <%= _oStyleTheme.getTimeCreated()  %> </td>
	<td> <%= _oStyleTheme.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/styleThemeAction.html?del=true&id=<%=_oStyleTheme.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
</script>