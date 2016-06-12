<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_config_home";

    String _style_keyValue= WebUtil.display((String)reqParams.get("styleKey"));
    String _style_useValue= WebUtil.display((String)reqParams.get("styleUse"));
    String _is_globalValue= WebUtil.display((String)reqParams.get("isGlobal"));
    String _id_classValue= WebUtil.display((String)reqParams.get("idClass"));
    String _is_idValue= WebUtil.display((String)reqParams.get("isId"));
    String _colorValue= WebUtil.display((String)reqParams.get("color"));
    String _bg_colorValue= WebUtil.display((String)reqParams.get("bgColor"));
    String _bg_imageValue= WebUtil.display((String)reqParams.get("bgImage"));
    String _bg_repeatValue= WebUtil.display((String)reqParams.get("bgRepeat"));
    String _bg_attachValue= WebUtil.display((String)reqParams.get("bgAttach"));
    String _bg_positionValue= WebUtil.display((String)reqParams.get("bgPosition"));
    String _text_alignValue= WebUtil.display((String)reqParams.get("textAlign"));
    String _font_familyValue= WebUtil.display((String)reqParams.get("fontFamily"));
    String _font_sizeValue= WebUtil.display((String)reqParams.get("fontSize"));
    String _font_styleValue= WebUtil.display((String)reqParams.get("fontStyle"));
    String _font_variantValue= WebUtil.display((String)reqParams.get("fontVariant"));
    String _font_weightValue= WebUtil.display((String)reqParams.get("fontWeight"));
    String _border_directionValue= WebUtil.display((String)reqParams.get("borderDirection"));
    String _border_widthValue= WebUtil.display((String)reqParams.get("borderWidth"));
    String _border_styleValue= WebUtil.display((String)reqParams.get("borderStyle"));
    String _border_colorValue= WebUtil.display((String)reqParams.get("borderColor"));
    String _marginValue= WebUtil.display((String)reqParams.get("margin"));
    String _paddingValue= WebUtil.display((String)reqParams.get("padding"));
    String _list_style_typeValue= WebUtil.display((String)reqParams.get("listStyleType"));
    String _list_style_positionValue= WebUtil.display((String)reqParams.get("listStylePosition"));
    String _list_style_imageValue= WebUtil.display((String)reqParams.get("listStyleImage"));
    String _floatingValue= WebUtil.display((String)reqParams.get("floating"));
    String _extra_style_strValue= WebUtil.display((String)reqParams.get("extraStyleStr"));
    String _item_style_strValue= WebUtil.display((String)reqParams.get("itemStyleStr"));
    String _linkValue= WebUtil.display((String)reqParams.get("link"));
    String _link_hoverValue= WebUtil.display((String)reqParams.get("linkHover"));
    String _link_activeValue= WebUtil.display((String)reqParams.get("linkActive"));
    String _heightValue= WebUtil.display((String)reqParams.get("height"));
    String _widthValue= WebUtil.display((String)reqParams.get("width"));
    String _is_tableValue= WebUtil.display((String)reqParams.get("isTable"));
    String _border_collapseValue= WebUtil.display((String)reqParams.get("borderCollapse"));
    String _border_spacingValue= WebUtil.display((String)reqParams.get("borderSpacing"));
    String _tr_style_idsValue= WebUtil.display((String)reqParams.get("trStyleIds"));
    String _td_style_idsValue= WebUtil.display((String)reqParams.get("tdStyleIds"));
    String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));
    String _time_updatedValue= WebUtil.display((String)reqParams.get("timeUpdated"));
%> 

<a href="/v_style_config_home.html"> StyleConfig Home </a>
<%
	
	List list = null;
	list = StyleConfigDS.getInstance().getBySiteId(site.getId());

%>

