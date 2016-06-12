/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:43 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;
import java.util.List;


import com.autosite.db.ExpenseItem;

public interface ExpenseItemRepository extends PagingAndSortingRepository<ExpenseItem> {

    ExpenseItem save(ExpenseItem customer);
    ExpenseItem findOne(Long id);
    List<ExpenseItem> removeById(Long id);

    List<ExpenseItem> findById(Long value);
    List<ExpenseItem> findBySiteId(Long value);
    List<ExpenseItem> findByExpenseCategoryId(Long value);
    List<ExpenseItem> findByExpenseItem(String value);
    
}


