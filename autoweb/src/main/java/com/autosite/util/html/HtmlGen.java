package com.autosite.util.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.autosite.holder.GenFormFlowDataHolder;

public class HtmlGen {

    public static final int TYPE_STRING_LIST = 1;
    public static final int TYPE_OBJECT_LIST = 2;
    public static final int TYPE_LIST_LIST = 3;
    public static final int TYPE_GEN_ROW = 4;
    
    
    public static final String tableStyle  = "style=\"font: normal normal normal 11px verdana;color: #404040; width: 100%;background-color: #fafafa;border: 1px #6699CC solid;border-collapse: collapse;border-spacing: 0px;text-align: left; margin: 10px 10px 10px 0px;text-indent: 10px;\"";
    public static final String trStyle     = "style=\"border-bottom: 1px #6699CC solid;\"";
    public static final String tdTitletyle = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top; font-weight:bold;\"";
    public static final String tdStyle     = "style=\"border-left: 1px #6699CC solid;text-align: left;padding: 5px 10px 5px 10px;background: #e5e5e5 url('http://www.uxsx.com:8080/images/reply-e5e5e5.gif') repeat-x scroll top;\"";
    
    private int defaultType = TYPE_STRING_LIST;
    
    private StringBuilder builder = new StringBuilder();
    private Map styleMap = new HashMap(); // key by "table" "tr" "td"
    private Map classMap = new HashMap();
    private Map idMap = new HashMap();
    
    private String header = "<table>";
    private String closer = "</table>";
    private List tableRows = new ArrayList();
    
    private boolean modified = false;
    
    public HtmlGen(){
        this(TYPE_STRING_LIST, false);
    }
    
    public HtmlGen(int inDataType, boolean hasTitle){
        defaultType = inDataType;
        styleMap.put("td", tdStyle);
        styleMap.put("tdTitle", tdTitletyle);
        styleMap.put("tr", trStyle);
        styleMap.put("table", tableStyle);
        start();
    }

    //================================================================================================================
    public void start(){
        start(defaultType);
    }
    public void start(int type){
        StringBuilder b = new StringBuilder();
        tableRows = new ArrayList();

        b.append("<table ");
        b.append(getStyle("table"));
        b.append(">");
        header = b.toString();
        modified = true;
        defaultType = type;
    }
    
    public String endAndGet(){

        if ( modified) {
        
            builder = new StringBuilder();
            builder.append(header);
            
            for (Iterator iterator = tableRows.iterator(); iterator.hasNext();) {
                String str = (String) iterator.next();
                builder.append(str);
            }
            builder.append(closer);
            modified = false;
        }
        return builder.toString();
    }

    public String toString(){
        return endAndGet();
    }
    
    //================================================================================================================

    public void addTableRow(String data){
        addTableRow(data, false);
    }
    public void addTableRow(String str, boolean asTitle){
        String row = genTableRowFromStringList(str, asTitle);
        tableRows.add(row);
        modified = true;
    }
    
    public void addTableRow(List data){
        addTableRow(data, false);
    }
    public void addTableRow(List list, boolean asTitle){
        String row = genTableRowFromListList(list, asTitle);
        tableRows.add(row);
        modified = true;
    }
    
    public void addTableRow(HtmlGenRow data){
        addTableRow(data, false);
    }
    public void addTableRow(HtmlGenRow data, boolean asTitle){
        String row = genTableRowFromRowList(data, asTitle);
        tableRows.add(row);
        modified = true;
    }
    
