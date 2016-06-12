package com.autosite.util;

import java.util.StringTokenizer;

import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class PanelStyleUtil {

    // Get called by panel_dynamic_menu_contect_section to calculate column width for multi-column panels. 
    // panelColumWidthOptionsString is from panel.getOptions1();
    
    public static int [] getWidthForMultiColumnPanels( int columnCount, String panelColumWidthOptionsString ){
        
        int widthRate = (100 - columnCount + 1)/columnCount;
        int widthRateArray[] = new int[columnCount];
        
        if ( WebUtil.isNull(panelColumWidthOptionsString)){
            
            for (int col = 1; col<= columnCount;col++) {
                widthRateArray[col-1] = widthRate; 
            }       
            
        } else {
            
            // expecting option1 value of Panel, contains like 50-30-20 to represent rate of each column
            StringTokenizer tok = new StringTokenizer(panelColumWidthOptionsString, ",|=-:");
            
            int idx = 0;
            while(tok.hasMoreTokens()){
                widthRateArray[idx] = WebParamUtil.getIntValue(tok.nextToken());
                idx++;
            }
        }
        
        // Auto fill for the empty rate
        for (int idx = 0; idx< columnCount;idx++) {
            if ( widthRateArray[idx] == 0 && idx > 0){
                int rateOfPrevColumn =widthRateArray[idx-1];
                
                int newRate = rateOfPrevColumn/2;
                
                widthRateArray[idx-1] = newRate;
                widthRateArray[idx] = newRate;
            }
        }       
        
        // in case sum over 100, decduct evenly 
        int total = 0;  
        for (int idx = 0; idx< columnCount;idx++) {
            total += widthRateArray[idx];
        }

        if ( total > 100) {
            
            double ratio = 100.0/(long)total;
            
            for (int idx = 0; idx< columnCount;idx++) {
                widthRateArray[idx] = (int)(widthRateArray[idx]*ratio);
            }
        }
        
        
        return widthRateArray;
    }
}
