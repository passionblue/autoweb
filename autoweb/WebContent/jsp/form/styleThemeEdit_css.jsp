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

    StyleTheme _StyleTheme = StyleThemeDS.getInstance().getById(id);

    if ( _StyleTheme == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_theme_home";

    String _titleValue=  WebUtil.display(_StyleTheme.getTitle());
    String _body_widthValue=  WebUtil.display(_StyleTheme.getBodyWidth());
    String _body_alignValue=  WebUtil.display(_StyleTheme.getBodyAlign());
    String _body_bg_colorValue=  WebUtil.display(_StyleTheme.getBodyBgColor());
    String _body_bg_imageValue=  WebUtil.display(_StyleTheme.getBodyBgImage());
    String _body_bg_attachValue=  WebUtil.display(_StyleTheme.getBodyBgAttach());
    String _body_bg_repeatValue=  WebUtil.display(_StyleTheme.getBodyBgRepeat());
    String _body_bg_positionValue=  WebUtil.display(_StyleTheme.getBodyBgPosition());
    String _content_bg_colorValue=  WebUtil.display(_StyleTheme.getContentBgColor());
    String _use_absoluteValue=  WebUtil.display(_StyleTheme.getUseAbsolute());
    String _absolute_topValue=  WebUtil.display(_StyleTheme.getAbsoluteTop());
    String _absolute_leftValue=  WebUtil.display(_StyleTheme.getAbsoluteLeft());
    String _absolute_rightValue=  WebUtil.display(_StyleTheme.getAbsoluteRight());
    String _absolute_bottomValue=  WebUtil.display(_StyleTheme.getAbsoluteBottom());
    String _panel_style_idValue=  WebUtil.display(_StyleTheme.getPanelStyleId());
    String _panel_data_style_idValue=  WebUtil.display(_StyleTheme.getPanelDataStyleId());
    String _panel_link_style_idValue=  WebUtil.display(_StyleTheme.getPanelLinkStyleId());
    String _panel_title_style_idValue=  WebUtil.display(_StyleTheme.getPanelTitleStyleId());
    String _menu_style_idValue=  WebUtil.display(_StyleTheme.getMenuStyleId());
    String _menu_link_style_idValue=  WebUtil.display(_StyleTheme.getMenuLinkStyleId());
    String _header_menu_style_idValue=  WebUtil.display(_StyleTheme.getHeaderMenuStyleId());
    String _header_menu_link_style_idValue=  WebUtil.display(_StyleTheme.getHeaderMenuLinkStyleId());
    String _list_frame_style_idValue=  WebUtil.display(_StyleTheme.getListFrameStyleId());
    String _list_subject_style_idValue=  WebUtil.display(_StyleTheme.getListSubjectStyleId());
    String _list_data_style_idValue=  WebUtil.display(_StyleTheme.getListDataStyleId());
    String _subject_style_idValue=  WebUtil.display(_StyleTheme.getSubjectStyleId());
    String _data_style_idValue=  WebUtil.display(_StyleTheme.getDataStyleId());
    String _single_frame_style_idValue=  WebUtil.display(_StyleTheme.getSingleFrameStyleId());
    String _single_subject_style_idValue=  WebUtil.display(_StyleTheme.getSingleSubjectStyleId());
    String _single_data_style_idValue=  WebUtil.display(_StyleTheme.getSingleDataStyleId());
    String _content_panel_style_idValue=  WebUtil.display(_StyleTheme.getContentPanelStyleId());
    String _content_panel_title_style_idValue=  WebUtil.display(_StyleTheme.getContentPanelTitleStyleId());
    String _globalValue=  WebUtil.display(_StyleTheme.getGlobal());
    String _disableValue=  WebUtil.display(_StyleTheme.getDisable());
    String _time_createdValue=  WebUtil.display(_StyleTheme.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_StyleTheme.getTimeUpdated());
%> 

<br>
<div id="styleThemeForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleThemeFormEdit" method="POST" action="/styleThemeAction.html" >




	<div id="styleThemeForm_title_field">
    <div id="styleThemeForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="styleThemeForm_title_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_bodyWidth_field">
    <div id="styleThemeForm_bodyWidth_label" class="formLabel" >Body Width </div>
    <div id="styleThemeForm_bodyWidth_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="styleThemeForm_bodyAlign_field">
    <div id="styleThemeForm_bodyAlign_label" class="formLabel" >Body Align </div>
    <div id="styleThemeForm_bodyAlign_dropdown" class="formFieldDropDown" >       
        <select id="field" name="bodyAlign">
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
        <input id="field" type="text" size="70" name="bodyBgColor" value="<%=WebUtil.display(_body_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_bodyBgImage_field">
    <div id="styleThemeForm_bodyBgImage_label" class="formLabel" >Body Bg Image </div>
    <div id="styleThemeForm_bodyBgImage_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bodyBgImage" value="<%=WebUtil.display(_body_bg_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_bodyBgAttach_field">
    <div id="styleThemeForm_bodyBgAttach_label" class="formLabel" >Body Bg Attach </div>
    <div id="styleThemeForm_bodyBgAttach_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bodyBgAttach" value="<%=WebUtil.display(_body_bg_attachValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_bodyBgRepeat_field">
    <div id="styleThemeForm_bodyBgRepeat_label" class="formLabel" >Body Bg Repeat </div>
    <div id="styleThemeForm_bodyBgRepeat_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bodyBgRepeat" value="<%=WebUtil.display(_body_bg_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_bodyBgPosition_field">
    <div id="styleThemeForm_bodyBgPosition_label" class="formLabel" >Body Bg Position </div>
    <div id="styleThemeForm_bodyBgPosition_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="bodyBgPosition" value="<%=WebUtil.display(_body_bg_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_contentBgColor_field">
    <div id="styleThemeForm_contentBgColor_label" class="formLabel" >Content Bg Color </div>
    <div id="styleThemeForm_contentBgColor_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="contentBgColor" value="<%=WebUtil.display(_content_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleThemeForm_useAbsolute_field">
    <div id="styleThemeForm_useAbsolute_label" class="formLabel" >Use Absolute </div>
    <div id="styleThemeForm_useAbsolute_dropdown" class="formFieldDropDown" >       
        <select name="useAbsolute">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _use_absoluteValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _use_absoluteValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="styleThemeForm_absoluteTop_field">
    <div id="styleThemeForm_absoluteTop_label" class="formLabel" >Absolute Top </div>
    <div id="styleThemeForm_absoluteTop_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="absoluteTop" value="<%=WebUtil.display(_absolute_topValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_absoluteLeft_field">
    <div id="styleThemeForm_absoluteLeft_label" class="formLabel" >Absolute Left </div>
    <div id="styleThemeForm_absoluteLeft_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="absoluteLeft" value="<%=WebUtil.display(_absolute_leftValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_absoluteRight_field">
    <div id="styleThemeForm_absoluteRight_label" class="formLabel" >Absolute Right </div>
    <div id="styleThemeForm_absoluteRight_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="absoluteRight" value="<%=WebUtil.display(_absolute_rightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_absoluteBottom_field">
    <div id="styleThemeForm_absoluteBottom_label" class="formLabel" >Absolute Bottom </div>
    <div id="styleThemeForm_absoluteBottom_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="absoluteBottom" value="<%=WebUtil.display(_absolute_bottomValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_panelStyleId_field">
    <div id="styleThemeForm_panelStyleId_label" class="formRequiredLabel" >Panel Style Id* </div>
    <div id="styleThemeForm_panelStyleId_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="panelStyleId" value="<%=WebUtil.display(_panel_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_panelDataStyleId_field">
    <div id="styleThemeForm_panelDataStyleId_label" class="formRequiredLabel" >Panel Data Style Id* </div>
    <div id="styleThemeForm_panelDataStyleId_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="panelDataStyleId" value="<%=WebUtil.display(_panel_data_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_panelLinkStyleId_field">
    <div id="styleThemeForm_panelLinkStyleId_label" class="formRequiredLabel" >Panel Link Style Id* </div>
    <div id="styleThemeForm_panelLinkStyleId_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="panelLinkStyleId" value="<%=WebUtil.display(_panel_link_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_panelTitleStyleId_field">
    <div id="styleThemeForm_panelTitleStyleId_label" class="formLabel" >Panel Title Style Id </div>
    <div id="styleThemeForm_panelTitleStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="panelTitleStyleId" value="<%=WebUtil.display(_panel_title_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_menuStyleId_field">
    <div id="styleThemeForm_menuStyleId_label" class="formLabel" >Menu Style Id </div>
    <div id="styleThemeForm_menuStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="menuStyleId" value="<%=WebUtil.display(_menu_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_menuLinkStyleId_field">
    <div id="styleThemeForm_menuLinkStyleId_label" class="formLabel" >Menu Link Style Id </div>
    <div id="styleThemeForm_menuLinkStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="menuLinkStyleId" value="<%=WebUtil.display(_menu_link_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_headerMenuStyleId_field">
    <div id="styleThemeForm_headerMenuStyleId_label" class="formLabel" >Header Menu Style Id </div>
    <div id="styleThemeForm_headerMenuStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="headerMenuStyleId" value="<%=WebUtil.display(_header_menu_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_headerMenuLinkStyleId_field">
    <div id="styleThemeForm_headerMenuLinkStyleId_label" class="formLabel" >Header Menu Link Style Id </div>
    <div id="styleThemeForm_headerMenuLinkStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="headerMenuLinkStyleId" value="<%=WebUtil.display(_header_menu_link_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_listFrameStyleId_field">
    <div id="styleThemeForm_listFrameStyleId_label" class="formLabel" >List Frame Style Id </div>
    <div id="styleThemeForm_listFrameStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listFrameStyleId" value="<%=WebUtil.display(_list_frame_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_listSubjectStyleId_field">
    <div id="styleThemeForm_listSubjectStyleId_label" class="formLabel" >List Subject Style Id </div>
    <div id="styleThemeForm_listSubjectStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listSubjectStyleId" value="<%=WebUtil.display(_list_subject_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_listDataStyleId_field">
    <div id="styleThemeForm_listDataStyleId_label" class="formLabel" >List Data Style Id </div>
    <div id="styleThemeForm_listDataStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="listDataStyleId" value="<%=WebUtil.display(_list_data_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_subjectStyleId_field">
    <div id="styleThemeForm_subjectStyleId_label" class="formLabel" >Subject Style Id </div>
    <div id="styleThemeForm_subjectStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="subjectStyleId" value="<%=WebUtil.display(_subject_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_dataStyleId_field">
    <div id="styleThemeForm_dataStyleId_label" class="formLabel" >Data Style Id </div>
    <div id="styleThemeForm_dataStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="dataStyleId" value="<%=WebUtil.display(_data_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_singleFrameStyleId_field">
    <div id="styleThemeForm_singleFrameStyleId_label" class="formLabel" >Single Frame Style Id </div>
    <div id="styleThemeForm_singleFrameStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="singleFrameStyleId" value="<%=WebUtil.display(_single_frame_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_singleSubjectStyleId_field">
    <div id="styleThemeForm_singleSubjectStyleId_label" class="formLabel" >Single Subject Style Id </div>
    <div id="styleThemeForm_singleSubjectStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="singleSubjectStyleId" value="<%=WebUtil.display(_single_subject_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_singleDataStyleId_field">
    <div id="styleThemeForm_singleDataStyleId_label" class="formLabel" >Single Data Style Id </div>
    <div id="styleThemeForm_singleDataStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="singleDataStyleId" value="<%=WebUtil.display(_single_data_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_contentPanelStyleId_field">
    <div id="styleThemeForm_contentPanelStyleId_label" class="formLabel" >Content Panel Style Id </div>
    <div id="styleThemeForm_contentPanelStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="contentPanelStyleId" value="<%=WebUtil.display(_content_panel_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleThemeForm_contentPanelTitleStyleId_field">
    <div id="styleThemeForm_contentPanelTitleStyleId_label" class="formLabel" >Content Panel Title Style Id </div>
    <div id="styleThemeForm_contentPanelTitleStyleId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="contentPanelTitleStyleId" value="<%=WebUtil.display(_content_panel_title_style_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleThemeForm_global_field">
    <div id="styleThemeForm_global_label" class="formLabel" >Global </div>
    <div id="styleThemeForm_global_dropdown" class="formFieldDropDown" >       
        <select name="global">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _globalValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _globalValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_disable_field">
    <div id="styleThemeForm_disable_label" class="formLabel" >Disable </div>
    <div id="styleThemeForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>







        <div id="styleThemeFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleThemeFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleTheme.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
