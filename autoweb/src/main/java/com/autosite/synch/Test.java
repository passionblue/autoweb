package com.autosite.synch;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.autosite.db.GenMain;
import com.autosite.db.SynkNamespaceRecord;
import com.autosite.synch.impl.SynkHanlderFactoryImpl;

public class Test {

    
    public static void main(String[] args) throws Exception  {
        
        System.out.println(LocalDateTime.now().toString("yyyyMMddHHmmss0000000"));

        
        SynkHanlderFactoryImpl f = SynkHanlderFactoryImpl.getInstance();
        
        SynkUpdateHandler u1 =  f.getUpdateHandler("1", "test", "1.1.1.1");
        SynkUpdateHandler u2 =  f.getUpdateHandler("2", "test", "1.1.1.2");
        SynkModHandler m1=  f.getModHandler("1", "test", "1.1.1.1");
        SynkModHandler m2 =  f.getModHandler("2", "test", "1.1.1.2");
        
        List<SynkNamespaceRecord> l = u1.getAllRecords();
        
        System.out.println(l);
        
        m1.insert(getData(), "john");
        Thread.sleep(1000);
        m1.insert(getData(), "john");
        Thread.sleep(1000);
        
        GenMain g = getData();
        m1.insert(g, "john");

        
        List<SynkNamespaceRecord> ll = u2.getUpdatesFromLastConfirm();
        
        System.out.println(ll);
        
        
        long highStamp = 0;
        for (SynkNamespaceRecord synkNamespaceRecord : ll) {
            
            if ( synkNamespaceRecord.getStamp() > highStamp ) {
                highStamp = synkNamespaceRecord.getStamp();
            }
        } 
        
        System.out.println(" hi:" + highStamp);
        
        u2.confirm(highStamp, "confirm" + System.currentTimeMillis(), ll.size(), null);
        
        Thread.sleep(1000);
        m1.delete(g.getId(), "john");
        
        List<SynkNamespaceRecord> listAfterDelete = u2.getUpdatesFromLastConfirm();
        
        highStamp = 0;
        for (SynkNamespaceRecord synkNamespaceRecord : listAfterDelete) {
            
            if ( synkNamespaceRecord.getStamp() > highStamp ) {
                highStamp = synkNamespaceRecord.getStamp();
            }
        }              
        
        System.out.println("UPDATES-AFTER-DELETE:"+listAfterDelete);
        
        u2.confirm(highStamp, "confirm-delete-" + System.currentTimeMillis(), listAfterDelete.size(), null);
        
        List<SynkNamespaceRecord> updates = u2.getUpdatesFromLastConfirm();
        System.out.println("UPDATES:"+updates);

        List<SynkNamespaceRecord> updates1 = u1.getUpdatesFromLastConfirm();
        System.out.println("UPDATES:"+updates1);
        
        
        List<SynkNamespaceRecord> updatesAfterDeleteU1 = u1.getUpdatesFromLastConfirm();
        
        highStamp = 0;
        for (SynkNamespaceRecord synkNamespaceRecord : updatesAfterDeleteU1) {
            
            if ( synkNamespaceRecord.getStamp() > highStamp ) {
                highStamp = synkNamespaceRecord.getStamp();
            }
        }      
        
        u1.confirm(highStamp, "confirm-u1", updatesAfterDeleteU1.size(), null);
        
        Thread.sleep(100);
        g = getData();
        m1.insert(g, "john");
        listAfterDelete = u2.getUpdatesFromLastConfirm();
        
        listAfterDelete = u2.getAllRecords();
        
    }
    
    
    public static GenMain getData(){
        
        GenMain g = new GenMain();
        g.setId(System.currentTimeMillis());
        
        return g;
    }
    
}
