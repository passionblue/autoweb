package com.autosite.util.chur;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.autosite.db.ChurExpense;
import com.autosite.db.ChurIncome;
import com.autosite.db.ChurIncomeItem;
import com.autosite.db.ChurMember;
import com.autosite.ds.ChurExpenseDS;
import com.autosite.ds.ChurIncomeDS;
import com.autosite.ds.ChurIncomeItemDS;
import com.autosite.ds.ChurMemberDS;
import com.autosite.holder.ChurIncomeDataHolder;

public class ChurReportUtil {

    // returns list of incomes in map by income item and listed/sorted by members
    public static List<Map<String,Object>> getFormatIncomeList(long siteId, int year, String week) {

        ChurIncomeItemDS itemDS = ChurIncomeItemDS.getInstance();
        ChurMemberDS memDS = ChurMemberDS.getInstance();
        
        Map formattedByMember = new HashMap();
        
        List list = ChurIncomeDS.getInstance().getBySiteIdWeekList(siteId, week);

        // This is item Total line with ID 0
        Map itemTotalMap = new HashMap();
        itemTotalMap.put("isTotalLine", "true");
        formattedByMember.put(new Long(0),itemTotalMap);
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurIncome income = (ChurIncome)((ChurIncomeDataHolder) iterator.next()).getDataObject();

            if ( income.getYear() != year) continue;
            
            long memberId = income.getChurMemberId();
            long itemId = income.getIncomeItemId();
            
            Map individualOfferMap = (Map) formattedByMember.get(new Long(memberId));
            if ( individualOfferMap == null) {
                individualOfferMap = new HashMap();
                formattedByMember.put(new Long(memberId), individualOfferMap);
                
                ChurMember mem = memDS.getById(memberId); 
                
                individualOfferMap.put("member", mem);
                individualOfferMap.put("memberTotal", new Double(0.0));
            }
                
            ChurIncomeItem item =  itemDS.getById(itemId);
            if ( item == null) {
                m_logger.info("** Item not found for " + itemId);
                continue;
            }
            
            // Same item already exists. Add up. 
            if ( individualOfferMap.containsKey((item.getIncomeItem()))){
                ChurIncome inc = (ChurIncome) individualOfferMap.get(item.getIncomeItem());
                inc.setAmmount(inc.getAmmount() + income.getAmmount());
            } else 
                individualOfferMap.put(item.getIncomeItem(), ChurIncomeDS.copy(income));
            
            // Add to Individual Total 
            double subTotal = ((Double)individualOfferMap.get("memberTotal")).doubleValue() + income.getAmmount();
            individualOfferMap.put("memberTotal", new Double(subTotal));
            
            // Add to Item Total
            if ( itemTotalMap.containsKey((item.getIncomeItem()))){
                double itemSubTotal = ((Double) itemTotalMap.get(item.getIncomeItem())).doubleValue() + income.getAmmount();
                itemTotalMap.put(item.getIncomeItem(), new Double(itemSubTotal));
            } else 
                itemTotalMap.put(item.getIncomeItem(), new Double(income.getAmmount()));
        }
        
