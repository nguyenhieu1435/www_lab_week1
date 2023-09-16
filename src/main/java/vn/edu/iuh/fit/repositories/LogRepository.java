package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.models.Log;

import java.time.LocalDateTime;

public class LogRepository extends GenericCRUD<Log>{
    public LogRepository() {
    }

    public boolean updateLogoutTime(String accountId){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            String sqlQuery = "update log as l1\n" +
                    "inner join (\n" +
                    "\tselect id from log\n" +
                    "\twhere account_id = ?1\n" +
                    "\torder by login_time desc limit 1\n" +
                    ") as l2 on l1.id = l2.id\n" +
                    "set l1.logout_time = ?2 ";
            Query query = em.createNativeQuery(sqlQuery);

            query.setParameter(1, accountId);
            query.setParameter(2, LocalDateTime.now());

            int result = query.executeUpdate();

            tr.commit();
            if (result > 0){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            tr.rollback();
        }
        return false;
    }
}
