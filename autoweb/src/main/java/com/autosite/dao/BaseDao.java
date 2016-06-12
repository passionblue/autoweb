package com.autosite.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

public class BaseDao<T> {
    @PersistenceUnit(unitName="xx")
    private EntityManagerFactory emf;

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    

    public void persist(Object object) throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            em.persist(object);
            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            if ( tx != null && tx.isActive() ) // withou this, if there is an error, may pri
                tx.rollback(); 
        } finally {
            em.close();
        }
    }

    public void update(Object object) throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            em.merge(object);
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
    public void remove(Object object) throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            em.remove(object);
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
    
    public Object get(Class<T> type, Object key) throws Exception {
        
        EntityManager em = emf.createEntityManager();

        return em.find(type, key);
    }
    
    
    public Collection<T> find(Class<T> type) throws Exception {
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createQuery("SELECT * FROM " + type);
        return query.getResultList();
    }
}
