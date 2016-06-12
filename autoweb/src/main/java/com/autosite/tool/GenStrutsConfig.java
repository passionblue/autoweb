package com.autosite.tool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.autosite.util.DefaultFilePropertiesLoader;

public class GenStrutsConfig {

    public static void main(String[] args) throws Exception{
        DefaultFilePropertiesLoader loader = new DefaultFilePropertiesLoader("/conf/action.properties");

        BufferedWriter writer = new BufferedWriter(new FileWriter("WebRoot/WEB-INF/struts-config-dynamic.xml"));
        
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");writer.newLine();
        writer.write("<!DOCTYPE struts-config PUBLIC \"-//Apache Software Foundation//DTD Struts Configuration 1.2//EN\" \"http://struts.apache.org/dtds/struts-config_1_2.dtd\">");
        writer.newLine();
        writer.write("<struts-config>");writer.newLine();
        
        writer.write("<form-beans>");writer.newLine();
        for (Iterator iterator = loader.getEntries().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            
            writer.write("    <form-bean name=\""+ entry.getKey() + "Form\" type=\"com.autosite.struts.form."+ StringUtils.capitalize(entry.getKey()) + "Form\" />");
            writer.newLine();
            
        }
        writer.write("</form-beans>");writer.newLine();
        writer.write("<action-mappings>");writer.newLine();
        for (Iterator iterator = loader.getEntries().iterator(); iterator.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            
            writer.write("    <action   ");writer.newLine();
            writer.write("        attribute=\""+entry.getKey()+"Form\"");writer.newLine();
            writer.write("        name=\""+entry.getKey()+"Form\"");writer.newLine();
            writer.write("        path=\"/"+entry.getKey()+"Action\"");writer.newLine();
            writer.write("        type=\""+entry.getValue()+"\">");writer.newLine();
            writer.write("         <forward name=\"default\" path=\"/jsp/layout/layout.jsp\" contextRelative=\"true\" />");writer.newLine();
            writer.write("    </action>");writer.newLine();
            
            
            
        }
        writer.write("</action-mappings>");writer.newLine();
        
        writer.write("</struts-config>");writer.newLine();
        writer.flush();
        writer.close();
    }
    
//    public static String do(){
//        return null;
//    }
    
}
