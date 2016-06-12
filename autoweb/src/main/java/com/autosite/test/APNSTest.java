package com.autosite.test;

import java.io.File;

import javapns.Push;
import javapns.notification.PushedNotifications;

public class APNSTest {

    public static void main(String[] args) throws Exception{
        
        File f = new File("c:\\work\\autosite\\PushGo.p12");
        System.out.println(f.exists());
        PushedNotifications n = Push.alert("Hello World!", f, "joshua", false, "fb6b71488c8aa12d85f6f14dbf58a1e68e163e1f5c7660417ecf15616977311c");
       // Push.badge("Hello World!", "PushGo.p12", "joshua", false, "fb6b71488c8aa12d85f6f14dbf58a1e68e163e1f5c7660417ecf15616977311c");

        
        System.out.println("XXXX"+ n);
        
    }
    
}
