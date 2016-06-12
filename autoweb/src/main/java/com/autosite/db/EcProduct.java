package com.autosite.db;

import java.sql.Timestamp;

/**
 * EcProduct entity. @author MyEclipse Persistence Tools
 */

public class EcProduct extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long categoryId;
    private String factorySku;
    private String siteSku;
    private String altSku;
    private String itemCode;
    private String maker;
    private String brand;
    private String name;
    private String subName;
    private String shortDescription;
    private String description;
    private String description2;
    private String imageUrl;
    private String color;
    private String size;
    private double msrp;
    private String msrpCurrency;
    private double salePrice;
    private int saleEnds;
    private int soldout;
    private int hide;
    private String promoNote;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public EcProduct() {
    }

    /** minimal constructor */
    public EcProduct(long siteId, String factorySku, String siteSku, String maker, String brand, String name, String description, double msrp, String msrpCurrency, Timestamp timeCreated,
            Timestamp timeUpdated) {
        this.siteId = siteId;
        this.factorySku = factorySku;
        this.siteSku = siteSku;
        this.maker = maker;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.msrp = msrp;
        this.msrpCurrency = msrpCurrency;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    /** full constructor */
    public EcProduct(long siteId, long categoryId, String factorySku, String siteSku, String altSku, String itemCode, String maker, String brand, String name, String subName, String shortDescription,
            String description, String description2, String imageUrl, String color, String size, double msrp, String msrpCurrency, double salePrice, int saleEnds, int soldout, int hide,
            String promoNote, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.categoryId = categoryId;
        this.factorySku = factorySku;
        this.siteSku = siteSku;
        this.altSku = altSku;
        this.itemCode = itemCode;
        this.maker = maker;
        this.brand = brand;
        this.name = name;
        this.subName = subName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.description2 = description2;
        this.imageUrl = imageUrl;
        this.color = color;
        this.size = size;
        this.msrp = msrp;
        this.msrpCurrency = msrpCurrency;
        this.salePrice = salePrice;
        this.saleEnds = saleEnds;
        this.soldout = soldout;
        this.hide = hide;
        this.promoNote = promoNote;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFactorySku() {
        return this.factorySku;
    }

    public void setFactorySku(String factorySku) {
        this.factorySku = factorySku;
    }

    public String getSiteSku() {
        return this.siteSku;
    }

    public void setSiteSku(String siteSku) {
        this.siteSku = siteSku;
    }

    public String getAltSku() {
        return this.altSku;
    }

    public void setAltSku(String altSku) {
        this.altSku = altSku;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return this.description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getMsrp() {
        return this.msrp;
    }

    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }

    public String getMsrpCurrency() {
        return this.msrpCurrency;
    }

    public void setMsrpCurrency(String msrpCurrency) {
        this.msrpCurrency = msrpCurrency;
    }

    public double getSalePrice() {
        return this.salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getSaleEnds() {
        return this.saleEnds;
    }

    public void setSaleEnds(int saleEnds) {
        this.saleEnds = saleEnds;
    }

    public int getSoldout() {
        return this.soldout;
    }

    public void setSoldout(int soldout) {
        this.soldout = soldout;
    }

    public int getHide() {
        return this.hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public String getPromoNote() {
        return this.promoNote;
    }

    public void setPromoNote(String promoNote) {
        this.promoNote = promoNote;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

}
