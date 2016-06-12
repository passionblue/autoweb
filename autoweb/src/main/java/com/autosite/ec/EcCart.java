package com.autosite.ec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class EcCart {
    
    protected List m_items = new ArrayList();
    
    protected int m_totalProductCount;
    protected double m_total;
    
    protected String m_serial;
    protected long m_created;
    
    protected Map m_mapByItemId = new HashMap();
    
    protected long m_userId = 0;
    protected String m_RPCI;
    
    public EcCart(){
        m_created = System.currentTimeMillis();
        m_serial = System.currentTimeMillis()+":"+System.nanoTime();
    }

    public synchronized void recalculate(){
        double total  = 0.0;
        int prodCount = 0;
        
        for (Iterator iterator = m_items.iterator(); iterator.hasNext();) {
            EcCartItem item = (EcCartItem) iterator.next();

            total +=item.getOrderPirce()*item.getCount();
            prodCount += item.getCount();
        }
        
        m_totalProductCount = prodCount;
        m_total = total;
        
    }
    
    public synchronized List getCartItems(){
        return new ArrayList(m_items);
    }
    
    public synchronized int getNumCartItem(){
        return m_items.size();
    }
    
    public synchronized boolean removeCartItem(EcCartItem itemToRemove){
        
        boolean result = false;
        for (Iterator iterator = m_items.iterator(); iterator.hasNext();) {
            EcCartItem item = (EcCartItem) iterator.next();
            
            if (item.getSerial().equals(itemToRemove.getSerial())){
                m_items.remove(item);
                result = true;
                break;
            }
        }

        m_mapByItemId.remove(itemToRemove.getSerial());
        recalculate();
        return result;
    }
    
    public synchronized void addCartItem(EcCartItem item) throws Exception{
    
        EcCartItem dup = findDuplicateItem(item);
        if (dup != null) 
            dup.merge(item);
        else 
            m_items.add(item);

        m_total += item.getOrderPirce()*item.getCount();
        m_totalProductCount += item.getCount();
        m_logger.info("Cart Item added to Cart. " + item.getProduct().getId() + ",price=" + item.getOrderPirce() + ",qty=" + item.getColor() + ",current Total=" + m_total + ",num_item=" + m_items.size());
        
        m_mapByItemId.put(item.getSerial(), item);
    }
    
    // 
    public synchronized EcCartItem findDuplicateItem(EcCartItem item){
        
        for (Iterator iterator = m_items.iterator(); iterator.hasNext();) {
            EcCartItem it = (EcCartItem) iterator.next();

            if (it.equals(item)){
                return it;
            }
        }
        
        return null;
    }
    
    public synchronized EcCartItem getItem(String itemSerial){
        if (itemSerial == null) return null;
        return (EcCartItem) m_mapByItemId.get(itemSerial);
    }
    
    public synchronized double getTotalPrice(){
        return m_total;
    }
    
    public long getUserId() {
        return m_userId;
    }

    public void setUserId(long userId) {
        m_userId = userId;
    }

    public String getRPCI() {
        return m_RPCI;
    }

    public void setRPCI(String rpci) {
        m_RPCI = rpci;
    }
    
    public String getSerial(){
        return m_serial;
    }
    
    public String toString(){
        return "Serial=" + m_serial + "   RPIC=" + m_RPCI + "  USER=" + m_userId + ", Num Items=" + getNumCartItem() + ", total Price=" + getTotalPrice();
    }

    private static Logger m_logger = Logger.getLogger(EcCart.class);
}
