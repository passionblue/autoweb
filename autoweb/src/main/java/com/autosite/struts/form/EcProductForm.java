package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EcProductForm extends ActionForm {

	private String _CategoryId;             
	private String _FactorySku;             
	private String _SiteSku;             
	private String _AltSku;             
	private String _ItemCode;             
	private String _Maker;             
	private String _Brand;             
	private String _Name;             
	private String _SubName;             
	private String _ShortDescription;             
	private String _Description;             
	private String _Description2;             
	private String _ImageUrl;             
	private String _Color;             
	private String _Size;             
	private String _Msrp;             
	private String _MsrpCurrency;             
	private String _SalePrice;             
	private String _SaleEnds;             
	private String _Soldout;             
	private String _Hide;             
	private String _PromoNote;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getCategoryId() {
        return _CategoryId;
    }

    public void setCategoryId(String CategoryId) {
        this._CategoryId = CategoryId;
    }    

    public String getFactorySku() {
        return _FactorySku;
    }

    public void setFactorySku(String FactorySku) {
        this._FactorySku = FactorySku;
    }    

    public String getSiteSku() {
        return _SiteSku;
    }

    public void setSiteSku(String SiteSku) {
        this._SiteSku = SiteSku;
    }    

    public String getAltSku() {
        return _AltSku;
    }

    public void setAltSku(String AltSku) {
        this._AltSku = AltSku;
    }    

    public String getItemCode() {
        return _ItemCode;
    }

    public void setItemCode(String ItemCode) {
        this._ItemCode = ItemCode;
    }    

    public String getMaker() {
        return _Maker;
    }

    public void setMaker(String Maker) {
        this._Maker = Maker;
    }    

    public String getBrand() {
        return _Brand;
    }

    public void setBrand(String Brand) {
        this._Brand = Brand;
    }    

    public String getName() {
        return _Name;
    }

    public void setName(String Name) {
        this._Name = Name;
    }    

    public String getSubName() {
        return _SubName;
    }

    public void setSubName(String SubName) {
        this._SubName = SubName;
    }    

    public String getShortDescription() {
        return _ShortDescription;
    }

    public void setShortDescription(String ShortDescription) {
        this._ShortDescription = ShortDescription;
    }    

    public String getDescription() {
        return _Description;
    }

    public void setDescription(String Description) {
        this._Description = Description;
    }    

    public String getDescription2() {
        return _Description2;
    }

    public void setDescription2(String Description2) {
        this._Description2 = Description2;
    }    

    public String getImageUrl() {
        return _ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this._ImageUrl = ImageUrl;
    }    

    public String getColor() {
        return _Color;
    }

    public void setColor(String Color) {
        this._Color = Color;
    }    

    public String getSize() {
        return _Size;
    }

    public void setSize(String Size) {
        this._Size = Size;
    }    

    public String getMsrp() {
        return _Msrp;
    }

    public void setMsrp(String Msrp) {
        this._Msrp = Msrp;
    }    

    public String getMsrpCurrency() {
        return _MsrpCurrency;
    }

    public void setMsrpCurrency(String MsrpCurrency) {
        this._MsrpCurrency = MsrpCurrency;
    }    

    public String getSalePrice() {
        return _SalePrice;
    }

    public void setSalePrice(String SalePrice) {
        this._SalePrice = SalePrice;
    }    

    public String getSaleEnds() {
        return _SaleEnds;
    }

    public void setSaleEnds(String SaleEnds) {
        this._SaleEnds = SaleEnds;
    }    

    public String getSoldout() {
        return _Soldout;
    }

    public void setSoldout(String Soldout) {
        this._Soldout = Soldout;
    }    

    public String getHide() {
        return _Hide;
    }

    public void setHide(String Hide) {
        this._Hide = Hide;
    }    

    public String getPromoNote() {
        return _PromoNote;
    }

    public void setPromoNote(String PromoNote) {
        this._PromoNote = PromoNote;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}