<div id="styleConfigList"> 
<%
	for(Iterator iter = list.iterator();iter.hasNext();){
		StyleConfig _StyleConfig = (StyleConfig) iter.next();	
%>

	<div id="styleConfigFrame<%=_StyleConfig.getId() %>" >

		<div id="styleKey" >
			styleKey:<%= _StyleConfig.getStyleKey() %>
		</div>
		<div id="styleUse" >
			styleUse:<%= _StyleConfig.getStyleUse() %>
		</div>
		<div id="isGlobal" >
			isGlobal:<%= _StyleConfig.getIsGlobal() %>
		</div>
		<div id="idClass" >
			idClass:<%= _StyleConfig.getIdClass() %>
		</div>
		<div id="isId" >
			isId:<%= _StyleConfig.getIsId() %>
		</div>
		<div id="color" >
			color:<%= _StyleConfig.getColor() %>
		</div>
		<div id="bgColor" >
			bgColor:<%= _StyleConfig.getBgColor() %>
		</div>
		<div id="bgImage" >
			bgImage:<%= _StyleConfig.getBgImage() %>
		</div>
		<div id="bgRepeat" >
			bgRepeat:<%= _StyleConfig.getBgRepeat() %>
		</div>
		<div id="bgAttach" >
			bgAttach:<%= _StyleConfig.getBgAttach() %>
		</div>
		<div id="bgPosition" >
			bgPosition:<%= _StyleConfig.getBgPosition() %>
		</div>
		<div id="textAlign" >
			textAlign:<%= _StyleConfig.getTextAlign() %>
		</div>
		<div id="fontFamily" >
			fontFamily:<%= _StyleConfig.getFontFamily() %>
		</div>
		<div id="fontSize" >
			fontSize:<%= _StyleConfig.getFontSize() %>
		</div>
		<div id="fontStyle" >
			fontStyle:<%= _StyleConfig.getFontStyle() %>
		</div>
		<div id="fontVariant" >
			fontVariant:<%= _StyleConfig.getFontVariant() %>
		</div>
		<div id="fontWeight" >
			fontWeight:<%= _StyleConfig.getFontWeight() %>
		</div>
		<div id="borderDirection" >
			borderDirection:<%= _StyleConfig.getBorderDirection() %>
		</div>
		<div id="borderWidth" >
			borderWidth:<%= _StyleConfig.getBorderWidth() %>
		</div>
		<div id="borderStyle" >
			borderStyle:<%= _StyleConfig.getBorderStyle() %>
		</div>
		<div id="borderColor" >
			borderColor:<%= _StyleConfig.getBorderColor() %>
		</div>
		<div id="margin" >
			margin:<%= _StyleConfig.getMargin() %>
		</div>
		<div id="padding" >
			padding:<%= _StyleConfig.getPadding() %>
		</div>
		<div id="listStyleType" >
			listStyleType:<%= _StyleConfig.getListStyleType() %>
		</div>
		<div id="listStylePosition" >
			listStylePosition:<%= _StyleConfig.getListStylePosition() %>
		</div>
		<div id="listStyleImage" >
			listStyleImage:<%= _StyleConfig.getListStyleImage() %>
		</div>
		<div id="floating" >
			floating:<%= _StyleConfig.getFloating() %>
		</div>
		<div id="extraStyleStr" >
			extraStyleStr:<%= _StyleConfig.getExtraStyleStr() %>
		</div>
		<div id="itemStyleStr" >
			itemStyleStr:<%= _StyleConfig.getItemStyleStr() %>
		</div>
		<div id="link" >
			link:<%= _StyleConfig.getLink() %>
		</div>
		<div id="linkHover" >
			linkHover:<%= _StyleConfig.getLinkHover() %>
		</div>
		<div id="linkActive" >
			linkActive:<%= _StyleConfig.getLinkActive() %>
		</div>
		<div id="height" >
			height:<%= _StyleConfig.getHeight() %>
		</div>
		<div id="width" >
			width:<%= _StyleConfig.getWidth() %>
		</div>
		<div id="isTable" >
			isTable:<%= _StyleConfig.getIsTable() %>
		</div>
		<div id="borderCollapse" >
			borderCollapse:<%= _StyleConfig.getBorderCollapse() %>
		</div>
		<div id="borderSpacing" >
			borderSpacing:<%= _StyleConfig.getBorderSpacing() %>
		</div>
		<div id="trStyleIds" >
			trStyleIds:<%= _StyleConfig.getTrStyleIds() %>
		</div>
		<div id="tdStyleIds" >
			tdStyleIds:<%= _StyleConfig.getTdStyleIds() %>
		</div>
		<div id="timeCreated" >
			timeCreated:<%= _StyleConfig.getTimeCreated() %>
		</div>
		<div id="timeUpdated" >
			timeUpdated:<%= _StyleConfig.getTimeUpdated() %>
		</div>
		<div>
		<a id="styleConfigDeleteButton" href="javascript:deleteThis('/styleConfigAction.html',<%= _StyleConfig.getId()%>,'styleConfigFrame<%=_StyleConfig.getId()%>' );" > 
			<img src="/images/icons/led/cancel.png" /> 
		</a>
		</div>
	</div>
<%
	}
