/*
 * Created on Nov 11, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools;

public class DosSleep {

    public static void main(String args[]) {
        
        String arg = "10";
        if (args.length >= 1) {
            arg = args[0];
        }
        
        int secInSleep = Integer.parseInt(arg);
        
        try {
            Thread.sleep(secInSleep*1000);
        }
        catch (InterruptedException e) {
        }
    }
}
