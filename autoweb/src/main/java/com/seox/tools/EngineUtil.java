/*
 * Created on Nov 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools;

public class EngineUtil {

    public static String getEngineName(int engine) {
        switch(engine) {
        case 1: return "google";
        case 2: return "yahoo";
        case 3: return "msn";
        default : return "NOTSUPPORT";
        }
    }
    public static int getEngineNum(String engine) {
        if ("google".equals(engine)) return 1;
        else if ( "yahoo".equals(engine)) return 2;
        else if ( "google".equals(engine)) return 3;
        return -1;
    }
}