%>
</div>
<br>
<div id="styleConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleConfigFormAdd" method="POST" action="/styleConfigAction.html" id="styleConfigFormAdd">

	<div id="styleConfigForm_styleKey_field">
    <div id="styleConfigForm_styleKey_label" class="formLabel" >Style Key </div>
    <div id="styleConfigForm_styleKey_text" class="formFieldText" >       
        <input class="field" id="_ffd_styleKey" type="text" size="70" name="styleKey" value="<%=WebUtil.display(_style_keyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_styleUse_field">
    <div id="styleConfigForm_styleUse_label" class="formLabel" >Style Use </div>
    <div id="styleConfigForm_styleUse_dropdown" class="formFieldDropDown" >       
        <select class="field" name="styleUse" id="_ffd_styleUse">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _style_useValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _style_useValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _style_useValue)%>>Custom</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _style_useValue)%>>TBD</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _style_useValue)%>>TBD</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _style_useValue)%>>TBD</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_isGlobal_field">
    <div id="styleConfigForm_isGlobal_label" class="formLabel" >Is Global </div>
    <div id="styleConfigForm_isGlobal_text" class="formFieldText" >       
        <input class="field" id="_ffd_isGlobal" type="text" size="70" name="isGlobal" value="<%=WebUtil.display(_is_globalValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_idClass_field">
    <div id="styleConfigForm_idClass_label" class="formLabel" >Id Class </div>
    <div id="styleConfigForm_idClass_text" class="formFieldText" >       
        <input class="field" id="_ffd_idClass" type="text" size="70" name="idClass" value="<%=WebUtil.display(_id_classValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_isId_field">
    <div id="styleConfigForm_isId_label" class="formLabel" >Is Id </div>
    <div id="styleConfigForm_isId_text" class="formFieldText" >       
        <input class="field" id="_ffd_isId" type="text" size="70" name="isId" value="<%=WebUtil.display(_is_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_color_field">
    <div id="styleConfigForm_color_label" class="formLabel" >Color </div>
    <div id="styleConfigForm_color_text" class="formFieldText" >       
        <input class="field" id="_ffd_color" type="text" size="70" name="color" value="<%=WebUtil.display(_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_bgColor_field">
    <div id="styleConfigForm_bgColor_label" class="formLabel" >Bg Color </div>
    <div id="styleConfigForm_bgColor_text" class="formFieldText" >       
        <input class="field" id="_ffd_bgColor" type="text" size="70" name="bgColor" value="<%=WebUtil.display(_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_bgImage_field">
    <div id="styleConfigForm_bgImage_label" class="formLabel" >Bg Image </div>
    <div id="styleConfigForm_bgImage_text" class="formFieldText" >       
        <input class="field" id="_ffd_bgImage" type="text" size="70" name="bgImage" value="<%=WebUtil.display(_bg_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_bgRepeat_field">
    <div id="styleConfigForm_bgRepeat_label" class="formLabel" >Bg Repeat </div>
    <div id="styleConfigForm_bgRepeat_text" class="formFieldText" >       
        <input class="field" id="_ffd_bgRepeat" type="text" size="70" name="bgRepeat" value="<%=WebUtil.display(_bg_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_bgAttach_field">
    <div id="styleConfigForm_bgAttach_label" class="formLabel" >Bg Attach </div>
    <div id="styleConfigForm_bgAttach_text" class="formFieldText" >       
        <input class="field" id="_ffd_bgAttach" type="text" size="70" name="bgAttach" value="<%=WebUtil.display(_bg_attachValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_bgPosition_field">
    <div id="styleConfigForm_bgPosition_label" class="formLabel" >Bg Position </div>
    <div id="styleConfigForm_bgPosition_text" class="formFieldText" >       
        <input class="field" id="_ffd_bgPosition" type="text" size="70" name="bgPosition" value="<%=WebUtil.display(_bg_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_textAlign_field">
    <div id="styleConfigForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="styleConfigForm_textAlign_text" class="formFieldText" >       
        <input class="field" id="_ffd_textAlign" type="text" size="70" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_fontFamily_field">
    <div id="styleConfigForm_fontFamily_label" class="formLabel" >Font Family </div>
    <div id="styleConfigForm_fontFamily_text" class="formFieldText" >       
        <input class="field" id="_ffd_fontFamily" type="text" size="70" name="fontFamily" value="<%=WebUtil.display(_font_familyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_fontSize_field">
    <div id="styleConfigForm_fontSize_label" class="formLabel" >Font Size </div>
    <div id="styleConfigForm_fontSize_text" class="formFieldText" >       
        <input class="field" id="_ffd_fontSize" type="text" size="70" name="fontSize" value="<%=WebUtil.display(_font_sizeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_fontStyle_field">
    <div id="styleConfigForm_fontStyle_label" class="formLabel" >Font Style </div>
    <div id="styleConfigForm_fontStyle_text" class="formFieldText" >       
        <input class="field" id="_ffd_fontStyle" type="text" size="70" name="fontStyle" value="<%=WebUtil.display(_font_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_fontVariant_field">
    <div id="styleConfigForm_fontVariant_label" class="formLabel" >Font Variant </div>
    <div id="styleConfigForm_fontVariant_text" class="formFieldText" >       
        <input class="field" id="_ffd_fontVariant" type="text" size="70" name="fontVariant" value="<%=WebUtil.display(_font_variantValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_fontWeight_field">
    <div id="styleConfigForm_fontWeight_label" class="formLabel" >Font Weight </div>
    <div id="styleConfigForm_fontWeight_text" class="formFieldText" >       
        <input class="field" id="_ffd_fontWeight" type="text" size="70" name="fontWeight" value="<%=WebUtil.display(_font_weightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_borderDirection_field">
    <div id="styleConfigForm_borderDirection_label" class="formLabel" >Border Direction </div>
    <div id="styleConfigForm_borderDirection_text" class="formFieldText" >       
        <input class="field" id="_ffd_borderDirection" type="text" size="70" name="borderDirection" value="<%=WebUtil.display(_border_directionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_borderWidth_field">
    <div id="styleConfigForm_borderWidth_label" class="formLabel" >Border Width </div>
    <div id="styleConfigForm_borderWidth_text" class="formFieldText" >       
        <input class="field" id="_ffd_borderWidth" type="text" size="70" name="borderWidth" value="<%=WebUtil.display(_border_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_borderStyle_field">
    <div id="styleConfigForm_borderStyle_label" class="formLabel" >Border Style </div>
    <div id="styleConfigForm_borderStyle_text" class="formFieldText" >       
        <input class="field" id="_ffd_borderStyle" type="text" size="70" name="borderStyle" value="<%=WebUtil.display(_border_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_borderColor_field">
    <div id="styleConfigForm_borderColor_label" class="formLabel" >Border Color </div>
    <div id="styleConfigForm_borderColor_text" class="formFieldText" >       
        <input class="field" id="_ffd_borderColor" type="text" size="70" name="borderColor" value="<%=WebUtil.display(_border_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_margin_field">
    <div id="styleConfigForm_margin_label" class="formLabel" >Margin </div>
    <div id="styleConfigForm_margin_text" class="formFieldText" >       
        <input class="field" id="_ffd_margin" type="text" size="70" name="margin" value="<%=WebUtil.display(_marginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_padding_field">
    <div id="styleConfigForm_padding_label" class="formLabel" >Padding </div>
    <div id="styleConfigForm_padding_text" class="formFieldText" >       
        <input class="field" id="_ffd_padding" type="text" size="70" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_listStyleType_field">
    <div id="styleConfigForm_listStyleType_label" class="formLabel" >List Style Type </div>
    <div id="styleConfigForm_listStyleType_text" class="formFieldText" >       
        <input class="field" id="_ffd_listStyleType" type="text" size="70" name="listStyleType" value="<%=WebUtil.display(_list_style_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_listStylePosition_field">
    <div id="styleConfigForm_listStylePosition_label" class="formLabel" >List Style Position </div>
    <div id="styleConfigForm_listStylePosition_text" class="formFieldText" >       
        <input class="field" id="_ffd_listStylePosition" type="text" size="70" name="listStylePosition" value="<%=WebUtil.display(_list_style_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_listStyleImage_field">
    <div id="styleConfigForm_listStyleImage_label" class="formLabel" >List Style Image </div>
    <div id="styleConfigForm_listStyleImage_text" class="formFieldText" >       
        <input class="field" id="_ffd_listStyleImage" type="text" size="70" name="listStyleImage" value="<%=WebUtil.display(_list_style_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_floating_field">
    <div id="styleConfigForm_floating_label" class="formLabel" >Floating </div>
    <div id="styleConfigForm_floating_text" class="formFieldText" >       
        <input class="field" id="_ffd_floating" type="text" size="70" name="floating" value="<%=WebUtil.display(_floatingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_extraStyleStr_field">
    <div id="styleConfigForm_extraStyleStr_label" class="formLabel" >Extra Style Str </div>
    <div id="styleConfigForm_extraStyleStr_text" class="formFieldText" >       
        <input class="field" id="_ffd_extraStyleStr" type="text" size="70" name="extraStyleStr" value="<%=WebUtil.display(_extra_style_strValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_itemStyleStr_field">
    <div id="styleConfigForm_itemStyleStr_label" class="formLabel" >Item Style Str </div>
    <div id="styleConfigForm_itemStyleStr_text" class="formFieldText" >       
        <input class="field" id="_ffd_itemStyleStr" type="text" size="70" name="itemStyleStr" value="<%=WebUtil.display(_item_style_strValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_link_field">
    <div id="styleConfigForm_link_label" class="formLabel" >Link </div>
    <div id="styleConfigForm_link_text" class="formFieldText" >       
        <input class="field" id="_ffd_link" type="text" size="70" name="link" value="<%=WebUtil.display(_linkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_linkHover_field">
    <div id="styleConfigForm_linkHover_label" class="formLabel" >Link Hover </div>
    <div id="styleConfigForm_linkHover_text" class="formFieldText" >       
        <input class="field" id="_ffd_linkHover" type="text" size="70" name="linkHover" value="<%=WebUtil.display(_link_hoverValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_linkActive_field">
    <div id="styleConfigForm_linkActive_label" class="formLabel" >Link Active </div>
    <div id="styleConfigForm_linkActive_text" class="formFieldText" >       
        <input class="field" id="_ffd_linkActive" type="text" size="70" name="linkActive" value="<%=WebUtil.display(_link_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_height_field">
    <div id="styleConfigForm_height_label" class="formLabel" >Height </div>
    <div id="styleConfigForm_height_text" class="formFieldText" >       
        <input class="field" id="_ffd_height" type="text" size="70" name="height" value="<%=WebUtil.display(_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_width_field">
    <div id="styleConfigForm_width_label" class="formLabel" >Width </div>
    <div id="styleConfigForm_width_text" class="formFieldText" >       
        <input class="field" id="_ffd_width" type="text" size="70" name="width" value="<%=WebUtil.display(_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_isTable_field">
    <div id="styleConfigForm_isTable_label" class="formLabel" >Is Table </div>
    <div id="styleConfigForm_isTable_text" class="formFieldText" >       
        <input class="field" id="_ffd_isTable" type="text" size="70" name="isTable" value="<%=WebUtil.display(_is_tableValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_borderCollapse_field">
    <div id="styleConfigForm_borderCollapse_label" class="formLabel" >Border Collapse </div>
    <div id="styleConfigForm_borderCollapse_text" class="formFieldText" >       
        <input class="field" id="_ffd_borderCollapse" type="text" size="70" name="borderCollapse" value="<%=WebUtil.display(_border_collapseValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_borderSpacing_field">
    <div id="styleConfigForm_borderSpacing_label" class="formLabel" >Border Spacing </div>
    <div id="styleConfigForm_borderSpacing_text" class="formFieldText" >       
        <input class="field" id="_ffd_borderSpacing" type="text" size="70" name="borderSpacing" value="<%=WebUtil.display(_border_spacingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_trStyleIds_field">
    <div id="styleConfigForm_trStyleIds_label" class="formLabel" >Tr Style Ids </div>
    <div id="styleConfigForm_trStyleIds_text" class="formFieldText" >       
        <input class="field" id="_ffd_trStyleIds" type="text" size="70" name="trStyleIds" value="<%=WebUtil.display(_tr_style_idsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>
	<div id="styleConfigForm_tdStyleIds_field">
    <div id="styleConfigForm_tdStyleIds_label" class="formLabel" >Td Style Ids </div>
    <div id="styleConfigForm_tdStyleIds_text" class="formFieldText" >       
        <input class="field" id="_ffd_tdStyleIds" type="text" size="70" name="tdStyleIds" value="<%=WebUtil.display(_td_style_idsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="styleConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit_ajax" href="javascript:sendFormAjax2('/styleConfigAction.html', 'styleConfigFormAdd', 'formSubmit_ajax', 'styleConfig');">Submit</a>
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
	document.getElementById("resultDisplayStyleConfig").innerHTML = data;
}

