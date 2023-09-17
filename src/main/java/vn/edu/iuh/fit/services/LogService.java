package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.Log;
import vn.edu.iuh.fit.repositories.LogRepository;

import java.util.List;

public class LogService {
    private LogRepository logRepository;

    public LogService(){
        this.logRepository = new LogRepository();
    }
    public boolean updateLogoutTime(String accountId){
        return logRepository.updateLogoutTime(accountId);
    }
    public boolean add(Log log){
        return logRepository.add(log);
    }
    public List<Log> getAll(){
        return logRepository.getAll(Log.class);
    }

    public boolean update(Log log){
        return logRepository.update(log);
    }

    public boolean delete(long id){
        return logRepository.delete(Log.class, id);
    }
}
