<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    StyleTheme _StyleThemeDefault = new StyleTheme();// StyleThemeDS.getInstance().getDeafult();
    
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_StyleThemeDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _body_widthValue= (reqParams.get("bodyWidth")==null?WebUtil.display(_StyleThemeDefault.getBodyWidth()):WebUtil.display((String)reqParams.get("bodyWidth")));
    String _body_alignValue= (reqParams.get("bodyAlign")==null?WebUtil.display(_StyleThemeDefault.getBodyAlign()):WebUtil.display((String)reqParams.get("bodyAlign")));
    String _body_bg_colorValue= (reqParams.get("bodyBgColor")==null?WebUtil.display(_StyleThemeDefault.getBodyBgColor()):WebUtil.display((String)reqParams.get("bodyBgColor")));
    String _body_bg_imageValue= (reqParams.get("bodyBgImage")==null?WebUtil.display(_StyleThemeDefault.getBodyBgImage()):WebUtil.display((String)reqParams.get("bodyBgImage")));
    String _body_bg_attachValue= (reqParams.get("bodyBgAttach")==null?WebUtil.display(_StyleThemeDefault.getBodyBgAttach()):WebUtil.display((String)reqParams.get("bodyBgAttach")));
    String _body_bg_repeatValue= (reqParams.get("bodyBgRepeat")==null?WebUtil.display(_StyleThemeDefault.getBodyBgRepeat()):WebUtil.display((String)reqParams.get("bodyBgRepeat")));
    String _body_bg_positionValue= (reqParams.get("bodyBgPosition")==null?WebUtil.display(_StyleThemeDefault.getBodyBgPosition()):WebUtil.display((String)reqParams.get("bodyBgPosition")));
    String _content_bg_colorValue= (reqParams.get("contentBgColor")==null?WebUtil.display(_StyleThemeDefault.getContentBgColor()):WebUtil.display((String)reqParams.get("contentBgColor")));
    String _use_absoluteValue= (reqParams.get("useAbsolute")==null?WebUtil.display(_StyleThemeDefault.getUseAbsolute()):WebUtil.display((String)reqParams.get("useAbsolute")));
    String _absolute_topValue= (reqParams.get("absoluteTop")==null?WebUtil.display(_StyleThemeDefault.getAbsoluteTop()):WebUtil.display((String)reqParams.get("absoluteTop")));
    String _absolute_leftValue= (reqParams.get("absoluteLeft")==null?WebUtil.display(_StyleThemeDefault.getAbsoluteLeft()):WebUtil.display((String)reqParams.get("absoluteLeft")));
    String _absolute_rightValue= (reqParams.get("absoluteRight")==null?WebUtil.display(_StyleThemeDefault.getAbsoluteRight()):WebUtil.display((String)reqParams.get("absoluteRight")));
    String _absolute_bottomValue= (reqParams.get("absoluteBottom")==null?WebUtil.display(_StyleThemeDefault.getAbsoluteBottom()):WebUtil.display((String)reqParams.get("absoluteBottom")));
    String _panel_style_idValue= (reqParams.get("panelStyleId")==null?WebUtil.display(_StyleThemeDefault.getPanelStyleId()):WebUtil.display((String)reqParams.get("panelStyleId")));
    String _panel_data_style_idValue= (reqParams.get("panelDataStyleId")==null?WebUtil.display(_StyleThemeDefault.getPanelDataStyleId()):WebUtil.display((String)reqParams.get("panelDataStyleId")));
    String _panel_link_style_idValue= (reqParams.get("panelLinkStyleId")==null?WebUtil.display(_StyleThemeDefault.getPanelLinkStyleId()):WebUtil.display((String)reqParams.get("panelLinkStyleId")));
    String _panel_title_style_idValue= (reqParams.get("panelTitleStyleId")==null?WebUtil.display(_StyleThemeDefault.getPanelTitleStyleId()):WebUtil.display((String)reqParams.get("panelTitleStyleId")));
    String _menu_style_idValue= (reqParams.get("menuStyleId")==null?WebUtil.display(_StyleThemeDefault.getMenuStyleId()):WebUtil.display((String)reqParams.get("menuStyleId")));
    String _menu_link_style_idValue= (reqParams.get("menuLinkStyleId")==null?WebUtil.display(_StyleThemeDefault.getMenuLinkStyleId()):WebUtil.display((String)reqParams.get("menuLinkStyleId")));
    String _header_menu_style_idValue= (reqParams.get("headerMenuStyleId")==null?WebUtil.display(_StyleThemeDefault.getHeaderMenuStyleId()):WebUtil.display((String)reqParams.get("headerMenuStyleId")));
    String _header_menu_link_style_idValue= (reqParams.get("headerMenuLinkStyleId")==null?WebUtil.display(_StyleThemeDefault.getHeaderMenuLinkStyleId()):WebUtil.display((String)reqParams.get("headerMenuLinkStyleId")));
    String _list_frame_style_idValue= (reqParams.get("listFrameStyleId")==null?WebUtil.display(_StyleThemeDefault.getListFrameStyleId()):WebUtil.display((String)reqParams.get("listFrameStyleId")));
    String _list_subject_style_idValue= (reqParams.get("listSubjectStyleId")==null?WebUtil.display(_StyleThemeDefault.getListSubjectStyleId()):WebUtil.display((String)reqParams.get("listSubjectStyleId")));
    String _list_data_style_idValue= (reqParams.get("listDataStyleId")==null?WebUtil.display(_StyleThemeDefault.getListDataStyleId()):WebUtil.display((String)reqParams.get("listDataStyleId")));
    String _subject_style_idValue= (reqParams.get("subjectStyleId")==null?WebUtil.display(_StyleThemeDefault.getSubjectStyleId()):WebUtil.display((String)reqParams.get("subjectStyleId")));
    String _data_style_idValue= (reqParams.get("dataStyleId")==null?WebUtil.display(_StyleThemeDefault.getDataStyleId()):WebUtil.display((String)reqParams.get("dataStyleId")));
    String _single_frame_style_idValue= (reqParams.get("singleFrameStyleId")==null?WebUtil.display(_StyleThemeDefault.getSingleFrameStyleId()):WebUtil.display((String)reqParams.get("singleFrameStyleId")));
    String _single_subject_style_idValue= (reqParams.get("singleSubjectStyleId")==null?WebUtil.display(_StyleThemeDefault.getSingleSubjectStyleId()):WebUtil.display((String)reqParams.get("singleSubjectStyleId")));
    String _single_data_style_idValue= (reqParams.get("singleDataStyleId")==null?WebUtil.display(_StyleThemeDefault.getSingleDataStyleId()):WebUtil.display((String)reqParams.get("singleDataStyleId")));
    String _content_panel_style_idValue= (reqParams.get("contentPanelStyleId")==null?WebUtil.display(_StyleThemeDefault.getContentPanelStyleId()):WebUtil.display((String)reqParams.get("contentPanelStyleId")));
    String _content_panel_title_style_idValue= (reqParams.get("contentPanelTitleStyleId")==null?WebUtil.display(_StyleThemeDefault.getContentPanelTitleStyleId()):WebUtil.display((String)reqParams.get("contentPanelTitleStyleId")));
    String _globalValue= (reqParams.get("global")==null?WebUtil.display(_StyleThemeDefault.getGlobal()):WebUtil.display((String)reqParams.get("global")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_StyleThemeDefault.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleThemeDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleThemeDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_theme_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="styleThemeForm_topArea" class="formTopArea"></div>
<div id="styleThemeForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="styleThemeForm" method="POST" action="/styleThemeAction.html" >




	<div id="styleThemeForm_title_field">
    <div id="styleThemeForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="styleThemeForm_title_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyWidth_field">
    <div id="styleThemeForm_bodyWidth_label" class="formLabel" >Body Width </div>
    <div id="styleThemeForm_bodyWidth_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bodyWidth" value="<%=WebUtil.display(_body_widthValue)%>"/>
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
    <div id="styleThemeForm_bodyBgColor_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bodyBgColor" value="<%=WebUtil.display(_body_bg_colorValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgImage_field">
    <div id="styleThemeForm_bodyBgImage_label" class="formLabel" >Body Bg Image </div>
    <div id="styleThemeForm_bodyBgImage_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bodyBgImage" value="<%=WebUtil.display(_body_bg_imageValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgAttach_field">
    <div id="styleThemeForm_bodyBgAttach_label" class="formLabel" >Body Bg Attach </div>
    <div id="styleThemeForm_bodyBgAttach_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bodyBgAttach" value="<%=WebUtil.display(_body_bg_attachValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgRepeat_field">
    <div id="styleThemeForm_bodyBgRepeat_label" class="formLabel" >Body Bg Repeat </div>
    <div id="styleThemeForm_bodyBgRepeat_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bodyBgRepeat" value="<%=WebUtil.display(_body_bg_repeatValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_bodyBgPosition_field">
    <div id="styleThemeForm_bodyBgPosition_label" class="formLabel" >Body Bg Position </div>
    <div id="styleThemeForm_bodyBgPosition_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="bodyBgPosition" value="<%=WebUtil.display(_body_bg_positionValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_contentBgColor_field">
    <div id="styleThemeForm_contentBgColor_label" class="formLabel" >Content Bg Color </div>
    <div id="styleThemeForm_contentBgColor_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentBgColor" value="<%=WebUtil.display(_content_bg_colorValue)%>"/>
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
    <div id="styleThemeForm_absoluteTop_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="absoluteTop" value="<%=WebUtil.display(_absolute_topValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_absoluteLeft_field">
    <div id="styleThemeForm_absoluteLeft_label" class="formLabel" >Absolute Left </div>
    <div id="styleThemeForm_absoluteLeft_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="absoluteLeft" value="<%=WebUtil.display(_absolute_leftValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_absoluteRight_field">
    <div id="styleThemeForm_absoluteRight_label" class="formLabel" >Absolute Right </div>
    <div id="styleThemeForm_absoluteRight_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="absoluteRight" value="<%=WebUtil.display(_absolute_rightValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_absoluteBottom_field">
    <div id="styleThemeForm_absoluteBottom_label" class="formLabel" >Absolute Bottom </div>
    <div id="styleThemeForm_absoluteBottom_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="absoluteBottom" value="<%=WebUtil.display(_absolute_bottomValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_panelStyleId_field">
    <div id="styleThemeForm_panelStyleId_label" class="formRequiredLabel" >Panel Style Id* </div>
    <div id="styleThemeForm_panelStyleId_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="panelStyleId" value="<%=WebUtil.display(_panel_style_idValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_panelDataStyleId_field">
    <div id="styleThemeForm_panelDataStyleId_label" class="formRequiredLabel" >Panel Data Style Id* </div>
    <div id="styleThemeForm_panelDataStyleId_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="panelDataStyleId" value="<%=WebUtil.display(_panel_data_style_idValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_panelLinkStyleId_field">
    <div id="styleThemeForm_panelLinkStyleId_label" class="formRequiredLabel" >Panel Link Style Id* </div>
    <div id="styleThemeForm_panelLinkStyleId_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="panelLinkStyleId" value="<%=WebUtil.display(_panel_link_style_idValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_panelTitleStyleId_field">
    <div id="styleThemeForm_panelTitleStyleId_label" class="formLabel" >Panel Title Style Id </div>
    <div id="styleThemeForm_panelTitleStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="panelTitleStyleId" value="<%=WebUtil.display(_panel_title_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_menuStyleId_field">
    <div id="styleThemeForm_menuStyleId_label" class="formLabel" >Menu Style Id </div>
    <div id="styleThemeForm_menuStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="menuStyleId" value="<%=WebUtil.display(_menu_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_menuLinkStyleId_field">
    <div id="styleThemeForm_menuLinkStyleId_label" class="formLabel" >Menu Link Style Id </div>
    <div id="styleThemeForm_menuLinkStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="menuLinkStyleId" value="<%=WebUtil.display(_menu_link_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_headerMenuStyleId_field">
    <div id="styleThemeForm_headerMenuStyleId_label" class="formLabel" >Header Menu Style Id </div>
    <div id="styleThemeForm_headerMenuStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="headerMenuStyleId" value="<%=WebUtil.display(_header_menu_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_headerMenuLinkStyleId_field">
    <div id="styleThemeForm_headerMenuLinkStyleId_label" class="formLabel" >Header Menu Link Style Id </div>
    <div id="styleThemeForm_headerMenuLinkStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="headerMenuLinkStyleId" value="<%=WebUtil.display(_header_menu_link_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_listFrameStyleId_field">
    <div id="styleThemeForm_listFrameStyleId_label" class="formLabel" >List Frame Style Id </div>
    <div id="styleThemeForm_listFrameStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listFrameStyleId" value="<%=WebUtil.display(_list_frame_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_listSubjectStyleId_field">
    <div id="styleThemeForm_listSubjectStyleId_label" class="formLabel" >List Subject Style Id </div>
    <div id="styleThemeForm_listSubjectStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listSubjectStyleId" value="<%=WebUtil.display(_list_subject_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_listDataStyleId_field">
    <div id="styleThemeForm_listDataStyleId_label" class="formLabel" >List Data Style Id </div>
    <div id="styleThemeForm_listDataStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="listDataStyleId" value="<%=WebUtil.display(_list_data_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_subjectStyleId_field">
    <div id="styleThemeForm_subjectStyleId_label" class="formLabel" >Subject Style Id </div>
    <div id="styleThemeForm_subjectStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="subjectStyleId" value="<%=WebUtil.display(_subject_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_dataStyleId_field">
    <div id="styleThemeForm_dataStyleId_label" class="formLabel" >Data Style Id </div>
    <div id="styleThemeForm_dataStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="dataStyleId" value="<%=WebUtil.display(_data_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_singleFrameStyleId_field">
    <div id="styleThemeForm_singleFrameStyleId_label" class="formLabel" >Single Frame Style Id </div>
    <div id="styleThemeForm_singleFrameStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="singleFrameStyleId" value="<%=WebUtil.display(_single_frame_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_singleSubjectStyleId_field">
    <div id="styleThemeForm_singleSubjectStyleId_label" class="formLabel" >Single Subject Style Id </div>
    <div id="styleThemeForm_singleSubjectStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="singleSubjectStyleId" value="<%=WebUtil.display(_single_subject_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_singleDataStyleId_field">
    <div id="styleThemeForm_singleDataStyleId_label" class="formLabel" >Single Data Style Id </div>
    <div id="styleThemeForm_singleDataStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="singleDataStyleId" value="<%=WebUtil.display(_single_data_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_contentPanelStyleId_field">
    <div id="styleThemeForm_contentPanelStyleId_label" class="formLabel" >Content Panel Style Id </div>
    <div id="styleThemeForm_contentPanelStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentPanelStyleId" value="<%=WebUtil.display(_content_panel_style_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="styleThemeForm_contentPanelTitleStyleId_field">
    <div id="styleThemeForm_contentPanelTitleStyleId_label" class="formLabel" >Content Panel Title Style Id </div>
    <div id="styleThemeForm_contentPanelTitleStyleId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentPanelTitleStyleId" value="<%=WebUtil.display(_content_panel_title_style_idValue)%>"/>
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










        <div id="styleThemeForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleThemeForm.submit();">Submit</a>
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
<div id="styleThemeForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = StyleThemeDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleTheme _StyleTheme = (StyleTheme) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleTheme.getId() %> </td>

    <td> <%= WebUtil.display(_StyleTheme.getTitle()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getBodyWidth()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getBodyAlign()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getBodyBgColor()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getBodyBgImage()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getBodyBgAttach()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getBodyBgRepeat()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getBodyBgPosition()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getContentBgColor()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getUseAbsolute()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getAbsoluteTop()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getAbsoluteLeft()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getAbsoluteRight()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getAbsoluteBottom()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getPanelStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getPanelDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getPanelLinkStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getPanelTitleStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getMenuStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getMenuLinkStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getHeaderMenuStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getHeaderMenuLinkStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getListFrameStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getListSubjectStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getListDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getSubjectStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getSingleFrameStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getSingleSubjectStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getSingleDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getContentPanelStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getContentPanelTitleStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getGlobal()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getDisable()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_StyleTheme.getTimeUpdated()) %></td>


<td>
<form name="styleThemeForm<%=_StyleTheme.getId()%>2" method="get" action="/v_style_theme_edit2.html" >
    <a href="javascript:document.styleThemeForm<%=_StyleTheme.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleTheme.getId() %>">
</form>

</td>
<td>
<a href="/styleThemeAction.html?del=true&id=<%=_StyleTheme.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>