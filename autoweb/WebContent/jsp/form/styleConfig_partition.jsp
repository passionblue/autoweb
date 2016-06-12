<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _style_keyValue= "";
    String _style_useValue= "";
    String _is_globalValue= "";
    String _id_classValue= "";
    String _is_idValue= "";
    String _colorValue= "";
    String _bg_colorValue= "";
    String _bg_imageValue= "";
    String _bg_repeatValue= "";
    String _bg_attachValue= "";
    String _bg_positionValue= "";
    String _text_alignValue= "";
    String _font_familyValue= "";
    String _font_sizeValue= "";
    String _font_styleValue= "";
    String _font_variantValue= "";
    String _font_weightValue= "";
    String _border_directionValue= "";
    String _border_widthValue= "";
    String _border_styleValue= "";
    String _border_colorValue= "";
    String _marginValue= "";
    String _paddingValue= "";
    String _list_style_typeValue= "";
    String _list_style_positionValue= "";
    String _list_style_imageValue= "";
    String _floatingValue= "";
    String _extra_style_strValue= "";
    String _item_style_strValue= "";
    String _linkValue= "";
    String _link_hoverValue= "";
    String _link_activeValue= "";
    String _heightValue= "";
    String _widthValue= "";
    String _is_tableValue= "";
    String _border_collapseValue= "";
    String _border_spacingValue= "";
    String _tr_style_idsValue= "";
    String _td_style_idsValue= "";
    String _time_createdValue= "";
    String _time_updatedValue= "";

%>

