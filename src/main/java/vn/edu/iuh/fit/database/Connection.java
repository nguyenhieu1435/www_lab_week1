package vn.edu.iuh.fit.database;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Connection {
    private static Connection instance;
    private EntityManagerFactory emf;

    private Connection(){
        emf = Persistence.createEntityManagerFactory("week01_lab_NguyenVanHieu_20000175");
    }
    public static Connection getInstance(){
        if (instance == null){
            instance = new Connection();
        }
        return instance;
    }
    public EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }

}
