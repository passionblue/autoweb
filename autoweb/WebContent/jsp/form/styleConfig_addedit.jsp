<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	//AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	AutositeSessionContext sessionContext = SessionWrapper.wrapIt(request, site.getId()).getSessionCtx();

    Map reqParams = (Map) request.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    StyleConfig _StyleConfig = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_StyleConfig = StyleConfigDS.getInstance().getById(id);
		if ( _StyleConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _StyleConfig = new StyleConfig();// StyleConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request));

	}	else {
    	_wpId = WebProcManager.registerWebProcess(PageViewUtil.getCurrentViewAlias(request), true);
	}


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "style_config_home";

	String backPage = (String) reqParams.get("backPage");    
	String extPage = (String) reqParams.get("extPage");    

    String _style_keyValue= (reqParams.get("styleKey")==null?WebUtil.display(_StyleConfig.getStyleKey()):WebUtil.display((String)reqParams.get("styleKey")));
    String _style_useValue= (reqParams.get("styleUse")==null?WebUtil.display(_StyleConfig.getStyleUse()):WebUtil.display((String)reqParams.get("styleUse")));
    String _is_globalValue= (reqParams.get("isGlobal")==null?WebUtil.display(_StyleConfig.getIsGlobal()):WebUtil.display((String)reqParams.get("isGlobal")));
    String _id_classValue= (reqParams.get("idClass")==null?WebUtil.display(_StyleConfig.getIdClass()):WebUtil.display((String)reqParams.get("idClass")));
    String _is_idValue= (reqParams.get("isId")==null?WebUtil.display(_StyleConfig.getIsId()):WebUtil.display((String)reqParams.get("isId")));
    String _colorValue= (reqParams.get("color")==null?WebUtil.display(_StyleConfig.getColor()):WebUtil.display((String)reqParams.get("color")));
    String _bg_colorValue= (reqParams.get("bgColor")==null?WebUtil.display(_StyleConfig.getBgColor()):WebUtil.display((String)reqParams.get("bgColor")));
    String _bg_imageValue= (reqParams.get("bgImage")==null?WebUtil.display(_StyleConfig.getBgImage()):WebUtil.display((String)reqParams.get("bgImage")));
    String _bg_repeatValue= (reqParams.get("bgRepeat")==null?WebUtil.display(_StyleConfig.getBgRepeat()):WebUtil.display((String)reqParams.get("bgRepeat")));
    String _bg_attachValue= (reqParams.get("bgAttach")==null?WebUtil.display(_StyleConfig.getBgAttach()):WebUtil.display((String)reqParams.get("bgAttach")));
    String _bg_positionValue= (reqParams.get("bgPosition")==null?WebUtil.display(_StyleConfig.getBgPosition()):WebUtil.display((String)reqParams.get("bgPosition")));
    String _text_alignValue= (reqParams.get("textAlign")==null?WebUtil.display(_StyleConfig.getTextAlign()):WebUtil.display((String)reqParams.get("textAlign")));
    String _font_familyValue= (reqParams.get("fontFamily")==null?WebUtil.display(_StyleConfig.getFontFamily()):WebUtil.display((String)reqParams.get("fontFamily")));
    String _font_sizeValue= (reqParams.get("fontSize")==null?WebUtil.display(_StyleConfig.getFontSize()):WebUtil.display((String)reqParams.get("fontSize")));
    String _font_styleValue= (reqParams.get("fontStyle")==null?WebUtil.display(_StyleConfig.getFontStyle()):WebUtil.display((String)reqParams.get("fontStyle")));
    String _font_variantValue= (reqParams.get("fontVariant")==null?WebUtil.display(_StyleConfig.getFontVariant()):WebUtil.display((String)reqParams.get("fontVariant")));
    String _font_weightValue= (reqParams.get("fontWeight")==null?WebUtil.display(_StyleConfig.getFontWeight()):WebUtil.display((String)reqParams.get("fontWeight")));
    String _border_directionValue= (reqParams.get("borderDirection")==null?WebUtil.display(_StyleConfig.getBorderDirection()):WebUtil.display((String)reqParams.get("borderDirection")));
    String _border_widthValue= (reqParams.get("borderWidth")==null?WebUtil.display(_StyleConfig.getBorderWidth()):WebUtil.display((String)reqParams.get("borderWidth")));
    String _border_styleValue= (reqParams.get("borderStyle")==null?WebUtil.display(_StyleConfig.getBorderStyle()):WebUtil.display((String)reqParams.get("borderStyle")));
    String _border_colorValue= (reqParams.get("borderColor")==null?WebUtil.display(_StyleConfig.getBorderColor()):WebUtil.display((String)reqParams.get("borderColor")));
    String _marginValue= (reqParams.get("margin")==null?WebUtil.display(_StyleConfig.getMargin()):WebUtil.display((String)reqParams.get("margin")));
    String _paddingValue= (reqParams.get("padding")==null?WebUtil.display(_StyleConfig.getPadding()):WebUtil.display((String)reqParams.get("padding")));
    String _list_style_typeValue= (reqParams.get("listStyleType")==null?WebUtil.display(_StyleConfig.getListStyleType()):WebUtil.display((String)reqParams.get("listStyleType")));
    String _list_style_positionValue= (reqParams.get("listStylePosition")==null?WebUtil.display(_StyleConfig.getListStylePosition()):WebUtil.display((String)reqParams.get("listStylePosition")));
    String _list_style_imageValue= (reqParams.get("listStyleImage")==null?WebUtil.display(_StyleConfig.getListStyleImage()):WebUtil.display((String)reqParams.get("listStyleImage")));
    String _floatingValue= (reqParams.get("floating")==null?WebUtil.display(_StyleConfig.getFloating()):WebUtil.display((String)reqParams.get("floating")));
    String _extra_style_strValue= (reqParams.get("extraStyleStr")==null?WebUtil.display(_StyleConfig.getExtraStyleStr()):WebUtil.display((String)reqParams.get("extraStyleStr")));
    String _item_style_strValue= (reqParams.get("itemStyleStr")==null?WebUtil.display(_StyleConfig.getItemStyleStr()):WebUtil.display((String)reqParams.get("itemStyleStr")));
    String _linkValue= (reqParams.get("link")==null?WebUtil.display(_StyleConfig.getLink()):WebUtil.display((String)reqParams.get("link")));
    String _link_hoverValue= (reqParams.get("linkHover")==null?WebUtil.display(_StyleConfig.getLinkHover()):WebUtil.display((String)reqParams.get("linkHover")));
    String _link_activeValue= (reqParams.get("linkActive")==null?WebUtil.display(_StyleConfig.getLinkActive()):WebUtil.display((String)reqParams.get("linkActive")));
    String _heightValue= (reqParams.get("height")==null?WebUtil.display(_StyleConfig.getHeight()):WebUtil.display((String)reqParams.get("height")));
    String _widthValue= (reqParams.get("width")==null?WebUtil.display(_StyleConfig.getWidth()):WebUtil.display((String)reqParams.get("width")));
    String _is_tableValue= (reqParams.get("isTable")==null?WebUtil.display(_StyleConfig.getIsTable()):WebUtil.display((String)reqParams.get("isTable")));
    String _border_collapseValue= (reqParams.get("borderCollapse")==null?WebUtil.display(_StyleConfig.getBorderCollapse()):WebUtil.display((String)reqParams.get("borderCollapse")));
    String _border_spacingValue= (reqParams.get("borderSpacing")==null?WebUtil.display(_StyleConfig.getBorderSpacing()):WebUtil.display((String)reqParams.get("borderSpacing")));
    String _tr_style_idsValue= (reqParams.get("trStyleIds")==null?WebUtil.display(_StyleConfig.getTrStyleIds()):WebUtil.display((String)reqParams.get("trStyleIds")));
    String _td_style_idsValue= (reqParams.get("tdStyleIds")==null?WebUtil.display(_StyleConfig.getTdStyleIds()):WebUtil.display((String)reqParams.get("tdStyleIds")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleConfig.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleConfig.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    String pagestamp = "style_config_" + System.nanoTime();
%> 

<br>
<div id="styleConfigForm" class="formFrame">
<div id="styleConfigFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="styleConfigForm_Form" method="POST" action="/styleConfigAction.html" id="styleConfigForm_Form">





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







<div class="submitFrame">

    <div id="styleConfigForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.styleConfigForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="styleConfigForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="styleConfigForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="styleConfigForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

<% if (!WebUtil.isNull(backPage)) {%>
    <div id="styleConfigForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();">Back</a>
    </div>      
<%} %>    

<% if (!WebUtil.isNull(extPage)) {%>
    <div id="styleConfigForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      
<%} %>    

    <div id="styleConfigForm_submit_back" class="formButton" >       
        <a href="javascript:submit_back_<%=pagestamp%>();"><%= WebUtil.capitalize("back") %></a>
    </div>      
    <div id="styleConfigForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_ext_<%=pagestamp%>();"><%= WebUtil.capitalize("ext") %></a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_StyleConfig.getId()%>">

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
<INPUT TYPE="HIDDEN" NAME="fromto" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="prv_backPage" value="<%= SessionWrapper.wrapIt(request, site.getId()).getViewPage().getAlias() %>">
<INPUT TYPE="HIDDEN" NAME="_reqhid" value="<%= WebUtil.display(SessionWrapper.wrapIt(request, site.getId()).getRequestHandleId()) %>">
</form>
</div> 				 
</div> <!-- form -->

<br/>
<a href="/v_style_config_home.html">home</a> | <a href="/v_style_config_home.html">home</a> | <a href="/v_style_config_home.html">home</a>
<br/>
<br/>



<%
	List list_StyleConfig = new ArrayList();
	StyleConfigDS ds_StyleConfig = StyleConfigDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_StyleConfig = ds_StyleConfig.getAll();
	else		
    	list_StyleConfig = ds_StyleConfig.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_StyleConfig, numDisplayInPage, listPage);

	list_StyleConfig = PagingUtil.getPagedList(pagingInfo, list_StyleConfig);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Style Key </td> 
    <td class="columnTitle">  Style Use </td> 
    <td class="columnTitle">  Is Global </td> 
    <td class="columnTitle">  Id Class </td> 
    <td class="columnTitle">  Is Id </td> 
    <td class="columnTitle">  Color </td> 
    <td class="columnTitle">  Bg Color </td> 
    <td class="columnTitle">  Bg Image </td> 
    <td class="columnTitle">  Bg Repeat </td> 
    <td class="columnTitle">  Bg Attach </td> 
    <td class="columnTitle">  Bg Position </td> 
    <td class="columnTitle">  Text Align </td> 
    <td class="columnTitle">  Font Family </td> 
    <td class="columnTitle">  Font Size </td> 
    <td class="columnTitle">  Font Style </td> 
    <td class="columnTitle">  Font Variant </td> 
    <td class="columnTitle">  Font Weight </td> 
    <td class="columnTitle">  Border Direction </td> 
    <td class="columnTitle">  Border Width </td> 
    <td class="columnTitle">  Border Style </td> 
    <td class="columnTitle">  Border Color </td> 
    <td class="columnTitle">  Margin </td> 
    <td class="columnTitle">  Padding </td> 
    <td class="columnTitle">  List Style Type </td> 
    <td class="columnTitle">  List Style Position </td> 
    <td class="columnTitle">  List Style Image </td> 
    <td class="columnTitle">  Floating </td> 
    <td class="columnTitle">  Extra Style Str </td> 
    <td class="columnTitle">  Item Style Str </td> 
    <td class="columnTitle">  Link </td> 
    <td class="columnTitle">  Link Hover </td> 
    <td class="columnTitle">  Link Active </td> 
    <td class="columnTitle">  Height </td> 
    <td class="columnTitle">  Width </td> 
    <td class="columnTitle">  Is Table </td> 
    <td class="columnTitle">  Border Collapse </td> 
    <td class="columnTitle">  Border Spacing </td> 
    <td class="columnTitle">  Tr Style Ids </td> 
    <td class="columnTitle">  Td Style Ids </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_StyleConfig.iterator();iter.hasNext();) {
        StyleConfig o_StyleConfig = (StyleConfig) iter.next();
%>

<TR id="tableRow<%= o_StyleConfig.getId()%>">
    <td> <%= o_StyleConfig.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_StyleConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_StyleConfig.getStyleKey()  %> </td>
	<td> <%= o_StyleConfig.getStyleUse()  %> </td>
	<td> <%= o_StyleConfig.getIsGlobal()  %> </td>
	<td> <%= o_StyleConfig.getIdClass()  %> </td>
	<td> <%= o_StyleConfig.getIsId()  %> </td>
	<td> <%= o_StyleConfig.getColor()  %> </td>
	<td> <%= o_StyleConfig.getBgColor()  %> </td>
	<td> <%= o_StyleConfig.getBgImage()  %> </td>
	<td> <%= o_StyleConfig.getBgRepeat()  %> </td>
	<td> <%= o_StyleConfig.getBgAttach()  %> </td>
	<td> <%= o_StyleConfig.getBgPosition()  %> </td>
	<td> <%= o_StyleConfig.getTextAlign()  %> </td>
	<td> <%= o_StyleConfig.getFontFamily()  %> </td>
	<td> <%= o_StyleConfig.getFontSize()  %> </td>
	<td> <%= o_StyleConfig.getFontStyle()  %> </td>
	<td> <%= o_StyleConfig.getFontVariant()  %> </td>
	<td> <%= o_StyleConfig.getFontWeight()  %> </td>
	<td> <%= o_StyleConfig.getBorderDirection()  %> </td>
	<td> <%= o_StyleConfig.getBorderWidth()  %> </td>
	<td> <%= o_StyleConfig.getBorderStyle()  %> </td>
	<td> <%= o_StyleConfig.getBorderColor()  %> </td>
	<td> <%= o_StyleConfig.getMargin()  %> </td>
	<td> <%= o_StyleConfig.getPadding()  %> </td>
	<td> <%= o_StyleConfig.getListStyleType()  %> </td>
	<td> <%= o_StyleConfig.getListStylePosition()  %> </td>
	<td> <%= o_StyleConfig.getListStyleImage()  %> </td>
	<td> <%= o_StyleConfig.getFloating()  %> </td>
	<td> <%= o_StyleConfig.getExtraStyleStr()  %> </td>
	<td> <%= o_StyleConfig.getItemStyleStr()  %> </td>
	<td> <%= o_StyleConfig.getLink()  %> </td>
	<td> <%= o_StyleConfig.getLinkHover()  %> </td>
	<td> <%= o_StyleConfig.getLinkActive()  %> </td>
	<td> <%= o_StyleConfig.getHeight()  %> </td>
	<td> <%= o_StyleConfig.getWidth()  %> </td>
	<td> <%= o_StyleConfig.getIsTable()  %> </td>
	<td> <%= o_StyleConfig.getBorderCollapse()  %> </td>
	<td> <%= o_StyleConfig.getBorderSpacing()  %> </td>
	<td> <%= o_StyleConfig.getTrStyleIds()  %> </td>
	<td> <%= o_StyleConfig.getTdStyleIds()  %> </td>
	<td> <%= o_StyleConfig.getTimeCreated()  %> </td>
	<td> <%= o_StyleConfig.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_style_config_form('<%=o_StyleConfig.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/styleConfigAction.html?del=true&id=<%=o_StyleConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_StyleConfig.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_style_config_form('<%=o_StyleConfig.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function open_edit_style_config_form(target){
		location.href='/v_style_config_form.html?cmd=edit&prv_returnPage=<%= PageViewUtil.getCurrentViewAlias(request) %>&id=' + target + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function delete_style_config_form(target){
		javascript:sendFormAjaxSimple('/styleConfigAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus' + '<%=HttpUrlUtil.getCommonUrlAppends(request)%>', false, updateVal,target);
	}


</script>




<script type="text/javascript">
	$(document).ready(function(){
		setInterval(function(){
			$ .ajax({
			   type: "GET",
		   		url: "/styleConfigAction.html?ajxr=hb",
		  		success: function(msg){ 
		     		//alert( "Value Successfully Changed");
		   		}
	 		});
		}, 300000);

//		setTimeout(function(){
//		}, 10000);
	});

	function submit_cancel_<%=pagestamp%>(){
		//alert("submit_cancel_");		
		//location.href='/moveTo.html?dest=<%=cancelPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=cancel<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	
	function submit_back_<%=pagestamp%>(){
		//alert("submit_back_");		
		//location.href='/moveTo.html?dest=<%=backPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_extent_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=extent<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}

	function submit_back_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=back<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
	function submit_ext_<%=pagestamp%>(){
		//alert("submit_extent_");		
		//location.href='/moveTo.html?dest=<%=extPage%><%=HttpUrlUtil.getCommonUrlAppends(request)%>';
		location.href='/fowardTo.html?cmd=ext<%=HttpUrlUtil.getCommonUrlAppends(request)%>';
	}
</script>
