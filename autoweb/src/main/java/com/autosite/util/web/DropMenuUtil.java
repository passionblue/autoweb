package com.autosite.util.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.seox.util.PropertiesLoader;

public class DropMenuUtil {
    
    private static Logger m_logger = Logger.getLogger(DropMenuUtil.class);
    private static Map m_map = new HashMap();
    
    
    static{
        String file = PropertiesLoader.getInstance().getProperty("app.cfg.dropmenus");

        m_logger.debug("########################### Loading drop-menu properties from " + file);
        InputStream ins = null;

        try {
            ins = PropertiesLoader.class.getResourceAsStream(file);
            if (ins == null)
                ins = PropertiesLoader.class.getResourceAsStream("/" + file);

            BufferedReader in = new BufferedReader(new InputStreamReader(ins));
            while (true) {
                String line = in.readLine();
                m_logger.debug("## " + line);
                if (line == null)
                    break;

                int pos = line.indexOf("=");
                if (pos < 0) {
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }

                if (line.trim().startsWith("#") || line.trim().equals("")) {
                    continue;
                }

                String menuItemName = line.substring(0, pos);
                String items = line.substring(pos + 1);
                String compareValue = ""; // This is optional. By default it should be the same with parts[2]
                String[] parts = items.split(",");
                
                if (parts.length < 3){
                    m_logger.warn("Line corrupted = " + line);
                    continue;
                }
                
                if (parts.length == 4) {
                    compareValue = parts[3];
                    if (parts[3].equalsIgnoreCase("NULL")|| parts[3].equals(""))
                        compareValue = "";
                            
                } else {
                    compareValue = parts[2];
                }

                DropMenuItem item = new DropMenuItem(menuItemName, parts[0], parts[1], parts[2], compareValue);
                
                List list = (List) m_map.get(menuItemName);
                if (list == null) {
                    list = new ArrayList();
                    m_map.put(menuItemName, list);
                } 
            
                list.add(item);
            }

        }
        catch (Exception e) {
        }
        finally {
            try {
                if (ins != null)
                    ins.close();
            }
            catch (Exception e) {
            }
        }
        m_logger.debug("########################### Loading drop-menu properties from " + file);
        
    }

    public static List getDropMenus(String name){
        if ( m_map.containsKey(name))
            return (List) m_map.get(name);
        
        return new ArrayList();
    }
    
    public static void main(String[] args) {
        System.out.println(DropMenuUtil.getDropMenus("DynamicPages"));
        List list = DropMenuUtil.getDropMenus("DynamicPages");
        System.out.println(list.size());
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            DropMenuItem    item = (DropMenuItem) iterator.next();
            System.out.println(item);
            
        }
    }
    
}
