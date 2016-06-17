package com.webtool.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("fixMsgDao")
@Transactional(propagation = Propagation.REQUIRED)
public class FixMsgDao {

    private static final String SELECT_QUERY = "select f from FIXMsgEntry f";

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void executeSql(String sql) {
        EntityManager manager = getEntityManager(); 
        Query q = manager.createNativeQuery(sql);
        q.executeUpdate();
        
    }
    
    public void insert(FIXMsgEntry person) {
        entityManager.persist(person);
    }

    public List<FIXMsgEntry> selectAll() {
        Query query = entityManager.createQuery(SELECT_QUERY);
        List<FIXMsgEntry> persons = (List<FIXMsgEntry>) query.getResultList();
        return persons;
    }
    
}
