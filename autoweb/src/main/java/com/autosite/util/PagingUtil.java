package com.autosite.util;

import java.util.ArrayList;
import java.util.List;

public class PagingUtil {
    
    public static PagingInfo getPageDisplayInfo(List list, int numDisplayInPage, int displayPage){
        
        int totalDisplayPage = list.size()/numDisplayInPage + (list.size()%numDisplayInPage==0?0:1);
        if ( displayPage > totalDisplayPage) {
            displayPage = totalDisplayPage;
        }   
        
        if ( displayPage < 1) displayPage = 1;

        int beginIdx = (displayPage-1)*numDisplayInPage;
        int endIdx = beginIdx + numDisplayInPage;
        if ( endIdx > list.size()) endIdx = list.size();
        boolean hasPrevious  = (displayPage >1 ); 
        boolean hasNext  = (displayPage < totalDisplayPage ); 

        
        PagingInfo pagingInfo = new PagingInfo();
        
        pagingInfo.setBeginIdx(beginIdx);
        pagingInfo.setEndIdx(endIdx);
        pagingInfo.setHasNext(hasNext);
        pagingInfo.setHasPrev(hasPrevious);
        pagingInfo.setCurDisplayPage(displayPage);
        pagingInfo.setTotalNumPages(totalDisplayPage);

        return pagingInfo;
        
        
        
        
/*        
        String prevLinkStr = "[go prev]";
        if (hasPrevious) 
            prevLinkStr = "<a href=\"t_ec_wish_list_main.html?listPage=" +(displayPage -1)+ "\">"+ prevLinkStr + "</a>";

        String nextLinkStr = "[go next]";
        if (hasNext) 
            nextLinkStr = "<a href=\"t_ec_wish_list_main.html?listPage=" +(displayPage +1)+ "\">"+ nextLinkStr + "</a>";

        
        String pageIndexShortcut[] = new String[totalDisplayPage];
        for (int p = 0; p <= totalDisplayPage-1; p++){
            if ( p == displayPage-1)
                pageIndexShortcut[p] = "<a href=\"t_ec_wish_list_main.html?listPage=" +(p+1)+ "\"><b>"+ (p+1) + "</b></a>";
            else 
                pageIndexShortcut[p] = "<a href=\"t_ec_wish_list_main.html?listPage=" +(p+1)+ "\">"+ (p+1) + "</a>";
        }
*/
    }

    public static String createPagingHtmlFromPagingInfo(PagingInfo pagingInfo, String uri, String optionQueryStr, int listPage ){
        String prevLinkStr = "[go prev]";
        if (pagingInfo.isHasPrev()) 
            prevLinkStr = "<a href=\""+ uri +"?listPage=" +(listPage -1)+ optionQueryStr + "\">"+ prevLinkStr + "</a>";

        String nextLinkStr = "[go next]";
        if (pagingInfo.isHasNext()) 
            nextLinkStr = "<a href=\""+ uri +"?listPage=" +(listPage +1)+ optionQueryStr + "\">"+ nextLinkStr +"</a>";

        prevLinkStr = "<div class=\"pagingNavMove\">" + prevLinkStr + "</div>";
        nextLinkStr = "<div class=\"pagingNavMov\">" + nextLinkStr + "</div>";

        
        String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
        for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
            if ( p == pagingInfo.getCurDisplayPage())
                pageIndexShortcut[p-1] = "<div class=\"pagingNavNumCurrent\"><a href=\""+ uri +"?listPage=" +(p)+ optionQueryStr + "\">"+ (p) + "</a></div>";
            else 
                pageIndexShortcut[p-1] = "<div class=\"pagingNavNum\"><a href=\""+ uri +"?listPage=" +(p)+ optionQueryStr + "\">"+ (p) + "</a></div>";
        }
        
        StringBuffer buf = new StringBuffer();
        buf.append("<div class=\"pagingFrame\">");
        buf.append(prevLinkStr);

        for(int p = 0 ; p< pageIndexShortcut.length; p++){
            buf.append(pageIndexShortcut[p]).append("<div class=\"pagingNavDiv\">").append((p>=pageIndexShortcut.length-1?"":"/")).append("</div>");
        }
        buf.append(nextLinkStr);
        buf.append("</div><div class=\"clear\"></div>");

        return buf.toString();
    }
    
    
    public static List getPagedList(PagingInfo pagingInfo, List orgList){
        List pageList = new ArrayList();
        for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){
            pageList.add(orgList.get(i));
        }

        return pageList;
    }
    
}
