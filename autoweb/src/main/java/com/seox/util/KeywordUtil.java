/*
 * Created on Oct 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class KeywordUtil {

    public static boolean KEEP_WORD_ORDER = true; //TODO load props from outside or db

    // We could keep the order or just normalize the keyword in natural order.
    public static String normalizeKeyword(String string){
        
        if ( string == null ) return null;
        
        Iterator iter = null;
        StringTokenizer tok = new StringTokenizer(string, " ,+");
        
        TreeSet set = new TreeSet();
        List list = new ArrayList();
        while(tok.hasMoreTokens()) {
            String str = tok.nextToken().trim().toLowerCase();
            if (!set.contains(str)) {
                set.add(str);
                list.add(str);
            }
        }

        if (KEEP_WORD_ORDER ) {
            iter = list.iterator();
        } else {
            iter = set.iterator();
        }        
        StringBuffer buffer = new StringBuffer();
        for (;iter.hasNext();) {
            buffer.append(iter.next().toString()).append(" ");
        }
        return buffer.toString().trim();
    }
    
    public static String addUnderscore(String keyword, boolean normalize) {
        if (keyword == null ){
            return null;
        }
        
        String ret = keyword.trim();
        if (normalize) ret = normalizeKeyword(keyword);
        
        ret = ret.replaceAll(" ", "_");
        return ret;
    }

    public static void main(String args[]){
        System.out.println(normalizeKeyword("asdf asdf 4asdfs 2323") + ";");
        System.out.println(addUnderscore("asdf asdf 4asdfs 2323", false) + ";");
        
    }
    
}
