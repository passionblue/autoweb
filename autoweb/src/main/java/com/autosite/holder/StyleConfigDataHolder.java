package com.autosite.holder;

import java.sql.Timestamp;
import java.sql.Date;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.util.html.HtmlGenRow;
import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.autosite.db.StyleConfig;


public class StyleConfigDataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("styleKey", new Integer(0));
        fieldsMap.put("styleUse", new Integer(1));
        fieldsMap.put("isGlobal", new Integer(2));
        fieldsMap.put("idClass", new Integer(3));
        fieldsMap.put("isId", new Integer(4));
        fieldsMap.put("color", new Integer(5));
        fieldsMap.put("bgColor", new Integer(6));
        fieldsMap.put("bgImage", new Integer(7));
        fieldsMap.put("bgRepeat", new Integer(8));
        fieldsMap.put("bgAttach", new Integer(9));
        fieldsMap.put("bgPosition", new Integer(10));
        fieldsMap.put("textAlign", new Integer(11));
        fieldsMap.put("fontFamily", new Integer(12));
        fieldsMap.put("fontSize", new Integer(13));
        fieldsMap.put("fontStyle", new Integer(14));
        fieldsMap.put("fontVariant", new Integer(15));
        fieldsMap.put("fontWeight", new Integer(16));
        fieldsMap.put("borderDirection", new Integer(17));
        fieldsMap.put("borderWidth", new Integer(18));
        fieldsMap.put("borderStyle", new Integer(19));
        fieldsMap.put("borderColor", new Integer(20));
        fieldsMap.put("margin", new Integer(21));
        fieldsMap.put("padding", new Integer(22));
        fieldsMap.put("listStyleType", new Integer(23));
        fieldsMap.put("listStylePosition", new Integer(24));
        fieldsMap.put("listStyleImage", new Integer(25));
        fieldsMap.put("floating", new Integer(26));
        fieldsMap.put("extraStyleStr", new Integer(27));
        fieldsMap.put("itemStyleStr", new Integer(28));
        fieldsMap.put("link", new Integer(29));
        fieldsMap.put("linkHover", new Integer(30));
        fieldsMap.put("linkActive", new Integer(31));
        fieldsMap.put("height", new Integer(32));
        fieldsMap.put("width", new Integer(33));
        fieldsMap.put("isTable", new Integer(34));
        fieldsMap.put("borderCollapse", new Integer(35));
        fieldsMap.put("borderSpacing", new Integer(36));
        fieldsMap.put("trStyleIds", new Integer(37));
        fieldsMap.put("tdStyleIds", new Integer(38));
        fieldsMap.put("timeCreated", new Integer(39));
        fieldsMap.put("timeUpdated", new Integer(40));
    }


	StyleConfig m_styleConfig;

	public StyleConfigDataHolder(StyleConfig _StyleConfig){
		m_styleConfig =  _StyleConfig; 	
	}

	public StyleConfigDataHolder(){
		m_styleConfig =  new StyleConfig(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_styleConfig;
	}


	public long getId(){
		return 	m_styleConfig.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_styleConfig.getSiteId();
	}
	public void setSiteId(long sid){
		m_styleConfig.setSiteId(sid);
	}


	public String getStyleKey(){
		return m_styleConfig.getStyleKey();
	}
	public int getStyleUse(){
		return m_styleConfig.getStyleUse();
	}
	public int getIsGlobal(){
		return m_styleConfig.getIsGlobal();
	}
	public String getIdClass(){
		return m_styleConfig.getIdClass();
	}
	public int getIsId(){
		return m_styleConfig.getIsId();
	}
	public String getColor(){
		return m_styleConfig.getColor();
	}
	public String getBgColor(){
		return m_styleConfig.getBgColor();
	}
	public String getBgImage(){
		return m_styleConfig.getBgImage();
	}
	public String getBgRepeat(){
		return m_styleConfig.getBgRepeat();
	}
	public String getBgAttach(){
		return m_styleConfig.getBgAttach();
	}
	public String getBgPosition(){
		return m_styleConfig.getBgPosition();
	}
	public String getTextAlign(){
		return m_styleConfig.getTextAlign();
	}
	public String getFontFamily(){
		return m_styleConfig.getFontFamily();
	}
	public String getFontSize(){
		return m_styleConfig.getFontSize();
	}
	public String getFontStyle(){
		return m_styleConfig.getFontStyle();
	}
	public String getFontVariant(){
		return m_styleConfig.getFontVariant();
	}
	public String getFontWeight(){
		return m_styleConfig.getFontWeight();
	}
	public String getBorderDirection(){
		return m_styleConfig.getBorderDirection();
	}
	public String getBorderWidth(){
		return m_styleConfig.getBorderWidth();
	}
	public String getBorderStyle(){
		return m_styleConfig.getBorderStyle();
	}
	public String getBorderColor(){
		return m_styleConfig.getBorderColor();
	}
	public String getMargin(){
		return m_styleConfig.getMargin();
	}
	public String getPadding(){
		return m_styleConfig.getPadding();
	}
	public String getListStyleType(){
		return m_styleConfig.getListStyleType();
	}
	public String getListStylePosition(){
		return m_styleConfig.getListStylePosition();
	}
	public String getListStyleImage(){
		return m_styleConfig.getListStyleImage();
	}
	public String getFloating(){
		return m_styleConfig.getFloating();
	}
	public String getExtraStyleStr(){
		return m_styleConfig.getExtraStyleStr();
	}
	public String getItemStyleStr(){
		return m_styleConfig.getItemStyleStr();
	}
	public String getLink(){
		return m_styleConfig.getLink();
	}
	public String getLinkHover(){
		return m_styleConfig.getLinkHover();
	}
	public String getLinkActive(){
		return m_styleConfig.getLinkActive();
	}
	public int getHeight(){
		return m_styleConfig.getHeight();
	}
	public int getWidth(){
		return m_styleConfig.getWidth();
	}
	public int getIsTable(){
		return m_styleConfig.getIsTable();
	}
	public String getBorderCollapse(){
		return m_styleConfig.getBorderCollapse();
	}
	public String getBorderSpacing(){
		return m_styleConfig.getBorderSpacing();
	}
	public String getTrStyleIds(){
		return m_styleConfig.getTrStyleIds();
	}
	public String getTdStyleIds(){
		return m_styleConfig.getTdStyleIds();
	}
	public Timestamp getTimeCreated(){
		return m_styleConfig.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_styleConfig.getTimeUpdated();
	}

	public void setStyleKey(String _styleKey ){
		m_styleConfig.setStyleKey(_styleKey);
	}
	public void setStyleUse(int _styleUse ){
		m_styleConfig.setStyleUse(_styleUse);
	}
	public void setIsGlobal(int _isGlobal ){
		m_styleConfig.setIsGlobal(_isGlobal);
	}
	public void setIdClass(String _idClass ){
		m_styleConfig.setIdClass(_idClass);
	}
	public void setIsId(int _isId ){
		m_styleConfig.setIsId(_isId);
	}
	public void setColor(String _color ){
		m_styleConfig.setColor(_color);
	}
	public void setBgColor(String _bgColor ){
		m_styleConfig.setBgColor(_bgColor);
	}
	public void setBgImage(String _bgImage ){
		m_styleConfig.setBgImage(_bgImage);
	}
	public void setBgRepeat(String _bgRepeat ){
		m_styleConfig.setBgRepeat(_bgRepeat);
	}
	public void setBgAttach(String _bgAttach ){
		m_styleConfig.setBgAttach(_bgAttach);
	}
	public void setBgPosition(String _bgPosition ){
		m_styleConfig.setBgPosition(_bgPosition);
	}
	public void setTextAlign(String _textAlign ){
		m_styleConfig.setTextAlign(_textAlign);
	}
	public void setFontFamily(String _fontFamily ){
		m_styleConfig.setFontFamily(_fontFamily);
	}
	public void setFontSize(String _fontSize ){
		m_styleConfig.setFontSize(_fontSize);
	}
	public void setFontStyle(String _fontStyle ){
		m_styleConfig.setFontStyle(_fontStyle);
	}
	public void setFontVariant(String _fontVariant ){
		m_styleConfig.setFontVariant(_fontVariant);
	}
	public void setFontWeight(String _fontWeight ){
		m_styleConfig.setFontWeight(_fontWeight);
	}
	public void setBorderDirection(String _borderDirection ){
		m_styleConfig.setBorderDirection(_borderDirection);
	}
	public void setBorderWidth(String _borderWidth ){
		m_styleConfig.setBorderWidth(_borderWidth);
	}
	public void setBorderStyle(String _borderStyle ){
		m_styleConfig.setBorderStyle(_borderStyle);
	}
	public void setBorderColor(String _borderColor ){
		m_styleConfig.setBorderColor(_borderColor);
	}
	public void setMargin(String _margin ){
		m_styleConfig.setMargin(_margin);
	}
	public void setPadding(String _padding ){
		m_styleConfig.setPadding(_padding);
	}
	public void setListStyleType(String _listStyleType ){
		m_styleConfig.setListStyleType(_listStyleType);
	}
	public void setListStylePosition(String _listStylePosition ){
		m_styleConfig.setListStylePosition(_listStylePosition);
	}
	public void setListStyleImage(String _listStyleImage ){
		m_styleConfig.setListStyleImage(_listStyleImage);
	}
	public void setFloating(String _floating ){
		m_styleConfig.setFloating(_floating);
	}
	public void setExtraStyleStr(String _extraStyleStr ){
		m_styleConfig.setExtraStyleStr(_extraStyleStr);
	}
	public void setItemStyleStr(String _itemStyleStr ){
		m_styleConfig.setItemStyleStr(_itemStyleStr);
	}
	public void setLink(String _link ){
		m_styleConfig.setLink(_link);
	}
	public void setLinkHover(String _linkHover ){
		m_styleConfig.setLinkHover(_linkHover);
	}
	public void setLinkActive(String _linkActive ){
		m_styleConfig.setLinkActive(_linkActive);
	}
	public void setHeight(int _height ){
		m_styleConfig.setHeight(_height);
	}
	public void setWidth(int _width ){
		m_styleConfig.setWidth(_width);
	}
	public void setIsTable(int _isTable ){
		m_styleConfig.setIsTable(_isTable);
	}
	public void setBorderCollapse(String _borderCollapse ){
		m_styleConfig.setBorderCollapse(_borderCollapse);
	}
	public void setBorderSpacing(String _borderSpacing ){
		m_styleConfig.setBorderSpacing(_borderSpacing);
	}
	public void setTrStyleIds(String _trStyleIds ){
		m_styleConfig.setTrStyleIds(_trStyleIds);
	}
	public void setTdStyleIds(String _tdStyleIds ){
		m_styleConfig.setTdStyleIds(_tdStyleIds);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_styleConfig.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_styleConfig.setTimeUpdated(_timeUpdated);
	}

	// For HtmlGenRow
    public int getColumnCount() {
		return fieldsMap.size();
    }
    public static List getFieldsName(){
        return new ArrayList(fieldsMap.keySet());
    }
    public String getColumnData(String col){
        if(fieldsMap.containsKey(col)){
            return getColumnData(fieldsMap.get(col).intValue());
        } else {
            return null;
        }
    }

    public String getColumnData(int colNum) {

        if ( colNum == 0) return getStyleKey() == null?"":getStyleKey().toString();
        if ( colNum == 1) return String.valueOf(getStyleUse());
        if ( colNum == 2) return String.valueOf(getIsGlobal());
        if ( colNum == 3) return getIdClass() == null?"":getIdClass().toString();
        if ( colNum == 4) return String.valueOf(getIsId());
        if ( colNum == 5) return getColor() == null?"":getColor().toString();
        if ( colNum == 6) return getBgColor() == null?"":getBgColor().toString();
        if ( colNum == 7) return getBgImage() == null?"":getBgImage().toString();
        if ( colNum == 8) return getBgRepeat() == null?"":getBgRepeat().toString();
        if ( colNum == 9) return getBgAttach() == null?"":getBgAttach().toString();
        if ( colNum == 10) return getBgPosition() == null?"":getBgPosition().toString();
        if ( colNum == 11) return getTextAlign() == null?"":getTextAlign().toString();
        if ( colNum == 12) return getFontFamily() == null?"":getFontFamily().toString();
        if ( colNum == 13) return getFontSize() == null?"":getFontSize().toString();
        if ( colNum == 14) return getFontStyle() == null?"":getFontStyle().toString();
        if ( colNum == 15) return getFontVariant() == null?"":getFontVariant().toString();
        if ( colNum == 16) return getFontWeight() == null?"":getFontWeight().toString();
        if ( colNum == 17) return getBorderDirection() == null?"":getBorderDirection().toString();
        if ( colNum == 18) return getBorderWidth() == null?"":getBorderWidth().toString();
        if ( colNum == 19) return getBorderStyle() == null?"":getBorderStyle().toString();
        if ( colNum == 20) return getBorderColor() == null?"":getBorderColor().toString();
        if ( colNum == 21) return getMargin() == null?"":getMargin().toString();
        if ( colNum == 22) return getPadding() == null?"":getPadding().toString();
        if ( colNum == 23) return getListStyleType() == null?"":getListStyleType().toString();
        if ( colNum == 24) return getListStylePosition() == null?"":getListStylePosition().toString();
        if ( colNum == 25) return getListStyleImage() == null?"":getListStyleImage().toString();
        if ( colNum == 26) return getFloating() == null?"":getFloating().toString();
        if ( colNum == 27) return getExtraStyleStr() == null?"":getExtraStyleStr().toString();
        if ( colNum == 28) return getItemStyleStr() == null?"":getItemStyleStr().toString();
        if ( colNum == 29) return getLink() == null?"":getLink().toString();
        if ( colNum == 30) return getLinkHover() == null?"":getLinkHover().toString();
        if ( colNum == 31) return getLinkActive() == null?"":getLinkActive().toString();
        if ( colNum == 32) return String.valueOf(getHeight());
        if ( colNum == 33) return String.valueOf(getWidth());
        if ( colNum == 34) return String.valueOf(getIsTable());
        if ( colNum == 35) return getBorderCollapse() == null?"":getBorderCollapse().toString();
        if ( colNum == 36) return getBorderSpacing() == null?"":getBorderSpacing().toString();
        if ( colNum == 37) return getTrStyleIds() == null?"":getTrStyleIds().toString();
        if ( colNum == 38) return getTdStyleIds() == null?"":getTdStyleIds().toString();
        if ( colNum == 39) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 40) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }
}