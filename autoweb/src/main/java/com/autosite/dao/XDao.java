package com.autosite.dao;
import java.util.List;

import com.autosite.dbmodel.ChurExpense;

public class XDao extends BaseDao<ChurExpense> {

    List<ChurExpense> getAll(String key) throws Exception {
        return (List<ChurExpense>) super.find(ChurExpense.class);
    }
}
