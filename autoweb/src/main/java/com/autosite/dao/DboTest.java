package com.autosite.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.autosite.dbmodel.ChurExpense;
import com.autosite.mail.AutositeMailServiceFactory;

public class DboTest {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-persistence.xml");
        BaseDao dao = (BaseDao) context.getBean("SomeDao");

        ChurExpense e = new ChurExpense();
        e.setId(new Long(System.nanoTime()));
        dao.persist(e);
        
        
        System.out.println(dao.find(ChurExpense.class));
    }
}
