/* 
Template last modification history:


Source Generated: Sun Jul 12 20:19:41 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;
import java.util.List;


import com.autosite.db.ExpenseCategory;

public interface ExpenseCategoryRepository extends PagingAndSortingRepository<ExpenseCategory> {

    ExpenseCategory save(ExpenseCategory customer);
    ExpenseCategory findOne(Long id);
    List<ExpenseCategory> removeById(Long id);

    List<ExpenseCategory> findById(Long value);
    List<ExpenseCategory> findBySiteId(Long value);
    List<ExpenseCategory> findByExpenseCategory(String value);
    
}


