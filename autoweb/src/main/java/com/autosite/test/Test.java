package com.autosite.test;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.autosite.db.ChurIncome;
import com.autosite.db.ChurIncomeCategory;
import com.autosite.db.ChurMember;
import com.autosite.db.ChurPayee;
import com.autosite.ds.ChurIncomeCategoryDS;
import com.autosite.ds.ChurIncomeDS;
import com.autosite.ds.ChurMemberDS;
import com.autosite.ds.ChurPayeeDS;
import com.autosite.holder.ChurIncomeDataHolder;
import com.autosite.util.chur.ChurReportUtil;
import com.autosite.util.chur.ChurUtil;

public class Test {
    public static void main(String[] args) {
        ChurMember m = new ChurMember();
        m.setSiteId(100);
        m.setFullName("xx");
        m.setFirstName("윤");
        m.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        
        ChurMemberDS.getInstance().put(m);
        

        ChurIncomeCategory c = new ChurIncomeCategory();
        c.setIncomeCategory("현주");
        c.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        ChurIncomeCategoryDS.getInstance().put(c);

        ChurPayee ee = new ChurPayee();
        
        ee.setTitle("감나무");
        ee.setRemark("보자기");
        
        ChurPayeeDS.getInstance().put(ee);
        
        System.out.println("!!!!!!!!!!!!!!!!");
        
        //List lst = ChurReportUtil.getSortedMemberListForItem(238, "01/15", "tithe");
        
        //System.out.println(lst);
        
    }
    public static void main2(String[] args) {
//        ChurIncomeItem incomeItem = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(238, "tithe");
//        System.out.println(incomeItem.getDisplay());
        
//        System.out.println(ChurUtil.getWeekString());
        
        
        System.out.println(ChurUtil.getWeeksForYear(2012));
        
        Map m = ChurIncomeDS.getInstance().getMapBySiteIdWeekList(238 , "1/1");
        
        
        for (Iterator iterator = m.values().iterator(); iterator.hasNext();) {
            ChurIncomeDataHolder income = (ChurIncomeDataHolder) iterator.next();

            System.out.println(ChurIncomeDS.objectToString((ChurIncome)income.getDataObject()));
            
        }

        List<Map<String,Object>> list = ChurReportUtil.getFormatIncomeList(238, 2012, "01/15");
    
    
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Map<String, ChurIncome> map = (Map<String, ChurIncome>) iterator.next();

            m_logger.debug("--------------------------------");
            
            ChurIncome t = map.get("tithe"); 
            if (t != null ) m_logger.debug("t=" + t.getAmmount());
            
            ChurIncome w = map.get("weekly");
            if (w != null ) m_logger.debug("t=" + w.getAmmount());

            ChurIncome th = map.get("thanks");
            if (th != null ) m_logger.debug("t=" + th.getAmmount());

            ChurIncome mi = map.get("mission");
            if (mi != null ) m_logger.debug("t=" + mi.getAmmount());
        }
    
    }
    
    
    
    private static Logger m_logger = Logger.getLogger(Test.class);
}
