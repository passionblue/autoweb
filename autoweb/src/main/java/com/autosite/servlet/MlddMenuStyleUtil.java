package com.autosite.servlet;

import com.autosite.db.LinkStyleConfig;

public class MlddMenuStyleUtil {
    public static String makeDefaultVerticalMlddMenuConfig(String configKeyId){
        LinkStyleConfig config = new LinkStyleConfig();
        config.setBackground("#e0e0e0");
        config.setColor("white");
        config.setHovBackground("maroon");
        config.setHovColor("white");
        
        String vConfig = makeMlddVerticalMenuConfig(configKeyId, config);
        
        return vConfig;
        
    }

    
    public  static String makeDefaultHorizontalMlddMenuConfig(String configKeyId){
        LinkStyleConfig config = new LinkStyleConfig();
        config.setBackground("#e0e0e0");
        config.setColor("white");
        config.setHovBackground("maroon");
        config.setHovColor("white");
        
        String hConfig = makeMlddHorizontalMenuConfig(configKeyId, config);
        
        return hConfig;
        
    }
    
    public  static String makeMlddVerticalMenuConfig(String configKeyId, LinkStyleConfig config){
        StringBuffer buf = new StringBuffer();

        String configSelector = "#menuv" + configKeyId;

        buf.append(configSelector).append("{ position: relative; visibility: hidden; margin: 0;  padding: 0; background-color: #7BA5C9;  border: 1px solid #5970B2;  border-bottom: 1px;}").append("\n");
        buf.append(configSelector).append(" li{  display: inline;    list-style: none;   padding: 0; margin: 0;}").append("\n");
        buf.append(configSelector).append(" li a, ").append(configSelector).append(" li a#buttonnohover{ display: block; margin: 0px; padding: 5px 17px 5px 13px; width: 143px; font: bold 11px arial; text-align: right; text-decoration: none;  white-space: nowrap;    color: black;   background-color: #ECEFE4;  border-bottom: 1px solid #5970B2;}").append("\n");
        buf.append(configSelector).append(" li a:hover, ").append(configSelector).append(" li a#buttonhover{ background-color: #ABC9DE; }").append("\n");

        buf.append(configSelector).append(" ul{z-index: 100; position: absolute; visibility: hidden; margin: 0; padding: 0; border: 1px solid #5970B2}").append("\n");
        buf.append(configSelector).append(" ul li{}").append("\n");
        buf.append(configSelector).append(" ul li a{ width: auto; text-align: left; border-width: 0;color: #2875DE;}").append("\n");
        buf.append(configSelector).append(" ul li a:hover{color: white;}").append("\n");

        buf.append(configSelector).append(" .hsub{background: #ECEFE4 no-repeat right url(/menuscripts/mldd/arrow.gif)}").append("\n");
        buf.append(configSelector).append(" .hsub:hover{background: #ABC9DE no-repeat right url(/menuscripts/mldd/arrow.gif)}").append("\n");        
        return buf.toString();
    }
    
    public  static String makeMlddHorizontalMenuConfig(String configKeyId, LinkStyleConfig config){
        
        boolean setWidth = true;
        
        String widthTop = ";width: 130px";
        String widthDeep = ";width: 96px"; // Deep level has padding 30px total on sides and 2px margin. 

        if (!setWidth) {
            widthTop = "";
            widthDeep = "";
        }
            
        
        
        StringBuffer buf = new StringBuffer();
        String configSelector = "#menuh" + configKeyId;

        buf.append(configSelector).append("{visibility: visible; margin: 2px 0px 2px 0px; padding: 0; white-space: nowrap; overflow: hidden; background-color: white}").append("\n");
        buf.append(configSelector).append(" li { display: inline; list-style: none; float: left; margin: 0; padding: 0 "+ widthTop+"} ").append("\n");
        buf.append(configSelector).append(" li a, ").append(configSelector).append(" li a#buttonnohover { display: block; margin: 0 2px 0 0; padding: 4px 15px 4px 15px; font: bold 11px arial; text-align: center; text-decoration: none; text-transform: uppercase; white-space: nowrap; color: black; background-color: #ECEFE4; border: 1px solid #5970B2}").append("\n");
        buf.append(configSelector).append(" li a:hover, ").append(configSelector).append(" li a#buttonhover { background-color: #ABC9DE}").append("\n");

        buf.append(configSelector).append(" ul{ z-index: 100; position: absolute; visibility: hidden; margin: 2px 0 0 0; padding: 0; background: #ECEFE4; border: 1px solid #5970B2}").append("\n");
        buf.append(configSelector).append(" ul li{ float: none; }").append("\n");
        buf.append(configSelector).append(" ul li a { display: block; width: auto; margin: 0; padding: 5px 17px 5px 13px; font-weight: normal; text-align: left; text-transform: none; white-space: pre; color: #2875DE; background: transparent; border-width: 0"+ widthDeep+"}").append("\n");
        buf.append(configSelector).append(" ul li a:hover{ color: white}").append("\n");

        buf.append(configSelector).append(" .hsub { background: #ECEFE4 no-repeat right url(/menuscripts/mldd/arrow.gif)}").append("\n");
        buf.append(configSelector).append(" .hsub:hover { background: #ABC9DE no-repeat right url(/menuscripts/mldd/arrow.gif)}").append("\n");
        
        
        return buf.toString();
    }   

}