        return new ArrayList(formattedByMember.values());
    }

    // returns list of incomes in map by income item and listed/sorted by members
    public static List<ChurIncome> getYearlyIncomeListForMember(long siteId, int year, long memberId) {
        
        List all = ChurIncomeDS.getInstance().getBySiteIdToChurMemberIdList(siteId, memberId);
        
        List ret = new ArrayList();
        
        
        for (Iterator iterator = all.iterator(); iterator.hasNext();) {
            ChurIncome inc = (ChurIncome) iterator.next();
            
            if (inc.getYear() != year) continue;
            
            ret.add(inc);
        }
        
        return ret;
    }
    
    public static List<ChurIncome> getIncomeList(long siteId, int year, String week) {
        
        List ret = new ArrayList();
        
        List list = ChurIncomeDS.getInstance().getBySiteIdWeekList(siteId, week);
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurIncome income = (ChurIncome)((ChurIncomeDataHolder) iterator.next()).getDataObject();
            
            if ( income.getYear() == year) {
                ret.add(income);
            }
            
        }        

        return ret;
    }    
    
    // Returns member in sorted order for the week.
    public static List<Map<String,Object>> getSortedMemberListForItem(long siteId, String week, String incomeItem ) {
        return getSortedMemberListForItem(siteId, ChurUtil.getCurrentYear(), week, incomeItem);
    }
    public static List<Map<String,Object>> getSortedMemberListForItem(long siteId, int year, String week, String incomeItem ) {
        
        
        List list = ChurIncomeDS.getInstance().getBySiteIdWeekList(siteId, week);

        Map itemTotalMap = new HashMap();

        ChurIncomeItem item = ChurIncomeItemDS.getInstance().getBySiteIdToIncomeItem(siteId, incomeItem);
        if (item == null) return new ArrayList();
        
        
        TreeSet<String> byNames = new TreeSet();
        TreeMap<Integer, String> byPriority = new TreeMap();
        TreeMap<Integer, String> byUnderPriority = new TreeMap();
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurIncome income = (ChurIncome)((ChurIncomeDataHolder) iterator.next()).getDataObject();
        
            if ( income.getIncomeItemId() != item.getId() || income.getAmmount() == 0.0)
                continue;
            if ( income.getYear() != year) continue;
            
            ChurMember mem = ChurMemberDS.getInstance().getById(income.getChurMemberId());
            if ( mem == null ) continue;
            
            if ( mem.getListIndex() > 0) {
                byPriority.put(new Integer(mem.getListIndex()), mem.getFullName());
            } else if ( mem.getListIndex() < 0) {
                byUnderPriority.put(new Integer(mem.getListIndex()), mem.getFullName());
            } else {
                byNames.add(mem.getFullName());
            }
        }

        List ret = new ArrayList();
        
        ret.addAll(byPriority.values());
        Collections.reverse(ret);
        ret.addAll(byNames);
        ret.addAll(byUnderPriority.values());
        return ret;
    }

    // returns total YTD income amount
    public static double getYearTotalIncomeAmount(long siteId, int year){
        
        List list = ChurIncomeDS.getInstance().getBySiteIdYear(siteId, year);
        
        double total = 0.0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurIncomeDataHolder chur = (ChurIncomeDataHolder) iterator.next();
            total += chur.getAmmount();
        }
        return total;
    }

    // returns total YTD income amoun
    public static double getYearTotalExpenseAmount(long siteId, int year){
        
        List list = ChurExpenseDS.getInstance().getBySiteIdYear(siteId, year);
        
        double total = 0.0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurExpense chur = (ChurExpense) iterator.next();
            total += chur.getAmount();
        }
        return total;
    }
    
    public static double getWeeklyTotalIncomeAmount(long siteId, int year, String week){
        
        List list = ChurIncomeDS.getInstance().getBySiteIdWeekList(siteId, week);
        
        double total = 0.0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurIncomeDataHolder chur = (ChurIncomeDataHolder) iterator.next();
            if ( chur.getYear() != year) continue;
            total += chur.getAmmount();
        }
        return total;
    }

    // returns total YTD income amount
    public static double getWeeklyTotalExpenseAmount(long siteId, int year, String week){
        
        List list = ChurExpenseDS.getInstance().getBySiteIdWeekList(siteId, week);
        
        double total = 0.0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurExpense chur = (ChurExpense) iterator.next();
            if ( chur.getYear() != year) continue;
            total += chur.getAmount();
        }
        return total;
    }
    
    public static Map getYearlyTotalExpenseAmount(long siteId, int year){
        
        List list = ChurExpenseDS.getInstance().getBySiteIdYear(siteId, year);
        
        Map<Object, BigDecimal> ret = new HashMap<Object, BigDecimal>();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurExpense chur = (ChurExpense) iterator.next();
            if ( chur.getYear() != year) continue;
            
            updateTotalMap(ret, new Long(chur.getExpenseItemId()), chur.getAmount());
            
        }
        
        return ret;
    }    

    public static Map getYearlyTotalIncomeAmountByItem(long siteId, int year){
        
        List list = ChurIncomeDS.getInstance().getBySiteIdYear(siteId, year);
        
        Map<Object, BigDecimal> ret = new HashMap<Object, BigDecimal>();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurIncomeDataHolder income = (ChurIncomeDataHolder) iterator.next();
            if ( income.getYear() != year) continue;
            
            updateTotalMap(ret, new Long(income.getIncomeItemId()), income.getAmmount());
        }
        
        return ret;
    }    

    public static Map getYearlyTotalIncomeAmountByMember(long siteId, int year){
        
        List list = ChurIncomeDS.getInstance().getBySiteIdYear(siteId, year);
        
        Map<Object, BigDecimal> ret = new HashMap<Object, BigDecimal>();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ChurIncomeDataHolder income = (ChurIncomeDataHolder) iterator.next();
            if ( income.getYear() != year) continue;
            
            if ( income.getIncomeItemId() != 21 ) 
                continue;
            
            updateTotalMap(ret, new Long(income.getChurMemberId()), income.getAmmount());
        }
        
        return ret;
    }    

    
    // 
    private static void updateTotalMap(Map<Object, BigDecimal> map, Object key, double amount){
        
        if ( map.containsKey(key)) {
            double newAmount = map.get(key).doubleValue() + amount;
            map.put(key, new BigDecimal(newAmount));
        } else {
            map.put(key, new BigDecimal(amount));
        }
        
    }
    
    private static Logger m_logger = Logger.getLogger(ChurReportUtil.class);
}