    // Return complete table from the list 
    public void addRows(List list, boolean append, boolean hasTitle){
        StringBuilder sb = new StringBuilder();
        
        if (!append) {
            tableRows.clear();
        }
        
        boolean titleComing = hasTitle && !append;
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            
            String str = "";
            switch(defaultType){
            case TYPE_STRING_LIST: str = genTableRowFromStringList((String)iterator.next(), titleComing); break;
            case TYPE_OBJECT_LIST: str = genTableRowFromObjectList(iterator.next(),titleComing); break;
            case TYPE_LIST_LIST: str = genTableRowFromListList((List)iterator.next(), titleComing); break;
            case TYPE_GEN_ROW: str = genTableRowFromRowList((HtmlGenRow)iterator.next(), titleComing); break;
            default : str = genTableRowFromObjectList(iterator.next(),titleComing); break;
            }
            
            tableRows.add(str);
            sb.append(str);
            
            if (titleComing) titleComing = false;
        }
        modified = true;
    }

    //================================================================================================================
    //================================================================================================================
    
    public String genTableRowFromObjectList(Object data){
        return genTableRowFromObjectList(data, false);
    }    
    public String genTableRowFromObjectList(Object data, boolean asTitle){
        if (data == null) {
            return genTableRowFromStringList("", asTitle);
        }
        return genTableRowFromStringList(data.toString(), asTitle);
    }

    public String genTableRowFromStringList(String data){
        return genTableRowFromStringList(data, false);
    }
    public String genTableRowFromStringList(String data, boolean asTitle){
        StringBuilder sb = new StringBuilder();
        sb.append("<tr ");
        sb.append(getStyle("tr"));
        sb.append(">");

        sb.append("<td ");
        if (asTitle) sb.append(getStyle("tdTitle"));
        else sb.append(getStyle("td"));
        sb.append(">");
        sb.append(data);
        sb.append("</td>");
        
        sb.append("</tr>");
        return sb.toString();
    }

    public String genTableRowFromListList(List data){
        return genTableRowFromListList(data, false);
    }
    public String genTableRowFromListList(List data, boolean asTitle){
        StringBuilder sb = new StringBuilder();
        sb.append("<tr ");
        sb.append(getStyle("table"));
        sb.append(">");

        for (Iterator iterator = data.iterator(); iterator.hasNext();) {
            String colVal = (String) iterator.next();
            
            sb.append("<td ");
            if (asTitle) sb.append(getStyle("tdTitle"));
            else sb.append(getStyle("td"));
            sb.append(">");
            sb.append(colVal);
            sb.append("</td>");
        }
        
        sb.append("</tr>");
        return sb.toString();
    }
    
    public String genTableRowFromRowList(HtmlGenRow data){
        return genTableRowFromRowList(data, false);
    }    
    public String genTableRowFromRowList(HtmlGenRow data, boolean asTitle){
        
        StringBuilder sb = new StringBuilder();
        sb.append("<tr ");
        sb.append(getStyle("tr"));
        sb.append(">");

        for (int i = 0; i < data.getColumnCount(); i++) {
            String colVal = data.getColumnData(i);
            
            sb.append("<td ");
            if (asTitle) sb.append(getStyle("tdTitle"));
            else sb.append(getStyle("td"));
            sb.append(">");
            if (colVal != null) sb.append(colVal);
            sb.append("</td>");
        }
        
        sb.append("</tr>");
        return sb.toString();
    }

    //================================================================================================================
    public String getStyle(String prefix){
        if (styleMap.containsKey(prefix))
            return (String) styleMap.get(prefix);
        return "";
    }
    
    public String getClas(String prefix){
        if (classMap.containsKey(prefix))
            return (String) classMap.get(prefix);
        return "";
    }

    public String getId(String prefix){
        if (idMap.containsKey(prefix))
            return (String) idMap.get(prefix);
        return "";
    }
    
    public void setClass(String prefix, String name){
        classMap.put(prefix, name);
    }   

    public void setId(String prefix, String name){
        idMap.put(prefix, name);
    }   
    
    public void modStyle(String prefix, String name, String value){
        
        String modStr = (String) styleMap.get(prefix);
        
        m_logger.debug("END=" + modStr);
        
        if (modStr == null) {
            modStr = "style=\"" + name+":" + value + ";\"";
        } else {
            
            int pos = modStr.indexOf(name);
            if (pos >= 0 ) {
                int end = modStr.indexOf(";", pos);
                String newMod = "";
                if (end >= 0) {
                    newMod = modStr.substring(0, pos);
                    newMod += modStr.substring(end+1);
                }else {
                    newMod = modStr.substring(0, pos);
                }
                modStr = newMod.trim();
                modStr = newMod.substring(0, newMod.length()-1);
                if (modStr.charAt(modStr.length()-1) == ';')
                    modStr += name+":" + value + ";\"";
                else
                    modStr += ";" + name+":" + value + ";\"";
                
            } else {
                // style item not existing so append at the end
                modStr = modStr.trim();
                modStr = modStr.substring(0, modStr.length()-1);
                modStr += ";" + name+":" + value + ";\"";
            }
        }
        
        m_logger.debug("END=" + modStr);
        styleMap.put(prefix, modStr);
    }
    
    
    //================================================================================================================
    private static Logger m_logger = Logger.getLogger(HtmlGen.class);
    
    public static void main(String[] args) {
        
        List list = new ArrayList();
        
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        HtmlGen gen = new HtmlGen();
        gen.modStyle("table", "text-indent", "14px");
        gen.modStyle("table", "color", "red");
        gen.modStyle("tdTitle", "color", "blue");

        
        gen.addRows(list, false, true);
        
        String s = gen.toString();
        
        System.out.println(s);
        
        System.out.println(GenFormFlowDataHolder.getFieldsName());
        HtmlGen gen2 = new HtmlGen(HtmlGen.TYPE_GEN_ROW, false);
//        gen2.addTableRow(fieldSetStr, true);
    }
}
