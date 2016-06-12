package com.autosite.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.DisplayOrder;
import com.autosite.db.Panel;
import com.autosite.ds.DisplayOrderDS;



public class DisplayOrderManager {
    
    private static Logger m_logger = Logger.getLogger(DisplayOrderManager.class);
    private static DynMenuManager m_instance = new DynMenuManager();;
    public static boolean m_debug = AutositeGlobals.m_debug;

    public static DynMenuManager getInstance() {
        return m_instance;
    }
    
    public List reorder(List list, String orderString){
        Map orderMap = convertToMap(orderString);
        return reorder(list, orderMap);
    }    
    
    public List reorder(List list, long [] array){
        Map orderMap = convertToMap(array);
        return reorder(list, orderMap);
    }    
    
    public List reorder(List list, Map orderIdxMap){

        if (orderIdxMap == null)
            return list;

        List ret = new ArrayList();
        
        BaseAutositeDataObject array []= new BaseAutositeDataObject[list.size()];
        List unordered = new ArrayList();
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            BaseAutositeDataObject d = (BaseAutositeDataObject) iterator.next();

            Long idKey = new Long(d.getId());
            if (orderIdxMap.containsKey(idKey)){
                int idx = ((Integer)orderIdxMap.get(idKey)).intValue();
                array[idx] = d;
            }
        }

        for(int i = 0; i <list.size();i++){
            if (array[i] != null){
                ret.add(array[i]);
            }
        }
        
        for (Iterator iterator2 = unordered.iterator(); iterator2.hasNext();) {
            Object object = (Object) iterator2.next();
            ret.add(object);
        }
        
        return ret;
    }
    
    public Map convertToMap(long array[]){
        Map ret = new HashMap();
        for (int i = 0; i < array.length; i++) {
            ret.put(new Long(array[i]), new Integer(i));
        }
        
        return ret;
    }
    public Map convertToMap(String list){

        long longArray[] = convertToLongArray(list);
        return convertToMap(longArray);
    }

    public static long [] convertToLongArray(String list){
        String sp[] = list.split("-");
        long ret[] = new long[sp.length]; 
        for (int i = 0; i < sp.length; i++) {
            Long.parseLong(sp[i]);
        }
        return ret;
    }
    
    public static String convertToString(long array[]){
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if (i > 0)
                buf.append("-");
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static String createKeyForPanels(long siteId, int columnNum){
        return "panels" + siteId + "." + columnNum;
    }

    public static String createKeyForSitePost(long siteId, long panelId){
        return "siteposts" + siteId + "." + panelId;
    }

    public static String createKeyForMenuOrder(long siteId, long panelId){
        return "menuorder" + siteId + "." + panelId;
    }
    
    public static void main(String[] args) {
    
        DisplayOrder o = DisplayOrderDS.getInstance().getByOrderKey("panels29.2");
        
        System.out.println(DisplayOrderDS.objectToString(o));
        
        long ol [] = convertToLongArray(o.getOrderList());
        
        
        o.setOrderList(convertToString( getSample()));
        o.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
        
        DisplayOrderDS.getInstance().update(o);
    }
    public static void testCreate() {
        
        DisplayOrder o = new DisplayOrder();

        long ex[] = getSample();
        
        String str = convertToString(ex);
        
        String key = createKeyForPanels(29, 2);
        o.setOrderKey(key);
        o.setOrderList(str);
        o.setSiteId(29);

        o.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        o.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
        
        DisplayOrderDS.getInstance().put(o);


        
    }
    
    
    public static long [] getSample(){
 
        long ret[] = new long[10];
        
        for (int i = 0; i < ret.length; i++) {
            ret[i] = System.nanoTime();
        }
        
        return ret;
    }
    
}
