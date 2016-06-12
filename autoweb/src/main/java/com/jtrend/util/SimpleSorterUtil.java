package com.jtrend.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.autosite.db.ChurPayee;
import com.autosite.ds.ChurPayeeDS;

/*
 * mostly created to support sorting menu items on the fly. 
    Along with SorterActor, this class is used as below
    
    List sorted = SimpleSorterUtil.sortedByKey(_listChurPayee_payeeId, new SorterActor<ChurPayee>(){
        public Object getSortable(ChurPayee t){
            return t.getTitle();
        }
    });
 */

public class SimpleSorterUtil {

    public static List sortedByKey(List list, SorterActor a){

        Map m = new TreeMap();
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Object val = iterator.next();
            Object object = a.getSortable(val);
            m.put(object, val);
            System.out.println("Putting " + object + " -> " + val);
        }
        
        return new ArrayList( m.values());
    }
    
    public static void main(String[] args) {

        List _listChurPayee_payeeId = ChurPayeeDS.getInstance().getBySiteId(603);

        List sorted = SimpleSorterUtil.sortedByKey(_listChurPayee_payeeId, new SorterActor<ChurPayee>(){
            public Object getSortable(ChurPayee t){
                return t.getTitle();
            }
        });
        
        for (Iterator iterator = sorted.iterator(); iterator.hasNext();) {
            ChurPayee c = (ChurPayee) iterator.next();
            
            System.out.println(c.getTitle());
            
        }
        
        
    }
}
