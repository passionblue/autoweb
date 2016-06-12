package com.autosite.util;

import org.apache.struts.taglib.tiles.GetAttributeTag;

import com.autosite.db.Content;
import com.autosite.ds.ContentDS;
import com.jtrend.util.WebUtil;

public class ContentUtil {

    public static String getContentAbstract(Content content, int length) {

        return getBeginingOfContent(content, length);
        
        
//        String ret = "";
//        if (content.getContent() != null && content.getContent().length() > length) {
//            int idx = content.getContent().indexOf(" ", length);
//            if (idx >= length || idx == -1) {
//                return content.getContent().substring(0, length);
//            } else {
//                return content.getContent().substring(0, idx);
//            }
//        }
//        else {
//            ret = content.getContent();
//        }
//        
//        return ret;
    }
    
    // Return the content at least <length> long content. The end of the string will either 
    public static String getBeginingOfContent(Content content, int length) {
    
        if ( content.getContentAbstract() != null) 
            return formatToHtml(content.getContentAbstract());
            

        String cont = content.getContent();

        if (cont.length() < length )
            return cont;
        
        int endPos = cont.indexOf("</p>", length);
        
        if ( endPos < 0)
            endPos = cont.indexOf("\n", length);
        else {
            return cont.substring(0, endPos+4);
        }
        
        if ( endPos < 0)
            endPos = cont.indexOf(".", length);
        else {
            return cont.substring(0, endPos);
        }
        
        if ( endPos < 0) {
            endPos = cont.indexOf(" ", length);
        }
        else {
            return cont.substring(0, endPos+1);
        }

        if ( endPos < 0) {
            return cont.substring(0, length);
        }
        else {
            return cont.substring(0, endPos);
        }
    }
    
    // Convert to Plain text to surrounded and divided by <p></p>
    public static String formatToHtml(String str){
        int pos1 = str.indexOf("<p>");
        int pos2 = str.indexOf("</p>");
        
        if (pos1<0) pos1 = str.indexOf("<P>");
        if (pos2<0) pos2 = str.indexOf("</P>");
        
        if ( pos1 >=0 && pos2 >=0 ) return str;
        
        String ret = "<p>" + str.trim() + "</p>";
        ret = ret.replace("\n", "</p><p>");

        return ret;
    }

    public static String stripHtml(String str){
        if (str == null) return "";

        StringBuffer ret = new StringBuffer();
        boolean isHtmlTags = false;
        for(int i = 0;i <str.length();i++){
            
            char c = str.charAt(i);
            
            if ( c == '<'){
                isHtmlTags = true;
                continue;
            } else if ( c == '>'){
                isHtmlTags = false;
                continue;
            }
            
            if (!isHtmlTags) ret.append(c);
        }
        
        return ret.toString();
    }
    
    public static String getContentHeading(Content content, int length) {
        if (WebUtil.isNull(content.getContentSubject())){

            return getContentAbstract(content, length);
        }

        return content.getContentSubject();
    }    
    
    public static String makeShortcutUrl(Content content){
        
        if (content == null) return "INVALIDURL";
        
        if ( content.getShortcutUrl() != null && !content.getShortcutUrl().equals("")){
            return content.getShortcutUrl();
        }
        
        return makeShortcutUrl(content.getContentSubject());
    }    
    
    public static String makeShortcutUrl(String subject){
        if (subject == null) return "";
        
        String ret = stripHtml(subject);
        ret = subject.trim().replaceAll(" ", "-");
        ret = ret.replaceAll("'", "");
        ret = ret.replaceAll("\"", "");
//        ret = ret.replaceAll("\\", "");
        ret = ret.replaceAll("&", "");
        
        return ret;
    }
    
    public static String getContentPartByType(int type){
        switch(type){
        case 0: return "/jsp/contents/parts/partContentDefault.jsp";
        default: return "/jsp/contents/parts/partContentDefault.jsp";
        }
    }
    
    
    public static void main(String[] args) {
        
//        Content c = ContentDS.getInstance().getById((long)257);
//        System.out.println(ContentDS.objectToString(c));
//        System.out.println(getContentHeading(c, 30));
        
        String org1 = "<p> this is test content to this is what </p><p> this is test content to this is what </p>";
        String org2 = "this is test content to this is what this is test content to this is what";
        String org3 = "this is test content to this is what this is test contentxxxxx. to this is what";
        String org4 = "this is test content to this is what this is testcontenttothisiswhat";
        String org5 = "this is test content\n to this is what this is test\n content to this\n is what";
        
        Content c = new Content();
        
        c.setContent(org1);
        System.out.println(getBeginingOfContent(c, 60));
        c.setContent(org2);
        System.out.println(getBeginingOfContent(c, 60));
        c.setContent(org3);
        System.out.println(getBeginingOfContent(c, 60));
        c.setContent(org4);
        System.out.println(getBeginingOfContent(c, 60));
        c.setContent(org5);
        System.out.println(getBeginingOfContent(c, 60));
        
    }

}
