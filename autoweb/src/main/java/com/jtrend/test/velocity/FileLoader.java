package com.jtrend.test.velocity;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import com.seox.util.PropertiesLoader;

public class FileLoader {
    String appRootDir = "velocity/apps/forum/";
    Map m_map = new HashMap();
    
    public FileLoader(){
        
        PropertiesLoader allProps = new PropertiesLoader(appRootDir + "definition.properties");
        String dsGenList = allProps.getProperty("form.list");
        
        StringTokenizer tokenizer = new StringTokenizer(dsGenList, ", ");

        while (tokenizer.hasMoreTokens()) {
            
            String appName = tokenizer.nextToken();
            Properties subProps = AutoGen.getSubProperties(allProps, appName);
            
            ColumnData data = new ColumnData();
            
            data.name = subProps.getProperty("name", null);
            data.typeName = subProps.getProperty("typeName", null);
            data.display = subProps.getProperty("display", null);
//            data.size = subProps.getProperty("size", null);
            
            System.out.println(data);
            
            m_map.put(data.name, data);
        }

    }
    
    public ColumnData getData(String name){
        return (ColumnData) m_map.get(name);
    }
    
    public static void main(String[] args) {
        new FileLoader();
    }
}
