package com.autosite.ec;

import org.apache.log4j.Logger;

import com.autosite.db.EcProduct;

public class EcCartItem {

    protected EcProduct m_product;
    protected int m_count;
    protected double m_orderPirce;
    
    protected long m_timeCreated;
    protected String m_serial;
    
    protected String m_size;
    protected String m_color;
    
    
    public EcCartItem(){
        m_timeCreated = System.currentTimeMillis();
        m_serial = System.currentTimeMillis() + ":" + System.nanoTime();
    }
    
    public EcProduct getProduct() {
        return m_product;
    }
    public void setProduct(EcProduct product) {
        m_product = product;
    }
    public int getCount() {
        return m_count;
    }
    public void setCount(int count) {
        m_count = count;
    }
    public long getTimeCreated() {
        return m_timeCreated;
    }
    public void setTimeCreated(long timeCreated) {
        m_timeCreated = timeCreated;
    }

    public double getOrderPirce() {
        return m_orderPirce;
    }

    public void setOrderPirce(double orderPirce) {
        m_orderPirce = orderPirce;
    }

    public String getSerial() {
        return m_serial;
    }

    public void setSerial(String serial) {
        m_serial = serial;
    }

    public String getSize() {
        return m_size;
    }

    public void setSize(String size) {
        m_size = size;
    }

    public String getColor() {
        return m_color;
    }

    public void setColor(String color) {
        m_color = color;
    }
    
    public void merge(EcCartItem itemToMerge) throws Exception{
        
        if (!this.equals(itemToMerge))
            throw new Exception ("Can't merge the item to this. Items differ");
        
        m_count += itemToMerge.getCount();
        m_logger.debug("Cart count updated to " + m_count + " for product " + m_product.getName());
        
    }
    
    public boolean equals(EcCartItem item2){
        
        // Check product
        if (!this.getProduct().getSiteSku().equals(item2.getProduct().getSiteSku())) {
            m_logger.debug("product sku differ this=" + this.getProduct().getSiteSku() + "<->" + item2.getProduct().getSiteSku());
            return false;
        }
    
        // Check variations
        if (this.getSize() != null && !this.getSize().equals(item2.getSize())) { 
            m_logger.debug("product getSize differ this=" + this.getSize() + "<->" + item2.getSize());
            return false;
        }
        
        if ( (this.getSize() == null) && (item2.getSize() != null)) {
            m_logger.debug("product getSize differ this=" + this.getSize() + "<->" + item2.getSize());
            return false;
        }

        if (this.getOrderPirce() != item2.getOrderPirce()){
            m_logger.debug("product getOrderPirce differ this=" + this.getOrderPirce() + "<->" + item2.getOrderPirce());
            return false;
        }
        
        return true;
    }

    private static Logger m_logger = Logger.getLogger(EcCartItem.class);
    
}