function sendRequest(){
	xmlhttpPostXX('styleConfigFormAddDis','/styleConfigAction.html', 'resultDisplayStyleConfig', '${ajax_response_fields}', responseCallback);
}
</script>



<form name="styleConfigFormAddDis" method="POST" action="/styleConfigAction.html" id="styleConfigFormAddDis">
	<INPUT TYPE="HIDDEN" NAME="add" value="true">


		 <div class="ajaxFormLabel"> Style Key</div>
        <input class="field" id="styleKey" type="text" size="70" name="styleKey" value="<%=WebUtil.display(_style_keyValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Style Use</div>
        <select class="field" name="styleUse" id="styleUse">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _style_useValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _style_useValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _style_useValue)%>>Custom</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _style_useValue)%>>TBD</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _style_useValue)%>>TBD</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _style_useValue)%>>TBD</option>
        </select>  <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Global</div>
        <input class="field" id="isGlobal" type="text" size="70" name="isGlobal" value="<%=WebUtil.display(_is_globalValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Id Class</div>
        <input class="field" id="idClass" type="text" size="70" name="idClass" value="<%=WebUtil.display(_id_classValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Id</div>
        <input class="field" id="isId" type="text" size="70" name="isId" value="<%=WebUtil.display(_is_idValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Color</div>
        <input class="field" id="color" type="text" size="70" name="color" value="<%=WebUtil.display(_colorValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Bg Color</div>
        <input class="field" id="bgColor" type="text" size="70" name="bgColor" value="<%=WebUtil.display(_bg_colorValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Bg Image</div>
        <input class="field" id="bgImage" type="text" size="70" name="bgImage" value="<%=WebUtil.display(_bg_imageValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Bg Repeat</div>
        <input class="field" id="bgRepeat" type="text" size="70" name="bgRepeat" value="<%=WebUtil.display(_bg_repeatValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Bg Attach</div>
        <input class="field" id="bgAttach" type="text" size="70" name="bgAttach" value="<%=WebUtil.display(_bg_attachValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Bg Position</div>
        <input class="field" id="bgPosition" type="text" size="70" name="bgPosition" value="<%=WebUtil.display(_bg_positionValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Text Align</div>
        <input class="field" id="textAlign" type="text" size="70" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Font Family</div>
        <input class="field" id="fontFamily" type="text" size="70" name="fontFamily" value="<%=WebUtil.display(_font_familyValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Font Size</div>
        <input class="field" id="fontSize" type="text" size="70" name="fontSize" value="<%=WebUtil.display(_font_sizeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Font Style</div>
        <input class="field" id="fontStyle" type="text" size="70" name="fontStyle" value="<%=WebUtil.display(_font_styleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Font Variant</div>
        <input class="field" id="fontVariant" type="text" size="70" name="fontVariant" value="<%=WebUtil.display(_font_variantValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Font Weight</div>
        <input class="field" id="fontWeight" type="text" size="70" name="fontWeight" value="<%=WebUtil.display(_font_weightValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Border Direction</div>
        <input class="field" id="borderDirection" type="text" size="70" name="borderDirection" value="<%=WebUtil.display(_border_directionValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Border Width</div>
        <input class="field" id="borderWidth" type="text" size="70" name="borderWidth" value="<%=WebUtil.display(_border_widthValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Border Style</div>
        <input class="field" id="borderStyle" type="text" size="70" name="borderStyle" value="<%=WebUtil.display(_border_styleValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Border Color</div>
        <input class="field" id="borderColor" type="text" size="70" name="borderColor" value="<%=WebUtil.display(_border_colorValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Margin</div>
        <input class="field" id="margin" type="text" size="70" name="margin" value="<%=WebUtil.display(_marginValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Padding</div>
        <input class="field" id="padding" type="text" size="70" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Style Type</div>
        <input class="field" id="listStyleType" type="text" size="70" name="listStyleType" value="<%=WebUtil.display(_list_style_typeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Style Position</div>
        <input class="field" id="listStylePosition" type="text" size="70" name="listStylePosition" value="<%=WebUtil.display(_list_style_positionValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> List Style Image</div>
        <input class="field" id="listStyleImage" type="text" size="70" name="listStyleImage" value="<%=WebUtil.display(_list_style_imageValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Floating</div>
        <input class="field" id="floating" type="text" size="70" name="floating" value="<%=WebUtil.display(_floatingValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Extra Style Str</div>
        <input class="field" id="extraStyleStr" type="text" size="70" name="extraStyleStr" value="<%=WebUtil.display(_extra_style_strValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Item Style Str</div>
        <input class="field" id="itemStyleStr" type="text" size="70" name="itemStyleStr" value="<%=WebUtil.display(_item_style_strValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Link</div>
        <input class="field" id="link" type="text" size="70" name="link" value="<%=WebUtil.display(_linkValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Link Hover</div>
        <input class="field" id="linkHover" type="text" size="70" name="linkHover" value="<%=WebUtil.display(_link_hoverValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Link Active</div>
        <input class="field" id="linkActive" type="text" size="70" name="linkActive" value="<%=WebUtil.display(_link_activeValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Height</div>
        <input class="field" id="height" type="text" size="70" name="height" value="<%=WebUtil.display(_heightValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Width</div>
        <input class="field" id="width" type="text" size="70" name="width" value="<%=WebUtil.display(_widthValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Is Table</div>
        <input class="field" id="isTable" type="text" size="70" name="isTable" value="<%=WebUtil.display(_is_tableValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Border Collapse</div>
        <input class="field" id="borderCollapse" type="text" size="70" name="borderCollapse" value="<%=WebUtil.display(_border_collapseValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Border Spacing</div>
        <input class="field" id="borderSpacing" type="text" size="70" name="borderSpacing" value="<%=WebUtil.display(_border_spacingValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Tr Style Ids</div>
        <input class="field" id="trStyleIds" type="text" size="70" name="trStyleIds" value="<%=WebUtil.display(_tr_style_idsValue)%>"/> <span></span>
		<br/>
		 <div class="ajaxFormLabel"> Td Style Ids</div>
        <input class="field" id="tdStyleIds" type="text" size="70" name="tdStyleIds" value="<%=WebUtil.display(_td_style_idsValue)%>"/> <span></span>
		<br/>

	<a id="formSubmit_ajax_simpleform" href="javascript:sendRequest()">Submit</a><br>
	<a href="javascript:clearFormXX('styleConfigFormAddDis')">Clear Form</a><br>
    <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a><br>

	<!--INPUT TYPE="HIDDEN" id="_ffd_wpid" name="wpid" value=" _wpId " -->

</form>
<span id="resultDisplayStyleConfig"></span>
<a href="javascript:backToXX('styleConfigFormAddDis','resultDisplayStyleConfig')">show back</a><br>
<hr/>
