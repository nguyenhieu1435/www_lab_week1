package vn.edu.iuh.fit.repositories;

import vn.edu.iuh.fit.models.Account;

public class AccountRepository extends GenericCRUD<Account>{

    public AccountRepository() {

    }

    public static void main(String[] args) {
        AccountRepository accountRepository = new AccountRepository();
    }

}
