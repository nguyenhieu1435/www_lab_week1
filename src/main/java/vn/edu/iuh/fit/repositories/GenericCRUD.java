package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.edu.iuh.fit.database.Connection;

import java.util.List;
import java.util.Optional;

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
            Optional<T> op = findOne(clazz, id);
            em.remove(op.isPresent() ? op.get() : null);
            tr.commit();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            tr.rollback();;
        }
        return false;
    }
    public Optional<T> findOne(Class<T> clazz, Object id){
         T t = em.find(clazz, id);
        return t != null ? Optional.of(t) : Optional.empty();
    }
    public List<T> getAll(Class<T> clazz){
        return em.createQuery("from " + clazz.getName() , clazz).getResultList();
    }

}
