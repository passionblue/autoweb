package com.autosite.util;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.autosite.db.EcPageProductRel;
import com.autosite.ds.EcPageProductRelDS;

public class EcProductUtil {

    public static Set getAddToCategoryIds(long siteId, long prodId){
        
        EcPageProductRelDS pageProdRelDS = EcPageProductRelDS.getInstance();

        Set ret = new HashSet();
        
        List list = pageProdRelDS.getBySiteIdProductId(siteId, prodId);

        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            EcPageProductRel ppRel = (EcPageProductRel) iterator.next();
            ret.add(new Long(ppRel.getCategoryId()));
        }
        
        return ret;
    }
    
    public static void main(String[] args) {
        
        System.out.println(EcProductUtil.getAddToCategoryIds(66, 19));
        
        
        EcPageProductRel  ccPageProductRel = new  EcPageProductRel();
        ccPageProductRel.setCategoryId(7);
        ccPageProductRel.setProductId(19);
        ccPageProductRel.setSiteId(66);
        ccPageProductRel.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        EcPageProductRelDS.getInstance().put(ccPageProductRel);
        
        System.out.println(EcProductUtil.getAddToCategoryIds(66, 19));
        
        
    }
    
    
    
    
}
