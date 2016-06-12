package com.autosite.ec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.autosite.db.EcProduct;
import com.autosite.ds.EcProductDS;

public class EcProductPriceWrapper {
    
    protected EcProduct m_product;
    protected int m_numSizeVariation;
    protected int m_numColorVariation;
    
    protected Map m_sizeVarMap;
    protected Map m_colorVarMap;
    
    
    private static Logger m_logger = Logger.getLogger(EcProductPriceWrapper.class);
    public EcProductPriceWrapper(EcProduct product){
        m_product = product;
        
        m_sizeVarMap = new HashMap();
        m_colorVarMap = new HashMap();
        
        reset();
        
    }
    
    public void setSize(String size){
        
    }
    
    public void setColor(String color){
        
    }
    
    protected void reset(){
        
        if ( m_product.getSize() != null){
            
            String sizeVariations []= m_product.getSize().split(",");
            m_numSizeVariation = sizeVariations.length;
            for(int i = 0; i <sizeVariations.length;i++){
                
                String item = sizeVariations[i].trim();
                int pos = item.indexOf("##");
                
                VariationEntry ve = new VariationEntry();
                if ( pos < 0){
                    ve.m_text = item;
                    ve.m_offset = 0.0;
                } else {
                    ve.m_text = item.substring(0,pos);
                    
                    try{
                        ve.m_offset = Double.parseDouble(item.substring(pos+2));
                    }
                    catch (NumberFormatException e) {
                        m_logger.error(e.getMessage(), e);
                        ve.m_offset = 0.0;
                    }
                }
                
                m_sizeVarMap.put(ve.m_text, ve);

                m_logger.debug("Variation added " + ve.m_text + " offset=" + ve.m_offset);
            }
        }
        
        if ( m_product.getColor() != null){
            
            String colorVariations []= m_product.getColor().split(",");
            m_numColorVariation = colorVariations.length;
            for(int i = 0; i <colorVariations.length;i++){
                
                String item = colorVariations[i].trim();
                int pos = item.indexOf("##");
                
                VariationEntry ve = new VariationEntry();
                if ( pos < 0){
                    ve.m_text = item;
                    ve.m_offset = 0.0;
                } else {
                    ve.m_text = item.substring(0,pos);
                    
                    try{
                        ve.m_offset = Double.parseDouble(item.substring(pos+2));
                    }
                    catch (NumberFormatException e) {
                        m_logger.error(e.getMessage(), e);
                        ve.m_offset = 0.0;
                    }
                }

                m_colorVarMap.put(ve.m_text, ve);
                
                m_logger.debug("Variation added " + ve.m_text + " offset=" + ve.m_offset);
                
            }
        }
        
    }
    
    public int getNumSizeVariation(){
        return m_numSizeVariation;
    }
    
    public int getNumColorVariation(){
        return m_numColorVariation;
    }
    
    public boolean isValidSize(String size){
        if (size == null) return false;
        return m_sizeVarMap.containsKey(size.trim());
    }

    public boolean isValidColor(String color){
        if (color == null) return false;
        return m_colorVarMap.containsKey(color.trim());
    }
    
    public List getSizeVarList(){
        return new ArrayList(m_sizeVarMap.keySet());
    }

    public List getColorVarList(){
        return new ArrayList(m_colorVarMap.keySet());
    }
    
    public double getSizeOffset(String var){
        if (var == null) return 0.0;
        if ( m_sizeVarMap.containsKey(var.trim())){
            
            VariationEntry entry = (VariationEntry) m_sizeVarMap.get(var.trim());
            return entry.m_offset;
        }
        
        return 0.0;
    }
    
    public double getColorOffset(String var){
        if (var == null) return 0.0;
        if ( m_colorVarMap.containsKey(var.trim())){
            
            VariationEntry entry = (VariationEntry) m_colorVarMap.get(var.trim());
            return entry.m_offset;
        }
        
        return 0.0;
    }
    
    public double getEffectivePrice(){
        
        if ( m_product.getSalePrice() > 0.0 && m_product.getSaleEnds() == 0){
            return m_product.getSalePrice();
        }
        
        return m_product.getMsrp();
    }
    
    
    public static void main(String[] args) {
        
        EcProduct prod = EcProductDS.getInstance().getById((long)12);
        
        EcProductPriceWrapper wrapper = new EcProductPriceWrapper(prod);
        
        for (Iterator iterator = wrapper.getSizeVarList().iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            
            System.out.println(string);
            System.out.println(wrapper.getSizeOffset(string));
        }
        
    }
    
}

class VariationEntry{
    protected String m_text;
    protected double m_offset;
}