<div id="partitionFormFrame_style_config_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /StyleConfig_artition.jsp -->

	<script type="text/javascript">
		function sendForm_style_config_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">





	<div id="styleConfigForm_styleKey_field" class="formFieldFrame">
    <div id="styleConfigForm_styleKey_label" class="formLabel" >Style Key </div>
    <div id="styleConfigForm_styleKey_text" class="formFieldText" >       
        <input id="styleKey" class="field" type="text" size="70" name="styleKey" value="<%=WebUtil.display(_style_keyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="styleConfigForm_styleUse_field" class="formFieldFrame">
    <div id="styleConfigForm_styleUse_label" class="formLabel" >Style Use </div>
    <div id="styleConfigForm_styleUse_dropdown" class="formFieldDropDown" >       
        <select class="field" name="styleUse" id="styleUse">
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





	<div id="styleConfigForm_isGlobal_field" class="formFieldFrame">
    <div id="styleConfigForm_isGlobal_label" class="formLabel" >Is Global </div>
    <div id="styleConfigForm_isGlobal_text" class="formFieldText" >       
        <input id="isGlobal" class="field" type="text" size="70" name="isGlobal" value="<%=WebUtil.display(_is_globalValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_idClass_field" class="formFieldFrame">
    <div id="styleConfigForm_idClass_label" class="formLabel" >Id Class </div>
    <div id="styleConfigForm_idClass_text" class="formFieldText" >       
        <input id="idClass" class="field" type="text" size="70" name="idClass" value="<%=WebUtil.display(_id_classValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_isId_field" class="formFieldFrame">
    <div id="styleConfigForm_isId_label" class="formLabel" >Is Id </div>
    <div id="styleConfigForm_isId_text" class="formFieldText" >       
        <input id="isId" class="field" type="text" size="70" name="isId" value="<%=WebUtil.display(_is_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_color_field" class="formFieldFrame">
    <div id="styleConfigForm_color_label" class="formLabel" >Color </div>
    <div id="styleConfigForm_color_text" class="formFieldText" >       
        <input id="color" class="field" type="text" size="70" name="color" value="<%=WebUtil.display(_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgColor_field" class="formFieldFrame">
    <div id="styleConfigForm_bgColor_label" class="formLabel" >Bg Color </div>
    <div id="styleConfigForm_bgColor_text" class="formFieldText" >       
        <input id="bgColor" class="field" type="text" size="70" name="bgColor" value="<%=WebUtil.display(_bg_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgImage_field" class="formFieldFrame">
    <div id="styleConfigForm_bgImage_label" class="formLabel" >Bg Image </div>
    <div id="styleConfigForm_bgImage_text" class="formFieldText" >       
        <input id="bgImage" class="field" type="text" size="70" name="bgImage" value="<%=WebUtil.display(_bg_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgRepeat_field" class="formFieldFrame">
    <div id="styleConfigForm_bgRepeat_label" class="formLabel" >Bg Repeat </div>
    <div id="styleConfigForm_bgRepeat_text" class="formFieldText" >       
        <input id="bgRepeat" class="field" type="text" size="70" name="bgRepeat" value="<%=WebUtil.display(_bg_repeatValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgAttach_field" class="formFieldFrame">
    <div id="styleConfigForm_bgAttach_label" class="formLabel" >Bg Attach </div>
    <div id="styleConfigForm_bgAttach_text" class="formFieldText" >       
        <input id="bgAttach" class="field" type="text" size="70" name="bgAttach" value="<%=WebUtil.display(_bg_attachValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_bgPosition_field" class="formFieldFrame">
    <div id="styleConfigForm_bgPosition_label" class="formLabel" >Bg Position </div>
    <div id="styleConfigForm_bgPosition_text" class="formFieldText" >       
        <input id="bgPosition" class="field" type="text" size="70" name="bgPosition" value="<%=WebUtil.display(_bg_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_textAlign_field" class="formFieldFrame">
    <div id="styleConfigForm_textAlign_label" class="formLabel" >Text Align </div>
    <div id="styleConfigForm_textAlign_text" class="formFieldText" >       
        <input id="textAlign" class="field" type="text" size="70" name="textAlign" value="<%=WebUtil.display(_text_alignValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontFamily_field" class="formFieldFrame">
    <div id="styleConfigForm_fontFamily_label" class="formLabel" >Font Family </div>
    <div id="styleConfigForm_fontFamily_text" class="formFieldText" >       
        <input id="fontFamily" class="field" type="text" size="70" name="fontFamily" value="<%=WebUtil.display(_font_familyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontSize_field" class="formFieldFrame">
    <div id="styleConfigForm_fontSize_label" class="formLabel" >Font Size </div>
    <div id="styleConfigForm_fontSize_text" class="formFieldText" >       
        <input id="fontSize" class="field" type="text" size="70" name="fontSize" value="<%=WebUtil.display(_font_sizeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontStyle_field" class="formFieldFrame">
    <div id="styleConfigForm_fontStyle_label" class="formLabel" >Font Style </div>
    <div id="styleConfigForm_fontStyle_text" class="formFieldText" >       
        <input id="fontStyle" class="field" type="text" size="70" name="fontStyle" value="<%=WebUtil.display(_font_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontVariant_field" class="formFieldFrame">
    <div id="styleConfigForm_fontVariant_label" class="formLabel" >Font Variant </div>
    <div id="styleConfigForm_fontVariant_text" class="formFieldText" >       
        <input id="fontVariant" class="field" type="text" size="70" name="fontVariant" value="<%=WebUtil.display(_font_variantValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_fontWeight_field" class="formFieldFrame">
    <div id="styleConfigForm_fontWeight_label" class="formLabel" >Font Weight </div>
    <div id="styleConfigForm_fontWeight_text" class="formFieldText" >       
        <input id="fontWeight" class="field" type="text" size="70" name="fontWeight" value="<%=WebUtil.display(_font_weightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderDirection_field" class="formFieldFrame">
    <div id="styleConfigForm_borderDirection_label" class="formLabel" >Border Direction </div>
    <div id="styleConfigForm_borderDirection_text" class="formFieldText" >       
        <input id="borderDirection" class="field" type="text" size="70" name="borderDirection" value="<%=WebUtil.display(_border_directionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderWidth_field" class="formFieldFrame">
    <div id="styleConfigForm_borderWidth_label" class="formLabel" >Border Width </div>
    <div id="styleConfigForm_borderWidth_text" class="formFieldText" >       
        <input id="borderWidth" class="field" type="text" size="70" name="borderWidth" value="<%=WebUtil.display(_border_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderStyle_field" class="formFieldFrame">
    <div id="styleConfigForm_borderStyle_label" class="formLabel" >Border Style </div>
    <div id="styleConfigForm_borderStyle_text" class="formFieldText" >       
        <input id="borderStyle" class="field" type="text" size="70" name="borderStyle" value="<%=WebUtil.display(_border_styleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderColor_field" class="formFieldFrame">
    <div id="styleConfigForm_borderColor_label" class="formLabel" >Border Color </div>
    <div id="styleConfigForm_borderColor_text" class="formFieldText" >       
        <input id="borderColor" class="field" type="text" size="70" name="borderColor" value="<%=WebUtil.display(_border_colorValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_margin_field" class="formFieldFrame">
    <div id="styleConfigForm_margin_label" class="formLabel" >Margin </div>
    <div id="styleConfigForm_margin_text" class="formFieldText" >       
        <input id="margin" class="field" type="text" size="70" name="margin" value="<%=WebUtil.display(_marginValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_padding_field" class="formFieldFrame">
    <div id="styleConfigForm_padding_label" class="formLabel" >Padding </div>
    <div id="styleConfigForm_padding_text" class="formFieldText" >       
        <input id="padding" class="field" type="text" size="70" name="padding" value="<%=WebUtil.display(_paddingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_listStyleType_field" class="formFieldFrame">
    <div id="styleConfigForm_listStyleType_label" class="formLabel" >List Style Type </div>
    <div id="styleConfigForm_listStyleType_text" class="formFieldText" >       
        <input id="listStyleType" class="field" type="text" size="70" name="listStyleType" value="<%=WebUtil.display(_list_style_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_listStylePosition_field" class="formFieldFrame">
    <div id="styleConfigForm_listStylePosition_label" class="formLabel" >List Style Position </div>
    <div id="styleConfigForm_listStylePosition_text" class="formFieldText" >       
        <input id="listStylePosition" class="field" type="text" size="70" name="listStylePosition" value="<%=WebUtil.display(_list_style_positionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_listStyleImage_field" class="formFieldFrame">
    <div id="styleConfigForm_listStyleImage_label" class="formLabel" >List Style Image </div>
    <div id="styleConfigForm_listStyleImage_text" class="formFieldText" >       
        <input id="listStyleImage" class="field" type="text" size="70" name="listStyleImage" value="<%=WebUtil.display(_list_style_imageValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_floating_field" class="formFieldFrame">
    <div id="styleConfigForm_floating_label" class="formLabel" >Floating </div>
    <div id="styleConfigForm_floating_text" class="formFieldText" >       
        <input id="floating" class="field" type="text" size="70" name="floating" value="<%=WebUtil.display(_floatingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_extraStyleStr_field" class="formFieldFrame">
    <div id="styleConfigForm_extraStyleStr_label" class="formLabel" >Extra Style Str </div>
    <div id="styleConfigForm_extraStyleStr_text" class="formFieldText" >       
        <input id="extraStyleStr" class="field" type="text" size="70" name="extraStyleStr" value="<%=WebUtil.display(_extra_style_strValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_itemStyleStr_field" class="formFieldFrame">
    <div id="styleConfigForm_itemStyleStr_label" class="formLabel" >Item Style Str </div>
    <div id="styleConfigForm_itemStyleStr_text" class="formFieldText" >       
        <input id="itemStyleStr" class="field" type="text" size="70" name="itemStyleStr" value="<%=WebUtil.display(_item_style_strValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_link_field" class="formFieldFrame">
    <div id="styleConfigForm_link_label" class="formLabel" >Link </div>
    <div id="styleConfigForm_link_text" class="formFieldText" >       
        <input id="link" class="field" type="text" size="70" name="link" value="<%=WebUtil.display(_linkValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_linkHover_field" class="formFieldFrame">
    <div id="styleConfigForm_linkHover_label" class="formLabel" >Link Hover </div>
    <div id="styleConfigForm_linkHover_text" class="formFieldText" >       
        <input id="linkHover" class="field" type="text" size="70" name="linkHover" value="<%=WebUtil.display(_link_hoverValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_linkActive_field" class="formFieldFrame">
    <div id="styleConfigForm_linkActive_label" class="formLabel" >Link Active </div>
    <div id="styleConfigForm_linkActive_text" class="formFieldText" >       
        <input id="linkActive" class="field" type="text" size="70" name="linkActive" value="<%=WebUtil.display(_link_activeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_height_field" class="formFieldFrame">
    <div id="styleConfigForm_height_label" class="formLabel" >Height </div>
    <div id="styleConfigForm_height_text" class="formFieldText" >       
        <input id="height" class="field" type="text" size="70" name="height" value="<%=WebUtil.display(_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_width_field" class="formFieldFrame">
    <div id="styleConfigForm_width_label" class="formLabel" >Width </div>
    <div id="styleConfigForm_width_text" class="formFieldText" >       
        <input id="width" class="field" type="text" size="70" name="width" value="<%=WebUtil.display(_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_isTable_field" class="formFieldFrame">
    <div id="styleConfigForm_isTable_label" class="formLabel" >Is Table </div>
    <div id="styleConfigForm_isTable_text" class="formFieldText" >       
        <input id="isTable" class="field" type="text" size="70" name="isTable" value="<%=WebUtil.display(_is_tableValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderCollapse_field" class="formFieldFrame">
    <div id="styleConfigForm_borderCollapse_label" class="formLabel" >Border Collapse </div>
    <div id="styleConfigForm_borderCollapse_text" class="formFieldText" >       
        <input id="borderCollapse" class="field" type="text" size="70" name="borderCollapse" value="<%=WebUtil.display(_border_collapseValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_borderSpacing_field" class="formFieldFrame">
    <div id="styleConfigForm_borderSpacing_label" class="formLabel" >Border Spacing </div>
    <div id="styleConfigForm_borderSpacing_text" class="formFieldText" >       
        <input id="borderSpacing" class="field" type="text" size="70" name="borderSpacing" value="<%=WebUtil.display(_border_spacingValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_trStyleIds_field" class="formFieldFrame">
    <div id="styleConfigForm_trStyleIds_label" class="formLabel" >Tr Style Ids </div>
    <div id="styleConfigForm_trStyleIds_text" class="formFieldText" >       
        <input id="trStyleIds" class="field" type="text" size="70" name="trStyleIds" value="<%=WebUtil.display(_tr_style_idsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="styleConfigForm_tdStyleIds_field" class="formFieldFrame">
    <div id="styleConfigForm_tdStyleIds_label" class="formLabel" >Td Style Ids </div>
    <div id="styleConfigForm_tdStyleIds_text" class="formFieldText" >       
        <input id="tdStyleIds" class="field" type="text" size="70" name="tdStyleIds" value="<%=WebUtil.display(_td_style_idsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>






		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_style_config_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
