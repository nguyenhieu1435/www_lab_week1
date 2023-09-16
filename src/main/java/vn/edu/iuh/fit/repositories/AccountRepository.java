package vn.edu.iuh.fit.repositories;

import jakarta.persistence.Query;
import vn.edu.iuh.fit.models.Account;

import java.util.List;
import java.util.Optional;

public class AccountRepository extends GenericCRUD<Account>{

    public AccountRepository() {

    }

    public Optional<Account> login(String accountId, String password) throws NullPointerException{
        String sqlQuery = "select * from account a where a.account_id  = ?1 and a.password  = ?2 limit 1";
        List<Account> accounts = em.createNativeQuery(sqlQuery, Account.class).setParameter(1, accountId).setParameter(2, password).getResultList();

        return Optional.of(accounts.size() > 0 ? accounts.get(0) : null);
    }


    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
       ;
       Optional<Account> op = accountRepository.findOne(Account.class, "met");

       Account acc = op.isPresent()  ? op.get() : null;

        System.out.println(accountRepository.delete(Account.class, "hieu"));
    }
    public List<Account> getAccountsByRoleId(String roleId){
        String sqlQuery = "select a.* from `role` r \n" +
                "join grant_access ga on r.role_id  = ga.role_id \n" +
                "join account a on a.account_id  = ga.account_id \n" +
                "where r.role_id = ?1";
        Query query = em.createNativeQuery(sqlQuery, Account.class);
        query.setParameter(1, roleId);
        return (List<Account>) query.getResultList();
    }

}
