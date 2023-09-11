package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.edu.iuh.fit.database.Connection;

import java.util.List;

public class GenericCRUD<T>{
    protected EntityManager em;

    public GenericCRUD(){
        em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public boolean add(T t){
        EntityTransaction tr = em.getTransaction();

        tr.begin();
        try {
            em.persist(t);
            tr.commit();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            tr.rollback();
        }
        return false;
    }
    public boolean update(T t) {
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            em.merge(t);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            ;
            tr.rollback();
        }
        return false;
    }
    public boolean delete(Class<T> clazz, Object id){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            em.remove(findOne(clazz, id));
            tr.commit();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            tr.rollback();;
        }
        return false;
    }
    public T findOne(Class<T> clazz, Object id){
        return em.find(clazz, id);
    }
    public List<T> getAll(Class<T> clazz){
        return em.createQuery("from " + clazz.getName() , clazz).getResultList();
    }

}
