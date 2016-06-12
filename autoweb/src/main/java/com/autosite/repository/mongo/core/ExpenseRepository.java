/* 
Template last modification history:


Source Generated: Sun Jul 12 20:40:22 EDT 2015
*/

package com.autosite.repository.mongo.core;
import org.springframework.data.repository.Repository;
import com.autosite.lab.mongo.core.PagingAndSortingRepository;
import java.util.List;


import com.autosite.db.Expense;

public interface ExpenseRepository extends PagingAndSortingRepository<Expense> {

    Expense save(Expense customer);
    Expense findOne(Long id);
    List<Expense> removeById(Long id);

    List<Expense> findById(Long value);
    List<Expense> findBySiteId(Long value);
    List<Expense> findByExpenseItemId(Long value);
    List<Expense> findByAmount(Long value);
    List<Expense> findByDescription(String value);
    List<Expense> findByPayMethod(String value);
    List<Expense> findByNotExpense(Integer value);
    List<Expense> findByReference(String value);
    List<Expense> findByDateExpense(String value);
    
}


