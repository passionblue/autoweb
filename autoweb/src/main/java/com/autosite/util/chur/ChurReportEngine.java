package com.autosite.util.chur;

import org.apache.log4j.Logger;

import com.autosite.ds.ChurIncomeDS;
import com.autosite.ds.event.DSEvent;
import com.autosite.ds.event.DSEventListener;
import com.autosite.holder.ChurExpenseDataHolder;
import com.autosite.holder.ChurIncomeDataHolder;

public class ChurReportEngine implements DSEventListener {

    private static Logger m_logger = Logger.getLogger(ChurReportEngine.class);
    private static ChurReportEngine m_instance = new ChurReportEngine();

    public static ChurReportEngine getInstance() {
        return m_instance;
    }

    private ChurReportEngine() {
        ChurIncomeDS.getInstance().addUpdateListener("ChurReport", this);
        
    }

    public void updated(DSEvent event) {

        if ( event == null || event.getObject() == null) return;

        ChurIncomeDataHolder expense = (ChurIncomeDataHolder) event.getObject();
//        m_logger.debug(event.getSourceDS().objectToString2(event.getObject())); 
    }
    
    public static void main(String[] args) {
        ChurReportEngine.getInstance();
    }
    
}
