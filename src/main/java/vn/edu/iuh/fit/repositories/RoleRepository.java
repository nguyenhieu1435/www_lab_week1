package vn.edu.iuh.fit.repositories;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import vn.edu.iuh.fit.models.Account;
import vn.edu.iuh.fit.models.Role;
import vn.edu.iuh.fit.models.Status;

import java.util.List;
import java.util.Optional;

public class RoleRepository extends GenericCRUD<Role>{
    public RoleRepository() {
    }

    @Override
    public boolean delete(Class<Role> clazz, Object id) {
        EntityTransaction tr = em.getTransaction();
        tr.begin();

        try {
            Optional<Role> op = findOne(Role.class, id);
            Role role = op.isPresent() ? op.get() : null;

            if (role != null){
                role.setStatus(Status.DELETE);
                em.merge(role);
            }
            tr.commit();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            tr.rollback();
        }
        return false;
    }

    @Override
    public List<Role> getAll(Class<Role> clazz) {
        String sqlQuery = "select * from role r\n" +
                "where status != ?1";
        Query query = em.createNativeQuery(sqlQuery, Role.class);
        query.setParameter(1, Status.DELETE.getStatusNumber());
        return query.getResultList();
    }
}
