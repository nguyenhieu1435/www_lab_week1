package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.database.Connection;
import vn.edu.iuh.fit.models.GrantAccess;
import vn.edu.iuh.fit.models.IsGrant;

import java.util.List;
import java.util.Optional;

public class GrantAccessRepository {
    private EntityManager em;


    public GrantAccessRepository(){
        this.em = Connection.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public boolean add(GrantAccess grantAccess){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            em.persist(grantAccess);
            tr.commit();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            tr.rollback();
        }
        return false;
    }
    public boolean update(GrantAccess grantAccess){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            em.merge(grantAccess);
            tr.commit();
            return true;
        } catch (Exception e){
            e.printStackTrace();;
            tr.rollback();
        }
        return false;
    }
    public Optional<GrantAccess> findOne(String accountId, String roleId){
        String sqlQuery = "select * from grant_access ga  where account_id  = ?1 and role_id = ?2";

        Query query = em.createNativeQuery(sqlQuery, GrantAccess.class);
        query.setParameter(1, accountId);
        query.setParameter(2, roleId);
        List<GrantAccess> grantAccesses = query.getResultList();

        if (grantAccesses == null || grantAccesses.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(grantAccesses.get(0));
        }
    }
    public boolean delete(String accountId, String roleId){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            Optional<GrantAccess> op = findOne(accountId, roleId);
            if (op.isPresent()){
                GrantAccess grantAccess = op.get();
                grantAccess.setIsGrant(IsGrant.DISABLE);
                em.merge(grantAccess);
                tr.commit();
                return true;
            } else {
                tr.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();

        }
        return false;
    }
    public List<GrantAccess> getAll(){
        String sqlQuery = "select * from grant_access ga  \n" +
                "where is_grant != ?1";
        Query query = em.createNativeQuery(sqlQuery, GrantAccess.class);
        query.setParameter(1, "0");
        return query.getResultList();
    }

}
